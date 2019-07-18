package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.util.Log;

import com.baidu.speech.utils.MD5Util;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.yolanda.nohttp.IBasicRequest;
import com.yolanda.nohttp.rest.Request;

/**
 * @author wyx
 * @role 请求添加 头部信息
 * @time 2018 15:08
 */

public class RequestAddHead {


    /***
     * 添加Request 信息
     * @param mRequest
     * @param context
     * @param requesUrl
     * @param requesType GET  POST
     */
    public static void addHead(CommonRequest mRequest, Context context,String requesUrl, String requesType) {

        String timestamp = System.currentTimeMillis() + "";
        String uuid = java.util.UUID.randomUUID().toString();

        String token = (String) SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.getValue(), "");
        mRequest.setHeader("token", token);
        mRequest.setHeader("x-ca-key", "appScript");
        mRequest.setHeader("x-ca-timestamp", timestamp);
        mRequest.setHeader("x-ca-nonce", uuid);
        mRequest.setHeader("Accept", "*/*");
        mRequest.setHeader("Content-Type", "application/json; charset=utf-8");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(requesType).append("\n");
        stringBuffer.append(mRequest.headers().getValue("Accept", 0)).append("\n");
        stringBuffer.append(mRequest.headers().getValue("Content-Type", 0)).append("\n");
        stringBuffer.append("x-ca-nonce:" + uuid).append("\n");
        stringBuffer.append("x-ca-timestamp:" + timestamp).append("\n");
        stringBuffer.append(requesUrl.replace(Api.SAME_CITY_URL, ""));
        StringBuffer stringBuffer1 = new StringBuffer();

        //Log.d("nohttp1",stringBuffer.toString());

        stringBuffer1.append(MD5Util.toMd5(stringBuffer.toString().getBytes(), false)).append("$lw1XRJhQ#ys2q");
        mRequest.setHeader("x-ca-signature", MD5Util.toMd5(stringBuffer1.toString().getBytes(), false));
    }


    /***
     * nohttp(无封装)添加head
     * @param request
     * @param context
     * @param requesUrl
     * @param requesType
     */
    public static void addNoHttpHead(Request<String> request, Context context, String requesUrl, String requesType) {

        String timestamp = System.currentTimeMillis() + "";
        String uuid = java.util.UUID.randomUUID().toString();

        String token = (String) SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.getValue(), "");
        request.setHeader("token", token);
        request.setHeader("x-ca-key", "appScript");
        request.setHeader("x-ca-timestamp", timestamp);
        request.setHeader("x-ca-nonce", uuid);
        request.setHeader("Accept", "*/*");
        request.setHeader("Content-Type", "application/json; charset=utf-8");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(requesType).append("\n");
        stringBuffer.append(request.headers().getValue("Accept", 0)).append("\n");
        stringBuffer.append(request.headers().getValue("Content-Type", 0)).append("\n");
        stringBuffer.append("x-ca-nonce:" + uuid).append("\n");
        stringBuffer.append("x-ca-timestamp:" + timestamp).append("\n");
        stringBuffer.append(requesUrl.replace(Api.SAME_CITY_URL, ""));
        StringBuffer stringBuffer1 = new StringBuffer();

        //Log.d("nohttp2",stringBuffer.toString());

        stringBuffer1.append(MD5Util.toMd5(stringBuffer.toString().getBytes(), false)).append("$lw1XRJhQ#ys2q");
        request.setHeader("x-ca-signature", MD5Util.toMd5(stringBuffer1.toString().getBytes(), false));
    }

}
