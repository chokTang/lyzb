package com.szy.yishopcustomer.ResponseModel.OrderDetailModel;

import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;

import java.util.List;

/**
 * Created by lw on 2016/7/22.
 */
public class OrderInfoModel {
    public String order_id;//"1573",
    public String order_sn;//"20161222449230",
    public String parent_sn;//null,
    public String user_id;//"4",
    public int order_status;//"0",
    public String shop_id;//"2",
    public String site_id;//"1",
    public String store_id;//"0",
    public int shipping_status;//"1",
    public int pay_status;//"1",
    public String consignee;//"Taki",
    public String region_code;//"12,01,01,0001",
    public String address;//"122222",
    public String receiving_mode;//"0",
    public String tel;//"13333333333",
    public String email;//null,
    public String postscript;//"",
    public String best_time;//"工作日/周末/假日均可 ",
    public int pay_id;//"0",
    public String pay_code;//"0",
    public String pay_name;//"余额支付",
    public String pay_sn;//"0",
    public int is_cod;//"0",
    public String order_amount;//"10.01",
    public String money_paid;//"0.00",
    public String goods_amount;//"0.01",
    public String inv_fee;//"0.00",
    public String shipping_fee;//"10.00",
    public String cash_more;//"0.00",
    public String cash_more_format;//"0.00",
    public String surplus;//"10.01",
    public String surplus_format;//"10.01",
    public String order_amount_format;
    public String bonus_id;//"0",
    public String shop_bonus_id;//"0",
    public float bonus;//"0.00",
    public float shop_bonus;//"0.00",
    public String all_bonus_format;//"0.00",
    public String integral;//"0",
    public String integral_money;//"0.00",
    public String give_integral;//"0",
    public String order_from;//"4",
    public String add_time;//"1482377543",
    public String pay_time;//"1482377543",
    public String shipping_time;//"1482462362",
    public String confirm_time;//"1482635162",
    public int delay_days;//"0",
    public String order_type;//"0",
    public String service_mark;//"0",
    public String send_mark;//"0",
    public String shipping_mark;//"0",
    public String buyer_type;//"0",
    public String evaluate_status;//"0",
    public String evaluate_time;//"0",
    public String end_time;//"0",
    public String is_distrib;//"0",
    public String distrib_status;//"0",
    public String is_show;//"1,2,3,4",
    public String is_delete;//"0",
    public String order_data;//null,
    public String mall_remark;//null,
    public String shop_remark;//null,
    public String store_remark;//null,
    public String inv_id;//null,
    public String inv_type;//null,
    public String inv_title;//null,
    public String inv_content;//null,
    public String inv_company;//null,
    public String inv_taxpayers;//null,
    public String inv_address;//null,
    public String inv_tel;//null,
    public String inv_account;//null,
    public String inv_bank;//null,
    public String inv_money;//null,
    public String ship_time;//"1482462362",
    public String comment_id;//null,
    public String shop_name;//"达芙妮旗舰店",
    public String shop_type;//"1",
    public String service_tel;//"400-078-5268",
    public String customer_tool;//"2",
    public String customer_account;//"111111",
    public String back_id;//null,
    public String order_status_code;//"shipped",
    public String order_status_format;//"卖家已发货，等待买家确认收货",
    public String inv_type_format;//"普通发票",
    public String region_name;//"天津市-天津市-和平区-和平区",
    public String comment_type;//3,
    public long countdown;//null
    public String pay_status_format;
    public String qrcode_image;
    public String order_points;
    public double change_amount;
    public double discount_fee;
    public String reachbuy_code;
    public int complainted;
    public String complaint_id;
    public String parent_id;
    public String card_no;
    public String activity_name;

    public String pickup_id;
    public String pickup_name;
    public String pickup_address;
    public String pickup_tel;

    public int order_cancel;

    public String group_sn;
    public List buttons;
    public List<OutDeliveryModel> out_delivery;
    public List<DeliveryListModel> delivery_list;

    public List<GoodsListBean> goods_list;

    public Double store_card_price;
    public String store_card_price_format;
    public String store_card_id;

    public String favorable_format;//优惠

    public PickUpModel pickup;

    public String goods_amount_format;
    public String shipping_fee_format;
    public String money_paid_format;

    public static class GoodsListBean{

        /**
         * goods_id : 17
         * goods_name : 还是测试积分
         * goods_image : /system/config/default_image/default_goods_image_0.png
         * goods_integral : 1080
         * goods_number : 21
         * delivery_number : 1
         */

        public String goods_id;
        public String goods_name;
        public String goods_image;
        public String goods_integral;
        public String goods_number;
        public String delivery_number;
    }
}
