package com.cs.liker.bluetooth.test.ui.setup.platform;


import android.app.Application;
import android.view.View;
import android.widget.Toast;

import com.cs.android_base.platform.BasePresenterView;
import com.cs.liker.bluetooth.test.R;
import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsController;
import com.cs.liker.bluetooth.test.analytics.domain.SupportAction;
import com.cs.liker.bluetooth.test.navigation.domain.NavigationInvoker;
import com.cs.liker.bluetooth.test.ui.setup.domain.BluetoothResultPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

public class SetupScreenView extends BasePresenterView {
    @Inject
    Application application;
    @Inject
    BluetoothResultPresenter presenter;
    @Inject
    NavigationInvoker appNavigation;
    @Inject
    AnalyticsController analyticsController;


    private Action1<Boolean> checkPeripheralAction = new Action1<Boolean>() {
        @Override
        public void call(Boolean support) {

            if (support) {
                appNavigation.goToSuccess();
                analyticsController.trackSupportPeripheral(SupportAction.createDeviceSupportAction());
            } else {
                appNavigation.goToNotSupported();
                analyticsController.trackSupportPeripheral(SupportAction.createDoesNotSupportAction());
            }
        }
    };

    private Action1<Throwable> checkPeripheralError = throwable -> showNoBluetoothError();


    @Inject
    SetupScreenView() {
        super(R.layout.view_need_setup);
    }

    @Override
    public void injectViews(View view) {

    }

    @Override
    public Subscription bindPresenter() {
        return Subscriptions.from(
                presenter.bluetoothSupported()
                        .flatMap(support -> presenter.enableBluetooth(getActivity()))
                        .subscribe(r -> {
                        }, checkPeripheralError),
                presenter.onBluetoothEnabled()
                        .flatMap(aBoolean -> presenter.hasPeripheral())
                        .subscribe(checkPeripheralAction),
                presenter.isBluetoothEnabled()
                        .filter(enabled -> enabled)
                        .flatMap(aBoolean -> presenter.hasPeripheral())
                        .subscribe(checkPeripheralAction));
    }


    public void showNoBluetoothError() {
        Toast.makeText(application, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
    }
}
