package com.szy.yishopcustomer.ResponseModel.OrderList;

import java.util.List;

/**
 * Created by liwei on 2016/8/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DelayOrderModel {
    public int code;
    public DelayDataModel data;

    public class DelayDataModel {
        public String order_id;
        public List delay_days_array;
    }

    public String message;
}
