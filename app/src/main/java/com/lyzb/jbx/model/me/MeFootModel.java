package com.lyzb.jbx.model.me;

/**
 * 我的足迹下的item实体
 * 来源接口：/lbs/gs/user/selectMyAccessRecord
 * @author shidengzhong
 */
public class MeFootModel {
    private String userId;
    private String createTime;//访问时间
    private int type;//1 名片 2动态 3 商品
    private String title;//类型为1--名片姓名，类型为2 动态内容，类型为3-商品名称
    private String content;
    private String id;//对应，名片id,动态id,商品id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
