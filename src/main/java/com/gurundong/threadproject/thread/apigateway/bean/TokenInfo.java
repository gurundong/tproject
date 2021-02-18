package com.gurundong.threadproject.thread.apigateway.bean;

public class TokenInfo {
    // api: 0 、 user: 1
    private Integer tokenType;
    private AppInfo appInfo;
    private UserInfo userInfo;

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
