package com.szy.yishopcustomer.ResponseModel.CartSelect;

import com.szy.yishopcustomer.ResponseModel.CartIndex.CartModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class ResponseCartSelectModel {
    public String code;
    public DataModel data;
    public CartModel cart;

    public class DataModel {
        public String select_goods_number;
        public String select_goods_amount;//": 0,
        public String select_goods_amount_format;//":public String ￥0.00",
        public String goods_number;//": 14,
        public String goods_amount;//": 961.9
        public int submit_enable;

        public List<CartModel.ShopDeliveryEnableBean> shop_delivery_enable;
        public Map<String,String> select_shop_amount;
    }
}
