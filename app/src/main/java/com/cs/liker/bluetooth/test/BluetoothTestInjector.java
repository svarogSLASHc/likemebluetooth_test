package com.cs.liker.bluetooth.test;


import android.content.Context;

import com.cs.android_base.platform.BaseApplication;

public class BluetoothTestInjector {
    public static BluetoothTestComponent get(Context context) {
        BaseApplication<BluetoothTestComponent> application = BaseApplication.getInstance(context);
        return application.getAppComponent();
    }
}
