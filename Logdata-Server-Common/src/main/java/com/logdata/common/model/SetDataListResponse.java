package com.logdata.common.model;

import java.util.Set;

public class SetDataListResponse {
    private Set<String> setData;

    public SetDataListResponse(Set<String> setData) {
        this.setData = setData;
    }

    public Set<String> getLogData() {
        return setData;
    }
}
