package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.BackApplyActivity;
import com.szy.yishopcustomer.Activity.DeliveryAddressActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Adapter.BackDetailAdapter;
import com.szy.yishopcustomer.Adapter.GoodsCommentImageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BackDetailModel.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailTitleModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailTitleTwoModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailValueImageModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailValueModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 退款详情
 * 退款申请碎片
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BackDetailFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_back_detail_RecyclerView)
    CommonRecyclerView mRecyclerView;
    private BackDetailAdapter adapter;
    private ArrayList<Object> list;
    private TimerTask timerTask;
    private long time = -1;
    private int type = -1;
    private String id;
    private String backType; //jd_take_type  4-jd快递上门取件  40-用户自己发货
    private Model model;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            time -= 1;
            if (time <= 0) {
                timerTask.cancel();

                BackDetailTitleModel mBackDetailTitleMode = (BackDetailTitleModel) adapter.data.get
                        (0);
                if (type == 0) {
                    mBackDetailTitleMode.nameThree = "如果卖家未处理: 将于" + "已超时" + "后自动达成";
                    timeOutHandleConfirmSys();
                } else if (type == 1) {
                    mBackDetailTitleMode.nameOne = "退货时间: 超过" + "已超时" + "将自动取消";
                    timeOutHandleCancelSys();
                } else if (type == 2) {
                    mBackDetailTitleMode.nameOne = "如果卖家未确认收货: 超过" + "已超时" +
                            "后将自动收货，将退款信息推送到平台方";
                    timeOutHandleShippedSys();
                } else if (type == 3) {
                    /*mBackDetailTitleMode.nameOne = "请尽快将货物寄出，如您" + Utils.Day(time) +
                            "内未填写退货物流单，系统将自动撤销您的退货退款申请。如有疑问，请联系卖家协商处理";*/
                } else if (type == 5) {
                    mBackDetailTitleMode.nameTwo = "您可以修改退款申请后再次发起，卖家会重新处理 如果" + "已超时" +
                            "内您未处理，退款申请将自动取消";
                    timeOutHandleCancelSys();
                }
                adapter.notifyDataSetChanged();


            } else {
                BackDetailTitleModel mBackDetailTitleMode = (BackDetailTitleModel) adapter.data.get
                        (0);
                if (type == 0) {
                    mBackDetailTitleMode.nameThree = "如果卖家未处理: 将于" + Utils.Day(time) + "后自动达成";
                } else if (type == 1) {
                    mBackDetailTitleMode.nameOne = "退货时间: 超过" + Utils.Day(time) + "将自动取消";
                } else if (type == 2) {
                    mBackDetailTitleMode.nameOne = "如果卖家未确认收货: 超过" + Utils.Day(time) +
                            "后将自动收货，将退款信息推送到平台方";
                } else if (type == 3) {
                    /*mBackDetailTitleMode.nameOne = "请尽快将货物寄出，如您" + Utils.Day(time) +
                            "内未填写退货物流单，系统将自动撤销您的退货退款申请。如有疑问，请联系卖家协商处理";*/
                } else if (type == 5) {
                    mBackDetailTitleMode.nameTwo = "您可以修改退款申请后再次发起，卖家会重新处理如果" + Utils.Day(time) +
                            "内您尚未处理，退款申请将自动取消";
                }
                adapter.notifyDataSetChanged();
            }
        }

    };

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_BACK_DETAIL:
                refresh();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int info = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_GOODS_COMMENT_IMAGE:
                BackDetailValueImageModel goodsCommentDescModel = (BackDetailValueImageModel)
                        adapter.data.get(info);
                viewOriginalImage(goodsCommentDescModel.url, position);
                break;
            case VIEW_TYPE_BACK_DETAIL_BUTTON_ONE:
                if (info == 0) {
                    //修改退款申请
                    openBackApplyActivity("1");
                } else if (info == 1) {
                    //填写货单
                    openDeliveryAddressActivity();
                } else if (info == 2) {
                    //修改物流单
                    openDeliveryAddressActivity();
                } else if (info == 3) {
                    //修改物流单
                    openBackApplyActivity("1");
                } else if (info == 5) {
                    //修改退款申请
                    openBackApplyActivity("1");
                } else if (info == 6) {
                    //再次申请退款
                    openBackApplyActivity(null);
                } else if (info == 7) {
                    //再次申请退款
                    openBackApplyActivity(null);
                }
                break;
            case VIEW_TYPE_BACK_DETAIL_BUTTON_TWO:
                if (info == 0) {
                    refreshCancel();
                } else if (info == 1) {
                    //撤销退货
                    refreshCancel();
                } else if (info == 2) {
                    //修改物流单
                    openBackApplyActivity("1");
                } else if (info == 3) {
                    //撤销退款
                    refreshCancel();
                } else if (info == 5) {
                    //修改退款申请
                    openBackApplyActivity("1");
                } else if (info == 7) {
                    //再次申请退款
                    openBackApplyActivity(null);
                }
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    public void viewOriginalImage(ArrayList data, int selectedIndex) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), data);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), selectedIndex);
        startActivity(intent);
    }

    /**
     * 填写退货物流单
     */
    private void openDeliveryAddressActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_BACK_ID.getValue(), id);
        intent.putExtra(Key.KEY_BACK_TYPE.getValue(), backType);
        intent.setClass(getActivity(), DeliveryAddressActivity.class);
        startActivity(intent);
    }

    private void openBackApplyActivity(String again) {
        Intent intent = new Intent();
        if (again != null) {
            intent.putExtra(Key.KEY_BACK_ID.getValue(), id);
        } else {
            intent.putExtra(Key.KEY_ORDER_ID.getValue(), model.data.back_info.order_id);
            intent.putExtra(Key.KEY_GOODS_ID.getValue(), model.data.back_info.goods_id);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), model.data.back_info.sku_id);
            intent.putExtra(Key.KEY_RECORD_ID.getValue(), model.data.back_info.record_id);
        }
        intent.setClass(getActivity(), BackApplyActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_back_detail;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra(Key.KEY_BACK_ID.getValue());
        list = new ArrayList<Object>();
        adapter = new BackDetailAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        final int topMargin = Utils.dpToPx(getContext(), 10);
        final int bottomMargin = Utils.dpToPx(getContext(), 40);
        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                final int itemPosition = parent.getChildAdapterPosition(view);
                if (itemPosition == 1 || itemPosition == 2) {
                    outRect.top = topMargin;
                } else if (itemPosition == 10) {
                    outRect.bottom = bottomMargin;
                }

            }
        };
        mRecyclerView.addItemDecoration(mItemDecoration);
        BackDetailAdapter.onClickListener = this;
        GoodsCommentImageAdapter.onClickListener = this;
        refresh();
        return v;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_BACK_DETAIL_BACK_INFO, HttpWhat
                .HTTP_DETAIL.getValue());
        request.alarm = true;
        request.add("id", id);
        addRequest(request);
    }

    public void timeOutHandleConfirmSys() {
        CommonRequest request = new CommonRequest(Api.API_BACK_CONFIRMSYS, HttpWhat.HTTP_WHAT_BACK_TIMEOUT.getValue());
        request.add("back_id", id);
        addRequest(request);
    }

    public void timeOutHandleCancelSys() {
        CommonRequest request = new CommonRequest(Api.API_BACK_CANCELSYS, HttpWhat.HTTP_WHAT_BACK_TIMEOUT.getValue());
        request.add("back_id", id);
        addRequest(request);
    }

    public void timeOutHandleShippedSys() {
        CommonRequest request = new CommonRequest(Api.API_BACK_SHIPPEDSYS, HttpWhat.HTTP_WHAT_BACK_TIMEOUT.getValue());
        request.add("back_id", id);
        addRequest(request);
    }

    private void refreshCancel() {
      /*  CommonRequest request = new CommonRequest(Utils.urlOfWap() + "user/back/cancel", HttpWhat
                .HTTP_BACK_DETAIL_CANCEL.getValue(), RequestMethod.POST); */
        CommonRequest request = new CommonRequest(Api.API_USER_BACK_CANCEL, HttpWhat
                .HTTP_BACK_DETAIL_CANCEL.getValue(), RequestMethod.POST);
        request.add("id", id);
        request.add("send_id", "1");
        addRequest(request);
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_DETAIL:
                if (!Utils.isNull(list)) {
                    if (timerTask != null) {
                        timerTask.cancel();
                    }
                    list.clear();
                }

                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        model = back;
                        list.add(getTitleMode(model));
                        list.add(getTitleTwoModel());
                        list.add(getShopNameModel(model));
                        list.add(getOrderSnModel(model));
                        list.add(getBackTypeModel(model));
                        list.add(getRefundMoneyModel(model));
                        if ("1".equals(model.data.back_info.refund_freight_type)){
                            list.add(getRefundFreightMoneyModel(model));
                        }
                        list.add(getRefundReasonModel(model));
                        list.add(getRefundStyleModel(model));
                        list.add(getBackSnModel(model));
                        list.add(getRequestTimeModel(model));
                        list.add(getBackDescModel(model));
                        adapter.setData(list);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case HTTP_BACK_DETAIL_CANCEL:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        Utils.toastUtil.showToast(getActivity(), back.message);
                        refresh();
                    }
                }, true);
                break;
            case HTTP_WHAT_BACK_TIMEOUT:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        Utils.toastUtil.showToast(getActivity(), back.message);
                        refresh();
                    }
                }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private BackDetailValueModel getRefundStyleModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "退款方式";
        if (model.data.back_info.refund_type.equals("0")) {
            valueModel.value = "退回用户余额";
        } else {
            valueModel.value = "退回原支付方式";
        }
        return valueModel;
    }

    private BackDetailValueModel getBackSnModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "退款编号";
        valueModel.value = model.data.back_info.back_sn;
        return valueModel;
    }

    private BackDetailValueModel getOrderSnModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "订单编号";
        valueModel.value = model.data.order_info.order_sn;
        return valueModel;
    }

    private BackDetailValueModel getRefundReasonModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "退款原因";
        valueModel.value = model.data.back_info.back_reason_format;
        return valueModel;
    }

    private BackDetailValueImageModel getBackDescModel(Model model) {
        BackDetailValueImageModel valueModel = new BackDetailValueImageModel();
        valueModel.name = "退款说明";
        valueModel.value = model.data.back_info.back_desc;
        ArrayList list = new ArrayList<String>();
        if (!Utils.isNull(model.data.back_info.back_img1)) {
            list.add(model.data.back_info.back_img1);
        }
        if (!Utils.isNull(model.data.back_info.back_img2)) {
            list.add(model.data.back_info.back_img2);
        }
        if (!Utils.isNull(model.data.back_info.back_img3)) {
            list.add(model.data.back_info.back_img3);
        }
        valueModel.url = list;
        return valueModel;
    }

    private BackDetailValueModel getRequestTimeModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "申请时间";
        valueModel.value = Utils.times(model.data.back_info.add_time);
        return valueModel;
    }

    private BackDetailValueModel getRefundMoneyModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "退款商品金额";
        valueModel.color = ContextCompat.getColor(getContext(), R.color.colorBlue);
        valueModel.value = Utils.formatMoney(getContext(), model.data.back_info.refund_money);
        return valueModel;
    }

    private BackDetailValueModel getRefundFreightMoneyModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "退款运费金额";
        valueModel.color = ContextCompat.getColor(getContext(), R.color.colorBlue);
        valueModel.value = Utils.formatMoney(getContext(), model.data.back_info.refund_freight);
        return valueModel;
    }

    private BackDetailValueModel getShopNameModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "店铺名称";
        valueModel.value = model.data.shop_info.shop.shop_name;
        return valueModel;
    }

    private BackDetailTitleTwoModel getTitleTwoModel() {
        BackDetailTitleTwoModel valueModel = new BackDetailTitleTwoModel();
        valueModel.title = "退款申请";
        return valueModel;
    }

    private BackDetailValueModel getBackTypeModel(Model model) {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "退款类型";
        valueModel.color = ContextCompat.getColor(getContext(), R.color.colorBlue);
        if (model.data.back_info.back_type.equals("0")) {
            valueModel.value = "预留，作为无状态时用";
        } else if (model.data.back_info.back_type.equals("1")) {
            valueModel.value = "退款不退货";
        } else if (model.data.back_info.back_type.equals("2")) {
            valueModel.value = "退款退货";
        } else if (model.data.back_info.back_type.equals("3")) {
            valueModel.value = "换货";
        } else if (model.data.back_info.back_type.equals("4")) {
            valueModel.value = "申请维修";
        }
        return valueModel;
    }

    private BackDetailTitleModel getTitleMode(Model model) {
        BackDetailTitleModel titleModel = new BackDetailTitleModel();
        String backStatus = model.data.back_info.back_status;
        if (backStatus.equals("0")) {
            time = model.data.back_info.disabled_time - model.data.context.current_time;
            type = 0;
            titleModel.title = "等待卖家处理退款申请";
            titleModel.nameOne = "如果商家同意: 申请将达成并退款";
            titleModel.nameTwo = "如果商家拒绝: 可与卖家协商修改退款申请，若协商不成可申请平台方介入";
            titleModel.nameThree = "如果卖家未处理: 超过" + Utils.Day(time) + "退款申请将自动达成";
            titleModel.textOne = "修改退款申请";
            titleModel.textTwo = "撤销退款申请";
            titleModel.buttonType = type;

        } else if (backStatus.equals("1")) {
            //back_type = 2为退货申请，back_type=1为退款申请
            if (model.data.back_info.back_type.equals("2")) {
                time = model.data.back_info.disabled_time - model.data.context.current_time;
                type = 1;
                titleModel.title = "卖家同意，请退货给卖家";
                titleModel.nameOne = "退货时间: 剩余" + Utils.Day(time) + "逾期未退货申请将自动取消";
                titleModel.nameTwo = "退货地址:" + model.data.back_info.address_info.region_names +
                        "" + " " + model.data.back_info.address_info.address_detail + "," + model
                        .data.back_info.address_info.consignee + model.data.back_info
                        .address_info.mobile;
                titleModel.nameThree = "";
                backType = model.data.back_info.jd_take_type;
                if (model.data.back_info.jd_take_type.equals("4")){//上门取件
                    titleModel.textOne = "请确认";
                }else {//40用户自己发货
                    titleModel.textOne = "填写退货物流单";
                }
                titleModel.textTwo = "撤销退款退货申请";
                titleModel.buttonType = type;
            } else {
                type = 1;
                titleModel.title = "卖家同意，请等待平台方退款";
                titleModel.nameOne = "如有疑问，请联系平台客服人员";
                titleModel.nameTwo = "";
                titleModel.nameThree = "";
                titleModel.textOne = "";
                titleModel.textTwo = "撤销退款退货申请";
                titleModel.buttonType = type;
            }
        } else if (backStatus.equals("2")) {
            time = model.data.back_info.disabled_time - model.data.context.current_time;
            type = 2;
            titleModel.title = "买家已退货，等待卖家确认收货";
            titleModel.nameOne = "如果卖家未确认收货:超过" + Utils.Day(time) + "后将自动收货，将退款信息推送到平台方";
            titleModel.nameTwo = "";
            titleModel.nameThree = "";
            if (TextUtils.isEmpty(model.data.back_info.jd_sku_id)){//非京东
                titleModel.textOne = "修改退货物流单";
            }else {//京东
                titleModel.textOne = "";
            }
            titleModel.textTwo = "";
            titleModel.buttonType = type;
        } else if (backStatus.equals("3")) {
            titleModel.title = "卖家已同意，请等待平台方退款";
            time = model.data.back_info.disabled_time - model.data.context.current_time;
            type = 3;
            titleModel.nameOne = "如有疑问，请联系平台客服";
            titleModel.nameTwo = "";
            titleModel.nameThree = "";
            titleModel.textOne = "撤销退款申请";
            titleModel.textTwo = "";
            titleModel.buttonType = type;
            /*if (time < 0) {
                titleModel.nameOne = "如有疑问，请联系平台客服";
                titleModel.nameTwo = "";
                titleModel.nameThree = "";
                titleModel.textOne = "撤销退款申请";
                titleModel.textTwo = "";
                titleModel.buttonType = type;

            } else {
                titleModel.nameOne = "请尽快将货物寄出，如您" + Utils.Day(time) +
                        "内未填写退货物流单，系统将自动撤销您的退货退款申请。如有疑问，请联系卖家协商处理";
                titleModel.nameTwo = "退款收货人:";
                titleModel.nameThree = "";
                titleModel.textOne = "撤销退款申请";
                titleModel.textTwo = "填写退货物流单";
                titleModel.buttonType = type;
            }*/

        } else if (backStatus.equals("4")) {
            time = -1;
            type = 4;
            titleModel.title = "退款成功";
            titleModel.nameOne = "退款时间:" + Utils.times(model.data.back_info.disabled_time + "");
            if(!TextUtils.isEmpty(model.data.back_info.refund_money)){
                titleModel.nameTwo = "退款总金额:¥" + model.data.back_info.refund_money;
                if(!TextUtils.isEmpty(model.data.back_info.refund_freight)){
                    titleModel.nameTwo = "退款总金额:¥" + (Double.parseDouble(model.data.back_info.refund_money)+Double.parseDouble(model.data.back_info.refund_freight));
                }
            }
            titleModel.nameThree = "   ";
            titleModel.textOne = "";
            titleModel.textTwo = "";
            titleModel.buttonType = type;
        } else if (backStatus.equals("5")) {
            titleModel.title = "卖家已拒绝您的退款申请";
            time = model.data.back_info.disabled_time - model.data.context.current_time;
            type = 5;
            titleModel.nameOne = "";
            titleModel.nameTwo = "您可以修改退款申请后再次发起，卖家会重新处理 如果" + Utils.Day(time) + "内您未处理，退款申请将自动取消";
            titleModel.nameThree = "卖家拒绝理由：" + model.data.back_info.seller_desc;
            titleModel.textOne = "修改退款申请";
            titleModel.textTwo = "";
            titleModel.buttonType = type;
        } else if (backStatus.equals("6")) {
            //状态6代表失效，按照pc端显示撤销
            titleModel.title = "退款申请已被撤销 ";
            time = model.data.back_info.disabled_time - model.data.context.current_time;
            type = 6;
            titleModel.nameOne = "撤销时间:" + Utils.times(model.data.back_info.disabled_time + "");
            titleModel.nameTwo = "";
            titleModel.nameThree = "";
            titleModel.textOne = "再次申请退款";
            titleModel.textTwo = "";
            titleModel.buttonType = type;
        } else if (backStatus.equals("7")) {
            time = -1;
            type = 7;
            titleModel.title = "已撤销退款申请";
            titleModel.nameOne = "撤销时间:" + Utils.times(model.data.back_info.disabled_time + "");
            titleModel.nameTwo = "";
            titleModel.nameThree = "";
            titleModel.textOne = "再次申请退款";
            titleModel.textTwo = "";
            titleModel.buttonType = type;
        }
        if (time > 0) {
            Timer timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }

        return titleModel;
    }

}
