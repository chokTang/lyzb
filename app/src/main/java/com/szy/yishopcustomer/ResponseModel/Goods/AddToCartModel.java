package com.szy.yishopcustomer.ResponseModel.Goods;

/**
 * Created by liwei on 2016/8/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddToCartModel {
    public int code;//0,
    public DataModel data;
    public String message;//加入购物车成功"

    public class DataModel {
        public int sku_open;
        public Object cat_cart_num;

        public String number;

    }
}
