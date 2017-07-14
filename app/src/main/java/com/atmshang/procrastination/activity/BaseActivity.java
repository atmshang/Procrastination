package com.atmshang.procrastination.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.atmshang.procrastination.AppContext;
import com.atmshang.procrastination.R;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * BaseActivity
 * Created by atmshang on 2016/12/14.
 */
public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected Activity mActivity;
    protected AppContext mAppContext;
    protected ProgressDialog mProgressDialog;
    protected Handler mHandler = new Handler();
    protected Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mAppContext = (AppContext) getApplicationContext();
        mRealm = Realm.getDefaultInstance();
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("请稍候...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initToolbar(String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
                mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected void initToolbarNoBack(String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
        }
    }

    protected void fitsSystemWindows() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= 21) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.transparent));
            }
        }
    }

    protected void fullscreen() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Default color is white
     */
    protected void statusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            window.setStatusBarColor(getResources().getColor(R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    protected void statusBarColor(int colorId, boolean light_status_bar) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23) {
            if (light_status_bar) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                window.setStatusBarColor(getResources().getColor(colorId));
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            window.setStatusBarColor(getResources().getColor(colorId));
            if (light_status_bar) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                int uiVisibility = window.getDecorView().getSystemUiVisibility();
                uiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.getDecorView().setSystemUiVisibility(uiVisibility);
            }
        }
    }
}