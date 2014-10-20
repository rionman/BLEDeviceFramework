package com.android.framework.ble.util;

import java.nio.ByteBuffer;

public class ByteUtil {
	
	public static int getIntFromByte(final byte bite){
		return Integer.valueOf(bite & 0xFF);
	}
	
	public static int getIntFromByteArray(final byte[] byteArr) {
	     return ByteBuffer.wrap(byteArr).getInt();
	}
	
	
	public static int getIntFrom2ByteArray(final byte[] input){
		final byte[] result = new byte[4];

		result[0] = 0;
		result[1] = 0;
		result[2] = input[0];
		result[3] = input[1];

		return getIntFromByteArray(result);
	}
	
	public static byte[] invertedByteArray(byte[] byteArr){
		final int size = byteArr.length;
		byte temp;
		
		for (int i = 0; i < size/2; i++){
			temp = byteArr[size-1-i];
			byteArr[size-1-i] = byteArr[i];
			byteArr[i] = temp;		
		}
		
		return byteArr;
	}
	
}
