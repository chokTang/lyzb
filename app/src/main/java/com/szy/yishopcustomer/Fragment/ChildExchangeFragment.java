package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ExchangeDetailActivity;
import com.szy.yishopcustomer.Activity.ExpressActivity;
import com.szy.yishopcustomer.Activity.IntegralMallActivity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.OrderReviewActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.ExchangeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.OrderList.CancelOrderModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ExchangeModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildExchangeFragment extends YSCBaseFragment implements OnPullListener {

    @BindView(R.id.fragment_pullableLayout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;

    @BindView(R.id.relativeLayout_empty)
    View relativeLayout_empty;
    @BindView(R.id.empty_view_button)
    Button empty_view_button;
    @BindView(R.id.empty_view_imageView)
    ImageView empty_view_imageView;
    @BindView(R.id.empty_view_titleTextView)
    TextView empty_view_titleTextView;
    @BindView(R.id.empty_view_subtitleTextView)
    TextView empty_view_subtitleTextView;

    @BindView(R.id.fragment_order_list_search_input)
    EditText mOrderListSearchInput;
    @BindView(R.id.fragment_order_list_search_imageView)
    ImageView mOrderListSearchButton;

    private ExchangeAdapter mAdapter;
    private ExchangeModel data;

    private String name = "";
    private String order_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_child_exchange;

        mAdapter = new ExchangeAdapter();
        mAdapter.onClickListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_recyclerView.addOnScrollListener(mOnScrollListener);
        fragment_recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recyclerView.setLayoutManager(layoutManager);
        fragment_pullableLayout.topComponent.setOnPullListener(this);

        empty_view_imageView.setImageResource(R.mipmap.bg_public);
        empty_view_titleTextView.setText("您还没有相关的订单");
        empty_view_subtitleTextView.setText("可以去看看哪些想兑换的");
        empty_view_button.setText("立即去兑换");

        mOrderListSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mOrderListSearchInput.getText().toString();
                reset();
            }
        });

        mOrderListSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                name = mOrderListSearchInput.getText().toString();
                reset();
                return true;
            }
        });

        return view;
    }

    private void reset() {
        cur_page = 1;
        mAdapter.data.clear();
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            cur_page = 1;
            refresh();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }

    //默认加载第一页
    private int cur_page = 1;

    public void refresh() {
        if (data != null && cur_page > data.data.page.page_count && cur_page > 1) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest mOrderListRequest = new CommonRequest(Api.API_USER_INTEGRAL_ORDER_LIST, HttpWhat
                    .HTTP_ORDER_LIST.getValue());
            mOrderListRequest.add("page[cur_page]", cur_page);
            mOrderListRequest.add("name", name);
            addRequest(mOrderListRequest);
        }
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_DELETE:
                //删除成功
                HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity back) {
                        toast("删除订单成功");
                        refresh();
                    }
                });
                break;
            case HTTP_ORDER_LIST:
                refreshSucceed(response);
                break;
            case HTTP_ORDER_CANCEL:
                dialog(response);
                break;
            case HTTP_ORDER_CANCEL_CONFIRM:
                //取消订单成功
