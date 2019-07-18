package com.szy.yishopcustomer.ViewModel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Smart on 2017/7/4.
 */

public class RechargeCardInfoModel {

    /**
     * code : 0
     * data : {"model":{"id":null,"card_number":null,"pass_word":null,"type_id":null,"card_status":null,"user_id":null,"use_time":null,"add_time":null},"user_info":{"user_id":null,"user_name":null,"mobile":null,"email":null,"is_seller":null,"shop_id":null,"store_id":null,"password":null,"password_reset_token":null,"salt":null,"nickname":null,"sex":null,"birthday":0,"address_now":null,"detail_address":null,"headimg":null,"user_money":"1708.28","user_money_limit":"0.00","frozen_money":"0.00","pay_point":null,"rank_point":null,"address_id":null,"rank_id":null,"rank_start_time":null,"rank_end_time":null,"mobile_validated":null,"email_validated":null,"reg_time":null,"reg_ip":null,"last_time":null,"last_ip":null,"visit_count":null,"mobile_supplier":null,"mobile_province":null,"mobile_city":null,"reg_from":null,"surplus_password":null,"status":null,"auth_key":null,"type":null,"is_real":null,"shopping_status":null,"comment_status":null,"role_id":null,"auth_codes":null,"company_name":null,"company_region_code":null,"company_address":null,"purpose_type":null,"referral_mobile":null,"employees":null,"industry":null,"nature":null,"contact_name":null,"department":null,"company_tel":null,"qq_key":null,"weibo_key":null,"weixin_key":null,"user_remark":null,"invite_code":null,"parent_id":null,"is_recommend":null,"invitation_code":null},"nav_default":"recharge-card","context":{"current_time":1499134179,"user_info":{"user_id":2,"user_name":"liuxiaona","nickname":"哈哈","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":"15133518792","mobile_validated":0,"is_seller":1,"shop_id":2,"last_time":1499133992,"last_ip":"100.109.222.51","last_region_code":null,"user_rank":{"rank_id":11,"rank_name":"铜牌会员","rank_img":"/backend/1/images/2017/04/12/14919738061835.jpg","min_points":2,"max_points":10,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"800007396","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":1}}}
     * message :
     * limit_urls : ["goods/publish/edit-goods-column","goods/publish/batch-edit-sku-by-sku-id","goods/publish/onsale","goods/publish/offsale","trade/order/edit","trade/order/assign","trade/delivery/quick-delivery","trade/delivery/send-logistics","finance/bill/shop-bill","shop-share","site/upload-video","enable-sousou"]
     */

    public int code;
    public DataBean data;
    public String message;
    public List<String> limit_urls;

    public static class DataBean {
        /**
         * model : {"id":null,"card_number":null,"pass_word":null,"type_id":null,"card_status":null,"user_id":null,"use_time":null,"add_time":null}
         * user_info : {"user_id":null,"user_name":null,"mobile":null,"email":null,"is_seller":null,"shop_id":null,"store_id":null,"password":null,"password_reset_token":null,"salt":null,"nickname":null,"sex":null,"birthday":0,"address_now":null,"detail_address":null,"headimg":null,"user_money":"1708.28","user_money_limit":"0.00","frozen_money":"0.00","pay_point":null,"rank_point":null,"address_id":null,"rank_id":null,"rank_start_time":null,"rank_end_time":null,"mobile_validated":null,"email_validated":null,"reg_time":null,"reg_ip":null,"last_time":null,"last_ip":null,"visit_count":null,"mobile_supplier":null,"mobile_province":null,"mobile_city":null,"reg_from":null,"surplus_password":null,"status":null,"auth_key":null,"type":null,"is_real":null,"shopping_status":null,"comment_status":null,"role_id":null,"auth_codes":null,"company_name":null,"company_region_code":null,"company_address":null,"purpose_type":null,"referral_mobile":null,"employees":null,"industry":null,"nature":null,"contact_name":null,"department":null,"company_tel":null,"qq_key":null,"weibo_key":null,"weixin_key":null,"user_remark":null,"invite_code":null,"parent_id":null,"is_recommend":null,"invitation_code":null}
         * nav_default : recharge-card
         * context : {"current_time":1499134179,"user_info":{"user_id":2,"user_name":"liuxiaona","nickname":"哈哈","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":"15133518792","mobile_validated":0,"is_seller":1,"shop_id":2,"last_time":1499133992,"last_ip":"100.109.222.51","last_region_code":null,"user_rank":{"rank_id":11,"rank_name":"铜牌会员","rank_img":"/backend/1/images/2017/04/12/14919738061835.jpg","min_points":2,"max_points":10,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"800007396","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":1}}
         */

        public ModelBean model;
        public UserInfoBean user_info;
        public String nav_default;
        public ContextBean context;

        public static class ModelBean {
            /**
             * id : null
             * card_number : null
             * pass_word : null
             * type_id : null
             * card_status : null
             * user_id : null
             * use_time : null
             * add_time : null
             */

            public Object id;
            public Object card_number;
            public Object pass_word;
            public Object type_id;
            public Object card_status;
            public Object user_id;
            public Object use_time;
            public Object add_time;
        }

