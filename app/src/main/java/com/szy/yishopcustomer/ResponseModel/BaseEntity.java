package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by tiamo on 2015/7/24.
 */
public class BaseEntity{

    /**
     * code : 0
     * data :
     * message :
     */

    private int code;
    private String data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String redirect;
}
