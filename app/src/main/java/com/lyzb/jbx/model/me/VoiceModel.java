package com.lyzb.jbx.model.me;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/4/25  15:32
 * Desc:
 */
public class VoiceModel {

    /**
     * updateNum : 1
     * fileList : [{"id":386,"userId":110545,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/04/25/ecc8hT9NH6.amr","fileType":3,"createTime":"2019-04-25 15:19:44","delStatus":0,"tagType":6}]
     */

    private int updateNum;
    private List<FileListBean> fileList;

    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }

    public List<FileListBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public static class FileListBean {
        /**
         * id : 386
         * userId : 110545
         * filePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/04/25/ecc8hT9NH6.amr
         * fileType : 3
         * createTime : 2019-04-25 15:19:44
         * delStatus : 0
         * tagType : 6
         */

        private String id;
        private String userId;
        private String filePath;
        private int fileType;
        private String createTime;
        private int delStatus;
        private int tagType;
        private String fileLength;

        public String getFileLength() {
            if (null == fileLength) return "";
            return fileLength;
        }

        public void setFileLength(String fileLength) {
            this.fileLength = fileLength;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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
    }
}
