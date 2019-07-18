package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.Pay.ConfirmModel;
import com.szy.yishopcustomer.ResponseModel.Pay.OrderModel;
import com.szy.yishopcustomer.ResponseModel.Pay.PaymentItemModel;
import com.szy.yishopcustomer.ResponseModel.Pay.PaymentTipModel;
import com.szy.yishopcustomer.ResponseModel.Pay.UserInfoModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Pay.PayBalanceViewHolder;
import com.szy.yishopcustomer.ViewHolder.Pay.PayConfirmViewHolder;
import com.szy.yishopcustomer.ViewHolder.Pay.PayPaymentTipViewHolder;
import com.szy.yishopcustomer.ViewHolder.Pay.PayTopViewHolder;
import com.szy.yishopcustomer.ViewHolder.Pay.PaymentItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PayAdapter extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_TOP = 0;
    /**
     * 用户余额
     */
    public static final int VIEW_TYPE_BALANCE = 1;
    public static final int VIEW_TYPE_PAYMENT = 2;
    public static final int VIEW_TYPE_CONFIRM = 3;
    public static final int VIEW_TYPE_PAYMENET_TIP = 4;
    public static final int VIEW_TYPE_DIVIDER = 5;

    public View.OnClickListener onClickListener;
    public TextView.OnEditorActionListener onEditorActionListener;

    public List<Object> data;
    private Context mContext;

    public PayAdapter(Context context) {
        this.mContext = context;
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TOP:
                return createTopViewHolder(parent);
            case VIEW_TYPE_BALANCE:
                return createBalanceViewHolder(parent);
            case VIEW_TYPE_PAYMENT:
                return createPaymentViewHolder(parent);
            case VIEW_TYPE_CONFIRM:
                return createConfirmViewHolder(parent);
            case VIEW_TYPE_PAYMENET_TIP:
                return createPaymentTipViewHolder(parent);
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TOP:
                bindTopViewHolder((PayTopViewHolder) holder, position);
                break;
            case VIEW_TYPE_BALANCE:
                bindBalanceViewHolder((PayBalanceViewHolder) holder, position);
                break;
            case VIEW_TYPE_PAYMENT:
                bindPaymentViewHolder((PaymentItemViewHolder) holder, position);
                break;
            case VIEW_TYPE_CONFIRM:
                bindConfirmViewHolder((PayConfirmViewHolder) holder, position);
                break;
            case VIEW_TYPE_PAYMENET_TIP:
                bindPaymentTipViewHolder((PayPaymentTipViewHolder) holder, position);
                break;
            case VIEW_TYPE_DIVIDER:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof OrderModel) {
            return VIEW_TYPE_TOP;
        } else if (object instanceof UserInfoModel) {
            return VIEW_TYPE_BALANCE;
        } else if (object instanceof PaymentItemModel) {
            return VIEW_TYPE_PAYMENT;
        } else if (object instanceof ConfirmModel) {
            return VIEW_TYPE_CONFIRM;
        } else if (object instanceof PaymentTipModel) {
            return VIEW_TYPE_PAYMENET_TIP;
        } else if (object instanceof DividerModel) {
            return VIEW_TYPE_DIVIDER;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindBalanceViewHolder(PayBalanceViewHolder holder, int position) {

        UserInfoModel item = (UserInfoModel) data.get(position);

        /***
         * 余额-动态显示
         * 根据用户余额数值 显示or隐藏 余额支付
         */

        if (Double.parseDouble(item.balance) > 0) {
            holder.mRelativeLayout_Balance.setVisibility(View.VISIBLE);
        } else {
            holder.mRelativeLayout_Balance.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_BALANCE);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);


        if (!Utils.isNull(item.balanceForTheOrder) && item.balanceForTheOrder != null && Double.parseDouble(item.balanceForTheOrder) > 0) {
            holder.editText.setText(item.balanceForTheOrder);
        }

        holder.editText.setOnEditorActionListener(onEditorActionListener);
        Utils.setViewTypeForTag(holder.editText, ViewType.VIEW_TYPE_BALANCE);
        Utils.setPositionForTag(holder.editText, position);
        holder.availableBalanceTextView.setText(Utils.formatMoney(holder.availableBalanceTextView.getContext(), item.balance));
        holder.moneyTextView.setText(item.balanceForTheOrderFormat);

        if (item.balanceEnabled) {
            holder.payBalanceTextView.setVisibility(View.VISIBLE);
            holder.moneyTextView.setVisibility(View.VISIBLE);
            holder.checkboxImageView.setImageResource(R.mipmap.bg_switch_on);

            //holder.editText.setVisibility(View.VISIBLE);
            //holder.unitTextView.setVisibility(View.VISIBLE);
            //holder.minusTextView.setVisibility(View.VISIBLE);
        } else {
            holder.payBalanceTextView.setVisibility(View.GONE);
            holder.moneyTextView.setVisibility(View.GONE);
            holder.checkboxImageView.setImageResource(R.mipmap.bg_switch_off);

            //holder.editText.setVisibility(View.GONE);
            //holder.unitTextView.setVisibility(View.GONE);
            //holder.minusTextView.setVisibility(View.GONE);
        }
    }

    private void bindConfirmViewHolder(PayConfirmViewHolder holder, int position) {
        ConfirmModel item = (ConfirmModel) data.get(position);
        holder.button.setEnabled(item.enabled);
        holder.button.setText(holder.button.getContext().getResources().getString(R.string.buttonPayRightNow));
        Utils.setViewTypeForTag(holder.button, ViewType.VIEW_TYPE_CONFIRM);
        holder.button.setOnClickListener(onClickListener);
        Utils.setPositionForTag(holder.button, position);
    }

    private void bindPaymentTipViewHolder(PayPaymentTipViewHolder holder, int position) {
        PaymentTipModel item = (PaymentTipModel) data.get(position);
        SpannableStringBuilder style = null;
        style = Utils.spanStringColor("剩余应付金额" + item.left_money + "请选择以下支付方式支付", ContextCompat.getColor(holder.leftMoney
                .getContext(), R.color.colorPrimary), 6, item.left_money.length());
        holder.leftMoney.setText(style);
    }

    private void bindPaymentViewHolder(PaymentItemViewHolder holder, int position) {

        PaymentItemModel item = (PaymentItemModel) data.get(position);

        //微信支付 推荐图标
        Drawable drawableRight = mContext.getResources().getDrawable(R.mipmap.wx_pay_reom);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());

        switch (item.code) {
            case Macro.COD_CODE:
                holder.paymentImage.setImageResource(R.mipmap.btn_review);
                break;
            case Macro.ALIPAY_CODE:
                holder.paymentImage.setImageResource(R.mipmap.ic_alipay);
                break;
            case Macro.WEIXIN_CODE:
                holder.paymentImage.setImageResource(R.mipmap.ic_wechat);
                holder.wxpayHintText.setVisibility(View.VISIBLE);
                holder.nameTextView.setCompoundDrawables(null, null, drawableRight, null);
                break;
            case Macro.UNIONPAY_CODE:
                holder.paymentImage.setImageResource(R.mipmap.ic_bank_card);
                break;
        }

        holder.nameTextView.setText(item.name);

        if (item.checked.equals("checked")) {
            holder.checkboxImage.setSelected(true);
        } else {
            holder.checkboxImage.setSelected(false);
        }
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_PAYMENT);
        holder.itemView.setOnClickListener(onClickListener);
        Utils.setPositionForTag(holder.itemView, position);
    }

    private void bindTopViewHolder(PayTopViewHolder holder, int position) {
        OrderModel item = (OrderModel) data.get(position);
        holder.orderTextView.setText(item.order_sn);
        holder.moneyTextView.setText(Utils.formatMoney(holder.moneyTextView.getContext(), item
                .order_left_money));
/*        holder.moneyTextView.setText(
                Utils.formatMoney(holder.moneyTextView.getContext(), item.order_amount));*/
    }

    private RecyclerView.ViewHolder createBalanceViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_pay_balance, parent, false);
        return new PayBalanceViewHolder(view);
    }

    private RecyclerView.ViewHolder createConfirmViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_pay_confirm, parent, false);
        return new PayConfirmViewHolder(view);
    }

    private RecyclerView.ViewHolder createPaymentTipViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_pay_payment_tip, parent, false);
        return new PayPaymentTipViewHolder(view);
    }

    private RecyclerView.ViewHolder createPaymentViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_pay_payment, parent, false);
        return new PaymentItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createTopViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_pay_top, parent, false);
        return new PayTopViewHolder(view);
    }

    private RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_divider_one, parent, false);
        return new DividerViewHolder(view);
    }
}
