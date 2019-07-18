package com.lyzb.jbx.model.params;

/**
 * Created by :TYK
 * Date: 2019/3/12  17:43
 * Desc:
 */
public class ContentBody {
    private String content;
    private String groupId;
    private String class1;

    public ContentBody() {
    }

    public ContentBody(String content,String class1, String groupId) {
        this.content = content;
        this.groupId = groupId;
        this.class1 = class1;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
