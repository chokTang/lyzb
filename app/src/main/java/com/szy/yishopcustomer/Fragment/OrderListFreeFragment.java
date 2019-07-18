package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ExpressActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.OrderDetailFreeActivity;
import com.szy.yishopcustomer.Activity.OrderPayActivity;
import com.szy.yishopcustomer.Activity.OrderReviewActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Adapter.OrderListFreeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.CancelOrderModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GiftsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.Model;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderCountsModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.PickupModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.TotalModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/6/12
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListFreeFragment extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener {

    //自由购
    public static final int TYPE_FREEBUY = 0;
    //堂内点餐
    public static final int TYPE_REACHBUY = 1;
    //提货券
    public static final int TYPE_PICKUP = 2;

    public static final String PARAMS_TYPE = "params_type";
    //购买方式
    public int buy_type = TYPE_FREEBUY;

    @BindView(R.id.fragment_order_list_all_textView)
    TextView mOrderListAllTextView;
    @BindView(R.id.fragment_order_list_await_pay_textView)
    TextView mOrderListAwaitPayTextView;
    @BindView(R.id.fragment_order_list_await_ship_textView)
    TextView mOrderListAwaitShipTextView;
    @BindView(R.id.fragment_order_list_await_review_textView)
    TextView mOrderListAwaitReviewTextView;
    @BindView(R.id.fragment_order_list_search_input)
    EditText mOrderListSearchInput;
    @BindView(R.id.fragment_order_list_search_imageView)
    ImageView mOrderListSearchButton;

    @BindView(R.id.fragment_order_list_recyclerView)
    CommonRecyclerView mOrderRecyclerView;
    @BindView(R.id.fragment_order_list_recyclerView_layout)
    PullableLayout mPullableLayout;

    private LinearLayoutManager mLayoutManager;
    private OrderListFreeAdapter mOrderListAdapter;

    private String mOrderType;
    private int page = 1;
    private int pageCount;
    private Model mResponseModel;
    private String order_id;
    private boolean upDataSuccess = false;

    //搜索关键词
    private String name = "";

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mOrderRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }
                }
            }
        }
    };

    //存储取消订单的理由
    CancelOrderModel cancelOrderModel;

    /**
     * 取消订单
     *
     * @param orderId
     */
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

    public void delOrder(String orderId) {
        CommonRequest mCancelOrderRequest = new CommonRequest(Api.API_ORDER_DELETE, HttpWhat
                .HTTP_ORDER_DELETE.getValue(), RequestMethod.POST);
        mCancelOrderRequest.add("type", "1");
        mCancelOrderRequest.add("order_id", orderId);
        mCancelOrderRequest.setAjax(true);
        addRequest(mCancelOrderRequest);
    }

    public void colorSelect(TextView t1, TextView t2, TextView t3, TextView t5) {
        t1.setSelected(true);
        t2.setSelected(false);
        t3.setSelected(false);
        t5.setSelected(false);
    }

    public void goComment(String orderId, String shopId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.putExtra("shop_id", shopId);
        intent.setClass(getActivity(), OrderReviewActivity.class);
        startActivity(intent);
    }
    //    private CustomProgressDialog mProgress;

    public void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    public void goOrderDetail(String orderId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.setClass(getActivity(), OrderDetailFreeActivity.class);
        intent.putExtra(OrderListFreeFragment.PARAMS_TYPE, buy_type);
        startActivity(intent);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            mOrderListAdapter.data.clear();
            page = 1;
            refresh(mOrderType);
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_ORDER_LIST:
                mOrderListAdapter.data.clear();
                page = 1;
                refresh(mOrderType);
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_order_list_all_textView:
                mOrderListAdapter.data.clear();
                page = 1;
                mOrderType = Macro.ORDER_TYPE_ALL;
                colorSelect(mOrderListAllTextView, mOrderListAwaitPayTextView,
                        mOrderListAwaitShipTextView,
                        mOrderListAwaitReviewTextView);
                refresh(mOrderType);
                break;
            case R.id.fragment_order_list_await_pay_textView:
                mOrderListAdapter.data.clear();
                mOrderType = Macro.ORDER_TYPE_UNPAID;
                page = 1;
                colorSelect(mOrderListAwaitPayTextView, mOrderListAllTextView,
                        mOrderListAwaitShipTextView,
                        mOrderListAwaitReviewTextView);
                refresh(mOrderType);
                break;
            case R.id.fragment_order_list_await_ship_textView:
                mOrderListAdapter.data.clear();
                mOrderType = Macro.ORDER_TYPE_AWAIT_SHIPPED;
                page = 1;
                colorSelect(mOrderListAwaitShipTextView, mOrderListAwaitPayTextView,
                        mOrderListAllTextView,
                        mOrderListAwaitReviewTextView);
                refresh(mOrderType);
                break;
            case R.id.fragment_order_list_await_review_textView:
                mOrderListAdapter.data.clear();
                mOrderType = Macro.ORDER_TYPE_AWAIT_REVIEWED;
                page = 1;
                colorSelect(mOrderListAwaitReviewTextView,
                        mOrderListAwaitShipTextView, mOrderListAwaitPayTextView,
                        mOrderListAllTextView);
                refresh(mOrderType);
                break;
            case R.id.fragment_order_list_search_imageView:
                openOrderSearchActivity();
                break;
            default:
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
                    case VIEW_TYPE_DEL_ORDER:
//                        delOrder(String.valueOf(extraInfo));

                        showConfirmDialog(R.string.alertDeleteOrder, ViewType
                                .VIEW_TYPE_CLEAR_CONFIRM.ordinal());
                        delOrderId = String.valueOf(extraInfo);

                        break;
                    case VIEW_TYPE_VIEW_LOGISTICS:
                        viewExpress(String.valueOf(extraInfo));
                        break;
                    case VIEW_TYPE_COMMENT:
                        OrderStatusModel orderStatusModel = (OrderStatusModel) mOrderListAdapter
                                .data.get(position);
                        goComment(String.valueOf(extraInfo), orderStatusModel.shop_id);
                        break;
                    case VIEW_TYPE_PAYMENT:
                        openPayActivity(String.valueOf(extraInfo));
                        break;
                    case VIEW_TYPE_AWAIT_CONFIRM:
                        order_id = String.valueOf(extraInfo);
                        showConfirmDialog(R.string.confrimOrderTip, ViewType
                                .VIEW_TYPE_ORDER_CONFIRM.ordinal());
                        break;
                    case VIEW_TYPE_SHOP:
                        openShopActivity(String.valueOf(extraInfo));
                        break;
                    case VIEW_TYPE_INVITE_FRIEND:
                        OrderStatusModel orderStatusModel1 = (OrderStatusModel) mOrderListAdapter
                                .data.get(position);
                        openGrouponActivity(orderStatusModel1.order_sn);
                        break;
                    case VIEW_TYPE_PICK_UP:
                        openMap(position);
                        break;
                    default:
                        super.onClick(v);
                }
                break;
        }
    }


    private void openMap(int position) {
        PickupModel pickupModel = (PickupModel) mOrderListAdapter
                .data.get(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), MapActivity.class);
        intent.putExtra(Key.KEY_PICKUP_ID.getValue(), pickupModel.pickup_id);
        intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
        intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
        startActivity(intent);
    }

    private void openGrouponActivity(String groupSn) {
        Intent intent = new Intent(getContext(), UserGroupOnDetailActivity.class);
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.putExtra(Key.KEY_BOOLEAN.getValue(), "1");
        startActivity(intent);
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
            case VIEW_TYPE_CLEAR_CONFIRM:
                delOrder(delOrderId);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        mOrderListAllTextView.setOnClickListener(this);
        mOrderListAwaitPayTextView.setOnClickListener(this);
        mOrderListAwaitShipTextView.setOnClickListener(this);
        mOrderListAwaitReviewTextView.setOnClickListener(this);

        mOrderListSearchButton.setOnClickListener(this);
        mOrderRecyclerView.setEmptyViewClickListener(this);
        mOrderRecyclerView.addOnScrollListener(mOnScrollListener);

        mOrderListSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                openOrderSearchActivity();
                return true;
            }
        });

        switch (mOrderType) {
            case Macro.ORDER_TYPE_ALL:
                colorSelect(mOrderListAllTextView, mOrderListAwaitPayTextView,
                        mOrderListAwaitShipTextView,
                        mOrderListAwaitReviewTextView);
                break;
            case Macro.ORDER_TYPE_UNPAID:
                colorSelect(mOrderListAwaitPayTextView, mOrderListAllTextView,
                        mOrderListAwaitShipTextView,
                        mOrderListAwaitReviewTextView);
                break;
            case Macro.ORDER_TYPE_AWAIT_SHIPPED:
                colorSelect(mOrderListAwaitShipTextView, mOrderListAwaitPayTextView,
                        mOrderListAllTextView,
                        mOrderListAwaitReviewTextView);
                break;
            case Macro.ORDER_TYPE_AWAIT_REVIEWED:
                colorSelect(mOrderListAwaitReviewTextView,
                        mOrderListAwaitShipTextView, mOrderListAwaitPayTextView,
                        mOrderListAllTextView);
                break;
        }

        mOrderListAdapter = new OrderListFreeAdapter();
        mOrderListAdapter.buy_type = buy_type;
        mLayoutManager = new LinearLayoutManager(getContext());
        mOrderRecyclerView.setLayoutManager(mLayoutManager);
        mOrderRecyclerView.setAdapter(mOrderListAdapter);
        mPullableLayout.topComponent.setOnPullListener(this);
        mOrderListAdapter.onClickListener = this;

        return view;
    }

    @Override
    public void onEmptyViewClicked() {
        goIndex();
    }

    @Override
    public void onOfflineViewClicked() {
        mOrderListAdapter.data.clear();
        refresh(mOrderType);
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_LIST:
                mOrderRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_LIST:
                refreshCallback(response);
                break;
            case HTTP_ORDER_CANCEL:
                dialog(response);
                break;
            case HTTP_ORDER_DELETE:
                HttpResultManager.resolve(response,
                        ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                Utils.makeToast(getActivity(), back.message);
                                mOrderListAdapter.data.clear();
                                page = 1;
                                refresh(mOrderType);
                            }
                        }, true);
                break;
            case HTTP_ORDER_CANCEL_CONFIRM:
                //取消订单成功
                mOrderListAdapter.onClickListener = null;

                HttpResultManager.resolve(response,
                        ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                Utils.makeToast(getActivity(), back.message);
                                //判断如果剩余条目剩余大于3，就直接删除adapter数据，否则请求网络
                                //这里我用个简单的方法，删除mResponseModel里面的数据，然后转成string，在调用refreshCallback，跟网络请求后是一样的操作
                                mOrderListAdapter.data.clear();
                                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                                        .getValue()));

