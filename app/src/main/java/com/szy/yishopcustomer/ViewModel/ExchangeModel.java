package com.szy.yishopcustomer.ViewModel;

import com.alibaba.fastjson.annotation.JSONField;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;

import java.util.List;

/**
 * Created by Smart on 2017/7/6.
 */

public class ExchangeModel {

    /**
     * code : 0
     * data : {"page":{"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":3,"page_count":1,"offset":0,"url":null,"sql":null},"list":[{"order_id":"1412","order_sn":"20170706025858869680","parent_sn":null,"user_id":"64","order_status":"0","shop_id":"2","site_id":"1","store_id":"0","pickup_id":"0","shipping_status":"0","pay_status":"1","consignee":"13001163148","region_code":"13,03,02","address":"13001163148","address_lng":"119.592007","address_lat":"39.965726","receiving_mode":"0","tel":"13001163148","email":null,"postscript":"","best_time":"工作日/周末/假日均可 ","pay_id":"99","pay_code":"exchange","pay_name":"积分兑换","pay_sn":"0","is_cod":"0","order_amount":"0.00","order_points":"46","money_paid":"0.00","goods_amount":"46.00","inv_fee":"0.00","shipping_fee":"0.00","cash_more":"0.00","surplus":"0.00","user_surplus":"0.00","user_surplus_limit":"0.00","bonus_id":"0","shop_bonus_id":"0","bonus":"0.00","shop_bonus":"0.00","integral":"0","integral_money":"0.00","give_integral":"0","order_from":"2","add_time":"1499309938","pay_time":"0","shipping_time":"0","confirm_time":"0","delay_days":"0","order_type":"0","service_mark":"0","send_mark":"0","shipping_mark":"0","buyer_type":"0","evaluate_status":"0","evaluate_time":"0","end_time":"0","is_distrib":"0","distrib_status":"0","is_show":"1,2,3,4","is_delete":"0","order_data":null,"mall_remark":null,"shop_remark":null,"store_remark":null,"close_reason":null,"cash_user_id":"0","last_time":null,"pickup_name":null,"shop_name":"美廉美超市","shop_type":"1","customer_tool":null,"customer_account":null,"complaint_id":null,"complaint_status":null,"order_status_format":"买家已付款","order_from_format":"WAP端","comment_type":3,"complainted":-1,"buttons":["cancel_order"],"goods_list":[{"record_id":"1651","order_id":"1412","goods_id":"259","sku_id":"788","spec_info":"","goods_name":"茶花可弯饮料吸管-测试","goods_sn":"19100","sku_sn":"19100","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/44588660941/TB183_QOXXXXXbbXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"23","distrib_price":"0.00","goods_number":"2","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":"11.00","back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}],"rowspan":1,"rowspan_all":1,"aliim_enable":"0","goods_num":2,"has_backing_goods":false},{"order_id":"1410","order_sn":"20170706025808179890","parent_sn":null,"user_id":"64","order_status":"0","shop_id":"2","site_id":"1","store_id":"0","pickup_id":"0","shipping_status":"0","pay_status":"1","consignee":"13001163148","region_code":"13,03,02","address":"13001163148","address_lng":"119.592007","address_lat":"39.965726","receiving_mode":"0","tel":"13001163148","email":null,"postscript":"","best_time":"仅周末送货 仅周末送货","pay_id":"99","pay_code":"exchange","pay_name":"积分兑换","pay_sn":"0","is_cod":"0","order_amount":"0.00","order_points":"1","money_paid":"0.00","goods_amount":"1.00","inv_fee":"0.00","shipping_fee":"0.00","cash_more":"0.00","surplus":"0.00","user_surplus":"0.00","user_surplus_limit":"0.00","bonus_id":"0","shop_bonus_id":"0","bonus":"0.00","shop_bonus":"0.00","integral":"0","integral_money":"0.00","give_integral":"0","order_from":"2","add_time":"1499309888","pay_time":"0","shipping_time":"0","confirm_time":"0","delay_days":"0","order_type":"0","service_mark":"0","send_mark":"0","shipping_mark":"0","buyer_type":"0","evaluate_status":"0","evaluate_time":"0","end_time":"0","is_distrib":"0","distrib_status":"0","is_show":"1,2,3,4","is_delete":"0","order_data":null,"mall_remark":null,"shop_remark":null,"store_remark":null,"close_reason":null,"cash_user_id":"0","last_time":null,"pickup_name":null,"shop_name":"美廉美超市","shop_type":"1","customer_tool":null,"customer_account":null,"complaint_id":null,"complaint_status":null,"order_status_format":"买家已付款","order_from_format":"WAP端","comment_type":3,"complainted":-1,"buttons":["cancel_order"],"goods_list":[{"record_id":"1650","order_id":"1410","goods_id":"258","sku_id":"787","spec_info":"","goods_name":" 澳洲原瓶进口 黄尾袋鼠 西拉干红葡萄酒 750ml/瓶","goods_sn":"","sku_sn":"","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/26218392020/TB1t5olNVXXXXabXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"1","distrib_price":"0.00","goods_number":"1","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":null,"back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}],"rowspan":1,"rowspan_all":1,"aliim_enable":"0","goods_num":1,"has_backing_goods":false},{"order_id":"1409","order_sn":"20170706024937721480","parent_sn":null,"user_id":"64","order_status":"0","shop_id":"2","site_id":"1","store_id":"0","pickup_id":"0","shipping_status":"0","pay_status":"1","consignee":"13001163148","region_code":"13,03,02","address":"13001163148","address_lng":"119.592007","address_lat":"39.965726","receiving_mode":"0","tel":"13001163148","email":null,"postscript":"","best_time":"工作日/周末/假日均可 ","pay_id":"99","pay_code":"exchange","pay_name":"积分兑换","pay_sn":"0","is_cod":"0","order_amount":"0.00","order_points":"1","money_paid":"0.00","goods_amount":"1.00","inv_fee":"0.00","shipping_fee":"0.00","cash_more":"0.00","surplus":"0.00","user_surplus":"0.00","user_surplus_limit":"0.00","bonus_id":"0","shop_bonus_id":"0","bonus":"0.00","shop_bonus":"0.00","integral":"0","integral_money":"0.00","give_integral":"0","order_from":"2","add_time":"1499309377","pay_time":"0","shipping_time":"0","confirm_time":"0","delay_days":"0","order_type":"0","service_mark":"0","send_mark":"0","shipping_mark":"0","buyer_type":"0","evaluate_status":"0","evaluate_time":"0","end_time":"0","is_distrib":"0","distrib_status":"0","is_show":"1,2,3,4","is_delete":"0","order_data":null,"mall_remark":null,"shop_remark":null,"store_remark":null,"close_reason":null,"cash_user_id":"0","last_time":null,"pickup_name":null,"shop_name":"美廉美超市","shop_type":"1","customer_tool":null,"customer_account":null,"complaint_id":null,"complaint_status":null,"order_status_format":"买家已付款","order_from_format":"WAP端","comment_type":3,"complainted":-1,"buttons":["cancel_order"],"goods_list":[{"record_id":"1649","order_id":"1409","goods_id":"258","sku_id":"787","spec_info":"","goods_name":" 澳洲原瓶进口 黄尾袋鼠 西拉干红葡萄酒 750ml/瓶","goods_sn":"","sku_sn":"","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/26218392020/TB1t5olNVXXXXabXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"1","distrib_price":"0.00","goods_number":"1","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":null,"back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}],"rowspan":1,"rowspan_all":1,"aliim_enable":"0","goods_num":1,"has_backing_goods":false}],"pay_term":"5","order_status_list":{"":"全部","unshipped":"等待卖家发货","shipped":"卖家已发货","finished":"交易成功","closed":"交易关闭"},"evaluate_status_list":{"":"全部","unevaluate":"待评价","evaluate":"已评价"},"order_time_list":{"":"全部","ThreeMonth":"近三个月订单","ThisYear":"今年内订单","OneYear":"2016年订单","TwoYear":"2015年订单","ThreeYear":"2014年订单","OldYear":"2014年以前订单"},"pickup_list":["全部","普通快递","上门自提"],"order_counts":{"all":"0","unpayed":"0","unshipped":"0","shipped":"0","backing":"0","unevaluate":"0","finished":"0","closed":"0"},"nav_default":"exchange","is_exchange":"1","context":{"current_time":1499320601,"user_info":{"user_id":64,"user_name":"SZY130OOVM3148","nickname":"SZY130OOVM3148","headimg":"http://wx.qlogo.cn/mmopen/XCopLcwfzefY8FIgFDQ4EbbSqW3YwJadRecSSKCA6nKibtPGwHic6bkR4UiaxRZ5v1QOgZ8nT2HQeY2FAjpKrxdaw/0","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1499320589,"last_ip":"100.109.222.56","last_region_code":"13,03,02","user_rank":{"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":1}}}
     * message :
     * limit_urls : ["goods/publish/edit-goods-column","goods/publish/batch-edit-sku-by-sku-id","goods/publish/onsale","goods/publish/offsale","trade/order/edit","trade/order/assign","trade/delivery/quick-delivery","trade/delivery/send-logistics","finance/bill/shop-bill","shop-share","site/upload-video","enable-sousou"]
     */

