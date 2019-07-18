package com.szy.yishopcustomer.ResponseModel.Shop;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Smart on 2017/5/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ResponseShopPrepareModel {

    /**
     * code : 0
     * data : {"page":{"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":2,"page_count":1,"offset":0,"url":null,"sql":null},"list":[{"id":"8","shop_id":"71","user_id":"27","message":"hhha","add_time":"1499399575","is_show":"0","user_name":"S**5","headimg":"http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0","nickname":"ls"},{"id":"6","shop_id":"71","user_id":"78","message":"唐测试留言","add_time":"1499399057","is_show":"1","user_name":"t**1","headimg":"/user/78/images/2017/07/24/15008578685757.jpeg","nickname":"txh870121"}],"recommend_shop_info":{"id":"42","shop_id":"71","shop_name":"太阳当空照","cat_id":"0","mobile":"13333333333","region_code":"13,03,02","address":"花儿对我笑笑","shop_lng":"","shop_lat":"","shop_type":"1","res_reason":"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈","examine_reason":"","facade_img":"","card_img":"","user_id":"27","add_time":"1499397734","status":"1","remark":"'"},"shop_info":{"shop_id":"71","user_id":"0","site_id":"0","shop_name":"太阳当空照","shop_image":"/system/config/default_image/default_shop_image_0.jpg","shop_logo":null,"shop_poster":null,"shop_sign":null,"shop_type":"1","is_supply":"0","cat_id":"0","credit":"0","desc_score":"5.00","service_score":"5.00","send_score":"5.00","logistics_score":"5.00","region_code":"13,03,02","address":"花儿对我笑笑","shop_lng":"","shop_lat":"","opening_hour":"","add_time":"1499398099","pass_time":null,"duration":"0","unit":"0","clearing_cycle":"0","open_time":"0","end_time":"0","system_fee":"0.00","insure_fee":"0.00","goods_status":"1","shop_status":"1","close_info":null,"shop_sort":"255","shop_audit":"0","fail_info":null,"simply_introduce":null,"shop_keywords":null,"shop_description":null,"detail_introduce":null,"service_tel":"13333333333","service_hours":null,"shop_sign_m":null,"take_rate":"0.00","qrcode_take_rate":"0.00","collect_allow_number":"0","collected_number":"0","store_allow_number":"0","store_number":"0","comment_allow_number":"0","comment_number":"0","login_status":"1","show_credit":"1","show_in_street":"1","goods_is_show":"1","control_price":"1","show_price":"1","show_content":null,"button_content":null,"button_url":null,"start_price":"0.00","service_tel_format":"133****3333","exists":true,"region_name":"河北省 秦皇岛市 海港区"},"support_count":"2","searchModel":{"shop_id":"71"},"context":{"current_time":1501232324,"user_info":{"user_id":27,"user_name":"SZY133ZPVI4125","nickname":"ls","headimg":"http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0","email":"83595875@qq.com","email_validated":1,"mobile":"13333344125","mobile_validated":1,"is_seller":1,"shop_id":74,"last_time":1501230106,"last_ip":"100.97.126.61","last_region_code":null,"user_rank":{"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"10000","type":"0","is_special":"0"},"unread_msg_cnt":"77"},"config":{"mall_logo":"/site/4/images/2017/04/19/14925857323456.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"444444444444","mall_email":"4444@qq.com","mall_qq":"2222","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png"},"cart":{"goods_count":0}}}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * page : {"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":2,"page_count":1,"offset":0,"url":null,"sql":null}
         * list : [{"id":"8","shop_id":"71","user_id":"27","message":"hhha","add_time":"1499399575","is_show":"0","user_name":"S**5","headimg":"http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0","nickname":"ls"},{"id":"6","shop_id":"71","user_id":"78","message":"唐测试留言","add_time":"1499399057","is_show":"1","user_name":"t**1","headimg":"/user/78/images/2017/07/24/15008578685757.jpeg","nickname":"txh870121"}]
         * recommend_shop_info : {"id":"42","shop_id":"71","shop_name":"太阳当空照","cat_id":"0","mobile":"13333333333","region_code":"13,03,02","address":"花儿对我笑笑","shop_lng":"","shop_lat":"","shop_type":"1","res_reason":"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈","examine_reason":"","facade_img":"","card_img":"","user_id":"27","add_time":"1499397734","status":"1","remark":"'"}
         * shop_info : {"shop_id":"71","user_id":"0","site_id":"0","shop_name":"太阳当空照","shop_image":"/system/config/default_image/default_shop_image_0.jpg","shop_logo":null,"shop_poster":null,"shop_sign":null,"shop_type":"1","is_supply":"0","cat_id":"0","credit":"0","desc_score":"5.00","service_score":"5.00","send_score":"5.00","logistics_score":"5.00","region_code":"13,03,02","address":"花儿对我笑笑","shop_lng":"","shop_lat":"","opening_hour":"","add_time":"1499398099","pass_time":null,"duration":"0","unit":"0","clearing_cycle":"0","open_time":"0","end_time":"0","system_fee":"0.00","insure_fee":"0.00","goods_status":"1","shop_status":"1","close_info":null,"shop_sort":"255","shop_audit":"0","fail_info":null,"simply_introduce":null,"shop_keywords":null,"shop_description":null,"detail_introduce":null,"service_tel":"13333333333","service_hours":null,"shop_sign_m":null,"take_rate":"0.00","qrcode_take_rate":"0.00","collect_allow_number":"0","collected_number":"0","store_allow_number":"0","store_number":"0","comment_allow_number":"0","comment_number":"0","login_status":"1","show_credit":"1","show_in_street":"1","goods_is_show":"1","control_price":"1","show_price":"1","show_content":null,"button_content":null,"button_url":null,"start_price":"0.00","service_tel_format":"133****3333","exists":true,"region_name":"河北省 秦皇岛市 海港区"}
         * support_count : 2
         * searchModel : {"shop_id":"71"}
         * context : {"current_time":1501232324,"user_info":{"user_id":27,"user_name":"SZY133ZPVI4125","nickname":"ls","headimg":"http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0","email":"83595875@qq.com","email_validated":1,"mobile":"13333344125","mobile_validated":1,"is_seller":1,"shop_id":74,"last_time":1501230106,"last_ip":"100.97.126.61","last_region_code":null,"user_rank":{"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"10000","type":"0","is_special":"0"},"unread_msg_cnt":"77"},"config":{"mall_logo":"/site/4/images/2017/04/19/14925857323456.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"444444444444","mall_email":"4444@qq.com","mall_qq":"2222","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png"},"cart":{"goods_count":0}}
         */

        public PageBean page;
        public RecommendShopInfoBean recommend_shop_info;
        public ShopInfoBean shop_info;
        public String support_count;
        public SearchModelBean searchModel;
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

        public static class RecommendShopInfoBean {
            /**
             * id : 42
             * shop_id : 71
             * shop_name : 太阳当空照
             * cat_id : 0
             * mobile : 13333333333
             * region_code : 13,03,02
             * address : 花儿对我笑笑
             * shop_lng :
             * shop_lat :
             * shop_type : 1
             * res_reason : 哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
             * examine_reason :
             * facade_img :
             * card_img :
             * user_id : 27
             * add_time : 1499397734
             * status : 1
             * remark : '
             */

            public String id;
            public String shop_id;
            public String shop_name;
            public String cat_id;
            public String mobile;
            public String region_code;
            public String address;
            public String shop_lng;
            public String shop_lat;
            public String shop_type;
            public String res_reason;
            public String examine_reason;
            public String facade_img;
            public String card_img;
            public String user_id;
            public String add_time;
            public String status;
            public String remark;
        }

        public static class ShopInfoBean {
            /**
             * shop_id : 71
             * user_id : 0
             * site_id : 0
             * shop_name : 太阳当空照
             * shop_image : /system/config/default_image/default_shop_image_0.jpg
             * shop_logo : null
             * shop_poster : null
             * shop_sign : null
             * shop_type : 1
             * is_supply : 0
             * cat_id : 0
             * credit : 0
             * desc_score : 5.00
             * service_score : 5.00
             * send_score : 5.00
             * logistics_score : 5.00
             * region_code : 13,03,02
             * address : 花儿对我笑笑
             * shop_lng :
             * shop_lat :
             * opening_hour :
             * add_time : 1499398099
             * pass_time : null
             * duration : 0
             * unit : 0
             * clearing_cycle : 0
             * open_time : 0
             * end_time : 0
             * system_fee : 0.00
             * insure_fee : 0.00
             * goods_status : 1
             * shop_status : 1
             * close_info : null
             * shop_sort : 255
             * shop_audit : 0
             * fail_info : null
             * simply_introduce : null
             * shop_keywords : null
             * shop_description : null
             * detail_introduce : null
             * service_tel : 13333333333
             * service_hours : null
             * shop_sign_m : null
             * take_rate : 0.00
             * qrcode_take_rate : 0.00
             * collect_allow_number : 0
             * collected_number : 0
             * store_allow_number : 0
             * store_number : 0
             * comment_allow_number : 0
             * comment_number : 0
             * login_status : 1
             * show_credit : 1
             * show_in_street : 1
             * goods_is_show : 1
             * control_price : 1
             * show_price : 1
             * show_content : null
             * button_content : null
             * button_url : null
             * start_price : 0.00
             * service_tel_format : 133****3333
             * exists : true
             * region_name : 河北省 秦皇岛市 海港区
             */

            public String shop_id;
            public String user_id;
            public String site_id;
            public String shop_name;
            public String shop_image;
            public Object shop_logo;
            public Object shop_poster;
            public Object shop_sign;
            public String shop_type;
            public String is_supply;
            public String cat_id;
            public String credit;
            public String desc_score;
            public String service_score;
            public String send_score;
            public String logistics_score;
            public String region_code;
            public String address;
            public String shop_lng;
            public String shop_lat;
            public String opening_hour;
            public String add_time;
            public Object pass_time;
            public String duration;
            public String unit;
            public String clearing_cycle;
            public String open_time;
            public String end_time;
            public String system_fee;
            public String insure_fee;
            public String goods_status;
            public String shop_status;
            public Object close_info;
            public String shop_sort;
            public String shop_audit;
            public Object fail_info;
            public String simply_introduce;
            public Object shop_keywords;
            public Object shop_description;
            public Object detail_introduce;
            public String service_tel;
            public Object service_hours;
            public Object shop_sign_m;
            public String take_rate;
            public String qrcode_take_rate;
            public String collect_allow_number;
            public String collected_number;
            public String store_allow_number;
            public String store_number;
            public String comment_allow_number;
            public String comment_number;
            public String login_status;
            public String show_credit;
            public String show_in_street;
            public String goods_is_show;
            public String control_price;
            public String show_price;
            public Object show_content;
            public Object button_content;
            public Object button_url;
            public String start_price;
            public String service_tel_format;
            public boolean exists;
            public String region_name;
        }

        public static class SearchModelBean {
            /**
             * shop_id : 71
             */

            public String shop_id;
        }

        public static class ContextBean {
            /**
             * current_time : 1501232324
             * user_info : {"user_id":27,"user_name":"SZY133ZPVI4125","nickname":"ls","headimg":"http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0","email":"83595875@qq.com","email_validated":1,"mobile":"13333344125","mobile_validated":1,"is_seller":1,"shop_id":74,"last_time":1501230106,"last_ip":"100.97.126.61","last_region_code":null,"user_rank":{"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"10000","type":"0","is_special":"0"},"unread_msg_cnt":"77"}
             * config : {"mall_logo":"/site/4/images/2017/04/19/14925857323456.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"444444444444","mall_email":"4444@qq.com","mall_qq":"2222","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png"}
             * cart : {"goods_count":0}
             */

            public int current_time;
            public UserInfoBean user_info;
            public ConfigBean config;
            public CartBean cart;

            public static class UserInfoBean {
                /**
                 * user_id : 27
                 * user_name : SZY133ZPVI4125
                 * nickname : ls
                 * headimg : http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0
                 * email : 83595875@qq.com
                 * email_validated : 1
                 * mobile : 13333344125
                 * mobile_validated : 1
                 * is_seller : 1
                 * shop_id : 74
                 * last_time : 1501230106
                 * last_ip : 100.97.126.61
                 * last_region_code : null
                 * user_rank : {"rank_id":"14","rank_name":"金牌会员","rank_img":"/backend/1/images/2017/04/12/14919739201381.jpg","min_points":"1001","max_points":"10000","type":"0","is_special":"0"}
                 * unread_msg_cnt : 77
                 */

                public int user_id;
                public String user_name;
                public String nickname;
                public String headimg;
                public String email;
                public int email_validated;
                public String mobile;
                public int mobile_validated;
                public int is_seller;
                public int shop_id;
                public int last_time;
                public String last_ip;
                public Object last_region_code;
                public UserRankBean user_rank;
                public String unread_msg_cnt;

                public static class UserRankBean {
                    /**
                     * rank_id : 14
                     * rank_name : 金牌会员
                     * rank_img : /backend/1/images/2017/04/12/14919739201381.jpg
                     * min_points : 1001
                     * max_points : 10000
                     * type : 0
                     * is_special : 0
                     */

                    public String rank_id;
                    public String rank_name;
                    public String rank_img;
                    public String min_points;
                    public String max_points;
                    public String type;
                    public String is_special;
                }
            }

            public static class ConfigBean {
                /**
                 * mall_logo : /site/4/images/2017/04/19/14925857323456.jpg
                 * backend_logo : /system/config/website/backend_logo_0.png
                 * site_name : 商之翼·云商城
                 * user_center_logo : /system/config/mall/user_center_logo_0.png
                 * mall_region_code : 13,03,02
                 * mall_region_name : {"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"}
                 * mall_address : 秦皇半岛51号楼3层　
                 * site_icp : 12222222222222222
                 * site_copyright : <script type="text/javascript"></script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。
                 * site_powered_by :
                 * stats_code : <script>
                 var _hmt = _hmt || [];
                 (function() {
                 var hm = document.createElement("script");
                 hm.src = "https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc";
                 var s = document.getElementsByTagName("script")[0];
                 s.parentNode.insertBefore(hm, s);
                 })();
                 </script>
                 * mall_service_right :
                 * open_download_qrcode : 1
                 * mall_phone : 444444444444
                 * mall_email : 4444@qq.com
                 * mall_qq : 2222
                 * mall_wangwang :
                 * favicon : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg
                 * aliim_appkey : 23488235
                 * aliim_secret_key : b88d4dd831e463d7ec451d7c171a323e
                 * aliim_main_customer : xn8801160116
                 * aliim_customer_logo :
                 * aliim_welcome_words :
                 * aliim_uid : d41d8cd98f00b204e9800998ecf8427e
                 * aliim_pwd : d41d8cd98f00b204e9800998ecf8427e
                 * mall_wx_qrcode : /system/config/mall/mall_wx_qrcode_0.jpg
                 * default_user_portrait : /system/config/default_image/default_user_portrait_0.png
                 */

                public String mall_logo;
                public String backend_logo;
                public String site_name;
                public String user_center_logo;
                public String mall_region_code;
                public MallRegionNameBean mall_region_name;
                public String mall_address;
                public String site_icp;
                public String site_copyright;
                public String site_powered_by;
                public String stats_code;
                public String mall_service_right;
                public String open_download_qrcode;
                public String mall_phone;
                public String mall_email;
                public String mall_qq;
                public String mall_wangwang;
                public String favicon;
                public String aliim_appkey;
                public String aliim_secret_key;
                public String aliim_main_customer;
                public String aliim_customer_logo;
                public String aliim_welcome_words;
                public String aliim_uid;
                public String aliim_pwd;
                public String mall_wx_qrcode;
                public String default_user_portrait;

                public static class MallRegionNameBean {
                    /**
                     * 13 : 河北省
                     * 13,03 : 秦皇岛市
                     * 13,03,02 : 海港区
                     */

                    @JSONField(name = "13")
                    public String _$13;
                    @JSONField(name = "13,03")
                    public String _$_130317; // FIXME check this code
                    @JSONField(name = "13,03,02")
                    public String _$_13030273; // FIXME check this code
                }
            }

            public static class CartBean {
                /**
                 * goods_count : 0
                 */

                public int goods_count;
            }
        }

        public static class ListBean {
            /**
             * id : 8
             * shop_id : 71
             * user_id : 27
             * message : hhha
             * add_time : 1499399575
             * is_show : 0
             * user_name : S**5
             * headimg : http://wx.qlogo.cn/mmopen/0sCUic054olricxQ8JuyesXu5Stk3w8fKfX4u1Sk6aGXWVMwLtPV1hia8OTPdfSzCbECYsHibiabTz9FIU1tFtw8y6EW24SxXib4Yz/0
             * nickname : ls
             */

            public String id;
            public String shop_id;
            public String user_id;
            public String message;
            public String add_time;
            public String is_show;
            public String user_name;
            public String headimg;
            public String nickname;
        }
    }
}
