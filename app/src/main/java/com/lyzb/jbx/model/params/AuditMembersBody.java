package com.lyzb.jbx.model.params;

import com.lyzb.jbx.model.me.company.ChildrenBean;

import java.util.List;

public class AuditMembersBody {

    private String applyId;
    private int auditState;
    private List<ChildrenBean> memberRoles;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public List<ChildrenBean> getMemberRoles() {
        return memberRoles;
    }

    public void setMemberRoles(List<ChildrenBean> memberRoles) {
        this.memberRoles = memberRoles;
    }
}
