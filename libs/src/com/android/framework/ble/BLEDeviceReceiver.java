package com.android.framework.ble;

import com.android.framework.ble.device.BluetoothLEDevice;
import com.android.framework.ble.listener.BLEConnDevicesListener;
import com.android.framework.ble.util.BLEFeature;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BLEDeviceReceiver extends BroadcastReceiver implements BLEFeature{
	private static final String TAG = BLEDeviceReceiver.class.getSimpleName();
	
	private BLEDeviceUtil mBLEDeviceUtil = BLEDeviceUtil.instance();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.i(TAG, "onReceive - action = " + action);
		
		if (ACTION_FOUND_BLEDEVICE.equals(action)) {
			BLEConnDevicesListener conn = mBLEDeviceUtil.getConnDeviceListener();			
			if(conn == null) {
				Log.w(TAG, "Unregistered mBLEDeviceUtil - can't receive discovered device's info.");
				return;
			}
			
			BluetoothDevice device = intent.getParcelableExtra(EXTRA_BLEDEVICE);
			int rssi = intent.getIntExtra(EXTRA_RSSI, 0);
			byte[] scanRecord = intent.getByteArrayExtra(EXTRA_SCANRECORD);

			Log.i(TAG, "onReceive - device Name = " + device.getName() + " rssi = " + rssi);
			
			BluetoothLEDevice bledevice = null;
			if(mBLEDeviceUtil.isRegisterBLEDevice(device.getAddress())) {
				
				bledevice = mBLEDeviceUtil.getBLEDevice(device.getAddress());
				bledevice.addToRssi(rssi);
				bledevice.setCurrentScanRecord(scanRecord);
				
			} else {
				bledevice = new BluetoothLEDevice(device, rssi, scanRecord);
			}
			mBLEDeviceUtil.addBLEDevice(bledevice);
			conn.bleDeviceDiscover(bledevice);
			
		} else {
			// update to do.
		}
	}

}