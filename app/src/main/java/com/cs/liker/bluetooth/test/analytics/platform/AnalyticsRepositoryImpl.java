package com.cs.liker.bluetooth.test.analytics.platform;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsRepository;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

public class AnalyticsRepositoryImpl implements AnalyticsRepository {
    private static final String KEY_ENABLED = "enabled";
    private static final boolean KEY_DEFAULT_ENABLED = true;
    private static final String PREFERENCE_FOLDER_PREFIX = "analytics_preferences";

    private final RxSharedPreferences rxSharedPreferences;

    @Inject
    AnalyticsRepositoryImpl(Application context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_FOLDER_PREFIX, Context.MODE_PRIVATE);
        this.rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
    }

    @Override
    public void setTrackingStatus(boolean enabled) {
        rxSharedPreferences.getBoolean(KEY_ENABLED).set(enabled);
        Timber.i("Tracking enabled " + enabled);
    }

    @Override
    public Observable<Boolean> getTrackingStatusObservable() {
        return rxSharedPreferences.getBoolean(KEY_ENABLED, KEY_DEFAULT_ENABLED).asObservable();
    }
}
