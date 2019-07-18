package com.szy.yishopcustomer.ViewModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2018/12/20  15:58
 * Desc:
 */
public class HomeShopAndProductBean implements Serializable{


    /**
     * shop_hot : [{"shop_id":113,"shop_poster":"/shop/113/images/2018/02/26/15196347658363.png","local_service":0,"trips":53,"shop_name":"燃尚全品店","shop_logo":"/shop/113/images/2018/11/23/15429567914428.jpg","shop_image":"/shop/113/images/2018/11/23/15429567914428.jpg","is_supply":0},{"shop_id":1437,"shop_poster":"/shop/1437/images/2018/05/29/15275826635627.jpg","local_service":0,"trips":46,"shop_name":"广东老中医保健食品有限公司","shop_logo":"/shop/1437/images/2018/05/29/15275806715862.png","shop_image":"/shop/1437/images/2018/05/29/15275806649337.jpg","is_supply":0},{"shop_id":4274,"shop_poster":"/shop/4274/images/2018/11/13/15420917035954.jpg","local_service":0,"trips":40,"shop_name":"乐家优选","shop_logo":"/shop/4274/images/2018/11/11/15419200957316.jpg","shop_image":"/shop/4274/images/2018/11/07/15415784426463.jpg","is_supply":0}]
     * shop_new : [{"shop_id":4711,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/19/4Phj8832n2.jpg","local_service":1,"shop_name":"易县康惠副食商店","shop_logo":"/shop/4711/images/2018/12/19/15452072043261.jpg","shop_image":"/shop/4711/images/2018/12/19/15452072043261.jpg","is_supply":0},{"shop_id":4712,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/19/3375kL962T.jpg","local_service":1,"shop_name":"南宁市石岭草药堂","shop_logo":"/shop/4712/images/2018/12/19/15452123658261.jpg","shop_image":"/shop/4712/images/2018/12/19/15452123658261.jpg","is_supply":0},{"shop_id":4713,"shop_poster":null,"local_service":1,"shop_name":"莒县东夷硒都土特产店","shop_logo":null,"shop_image":null,"is_supply":0},{"shop_id":4703,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/19/8icn3LaD12.jpg","local_service":1,"shop_name":"碧秀佳","shop_logo":"/shop/4703/images/2018/12/19/15451838195523.jpg","shop_image":"/shop/4703/images/2018/12/19/15451838195523.jpg","is_supply":0},{"shop_id":4699,"shop_poster":null,"local_service":1,"shop_name":"云南红河县中垠农业科技发展有限责任公司","shop_logo":null,"shop_image":null,"is_supply":0},{"shop_id":4697,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/17/27v0833552.jpg","local_service":1,"shop_name":"迎君超市","shop_logo":"/shop/4697/images/2018/12/17/15450250869583.jpg","shop_image":"/shop/4697/images/2018/12/17/15450250869583.jpg","is_supply":0},{"shop_id":4694,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/17/F19DcU44z1.jpg","local_service":1,"shop_name":"西湖区嘉得利工艺品店","shop_logo":"/shop/4694/images/2018/12/17/15450248297262.jpg","shop_image":"/shop/4694/images/2018/12/17/15450248297262.jpg","is_supply":0},{"shop_id":4691,"shop_poster":null,"local_service":1,"shop_name":"赛维洗衣店","shop_logo":null,"shop_image":null,"is_supply":0},{"shop_id":4692,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/17/K8593c7iNd.jpg","local_service":1,"shop_name":"赣州市易九堂食品有限公司","shop_logo":"/shop/4692/images/2018/12/17/15450323586329.jpg","shop_image":"/shop/4692/images/2018/12/17/15450323586329.jpg","is_supply":0},{"shop_id":4672,"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/18/71D8E433-0822-4732-90B3-5976358C168B.jpeg","local_service":1,"shop_name":"泗水永昶食品有限公司","shop_logo":"/shop/4672/images/2018/12/18/15451200474557.jpg","shop_image":"/shop/4672/images/2018/12/18/15451200474557.jpg","is_supply":0}]
     * shop_like : [{"shop_id":113,"shop_poster":"/shop/113/images/2018/02/26/15196347658363.png","local_service":0,"trips":53,"shop_name":"燃尚全品店","shop_logo":"/shop/113/images/2018/11/23/15429567914428.jpg","shop_image":"/shop/113/images/2018/11/23/15429567914428.jpg","is_supply":0},{"shop_id":1437,"shop_poster":"/shop/1437/images/2018/05/29/15275826635627.jpg","local_service":0,"trips":46,"shop_name":"广东老中医保健食品有限公司","shop_logo":"/shop/1437/images/2018/05/29/15275806715862.png","shop_image":"/shop/1437/images/2018/05/29/15275806649337.jpg","is_supply":0},{"shop_id":4274,"shop_poster":"/shop/4274/images/2018/11/13/15420917035954.jpg","local_service":0,"trips":40,"shop_name":"乐家优选","shop_logo":"/shop/4274/images/2018/11/11/15419200957316.jpg","shop_image":"/shop/4274/images/2018/11/07/15415784426463.jpg","is_supply":0}]
     * repurchase : [{"is_sale":10001,"goods_name":"泡泡枪","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/6lc3D1SE79.jpg","goods_price":-91,"goods_id":42504258230623393,"shop_name":"天猫小店","max_integral_num":100},{"is_sale":10001,"goods_name":"秘制麻婆豆腐","shop_id":2528,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/30/O75Ram4VOv.jpg","goods_price":-982,"goods_id":42504259612039191,"shop_name":"王蹄花","max_integral_num":1000},{"is_sale":10001,"goods_name":"清洗衬衣","shop_id":3577,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/21/87021t6645.jpg","goods_price":-9985,"goods_id":42504258230631014,"shop_name":"赛维洗衣连锁中华坊店","max_integral_num":10000},{"is_sale":10001,"goods_name":"火锅超值8人餐","shop_id":1000,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/10/8F806Ns440.jpg","goods_price":-600,"goods_id":42504255216003474,"shop_name":"两江新区乾村餐饮店","max_integral_num":999},{"is_sale":10001,"goods_name":"鸡腿河粉","shop_id":2491,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/20/6W6NF293OW.jpg","goods_price":-87,"goods_id":42504258230630224,"shop_name":"沙县小吃","max_integral_num":100},{"is_sale":10001,"goods_name":"河水豆花（不含饭）","shop_id":2577,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/13/2585tS729c.jpg","goods_price":-93,"goods_id":42504258230621931,"shop_name":"老四川肥肠饭","max_integral_num":100},{"is_sale":10001,"goods_name":"避风塘炒辣蟹","shop_id":3010,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/07/cjFXHh8H59.jpg","goods_price":188,"goods_id":42504257236988671,"shop_name":"蟹爱餐厅","max_integral_num":100},{"is_sale":10001,"goods_name":"劲酒125ml","shop_id":3961,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/10/19/2G0TlF1x2z.jpg","goods_price":-88,"goods_id":42504260661725348,"shop_name":"两江新区微店便利店","max_integral_num":100},{"is_sale":10001,"goods_name":"三生食","shop_id":4162,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/10/29/619101E4-1624-412E-A6DD-375553D7C788.jpeg","goods_price":8,"goods_id":42504260661738658,"shop_name":"元气生活","max_integral_num":10}]
     * advert : [{"area":"","orderNo":999,"liveType":1,"isDelete":0,"type":0,"title":"入驻测试1","createTime":"2018-12-18 11:47:58","imageUrl":"http://images.jibaoh.com/backend/gallery/2018/12/03/15438041884368.gif","linkUrl":"http://www.baidu.com","showType":1,"opreateType":1,"startTime":"2018-12-18 12:45:40","id":"d44bb8de488d49f19e25435a5ff819fb1","endTime":"2019-12-20 11:45:40","refId":"","position":"A4","merchantNumStatus":0,"status":1},{"orderNo":999,"liveType":1,"isDelete":0,"type":0,"title":"cs","createTime":"2018-12-19 09:53:35","imageUrl":"http://images.jibaoh.com/backend/gallery/2018/12/03/15438045186864.gif","linkUrl":"http://www.baidu.com","showType":1,"opreateType":1,"startTime":"2018-12-19 09:53:08","id":"d44bb8de488d49f19e25435a5ff819fb2","endTime":"2019-12-19 09:53:11","position":"A4","merchantNumStatus":0,"status":1}]
     * sort : [{"type":"advert","key":"advert"},{"type":"goods","key":"repurchase"},{"type":"shopAndGoods","key":"nearby"},{"type":"shopNum","key":"shop_num"},{"type":"shop","key":"shop_new"},{"type":"shop","key":"shop_hot"},{"type":"shop","key":"shop_like"}]
     * nearby : [{"shop_id":3442,"address":"黄山大道东段193号","goods_list":[{"is_sale":10001,"goods_name":"泡泡枪","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/6lc3D1SE79.jpg","goods_price":-91,"goods_id":42504258230623393,"max_integral_num":100},{"is_sale":10001,"goods_name":"甜心公主","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/89Tgi5E52c.jpg","goods_price":-85.1,"goods_id":42504258230623362,"max_integral_num":100},{"is_sale":10001,"goods_name":"百草味板栗仁80g","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/k38R656yL6.jpg","goods_price":-94,"goods_id":42504258230623375,"max_integral_num":100}],"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/17/lL71GTP63U.jpg","distance":32,"local_service":1,"shop_location":"29.614839,106.510658","desc_score":5,"shop_name":"天猫小店","shop_logo":"/shop/3442/images/2018/09/17/15371761868154.jpg","shop_image":null,"is_supply":0},{"shop_id":2528,"address":"人和街道黄山大道185号","goods_list":[{"is_sale":10001,"goods_name":"秘制麻婆豆腐","shop_id":2528,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/30/O75Ram4VOv.jpg","goods_price":-982,"goods_id":42504259612039191,"max_integral_num":1000},{"is_sale":10001,"goods_name":"特色土猪蹄花汤","shop_id":2528,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/30/0qABqZ97iG.jpg","goods_price":-970,"goods_id":42504259612039183,"max_integral_num":1000},{"is_sale":10001,"goods_name":"盐煎肉","shop_id":2528,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/30/mpq42wJDMy.jpg","goods_price":-975,"goods_id":42504259612039221,"max_integral_num":1000}],"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/08/24/Q08w50KARk.jpg","distance":43,"local_service":1,"shop_location":"29.614751,106.51062","desc_score":5,"shop_name":"王蹄花","shop_logo":"/shop/2528/images/2018/10/08/15389906998965.jpg","shop_image":null,"is_supply":0},{"shop_id":3577,"address":"人和街道金科·中华坊紫园","goods_list":[{"is_sale":10001,"goods_name":"清洗衬衣","shop_id":3577,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/21/87021t6645.jpg","goods_price":-9985,"goods_id":42504258230631014,"max_integral_num":10000},{"is_sale":10001,"goods_name":"清洗短羽绒服","shop_id":3577,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/21/9HAsgOU093.jpg","goods_price":-475,"goods_id":42504258230631062,"max_integral_num":500},{"is_sale":10001,"goods_name":"清洗羊毛呢大衣","shop_id":3577,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/21/i8wwqh9d51.jpg","goods_price":-475,"goods_id":42504258230631080,"max_integral_num":500}],"shop_poster":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/21/Rd0z58j126.jpg","distance":448,"local_service":1,"shop_location":"29.616428,106.515228","desc_score":5,"shop_name":"赛维洗衣连锁中华坊店","shop_logo":"/shop/3577/images/2018/09/21/15374976616173.jpg","shop_image":null,"is_supply":0}]
     * shop_num : 1972
     */

