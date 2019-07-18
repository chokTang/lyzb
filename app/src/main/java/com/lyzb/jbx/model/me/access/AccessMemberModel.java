package com.lyzb.jbx.model.me.access;

import com.lyzb.jbx.model.common.VipModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 访问XX人用户列表item实体
 * 接口：/lbs/gs/user/selectMyViewRecordVoList
 *
 * @author shidengzhong
 */
public class AccessMemberModel implements Serializable {
    private int userExtNum;//名片浏览数
    private String createTime;
    private int topicNum;//动态浏览数
    private int goodsNum;//商品浏览次数
    private int viewHotNum;//热文浏览次数
    private String headimg;
    private String gaName;
    private String mobile;//手机号
    private String wxImg;//微信二维码
    private String id;//用户名片id
    private String userId;//用户userId
    private List<VipModel> userVipAction;
    //是否已经设置为意向客户
    private boolean customersStatus;
    //热文下的一些参数
    private String openId;//访问人openid
    private String userGuid;//分享人guid
    private int visitcount;//数量
    private String userGuidView;//当前访问人guid
    private String gsName;//名称

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public int getVisitcount() {
        return visitcount;
    }

    public void setVisitcount(int visitcount) {
        this.visitcount = visitcount;
    }

    public String getUserGuidView() {
        return userGuidView;
    }

    public void setUserGuidView(String userGuidView) {
        this.userGuidView = userGuidView;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public int getUserExtNum() {
        return userExtNum;
    }

    public void setUserExtNum(int userExtNum) {
        this.userExtNum = userExtNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getGaName() {
        return gaName;
    }

    public void setGaName(String gaName) {
        this.gaName = gaName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxImg() {
        return wxImg;
    }

    public void setWxImg(String wxImg) {
        this.wxImg = wxImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<VipModel> getUserVipAction() {
        if (userVipAction == null)
            return new ArrayList<>();
        return userVipAction;
    }

    public void setUserVipAction(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }

    public boolean isCustomersStatus() {
        return customersStatus;
    }

    public void setCustomersStatus(boolean customersStatus) {
        this.customersStatus = customersStatus;
    }

    public int getHotNum() {
        return viewHotNum;
    }

    public void setHotNum(int hotNum) {
        this.viewHotNum = hotNum;
    }
}
