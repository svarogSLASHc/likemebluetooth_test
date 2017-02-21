package com.cs.liker.bluetooth.test.ui.setup.injection;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

import com.annimon.stream.Optional;
import com.cs.android_base.platform.ActivityRequestInvoker;
import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsController;
import com.cs.liker.bluetooth.test.navigation.domain.NavigationInvoker;
import com.cs.liker.csbeacon.domain.BeaconPeripheral;

public interface TestResultDependenciesComponent {

    Application getApplication();

    NavigationInvoker getNavigationInvoker();

    ActivityRequestInvoker getActivityRequestInvoker();

    BeaconPeripheral getBeaconPeripheral();

    Optional<BluetoothAdapter> getBluetoothAdapter();

    AnalyticsController getAnalyticTracker();
}
