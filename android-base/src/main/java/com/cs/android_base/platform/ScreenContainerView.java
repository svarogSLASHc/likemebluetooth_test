/**
 * Copyright (c) 2015 Deutsche Telekom AG.
 */
package com.cs.android_base.platform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cs.android_base.R;
import com.cs.android_base.domain.Screen;

import timber.log.Timber;

/**
 * Ensures that presenter view and bound is attached iff this container is attached and screen is set.
 */
public class ScreenContainerView extends FrameLayout {

    private final ScreenContainer container;
    private Screen screen;
    private boolean isAttachedToWindow = false;

    public ScreenContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.container = new ScreenContainer(context, this);
        final String classAttribute = attrs.getClassAttribute();
        if (!TextUtils.isEmpty(classAttribute)) {
            try {
                screen = new GenericScreen(Class.forName(classAttribute).asSubclass(BasePresenterView.class));
            } catch (Exception e) {
                Timber.e(e, "Cannot use specified class view %s", classAttribute);
            }
        }
    }

    @NonNull
    public static ScreenContainerView create(LayoutInflater layoutInflater, ViewGroup container, Screen screen) {
        final ScreenContainerView screenContainer = (ScreenContainerView) layoutInflater
            .inflate(R.layout.screen_container_view, container, false);
        screenContainer.setScreen(screen);
        return screenContainer;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
        if (isAttachedToWindow) {
            // change screen now
            container.setScreen(screen);
        } // else will be set onAttachedToWindow()
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        detachFromWindow();
        super.onDetachedFromWindow();
    }

    public void attachToWindow() {
        this.isAttachedToWindow = true;
        if (screen != null) {
            container.setScreen(screen);
        } // else will be set on setScreen()
    }

    public void detachFromWindow() {
        container.removeCurrentScreen();
        this.isAttachedToWindow = false;
    }

    static class GenericScreen implements Screen {
        final Class<? extends BasePresenterView> viewClass;

        GenericScreen(Class<? extends BasePresenterView> viewClass) {
            this.viewClass = viewClass;
        }

        @Override
        public BasePresenterView createPresenterView() {
            try {
                return viewClass.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
