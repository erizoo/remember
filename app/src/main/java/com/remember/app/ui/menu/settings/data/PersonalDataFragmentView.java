package com.remember.app.ui.menu.settings.data;

import com.arellomobile.mvp.MvpView;
import com.remember.app.data.models.ResponseSettings;

public interface PersonalDataFragmentView extends MvpView {

    void onReceivedInfo(ResponseSettings responseSettings);

    void error(Throwable throwable);

    void onSaved(Object o);

    void onSavedImage(Object o);
}
