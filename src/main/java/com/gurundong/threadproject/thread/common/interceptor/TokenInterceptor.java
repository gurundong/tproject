package com.gurundong.threadproject.thread.common.interceptor;

import com.gurundong.threadproject.thread.apigateway.anotation.NotRepeatSubmit;
import com.gurundong.threadproject.thread.apigateway.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String timestamp = request.getHeader("timestamp");
        // 随机字符串
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");


        // 防重复点击部分
        NotRepeatSubmit notRepeatSubmit = ApiUtil.
        // 请求时间间隔
        long requestInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
        Assert.isTrue(requestInterval < );





















    }
}
