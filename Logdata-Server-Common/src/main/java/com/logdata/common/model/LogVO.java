package com.logdata.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "logData")
public class LogVO {
    @Id
    private String id;
    private String packageName;
    private String level;
    private String tag;
    private String message;
    private long time;
    private Map<String, Object> memoryInfo;
    private String apiKey;

    public LogVO() {
    }

    public LogVO(String packageName, String level, String tag, String message, long time, Map<String, Object> memoryInfo, String apiKey) {
        this.packageName = packageName;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.time = time;
        this.memoryInfo = memoryInfo;
        this.apiKey = apiKey;
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

    public Map<String, Object> getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(Map<String, Object> memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "{\"packageName\":\"" + getPackageName() + "\"," +
                "\"Level\":\"" + getLevel() + "\"," +
                "\"Tag\":\"" + getTag() + "\"," +
                "\"Message\":\"" + getMessage() + "\"," +
                "\"Time\":" + getTime() + "," +
                "\"MemoryInfo\":" + getMemoryInfo() + "," +
                "\"ApiKey\":\"" + getApiKey() + "\"" + "}";
    }
}