//                                if (!Utils.isNull(mResponseModel.data.list)) {
//                                    int len = mResponseModel.data.list.size();
//                                    if(len > 3) {
//                                        for(int i = 0 ;i<len ; i ++) {
//                                            if(mResponseModel.data.list.get(i).order_id.equals(delOrderId)) {
//                                                mResponseModel.data.list.remove(i);
//                                                break;
//                                            }
//                                        }
//                                        refreshCallback(JSON.toJSONString(mResponseModel));
//                                        return;
//                                    }
//                                }

                                page = 1;
                                refresh(mOrderType);
                            }
                        }, true);
                break;
            case HTTP_ORDER_CONFIRM:
                ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
                if (model.code == 0) {
                    Utils.makeToast(getActivity(), model.message);
                    mOrderListAdapter.data.clear();
                    page = 1;
                    refresh(mOrderType);
                } else {
                    Utils.makeToast(getActivity(), model.message);
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
        mLayoutId = R.layout.fragment_order_list_free;
        Intent intent = getActivity().getIntent();
        mOrderType = intent.getStringExtra(Key.KEY_ORDER_STATUS.getValue());
        buy_type = intent.getIntExtra(PARAMS_TYPE, TYPE_FREEBUY);

        if (buy_type == TYPE_REACHBUY) {
            getActivity().setTitle("订单列表-堂内点餐");
        }

        refresh(mOrderType);
    }

    public void openPayActivity(String orderId) {
        Intent intent = new Intent(getContext(), OrderPayActivity.class);
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        startActivity(intent);
    }

    public void refresh(String status) {
        String api = Api.API_FREEBUY_ORDER_LIST;

        if (buy_type == TYPE_REACHBUY) {
            api = Api.API_REACHBUY_ORDER_LIST;
        }

        CommonRequest mOrderListRequest = new CommonRequest(api, HttpWhat
                .HTTP_ORDER_LIST.getValue());

        if ("unevaluate".equals(status)) {
            mOrderListRequest.add("evaluate_status", status);
        } else {
            mOrderListRequest.add("order_status", status);
        }
        mOrderListRequest.add("page[page_size]", "5");
        mOrderListRequest.add("page[cur_page]", page);
        mOrderListRequest.add("name", name);

        addRequest(mOrderListRequest);
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
        CancelOrderModel model = JSON.parseObject(response, CancelOrderModel.class);
        cancelOrderModel = model;
        dialog(cancelOrderModel);
    }

    private String delOrderId;

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
        delOrderId = model.data.order_id;

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

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel blankModel = new CheckoutDividerModel();
            mOrderListAdapter.data.add(blankModel);
            mOrderListAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh(mOrderType);
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void openOrderSearchActivity() {
//        Intent intent = new Intent();
//        intent.putExtra(Key.KEY_SEARCH_ACTIVITY_KEY.getValue(), searchKey);
//        intent.setClass(getActivity(), OrderListSearchActivity.class);
//        startActivity(intent);

        mOrderListAdapter.data.clear();
        page = 1;
        name = mOrderListSearchInput.getText().toString();
        refresh(mOrderType);
    }

