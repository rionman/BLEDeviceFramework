package com.android.framework.ble.service;

interface IBLEBackgroundService {

    boolean registerUUIDFromApp(in String[] uuId, String packageName);
    
    boolean unregisterUUIDFromApp(String packageName, in String[] uuidString);
    
    boolean unregisterUUIDFromAppAll(String packageName);
    
    boolean scanLEDevice(String packageName, int period, int scanStream);
    
    void stopLEDevice(String packageName);
}