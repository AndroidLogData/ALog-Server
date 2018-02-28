package com.logdata.common.model;

public class CrashTimeVO {
    private String packageName;
    private String stringTime;

    public CrashTimeVO(String packageName, String stringTime) {
        this.packageName = packageName;
        this.stringTime = stringTime;
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
