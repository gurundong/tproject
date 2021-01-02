package com.gurundong.threadproject.thread.threadtest.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

public class HttpTest {
    public static Logger logger = LoggerFactory.getLogger(HttpTest.class);
    public static Logger serviceLogger = LoggerFactory.getLogger("serviceLog");

    public static void main(String[] args) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3600000);// 设置超时
        requestFactory.setReadTimeout(3600000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", authentication());
        HttpEntity<String> httpEntity = new HttpEntity<>("",headers);
        String url = "http://crmha.inspur.com/SAP/ZFMJSON/ZFM_CSG_GETSIGN_STATUS";
        ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
        String resBody = response.getBody();
        System.out.println(resBody);
    }

    private static String authentication() {
        String credentials = "RFCMOB" + ":" + "inspurcrm";
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

}

