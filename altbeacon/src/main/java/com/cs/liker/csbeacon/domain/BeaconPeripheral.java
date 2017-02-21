package com.cs.liker.csbeacon.domain;

import rx.Observable;

public interface BeaconPeripheral {

    void init(String serviceUUID, String characteristicUUID, String userUUID);

    void stopAdvertise();

    void start() ;

    Observable<Boolean> supportPeripheralMode();
}
