package com.szy.yishopcustomer.Interface;

import com.szy.yishopcustomer.Adapter.OrderTakeOutListAdapter;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;

/**
 * Created by Administrator on 2018/6/12.
 */

public class SameCityOrderListener implements OrderTakeOutListAdapter.IOrderClickStatus {
    @Override
    public void onOrderDetail(OrderModel orderModel, int positions) {

    }

    @Override
    public void onOrderTuiKuaiDetail(OrderModel orderModel, int positions) {

    }

    @Override
    public void onDeteleOrder(OrderModel orderModel, int positions) {

    }

    @Override
    public void onPingLunOrder(OrderModel orderModel, int positions) {

    }

    @Override
    public void onOrderPingLunDetail(OrderModel orderModel, int positions) {

    }

    @Override
    public void onTuiKuaiJinDu(OrderModel orderModel, int position) {

    }

    @Override
    public void onApplyRefund(OrderModel orderModel, int position) {

    }

    @Override
    public void onContactShop(OrderModel orderModel, int position) {

    }

    @Override
    public void onSeeMapDetail(OrderModel orderModel, int position) {

    }
}
