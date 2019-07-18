package com.szy.yishopcustomer.ViewModel;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/2/27  15:44
 * Desc:
 */
public class HxqBean {

    /**
     * pageNum : 1
     * pageSize : 10
     * total : 12
     * pages : 2
     * list : [{"prodId":"62770465724460627","storeId":"2564","isSale":"10001","prodName":"安辉2","marketPrice":5,"jcPrice":2,"addPrice":0,"acer":2,"soldNum":3,"prodDesc":"{\"desc\":\"度假酒店见\",\"prodDescAttributes\":[]}","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/28/636E582A-2000-4A87-94D7-F8C8362CCAD4.jpeg","jibPice":0,"hotCake":1,"isDrainage":false,"type":"1","shopName":"18800000011"},{"prodId":"42504247270251720","storeId":"1140","isSale":"10001","prodName":"车辆清洗","marketPrice":15,"jcPrice":10,"addPrice":0,"acer":10,"soldNum":1,"prodDesc":"适用起亚辆外观清洗","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/nY15s6fXOp.jpg","jibPice":0,"type":"1","shopName":"东风悦达起亚南宁荣力4S店","shopLogo":"/shop/1140/images/2018/05/07/15256732554316.jpg"},{"prodId":"42504249039087347","storeId":"1534","isSale":"10001","prodName":"拔罐","marketPrice":18,"jcPrice":10,"addPrice":0,"acer":10,"soldNum":0,"prodDesc":"活血化瘀 解痉止痛","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/FES1BHr0K2.jpg","jibPice":0,"type":"1","shopName":"曲阜市烆康保健按摩店","shopLogo":"/shop/1534/images/2018/05/30/15276658992183.jpg"},{"prodId":"42504249039085554","storeId":"1000","isSale":"10001","prodName":"泡椒土豆片","marketPrice":18,"jcPrice":18,"addPrice":0,"acer":18,"soldNum":0,"prodDesc":"好吃开胃！绝对美味！","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/39c67wk0Ib.jpg","jibPice":0,"type":"1","shopName":"两江新区乾村餐饮店","shopLogo":"/shop/1000/images/2018/05/14/15262770792541.jpg"},{"prodId":"42504249039086102","storeId":"972","isSale":"10001","prodName":"0元换购 井冈山矿泉水 17.5升/桶天然矿泉水","marketPrice":18,"jcPrice":18,"addPrice":0,"acer":18,"soldNum":2,"prodDesc":"井冈山矿泉水，富含人体所需有益健康微量元素，锶、硒、锂、偏硅酸等矿物元素。长期饮用，健康倍增！","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/z1eCta4lJ5.jpg","jibPice":0,"type":"1","shopName":"仙女湖区集食惠超市","shopLogo":"/shop/972/images/2018/05/30/15276892959719.jpg"},{"prodId":"42504248554047943","storeId":"1304","isSale":"10001","prodName":"特色打卤面(老少最爱)","marketPrice":30,"jcPrice":29,"addPrice":0,"acer":29,"soldNum":0,"prodDesc":"为庆祝本店正式入住网络，特别优惠活动，50份免费送，先到先得。","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/2F67l4iU41.jpg","jibPice":0,"type":"1","shopName":"咸丰县土家腊鱼馆","shopLogo":"/shop/1304/images/2018/05/19/15267452556154.jpg"},{"prodId":"42504247413741300","storeId":"1066","isSale":"10001","prodName":"免费全口检查","marketPrice":300,"jcPrice":300,"addPrice":0,"acer":300,"soldNum":0,"prodDesc":"口腔全口检查，全景片，牙菌斑测试，牙龈健康测试","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/z5k0sJ8h2f.jpg","jibPice":0,"type":"1","shopName":"新时代口腔","shopLogo":"/shop/1066/images/2018/05/13/15261777306294.jpg"},{"prodId":"42504247413741348","storeId":"1072","isSale":"10001","prodName":"免费全口检查","marketPrice":300,"jcPrice":300,"addPrice":0,"acer":300,"soldNum":0,"prodDesc":"免费全口检查，全景片，牙菌斑测试，牙龈健康测试","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/X1trF8sm8Q.jpg","jibPice":0,"type":"1","shopName":"李剑英口腔","shopLogo":"/shop/1072/images/2018/05/13/15261778243616.jpg"},{"prodId":"42504247413741392","storeId":"1066","isSale":"10001","prodName":"修复抵用券300元","marketPrice":300,"jcPrice":300,"addPrice":0,"acer":300,"soldNum":0,"prodDesc":"","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/cW64YQTxi7.jpg","jibPice":0,"type":"1","shopName":"新时代口腔","shopLogo":"/shop/1066/images/2018/05/13/15261777306294.jpg"},{"prodId":"42504247413741405","storeId":"1072","isSale":"10001","prodName":"修复抵用券300元","marketPrice":300,"jcPrice":300,"addPrice":0,"acer":300,"soldNum":0,"prodDesc":"","prodLogo":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/shop/zB0N5b7l49.jpg","jibPice":0,"type":"1","shopName":"李剑英口腔","shopLogo":"/shop/1072/images/2018/05/13/15261778243616.jpg"}]
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
         * prodId : 62770465724460627
         * storeId : 2564
         * isSale : 10001
         * prodName : 安辉2
         * marketPrice : 5
         * jcPrice : 2
         * addPrice : 0
         * acer : 2
         * soldNum : 3
         * prodDesc : {"desc":"度假酒店见","prodDescAttributes":[]}
         * prodLogo : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/28/636E582A-2000-4A87-94D7-F8C8362CCAD4.jpeg
         * jibPice : 0
         * hotCake : 1
         * isDrainage : false
         * type : 1
         * shopName : 18800000011
         * shopLogo : /shop/1140/images/2018/05/07/15256732554316.jpg
         */

