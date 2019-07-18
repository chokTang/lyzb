package com.lyzb.jbx.model.eventbus;

/**
 * @author wyx
 * @role 动态的点赞 评论 关注等操作
 * @time 2019 2019/4/9 9:30
 */

public class DynamicItemStatusEventBus {
    private boolean isFollow;//是否关注
    private boolean isZan;//是否点赞
    private int commentNumber;//评论数

    private boolean isLoginOut =false;//是否退出了登录操作 针对于个人中心退出了登录以后的操作

    public DynamicItemStatusEventBus(){

    }

    public DynamicItemStatusEventBus(boolean isLoginOut) {
        this.isLoginOut = isLoginOut;
    }

    public DynamicItemStatusEventBus(boolean isFollow, boolean isZan, int commentNumber) {
        this.isFollow = isFollow;
        this.isZan = isZan;
        this.commentNumber = commentNumber;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public boolean isZan() {
        return isZan;
    }

    public void setZan(boolean zan) {
        isZan = zan;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public boolean isLoginOut() {
        return isLoginOut;
    }

    public void setLoginOut(boolean loginOut) {
        isLoginOut = loginOut;
    }
}
