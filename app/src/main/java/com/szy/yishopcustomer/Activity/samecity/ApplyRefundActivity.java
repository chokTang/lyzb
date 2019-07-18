package com.szy.yishopcustomer.Activity.samecity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Activity.YSCBaseActivity;
import com.szy.yishopcustomer.Fragment.SameCityApplyRefundFragment;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;

/**
 * 同城生活-外卖订单-申请退款/退款详情/退款进度
 * type :1：申请退款 2：退款进度 3：退款详情
 * Created by Administrator on 2018/6/19.
 */

public class ApplyRefundActivity extends YSCBaseActivity {

    private OrderModel mOrderModel;
    private int mType = 1;

    @Override
    protected CommonFragment createFragment() {
        return SameCityApplyRefundFragment.newIntance(mOrderModel, mType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mOrderModel = (OrderModel) args.getSerializable("mode");
            mType = args.getInt("type");
        }
        switch (mType) {
            case 1:
                setTitle("申请退款");
                break;
            case 2:
                setTitle("退款进度");
                break;
            case 3:
                setTitle("退款详情");
                break;
            default:
                mType=1;
                setTitle("申请退款");
                break;
        }
        super.onCreate(savedInstanceState);
    }

}
