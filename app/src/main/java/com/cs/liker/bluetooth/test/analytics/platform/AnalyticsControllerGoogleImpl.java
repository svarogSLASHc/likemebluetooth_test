package com.cs.liker.bluetooth.test.analytics.platform;


import android.app.Application;

import com.annimon.stream.Optional;
import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsController;
import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsRepository;
import com.cs.liker.bluetooth.test.analytics.domain.SupportAction;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import timber.log.Timber;

@Singleton
public class AnalyticsControllerGoogleImpl implements AnalyticsController {
    private final AtomicBoolean isInit = new AtomicBoolean(false);
    @Inject
    AnalyticsRepository analyticsRepository;
    @Inject
    Application application;
    private Optional<Tracker> trackerOptional = Optional.empty();

    @Inject
    AnalyticsControllerGoogleImpl() {
    }

    @Override
    public void init(String projectId) {
        if (!isInit.getAndSet(true)) {
            final Optional<GoogleAnalytics> analyticsOptional = Optional.ofNullable(GoogleAnalytics.getInstance(application));
            analyticsOptional.ifPresent(analytics -> trackerOptional = Optional.ofNullable(analytics.newTracker(projectId)));

            analyticsRepository.getTrackingStatusObservable()
                    .filter(enabled -> analyticsOptional.isPresent())
                    .subscribe(
                            enabled -> {
                                analyticsOptional.get().setAppOptOut(!enabled);
                                Timber.i("setAppOptOut " + !enabled);
                            }
                    );
        }
    }

    @Override
    public void setTracking(boolean enabled) {
        analyticsRepository.setTrackingStatus(enabled);
    }

    @Override
    public Observable<Boolean> enabledStatusObservable() {
        return analyticsRepository.getTrackingStatusObservable();
    }

    @Override
    public void trackSupportPeripheral(SupportAction supportAction) {
        trackerOptional.ifPresent(tracker -> {
                    Timber.i("trackSupportPeripheral " + supportAction);
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory(supportAction.category())
                            .setAction(supportAction.action())
                            .setValue(supportAction.value())
                            .build());
                }

        );
    }
}