    public int code;
    public DataBean data;
    public String message;
    public List<String> limit_urls;

    public static class DataBean {
        /**
         * page : {"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":3,"page_count":1,"offset":0,"url":null,"sql":null}
         * list : [{"order_id":"1412","order_sn":"20170706025858869680","parent_sn":null,"user_id":"64","order_status":"0","shop_id":"2","site_id":"1","store_id":"0","pickup_id":"0","shipping_status":"0","pay_status":"1","consignee":"13001163148","region_code":"13,03,02","address":"13001163148","address_lng":"119.592007","address_lat":"39.965726","receiving_mode":"0","tel":"13001163148","email":null,"postscript":"","best_time":"工作日/周末/假日均可 ","pay_id":"99","pay_code":"exchange","pay_name":"积分兑换","pay_sn":"0","is_cod":"0","order_amount":"0.00","order_points":"46","money_paid":"0.00","goods_amount":"46.00","inv_fee":"0.00","shipping_fee":"0.00","cash_more":"0.00","surplus":"0.00","user_surplus":"0.00","user_surplus_limit":"0.00","bonus_id":"0","shop_bonus_id":"0","bonus":"0.00","shop_bonus":"0.00","integral":"0","integral_money":"0.00","give_integral":"0","order_from":"2","add_time":"1499309938","pay_time":"0","shipping_time":"0","confirm_time":"0","delay_days":"0","order_type":"0","service_mark":"0","send_mark":"0","shipping_mark":"0","buyer_type":"0","evaluate_status":"0","evaluate_time":"0","end_time":"0","is_distrib":"0","distrib_status":"0","is_show":"1,2,3,4","is_delete":"0","order_data":null,"mall_remark":null,"shop_remark":null,"store_remark":null,"close_reason":null,"cash_user_id":"0","last_time":null,"pickup_name":null,"shop_name":"美廉美超市","shop_type":"1","customer_tool":null,"customer_account":null,"complaint_id":null,"complaint_status":null,"order_status_format":"买家已付款","order_from_format":"WAP端","comment_type":3,"complainted":-1,"buttons":["cancel_order"],"goods_list":[{"record_id":"1651","order_id":"1412","goods_id":"259","sku_id":"788","spec_info":"","goods_name":"茶花可弯饮料吸管-测试","goods_sn":"19100","sku_sn":"19100","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/44588660941/TB183_QOXXXXXbbXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"23","distrib_price":"0.00","goods_number":"2","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":"11.00","back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}],"rowspan":1,"rowspan_all":1,"aliim_enable":"0","goods_num":2,"has_backing_goods":false},{"order_id":"1410","order_sn":"20170706025808179890","parent_sn":null,"user_id":"64","order_status":"0","shop_id":"2","site_id":"1","store_id":"0","pickup_id":"0","shipping_status":"0","pay_status":"1","consignee":"13001163148","region_code":"13,03,02","address":"13001163148","address_lng":"119.592007","address_lat":"39.965726","receiving_mode":"0","tel":"13001163148","email":null,"postscript":"","best_time":"仅周末送货 仅周末送货","pay_id":"99","pay_code":"exchange","pay_name":"积分兑换","pay_sn":"0","is_cod":"0","order_amount":"0.00","order_points":"1","money_paid":"0.00","goods_amount":"1.00","inv_fee":"0.00","shipping_fee":"0.00","cash_more":"0.00","surplus":"0.00","user_surplus":"0.00","user_surplus_limit":"0.00","bonus_id":"0","shop_bonus_id":"0","bonus":"0.00","shop_bonus":"0.00","integral":"0","integral_money":"0.00","give_integral":"0","order_from":"2","add_time":"1499309888","pay_time":"0","shipping_time":"0","confirm_time":"0","delay_days":"0","order_type":"0","service_mark":"0","send_mark":"0","shipping_mark":"0","buyer_type":"0","evaluate_status":"0","evaluate_time":"0","end_time":"0","is_distrib":"0","distrib_status":"0","is_show":"1,2,3,4","is_delete":"0","order_data":null,"mall_remark":null,"shop_remark":null,"store_remark":null,"close_reason":null,"cash_user_id":"0","last_time":null,"pickup_name":null,"shop_name":"美廉美超市","shop_type":"1","customer_tool":null,"customer_account":null,"complaint_id":null,"complaint_status":null,"order_status_format":"买家已付款","order_from_format":"WAP端","comment_type":3,"complainted":-1,"buttons":["cancel_order"],"goods_list":[{"record_id":"1650","order_id":"1410","goods_id":"258","sku_id":"787","spec_info":"","goods_name":" 澳洲原瓶进口 黄尾袋鼠 西拉干红葡萄酒 750ml/瓶","goods_sn":"","sku_sn":"","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/26218392020/TB1t5olNVXXXXabXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"1","distrib_price":"0.00","goods_number":"1","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":null,"back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}],"rowspan":1,"rowspan_all":1,"aliim_enable":"0","goods_num":1,"has_backing_goods":false},{"order_id":"1409","order_sn":"20170706024937721480","parent_sn":null,"user_id":"64","order_status":"0","shop_id":"2","site_id":"1","store_id":"0","pickup_id":"0","shipping_status":"0","pay_status":"1","consignee":"13001163148","region_code":"13,03,02","address":"13001163148","address_lng":"119.592007","address_lat":"39.965726","receiving_mode":"0","tel":"13001163148","email":null,"postscript":"","best_time":"工作日/周末/假日均可 ","pay_id":"99","pay_code":"exchange","pay_name":"积分兑换","pay_sn":"0","is_cod":"0","order_amount":"0.00","order_points":"1","money_paid":"0.00","goods_amount":"1.00","inv_fee":"0.00","shipping_fee":"0.00","cash_more":"0.00","surplus":"0.00","user_surplus":"0.00","user_surplus_limit":"0.00","bonus_id":"0","shop_bonus_id":"0","bonus":"0.00","shop_bonus":"0.00","integral":"0","integral_money":"0.00","give_integral":"0","order_from":"2","add_time":"1499309377","pay_time":"0","shipping_time":"0","confirm_time":"0","delay_days":"0","order_type":"0","service_mark":"0","send_mark":"0","shipping_mark":"0","buyer_type":"0","evaluate_status":"0","evaluate_time":"0","end_time":"0","is_distrib":"0","distrib_status":"0","is_show":"1,2,3,4","is_delete":"0","order_data":null,"mall_remark":null,"shop_remark":null,"store_remark":null,"close_reason":null,"cash_user_id":"0","last_time":null,"pickup_name":null,"shop_name":"美廉美超市","shop_type":"1","customer_tool":null,"customer_account":null,"complaint_id":null,"complaint_status":null,"order_status_format":"买家已付款","order_from_format":"WAP端","comment_type":3,"complainted":-1,"buttons":["cancel_order"],"goods_list":[{"record_id":"1649","order_id":"1409","goods_id":"258","sku_id":"787","spec_info":"","goods_name":" 澳洲原瓶进口 黄尾袋鼠 西拉干红葡萄酒 750ml/瓶","goods_sn":"","sku_sn":"","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/26218392020/TB1t5olNVXXXXabXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"1","distrib_price":"0.00","goods_number":"1","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":null,"back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}],"rowspan":1,"rowspan_all":1,"aliim_enable":"0","goods_num":1,"has_backing_goods":false}]
         * pay_term : 5
         * order_status_list : {"":"全部","unshipped":"等待卖家发货","shipped":"卖家已发货","finished":"交易成功","closed":"交易关闭"}
         * evaluate_status_list : {"":"全部","unevaluate":"待评价","evaluate":"已评价"}
         * order_time_list : {"":"全部","ThreeMonth":"近三个月订单","ThisYear":"今年内订单","OneYear":"2016年订单","TwoYear":"2015年订单","ThreeYear":"2014年订单","OldYear":"2014年以前订单"}
         * pickup_list : ["全部","普通快递","上门自提"]
         * order_counts : {"all":"0","unpayed":"0","unshipped":"0","shipped":"0","backing":"0","unevaluate":"0","finished":"0","closed":"0"}
         * nav_default : exchange
         * is_exchange : 1
         * context : {"current_time":1499320601,"user_info":{"user_id":64,"user_name":"SZY130OOVM3148","nickname":"SZY130OOVM3148","headimg":"http://wx.qlogo.cn/mmopen/XCopLcwfzefY8FIgFDQ4EbbSqW3YwJadRecSSKCA6nKibtPGwHic6bkR4UiaxRZ5v1QOgZ8nT2HQeY2FAjpKrxdaw/0","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1499320589,"last_ip":"100.109.222.56","last_region_code":"13,03,02","user_rank":{"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"},"cart":{"goods_count":1}}
         */

