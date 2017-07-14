package com.atmshang.procrastination.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmshang.procrastination.R;
import com.atmshang.procrastination.enums.TimerState;
import com.atmshang.procrastination.event.ControlEvent;
import com.atmshang.procrastination.event.TimeChangeEvent;
import com.atmshang.toolkit.util.CustomInputDialog;
import com.atmshang.toolkit.view.RevealLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

/**
 * Created by atmshang on 2017/1/16.
 */

public class TimerFragment extends BaseFragment {
    private RevealLayout mRlProcess;
    private RevealLayout mRlProcrastinate;
    private FrameLayout mFlGroup;

    private FloatingActionButton mFabSetting;
    private FloatingActionButton mFabController;
    private FloatingActionButton mFabStop;

    private TextView mTvProcess;
    private TextView mTvProcessName;
    private TextView mTvProcrastinate;

    private LinearLayout.LayoutParams paramsSetting, paramsStop;
    private FrameLayout.LayoutParams paramsController;

    private int widthM, widthL;

    private TimerState mState = TimerState.stop;
    private String processName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootView(R.layout.fragment_timer);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRlProcess = (RevealLayout) mRootView.findViewById(R.id.rl_process);
        mRlProcrastinate = (RevealLayout) mRootView.findViewById(R.id.rl_procrastinate);
        mFlGroup = (FrameLayout) mRootView.findViewById(R.id.fl_group);
        mFabSetting = (FloatingActionButton) mRootView.findViewById(R.id.fab_setting);
        mFabController = (FloatingActionButton) mRootView.findViewById(R.id.fab_controller);
        mFabStop = (FloatingActionButton) mRootView.findViewById(R.id.fab_stop);
        mTvProcess = (TextView) mRootView.findViewById(R.id.tv_process);
        mTvProcessName = (TextView) mRootView.findViewById(R.id.tv_process_name);
        mTvProcrastinate = (TextView) mRootView.findViewById(R.id.tv_procrastinate);

        paramsSetting = (LinearLayout.LayoutParams) mFabSetting.getLayoutParams();
        paramsController = (FrameLayout.LayoutParams) mFabController.getLayoutParams();
        paramsStop = (LinearLayout.LayoutParams) mFabStop.getLayoutParams();

        widthM = paramsSetting.width;
        widthL = paramsController.width;

        paramsStop.width = 0;
        mFabStop.setLayoutParams(paramsStop);

        mRlProcess.setChecked(false, false);
        mRlProcess.setChecked(false, false);
        mTvProcessName.setText(TextUtils.isEmpty(processName) ? "未定义" : processName);

