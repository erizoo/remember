package com.remember.app.ui.cabinet.memory_pages.events.current_event;

import com.arellomobile.mvp.InjectViewState;
import com.remember.app.Remember;
import com.remember.app.data.network.ServiceNetwork;
import com.remember.app.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CurrentEventPresenter extends BasePresenter<CurrentEventView> {

    @Inject
    ServiceNetwork serviceNetwork;

    public CurrentEventPresenter() {
        Remember.getApplicationComponent().inject(this);
    }

    public void getEvent(int id) {
        Disposable subscription = serviceNetwork.getEvent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::onReceivedEvent);
        unsubscribeOnDestroy(subscription);

    }
}
