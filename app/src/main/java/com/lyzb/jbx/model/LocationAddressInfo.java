package com.lyzb.jbx.model;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/6/19  11:56
 * Desc:
 */
public class LocationAddressInfo implements Serializable {

    public String title;
    public String text;
    public Double lon;
    public Double lat;
    public boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public LocationAddressInfo(Double lon, Double lat, String title, String text) {
        this.title = title;
        this.text = text;
        this.lon = lon;
        this.lat = lat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
