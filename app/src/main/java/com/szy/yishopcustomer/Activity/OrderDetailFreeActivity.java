package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.OrderDetailFragment;
import com.szy.yishopcustomer.Fragment.OrderDetailFreeFragment;


public class OrderDetailFreeActivity extends YSCBaseActivity {
    public OrderDetailFreeFragment createFragment() {
        return new OrderDetailFreeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
