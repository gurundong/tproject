package com.gurundong.threadproject.thread.apigateway.bean;

public class AppInfo {
    private String appId;
    private String key;

    public AppInfo() {
    }

    public AppInfo(String appId, String key) {
        this.appId = appId;
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
