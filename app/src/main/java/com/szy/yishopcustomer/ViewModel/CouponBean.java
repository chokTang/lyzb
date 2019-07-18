package com.szy.yishopcustomer.ViewModel;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/2/27  13:33
 * Desc:
 */
public class CouponBean{


    /**
     * list : [{"addTime":"1550899485","bonusAmount":99,"bonusData":"a:1:{s:9:\"goods_ids\";a:2:{i:0;s:5:\"32444\";i:1;s:5:\"32445\";}}","bonusId":212,"bonusName":"指定商品优惠券","bonusNumber":10,"bonusType":1,"bonusUseType":0,"endTime":"1551542399","isDelete":0,"isEnable":1,"isOriginalPrice":1,"minGoodsAmount":99,"receiveCount":10,"receivedCount":83634,"sendType":0,"shopId":2555,"startTime":"1550851200","useRange":1,"userCount":0}]
     * pageNum : 1
     * pageSize : 30
     * pages : 1
     * total : 6
     */

    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * addTime : 1550899485
         * bonusAmount : 99
         * bonusData : a:1:{s:9:"goods_ids";a:2:{i:0;s:5:"32444";i:1;s:5:"32445";}}
         * bonusId : 212
         * bonusName : 指定商品优惠券
         * bonusNumber : 10
         * bonusType : 1
         * bonusUseType : 0
         * endTime : 1551542399
         * isDelete : 0
         * isEnable : 1
         * isOriginalPrice : 1
         * minGoodsAmount : 99
         * receiveCount : 10
         * receivedCount : 83634
         * sendType : 0
         * shopId : 2555
         * startTime : 1550851200
         * useRange : 1
         * userCount : 0
         */

        private String addTime;
        private int bonusAmount;
        private String bonusData;
        private int bonusId;
        private String bonusName;
        private int bonusNumber;
        private int bonusType;
        private int bonusUseType;
        private String endTime;
        private int isDelete;
        private int isEnable;
        private int isOriginalPrice;
        private int minGoodsAmount;
        private int receiveCount;
        private int receivedCount;
        private int sendType;
        private int shopId;
        private String startTime;
        private int useRange;
        private int userCount;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getBonusAmount() {
            return bonusAmount;
        }

        public void setBonusAmount(int bonusAmount) {
            this.bonusAmount = bonusAmount;
        }

        public String getBonusData() {
            return bonusData;
        }

        public void setBonusData(String bonusData) {
            this.bonusData = bonusData;
        }

        public int getBonusId() {
            return bonusId;
        }

        public void setBonusId(int bonusId) {
            this.bonusId = bonusId;
        }

        public String getBonusName() {
            return bonusName;
        }

        public void setBonusName(String bonusName) {
            this.bonusName = bonusName;
        }

        public int getBonusNumber() {
            return bonusNumber;
        }

        public void setBonusNumber(int bonusNumber) {
            this.bonusNumber = bonusNumber;
        }

        public int getBonusType() {
            return bonusType;
        }

        public void setBonusType(int bonusType) {
            this.bonusType = bonusType;
        }

        public int getBonusUseType() {
            return bonusUseType;
        }

        public void setBonusUseType(int bonusUseType) {
            this.bonusUseType = bonusUseType;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(int isEnable) {
            this.isEnable = isEnable;
        }

        public int getIsOriginalPrice() {
            return isOriginalPrice;
        }

        public void setIsOriginalPrice(int isOriginalPrice) {
            this.isOriginalPrice = isOriginalPrice;
        }

        public int getMinGoodsAmount() {
            return minGoodsAmount;
        }

        public void setMinGoodsAmount(int minGoodsAmount) {
            this.minGoodsAmount = minGoodsAmount;
        }

        public int getReceiveCount() {
            return receiveCount;
        }

        public void setReceiveCount(int receiveCount) {
            this.receiveCount = receiveCount;
        }

        public int getReceivedCount() {
            return receivedCount;
        }

        public void setReceivedCount(int receivedCount) {
            this.receivedCount = receivedCount;
        }

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getUseRange() {
            return useRange;
        }

        public void setUseRange(int useRange) {
            this.useRange = useRange;
        }

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }
    }
}
