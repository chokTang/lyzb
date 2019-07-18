package com.lyzb.jbx.model.dynamic;

import android.text.TextUtils;

import com.lyzb.jbx.model.UserVipActionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/21  15:51
 * Desc:
 */
public class AddCommentOrReplyModel {

    /**
     * fileList : [{"createtime":"2019-03-06 17:14:20","file":"filepath","fileType":1,"id":"35844785a7344406b526efd00ceac705","sort":0,"topicId":"00760ea444284e6fbfe57a109242354b"},{"createtime":"2019-03-06 17:14:20","file":"filepath2","fileType":1,"id":"4b90f3c27f4749578dfc9a453c004bbc","sort":1,"topicId":"00760ea444284e6fbfe57a109242354b"},{"createtime":"2019-03-06 17:14:20","file":"filepath3","fileType":1,"id":"a8116074150048d3b35513ff43f86026","sort":2,"topicId":"00760ea444284e6fbfe57a109242354b"}]
     * gsTopicComment : {"content":"plcontent","createDate":"2019-03-06 17:14:20","id":"a7b536ad81c740f8b0e15faa55fa1b6d","topicId":"00760ea444284e6fbfe57a109242354b","userId":32801}
     */

    private GsTopicCommentBean gsTopicComment;
    private List<FileListBean> fileList;
    private List<UserVipActionModel> userVipAction;
    private GsUserExt gsUserExt;


    public GsTopicCommentBean getGsTopicComment() {
        return gsTopicComment;
    }

    public void setGsTopicComment(GsTopicCommentBean gsTopicComment) {
        this.gsTopicComment = gsTopicComment;
    }

    public List<FileListBean> getFileList() {
        if (fileList == null) return new ArrayList<>();
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public List<UserVipActionModel> getUserVipAction() {
        return userVipAction;
    }

    public void setUserVipAction(List<UserVipActionModel> userVipAction) {
        this.userVipAction = userVipAction;
    }

    public GsUserExt getGsUserExt() {
        return gsUserExt;
    }

    public void setGsUserExt(GsUserExt gsUserExt) {
        this.gsUserExt = gsUserExt;
    }

    public static class GsTopicCommentBean {
        /**
         * content : plcontent
         * createDate : 2019-03-06 17:14:20
         * id : a7b536ad81c740f8b0e15faa55fa1b6d
         * topicId : 00760ea444284e6fbfe57a109242354b
         * userId : 32801
         */

        private String content;
        private String createDate;
        private String id;
        private String topicId;
        private String replyId;
        private String userId;
        private int type;

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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
    }

    public static class FileListBean {
        /**
         * createtime : 2019-03-06 17:14:20
         * file : filepath
         * fileType : 1
         * id : 35844785a7344406b526efd00ceac705
         * sort : 0
         * topicId : 00760ea444284e6fbfe57a109242354b
         */

        private String createtime;
        private String file;
        private int fileType;
        private String id;
        private int sort;
        private String topicId;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }
    }


    public static class GsUserExt {

        /**
         * id : 7081
         * userId : 109922
         * oftenToPace :
         * residence : 50;50,01;50,01,16
         * hometown :
         * concernProfession : 3
         * professionId : 3,9
         * interest : 喝酒,打麻将,斗地主,抽烟
         * position : 丁敏
         * companyInfo :
         * mapLng : 0.000000
         * mapLat : 0.000000
         * unreadComment : 0
         * unreadZan : 0
         * unreadReply : 0
         * gsName : 吕布
         * shopName : 明敏
         * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg
         * sex : 1
         * vipType : 1
         */

        private int id;
        private int userId;
        private String oftenToPace;
        private String residence;
        private String hometown;
        private String concernProfession;
        private String professionId;
        private String interest;
        private String position;
        private String companyInfo;
        private String mapLng;
        private String mapLat;
        private int unreadComment;
        private int unreadZan;
        private int unreadReply;
        private String gsName;
        private String shopName;
        private String headimg;
        private int sex;
        private int vipType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getOftenToPace() {
            return oftenToPace;
        }

        public void setOftenToPace(String oftenToPace) {
            this.oftenToPace = oftenToPace;
        }

        public String getResidence() {
            return residence;
        }

        public void setResidence(String residence) {
            this.residence = residence;
        }

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getConcernProfession() {
            return concernProfession;
        }

        public void setConcernProfession(String concernProfession) {
            this.concernProfession = concernProfession;
        }

        public String getProfessionId() {
            return professionId;
        }

        public void setProfessionId(String professionId) {
            this.professionId = professionId;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }

        public String getMapLng() {
            return mapLng;
        }

        public void setMapLng(String mapLng) {
            this.mapLng = mapLng;
        }

        public String getMapLat() {
            return mapLat;
        }

        public void setMapLat(String mapLat) {
            this.mapLat = mapLat;
        }

        public int getUnreadComment() {
            return unreadComment;
        }

        public void setUnreadComment(int unreadComment) {
            this.unreadComment = unreadComment;
        }

        public int getUnreadZan() {
            return unreadZan;
        }

        public void setUnreadZan(int unreadZan) {
            this.unreadZan = unreadZan;
        }

        public int getUnreadReply() {
            return unreadReply;
        }

        public void setUnreadReply(int unreadReply) {
            this.unreadReply = unreadReply;
        }

        public String getGsName() {
            if (TextUtils.isEmpty(gsName)) return "";
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }
    }
}
