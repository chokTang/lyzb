package com.lyzb.jbx.model.dynamic;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.like.utilslib.app.CommonUtil;

public class DynamicFileModel implements Comparable<DynamicFileModel> {
    private String createtime;
    private String file;
    private int fileType;//文件所属 0 主题 1 评论
    private String id;
    private int sort;
    private String topicId;
    @SerializedName("class1")
    private String itemType;//文件类型 1 图片 2 视频 3热文 4名片5商品

    public int getItemType() {
        return CommonUtil.converToT(itemType, 0);
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

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

    @Override
    public int compareTo(@NonNull DynamicFileModel o) {
        return this.sort - o.sort;
    }
}