        public PageBean page;
        public String pay_term;
        public OrderStatusListBean order_status_list;
        public EvaluateStatusListBean evaluate_status_list;
        public OrderTimeListBean order_time_list;
        public OrderCountsBean order_counts;
        public String nav_default;
        public String is_exchange;
        public ContextBean context;
        public List<ListBean> list;
        public List<String> pickup_list;

        public static class PageBean {
            /**
             * page_key : page
             * page_id : pagination
             * default_page_size : 15
             * cur_page : 1
             * page_size : 10
             * page_size_list : [10,50,500,1000]
             * record_count : 3
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

        public static class OrderStatusListBean {
            /**
             *  : 全部
             * unshipped : 等待卖家发货
             * shipped : 卖家已发货
             * finished : 交易成功
             * closed : 交易关闭
             */

            @JSONField(name = "")
            public String _$260; // FIXME check this code
            public String unshipped;
            public String shipped;
            public String finished;
            public String closed;
        }

        public static class EvaluateStatusListBean {
            /**
             *  : 全部
             * unevaluate : 待评价
             * evaluate : 已评价
             */

            @JSONField(name = "")
            public String _$82; // FIXME check this code
            public String unevaluate;
            public String evaluate;
        }

        public static class OrderTimeListBean {
            /**
             *  : 全部
             * ThreeMonth : 近三个月订单
             * ThisYear : 今年内订单
             * OneYear : 2016年订单
             * TwoYear : 2015年订单
             * ThreeYear : 2014年订单
             * OldYear : 2014年以前订单
             */

