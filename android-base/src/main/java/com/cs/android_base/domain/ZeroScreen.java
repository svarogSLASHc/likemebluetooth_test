package com.cs.android_base.domain;


import com.cs.android_base.platform.BasePresenterView;

public class ZeroScreen extends AppScreen {
    @Override
    public BasePresenterView createPresenterView() {
        throw new IllegalStateException("ZeroScreen is just a placeholder, before first screen is initialized");
    }
}