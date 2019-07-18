package com.szy.yishopcustomer.ViewModel;

import com.alibaba.fastjson.annotation.JSONField;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;

import java.util.List;

/**
 * Created by Smart on 2017/7/11.
 */

public class PickUpListModel {

    /**
     * code : 0
     * data : {"pickup":[{"pickup_id":4,"pickup_name":"白塔岭自提点","region_code":"13,03,02","pickup_address":"西港镇北京博苑发现王国幼儿园森林家园1区","address_lng":"119.519231","address_lat":"39.89434","pickup_tel":"15111111111","pickup_desc":"测试","pickup_images":"/shop/2/images/2017/06/05/14966322781214.png","shop_id":2,"is_show":1,"sort":255},{"pickup_id":8,"pickup_name":"石测试1","region_code":"13,03","pickup_address":"海港区东环路街道建设大街辅路","address_lng":"119.66962","address_lat":"39.944374","pickup_tel":"","pickup_desc":"","pickup_images":"/shop/2/images/2017/06/15/14975068274754.jpg","shop_id":2,"is_show":1,"sort":255}],"smarty_output":false,"context":{"current_time":1499760223,"user_info":{"last_region_code":null},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":0}}}
     * message :
     * limit_urls : ["goods/publish/edit-goods-column","goods/publish/batch-edit-sku-by-sku-id","goods/publish/onsale","goods/publish/offsale","trade/order/edit","trade/order/assign","trade/delivery/quick-delivery","trade/delivery/send-logistics","finance/bill/shop-bill","shop-share","site/upload-video","enable-sousou"]
     */

    public int code;
    public DataBean data;
    public String message;
    public List<String> limit_urls;

    public static class DataBean {
        /**
         * pickup : [{"pickup_id":4,"pickup_name":"白塔岭自提点","region_code":"13,03,02","pickup_address":"西港镇北京博苑发现王国幼儿园森林家园1区","address_lng":"119.519231","address_lat":"39.89434","pickup_tel":"15111111111","pickup_desc":"测试","pickup_images":"/shop/2/images/2017/06/05/14966322781214.png","shop_id":2,"is_show":1,"sort":255},{"pickup_id":8,"pickup_name":"石测试1","region_code":"13,03","pickup_address":"海港区东环路街道建设大街辅路","address_lng":"119.66962","address_lat":"39.944374","pickup_tel":"","pickup_desc":"","pickup_images":"/shop/2/images/2017/06/15/14975068274754.jpg","shop_id":2,"is_show":1,"sort":255}]
         * smarty_output : false
         * context : {"current_time":1499760223,"user_info":{"last_region_code":null},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":0}}
         */

        public boolean smarty_output;
        public ContextBean context;
        public List<PickUpModel> pickup;

        public static class ContextBean {
            /**
             * current_time : 1499760223
             * user_info : {"last_region_code":null}
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"}
             * cart : {"goods_count":0}
             */

            public int current_time;
            public UserInfoBean user_info;
            public ConfigBean config;
            public CartBean cart;

            public static class UserInfoBean {
                /**
                 * last_region_code : null
                 */

                public Object last_region_code;
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
                    public String _$_1303196; // FIXME check this code
                    @JSONField(name = "13,03,02")
                    public String _$_130302180; // FIXME check this code
                }
            }

            public static class CartBean {
                /**
                 * goods_count : 0
                 */

                public int goods_count;
            }
        }

    }
}
