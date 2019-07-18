package com.lyzb.jbx.model.cenum;

import android.text.TextUtils;

/**
 * 我的-item实体
 * Created by Administrator on 2017/10/15.
 */

public class FunctionItemEnum {
    private String resId;
    private String type;
    private String xh;
    private String resLevel;
    private String code;
    private String enabled;
    private String orgType;
    private String class1;
    private String class2;//APP H5 WXMINI
    private String class3;
    private String protectedRes;
    private String display;
    private String pathWXMini;
    private String pathIos;
    private String pathAndorid;
    private String note;

    public String getPathWXMini() {
        return pathWXMini;
    }

    public String getPathIos() {
        return pathIos;
    }

    public String getPathAndorid() {
        return pathAndorid;
    }

    public String getNote() {
        return note;
    }

    public String getResId() {
        return resId;
    }

    public String getType() {
        return type;
    }

    public String getXh() {
        return xh;
    }

    public String getResLevel() {
        return resLevel;
    }

    public String getCode() {
        return code;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getOrgType() {
        return orgType;
    }

    public String getClass1() {
        return class1;
    }

    public String getClass2() {
        if (TextUtils.isEmpty(class1)) return "";
        return class2;
    }

    public String getClass3() {
        return class3;
    }

    public String getProtectedRes() {
        return protectedRes;
    }

    public String getDisplay() {
        return display;
    }
}
