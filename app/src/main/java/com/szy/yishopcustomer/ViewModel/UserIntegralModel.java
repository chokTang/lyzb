package com.szy.yishopcustomer.ViewModel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Smart on 2017/7/5.
 */

public class UserIntegralModel {

    /**
     * code : 0
     * data : {"page":{"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":2,"page_count":1,"offset":0,"url":null,"sql":null},"list":[{"current_points":"12","shop_id":"4","shop_name":"王雷_店铺","shop_logo":null},{"current_points":"99943","shop_id":"6","shop_name":"zhaoyunlong_店铺1","shop_logo":"/shop/6/images/2017/07/01/14988804313010.jpg"}],"total_points":"99955","nav_default":"exchange","exchanged_count":"0","context":{"current_time":1499220618,"user_info":{"user_id":78,"user_name":"txh870121","nickname":"txh870121","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":null,"mobile_validated":0,"is_seller":0,"shop_id":0,"last_time":1499219740,"last_ip":"100.109.222.0","last_region_code":null,"user_rank":{"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":3}}}
     * message :
     * limit_urls : ["goods/publish/edit-goods-column","goods/publish/batch-edit-sku-by-sku-id","goods/publish/onsale","goods/publish/offsale","trade/order/edit","trade/order/assign","trade/delivery/quick-delivery","trade/delivery/send-logistics","finance/bill/shop-bill","shop-share","site/upload-video","enable-sousou"]
     */

    public int code;
    public DataBean data;
    public String message;
    public List<String> limit_urls;

    public static class DataBean {
        /**
         * page : {"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":2,"page_count":1,"offset":0,"url":null,"sql":null}
         * list : [{"current_points":"12","shop_id":"4","shop_name":"王雷_店铺","shop_logo":null},{"current_points":"99943","shop_id":"6","shop_name":"zhaoyunlong_店铺1","shop_logo":"/shop/6/images/2017/07/01/14988804313010.jpg"}]
         * total_points : 99955
         * nav_default : exchange
         * exchanged_count : 0
         * context : {"current_time":1499220618,"user_info":{"user_id":78,"user_name":"txh870121","nickname":"txh870121","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":null,"mobile_validated":0,"is_seller":0,"shop_id":0,"last_time":1499219740,"last_ip":"100.109.222.0","last_region_code":null,"user_rank":{"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":3}}
         */

        public PageBean page;
        public String total_points;
        public String nav_default;
        public String exchanged_count;
        public ContextBean context;
        public List<ListBean> list;

        public static class PageBean {
            /**
             * page_key : page
             * page_id : pagination
             * default_page_size : 15
             * cur_page : 1
             * page_size : 10
             * page_size_list : [10,50,500,1000]
             * record_count : 2
             * page_count : 1
             * offset : 0
             * url : null
             * sql : null
             */

            public String page_key;
            public String page_id;
            public int default_page_size;
            public int cur_page;
            public int page_size;
            public int record_count;
            public int page_count;
            public int offset;
            public Object url;
            public Object sql;
            public List<Integer> page_size_list;
        }

        public static class ContextBean {
            /**
             * current_time : 1499220618
             * user_info : {"user_id":78,"user_name":"txh870121","nickname":"txh870121","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":null,"mobile_validated":0,"is_seller":0,"shop_id":0,"last_time":1499219740,"last_ip":"100.109.222.0","last_region_code":null,"user_rank":{"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}}
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"}
             * cart : {"goods_count":3}
             */

            public int current_time;
            public UserInfoBean user_info;
            public ConfigBean config;
            public CartBean cart;

            public static class UserInfoBean {
                /**
                 * user_id : 78
                 * user_name : txh870121
                 * nickname : txh870121
                 * headimg : /system/config/default_image/default_user_portrait_0.png
                 * email : null
                 * email_validated : 0
                 * mobile : null
                 * mobile_validated : 0
                 * is_seller : 0
                 * shop_id : 0
                 * last_time : 1499219740
                 * last_ip : 100.109.222.0
                 * last_region_code : null
                 * user_rank : {"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}
                 */

                public int user_id;
                public String user_name;
                public String nickname;
                public String headimg;
                public Object email;
                public int email_validated;
                public Object mobile;
                public int mobile_validated;
                public int is_seller;
                public int shop_id;
                public int last_time;
                public String last_ip;
                public Object last_region_code;
                public UserRankBean user_rank;

                public static class UserRankBean {
                    /**
                     * rank_id : 1
                     * rank_name : 注册会员
                     * rank_img : /user/rank/2017/04/12/14919737728332.jpg
                     * min_points : 0
                     * max_points : 1
                     * type : 0
                     * is_special : 0
                     */

                    public int rank_id;
                    public String rank_name;
                    public String rank_img;
                    public int min_points;
                    public int max_points;
                    public int type;
                    public int is_special;
                }
            }

            public static class ConfigBean {
                /**
                 * mall_logo : /system/config/mall/mall_logo_0.png
                 * site_name : 小京东+测试站
                 * user_center_logo : /system/config/mall/user_center_logo_0.png
                 * mall_region_code : 13,03,02
                 * mall_region_name : {"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"}
                 * mall_address : 秦皇半岛51号楼3层　
                 * site_icp : 12222222222222222
                 * site_copyright : <script type="text/javascript"></script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。
                 * site_powered_by :
                 * mall_phone : 13333344125
                 * mall_email : zhaoyunlong@68ecshop.com
                 * mall_wx_qrcode : /system/config/mall/mall_wx_qrcode_0.jpg
                 * mall_qq : 4
                 * mall_wangwang :
                 * default_user_portrait : /system/config/default_image/default_user_portrait_0.png
                 * favicon : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg
                 * aliim_appkey : 23488235
                 * aliim_secret_key : b88d4dd831e463d7ec451d7c171a323e
                 * aliim_main_customer : xn8801160116
                 * aliim_customer_logo : /system/config/aliim/aliim_customer_logo_0.gif
                 * aliim_welcome_words :
                 * aliim_uid : d41d8cd98f00b204e9800998ecf8427e
                 * aliim_pwd : d41d8cd98f00b204e9800998ecf8427e
                 */

                public String mall_logo;
                public String site_name;
                public String user_center_logo;
                public String mall_region_code;
                public MallRegionNameBean mall_region_name;
                public String mall_address;
                public String site_icp;
                public String site_copyright;
                public String site_powered_by;
                public String mall_phone;
                public String mall_email;
                public String mall_wx_qrcode;
                public String mall_qq;
                public String mall_wangwang;
                public String default_user_portrait;
                public String favicon;
                public String aliim_appkey;
                public String aliim_secret_key;
                public String aliim_main_customer;
                public String aliim_customer_logo;
                public String aliim_welcome_words;
                public String aliim_uid;
                public String aliim_pwd;

                public static class MallRegionNameBean {
                    /**
                     * 13 : 河北省
                     * 13,03 : 秦皇岛市
                     * 13,03,02 : 海港区
                     */

                    @JSONField(name = "13")
                    public String _$13;
                    @JSONField(name = "13,03")
                    public String _$_1303141; // FIXME check this code
                    @JSONField(name = "13,03,02")
                    public String _$_130302165; // FIXME check this code
                }
            }

            public static class CartBean {
                /**
                 * goods_count : 3
                 */

                public int goods_count;
            }
        }

        public static class ListBean {
            /**
             * current_points : 12
             * shop_id : 4
             * shop_name : 王雷_店铺
             * shop_logo : null
             */

            public String current_points;
            public String shop_id;
            public String shop_name;
            public String shop_logo;
        }
    }
}
