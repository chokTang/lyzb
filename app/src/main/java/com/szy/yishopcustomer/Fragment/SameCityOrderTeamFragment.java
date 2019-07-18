package com.szy.yishopcustomer.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Adapter.OrderShopListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderListResult;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import me.zongren.pullablelayout.Constant.Side;

/**
 * 团购订单
 * Created by Administrator on 2018/6/11.
 */

public class SameCityOrderTeamFragment extends YSCBaseFragment {

    private View mView;
    private RadioGroup type_rg;
    private CommonRecyclerView recy_same_city_order;

    private final static int RERESH = 0x255;
    private final static int ONLOADMORE = 0x256;
    private OrderShopListAdapter mAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int type = 0;// 0(or undefined):全部;1:待付款;2:待使用;3:待评价;4:退款;

    private Intent mIntent;
    private SameCityOrderFragment parentFragment;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (recy_same_city_order.reachEdgeOfSide(Side.BOTTOM)) {
                    getOrderList(type, false);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentFragment = (SameCityOrderFragment) getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_samecity_order_team, container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type_rg = findViewById(R.id.type_rg);
        recy_same_city_order = findViewById(R.id.recy_same_city_order);

        recy_same_city_order.setEmptyViewClickListener(new OnEmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked() {
                parentFragment.onHome();
            }

            @Override
            public void onOfflineViewClicked() {

            }
        });

        mAdapter = new OrderShopListAdapter(getContext());
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        recy_same_city_order.setLayoutManager(mLinearLayoutManager);
        recy_same_city_order.setAdapter(mAdapter);
        recy_same_city_order.addOnScrollListener(mOnScrollListener);

