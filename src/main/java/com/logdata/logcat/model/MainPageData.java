package com.logdata.logcat.model;

import org.joda.time.DateTime;

public class MainPageData {
    private String packageName;
    private int logDataCount;
    private DateTime recentCrashTime;

    public MainPageData(String packageName, int logDataCount, DateTime recentCrashTime) {
        this.packageName = packageName;
        this.logDataCount = logDataCount;
        this.recentCrashTime = recentCrashTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getLogDataCount() {
        return logDataCount;
    }

    public void setLogDataCount(int logDataCount) {
        this.logDataCount = logDataCount;
    }

    public DateTime getRecentCrashTime() {
        return recentCrashTime;
    }

    public void setRecentCrashTime(DateTime recentCrashTime) {
        this.recentCrashTime = recentCrashTime;
    }
}
