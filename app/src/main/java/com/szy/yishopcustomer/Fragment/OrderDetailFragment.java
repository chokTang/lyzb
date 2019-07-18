package com.szy.yishopcustomer.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.BackApplyActivity;
import com.szy.yishopcustomer.Activity.BackDetailActivity;
import com.szy.yishopcustomer.Activity.ComplaintActivity;
import com.szy.yishopcustomer.Activity.ComplaintDetailActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Adapter.OrderDetailAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnOrderButtonListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.DeliveryListModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.GroupOnModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.Model;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderAddressModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderCountDownModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OutDeliveryModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.QrcodeModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.TitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.TotalModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by liwei on 2016/6/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderDetailFragment extends OrderOperation {
    public long leftTime;
    @BindView(R.id.fragment_order_detail_recyclerView)
    CommonRecyclerView mRecyclerView;

    @BindView(R.id.linearlayout_buttons)
    LinearLayout linearlayout_buttons;

    @BindView(R.id.fragment_order_detail_button_layout)
    LinearLayout mButtonLayout;
    private String order_id;
    private String shop_id;
    private String orderStatusCode;
    private LinearLayoutManager mLayoutManager;
    private OrderDetailAdapter mOrderDetailAdapter;
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            leftTime -= 1;
            if (leftTime <= 0) {
                timerTask.cancel();
                operateTimeOut();
            } else {
                OrderCountDownModel orderCountDownModel = null;

                for (int i = 0; i < mOrderDetailAdapter.data.size(); i++) {
                    if (mOrderDetailAdapter.data.get(i) instanceof OrderCountDownModel) {
                        orderCountDownModel = (OrderCountDownModel) mOrderDetailAdapter.data.get(i);
                    }
                }

                if (orderCountDownModel != null) {
                    orderCountDownModel.count_down = leftTime;
                    mOrderDetailAdapter.notifyDataSetChanged();
                }
            }
        }

    };
    private String order_sn;
    private String complaint_id;

    private void operateTimeOut() {
        //未支付超时，自动取消订单
        if (orderStatusCode.equals("unpayed")) {
            CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_CANCEL_SYS, HttpWhat
                    .HTTP_ORDER_CANCEL_SYS.getValue(), RequestMethod.POST);
            mCancelOrderRequest.add("order_id", order_id);
            addRequest(mCancelOrderRequest);
            //未收货超时，自动确认收货
        } else if (orderStatusCode.equals("shipped")) {
            CommonRequest mConfirmOrderRequest = new CommonRequest(Api.API_ORDER_CONFIRM_SYS, HttpWhat
                    .HTTP_ORDER_CONFIRM_SYS.getValue(), RequestMethod.POST);
            mConfirmOrderRequest.add("order_id", order_id);
            addRequest(mConfirmOrderRequest);
            //拼团结束
        } else if (orderStatusCode.equals("groupon_active")) {
            CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_CANCEL_GROUPON, HttpWhat
                    .HTTP_ORDER_CANCEL.getValue());
            mQuickBuyRequest.add("group_sn", grouponModel.group_sn);
            addRequest(mQuickBuyRequest);
        }
    }

    private GroupOnModel grouponModel;

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_ORDER_GOODS:
                openGoodsActivity(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_REFUND:
                String orderId;
                String goodsId;
                String skuId;
                String recordId;
                if (extraInfo == 0) {
                    OutDeliveryModel outDeliveryModel = (OutDeliveryModel) mOrderDetailAdapter
                            .data.get(position);
                    orderId = outDeliveryModel.order_id;
                    goodsId = outDeliveryModel.goods_id;
                    skuId = outDeliveryModel.sku_id;
                    recordId = outDeliveryModel.record_id;
                } else {
                    GoodsModel goodsModel = (GoodsModel) mOrderDetailAdapter
                            .data.get(position);
                    orderId = goodsModel.order_id;
                    goodsId = goodsModel.goods_id;
                    skuId = goodsModel.sku_id;
                    recordId = goodsModel.record_id;
                }
                openRefundActivity(orderId, goodsId, skuId, recordId);
                break;
            case VIEW_TYPE_GROUPON_DETAIL:
                openGoupOnDetailActivity(grouponModel.group_sn);
                break;
            case VIEW_TYPE_BACK_DETAIL:
                openBackDetailActivity(extraInfo);
                break;
            case VIEW_TYPE_COMMENT:
                goComment(order_id, shop_id);
                break;
            case VIEW_TYPE_COMPLAINT:

                GoodsModel goodsModel = (GoodsModel) mOrderDetailAdapter
                        .data.get(position);
                orderId = goodsModel.order_id;
                goodsId = goodsModel.goods_id;
                skuId = goodsModel.sku_id;

                openComplaintActivity(orderId, skuId);
                break;
            case VIEW_TYPE_DETAIL:
                openComplaintDetail(complaint_id);
                break;
            case VIEW_TYPE_COPY:
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context
                        .CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", order_sn);
                clipboard.setPrimaryClip(clip);
                Utils.toastUtil.showToast(getActivity(), "订单编号复制成功");
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_BACK_DETAIL:
                refresh(order_id);
                break;
        }
    }

    private void openComplaintDetail(String complaint_id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ComplaintDetailActivity.class);
        intent.putExtra(Key.KEY_COMPLAINT_ID.getValue(), complaint_id);
        startActivity(intent);
    }

    private void openRefundActivity(String orderId, String goodsId, String skuId, String record_id) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.putExtra(Key.KEY_RECORD_ID.getValue(), record_id);
        intent.setClass(getActivity(), BackApplyActivity.class);
        startActivity(intent);
    }

    private void openBackDetailActivity(int backId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_BACK_ID.getValue(), backId + "");
        intent.setClass(getActivity(), BackDetailActivity.class);
        startActivity(intent);
    }

    private void openComplaintActivity(String orderId, String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), ComplaintActivity.class);
        startActivity(intent);
    }

    public void openGoupOnDetailActivity(String groupSn) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.setClass(getActivity(), UserGroupOnDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mOrderDetailAdapter = new OrderDetailAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mOrderDetailAdapter);
        Intent intent = getActivity().getIntent();
        order_id = intent.getStringExtra(Key.KEY_ORDER_ID.getValue());
        refresh(order_id);

        mOrderDetailAdapter.onClickListener = this;

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_DETAIL:
                refreshCallBack(response);
                break;
            case HTTP_ORDER_CANCEL_SYS:
            case HTTP_ORDER_CONFIRM_SYS:
                HttpResultManager.resolve(response,
                        ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                Utils.makeToast(getActivity(), back.message);
                                refresh(order_id);
                            }
                        }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_order_detail;
    }

    @Override
    public void delOrderCallback(String response) {
        super.delOrderCallback(response);
        HttpResultManager.resolve(response,
                ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        Utils.makeToast(getActivity(), back.message);
                        getActivity().finish();
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_ORDER_LIST
                                .getValue()));
                    }
                }, true);
    }

    @Override
    public void confirmOrderCallback(String response) {
        super.confirmOrderCallback(response);
        ResponseCommonModel responseModel = JSON.parseObject(response,
                ResponseCommonModel.class);
        if (responseModel.code == 0) {
            Utils.makeToast(getActivity(), responseModel.message);
            mOrderDetailAdapter.data.clear();
            refresh(order_id);
        } else {
            Utils.makeToast(getActivity(), responseModel.message);
        }
    }

    @Override
    public void cancelOrderCallback(String response) {
        super.cancelOrderCallback(response);
        mOrderDetailAdapter.onClickListener = null;
        ResponseCommonModel orderConfirmModel = JSON.parseObject(response,
                ResponseCommonModel.class);
        if (orderConfirmModel.code == 0) {
            if (timerTask != null) {
                timerTask.cancel();
            }
            Utils.makeToast(getActivity(), orderConfirmModel.message);
            mOrderDetailAdapter.data.clear();
            refresh(order_id);
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                    .getValue()));
        } else {
            Utils.makeToast(getActivity(), orderConfirmModel.message);
        }
    }

    @Override
    public void delayOrderCallback(String response) {
        super.cancelOrderCallback(response);
        mOrderDetailAdapter.onClickListener = null;
        ResponseCommonModel orderConfirmModel = JSON.parseObject(response,
                ResponseCommonModel.class);
        if (orderConfirmModel.code == 0) {
            if (timerTask != null) {
                timerTask.cancel();
            }
            Utils.makeToast(getActivity(), orderConfirmModel.message);
            mOrderDetailAdapter.data.clear();
            refresh(order_id);
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                    .getValue()));
        } else {
            Utils.makeToast(getActivity(), orderConfirmModel.message);
        }
    }


    public void openGoodsActivity(String goodsId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    private void refresh(String orderId) {

        if (!Utils.isNull(timerTask)) {
            timerTask.cancel();
        }

        CommonRequest mOrderDetailRequest = new CommonRequest(Api.API_ORDER_INFO, HttpWhat
                .HTTP_ORDER_DETAIL.getValue());
        mOrderDetailRequest.add("id", orderId);
        addRequest(mOrderDetailRequest);
    }

    private void refreshCallBack(String response) {
        Model model = JSON.parseObject(response, Model.class);

        final OrderInfoModel order_info = model.data.order_info;
        shop_id = order_info.shop_id;
        grouponModel = model.data.groupon_info;
        order_sn = model.data.order_info.order_sn;
        setGroup_sn(grouponModel.group_sn);
        if (model.data.order_info.parent_id.equals("0")) {
            complaint_id = model.data.order_info.complaint_id;
        } else {
            complaint_id = model.data.order_info.parent_id;
        }
        mOrderDetailAdapter.data.clear();
        DividerModel dividerModel = new DividerModel();

        OrderStatusModel orderStatusModel = new OrderStatusModel();
        //订单状态展示
        if (Utils.isNull(grouponModel.log_id)) {
            orderStatusModel.order_status = order_info.order_status_format;
        } else {
            orderStatusModel.order_status = grouponModel.groupon_status_format;
        }
        mOrderDetailAdapter.data.add(orderStatusModel);

        QrcodeModel qrcodeModel = new QrcodeModel();
        qrcodeModel.order_sn = order_info.order_sn;
        qrcodeModel.order_id = order_info.order_id;
        mOrderDetailAdapter.data.add(qrcodeModel);

        mOrderDetailAdapter.data.add(dividerModel);

        leftTime = 0;

        if (order_info.countdown > 0 && Utils.isNull(grouponModel.log_id)) {
            leftTime = order_info.countdown;
            OrderCountDownModel orderCountDownModel = new OrderCountDownModel();
            orderCountDownModel.count_down = leftTime;
            orderCountDownModel.order_status_code = order_info.order_status_code;
            mOrderDetailAdapter.data.add(orderCountDownModel);

            mOrderDetailAdapter.data.add(dividerModel);

            orderStatusCode = order_info.order_status_code;
        } else {
            //拼团倒计时，组团中增加
            if (grouponModel.status == 0 && !Utils.isNull(grouponModel.log_id)) {
                leftTime = grouponModel.end_time - model.data.context.current_time;
                OrderCountDownModel orderCountDownModel = new OrderCountDownModel();
                orderCountDownModel.count_down = leftTime;
                orderCountDownModel.order_status_code = "groupon_active";
                orderCountDownModel.diff_num = grouponModel.diff_num;
                mOrderDetailAdapter.data.add(orderCountDownModel);

                mOrderDetailAdapter.data.add(dividerModel);

                orderStatusCode = "groupon_active";
            }
        }

        OrderAddressModel orderAddressModel = new OrderAddressModel();
        orderAddressModel.consignee = order_info.consignee;
        orderAddressModel.tel = order_info.tel;

        if (!Utils.isNull(order_info.pickup_id)) {
            orderAddressModel.address = order_info.region_name + " " + order_info.pickup_address + " " +
                    "(自提点：" + order_info.pickup_name + "，自提点联系方式：" + order_info.pickup_tel + ")";
        } else {
            orderAddressModel.address = order_info.region_name + " " + order_info.address;
        }

        mOrderDetailAdapter.data.add(orderAddressModel);

        mOrderDetailAdapter.data.add(dividerModel);

        //是否显示退款退货按钮
        Boolean showBackButton = false;
        if (order_info.shipping_status != Macro.SS_UNSHIPPED && order_info.order_status == Macro
                .ORDER_CONFIRM && order_info.is_cod == 0) {
            showBackButton = true;
        }

        if (!Utils.isNull(order_info.out_delivery)) {
            TitleModel titleModel = new TitleModel();
            titleModel.title = "待发货商品";
            mOrderDetailAdapter.data.add(titleModel);

            for (OutDeliveryModel outDeliveryModel : order_info.out_delivery) {
                outDeliveryModel.backAllow = showBackButton;
                mOrderDetailAdapter.data.add(outDeliveryModel);
            }
            mOrderDetailAdapter.data.add(dividerModel);
        }

        //是否显示投诉商家按钮
        //只有确认收货后的订单才会显示“投诉商家” 的按钮
        Boolean showComplaintButton = false;
        if (order_info.order_status == Macro.ORDER_FINISHED || order_info.order_status == Macro.ORDER_DISABLE
                || order_info.order_status == Macro.ORDER_CANCEL || order_info.order_status == Macro.ORDER_SYS_CANCEL) {
            showComplaintButton = true;
        }

        if (order_info.delivery_list != null) {

            int i = 1;
            for (DeliveryListModel deliveryListModel : order_info.delivery_list) {
                TitleModel titleModel = new TitleModel();
                titleModel.title = "包裹" + i;

                if (deliveryListModel.base_info.express_sn.equals("0") && deliveryListModel.base_info.delivery_status == Macro.SS_SHIPPED) {
                    titleModel.subTitle = "[无需物流]";
                } else {
                    titleModel.subTitle = deliveryListModel.base_info.shipping_name + "" +
                            deliveryListModel.base_info.express_sn;
                }

                mOrderDetailAdapter.data.add(titleModel);
                for (GoodsModel goodsModel : deliveryListModel.goods_list) {
                    goodsModel.backAllow = showBackButton;

                    goodsModel.complaintAllow = showComplaintButton;
                    goodsModel.complainted = order_info.complainted;
                    mOrderDetailAdapter.data.add(goodsModel);
                }
                i++;
                mOrderDetailAdapter.data.add(dividerModel);
            }
        }

        TotalModel totalModel = new TotalModel();
        totalModel.goods_amount_format = order_info.goods_amount_format;
        totalModel.shipping_fee_format = order_info.shipping_fee_format;

        BigDecimal bd1 = BigDecimal.valueOf(order_info.bonus);
        BigDecimal bd2 = BigDecimal.valueOf(order_info.shop_bonus);

        totalModel.bonus = String.valueOf(bd1.add(bd2));
        totalModel.all_bonus_format = order_info.all_bonus_format;
        totalModel.surplus = order_info.surplus;
        totalModel.surplus_format = order_info.surplus_format;
        totalModel.order_amount = order_info.order_amount_format;
        totalModel.money_paid = order_info.money_paid;
        totalModel.money_paid_format = order_info.money_paid_format;
        totalModel.pay_code = order_info.pay_code;
        totalModel.cash_more = order_info.cash_more;
        totalModel.cash_more_format = order_info.cash_more_format;
        totalModel.pay_status = order_info.pay_status;
        totalModel.order_status_code = order_info.order_status_format;
        totalModel.store_card_price = order_info.store_card_price;
        totalModel.store_card_price_format = order_info.store_card_price_format;
        totalModel.integral = order_info.integral;

        if (order_info.pay_status == Macro.PS_PAID) {
            totalModel.pay_status_format = "实付款金额";
        } else {
            totalModel.pay_status_format = "待付款金额";
        }
        totalModel.order_status = order_info.order_status;

        totalModel.benefit = order_info.favorable_format;

        totalModel.store_card_price = order_info.store_card_price;

        mOrderDetailAdapter.data.add(totalModel);

        mOrderDetailAdapter.data.add(dividerModel);

        mOrderDetailAdapter.data.add(order_info);
        //按钮状态
        linearlayout_buttons.removeAllViews();

        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (!model.data.operate_text.equals("")) {
            if (model.data.operate_text.contains("等待商家审核取消订单申请")) {
                order_info.buttons.remove("cancel_order");

                layoutParams.setMargins(0, 0, 0, 0);

            } else {
                layoutParams.setMargins(0, 0, Utils.dpToPx(getActivity(), 10), 0);
            }
            textView.setLayoutParams(layoutParams);
            textView.setText(model.data.operate_text);
            linearlayout_buttons.addView(textView);
        } else if (order_info.delay_days != 0 && order_info.order_status == Macro.ORDER_CONFIRM) {
            layoutParams.setMargins(0, 0, Utils.dpToPx(getActivity(), 10), 0);
            textView.setLayoutParams(layoutParams);
            textView.setText("已延长" + order_info.delay_days + "天收货");
            linearlayout_buttons.addView(textView);
        }

        List buttons = order_info.buttons;
        if (!Utils.isNull(buttons) && buttons.size() > 0) {
            mButtonLayout.setVisibility(View.VISIBLE);

            Context context = getContext();

            OrderButtonHelper.initButtons(context, buttons, new OnOrderButtonListener(linearlayout_buttons) {
                @Override
                public void setButtons(Button button) {
//                    String button_type = (String) button.getTag(OrderButtonHelper.TAG_BUTTON_TYPE);
                    super.setButtons(button);
                    Utils.setExtraInfoForTag(button, Integer.valueOf
                            (order_info.order_id));
                    button.setOnClickListener(OrderDetailFragment.this);
                }

                @Override
                public void commented(Button button) {
                    button.setEnabled(false);
                    linearlayout_buttons.addView(button);
                }
            });


        } else if (TextUtils.isEmpty(model.data.operate_text)) {
            mButtonLayout.setVisibility(View.GONE);
        }

        if (leftTime > 0) {
            Timer timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }

        mOrderDetailAdapter.onClickListener = this;

        setData(mOrderDetailAdapter.data);

        mOrderDetailAdapter.notifyDataSetChanged();
    }

}
