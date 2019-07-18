package com.lyzb.jbx.model.msg;

/**
 * Created by :TYK
 * Date: 2019/6/28  10:36
 * Desc:
 */
public class EventBusMsg {
    public String msg;
    public String companyId;
    public int type;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
