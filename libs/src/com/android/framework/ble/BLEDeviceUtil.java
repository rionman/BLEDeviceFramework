package com.android.framework.ble;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.util.Log;
import android.util.SparseArray;

import com.android.framework.ble.device.BluetoothLEDevice;
import com.android.framework.ble.listener.BLEConnDevicesListener;

class BLEDeviceUtil {
	
	private static final String TAG = BLEDeviceUtil.class.getSimpleName();
	private static final int MAX_BLE_DEVICE_LIST = 20;

	private static BLEDeviceUtil mBleDeviceUtil = null;
	
	//BLE Device Listener
	private BLEConnDevicesListener mBLEConnectDeviceListener = null;
	
	private ArrayList<String> mBLEDeviceAddressList = new ArrayList<String>(MAX_BLE_DEVICE_LIST);
	
	private final ExecutorService schExService = Executors.newSingleThreadExecutor();

	public static BLEDeviceUtil instance() {
		if (mBleDeviceUtil == null) {
			mBleDeviceUtil = new BLEDeviceUtil();
		}
		return mBleDeviceUtil;
	}

	public void registerBLEConnectDeviceListener(BLEConnDevicesListener conn) {
		unregisterBLEConnectDeviceListener();
		mBLEConnectDeviceListener = conn;
	}

	public void unregisterBLEConnectDeviceListener() {
		if (mBLEConnectDeviceListener != null) {
			mBLEConnectDeviceListener = null;
		}
	}

	public BLEConnDevicesListener getConnDeviceListener() {
		return mBLEConnectDeviceListener;
	}
	
	public boolean isRegisterBLEDevice(String address){
		return mBLEDeviceAddressList.contains(address);
	}
	
	public synchronized void addBLEDevice(BluetoothLEDevice device) {
		String address = device.getAddress();
		if (address == null || "".equals(address))
			return;
		
		if(mBLEDeviceAddressList.contains(address)) {
			int key = mBLEDeviceAddressList.indexOf(address);
			BLEDeviceControl.replaceBLEDeviceList(key, device);
		} else {
			mBLEDeviceAddressList.add(address);
			BLEDeviceControl.addBLEDeviceToList(mBLEDeviceAddressList.indexOf(address), device);
		}
		
		if (BLEDeviceControl.getListSize() >= MAX_BLE_DEVICE_LIST) {
			startScanBleDeviceControlExcutor();
		}
	}
	
	public BluetoothLEDevice getBLEDevice(String address){
		BluetoothLEDevice device = null;
		if(mBLEDeviceAddressList.contains(address)){
			device = BLEDeviceControl.getBLEDevice(mBLEDeviceAddressList.indexOf(address));
		}
		
		return device;
	}
	
	public void removeBLEDevice(BluetoothLEDevice device) {
		removeBLEDevice(device.getAddress());
	}
	
	public synchronized void removeBLEDevice(String address) {
		if(mBLEDeviceAddressList.contains(address)){
			BLEDeviceControl.removeBLEDeviceToList(mBLEDeviceAddressList.indexOf(address));
			mBLEDeviceAddressList.remove(mBLEDeviceAddressList.indexOf(address));
			
			if(BLEDeviceControl.getListSize() >= MAX_BLE_DEVICE_LIST)
				startScanBleDeviceControlExcutor();			
		} else { 
			Log.i(TAG, "");
		}
	}
	
	public void removeBLEDeviceAll(){
		BLEDeviceControl.removeBLEDeviceToListAll();
		mBLEDeviceAddressList.clear();
	}
	

	public void startScanBleDeviceControlExcutor() {

		Future<?> future = schExService.submit(mBLEDeviceControlCommand);

		if (future != null) {
			Log.i(TAG, "startScanBleDeviceControlExcutor is successed.");
		}

		if (!schExService.isShutdown())
			schExService.shutdown();

	}
	
	private Runnable mBLEDeviceControlCommand = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			BLEDeviceControl.removeBLEDeviceToList(0);
		}
	};

	private static class BLEDeviceControl {

		private static SparseArray<BluetoothLEDevice> mScanBLEDeviceList = new SparseArray<BluetoothLEDevice>(MAX_BLE_DEVICE_LIST);
		
		public static void addBLEDeviceToList(int addressKey, BluetoothLEDevice device) {
			synchronized (mScanBLEDeviceList) {
				mScanBLEDeviceList.put(addressKey, device);
			}
		}
		
		public static void replaceBLEDeviceList(int addressKey, BluetoothLEDevice device){
			synchronized (mScanBLEDeviceList) {
				mScanBLEDeviceList.remove(addressKey);
				mScanBLEDeviceList.append(addressKey, device);
			}
		}
		

		public static int getListSize() {
			return mScanBLEDeviceList.size();
		}
		
		public static void removeBLEDeviceToList(int key) {
			synchronized (mScanBLEDeviceList) {
				mScanBLEDeviceList.removeAt(key);
			}
		}
		
		public static BluetoothLEDevice getBLEDevice(int key){
			BluetoothLEDevice device = null;
			synchronized (mScanBLEDeviceList) {
				device = mScanBLEDeviceList.get(key);
			}
			return device;
		}
		
		public static void removeBLEDeviceToListAll() {
			synchronized (mScanBLEDeviceList) {
				mScanBLEDeviceList.clear();
			}
		}
	}

}
