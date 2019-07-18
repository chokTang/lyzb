package com.lyzb.jbx.model.me;

import java.util.List;

/**
 * @author wyx
 * @role 圈子成员 申请列表
 * @time 2019 2019/3/22 9:15
 */

public class ApplyListModel {


    /**
     * pageNum : 1
     * pageSize : 10
     * total : 6
     * pages : 1
     * list : [{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":6,"groupId":"77070554169345","userId":"15337","pass":false,"addTime":"2019-03-14 14:16:35","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png"},{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":8,"groupId":"77070554169345","userId":"103959","pass":false,"addTime":"2019-03-19 16:13:15","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png"},{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":11,"groupId":"77070554169345","userId":"67743","pass":false,"addTime":"2019-03-07 14:17:25","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png"},{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":12,"groupId":"77070554169345","userId":"67899","pass":false,"addTime":"2019-03-07 14:17:55","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png"},{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":13,"groupId":"77070554169345","userId":"68430","pass":false,"addTime":"2019-03-15 14:17:59","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png"},{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":14,"groupId":"77070554169345","userId":"67796","pass":false,"addTime":"2019-03-09 14:18:02","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png"}]
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<ListBean> list;

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

    public class ListBean {
        /**
         * pageNum : 1
         * extPageSize : 30
         * childPageSize : 1
         * id : 6
         * groupId : 77070554169345
         * userId : 15337
         * pass : false
         * addTime : 2019-03-14 14:16:35
         * headimg : http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png
         */

        private int pageNum;
        private int extPageSize;
        private int childPageSize;
        private String id;
        private String groupId;
        private String userId;
        /**
         * 1通过，0拒绝，3申请中
         */
        private int pass;
        private String addTime;

        private String userName;
        private String companyInfo;
        private String headimg;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        /**
         * 1通过，0拒绝，3申请中
         */
        public int getPass() {
            return pass;
        }

        public void setPass(int pass) {
            this.pass = pass;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }
}
