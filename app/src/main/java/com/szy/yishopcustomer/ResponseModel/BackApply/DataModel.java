package com.szy.yishopcustomer.ResponseModel.BackApply;


import java.util.LinkedHashMap;

/**
 * Created by liwei on 2017/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DataModel {
    public GoodsInfoModel goods_info;
    public TakePackModel jd_take_pack_array;
    public TakeTypeModel jd_take_type_array;
    public LinkedHashMap<String,String> back_reason_array;
    public LinkedHashMap<String,String> back_type_array;
    public BackModel model;

    public OrderInfoModel order_info;
    public String max_refund_freight="";//最大的金额



    public class OrderInfoModel{

        /**
         * order_id : 1149
         * order_sn : 20170623080250304490
         * parent_sn : null
         * user_id : 56
         * order_status : 0
         * shop_id : 6
         * site_id : 1
         * store_id : 0
         * pickup_id : 0
         * shipping_status : 1
         * pay_status : 1
         * consignee : testmall
         * region_code : 13,03,02
         * address : 北环路108号华电测控公司门口 海港区老玩童饭店
         * address_lng :
         * address_lat :
         * receiving_mode : 0
         * tel : 13333333333
         * email : null
         * postscript :
         * best_time : 工作日/周末/假日均可
         * pay_id : 0
         * pay_code : 0
         * pay_name : 余额支付
         * pay_sn : 0
         * is_cod : 0
         * order_amount : 16.90
         * order_points : 0
         * money_paid : 0.00
         * goods_amount : 16.90
         * inv_fee : 0.00
         * shipping_fee : 0.00
         * cash_more : 0.00
         * surplus : 16.90
         * bonus_id : 0
         * shop_bonus_id : 0
         * bonus : 0.00
         * shop_bonus : 0.00
         * integral : 0
         * integral_money : 0.00
         * give_integral : 0
         * order_from : 4
         * add_time : 1498204970
         * pay_time : 1498204970
         * shipping_time : 1498206119
         * confirm_time : 1498638119
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
         */

        private int order_id;
        private String order_sn;
        private Object parent_sn;
        private int user_id;
        private int order_status;
        private int shop_id;
        private int site_id;
        private int store_id;
        private int pickup_id;
        private int shipping_status;
        private int pay_status;
        private String consignee;
        private String region_code;
        private String address;
        private String address_lng;
        private String address_lat;
        private int receiving_mode;
        private String tel;
        private Object email;
        private String postscript;
        private String best_time;
        private int pay_id;
        private String pay_code;
        private String pay_name;
        private String pay_sn;
        private int is_cod;
        private String order_amount;
        private int order_points;
        private String money_paid;
        private String goods_amount;
        private String inv_fee;
        private String shipping_fee;
        private String cash_more;
        private String surplus;
        private int bonus_id;
        private int shop_bonus_id;
        private String bonus;
        private String shop_bonus;
        private int integral;
        private String integral_money;
        private int give_integral;
        private String order_from;
        private int add_time;
        private int pay_time;
        private int shipping_time;
        private int confirm_time;
        private int delay_days;
        private int order_type;
        private int service_mark;
        private int send_mark;
        private int shipping_mark;
        private int buyer_type;
        private int evaluate_status;
        private int evaluate_time;
        private int end_time;
        private int is_distrib;
        private int distrib_status;
        private String is_show;
        private int is_delete;
        private Object order_data;
        private Object mall_remark;
        private Object shop_remark;
        private Object store_remark;
        private Object close_reason;
        private int cash_user_id;
        private Object last_time;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public Object getParent_sn() {
            return parent_sn;
        }

        public void setParent_sn(Object parent_sn) {
            this.parent_sn = parent_sn;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getSite_id() {
            return site_id;
        }

        public void setSite_id(int site_id) {
            this.site_id = site_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getPickup_id() {
            return pickup_id;
        }

        public void setPickup_id(int pickup_id) {
            this.pickup_id = pickup_id;
        }

        public int getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(int shipping_status) {
            this.shipping_status = shipping_status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getRegion_code() {
            return region_code;
        }

        public void setRegion_code(String region_code) {
            this.region_code = region_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_lng() {
            return address_lng;
        }

        public void setAddress_lng(String address_lng) {
            this.address_lng = address_lng;
        }

        public String getAddress_lat() {
            return address_lat;
        }

        public void setAddress_lat(String address_lat) {
            this.address_lat = address_lat;
        }

        public int getReceiving_mode() {
            return receiving_mode;
        }

        public void setReceiving_mode(int receiving_mode) {
            this.receiving_mode = receiving_mode;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getPostscript() {
            return postscript;
        }

        public void setPostscript(String postscript) {
            this.postscript = postscript;
        }

        public String getBest_time() {
            return best_time;
        }

        public void setBest_time(String best_time) {
            this.best_time = best_time;
        }

        public int getPay_id() {
            return pay_id;
        }

        public void setPay_id(int pay_id) {
            this.pay_id = pay_id;
        }

        public String getPay_code() {
            return pay_code;
        }

        public void setPay_code(String pay_code) {
            this.pay_code = pay_code;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getPay_sn() {
            return pay_sn;
        }

        public void setPay_sn(String pay_sn) {
            this.pay_sn = pay_sn;
        }

        public int getIs_cod() {
            return is_cod;
        }

        public void setIs_cod(int is_cod) {
            this.is_cod = is_cod;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public int getOrder_points() {
            return order_points;
        }

        public void setOrder_points(int order_points) {
            this.order_points = order_points;
        }

        public String getMoney_paid() {
            return money_paid;
        }

        public void setMoney_paid(String money_paid) {
            this.money_paid = money_paid;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public String getInv_fee() {
            return inv_fee;
        }

        public void setInv_fee(String inv_fee) {
            this.inv_fee = inv_fee;
        }

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public String getCash_more() {
            return cash_more;
        }

        public void setCash_more(String cash_more) {
            this.cash_more = cash_more;
        }

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }

        public int getBonus_id() {
            return bonus_id;
        }

        public void setBonus_id(int bonus_id) {
            this.bonus_id = bonus_id;
        }

        public int getShop_bonus_id() {
            return shop_bonus_id;
        }

        public void setShop_bonus_id(int shop_bonus_id) {
            this.shop_bonus_id = shop_bonus_id;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public String getShop_bonus() {
            return shop_bonus;
        }

        public void setShop_bonus(String shop_bonus) {
            this.shop_bonus = shop_bonus;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getIntegral_money() {
            return integral_money;
        }

        public void setIntegral_money(String integral_money) {
            this.integral_money = integral_money;
        }

        public int getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(int give_integral) {
            this.give_integral = give_integral;
        }

        public String getOrder_from() {
            return order_from;
        }

        public void setOrder_from(String order_from) {
            this.order_from = order_from;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public int getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(int shipping_time) {
            this.shipping_time = shipping_time;
        }

        public int getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(int confirm_time) {
            this.confirm_time = confirm_time;
        }

        public int getDelay_days() {
            return delay_days;
        }

        public void setDelay_days(int delay_days) {
            this.delay_days = delay_days;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getService_mark() {
            return service_mark;
        }

        public void setService_mark(int service_mark) {
            this.service_mark = service_mark;
        }

        public int getSend_mark() {
            return send_mark;
        }

        public void setSend_mark(int send_mark) {
            this.send_mark = send_mark;
        }

        public int getShipping_mark() {
            return shipping_mark;
        }

        public void setShipping_mark(int shipping_mark) {
            this.shipping_mark = shipping_mark;
        }

        public int getBuyer_type() {
            return buyer_type;
        }

        public void setBuyer_type(int buyer_type) {
            this.buyer_type = buyer_type;
        }

        public int getEvaluate_status() {
            return evaluate_status;
        }

        public void setEvaluate_status(int evaluate_status) {
            this.evaluate_status = evaluate_status;
        }

        public int getEvaluate_time() {
            return evaluate_time;
        }

        public void setEvaluate_time(int evaluate_time) {
            this.evaluate_time = evaluate_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getIs_distrib() {
            return is_distrib;
        }

        public void setIs_distrib(int is_distrib) {
            this.is_distrib = is_distrib;
        }

        public int getDistrib_status() {
            return distrib_status;
        }

        public void setDistrib_status(int distrib_status) {
            this.distrib_status = distrib_status;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public Object getOrder_data() {
            return order_data;
        }

        public void setOrder_data(Object order_data) {
            this.order_data = order_data;
        }

        public Object getMall_remark() {
            return mall_remark;
        }

        public void setMall_remark(Object mall_remark) {
            this.mall_remark = mall_remark;
        }

        public Object getShop_remark() {
            return shop_remark;
        }

        public void setShop_remark(Object shop_remark) {
            this.shop_remark = shop_remark;
        }

        public Object getStore_remark() {
            return store_remark;
        }

        public void setStore_remark(Object store_remark) {
            this.store_remark = store_remark;
        }

        public Object getClose_reason() {
            return close_reason;
        }

        public void setClose_reason(Object close_reason) {
            this.close_reason = close_reason;
        }

        public int getCash_user_id() {
            return cash_user_id;
        }

        public void setCash_user_id(int cash_user_id) {
            this.cash_user_id = cash_user_id;
        }

        public Object getLast_time() {
            return last_time;
        }

        public void setLast_time(Object last_time) {
            this.last_time = last_time;
        }
    }
}
