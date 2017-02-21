package com.cs.liker.bluetooth.test.ui.setup.domain;


import com.cs.android_base.domain.AppScreen;
import com.cs.android_base.platform.BasePresenterView;
import com.cs.liker.bluetooth.test.ui.setup.platform.SetupScreenView;

import javax.inject.Inject;

public class SetupScreen extends AppScreen {
    @Inject
    SetupScreenView setupScreenView;

    @Inject
    SetupScreen() {
    }

    @Override
    public BasePresenterView createPresenterView() {
        return setupScreenView;
    }
}
