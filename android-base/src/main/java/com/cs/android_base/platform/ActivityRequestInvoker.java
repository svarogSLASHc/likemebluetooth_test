/**
 * Copyright (c) 2015 Deutsche Telekom AG.
 */
package com.cs.android_base.platform;

import android.app.Activity;
import android.content.Intent;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;
import rx.subjects.PublishSubject;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;


@Singleton
public class ActivityRequestInvoker {

    private final AtomicInteger requestCodeCounter = new AtomicInteger();
    private final PublishSubject<ActivityResult> activityResultPublishSubject = PublishSubject.create();

    @Inject
    ActivityRequestInvoker() {
    }

    /** Callback from activity */
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        activityResultPublishSubject.onNext(new ActivityResult(requestCode, resultCode, data));
    }

    public Single<ActivityResult> forActivityRequest(Activity caller, Intent requestIntent) {
        checkNotNull(caller);
        checkNotNull(requestIntent);
        final int requestCode = requestCodeCounter.getAndIncrement();
        return activityResultPublishSubject
            // ask only once we are ready to receive the result
            .doOnSubscribe(() -> {
                Timber.i("activityForResult %s %s %s", caller, requestIntent, requestCode);
                caller.startActivityForResult(requestIntent, requestCode);
            })
            .filter(activityResult -> activityResult.requestCode == requestCode)
            .first()
            .toSingle();
    }

    public class ActivityResult {

        private final int requestCode;
        private final int resultCode;
        private final Intent data;

        public ActivityResult(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        public int getResultCode() {
            return resultCode;
        }

        public Intent getData() {
            return data;
        }

        @Override
        public String toString() {
            return "ActivityResult{" +
                "requestCode=" + requestCode +
                ", resultCode=" + resultCode +
                ", data=" + data +
                '}';
        }
    }

}
