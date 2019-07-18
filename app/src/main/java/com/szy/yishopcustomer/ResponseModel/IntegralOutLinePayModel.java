package com.szy.yishopcustomer.ResponseModel;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Smart on 2018/1/17.
 */

public class IntegralOutLinePayModel {


    /**
     * code : 0
     * data : {"logo":"/system/config/default_image/default_shop_image_0.jpg","name":"商之翼\u2022云商城","bg_image":"","shop_id":0,"user_info":{"birthday":1515945600,"user_id":83,"user_name":"SZY182MKXY8696","mobile":"18233588696","email":null,"is_seller":0,"shop_id":0,"store_id":0,"password":"$2y$13$uf1bPjLSPHEB09mGN65K1.TRfQ8Z8Cr/ZYKpKu5rLGTZoWlb.MBKK","password_reset_token":"","salt":"","nickname":"小呀小二郎测试","sex":2,"address_now":"11","detail_address":"ggggg","headimg":"/upload/images/2018/01/13/15158261063360.jpg","user_money":"7.42","user_money_limit":"362.50","frozen_money":"0.00","pay_point":81,"rank_point":16614,"address_id":170,"rank_id":17,"rank_start_time":0,"rank_end_time":0,"mobile_validated":1,"email_validated":0,"reg_time":1497332838,"reg_ip":"100.109.222.30","last_time":1516181511,"last_ip":"111.227.182.176","visit_count":4198,"mobile_supplier":"中国移动","mobile_province":"河北","mobile_city":"秦皇岛","reg_from":"1","surplus_password":null,"status":1,"auth_key":"T-0M1axCC4LQA2iF0q92tFAuawT8ip8k","type":0,"is_real":2,"shopping_status":1,"comment_status":1,"role_id":0,"auth_codes":null,"company_name":null,"company_region_code":null,"company_address":null,"purpose_type":null,"referral_mobile":null,"employees":null,"industry":null,"nature":null,"contact_name":null,"department":null,"company_tel":null,"qq_key":"qq_A742A588DC5973BE77413B7B2EE9B741","weibo_key":"weibo_2464376437","weixin_key":"weixin_o7rSzw71Z8UlFC1fpdRVasWiLOT8","user_remark":null,"invite_code":null,"parent_id":0,"is_recommend":0,"invitation_code":null,"balance":"7.42","balance_format":"￥7.42"},"model":{"shop_id":0,"amount":"","points":"","payment_code":"","order_sn":"","balance_password":"","clientRuleCache":"cache"},"payment_list":null,"balance_password_enable":0,"context":{"current_time":1516181540,"user_info":{"user_id":83,"user_name":"SZY182MKXY8696","nickname":"小呀小二郎测试","headimg":"/upload/images/2018/01/13/15158261063360.jpg","email":null,"email_validated":0,"mobile":"18233588696","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1516181511,"last_ip":"111.227.182.176","last_region_code":null,"user_rank":{"rank_id":"17","rank_name":"v3","rank_img":"/backend/1/images/2017/12/13/15131514917386.png","min_points":"35","max_points":"999999","type":"0","is_special":"0"},"unread_msg_cnt":"399"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼\u2022云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"323829493","site_copyright":"秦皇岛市海港区文化路203号","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"<img src=\"http://68test.oss-cn-beijing.aliyuncs.com/images/746/backend/1/images/2018/01/12/15157451858331.jpg\" alt=\"\" width=\"256\" height=\"256\" />2133456","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"944932343","mall_wangwang":"wanwan","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.png","mall_wx_qrcode":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.gif","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.jpg","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"},"site_id":0,"cart":{"goods_count":10}}}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * logo : /system/config/default_image/default_shop_image_0.jpg
         * name : 商之翼•云商城
         * bg_image :
         * shop_id : 0
         * user_info : {"birthday":1515945600,"user_id":83,"user_name":"SZY182MKXY8696","mobile":"18233588696","email":null,"is_seller":0,"shop_id":0,"store_id":0,"password":"$2y$13$uf1bPjLSPHEB09mGN65K1.TRfQ8Z8Cr/ZYKpKu5rLGTZoWlb.MBKK","password_reset_token":"","salt":"","nickname":"小呀小二郎测试","sex":2,"address_now":"11","detail_address":"ggggg","headimg":"/upload/images/2018/01/13/15158261063360.jpg","user_money":"7.42","user_money_limit":"362.50","frozen_money":"0.00","pay_point":81,"rank_point":16614,"address_id":170,"rank_id":17,"rank_start_time":0,"rank_end_time":0,"mobile_validated":1,"email_validated":0,"reg_time":1497332838,"reg_ip":"100.109.222.30","last_time":1516181511,"last_ip":"111.227.182.176","visit_count":4198,"mobile_supplier":"中国移动","mobile_province":"河北","mobile_city":"秦皇岛","reg_from":"1","surplus_password":null,"status":1,"auth_key":"T-0M1axCC4LQA2iF0q92tFAuawT8ip8k","type":0,"is_real":2,"shopping_status":1,"comment_status":1,"role_id":0,"auth_codes":null,"company_name":null,"company_region_code":null,"company_address":null,"purpose_type":null,"referral_mobile":null,"employees":null,"industry":null,"nature":null,"contact_name":null,"department":null,"company_tel":null,"qq_key":"qq_A742A588DC5973BE77413B7B2EE9B741","weibo_key":"weibo_2464376437","weixin_key":"weixin_o7rSzw71Z8UlFC1fpdRVasWiLOT8","user_remark":null,"invite_code":null,"parent_id":0,"is_recommend":0,"invitation_code":null,"balance":"7.42","balance_format":"￥7.42"}
         * model : {"shop_id":0,"amount":"","points":"","payment_code":"","order_sn":"","balance_password":"","clientRuleCache":"cache"}
         * payment_list : null
         * balance_password_enable : 0
         * context : {"current_time":1516181540,"user_info":{"user_id":83,"user_name":"SZY182MKXY8696","nickname":"小呀小二郎测试","headimg":"/upload/images/2018/01/13/15158261063360.jpg","email":null,"email_validated":0,"mobile":"18233588696","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1516181511,"last_ip":"111.227.182.176","last_region_code":null,"user_rank":{"rank_id":"17","rank_name":"v3","rank_img":"/backend/1/images/2017/12/13/15131514917386.png","min_points":"35","max_points":"999999","type":"0","is_special":"0"},"unread_msg_cnt":"399"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼\u2022云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"323829493","site_copyright":"秦皇岛市海港区文化路203号","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"<img src=\"http://68test.oss-cn-beijing.aliyuncs.com/images/746/backend/1/images/2018/01/12/15157451858331.jpg\" alt=\"\" width=\"256\" height=\"256\" />2133456","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"944932343","mall_wangwang":"wanwan","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.png","mall_wx_qrcode":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.gif","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.jpg","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"},"site_id":0,"cart":{"goods_count":10}}
         */

