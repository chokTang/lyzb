package com.lyzb.jbx.model.me;

// FIXME generate failure  field _$IntroductionImgFile2246


import android.text.TextUtils;

import com.like.utilslib.app.CommonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 智能名片 model
 * @time 2019 2019/3/14 14:03
 */

public class CardModel implements Serializable {


    /**
     * companyInfo : 重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司重庆小胖子科技有限公司
     * concernProfession : 1,9
     * concernProfessionName : 运营,消费服务
     * currentSurnameId : 115
     * education :
     * experience :
     * familyTree : 苍茫的天涯是我的爱连绵的青山脚下花正开什么样的节奏是最呀最摇摆什么样的歌声才是最开怀弯弯的河水从天上来留下那万紫千红一片海火辣辣的歌谣是我们的期待一句边走边唱才是最自在我们要唱就要唱的最痛快你是我天边最美的云彩让我用心把你留下来悠悠的唱着最炫的民族风让爱卷走所有的尘埃
     * generation : 天
     * gsName : 测试2
     * headimg : 测试2
     * hometown : 1,32,394,3325
     * honorImgFile : [{"createTime":"2019-03-07 20:22:10","delStatus":0,"filePath":"path","fileType":2,"id":6,"tagType":1,"userId":8589}]
     * id : 536
     * interest :
     * introduction : ggupdate22222
     * introductionImgFile|2 : [{}]
     * introductionVideoFile : [{"createTime":"2019-03-07 20:22:10","delStatus":0,"filePath":"path","fileType":2,"id":6,"tagType":1,"userId":8589}]
     * mapLat : 29.589993
     * mapLng : 106.539619
     * myDemand :
     * myDemandImgFile : [{"createTime":"2019-03-07 20:22:10","delStatus":0,"filePath":"path","fileType":2,"id":6,"tagType":1,"userId":8589}]
     * myResources :
     * myResourcesImgFile : [{"createTime":"2019-03-07 20:22:10","delStatus":0,"filePath":"path","fileType":2,"id":6,"tagType":1,"userId":8589}]
     * myViewUserList : [{"gsName":"测试2","headimg":"测试2","operDate":"2019-03-06 11:54:19","userId":8589}]
     * oftenToPace : 1,32
     * position : 软件测试
     * professionId : 5
     * professionName : IT/互联网
     * relation : 0
     * relationFs : 16
     * relationGz : 49
     * residence : 1,2
     * residenceCN : 1
     * sex : 1
     * shopName : 测试2商家
     * topicCount : 0
     * unreadComment : 0
     * unreadReply : 0
     * unreadZan : 0
     * upCount : 0
     * userId : 8589
     * viewCount : 1
     * vipType : 0
     * wxImg : 1
     */


    private String companyInfo;             //公司名称
    private String concernProfession;       //感兴趣的行业id
    private String concernProfessionName;   //感兴趣的行业名称


    public String templateId = "1";//名片模板ID

    private String currentDepartmentID;     //当前用户选择的企业id
    private String distributorCreatorID;   //当前企业创建者的id

    private String currentSurnameId;
    private String education;               //毕业院校
    private String experience;
    private String familyTree;
    private String generation;
    private String gsName;                  //姓名
    private String headimg;                 //头像
    private String posterImg;                 //海报
    private String shopLogo;                 //企业logo  （个人）
    private String hometown;
    private String id;                      //名片id
    private String interest;                //兴趣爱好
    private String residence;

    private String introduction;            //简介信息
    private String mapLat;                  //纬度
    private String mapLng;                  //经度
    private String mobile;                  //手机号码
    private String myDemand;                //我的需求
    private String myResources;             //供应信息
    private String oftenToPace;                 //来往城市
    private String position;                //担任职位
    private String professionId;            //熟悉领域id
    private String professionName;          //熟悉领域名称
    private int relation;                   //是否已关注
    private int relationFs;                 //粉丝数量
    private int relationGz;                 //关注数目
    private String residenceCN;             //来自城市中文
    private int sex;                        //性别(1.先生 2.女士)

