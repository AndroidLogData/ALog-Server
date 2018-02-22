package com.logdata.backend.model;

import java.util.List;

public class MainPageDataListResponse {
    private List<MainPageVO> mainData;

    public MainPageDataListResponse(List<MainPageVO> mainData) {
        this.mainData = mainData;
    }

    public List<MainPageVO> getLogData() {
        return mainData;
    }
}
