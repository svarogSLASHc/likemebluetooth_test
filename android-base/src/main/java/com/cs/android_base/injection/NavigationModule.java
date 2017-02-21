/**
 * Copyright (c) 2016 Deutsche Telekom AG.
 */
package com.cs.android_base.injection;

import com.cs.android_base.domain.ZeroScreen;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import flow.History;

@Module
public class NavigationModule {
    @Provides
    @Singleton
    Flow provideFlow() {
        return new Flow(History.single(new ZeroScreen()));
    }
}
