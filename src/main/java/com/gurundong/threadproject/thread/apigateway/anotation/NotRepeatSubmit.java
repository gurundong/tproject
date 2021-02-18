package com.gurundong.threadproject.thread.apigateway.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 防重复提交注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotRepeatSubmit {
    // 过期时间，单位毫秒，默认1秒
    long value() default 1000;
}
