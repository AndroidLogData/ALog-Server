package com.logdata.common.model;

import java.util.Map;

public class LogVO {
    private String id;
    private String packageName;
    private String level;
    private String tag;
    private Object message;
    private long time;
    private Map<String, Object> memoryInfo;
    private Map<String, Object> deviceInfo;

    public LogVO() {
    }

    public LogVO(String packageName, String level, String tag, Object message, long time, Map<String, Object> memoryInfo, Map<String, Object> deviceInfo) {
        this.packageName = packageName;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.time = time;
        this.memoryInfo = memoryInfo;
        this.deviceInfo = deviceInfo;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, Object> getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(Map<String, Object> memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

    public Map<String, Object> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Map<String, Object> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return "{\"packageName\":\"" + getPackageName() + "\"," +
                "\"Level\":\"" + getLevel() + "\"," +
                "\"Tag\":\"" + getTag() + "\"," +
                "\"Message\":\"" + getMessage() + "\"," +
                "\"Time\":" + getTime() + "," +
                "\"MemoryInfo\":" + getMemoryInfo() +
                "\"DeviceInfo\":" + getDeviceInfo() + "}";
    }
}
