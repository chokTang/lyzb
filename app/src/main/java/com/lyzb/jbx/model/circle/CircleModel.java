package com.lyzb.jbx.model.circle;

import com.like.utilslib.app.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class CircleModel {
    private boolean allowinvites;//是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主才可以往群
    private boolean approval;//是否需要批准为必选项
    private String background;//背景图
    private boolean banned;//是否全部禁言
    private String description;//群描述
    private String groupCreateTime;//群创建时间
    private String groupUpdateTime;//群修改时间
    private String groupname;//圈子名称
    private String id;//圈子ID
    private String logo;//群头像
    private Object ifOwner;//是否是群主：true 是；false 否（已加入）；null 未加入
    private boolean inviteNeedConfirm;//邀请加群，被邀请人是否需要确认。如果是true，表示邀请加群需要被邀请人确认；如果是false，表示
    private String groupType;//群类型（0：普通群，1：活动临时群，2：自建群）
    private int maxusers;//最大成员数
    private int menberNum;//成员数
    private int dynamicNum;//动态数目
    private String orgId;//群主id
    private String owner;//群主
    private boolean publicStr;//是否公开
    private int sort;//排序
    private String type;//类别
    private boolean membersOnly;//加入群组是否需要群主或者群管理员审批。true：是，false：否
    private String isJoin;//1、管理员 2：加入 ，3未加入4 申请中
    private OwnerVoBean ownerVo;
    private List<CircleMemberModel> menberList;//成员列表

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public boolean isAllowinvites() {
        return allowinvites;
    }

    public void setAllowinvites(boolean allowinvites) {
        this.allowinvites = allowinvites;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupCreateTime() {
        return groupCreateTime;
    }

    public void setGroupCreateTime(String groupCreateTime) {
        this.groupCreateTime = groupCreateTime;
    }

    public String getGroupUpdateTime() {
        return groupUpdateTime;
    }

    public void setGroupUpdateTime(String groupUpdateTime) {
        this.groupUpdateTime = groupUpdateTime;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Object isIfOwner() {
        return ifOwner;
    }

    public void setIfOwner(Object ifOwner) {
        this.ifOwner = ifOwner;
    }

    public boolean isInviteNeedConfirm() {
        return inviteNeedConfirm;
    }

    public void setInviteNeedConfirm(boolean inviteNeedConfirm) {
        this.inviteNeedConfirm = inviteNeedConfirm;
    }

    public int getGroupType() {
        return CommonUtil.converToT(groupType, 0);
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public int getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }

    public int getMenberNum() {
        return menberNum;
    }

    public void setMenberNum(int menberNum) {
        this.menberNum = menberNum;
    }

    public int getDynamicNum() {
        return dynamicNum;
    }

    public void setDynamicNum(int dynamicNum) {
        this.dynamicNum = dynamicNum;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPublicStr() {
        return publicStr;
    }

    public void setPublicStr(boolean publicStr) {
        this.publicStr = publicStr;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMembersOnly() {
        return membersOnly;
    }

    public void setMembersOnly(boolean membersOnly) {
        this.membersOnly = membersOnly;
    }

    public OwnerVoBean getOwnerVo() {
        return ownerVo;
    }

    public void setOwnerVo(OwnerVoBean ownerVo) {
        this.ownerVo = ownerVo;
    }

    public List<CircleMemberModel> getMenberList() {
        if (menberList == null)
            return new ArrayList<>();
        return menberList;
    }

    public void setMenberList(List<CircleMemberModel> menberList) {
        this.menberList = menberList;
    }
}
