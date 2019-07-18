package com.lyzb.jbx.model.params;

public class CampaignFollowBody {
    private String toUserId;
    private int enabled;

    public CampaignFollowBody(String toUserId, int enabled) {
        this.toUserId = toUserId;
        this.enabled = enabled;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
