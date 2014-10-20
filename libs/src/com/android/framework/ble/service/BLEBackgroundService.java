package com.android.framework.ble.service;

import java.util.UUID;

import com.android.framework.ble.exception.NotSupportBLEException;
import com.android.framework.ble.listener.BLEScanCallback;
import com.android.framework.ble.util.BLEFeature;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class BLEBackgroundService extends Service {
	private final static String TAG = BLEBackgroundService.class.getSimpleName();
	
	public final static String EXTRA_BIND_PACKAGENAME = "bind_pkg_name";
	
	private BluetoothAdapter mBtDeviceAdapter = null;
	private BLESeviceManager mBleServiceManager = BLESeviceManager.instance();
	private BLEBackgroundServiceImpl mServiceImpl = null;

	@Override
	public void onCreate() {
		try {
			Log.d(TAG, "onCreate Service");
			initBtDevice();
		} catch (NotSupportBLEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			finishService();
		}
		// BroadCastReceiver - Discovery Device
		registerBroadcastReceive();

	}

	@Override
	public int onStartCommand(Intent intent, int frags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		if (mServiceImpl == null) {
			mServiceImpl = new BLEBackgroundServiceImpl();
		}
		//String packageName = intent.getComponent().getPackageName();
		String packageName = intent.getStringExtra(EXTRA_BIND_PACKAGENAME);
		Log.i(TAG, "onBind is called from package Name( " + packageName + " )");
		
		if(packageName == null || "".equals(packageName)) {
			Log.i(TAG, "Can not connect Service because package which asked binding is null.");
			return null;
		}
		
		mBleServiceManager.registerConnectApp(packageName);

		return mServiceImpl;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		//String packageName = intent.getComponent().getPackageName();
		String packageName = intent.getStringExtra(EXTRA_BIND_PACKAGENAME);
		Log.i(TAG, "onUnbind is called from package Name( " + packageName + " )");
		releaseAllConnectApp(packageName);
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// BroadCastReceiver - Discovery Device : unregister
		unregisterBroadcastReceive();
	}

	private void initBtDevice() throws NotSupportBLEException {
		Log.d(TAG, "initBtDevice() start.");
		if (BLEFeature.BLE_SUPPORT_SDK) {
			final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			mBtDeviceAdapter = bluetoothManager.getAdapter();
		} else {
			mBtDeviceAdapter = BluetoothAdapter.getDefaultAdapter();
		}

		if (mBtDeviceAdapter == null) {
			throw new NotSupportBLEException();
		}

		Log.d(TAG, "initBtDevice() end.");
	}

	private void releaseAllConnectApp(String packageName) {
		// TODO Auto-generated method stub
		// release register-date
		stopLEDevice(packageName);
		mBleServiceManager.unregisterLECallbackFromApp(packageName);
		mBleServiceManager.unregisterUUIDFromAppAll(packageName);
		mBleServiceManager.unregisterConnectApp(packageName);
	}

	private void registerBroadcastReceive() {
		// TODO Auto-generated method stub
		IntentFilter bleFileFilter = new IntentFilter();
		bleFileFilter.addAction(BluetoothDevice.ACTION_FOUND);

		getApplicationContext().registerReceiver(mBleReceiver, bleFileFilter);
	}

	private void unregisterBroadcastReceive() {
		getApplicationContext().unregisterReceiver(mBleReceiver);
	}

	private void finishService() {
		Log.d(TAG, "Finish Service by other reason.");
		stopSelf();
	}

	private final BroadcastReceiver mBleReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();

			// Action - Discovery Device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {

			} else {

			}
		}
	};

	// //////////////////////////
	// Stub Method should be generated

	public boolean registerUUIDFromApp(String[] uuId, String packageName) {
		// TODO Auto-generated method stub
		Log.i(TAG, "registerUUIDFromApp - packageName = " + packageName + " uuid = " + uuId);
		return mBleServiceManager.registerUUIDFromApp(packageName, uuId);
	}

	public boolean unregisterUUIDFromApp(String packageName, String[] uuidString) {
		// TODO Auto-generated method stub
		Log.i(TAG, "unregisterUUIDFromApp - packageName = " + packageName + " uuid = " + uuidString);
		return mBleServiceManager.unregisterUUIDFromApp(packageName, uuidString);
	}

	public boolean scanLEDevice(String packageName, int period, int scanStream) {
		// TODO Auto-generated method stub

		UUID[] regUUID = mBleServiceManager.getUUIDFromApp(packageName);
		BLEScanCallback bleScanCallback = new BLEScanCallback(getApplicationContext(), packageName);
		if(scanStream == BLEFeature.SCAN_ALL_BLE) {
			if(!mBtDeviceAdapter.startLeScan(bleScanCallback)){
				Log.w(TAG, "scanLEDevice is failed - return false");
				return false;
			}
		} else if (scanStream == BLEFeature.SCAN_BY_REGUUID) {
			if (regUUID != null && regUUID.length > 0) {
				if(!mBtDeviceAdapter.startLeScan(regUUID, bleScanCallback)) {
					Log.w(TAG, "scanLEDevice is failed - return false");
					return false;
				}
			} else {
				Log.w(TAG, "scanLEDevice is failed - regUUID is null or regUUID lengh is 0.");
				return false;
			}
		}
		
		mBleServiceManager.registerLECallbackFromApp(packageName, bleScanCallback);
		
		Log.i(TAG, "scanLEDevice - packageName = " + packageName +" : device ScanStart.");
		return true;
	}

	public void stopLEDevice(String packageName) {
		LeScanCallback callback = mBleServiceManager.getLeCallbackFromApp(packageName);

		if (callback != null) {
			Log.i(TAG, "stopLEDevice - package Name( " + packageName + " ) is stopped by LEdevice");
			mBtDeviceAdapter.stopLeScan(callback);
		}
	}

	public boolean unregisterUUIDFromAppAll(String packageName) {
		// TODO Auto-generated method stub
		return mBleServiceManager.unregisterUUIDFromAppAll(packageName);
	}

	// /////////////////////

	private class BLEBackgroundServiceImpl extends IBLEBackgroundService.Stub {

		@Override
		public boolean registerUUIDFromApp(String[] uuId, String packageName) throws RemoteException {
			// TODO Auto-generated method stub
			return BLEBackgroundService.this.registerUUIDFromApp(uuId, packageName);
		}

		@Override
		public boolean scanLEDevice(String packageName, int period,  int scanStream) throws RemoteException {
			// TODO Auto-generated method stub
			return BLEBackgroundService.this.scanLEDevice(packageName, period, scanStream);
		}

		@Override
		public boolean unregisterUUIDFromApp(String packageName, String[] uuidString) throws RemoteException {
			// TODO Auto-generated method stub
			return BLEBackgroundService.this.unregisterUUIDFromApp(packageName, uuidString);
		}

		@Override
		public boolean unregisterUUIDFromAppAll(String packageName) throws RemoteException {
			// TODO Auto-generated method stub
			return BLEBackgroundService.this.unregisterUUIDFromAppAll(packageName);
		}

		@Override
		public void stopLEDevice(String packageName) throws RemoteException {
			// TODO Auto-generated method stub
			BLEBackgroundService.this.stopLEDevice(packageName);
		}

	}

}
