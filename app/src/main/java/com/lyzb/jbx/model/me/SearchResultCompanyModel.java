package com.lyzb.jbx.model.me;

/**
 * Created by :TYK
 * Date: 2019/4/10  11:16
 * Desc:
 */
public class SearchResultCompanyModel {


    /**
     * id : 083027C8-2093-4524-BDC6-C6EF511CD134
     * companyName : 礼仪之邦-LC1的店铺
     */

    private String id;
    private String companyName;
    private String creatorName;
    private String industryName;
    private int membersNum;
    private int applyState;
    private String distributorLogo;
    private String groupId;

    public String getDistributorLogo() {
        return distributorLogo;
    }

    public void setDistributorLogo(String distributorLogo) {
        this.distributorLogo = distributorLogo;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getMembersNum() {
        return membersNum;
    }

    public void setMembersNum(int membersNum) {
        this.membersNum = membersNum;
    }

    public int getApplyState() {
        return applyState;
    }

    public void setApplyState(int applyState) {
        this.applyState = applyState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
