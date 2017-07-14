package com.atmshang.procrastination.event;

/**
 * Created by atmshang on 2017/1/19.
 */

public class FreezeEvent {
    private boolean scrollAble = false;

    public FreezeEvent(boolean scrollAble) {
        this.scrollAble = scrollAble;
    }

    public boolean isScrollAble() {
        return scrollAble;
    }

    public void setScrollAble(boolean scrollAble) {
        this.scrollAble = scrollAble;
    }
}
