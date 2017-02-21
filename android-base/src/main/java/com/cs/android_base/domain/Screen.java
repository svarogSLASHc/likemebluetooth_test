package com.cs.android_base.domain;


import com.cs.android_base.platform.BasePresenterView;

public interface Screen {
    BasePresenterView createPresenterView();
}
