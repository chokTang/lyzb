package com.lyzb.jbx.model.me.company;

/**
 * Created by :TYK
 * Date: 2019/6/25  20:23
 * Desc:
 */
public class UpdateCompanyMsg {

    /**
     * msg : 创建机构成功
     * code : 200
     * orgId : 84be0fadf0b94bedbab126862eb31349
     */

    private String msg;
    private int code;
    private String orgId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
