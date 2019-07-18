package com.lyzb.jbx.model.me;

import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.model.account.AccountItemModel;
import com.lyzb.jbx.model.common.VipModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝页面的model
 * 对应接口：/lbs/gs/topic/selectMyRelatioList
 */
public class FansModel {
    private String id;
    private String gsName;
    private AccountItemModel gsTopicVo;
    private String headimg;
    private String shopName;
    private String toUserId;
    private String fromUserId;
    private String relationNum;
    private List<VipModel> userVipAction;//Vip类型
    private String sex;
    private String interestNum;//列表用户是否关注当前用户0 未关注 1或大于1关注 表示对方是否关注你

    public int getInterestNum() {
        return CommonUtil.converToT(interestNum, 0);
    }

    public void setInterestNum(String interestNum) {
        this.interestNum = interestNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public AccountItemModel getGsTopicVo() {
        if (gsTopicVo == null) {
            return new AccountItemModel();
        }
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getRelationNum() {
        return CommonUtil.converToT(relationNum, 0);
    }

    public void setRelationNum(int relationNum) {
        this.relationNum = String.valueOf(relationNum);
    }

    public List<VipModel> getUserVipAction() {
        if (userVipAction == null) return new ArrayList<>();
        return userVipAction;
    }

    public void setUserVipAction(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