        public static class UserInfoBean {
            /**
             * user_id : null
             * user_name : null
             * mobile : null
             * email : null
             * is_seller : null
             * shop_id : null
             * store_id : null
             * password : null
             * password_reset_token : null
             * salt : null
             * nickname : null
             * sex : null
             * birthday : 0
             * address_now : null
             * detail_address : null
             * headimg : null
             * user_money : 1708.28
             * user_money_limit : 0.00
             * frozen_money : 0.00
             * pay_point : null
             * rank_point : null
             * address_id : null
             * rank_id : null
             * rank_start_time : null
             * rank_end_time : null
             * mobile_validated : null
             * email_validated : null
             * reg_time : null
             * reg_ip : null
             * last_time : null
             * last_ip : null
             * visit_count : null
             * mobile_supplier : null
             * mobile_province : null
             * mobile_city : null
             * reg_from : null
             * surplus_password : null
             * status : null
             * auth_key : null
             * type : null
             * is_real : null
             * shopping_status : null
             * comment_status : null
             * role_id : null
             * auth_codes : null
             * company_name : null
             * company_region_code : null
             * company_address : null
             * purpose_type : null
             * referral_mobile : null
             * employees : null
             * industry : null
             * nature : null
             * contact_name : null
             * department : null
             * company_tel : null
             * qq_key : null
             * weibo_key : null
             * weixin_key : null
             * user_remark : null
             * invite_code : null
             * parent_id : null
             * is_recommend : null
             * invitation_code : null
             */

            public Object user_id;
            public Object user_name;
            public Object mobile;
            public Object email;
            public Object is_seller;
            public Object shop_id;
            public Object store_id;
            public Object password;
            public Object password_reset_token;
            public Object salt;
            public Object nickname;
            public Object sex;
            public int birthday;
            public Object address_now;
            public Object detail_address;
            public Object headimg;
            public String user_money;
            public String user_money_limit;
            public String frozen_money;
            public Object pay_point;
            public Object rank_point;
            public Object address_id;
            public Object rank_id;
            public Object rank_start_time;
            public Object rank_end_time;
            public Object mobile_validated;
            public Object email_validated;
            public Object reg_time;
            public Object reg_ip;
            public Object last_time;
            public Object last_ip;
            public Object visit_count;
            public Object mobile_supplier;
            public Object mobile_province;
            public Object mobile_city;
            public Object reg_from;
            public Object surplus_password;
            public Object status;
            public Object auth_key;
            public Object type;
            public Object is_real;
            public Object shopping_status;
            public Object comment_status;
            public Object role_id;
            public Object auth_codes;
            public Object company_name;
            public Object company_region_code;
            public Object company_address;
            public Object purpose_type;
            public Object referral_mobile;
            public Object employees;
            public Object industry;
            public Object nature;
            public Object contact_name;
            public Object department;
            public Object company_tel;
            public Object qq_key;
            public Object weibo_key;
            public Object weixin_key;
            public Object user_remark;
            public Object invite_code;
            public Object parent_id;
            public Object is_recommend;
            public Object invitation_code;
        }

        public static class ContextBean {
            /**
             * current_time : 1499134179
             * user_info : {"user_id":2,"user_name":"liuxiaona","nickname":"哈哈","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":"15133518792","mobile_validated":0,"is_seller":1,"shop_id":2,"last_time":1499133992,"last_ip":"100.109.222.51","last_region_code":null,"user_rank":{"rank_id":11,"rank_name":"铜牌会员","rank_img":"/backend/1/images/2017/04/12/14919738061835.jpg","min_points":2,"max_points":10,"type":0,"is_special":0}}
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"800007396","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"}
             * cart : {"goods_count":1}
             */

            public int current_time;
            public UserInfoBeanX user_info;
            public ConfigBean config;
            public CartBean cart;

            public static class UserInfoBeanX {
                /**
                 * user_id : 2
                 * user_name : liuxiaona
                 * nickname : 哈哈
                 * headimg : /system/config/default_image/default_user_portrait_0.png
                 * email : null
                 * email_validated : 0
                 * mobile : 15133518792
                 * mobile_validated : 0
                 * is_seller : 1
                 * shop_id : 2
                 * last_time : 1499133992
                 * last_ip : 100.109.222.51
                 * last_region_code : null
                 * user_rank : {"rank_id":11,"rank_name":"铜牌会员","rank_img":"/backend/1/images/2017/04/12/14919738061835.jpg","min_points":2,"max_points":10,"type":0,"is_special":0}
                 */

                public int user_id;
                public String user_name;
                public String nickname;
                public String headimg;
                public Object email;
                public int email_validated;
                public String mobile;
                public int mobile_validated;
                public int is_seller;
                public int shop_id;
                public int last_time;
                public String last_ip;
                public Object last_region_code;
                public UserRankBean user_rank;

                public static class UserRankBean {
                    /**
                     * rank_id : 11
                     * rank_name : 铜牌会员
                     * rank_img : /backend/1/images/2017/04/12/14919738061835.jpg
                     * min_points : 2
                     * max_points : 10
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
                 * mall_qq : 800007396
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
                    public String _$_1303211; // FIXME check this code
                    @JSONField(name = "13,03,02")
                    public String _$_130302165; // FIXME check this code
                }
            }

            public static class CartBean {
                /**
                 * goods_count : 1
                 */

                public int goods_count;
            }
        }
    }
}
