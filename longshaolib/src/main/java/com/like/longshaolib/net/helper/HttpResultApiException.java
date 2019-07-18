package com.like.longshaolib.net.helper;

/**
 * 异常信息类处理，在HttpResultFunc处理数据请求成功之后，code的不一样
 * Created by longshao on 2017/3/20.
 */

public class HttpResultApiException extends RuntimeException {

    public static final int HTTP_CODE_SUCCESS = 200;//表示成功code码

    private static final int HTTP_CODE_ERROR_1 = 401;
    private static final int HTTP_CODE_ERROR_2 = 3;
    private static final int HTTP_CODE_ERROR_3 = 4;
    private static final int HTTP_CODE_ERROR_4 = 5;
    private static final int HTTP_CODE_ERROR_5 = 6;
    private static final int HTTP_CODE_ERROR_6 = 7;

    public HttpResultApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public HttpResultApiException(String detailMessage) {
        super(detailMessage);
    }

    //开发者查看
    private static String getApiExceptionMessage(int resultCode) {
        String message;
        switch (resultCode) {
            case HTTP_CODE_ERROR_1:
                message = "登陆超时";
                break;
            case HTTP_CODE_ERROR_2:
                message = "参数错误";
                break;
            case HTTP_CODE_ERROR_3:
                message = "后台代码异常";
                break;
            case HTTP_CODE_ERROR_4:
                message = "签名未通过";
                break;
            case HTTP_CODE_ERROR_5:
                message = "请求失败";
                break;
            case HTTP_CODE_ERROR_6:
                message = "token验证未通过";
                break;
            default:
                message = "服务器异常";
                break;
        }
        return message;
    }
}
