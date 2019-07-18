package com.lyzb.jbx.model.common;

import com.lyzb.jbx.model.dynamic.DynamicFileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态item-下的file对应的item实体
 */
public class DynamicItemModel {
    private int commentCount;
    private int shareCount;
    private int type;
    private int upCount;
    private int viewCount;
    private String content;
    private String createDate;
    private String createMan;
    private String groupId;
    private List<DynamicFileModel> fileList;
    private String id;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

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

    public List<DynamicFileModel> getFileList() {
        if (fileList == null) return new ArrayList<>();
        return fileList;
    }

    public void setFileList(List<DynamicFileModel> fileList) {
        this.fileList = fileList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
