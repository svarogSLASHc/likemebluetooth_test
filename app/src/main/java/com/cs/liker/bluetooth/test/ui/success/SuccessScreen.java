package com.cs.liker.bluetooth.test.ui.success;


import com.cs.android_base.domain.AppScreen;
import com.cs.android_base.platform.BasePresenterView;

import javax.inject.Inject;

public class SuccessScreen extends AppScreen {
    @Inject
    SuccessScreenView successScreenView;

    @Inject
    SuccessScreen() {

    }

    @Override
    public BasePresenterView createPresenterView() {
        return successScreenView;
    }
}
