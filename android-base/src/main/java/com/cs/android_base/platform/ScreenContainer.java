
package com.cs.android_base.platform;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.MainThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.android_base.domain.Screen;

import rx.Subscription;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public final class ScreenContainer {
    private final Activity activity;
    private final ViewGroup container;
    private final LayoutInflater layoutInflater;
    // state
    private Subscription currentPresenterBindings = Subscriptions.unsubscribed();

    public ScreenContainer(Context context, ViewGroup container) {
        this.activity = (Activity) context;
        this.layoutInflater = this.activity.getLayoutInflater();
        this.container = container;
    }

    @MainThread
    public void setScreen(Screen newScreen) {
        removeCurrentScreen();
        Timber.d("setScreen %s", newScreen);

        final BasePresenterView presenterView = newScreen.createPresenterView();
        presenterView.bindActivity(activity);

        final View view = presenterView.createView(layoutInflater, container);
        presenterView.injectViews(view);
        container.addView(view);

        currentPresenterBindings = presenterView.bindPresenter();
    }

    @MainThread
    public void removeCurrentScreen() {
        Timber.d("removeCurrentScreen %s", currentPresenterBindings);
        currentPresenterBindings.unsubscribe();

        container.removeAllViews();
    }
}
