package com.lyzb.jbx.model.me;

import java.io.Serializable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/15 10:13
 */

public class CompanyCircleTabModel implements Serializable {


    /**
     * funName : 成员动态
     * funId : 5
     * sort : 1
     * objectType : 2
     * funCode : member_topic
     */
    private String id;
    private String funName;

    /**
     * 5成员动态，6企业动态，7企业官网，8企业商城
     */
    private int funId;
    private int sort;
    private int objectType;
    private String funCode;
    /**
     * 操作id(名片记：user_id ；圈子记：圈子id)
     */
    private String objectId;
    /**
     * 状态（1：显示 0：隐藏）
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    /**
     * 5成员动态，6企业动态，7企业官网，8企业商城
     */
    public int getFunId() {
        return funId;
    }

    public void setFunId(int funId) {
        this.funId = funId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
