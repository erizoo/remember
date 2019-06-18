package com.remember.app.ui.settings.data;

import com.arellomobile.mvp.InjectViewState;
import com.pixplicity.easyprefs.library.Prefs;
import com.remember.app.Remember;
import com.remember.app.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PersonalDataFragmentPresenter extends BasePresenter<PersonalDataFragmentView> {

    public PersonalDataFragmentPresenter() {
        Remember.getApplicationComponent().inject(this);
    }

    public void getInfo() {
        Disposable subscription = getServiceNetwork().getInfo(Prefs.getString("USER_ID","0"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::onReceivedInfo,
                        getViewState()::error);
        unsubscribeOnDestroy(subscription);
    }
}
