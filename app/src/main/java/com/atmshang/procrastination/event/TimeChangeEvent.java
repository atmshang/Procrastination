package com.atmshang.procrastination.event;

/**
 * Created by atmshang on 2017/1/19.
 */

public class TimeChangeEvent {
    private long process;
    private long procrastinate;

    public TimeChangeEvent(long process, long procrastinate) {
        this.process = process;
        this.procrastinate = procrastinate;
    }

    public long getProcess() {
        return process;
    }

    public void setProcess(long process) {
        this.process = process;
    }

    public long getProcrastinate() {
        return procrastinate;
    }

    public void setProcrastinate(long procrastinate) {
        this.procrastinate = procrastinate;
    }
}
