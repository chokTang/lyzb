package com.szy.yishopcustomer.ResponseModel.Pay;

import java.util.List;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public String key;
    public OrderModel order;
    public List<PaymentItemModel> pay_list;
    public UserInfoModel user_info;
}
