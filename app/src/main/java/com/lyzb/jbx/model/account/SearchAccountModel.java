package com.lyzb.jbx.model.account;

import com.lyzb.jbx.model.common.VipModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索--用户页面实体
 * lbs/gs/home/searchGsUser
 */
public class SearchAccountModel {
    private String userId;
    private String gsName;
    private String shopName;
    private AccountItemModel gsTopicVo;
    private String headimg;
    private int relationNum;
    private List<VipModel> userVipAction;//Vip类型

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public AccountItemModel getGsTopicVo() {
        return gsTopicVo;
    }

    public void setGsTopicVo(AccountItemModel gsTopicVo) {
        this.gsTopicVo = gsTopicVo;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getRelationNum() {
        return relationNum;
    }

    public void setRelationNum(int relationNum) {
        this.relationNum = relationNum;
    }

    public List<VipModel> getUserVipAction() {
        if (userVipAction == null) return new ArrayList<>();
        return userVipAction;
    }

    public void setUserVipAction(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }
}
