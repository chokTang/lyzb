package com.lyzb.jbx.model.params;

import com.lyzb.jbx.model.me.company.ChildrenBean;

import java.util.List;

public class AddStaffBody {
    private String userId;
    private String prefix;
    private String account;
    private String fullName;
    private String mobile;
    private String optType;
    private String orgId;
    private int status;
    private String pwd;
    private List<ChildrenBean> memberRoles;

    public String getUserId() {
        return userId;
    }

    /**
     * 生成账号返回的userId，没有就不传。修改必传
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrefix() {
        return prefix;
    }

    /**
     * 前缀（机构有前缀会返回，没有手动填写）必传
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAccount() {
        return account;
    }

    /**
     * 账号（如果有生成，回传；没有则不传）
     */
    public void setAccount(String account) {
        this.account = account;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * 姓名
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    /**
     * 电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOptType() {
        return optType;
    }

    /**
     * 操作方式（add：新增；upt：修改
     */
    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getOrgId() {
        return orgId;
    }

    /**
     * 当前操作的机构id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 登录状态(0 禁用 1启用) 修改时传
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public String getPwd() {
        return pwd;
    }

    /**
     * 密码（用户填写后调用PHP接口：www.jibaoh.com/user/security/build-password 生成加密结果后传参）
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<ChildrenBean> getMemberRoles() {
        return memberRoles;
    }

    /**
     * 机构角色集
     */
    public void setMemberRoles(List<ChildrenBean> memberRoles) {
        this.memberRoles = memberRoles;
    }
}
