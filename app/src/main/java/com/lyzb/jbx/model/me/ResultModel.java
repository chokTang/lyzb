package com.lyzb.jbx.model.me;

/**
 * @author wyx
 * @role 部分 单独操作接口  固定返回model
 * @time 2019 2019/3/22 9:12
 */

public class ResultModel {

    private String msg;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