//                mAdapter.onClickListener = null;

                HttpResultManager.resolve(response,
                        ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                Utils.makeToast(getActivity(), back.message);
                                mAdapter.data.clear();
                                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                                        .getValue()));
                                cur_page = 1;
                                refresh();

                            }
                        }, true);
                break;
            case HTTP_ORDER_CONFIRM:
                ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
                if (model.code == 0) {
                    Utils.makeToast(getActivity(), model.message);
                    mAdapter.data.clear();
                    cur_page = 1;
                    refresh();
                } else {
                    Utils.makeToast(getActivity(), model.message);
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);

        HttpResultManager.resolve(response, ExchangeModel.class, new HttpResultManager.HttpResultCallBack<ExchangeModel>() {
            @Override
            public void onSuccess(ExchangeModel back) {
                data = back;

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (back.data.list != null && back.data.list.size() > 0) {

                    for (int i = 0; i < back.data.list.size(); i++) {
                        ExchangeModel.DataBean.ListBean orderListModel = back.data.list.get(i);
//                        String cancelTip = "";
//                        if ((orderListModel.order_status == Macro
//                                .ORDER_CONFIRM || orderListModel.order_status == Macro.ORDER_SCRAMBLE) && orderListModel.shipping_status == Macro.SS_UNSHIPPED) {
//
//                            if (orderListModel.order_cancel == 0 || orderListModel.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
//                                if (orderListModel.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
//                                    cancelTip = "商家拒绝取消订单申请";
//                                }
//                            } else if (orderListModel.order_cancel == Macro.ORDER_CANCEL_APPLY) {
//                                cancelTip = "商家审核取消订单申请";
//                                orderListModel.buttons.remove("cancel_order");
//                            }
//                        }
//                        orderListModel.cancelTip = cancelTip;

                        orderListModel.buttons = new ArrayList<>();
                        if (!TextUtils.isEmpty(orderListModel.pickup_id) && !"0".equals(orderListModel.pickup_id)) {
                            orderListModel.buttons.add(OrderButtonHelper.VIEW_PICKLIST);
                        } else if (orderListModel.shipping_status == Macro.SS_SHIPPED) {
                            orderListModel.buttons.add(OrderButtonHelper.VIEW_EXCHANGE_LOGISTICS);
                        }
                    }

                    relativeLayout_empty.setVisibility(View.GONE);
                    mAdapter.data.addAll(back.data.list);

                } else {
                    relativeLayout_empty.setVisibility(View.VISIBLE);
                    empty_view_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            cur_page = 1;
//                            refresh();
                            Intent intent = new Intent(getActivity(), IntegralMallActivity.class);
                            startActivity(intent);
                        }
                    });
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEmptyData(int state) {
                relativeLayout_empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String message) {
                toast(message);
                relativeLayout_empty.setVisibility(View.VISIBLE);
            }
        });
    }


    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_recyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        if (data != null) {
                            cur_page = data.data.page.cur_page + 1;
                            refresh();
                        }
                    }
                }
            }
        }
    };


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
                goOrderDetail(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_CANCEL_ORDER:
                cancelOrder(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_AWAIT_CONFIRM:
                order_id = String.valueOf(extraInfo);
                showConfirmDialog(R.string.confrimOrderTip, ViewType
                        .VIEW_TYPE_ORDER_CONFIRM.ordinal());
                break;
            case VIEW_TYPE_SHOP:
                openShopActivity(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_DEL_ORDER:
                order_id = String.valueOf(extraInfo);
                showConfirmDialog(R.string.msgDelOrder, ViewType.VIEW_TYPE_DEL_ORDER
                        .ordinal());
                break;
            case VIEW_TYPE_COMMENT:
                ExchangeModel.DataBean.ListBean orderStatusModel = mAdapter
                        .data.get(position);
                goComment(String.valueOf(extraInfo), orderStatusModel.shop_id);
                break;
            case VIEW_TYPE_VIEW_EXCHANGE_LOGISTICS:
                viewExpress(String.valueOf(extraInfo));
                break;
            case VIEW_TYPE_PICK_UP:
                openMapActivity(position);
                break;
            case VIEW_TYPE_SEARCH:
                name = mOrderListSearchInput.getText().toString();
                reset();
                break;
            default:
                super.onClick(v);
        }
    }

    private void openMapActivity(int position) {
        ExchangeModel.DataBean.ListBean orderListModel = mAdapter.data.get
                (position);

        Intent intent = new Intent();
        intent.setClass(getActivity(), MapActivity.class);
        intent.putExtra(Key.KEY_MARKER_NAME.getValue(), orderListModel.pickup.pickup_name);
        intent.putExtra(Key.KEY_MARKER_SNIPPET.getValue(), orderListModel.pickup.pickup_address);
        intent.putExtra(Key.KEY_LATITUDE.getValue(), orderListModel.pickup.address_lat);
        intent.putExtra(Key.KEY_LONGITUDE.getValue(), orderListModel.pickup.address_lng);
        intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
        intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
        intent.putExtra(Key.KEY_TITLE.getValue(),orderListModel.pickup.pickup_name);
        startActivity(intent);
    }

    public void viewExpress(String orderId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.putExtra("express_type", "exchange");
        intent.setClass(getActivity(), ExpressActivity.class);
        startActivity(intent);
    }

    public void goComment(String orderId, String shopId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.putExtra("shop_id", shopId);
        intent.setClass(getActivity(), OrderReviewActivity.class);
        startActivity(intent);
    }

    public void delOrder(String orderId) {
        CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_DELETE, HttpWhat
                .HTTP_ORDER_DELETE.getValue(), RequestMethod.POST);
        mCancelOrderRequest.add("order_id", orderId);
        mCancelOrderRequest.add("type", "1");
        addRequest(mCancelOrderRequest);
    }


    public void goOrderDetail(String orderId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.setClass(getActivity(), ExchangeDetailActivity.class);
        startActivity(intent);
    }

    //存储取消订单的理由
    CancelOrderModel cancelOrderModel;

    public void cancelOrder(String orderId) {
        if (cancelOrderModel != null) {
            cancelOrderModel.data.order_id = orderId;
            //取消订单
            dialog(cancelOrderModel);
        } else {
            CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_CANCEL, HttpWhat
                    .HTTP_ORDER_CANCEL.getValue());
            mCancelOrderRequest.add("from", "list");
            mCancelOrderRequest.add("type", "cancel");
            mCancelOrderRequest.add("id", orderId);
            mCancelOrderRequest.setAjax(true);
            addRequest(mCancelOrderRequest);
        }
    }

    private void openShopActivity(String shopId) {
        Intent intent = new Intent(getContext(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_ORDER_CONFIRM:
                confrimOrder(order_id);
                break;
            case VIEW_TYPE_DEL_ORDER:
                delOrder(order_id);
                break;
        }
    }

    private void confrimOrder(String orderId) {
        CommonRequest mOrderConfirmRequest = new CommonRequest(Api.API_ORDER_CONFIRM, HttpWhat
                .HTTP_ORDER_CONFIRM.getValue(), RequestMethod.POST);
        mOrderConfirmRequest.add("id", orderId);
        addRequest(mOrderConfirmRequest);
    }

    private void dialog(String response) {
        CancelOrderModel model = JSON.parseObject(response, CancelOrderModel.class);
        cancelOrderModel = model;
        dialog(cancelOrderModel);
    }

//    private String delOrderId;

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

        //由于订单好是唯一的，这里我用个全局变量来存储删除的订单id
//        delOrderId = model.data.order_id;

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

}
