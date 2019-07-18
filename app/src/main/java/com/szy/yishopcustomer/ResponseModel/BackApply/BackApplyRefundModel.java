package com.szy.yishopcustomer.ResponseModel.BackApply;

import com.szy.yishopcustomer.Constant.ViewType;

/**
 * Created by liwei on 2017/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BackApplyRefundModel {
    public String content;
    public ViewType viewType;

    //退款金额不能超过订单金额
    public String order_amount;
}