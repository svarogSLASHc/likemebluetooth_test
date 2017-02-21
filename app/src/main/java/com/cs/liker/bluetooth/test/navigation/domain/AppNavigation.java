package com.cs.liker.bluetooth.test.navigation.domain;

import com.cs.android_base.domain.AppScreen;
import com.cs.android_base.domain.OnBackPressed;
import com.cs.android_base.domain.ZeroScreen;
import com.cs.liker.bluetooth.test.ui.notsupport.NotSupportScreen;
import com.cs.liker.bluetooth.test.ui.setup.injection.SetupScreenFactory;
import com.cs.liker.bluetooth.test.ui.success.SuccessScreen;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.History;

@Singleton
public class AppNavigation implements NavigationInvoker {
    @Inject Flow flow;
    @Inject SetupScreenFactory setupScreenFactory;
    @Inject SuccessScreen successScreen;
    @Inject NotSupportScreen notSupportScreen;


    @Inject AppNavigation(){

    }

    @Override
    public void ensureFirstScreenInit() {
        if (flow.getHistory().top() instanceof ZeroScreen) {
            goToSetupScreen();
        }
    }

    @Override
    public void setFlowDispatcher(Flow.Dispatcher dispatcher) {
        flow.setDispatcher(dispatcher);
    }

    @Override
    public void goToSetupScreen() {
        setHistory(setupScreenFactory.create());
    }

    @Override
    public void goToSuccess() {
        setHistory(successScreen);
    }

    @Override
    public void goToNotSupported() {
        setHistory(notSupportScreen);
    }

    @Override
    public boolean onBackPressed() {
        return ((OnBackPressed) flow.getHistory().top()).onBackPressed() || flow.goBack();
    }

    private void setHistory(AppScreen screen) {
        flow.setHistory(History.emptyBuilder().push(screen).build(), Flow.Direction.REPLACE);
    }
}
