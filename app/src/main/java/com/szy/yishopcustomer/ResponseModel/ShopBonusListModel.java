package com.szy.yishopcustomer.ResponseModel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.Map;

/**
 * Created by Smart on 2017/10/16.
 */

public class ShopBonusListModel {


    /**
     * code : 0
     * data : {"shop_id":"6","shop_info":{"shop":{"shop_id":"6","user_id":"143","site_id":"1","shop_name":"广缘超市","shop_image":"/shop/6/images/2017/08/22/15033822189747.jpg","shop_logo":"/shop/6/images/2017/08/22/15033822316799.jpg","shop_poster":"/shop/6/images/2017/08/22/15033822447937.jpg","shop_sign":"/shop/6/images/2017/08/22/15033822628630.jpg","shop_type":"1","is_supply":"0","cat_id":"0","credit":"67","desc_score":"4.98","service_score":"5.00","send_score":"5.00","logistics_score":"5.00","region_code":"13,03,02,0001","address":"白塔岭街道河北大街西段362号河北科技师范学院","shop_lng":"0","shop_lat":"0","opening_hour":{"week":["0","1","2","3","4"],"begin_hour":["8"],"begin_minute":["30"],"end_hour":["18"],"end_minute":["30"]},"add_time":"1491969723","pass_time":"1491969724","duration":"3","unit":"2","clearing_cycle":"2","open_time":"1507970440","end_time":"1539506440","system_fee":"500.00","insure_fee":"300.00","goods_status":"0","shop_status":"1","close_info":"","shop_sort":"4","shop_audit":"1","fail_info":null,"simply_introduce":"231","shop_keywords":"广缘、兴隆","shop_description":"广缘超市，您值得信赖！","detail_introduce":"本店郑重承若，本店商品质量绝对有保证！","service_tel":"13333333333","service_hours":"","shop_sign_m":"/shop/6/images/2017/08/22/15033823894591.jpg","take_rate":"1.00","qrcode_take_rate":"5.00","collect_allow_number":"200","collected_number":"108","store_allow_number":"3","store_number":"3","comment_allow_number":"100","comment_number":"5","login_status":"1","show_credit":"1","show_in_street":"1","goods_is_show":"1","control_price":"1","show_price":"1","show_content":"店铺价格显示内容","button_content":"购买按钮显示内容","button_url":null,"start_price":null,"wx_barcode":"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGR8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1BUbnZmb0hscnZyWG4tRHFlUlZEAAIEgv/CVQMEAAAAAA=="},"user":{"user_id":"143","user_name":"zhaoyunlong","mobile":null,"email":null,"nickname":"傻狍子","headimg":"/user/143/images/2017/09/09/15049288572170.jpeg"},"real":{"real_name":"赵云龙","card_no":"230623198800000000","hand_card":"/backend/1/images/2017/04/17/14923980838561.jpg","card_side_a":"/backend/1/images/2017/04/17/14923980887692.jpg","card_side_b":"/backend/1/images/2017/04/17/14923980931022.jpg","address":"商之翼","special_aptitude":"/backend/1/images/2017/08/23/15034775235804.png|/backend/1/images/2017/08/23/15034775271403.png|/backend/1/images/2017/08/23/15034775316946.png"},"credit":{"credit_id":"3","credit_name":"三星","credit_img":"/system/credit/2017/04/12/14919691179161.gif","min_point":"41","max_point":"90","remark":""},"address":[{"address_id":"16","consignee":"赵云龙","region_code":"13,03,02","address_detail":"商之翼","mobile":"13333344125","tel":"","email":"","is_default":"1","shop_id":"6"}],"customer_main":{"customer_name":"懒懒","customer_account":"zl8810251025","customer_tool":"2","shop_type":"1","shop_id":"6"},"aliim_enable":"0"},"region_name":"河北省,秦皇岛市,海港区,白塔岭街道","duration_time":"2天","is_collect":0,"collect_count":"27","goods_count":170,"bonus_count":"2","article":[{"article_id":"40","title":"43343","cat_id":"31","content":"4","keywords":"","jurisdiction":null,"article_thumb":"","add_time":"1498544275","is_comment":"1","click_number":"0","is_show":"0","user_id":"0","status":"0","link":"","source":"","summary":"","goods_ids":"","shop_id":"6","extend_cat":null,"sort":"2","is_recommend":"0"},{"article_id":"41","title":"3434","cat_id":"38","content":"<p>\r\n\t&gt; 软件编程 &gt; IOS开发 &gt; 听说你想找一个可以自定义的相机demo，最好还可以自定义裁剪2016-10-25 19:56&emsp;出处：未知&emsp;人气：136&emsp;评论（0）\r\n<\/p>\r\n<p>\r\n\t开发iOS<strong>应用的过程中，很多情景都要调用相机，大多数初学开发者都是采用的苹果提供的系统相机的方法。<\/strong>\r\n<\/p>\r\n<p>\r\n\t<strong>UIImagePickerController *imagePic<\/strong>kerController = &nbsp;[[UIImagePickerController alloc] init];\r\n<\/p>\r\n<p>\r\n\t&nbsp; imagePickerController.delegate = self;\r\n<\/p>\r\n<p>\r\n\t&nbsp; imagePickerController.allowsEditing = YES;\r\n<\/p>\r\n<p>\r\n\t&nbsp; imagePickerController.sourceType = sourceType;\r\n<\/p>\r\n<p>\r\n\t&nbsp; [self presentViewController:imagePickerController animated:YES completion:^{}];\r\n<\/p>","keywords":"34","jurisdiction":null,"article_thumb":"","add_time":"1498544293","is_comment":"1","click_number":"0","is_show":"1","user_id":"0","status":"0","link":"","source":"","summary":"","goods_ids":"","shop_id":"6","extend_cat":null,"sort":"3","is_recommend":"0"}],"position":"bonus","list":[{"bonus_id":"164","shop_id":"6","bonus_type":"1","bonus_name":"测试店铺红包","bonus_desc":null,"bonus_image":null,"send_type":"0","bonus_amount":"11.00","receive_count":"1","bonus_number":"11","use_range":"0","bonus_data":{"goods_ids":null},"min_goods_amount":"2.00","is_original_price":"1","start_time":"1508083200","end_time":"1508774399","is_enable":"1","is_delete":"0","add_time":"1508123726","receive_number":1,"used_number":"1","shop_name":"广缘超市","start_time_format":"2017-10-16","end_time_format":"2017-10-23","bonus_type_name":"到店送红包","min_goods_amount_format":"￥2.00","bonus_status":0,"bonus_status_format":"正常","user_bonus_status":0,"search_url":"/search.html?shop_id=6","bonus_amount_format":"￥11.00","goods_ids":null},{"bonus_id":"162","shop_id":"6","bonus_type":"1","bonus_name":"测试红包","bonus_desc":null,"bonus_image":null,"send_type":"0","bonus_amount":"77.70","receive_count":"1","bonus_number":"5","use_range":"1","bonus_data":{"goods_ids":["670","849"]},"min_goods_amount":"9.00","is_original_price":"0","start_time":"1507651200","end_time":"1508342399","is_enable":"1","is_delete":"0","add_time":"1507690952","receive_number":4,"used_number":"0","shop_name":"广缘超市","start_time_format":"2017-10-11","end_time_format":"2017-10-18","bonus_type_name":"到店送红包","min_goods_amount_format":"￥9.00","bonus_status":0,"bonus_status_format":"正常","user_bonus_status":0,"search_url":"/search.html?shop_id=6&goods_ids=670,849","bonus_amount_format":"￥77.70","goods_ids":["670","849"]}],"page":{"page_key":"page","page_id":"pagination","default_page_size":10,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":2,"page_count":1,"offset":0,"url":null,"sql":null},"shop_header_style":"0","context":{"current_time":1508229065,"user_info":null,"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"11,01,09","mall_region_name":{"11":"北京市","11,01":"北京市","11,01,09":"门头沟区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"111111111","mall_wangwang":"111111111111","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.png","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"},"site_id":0}}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * shop_id : 6
         * shop_info : {"shop":{"shop_id":"6","user_id":"143","site_id":"1","shop_name":"广缘超市","shop_image":"/shop/6/images/2017/08/22/15033822189747.jpg","shop_logo":"/shop/6/images/2017/08/22/15033822316799.jpg","shop_poster":"/shop/6/images/2017/08/22/15033822447937.jpg","shop_sign":"/shop/6/images/2017/08/22/15033822628630.jpg","shop_type":"1","is_supply":"0","cat_id":"0","credit":"67","desc_score":"4.98","service_score":"5.00","send_score":"5.00","logistics_score":"5.00","region_code":"13,03,02,0001","address":"白塔岭街道河北大街西段362号河北科技师范学院","shop_lng":"0","shop_lat":"0","opening_hour":{"week":["0","1","2","3","4"],"begin_hour":["8"],"begin_minute":["30"],"end_hour":["18"],"end_minute":["30"]},"add_time":"1491969723","pass_time":"1491969724","duration":"3","unit":"2","clearing_cycle":"2","open_time":"1507970440","end_time":"1539506440","system_fee":"500.00","insure_fee":"300.00","goods_status":"0","shop_status":"1","close_info":"","shop_sort":"4","shop_audit":"1","fail_info":null,"simply_introduce":"231","shop_keywords":"广缘、兴隆","shop_description":"广缘超市，您值得信赖！","detail_introduce":"本店郑重承若，本店商品质量绝对有保证！","service_tel":"13333333333","service_hours":"","shop_sign_m":"/shop/6/images/2017/08/22/15033823894591.jpg","take_rate":"1.00","qrcode_take_rate":"5.00","collect_allow_number":"200","collected_number":"108","store_allow_number":"3","store_number":"3","comment_allow_number":"100","comment_number":"5","login_status":"1","show_credit":"1","show_in_street":"1","goods_is_show":"1","control_price":"1","show_price":"1","show_content":"店铺价格显示内容","button_content":"购买按钮显示内容","button_url":null,"start_price":null,"wx_barcode":"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGR8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1BUbnZmb0hscnZyWG4tRHFlUlZEAAIEgv/CVQMEAAAAAA=="},"user":{"user_id":"143","user_name":"zhaoyunlong","mobile":null,"email":null,"nickname":"傻狍子","headimg":"/user/143/images/2017/09/09/15049288572170.jpeg"},"real":{"real_name":"赵云龙","card_no":"230623198800000000","hand_card":"/backend/1/images/2017/04/17/14923980838561.jpg","card_side_a":"/backend/1/images/2017/04/17/14923980887692.jpg","card_side_b":"/backend/1/images/2017/04/17/14923980931022.jpg","address":"商之翼","special_aptitude":"/backend/1/images/2017/08/23/15034775235804.png|/backend/1/images/2017/08/23/15034775271403.png|/backend/1/images/2017/08/23/15034775316946.png"},"credit":{"credit_id":"3","credit_name":"三星","credit_img":"/system/credit/2017/04/12/14919691179161.gif","min_point":"41","max_point":"90","remark":""},"address":[{"address_id":"16","consignee":"赵云龙","region_code":"13,03,02","address_detail":"商之翼","mobile":"13333344125","tel":"","email":"","is_default":"1","shop_id":"6"}],"customer_main":{"customer_name":"懒懒","customer_account":"zl8810251025","customer_tool":"2","shop_type":"1","shop_id":"6"},"aliim_enable":"0"}
         * region_name : 河北省,秦皇岛市,海港区,白塔岭街道
         * duration_time : 2天
         * is_collect : 0
         * collect_count : 27
         * goods_count : 170
         * bonus_count : 2
         * article : [{"article_id":"40","title":"43343","cat_id":"31","content":"4","keywords":"","jurisdiction":null,"article_thumb":"","add_time":"1498544275","is_comment":"1","click_number":"0","is_show":"0","user_id":"0","status":"0","link":"","source":"","summary":"","goods_ids":"","shop_id":"6","extend_cat":null,"sort":"2","is_recommend":"0"},{"article_id":"41","title":"3434","cat_id":"38","content":"<p>\r\n\t&gt; 软件编程 &gt; IOS开发 &gt; 听说你想找一个可以自定义的相机demo，最好还可以自定义裁剪2016-10-25 19:56&emsp;出处：未知&emsp;人气：136&emsp;评论（0）\r\n<\/p>\r\n<p>\r\n\t开发iOS<strong>应用的过程中，很多情景都要调用相机，大多数初学开发者都是采用的苹果提供的系统相机的方法。<\/strong>\r\n<\/p>\r\n<p>\r\n\t<strong>UIImagePickerController *imagePic<\/strong>kerController = &nbsp;[[UIImagePickerController alloc] init];\r\n<\/p>\r\n<p>\r\n\t&nbsp; imagePickerController.delegate = self;\r\n<\/p>\r\n<p>\r\n\t&nbsp; imagePickerController.allowsEditing = YES;\r\n<\/p>\r\n<p>\r\n\t&nbsp; imagePickerController.sourceType = sourceType;\r\n<\/p>\r\n<p>\r\n\t&nbsp; [self presentViewController:imagePickerController animated:YES completion:^{}];\r\n<\/p>","keywords":"34","jurisdiction":null,"article_thumb":"","add_time":"1498544293","is_comment":"1","click_number":"0","is_show":"1","user_id":"0","status":"0","link":"","source":"","summary":"","goods_ids":"","shop_id":"6","extend_cat":null,"sort":"3","is_recommend":"0"}]
         * position : bonus
         * list : [{"bonus_id":"164","shop_id":"6","bonus_type":"1","bonus_name":"测试店铺红包","bonus_desc":null,"bonus_image":null,"send_type":"0","bonus_amount":"11.00","receive_count":"1","bonus_number":"11","use_range":"0","bonus_data":{"goods_ids":null},"min_goods_amount":"2.00","is_original_price":"1","start_time":"1508083200","end_time":"1508774399","is_enable":"1","is_delete":"0","add_time":"1508123726","receive_number":1,"used_number":"1","shop_name":"广缘超市","start_time_format":"2017-10-16","end_time_format":"2017-10-23","bonus_type_name":"到店送红包","min_goods_amount_format":"￥2.00","bonus_status":0,"bonus_status_format":"正常","user_bonus_status":0,"search_url":"/search.html?shop_id=6","bonus_amount_format":"￥11.00","goods_ids":null},{"bonus_id":"162","shop_id":"6","bonus_type":"1","bonus_name":"测试红包","bonus_desc":null,"bonus_image":null,"send_type":"0","bonus_amount":"77.70","receive_count":"1","bonus_number":"5","use_range":"1","bonus_data":{"goods_ids":["670","849"]},"min_goods_amount":"9.00","is_original_price":"0","start_time":"1507651200","end_time":"1508342399","is_enable":"1","is_delete":"0","add_time":"1507690952","receive_number":4,"used_number":"0","shop_name":"广缘超市","start_time_format":"2017-10-11","end_time_format":"2017-10-18","bonus_type_name":"到店送红包","min_goods_amount_format":"￥9.00","bonus_status":0,"bonus_status_format":"正常","user_bonus_status":0,"search_url":"/search.html?shop_id=6&goods_ids=670,849","bonus_amount_format":"￥77.70","goods_ids":["670","849"]}]
         * page : {"page_key":"page","page_id":"pagination","default_page_size":10,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":2,"page_count":1,"offset":0,"url":null,"sql":null}
         * shop_header_style : 0
         * context : {"current_time":1508229065,"user_info":null,"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"11,01,09","mall_region_name":{"11":"北京市","11,01":"北京市","11,01,09":"门头沟区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"111111111","mall_wangwang":"111111111111","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.png","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"},"site_id":0}
         */

