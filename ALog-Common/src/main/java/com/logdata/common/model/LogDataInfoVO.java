package com.logdata.common.model;

import java.util.HashMap;

public class LogDataInfoVO {
    private String packageName;
    private String recentCrashTime;
    private int verb;
    private int info;
    private int debug;
    private int warning;
    private int error;
    private HashMap<String, Integer> logCount;

    public LogDataInfoVO() {}

    public LogDataInfoVO(String packageName, String recentCrashTime, int verb, int info, int debug, int warning, int error) {
        this.packageName = packageName;
        this.recentCrashTime = recentCrashTime;
        this.verb = verb;
        this.info = info;
        this.debug = debug;
        this.warning = warning;
        this.error = error;
    }

    public LogDataInfoVO(String packageName, String recentCrashTime, HashMap<String, Integer> logCount) {
        this.packageName = packageName;
        this.recentCrashTime = recentCrashTime;
        this.logCount = logCount;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRecentCrashTime() {
        return recentCrashTime;
    }

    public void setRecentCrashTime(String recentCrashTime) {
        this.recentCrashTime = recentCrashTime;
    }

    public int getVerb() {
        return verb;
    }

    public void setVerb(int verb) {
        this.verb = verb;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public HashMap<String, Integer> getLogCount() {
        return logCount;
    }

    public void setLogCount(HashMap<String, Integer> logCount) {
        this.logCount = logCount;
    }

    @Override
    public String toString() {
        return "{\"packageName\":\"" + getPackageName() + "\"," +
                "\"RecentCrashTime\":\"" + getRecentCrashTime() + "\"," +
                "\"Verb\":" + getVerb() + "," +
                "\"Info\":" + getInfo() + "," +
                "\"Debug\":" + getDebug() + "," +
                "\"Warning\":" + getWarning() + "," +
                "\"Error\":" + getError() + "}";
    }
}
