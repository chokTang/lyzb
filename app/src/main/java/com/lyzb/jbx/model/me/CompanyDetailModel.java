package com.lyzb.jbx.model.me;

import android.text.TextUtils;

/**
 * Created by :TYK
 * Date: 2019/4/24  17:59
 * Desc:
 */
public class CompanyDetailModel {

    /**
     * msg : 查询成功
     * code : 200
     * data : {"distributorLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/04/24/o7G66o1LEM.jpg","industryName":"文化/传媒/广告","membersNum":"6","disType":1,"distributorId":"238b81fa706845eb90b412105f086920","companyName":"第八个家","isMy":1,"accountType":0,"creatorName":"唐义康","creatorId":"110342","showType":"3","groupId":""}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * distributorLogo : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/04/24/o7G66o1LEM.jpg
         * industryName : 文化/传媒/广告
         * membersNum : 6
         * disType : 1
         * distributorId : 238b81fa706845eb90b412105f086920
         * companyName : 第八个家
         * isMy : 1
         * accountType : 0
         * creatorName : 唐义康
         * creatorId : 110342
         * showType : 3
         * groupId :
         */

        private String distributorLogo;
        private String industryName;
        private String membersNum;
        private int disType;
        private String distributorId;
        private String companyName;
        private int isMy;
        private int accountType;
        private String creatorName;
        private String creatorId;
        private String showType;
        private String groupId;

        public String getDistributorLogo() {
            return distributorLogo;
        }

        public void setDistributorLogo(String distributorLogo) {
            this.distributorLogo = distributorLogo;
        }

        public String getIndustryName() {
            if (TextUtils.isEmpty(industryName)) return "";
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getMembersNum() {
            return membersNum;
        }

        public void setMembersNum(String membersNum) {
            this.membersNum = membersNum;
        }

        public int getDisType() {
            return disType;
        }

        public void setDisType(int disType) {
            this.disType = disType;
        }

        public String getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(String distributorId) {
            this.distributorId = distributorId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getIsMy() {
            return isMy;
        }

        public void setIsMy(int isMy) {
            this.isMy = isMy;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getGroupId() {
            if (null ==groupId) return "";
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }
}
