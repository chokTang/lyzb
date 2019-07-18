package com.lyzb.jbx.model.send;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/4  17:56
 * Desc:
 */
public class SendTWBean {


    /**
     * fileList : [{"createtime":"2019-03-08 17:22:43","file":"http://b-ssl.duitang.com/uploads/item/201812/05/20181205211932_xvslr.jpeg","fileType":0,"id":"528fca818f0c4f3bbd217b2fbed470b3","sort":1,"topicId":"7167a2ac7a1e44ed804836bc55bd348c"}]
     * gsTopicInfo : {"content":"我想发布一条动态","createDate":"2019-03-08 17:22:43","createMan":"32801","groupId":"1","id":"7167a2ac7a1e44ed804836bc55bd348c"}
     */

    private GsTopicInfoBean gsTopicInfo;
    private List<FileListBean> fileList;
    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public GsTopicInfoBean getGsTopicInfo() {
        return gsTopicInfo;
    }

    public void setGsTopicInfo(GsTopicInfoBean gsTopicInfo) {
        this.gsTopicInfo = gsTopicInfo;
    }

    public List<FileListBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public static class GsTopicInfoBean {
        /**
         * content : 我想发布一条动态
         * createDate : 2019-03-08 17:22:43
         * createMan : 32801
         * groupId : 1
         * id : 7167a2ac7a1e44ed804836bc55bd348c
         */

        private String content;
        private String createDate;
        private String createMan;
        private String groupId;
        private String id;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateMan() {
            return createMan;
        }

        public void setCreateMan(String createMan) {
            this.createMan = createMan;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class FileListBean {
        /**
         * createtime : 2019-03-08 17:22:43
         * file : http://b-ssl.duitang.com/uploads/item/201812/05/20181205211932_xvslr.jpeg
         * fileType : 0
         * id : 528fca818f0c4f3bbd217b2fbed470b3
         * sort : 1
         * topicId : 7167a2ac7a1e44ed804836bc55bd348c
         */

        private String createtime;
        private String file;
        private int fileType;
        private String id;
        private int sort;
        private String topicId;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }
    }
}
