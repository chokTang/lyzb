package com.szy.yishopcustomer.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.samecity.ApplyRefundActivity;
import com.szy.yishopcustomer.Adapter.samecity.GoodsAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Other.SameCityDeleteOrderBus;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderGoodModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderShopingModel;
import com.szy.yishopcustomer.Util.DateStyle;
import com.szy.yishopcustomer.Util.DateUtil;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.View.MyRecycleView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 同城生活-外卖订单详情
 * Created by Administrator on 2018/6/12.
 */

public class SameCityOrderDetailFragment extends YSCBaseFragment {

    private final static String PARAMS_ID = "PARAMS_ID";
    private OrderModel mOrderModel;

    private final static int GET_DETAIL = 0x6555;

    private String shopId;
    private View mView;
    private TextView order_status_tv;
    private TextView order_title_tv;
    private MyRecycleView goods_recycle;
    private TextView order_total_tv;
    private TextView packing_tv;
    private TextView distribution_tv;
    private TextView eductible_tv;
    private TextView order_pay_tv;
    private FrameLayout call_shop_flayout;
    private TextView shop_song_address_tv;
    private TextView shop_song_nameandphone_tv;
    private TextView shop_song_phone_tv;
    private TextView order_pay_time_tv;
    private LinearLayout shop_song_phone_layout;
    private LinearLayout llshop;
    private TextView shop_song_time_tv;
    private CheckBox xieyi_check;
    private TextView xieyi_tv;
    private LinearLayout account_xieyi_layout;
    private TextView order_number_tv;
    private TextView order_pay_number_tv;
    private TextView order_pay_type_tv;
    private LinearLayout order_remark_layout;
    private TextView order_remark_tv;
    private TextView order_fapiao_tv;
    private LinearLayout bottom_layout;
    private TextView gray_btn;
    private TextView red_btn;
    private FrameLayout open_or_colse_frame;
    private TextView open_or_colse_tv;
    private TextView order_status_submit_tv;
    private LinearLayout invoice_layout;
    private TextView distribution_type_tv;
    private TextView distribution_address_tv;
    private TextView distribution_time_tv;
    private LinearLayout shop_layout;
    private LinearLayout distribution_time_layout;

    private GoodsAdapter mGoodsAdapter;

    //退款中的倒计时
    private Timer mTimer = null;
    private int mCount = 2 * 24 * 60 * 60;

