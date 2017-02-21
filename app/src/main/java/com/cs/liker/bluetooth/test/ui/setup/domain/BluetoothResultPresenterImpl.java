package com.cs.liker.bluetooth.test.ui.setup.domain;


import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;

import com.annimon.stream.Optional;
import com.cs.android_base.platform.ActivityRequestInvoker;
import com.cs.liker.csbeacon.domain.BeaconPeripheral;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class BluetoothResultPresenterImpl implements BluetoothResultPresenter {
    @Inject
    Application application;
    @Inject
    BeaconPeripheral beaconPeripheral;
    @Inject
    Optional<BluetoothAdapter> bluetoothAdapter;
    @Inject
    ActivityRequestInvoker activityRequestInvoker;

    private BehaviorSubject<Object> rejectSubject = BehaviorSubject.create();

    @Inject
    BluetoothResultPresenterImpl() {
    }

    @Override
    public Observable<Boolean> hasPeripheral() {
        return beaconPeripheral.supportPeripheralMode();
    }

    @Override
    public Observable<Boolean> isBluetoothEnabled() {
        return Observable.just(bluetoothAdapter.isPresent() && bluetoothAdapter.get().isEnabled());
    }

    @Override
    public Observable<Boolean> bluetoothSupported() {
        return Observable.create(subscriber -> {
            if (bluetoothAdapter.isPresent()) {
                subscriber.onNext(true);
            } else {
                subscriber.onError(new NotPresentBluetoothException());
            }
        });
    }

    @Override
    public Observable<Boolean> onBluetoothEnabled() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return RxBroadcastReceiver.create(application, filter)
                .map(intent ->
                        intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR) == BluetoothAdapter.STATE_ON)
                .filter(enabled -> enabled);
    }

    @Override
    public Observable<Intent> enableBluetooth(Activity activity) {
        return isBluetoothEnabled()
                .filter(enabled -> !enabled)
                .takeUntil(rejectSubject)
                .flatMap(disabled -> activityRequestInvoker.forActivityRequest(activity, new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)).toObservable())
                .map(activityResult -> {
                    if (Activity.RESULT_CANCELED == activityResult.getResultCode()) {
                        rejectSubject.onNext(null);
                    }
                    return activityResult.getData();
                });
    }

    public class NotPresentBluetoothException extends Throwable {
    }
}
