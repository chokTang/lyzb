package com.lyzb.jbx.model.me.company;

import java.util.List;

/**
 * 机构详情（企业详情）
 */
public class OrganDetailModel {

    /**
     * msg : 查询成功
     * code : 200
     * data : {"id":"ef9e9cb75fa44c98a2437c48b4bacce6","companyName":"6月测试新增企业_1","logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/04/01/5B587BC1-2498-4010-AAB2-3DA80FEE5446.jpeg","industryName":"快速消费品","membersNum":1,"address":"重庆","telPhone":"123456","selectSt":false,"orgList":[{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"}],"membersVoList":[{"gsName":"xxxx","extId":7643,"position":"boss","headimg":"https://wx.qlogo.cn/mmopen/vi_32/DEqsKbjAcR20FG5tHhSga8RgHFPeTbib810QSNJP0Tw35kc2fWGgric2w0VjuicjPuTHEoGUMrghV9NZL3EcLHw0g/132","role":1,"companyId":"ef9e9cb75fa44c98a2437c48b4bacce6","userId":110689}]}
     * showSetBtn : true
     * showEditBtn : true
     */

    private String msg;
    private String code;
    private DataBean data;
    /**
     * 是否展示“设为当前机构”
     */
    private boolean showSetBtn;
    /**
     * 是否展示“修改机构信息按钮”、是否展示修改成员按钮
     */
    private boolean showEditBtn;

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

    public boolean isShowSetBtn() {
        return showSetBtn;
    }

    public void setShowSetBtn(boolean showSetBtn) {
        this.showSetBtn = showSetBtn;
    }

    public boolean isShowEditBtn() {
        return showEditBtn;
    }

    public void setShowEditBtn(boolean showEditBtn) {
        this.showEditBtn = showEditBtn;
    }

    public class DataBean {
        /**
         * id : ef9e9cb75fa44c98a2437c48b4bacce6
         * companyName : 6月测试新增企业_1
         * logoFilePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/04/01/5B587BC1-2498-4010-AAB2-3DA80FEE5446.jpeg
         * industryName : 快速消费品
         * membersNum : 1
         * address : 重庆
         * telPhone : 123456
         * selectSt : false
         * orgList : [{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":1,"parentId":"0"}]
         * membersVoList : [{"gsName":"xxxx","extId":7643,"position":"boss","headimg":"https://wx.qlogo.cn/mmopen/vi_32/DEqsKbjAcR20FG5tHhSga8RgHFPeTbib810QSNJP0Tw35kc2fWGgric2w0VjuicjPuTHEoGUMrghV9NZL3EcLHw0g/132","role":1,"companyId":"ef9e9cb75fa44c98a2437c48b4bacce6","userId":110689}]
         */

        /**
         * 企业、机构id
         */
        private String id;
        /**
         * 企业、机构名称
         */
        private String companyName;
        /**
         * logo
         */
        private String logoFilePath;
        /**
         * 行业
         */
        private String industryName;
        /**
         * 成员数量
         */
        private int membersNum;
        /**
         * 地址
         */
        private String address;
        /**
         * 电话
         */
        private String telPhone;
        /**
         * 是否已选择“设为当前机构”
         */
        private boolean selectSt;
        /**
         * 上级机构id
         */
        private String parentOrgId;
        /**
         * 圈子id
         */
        private String groupId;
        /**
         * 类型（1.企业 2.部门）
         */
        private String disType;
        /**
         * 所属企业
         */
        private String parentOrgName;
        /**
         * 创建人id
         */
        private String creatorId;

        private List<CompanyModel> companyList;
        /**
         * 机构列表
         */
        private List<OrganTowLvModel> orgList;
        /**
         * 成员数据集
         */
        private List<StaffModel> membersVoList;
        /**
         * 按钮集
         */
        private List<AuthListBean> authList;

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

        public String getLogoFilePath() {
            return logoFilePath;
        }

        public void setLogoFilePath(String logoFilePath) {
            this.logoFilePath = logoFilePath;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelPhone() {
            return telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public boolean isSelectSt() {
            return selectSt;
        }

        public void setSelectSt(boolean selectSt) {
            this.selectSt = selectSt;
        }

        public String getParentOrgId() {
            return parentOrgId == null ? "0" : parentOrgId;
        }

        public void setParentOrgId(String parentOrgId) {
            this.parentOrgId = parentOrgId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getDisType() {
            return disType;
        }

        public void setDisType(String disType) {
            this.disType = disType;
        }

        public String getParentOrgName() {
            return parentOrgName;
        }

        public void setParentOrgName(String parentOrgName) {
            this.parentOrgName = parentOrgName;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public List<CompanyModel> getCompanyList() {
            return companyList;
        }

        public void setCompanyList(List<CompanyModel> companyList) {
            this.companyList = companyList;
        }

        public List<OrganTowLvModel> getOrgList() {
            return orgList;
        }

        public void setOrgList(List<OrganTowLvModel> orgList) {
            this.orgList = orgList;
        }

        public List<StaffModel> getMembersVoList() {
            return membersVoList;
        }

        public void setMembersVoList(List<StaffModel> membersVoList) {
            this.membersVoList = membersVoList;
        }

        public List<AuthListBean> getAuthList() {
            return authList;
        }

        public void setAuthList(List<AuthListBean> authList) {
            this.authList = authList;
        }

        public class AuthListBean {
            /**
             * 图标
             */
            private String class1;
            /**
             * 图标
             */
            private String class2;
            /**
             * 前后台功能标志（ios，andorid，mini（小程序），backPC）
             */
            private String class3;
            /**
             * 按钮名称
             */
            private String display;
            /**
             * 是否显示（Y：显示；N：不显示）
             */
            private String enabled;
            /**
             * 序号
             */
            private String xh;
            /**
             *
             */
            private String code;
            /**
             *
             */
            private String type;

            /**
             * H5或小程序时的跳转
             */
            private String pathAndorid;

            private int messageNumber;
            private List<AuthListBean> childResource;

            public String getClass1() {
                return class1;
            }

            public void setClass1(String class1) {
                this.class1 = class1;
            }

            public String getClass2() {
                return class2;
            }

            public void setClass2(String class2) {
                this.class2 = class2;
            }

            public String getClass3() {
                return class3;
            }

            public void setClass3(String class3) {
                this.class3 = class3;
            }

            public String getDisplay() {
                return display;
            }

            public void setDisplay(String display) {
                this.display = display;
            }

            public String getEnabled() {
                return enabled;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
            }

            public String getXh() {
                return xh;
            }

            public void setXh(String xh) {
                this.xh = xh;
            }

            public int getMessageNumber() {
                return messageNumber;
            }

            public void setMessageNumber(int messageNumber) {
                this.messageNumber = messageNumber;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPathAndroid() {
                return pathAndorid;
            }

            public void setPathAndroid(String pathAndroid) {
                this.pathAndorid = pathAndroid;
            }

            public List<AuthListBean> getChildResource() {
                return childResource;
            }

            public void setChildResource(List<AuthListBean> childResource) {
                this.childResource = childResource;
            }
        }
    }
}