        public String shop_id;
        public String shop_info;
        public String region_name;
        public String duration_time;
        public int is_collect;
        public String collect_count;
        public int goods_count;
        public String bonus_count;
        public String position;
        public PageBean page;
        public String shop_header_style;
        public ContextBean context;
        public List<ArticleBean> article;
        public List<ShopBonusModel> list;

        public static class PageBean {
            /**
             * page_key : page
             * page_id : pagination
             * default_page_size : 10
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
            public String url;
            public String sql;
            public List<Integer> page_size_list;
        }

        public static class ContextBean {
            /**
             * current_time : 1508229065
             * user_info : null
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.jpg","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"11,01,09","mall_region_name":{"11":"北京市","11,01":"北京市","11,01,09":"门头沟区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"111111111","mall_wangwang":"111111111111","favicon":"http://68test.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.png","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","aliim_enable":"1","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","goods_price_format":"￥{0}"}
             * site_id : 0
             */

            public int current_time;
            public String user_info;
            public String config;
            public int site_id;
        }

        public static class ArticleBean {
            /**
             * article_id : 40
             * title : 43343
             * cat_id : 31
             * content : 4
             * keywords :
             * jurisdiction : null
             * article_thumb :
             * add_time : 1498544275
             * is_comment : 1
             * click_number : 0
             * is_show : 0
             * user_id : 0
             * status : 0
             * link :
             * source :
             * summary :
             * goods_ids :
             * shop_id : 6
             * extend_cat : null
             * sort : 2
             * is_recommend : 0
             */

            public String article_id;
            public String title;
            public String cat_id;
            public String content;
            public String keywords;
            public String jurisdiction;
            public String article_thumb;
            public String add_time;
            public String is_comment;
            public String click_number;
            public String is_show;
            public String user_id;
            public String status;
            public String link;
            public String source;
            public String summary;
            public String goods_ids;
            public String shop_id;
            public String extend_cat;
            public String sort;
            public String is_recommend;
        }


    }
}
