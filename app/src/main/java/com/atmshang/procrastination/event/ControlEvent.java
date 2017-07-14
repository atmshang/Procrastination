package com.atmshang.procrastination.event;

import com.atmshang.procrastination.enums.TimerState;

/**
 * Created by atmshang on 2017/1/17.
 */

public class ControlEvent {
    private String name;
    private TimerState state;

    public ControlEvent(String name, TimerState state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public TimerState getState() {
        return state;
    }
}
