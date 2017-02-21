package com.cs.liker.bluetooth.test.ui.setup.domain;


import android.app.Activity;
import android.content.Intent;

import rx.Observable;

public interface BluetoothResultPresenter{

    Observable<Boolean> hasPeripheral();

    Observable<Boolean> isBluetoothEnabled();

    Observable<Boolean> bluetoothSupported();

    Observable<Boolean> onBluetoothEnabled();

    Observable<Intent> enableBluetooth(Activity activity);
 }
