package com.android.framework.ble.listener;

import com.android.framework.ble.util.BLEFeature;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BLEScanCallback implements BluetoothAdapter.LeScanCallback, BLEFeature{
	private String packageName = null;
	private Context mContext = null;
	
	public BLEScanCallback(Context context, String packageName) {
		// TODO Auto-generated constructor stub
		this.packageName = packageName;
		this.mContext = context;
	}
	
	@Override
	public void onLeScan(BluetoothDevice bleDevice, int rssi, byte[] scanRecord) {
		// TODO Auto-generated method stub
		sendBroadCastBLEDevice(bleDevice, rssi, scanRecord);
	}

	private void sendBroadCastBLEDevice(BluetoothDevice bleDevice, int rssi, byte[] scanRecord) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(ACTION_FOUND_BLEDEVICE);
		
		intent.setComponent(new ComponentName(packageName, BLEDEVICE_BR_CLS));
		
		intent.putExtra(EXTRA_BLEDEVICE, bleDevice);
		intent.putExtra(EXTRA_RSSI, rssi);
		intent.putExtra(EXTRA_SCANRECORD, scanRecord);
		
		mContext.sendBroadcast(intent);
	}

}