            @JSONField(name = "")
            public String _$249; // FIXME check this code
            public String ThreeMonth;
            public String ThisYear;
            public String OneYear;
            public String TwoYear;
            public String ThreeYear;
            public String OldYear;
        }

        public static class OrderCountsBean {
            /**
             * all : 0
             * unpayed : 0
             * unshipped : 0
             * shipped : 0
             * backing : 0
             * unevaluate : 0
             * finished : 0
             * closed : 0
             */

            public String all;
            public String unpayed;
            public String unshipped;
            public String shipped;
            public String backing;
            public String unevaluate;
            public String finished;
            public String closed;
        }

        public static class ContextBean {
            /**
             * current_time : 1499320601
             * user_info : {"user_id":64,"user_name":"SZY130OOVM3148","nickname":"SZY130OOVM3148","headimg":"http://wx.qlogo.cn/mmopen/XCopLcwfzefY8FIgFDQ4EbbSqW3YwJadRecSSKCA6nKibtPGwHic6bkR4UiaxRZ5v1QOgZ8nT2HQeY2FAjpKrxdaw/0","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1499320589,"last_ip":"100.109.222.56","last_region_code":"13,03,02","user_rank":{"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}}
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"4","mall_wangwang":"","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e"}
             * cart : {"goods_count":1}
             */

