package com.android.ble.sample;


import java.util.UUID;

import com.android.framework.ble.BLEService;
import com.android.framework.ble.R;
import com.android.framework.ble.device.BluetoothLEDevice;
import com.android.framework.ble.exception.BLEServiceExcpetion;
import com.android.framework.ble.listener.BLEConnDevicesListener;
import com.android.framework.ble.listener.BLEConnServiceListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private final String TAG = "Test";
	private BLEService mBleService = null;
	
	private Button mStartButton = null;
	private Button mStopButton = null;
	
	private Button mEnableButton = null;
	private Button mStartScan = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mStartButton = (Button) findViewById(R.id.start_service_bt);
		mStopButton = (Button) findViewById(R.id.stop_service_bt);
		mEnableButton = (Button) findViewById(R.id.enable_ble_bt);
		mStartScan = (Button) findViewById(R.id.scan_start);
		
		mStartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startBleServiec();
			}
		});
		
		mStopButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mBleService.stopBTbgService();
				} catch (BLEServiceExcpetion e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				changeButtonState(mBleService.isRunningService());
			}
		});
		
		mEnableButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enableBle();
			}
		});
		
		mStartScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mBleService.scanLeDevice(deviceConnListener, 0);
				} catch (BLEServiceExcpetion e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		UUID[] uuid = new UUID[1];
		uuid[0] = UUID.fromString("f000aa00-0451-4000-b000-000000000000");	
		mBleService = new BLEService(getApplicationContext(), uuid);
		
	}
	
	
	public void changeButtonState(boolean isRunService) {
		mStartButton.setEnabled(!isRunService);
		mStopButton.setEnabled(isRunService);
		mEnableButton.setEnabled(isRunService);
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		changeButtonState(mBleService.isRunningService());
	}
	
	
	public void enableBle() {
		try {
			mBleService.enableBTDevice(this);
		} catch (BLEServiceExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.i(TAG, "onActivityResult () = requestCode = "+requestCode+" resultCode = "+requestCode);	
	}
	
	
	public void startBleServiec(){
		try {
			mBleService.startBTbgService(new BLEConnServiceListener() {
				
				@Override
				public void disconnectBLEService() {
					// TODO Auto-generated method stub
					Log.d(TAG, "disconnectBLEService()");
					changeButtonState(false);
				}

				@Override
				public void connectBLEService(boolean result) {
					// TODO Auto-generated method stub
					Log.d(TAG, "connectBLEService() result = "+result);
					if(result)
						changeButtonState(true);
				}
			});
		} catch (BLEServiceExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy(){
		mBleService.release();
		mBleService = null;
		super.onDestroy();
	}
	
	
	private BLEConnDevicesListener deviceConnListener = new BLEConnDevicesListener() {
		@Override
		public void bleDeviceDiscover(BluetoothLEDevice decDevice) {
			// TODO Auto-generated method stub
			Log.i("Test", "BluetoothLEDevice address = "+decDevice.getAddress());
			Log.i("Test", "BluetoothLEDevice rssi = "+decDevice.getAverageRSSI());
		}
	};
	
		
}
