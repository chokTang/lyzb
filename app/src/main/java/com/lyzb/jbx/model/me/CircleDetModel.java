package com.lyzb.jbx.model.me;

import com.lyzb.jbx.model.circle.OwnerVoBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyx
 * @role 圈子详情 model
 * @time 2019 2019/3/21 20:49
 */

public class CircleDetModel implements Serializable {


    /**
     * id : 77068079529985
     * groupname : 空调
     * publicStr : false
     * membersOnly : false
     * allowinvites : false
     * maxusers : 2000
     * owner : jbx109112
     * inviteNeedConfirm : false
     * approval : false
     * type : 9999
     * banned : false
     * logo : https://b-ssl.duitang.com/uploads/item/201512/13/20151213102616_rCiEx.thumb.700_0.jpeg
     * sort : 0
     * background : http://h.hiphotos.baidu.com/image/pic/item/eaf81a4c510fd9f9f0427a96282dd42a2934a4f3.jpg
     * groupType : 0
     * description : 咯无聊咯图
     * ownerVo : {"pageNum":1,"extPageSize":30,"childPageSize":1,"id":506,"groupId":"77068079529985","memberId":"jbx109112","banned":false,"admin":false,"owner":true,"black":false,"pass":true,"userName":"测试名称1122","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg","userId":"109112","companyInfo":"企业名称测试new"}
     * menberList : [{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":506,"groupId":"77068079529985","memberId":"jbx109112","banned":false,"admin":false,"owner":true,"black":false,"pass":true,"userName":"测试名称1122","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg","userId":"109112","companyInfo":"企业名称测试new"}]
     * menberNum : 1
     * dynamicNum : 0
     * notApplyCount : 0
     * isJoin : 2
     */

    private String id;
    private String groupname;
    private boolean publicStr;
    private boolean membersOnly;
    private boolean allowinvites;
    private int maxusers;
    private String owner;
    private boolean inviteNeedConfirm;
    private boolean approval;
    private String type;
    private boolean banned;
    private String logo;
    private int sort;
    private String background;
    private String groupType;
    private String description;
    private OwnerVoBean ownerVo;
    private int menberNum;
    private int dynamicNum;
    private int notApplyCount;
    /**
     * 加入圈子的状态1管理员，2加入，3申请中，4未加入
     */
    private String isJoin;
    private String orgId;
    private List<MenberListBean> menberList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public boolean isPublicStr() {
        return publicStr;
    }

    public void setPublicStr(boolean publicStr) {
        this.publicStr = publicStr;
    }

    public boolean isMembersOnly() {
        return membersOnly;
    }

    public void setMembersOnly(boolean membersOnly) {
        this.membersOnly = membersOnly;
    }

    public boolean isAllowinvites() {
        return allowinvites;
    }

    public void setAllowinvites(boolean allowinvites) {
        this.allowinvites = allowinvites;
    }

    public int getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isInviteNeedConfirm() {
        return inviteNeedConfirm;
    }

    public void setInviteNeedConfirm(boolean inviteNeedConfirm) {
        this.inviteNeedConfirm = inviteNeedConfirm;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OwnerVoBean getOwnerVo() {
        return ownerVo;
    }

    public void setOwnerVo(OwnerVoBean ownerVo) {
        this.ownerVo = ownerVo;
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

    public int getNotApplyCount() {
        return notApplyCount;
    }

    public void setNotApplyCount(int notApplyCount) {
        this.notApplyCount = notApplyCount;
    }

    public String getIsJoin() {
        if (isJoin == null) return "";
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<MenberListBean> getMenberList() {
        return menberList;
    }

    public void setMenberList(List<MenberListBean> menberList) {
        this.menberList = menberList;
    }

    public static class OwnerVoBean implements Serializable {
        /**
         * pageNum : 1
         * extPageSize : 30
         * childPageSize : 1
         * id : 506
         * groupId : 77068079529985
         * memberId : jbx109112
         * banned : false
         * admin : false
         * owner : true
         * black : false
         * pass : true
         * userName : 测试名称1122
         * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg
         * userId : 109112
         * companyInfo : 企业名称测试new
         */

        private int pageNum;
        private int extPageSize;
        private int childPageSize;
        private String id;
        private String groupId;
        private String memberId;
        private boolean banned;
        private boolean admin;
        private boolean owner;
        private boolean black;
        private boolean pass;
        private String userName;
        private String headimg;
        private String userId;
        private String companyInfo;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getExtPageSize() {
            return extPageSize;
        }

        public void setExtPageSize(int extPageSize) {
            this.extPageSize = extPageSize;
        }

        public int getChildPageSize() {
            return childPageSize;
        }

        public void setChildPageSize(int childPageSize) {
            this.childPageSize = childPageSize;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public boolean isBanned() {
            return banned;
        }

        public void setBanned(boolean banned) {
            this.banned = banned;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public boolean isOwner() {
            return owner;
        }

        public void setOwner(boolean owner) {
            this.owner = owner;
        }

        public boolean isBlack() {
            return black;
        }

        public void setBlack(boolean black) {
            this.black = black;
        }

        public boolean isPass() {
            return pass;
        }

        public void setPass(boolean pass) {
            this.pass = pass;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }
    }

    public static class MenberListBean implements Serializable {
        /**
         * pageNum : 1
         * extPageSize : 30
         * childPageSize : 1
         * id : 506
         * groupId : 77068079529985
         * memberId : jbx109112
         * banned : false
         * admin : false
         * owner : true
         * black : false
         * pass : true
         * userName : 测试名称1122
         * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg
         * userId : 109112
         * companyInfo : 企业名称测试new
         */

        private int pageNum;
        private int extPageSize;
        private int childPageSize;
        private String id;
        private String groupId;
        private String memberId;
        private boolean banned;
        private boolean admin;
        private boolean owner;
        private boolean black;
        private boolean pass;
        private String userName;
        private String headimg;
        private String userId;
        private String companyInfo;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getExtPageSize() {
            return extPageSize;
        }

        public void setExtPageSize(int extPageSize) {
            this.extPageSize = extPageSize;
        }

        public int getChildPageSize() {
            return childPageSize;
        }

        public void setChildPageSize(int childPageSize) {
            this.childPageSize = childPageSize;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public boolean isBanned() {
            return banned;
        }

        public void setBanned(boolean banned) {
            this.banned = banned;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public boolean isOwner() {
            return owner;
        }

        public void setOwner(boolean owner) {
            this.owner = owner;
        }

        public boolean isBlack() {
            return black;
        }

        public void setBlack(boolean black) {
            this.black = black;
        }

        public boolean isPass() {
            return pass;
        }

        public void setPass(boolean pass) {
            this.pass = pass;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }
    }
}
