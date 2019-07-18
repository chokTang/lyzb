package com.lyzb.jbx.model.me;

/**
 * @author wyx
 * @role TA的名片-点赞model
 * @time 2019 2019/3/23 13:36
 */

public class DoLikeModel {

    private String handleId;
    private int operStatus;
    private int operType;
    private int recordType;

    public String getHandleId() {
        return handleId;
    }

    public void setHandleId(String handleId) {
        this.handleId = handleId;
    }

    public int getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(int operStatus) {
        this.operStatus = operStatus;
    }

    public int getOperType() {
        return operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