        private String prodId;
        private String storeId;
        private String isSale;
        private String prodName;
        private int marketPrice;
        private int jcPrice;
        private int addPrice;
        private double distance ;
        private int acer;
        private int soldNum;
        private String prodDesc;
        private String prodLogo;
        private int jibPice;
        private int hotCake;
        private boolean isDrainage;
        private String type;
        private String shopName;
        private String shopLogo;


        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public boolean isDrainage() {
            return isDrainage;
        }

        public void setDrainage(boolean drainage) {
            isDrainage = drainage;
        }

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getIsSale() {
            return isSale;
        }

        public void setIsSale(String isSale) {
            this.isSale = isSale;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public int getJcPrice() {
            return jcPrice;
        }

        public void setJcPrice(int jcPrice) {
            this.jcPrice = jcPrice;
        }

        public int getAddPrice() {
            return addPrice;
        }

        public void setAddPrice(int addPrice) {
            this.addPrice = addPrice;
        }

        public int getAcer() {
            return acer;
        }

        public void setAcer(int acer) {
            this.acer = acer;
        }

        public int getSoldNum() {
            return soldNum;
        }

        public void setSoldNum(int soldNum) {
            this.soldNum = soldNum;
        }

        public String getProdDesc() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc = prodDesc;
        }

        public String getProdLogo() {
            return prodLogo;
        }

        public void setProdLogo(String prodLogo) {
            this.prodLogo = prodLogo;
        }

        public int getJibPice() {
            return jibPice;
        }

        public void setJibPice(int jibPice) {
            this.jibPice = jibPice;
        }

        public int getHotCake() {
            return hotCake;
        }

        public void setHotCake(int hotCake) {
            this.hotCake = hotCake;
        }

        public boolean isIsDrainage() {
            return isDrainage;
        }

        public void setIsDrainage(boolean isDrainage) {
            this.isDrainage = isDrainage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopLogo() {
            return shopLogo;
        }

        public void setShopLogo(String shopLogo) {
            this.shopLogo = shopLogo;
        }
    }
}
