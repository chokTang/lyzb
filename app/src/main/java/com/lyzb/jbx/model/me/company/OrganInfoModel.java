package com.lyzb.jbx.model.me.company;

public class OrganInfoModel {

    /**
     * industryName : 快速消费品
     * mapLng : 0.000000
     * companyName : 6月测试新增企业_1_机构1(公司)
     * mapLat : 0.0000
     * disTel : 123123123
     * parentOrgName : 6月测试新增企业_1
     * parentType : 2
     * orgType : 100
     * industryId : 5
     * mParentOrgId : ef9e9cb75fa44c98a2437c48b4bacce6
     * chargePerson : xxxx
     * logoFilePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/04/01/5B587BC1-2498-4010-AAB2-3DA80FEE5446.jpeg
     * id : 84be0fadf0b94bedbab126862eb31349
     * disAddress : 重庆
     * isEditCharge : false
     */

    /**
     * 企业类型
     */
    private String industryName;
    /**
     * 经度
     */
    private String mapLng;
    /**
     * 机构名称
     */
    private String companyName;
    /**
     * 纬度
     */
    private String mapLat;
    /**
     * 电话
     */
    private String disTel;
    /**
     * 上级机构名称
     */
    private String parentOrgName;
    /**
     * 是否是顶级机构（1.是 2.够）
     */
    private String parentType;
    /**
     * 机构类型：101：部门 其他：企业
     */
    private String orgType;
    /**
     * 企业类型id
     */
    private String industryId;
    /**
     * 上级机构id
     */
    private String parentOrgId;
    /**
     * 机构负责人
     */
    private String chargePerson;
    /**
     * 机构负责人id
     */
    private String chargePersonId;
    /**
     * logo
     */
    private String logoFilePath;
    /**
     * 机构id
     */
    private String id;
    /**
     * 企业地址
     */
    private String disAddress;
    /**
     * 能否修改负责人
     */
    private boolean isEditCharge;

    /**
     * 负责人id
     */
    private String membershipId;

    /**
     * 能否编辑企业名称和地址（1.能 2.否）
     */
    private String canEdit;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getMapLng() {
        return mapLng;
    }

    public void setMapLng(String mapLng) {
        this.mapLng = mapLng;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMapLat() {
        return mapLat;
    }

    public void setMapLat(String mapLat) {
        this.mapLat = mapLat;
    }

    public String getDisTel() {
        return disTel;
    }

    public void setDisTel(String disTel) {
        this.disTel = disTel;
    }

    public String getParentOrgName() {
        return parentOrgName;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(String chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public String getLogoFilePath() {
        return logoFilePath;
    }

    public void setLogoFilePath(String logoFilePath) {
        this.logoFilePath = logoFilePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisAddress() {
        return disAddress;
    }

    public void setDisAddress(String disAddress) {
        this.disAddress = disAddress;
    }

    public boolean isIsEditCharge() {
        return isEditCharge;
    }

    public void setIsEditCharge(boolean isEditCharge) {
        this.isEditCharge = isEditCharge;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }
}
