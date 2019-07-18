package com.szy.yishopcustomer.ResponseModel.SetBalance;

import com.szy.yishopcustomer.ResponseModel.Pay.OrderModel;
import com.szy.yishopcustomer.ResponseModel.Pay.PaymentItemModel;

import java.util.List;

/**
 * Created by 宗仁 on 16/8/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PayBankModel {
    public String is_support_cod;// 0,
    public String cod_more;// 0
    public List<PaymentItemModel> pay_list;
    public OrderModel order;
}
