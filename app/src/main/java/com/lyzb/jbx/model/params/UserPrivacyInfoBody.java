package com.lyzb.jbx.model.params;

public class UserPrivacyInfoBody {
    private String showInfo;

    public UserPrivacyInfoBody(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }
}
