package com.logdata.logcat.model;

import java.util.List;

public class LogDataListResponse {
    private List<LogData> logData;

    public LogDataListResponse(List<LogData> logData) {
        this.logData = logData;
    }

    public List<LogData> getLogData() {
        return logData;
    }
}
