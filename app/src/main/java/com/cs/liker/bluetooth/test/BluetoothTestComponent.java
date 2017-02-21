package com.cs.liker.bluetooth.test;

import com.cs.liker.bluetooth.test.analytics.injection.AnalyticTrackerModule;
import com.cs.liker.bluetooth.test.navigation.injection.AppNavigationModule;
import com.cs.liker.bluetooth.test.navigation.injection.NavigationComponent;
import com.cs.liker.bluetooth.test.ui.setup.injection.TestResultDependenciesComponent;
import com.cs.liker.csbeacon.injection.CsBeaconComponent;
import com.cs.liker.csbeacon.injection.CsBeaconModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        BluetoothTestMainModule.class,
        CsBeaconModule.class,
        AppNavigationModule.class,
        AnalyticTrackerModule.class
})
public interface BluetoothTestComponent extends
        CsBeaconComponent,
        TestResultDependenciesComponent,
        NavigationComponent {
}
