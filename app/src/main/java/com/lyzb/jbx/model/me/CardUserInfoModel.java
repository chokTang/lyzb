package com.lyzb.jbx.model.me;

import android.text.TextUtils;

/**
 * @author wyx
 * @role 编辑 用户信息 model
 * @time 2019 2019/3/15 13:49
 */

public class CardUserInfoModel {

    private String gsName;      //姓名
    private String headimg;     //头像
    private String shopLogo;     //公司logo
    private String posterImg;     //海报
    private String mobile;      //手机号码
    private String position;    //职位
    private String qqNum;       //QQ
    private String wxNum;       //微信账号
    private int sex;       //性别(1.先生 2.女士)
    private String shopName;    //商家名称
    private String wxImg;       //微信二维码

    private String professionId;        //熟悉行业
    private String shopAddress;        //商家地址
    private String concernProfession;   //期待认识
    private String education;           //毕业院校
    private String interest;            //兴趣爱好
    private String residence;           //来自城市
    private String oftenToPace;           //来往城市
    private String cardShareWord;       //名片分享语
    private String topicShareWord;      //动态分享语
    private String generation;      //个性签名
    private String email;      //email
    private String tel;      //座机
    private int showHot;      //热文是显示名片 0 不显示 1 显示
    private String mapLng;      //经度
    private String mapLat;      //纬度


    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getShowHot() {
        return showHot;
    }

    public void setShowHot(int showHot) {
        this.showHot = showHot;
    }

    public String getMapLng() {
        return mapLng;
    }

    public void setMapLng(String mapLng) {
        this.mapLng = mapLng;
    }

    public String getMapLat() {
        return mapLat;
    }

    public void setMapLat(String mapLat) {
        this.mapLat = mapLat;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWxImg() {
        return wxImg;
    }

    public void setWxImg(String wxImg) {
        this.wxImg = wxImg;
    }

    public String getProfessionId() {
        if (TextUtils.isEmpty(professionId)) return "";
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getConcernProfession() {
        if (TextUtils.isEmpty(concernProfession)) return "";
        return concernProfession;
    }

    public void setConcernProfession(String concernProfession) {
        this.concernProfession = concernProfession;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }


    public String getOftenToPace() {
        if (TextUtils.isEmpty(oftenToPace)) return "";
        return oftenToPace;
    }

    public void setOftenToPace(String oftenToPace) {
        this.oftenToPace = oftenToPace;
    }

    public String getInterest() {
        if (TextUtils.isEmpty(interest)) return "";
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getCardShareWord() {
        return cardShareWord;
    }

    public void setCardShareWord(String cardShareWord) {
        this.cardShareWord = cardShareWord;
    }

    public String getTopicShareWord() {
        return topicShareWord;
    }

    public void setTopicShareWord(String topicShareWord) {
        this.topicShareWord = topicShareWord;
    }
}
