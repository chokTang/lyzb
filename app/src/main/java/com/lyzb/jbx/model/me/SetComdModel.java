package com.lyzb.jbx.model.me;

/**
 * @author wyx
 * @role 设置 默认企业
 * @time 2019 2019/3/8 11:37
 */

public class SetComdModel {

    private String companyId;

    public SetComdModel(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
