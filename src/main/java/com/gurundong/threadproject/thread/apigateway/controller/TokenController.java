package com.gurundong.threadproject.thread.apigateway.controller;

import com.gurundong.threadproject.thread.apigateway.anotation.NotRepeatSubmit;
import com.gurundong.threadproject.thread.apigateway.bean.*;
import com.gurundong.threadproject.thread.common.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private RedisTemplate redisTemplate;


    // 获取 密钥型 token
    public ApiResponse<AccessToken> apiToken(String appId, @RequestHeader("timestamp") String timestamp, @RequestHeader("sign") String sign) {
        Assert.isTrue(!StringUtils.isEmpty(appId)  && !StringUtils.isEmpty(sign), "参数错误");

        // 实际情况 根据appId查询数据库获取appSecret
        AppInfo appInfo = new AppInfo("1", "12345678954556");
        // 时间戳 + appid + app key
        String signString = timestamp + appId + appInfo.getKey();
        String signature = MD5Util.MD5Upper(signString);
        Assert.isTrue(signature.equals(sign), "签名错误");

        AccessToken accessToken = this.saveToken(0, appInfo, null);

        return ApiResponse.success(accessToken);
    }

    @NotRepeatSubmit(5000)
    @PostMapping("user_token")
    // 获取用户型token
    public ApiResponse<UserInfo> userToken(String username, String password) {
        // 根据用户名查询密码, 并比较密码(密码可以RSA加密一下)
        UserInfo userInfo = new UserInfo();
        // 校验用户密码

        // 2. 保存Token
        AppInfo appInfo = new AppInfo("1", "12345678954556");
        AccessToken accessToken = this.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);
    }

    // 保存token到redis
    private AccessToken saveToken(int tokenType, AppInfo appInfo,  UserInfo userInfo) {
        String token = UUID.randomUUID().toString();

        // token有效期为2小时 expireTime
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();

        // 4. 保存token
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);

        // 用户型 token
        if (tokenType == 1) {
            tokenInfo.setUserInfo(userInfo);
        }

        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);

        AccessToken accessToken = new AccessToken(token, expireTime);

        return accessToken;
    }
}
