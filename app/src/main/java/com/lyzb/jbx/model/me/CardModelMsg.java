package com.lyzb.jbx.model.me;

/**
 * Created by :TYK
 * Date: 2019/6/21  11:39
 * Desc:
 */
public class CardModelMsg {

    /**
     * data : {"pageNum":1,"extPageSize":30,"childPageSize":1,"id":50,"modularName":"个人的","objectId":"245300","userExtId":15576,"belongType":1,"createTime":"2019-06-21 11:31:13","modifyTime":"2019-06-21 11:31:13","sort":0,"showState":1,"operUserId":245300}
     * modId : 50
     * status : 200
     */

    private CustomModular data;
    private String modId;
    private String status;
    private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomModular getData() {
        return data;
    }

    public void setData(CustomModular data) {
        this.data = data;
    }
}
