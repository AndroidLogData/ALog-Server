package com.logdata.logcat.model;

import java.util.List;

public class LogDataListResponse {
    private List<LogVO> logData;

    public LogDataListResponse(List<LogVO> logData) {
        this.logData = logData;
    }

    public List<LogVO> getLogData() {
        return logData;
    }
}
