package com.lyzb.jbx.model.me;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/3/22 14:42
 */

public class AccessModel implements Serializable {


    /**
     * pageNum : 1
     * pageSize : 10
     * total : 7
     * pages : 1
     * list : [{"gaName":"克莱尔","headimg":"http://b-ssl.duitang.com/uploads/item/201812/05/20181205211932_xvslr.jpeg","userId":32801,"id":7083,"vipType":1,"createTime":"2019-03-22 09:57:27","topicNum":2,"userExtNum":0,"goodsNum":0,"userVipAction":[]},{"gaName":"吕布","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/19/V8s345w98R.jpg","userId":109922,"id":7081,"vipType":1,"createTime":"2019-03-21 17:11:24","topicNum":0,"userExtNum":0,"goodsNum":0,"userVipAction":[{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"智能名片","imageUrl":"http://img1.jpg"}]},{"gaName":"周瑜","userId":15558,"id":6910,"vipType":1,"createTime":"2019-03-21 13:53:58","topicNum":2,"userExtNum":0,"goodsNum":0,"userVipAction":[]},{"gaName":"周瑜","userId":15558,"id":6910,"vipType":1,"createTime":"2019-03-21 13:35:01","topicNum":2,"userExtNum":0,"goodsNum":0,"userVipAction":[]},{"gaName":"克莱尔","headimg":"http://b-ssl.duitang.com/uploads/item/201812/05/20181205211932_xvslr.jpeg","userId":32801,"id":7083,"vipType":1,"createTime":"2019-03-21 11:32:25","topicNum":2,"userExtNum":0,"goodsNum":0,"userVipAction":[]},{"gaName":"克莱尔","headimg":"http://b-ssl.duitang.com/uploads/item/201812/05/20181205211932_xvslr.jpeg","userId":32801,"id":7083,"vipType":1,"createTime":"2019-03-20 21:18:25","topicNum":2,"userExtNum":0,"goodsNum":0,"userVipAction":[]},{"gaName":"周瑜","userId":15558,"id":6910,"vipType":1,"createTime":"2019-03-20 20:04:36","topicNum":2,"userExtNum":0,"goodsNum":0,"userVipAction":[]}]
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

    public static class ListBean implements Serializable {
        /**
         * gaName : 克莱尔
         * headimg : http://b-ssl.duitang.com/uploads/item/201812/05/20181205211932_xvslr.jpeg
         * userId : 32801
         * id : 7083
         * vipType : 1
         * createTime : 2019-03-22 09:57:27
         * topicNum : 2
         * userExtNum : 0
         * goodsNum : 0
         * userVipAction : []
         */

        private String gaName;
        private String gsName;
        private String headimg;
        private String userId;
        private String sex;
        private String shopName;
        private String id;
        private int relationNum;

        private String mobile;

        private int vipType;
        private String createTime;
        private int topicNum;
        private int userExtNum;
        private int goodsNum;

        private String textNumber;

        private List<?> userVipAction;
        private GsTopicVo gsTopicVo;

        private String wxImg;

        public String getGsName() {
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getRelationNum() {
            return relationNum;
        }

        public void setRelationNum(int relationNum) {
            this.relationNum = relationNum;
        }

        public GsTopicVo getGsTopicVo() {
            return gsTopicVo;
        }

        public void setGsTopicVo(GsTopicVo gsTopicVo) {
            this.gsTopicVo = gsTopicVo;
        }

        public String getGaName() {
            return gaName;
        }

        public void setGaName(String gaName) {
            this.gaName = gaName;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getTopicNum() {
            return topicNum;
        }

        public void setTopicNum(int topicNum) {
            this.topicNum = topicNum;
        }

        public int getUserExtNum() {
            return userExtNum;
        }

        public void setUserExtNum(int userExtNum) {
            this.userExtNum = userExtNum;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public List<?> getUserVipAction() {
            if (userVipAction == null) return new ArrayList<>();
            return userVipAction;
        }

        public void setUserVipAction(List<?> userVipAction) {
            this.userVipAction = userVipAction;
        }

        public String getTextNumber() {
            return textNumber;
        }

        public void setTextNumber(String textNumber) {
            this.textNumber = textNumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getWxImg() {
            return wxImg;
        }

        public void setWxImg(String wxImg) {
            this.wxImg = wxImg;
        }

        public static class GsTopicVo implements Serializable {
            public int pageNum;
            public int pageSize;
            public int total;
            public int pages;
            private List<GsTopicVoListBean> list;

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

            public List<GsTopicVoListBean> getList() {
                return list;
            }

            public void setList(List<GsTopicVoListBean> list) {
                this.list = list;
            }

            public static class GsTopicVoListBean implements Serializable {

                public String id;
                public String createMan;
                public String createDate;
                public String content;
                public int commentCount;
                public int shareCount;
                public int viewCount;
                public int upCount;
                public int sex;
                public int vipType;
                public String shopName;
                public String gsName;
                public String headimg;
                private List<FileList> fileList;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCreateMan() {
                    return createMan;
                }

                public void setCreateMan(String createMan) {
                    this.createMan = createMan;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getCommentCount() {
                    return commentCount;
                }

                public void setCommentCount(int commentCount) {
                    this.commentCount = commentCount;
                }

                public int getShareCount() {
                    return shareCount;
                }

                public void setShareCount(int shareCount) {
                    this.shareCount = shareCount;
                }

                public int getViewCount() {
                    return viewCount;
                }

                public void setViewCount(int viewCount) {
                    this.viewCount = viewCount;
                }

                public int getUpCount() {
                    return upCount;
                }

                public void setUpCount(int upCount) {
                    this.upCount = upCount;
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

                public String getShopName() {
                    return shopName;
                }

                public void setShopName(String shopName) {
                    this.shopName = shopName;
                }

                public String getGsName() {
                    return gsName;
                }

                public void setGsName(String gsName) {
                    this.gsName = gsName;
                }

                public String getHeadimg() {
                    return headimg;
                }

                public void setHeadimg(String headimg) {
                    this.headimg = headimg;
                }

                public List<FileList> getFileList() {
                    return fileList;
                }

                public void setFileList(List<FileList> fileList) {
                    this.fileList = fileList;
                }

                public static class FileList implements Serializable {
                    public String id;
                    public String topicId;
                    public String file;
                    public String fileType;
                    public String createtime;
                    public int sort;
                    public int class1;

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

                    public String getFile() {
                        return file;
                    }

                    public void setFile(String file) {
                        this.file = file;
                    }

                    public String getFileType() {
                        return fileType;
                    }

                    public void setFileType(String fileType) {
                        this.fileType = fileType;
                    }

                    public String getCreatetime() {
                        return createtime;
                    }

                    public void setCreatetime(String createtime) {
                        this.createtime = createtime;
                    }

                    public int getSort() {
                        return sort;
                    }

                    public void setSort(int sort) {
                        this.sort = sort;
                    }

                    public int getClass1() {
                        return class1;
                    }

                    public void setClass1(int class1) {
                        this.class1 = class1;
                    }
                }
            }


        }
    }
}