        type_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //全部
                    case R.id.tv_same_city_order_all:
                        type = 0;
                        break;
                    //待付款
                    case R.id.tv_same_city_order_payment:
                        type = 1;
                        break;
                    //待使用
                    case R.id.tv_same_city_order_use:
                        type = 2;
                        break;
                    //待评价
                    case R.id.tv_same_city_order_evaluate:
                        type = 3;
                        break;
                    //退款
                    case R.id.tv_same_city_order_refund:
                        type = 4;
                        break;
                }
                getOrderList(type, true);
            }
        });

        mAdapter.setListener(new OrderShopListAdapter.IOrderClickStatus() {

            //店铺跳转
            @Override
            public void onLookStore(OrderModel orderModel, int positions) {
                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(),
                        Api.H5_CITYLIFE_URL + "/shopIndex?storeId=" + orderModel.getStoreId());
                startActivity(mIntent);
            }

            //订单详情
            @Override
            public void onOrderDetail(OrderModel orderModel, int positions) {

                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(),
                        Api.H5_CITYLIFE_URL + "/orderdetail?orderId=" + orderModel.getOrderId()
                                + "&storeId=" + orderModel.getStoreId() + "&prodName=" + orderModel.getStoreName() + "&orderProdLogo=" + orderModel.getShopLogo());
                startActivity(mIntent);
            }

            @Override
            public void onCancleOrder(final OrderModel orderModel, final int positions) {

                //取消订单
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否取消该订单?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cancleOrder(orderModel, positions);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }

            @Override
            public void onPayOrder(OrderModel orderModel, int positions) {


                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                //立即付款
                mIntent.putExtra(Key.KEY_URL.getValue(),
                        Api.H5_CITYLIFE_URL + "/payMethod?orderId=" + orderModel.getOrderId() + "&orderSource=1&fromType=1");
                startActivity(mIntent);
            }

            @Override
            public void onLoadMa(OrderModel orderModel, int positions) {

                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                //查看核销码
                mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/orderdetail?orderId=" + orderModel.getOrderId());
                startActivity(mIntent);
            }

            @Override
            public void onOrderTuiKuaiDetail(OrderModel orderModel, int positions) {

                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                //退款详情
                mIntent.putExtra(Key.KEY_URL.getValue(),
                        Api.H5_CITYLIFE_URL + "/refund?orderId=" + orderModel.getOrderId());
                startActivity(mIntent);
            }

            @Override
            public void onDeteleOrder(final OrderModel orderModel, final int positions) {

                //删除订单
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除该订单?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteOrder(orderModel.getOrderId(), positions);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }

            @Override
            public void onPingLunOrder(OrderModel model, int positions) {
                String shopId="";
                if (model.getGoodsList()!=null&&model.getGoodsList().size()>0){
                    shopId=model.getGoodsList().get(0).getProdId();
                }
                Intent mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                //评价
                mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/comment?orderId="
                        + model.getOrderId()+"&storeId="+model.getStoreId()+"&prodId="+shopId+"&orderCode="+model.getOrderCode());
                startActivity(mIntent);
            }

            @Override
            public void onOrderPingLunDetail(OrderModel orderModel, int positions) {

                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(),
                        Api.H5_CITYLIFE_URL + "/userCommentList?orderId=" + orderModel.getOrderId());

                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                getOrderList(type, true);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        super.onRequestSucceed(what, response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SIGN_OFF:
                App.loginOut(response, getActivity());
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        super.onRequestFailed(what, response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SIGN_OFF:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    /**
     * 获取订单数据
     *
     * @param type      0(or undefined):全部;1:待付款;2:待使用;3:待评价;4:退款;
     * @param isRefresh
     */
    public void getOrderList(final int type, final boolean isRefresh) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_ORDER_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_ORDER_LIST, "GET");

        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        int orderType;
        if (isRefresh) {
            orderType = RERESH;
        } else {
            orderType = ONLOADMORE;
        }
        request.add("orderMent", 1);
        request.add("orderStatus", type);
        request.add("pageNum", pageIndex);
        request.add("pageSize", pageSize);
        requestQueue.add(orderType, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject object = JSON.parseObject(response.get());
                OrderListResult result = JSON.parseObject(response.get(), OrderListResult.class);
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    if (result == null) {
                        return;
                    }
                    recy_same_city_order.hideEmptyView();
                    switch (what) {
                        //刷新
                        case RERESH:
                            mAdapter.udpateList(result.getList());
                            break;
                        //加载更多
                        case ONLOADMORE:
                            mAdapter.addAll(result.getList());
                            break;
                        default:
                            break;
                    }
                    if (type == 0) {
                        if (mAdapter.getData().size() == 0) {
                            //暂无订单数据  视图提示
                            recy_same_city_order.setEmptyButton(R.string.goShopping);
                            recy_same_city_order.setEmptyImage(R.mipmap.bg_public);
                            recy_same_city_order.setEmptyTitle(R.string.emptyOrderListTitle);
                            recy_same_city_order.setEmptySubtitle(R.string.emptyOrderListSubtitle);
                            recy_same_city_order.showEmptyView();
                        }
                    } else {
                        if (mAdapter.getData().size() == 0) {
                            //暂无订单数据  视图提示
                            recy_same_city_order.setEmptyImage(R.mipmap.bg_public);
                            recy_same_city_order.setEmptyTitle(R.string.order_type_empty);
                            recy_same_city_order.setEmptySubtitle(R.string.order_type_button);
                            recy_same_city_order.showEmptyView();
                        }
                    }
                } else if (response.responseCode() == Utils.CITY_NET_LOGIN) {
                    //登录状态失效
                    Toast.makeText(getActivity(), R.string.loginout_hint, Toast.LENGTH_SHORT).show();
                    logOut();
                } else {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                switch (what) {
                    case RERESH:
                    case ONLOADMORE:
                        recy_same_city_order.showOfflineView();
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

    //取消订单
    private void cancleOrder(final OrderModel orderModel, final int position) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_CANCLE_ORDER, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_CANCLE_ORDER, "POST");

        JSONObject json_post = new JSONObject();
        json_post.put("orderId", orderModel.getOrderId());
        request.setDefineRequestBodyForJson(json_post.toJSONString());

        requestQueue.add(101, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == 200) {
                    Toast.makeText(getActivity(), "订单取消成功", Toast.LENGTH_SHORT).show();
                    orderModel.setOrderStatus(7);
                    mAdapter.onUpdataPosition(position, orderModel);
//                    getOrderList(type, true);
                } else {
                    Toast.makeText(getActivity(), "订单取消失败", Toast.LENGTH_SHORT).show();
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

    private void deleteOrder(String orderId, final int position) {
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

                    mAdapter.onDelete(position);
//                    getOrderList(type, true);
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

    private void logOut() {
        CommonRequest request = new CommonRequest(Api.API_SIGN_OFF, HttpWhat.HTTP_SIGN_OFF.getValue(), RequestMethod.POST);
        addRequest(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isLogin()) {
            getOrderList(type, true);
        }
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
}
