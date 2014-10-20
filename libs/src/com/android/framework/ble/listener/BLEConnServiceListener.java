package com.android.framework.ble.listener;

public interface BLEConnServiceListener {
	
	int STATE_CONNECT = 1;
	int STATE_DISCONNECT = 0;
	int STATE_ALREADY_CONNECT = 2;
	
	void connectBLEService(boolean result);
	void disconnectBLEService();
}