            public int current_time;
            public UserInfoBean user_info;
            public ConfigBean config;
            public CartBean cart;

            public static class UserInfoBean {
                /**
                 * user_id : 64
                 * user_name : SZY130OOVM3148
                 * nickname : SZY130OOVM3148
                 * headimg : http://wx.qlogo.cn/mmopen/XCopLcwfzefY8FIgFDQ4EbbSqW3YwJadRecSSKCA6nKibtPGwHic6bkR4UiaxRZ5v1QOgZ8nT2HQeY2FAjpKrxdaw/0
                 * email : null
                 * email_validated : 0
                 * mobile : 13001163148
                 * mobile_validated : 1
                 * is_seller : 0
                 * shop_id : 0
                 * last_time : 1499320589
                 * last_ip : 100.109.222.56
                 * last_region_code : 13,03,02
                 * user_rank : {"rank_id":1,"rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":0,"max_points":1,"type":0,"is_special":0}
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
                public String last_region_code;
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
                    public String _$_1303227; // FIXME check this code
                    @JSONField(name = "13,03,02")
                    public String _$_13030272; // FIXME check this code
                }
            }

            public static class CartBean {
                /**
                 * goods_count : 1
                 */

                public int goods_count;
            }
        }

        public static class ListBean {
            /**
             * order_id : 1412
             * order_sn : 20170706025858869680
             * parent_sn : null
             * user_id : 64
             * order_status : 0
             * shop_id : 2
             * site_id : 1
             * store_id : 0
             * pickup_id : 0
             * shipping_status : 0
             * pay_status : 1
             * consignee : 13001163148
             * region_code : 13,03,02
             * address : 13001163148
             * address_lng : 119.592007
             * address_lat : 39.965726
             * receiving_mode : 0
             * tel : 13001163148
             * email : null
             * postscript :
             * best_time : 工作日/周末/假日均可
             * pay_id : 99
             * pay_code : exchange
             * pay_name : 积分兑换
             * pay_sn : 0
             * is_cod : 0
             * order_amount : 0.00
             * order_points : 46
             * money_paid : 0.00
             * goods_amount : 46.00
             * inv_fee : 0.00
             * shipping_fee : 0.00
             * cash_more : 0.00
             * surplus : 0.00
             * user_surplus : 0.00
             * user_surplus_limit : 0.00
             * bonus_id : 0
             * shop_bonus_id : 0
             * bonus : 0.00
             * shop_bonus : 0.00
             * integral : 0
             * integral_money : 0.00
             * give_integral : 0
             * order_from : 2
             * add_time : 1499309938
             * pay_time : 0
             * shipping_time : 0
             * confirm_time : 0
             * delay_days : 0
             * order_type : 0
             * service_mark : 0
             * send_mark : 0
             * shipping_mark : 0
             * buyer_type : 0
             * evaluate_status : 0
             * evaluate_time : 0
             * end_time : 0
             * is_distrib : 0
             * distrib_status : 0
             * is_show : 1,2,3,4
             * is_delete : 0
             * order_data : null
             * mall_remark : null
             * shop_remark : null
             * store_remark : null
             * close_reason : null
             * cash_user_id : 0
             * last_time : null
             * pickup_name : null
             * shop_name : 美廉美超市
             * shop_type : 1
             * customer_tool : null
             * customer_account : null
             * complaint_id : null
             * complaint_status : null
             * order_status_format : 买家已付款
             * order_from_format : WAP端
             * comment_type : 3
             * complainted : -1
             * buttons : ["cancel_order"]
             * goods_list : [{"record_id":"1651","order_id":"1412","goods_id":"259","sku_id":"788","spec_info":"","goods_name":"茶花可弯饮料吸管-测试","goods_sn":"19100","sku_sn":"19100","goods_image":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/44588660941/TB183_QOXXXXXbbXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220","goods_price":"0.00","goods_points":"23","distrib_price":"0.00","goods_number":"2","other_price":"0.00","parent_id":"0","is_gift":"0","is_evaluate":"0","goods_status":"0","give_integral":"0","stock_mode":"0","stock_dropped":"1","act_type":"0","goods_type":"0","is_distrib":"0","discount":"0.00","profits":"0.00","distrib_money":"0.00","shop_id":"2","contract_ids":null,"market_price":"11.00","back_id":null,"back_status":null,"back_number":null,"saleservice":null,"goods_back_format":"","gifts_list":null}]
             * rowspan : 1
             * rowspan_all : 1
             * aliim_enable : 0
             * goods_num : 2
             * has_backing_goods : false
             */

