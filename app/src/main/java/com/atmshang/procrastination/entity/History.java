package com.atmshang.procrastination.entity;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by atmshang on 2017/1/16.
 */

public class History extends RealmObject implements Serializable {
    private String name;
    private boolean header;
    private long dayTimestamp;
    private long createTimestamp;
    private long finishTimestamp;
    private long process;
    private long procrastinate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public long getDayTimestamp() {
        return dayTimestamp;
    }

    public void setDayTimestamp(long dayTimestamp) {
        this.dayTimestamp = dayTimestamp;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(long finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
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
