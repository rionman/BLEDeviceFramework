package com.android.framework.ble.exception;

import android.util.Log;

public class BLEServiceExcpetion extends Exception{
	
	private static final String DEFAULT_TAG = "BLEServiceExcpetion";
	private static final String DEFAULT_ERR_TOKEN = " - ";
	private static final String DEFAULT_ERROR_MSG = DEFAULT_TAG+ DEFAULT_ERR_TOKEN + "In BleService, Exception occur";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String defaultTAG;
	
	public BLEServiceExcpetion() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_ERROR_MSG);
	}
	
	public BLEServiceExcpetion(String exceptionMsg) {
		// TODO Auto-generated constructor stub
		super(exceptionMsg);
		defaultTAG = DEFAULT_TAG;
	}
	
	public BLEServiceExcpetion(String tag, String exceptionMsg) {
		// TODO Auto-generated constructor stub
		super(exceptionMsg);
		defaultTAG = tag;
	}
		
	@Override
	public String getMessage(){
		StringBuffer msgbuffer = new StringBuffer();
		
		String msg = super.getMessage();
		if (!DEFAULT_ERROR_MSG.equalsIgnoreCase(msg)) {
			msgbuffer.append(defaultTAG + DEFAULT_ERR_TOKEN);
		}
		
		msgbuffer.append(msg);
		return msgbuffer.toString();
	}
	
	public void printLog(String tag){
		Log.w(tag, getMessage());
	}
}