    public static SameCityOrderDetailFragment newIntance(OrderModel orderModel) {
        SameCityOrderDetailFragment fragment = new SameCityOrderDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMS_ID, orderModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mOrderModel = (OrderModel) args.getSerializable(PARAMS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_same_city_order_detail_layout, container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAdapter(mOrderModel.getGoodsList());
    }

    private void initView() {
        order_status_tv = findViewById(R.id.order_status_tv);
        order_title_tv = findViewById(R.id.order_title_tv);
        goods_recycle = findViewById(R.id.goods_recycle);
        order_total_tv = findViewById(R.id.order_total_tv);
        packing_tv = findViewById(R.id.packing_tv);
        distribution_tv = findViewById(R.id.distribution_tv);
        eductible_tv = findViewById(R.id.eductible_tv);
        order_pay_tv = findViewById(R.id.order_pay_tv);
        order_pay_time_tv = findViewById(R.id.order_pay_time_tv);
        call_shop_flayout = findViewById(R.id.call_shop_flayout);
        shop_song_address_tv = findViewById(R.id.shop_song_address_tv);
        shop_song_nameandphone_tv = findViewById(R.id.shop_song_nameandphone_tv);
        shop_song_phone_tv = findViewById(R.id.shop_song_phone_tv);
        shop_song_phone_layout = findViewById(R.id.shop_song_phone_layout);
        shop_song_time_tv = findViewById(R.id.shop_song_time_tv);
        xieyi_check = findViewById(R.id.xieyi_check);
        xieyi_tv = findViewById(R.id.xieyi_tv);
        account_xieyi_layout = findViewById(R.id.account_xieyi_layout);
        order_number_tv = findViewById(R.id.order_number_tv);
        order_pay_number_tv = findViewById(R.id.order_pay_number_tv);
        order_pay_type_tv = findViewById(R.id.order_pay_type_tv);
        order_remark_layout = findViewById(R.id.order_remark_layout);
        order_remark_tv = findViewById(R.id.order_remark_tv);
        order_fapiao_tv = findViewById(R.id.order_fapiao_tv);
        bottom_layout = findViewById(R.id.bottom_layout);
        gray_btn = findViewById(R.id.gray_btn);
        red_btn = findViewById(R.id.red_btn);
        open_or_colse_frame = findViewById(R.id.open_or_colse_frame);
        open_or_colse_tv = findViewById(R.id.open_or_colse_tv);
        order_status_submit_tv = findViewById(R.id.order_status_submit_tv);
        invoice_layout = findViewById(R.id.invoice_layout);
        distribution_type_tv = findViewById(R.id.distribution_type_tv);
        distribution_address_tv = findViewById(R.id.distribution_address_tv);
        distribution_time_tv = findViewById(R.id.distribution_time_tv);
        shop_layout = findViewById(R.id.shop_layout);
        distribution_time_layout = findViewById(R.id.distribution_time_layout);
        llshop = findViewById(R.id.ll_shop);

        call_shop_flayout.setOnClickListener(this);
        llshop.setOnClickListener(this);
        xieyi_tv.setOnClickListener(this);
        open_or_colse_tv.setOnClickListener(this);
    }

    /**
     * 初始化组件
     *
     * @param idres
     * @param <T>
     * @return
     */
    private <T> T findViewById(@IdRes int idres) {
        return (T) mView.findViewById(idres);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //拨打电话
            case R.id.call_shop_flayout:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mOrderModel.getServiceTel());
                intent.setData(data);
                startActivity(intent);
                break;
            //用户快递协议
            case R.id.xieyi_tv:
                Intent mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(),
                        Api.H5_CITYLIFE_URL + "/takeoutAgreement");

                startActivity(mIntent);
                break;
            //展开的效果
            case R.id.open_or_colse_tv:
                mGoodsAdapter.udpateList(mOrderModel.getGoodsList());
                open_or_colse_frame.setVisibility(View.GONE);
                break;

