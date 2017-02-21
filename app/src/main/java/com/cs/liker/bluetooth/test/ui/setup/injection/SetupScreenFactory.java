package com.cs.liker.bluetooth.test.ui.setup.injection;

import android.app.Application;

import com.cs.liker.bluetooth.test.BluetoothTestInjector;
import com.cs.liker.bluetooth.test.ui.setup.domain.SetupScreen;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SetupScreenFactory {
    private final TestResultDependenciesComponent testResultDependenciesComponent;

        @Inject SetupScreenFactory(Application application){
            testResultDependenciesComponent = BluetoothTestInjector.get(application);
        }

    public SetupScreen create(){
         TestResultComponent testResultComponent = DaggerTestResultComponent.builder()
                 .testResultDependenciesComponent(testResultDependenciesComponent).build();
        return testResultComponent.getSetupScreen();
    }
}
