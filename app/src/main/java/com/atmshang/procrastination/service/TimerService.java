package com.atmshang.procrastination.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.atmshang.procrastination.entity.History;
import com.atmshang.procrastination.enums.TimerState;
import com.atmshang.procrastination.event.ControlEvent;
import com.atmshang.procrastination.event.TimeChangeEvent;
import com.atmshang.toolkit.util.IUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

import static com.atmshang.procrastination.enums.TimerState.stop;

/**
 * Created by atmshang on 2017/1/17.
 */

public class TimerService extends Service {

    private Realm mRealm;
    private Timer mTimer;
    private Handler mHandler = new Handler();
    private TimerState mState = stop;
    private History mHistory;
    private TimeChangeEvent mTimeChangeEvent;
    private long delay = 500;

    public static void start(Context context) {
        Intent starter = new Intent(context, TimerService.class);
        context.startService(starter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRealm = Realm.getDefaultInstance();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlEvent(final ControlEvent event) {
        mState = event.getState();
        switch (mState) {
            case process:
                if (mTimer == null) {
                    mTimer = new Timer();
                    History temp = mRealm.where(History.class).equalTo("header", true).equalTo("dayTimestamp", IUtil.getTodayLast()).findFirst();

                    if (temp == null) {
                        mRealm.executeTransaction(realm -> {
                            History temp1 = realm.createObject(History.class);
                            temp1.setHeader(true);
                            temp1.setDayTimestamp(IUtil.getTodayLast());
                            temp1.setCreateTimestamp(IUtil.getTodayLast());
                        });
                    }

                    mRealm.executeTransaction(realm -> {
                        mHistory = realm.createObject(History.class);
                        mHistory.setName(event.getName());
                        mHistory.setHeader(false);
                        mHistory.setDayTimestamp(IUtil.getToday());
                        mHistory.setCreateTimestamp(System.currentTimeMillis());
                        mHistory.setFinishTimestamp(System.currentTimeMillis());
                    });
                    mTimeChangeEvent = new TimeChangeEvent(0, 0);
                    mTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            mHandler.post(() -> {
                                switch (mState) {
                                    case process:
                                        mTimeChangeEvent.setProcess(mTimeChangeEvent.getProcess() + delay);
                                        break;
                                    case procrastinate:
                                        mTimeChangeEvent.setProcrastinate(mTimeChangeEvent.getProcrastinate() + delay);
                                        break;
                                }
                                EventBus.getDefault().post(mTimeChangeEvent);
                                if ((mTimeChangeEvent.getProcess() + mTimeChangeEvent.getProcrastinate() - mHistory.getProcess() - mHistory.getProcrastinate()) > 60 * 1000) {
                                    mRealm.executeTransaction(realm -> {
                                        mHistory.setName(event.getName());
                                        mHistory.setFinishTimestamp(System.currentTimeMillis());
                                        mHistory.setProcess(mTimeChangeEvent.getProcess());
                                        mHistory.setProcrastinate(mTimeChangeEvent.getProcrastinate());
                                    });
                                }
                            });
                        }
                    }, delay, delay);
                }
                break;
            case stop:
                mTimer.cancel();
                mTimer = null;
                mRealm.executeTransaction(realm -> {
                    mHistory.setName(event.getName());
                    mHistory.setFinishTimestamp(System.currentTimeMillis());
                    mHistory.setProcess(mTimeChangeEvent.getProcess());
                    mHistory.setProcrastinate(mTimeChangeEvent.getProcrastinate());
                });
                break;
        }
    }
}
