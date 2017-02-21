package com.cs.liker.csbeacon.platform;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

public class BeaconScannerOld extends BeaconScanner {

    private BluetoothAdapter.LeScanCallback leScanCallback =
            (device, rssi, scanRecord) -> {
                handleDevice(device, rssi, scanRecord);
            };

    public BeaconScannerOld(Application application, BluetoothAdapter bluetoothAdapter, CsDeviceManager csDeviceManager) {
        super(bluetoothAdapter, csDeviceManager);
    }

    public void start() {
        bluetoothAdapter.startLeScan(leScanCallback);
    }

    protected void stop() {
        bluetoothAdapter.stopLeScan(leScanCallback);
    }
}
