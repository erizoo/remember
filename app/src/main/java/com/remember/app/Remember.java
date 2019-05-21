package com.remember.app;

import android.app.Application;

import com.remember.app.di.component.ApplicationComponent;
import com.remember.app.di.component.DaggerApplicationComponent;
import com.remember.app.di.module.ApplicationModule;

public class Remember extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//        Fabric.with(this, new Crashlytics());
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);

    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
