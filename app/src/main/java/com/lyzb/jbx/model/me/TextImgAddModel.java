package com.lyzb.jbx.model.me;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 图文信息添加or编辑成功 model
 * @time 2019 2019/3/20 10:31
 */

public class TextImgAddModel {


    /**
     * updateNum : 1
     * content : 文本内容
     * fileList : [{"id":133,"userId":109112,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/n9vl9G6886.jpg","fileType":1,"createTime":"2019-03-20 10:28:14","delStatus":0,"tagType":1},{"id":134,"userId":109112,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/X3596dB7L9.jpg","fileType":1,"createTime":"2019-03-20 10:28:14","delStatus":0,"tagType":1},{"id":135,"userId":109112,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/n9vl9G6886.jpg","fileType":1,"createTime":"2019-03-20 10:28:50","delStatus":0,"tagType":1},{"id":136,"userId":109112,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/X3596dB7L9.jpg","fileType":1,"createTime":"2019-03-20 10:28:50","delStatus":0,"tagType":1},{"id":137,"userId":109112,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/n9vl9G6886.jpg","fileType":1,"createTime":"2019-03-20 10:29:31","delStatus":0,"tagType":1},{"id":138,"userId":109112,"filePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/X3596dB7L9.jpg","fileType":1,"createTime":"2019-03-20 10:29:31","delStatus":0,"tagType":1}]
     */

    private int updateNum;
    private String content;
    private List<CardItemInfoModel> fileList;

    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CardItemInfoModel> getFileList() {
        if (fileList==null) return new ArrayList<>();
        return fileList;
    }

    public void setFileList(List<CardItemInfoModel> fileList) {
        this.fileList = fileList;
    }

    public static class FileListBean {
        /**
         * id : 133
         * userId : 109112
         * filePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/n9vl9G6886.jpg
         * fileType : 1
         * createTime : 2019-03-20 10:28:14
         * delStatus : 0
         * tagType : 1
         */

        private String id;
        private String userId;
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
