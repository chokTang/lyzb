package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.ResultActivity;
import com.szy.yishopcustomer.Activity.ResultFreeActivity;
import com.szy.yishopcustomer.Adapter.PayAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.Pay.ConfirmModel;
import com.szy.yishopcustomer.ResponseModel.Pay.Model;
import com.szy.yishopcustomer.ResponseModel.Pay.PaymentItemModel;
import com.szy.yishopcustomer.ResponseModel.Pay.PaymentTipModel;
import com.szy.yishopcustomer.ResponseModel.Pay.ResubmitOrderModel;
import com.szy.yishopcustomer.ResponseModel.SetBalance.ResponseSetBalanceModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;
import me.zongren.pullablelayout.View.PullableRecyclerView;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderPayFragment extends CommonPayFragment implements OnPullListener, TextView
        .OnEditorActionListener {
    @BindView(R.id.fragment_pay_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.fragment_pay_pullableRecyclerView)
    PullableRecyclerView mRecyclerView;
    private String mOrderSn;
    private String mOrderId;
    private String key;
    private LinearLayoutManager mLayoutManager;
    private PayAdapter mPayAdapter;
    private Model mModel;
    private boolean balancePasswordEnable;
    private String balance_password;
    private boolean useBalance;

    @Override
    public String getPayType() {
        return Macro.PAY_TYPE_ORDER;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_pay;
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        mOrderId = arguments.getString(Key.KEY_ORDER_ID.getValue());
        if ((mOrderSn = arguments.getString(Key.KEY_ORDER_SN.getValue())) == null) {
            return;
        }
    }

    public void goResult() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), resultClass);
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), mOrderId);
        startActivity(intent);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
        mRequestQueue.cancelAll();
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
        if(Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_BALANCE:
                mModel.data.user_info.balanceEnabled = !mModel.data.user_info.balanceEnabled;
                changeBalance();
                break;
            case VIEW_TYPE_PAYMENT:
                setSelectedPayment(((PaymentItemModel) mPayAdapter.data.get(position)).code);
                changePayment();
                break;
            case VIEW_TYPE_CONFIRM:
                if (useBalance && balancePasswordEnable) {
                    openInputBalancePassword();
                } else {
                    updateOrder();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, group, savedInstanceState);
        mPullableLayout.topComponent.setOnPullListener(this);

        mPayAdapter = new PayAdapter(getActivity());
        mPayAdapter.onClickListener = this;
        mPayAdapter.onEditorActionListener = this;

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPayAdapter);
        refresh();
        return view;
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_PAY:
                refreshCallback(response);
                break;
            case HTTP_SET_BALANCE:
                changeBalanceCallback(response);
                break;
            case HTTP_SET_PAY_CODE:
                changePaymentCallback(response);
                break;
            case HTTP_RESUBMIT:
                updateOrderCallback(response);
                break;
            case HTTP_PAYMENT:
                getPaymentCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mModel.data.user_info.balanceForTheOrder = view.getText().toString();
            changeBalance();
        }
        return false;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_PAY, HttpWhat.HTTP_PAY.getValue());
        request.add("order_sn", mOrderSn);
        if(TextUtils.isEmpty(mOrderSn)) {
            request.add("id", mOrderId);
        }
        addRequest(request);
    }

    private void changeBalance() {
        CommonRequest request = new CommonRequest(Api.API_USER_SET_PAYMENT, HttpWhat
                .HTTP_SET_BALANCE.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("key", key);
        request.add("order_id", mOrderId);
        request.add("order_sn", mOrderSn);
        request.add("integral_enable", 0);
        request.add("balance_enable", mModel.data.user_info.balanceEnabled ? "1" : "0");
        request.add("balance", mModel.data.user_info.balanceForTheOrder);
        addRequest(request);
    }

    private void changeBalanceCallback(String response) {
        ResponseSetBalanceModel responseSetBalanceModel = JSON.parseObject(response,
                ResponseSetBalanceModel.class);
        if (!Utils.isNull(responseSetBalanceModel.message)) {
            Toast.makeText(getActivity(), responseSetBalanceModel.message, Toast.LENGTH_SHORT).show();
        }
        mModel.data.order.money_paid = responseSetBalanceModel.order.money_pay;
        mModel.data.order.money_paid_format = responseSetBalanceModel.order.money_pay_format;
        mModel.data.user_info.balanceForTheOrder = responseSetBalanceModel.order.balance;
        mModel.data.user_info.balanceForTheOrderFormat = responseSetBalanceModel.order.balance_format;
        setUpAdapterData();
    }

    private void changePayment() {
        CommonRequest request = new CommonRequest(Api.API_USER_SET_PAYMENT, HttpWhat
                .HTTP_SET_PAY_CODE.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("key", key);
        request.add("order_id", mOrderId);
        request.add("order_sn", mOrderSn);
        request.add("integral_enable", 0);
        request.add("balance_enable", mModel.data.user_info.balanceEnabled ? "1" : "0");
        request.add("balance", mModel.data.user_info.balanceForTheOrder);
        request.add("pay_code", getSelectedPayment());
        addRequest(request);
    }

    private void changePaymentCallback(String response) {
        ResponseSetBalanceModel responseSetBalanceModel = JSON.parseObject(response,
                ResponseSetBalanceModel.class);
        mModel.data.order.money_paid = responseSetBalanceModel.order.money_pay;
        mModel.data.order.money_paid_format = responseSetBalanceModel.order.money_pay_format;
        mModel.data.user_info.balanceForTheOrder = responseSetBalanceModel.order.balance;
        mModel.data.user_info.balanceForTheOrderFormat = responseSetBalanceModel.order.balance_format;
        setUpAdapterData();
    }

    private String getSelectedPayment() {
        String payCode = "";
        for (PaymentItemModel paymentItemModel : mModel.data.pay_list) {
            if (paymentItemModel.checked.equals("checked")) {
                payCode = paymentItemModel.code;
            }
        }
        return payCode;
    }

    private void setSelectedPayment(String code) {
        for (PaymentItemModel paymentItemModel : mModel.data.pay_list) {
            paymentItemModel.checked = paymentItemModel.code.equals(code)?"checked":"";
        }
        //setUpAdapterData();
    }

    private void openInputBalancePassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View balancePasswordDialogView = layoutInflater.inflate(R.layout
                .dialog_input_balance_password, null);

        final AlertDialog mBalancePasswordDialog = new AlertDialog.Builder(getActivity()).setView
                (balancePasswordDialogView).create();

        TextView cancel = (TextView) balancePasswordDialogView.findViewById(R.id
                .dialog_balance_password_cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBalancePasswordDialog.dismiss();
            }
        });

        TextView confirm = (TextView) balancePasswordDialogView.findViewById(R.id
                .dialog_bonus_confirm_confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText balancePasswordEditText = (EditText) balancePasswordDialogView
                        .findViewById(R.id.dialog_balance_password_editText);
                String balancePassword = balancePasswordEditText.getText().toString();
                if (Utils.isNull(balancePassword)) {
                    Utils.makeToast(getActivity(), getResources().getString(R.string
                            .hintPleaseEnterBalancePassword));
                } else {
                    balance_password = balancePassword;
                    updateOrder();
                }
            }
        });

        mBalancePasswordDialog.show();
        mBalancePasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
    }

    private void refreshCallback(String response) {
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        mModel = JSON.parseObject(response, Model.class);
        key = mModel.data.key;
        if(mModel.code == 0){
            mModel.data.user_info.balanceForTheOrder = "0";
            mModel.data.order.order_left_money = mModel.data.order.money_paid;

            if("1".equals(mModel.data.order.is_freebuy)) {
                resultClass = ResultFreeActivity.class;
            } else {
                resultClass = ResultActivity.class;
            }

            setUpAdapterData();
            changePayment();
        }else{
            Toast.makeText(getActivity(),mModel.message,Toast.LENGTH_SHORT).show();
            getActivity().finish();
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_ORDER_LIST
                    .getValue()));
        }
    }

    private void setUpAdapterData() {
        mPayAdapter.data.clear();
        mPayAdapter.data.add(mModel.data.order);
        if (mModel.data.user_info.balance != null && Double.parseDouble(mModel.data.user_info
                .balance) > 0) {
            mPayAdapter.data.add(mModel.data.user_info);
        }

        boolean enabled = false;

        if (!Utils.isNull(mModel.data.order.money_paid)&&Double.parseDouble(mModel.data.order.money_paid) > 0) {
            mPayAdapter.data.add(new DividerModel());
            PaymentTipModel paymentTipModel = new PaymentTipModel();
            paymentTipModel.left_money = mModel.data.order.money_paid_format;
            mPayAdapter.data.add(paymentTipModel);
            for (PaymentItemModel paymentItemModel : mModel.data.pay_list) {
                if (Integer.parseInt(paymentItemModel.id) >= 0) {
                    mPayAdapter.data.add(paymentItemModel);
                }
                if (paymentItemModel.checked.equals("checked")) {
                    enabled = true;
                }
            }
        } else {
            enabled = true;
        }
        ConfirmModel confirmModel = new ConfirmModel();
        confirmModel.enabled = enabled;
        useBalance = mModel.data.user_info.balanceEnabled;
        balancePasswordEnable = mModel.data.user_info.balance_password_enable;
        mPayAdapter.data.add(confirmModel);
        mPayAdapter.notifyDataSetChanged();
    }

    private void updateOrder() {
        CommonRequest request = new CommonRequest(Api.API_CHECKOUT_RESUBMIT, HttpWhat
                .HTTP_RESUBMIT.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("key",key);
        request.add("order_id", mOrderId);
        request.add("order_sn", mOrderSn);
        request.add("integral_enable", false);
        request.add("balance_enable", mModel.data.user_info.balanceEnabled ? "1" : "0");
        request.add("balance", mModel.data.user_info.balanceForTheOrder);
        String selectedCode = getSelectedPayment();
        request.add("pay_code",selectedCode);
        if (!Utils.isNull(balance_password)) {
            request.add("balance_password", balance_password);
        }
        addRequest(request);
    }

    private void updateOrderCallback(String response) {
        ResubmitOrderModel model = JSON.parseObject(response, ResubmitOrderModel.class);
        if (model.code == 0) {
            String orderSn = model.order_sn;
            if (Utils.isNull(orderSn)) {
                goResult();
                getActivity().finish();
            } else {
                getPayment(orderSn,getPayType());
            }
        } else {
            Toast.makeText(getContext(), model.message, Toast.LENGTH_SHORT).show();
        }
    }
}
