package com.lyzb.jbx.model.params;

import java.util.List;

public class ChangeAdminBody {
    private String orgId;
    private String userId;
    private String optType;

    private List<ChangeAdminBody> roleList;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public List<ChangeAdminBody> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ChangeAdminBody> roleList) {
        this.roleList = roleList;
    }
}
