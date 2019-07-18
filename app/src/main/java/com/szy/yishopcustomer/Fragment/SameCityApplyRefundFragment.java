package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

/**
 * 同城生活-外卖订单-申请退款/退款详情/退款进度
 * type :1：申请退款 2：退款进度 3：退款详情
 * Created by Administrator on 2018/6/19.
 */

public class SameCityApplyRefundFragment extends YSCBaseFragment {

    //上个页面传过来的参数
    private OrderModel mOrderModel;
    private int mType = 1;
    private static final String PARAMS_MODEL = "PARAMS_MODEL";
    private static final String PARAMS_TYPE = "PARAMS_TYPE";

    private View mView;
    private TextView same_order_tv;
    private TextView same_refund_refund_tv;
    private TextView apply_submit_btn;
    private LinearLayout refund_speed_layout;
    private TextView refund_speed_tv;

    public static SameCityApplyRefundFragment newIntance(OrderModel orderModel, int type) {
        SameCityApplyRefundFragment fragment = new SameCityApplyRefundFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMS_MODEL, orderModel);
        args.putInt(PARAMS_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mOrderModel = (OrderModel) args.getSerializable(PARAMS_MODEL);
            mType = args.getInt(PARAMS_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_samecity_apply_refund_layout, container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initStatus();
        initData();
    }

    private final void initView() {
        same_order_tv = findViewById(R.id.same_order_tv);
        same_refund_refund_tv = findViewById(R.id.same_refund_refund_tv);
        apply_submit_btn = findViewById(R.id.apply_submit_btn);
        refund_speed_layout = findViewById(R.id.refund_speed_layout);
        refund_speed_tv = findViewById(R.id.refund_speed_tv);

        apply_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onApplyRefund();
            }
        });
    }

    private final void initStatus() {
        switch (mType) {
            case 1:
                refund_speed_layout.setVisibility(View.GONE);
                apply_submit_btn.setVisibility(View.VISIBLE);
                break;
            case 2:
                refund_speed_layout.setVisibility(View.VISIBLE);
                refund_speed_tv.setText("退款中");
                apply_submit_btn.setVisibility(View.GONE);
                break;
            case 3:
                refund_speed_layout.setVisibility(View.VISIBLE);
                refund_speed_tv.setText("已退款");
                apply_submit_btn.setVisibility(View.GONE);
                break;
        }
    }

    private final void initData() {
        if (mOrderModel == null) return;
        same_order_tv.setText(mOrderModel.getOrderId());
        same_refund_refund_tv.setText(String.format("￥%.2f", mOrderModel.getPayAmount()));
    }

    private final <T> T findViewById(@IdRes int id) {
        return (T) mView.findViewById(id);
    }

    /**
     * 申请退款
     */
    private void onApplyRefund(){
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_ORDER_APPLY_REFUND, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_ORDER_APPLY_REFUND, "POST");

        JSONObject json_post = new JSONObject();
        json_post.put("orderId", mOrderModel.getOrderId());
        json_post.put("refundType", 1);
        request.setDefineRequestBodyForJson(json_post.toJSONString());

        requestQueue.add(1030, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {
                    Toast.makeText(getActivity(), "申请退款成功", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "申请退款失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(), "申请退款失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
