package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.GiftCardPickUpActivity;
import com.szy.yishopcustomer.Activity.GiftCardsActivity;
import com.szy.yishopcustomer.Activity.IngotListActivity;
import com.szy.yishopcustomer.Activity.IntegralMallActivity;
import com.szy.yishopcustomer.Activity.OrderListActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Activity.UserIntegralActivity;
import com.szy.yishopcustomer.Adapter.ResultAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.Result.ConfirmModel;
import com.szy.yishopcustomer.ResponseModel.Result.Model;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.View.PullableRecyclerView;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ResultFragment extends YSCBaseFragment implements OnPullListener {

    @BindView(R.id.fragment_result_pullableRecyclerView)
    PullableRecyclerView mPullableRecyclerView;

    private String orderId;
    private ResultAdapter mResultAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mPaySuccess;
    private String payType;
    private String key;
    private String order_sn;

    private List<GuessGoodsModel> mGoodsModelList = new ArrayList<>();

    //积分兑换商品
    private boolean exchange;

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_UNPAID_ORDER_LIST:
                openUnpaidOrderList();
                break;
            case VIEW_TYPE_ORDER_LIST:
                openOrderList();
                break;
            case VIEW_TYPE_SEE_ORDER:
                //查看提货单
                openPickupOrder();
                break;
            case VIEW_TYPE_CONTINUE_PICKUP:
                //继续提货
                openGiftCardPickUpActivity();
                break;
            case VIEW_TYPE_CONTINUE_EXCHANGE:
                //继续兑换
                openIntegralMallActivity();
                break;
            case VIEW_TYPE_CONTINUE_SHOPPING:
                openIndex();
                break;
            case VIEW_TYPE_GROUPON_DETAIL:
                ConfirmModel confirmModel = (ConfirmModel) mResultAdapter.data.get(position);
                openUserGroupOnDetail(confirmModel.group_sn, 0);
                break;
            case VIEW_LOOK_INGOT:
                /**查看元宝**/
                startActivity(new Intent(getActivity(), IngotListActivity.class));
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_RESULT:
                refreshCallback(response);
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        super.onRequestFailed(what, response);
        switch (HttpWhat.valueOf(what)) {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_result;

        Bundle arguments = getArguments();
        if (arguments != null) {
            orderId = arguments.getString(Key.KEY_ORDER_ID.getValue());
            mPaySuccess = arguments.getString(Key.KEY_ORDER_PAY_SUCCESS.getValue());
            payType = arguments.getString(Key.KEY_ORDER_PAY_TYPE.getValue());
            order_sn = arguments.getString(Key.KEY_ORDER_SN.getValue());
            exchange = arguments.getBoolean(Key.KEY_EXCHANGE.getValue());
            key = arguments.getString(Key.KEY_APP_KEY.getValue());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mResultAdapter = new ResultAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getContext());

        mPullableRecyclerView.setLayoutManager(mLayoutManager);
        mPullableRecyclerView.setAdapter(mResultAdapter);
        mResultAdapter.onClickListener = this;

        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_GOODS.getValue()));
        refresh();

        /**猜你喜欢 data*/
        getLikeData();

        return view;
    }

    @Override
    public void refresh() {

        CommonRequest request = new CommonRequest(getApi(), HttpWhat.HTTP_RESULT.getValue());

        //从结算页支付跳到支付结果需要以下两个参数
        if (Utils.isNull(orderId)) {
            request.add("order_sn", order_sn);
            if (!Utils.isNull(key)) {
                request.add("key", key);
            }
        }

        request.add("is_success", mPaySuccess);
        addRequest(request);
    }

    private void getLikeData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_GUESS_LIKE_URL, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        request.add("user_id", App.getInstance().userId);
        request.add("cur_page", 1);
        request.add("page_size", 24);
        requestQueue.add(HttpWhat.HTPP_LIKE_GOOD.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                try {
                    int code = JSONObject.parseObject(response.get()).getInteger("code");
                    String message = JSONObject.parseObject(response.get()).getString("message");
                    JSONObject object_data = JSONObject.parseObject(response.get()).getJSONObject("data");

                    if (object_data != null) {
                        if (code == 0) {
                            mGoodsModelList = JSON.parseArray(object_data.getString("list"), GuessGoodsModel.class);
                            if (mGoodsModelList.size() > 0) {
                                mResultAdapter.mLikeDataModels = mGoodsModelList;
                                mResultAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
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


    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                mResultAdapter.buy_type = model.data.order.buy_type;


                if (exchange) {
                    model.data.order.is_pay = model.data.order.pay_status;
                }

                mResultAdapter.data.clear();
                mResultAdapter.data.add(model.data.order);
                //mResultAdapter.data.addAll(model.data.order_list);
                ConfirmModel confirmModel = new ConfirmModel();
                confirmModel.payStatus = model.data.order.is_pay;
                confirmModel.is_cod = model.data.order.is_cod;
                confirmModel.pay_type = payType;
                confirmModel.group_sn = model.data.group_sn;

                mResultAdapter.data.add(confirmModel);

                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_INVENTORY.getValue()));
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_CHECKOUT_FINISH.getValue()));

//                if (model.data.order.buy_type != Macro.EXCHANGE || model.data.order.buy_type != Macro.GIFT) {
//                    if (model.data.order.is_pay == 1) {
//                        /*** 支付成功 **/
//                    }
//                }


                mResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                getActivity().finish();
            }
        }, true);
    }

    private void bringRootToFront() {
        Intent intent = new Intent(getContext(), RootActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private String getApi() {
        if (!exchange) {
            if (orderId != null) {
                //return Api.API_RESULT_PAY_AGAIN + "?order_id=" + orderId;
                return Api.API_RESULT_PAY_AGAIN + "?id=" + orderId;
            } else {
                return Api.API_RESULT;
            }
        } else {
            return Api.API_INTEGRAL_RESULT_PAY_AGAIN;
        }
    }

    private void openIndex() {
        bringRootToFront();
        CommonEvent event = new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue(), this.getClass
                ().getSimpleName());
        EventBus.getDefault().post(event);
    }

    private void openPickupOrder() {
        Intent intent = new Intent(getContext(), GiftCardsActivity.class);
        startActivity(intent);
    }

    //
    private void openGiftCardPickUpActivity() {
        Intent intent = new Intent(getContext(), GiftCardPickUpActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void openIntegralMallActivity() {
        Intent intent = new Intent(getContext(), IntegralMallActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void openOrderList() {
        /*bringRootToFront();
        CommonEvent event = new CommonEvent(EventWhat.EVENT_AWAIT_SHIP_ORDER_LIST.getValue(),
                this.getClass().getSimpleName());
        EventBus.getDefault().post(event);*/
        openOrderListActivity(Macro.ORDER_TYPE_AWAIT_SHIPPED);
        finish();
    }

    private void openUnpaidOrderList() {
        /*bringRootToFront();
        CommonEvent event = new CommonEvent(EventWhat.EVENT_UNPAID_ORDER_LIST.getValue(),
                this.getClass().getSimpleName());
        EventBus.getDefault().post(event);*/
        openOrderListActivity(Macro.ORDER_TYPE_UNPAID);
    }

    public void openOrderListActivity(final String status) {
        if (exchange) {
            Intent intent = new Intent(getContext(), UserIntegralActivity.class);
            intent.putExtra(Key.KEY_PAGE.getValue(), 2);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), OrderListActivity.class);
            intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), status);
            startActivity(intent);
        }
        finish();

    }

    private void openUserGroupOnDetail(String groupSn, int status) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.putExtra(Key.KEY_GROUPON_STATUS.getValue(), status);
        intent.setClass(getActivity(), UserGroupOnDetailActivity.class);
        startActivity(intent);
    }
}
