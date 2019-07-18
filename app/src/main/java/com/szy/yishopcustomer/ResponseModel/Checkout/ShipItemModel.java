package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;

/**
 * Created by 宗仁 on 2016/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShipItemModel {
    public String id;//0,
    public String name;//"普通快递 包邮",
    public String price;//0,
    public String selected;//"selected",
    public String is_cash;//1,
    public String cash_more;//0,
    public String limit_goods;//null
    public String shop_id;

    public String pickup_id;
    public String pickup_name;

    //用来保存是否选中，默认给数组的第一个
    public boolean isCheck = false;

}
