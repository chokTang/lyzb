package com.lyzb.jbx.model.me;

import java.io.Serializable;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/4/19  10:50
 * Desc: 创建企业 请求body
 */
public class CreateCompanyBody {
    /**
     * gsDistributorVo : {"id":"417f6ae835314eaf8d8df9d90c6e021e","companyName":"upt企业名称测试new444","industryId":"1","modifierId":"32801","modifiedTime":"2019-03-20 15:56:16","industryName":"运营","introduction":"修改企业简介描述企业简介描述企业简介描述企业简介描述"}
     * distributorFiles : [{"id":178,"distributorId":"417f6ae835314eaf8d8df9d90c6e021e","filePath":"111newffffffffffff1...........","fileType":1,"createTime":"2019-03-20 15:56:16","delStatus":0,"tagType":1}]
     */


    private GsDistributorVoBean gsDistributorVo;
    private List<ComdFile> distributorFiles;

    private String optType;
    private String diff;

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public GsDistributorVoBean getGsDistributorVo() {
        return gsDistributorVo;
    }

    public void setGsDistributorVo(GsDistributorVoBean gsDistributorVo) {
        this.gsDistributorVo = gsDistributorVo;
    }

    public List<ComdFile> getDistributorFiles() {
        return distributorFiles;
    }

    public void setDistributorFiles(List<ComdFile> distributorFiles) {
        this.distributorFiles = distributorFiles;
    }

    public static class GsDistributorVoBean {
        /**
         * id : 417f6ae835314eaf8d8df9d90c6e021e
         * companyName : upt企业名称测试new444
         * industryId : 1
         * modifierId : 32801
         * modifiedTime : 2019-03-20 15:56:16
         * industryName : 运营
         * introduction : 修改企业简介描述企业简介描述企业简介描述企业简介描述
         */

        private String id;
        private String brand;
        private String companyName;
        private String culture;
        private String industryId;
        private String modifierId;
        private String modifiedTime;
        private String industryName;
        private String introduction;

        private String disAddress;
        private String disTel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCulture() {
            return culture;
        }

        public void setCulture(String culture) {
            this.culture = culture;
        }

        public String getIndustryId() {
            return industryId;
        }

        public void setIndustryId(String industryId) {
            this.industryId = industryId;
        }

        public String getModifierId() {
            return modifierId;
        }

        public void setModifierId(String modifierId) {
            this.modifierId = modifierId;
        }

        public String getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(String modifiedTime) {
            this.modifiedTime = modifiedTime;
        }

        public String getIndustryName() {
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

        public String getDisAddress() {
            return disAddress;
        }

        public void setDisAddress(String disAddress) {
            this.disAddress = disAddress;
        }

        public String getDisTel() {
            return disTel;
        }

        public void setDisTel(String disTel) {
            this.disTel = disTel;
        }
    }

    public static class ComdFile implements Serializable {
        /**
         * id : 178
         * distributorId : 417f6ae835314eaf8d8df9d90c6e021e
         * filePath : 111newffffffffffff1...........
         * fileType : 1
         * createTime : 2019-03-20 15:56:16
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

        private int imgType;

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

        public int getImgType() {
            return imgType;
        }

        public void setImgType(int imgType) {
            this.imgType = imgType;
        }
    }
}
