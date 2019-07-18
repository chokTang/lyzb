package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.OrderListFreeActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Adapter.OrderListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsRecyclerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsSingleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.Model;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.PickupModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.TotalModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/6/12
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListFragment extends OrderOperation implements OnEmptyViewClickListener {
    @BindView(R.id.fragment_order_list_all_textView)
    TextView mOrderListAllTextView;
    @BindView(R.id.fragment_order_list_await_pay_textView)
    TextView mOrderListAwaitPayTextView;
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

    @BindView(R.id.fragment_order_list_recyclerView)
    CommonRecyclerView mOrderRecyclerView;
/*
    @BindView(R.id.fragment_order_list_recyclerView_layout)
    PullableLayout mPullableLayout;
*/

    @BindView(R.id.linearlayout_free_order)
    View linearlayout_free_order;
    @BindView(R.id.fragment_order_list_citywide_textView)
    TextView mOrderListCitywideTextView;

    private LinearLayoutManager mLayoutManager;
    private OrderListAdapter mOrderListAdapter;

    private String mOrderType;
    private int page = 1;
    private int pageCount;
    private Model mResponseModel;
    private boolean upDataSuccess = false;
    private String searchWord;

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

    public void colorSelect(TextView t) {
        mOrderListAwaitPayTextView.setSelected(false);
        mOrderListAllTextView.setSelected(false);
        mOrderListAwaitShipTextView.setSelected(false);
        mOrderListAwaitReceiveTextView.setSelected(false);
        mOrderListAwaitReviewTextView.setSelected(false);

        t.setSelected(true);
    }

    public void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }


