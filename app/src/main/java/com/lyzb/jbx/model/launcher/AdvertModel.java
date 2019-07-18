package com.lyzb.jbx.model.launcher;

import java.io.Serializable;

public class AdvertModel implements Serializable {
    private String advert_link;//跳转的链接
    private int advert_time;//倒计时
    private String xhdpi;//图片地址

    public String getAdvert_link() {
        return advert_link;
    }

    public void setAdvert_link(String advert_link) {
        this.advert_link = advert_link;
    }

    public int getAdvert_time() {
        return advert_time;
    }

    public void setAdvert_time(int advert_time) {
        this.advert_time = advert_time;
    }

    public String getXhdpi() {
        return xhdpi;
    }

    public void setXhdpi(String xhdpi) {
        this.xhdpi = xhdpi;
    }
}
