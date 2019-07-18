package com.lyzb.jbx.model.params;

import com.lyzb.jbx.model.me.company.OrganInfoModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/6/15 9:36
 */

public class AddOrganBody {
    /**
     * 图片集(logo)(不知道为什么要用array)
     */
    private List<DistributorFiles> distributorFiles;
    /**
     * 机构信息
     */
    private OrganInfoModel gsDistributorVo;
    /**
     * 操作（add：新增；upt：修改
     */
    private String optType;
    /**
     * 机构类型（1.企业 2.部门）默认为部门
     */
    private String dataType = "2";
    /**
     * 上级机构id
     */
    private String parentOrgId;

    public List<DistributorFiles> getDistributorFiles() {
        return distributorFiles;
    }

    public void setDistributorFiles(List<DistributorFiles> distributorFiles) {
        this.distributorFiles = distributorFiles;
    }

    public OrganInfoModel getGsDistributorVo() {
        return gsDistributorVo;
    }

    public void setGsDistributorVo(OrganInfoModel gsDistributorVo) {
        this.gsDistributorVo = gsDistributorVo;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public class DistributorFiles {
        /**
         * 文件类型（1.图片）传1
         */
        private int fileType;
        /**
         * 是否删除（1 删除 0 显示  ）
         */
        private int delStatus;
        /**
         * 文件所属（6.企业logo）传6
         */
        private int tagType;
        /**
         * 图片路径
         */
        private String filePath;

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
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

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}