            public String order_id;
            public String order_sn;
            public Object parent_sn;
            public String user_id;
            public int order_status;
            public int order_cancel;
            public String shop_id;
            public String site_id;
            public String store_id;
            public String pickup_id;
            public int shipping_status;
            public String pay_status;
            public String consignee;
            public String region_code;
            public String address;
            public String address_lng;
            public String address_lat;
            public String receiving_mode;
            public String tel;
            public Object email;
            public String postscript;
            public String best_time;
            public String pay_id;
            public String pay_code;
            public String pay_name;
            public String pay_sn;
            public String is_cod;
            public String order_amount;
            public String order_points;
            public String money_paid;
            public String goods_amount;
            public String inv_fee;
            public String shipping_fee;
            public String cash_more;
            public String surplus;
            public String user_surplus;
            public String user_surplus_limit;
            public String bonus_id;
            public String shop_bonus_id;
            public String bonus;
            public String shop_bonus;
            public String integral;
            public String integral_money;
            public String give_integral;
            public String order_from;
            public String add_time;
            public String pay_time;
            public String shipping_time;
            public String confirm_time;
            public String delay_days;
            public String order_type;
            public String service_mark;
            public String send_mark;
            public String shipping_mark;
            public String buyer_type;
            public String evaluate_status;
            public String evaluate_time;
            public String end_time;
            public String is_distrib;
            public String distrib_status;
            public String is_show;
            public String is_delete;
            public Object order_data;
            public Object mall_remark;
            public Object shop_remark;
            public Object store_remark;
            public Object close_reason;
            public String cash_user_id;
            public Object last_time;
            public Object pickup_name;
            public String shop_name;
            public String shop_type;
            public Object customer_tool;
            public Object customer_account;
            public Object complaint_id;
            public Object complaint_status;
            public String order_status_format;
            public String order_from_format;
            public int comment_type;
            public int complainted;
            public int rowspan;
            public int rowspan_all;
            public String aliim_enable;
            public int goods_num;
            public boolean has_backing_goods;
            public List<String> buttons;
            public List<GoodsListBean> goods_list;
            public String cancelTip = "";

