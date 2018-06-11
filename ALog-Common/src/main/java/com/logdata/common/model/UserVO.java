package com.logdata.common.model;

import java.util.List;

public class UserVO {
    private String id;
    private String userID;
    private String password;
    private String apiKey;
    private List<UserRoles> roles;

    public UserVO() {}

    public UserVO(String userID, String password, List<UserRoles> userRoles, String apiKey) {
        this.userID = userID;
        this.password = password;
        this.roles = userRoles;
        this.apiKey = apiKey;
    }

    public UserVO(String userID, String password, List<UserRoles> userRoles) {
        this.userID = userID;
        this.password = password;
        this.roles = userRoles;
    }

    public UserVO(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoles> roles) {
        this.roles = roles;
    }
}
