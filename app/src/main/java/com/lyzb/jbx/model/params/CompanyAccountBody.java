package com.lyzb.jbx.model.params;

import java.io.Serializable;

public class CompanyAccountBody implements Serializable {
    private String companyId;
    private int status;
    private String position;
    private String userId;
    private String mobile;
    private String remark;
    private Boolean isUnbind;
    private Boolean bindCheck;
    private String versionCode;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getUnbind() {
        return isUnbind;
    }

    public void setUnbind(Boolean unbind) {
        isUnbind = unbind;
    }

    public Boolean getBindCheck() {
        return bindCheck;
    }

    public void setBindCheck(Boolean bindCheck) {
        this.bindCheck = bindCheck;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
