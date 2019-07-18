package com.lyzb.jbx.model.me;

import java.util.List;

/**
 * @author wyx
 * @role 名片-企业 修改视频or图文 成功后的model
 * @time 2019 2019/3/23 17:55
 */

public class CardCmdListModel {


    /**
     * distributorFiles : [{"distributorId":"e2a0ff10a6a84513811038a9c7d58092","filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/23/h1wW86P00f.mp4","fileType":2,"delStatus":0,"tagType":4}]
     * optUser : 32801
     * optType : upt
     */

    private String optUser;
    private String optType;
    private List<DistributorFilesBean> distributorFiles;

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public List<DistributorFilesBean> getDistributorFiles() {
        return distributorFiles;
    }

    public void setDistributorFiles(List<DistributorFilesBean> distributorFiles) {
        this.distributorFiles = distributorFiles;
    }

    public static class DistributorFilesBean {
        /**
         * distributorId : e2a0ff10a6a84513811038a9c7d58092
         * filePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/23/h1wW86P00f.mp4
         * fileType : 2
         * delStatus : 0
         * tagType : 4
         */

        private String distributorId;
        private String filePath;
        private int fileType;
        private int delStatus;
        private int tagType;

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
