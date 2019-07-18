package com.szy.yishopcustomer.Util;

import android.content.Context;

import com.like.utilslib.preference.PreferenceUtil;

/**
 * Created by Smart on 2017/9/4.
 */

public class SharedPreferencesHelper {

    private static Context mContext;

    private SharedPreferencesHelper() {
    }

    public static void initialize(Context context) {
        mContext = context;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        if (object instanceof String) {
            PreferenceUtil.getIntance().setString(key, (String) object);
        } else if (object instanceof Integer) {
            PreferenceUtil.getIntance().setInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            PreferenceUtil.getIntance().setBoolean(key, (Boolean) object);
        } else {
            PreferenceUtil.getIntance().saveObject(key, object);
        }
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return PreferenceUtil.getIntance().getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return PreferenceUtil.getIntance().getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return PreferenceUtil.getIntance().getBoolean(key, (Boolean) defaultObject);
        } else {
            return PreferenceUtil.getIntance().readObject(key);
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        PreferenceUtil.getIntance().remove(key);
    }

    /**
     * 清除所有的数据
     */
    public static void clear() {
        PreferenceUtil.getIntance().removePreference();
    }
}