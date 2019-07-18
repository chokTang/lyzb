package com.lyzb.jbx.model.me;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 企业详情 model
 * @time 2019 2019/3/21 11:11
 */

public class ComdDetailModel {
    private GsDistributorVoBean gsDistributor;
    private List<ComdFile> gsDistributorFiles;
    private boolean isShowJoinBtn;
    private String joinBtnType;

    public boolean isShowJoinBtn() {
        return isShowJoinBtn;
    }

    public void setShowJoinBtn(boolean showJoinBtn) {
        isShowJoinBtn = showJoinBtn;
    }

    public String getJoinBtnType() {
        return joinBtnType;
    }

    public void setJoinBtnType(String joinBtnType) {
        this.joinBtnType = joinBtnType;
    }

    public GsDistributorVoBean getGsDistributorVo() {
        return gsDistributor;
    }

    public void setGsDistributorVo(GsDistributorVoBean gsDistributorVo) {
        this.gsDistributor = gsDistributorVo;
    }

    public List<ComdFile> getDistributorFiles() {
        if (gsDistributorFiles == null) return new ArrayList<>();
        return gsDistributorFiles;
    }

    public void setDistributorFiles(List<ComdFile> distributorFiles) {
        this.gsDistributorFiles = distributorFiles;
    }

    public static class GsDistributorVoBean {
        /**
         * id : 17dbd35891a147e8bcc8f6c245a4cd54
         * companyName : 企业名称12wyx
         * industryId : 8
         * auditState : 1
         * creatorId : 32801
         * createdTime : 2019-03-21 11:06:54
         * industryName : 农林牧渔
         * introduction : 企业公司简介
         * brand : 轻拍信息
         * culture : 荣誉信息333
         */

        private String id;
        private String companyName;
        private String industryId;
        private int auditState;
        private String creatorId;
        private String createdTime;
        private String industryName;
        private String introduction;
        private String brand;
        private String culture;
        private String isDonate;
        private String fax;  //logo地址
        private String assignedID;  //logo地址


        public String getAssignedID() {
            if (null == assignedID) return "";
            return assignedID;
        }

        public String getIsDonate() {
            return isDonate;
        }

        public void setIsDonate(String isDonate) {
            this.isDonate = isDonate;
        }

        public void setAssignedID(String assignedID) {
            this.assignedID = assignedID;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        private String disTel;//公司电话
        private int isDefault;

        private String address;//企业地址
        private String bLImgUrl;//营业执照路径
        private String contractNumber;//服务码
        private String description;//申请入驻经销或运营审核描述
        private String disAddress;//公司地址
        private int disSt;//企业审核状态(1.待审核 2.通过 3.驳回) 因预审核,状态待审核也是通过

        private String legalEntityMobile;//联系人电话
        private String legalEntityName;//联系人姓名
        private String regionId;//地区id
        private int regionLevel;//地区层级
        private String regionName;//注册地名称
        private String sCCode;//执照号
        private int distributorType;//经销商类型:12，省级运营伙伴；13，市级运营伙伴；14，区县级运营伙伴  16.共享平台经销商


        public String getLogo() {
            if (null == fax) return "";
            return fax;
        }

        public void setLogo(String logo) {
            this.fax = logo;
        }

        public int getDistributorType() {
            return distributorType;
        }

        public void setDistributorType(int distributorType) {
            this.distributorType = distributorType;
        }

        public String getAddress() {
            if (null == address) return "";
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getbLImgUrl() {
            return bLImgUrl;
        }

        public void setbLImgUrl(String bLImgUrl) {
            this.bLImgUrl = bLImgUrl;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getDisSt() {
            return disSt;
        }

        public void setDisSt(int disSt) {
            this.disSt = disSt;
        }

        public String getLegalEntityMobile() {
            return legalEntityMobile;
        }

        public void setLegalEntityMobile(String legalEntityMobile) {
            this.legalEntityMobile = legalEntityMobile;
        }

        public String getLegalEntityName() {
            return legalEntityName;
        }

        public void setLegalEntityName(String legalEntityName) {
            this.legalEntityName = legalEntityName;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public int getRegionLevel() {
            return regionLevel;
        }

        public void setRegionLevel(int regionLevel) {
            this.regionLevel = regionLevel;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getsCCode() {
            return sCCode;
        }

        public void setsCCode(String sCCode) {
            this.sCCode = sCCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            if (null == companyName) return "";
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getIndustryId() {
            return industryId;
        }

        public void setIndustryId(String industryId) {
            this.industryId = industryId;
        }

        public int getAuditState() {
            return auditState;
        }

        public void setAuditState(int auditState) {
            this.auditState = auditState;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getIndustryName() {
            if (null == industryName) return "";
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCulture() {
            return culture;
        }

        public void setCulture(String culture) {
            this.culture = culture;
        }

        public String getDisTel() {
            if (null == disTel) return "";
            return disTel;
        }

        public String getDisAddress() {
            return disAddress;
        }

        public void setDisAddress(String disAddress) {
            this.disAddress = disAddress;
        }

        public void setDisTel(String disTel) {
            this.disTel = disTel;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }

    public static class ComdFile {
        /**
         * id : 179
         * distributorId : 17dbd35891a147e8bcc8f6c245a4cd54
         * filePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/21/MIf709CT67.jpg
         * fileType : 1
         * createTime : 2019-03-21 11:06:54
         * delStatus : 0
         * tagType : 1
         */

        private String id;
        private String distributorId;
        private String filePath;
        private int fileType;
        private String createTime;
        private int delStatus;
        private int tagType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(String distributorId) {
            this.distributorId = distributorId;
        }

        public String getFilePath() {
            if (null == filePath) return "";
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(int delStatus) {
            this.delStatus = delStatus;
        }

        public int getTagType() {
            return tagType;
        }

        public void setTagType(int tagType) {
            this.tagType = tagType;
        }
    }
}
