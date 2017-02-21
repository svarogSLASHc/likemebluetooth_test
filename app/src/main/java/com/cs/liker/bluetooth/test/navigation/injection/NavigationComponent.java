package com.cs.liker.bluetooth.test.navigation.injection;


import com.cs.liker.bluetooth.test.BluetoothApplication;
import com.cs.liker.bluetooth.test.ui.MainActivity;

public interface NavigationComponent {
    void inject(MainActivity mainActivity);

    void inject(BluetoothApplication application);
}
