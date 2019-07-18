package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ExpressActivity;
import com.szy.yishopcustomer.Activity.GiftCardPickUpActivity;
import com.szy.yishopcustomer.Activity.OrderDetailFreeActivity;
import com.szy.yishopcustomer.Activity.OrderListFreeActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.OrderListFreeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GiftsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.Model;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.PickupModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiftCardsFragment extends YSCBaseFragment implements OnEmptyViewClickListener {

    @BindView(R.id.fragment_order_list_all_textView)
    TextView mOrderListAllTextView;
    @BindView(R.id.fragment_order_list_await_ship_textView)
    TextView mOrderListAwaitShipTextView;
    @BindView(R.id.fragment_order_list_await_receive_textView)
    TextView mOrderListAwaitReceiveTextView;
    @BindView(R.id.fragment_order_list_await_review_textView)
    TextView mOrderListAwaitReviewTextView;
    @BindView(R.id.fragment_order_list_search_input)
    EditText mOrderListSearchInput;
    @BindView(R.id.fragment_order_list_search_imageView)
    ImageView mOrderListSearchButton;

    @BindView(R.id.fragment_list_recyclerView)
    CommonRecyclerView mOrderRecyclerView;

    private LinearLayoutManager mLayoutManager;
    public OrderListFreeAdapter mOrderListAdapter;

    private String mOrderType = Macro.ORDER_TYPE_ALL;
    private int page = 1;
    private int pageCount;
    private Model mResponseModel;
    private String order_id;
    private boolean upDataSuccess = false;

    //搜索关键词
    private String name_gift = "";

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

    @Override
    public void onEmptyViewClicked() {
        startActivity(new Intent(getActivity(), GiftCardPickUpActivity.class));
    }

    @Override
    public void onOfflineViewClicked() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_gift_cards;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mOrderListAllTextView.setOnClickListener(this);
        mOrderListAwaitShipTextView.setOnClickListener(this);
        mOrderListAwaitReceiveTextView.setOnClickListener(this);
        mOrderListAwaitReviewTextView.setOnClickListener(this);

        mOrderListSearchButton.setOnClickListener(this);
        mOrderRecyclerView.setEmptyViewClickListener(this);
        mOrderRecyclerView.addOnScrollListener(mOnScrollListener);

        mOrderListSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                name_gift = mOrderListSearchInput.getText().toString();
                reset();
                return true;
            }
        });

        mOrderListAdapter = new OrderListFreeAdapter();
        mOrderListAdapter.buy_type = 2;
        mLayoutManager = new LinearLayoutManager(getContext());
        mOrderRecyclerView.setLayoutManager(mLayoutManager);
        mOrderRecyclerView.setAdapter(mOrderListAdapter);

        //mPullableLayout.topComponent.setOnPullListener(this);
        mOrderListAdapter.onClickListener = this;

        colorSelect(mOrderListAllTextView);
        refreshOrderList();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_order_list_all_textView:
                mOrderType = Macro.ORDER_TYPE_ALL;
                colorSelect(mOrderListAllTextView);
                reset();
                break;
            case R.id.fragment_order_list_await_ship_textView:
                mOrderType = Macro.ORDER_TYPE_AWAIT_SHIPPED;
                colorSelect(mOrderListAwaitShipTextView);
                reset();
                break;
            case R.id.fragment_order_list_await_receive_textView:
                mOrderType = Macro.ORDER_TYPE_SHIPPED;
                colorSelect(mOrderListAwaitReceiveTextView);
                reset();
                break;
            case R.id.fragment_order_list_await_review_textView:
                mOrderType = Macro.ORDER_TYPE_FINISHED;
                colorSelect(mOrderListAwaitReviewTextView);
                reset();
                break;
            case R.id.fragment_order_list_search_imageView:
                name_gift = mOrderListSearchInput.getText().toString();
                reset();
                break;
            case R.id.linearlayout_free_order:
                Intent intent = new Intent(getActivity(), OrderListFreeActivity.class);
                intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), Macro.ORDER_TYPE_ALL);
                startActivity(intent);
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_ORDER_GOODS:
                        goOrderDetail(String.valueOf(extraInfo));
                        break;
                    case VIEW_TYPE_VIEW_LOGISTICS:
                        viewExpress(String.valueOf(extraInfo));
                        break;
                    case VIEW_TYPE_AWAIT_CONFIRM:
                        order_id = String.valueOf(extraInfo);
                        showConfirmDialog(R.string.confrimOrderTip, ViewType
                                .VIEW_TYPE_ORDER_CONFIRM.ordinal());
                        break;
                    case VIEW_TYPE_SHOP:
                        openShopActivity(String.valueOf(extraInfo));
                        break;
                    default:
                        super.onClick(v);
                }
                break;
        }
    }


    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_ORDER_CONFIRM:
                confrimOrder(order_id);
                break;
        }
    }

    private void confrimOrder(String orderId) {
        CommonRequest mOrderConfirmRequest = new CommonRequest(Api.API_ORDER_CONFIRM, HttpWhat
                .HTTP_ORDER_CONFIRM.getValue(), RequestMethod.POST);
        mOrderConfirmRequest.add("id", orderId);
        addRequest(mOrderConfirmRequest);
    }

    private void openShopActivity(String shopId) {
        Intent intent = new Intent(getContext(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    public void viewExpress(String orderId) {
        Intent intent = new Intent();
        intent.putExtra("order_id", orderId);
        intent.setClass(getActivity(), ExpressActivity.class);
        startActivity(intent);
    }

    public void goOrderDetail(String orderId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.setClass(getActivity(), OrderDetailFreeActivity.class);
        intent.putExtra(OrderListFreeFragment.PARAMS_TYPE, OrderListFreeFragment.TYPE_PICKUP);
        startActivity(intent);
    }

    private void reset() {
        mOrderListAdapter.data.clear();
        page = 1;
        refreshOrderList();
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
            refreshOrderList();
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_LIST:
                refreshOrderListCallback(response);
                break;
            case HTTP_ORDER_CONFIRM:

                ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
                if (model.code == 0) {
                    Utils.makeToast(getActivity(), model.message);
                    mOrderListAdapter.data.clear();
                    page = 1;
                    refreshOrderList();
                } else {
                    Utils.makeToast(getActivity(), model.message);
                }

                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void refreshOrderListCallback(String response) {

        upDataSuccess = true;
        mResponseModel = JSON.parseObject(response, Model.class);
        if (mResponseModel.code == 0) {
            pageCount = mResponseModel.data.page.page_count;

            mOrderListAllTextView.setText("全部(" + mResponseModel.data.order_counts.all + ")");
            mOrderListAwaitShipTextView.setText("待发货(" + mResponseModel.data.order_counts.unshipped + ")");
            mOrderListAwaitReceiveTextView.setText("待收货(" + mResponseModel.data.order_counts.shipped + ")");
            mOrderListAwaitReviewTextView.setText("交易成功(" + mResponseModel.data.order_counts.finished + ")");

            if (!Utils.isNull(mResponseModel.data.list)) {
                mOrderRecyclerView.hideEmptyView();

                if (page == 1) {
                    mOrderListAdapter.data.add(new DividerModel());
                }

                for (OrderListModel orderListModel : mResponseModel.data.list) {
                    OrderTitleModel orderTitleModel = new OrderTitleModel();
                    orderTitleModel.shop_id = orderListModel.shop_id;
                    orderTitleModel.shop_name = orderListModel.shop_name;

                    //判断是否是拼团
                    if (orderListModel.order_type == Macro.OT_FIGHT_GROUP && !TextUtils
                            .isEmpty(orderListModel.groupon_status_format)) {
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

                    //判断是否是拼团
                    if (orderListModel.order_type != Macro.OT_FIGHT_GROUP ||
                            orderListModel.groupon_status != 2) {
                        if (!Utils.isNull(orderListModel.buttons) && orderListModel.buttons.size
                                () > 0) {
                            OrderStatusModel orderStatusModel = new OrderStatusModel();

                            //用于存储所有的按钮
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

            //mPullableLayout.topComponent.finish(Result.SUCCEED);

            if (page != 1) {
                mOrderRecyclerView.smoothScrollBy(0, 100);
            }
        } else {
            //mPullableLayout.topComponent.finish(Result.FAILED);
        }
    }


    //订单列表
    public void refreshOrderList() {
        CommonRequest mOrderListRequest = new CommonRequest(Api.API_ORDER_LIST, HttpWhat
                .HTTP_ORDER_LIST.getValue());

        mOrderListRequest.alarm = true;

        if (mOrderType.equals("unevaluate")) {
            mOrderListRequest.add("evaluate_status", mOrderType);
        } else {
            mOrderListRequest.add("order_status", mOrderType);
        }

        mOrderListRequest.add("is_gift", "1");
        mOrderListRequest.add("page[page_size]", "5");
        mOrderListRequest.add("page[cur_page]", page);
        mOrderListRequest.add("name_gift", name_gift);

        addRequest(mOrderListRequest);
    }

    public void colorSelect(TextView t) {
        mOrderListAwaitShipTextView.setSelected(false);
        mOrderListAllTextView.setSelected(false);
        mOrderListAwaitReceiveTextView.setSelected(false);
        mOrderListAwaitReviewTextView.setSelected(false);

        t.setSelected(true);
    }

}
