package com.gurundong.threadproject.thread.apigateway.util;

import com.gurundong.threadproject.thread.apigateway.anotation.NotRepeatSubmit;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApiUtil {

    /**
     * 按参数名升序排序参数
     * 入参：
     * {
     *     "typeCode": "ECS",
     *     "typeName": "服务器"
     * }
     * 出参： typeCode:ECS&typeName:服务器&
     * @return 参数升序字符串
     */
    public static String concatSignString(HttpServletRequest request){
        Map<String,String> paramterMap = new HashMap<>();
        request.getParameterMap().forEach((key,value) -> paramterMap.put(key,value[0]));

        // 按key升序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);

        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 忽略的字段
            if(k.equals("sign")){
                continue;
            }
            if(paramterMap.get(k).trim().length()>0){
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }

    public static String concatSignString(Map<String,String> map){
        Map<String,String> paramterMap = new HashMap<>();
        paramterMap = map;

        // 按key升序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);

        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 忽略的字段
            if(k.equals("sign")){
                continue;
            }
            if(paramterMap.get(k).trim().length()>0){
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }

    public static NotRepeatSubmit getNotRepeatSubmit(Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            NotRepeatSubmit annotation = method.getAnnotation(NotRepeatSubmit.class);

            return annotation;
        }
        return null;
    }
}
