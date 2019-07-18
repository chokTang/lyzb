package com.lyzb.jbx.model.me;

import android.text.TextUtils;

/**
 * @author wyx
 * @role 指定 访问记录
 * @time 2019 2019/3/22 16:31
 */

public class AcsRecomdModel {
    private String title;
    private String createTime;//创建时间
    private String userId;
    private int type;// 1 名片 2动态 3商品
    private String typeId;//对应的typeId
    private String content;
    private int source;//1：APP(IOS) 2：微信 3：小程序 4：APP(安卓)）5 其它
    private int stayTime;//停留时间 单位（秒）

    public String getTypeId() {
        return typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        if (TextUtils.isEmpty(createTime)){
            createTime="2019-01-01 12:00:00";
        }
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSource() {
        return source;
    }

    //APP(IOS) 2：微信 3：小程序 4：APP(安卓)）5 其它
    public String getSourceZh() {
        switch (source) {
            case 1:
                return "APP(IOS)";
            case 2:
                return "微信";
            case 3:
                return "小程序";
            case 4:
                return "APP(安卓)";
            case 5:
                return "其他";
        }
        return "其他";
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }
}
