package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.ExpressActivity;
import com.szy.yishopcustomer.Activity.OrderDetailActivity;
import com.szy.yishopcustomer.Activity.OrderPayActivity;
import com.szy.yishopcustomer.Activity.OrderReviewActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.CancelOrderModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.DelayOrderModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderStatusModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2017/11/09
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderOperation extends YSCBaseFragment implements
        OnEmptyViewClickListener {

    //order_id
    private String orderId;
    //group_sn
    private String group_sn;
    private List<Object> data;
    //存储取消订单的理由
    CancelOrderModel cancelOrderModel;
    //存储延迟收货天数
    DelayOrderModel delayOrderModel;

    public void setGroup_sn(String group_sn) {
        this.group_sn = group_sn;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public void setCancelOrderModel(CancelOrderModel cancelOrderModel) {
        this.cancelOrderModel = cancelOrderModel;
    }

    public void setDelayOrderModel(DelayOrderModel delayOrderModel) {
        this.delayOrderModel = delayOrderModel;
    }


    //取消订单
    public void cancelOrder(String orderId) {
        if (cancelOrderModel != null) {
            //弹出取消订单Dialog
            dialog(cancelOrderModel);
        } else {
            //请求取消订单理由
            CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_CANCEL, HttpWhat
                    .HTTP_ORDER_CANCEL.getValue());
            mCancelOrderRequest.add("from", "list");
            mCancelOrderRequest.add("type", "cancel");
            mCancelOrderRequest.add("id", orderId);
            mCancelOrderRequest.setAjax(true);
            addRequest(mCancelOrderRequest);
        }
    }

    //延迟收货
    public void delayOrder(String orderId) {
        if (delayOrderModel != null) {
            //弹出延长收货订单Dialog

            if (delayOrderModel.code == 0) {
                dialogDelay(delayOrderModel);
            } else {
                Toast.makeText(getActivity(), delayOrderModel.message, Toast.LENGTH_SHORT).show();
            }

        } else {
            //请求取消订单理由
            CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_CANCEL, HttpWhat
                    .HTTP_ORDER_DELAY.getValue());
            mCancelOrderRequest.add("from", "list");
            mCancelOrderRequest.add("type", "delay");
            mCancelOrderRequest.add("id", orderId);
            mCancelOrderRequest.setAjax(true);
            addRequest(mCancelOrderRequest);
        }
    }

    //删除订单
    public void delOrder(String orderId) {
        CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_DELETE, HttpWhat
                .HTTP_ORDER_DELETE.getValue(), RequestMethod.POST);
        mCancelOrderRequest.add("type", "1");
        mCancelOrderRequest.add("order_id", orderId);
        mCancelOrderRequest.setAjax(true);
        addRequest(mCancelOrderRequest);
    }

    //删除订单回调
    public void delOrderCallback(String response) {
    }

    //去评价
    public void goComment(String orderId, String shopId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.putExtra("shop_id", shopId);
        intent.setClass(getActivity(), OrderReviewActivity.class);
        startActivity(intent);
    }

    //订单为空时，点击去购物回到首页
    public void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    //订单详情
    public void goOrderDetail(String orderId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.setClass(getActivity(), OrderDetailActivity.class);
        startActivity(intent);
    }

    //拼团详情
    public void openGrouponActivity(String groupSn) {
        Intent intent = new Intent(getContext(), UserGroupOnDetailActivity.class);
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.putExtra(Key.KEY_BOOLEAN.getValue(), "1");
        startActivity(intent);
    }

    //店铺首页
    private void openShopActivity(String shopId) {
        Intent intent = new Intent(getContext(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_ORDER_CONFIRM:
                confrimOrder(orderId);
                break;
            case VIEW_TYPE_CLEAR_CONFIRM:
                delOrder(orderId);
                break;
        }
    }

    @Override
    public void onEmptyViewClicked() {
        goIndex();
    }

    @Override
    public void onOfflineViewClicked() {
    }


    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_CANCEL:
                dialog(response);
                break;
            case HTTP_ORDER_DELAY:
                dialogDelay(response);
                break;
            case HTTP_ORDER_DELETE:
                delOrderCallback(response);
                break;
            case HTTP_ORDER_CANCEL_CONFIRM:
                cancelOrderCallback(response);
                break;
            case HTTP_ORDER_DELAY_CONFIRM:
                delayOrderCallback(response);
                break;
            case HTTP_ORDER_CONFIRM:
                confirmOrderCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_ORDER_GOODS:
                goOrderDetail(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_CANCEL_ORDER:
                cancelOrder(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_DELAY_ORDER:
                delayOrder(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_DEL_ORDER:
                showConfirmDialog(R.string.alertDeleteOrder, ViewType
                        .VIEW_TYPE_CLEAR_CONFIRM.ordinal());
                orderId = String.valueOf(extraInfo);
                break;
            case VIEW_TYPE_VIEW_LOGISTICS:
                viewExpress(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_COMMENT:
                break;
            case VIEW_TYPE_PAYMENT:
                if (data.get(position) instanceof OrderStatusModel) {
                    OrderStatusModel orderStatusModel = (OrderStatusModel) data.get(position);
                    openPayActivity(orderStatusModel.order_sn, String.valueOf(extraInfo));
                } else {
                    OrderInfoModel orderInfoModel = (OrderInfoModel) data.get(data.size() - 1);
                    openPayActivity(orderInfoModel.order_sn, String.valueOf(extraInfo));
                }
                break;
            case VIEW_TYPE_AWAIT_CONFIRM:
                orderId = String.valueOf(extraInfo);
                showConfirmDialog(R.string.confrimOrderTip, ViewType
                        .VIEW_TYPE_ORDER_CONFIRM.ordinal());
                break;
            case VIEW_TYPE_SHOP:
                openShopActivity(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_INVITE_FRIEND:
                openGrouponActivity(group_sn);
                break;
            default:
                super.onClick(v);
        }
    }

    //去支付
    public void openPayActivity(String orderSn, String orderId) {
        Intent intent = new Intent(getContext(), OrderPayActivity.class);
        intent.putExtra(Key.KEY_ORDER_SN.getValue(), orderSn);
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        startActivity(intent);
    }

    //查看物流
    public void viewExpress(String orderId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.setClass(getActivity(), ExpressActivity.class);
        startActivity(intent);
    }

    //确认收货
    private void confrimOrder(String orderId) {
        CommonRequest mOrderConfirmRequest = new CommonRequest(Api.API_ORDER_CONFIRM, HttpWhat
                .HTTP_ORDER_CONFIRM.getValue(), RequestMethod.POST);
        mOrderConfirmRequest.add("id", orderId);
        addRequest(mOrderConfirmRequest);
    }

    //确认收货回调
    public void confirmOrderCallback(String response) {

    }

    //弹出取消订单理由
    private void dialogDelay(String response) {

        DelayOrderModel model = JSON.parseObject(response, DelayOrderModel.class);
        delayOrderModel = model;

        if (delayOrderModel.code == 0) {
            dialogDelay(delayOrderModel);
        } else {
            Toast.makeText(getActivity(), delayOrderModel.message, Toast.LENGTH_SHORT).show();
        }
    }

    //延迟收货天数Dialog
    private void dialogDelay(DelayOrderModel response) {
        DelayOrderModel model = response;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        final ArrayList<String> data = new ArrayList<String>();

        if (model.code == 0) {
            for (int i = 0; i < model.data.delay_days_array.size(); i++) {
                data.add(model.data.delay_days_array.get(i).toString());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id.item_cancel_order_textView, data);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id.order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);

        final String[] cancel_reason = {""};
        cancel_reason[0] = data.get(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cancel_reason[0] = data.get(position);
            }
        });

        final String finalOrder_id = model.data.order_id;

        TextView title = (TextView) cancelOrderDialogView.findViewById(R.id.dialog_cancel_order_textView);
        title.setText("延长确认收货时间");

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
                        .API_ORDER_DELAY, HttpWhat.HTTP_ORDER_DELAY_CONFIRM.getValue(),
                        RequestMethod.POST);
                mCancelOrderConfirmRequest.add("delay_days", cancel_reason[0]);
                mCancelOrderConfirmRequest.add("id", finalOrder_id);
                mCancelOrderConfirmRequest.setAjax(true);
                addRequest(mCancelOrderConfirmRequest);
                mDialogDialog.dismiss();
            }
        });
        mDialogDialog.show();
    }

    //弹出取消订单理由
    private void dialog(String response) {
        CancelOrderModel model = JSON.parseObject(response, CancelOrderModel.class);
        cancelOrderModel = model;
        dialog(cancelOrderModel);
    }

    //取消订单Dialog
    private void dialog(CancelOrderModel response) {
        CancelOrderModel model = response;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        final ArrayList<String> data = new ArrayList<String>();

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

    public void cancelOrderCallback(String response) {
    }

    public void delayOrderCallback(String response) {

    }
}