/*
    public void goIndex() {
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        this.finish();
    }
*/

    private void refreshCallback(String response) {
        upDataSuccess = true;
        mResponseModel = JSON.parseObject(response, Model.class);
        if (mResponseModel.code == 0) {
            pageCount = mResponseModel.data.page.page_count;

            //mOrderRecyclerView.setVisibility(View.VISIBLE);

            //订单列表数量
            OrderCountsModel order_number = mResponseModel.data.order_counts;
            mOrderListAllTextView.setText(
                    String.format("全部(%s)",
                            order_number.all));//全部
            mOrderListAwaitPayTextView.setText(
                    String.format("待付款(%s)",
                            order_number.unpayed));//待付款
            mOrderListAwaitShipTextView.setText(
                    String.format("待发货(%s)",
                            order_number.unshipped));//待发货
            mOrderListAwaitReviewTextView.setText(
                    String.format("待评价(%s)",
                            order_number.unevaluate));//待评价*/

            if (!Utils.isNull(mResponseModel.data.list)) {
                mOrderRecyclerView.hideEmptyView();

                if (page == 1) {
                    mOrderListAdapter.data.add(new DividerModel());
                }

                for (OrderListModel orderListModel : mResponseModel.data.list) {
                    OrderTitleModel orderTitleModel = new OrderTitleModel();
                    orderTitleModel.shop_id = orderListModel.shop_id;
                    orderTitleModel.shop_name = orderListModel.shop_name;
                    orderTitleModel.table_number = orderListModel.reachbuy_code;

                    //判断是否是拼团
                    if (orderListModel.order_type == Macro.OT_FIGHT_GROUP && !TextUtils.isEmpty(orderListModel.groupon_status_format)) {
                        orderTitleModel.order_status = orderListModel.groupon_status_format;
                    } else {
                        orderTitleModel.order_status = orderListModel.order_status_format;
                    }

                    mOrderListAdapter.data.add(orderTitleModel);

                    for (GoodsListModel goodsListModel : orderListModel.goods_list) {
                        mOrderListAdapter.data.add(goodsListModel);

                        if (goodsListModel.gifts_list != null) {
                            for (GiftsListModel giftsListModel : goodsListModel.gifts_list.values
                                    ()) {
                                mOrderListAdapter.data.add(giftsListModel);
                            }
                        }
                    }

                    if (!TextUtils.isEmpty(orderListModel.pickup_name)) {
                        PickupModel pickupModel = new PickupModel();
                        pickupModel.pickname = orderListModel.pickup_name;
                        pickupModel.pickup_id = orderListModel.pickup_id;
                        mOrderListAdapter.data.add(pickupModel);
                    }

                    TotalModel totalModel = new TotalModel();
                    totalModel.mGoodsNumber = orderListModel.goods_num;
                    totalModel.mGoodsAmount = orderListModel.order_amount;
                    totalModel.mGoodsShippingFee = orderListModel.shipping_fee;
                    mOrderListAdapter.data.add(totalModel);

                    //判断是否是拼团
                    if (orderListModel.order_type != Macro.OT_FIGHT_GROUP || orderListModel.groupon_status != 2) {
                        if (!Utils.isNull(orderListModel.buttons) && orderListModel.buttons.size() > 0) {
                            OrderStatusModel orderStatusModel = new OrderStatusModel();
                            int button_number = orderListModel.buttons.size();

                            switch (buy_type) {
                                case TYPE_PICKUP:
                                    orderStatusModel.buttons = new ArrayList();
                                    orderStatusModel.order_id = orderListModel.order_id;
                                    orderStatusModel.shop_id = orderListModel.shop_id;
                                    orderStatusModel.order_sn = orderListModel.group_sn;

                                    if (orderListModel.shipping_status == Macro.SS_SHIPPED || orderListModel.shipping_status == Macro.SS_SHIPPED_PART) {
                                        orderStatusModel.buttons.add(OrderButtonHelper.VIEW_LOGISTICS);
                                    }

                                    if (orderListModel.order_status == Macro.ORDER_CONFIRM && orderListModel.shipping_status == Macro.SS_SHIPPED) {
                                        orderStatusModel.buttons.add(OrderButtonHelper.CONFIRM_ORDER);
                                    }
                                    break;
                                default:
                                    //用于存储所有的按钮
                                    orderStatusModel.buttons = orderListModel.buttons;
//                            if (button_number == 2) {
//                                orderStatusModel.button1_code = orderListModel.buttons.get(0)
//                                        .toString();
//                                orderStatusModel.button2_code = orderListModel.buttons.get(1)
//                                        .toString();
//                            } else if (button_number == 1) {
//                                orderStatusModel.button1_code = "";
//                                orderStatusModel.button2_code = orderListModel.buttons.get(0)
//                                        .toString();
//                            }
                                    orderStatusModel.order_id = orderListModel.order_id;
                                    orderStatusModel.shop_id = orderListModel.shop_id;
                                    orderStatusModel.order_sn = orderListModel.group_sn;

                                    String cancelTip = "";

                                    if ((orderListModel.order_status == Macro
                                            .ORDER_CONFIRM || orderListModel.order_status == Macro.ORDER_SCRAMBLE) && orderListModel.shipping_status == Macro.SS_UNSHIPPED) {

                                        if (orderListModel.order_cancel == 0 || orderListModel.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
                                            if (orderListModel.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
                                                cancelTip = "商家拒绝取消订单申请";
                                            }
                                        } else if (orderListModel.order_cancel == Macro.ORDER_CANCEL_APPLY) {
                                            cancelTip = "商家审核取消订单申请";
                                            orderStatusModel.buttons.remove("cancel_order");
                                        }
                                    }
                                    orderStatusModel.cancelTip = cancelTip;
                                    break;
                            }

                            mOrderListAdapter.data.add(orderStatusModel);
                        }
                    }
                    mOrderListAdapter.data.add(new DividerModel());
                }
            } else {
                mOrderListAdapter.data.clear();
                upDataSuccess = false;
                mOrderRecyclerView.showEmptyView();
            }
            mOrderListAdapter.notifyDataSetChanged();
            //恢复点击事件
            mOrderListAdapter.onClickListener = this;

            mPullableLayout.topComponent.finish(Result.SUCCEED);

            if (page != 1) {
                mOrderRecyclerView.smoothScrollBy(0, 100);
            }
        } else {
            mPullableLayout.topComponent.finish(Result.FAILED);
        }
    }

}
