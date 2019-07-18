package com.szy.yishopcustomer.ResponseModel.Result;

import java.util.List;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public OrderModel order;
    public List<OrderListModel> order_list;
    public String group_sn;

    public boolean is_exchange;
    public boolean is_gift;
}
