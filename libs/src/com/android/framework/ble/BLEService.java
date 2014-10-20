package com.android.framework.ble;

import java.util.ArrayList;
import java.util.UUID;

import com.android.framework.ble.exception.BLEServiceExcpetion;
import com.android.framework.ble.exception.NotInitializeException;
import com.android.framework.ble.exception.NotSupportBLEException;
import com.android.framework.ble.listener.BLEConnDevicesListener;
import com.android.framework.ble.listener.BLEConnServiceListener;
import com.android.framework.ble.service.IBLEBackgroundService;
import com.android.framework.ble.util.BLEFeature;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class BLEService implements BLEFeature{
	private static final String TAG = BLEService.class.getSimpleName();

	private Context mContext = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BLEServiceUtil mBleServiceUtil = null;

	private ArrayList<String> mRegUUIDList = new ArrayList<String>();
	private String[] mPrepareUUIDString = null;

	private BLEConnServiceListener mConnServiceListener = null;
	private BLEDeviceReceiver mBleBR = null;
	
	private BLEDeviceUtil mBleDeviceUtil = BLEDeviceUtil.instance();
	
	private interface BleState {
		int IDLE = 0;
		int INITIALIZED = 1;
		int SERVICE_STOPED = 2;
		int SERVICE_RUNNING = 3;

		int START_BLE_SCANNING = 11;
		int START_BLE_SCAN_STOP = 12;
	}

	private int mCurrentstate = BleState.IDLE;

	public BLEService(Context context) {
		mContext = context;
		mBleServiceUtil = new BLEServiceUtil();
		mCurrentstate = BleState.INITIALIZED;
		mBleBR = new BLEDeviceReceiver();
		
	}

	public BLEService(Context context, UUID[] uudiArr) {
		this(context);
		initBLE(mBleServiceUtil.convertUUIDtoString(uudiArr));
	}

	public BLEService(Context context, String[] uudiString) {
		this(context);
		initBLE(uudiString);
	}

	// ////////////////////
	// Common BT API Start
	private void initBLE(String[] uuid) {
		Log.d(TAG, "initBLE() start");
		if (uuid != null && uuid.length > 0) {
			try {
				registerUUID(uuid);
			} catch (BLEServiceExcpetion e) {
				// TODO Auto-generated catch block
				e.printLog(TAG);
			}
		}
		Log.d(TAG, "initBLE() end - mCurrent : " + mCurrentstate);
	}

	private boolean initService() {
		Log.d(TAG, "initService start.");
		if (BLE_SUPPORT_SDK) {
			final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
			mBluetoothAdapter = bluetoothManager.getAdapter();
		} else {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}

		Log.d(TAG, "initService BLEAdaper = " + mBluetoothAdapter);
		if (mBluetoothAdapter == null)
			return false;

		if (mPrepareUUIDString != null && mPrepareUUIDString.length > 0) {
			Log.i(TAG, "prepared regUUID would register at BG service.");
			mBleServiceUtil.registerUUIDFromApp(mContext, mPrepareUUIDString);
			mPrepareUUIDString = null;
		}

		return true;
	}

	// Start Service
	// BLEState == INITIALIZED
	public void startBTbgService(BLEConnServiceListener listener) throws BLEServiceExcpetion {

		if (mCurrentstate <= BleState.IDLE) {
			throw new NotInitializeException("startBTbgService - current State is IDLE");
		}

		if (mBleServiceUtil.isRunningbgService(mContext)) {
			Log.i(TAG, "Already run BLEService by other Application");
		} else {
			if (isRunningService()) {
				throw new BLEServiceExcpetion("BLEService - already run BLEService");
			}
		}
		mConnServiceListener = listener;
		Log.d(TAG, "onBindService - Service Start");
		mBleServiceUtil.startBTbgService(mContext, mServiceConnection);
	}

	public void stopBTbgService() throws BLEServiceExcpetion {
		if (!mBleServiceUtil.isRunningbgService(mContext)) {
			throw new BLEServiceExcpetion("BLEService - already stop BLEService");
		}
		mBleServiceUtil.stopBTbgService(mContext, mServiceConnection);
	}

	public boolean isSupportBTDevice() {
		return (mBluetoothAdapter != null) ? true : false;
	}

	public boolean isEnableBTDevice() {
		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			return false;
		}
		return mBluetoothAdapter.isEnabled();
	}

	public boolean isRunningService() {
		if (mCurrentstate >= BleState.SERVICE_RUNNING) {
			return true;
		} else {
			return false;
		}
	}

	public void enableBTDevice(Context context) throws BLEServiceExcpetion {
		if (!isSupportBTDevice()) {
			throw new NotSupportBLEException();
		} else if (isEnableBTDevice()) {
			throw new BLEServiceExcpetion("Already be enable the BT");
		}

		if (context instanceof Activity) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			((Activity) context).startActivityForResult(intent, REQUEST_CODE_ENABLE_BT);
		} else {
			throw new BLEServiceExcpetion("Should not do enable BT - Not called through Activity Component");
		}
	}

	public void discoverableBTDevice(Context context, int durationtime) throws BLEServiceExcpetion {
		if (!isSupportBTDevice()) {
			throw new NotSupportBLEException();
		} else if (!isEnableBTDevice()) {
			throw new BLEServiceExcpetion("disable bluetooth device - not be enable BT");
		}

		if (context instanceof Activity) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, durationtime);
			((Activity) context).startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT);
		} else {
			throw new BLEServiceExcpetion("Should not do discoverable BT - Not called through Activity Component");
		}

	}
	
	
	public void getPairedBTDevice() throws BLEServiceExcpetion {
		// Update to do
	}

	public void release() {
		mConnServiceListener = null;
		if (mCurrentstate >= BleState.SERVICE_RUNNING) {
			try {
				stopBTbgService();
			} catch (BLEServiceExcpetion e) {
				// TODO Auto-generated catch block
				if ("BLEService - already stop BLEService".equalsIgnoreCase(e.getMessage())) {
					e.printLog(TAG);
				}
			}
		}
		mCurrentstate = BleState.IDLE;
	}

	private void observerConnServiceListener(int state, Bundle resultBundle) {
		if (mConnServiceListener == null) {
			Log.w(TAG, "observerConnServiceListener - unregister ConnectServiceListener");
			return;
		}

		switch (state) {
		case BLEConnServiceListener.STATE_DISCONNECT:
			mConnServiceListener.disconnectBLEService();
			break;

		case BLEConnServiceListener.STATE_CONNECT:
			boolean result = resultBundle.getBoolean("connect_result", false);
			mConnServiceListener.connectBLEService(result);
			break;

		default:
			break;
		}
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.d(TAG, "BLEService - Service disconnect");
			observerConnServiceListener(BLEConnServiceListener.STATE_DISCONNECT, null);
			mCurrentstate = BleState.SERVICE_STOPED;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.d(TAG, "BLEService - Service connect");
			mBleServiceUtil.setServiceInstance(IBLEBackgroundService.Stub.asInterface(service));
			boolean result = BLEService.this.initService();

			Bundle resultBundle = new Bundle();
			resultBundle.putBoolean("connect_result", result);
			observerConnServiceListener(BLEConnServiceListener.STATE_CONNECT, resultBundle);
			mCurrentstate = BleState.SERVICE_RUNNING;
		}
	};

	// Common BT API End
	// /////////////////////

	// ///////////////////
	// BLE API Start
	public void registerUUIDArray(String[] uuid) {
		// TODO Auto-generated method stub
		try {
			registerUUID(uuid);
		} catch (BLEServiceExcpetion e) {
			// TODO Auto-generated catch block
			e.printLog(TAG);
		}
	}

	public void registerUUID(String uuid) {
		try {
			registerUUID(new String[] { uuid, });
		} catch (BLEServiceExcpetion e) {
			// TODO Auto-generated catch block
			e.printLog(TAG);
		}
	}

	public void registerUUIDArray(UUID[] uuid) {
		try {
			registerUUID(mBleServiceUtil.convertUUIDtoString(uuid));
		} catch (BLEServiceExcpetion e) {
			// TODO Auto-generated catch block
			e.printLog(TAG);
		}
	}

	public void registerUUID(UUID uuid) {
		try {
			registerUUID(mBleServiceUtil.convertUUIDtoString(new UUID[] { uuid, }));
		} catch (BLEServiceExcpetion e) {
			// TODO Auto-generated catch block
			e.printLog(TAG);
		}
	}

	private void registerUUID(String[] uuidString) throws BLEServiceExcpetion {
		// TODO Auto-generated method stub
		if (uuidString == null || uuidString.length <= 0) {
			throw new BLEServiceExcpetion("registered UUID is zero or null");
		} else {
			if (!mBleServiceUtil.isRunningbgService(mContext)) {
				mPrepareUUIDString = uuidString;
				Log.i(TAG, "prepare registered UUID - Because Background Service isn't yet running");
				return;
			}
			
			if(mCurrentstate < BleState.SERVICE_RUNNING) {
				Log.i(TAG, "Not connect BLEBackgroundService.");
				return;
			}
			
			for (String uuidstr : uuidString) {
				if (!mRegUUIDList.contains(uuidstr))
					mRegUUIDList.add(uuidstr);
			}

			boolean result = mBleServiceUtil.registerUUIDFromApp(mContext, uuidString);
			if (!result) {
				Log.w(TAG, "Process that registerUUID is failed");
			}
		}

	}

	public void unregisterUUID(String[] uuidString) {
		// TODO Auto-generated method stub
		if (mRegUUIDList != null && mRegUUIDList.size() > 0) {
			if(!mBleServiceUtil.unregisterUUIDFromApp(mContext, uuidString)){
				Log.w(TAG, "Process that unregisterUUID is failed : uuidString array conunt = " + uuidString.length);
			} else {
				for (int i = 0; i < uuidString.length; i++) {
					if(mRegUUIDList.contains(uuidString[i])) { mRegUUIDList.remove(uuidString[i]); }
				}
			}
		}
	}

	public void unregisterUUIDAll() {
		// TODO Auto-generated method stub
		if (mRegUUIDList != null && mRegUUIDList.size() > 0) {
			boolean result = mBleServiceUtil.unregisterUUIDFromAppAll(mContext);
			if (result) {
				mRegUUIDList.clear();
			} else {
				Log.w(TAG, "Process that unregisterUUIDAll is failed");
			}
		}
	}
	
	public void scanLeDevice(BLEConnDevicesListener listener, int period) throws BLEServiceExcpetion {
		// TODO Auto-generated method stub

		boolean result = false;
		if (BLE_SUPPORT_SDK && mCurrentstate >= BleState.SERVICE_RUNNING) {
			result = mRegUUIDList.size() > 0 ? mBleServiceUtil.scanLeDevice(mContext, period, SCAN_BY_REGUUID) : mBleServiceUtil
					.scanLeDevice(mContext, period, SCAN_ALL_BLE);
		}

		if (!result) {
			if (!BLE_SUPPORT_SDK) {
				throw new NotSupportBLEException();
			} else {
				throw new BLEServiceExcpetion("scanLeDevice fail - scanLeCurrentState : " + mCurrentstate);
			}
		}
		
		setBLEDeviceListener(listener);
		//registerDeviceBroadReceiver();
		mCurrentstate = BleState.START_BLE_SCANNING;
	}

	public void stopLeDevice() throws NotSupportBLEException {
		// TODO Auto-generated method stub
		if (BLE_SUPPORT_SDK && mCurrentstate >= BleState.SERVICE_RUNNING)
			mBleServiceUtil.stopLeDevice(mContext);
		else
			throw new NotSupportBLEException();

		unregisterDeviceBroadReceiver();
		mCurrentstate = BleState.START_BLE_SCAN_STOP;
	}	
	
	public void setBLEDeviceListener(BLEConnDevicesListener conn){
		mBleDeviceUtil.registerBLEConnectDeviceListener(conn);
	}
	
	
	private void registerDeviceBroadcastReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_FOUND_BLEDEVICE);
		
		mContext.registerReceiver(mBleBR, filter, PERMISSION_BROADCAST_BLEDEVICE, null);
	}
	
	private void unregisterDeviceBroadReceiver() {
		mContext.unregisterReceiver(mBleBR);
	}

}
