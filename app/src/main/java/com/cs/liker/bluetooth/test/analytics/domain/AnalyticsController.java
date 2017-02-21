package com.cs.liker.bluetooth.test.analytics.domain;


import rx.Observable;

public interface AnalyticsController {

    void init(String projectId);

    void setTracking(boolean enabled);

    Observable<Boolean> enabledStatusObservable();

    void trackSupportPeripheral(SupportAction support);
}