    private List<ShopNumBean> shop_num;
    private List<ShopHotBean> shop_hot;
    private List<ShopNewBean> shop_new;
    private List<ShopLikeBean> shop_like;
    private List<RepurchaseBean> repurchase;
    private List<AdvertBean> advert;
    private List<SortBean> sort;
    private List<NearbyBean> nearby;
    private List<LiveBean> live;


    public List<LiveBean> getLive() {
        return live;
    }

    public void setLive(List<LiveBean> live) {
        this.live = live;
    }

    public List<ShopNumBean> getShop_num() {
        return shop_num;
    }

    public void setShop_num(List<ShopNumBean> shop_num) {
        this.shop_num = shop_num;
    }

    public List<ShopHotBean> getShop_hot() {
        return shop_hot;
    }

    public void setShop_hot(List<ShopHotBean> shop_hot) {
        this.shop_hot = shop_hot;
    }

    public List<ShopNewBean> getShop_new() {
        return shop_new;
    }

    public void setShop_new(List<ShopNewBean> shop_new) {
        this.shop_new = shop_new;
    }

    public List<ShopLikeBean> getShop_like() {
        return shop_like;
    }

    public void setShop_like(List<ShopLikeBean> shop_like) {
        this.shop_like = shop_like;
    }

    public List<RepurchaseBean> getRepurchase() {
        return repurchase;
    }

