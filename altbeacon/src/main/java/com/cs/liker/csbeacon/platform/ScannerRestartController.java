package com.cs.liker.csbeacon.platform;

import com.cs.liker.csbeacon.domain.BeaconPeripheral;
import com.cs.liker.csbeacon.domain.CsBeaconDevice;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

@Singleton
public class ScannerRestartController {
    @Inject
    BeaconScanner beaconScanner;
    @Inject
    BeaconPeripheral beaconPeripheral;
    private Subscription restartScunnSubscription;

    @Inject
    ScannerRestartController() {
    }

    public void init(String serviceUUID, String characteristicUUID, String userUUID) {
        beaconScanner.init(serviceUUID, characteristicUUID);
        beaconPeripheral.supportPeripheralMode()
                .first()
                .filter(aBoolean -> aBoolean)
                .subscribe(support -> beaconPeripheral.init(serviceUUID, characteristicUUID, userUUID));
    }


    public void start() {
        restartScunnSubscription = Observable.defer(() -> {
            beaconPeripheral.start();
            beaconScanner.start();
            return Observable.empty();
        })
                .concatWith(Observable.timer(1500, TimeUnit.MILLISECONDS))
                .flatMap(o -> Observable.defer(() -> {
                    beaconPeripheral.stopAdvertise();
                    beaconScanner.stopScan();
                    return Observable.empty();
                }))
                .concatWith(Observable.timer(100, TimeUnit.MILLISECONDS))
                .repeat()
                .subscribe();
    }

    public void stop() {
        restartScunnSubscription.unsubscribe();
    }

    public Observable<CsBeaconDevice> bind() {
        return beaconScanner.bind();
    }
}
