package com.cs.android_base.platform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.android_base.injection.IHasComponent;


public abstract class BaseDaggerActivity<K,T> extends AppCompatActivity implements IHasComponent<T> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(getApplicationComponent());
    }

    protected abstract K getApplicationComponent();

    protected abstract void setupComponent(K appComponent);

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent == null) {
            intent = new Intent();
        }
        super.startActivityForResult(intent, requestCode);
    }
}
