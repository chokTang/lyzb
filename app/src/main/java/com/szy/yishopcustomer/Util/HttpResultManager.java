package com.szy.yishopcustomer.Util;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.parser.Feature;
import com.like.utilslib.other.LogUtil;
import com.like.utilslib.other.ToastUtil;
import com.szy.common.Util.CommonUtils;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MallStatusActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Smart on 2017/5/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class HttpResultManager {
    public static final int CODE_STATE_SUCCESS = 0;
    public static final int CODE_STATE_FAILURE = -1;
    public static final int CODE_STATE_NOLOGIN = 99;
    public static final int CODE_STATE_TRIAL_VERSION = 68;
    public static final int CODE_STATE_BALANCE_PASSOWRD = 112;
    public static final int CODE_STATE_NOT_SUPPORTED = 109;
    public static final int CODE_STATE_MALL_CLOSED = 98;
    public static final int CODE_STATE_MALL_UPDATE = 97;
    public static String customTagPrefix = "x_log";

    /**
     * 网络数据请求统一处理
     *
     * @param result
     * @param clazz
     * @param callback
     * @param <T>
     */
    public static <T> void resolve(String result, Class<T> clazz, HttpResultCallBack<T> callback) {
        resolve(result, clazz, callback, false);
    }


    /**
     * 网络数据请求统一处理
     *
     * @param result
     * @param clazz
     * @param callback
     * @param empty    这个参数用于在onSuccess中没有使用data数据，那么传true，默认可能导致走empty方法
     * @param <T>
     */
    public static <T> void resolve(String result, Class<T> clazz, HttpResultCallBack<T> callback, boolean empty) {
        T obj = com.alibaba.fastjson.JSON.parseObject(result, clazz, Feature.OrderedField);
        resolve(obj, callback, empty);
    }


//    public static <T> void resolve(String result,HttpResultCallBack<T> callback,boolean empty) {
//        try {
//            T obj = com.alibaba.fastjson.JSON.parseObject(result,new TypeReference<T>(){});
//
//            resolve(obj,callback,empty);
//
//        } catch (Exception e) {
//            callback.onFailure(e.getMessage());
//        }
//    }
//
//    public static <T> void resolve(String result,HttpResultCallBack<T> callback) {
//        try {
//            T obj = com.alibaba.fastjson.JSON.parseObject(result,new TypeReference<T>(){});
//
//            resolve(obj,callback,false);
//
//        } catch (Exception e) {
//            callback.onFailure("" + e);
//        }
//    }

    /**
     * @param obj      json解析后的模型类
     * @param callback 回调接口
     * @param empty    是否判断data是否为空，true可以为空，不走empty方法
     * @param <T>
     */
    public static <T> void resolve(T obj, HttpResultCallBack<T> callback, boolean empty) {
        if (obj == null) {
            callback.onEmptyData(HttpResultCallBack.EMPTY_STATE_PARENT);
            return;
        }
        int code = getCode("code", obj);
        Object data = getFieldValueByName("data", obj);
        String message = getFieldValueByName("message", obj) == null ? "" : (String)
                getFieldValueByName("message", obj);

        Set<String> packages = new HashSet<>();
        packages.add("ResponseModel");
        packages.add("ViewModel");
        //这个处理之后data就永远不为空了
        try {
            CommonUtils.instantiateFieldsOfObject(obj, packages);
        } catch (Exception e) {

        }

        callback.getObj(obj);
        switch (code) {
            //正常
            case CODE_STATE_SUCCESS:
                if (data == null && !empty) {//empty是未使用success数据
                    callback.onEmptyData(HttpResultCallBack.EMPTY_STATE_INSIDE);
                    return;
                } else {
                    callback.onSuccess(obj);
                }
                break;
            //错误
            case CODE_STATE_FAILURE:
                callback.onFailure(message);
                break;
            //没有登录或者登陆过期
            case CODE_STATE_NOLOGIN:
                callback.onLogin();
                break;
            case CODE_STATE_BALANCE_PASSOWRD:
                callback.onFailure(message);
                break;
            //试用版本
            case CODE_STATE_TRIAL_VERSION:
                Toast.makeText(App.getInstance().mContext, "试用版本！", Toast.LENGTH_SHORT).show();
                break;
            case CODE_STATE_NOT_SUPPORTED:
                callback.onFailure(message);
                break;
            case CODE_STATE_MALL_CLOSED:
                callback.onMallClosed();
                break;
            case CODE_STATE_MALL_UPDATE:
                callback.onMallUpdate();
                break;
            default:
                callback.ohter(code, message);
                break;
        }
    }


    /**
     * 因为有的data是string类型的，判断一下
     *
     * @param fieldName
     * @param obj
     * @return
     */
    private static int getCode(String fieldName, Object obj) {
        int code = -1;
        Object cobj = getFieldValueByName(fieldName, obj);
        if (cobj != null) {

            if (cobj instanceof String) {
                code = Integer.parseInt(String.valueOf(cobj));
            }

            if (cobj instanceof Integer) {
                code = (int) cobj;
            }
        }
        return code;
    }

    public static Object getFieldValueByName(String fieldName, Object obj) {
        try {
            Class cls = obj.getClass();
            Field[] fs = cls.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true);
                //这个值是我要获取的
                if (f.getName().equals(fieldName)) {
                    return f.get(obj);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }


    public abstract static class HttpResultCallBack<T> {

        /**
         * 空数据状态，外部的类都没转换成功
         */
        public static final int EMPTY_STATE_PARENT = 0;
        /**
         * 空数据状态，类里面的data数据是空的
         */
        public static final int EMPTY_STATE_INSIDE = 1;

        public abstract void onSuccess(T back);

        //可以从任何方法中获取转换后的模型类
        public void getObj(T back) {


        }

        //可以从任何方法中获取原始的json数据
        public void getResult(String result) {

        }

        //非必须，可重写
        public void onFailure(String message) {
            if (TextUtils.isEmpty(message)) {
                Toast.makeText(App.getInstance().mContext, "加载数据失败,请检查网络连接" + message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(App.getInstance().mContext, message, Toast.LENGTH_LONG).show();
            }
        }

        //非必须，可重写
        public void onLogin() {
//            String tag = generateTag();
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(App.getInstance().mContext, LoginActivity.class);
            App.getInstance().mContext.startActivity(intent);
        }

        //空数据
        public void onEmptyData(int state) {
            Toast.makeText(App.getInstance().mContext, "数据为空！" + state, Toast.LENGTH_SHORT).show();
        }

        //商城关闭
        public void onMallClosed() {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(App.getInstance().mContext, MallStatusActivity.class);
            intent.putExtra(Key.KEY_ACT_CODE.getValue(), CODE_STATE_MALL_CLOSED);
            App.getInstance().mContext.startActivity(intent);
            Toast.makeText(App.getInstance().mContext, "商城关闭", Toast.LENGTH_SHORT).show();
        }

        //商城升级
        public void onMallUpdate() {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(App.getInstance().mContext, MallStatusActivity.class);
            intent.putExtra(Key.KEY_ACT_CODE.getValue(), CODE_STATE_MALL_UPDATE);
            App.getInstance().mContext.startActivity(intent);
            Toast.makeText(App.getInstance().mContext, "商城升级", Toast.LENGTH_SHORT).show();
        }

        public void ohter(int code, String message) {
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(App.getInstance().mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public interface OnLoginForResult {
        void onAction();
    }
//使用
    //    HttpResultManager.OnLoginForResult onLoginForResult = new HttpResultManager.OnLoginForResult(){
//
//        @Override
//        public void onAction() {
//
//        }
//    };
//
//    onLoginForResult.onAction();


}