    public void setRepurchase(List<RepurchaseBean> repurchase) {
        this.repurchase = repurchase;
    }

    public List<AdvertBean> getAdvert() {
        return advert;
    }

    public void setAdvert(List<AdvertBean> advert) {
        this.advert = advert;
    }

    public List<SortBean> getSort() {
        return sort;
    }

    public void setSort(List<SortBean> sort) {
        this.sort = sort;
    }

    public List<NearbyBean> getNearby() {
        return nearby;
    }

    public void setNearby(List<NearbyBean> nearby) {
        this.nearby = nearby;
    }


    public static class ShopNumBean{


        /**
         * area :
         * createTime : 2018-12-18 11:47:58
         * endTime : 2019-12-20 11:45:40
         * id : d44bb8de488d49f19e25435a5ff819fb1
         * imageUrl : http://images.jibaoh.com/backend/gallery/2018/12/03/15438041884368.gif
         * isDelete : 0
         * linkUrl : http://www.baidu.com
         * liveType : 1
         * merchantNumStatus : 1
         * opreateType : 1
         * orderNo : 999
         * position : A4
         * refId :
         * shopNum : 123
         * shopTitle : 商家已入驻
         * showType : 1
         * startTime : 2018-12-18 12:45:40
         * status : 1
         * title : 入驻测试1
         * type : 0
         */

