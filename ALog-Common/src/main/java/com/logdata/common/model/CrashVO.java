package com.logdata.common.model;

import java.util.Map;

public class CrashVO {
    private String id;
    private String packageName;
    private long time;
    private String androidVersion;
    private String appVersionCode;
    private String appVersionName;
    private long availableMemorySize;
    private String brand;
    private String logcat;
    private String deviceID;
    private Map<String, Object> display;
    private Map<String, Object> environment;
    private Map<String, Object> deviceFeatures;
    private Map<String, Object> build;

    public CrashVO() {}

    public CrashVO(String packageName, long time, String androidVersion, String appVersionCode, String appVersionName,
                   long availableMemorySize, String brand, String logcat, String deviceID,
                   Map<String, Object> display, Map<String, Object> environment, Map<String, Object> deviceFeatures,
                   Map<String, Object> build) {
        this.packageName = packageName;
        this.time = time;
        this.androidVersion = androidVersion;
        this.appVersionCode = appVersionCode;
        this.appVersionName = appVersionName;
        this.availableMemorySize = availableMemorySize;
        this.brand = brand;
        this.logcat = logcat;
        this.deviceID = deviceID;
        this.display = display;
        this.environment = environment;
        this.deviceFeatures = deviceFeatures;
        this.build = build;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public long getAvailableMemorySize() {
        return availableMemorySize;
    }

    public void setAvailableMemorySize(long availableMemorySize) {
        this.availableMemorySize = availableMemorySize;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogcat() {
        return logcat;
    }

    public void setLogcat(String logcat) {
        this.logcat = logcat;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public Map<String, Object> getDisplay() {
        return display;
    }

    public void setDisplay(Map<String, Object> display) {
        this.display = display;
    }

    public Map<String, Object> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, Object> environment) {
        this.environment = environment;
    }

    public Map<String, Object> getDeviceFeatures() {
        return deviceFeatures;
    }

    public void setDeviceFeatures(Map<String, Object> deviceFeatures) {
        this.deviceFeatures = deviceFeatures;
    }

    public Map<String, Object> getBuild() {
        return build;
    }

    public void setBuild(Map<String, Object> build) {
        this.build = build;
    }

    @Override
    public String toString() {
        return "{\"packageName\":\"" + getPackageName() + "\"," +
                "\"time\":" + getTime() + "," +
                "\"androidVersion\":\"" + getAndroidVersion() + "\"," +
                "\"appVersionCode\":\"" + getAppVersionCode() + "\"," +
                "\"appVersionName\":\"" + getAppVersionName() + "\"," +
                "\"availableMemorySize\":" + getAvailableMemorySize() + "," +
                "\"brand\":\"" + getBrand() + "\"," +
                "\"logcat\":\"" + getLogcat() + "\"," +
                "\"deviceID\":\"" + getDeviceID() + "\"," +
                "\"display\":" + getDisplay() + "," +
                "\"environment\":" + getEnvironment() + "," +
                "\"deviceFeatures\":" + getDeviceFeatures() + "," +
                "\"build\":" + getBuild() + "}";
    }
}