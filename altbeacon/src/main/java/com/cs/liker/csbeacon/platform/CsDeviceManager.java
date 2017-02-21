package com.cs.liker.csbeacon.platform;

import com.cs.liker.csbeacon.domain.CsBeaconDevice;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CsDeviceManager {
    private Map<String, CsBeaconDevice> nearByDevices = new HashMap<>();

    @Inject
    CsDeviceManager(){}

    public void resetConnectionStatus(){
        for (String key:nearByDevices.keySet()){
            nearByDevices.get(key).setConnecting(false);
        }
    }

    public void put(String address, CsBeaconDevice beaconDevice){
        nearByDevices.put(address, beaconDevice);
    }

    public CsBeaconDevice emitDevice(String deviceAddress, String UUID) {
        long currentTime = Calendar.getInstance().getTimeInMillis();

        CsBeaconDevice csBeaconDevice = nearByDevices.get(deviceAddress);
        csBeaconDevice.setUuid(UUID);
        csBeaconDevice.setTimestamp(currentTime);
        csBeaconDevice.setEmited(true);
        csBeaconDevice.setConnecting(false);
        csBeaconDevice.setConnectingTime(0);
        nearByDevices.put(deviceAddress, csBeaconDevice);
        return csBeaconDevice;
    }

    public CsBeaconDevice emitExistDevice(CsBeaconDevice beaconDevice) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        beaconDevice.setTimestamp(currentTime);
       return beaconDevice;
    }

    public CsBeaconDevice createDevice(String deviceAddress, int rssi) {
        CsBeaconDevice csBeaconDevice = getDevice(deviceAddress);
        csBeaconDevice.setRssi(rssi);
        nearByDevices.put(deviceAddress, csBeaconDevice);
        return csBeaconDevice;
    }

    public CsBeaconDevice getDevice(String deviceAddress) {
        CsBeaconDevice csBeaconDevice = nearByDevices.get(deviceAddress);
        if (csBeaconDevice == null) {
            csBeaconDevice = new CsBeaconDevice();
        }
        return csBeaconDevice;
    }


}
