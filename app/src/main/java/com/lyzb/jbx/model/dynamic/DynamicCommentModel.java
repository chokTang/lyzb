package com.lyzb.jbx.model.dynamic;

import com.lyzb.jbx.model.UserVipActionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/18  11:00
 * Desc:
 */
public class DynamicCommentModel {


    /**
     * pageNum : 1
     * pageSize : 5
     * total : 7
     * pages : 2
     * list : [{"id":"0e71ed29510445069d7b3a4478df2ebc","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":15624,"replyId":"0","createDate":"2019-03-20 17:48:01","type":0,"showStatus":"1","content":"你在我面前是那么幸福了","userName":"孙尚香","companyInfo":"尚尚经销商","giveNum":0,"giveLike":0,"fileVoList":[],"userActionVos":[]},{"id":"4ea78f52d29e484190e15be5a23b1c9d","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":15624,"replyId":"0","createDate":"2019-03-20 18:51:56","type":0,"showStatus":"1","content":"你们在哪里玩吗\u2026\u2026","userName":"孙尚香","companyInfo":"尚尚经销商","giveNum":0,"giveLike":0,"fileVoList":[],"chiledrenList":[{"id":"6e75787261bb4c9eb8d9f2cc5b4175a4","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":109922,"replyId":"4ea78f52d29e484190e15be5a23b1c9d","createDate":"2019-03-21 16:18:05","type":1,"showStatus":"1","content":"发送","userName":"吕布","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg","giveNum":0,"userActionVos":[{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"vip1","imageUrl":"http://img1.jpg"}]}],"userActionVos":[]},{"id":"585e5c188268421186fcff29eccb8110","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":15624,"replyId":"0","createDate":"2019-03-20 17:13:25","type":0,"showStatus":"1","content":"你的孩子在哪儿买什么时候","userName":"孙尚香","companyInfo":"尚尚经销商","giveNum":1,"giveLike":1,"fileVoList":[],"chiledrenList":[{"id":"d4e1d0d24fe24abca56fb1426c2f9f71","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":15624,"replyId":"585e5c188268421186fcff29eccb8110","createDate":"2019-03-20 17:16:24","type":1,"showStatus":"1","content":"石人山呜呜呜呜呜","userName":"孙尚香","companyInfo":"尚尚经销商","giveNum":0,"userActionVos":[]}],"userActionVos":[]},{"id":"6e75787261bb4c9eb8d9f2cc5b4175a4","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":109922,"replyId":"4ea78f52d29e484190e15be5a23b1c9d","createDate":"2019-03-21 16:18:05","type":1,"showStatus":"1","content":"发送","userName":"吕布","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg","giveNum":0,"giveLike":0,"fileVoList":[],"userActionVos":[{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"vip1","imageUrl":"http://img1.jpg"}]},{"id":"77dbb2261437464fa252e2adf8c3eaa7","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":15624,"replyId":"0","createDate":"2019-03-20 18:58:15","type":0,"showStatus":"1","content":"测试xeahi","userName":"孙尚香","companyInfo":"尚尚经销商","giveNum":0,"giveLike":0,"fileVoList":[],"userActionVos":[]}]
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

    public static class ListBean {
        /**
         * id : 0e71ed29510445069d7b3a4478df2ebc
         * topicId : ace039b94ade11e99aa6e0d55e132c77
         * userId : 15624
         * replyId : 0
         * createDate : 2019-03-20 17:48:01
         * type : 0
         * showStatus : 1
         * content : 你在我面前是那么幸福了
         * userName : 孙尚香
         * companyInfo : 尚尚经销商
         * giveNum : 0
         * giveLike : 0
         * fileVoList : []
         * userActionVos : []
         * chiledrenList : [{"id":"6e75787261bb4c9eb8d9f2cc5b4175a4","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":109922,"replyId":"4ea78f52d29e484190e15be5a23b1c9d","createDate":"2019-03-21 16:18:05","type":1,"showStatus":"1","content":"发送","userName":"吕布","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg","giveNum":0,"userActionVos":[{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"vip1","imageUrl":"http://img1.jpg"}]}]
         * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg
         */

        private String id;
        private String topicId;
        private String userId;
        private String replyId;
        private String createDate;
        private int type;
        private String showStatus;
        private String content;
        private String userName;
        private String companyInfo;
        private int giveNum;
        private int giveLike;
        private String headimg;
        private List<FileVoList> fileVoList;
        private List<UserVipActionModel> userActionVos;
        private List<ChiledrenListBean> chiledrenList;

        public List<UserVipActionModel> getUserActionVos() {
            if (null == userActionVos) return new ArrayList<>();
            return userActionVos;
        }

        public void setUserActionVos(List<UserVipActionModel> userActionVos) {
            this.userActionVos = userActionVos;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getShowStatus() {
            return showStatus;
        }

        public void setShowStatus(String showStatus) {
            this.showStatus = showStatus;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getGiveNum() {
            return giveNum;
        }

        public void setGiveNum(int giveNum) {
            this.giveNum = giveNum;
        }

        public int getGiveLike() {
            return giveLike;
        }

        public void setGiveLike(int giveLike) {
            this.giveLike = giveLike;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public List<FileVoList> getFileVoList() {
            if (null == fileVoList) return new ArrayList<>();
            return fileVoList;
        }

        public void setFileVoList(List<FileVoList> fileVoList) {
            this.fileVoList = fileVoList;
        }


        public List<ChiledrenListBean> getChiledrenList() {
            if (null == chiledrenList) return new ArrayList<>();
            return chiledrenList;
        }

        public void setChiledrenList(List<ChiledrenListBean> chiledrenList) {
            this.chiledrenList = chiledrenList;
        }

        public static class ChiledrenListBean {
            /**
             * id : 6e75787261bb4c9eb8d9f2cc5b4175a4
             * topicId : ace039b94ade11e99aa6e0d55e132c77
             * userId : 109922
             * replyId : 4ea78f52d29e484190e15be5a23b1c9d
             * createDate : 2019-03-21 16:18:05
             * type : 1
             * showStatus : 1
             * content : 发送
             * userName : 吕布
             * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg
             * giveNum : 0
             * userActionVos : [{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"vip1","imageUrl":"http://img1.jpg"}]
             */

            private String id;
            private String topicId;
            private String companyInfo;
            private String userId;
            private String replyId;
            private String createDate;
            private int type;
            private String showStatus;
            private String content;
            private String userName;
            private String headimg;
            private int giveNum;
            private List<UserVipActionModel> userActionVos;

            public String getCompanyInfo() {
                return companyInfo;
            }

            public void setCompanyInfo(String companyInfo) {
                this.companyInfo = companyInfo;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTopicId() {
                return topicId;
            }

            public void setTopicId(String topicId) {
                this.topicId = topicId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getReplyId() {
                return replyId;
            }

            public void setReplyId(String replyId) {
                this.replyId = replyId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getShowStatus() {
                return showStatus;
            }

            public void setShowStatus(String showStatus) {
                this.showStatus = showStatus;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public int getGiveNum() {
                return giveNum;
            }

            public void setGiveNum(int giveNum) {
                this.giveNum = giveNum;
            }

            public List<UserVipActionModel> getUserActionVos() {
                return userActionVos;
            }

            public void setUserActionVos(List<UserVipActionModel> userActionVos) {
                this.userActionVos = userActionVos;
            }


        }


        public static class FileVoList {
            public String createTime;
            public String file;
            public String id;
            public String topicId;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTopicId() {
                return topicId;
            }

            public void setTopicId(String topicId) {
                this.topicId = topicId;
            }
        }
    }
}
