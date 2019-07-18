package com.lyzb.jbx.model.me.company;

import java.util.List;

public class OrganNenberModel {


    /**
     * msg : 查询成功
     * code : 200
     * data : [{"headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg","gsName":"杨东风","position":"总监","roleType":"1","userId":110778},{"headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg","gsName":"杨东风","position":"总监","roleType":"1","userId":110778}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg
         * gsName : 杨东风
         * position : 总监
         * roleType : 1
         * userId : 110778
         */

        private String headimg;
        private String gsName;
        private String position;
        private String roleType;
        private String userId;
        private boolean selection;

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getGsName() {
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        /**
         * 角色类型（1.创建者 2.职员 3.管理员 4.负责人）
         */
        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isSelection() {
            return selection;
        }

        public void setSelection(boolean selection) {
            this.selection = selection;
        }
    }
}
