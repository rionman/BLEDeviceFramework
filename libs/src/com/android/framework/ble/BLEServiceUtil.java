package com.android.framework.ble;

import java.util.UUID;

import com.android.framework.ble.service.BLEBackgroundService;
import com.android.framework.ble.service.IBLEBackgroundService;
import com.android.framework.ble.util.BLEFeature;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;


class BLEServiceUtil {	
	
	private IBLEBackgroundService mService = null;
	
	public void setServiceInstance(IBLEBackgroundService service){
		mService = service;
	}
	
	public void startBTbgService(Context context, ServiceConnection conn) { 

		Intent serviceIntent = new Intent();
		serviceIntent.setAction(BLEFeature.ACTION_START_SERVICE);
		serviceIntent.setComponent(new ComponentName(BLEFeature.SERVICE_PACKAGE_NAME, BLEFeature.SERVICE_CLASS_NAME));		
		//serviceIntent.setPackage(context.getPackageName());
		serviceIntent.putExtra(BLEBackgroundService.EXTRA_BIND_PACKAGENAME, context.getPackageName());
		onBindService(context, conn, serviceIntent);
	}
	
	private void onBindService(Context context, ServiceConnection conn, Intent intent){
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	private void onUnBindService(Context context, ServiceConnection conn){
		context.unbindService(conn);
	}
	
	public boolean isRunningbgService(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	if (BLEFeature.SERVICE_CLASS_NAME.equalsIgnoreCase(service.service.getClassName())) {
	        	return true;
	        }
	    }
	    return false;
	}

	public void stopBTbgService(Context context, ServiceConnection conn) {
		// TODO Auto-generated method stub
		onUnBindService(context, conn);
	}

	public boolean scanLeDevice(Context context, int period, int scanStream) {
		// TODO Auto-generated method stub
		try {
			return mService.scanLEDevice(context.getPackageName(), period, scanStream);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}
	
	
	public void stopLeDevice(Context context) {
		try {
			mService.stopLEDevice(context.getPackageName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean registerUUIDFromApp(Context context, String[] uuidString){
		boolean result = false;
		try {
			result = mService.registerUUIDFromApp(uuidString, context.getPackageName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean unregisterUUIDFromApp(Context context, String[] uuidString){
		boolean result = false;
		try {
			result = mService.unregisterUUIDFromApp(context.getPackageName(), uuidString);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public boolean unregisterUUIDFromAppAll(Context context){
		boolean result = false;
		try {
			result = mService.unregisterUUIDFromAppAll(context.getPackageName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UUID[] convertStringtoUUID(String[] uuidString) {
		int i = 0;
		if(uuidString.length == 0) return null;
		
		UUID[] uuid = new UUID[uuidString.length];
		
		for (String uuidstr : uuidString) {
			uuid[i] = UUID.fromString(uuidstr);
			if(i < uuidString.length) i++;
		}
		
		return uuid;
	}
	
	public String[] convertUUIDtoString(UUID[] uuid) {
		int i = 0;
		if(uuid.length == 0) return null;
		
		String[] uuidstring = new String[uuid.length];
		
		for (UUID uuidstr : uuid) {
			uuidstring[i] = uuidstr.toString();
			if(i < uuid.length) i++;
		}
		
		return uuidstring;
	}

}
