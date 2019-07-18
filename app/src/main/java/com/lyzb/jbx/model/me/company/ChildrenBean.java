package com.lyzb.jbx.model.me.company;

import android.text.TextUtils;

import java.util.List;

public class ChildrenBean {

    /**
     * id : 588ca1daa6144afe96c4a92971049679
     * companyName : 6月测试新增企业_1_机构1-子(部门)
     * mParentOrgId : ec813bd3da56444d9c9ebbb8d58734f0
     */

    private String id;
    private String companyName;
    private String parentOrgId;
    private boolean selection;
    private boolean selection_Top;
    private String role = "2";
    private String orgName;
    private String orgId;
    private String roleId;
    private boolean noBranch = true;
    private List<ChildrenBean> children;

    public String getId() {
        return TextUtils.isEmpty(id) ? orgId : id;
    }

    public void setId(String id) {
        this.id = id;
        setOrgId(id);
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? orgName : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public boolean isSelection_Top() {
        return selection_Top;
    }

    public void setSelection_Top(boolean selection_Top) {
        this.selection_Top = selection_Top;
    }

    public String getRole() {
        return TextUtils.isEmpty(role) ? "2" : role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isNoBranch() {
        return noBranch;
    }

    public void setNoBranch(boolean noBranch) {
        this.noBranch = noBranch;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

}
