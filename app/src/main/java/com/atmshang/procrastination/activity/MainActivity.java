package com.atmshang.procrastination.activity;

import android.os.Bundle;

import com.atmshang.procrastination.R;
import com.atmshang.procrastination.adapter.TabFragmentAdapter;
import com.atmshang.procrastination.event.ControlEvent;
import com.atmshang.procrastination.event.FreezeEvent;
import com.atmshang.procrastination.fragment.HistoryFragment;
import com.atmshang.procrastination.fragment.TimerFragment;
import com.atmshang.toolkit.view.FreezeViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {
    private FreezeViewPager mViewPager;
    private final TabFragmentAdapter.TabItem[] items = {
            new TabFragmentAdapter.TabItem("计时", TimerFragment.newInstance()),
            new TabFragmentAdapter.TabItem("历史", HistoryFragment.newInstance())
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        fitsSystemWindows();
        mViewPager = (FreezeViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), items));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlEvent(ControlEvent event) {
        switch (event.getState()) {
            case stop:
                mViewPager.setScrollAble(true);
                break;
            default:
                mViewPager.setScrollAble(false);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFreezeEvent(FreezeEvent event) {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setScrollAble(event.isScrollAble());
        }
    }
}
