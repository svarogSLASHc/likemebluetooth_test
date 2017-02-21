package com.cs.liker.csbeacon.injection;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.os.Build;

import com.annimon.stream.Optional;
import com.cs.liker.csbeacon.domain.BeaconPeripheral;
import com.cs.liker.csbeacon.platform.BeaconPeripheralLollipop;
import com.cs.liker.csbeacon.platform.BeaconPeripheralMock;
import com.cs.liker.csbeacon.platform.BeaconScanner;
import com.cs.liker.csbeacon.platform.BeaconScannerLollipop;
import com.cs.liker.csbeacon.platform.BeaconScannerLollipopMock;
import com.cs.liker.csbeacon.platform.BeaconScannerOld;
import com.cs.liker.csbeacon.platform.CsDeviceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class CsBeaconModule {

    @Provides
    @Singleton
    Optional<BluetoothAdapter> provideBluetoothAdapter(Optional<BluetoothManager> bluetoothManager) {
        if (bluetoothManager.isPresent()) {
            return Optional.ofNullable(bluetoothManager.get().getAdapter());
        } else {
            return Optional.empty();
        }
    }

    @Provides
    @Singleton
    Optional<BluetoothManager> provideBluetoothManager(Application application) {
        return Optional.ofNullable((BluetoothManager) application.getSystemService(application.BLUETOOTH_SERVICE));
    }

    @Provides
    @Singleton
    BeaconPeripheral provideBeaconPeripheral(Application application, Optional<BluetoothManager> bluetoothManager, Optional<BluetoothAdapter> bluetoothAdapter) {
        if (Build.VERSION.SDK_INT >= 21 && bluetoothAdapter.isPresent()) {
            return new BeaconPeripheralLollipop(bluetoothAdapter.get(), bluetoothManager.get(), application);
        } else {
            Timber.w("Can't be a peripheral because is pre Lollipop or Genymotion");
            return new BeaconPeripheralMock();
        }
    }


    @Provides
    @Singleton
    BeaconScanner provideBeaconScanner(Application application, Optional<BluetoothAdapter> bluetoothAdapter, CsDeviceManager csDeviceManager) {
        if (bluetoothAdapter.isPresent()) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new BeaconScannerLollipop(bluetoothAdapter.get(), csDeviceManager);
            } else {
                return new BeaconScannerOld(application, bluetoothAdapter.get(), csDeviceManager);
            }
        } else {
            Timber.w("No BluetoothAdapter for BeaconScanner, seems like it is Genymotion");
            return new BeaconScannerLollipopMock();
        }
    }

}
