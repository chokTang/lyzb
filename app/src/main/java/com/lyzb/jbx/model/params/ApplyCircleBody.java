package com.lyzb.jbx.model.params;

/**
 * 申请加入圈子model
 */
public class ApplyCircleBody {
    private String groupId;

    public ApplyCircleBody(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
