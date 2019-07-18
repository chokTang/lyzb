package com.szy.yishopcustomer.ResponseModel.Checkout;

/**
 * Created by 宗仁 on 16/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BalanceInputModel {
    public String balance;
    public String balance_format;
    public String money_pay_format;
    public String money_pay;

    public BalanceInputModel(){}

    public BalanceInputModel(String balance, String balance_format, String money_pay,String money_pay_format) {
        this.balance = balance;
        this.balance_format = balance_format;
        this.money_pay_format = money_pay_format;
        this.money_pay = money_pay;
    }
}
