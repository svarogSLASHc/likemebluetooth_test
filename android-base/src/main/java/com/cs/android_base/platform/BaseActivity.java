package com.cs.android_base.platform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.android_base.injection.IHasComponent;
import com.cs.android_base.platform.ActivityRequestInvoker;

import javax.inject.Inject;


public abstract class BaseActivity extends AppCompatActivity{
    @Inject
    ActivityRequestInvoker activityRequestInvoker;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        activityRequestInvoker.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent == null) {
            intent = new Intent();
        }
        super.startActivityForResult(intent, requestCode);
    }
}
