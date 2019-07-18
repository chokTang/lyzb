package com.szy.yishopcustomer.ResponseModel;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Smart on 2018/1/17.
 */

public class BalancePayResultModel {

    /**
     * code : 0
     * data : {"name":"广缘超市啦啦啦","shop_id":"6","amount":0.01,"amount_format":"￥0.01","points":0,"context":{"current_time":1516174086,"user_info":{"user_id":83,"user_name":"SZY182MKXY8696","nickname":"小呀小二郎测试","headimg":"/upload/images/2018/01/13/15158261063360.jpg","email":null,"email_validated":0,"mobile":"18233588696","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1516173985,"last_ip":"111.227.182.176","last_region_code":null,"user_rank":{"rank_id":"17","rank_name":"v3","rank_img":"/backend/1/images/2017/12/13/15131514917386.png","min_points":"35","max_points":"999999","type":"0","is_special":"0"},"unread_msg_cnt":"398"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼\u2022云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"323829493","site_copyright":"秦皇岛市海港区文化路203号","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"<img src=\"http://68test.oss-cn-beijing.aliyuncs.com/images/746/backend/1/images/2018/01/12/15157451858331.jpg\" alt=\"\" width=\"256\" height=\"256\" />2133456","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"944932343","mall_wangwang":"wanwan","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.png","mall_wx_qrcode":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.gif","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.jpg","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"},"site_id":0,"cart":{"goods_count":1}}}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * name : 广缘超市啦啦啦
         * shop_id : 6
         * amount : 0.01
         * amount_format : ￥0.01
         * points : 0
         * context : {"current_time":1516174086,"user_info":{"user_id":83,"user_name":"SZY182MKXY8696","nickname":"小呀小二郎测试","headimg":"/upload/images/2018/01/13/15158261063360.jpg","email":null,"email_validated":0,"mobile":"18233588696","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1516173985,"last_ip":"111.227.182.176","last_region_code":null,"user_rank":{"rank_id":"17","rank_name":"v3","rank_img":"/backend/1/images/2017/12/13/15131514917386.png","min_points":"35","max_points":"999999","type":"0","is_special":"0"},"unread_msg_cnt":"398"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼\u2022云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"323829493","site_copyright":"秦皇岛市海港区文化路203号","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"<img src=\"http://68test.oss-cn-beijing.aliyuncs.com/images/746/backend/1/images/2018/01/12/15157451858331.jpg\" alt=\"\" width=\"256\" height=\"256\" />2133456","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"944932343","mall_wangwang":"wanwan","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.png","mall_wx_qrcode":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.gif","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.jpg","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"},"site_id":0,"cart":{"goods_count":1}}
         */

        public String name;
        public String shop_id;
        public String amount;
        public String amount_format;
        public int points;
        public String context;

    }
}
