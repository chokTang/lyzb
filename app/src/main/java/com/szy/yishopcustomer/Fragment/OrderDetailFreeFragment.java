package com.szy.yishopcustomer.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.BackApplyActivity;
import com.szy.yishopcustomer.Activity.BackDetailActivity;
import com.szy.yishopcustomer.Activity.ExpressActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.OrderPayActivity;
import com.szy.yishopcustomer.Activity.OrderReviewActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Adapter.OrderDetailFreeAdapter;
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
import com.szy.yishopcustomer.ResponseModel.OrderList.CancelOrderModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/6/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderDetailFreeFragment extends YSCBaseFragment {
    public long leftTime;
    @BindView(R.id.fragment_order_detail_recyclerView)
    CommonRecyclerView mRecyclerView;

    @BindView(R.id.linearlayout_buttons)
    LinearLayout linearlayout_buttons;

    @BindView(R.id.fragment_order_detail_button_layout)
    LinearLayout mButtonLayout;
    private String order_id;
    private LinearLayoutManager mLayoutManager;
    private OrderDetailFreeAdapter mOrderDetailAdapter;
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            leftTime -= 1;
            if (leftTime <= 0) {
                timerTask.cancel();
                refresh(order_id);
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

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    public void cancelOrder(String orderId) {
        CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_CANCEL, HttpWhat
                .HTTP_ORDER_CANCEL.getValue());
        mCancelOrderRequest.add("from", "list");
        mCancelOrderRequest.add("type", "cancel");
        mCancelOrderRequest.add("id", orderId);
        mCancelOrderRequest.setAjax(true);
        addRequest(mCancelOrderRequest);
    }

    public void goComment(String orderId, String shopId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.putExtra("shop_id", shopId);
        intent.setClass(getActivity(), OrderReviewActivity.class);
        startActivity(intent);
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
                String recordId = null;
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
                openRefundActivity(orderId, goodsId, skuId,recordId);
                break;
            case VIEW_TYPE_BACK_DETAIL:
                openBackDetailActivity(extraInfo);
                break;
            case VIEW_TYPE_CANCEL_ORDER:
                cancelOrder(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_DEL_ORDER:
                delOrder(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_VIEW_LOGISTICS:
                viewExpress(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_PAYMENT:
                openPayActivity(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_AWAIT_CONFIRM:
                order_id = String.valueOf(extraInfo);
                showConfirmDialog(R.string.confrimOrderTip, ViewType
                        .VIEW_TYPE_ORDER_CONFIRM.ordinal());
                break;
            case VIEW_TYPE_COPY:
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context
                        .CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", order_sn);
                clipboard.setPrimaryClip(clip);
                Utils.toastUtil.showToast(getActivity(), "复制成功");
                break;
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

    private void openRefundActivity(String orderId, String goodsId, String skuId,String recordId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.putExtra(Key.KEY_RECORD_ID.getValue(),recordId);
        intent.setClass(getActivity(), BackApplyActivity.class);
        startActivity(intent);
    }

    private void openBackDetailActivity(int backId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_BACK_ID.getValue(), backId + "");
        intent.setClass(getActivity(), BackDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_ORDER_CONFIRM:
                confrimOrder(order_id);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mOrderDetailAdapter = new OrderDetailFreeAdapter();
        mOrderDetailAdapter.buy_type = buy_type;
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
            case HTTP_ORDER_CONFIRM:
                ResponseCommonModel responseModel = JSON.parseObject(response,
                        ResponseCommonModel.class);
                if (responseModel.code == 0) {
                    Utils.makeToast(getActivity(), responseModel.message);
                    mOrderDetailAdapter.data.clear();
                    refresh(order_id);
                } else {
                    Utils.makeToast(getActivity(), responseModel.message);
                }
                break;
            case HTTP_ORDER_CANCEL:
                dialog(response);
                break;
            case HTTP_ORDER_CANCEL_CONFIRM:
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
                break;
            case HTTP_ORDER_DELETE:
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
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    int buy_type = OrderListFreeFragment.TYPE_FREEBUY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_order_detail;

        buy_type = getActivity().getIntent().getIntExtra(OrderListFreeFragment.PARAMS_TYPE, OrderListFreeFragment.TYPE_FREEBUY);

        if (buy_type == OrderListFreeFragment.TYPE_REACHBUY) {
            getActivity().setTitle("订单详情-堂内点餐");
        } else if (buy_type == OrderListFreeFragment.TYPE_PICKUP) {
            getActivity().setTitle("提货详情");
        }
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void openPayActivity(String orderId) {
        Intent intent = new Intent(getContext(), OrderPayActivity.class);
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        startActivity(intent);
    }

    public void viewExpress(String orderId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.setClass(getActivity(), ExpressActivity.class);
        startActivity(intent);
    }

    private void confrimOrder(String orderId) {
        CommonRequest mOrderConfirmRequest = new CommonRequest(Api.API_ORDER_CONFIRM, HttpWhat
                .HTTP_ORDER_CONFIRM.getValue(), RequestMethod.POST);
        mOrderConfirmRequest.add("id", orderId);
        addRequest(mOrderConfirmRequest);
    }

    private void dialog(String response) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        final ArrayList<String> data = new ArrayList<String>();
        String order_id = null;
        CancelOrderModel model = JSON.parseObject(response, CancelOrderModel.class);
        if (model.code == 0) {
            for (int i = 0; i < model.data.reason_array.size(); i++) {
                data.add(model.data.reason_array.get(i).toString());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id
                .item_cancel_order_textView, data);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id
                .order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);

        final String[] cancel_reason = {""};
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cancel_reason[0] = data.get(position).toString();
            }
        });

        final String finalOrder_id = model.data.order_id;

        final AlertDialog mDialogDialog = new AlertDialog.Builder(getActivity()).setView
                (cancelOrderDialogView).create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_cancle_textView);
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_confirm_textView);
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonRequest mCancelOrderConfirmRequest = new CommonRequest(Api
                        .API_ORDER_CANCEL_CONFIRM, HttpWhat.HTTP_ORDER_CANCEL_CONFIRM.getValue(),
                        RequestMethod.POST);
                mCancelOrderConfirmRequest.add("type", "list");
                mCancelOrderConfirmRequest.add("reason", cancel_reason[0]);
                mCancelOrderConfirmRequest.add("id", finalOrder_id);
                mCancelOrderConfirmRequest.setAjax(true);
                addRequest(mCancelOrderConfirmRequest);
                mDialogDialog.dismiss();
            }
        });
        mDialogDialog.show();
    }

    private void refresh(String orderId) {

        if (!Utils.isNull(timerTask)) {
            timerTask.cancel();
        }
        String api = Api.API_FREEBUY_ORDER_INFO;
        if (buy_type == OrderListFreeFragment.TYPE_REACHBUY) {
            api = Api.API_REACHBUY_ORDER_INFO;
        }

        CommonRequest mOrderDetailRequest = new CommonRequest(api, HttpWhat
                .HTTP_ORDER_DETAIL.getValue());
        mOrderDetailRequest.add("id", orderId);
        addRequest(mOrderDetailRequest);
    }

    private void refreshCallBack(String response) {
        Model model = JSON.parseObject(response, Model.class);

        final OrderInfoModel order_info = model.data.order_info;
        GroupOnModel grouponModel = model.data.groupon_info;
        order_sn = order_info.order_sn;
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

        if (order_info.pay_status == Macro
                .PS_PAID) {
            QrcodeModel qrcodeModel = new QrcodeModel();
            qrcodeModel.order_sn = order_info.order_sn;
            qrcodeModel.table_num = order_info.reachbuy_code;
            mOrderDetailAdapter.data.add(qrcodeModel);
            mOrderDetailAdapter.data.add(dividerModel);
        } else {
            leftTime = 0;
            if (order_info.countdown > 0 && Utils.isNull(grouponModel.log_id)) {
                mOrderDetailAdapter.data.add(dividerModel);
                leftTime = order_info.countdown;
                OrderCountDownModel orderCountDownModel = new OrderCountDownModel();
                orderCountDownModel.count_down = leftTime;
                orderCountDownModel.order_status_code = order_info.order_status_code;
                mOrderDetailAdapter.data.add(orderCountDownModel);

                mOrderDetailAdapter.data.add(dividerModel);
            }
        }

        if (buy_type == OrderListFreeFragment.TYPE_PICKUP) {
            OrderAddressModel orderAddressModel = new OrderAddressModel();
            orderAddressModel.consignee = order_info.consignee;
            orderAddressModel.tel = order_info.tel;

            orderAddressModel.address = order_info.region_name + " " + order_info.address;

            mOrderDetailAdapter.data.add(orderAddressModel);
            mOrderDetailAdapter.data.add(dividerModel);
        }

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

        if (order_info.delivery_list != null) {

            int i = 1;
            for (DeliveryListModel deliveryListModel : order_info.delivery_list) {
//                TitleModel titleModel = new TitleModel();
//                titleModel.title = "包裹" + i;
//                titleModel.subTitle = deliveryListModel.base_info.shipping_name + "" +
//                        deliveryListModel.base_info.express_sn;

//                mOrderDetailAdapter.data.add(titleModel);
                for (GoodsModel goodsModel : deliveryListModel.goods_list) {
                    goodsModel.backAllow = showBackButton;
                    mOrderDetailAdapter.data.add(goodsModel);
                }
                i++;
                mOrderDetailAdapter.data.add(dividerModel);
            }
        }

        if (buy_type != OrderListFreeFragment.TYPE_PICKUP) {
            TotalModel totalModel = new TotalModel();
            totalModel.goods_amount = order_info.goods_amount_format;
            totalModel.shipping_fee = order_info.shipping_fee_format;
            totalModel.bonus = String.valueOf(order_info.bonus + order_info.shop_bonus);
            totalModel.surplus = order_info.surplus_format;
            totalModel.order_amount = order_info.surplus_format;
            totalModel.money_paid = order_info.money_paid_format;
            totalModel.pay_code = order_info.pay_code;
            totalModel.cash_more = order_info.cash_more;
            totalModel.pay_status = order_info.pay_status;
            totalModel.order_status_code = order_info.order_status_format;
            totalModel.pay_status_format = order_info.pay_status_format;
            totalModel.store_card_price = order_info.store_card_price;
            mOrderDetailAdapter.data.add(totalModel);
            mOrderDetailAdapter.data.add(dividerModel);
        }

        OrderInfoModel orderInfoModel = new OrderInfoModel();
        orderInfoModel = order_info;
        mOrderDetailAdapter.data.add(orderInfoModel);
        //按钮状态
        linearlayout_buttons.removeAllViews();

        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (buy_type != OrderListFreeFragment.TYPE_PICKUP) {
            if (!model.data.operate_text.equals("")) {
                if (model.data.operate_text.contains("等待商家审核取消订单申请")) {
                    order_info.buttons.remove("cancel_order");

                    layoutParams.setMargins(0, Utils.dpToPx(getActivity(), 10), Utils.dpToPx(getActivity(), 10), Utils.dpToPx(getActivity(), 10));

                } else {
                    layoutParams.setMargins(0, 0, Utils.dpToPx(getActivity(), 10), 0);
                }
                textView.setLayoutParams(layoutParams);
                textView.setText(model.data.operate_text);
                linearlayout_buttons.addView(textView);
            }
        } else {
            order_info.buttons = new ArrayList();
            order_info.buttons = new ArrayList();
            if(order_info.shipping_status == Macro.SS_SHIPPED || order_info.shipping_status == Macro.SS_SHIPPED_PART) {
                order_info.buttons.add(OrderButtonHelper.VIEW_LOGISTICS);
            }
            if(order_info.order_status == Macro.ORDER_CONFIRM && order_info.shipping_status == Macro.SS_SHIPPED) {
                order_info.buttons.add(OrderButtonHelper.CONFIRM_ORDER);
            }
        }

        List buttons = order_info.buttons;

        if (!Utils.isNull(buttons) && buttons.size() > 0) {
            mButtonLayout.setVisibility(View.VISIBLE);
            linearlayout_buttons.removeAllViews();

            Context context = getContext();

            final OrderInfoModel finalOrderInfoModel = orderInfoModel;
            OrderButtonHelper.initButtons(context, buttons, new OnOrderButtonListener(linearlayout_buttons) {
                public void setButtons(Button button) {
//                    String button_type = (String) button.getTag(OrderButtonHelper.TAG_BUTTON_TYPE);
                    super.setButtons(button);
                    Utils.setExtraInfoForTag(button, Integer.valueOf
                            (order_info.order_id));
                    button.setOnClickListener(OrderDetailFreeFragment.this);
                }

                @Override
                public void to_comment(Button button) {
                    super.to_comment(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            goComment(order_info.order_id, order_info.shop_id);
                        }
                    });
                }

                @Override
                public void add_comment(Button button) {
                    super.add_comment(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            goComment(order_info.order_id, order_info.shop_id);
                        }
                    });
                }

                @Override
                public void commented(Button button) {
                    super.commented(button);
                    button.setOnClickListener(null);
                }

                @Override
                public void ot_fight_group(Button button) {
                    super.ot_fight_group(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openGrouponActivity(finalOrderInfoModel.group_sn);
                        }
                    });
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
        mOrderDetailAdapter.notifyDataSetChanged();
    }

    private Button getButton(Context context, int i) {
        Button button = (Button) LayoutInflater.from(context).inflate(
                R.layout.item_order_list_button, null);

        button.setMinWidth(Utils.dpToPx(context, 90));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(context, 35));

        if (i > 0) {
            layoutParams.setMargins(Utils.dpToPx(context, 5), 0, 0, 0);
        }

        button.setLayoutParams(layoutParams);

        return button;

    }

    private void openGrouponActivity(String groupSn) {
        Intent intent = new Intent(getContext(), UserGroupOnDetailActivity.class);
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.putExtra(Key.KEY_BOOLEAN.getValue(), "1");
        startActivity(intent);
    }


    public void delOrder(String orderId) {
        CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_DELETE, HttpWhat
                .HTTP_ORDER_DELETE.getValue(), RequestMethod.POST);
        mCancelOrderRequest.add("type", "1");
        mCancelOrderRequest.add("order_id", orderId);
        mCancelOrderRequest.setAjax(true);
        addRequest(mCancelOrderRequest);
    }
}
