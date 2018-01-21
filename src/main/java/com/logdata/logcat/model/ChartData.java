package com.logdata.logcat.model;

public class ChartData {
    private Long time;
    private int count;

    public ChartData(Long time) {
        this.time = time;
    }

    public ChartData(Long time, int count) {
        this.time = time;
        this.count = count;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