/*    @Override
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

    }*/

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_ORDER_LIST:
                reset();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    private void reset() {
        mOrderListAdapter.data.clear();
        page = 1;
        refreshOrderList(mOrderType);
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
            case R.id.fragment_order_list_await_pay_textView:
                mOrderType = Macro.ORDER_TYPE_UNPAID;
                colorSelect(mOrderListAwaitPayTextView);
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
                mOrderType = Macro.ORDER_TYPE_AWAIT_REVIEWED;
                colorSelect(mOrderListAwaitReviewTextView);
                reset();
                break;
            case R.id.fragment_order_list_search_imageView:
                searchWord = mOrderListSearchInput.getText().toString();
                reset();
                break;
            case R.id.linearlayout_free_order: {
                Intent intent = new Intent(getActivity(), OrderListFreeActivity.class);
                intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), Macro.ORDER_TYPE_ALL);
                startActivity(intent);
            }
                break;
            case R.id.fragment_order_list_citywide_textView: {
                Intent intent = new Intent(getActivity(), ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL+"orderList?isfromOrigin=1");
                startActivity(intent);
            }
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_COMMENT:
                        OrderStatusModel orderStatusModel = (OrderStatusModel) mOrderListAdapter
                                .data.get(position);
                        goComment(String.valueOf(extraInfo), orderStatusModel.shop_id);
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
        PickupModel pickupModel = (PickupModel) mOrderListAdapter.data.get(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), MapActivity.class);
        intent.putExtra(Key.KEY_PICKUP_ID.getValue(), pickupModel.pickup_id);
        intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
        intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_order_list;
        Intent intent = getActivity().getIntent();
        mOrderType = intent.getStringExtra(Key.KEY_ORDER_STATUS.getValue());
        refreshOrderList(mOrderType);
    }

    //订单列表
    public void refreshOrderList(String status) {
        CommonRequest mOrderListRequest = new CommonRequest(Api.API_ORDER_LIST, HttpWhat
                .HTTP_ORDER_LIST.getValue());
        mOrderListRequest.alarm = true;

        mOrderListRequest.add("name", searchWord);
        //待评价传参不同
        if("unevaluate".equals(status)){
            mOrderListRequest.add("order_status", "all");
            mOrderListRequest.add("evaluate_status", "unevaluate");
        }else{
            mOrderListRequest.add("order_status", status);
            mOrderListRequest.add("evaluate_status", "all");
        }
        mOrderListRequest.add("page[cur_page]", page);
        mOrderListRequest.add("page[page_size]", "5");

        addRequest(mOrderListRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        mOrderListAllTextView.setOnClickListener(this);
        mOrderListAwaitPayTextView.setOnClickListener(this);
        mOrderListAwaitShipTextView.setOnClickListener(this);
        mOrderListAwaitReceiveTextView.setOnClickListener(this);
        mOrderListAwaitReviewTextView.setOnClickListener(this);

        linearlayout_free_order.setOnClickListener(this);

        mOrderListSearchButton.setOnClickListener(this);
        mOrderRecyclerView.setEmptyViewClickListener(this);
        mOrderListCitywideTextView.setOnClickListener(this);
        mOrderRecyclerView.addOnScrollListener(mOnScrollListener);

        mOrderListSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                searchWord = mOrderListSearchInput.getText().toString();
                reset();
                return true;
            }
        });

        switch (mOrderType) {
            case Macro.ORDER_TYPE_ALL:
                colorSelect(mOrderListAllTextView);
                break;
            case Macro.ORDER_TYPE_UNPAID:
                colorSelect(mOrderListAwaitPayTextView);
                break;
            case Macro.ORDER_TYPE_AWAIT_SHIPPED:
                colorSelect(mOrderListAwaitShipTextView);
                break;
            case Macro.ORDER_TYPE_SHIPPED:
                colorSelect(mOrderListAwaitReceiveTextView);
                break;
            case Macro.ORDER_TYPE_AWAIT_REVIEWED:
                colorSelect(mOrderListAwaitReviewTextView);
                break;
        }

        mOrderListAdapter = new OrderListAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mOrderRecyclerView.setLayoutManager(mLayoutManager);
        mOrderRecyclerView.setAdapter(mOrderListAdapter);
        //mPullableLayout.topComponent.setOnPullListener(this);
        mOrderListAdapter.onClickListener = this;

        return view;
    }

    @Override
    public void onOfflineViewClicked() {
        mOrderListAdapter.data.clear();
        refreshOrderList(mOrderType);
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
                refreshOrderListCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void confirmOrderCallback(String response) {
        super.confirmOrderCallback(response);
        ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
        if (model.code == 0) {
            Utils.makeToast(getActivity(), model.message);
            reset();
        } else {
            Utils.makeToast(getActivity(), model.message);
        }
    }
    @Override
    public void delOrderCallback(String response) {
        super.delOrderCallback(response);
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                Utils.makeToast(getActivity(), back.message);
                reset();
            }
        }, true);
    }

    @Override
    public void cancelOrderCallback(String response) {
        super.cancelOrderCallback(response);
        mOrderListAdapter.onClickListener = null;

        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Utils.makeToast(getActivity(), back.message);
                //判断如果剩余条目剩余大于3，就直接删除adapter数据，否则请求网络
                //这里我用个简单的方法，删除mResponseModel里面的数据，然后转成string，在调用refreshCallback
                // ，跟网络请求后是一样的操作
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                        .getValue()));

