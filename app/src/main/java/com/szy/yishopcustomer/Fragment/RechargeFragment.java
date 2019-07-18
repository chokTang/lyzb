package com.szy.yishopcustomer.Fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.RechargeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.CashierInputFilter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Recharge.ConfirmModel;
import com.szy.yishopcustomer.ResponseModel.Recharge.Model;
import com.szy.yishopcustomer.ResponseModel.Recharge.PaymentItemModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 16/5/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RechargeFragment extends CommonPayFragment implements TextWatcherAdapter
        .TextWatcherListener {
    private static final String TAG = "RechargeFragment";

    @BindView(R.id.fragment_recharge_recyclerView)
    CommonRecyclerView mRecyclerView;

    private RechargeAdapter mAdapter;
    private Model mModel;
    private String order_sn;

    @Override
    public String getPayType() {
        return Macro.PAY_TYPE_RECHARGE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_recharge;
        mAdapter = new RechargeAdapter(getActivity());
        mAdapter.onCLickListener = this;
        mAdapter.mTextWatcherListener = this;
        new CashierInputFilter();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_PAY_WX_SUCCESS:
                finish();
                break;
            case EVENT_PAY_CANCEL:
                finish();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }


    @Override
    protected void onFinishAliPay() {
        if ("1".equals(mPaySuccess)) {
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
            finish();
        } else {
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
            finish();
        }
    }

    @Override
    void openResultActivity(String orderId) {
//        super.openResultActivity(orderId);
    }

    @Override
    public void onFinishPay() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
//        finish();
    }

    public String getPaymentCode() {
        for (PaymentItemModel paymentItemModel : mModel.data.payment_list) {
            if (paymentItemModel.selected.equals("selected")) {
                return paymentItemModel.pay_code;
            }
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_recharge_item_confirm_button:
                postRecharge();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(view);
                int position = Utils.getPositionOfTag(view);

                switch (viewType) {
                    case VIEW_TYPE_PAYMENT:
                        changePayment(Utils.getExtraInfoOfTag(view));
                        break;
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemDecoration());
        refresh();
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_RECHARGE:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager
                        .HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        mModel = back;
                        setUpModel();
                        setAdapterData();
                    }
                });
                break;
            case HTTP_RECHARGE_POST:
                postRechargeCallback(response);
                break;
            case HTTP_PAYMENT:
                getPaymentCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        mModel.data.model.amount = text;
    }

    public void postRecharge() {
        if (!checkRechargeInput()) {
            Toast.makeText(getContext(), R.string.pleaseEnterRequiredInformation, Toast
                    .LENGTH_SHORT).show();
            return;
        }
        CommonRequest request = new CommonRequest(Api.API_RECHARGE, HttpWhat.HTTP_RECHARGE_POST
                .getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("RechargeModel[amount]", mModel.data.model.amount);
        request.add("RechargeModel[user_note]", mModel.data.model.user_note);
        request.add("RechargeModel[payment_code]", getPaymentCode());
        addRequest(request);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_RECHARGE, HttpWhat.HTTP_RECHARGE
                .getValue());
        addRequest(request);
    }

    private void changePayment(int itemPosition) {
        for (int i = 0; i < mModel.data.payment_list.size(); i++) {
            if (i == itemPosition) {
                mModel.data.payment_list.get(i).selected = "selected";
            } else {
                mModel.data.payment_list.get(i).selected = "";
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private boolean checkRechargeInput() {
        if (!Utils.isNull(mModel.data.model.amount)) {
            return !(Double.parseDouble(mModel.data.model.amount) <= 0);
        } else {
            return false;
        }
    }

    private void postRechargeCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel model) {
                String prefix = "order_sn=";
                int start = model.data.indexOf(prefix);
                order_sn = model.data.substring(start + prefix.length());
                getPayment(order_sn, getPayType());
            }

            @Override
            public void onEmptyData(int state) {
                Utils.toastUtil.showToast(getActivity(), "充值金额不能大于十万");
            }
        });
    }

    private void setAdapterData() {
        mAdapter.data = new ArrayList<>();
        mAdapter.data.add(mModel.data.model);
        mAdapter.data.addAll(mModel.data.payment_list);
        mAdapter.data.add(mModel.data.confirmModel);
        mAdapter.notifyDataSetChanged();
    }

    private void setUpModel() {
        mModel.data.confirmModel = new ConfirmModel();
        int itemPosition = 0;
        for (PaymentItemModel paymentItemModel : mModel.data.payment_list) {
            paymentItemModel.itemPosition = itemPosition;
            if (itemPosition == 0) {
                paymentItemModel.selected = "selected";
            }
            itemPosition++;
        }
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                .State state) {
            RecyclerView.Adapter adapter = parent.getAdapter();
            int childPosition = parent.getChildAdapterPosition(view);
            switch (adapter.getItemViewType(childPosition)) {
                case RechargeAdapter.VIEW_TYPE_CONFIRM:
                    outRect.top = Utils.getPixel(getContext(), 24);
                    break;
                case RechargeAdapter.VIEW_TYPE_RECHARGE:
                    outRect.top = Utils.getPixel(getContext(), 10);
                    //outRect.bottom = Utils.getPixel(getContext(), 10);
                    break;
            }
        }
    }
}
