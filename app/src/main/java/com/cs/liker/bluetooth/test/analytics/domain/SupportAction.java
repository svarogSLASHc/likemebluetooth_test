package com.cs.liker.bluetooth.test.analytics.domain;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class SupportAction {

    public static Builder builder() {
        return new AutoParcel_SupportAction.Builder().value(0);
    }

    public static SupportAction createDeviceSupportAction() {
        return new AutoParcel_SupportAction.Builder()
                .action("Action")
                .category("Support peripheral")
                .value(0)
                .build();
    }

    public static SupportAction createDoesNotSupportAction() {
        return new AutoParcel_SupportAction.Builder()
                .action("Action")
                .category("Does not support")
                .value(0)
                .build();
    }

    public abstract String action();

    public abstract String category();

    public abstract long value();

    @AutoParcel.Builder
    public static abstract class Builder {

        public abstract Builder action(String action);

        public abstract Builder category(String category);

        public abstract Builder value(long value);

        public abstract SupportAction build();
    }
}
