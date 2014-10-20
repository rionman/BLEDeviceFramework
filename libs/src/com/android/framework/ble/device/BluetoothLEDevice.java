package com.android.framework.ble.device;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Parcel;
import android.util.SparseArray;

public class BluetoothLEDevice {
	
	private static final int MAX_RSSI_CAPACITY = 10;
	private static final long RANGE_RSSI_TIME = MAX_RSSI_CAPACITY * 1000;
	
	private static final String EXTRA_BLE_DEVICE = "ble_device";
	private static final String EXTRA_CURRENT_RSSI = "ble_current_rssi";
	private static final String EXTRA_DEVICE_SCANRECORD = "ble_scanrecord";
	private static final String EXTRA_ADRECORDLIST = "ble_adrecord_list";	

	//parcelable Variable
	protected BluetoothDevice mBTLeDevice = null;
	protected int mCurrentRssiDevice = 0;
	protected byte[] mScanRecord = null;
	protected SparseArray<AdRecord> mAdRecordList = null;
	
	//Not parcelable Variable
	private LinkedBlockingQueue<Rssi> mRssiQueue = new LinkedBlockingQueue<Rssi>(MAX_RSSI_CAPACITY); 
	private long mCurrentRssiTime = 0;
	private long mFirstRssiTime = 0;
	
	public BluetoothLEDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
		// TODO Auto-generated constructor stub
		mBTLeDevice = device;
		mCurrentRssiDevice = rssi;
		mScanRecord = scanRecord;
		mAdRecordList = AdRecordUtil.parseScanRecordAsSparseArray(scanRecord);
		updateRssiQueue(rssi);
	}
	
	public BluetoothLEDevice(Parcel in) {
		// TODO Auto-generated constructor stub
		final Bundle bundle = in.readBundle(getClass().getClassLoader());
		
		mBTLeDevice = bundle.getParcelable(EXTRA_BLE_DEVICE);
		mCurrentRssiDevice = bundle.getInt(EXTRA_CURRENT_RSSI);
		mScanRecord = bundle.getByteArray(EXTRA_DEVICE_SCANRECORD);
		mAdRecordList = bundle.getSparseParcelableArray(EXTRA_ADRECORDLIST);
		
		updateRssiQueue(mCurrentRssiDevice);
	}

	
	public void writeToParcel(Bundle bundle, int flags) {
		// TODO Auto-generated method stub
		bundle.putParcelable(EXTRA_BLE_DEVICE, mBTLeDevice);
		bundle.putInt(EXTRA_CURRENT_RSSI, mCurrentRssiDevice);
		bundle.putByteArray(EXTRA_DEVICE_SCANRECORD, mScanRecord);
		bundle.putSparseParcelableArray(EXTRA_ADRECORDLIST, mAdRecordList);
	}
	
	public void setCurrentScanRecord(byte[] scanRecord){
		mScanRecord = scanRecord;
		mAdRecordList = AdRecordUtil.parseScanRecordAsSparseArray(scanRecord);
	}
	
	protected synchronized void updateRssiQueue(int rssi){
		long time = System.currentTimeMillis();
		
		if(mFirstRssiTime > time)
			clearRssiQueue();
		
		if(mRssiQueue.size() == 0){
			mFirstRssiTime = time;
		} else {
			
			if(time - mCurrentRssiTime >= RANGE_RSSI_TIME){
				mRssiQueue.clear();
				mFirstRssiTime = time;
			}  else {
				if (time - mFirstRssiTime >= RANGE_RSSI_TIME){
					Iterator<Rssi> iterator = mRssiQueue.iterator();
					while (iterator.hasNext()) {
						Rssi rssielement = iterator.next();
						mFirstRssiTime = rssielement.timeStamp;
						if(time - rssielement.timeStamp >= RANGE_RSSI_TIME) {
							mRssiQueue.remove();
						} else {
							break;
						}
					}
				}
			}
		}
		
		if(mRssiQueue.size() >= MAX_RSSI_CAPACITY){
			mRssiQueue.remove();
		}
		
		mCurrentRssiDevice = rssi;
		mCurrentRssiTime = time;
		mRssiQueue.add(new Rssi(rssi, time));
	}
	
	public int getCureentRssi(){
		return mCurrentRssiDevice;
	}
	
	public int getAverageRSSI(){
		int averageRssi = 0;
		int totalRssi = 0;
		
		if(mRssiQueue.size() <= 0)
			return averageRssi;
			
		synchronized (mRssiQueue) {
			Iterator<Rssi> iterator = mRssiQueue.iterator();
			while (iterator.hasNext()) {
				Rssi rssielement = iterator.next();
				totalRssi += rssielement.rssi;
			}
		}
		
		return (int) totalRssi / mRssiQueue.size();
	}
	
	public String getAddress(){
		return mBTLeDevice.getAddress();
	}
	
	public SparseArray<AdRecord> getScanRecord(){
		return mAdRecordList;
	}
	
	public String calculateUUIDString(byte[] uuidByte){
		return AdRecordUtil.calculateUUIDString(uuidByte);
	}
	
	public void addToRssi(int rssi) {
		updateRssiQueue(rssi);
	}
	
	public double getDistance (int txPower, double rssi) {
		return AdRecordUtil.calculateAccuracy(txPower, rssi);
	}
	
	private void clearRssiQueue() {
		if (mRssiQueue != null) {
			mRssiQueue.clear();
		}
		
		mFirstRssiTime = 0;
		mCurrentRssiTime = 0;
	}
	
	private class Rssi implements Serializable{
		/** Class Name : Rssi
		 *  Variable : int rssi , long timeStamp
		 */
		private static final long serialVersionUID = 1L;
		public int rssi;
		public long timeStamp;
		
		public Rssi(int rssi, long timeStamp) {
			// TODO Auto-generated constructor stub
			this.rssi = rssi;
			this.timeStamp = timeStamp;
		}
	}
	

}
