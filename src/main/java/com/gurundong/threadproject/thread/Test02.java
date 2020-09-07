package com.gurundong.threadproject.thread;

import com.gurundong.threadproject.thread.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Test02 {
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/test02/test01")
    public void test01(){

        redisUtil.set("grdtest01","111");
        System.out.println(redisUtil.get("grdtest01"));
    }
}