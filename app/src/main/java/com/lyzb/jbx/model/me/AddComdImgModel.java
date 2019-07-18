package com.lyzb.jbx.model.me;

import java.io.Serializable;

/**
 * @author wyx
 * @role 创建企业 添加图片model
 * @time 2019 2019/3/20 20:53
 */

public class AddComdImgModel implements Serializable{

    private String imgUrl;
    private String imgId;
    private int imgType;

    private int isMeComd=0;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public int getIsMeComd() {
        return isMeComd;
    }

    public void setIsMeComd(int isMeComd) {
        this.isMeComd = isMeComd;
    }
}
