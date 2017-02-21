package com.cs.liker.csbeacon.platform;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BeaconScannerLollipop extends BeaconScanner {

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            handleDevice(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes());
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.e("ScanCallback", "onBatchScanResults result size : " + results.size());
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    public BeaconScannerLollipop(BluetoothAdapter bluetoothAdapter, CsDeviceManager csDeviceManager) {
        super(bluetoothAdapter, csDeviceManager);
    }

    public void start() {
        ScanFilter scanFilter = new ScanFilter.Builder().setServiceUuid(new ParcelUuid(serviceUUID)).build();
        ScanSettings settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
        List<ScanFilter> scanFilters = new ArrayList<>();
        scanFilters.add(scanFilter);
        bluetoothAdapter.getBluetoothLeScanner().startScan(scanFilters, settings, scanCallback);
    }

    protected void stop() {
        bluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
        bluetoothAdapter.getBluetoothLeScanner().flushPendingScanResults(scanCallback);

    }
}
