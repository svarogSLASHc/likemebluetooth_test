package com.cs.liker.csbeacon.platform;


import com.cs.liker.csbeacon.domain.CsBeaconDevice;

import rx.Observable;

public class BeaconScannerLollipopMock extends BeaconScannerLollipop {
    public BeaconScannerLollipopMock() {
        super(null, null);
    }

    @Override
    public void start() {

    }

    @Override
    protected void stop() {

    }

    @Override
    public Observable<CsBeaconDevice> bind() {
        return Observable.empty();
    }
}
