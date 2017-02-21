package com.cs.liker.bluetooth.test.navigation.injection;

import com.cs.android_base.domain.ZeroScreen;
import com.cs.liker.bluetooth.test.navigation.domain.AppNavigation;
import com.cs.liker.bluetooth.test.navigation.domain.NavigationInvoker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import flow.History;

@Module
public class AppNavigationModule {
    @Provides
    @Singleton
    Flow provideFlow() {
        return new Flow(History.single(new ZeroScreen()));
    }

    @Provides
    @Singleton
    NavigationInvoker provideNavigationInvoker(AppNavigation appNavigation) {
        return appNavigation;
    }
}
