package com.lyzb.jbx.model.me;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/5/10  10:35
 * Desc:
 */
public class TabShowHideBean implements Serializable {
    public String funName;//栏目名称
    //功能编码(名片：personal个人，topic动态，shop商城，news新闻 5 website官网)
    // ，（圈子：member_topic 成员动态,com_topic 企业动态 com_website 企业官网com_shop企业商城）
    public String funCode;
    public String funId;
    public String id;
    public String objectId;
    public String status = "1";//状态（1：显示 0：隐藏）
    public String updateTime;
    public int sort;
    public String objectType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getStatus() {
        if (TextUtils.isEmpty(status)) return "1";
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public TabShowHideBean(String funName) {
        this.funName = funName;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }


}
