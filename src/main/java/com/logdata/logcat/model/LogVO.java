package com.logdata.logcat.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logData")
public class LogVO {
    @Id
    private String id;
    private String packageName;
    private String level;
    private String tag;
    private String message;
    private DateTime time;
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
    private String apiKey;

    public LogVO() {
    }

    public LogVO(String packageName, String level, String tag, String message, DateTime time, long totalMemory,
                 long availMemory, double memoryPercentage, boolean lowMemory, long threshold, int dalvikPss,
                 int nativePss, int otherPss, int totalPss, String apiKey) {
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
        this.apiKey = apiKey;
    }

    public LogVO(String packageName, String level, String tag, String message, DateTime time, String apiKey) {
        this.packageName = packageName;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.time = time;
        this.apiKey = apiKey;
    }

    public LogVO(String id, String packageName, String level, String tag, String message, DateTime time, long totalMemory,
                 long availMemory, double memoryPercentage, boolean lowMemory, long threshold, int dalvikPss,
                 int nativePss, int otherPss, int totalPss, String apiKey) {
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
        this.apiKey = apiKey;
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

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
