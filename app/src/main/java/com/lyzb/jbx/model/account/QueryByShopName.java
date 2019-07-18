package com.lyzb.jbx.model.account;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/20  10:39
 * Desc: 根据关键词联想商家名称 model
 */
public class QueryByShopName {


    /**
     * msg : 查询成功
     * dataList : [{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""},{"id":"04c6533a90ef46e782dcb7cdedd540da","companyName":"cc测试企业我","creatorName":"未知","industryName":"运营","membersNum":1,"applyState":0,"distributorLogo":""}]
     * status : 200
     */

    private String msg;
    private String status;
    private List<DataListBean> dataList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 04c6533a90ef46e782dcb7cdedd540da
         * companyName : cc测试企业我
         * creatorName : 未知
         * industryName : 运营
         * membersNum : 1
         * applyState : 0
         * distributorLogo :
         */

        private String id;
        private String companyName;
        private String creatorName;
        private String industryName;
        private int membersNum;
        private int applyState;
        private String distributorLogo;

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

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public int getMembersNum() {
            return membersNum;
        }

        public void setMembersNum(int membersNum) {
            this.membersNum = membersNum;
        }

        public int getApplyState() {
            return applyState;
        }

        public void setApplyState(int applyState) {
            this.applyState = applyState;
        }

        public String getDistributorLogo() {
            return distributorLogo;
        }

        public void setDistributorLogo(String distributorLogo) {
            this.distributorLogo = distributorLogo;
        }
    }
}