    private String roleName;                //身份类型
    private String showInfo;//1 是否公开自己的资料 1：公开 0：不公开

    private String shopAddress;             //商家地址
    private String shopName;                //商家名称

    private boolean shopLocalService;

    private int topicCount;                 //动态数量(发表数)
    private int collectionCount;            //收藏数目
    private String companyAddress;          //商家地址
    private int unreadComment;
    private int unreadReply;
    private int unreadZan;
    private int upCount;                    //点赞数目
    private int upCountStatus;              //点赞状态 大于0表示已点赞
    private String userId;                     //名片userId
    private int viewCount;                  //浏览数
    private int vipType;                    //vip类型 0:非缴费用户
    private String wxImg;                      //微信二维码url
    private String tel;                      //座机
    private String email;                      //email
    private String wxNum;                      //微信号

    private String integralNum;             //成长长度
    private String integralImg;             //成长图片

    private boolean shopIsBuy;                  //该用户上传的商品的购买权限

    private List<CardItemInfoModel> introductionImgFile;                       //个人信息图片集合
    private List<IntroductionContent> introductionContent;                       // 个人简介   图片文字信息集合
    private List<IntroductionContent> myResourcesContent;                       //我能提供的 图片文字信息集合
    private List<IntroductionContent> myDemandContent;                       //我需要的 图片文字信息集合
    private List<CustomModular> customModular;                       //我需要的 图片文字信息集合
    private List<HonorImgFileBean> honorImgFile;                            //荣誉图片集合
    private List<IntroductionVideoFileBean> introductionVideoFile;          //简介视频信息集合
    private List<IntroductionAudioFileBean> introductionAudioFile;          //简介视频信息集合，个人信息音频文件(说明同 introductionImgFile)

    private List<CardItemInfoModel> myDemandImgFile;               //我的需求图片集合
    private List<CardItemInfoModel> myResourcesImgFile;          //供应信息图片

    private List<VipList> userVipAction;                        //会员标识

    private List<MyViewUserListBean> myViewUserList;              //浏览该名片的用户头像集合
    private List<TabShowHideBean> gsCardFunctionSetList;              //栏目列表  tab

    private Boolean userIsEnterpriseMembers;//用户是否为团购企业成员
    private Boolean userIsEnterpriseManager;//用户是否为团购企业管理员
    private String distributorLogo;//企业logo
    private String companyName;//企业名字

    private int perfectRatio;//完善度
    private String perfectStr;//完善度说明

    private Modular introductionModular;
    private Modular myResourcesModular;
    private Modular myDemandModular;
    private String groupId;


