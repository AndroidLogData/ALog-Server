package com.logdata.logcat.model;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class CrashData {
    @Id
    public String id;
    public long time;
    public String androidVersion;
    public String appVersionCode;
    public String appVersionName;
    public long availableMemorySize;
    public String brand;
    public String logcat;
    public String deviceID;
    public Map<String, Object> display;
    public Map<String, Object> environment;
    public Map<String, Object> deviceFeatures;
    public Map<String, Object> build;

    public CrashData() {}

    public CrashData(long time, String androidVersion, String appVersionCode, String appVersionName,
                     long availableMemorySize, String brand, String logcat, String deviceID,
                     Map<String, Object> display, Map<String, Object> environment, Map<String, Object> deviceFeatures,
                     Map<String, Object> build) {
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

    public CrashData(long time, String androidVersion, String appVersionCode, String appVersionName,
                     long availableMemorySize, String brand, String logcat, String deviceID, Map<String, Object> display,
                     Map<String, Object> environment, Map<String, Object> build) {
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
        this.build = build;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}