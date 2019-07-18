package com.lyzb.jbx.model.dynamic;

import com.lyzb.jbx.model.common.VipModel;

import java.util.ArrayList;
import java.util.List;

public class DynamicScanModel {
    private String createMan;
    private String userName;
    private String headimg;
    private String companyInfo;
    private int browseNum;
    private List<VipModel> userActionVos;

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public int getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
    }

    public List<VipModel> getUserActionVos() {
        if (userActionVos == null) return new ArrayList<>();
        return userActionVos;
    }

    public void setUserActionVos(List<VipModel> userActionVos) {
        this.userActionVos = userActionVos;
    }
}
