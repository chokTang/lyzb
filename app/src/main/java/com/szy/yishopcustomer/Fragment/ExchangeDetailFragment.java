package com.szy.yishopcustomer.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.ExpressActivity;
import com.szy.yishopcustomer.Activity.GoodsIntegralActivity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.OrderReviewActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnOrderButtonListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.DeliveryListModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.Model;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.CancelOrderModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExchangeDetailFragment extends YSCBaseFragment {
    //用户订单状态
    @BindView(R.id.trading_status)
    TextView trading_status;
    //用户地址
    @BindView(R.id.fragment_order_detail_consignee)
    TextView fragment_order_detail_consignee;
    @BindView(R.id.fragment_order_detail_tel)
    TextView fragment_order_detail_tel;
    @BindView(R.id.fragment_order_detail_address)
    TextView fragment_order_detail_address;

    @BindView(R.id.textView_exchange_order)
    TextView textView_exchange_order;
    @BindView(R.id.textView_add_time)
    TextView textView_add_time;
    @BindView(R.id.textView_required_points)
    TextView textView_required_points;

    @BindView(R.id.fragment_checkout_goods_numberTextView)
    TextView fragment_checkout_goods_numberTextView;
    @BindView(R.id.fragment_order_list_goods_number)
    TextView fragment_order_list_goods_number;
    @BindView(R.id.item_order_list_goods_attribute_textView)
    TextView item_order_list_goods_attribute_textView;
    @BindView(R.id.item_order_list_goods_name_textView)
    TextView item_order_list_goods_name_textView;
    @BindView(R.id.textView_commodity_delivery_status)
    TextView textView_commodity_delivery_status;
    @BindView(R.id.item_order_list_goods_imageView)
    ImageView item_order_list_goods_imageView;

    @BindView(R.id.textView_over_time)
    TextView textView_over_time;

    @BindView(R.id.tv_postscript)
    TextView tv_postscript;
    @BindView(R.id.tv_best_time)
    TextView tv_best_time;

    @BindView(R.id.ll_shipping_time)
    View ll_shipping_time;
    @BindView(R.id.tv_shipping_time)
    TextView tv_shipping_time;

    @BindView(R.id.linearlayout_add_time)
    View linearlayout_add_time;
    @BindView(R.id.linearlayout_over_time)
    View linearlayout_over_time;

    @BindView(R.id.linearlayout_buttons)
    LinearLayout linearlayout_buttons;

    @BindView(R.id.item_order_list)
    View item_order_list;
    @BindView(R.id.imageView_qrcode)
    ImageView imageView_qrcode;
    @BindView(R.id.textView_order_sn)
    TextView textView_order_sn;

    private String skuid = "";
    private String order_id;

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
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_BACK_DETAIL:
                refresh(order_id);
                break;
        }
    }

//    private void openRefundActivity(String orderId, String goodsId, String skuId) {
//        Intent intent = new Intent();
//        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
//        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
//        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
//        intent.setClass(getActivity(), BackApplyActivity.class);
//        startActivity(intent);
//    }

//    private void openBackDetailActivity(int backId) {
//        Intent intent = new Intent();
//        intent.putExtra(Key.KEY_BACK_ID.getValue(), backId + "");
//        intent.setClass(getActivity(), BackDetailActivity.class);
//        startActivity(intent);
//    }

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
        Intent intent = getActivity().getIntent();
        order_id = intent.getStringExtra(Key.KEY_ORDER_ID.getValue());
        refresh(order_id);

        item_order_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoodsIntegralActivity.class);
                intent.putExtra(Key.KEY_EXCHANGE.getValue(), true);
                intent.putExtra(Key.KEY_SKU_ID.getValue(), skuid);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
