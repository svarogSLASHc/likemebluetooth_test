package com.cs.liker.bluetooth.test.ui.setup.injection;

import com.cs.liker.bluetooth.test.ui.setup.domain.BluetoothResultPresenter;
import com.cs.liker.bluetooth.test.ui.setup.domain.BluetoothResultPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class TestResultModule {
    @Provides
    BluetoothResultPresenter provideIAddSocialPresenter(BluetoothResultPresenterImpl bluetoothResultPresenter) {
        return bluetoothResultPresenter;
    }
}
