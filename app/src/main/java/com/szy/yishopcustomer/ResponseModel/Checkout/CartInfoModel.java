package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.szy.yishopcustomer.ResponseModel.SetBalance.*;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by 宗仁 on 2016/7/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartInfoModel {
    public String select_goods_number;//1,
    public String select_goods_amount;//149,
    public String select_goods_amount_format;//"￥149.00",
    public String goods_number;//1,
    public String goods_amount;//149,
    public TreeMap<String, ShopItemModel> shop_list;
    public UserInfoModel user_info;

    //public OrderModel order;
    public OrderInfoModel order;
    //public IngotOrder order;// 订单 元宝相关数据
    public int invoice_type;
    public List<PlatformBonusItemModel> bonus_list;
    public String key;
    public String order_integram_num;
}
