package com.cs.liker.bluetooth.test.navigation.domain;


import flow.Flow;

public interface NavigationInvoker {

    void ensureFirstScreenInit();

    void setFlowDispatcher(Flow.Dispatcher dispatcher);

    void goToSetupScreen();

    void goToSuccess();

    void goToNotSupported();

    boolean onBackPressed();
}
