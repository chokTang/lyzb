package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.CashierInputFilter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Recharge.ConfirmModel;
import com.szy.yishopcustomer.ResponseModel.Recharge.PaymentItemModel;
import com.szy.yishopcustomer.ResponseModel.Recharge.RechargeModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Pay.PaymentItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.Recharge.RechargeConfirmViewHolder;
import com.szy.yishopcustomer.ViewHolder.Recharge.RechargeInputViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/7/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RechargeAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_CONFIRM = 0;
    public static final int VIEW_TYPE_PAYMENT = 1;
    public static final int VIEW_TYPE_RECHARGE = 2;

    public List<Object> data;
    public View.OnClickListener onCLickListener;
    public TextWatcherAdapter.TextWatcherListener mTextWatcherListener;

    private Context mContext;

    public RechargeAdapter(Context context) {
        this.mContext=context;
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CONFIRM:
                return createConfirmViewHolder(parent);
            case VIEW_TYPE_RECHARGE:
                return createInputViewHolder(parent);
            case VIEW_TYPE_PAYMENT:
                return createPaymentViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_CONFIRM:
                bindConfirmViewHolder((RechargeConfirmViewHolder) holder, position);
                break;
            case VIEW_TYPE_PAYMENT:
                //bindPaymentViewHolder((RechargePaymentViewHolder) holder, position);
                bindPaymentViewHolder((PaymentItemViewHolder) holder, position);
                break;
            case VIEW_TYPE_RECHARGE:
                bindInputViewHolder((RechargeInputViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ConfirmModel) {
            return VIEW_TYPE_CONFIRM;
        } else if (object instanceof PaymentItemModel) {
            return VIEW_TYPE_PAYMENT;
        } else if (object instanceof RechargeModel) {
            return VIEW_TYPE_RECHARGE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindConfirmViewHolder(RechargeConfirmViewHolder holder, int position) {
        holder.confirmButton.setOnClickListener(onCLickListener);
    }

    private void bindInputViewHolder(RechargeInputViewHolder holder, int position) {
        RechargeModel rechargeModel = (RechargeModel) data.get(position);
        InputFilter[] filters = {new CashierInputFilter()};
        holder.editText.setFilters(filters);
        holder.editText.setTextWatcherListener(mTextWatcherListener);
    }

    /*private void bindPaymentViewHolder(RechargePaymentViewHolder holder, int position) {
        PaymentItemModel paymentItemModel = (PaymentItemModel) data.get(position);
        switch (paymentItemModel.pay_code) {
            case Macro.ALIPAY_CODE:
                holder.iconImageView.setImageResource(R.mipmap.ic_alipay);
                break;
            case Macro.WEIXIN_CODE:
                holder.iconImageView.setImageResource(R.mipmap.ic_wechat);
                break;
            case Macro.UNIONPAY_CODE:
                holder.iconImageView.setImageResource(R.mipmap.ic_bank_card);
                break;
        }
        if (paymentItemModel.selected != null && paymentItemModel.selected.equals("selected")) {
            holder.paymentImageView.setImageResource(R.mipmap.bg_check_selected);
        } else {
            holder.paymentImageView.setImageResource(R.mipmap.bg_check_normal);
        }
        holder.nameTextView.setText(paymentItemModel.pay_name);
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setExtraInfoForTag(holder.itemView, paymentItemModel.itemPosition);
        holder.itemView.setOnClickListener(onCLickListener);
    }*/

    private void bindPaymentViewHolder(PaymentItemViewHolder holder, int position) {
        PaymentItemModel paymentItemModel = (PaymentItemModel) data.get(position);

        Drawable drawableRight = mContext.getResources().getDrawable(R.mipmap.wx_pay_reom);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());

        switch (paymentItemModel.pay_code) {
            case Macro.COD_CODE:
                holder.paymentImage.setImageResource(R.mipmap.btn_review);
                break;
            case Macro.ALIPAY_CODE:
                holder.paymentImage.setImageResource(R.mipmap.ic_alipay);
                break;
            case Macro.WEIXIN_CODE:
                holder.paymentImage.setImageResource(R.mipmap.ic_wechat);

                holder.nameTextView.setCompoundDrawables(null, null, drawableRight, null);
                holder.wxpayHintText.setVisibility(View.VISIBLE);
                break;
            case Macro.UNIONPAY_CODE:
                holder.paymentImage.setImageResource(R.mipmap.ic_bank_card);
                break;
        }

        holder.nameTextView.setText(paymentItemModel.pay_name);

        if (paymentItemModel.selected != null && paymentItemModel.selected.equals("selected")) {
            holder.checkboxImage.setEnabled(true);
        } else {
            holder.checkboxImage.setEnabled(false);
        }

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_PAYMENT);
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setExtraInfoForTag(holder.itemView, paymentItemModel.itemPosition);
        holder.itemView.setOnClickListener(onCLickListener);
    }

    private RecyclerView.ViewHolder createConfirmViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_recharge_item_confirm, parent, false);
        return new RechargeConfirmViewHolder(view);
    }

    private RecyclerView.ViewHolder createInputViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_recharge_item_input, parent, false);
        return new RechargeInputViewHolder(view);
    }

    private RecyclerView.ViewHolder createPaymentViewHolder(ViewGroup parent) {
/*        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_recharge_item_payment, parent, false);
        return new RechargePaymentViewHolder(view);        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_pay_payment, parent, false);
        return new PaymentItemViewHolder(view);
    }
}
