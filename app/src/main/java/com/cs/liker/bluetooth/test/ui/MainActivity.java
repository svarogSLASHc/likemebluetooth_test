package com.cs.liker.bluetooth.test.ui;


import android.os.Bundle;

import com.cs.android_base.domain.AppScreen;
import com.cs.android_base.platform.BaseActivity;
import com.cs.android_base.platform.ScreenContainerView;
import com.cs.liker.bluetooth.test.BluetoothTestInjector;
import com.cs.liker.bluetooth.test.R;
import com.cs.liker.bluetooth.test.navigation.domain.NavigationInvoker;

import javax.inject.Inject;

import static butterknife.ButterKnife.findById;


public class MainActivity extends BaseActivity {
    @Inject NavigationInvoker navigation;
    private ScreenContainerView container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothTestInjector.get(this).inject(this);
        container = findById(this, R.id.contentContainer);
        navigation.ensureFirstScreenInit();
        navigation.setFlowDispatcher((traversal, callback) -> {
            final AppScreen newScreen = traversal.destination.top();
            container.setScreen(newScreen);
            callback.onTraversalCompleted();
        });

    }

    @Override
    public void onBackPressed() {
        if (!navigation.onBackPressed()) {
            // Flow has nothing left in History
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        container.attachToWindow();
    }


    @Override
    protected void onPause() {
        super.onPause();
        container.detachFromWindow();
    }
}
