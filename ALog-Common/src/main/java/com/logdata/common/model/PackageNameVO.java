package com.logdata.common.model;

import java.util.ArrayList;

public class PackageNameVO {
    private String id;
    private ArrayList<String> packageNameList;
    private String apiKey;

    public PackageNameVO() {
        packageNameList = new ArrayList<String>();
    }

    public PackageNameVO(String apiKey) {
        this.apiKey = apiKey;
        packageNameList = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getPackageNameList() {
        return packageNameList;
    }

    public void setPackageNameList(ArrayList<String> packageNameList) {
        this.packageNameList = packageNameList;
    }

    public void setPackageName(String packageName) {
        packageNameList.add(packageName);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
