package com.android.framework.ble.device;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class AdRecord implements Parcelable {

	private static final String EXTRA_RECORD_DATA = "adrecord_data";
	private static final String EXTRA_RECORD_TYPE = "adrecord_type";
	private static final String EXTRA_RECORD_LENGTH = "adrecord_length";

	public static final int TYPE_MANUFACTURER_SPECIFIC_DATA = 0xFF;

	private final int mLength;
	private final int mType;
	private final byte[] mData;

	public AdRecord(int length, int type, byte[] data) {
		// TODO Auto-generated constructor stub
		mLength = length;
		mType = type;
		mData = data;
	}

	public AdRecord(Parcel in) {
		// TODO Auto-generated constructor stub
		final Bundle b = in.readBundle(getClass().getClassLoader());

		mLength = b.getInt(EXTRA_RECORD_LENGTH);
		mType = b.getInt(EXTRA_RECORD_TYPE);
		mData = b.getByteArray(EXTRA_RECORD_DATA);
	}

	public int getType() {
		// TODO Auto-generated method stub
		return mType;
	}

	public byte[] getData() {
		// TODO Auto-generated method stub
		return mData;
	}

	public int getLengh() {
		// TODO Auto-generated method stub
		return mLength;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		final Bundle b = new Bundle(getClass().getClassLoader());

		b.putInt(EXTRA_RECORD_TYPE, mType);
		b.putInt(EXTRA_RECORD_LENGTH, mLength);
		b.putByteArray(EXTRA_RECORD_DATA, mData);

		dest.writeBundle(b);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<AdRecord> CREATOR = new Parcelable.Creator<AdRecord>() {
		public AdRecord createFromParcel(Parcel in) {
			return new AdRecord(in);
		}

		public AdRecord[] newArray(int size) {
			return new AdRecord[size];
		}
	};

}
