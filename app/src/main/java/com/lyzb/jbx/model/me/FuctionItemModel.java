package com.lyzb.jbx.model.me;

import com.lyzb.jbx.model.cenum.FunctionItemEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-功能模块item实体
 */
public class FuctionItemModel {
    private String resId;
    private String type;
    private String xh;
    private String resLevel;
    private String code;
    private String enabled;
    private String orgType;
    private String class2;
    private String class3;
    private String protectedRes;
    private String display;
    private List<FunctionItemEnum> childResource;

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

    public String getClass2() {
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

    public List<FunctionItemEnum> getChildResource() {
        if (childResource == null) return new ArrayList<>();
        return childResource;
    }
}
