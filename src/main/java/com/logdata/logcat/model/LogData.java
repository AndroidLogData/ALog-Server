package com.logdata.logcat.model;

import org.springframework.data.annotation.Id;

public class LogData {
    @Id
    private String id;
    private String packageName;
    private String level;
    private String tag;
    private String message;
    private long time;
    private long totalMemory;
    private long availMemory;
    private double memoryPercentage;
    private boolean lowMemory;
    private long threshold;
    private int dalvikPss;
    private int nativePss;
    private int otherPss;
    private int totalPss;
    private String stringTime;

    public LogData() {
    }

    public LogData(String packageName, String level, String tag, String message, long time, long totalMemory,
                   long availMemory, double memoryPercentage, boolean lowMemory, long threshold, int dalvikPss,
                   int nativePss, int otherPss, int totalPss) {
        this.packageName = packageName;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.time = time;
        this.totalMemory = totalMemory;
        this.availMemory = availMemory;
        this.memoryPercentage = memoryPercentage;
        this.lowMemory = lowMemory;
        this.threshold = threshold;
        this.dalvikPss = dalvikPss;
        this.nativePss = nativePss;
        this.otherPss = otherPss;
        this.totalPss = totalPss;
    }

    public LogData(String packageName, String level, String tag, String message, long time) {
        this.packageName = packageName;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.time = time;
    }

    public LogData(String id, String packageName, String level, String tag, String message, long time, long totalMemory,
                   long availMemory, double memoryPercentage, boolean lowMemory, long threshold, int dalvikPss,
                   int nativePss, int otherPss, int totalPss) {
        this.id = id;
        this.packageName = packageName;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.time = time;
        this.totalMemory = totalMemory;
        this.availMemory = availMemory;
        this.memoryPercentage = memoryPercentage;
        this.lowMemory = lowMemory;
        this.threshold = threshold;
        this.dalvikPss = dalvikPss;
        this.nativePss = nativePss;
        this.otherPss = otherPss;
        this.totalPss = totalPss;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getAvailMemory() {
        return availMemory;
    }

    public void setAvailMemory(long availMemory) {
        this.availMemory = availMemory;
    }

    public double getMemoryPercentage() {
        return memoryPercentage;
    }

    public void setMemoryPercentage(double memoryPercentage) {
        this.memoryPercentage = memoryPercentage;
    }

    public boolean isLowMemory() {
        return lowMemory;
    }

    public void setLowMemory(boolean lowMemory) {
        this.lowMemory = lowMemory;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public int getDalvikPss() {
        return dalvikPss;
    }

    public void setDalvikPss(int dalvikPss) {
        this.dalvikPss = dalvikPss;
    }

    public int getNativePss() {
        return nativePss;
    }

    public void setNativePss(int nativePss) {
        this.nativePss = nativePss;
    }

    public int getOtherPss() {
        return otherPss;
    }

    public void setOtherPss(int otherPss) {
        this.otherPss = otherPss;
    }

    public int getTotalPss() {
        return totalPss;
    }

    public void setTotalPss(int totalPss) {
        this.totalPss = totalPss;
    }

    public String getStringTime() {
        return stringTime;
    }

    public void setStringTime(String stringTime) {
        this.stringTime = stringTime;
    }
}
