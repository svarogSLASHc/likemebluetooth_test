package com.cs.liker.bluetooth.test.ui.notsupport;


import com.cs.android_base.domain.AppScreen;
import com.cs.android_base.platform.BasePresenterView;

import javax.inject.Inject;

public class NotSupportScreen extends AppScreen{
    @Inject
    NotSupportScreenView notSupportScreenView;

    @Inject
    NotSupportScreen(){

    }

    @Override
    public BasePresenterView createPresenterView() {
        return notSupportScreenView;
    }
}