            case R.id.ll_shop:
                Intent intent1 = new Intent(getActivity(), ProjectH5Activity.class);
                intent1.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + shopId);
                getActivity().startActivity(intent1);
                break;

        }
    }

    /**
     * 根据状态来显示和隐藏组件
     *
     * @param model
     */
    private void onShowOrHideByState(final OrderModel model) {
        showAllView();
        final int orderState = model.getOrderStatus();
        final int shopingState = model.getShippingStatus();
        switch (orderState) {
            //待使用
            case 2:
                if (shopingState == 1) { //待发货
                    if (TextUtils.isEmpty(model.getRefundRefuseMsg())) {
                        order_status_submit_tv.setVisibility(View.GONE);
                    } else {
                        order_status_submit_tv.setVisibility(View.VISIBLE);
                        order_status_submit_tv.setText(model.getRefundRefuseMsg());
                    }
                    gray_btn.setVisibility(View.GONE);
                    red_btn.setText("申请退款");
                    red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onApplyOrder();
                        }
                    });
                } else if (shopingState == 0) {//等待商家接单
                    order_status_submit_tv.setVisibility(View.GONE);
                    gray_btn.setVisibility(View.GONE);
                    red_btn.setText("申请退款");
                    red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onApplyOrder();
                        }
                    });
                } else if (shopingState == 2) {//-配送中
                    order_status_submit_tv.setVisibility(View.GONE);
                    if (model.getShippingType() == 1) {//到店自提
                        red_btn.setText("确认自提");
                    } else {//商家配送
                        red_btn.setText("确认收货");
                    }
                    gray_btn.setVisibility(View.GONE);
                    red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSubmitOrderFinsh(model.getOrderId());
                        }
                    });
                } else {//已完成订单 shopingState==3
                    onShopPickUp(model);
                }
                break;
            case 4://退款中
                onOrderRefund(model);
                break;
            case 6://已退款
                onOrderRefunded(model);
                break;
            case 3://已完成--待评价
            case 5://已完成--已评价
                order_status_submit_tv.setVisibility(View.GONE);

                gray_btn.setText("删除订单");
                gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDeleteOrder(model.getOrderId());
                    }
                });
                red_btn.setText(orderState == 3 ? "去评价" : "查看评价");
                red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orderState == 3) {
                            onCommentOrder(model);
                        } else {
                            onSeeCommentDetail(model);
                        }
                    }
                });
                break;
            //商家拒单
            case 9:
                order_status_tv.setText("商家拒单");
                order_status_submit_tv.setText("系统已办理自动退款,请留意通知");
                shop_layout.setVisibility(View.GONE);
                bottom_layout.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 初始化适配器
     *
     * @param list
     */
    private void initAdapter(List<OrderGoodModel> list) {
        if (list == null) return;
        List<OrderGoodModel> mList = new ArrayList<>();
        if (list.size() > 3) {
            for (int i = 0; i < 3; i++) {
                mList.add(list.get(i));
            }
            open_or_colse_frame.setVisibility(View.VISIBLE);
        } else {
            mList.addAll(list);
            open_or_colse_frame.setVisibility(View.GONE);
        }
        mGoodsAdapter = new GoodsAdapter(getContext(), mList);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        goods_recycle.setLayoutManager(mLinearLayoutManager);
        goods_recycle.setAdapter(mGoodsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderDetail();
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetail() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_ORDER_DETAIL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_ORDER_DETAIL, "GET");
        request.add("orderId", mOrderModel.getOrderId());
        requestQueue.add(GET_DETAIL, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject object = JSON.parseObject(response.get());
                OrderModel result = JSON.parseObject(response.get(), OrderModel.class);
                if (response.responseCode() == 200) {
                    if (result == null) {
                        return;
                    }
                    switch (what) {
                        case GET_DETAIL:
                            mOrderModel = result;
                            shopId = result.getStoreId();
                            initData(mOrderModel);
                            onShowOrHideByState(mOrderModel);
                            break;
                        default:
                            break;
                    }
                } else if (response.responseCode() == 401) {
                } else {
                    toast(object.getString("message"));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                switch (what) {
                    case GET_DETAIL:
                        toast("获取详情失败");
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 加载数据
     *
     * @param model OrderTakeOutDetailModel
     */
    private void initData(OrderModel model) {
        if (model == null) return;
        order_status_tv.setText(model.getOrderStatusMsg());
        order_title_tv.setText(model.getStoreName());
        order_total_tv.setText(String.format("￥%.2f", model.getPayAmount() - model.getPackFee() - model.getShippingFee() + model.getPayPoint()));
        packing_tv.setText(String.format("￥%.2f", model.getPackFee()));
        distribution_tv.setText(String.format("￥%.2f", model.getShippingFee()));
        eductible_tv.setText(String.format("￥%d", model.getPayPoint()));
        order_pay_tv.setText(String.format("￥%.2f", model.getPayAmount()));

        //订单信息
        order_number_tv.setText(model.getOrderCode());
        order_pay_number_tv.setText(model.getPayCode());
        order_pay_time_tv.setText(DateUtil.StringToString(model.getPayTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
        order_pay_type_tv.setText(String.valueOf(model.getPayMentMsg()));//支付方式（1微信，2支付宝，3微信网页，99余额）
        //处理备注
        if (TextUtils.isEmpty(model.remark)) {
            order_remark_layout.setVisibility(View.GONE);
        } else {
            order_remark_layout.setVisibility(View.VISIBLE);
            order_remark_tv.setText(model.remark);
        }
        //发票
        if (TextUtils.isEmpty(model.getInvoiceDutyParagraph()) && TextUtils.isEmpty(model.getInvoiceRise())) {
            invoice_layout.setVisibility(View.GONE);
        } else {
            invoice_layout.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(model.getInvoiceDutyParagraph())) {
                order_fapiao_tv.setText(model.getInvoiceRise());
            } else {
                order_fapiao_tv.setText(model.getInvoiceRise() + "\n" + model.getInvoiceDutyParagraph());
            }
        }

        if (model.getShippingType() == 1) {//自提
            initShopHome(model);
        } else {//配送
            initShopSong(model);
        }
    }

    /**
     * 退款中
     *
     * @param model
     */
    private final void onOrderRefund(OrderModel model) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        //这里需要设置mCount 时间-----
        mCount = model.getTimeUp();
        if (mCount > 0) {
            mTimer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    onOrderRefundTimer();
                }
            };
            mTimer.schedule(task, 0, 1000);
            order_status_submit_tv.setText(String.format("%s以后商家未处理系统自动退款", getOrderRefundTime(mCount)));
        } else {
            order_status_submit_tv.setText("自动退款处理中...");
        }

        gray_btn.setVisibility(View.GONE);
        red_btn.setText("退款进度");
        red_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplyRefundActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("mode", mOrderModel);
                args.putInt("type", 2);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
    }

    /**
     * 已退款
     *
     * @param model
     */
    private final void onOrderRefunded(OrderModel model) {
        if (TextUtils.isEmpty(model.getRefundRefuseMsg())) {
            order_status_submit_tv.setVisibility(View.GONE);
        } else {
            order_status_submit_tv.setText(model.getRefundRefuseMsg());
        }
        gray_btn.setVisibility(View.GONE);
        red_btn.setText("退款详情");
        red_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplyRefundActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("mode", mOrderModel);
                args.putInt("type", 3);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
    }

    /**
     * 已自提 ---商家端已经确定提货
     *
     * @param model
     */
    private final void onShopPickUp(final OrderModel model) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        //这里需要设置mCount 时间-----
        mCount = model.getTimeUp();
        if (mCount > 0) {
            mTimer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (order_status_submit_tv != null) {
                                order_status_submit_tv.setText(String.format("商家已确认送达，%s后系统自动确认收货", getOrderRefundTime(mCount)));
                                mCount--;
                                if (mCount < 0) {
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                        mTimer = null;
                                        onSubmitOder(model.getOrderId());
                                    }
                                }
                            }
                        }
                    });
                }
            };
            mTimer.schedule(task, 0, 1000);
            order_status_submit_tv.setText(String.format("商家已确认送达，%s后系统自动确认收货", getOrderRefundTime(mCount)));

            if (model.getShippingType() == 1) {//到店自提
                red_btn.setText("确认自提");
            } else {//商家配送
                red_btn.setText("确认收货");
            }
            gray_btn.setVisibility(View.GONE);
            red_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubmitOrderFinsh(model.getOrderId());
                }
            });
        } else {
            mOrderModel.setOrderStatus(3);
            onShowOrHideByState(mOrderModel);
        }
    }

    /**
     * 申请退款
     */
    private void onApplyOrder() {
        Intent intent = new Intent(getActivity(), ApplyRefundActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("mode", mOrderModel);
        intent.putExtras(args);
        startActivity(intent);
    }

    /**
     * 删除订单
     *
     * @param orderId
     */
    private final void onDeleteOrder(final String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("是否删除该订单?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOrder(orderId);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    /**
     * 评论订单
     *
     * @param model
     */
    private final void onCommentOrder(OrderModel model) {
        String shopId = "";
        if (model.getGoodsList() != null && model.getGoodsList().size() > 0) {
            shopId = model.getGoodsList().get(0).getProdId();
        }
        Intent mIntent = new Intent(getActivity(), ProjectH5Activity.class);
        //评价
        mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/comment?orderId="
                + model.getOrderId() + "&storeId=" + model.getStoreId() + "&prodId=" + shopId + "&orderCode=" + model.getOrderCode());
        startActivity(mIntent);
    }

    /**
     * 查看评论详情
     *
     * @param model
     */
    private final void onSeeCommentDetail(OrderModel model) {
        Intent mIntent = new Intent(getActivity(), ProjectH5Activity.class);
        mIntent.putExtra(Key.KEY_URL.getValue(),
                Api.H5_CITYLIFE_URL + "/userCommentList?orderId=" + model.getOrderId());

        startActivity(mIntent);
    }

    /**
     * 确认自提
     *
     * @param orderId
     */
    private final void onSubmitOrderFinsh(final String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确认商品没有问题？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onSubmitOder(orderId);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    /**
     * 退款执行倒计时
     */
    private final void onOrderRefundTimer() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (order_status_submit_tv != null) {
                    order_status_submit_tv.setText(String.format("%s以后商家未处理系统自动退款", getOrderRefundTime(mCount)));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            //退款时间到了以后
                            onOrderRefunded(mOrderModel);
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取倒计时
     *
     * @param timeLong
     * @return
     */
    private final String getOrderRefundTime(int timeLong) {
        int time = timeLong / 60 / 60;
        int minute = timeLong / 60 % 60;
        int second = timeLong % 60;
        return String.format("%02d:%02d:%02d", time, minute, second);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 删除订单
     *
     * @param orderId
     */
    private void deleteOrder(final String orderId) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_DELETE_ORDER, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_DELETE_ORDER, "POST");

        JSONObject json_post = new JSONObject();
        json_post.put("orderId", orderId);
        request.setDefineRequestBodyForJson(json_post.toJSONString());

        requestQueue.add(102, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {
                    Toast.makeText(getActivity(), "订单删除成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new SameCityDeleteOrderBus(orderId));
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "订单删除失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 确认收货
     *
     * @param orderId
     */
    private void onSubmitOder(String orderId) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_TAKE_ORDER_SUBMIT, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_TAKE_ORDER_SUBMIT, "POST");

        JSONObject json_post = new JSONObject();
        json_post.put("orderId", orderId);
        request.setDefineRequestBodyForJson(json_post.toJSONString());

        requestQueue.add(1050, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {
                    Toast.makeText(getActivity(), "确认收货成功", Toast.LENGTH_SHORT).show();
                    mOrderModel.setOrderStatus(3);
                    onShowOrHideByState(mOrderModel);
                } else {
                    Toast.makeText(getActivity(), "确认收货失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 显示所有的组件
     */
    private void showAllView() {
        order_status_submit_tv.setVisibility(View.VISIBLE);
        bottom_layout.setVisibility(View.VISIBLE);
        gray_btn.setVisibility(View.VISIBLE);
        red_btn.setVisibility(View.VISIBLE);
    }

    /**
     * 商家配送
     */
    private final void initShopSong(OrderModel model) {
        shop_layout.setVisibility(View.VISIBLE);
        distribution_type_tv.setText("商家配送");
        distribution_address_tv.setText("配送地址");
        shop_song_nameandphone_tv.setVisibility(View.VISIBLE);
        //设置配送地址
        if (model.getShipping() != null && model.getShipping().size() > 0) {
            OrderShopingModel shopingModel = model.getShipping().get(0);
            shop_song_address_tv.setText(shopingModel.getShippingAddress());
            shop_song_nameandphone_tv.setText(shopingModel.getShippingUserName() + " " + shopingModel.getShippingPhone());
        }
        shop_song_phone_layout.setVisibility(View.GONE);

        //配送时间
        if (TextUtils.isEmpty(model.getShippingTime())) {
            distribution_time_layout.setVisibility(View.GONE);
        } else {
            distribution_time_layout.setVisibility(View.VISIBLE);
            distribution_time_tv.setText("配送时间");
            shop_song_time_tv.setText(Html.fromHtml("大约 <font color='#3f99d8'>" + DateUtil.StringToString(model.getShippingTime(),
                    DateStyle.HH_MM) + "</font> 送达"));
        }

        account_xieyi_layout.setVisibility(View.GONE);
    }

    /**
     * 上门自提
     *
     * @param model
     */
    private final void initShopHome(OrderModel model) {
        shop_layout.setVisibility(View.VISIBLE);
        distribution_type_tv.setText("到店自取");
        distribution_address_tv.setText("店铺地址");
        shop_song_nameandphone_tv.setVisibility(View.GONE);
        shop_song_address_tv.setText(model.getStoreAddress());

        //自取电话
        shop_song_phone_layout.setVisibility(View.VISIBLE);
        shop_song_phone_tv.setText(model.getServiceTel());

        distribution_time_tv.setText("自取时间");
        shop_song_time_tv.setText("请您在" + model.getShippingTimeEnd() + "之前到店自取商品，如果超过时请提前与商家沟通，否则商家有权丢弃商品并拒绝退款。");//---

        account_xieyi_layout.setVisibility(View.VISIBLE);
    }
}