        public String logo;
        public String name;
        public String bg_image;
        public int shop_id;
        public UserInfoBean user_info;
        public ModelBean model;
        public String payment_list;
        public int balance_password_enable;
        public String context;

        public static class UserInfoBean {
            /**
             * birthday : 1515945600
             * user_id : 83
             * user_name : SZY182MKXY8696
             * mobile : 18233588696
             * email : null
             * is_seller : 0
             * shop_id : 0
             * store_id : 0
             * password : $2y$13$uf1bPjLSPHEB09mGN65K1.TRfQ8Z8Cr/ZYKpKu5rLGTZoWlb.MBKK
             * password_reset_token :
             * salt :
             * nickname : 小呀小二郎测试
             * sex : 2
             * address_now : 11
             * detail_address : ggggg
             * headimg : /upload/images/2018/01/13/15158261063360.jpg
             * user_money : 7.42
             * user_money_limit : 362.50
             * frozen_money : 0.00
             * pay_point : 81
             * rank_point : 16614
             * address_id : 170
             * rank_id : 17
             * rank_start_time : 0
             * rank_end_time : 0
             * mobile_validated : 1
             * email_validated : 0
             * reg_time : 1497332838
             * reg_ip : 100.109.222.30
             * last_time : 1516181511
             * last_ip : 111.227.182.176
             * visit_count : 4198
             * mobile_supplier : 中国移动
             * mobile_province : 河北
             * mobile_city : 秦皇岛
             * reg_from : 1
             * surplus_password : null
             * status : 1
             * auth_key : T-0M1axCC4LQA2iF0q92tFAuawT8ip8k
             * type : 0
             * is_real : 2
             * shopping_status : 1
             * comment_status : 1
             * role_id : 0
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
             * qq_key : qq_A742A588DC5973BE77413B7B2EE9B741
             * weibo_key : weibo_2464376437
             * weixin_key : weixin_o7rSzw71Z8UlFC1fpdRVasWiLOT8
             * user_remark : null
             * invite_code : null
             * parent_id : 0
             * is_recommend : 0
             * invitation_code : null
             * balance : 7.42
             * balance_format : ￥7.42
             */

            public int birthday;
            public int user_id;
            public String user_name;
            public String mobile;
            public String email;
            public int is_seller;
            public int shop_id;
            public int store_id;
            public String password;
            public String password_reset_token;
            public String salt;
            public String nickname;
            public int sex;
            public String address_now;
            public String detail_address;
            public String headimg;
            public String user_money;
            public String user_money_limit;
            public String frozen_money;
            public int pay_point;
            public int rank_point;
            public int address_id;
            public int rank_id;
            public int rank_start_time;
            public int rank_end_time;
            public int mobile_validated;
            public int email_validated;
            public int reg_time;
            public String reg_ip;
            public int last_time;
            public String last_ip;
            public int visit_count;
            public String mobile_supplier;
            public String mobile_province;
            public String mobile_city;
            public String reg_from;
            public int status;
            public String auth_key;
            public int type;
            public int is_real;
            public int shopping_status;
            public int comment_status;
            public int role_id;
            public int parent_id;
            public int is_recommend;
            public Object invitation_code;
            public String balance;
            public String balance_format;
        }

        public static class ModelBean {
            /**
             * shop_id : 0
             * amount :
             * points :
             * payment_code :
             * order_sn :
             * balance_password :
             * clientRuleCache : cache
             */

            public String shop_id;
            public String amount;
            public String points;
            public String payment_code;
            public String order_sn;
            public String balance_password;
            public String clientRuleCache;
        }

    }
}
