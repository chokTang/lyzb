package com.szy.yishopcustomer.ResponseModel.OrderList;

import java.util.List;

/**
 * Created by liwei on 2016/8/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CancelOrderModel {
    public int code;
    public CancelOrderReasonModel data;

    public class CancelOrderReasonModel {
        public String order_id;
        public List reason_array;
    }
}
