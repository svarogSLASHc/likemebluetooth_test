package com.cs.liker.bluetooth.test.ui.success;


import android.view.View;
import android.widget.CheckBox;

import com.cs.android_base.platform.BasePresenterView;
import com.cs.liker.bluetooth.test.R;
import com.cs.liker.bluetooth.test.ui.common.domain.ResultPresenter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class SuccessScreenView extends BasePresenterView {
    @Inject
    ResultPresenter presenter;
    @Bind(R.id.analyticsLayout)
    View allowContainer;
    @Bind(R.id.analyticsCheckBox)
    CheckBox allowCheckBox;

    @Inject
    SuccessScreenView() {
        super(R.layout.view_success);
    }

    @Override
    public void injectViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public Subscription bindPresenter() {
        return Subscriptions.from(
                presenter.enableAnalyticsObservable()
                        .subscribe(enable -> RxCompoundButton.checked(allowCheckBox).call(enable)),
                RxCompoundButton.checkedChanges(allowCheckBox)
                        .skip(1)
                        .mergeWith(RxView.clicks(allowContainer)
                                .map(aVoid -> !allowCheckBox.isChecked()))
                        .subscribe(enable -> presenter.setAnalyticsEnabled(enable))
        );
    }
}
