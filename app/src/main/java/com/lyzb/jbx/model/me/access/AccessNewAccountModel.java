package com.lyzb.jbx.model.me.access;

import android.text.TextUtils;

import com.lyzb.jbx.model.common.VipModel;

import java.util.List;

/**
 * 引流新用户实体
 * 来源接口：/lbs/gsUserBelong/selectByPageInfo
 *
 * @author shidengzhong
 */
public class AccessNewAccountModel {
    private String id;//主键ID
    private String shareUserId;//分享人id
    private String shareUserGuid;//分享人UUID
    private String browserUserId;//引流人id
    private String browserUnionId;//引流人unionId
    private String browseTime;//浏览时间
    private String createdTime;//创建时间
    private int type;//1：名片 2：热文
    private String userName;//用户名
    private String headimg;//头像
    private String regTime;//注册时间
    private List<VipModel> userActionVos;
    private String shareName;//通过什么引流

    public String getShareName() {
        if (TextUtils.isEmpty(shareName)) {
            return "";
        }
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getShareUserGuid() {
        return shareUserGuid;
    }

    public void setShareUserGuid(String shareUserGuid) {
        this.shareUserGuid = shareUserGuid;
    }

    public String getBrowserUserId() {
        return browserUserId;
    }

    public void setBrowserUserId(String browserUserId) {
        this.browserUserId = browserUserId;
    }

    public String getBrowserUnionId() {
        return browserUnionId;
    }

    public void setBrowserUnionId(String browserUnionId) {
        this.browserUnionId = browserUnionId;
    }

    public String getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(String browseTime) {
        this.browseTime = browseTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public List<VipModel> getUserActionVos() {
        return userActionVos;
    }

    public void setUserActionVos(List<VipModel> userActionVos) {
        this.userActionVos = userActionVos;
    }
}
