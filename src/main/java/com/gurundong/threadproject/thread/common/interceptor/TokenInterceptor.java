package com.gurundong.threadproject.thread.common.interceptor;

import com.gurundong.threadproject.thread.apigateway.anotation.NotRepeatSubmit;
import com.gurundong.threadproject.thread.apigateway.bean.TokenInfo;
import com.gurundong.threadproject.thread.apigateway.util.ApiUtil;
import com.gurundong.threadproject.thread.common.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

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
        NotRepeatSubmit notRepeatSubmit = ApiUtil.getNotRepeatSubmit(handler);
        // 如果设置了，则时间间隔为默认1秒，否则默认5小时(相当于没设置)
        long expireTime = notRepeatSubmit == null ? 5 * 60 * 1000 : notRepeatSubmit.value();
        // 请求时间间隔
        long requestInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
        // 如果当前时间-请求时间戳 > 默认规定防重点时长，则超时报错。
        Assert.isTrue(requestInterval < expireTime,"请求超时，请重新请求");
        // 如果注解存在，则过期时间内，不能再次点击。 通过redis的expire key实现。
        if (notRepeatSubmit != null) {
            ValueOperations<String, Integer> signRedis = redisTemplate.opsForValue();
            boolean exists = redisTemplate.hasKey(sign);
            Assert.isTrue(!exists, "请勿重复提交");
            signRedis.set(sign, 0, expireTime, TimeUnit.MILLISECONDS);
        }

        // 校验Token是否存在，必须加token
        ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
        TokenInfo tokenInfo = tokenRedis.get(token);
        Assert.notNull(tokenInfo, "token错误");

        // 判断签名是否正确。   参数字符串 + app key + token + timestamp + nonce
        String signString = ApiUtil.concatSignString(request) + tokenInfo.getAppInfo().getKey() + token + timestamp + nonce;
        String signature = MD5Util.MD5Upper(signString);
        boolean flag = signature.equals(sign);
        Assert.isTrue(flag, "签名错误");

        return super.preHandle(request, response, handler);
    }
}