    public String getGroupId() {
        if (null == groupId) return "";
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Modular getIntroductionModular() {
        if (introductionModular==null) return new Modular();
        return introductionModular;
    }

    public void setIntroductionModular(Modular introductionModular) {
        this.introductionModular = introductionModular;
    }

    public Modular getMyResourcesModular() {
        if (myResourcesModular==null) return new Modular();
        return myResourcesModular;
    }

    public void setMyResourcesModular(Modular myResourcesModular) {
        this.myResourcesModular = myResourcesModular;
    }

    public Modular getMyDemandModular() {
        if (myDemandModular==null) return new Modular();
        return myDemandModular;
    }

    public void setMyDemandModular(Modular myDemandModular) {
        this.myDemandModular = myDemandModular;
    }

    public List<CustomModular> getCustomModular() {
        if (customModular == null) return new ArrayList<>();
        return customModular;
    }

    public void setCustomModular(List<CustomModular> customModular) {
        this.customModular = customModular;
    }

    public List<IntroductionContent> getMyResourcesContent() {
        if (null == myResourcesContent) return new ArrayList<>();
        return myResourcesContent;
    }

    public void setMyResourcesContent(List<IntroductionContent> myResourcesContent) {
        this.myResourcesContent = myResourcesContent;
    }

    public List<IntroductionContent> getMyDemandContent() {
        if (null == myDemandContent) return new ArrayList<>();
        return myDemandContent;
    }

    public void setMyDemandContent(List<IntroductionContent> myDemandContent) {
        this.myDemandContent = myDemandContent;
    }

    public List<IntroductionContent> getIntroductionContent() {
        if (null == introductionContent) return new ArrayList<>();
        return introductionContent;
    }

    public void setIntroductionContent(List<IntroductionContent> introductionContent) {
        this.introductionContent = introductionContent;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getTemplateId() {
        if (templateId == null) return "1";
        return templateId;
    }


    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public List<TabShowHideBean> getGsCardFunctionSetList() {
        if (null == gsCardFunctionSetList) return new ArrayList<>();
        return gsCardFunctionSetList;
    }

    public void setGsCardFunctionSetList(List<TabShowHideBean> gsCardFunctionSetList) {
        this.gsCardFunctionSetList = gsCardFunctionSetList;
    }

    public Boolean getUserIsEnterpriseMembers() {
        return userIsEnterpriseMembers;
    }

    public void setUserIsEnterpriseMembers(Boolean userIsEnterpriseMembers) {
        this.userIsEnterpriseMembers = userIsEnterpriseMembers;
    }

    public Boolean getUserIsEnterpriseManager() {
        return userIsEnterpriseManager;
    }

    public void setUserIsEnterpriseManager(Boolean userIsEnterpriseManager) {
        this.userIsEnterpriseManager = userIsEnterpriseManager;
    }

    public String getDistributorLogo() {
        if (distributorLogo == null) return "";
        return distributorLogo;
    }

    public void setDistributorLogo(String distributorLogo) {
        this.distributorLogo = distributorLogo;
    }

    public int getPerfectRatio() {
        return perfectRatio;
    }

    public void setPerfectRatio(int perfectRatio) {
        this.perfectRatio = perfectRatio;
    }

    public String getPerfectStr() {
        return perfectStr;
    }

    public void setPerfectStr(String perfectStr) {
        this.perfectStr = perfectStr;
    }

    public List<IntroductionAudioFileBean> getIntroductionAudioFile() {
        if (null == introductionAudioFile) return new ArrayList<>();
        return introductionAudioFile;
    }

    public void setIntroductionAudioFile(List<IntroductionAudioFileBean> introductionAudioFile) {
        this.introductionAudioFile = introductionAudioFile;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getConcernProfession() {
        return concernProfession;
    }

    public void setConcernProfession(String concernProfession) {
        this.concernProfession = concernProfession;
    }

    public String getConcernProfessionName() {
        return concernProfessionName;
    }

    public void setConcernProfessionName(String concernProfessionName) {
        this.concernProfessionName = concernProfessionName;
    }

    public String getCurrentDepartmentID() {
        if (TextUtils.isEmpty(currentDepartmentID)) return "";
        return currentDepartmentID;
    }

    public String getDistributorCreatorID() {
        if (TextUtils.isEmpty(distributorCreatorID)) return "";
        return distributorCreatorID;
    }

    public void setDistributorCreatorID(String distributorCreatorID) {
        this.distributorCreatorID = distributorCreatorID;
    }

    public boolean isShopLocalService() {
        return shopLocalService;
    }

    public void setShopLocalService(boolean shopLocalService) {
        this.shopLocalService = shopLocalService;
    }

    public void setCurrentDepartmentID(String currentDepartmentID) {
        this.currentDepartmentID = currentDepartmentID;
    }

    public String getCurrentSurnameId() {
        return currentSurnameId;
    }

    public void setCurrentSurnameId(String currentSurnameId) {
        this.currentSurnameId = currentSurnameId;
    }

    public String getMobile() {
        if (null == mobile) return "";
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getEducation() {
        if (null == education) return "";
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(String familyTree) {
        this.familyTree = familyTree;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public String getHeadimg() {
        if (null == headimg) return "";
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterest() {
        return removeDoublue(interest);
    }

    public void setInterest(String interest) {
        this.interest = removeDoublue(interest);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMapLat() {
        return mapLat;
    }

    public void setMapLat(String mapLat) {
        this.mapLat = mapLat;
    }

    public String getMapLng() {
        return mapLng;
    }

    public void setMapLng(String mapLng) {
        this.mapLng = mapLng;
    }

    public String getMyDemand() {
        return myDemand;
    }

    public void setMyDemand(String myDemand) {
        this.myDemand = myDemand;
    }

    public String getMyResources() {
        return myResources;
    }

    public void setMyResources(String myResources) {
        this.myResources = myResources;
    }

    public String getOftenToPace() {
        if (null == oftenToPace) return "";
        return removeDoublue(oftenToPace);
    }

    public void setOftenToPace(String oftenToPace) {
        this.oftenToPace = removeDoublue(oftenToPace);
    }

    public String getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(String integralNum) {
        this.integralNum = integralNum;
    }

    public String getIntegralImg() {
        if (TextUtils.isEmpty(integralImg)) return "";
        return integralImg;
    }

    public void setIntegralImg(String integralImg) {
        this.integralImg = integralImg;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProfessionId() {
        return removeDoublue(professionId);
    }

    public void setProfessionId(String professionId) {
        this.professionId = removeDoublue(professionId);
    }

    public String getProfessionName() {
        if (null == professionName) return "";
        return removeDoublue(professionName);
    }

    public void setProfessionName(String professionName) {
        this.professionName = removeDoublue(professionName);
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getRelationFs() {
        return relationFs;
    }

    public void setRelationFs(int relationFs) {
        this.relationFs = relationFs;
    }

    public int getRelationGz() {
        return relationGz;
    }

    public void setRelationGz(int relationGz) {
        this.relationGz = relationGz;
    }

    public String getResidence() {
        if (null == residence) return "";
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getResidenceCN() {
        if (residenceCN == null) return "";
        return residenceCN;
    }

    public void setResidenceCN(String residenceCN) {
        this.residenceCN = residenceCN;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public boolean isShopIsBuy() {
        return shopIsBuy;
    }

    public void setShopIsBuy(boolean shopIsBuy) {
        this.shopIsBuy = shopIsBuy;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getUnreadComment() {
        return unreadComment;
    }

    public void setUnreadComment(int unreadComment) {
        this.unreadComment = unreadComment;
    }

    public int getUnreadReply() {
        return unreadReply;
    }

    public void setUnreadReply(int unreadReply) {
        this.unreadReply = unreadReply;
    }

    public int getUnreadZan() {
        return unreadZan;
    }

    public void setUnreadZan(int unreadZan) {
        this.unreadZan = unreadZan;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getUpCountStatus() {
        return upCountStatus;
    }

    public void setUpCountStatus(int upCountStatus) {
        this.upCountStatus = upCountStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getWxImg() {
        if (wxImg == null) return "";
        return wxImg;
    }

    public void setWxImg(String wxImg) {
        this.wxImg = wxImg;
    }

    public List<CardItemInfoModel> getIntroductionImgFile() {
        if (null == introductionImgFile) return new ArrayList<>();
        return introductionImgFile;
    }

    public void setIntroductionImgFile(List<CardItemInfoModel> introductionImgFile) {
        this.introductionImgFile = introductionImgFile;
    }

    public List<HonorImgFileBean> getHonorImgFile() {
        return honorImgFile;
    }

    public void setHonorImgFile(List<HonorImgFileBean> honorImgFile) {
        this.honorImgFile = honorImgFile;
    }


    public static class HonorImgFileBean implements Serializable {
        /**
         * createTime : 2019-03-07 20:22:10
         * delStatus : 0
         * filePath : path
         * fileType : 2
         * id : 6
         * tagType : 1
         * userId : 8589
         */

        private String createTime;
        private int delStatus;
        private String filePath;
        private int fileType;
        private String id;
        private int tagType;
        private String userId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(int delStatus) {
            this.delStatus = delStatus;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
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

        public int getTagType() {
            return tagType;
        }

        public void setTagType(int tagType) {
            this.tagType = tagType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class IntroductionAudioFileBean implements Serializable {
        private String id;
        private String userId;
        private String filePath;
        private String createTime;
        private int fileType;
        private int delStatus;
        private int tagType;
        private String fileLength;

        public String getFileLength() {
            if (null == fileLength) return "";
            return fileLength;
        }

        public void setFileLength(String fileLength) {
            this.fileLength = fileLength;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public int getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(int delStatus) {
            this.delStatus = delStatus;
        }

        public int getTagType() {
            return tagType;
        }

        public void setTagType(int tagType) {
            this.tagType = tagType;
        }
    }

    public static class IntroductionVideoFileBean implements Serializable {
        /**
         * createTime : 2019-03-07 20:22:10
         * delStatus : 0
         * filePath : path
         * fileType : 2
         * id : 6
         * tagType : 1
         * userId : 8589
         */

        private String createTime;
        private int delStatus;
        private String filePath;
        private int fileType;
        private String id;
        private int tagType;
        private String userId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(int delStatus) {
            this.delStatus = delStatus;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
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

        public int getTagType() {
            return tagType;
        }

        public void setTagType(int tagType) {
            this.tagType = tagType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public List<CardItemInfoModel> getMyDemandImgFile() {
        if (myDemandImgFile == null) return new ArrayList<>();
        return myDemandImgFile;
    }

    public void setMyDemandImgFile(List<CardItemInfoModel> myDemandImgFile) {
        this.myDemandImgFile = myDemandImgFile;
    }

    public List<CardItemInfoModel> getMyResourcesImgFile() {
        if (myResourcesImgFile == null) return new ArrayList<>();
        return myResourcesImgFile;
    }

    public void setMyResourcesImgFile(List<CardItemInfoModel> myResourcesImgFile) {
        this.myResourcesImgFile = myResourcesImgFile;
    }

    public static class MyViewUserListBean implements Serializable {
        /**
         * gsName : 测试2
         * headimg : 测试2
         * operDate : 2019-03-06 11:54:19
         * userId : 8589
         */

        private String gsName;
        private String headimg;
        private String operDate;
        private String userId;

        public String getGsName() {
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }

        public String getHeadimg() {
            if (null == headimg) return "";
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getOperDate() {
            return operDate;
        }

        public void setOperDate(String operDate) {
            this.operDate = operDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public List<MyViewUserListBean> getMyViewUserList() {
        return myViewUserList;
    }

    public void setMyViewUserList(List<MyViewUserListBean> myViewUserList) {
        this.myViewUserList = myViewUserList;
    }

    public static class VipList implements Serializable {

        /**
         * id : 3457
         * actionId : 5
         * groupId : 0
         * userId : 32801
         * addTime : 2019-03-20 00:00:00
         * startDate : 2019-03-20 00:00:00
         * endDate : 2022-03-19 00:00:00
         * actionName : 三年服务
         * imageUrl : http://img2.jpg
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

    public List<IntroductionVideoFileBean> getIntroductionVideoFile() {
        if (introductionVideoFile == null)
            return new ArrayList<>();
        return introductionVideoFile;
    }

    public void setIntroductionVideoFile(List<IntroductionVideoFileBean> introductionVideoFile) {
        this.introductionVideoFile = introductionVideoFile;
    }

    public List<VipList> getUserVipAction() {
        if (userVipAction == null)
            return new ArrayList<>();
        return userVipAction;
    }

    public void setUserVipAction(List<VipList> userVipAction) {
        this.userVipAction = userVipAction;
    }


    /**
     * 去重字符串中用逗号隔开的字符串  如   "1,1,2"->"1,2"
     *
     * @param str
     * @return
     */
    private String removeDoublue(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        List<String> noDouble = new ArrayList<>();
        List<String> list = CommonUtil.StringToList(str);
        for (int i = 0; i < list.size(); i++) {
            if (!noDouble.contains(list.get(i))) {
                noDouble.add(list.get(i));
            }
        }
        return CommonUtil.ListToString(noDouble);
    }
}
