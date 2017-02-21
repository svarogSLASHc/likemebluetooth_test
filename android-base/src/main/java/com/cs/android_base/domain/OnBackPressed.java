package com.cs.android_base.domain;


public interface OnBackPressed {
    /**
     * May process back pressed events.
     *
     * @return true if back pressed event was consumed
     */
    boolean onBackPressed();
}