//                                if (!Utils.isNull(mResponseModel.data.list)) {
//                                    int len = mResponseModel.data.list.size();
//                                    if(len > 3) {
//                                        for(int i = 0 ;i<len ; i ++) {
//                                            if(mResponseModel.data.list.get(i).order_id.equals
// (delOrderId)) {
//                                                mResponseModel.data.list.remove(i);
//                                                break;
//                                            }
//                                        }
//                                        refreshCallback(JSON.toJSONString(mResponseModel));
//                                        return;
//                                    }
//                                }
                setCancelOrderModel(null);
                reset();
            }
        }, true);
    }

    @Override
    public void delayOrderCallback(String response) {
        super.cancelOrderCallback(response);
        mOrderListAdapter.onClickListener = null;

        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Utils.makeToast(getActivity(), back.message);

                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO
                        .getValue()));

                setDelayOrderModel(null);
                reset();
            }
        }, true);
    }

    public void refreshOrderListCallback(String response) {
        upDataSuccess = true;
        mResponseModel = JSON.parseObject(response, Model.class);
        if (mResponseModel.code == 0) {
            pageCount = mResponseModel.data.page.page_count;

            if (page == 1 && mOrderType.equals(Macro.ORDER_TYPE_ALL)) {
                if (TextUtils.isEmpty(mResponseModel.data.freebuy_order_counts) || "0".equals
                        (mResponseModel.data.freebuy_order_counts)) {

                    linearlayout_free_order.setVisibility(View.GONE);
                } else {
                    linearlayout_free_order.setVisibility(View.VISIBLE);
                }
            }
            //mOrderRecyclerView.setVisibility(View.VISIBLE);

            //订单列表数量
            /*OrderCountsModel order_number = mResponseModel.data.order_counts;
            mOrderListAllTextView.setText("全部("+order_number.all+")");

            mOrderListAwaitPayTextView.setText("待付款("+order_number.unpayed+")");

            mOrderListAwaitShipTextView.setText("待发货("+order_number.unshipped+")");

            mOrderListAwaitReceiveTextView.setText("待收货("+order_number.shipped+")");

            mOrderListAwaitReviewTextView.setText("待评价("+order_number.unevaluate+")");*/

            if (!Utils.isNull(mResponseModel.data.list)) {
                if (null!=mOrderRecyclerView){
                    mOrderRecyclerView.hideEmptyView();
                }
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

                    boolean hasGift = false;
                    if(orderListModel.goods_list.size()==1){
                        hasGift = orderListModel.goods_list.get(0).gifts_list.size()>0;
                    }

                    if(orderListModel.goods_list.size()>1||hasGift){
                        GoodsRecyclerModel goodsRecyclerModel = new GoodsRecyclerModel();
                        goodsRecyclerModel.goods_list = orderListModel.goods_list;
                        mOrderListAdapter.data.add(goodsRecyclerModel);
                    }else{
                        GoodsSingleModel goodsSingleModel = new GoodsSingleModel();
                        goodsSingleModel.order_id = orderListModel.goods_list.get(0).order_id;
                        goodsSingleModel.goods_name = orderListModel.goods_list.get(0).goods_name;
                        goodsSingleModel.goods_image = orderListModel.goods_list.get(0).goods_image;
                        goodsSingleModel.goods_type = orderListModel.goods_list.get(0).goods_type;
                        goodsSingleModel.max_integral_num = orderListModel.goods_list.get(0).max_integral_num;
                        mOrderListAdapter.data.add(goodsSingleModel);
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
                    if (!Utils.isNull(orderListModel.change_amount)) {
                        totalModel.mGoodsPriceEdit = orderListModel.change_amount;
                    }
                    //totalModel.mGoodsPriceEdit = orderListModel.change_amount;
                    mOrderListAdapter.data.add(totalModel);

                    //判断是否是拼团
                    if (orderListModel.order_type != Macro.OT_FIGHT_GROUP || orderListModel.groupon_status != 2) {
                        if (!Utils.isNull(orderListModel.buttons) && orderListModel.buttons.size() > 0) {
                            OrderStatusModel orderStatusModel = new OrderStatusModel();

                            //用于存储所有的按钮
                            orderStatusModel.buttons = orderListModel.buttons;
                            orderStatusModel.order_id = orderListModel.order_id;
                            orderStatusModel.shop_id = orderListModel.shop_id;
                            orderStatusModel.order_sn = orderListModel.order_sn;
                            orderStatusModel.delay_days = orderListModel.delay_days;
                            orderStatusModel.order_status = orderListModel.order_status;
                            orderStatusModel.integral = orderListModel.integral;

                            String cancelTip = "";
                            if ((orderListModel.order_status == Macro.ORDER_CONFIRM ||
                                    orderListModel.order_status == Macro.ORDER_SCRAMBLE) &&
                                    orderListModel.shipping_status == Macro.SS_UNSHIPPED) {

                                if (orderListModel.order_cancel == 0 || orderListModel
                                        .order_cancel == Macro.ORDER_CANCEL_REFUSE) {
                                    if (orderListModel.order_cancel == Macro.ORDER_CANCEL_REFUSE) {
                                        cancelTip = "商家拒绝取消订单申请";
                                    }
                                } else if (orderListModel.order_cancel == Macro
                                        .ORDER_CANCEL_APPLY) {
                                    cancelTip = "商家审核取消订单申请";
                                    orderStatusModel.buttons.remove("cancel_order");
                                }
                            }
                            orderStatusModel.cancelTip = cancelTip;
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

            setData(mOrderListAdapter.data);

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
            refreshOrderList(mOrderType);
        }
    }
}
