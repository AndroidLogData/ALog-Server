package com.logdata.common.model;

public class CrashTimeVO {
    private long time;
    private String packageName;
    private String stringTime;

    public CrashTimeVO() {}

    public CrashTimeVO(long time, String packageName, String stringTime) {
        this.time = time;
        this.packageName = packageName;
        this.stringTime = stringTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStringTime() {
        return stringTime;
    }

    public void setStringTime(String stringTime) {
        this.stringTime = stringTime;
    }
}
