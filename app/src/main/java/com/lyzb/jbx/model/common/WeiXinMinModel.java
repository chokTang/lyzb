package com.lyzb.jbx.model.common;

import com.lyzb.jbx.util.AppCommonUtil;

import java.io.Serializable;

/**
 * @author wyx
 * @role 微信小程序分享的model实体
 * @time 2019 2019/4/8 15:28
 * 【主分支】本地修改了
 */

public class WeiXinMinModel implements Serializable {
    private String lowVersionUrl;
    private String title;
    private String describe;
    private byte[] imageUrl;
    private String shareUrl;

    public String getLowVersionUrl() {
        return lowVersionUrl;
    }

    public void setLowVersionUrl(String lowVersionUrl) {
        this.lowVersionUrl = lowVersionUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        AppCommonUtil.shareBitmp = imageUrl;//这个赋值 是为了处理携带的参数
//        this.imageUrl = imageUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
