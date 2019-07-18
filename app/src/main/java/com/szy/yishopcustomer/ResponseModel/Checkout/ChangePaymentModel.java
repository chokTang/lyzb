package com.szy.yishopcustomer.ResponseModel.Checkout;

import java.util.List;

/**
 * Created by 宗仁 on 16/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ChangePaymentModel {
    public int code;
    public String message;
    public String data;
    public UserInfoModel user_info;
    public List<ShopOrderModel> shop_orders;

    public OrderInfoModel order;
}
