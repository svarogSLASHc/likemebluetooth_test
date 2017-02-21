package com.cs.liker.csbeacon.platform;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Build;

import com.cs.liker.csbeacon.domain.BeaconPeripheral;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;


public class BeaconPeripheralLollipop implements BeaconPeripheral {
    private static final String TAG = BeaconPeripheralLollipop.class.getCanonicalName();
    private final String UUID_REGEXP = "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeAdvertiser bluetoothLeAdvertiser;
    private AdvertiseSettings settings;
    private AdvertiseData data;

    private UUID myUUID;
    private BehaviorSubject<Boolean> supportPeripheralModeSubject = BehaviorSubject.create(false);
    private BehaviorSubject<String> logBehaviorSubject = BehaviorSubject.create();


    public BeaconPeripheralLollipop(BluetoothAdapter bluetoothAdapter,
                                    BluetoothManager bluetoothManager,
                                    Application application) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public void init(String serviceUUID, String characteristicUUID, String userUUID) {
        //TODO:REMOVE fake
        userUUID = "447533a353fc8175fdd2692ea91c27c1";
        this.myUUID = UUID.fromString(userUUID.replaceFirst(UUID_REGEXP, "$1-$2-$3-$4-$5"));
        initServer();
    }

    public void stopAdvertise() {
        if (bluetoothLeAdvertiser != null) {
            bluetoothLeAdvertiser.stopAdvertising(advertiseCallback);
            bluetoothLeAdvertiser = null;
        }
    }

    public void start() {
        supportPeripheralModeSubject
                .asObservable()
                .first()
                .filter(support -> support.equals(true))
                .flatMap(support -> Observable.create(subscriber -> {
                    bluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
                    startAdvertising();
                    subscriber.onNext(true);
                }))
                .subscribe();
    }

    public Observable<Boolean> supportPeripheralMode() {
        supportPeripheralModeSubject.onNext(
                Build.VERSION.SDK_INT >= 21
                        && bluetoothAdapter != null
                        && bluetoothAdapter.isMultipleAdvertisementSupported());
        return supportPeripheralModeSubject.asObservable();
    }

    public Observable<String> getLogObservable() {
        return logBehaviorSubject.asObservable();
    }


    private void initServer() {
        bluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();

        settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setConnectable(false)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .build();

        data = setAdvertiseData();
    }

    /*
         * Initialize the advertiser
         */
    private void startAdvertising() {
        if (bluetoothLeAdvertiser == null) {
            Timber.i("startAdvertising Error: bluetoothLeAdvertiser == null");
            return;
        }
        bluetoothLeAdvertiser.startAdvertising(settings, data, advertiseCallback);
    }


    protected AdvertiseData setAdvertiseData() {
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);

        bb.put((byte) 0x02); // Beacon Identifier
        bb.put((byte) 0x15);// Beacon Identifier
        // adding the UUID
        bb.putLong(myUUID.getMostSignificantBits());
        bb.putLong(myUUID.getLeastSignificantBits());
        bb.put(18, (byte) 0x00); // first byte of Major
        bb.put(19, (byte) 0x09); // second byte of Major
        bb.put(20, (byte) 0x00); // first minor
        bb.put(21, (byte) 0x06); // second minor
        bb.put(22, (byte) 0xB5); // txPower

        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x004c, manufacturerData);
        return builder.build();
    }

    /*
    * Callback handles events from the framework describing
    * if we were successful in starting the advertisement requests.
    */
    private AdvertiseCallback advertiseCallback = new AdvertiseCallback() {

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Timber.i(TAG, "Peripheral Advertise Started.");
            logBehaviorSubject.onNext("Peripheral Advertise Started.");
        }

        @Override
        public void onStartFailure(int errorCode) {
            String error = "Peripheral Advertise Failed";
            switch (errorCode) {
                case AdvertiseCallback.ADVERTISE_FAILED_ALREADY_STARTED:
                    error = "Advertise failed: already started.";
                    break;
                case AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE:
                    error = "Advertise failed: data too large.";
                    break;
                case AdvertiseCallback.ADVERTISE_FAILED_FEATURE_UNSUPPORTED:
                    error = "Advertise failed: feature unsupported.";
                    break;
                case AdvertiseCallback.ADVERTISE_FAILED_INTERNAL_ERROR:
                    error = "Advertise failed: internal error.";
                    break;
                case AdvertiseCallback.ADVERTISE_FAILED_TOO_MANY_ADVERTISERS:
                    error = "Advertise failed: too many advertisers.";
                    break;

            }
            Timber.i(error);
        }
    };
}

