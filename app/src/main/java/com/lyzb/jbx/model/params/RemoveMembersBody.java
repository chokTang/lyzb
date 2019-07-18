package com.lyzb.jbx.model.params;

public class RemoveMembersBody {
    private String userId;
    private String companyId;
    private int removeType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getRemoveType() {
        return removeType;
    }

    public void setRemoveType(int removeType) {
        this.removeType = removeType;
    }
}
