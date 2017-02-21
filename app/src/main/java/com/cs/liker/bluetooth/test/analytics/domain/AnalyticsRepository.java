package com.cs.liker.bluetooth.test.analytics.domain;


import rx.Observable;

public interface AnalyticsRepository {

    void setTrackingStatus(boolean enabled);

    Observable<Boolean> getTrackingStatusObservable();
}
