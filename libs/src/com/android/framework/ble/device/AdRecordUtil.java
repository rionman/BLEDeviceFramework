package com.android.framework.ble.device;

import java.util.Arrays;

import com.android.framework.ble.util.ByteUtil;

import android.util.SparseArray;

class AdRecordUtil {
	
	public static final int TYPE_SERVICE_DATA = 0x16;
	
	
	public static String getRecordDataAsString(final AdRecord nameRecord) {
		if(nameRecord == null){return new String();}
		return new String(nameRecord.getData());
	}
	
	public static byte[] getServiceData(final AdRecord serviceData) {
		if (serviceData == null) {return null;}
		if (serviceData.getType() != TYPE_SERVICE_DATA) return null;

		final byte[] raw = serviceData.getData();
		//Chop out the uuid
		return Arrays.copyOfRange(raw, 2, raw.length);
	}

	public static int getServiceDataUuid(final AdRecord serviceData) {
		if (serviceData == null) {return -1;}
		if (serviceData.getType() != TYPE_SERVICE_DATA) return -1;

		final byte[] raw = serviceData.getData();
		//Find UUID data in byte array
		int uuid = (raw[1] & 0xFF) << 8;
		uuid += (raw[0] & 0xFF);

		return uuid;
	}
	
	public static SparseArray<AdRecord> parseScanRecordAsSparseArray(byte[] scanRecord) {
		final SparseArray<AdRecord> records = new SparseArray<AdRecord>();

		int index = 0;
		while (index < scanRecord.length) {
			final int length = scanRecord[index++];
			//Done once we run out of records
			if (length == 0) break;

			final int type = Integer.valueOf(scanRecord[index] & 0xFF);

			//Done if our record isn't a valid type
			if (type == 0) break;

			final byte[] data = Arrays.copyOfRange(scanRecord, index+1, index+length);

			records.put(type, new AdRecord(length, type, data));

			//Advance
			index += length;
		}

		return records;
	}
	
	/**
	 * Calculates the accuracy of an RSSI reading.
	 *
	 * The code was taken from {@link http://stackoverflow.com/questions/20416218/understanding-ibeacon-distancing}
	 *
	 * @param txPower the calibrated TX power of an iBeacon
	 * @param rssi the RSSI value of the iBeacon
	 * @return
	 */
	public static double calculateAccuracy(int txPower, double rssi) {
		if (rssi == 0) {
			return -1.0; // if we cannot determine accuracy, return -1.
		}

		double ratio = rssi*1.0/txPower;
		if (ratio < 1.0) {
			return Math.pow(ratio,10);
		}
		else {
			final double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
			return accuracy;
		}
	}
	
	public static String calculateUUIDString(final byte[] uuid){
		final StringBuffer sb = new StringBuffer();

		for(int i = 0 ; i< uuid.length; i++){
			if(i == 4){sb.append('-');}
			if(i == 6){sb.append('-');}
			if(i == 8){sb.append('-');}
			if(i == 10){sb.append('-');}

			sb.append(Integer.toHexString(ByteUtil.getIntFromByte(uuid[i])));
		}

		return sb.toString();
	}
}
