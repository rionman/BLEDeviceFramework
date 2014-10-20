package com.android.framework.ble.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.util.Log;

class BLESeviceManager {
	private final static String TAG = BLESeviceManager.class.getSimpleName();
	
	private static BLESeviceManager mBleManagerInstance = null; 
	
	private HashMap<String, ArrayList<UUID>> mRegisterUUIDFromApp = new HashMap<String, ArrayList<UUID>>();  	
	private HashMap<String, LeScanCallback> mRegisterLECallbackFromApp = new HashMap<String, LeScanCallback>();  
	private List<String> mContactApplicationList = new ArrayList<String>(); 
	
	public static BLESeviceManager instance(){
		if(mBleManagerInstance == null){
			mBleManagerInstance = new BLESeviceManager();
		}
		
		return mBleManagerInstance;
	}
	
	public boolean registerConnectApp(String PackageName) {
		// TODO Auto-generated method stub
		boolean result = false;		
		synchronized (mContactApplicationList) {
			result = mContactApplicationList.contains(PackageName) ? false : mContactApplicationList.add(PackageName);
		}
		return result;
	}
	
	public boolean unregisterConnectApp(String PackageName) {
		// TODO Auto-generated method stub
		boolean result = false;		
		synchronized (mContactApplicationList) {
			result = !mContactApplicationList.contains(PackageName) ? false : mContactApplicationList.remove(PackageName);
		}
		return result;
	}

	public boolean registerUUIDFromApp(String packageName, String[] uuIdStringArray) {
		// TODO Auto-generated method stub
		boolean result = false;	
		synchronized (mRegisterUUIDFromApp) {
			if(!mRegisterUUIDFromApp.containsKey(packageName)) {
				ArrayList<UUID> uuidArray = new ArrayList<UUID>();
				int i = 0;
				for (String uuidstring : uuIdStringArray) {
					UUID uuid = UUID.fromString(uuidstring);
					uuidArray.add(uuid);
					if(i < uuIdStringArray.length) i++;
				}
				
				result = mRegisterUUIDFromApp.put(packageName, uuidArray) != null ? true : false; 
			}
		}
		return result;
	}
	
	public boolean unregisterUUIDFromApp(String packageName, String[] uuidString) {
		// TODO Auto-generated method stub
		boolean result = false;
		synchronized (mRegisterUUIDFromApp) {
			if(mRegisterUUIDFromApp.containsKey(packageName)) {
				ArrayList<UUID> uuid = mRegisterUUIDFromApp.get(packageName);
				
				for (int i = 0; i < uuidString.length; i++) {
					if(uuid.contains(uuidString[i]))
						uuid.remove(uuidString[i]);
				}
				result = true;
			}
		}
		
		return result;
	}
	
	
	public UUID[] getUUIDFromApp(String packageName) {
		ArrayList<UUID> uuid = null;
		if(mRegisterUUIDFromApp == null){
			return null;
		}
		
		synchronized (mRegisterUUIDFromApp) {
			if(mRegisterUUIDFromApp.containsKey(packageName)){
				uuid = mRegisterUUIDFromApp.get(packageName);
			} else {
				Log.w(TAG, "HashMap doesn't containt RegisterUUID.");
				return null;
			}
		}
		UUID[] uuidArr = new UUID[uuid.size()];
		return uuid.toArray(uuidArr);
	}
	
	public boolean registerLECallbackFromApp(String packageName, LeScanCallback callback) {
		boolean result = false;
		synchronized (mRegisterLECallbackFromApp) {
			result = mRegisterLECallbackFromApp.containsKey(packageName) ? false : 
				mRegisterLECallbackFromApp.put(packageName, callback) != null ? true : false;
		}
		
		return result;
	}
	
	public LeScanCallback getLeCallbackFromApp(String packageName){
		LeScanCallback callback = null;
		synchronized (mRegisterLECallbackFromApp) {
			if(mRegisterLECallbackFromApp.containsKey(packageName)){
				callback = mRegisterLECallbackFromApp.get(packageName);
			}
		}
		return callback;
	}
	
	public boolean unregisterLECallbackFromApp(String packageName) {
		boolean result = false;
		synchronized (mRegisterLECallbackFromApp) {
			if(mRegisterLECallbackFromApp.containsKey(packageName)){
				result = mRegisterLECallbackFromApp.remove(packageName) != null ? true : false;
			} else {
				Log.i(TAG, "Not have LECallback of package : "+packageName);
			}
		}
		
		return result;
	}

	public boolean unregisterUUIDFromAppAll(String packageName) {
		// TODO Auto-generated method stub
		boolean result = false;
		synchronized (mRegisterLECallbackFromApp) {
			if(mRegisterUUIDFromApp.containsKey(packageName)) {
				result = (mRegisterUUIDFromApp.remove(packageName) != null) ? true : false;
			} else {
				Log.i(TAG, "Not have UUID of package : "+packageName);
			}
		}
		return result;
	}
	
	
}
