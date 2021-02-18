package com.gurundong.threadproject.thread.apigateway.bean;

import com.gurundong.threadproject.thread.apigateway.util.ApiUtil;
import com.gurundong.threadproject.thread.common.utils.MD5Util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    // 签名
    private String sign;

    public static <T> ApiResponse success(T data) {
        return response(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data);
    }

    public static ApiResponse error(String code, String msg) {
        return response(code, msg, null);
    }

    // 返回的结果中带有sign
    // 使用结果前需要先校验sign
    public static <T> ApiResponse response(String code, String msg, T data) {
        ApiResponse response = new ApiResponse();
        response.setCode(code);
        response.setMessage(msg);
        String sign = signData(data);
        response.setSign(sign);

        return response;
    }

    // (参数升序 + key) 签名
    public static <T> String signData(T data) {
        // TODO
        String key = "112233";
        Map<String,String> dataMap = null;
        try{
            dataMap = getFields(data);
        }catch (Exception e){
            return null;
        }
        String contractStr = ApiUtil.concatSignString(dataMap);
        String signature = contractStr + "key = " + key;
        String sign = MD5Util.MD5Upper(signature);
        return sign;
    }

    /**
     *      * @param data 反射的对象,获取对象的字段名和值
     *      * @throws IllegalArgumentException
     *      * @throws IllegalAccessException
     *      
     */
    public static Map<String, String> getFields(Object data) throws IllegalAccessException, IllegalArgumentException {
        if (data == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        Field[] fields = data.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(data);
            if (field.get(data) != null) {
                map.put(name, value.toString());
            }
        }
        return map;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
