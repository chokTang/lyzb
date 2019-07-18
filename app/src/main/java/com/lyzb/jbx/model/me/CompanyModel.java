package com.lyzb.jbx.model.me;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyx
 * @role 我的企业 model
 * @time 2019 2019/3/8 11:37
 */

public class CompanyModel {


    /**
     * pageNum : 1
     * pageSize : 10
     * total : 2
     * pages : 1
     * list : [{"id":"0dfa7d318b6a4f489673ea4650aff9a9","companyName":"cc测试企业2次提交","isJoin":16,"distributorType":16,"isDefault":1},{"id":"95fec45670854bc0a7875cc2bdf42108","companyName":"cc测试企业1","isJoin":2,"isDefault":2}]
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private boolean isShowCreate;
    private int pages;
    private List<ListBean> list;

    public boolean isShowCreate() {
        return isShowCreate;
    }

    public void setShowCreate(boolean showCreate) {
        isShowCreate = showCreate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 0dfa7d318b6a4f489673ea4650aff9a9
         * companyName : cc测试企业2次提交
         * isJoin : 16
         * distributorType : 16
         * isDefault : 1
         */

        private boolean isSelected;    //是否为选中企业
        private String id;                  //企业id
        private String companyName;         //企业名称
        private int isJoin;                 //是否已加入经销或运营伙伴 2:不是  0:是
        private int distributorType;        //12:省级运营伙伴  13:市级运营伙伴  14：区县级运营伙伴  16：共享平台
        private int isDefault;              //是否为默认企业   1:是  2:否
        private String isEdit;              //y:可以编辑  n:不可以编辑

        private int showType = 0;               //1 显示申请经销按钮  2：显示申请运营伙伴按钮   3.两者显示  4.两者都不
        private int isMy = 0;                   //是否为自己创建的企业   1.是 2.否
        private String creatorName ;//创建人
        private String creatorID;//创建人ID
        private String industryName ;//企业类型
        private String distributorLogo ;//企业logo
        private int membersNum ;//成员数量
        private int disType ;//企业类型(1.普通企业 2.团购企业)


        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getIndustryName() {
            if (TextUtils.isEmpty(industryName)) return "";
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getDistributorLogo() {
            return distributorLogo;
        }

        public void setDistributorLogo(String distributorLogo) {
            this.distributorLogo = distributorLogo;
        }

        public int getMembersNum() {
            return membersNum;
        }

        public void setMembersNum(int membersNum) {
            this.membersNum = membersNum;
        }

        public int getDisType() {
            return disType;
        }

        public void setDisType(int disType) {
            this.disType = disType;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getIsJoin() {
            return isJoin;
        }

        public void setIsJoin(int isJoin) {
            this.isJoin = isJoin;
        }

        public int getDistributorType() {
            return distributorType;
        }

        public void setDistributorType(int distributorType) {
            this.distributorType = distributorType;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getIsEdit() {
            return isEdit;
        }

        public void setIsEdit(String isEdit) {
            this.isEdit = isEdit;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public int getIsMy() {
            return isMy;
        }

        public void setIsMy(int isMy) {
            this.isMy = isMy;
        }

        public String getCreatorId() {
            return creatorID;
        }

        public void setCreatorId(String creatorId) {
            this.creatorID = creatorId;
        }
    }
}
