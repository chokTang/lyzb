package com.szy.yishopcustomer.ResponseModel;

import java.util.List;

/**
 * Created by Smart on 2017/8/30.
 */

public class CardUsageModel {

    /**
     * code : 0
     * data : {"list":[{"log_id":"3","card_id":"37","use_type":"0","amount":"-5.00","order_sn":"20170825064416243460","add_time":"1503643456"}],"page":{"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":1,"page_count":1,"offset":0,"url":null,"sql":null},"context":{"current_time":1504081315,"user_info":{"user_id":27,"user_name":"SZY133ZPVI4125","nickname":"l2s，","headimg":"/user/27/headimg/15035445445690.png","email":"83595875@qq.com","email_validated":1,"mobile":"13333344125","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1504081093,"last_ip":"100.109.222.33","last_region_code":null,"user_rank":{"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"100000","type":"0","is_special":"0"},"unread_msg_cnt":"40"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"4","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"site_id":0,"cart":{"goods_count":16}}}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * list : [{"log_id":"3","card_id":"37","use_type":"0","amount":"-5.00","order_sn":"20170825064416243460","add_time":"1503643456"}]
         * page : {"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":1,"page_count":1,"offset":0,"url":null,"sql":null}
         * context : {"current_time":1504081315,"user_info":{"user_id":27,"user_name":"SZY133ZPVI4125","nickname":"l2s，","headimg":"/user/27/headimg/15035445445690.png","email":"83595875@qq.com","email_validated":1,"mobile":"13333344125","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1504081093,"last_ip":"100.109.222.33","last_region_code":null,"user_rank":{"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"100000","type":"0","is_special":"0"},"unread_msg_cnt":"40"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"4","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"site_id":0,"cart":{"goods_count":16}}
         */

        public PageBean page;
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
             * record_count : 1
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
             * current_time : 1504081315
             * user_info : {"user_id":27,"user_name":"SZY133ZPVI4125","nickname":"l2s，","headimg":"/user/27/headimg/15035445445690.png","email":"83595875@qq.com","email_validated":1,"mobile":"13333344125","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1504081093,"last_ip":"100.109.222.33","last_region_code":null,"user_rank":{"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"100000","type":"0","is_special":"0"},"unread_msg_cnt":"40"}
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"4","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"}
             * site_id : 0
             * cart : {"goods_count":16}
             */

            public int current_time;
            public String user_info;
            public String config;
            public int site_id;
            public CartBean cart;

            public static class CartBean {
                /**
                 * goods_count : 16
                 */

                public int goods_count;
            }
        }

        public static class ListBean {
            /**
             * log_id : 3
             * card_id : 37
             * use_type : 0
             * amount : -5.00
             * order_sn : 20170825064416243460
             * add_time : 1503643456
             */

            public String log_id;
            public String card_id;
            public String use_type;
            public String amount;
            public String order_sn;
            public String add_time;
        }
    }
}