        mRlProcess.setOnClickListener(this::onClick);
        mRlProcrastinate.setOnClickListener(this::onClick);
        mFabSetting.setOnClickListener(this::onClick);
        mFabController.setOnClickListener(this::onClick);
        mFabStop.setOnClickListener(this::onClick);
    }

    public static TimerFragment newInstance() {
        Bundle args = new Bundle();
        TimerFragment fragment = new TimerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeChangeEvent(TimeChangeEvent event) {
        if (event.getProcess() > 1000 * 60 * 60) {
            mTvProcess.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", event.getProcess() / 1000 / 60 / 60, event.getProcess() / 1000 / 60 % 60, event.getProcess() / 1000 % 60));
        } else {
            mTvProcess.setText(String.format(Locale.getDefault(), "%02d:%02d", event.getProcess() / 1000 / 60 % 60, event.getProcess() / 1000 % 60));
        }
        if (event.getProcrastinate() > 1000 * 60 * 60) {
            mTvProcrastinate.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", event.getProcrastinate() / 1000 / 60 / 60, event.getProcrastinate() / 1000 / 60 % 60, event.getProcrastinate() / 1000 % 60));
        } else {
            mTvProcrastinate.setText(String.format(Locale.getDefault(), "%02d:%02d", event.getProcrastinate() / 1000 / 60 % 60, event.getProcrastinate() / 1000 % 60));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_process:
                mState = !mRlProcess.isChecked() && !mRlProcrastinate.isChecked() ? TimerState.stop : mState == TimerState.pause ? TimerState.pause : TimerState.process;
                if (mState == TimerState.pause) {
                    mRlProcess.setChecked(true);
                    mRlProcrastinate.setChecked(false);
                }
                break;
            case R.id.rl_procrastinate:
                mState = !mRlProcess.isChecked() && !mRlProcrastinate.isChecked() ? TimerState.stop : mState == TimerState.pause ? TimerState.pause : TimerState.procrastinate;
                if (mState == TimerState.pause) {
                    mRlProcess.setChecked(false);
                    mRlProcrastinate.setChecked(true);
                }
                break;
            case R.id.fab_setting:
                CustomInputDialog dialog = new CustomInputDialog(mActivity).setMessage("输入事项代号")
                        .setCancelable(true).setNegativeListener("取消", null)
                        .setPositiveListener("确定", str -> {
                            processName = str;
                            mTvProcessName.setText(processName);
                        });
                dialog.show();
                break;
            case R.id.fab_controller:
                if (TextUtils.isEmpty(processName)) {
                    CustomInputDialog dialogEmpty = new CustomInputDialog(mActivity).setMessage("请先输入事项代号")
                            .setCancelable(true).setNegativeListener("取消", null)
                            .setPositiveListener("确定", str -> {
                                processName = str;
                                mTvProcessName.setText(processName);
                            });
                    dialogEmpty.show();
                } else {
                    mState = mState == TimerState.stop ? TimerState.process : mState != TimerState.pause ? TimerState.pause : mRlProcess.isChecked() ? TimerState.process : TimerState.procrastinate;
                }
                break;
            case R.id.fab_stop:
                mState = TimerState.stop;
                break;
        }

        EventBus.getDefault().post(new ControlEvent(processName, mState));
        TransitionManager.beginDelayedTransition(mFlGroup);
        switch (mState) {
            case process:
                paramsSetting.gravity = Gravity.RIGHT;
                paramsSetting.width = 0;
                paramsStop.gravity = Gravity.LEFT;
                paramsStop.width = 0;
                paramsController.width = widthM;
                mFabSetting.setLayoutParams(paramsSetting);
                mFabStop.setLayoutParams(paramsStop);
                mFabController.setLayoutParams(paramsController);

                mRlProcess.setChecked(true);
                mRlProcrastinate.setChecked(false);
                mFabController.setImageResource(R.drawable.ic_pause_black_24dp);
                break;
            case procrastinate:
                paramsSetting.gravity = Gravity.RIGHT;
                paramsSetting.width = 0;
                paramsStop.gravity = Gravity.LEFT;
                paramsStop.width = 0;
                paramsController.width = widthM;
                mFabSetting.setLayoutParams(paramsSetting);
                mFabStop.setLayoutParams(paramsStop);
                mFabController.setLayoutParams(paramsController);
                mRlProcess.setChecked(false);
                mRlProcrastinate.setChecked(true);
                mFabController.setImageResource(R.drawable.ic_pause_black_24dp);
                break;
            case pause:
                paramsSetting.gravity = Gravity.CENTER;
                paramsSetting.width = widthM;
                paramsStop.gravity = Gravity.CENTER;
                paramsStop.width = widthM;
                paramsController.width = widthL;
                mFabSetting.setLayoutParams(paramsSetting);
                mFabStop.setLayoutParams(paramsStop);
                mFabController.setLayoutParams(paramsController);
                mFabController.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                break;
            case stop:
                paramsStop.gravity = Gravity.CENTER;
                paramsStop.width = 0;
                mFabStop.setLayoutParams(paramsStop);
                mTvProcess.setText("00:00");
                mTvProcrastinate.setText("00:00");
                mRlProcess.setChecked(false);
                mRlProcrastinate.setChecked(false);
                mFabController.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                break;
        }
    }
}
