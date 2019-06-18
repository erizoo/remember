package com.remember.app.ui.cabinet.memory_pages.show_page;

import com.arellomobile.mvp.InjectViewState;
import com.remember.app.Remember;
import com.remember.app.data.network.ServiceNetwork;
import com.remember.app.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ShowPagePresenter extends BasePresenter<ShowPageView> {

    @Inject
    ServiceNetwork serviceNetwork;

    public ShowPagePresenter() {
        Remember.getApplicationComponent().inject(this);
    }

    public void getImageAfterSave(Integer id) {
        Disposable subscription = serviceNetwork.getImageAfterSave(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::onReceivedImage,
                        getViewState()::error);
        unsubscribeOnDestroy(subscription);
    }
}
