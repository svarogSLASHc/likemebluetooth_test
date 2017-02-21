package com.cs.liker.bluetooth.test.ui.setup.injection;

import com.cs.android_base.domain.ActivityScope;
import com.cs.liker.bluetooth.test.ui.setup.domain.SetupScreen;

import dagger.Component;


@Component(
        dependencies = TestResultDependenciesComponent.class,
        modules = {
                TestResultModule.class
        }
)
@ActivityScope
public interface TestResultComponent {
    SetupScreen getSetupScreen();
}
