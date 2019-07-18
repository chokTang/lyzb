package com.lyzb.jbx.model.me;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/6/20  13:55
 * Desc: 名片中个人信息简介 model
 */
public class IntroductionContent implements Serializable {
    public String graphContent;//内容(content_type 为 1：文字 2：图片)
    public String contentType="1";//1：文字 2：图片
    public String belongType;//1:个人 2：企业
    public String defualtModular;//默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示 ）
    private String id;
    private String objectId;
    private String userExtId;
    private String modularId;
    private int sort;
    private String creatTime;
    private String modifyTime;
    private String operUserId;
    private int showState;

    public String getModularId() {
        if (null == modularId) return "";
        return modularId;
    }

    public void setModularId(String modularId) {
        this.modularId = modularId;
    }

    public String getGraphContent() {
        return graphContent;
    }

    public void setGraphContent(String graphContent) {
        this.graphContent = graphContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBelongType() {
        return belongType;
    }

    public void setBelongType(String belongType) {
        this.belongType = belongType;
    }

    public String getDefualtModular() {
        return defualtModular;
    }

    public void setDefualtModular(String defualtModular) {
        this.defualtModular = defualtModular;
    }

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

    public String getUserExtId() {
        return userExtId;
    }

    public void setUserExtId(String userExtId) {
        this.userExtId = userExtId;
    }


    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public int getShowState() {
        return showState;
    }

    public void setShowState(int showState) {
        this.showState = showState;
    }
}
