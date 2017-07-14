package com.atmshang.toolkit.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * FreezeViewPager
 * Created by atmshang on 2016/6/25.
 */
public class FreezeViewPager extends ViewPager {

    private boolean scrollAble = true;

    public boolean isScrollAble() {
        return scrollAble;
    }

    public void setScrollAble(boolean scrollAble) {
        this.scrollAble = scrollAble;
    }

    public FreezeViewPager(Context context) {
        super(context);
    }

    public FreezeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scrollAble ? super.onTouchEvent(event) : scrollAble;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return scrollAble ? super.onInterceptTouchEvent(event) : scrollAble;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
