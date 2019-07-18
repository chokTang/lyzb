package com.szy.yishopcustomer.Activity.samecity;

import android.os.Bundle;

import com.szy.yishopcustomer.Activity.YSCBaseActivity;
import com.szy.yishopcustomer.Fragment.SameCityOrderDetailFragment;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;

/**
 * Created by liwei on 2017/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderDetailActivity extends YSCBaseActivity {

    private OrderModel orderModel;

    @Override
    public SameCityOrderDetailFragment createFragment() {
        return SameCityOrderDetailFragment.newIntance(orderModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getExtras() != null) {
            orderModel = (OrderModel) getIntent().getExtras().getSerializable("model");
        }
        super.onCreate(savedInstanceState);
        setTitle("订单详情");
    }
}
