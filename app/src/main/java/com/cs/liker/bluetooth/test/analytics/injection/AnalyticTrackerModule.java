package com.cs.liker.bluetooth.test.analytics.injection;


import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsController;
import com.cs.liker.bluetooth.test.analytics.domain.AnalyticsRepository;
import com.cs.liker.bluetooth.test.analytics.platform.AnalyticsControllerGoogleImpl;
import com.cs.liker.bluetooth.test.analytics.platform.AnalyticsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AnalyticTrackerModule {

    @Provides
    @Singleton
    AnalyticsController provideAnalyticTracker(AnalyticsControllerGoogleImpl analyticTrackerGoogle) {
        return analyticTrackerGoogle;
    }

    @Provides
    @Singleton
    AnalyticsRepository provideTrackerRepository(AnalyticsRepositoryImpl trackerRepository) {
        return trackerRepository;
    }
}
