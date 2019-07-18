package com.lyzb.jbx.model.params;

public class DeleteCompanyBody  {
    private String companyId;

    public DeleteCompanyBody(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
