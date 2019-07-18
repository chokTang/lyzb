package com.like.longshaolib.net.model;

/**
 * 网络实体基础类
 * 注：这个需要根据后台的数据来封装
 * Created by longshao on 2017/3/20.
 */

public class HttpResult<T> {

    public static final String DATA_KEY = "data";
    public static final String DATA_CODE = "code";
    public static final String DATA_MESSAGE = "message";

    private int code;//请求码

    private String message;//请求错误信息

    private T data;//请求返回的实体

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}
