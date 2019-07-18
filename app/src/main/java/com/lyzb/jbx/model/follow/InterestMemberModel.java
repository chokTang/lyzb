package com.lyzb.jbx.model.follow;

import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.model.common.VipModel;
import com.lyzb.jbx.model.common.VoiceModel;

import java.util.ArrayList;
import java.util.List;

public class InterestMemberModel {
    private String concernProfession;//关注职业ID
    private String concernProfessionName;//关注职业Name
    private String gsName;//用户名
    private String headimg;
    private String mobile;
    private String position;//职位
    private String professionId;//所在职业Id
    private String professionName;//所在职业
    private String shopName;//公司名字
    private String userId;//用户ID
    private List<VoiceModel> introductionAudioFile;//语音文件
    private String myDemand;//我需要的
    private String myResources;//我提供的
    private int relationNum;//是否关注
    private List<VipModel> userVipAction;

    public List<VipModel> getUserVipAction() {
        if (userVipAction == null) return new ArrayList<>();
        return userVipAction;
    }

    public void setUserVipAction(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }

    public int getRelationNum() {
        return relationNum;
    }

    public void setRelationNum(int relationNum) {
        this.relationNum = relationNum;
    }

    public String getMyDemand() {
        return myDemand;
    }

    public void setMyDemand(String myDemand) {
        this.myDemand = myDemand;
    }

    public String getMyResources() {
        return myResources;
    }

    public void setMyResources(String myResources) {
        this.myResources = myResources;
    }

    public List<VoiceModel> getIntroductionAudioFile() {
        if (introductionAudioFile == null) return new ArrayList<>();
        return introductionAudioFile;
    }

    public void setIntroductionAudioFile(List<VoiceModel> introductionAudioFile) {
        this.introductionAudioFile = introductionAudioFile;
    }

    public String getConcernProfession() {
        return concernProfession;
    }

    public void setConcernProfession(String concernProfession) {
        this.concernProfession = concernProfession;
    }

    public String getConcernProfessionName() {
        if (CommonUtil.isNull(concernProfessionName)) {
            return "";
        }
        return concernProfessionName;
    }

    public void setConcernProfessionName(String concernProfessionName) {
        this.concernProfessionName = concernProfessionName;
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

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        if (CommonUtil.isNull(professionName)) {
            return "";
        }
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
