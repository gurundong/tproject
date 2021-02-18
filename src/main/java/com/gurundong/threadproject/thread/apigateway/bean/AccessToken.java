package com.gurundong.threadproject.thread.apigateway.bean;

import java.util.Date;

public class AccessToken {
    private String token;

    private Date expireTime;

    public AccessToken() {
    }

    public AccessToken(String token, Date expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
