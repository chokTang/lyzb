package com.lyzb.jbx.model.dynamic;

import com.lyzb.jbx.model.send.GoodsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/15  14:39
 * Desc:
 */
public class DynamicDetailModel {

    /**
     * collect : 1
     * commentCount : 6
     * companyInfo : 中国宗亲
     * concern : 0
     * content : 【寻商机】宗亲们：有做城市基础设施及道路工程项目的公司没有？现四川乐山有一个PPP项目，工程报价5亿，可以分5个标段合作，即每标段1亿。有做这样工程的有实力的宗亲请私下微信联系我，我只是中间人，可以帮助你拿下工程项目。
     * createDate : 2015-09-14 23:42:33
     * createMan : 8042
     * groupId : 70248423292930
     * groupLogo : s.img
     * groupName : 管-团-业-个人零售名称[已关注]
     * headimg : 头像
     * id : 00760ea444284e6fbfe57a109242354b
     * isShow : 1
     * place :
     * shareCount : 1
     * showRange : 1
     * showWord : 谢谢宗亲
     * status : 2
     * title : 测试文章
     * topicFileList : [{"createTime":"2015-11-25 16:48:28","file":"/upload/topic/c1ede5fc7b7f4720b2a8fb748ffeed3f.png","id":"00290d517b9247cb92e6b37e434a3932","topicId":"00760ea444284e6fbfe57a109242354b"}]
     * type : 8
     * upCount : 0
     * userName : JSH13354999092893
     * viewCount : 0
     * vipType : 0
     */

    private int collect;
    private int commentCount;
    private String companyInfo;
    private int concern;
    private String content;
    private int giveLike;
    private String createDate;
    private String createMan;
    private String groupId;
    private String groupLogo;
    private String groupName;
    private String headimg;
    private String comAaccount;
    private String id;
    private String isShow;
    private String place;
    private int shareCount;
    private String showRange;
    private String showWord;
    private int status;
    private String title;
    private String type;
    private int upCount;
    private String userName;
    private int viewCount;
    private int vipType;
    private String shopName;
    private String shopAddress;
    private List<TopicFileListBean> topicFileList;
    private List<UserActionVos> userActionVos;
    private List<IntroductionAudioFile> introductionAudioFile;
    private GoodsList goodsList;

    public GoodsList getGoodsList() {
        if (null == goodsList) return new GoodsList();
        return goodsList;
    }

    public void setGoodsList(GoodsList goodsList) {
        this.goodsList = goodsList;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public List<IntroductionAudioFile> getIntroductionAudioFile() {
        return introductionAudioFile;
    }

    public void setIntroductionAudioFile(List<IntroductionAudioFile> introductionAudioFile) {
        this.introductionAudioFile = introductionAudioFile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<UserActionVos> getUserActionVos() {
        if (null == userActionVos) return new ArrayList<>();
        return userActionVos;
    }

    public void setUserActionVos(List<UserActionVos> userActionVos) {
        this.userActionVos = userActionVos;
    }

    public String getComAaccount() {
        return comAaccount;
    }

    public void setComAaccount(String comAaccount) {
        this.comAaccount = comAaccount;
    }

    public int getGiveLike() {
        return giveLike;
    }

    public void setGiveLike(int giveLike) {
        this.giveLike = giveLike;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
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

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        this.groupLogo = groupLogo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getShowRange() {
        return showRange;
    }

    public void setShowRange(String showRange) {
        this.showRange = showRange;
    }

    public String getShowWord() {
        return showWord;
    }

    public void setShowWord(String showWord) {
        this.showWord = showWord;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public List<TopicFileListBean> getTopicFileList() {
        if (null == topicFileList) return new ArrayList<>();
        return topicFileList;
    }

    public void setTopicFileList(List<TopicFileListBean> topicFileList) {
        this.topicFileList = topicFileList;
    }

    public static class TopicFileListBean {
        /**
         * createTime : 2015-11-25 16:48:28
         * file : /upload/topic/c1ede5fc7b7f4720b2a8fb748ffeed3f.png
         * id : 00290d517b9247cb92e6b37e434a3932
         * topicId : 00760ea444284e6fbfe57a109242354b
         */

        private String createTime;
        private String class1;
        private String file;
        private String id;
        private String topicId;

        public String getClass1() {
            return class1;
        }

        public void setClass1(String class1) {
            this.class1 = class1;
        }

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


    public static class UserActionVos {


        /**
         * id : 2318
         * actionId : 4
         * groupId : 0
         * userId : 17891
         * addTime : 2019-03-27 00:00:00
         * startDate : 2019-03-27 00:00:00
         * endDate : 2022-03-26 00:00:00
         * actionName : 一年服务
         * imageUrl : http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E6%99%BA%E8%83%BD%E5%90%8D%E7%89%87/%E5%A5%97%E9%A4%90%E5%9B%BE%E7%89%87/znmp@2x.png
         */

        private int id;
        private int actionId;
        private String groupId;
        private int userId;
        private String addTime;
        private String startDate;
        private String endDate;
        private String actionName;
        private String imageUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class IntroductionAudioFile {
        private String filePath;
        private String fileType;//1：图片 2：视频 3:音频
        private String tagType;//1：简介 2：个人荣誉 3：提供 4：需求 5 名片相册 6：个人信息语音
        private String fileLength;//时长

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getTagType() {
            return tagType;
        }

        public void setTagType(String tagType) {
            this.tagType = tagType;
        }

        public String getFileLength() {
            return fileLength;
        }

        public void setFileLength(String fileLength) {
            this.fileLength = fileLength;
        }
    }


}
