package com.atmshang.procrastination.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atmshang.procrastination.R;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * BaseFragment
 * Created by atmshang on 2016/12/14.
 */
public class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected View mRootView;
    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected ProgressDialog mProgressDialog;
    protected Handler mHandler = new Handler();
    protected Realm mRealm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRealm = Realm.getDefaultInstance();
        mActivity = getActivity();
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("请稍候...");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void initRootView(int resId) {
        mRootView = View.inflate(mActivity, resId, null);
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
    }

    protected void initToolbar(String title) {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
    }

    protected void initToolbarWithBack(String title) {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
                mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
    }
}