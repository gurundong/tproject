package com.gurundong.threadproject.thread.common.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriComponentsBuilderTest {
    public static void main(String[] args) {
        UriComponentsBuilderTest t = new UriComponentsBuilderTest();
        t.test01();
    }

    public void test01(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT,"13");
        httpHeaders.add(HttpHeaders.ACCEPT_CHARSET,"24");
        System.out.println(httpHeaders.toString());
    }
}
