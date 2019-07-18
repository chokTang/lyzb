package com.lyzb.jbx.model.me;

import java.util.List;

/**
 * @author wyx
 * @role 我的发表-动态model
 * @time 2019 2019/3/6 20:10
 */

public class PubDynamicModel {

    private String year;
    private String month;
    private String day;

    private String text;
    private List<String> mImgLists;

    private String browse;
    private String like;
    private String msg;
    private String share;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImgLists() {
        return mImgLists;
    }

    public void setImgLists(List<String> imgLists) {
        mImgLists = imgLists;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }
}
