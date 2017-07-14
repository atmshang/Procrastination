package com.atmshang.toolkit.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * RevealLayout
 * Created by atmshang on 2017/1/16.
 */

public class RevealLayout extends FrameLayout {

    private View unCheckView, checkedView;
    private int revealX, revealY;
    private boolean checked = false;
    private Animator animReveal;
    private Animation animFade;
    private onClickEvent onClickEvent;
    private FrameLayout innerFrameLayout;

    public RevealLayout(Context context) {
        this(context, null);
    }

    public RevealLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public interface onClickEvent {
        void onClick();
    }

    public void setOnClickEvent(RevealLayout.onClickEvent onClickEvent) {
        this.onClickEvent = onClickEvent;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked, boolean showAnim) {
        if (this.checked != checked) {
            this.checked = checked;
            revealAnim(showAnim);
        }
    }

    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            revealAnim(true);
        }
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount < 2) {
            throw new IllegalStateException("RevealLayout only can host 2+ elements");
        } else {
            unCheckView = getChildAt(0);
            checkedView = getChildAt(1);
            innerFrameLayout = new FrameLayout(getContext());
            innerFrameLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            removeView(unCheckView);
            removeView(checkedView);
            ArrayList<View> mList = new ArrayList<>();
            for (int i = 0; i < getChildCount(); i++) {
                mList.add(getChildAt(i));
            }
            for (View view : mList) {
                removeView(view);
            }
            addView(innerFrameLayout);
            for (View view : mList) {
                addView(view);
            }
            innerFrameLayout.addView(unCheckView);
            innerFrameLayout.addView(checkedView);
        }
        initView();
        super.onFinishInflate();
    }

    private void initView() {
        unCheckView.setVisibility(isChecked() ? INVISIBLE : VISIBLE);
        checkedView.setVisibility(isChecked() ? VISIBLE : INVISIBLE);
        animFade = new AlphaAnimation(0f, 1f);
        animFade.setInterpolator(new LinearInterpolator());
        animFade.setDuration(250);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                revealX = (int) event.getX();
                revealY = (int) event.getY();
                return false;
            }
        });
    }

    private void revealAnim(boolean showAnim) {
        if (showAnim) {
            if (isChecked()) {
                revealShow(checkedView);
            } else {
                fadeShow(unCheckView);
            }
        } else {
            unCheckView.setVisibility(isChecked() ? INVISIBLE : VISIBLE);
            checkedView.setVisibility(isChecked() ? VISIBLE : INVISIBLE);
        }

    }

    private void revealShow(@NonNull View mView) {
        innerFrameLayout.removeView(mView);
        innerFrameLayout.addView(mView);
        if (animReveal != null) {
            animReveal.end();
        }
        if (animFade != null) {
            animFade.cancel();
        }
        int finalRadius = Math.max(mView.getWidth(), mView.getHeight());
        animReveal = ViewAnimationUtils.createCircularReveal(mView, revealX, revealY, 0, finalRadius);
        animReveal.setInterpolator(new LinearInterpolator());
        animReveal.setDuration(500);
        mView.setVisibility(View.VISIBLE);
        animReveal.start();
    }

    private void fadeShow(@NonNull View mView) {
        innerFrameLayout.removeView(mView);
        innerFrameLayout.addView(mView);
        if (animFade != null) {
            animFade.cancel();
        }
        if (animReveal != null) {
            animReveal.end();
        }
        mView.setVisibility(VISIBLE);
        mView.startAnimation(animFade);
    }
}
