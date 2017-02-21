/**
 * Copyright (c) 2016 Deutsche Telekom AG.
 */
package com.cs.android_base.platform;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Subscription;

public abstract class BasePresenterView {
    private final int layoutId;
    private Activity activity;

    protected BasePresenterView(int layoutId) {
        this.layoutId = layoutId;
    }

    protected Activity getActivity() {
        return activity;
    }

    /** Internal framework method. */
    /* package */ void bindActivity(Activity activity) {
        this.activity = activity;
    }

    public abstract void injectViews(View view);

    public View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(layoutId, container, false);
    }

    public abstract Subscription bindPresenter();
}