            public PickUpModel pickup;

            public static class GoodsListBean {
                /**
                 * record_id : 1651
                 * order_id : 1412
                 * goods_id : 259
                 * sku_id : 788
                 * spec_info :
                 * goods_name : 茶花可弯饮料吸管-测试
                 * goods_sn : 19100
                 * sku_sn : 19100
                 * goods_image : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/taobao-yun-images/44588660941/TB183_QOXXXXXbbXpXXXXXXXXXX_!!0-item_pic.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220
                 * goods_price : 0.00
                 * goods_points : 23
                 * distrib_price : 0.00
                 * goods_number : 2
                 * other_price : 0.00
                 * parent_id : 0
                 * is_gift : 0
                 * is_evaluate : 0
                 * goods_status : 0
                 * give_integral : 0
                 * stock_mode : 0
                 * stock_dropped : 1
                 * act_type : 0
                 * goods_type : 0
                 * is_distrib : 0
                 * discount : 0.00
                 * profits : 0.00
                 * distrib_money : 0.00
                 * shop_id : 2
                 * contract_ids : null
                 * market_price : 11.00
                 * back_id : null
                 * back_status : null
                 * back_number : null
                 * saleservice : null
                 * goods_back_format :
                 * gifts_list : null
                 */

                public String record_id;
                public String order_id;
                public String goods_id;
                public String sku_id;
                public String spec_info;
                public String goods_name;
                public String goods_sn;
                public String sku_sn;
                public String goods_image;
                public String goods_price;
                public String goods_points;
                public String distrib_price;
                public String goods_number;
                public String other_price;
                public String parent_id;
                public String is_gift;
                public String is_evaluate;
                public String goods_status;
                public String give_integral;
                public String stock_mode;
                public String stock_dropped;
                public String act_type;
                public int goods_type;
                public String is_distrib;
                public String discount;
                public String profits;
                public String distrib_money;
                public String shop_id;
                public Object contract_ids;
                public String market_price;
                public Object back_id;
                public Object back_status;
                public Object back_number;
                public Object saleservice;
                public String goods_back_format;
                public Object gifts_list;
            }
        }
    }
}
