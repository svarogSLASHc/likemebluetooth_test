package com.cs.liker.csbeacon.platform;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.cs.liker.csbeacon.domain.CsBeaconDevice;
import com.cs.liker.csbeacon.utils.UuidUtils;

import java.util.Calendar;
import java.util.UUID;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public abstract class BeaconScanner {
    protected BluetoothAdapter bluetoothAdapter;
    protected CsDeviceManager csDeviceManager;

    protected UUID serviceUUID;
    protected UUID characteristicUUID;
    private BehaviorSubject<CsBeaconDevice> nearDevicesSubject = BehaviorSubject.create();

    public BeaconScanner(BluetoothAdapter bluetoothAdapter, CsDeviceManager csDeviceManager) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.csDeviceManager = csDeviceManager;
    }

    public void init(String serviceUUID, String characteristicUUID) {
        this.serviceUUID = UUID.fromString(serviceUUID);
        this.characteristicUUID = UUID.fromString(characteristicUUID);
    }

    public Observable<CsBeaconDevice> bind() {
        return nearDevicesSubject.asObservable();
    }

    public abstract void start();

    public void stopScan() {
        csDeviceManager.resetConnectionStatus();
        stop();
    }

    protected abstract void stop();

    protected void handleDevice(BluetoothDevice device, int Rssi, byte[] advertiseData) {
        csDeviceManager.createDevice(device.getAddress(), Rssi);

        long currentTime = Calendar.getInstance().getTimeInMillis();
        CsBeaconDevice beaconDevice = csDeviceManager.getDevice(device.getAddress());

        if (currentTime - beaconDevice.getTimestamp() > CsBeaconDevice.REFRESH_TIME) {
            interactWithDevice(beaconDevice, device, advertiseData);
        }
    }

    private void interactWithDevice(CsBeaconDevice beaconDevice, BluetoothDevice device,  byte[] advertiseData){
        if (beaconDevice.wasEmited()) {
            csDeviceManager.emitExistDevice(beaconDevice);
        } else {

        }
    }

    protected String readBeacon(String address, byte[] bytes){
        String hexScanRecord = UuidUtils.bytesToHex(bytes);
        String userUUID = UuidUtils.onLeScan(bytes);
        Timber.i("Read " + address + " hex string " + hexScanRecord);
        return userUUID;
    }

}
