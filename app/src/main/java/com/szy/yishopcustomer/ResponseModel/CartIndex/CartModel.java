package com.szy.yishopcustomer.ResponseModel.CartIndex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lw on 2016/7/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class CartModel {
    public String select_goods_number;// 3,
    public String select_goods_amount;//589,
    public String select_goods_amount_format;//￥589",
    public String goods_number;//11,
    public String goods_amount;//569,
    //public Map<String, ShopListModel> shop_list;

    public List<ShopListModel> shop_list;
//    public TreeMap<String, ShopListModel> shop_list;
//    public TreeMap<String, InvalidListModel> invalid_list;

    public List<InvalidListModel> invalid_list;

    public List<ShopDeliveryEnableBean> shop_delivery_enable;
    public Map<String,String> select_shop_amount;
    public List<String> show_start_price_ids;

    public String shopping_bag;

    public int submit_enable;

    public static class ShopDeliveryEnableBean {
        /**
         * shop_id : 36
         * enable : 1
         */

        public int shop_id;
        public int enable;
    }
}
