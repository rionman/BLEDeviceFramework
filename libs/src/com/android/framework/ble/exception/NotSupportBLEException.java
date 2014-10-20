package com.android.framework.ble.exception;

public class NotSupportBLEException extends BLEServiceExcpetion{
	
	private static final String DEFAULT_TAG = "NotSupportBLEException";
	private static final String DEFAULT_ERROR_MSG = DEFAULT_TAG + "Your Device is not supported at BLE";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSupportBLEException(){
		super(DEFAULT_ERROR_MSG);
	}
	
	public NotSupportBLEException(String msg){
		super(DEFAULT_TAG, msg);
	}
	
}
