package com.android.framework.ble.util;

import android.os.Build;

public interface BLEFeature {
	
	
	//BLE Scan Stream
	public static final int SCAN_ALL_BLE = 0;
	public static final int SCAN_BY_REGUUID = 1;
	
	//Intent Code
	public static final int REQUEST_CODE_ENABLE_BT = 0;
	public static final int REQUEST_CODE_DISCOVERABLE_BT = 1;
	
	public static final boolean BLE_SUPPORT_SDK = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? true : false;
	
	//BLE Action
	public final static String ACTION_FOUND_BLEDEVICE = "com.andrid.ble.ACTION_FOUND_BLEDEVICE";
	
	//BLE BR
	public final static String PERMISSION_BROADCAST_BLEDEVICE = "com.android.ble.permission.broadcast";
	public final static String BLEDEVICE_BR_CLS = "com.android.framework.ble.BLEDeviceReceiver";
	//BLE Receive Extra
	public final static String EXTRA_BLEDEVICE = "extra_ble_Device";
	public final static String EXTRA_RSSI = "extra_rssi";
	public final static String EXTRA_SCANRECORD = "extra_scan_record";
	
	//BLE Service
	public final static String SERVICE_PACKAGE_NAME = "com.android.framework.ble";
	public final static String SERVICE_CLASS_NAME = SERVICE_PACKAGE_NAME+".service.BLEBackgroundService";
	public final static String ACTION_START_SERVICE = "android.intent.action.BLESERVICE";
	
}