        private String area;
        private String createTime;
        private String endTime;
        private String id;
        private String imageUrl;
        private String isDelete;
        private String linkUrl;
        private String liveType;
        private String merchantNumStatus;
        private String opreateType;
        private String orderNo;
        private String position;
        private String refId;
        private String shopNum;
        private String shopTitle;
        private String showType;
        private String startTime;
        private String status;
        private String title;
        private String type;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getLiveType() {
            return liveType;
        }

        public void setLiveType(String liveType) {
            this.liveType = liveType;
        }

        public String getMerchantNumStatus() {
            return merchantNumStatus;
        }

        public void setMerchantNumStatus(String merchantNumStatus) {
            this.merchantNumStatus = merchantNumStatus;
        }

        public String getOpreateType() {
            return opreateType;
        }

        public void setOpreateType(String opreateType) {
            this.opreateType = opreateType;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getShopNum() {
            return shopNum;
        }

        public void setShopNum(String shopNum) {
            this.shopNum = shopNum;
        }

        public String getShopTitle() {
            return shopTitle;
        }

        public void setShopTitle(String shopTitle) {
            this.shopTitle = shopTitle;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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
    }


    public static class ShopHotBean {
        /**
         * shop_id : 113
         * shop_poster : /shop/113/images/2018/02/26/15196347658363.png
         * local_service : 0
         * trips : 53
         * shop_name : 燃尚全品店
         * shop_logo : /shop/113/images/2018/11/23/15429567914428.jpg
         * shop_image : /shop/113/images/2018/11/23/15429567914428.jpg
         * is_supply : 0
         */

        private String shop_id;
        private String shop_poster;
        private String local_service;
        private String trips;
        private String shop_name;
        private String shop_logo;
        private String shop_image;
        private String is_supply;
        private String take_out_status;


        public String getTake_out_status() {
            return take_out_status;
        }

        public void setTake_out_status(String take_out_status) {
            this.take_out_status = take_out_status;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_poster() {
            return shop_poster;
        }

        public void setShop_poster(String shop_poster) {
            this.shop_poster = shop_poster;
        }

        public String getLocal_service() {
            return local_service;
        }

        public void setLocal_service(String local_service) {
            this.local_service = local_service;
        }

        public String getTrips() {
            return trips;
        }

        public void setTrips(String trips) {
            this.trips = trips;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getShop_image() {
            return shop_image;
        }

        public void setShop_image(String shop_image) {
            this.shop_image = shop_image;
        }

        public String getIs_supply() {
            return is_supply;
        }

        public void setIs_supply(String is_supply) {
            this.is_supply = is_supply;
        }
    }

    public static class ShopNewBean {
        /**
         * shop_id : 4711
         * shop_poster : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/12/19/4Phj8832n2.jpg
         * local_service : 1
         * shop_name : 易县康惠副食商店
         * shop_logo : /shop/4711/images/2018/12/19/15452072043261.jpg
         * shop_image : /shop/4711/images/2018/12/19/15452072043261.jpg
         * is_supply : 0
         */

        private String shop_id;
        private String shop_poster;
        private String local_service;
        private String shop_name;
        private String shop_logo;
        private String shop_image;
        private String is_supply;
        private String take_out_status;


        public String getTake_out_status() {
            return take_out_status;
        }

        public void setTake_out_status(String take_out_status) {
            this.take_out_status = take_out_status;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_poster() {
            return shop_poster;
        }

        public void setShop_poster(String shop_poster) {
            this.shop_poster = shop_poster;
        }

        public String getLocal_service() {
            return local_service;
        }

        public void setLocal_service(String local_service) {
            this.local_service = local_service;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getShop_image() {
            return shop_image;
        }

        public void setShop_image(String shop_image) {
            this.shop_image = shop_image;
        }

        public String getIs_supply() {
            return is_supply;
        }

        public void setIs_supply(String is_supply) {
            this.is_supply = is_supply;
        }
    }

    public static class ShopLikeBean {
        /**
         * shop_id : 113
         * shop_poster : /shop/113/images/2018/02/26/15196347658363.png
         * local_service : 0
         * trips : 53
         * shop_name : 燃尚全品店
         * shop_logo : /shop/113/images/2018/11/23/15429567914428.jpg
         * shop_image : /shop/113/images/2018/11/23/15429567914428.jpg
         * is_supply : 0
         */

        private String shop_id;
        private String shop_poster;
        private String local_service;
        private String trips;
        private String shop_name;
        private String shop_logo;
        private String shop_image;
        private String is_supply;
        private String take_out_status;


        public String getTake_out_status() {
            return take_out_status;
        }

        public void setTake_out_status(String take_out_status) {
            this.take_out_status = take_out_status;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_poster() {
            return shop_poster;
        }

        public void setShop_poster(String shop_poster) {
            this.shop_poster = shop_poster;
        }

        public String getLocal_service() {
            return local_service;
        }

        public void setLocal_service(String local_service) {
            this.local_service = local_service;
        }

        public String getTrips() {
            return trips;
        }

        public void setTrips(String trips) {
            this.trips = trips;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getShop_image() {
            return shop_image;
        }

        public void setShop_image(String shop_image) {
            this.shop_image = shop_image;
        }

        public String getIs_supply() {
            return is_supply;
        }

        public void setIs_supply(String is_supply) {
            this.is_supply = is_supply;
        }
    }

    public static class RepurchaseBean {
        /**
         * is_sale : 10001
         * goods_name : 泡泡枪
         * shop_id : 3442
         * goods_image : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/6lc3D1SE79.jpg
         * goods_price : -91
         * goods_id : 42504258230623393
         * shop_name : 天猫小店
         * max_integral_num : 100
         */

        private String is_sale;
        private String goods_name;
        private String cat_id;
        private String shop_id;
        private String goods_image;
        private String goods_price;
        private String goods_id;
        private String shop_name;
        private String local_service;
        private String max_integral_num;


        public String getLocal_service() {
            return local_service;
        }

        public void setLocal_service(String local_service) {
            this.local_service = local_service;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getIs_sale() {
            return is_sale;
        }

        public void setIs_sale(String is_sale) {
            this.is_sale = is_sale;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getMax_integral_num() {
            return max_integral_num;
        }

        public void setMax_integral_num(String max_integral_num) {
            this.max_integral_num = max_integral_num;
        }
    }

    public static class AdvertBean {
        /**
         * area :
         * orderNo : 999
         * liveType : 1
         * isDelete : 0
         * type : 0
         * title : 入驻测试1
         * createTime : 2018-12-18 11:47:58
         * imageUrl : http://images.jibaoh.com/backend/gallery/2018/12/03/15438041884368.gif
         * linkUrl : http://www.baidu.com
         * showType : 1
         * opreateType : 1
         * startTime : 2018-12-18 12:45:40
         * id : d44bb8de488d49f19e25435a5ff819fb1
         * endTime : 2019-12-20 11:45:40
         * refId :
         * position : A4
         * merchantNumStatus : 0
         * status : 1
         */

        private String area;
        private String orderNo;
        private String liveType;
        private String isDelete;
        private String type;
        private String height;
        private String width;
        private String title;
        private String createTime;
        private String imageUrl;
        private String linkUrl;
        private String showType;
        private String opreateType;
        private String startTime;
        private String id;
        private String endTime;
        private String refId;
        private String position;
        private String merchantNumStatus;
        private String status;


        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getLiveType() {
            return liveType;
        }

        public void setLiveType(String liveType) {
            this.liveType = liveType;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getOpreateType() {
            return opreateType;
        }

        public void setOpreateType(String opreateType) {
            this.opreateType = opreateType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getMerchantNumStatus() {
            return merchantNumStatus;
        }

        public void setMerchantNumStatus(String merchantNumStatus) {
            this.merchantNumStatus = merchantNumStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class SortBean {
        /**
         * type : advert
         * key : advert
         */

        private String type;
        private String key;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class NearbyBean {
        /**
         * shop_id : 3442
         * address : 黄山大道东段193号
         * goods_list : [{"is_sale":10001,"goods_name":"泡泡枪","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/6lc3D1SE79.jpg","goods_price":-91,"goods_id":42504258230623393,"max_integral_num":100},{"is_sale":10001,"goods_name":"甜心公主","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/89Tgi5E52c.jpg","goods_price":-85.1,"goods_id":42504258230623362,"max_integral_num":100},{"is_sale":10001,"goods_name":"百草味板栗仁80g","shop_id":3442,"goods_image":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/k38R656yL6.jpg","goods_price":-94,"goods_id":42504258230623375,"max_integral_num":100}]
         * shop_poster : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/17/lL71GTP63U.jpg
         * distance : 32
         * local_service : 1
         * shop_location : 29.614839,106.510658
         * desc_score : 5
         * shop_name : 天猫小店
         * shop_logo : /shop/3442/images/2018/09/17/15371761868154.jpg
         * shop_image : null
         * is_supply : 0
         */

        private String shop_id;
        private String address;
        private String shop_poster;
        private String distance;
        private String distance_msg;
        private String shop_config_msg;
        private String local_service;
        private String shop_location;
        private String dc_least_cost;
        private String dc_cost;
        private String dc_delivery_time;
        private String desc_score;
        private String average_cost;
        private String shop_name;
        private String shop_logo;
        private String shop_image;
        private String is_supply;
        private String take_out_status;
        private List<GoodsListBean> goods_list;


        public String getDistance_msg() {
            return distance_msg;
        }

        public void setDistance_msg(String distance_msg) {
            this.distance_msg = distance_msg;
        }

        public String getShop_config_msg() {
            return shop_config_msg;
        }

        public void setShop_config_msg(String shop_config_msg) {
            this.shop_config_msg = shop_config_msg;
        }

        public String getDc_delivery_time() {
            return dc_delivery_time;
        }

        public void setDc_delivery_time(String dc_delivery_time) {
            this.dc_delivery_time = dc_delivery_time;
        }

        public String getTake_out_status() {
            return take_out_status;
        }

        public void setTake_out_status(String take_out_status) {
            this.take_out_status = take_out_status;
        }

        public String getAverage_cost() {
            return average_cost;
        }

        public void setAverage_cost(String average_cost) {
            this.average_cost = average_cost;
        }

        public String getDc_cost() {
            return dc_cost;
        }

        public void setDc_cost(String dc_cost) {
            this.dc_cost = dc_cost;
        }

        public String getDc_least_cost() {
            return dc_least_cost;
        }

        public void setDc_least_cost(String dc_least_cost) {
            this.dc_least_cost = dc_least_cost;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShop_poster() {
            return shop_poster;
        }

        public void setShop_poster(String shop_poster) {
            this.shop_poster = shop_poster;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLocal_service() {
            return local_service;
        }

        public void setLocal_service(String local_service) {
            this.local_service = local_service;
        }

        public String getShop_location() {
            return shop_location;
        }

        public void setShop_location(String shop_location) {
            this.shop_location = shop_location;
        }

        public String getDesc_score() {
            return desc_score;
        }

        public void setDesc_score(String desc_score) {
            this.desc_score = desc_score;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getShop_image() {
            return shop_image;
        }

        public void setShop_image(String shop_image) {
            this.shop_image = shop_image;
        }

        public String getIs_supply() {
            return is_supply;
        }

        public void setIs_supply(String is_supply) {
            this.is_supply = is_supply;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
            /**
             * is_sale : 10001
             * goods_name : 泡泡枪
             * shop_id : 3442
             * goods_image : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/shop/2018/09/14/6lc3D1SE79.jpg
             * goods_price : -91
             * goods_id : 42504258230623393
             * max_integral_num : 100
             */

            private String is_sale;
            private String goods_name;
            private String cat_id;
            private String shop_id;
            private String goods_image;
            private String goods_price;
            private String goods_id;
            private String max_integral_num;


            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getIs_sale() {
                return is_sale;
            }

            public void setIs_sale(String is_sale) {
                this.is_sale = is_sale;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getMax_integral_num() {
                return max_integral_num;
            }

            public void setMax_integral_num(String max_integral_num) {
                this.max_integral_num = max_integral_num;
            }
        }
    }


    public static class LiveBean implements Serializable{

        /**
         * room_id : 31223_oSGyW5Bh7i7t0NWmO4ReezdBs7Po
         * url_push : rtmp://31223.livepush.myqcloud.com/live/31223_room_01789b42_0f75?bizid=31223&txSecret=eada410bd0d59288e5d7f32663778edf&txTime=5C4C8C5C&record=mp4&record_interval=5400
         * url_review : null
         * activity_img_url : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/activity/live_room/dbn%402x.png
         * anchor_id : 1148
         * activity_url : http://m.jbxgo.com/lbs/index.html#/acerTurntable?activityId=3
         * live_type : 1
         * member_max : 300
         * url_play_acc : rtmp://31223.liveplay.myqcloud.com/live/31223_room_01789b42_0f75?bizid=31223&txSecret=eada410bd0d59288e5d7f32663778edf&txTime=5C4C8C5C
         * url_play_rtmp : rtmp://31223.liveplay.myqcloud.com/live/31223_room_01789b42_0f75
         * live_commend :
         * avatar_url : https://wx.qlogo.cn/mmopen/vi_32/N6EM3HsIO6262PtG9yHNVb5amfDxfgBUNiaCAuGkF5EVfJAiakISRiaSRppd1pt4apkrYOKLhK0ILU1JT8t1BdOFQ/132
         * live_img : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/wx/2019/01/22/a7de486d-1993-0416-2a3a-8f8d2d3fc704.png
         * member_num : 6
         * nick_name : wangzy
         * live_title : yy
         * member_min : 30
         * url_play_flv : http://31223.liveplay.myqcloud.com/live/31223_room_01789b42_0f75.flv
         * channel_id : 31223_room_01789b42_0f75
         * url_play_hls : http://31223.liveplay.myqcloud.com/live/31223_room_01789b42_0f75.m3u8
         * status : 1
         */

        private String room_id;
        private String url_push;
        private String url_review;
        private String activity_img_url;
        private String anchor_id;
        private String activity_url;
        private int live_type;
        private int member_max;
        private String url_play_acc;
        private String url_play_rtmp;
        private String live_commend;
        private String avatar_url;
        private String live_img;
        private int member_num;
        private String nick_name;
        private String live_title;
        private int member_min;
        private String url_play_flv;
        private String channel_id;
        private String url_play_hls;
        private int status;
        private List<GoodsBean> goods;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getUrl_push() {
            return url_push;
        }

        public void setUrl_push(String url_push) {
            this.url_push = url_push;
        }

        public String getUrl_review() {
            return url_review;
        }

        public void setUrl_review(String url_review) {
            this.url_review = url_review;
        }

        public String getActivity_img_url() {
            return activity_img_url;
        }

        public void setActivity_img_url(String activity_img_url) {
            this.activity_img_url = activity_img_url;
        }

        public String getAnchor_id() {
            return anchor_id;
        }

        public void setAnchor_id(String anchor_id) {
            this.anchor_id = anchor_id;
        }

        public String getActivity_url() {
            return activity_url;
        }

        public void setActivity_url(String activity_url) {
            this.activity_url = activity_url;
        }

        public int getLive_type() {
            return live_type;
        }

        public void setLive_type(int live_type) {
            this.live_type = live_type;
        }

        public int getMember_max() {
            return member_max;
        }

        public void setMember_max(int member_max) {
            this.member_max = member_max;
        }

        public String getUrl_play_acc() {
            return url_play_acc;
        }

        public void setUrl_play_acc(String url_play_acc) {
            this.url_play_acc = url_play_acc;
        }

        public String getUrl_play_rtmp() {
            return url_play_rtmp;
        }

        public void setUrl_play_rtmp(String url_play_rtmp) {
            this.url_play_rtmp = url_play_rtmp;
        }

        public String getLive_commend() {
            return live_commend;
        }

        public void setLive_commend(String live_commend) {
            this.live_commend = live_commend;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getLive_img() {
            return live_img;
        }

        public void setLive_img(String live_img) {
            this.live_img = live_img;
        }

        public int getMember_num() {
            return member_num;
        }

        public void setMember_num(int member_num) {
            this.member_num = member_num;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getLive_title() {
            return live_title;
        }

        public void setLive_title(String live_title) {
            this.live_title = live_title;
        }

        public int getMember_min() {
            return member_min;
        }

        public void setMember_min(int member_min) {
            this.member_min = member_min;
        }

        public String getUrl_play_flv() {
            return url_play_flv;
        }

        public void setUrl_play_flv(String url_play_flv) {
            this.url_play_flv = url_play_flv;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getUrl_play_hls() {
            return url_play_hls;
        }

        public void setUrl_play_hls(String url_play_hls) {
            this.url_play_hls = url_play_hls;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean implements Serializable{
            /**
             * goods_name : 大闸牌阳澄湖大闸蟹公螃蟹3.4-3.0母2.4-2.0四对礼盒装
             * goods_image : /shop/29/gallery/2018/01/31/sp_20180131141259_27672.jpg?x-oss-process=image/resize,m_pad,limit_0,h_400,w_400/auto-orient,1/quality,q_90/watermark,image_d3BuZy9wbGF5LnBuZz94LW9zcy1wcm9jZXNzPWltYWdlL3Jlc2l6ZSxQXzIw,g_center,t_45,x_10,y_10
             * goods_video : http://lyzbimage.jbxgo.com/lyzbjbxgo/videos/shop/4524/gallery/2018/11/30/15435692284921.mp4
             * goods_price : 155
             * cat_id : 388
             * goods_id : 7816
             * sku_id : 8304
             * price_fomat : ￥155+22元宝
             * price_msg : 宝箱价￥177
             * max_integral_num : 22
             */

            private String goods_name;
            private String goods_image;
            private String goods_video;
            private String goods_price;
            private int cat_id;
            private int goods_id;
            private int sku_id;
            private String price_fomat;
            private String price_msg;
            private int max_integral_num;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public String getGoods_video() {
                return goods_video;
            }

            public void setGoods_video(String goods_video) {
                this.goods_video = goods_video;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getCat_id() {
                return cat_id;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public String getPrice_fomat() {
                return price_fomat;
            }

            public void setPrice_fomat(String price_fomat) {
                this.price_fomat = price_fomat;
            }

            public String getPrice_msg() {
                return price_msg;
            }

            public void setPrice_msg(String price_msg) {
                this.price_msg = price_msg;
            }

            public int getMax_integral_num() {
                return max_integral_num;
            }

            public void setMax_integral_num(int max_integral_num) {
                this.max_integral_num = max_integral_num;
            }
        }
    }

}
