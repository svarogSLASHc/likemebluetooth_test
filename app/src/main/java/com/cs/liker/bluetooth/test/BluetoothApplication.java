package com.cs.liker.bluetooth.test;


import com.cs.android_base.platform.BaseApplication;
import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsController;

import javax.inject.Inject;

import timber.log.Timber;

public class BluetoothApplication extends BaseApplication<BluetoothTestComponent> {
    @Inject
    AnalyticsController analyticsController;
    private BluetoothTestComponent bluetoothTestComponent;

    @Override
    public BluetoothTestComponent getAppComponent() {
        return bluetoothTestComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        setComponents();
        analyticsController.init(getString(R.string.analytics_project_id));

    }

    private void setComponents() {
        bluetoothTestComponent = DaggerBluetoothTestComponent.builder()
                .bluetoothTestMainModule(new BluetoothTestMainModule(this))
                .build();
        BluetoothTestInjector.get(this).inject(this);
    }

}
