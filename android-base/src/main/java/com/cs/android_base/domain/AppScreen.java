package com.cs.android_base.domain;


public abstract class AppScreen implements Screen, OnBackPressed {
    @Override
    public boolean onBackPressed() {
        return false;
    }
}
