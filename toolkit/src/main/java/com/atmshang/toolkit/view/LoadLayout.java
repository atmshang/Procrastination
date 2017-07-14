package com.atmshang.toolkit.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atmshang.toolkit.R;

/**
 * LoadLayout
 * Created by Parents on 2017/1/10.
 */

public class LoadLayout extends FrameLayout {

    private View stateView, childView;

    private LinearLayout mLlLoading;
    private ProgressBar mPbLoading;
    private TextView mTvLoading;
    private LinearLayout mLlEmpty;
    private ImageView mIvEmpty;
    private TextView mTvEmpty;
    private TextView mTvReload;

    public LoadLayout(Context context) {
        super(context);
    }

    public LoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new IllegalStateException("LoadLayout only can host 1 elements");
        } else if (childCount == 1) {
            childView = getChildAt(0);
        }
        initView();
        super.onFinishInflate();
    }

    private void initView() {
        stateView = View.inflate(getContext(), R.layout.view_load, null);
        addView(stateView);
        mLlLoading = (LinearLayout) stateView.findViewById(R.id.ll_loading);
        mPbLoading = (ProgressBar) stateView.findViewById(R.id.pb_loading);
        mTvLoading = (TextView) stateView.findViewById(R.id.tv_loading);
        mLlEmpty = (LinearLayout) stateView.findViewById(R.id.ll_empty);
        mIvEmpty = (ImageView) stateView.findViewById(R.id.iv_empty);
        mTvEmpty = (TextView) stateView.findViewById(R.id.tv_empty);
        mTvReload = (TextView) stateView.findViewById(R.id.tv_reload);
    }

    public void setOnRefreshListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTvLoading.setVisibility(VISIBLE);
            mLlEmpty.setOnClickListener(onClickListener);
        }
    }

    public void dismiss() {
        stateView.setVisibility(GONE);
        if (childView != null) {
            childView.setVisibility(VISIBLE);
        }
    }

    public void loading() {
        mLlLoading.setVisibility(VISIBLE);
        mLlEmpty.setVisibility(GONE);
        stateView.setVisibility(VISIBLE);
        if (childView != null) {
            childView.setVisibility(GONE);
        }
    }

    public void empty() {
        mLlLoading.setVisibility(GONE);
        mLlEmpty.setVisibility(VISIBLE);
        stateView.setVisibility(VISIBLE);
        if (childView != null) {
            childView.setVisibility(GONE);
        }
    }
}
