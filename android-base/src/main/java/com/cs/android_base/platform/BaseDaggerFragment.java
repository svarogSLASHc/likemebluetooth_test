package com.cs.android_base.platform;


import android.support.v4.app.Fragment;


import com.cs.android_base.injection.IHasComponent;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class BaseDaggerFragment extends Fragment {
    protected Subscription subscription = Subscriptions.unsubscribed();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscription.unsubscribe();
    }

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
    }
}