//                    mOrderDetailAdapter.data.clear();
                    refresh(order_id);
                } else {
                    Utils.makeToast(getActivity(), responseModel.message);
                }
                break;
            case HTTP_ORDER_CANCEL:
                dialog(response);
                break;
            case HTTP_ORDER_CANCEL_CONFIRM:
                ResponseCommonModel orderConfirmModel = JSON.parseObject(response,
                        ResponseCommonModel.class);
                if (orderConfirmModel.code == 0) {
                    Utils.makeToast(getActivity(), orderConfirmModel.message);
                    refresh(order_id);
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                            .getValue()));
                } else {
                    Utils.makeToast(getActivity(), orderConfirmModel.message);
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_exchange_detail;
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), GoodsIntegralActivity.class);
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
        CommonRequest mOrderDetailRequest = new CommonRequest(Api.API_USER_INTEGRAL_ORDER_INFO, HttpWhat
                .HTTP_ORDER_DETAIL.getValue());
        mOrderDetailRequest.add("id", orderId);
        mOrderDetailRequest.add("is_exchange", 1);
        addRequest(mOrderDetailRequest);
    }


    OrderInfoModel order_info;

    private void refreshCallBack(String response) {
        Model model = JSON.parseObject(response, Model.class);


        order_info = model.data.order_info;

        imageView_qrcode.setImageBitmap(CodeUtils.createImage(order_info.order_sn, 400, 400, null));
        textView_order_sn.setText("兑换单号：" + order_info.order_sn);

        //设置数据
        trading_status.setText(order_info.order_status_format);
        fragment_order_detail_consignee.setText(order_info.consignee);
        fragment_order_detail_tel.setText(order_info.tel);
        fragment_order_detail_address.setText(order_info.region_name + " " + order_info.address);

        textView_exchange_order.setText(order_info.order_sn);

        textView_add_time.setText(Utils.times(order_info.add_time, "yyyy-MM-dd HH:mm:ss"));
        textView_required_points.setText(order_info.order_points + "积分");

        if (order_info.goods_list != null && order_info.goods_list.size() > 0) {
            OrderInfoModel.GoodsListBean good = order_info.goods_list.get(0);
            fragment_checkout_goods_numberTextView.setText(good.goods_integral + "积分");
            item_order_list_goods_attribute_textView.setText("");
            item_order_list_goods_name_textView.setText(good.goods_name);
            ImageLoader.displayImage(Utils.urlOfImage(good.goods_image), item_order_list_goods_imageView);
            fragment_order_list_goods_number.setText("x" + good.delivery_number);

        } else if (order_info.delivery_list != null && order_info.delivery_list.size() > 0) {
            DeliveryListModel good = order_info.delivery_list.get(0);
            fragment_checkout_goods_numberTextView.setText(good.goods_list.get(0).goods_points + "积分");
            item_order_list_goods_attribute_textView.setText(good.goods_list.get(0).spec_info);
            item_order_list_goods_name_textView.setText(good.goods_list.get(0).goods_name);
            ImageLoader.displayImage(Utils.urlOfImage(good.goods_list.get(0).goods_image), item_order_list_goods_imageView);
            fragment_order_list_goods_number.setText("x" + good.goods_list.get(0).goods_number);

        }

        String cancelTip = "";
//        if ((order_info.order_status == Macro
//                .ORDER_CONFIRM || order_info.order_status == Macro.ORDER_SCRAMBLE) && order_info.shipping_status == Macro.SS_UNSHIPPED) {
//
//            if (order_info.order_cancel == 0 || order_info.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
//                if (order_info.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
//                    cancelTip = "商家拒绝取消订单申请";
//                }
//            } else if (order_info.order_cancel == Macro.ORDER_CANCEL_APPLY) {
//                cancelTip = "商家审核取消订单申请";
//                order_info.buttons.remove("cancel_order");
//            }
//        }

        order_info.buttons = new ArrayList<>();
        if (!TextUtils.isEmpty(order_info.pickup_id) && !"0".equals(order_info.pickup_id)) {
            order_info.buttons.add(OrderButtonHelper.VIEW_PICKLIST);
        } else if (order_info.shipping_status == Macro.SS_SHIPPED) {
            order_info.buttons.add(OrderButtonHelper.VIEW_LOGISTICS);
        }

        //按钮部分
        linearlayout_buttons.setVisibility(View.VISIBLE);
        linearlayout_buttons.removeAllViews();
        Context context = getContext();

        TextView tv = (TextView) LayoutInflater.from(context).inflate(
                R.layout.item_order_list_textview, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(Color.parseColor("#999999"));
        tv.setText(cancelTip);
        tv.setPadding(0, 0, Utils.dpToPx(context, 5), 0);
        tv.setGravity(Gravity.RIGHT);

        linearlayout_buttons.addView(tv);

        //按钮状态
        if (order_info.buttons.size() > 0) {
            OrderButtonHelper.initButtons(context, order_info.buttons, new OnOrderButtonListener(linearlayout_buttons) {
                @Override
                public void setButtons(Button button) {
//                    String button_type = (String) button.getTag(OrderButtonHelper.TAG_BUTTON_TYPE);
                    super.setButtons(button);
                    button.setOnClickListener(onClickListener);
                }

                @Override
                public void commented(Button button) {
                    button.setEnabled(false);
                    linearlayout_buttons.addView(button);
                }
            });

        } else if (TextUtils.isEmpty(cancelTip)) {
            linearlayout_buttons.setVisibility(View.GONE);
        }

        if (("0").equals(order_info.end_time)) {
            linearlayout_over_time.setVisibility(View.GONE);
            linearlayout_buttons.setVisibility(View.VISIBLE);
        } else {
            textView_over_time.setText(Utils.times(order_info.end_time, "yyyy-MM-dd HH:mm:ss"));
            linearlayout_over_time.setVisibility(View.VISIBLE);
            linearlayout_buttons.setVisibility(View.GONE);
        }

        //买家留言
        if("".equals(order_info.postscript)){
            tv_postscript.setText("-");
        }else {
            tv_postscript.setText(order_info.postscript);
        }
        //送货时间
        tv_best_time.setText(order_info.best_time);

        //发货时间
        if (("0").equals(order_info.shipping_time)) {
            ll_shipping_time.setVisibility(View.GONE);
        } else {
            ll_shipping_time.setVisibility(View.VISIBLE);
            tv_shipping_time.setText(Utils.times(order_info.shipping_time, "yyyy-MM-dd HH:mm:ss"));
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (Utils.isDoubleClick()) {
                return;
            }
            ViewType viewType = Utils.getViewTypeOfTag(view);
            String extraInfo = order_id;
            switch (viewType) {
                case VIEW_TYPE_CANCEL_ORDER:
                    cancelOrder(String.valueOf(extraInfo));
                    break;
                case VIEW_TYPE_AWAIT_CONFIRM:
                    order_id = String.valueOf(extraInfo);
                    showConfirmDialog(R.string.confrimOrderTip, ViewType
                            .VIEW_TYPE_ORDER_CONFIRM.ordinal());
                    break;
                case VIEW_TYPE_DEL_ORDER:
                    order_id = String.valueOf(extraInfo);
                    showConfirmDialog(R.string.msgDelOrder, ViewType.VIEW_TYPE_DEL_ORDER
                            .ordinal());
                    break;
                case VIEW_TYPE_COMMENT:
                    goComment(String.valueOf(extraInfo), order_info.shop_id);
                    break;
                case VIEW_TYPE_VIEW_LOGISTICS:
                    viewExpress(String.valueOf(extraInfo));
                    break;
                case VIEW_TYPE_PICK_UP:
                    openMapActivity();
                    break;
            }
        }
    };

    private void openMapActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MapActivity.class);
        intent.putExtra(Key.KEY_MARKER_NAME.getValue(), order_info.pickup.pickup_name);
        intent.putExtra(Key.KEY_MARKER_SNIPPET.getValue(), order_info.pickup.pickup_address);
        intent.putExtra(Key.KEY_LATITUDE.getValue(), order_info.pickup.address_lat);
        intent.putExtra(Key.KEY_LONGITUDE.getValue(), order_info.pickup.address_lng);
        intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
        intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
        intent.putExtra(Key.KEY_TITLE.getValue(), order_info.pickup.pickup_name);
        startActivity(intent);
    }

    public void viewExpress(String orderId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.setClass(getActivity(), ExpressActivity.class);
        startActivity(intent);
    }

    private void openGrouponActivity(String groupSn) {
        Intent intent = new Intent(getContext(), UserGroupOnDetailActivity.class);
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.putExtra(Key.KEY_BOOLEAN.getValue(), "1");
        startActivity(intent);
    }
}
