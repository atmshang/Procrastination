package com.atmshang.procrastination;

import android.app.Application;

import com.atmshang.procrastination.service.TimerService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by atmshang on 2017/1/16.
 */

public class AppContext extends Application {
    private Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getDefaultInstance();
        TimerService.start(this);

    }
}
