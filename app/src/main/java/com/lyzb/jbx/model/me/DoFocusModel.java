package com.lyzb.jbx.model.me;

/**
 * @author wyx
 * @role 关注TA的名片 model
 * @time 2019 2019/3/23 13:47
 */

public class DoFocusModel {

    private int enabled;        //1 关注 0 取消关注
    private String toUserId;

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
