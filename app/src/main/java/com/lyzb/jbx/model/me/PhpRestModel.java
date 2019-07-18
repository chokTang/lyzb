package com.lyzb.jbx.model.me;

/**
 * @author wyx
 * @role php 接口返回  单向操作 返回model
 * @time 2019 2019/3/22 9:12
 */

public class PhpRestModel {

    private int code;
    private String message;

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
}
