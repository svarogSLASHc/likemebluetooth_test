package com.cs.android_base.platform;


import android.app.Application;
import android.content.Context;

public abstract class BaseApplication<K> extends Application{

    public static BaseApplication getInstance(Context context){
        return (BaseApplication) context.getApplicationContext();
    }
    public abstract K getAppComponent();
}
