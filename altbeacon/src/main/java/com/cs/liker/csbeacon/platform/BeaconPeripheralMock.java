package com.cs.liker.csbeacon.platform;

import com.cs.liker.csbeacon.domain.BeaconPeripheral;

import rx.Observable;

public class BeaconPeripheralMock implements BeaconPeripheral {
    private static final String TAG = BeaconPeripheralMock.class.getCanonicalName();

    public BeaconPeripheralMock() {
    }

    public Observable<Boolean> supportPeripheralMode() {
        return Observable.just(false);
    }

    public void init(String serviceUUID, String characteristicUUID, String userUUID) {}

    public void stopAdvertise() {}

    public void start() {}

    public Observable<String> getLogObservable() {
        return Observable.empty();
    }


}

