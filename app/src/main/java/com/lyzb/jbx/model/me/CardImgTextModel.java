package com.lyzb.jbx.model.me;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 智能名片-图片信息上传 必须参数
 * @time 2019 2019/3/19 17:08
 */

public class CardImgTextModel {

    private String content="";             //文本内容
    private List<Imgs> filePath=new ArrayList<>();    //图片地址

    private int fileType;               //图or视频 类型 1:图片 2:视频
    private int tagType;                //上传类型 1:个人简介  2:个人荣誉  3:个人提供  4:个人需求  5:个人信息相册

    public static class Imgs {

        private String filePath="";
        private String fileLength;
        private String id="";
        private int sort;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getFileLength() {
            return fileLength;
        }

        public void setFileLength(String fileLength) {
            this.fileLength = fileLength;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CardImgTextModel.Imgs> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<CardImgTextModel.Imgs> filePath) {
        this.filePath = filePath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }
}
