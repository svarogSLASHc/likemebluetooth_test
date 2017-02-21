package com.cs.liker.bluetooth.test;


import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class BluetoothTestMainModule {

    private Application application;

    BluetoothTestMainModule(Application application){
        this.application = application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }
}
