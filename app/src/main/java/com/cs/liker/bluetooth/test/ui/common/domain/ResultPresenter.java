package com.cs.liker.bluetooth.test.ui.common.domain;


import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsRepository;

import javax.inject.Inject;

import rx.Observable;

public class ResultPresenter {
    @Inject
    AnalyticsRepository analyticsRepository;

    @Inject
    ResultPresenter() {

    }

    public Observable<Boolean> enableAnalyticsObservable() {
        return analyticsRepository.getTrackingStatusObservable();
    }

    public void setAnalyticsEnabled(boolean enable) {
        analyticsRepository.setTrackingStatus(enable);
    }
}
