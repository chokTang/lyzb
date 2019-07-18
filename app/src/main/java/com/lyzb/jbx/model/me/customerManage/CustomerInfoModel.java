package com.lyzb.jbx.model.me.customerManage;

import com.lyzb.jbx.model.statistics.UserActionVosBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyx
 * @role 客户详情（跟客户管理的数据是一样的，所以共用一个）
 * @time 2019 2019/5/8 13:51
 */

public class CustomerInfoModel implements Serializable {

    /**
     * id : 2
     * remarkName : 小不点
     * sex : 1
     * mobile : 187237914678
     * company : 重庆礼仪之邦电子商务有限公司
     * position : java开发
     * residence : 50，105，2355
     * address : 重庆市两江新区海王星
     * birthday : 2010-09-10 00:00:00
     * wxAccount : 18723465895
     * remark : 重要客户
     * customerUserId : 110891
     * createTime : 2019-05-08 10:44:38
     * addUserId : 110890
     * status : 1
     * browseNum : 0
     * topicNum : 0
     * goodsNum : 0
     * customersFollowNum : 0
     * headimg : www.jibaoxianggo.com/d.jpg
     */

    /**
     * 用户名片ID
     */
    private String userExtId;

    private String id;
    /**
     * 名称
     */
    private String remarkName;
    /**
     * 性别(1.先生 2.女士)
     */
    private int sex;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 所在公司
     */
    private String company;
    /**
     * 担任职位
     */
    private String position;
    /**
     * 区域
     */
    private String residence;
    /**
     * 地址
     */
    private String address;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 微信号
     */
    private String wxAccount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 客户userid
     */
    private String customerUserId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 用户userid（添加客户的人）
     */
    private String addUserId;
    /**
     * 1:有效；2：删除
     */
    private String status;
    /**
     * 浏览名片数
     */
    private int browseNum;
    /**
     * 浏览我的动态数
     */
    private int topicNum;
    /**
     * 浏览我的商品数
     */
    private int goodsNum;
    /**
     * 跟进数
     */
    private int customersFollowNum;
    /**
     * 头像
     */
    private String headimg;

    /**
     * 毕业院校
     */
    private String school;
    /**
     * 区域名称
     */
    private String residenceName;
    /**
     * 是否为vip
     */
    private List<UserActionVosBean> userActionVos;

    /**
     * 是否为管理员(本地用的  后台不会返)
     */
    private boolean admin;

    public CustomerInfoModel(String userExtId) {
        this.userExtId = userExtId;
    }

    public String getUserExtId() {
        return userExtId;
    }

    public void setUserExtId(String userExtId) {
        this.userExtId = userExtId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
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

    public int getCustomersFollowNum() {
        return customersFollowNum;
    }

    public void setCustomersFollowNum(int customersFollowNum) {
        this.customersFollowNum = customersFollowNum;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    public List<UserActionVosBean> getUserActionVos() {
        return userActionVos;
    }

    public void setUserActionVos(List<UserActionVosBean> userActionVos) {
        this.userActionVos = userActionVos;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
