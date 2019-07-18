package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.*;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.Result.ConfirmModel;
import com.szy.yishopcustomer.ResponseModel.Result.OrderListModel;
import com.szy.yishopcustomer.ResponseModel.Result.OrderModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Result.ConfirmViewHolder;
import com.szy.yishopcustomer.ViewHolder.Result.OrderViewHolder;
import com.szy.yishopcustomer.ViewHolder.Result.TopViewHolder;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ResultAdapter extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_CONFIRM = 0;
    public static final int VIEW_TYPE_TOP = 1;
    public static final int VIEW_TYPE_ORDER = 2;

    public View.OnClickListener onClickListener;
    public List<Object> data;
    public List<GuessGoodsModel> mLikeDataModels;

    public int buy_type;

    private Context mContext;

    public ResultAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TOP:
                return createTopViewHolder(parent);
            case VIEW_TYPE_ORDER:
                return createOrderViewHolder(parent);
            case VIEW_TYPE_CONFIRM:
                return createConfirmViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TOP:
                bindTopViewHolder((TopViewHolder) holder, position);
                break;
            case VIEW_TYPE_ORDER:
                bindOrderViewHolder((OrderViewHolder) holder, position);
                break;
            case VIEW_TYPE_CONFIRM:
                bindConfirmViewHolder((ConfirmViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof OrderModel) {
            return VIEW_TYPE_TOP;
        } else if (object instanceof OrderListModel) {
            return VIEW_TYPE_ORDER;
        } else if (object instanceof ConfirmModel) {
            return VIEW_TYPE_CONFIRM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindConfirmViewHolder(ConfirmViewHolder holder, int position) {
        ConfirmModel confirmModel = (ConfirmModel) data.get(position);
        //非货到付款
        if (confirmModel.is_cod != 1) {
            //支付成功
            //结算页的支付结果
            if (confirmModel.pay_type.equals(Macro.PAY_TYPE_RECHARGE)) {
                holder.descriptionTextView.setVisibility(View.INVISIBLE);
                holder.mWrapperLinearLayout2.setVisibility(View.VISIBLE);
                holder.mWrapperLinearLayout.setVisibility(View.INVISIBLE);

                Utils.setViewTypeForTag(holder.continueShoppingButton2, ViewType
                        .VIEW_TYPE_CONTINUE_SHOPPING);
            } else {
                holder.descriptionTextView.setVisibility(View.VISIBLE);
                holder.mWrapperLinearLayout.setVisibility(View.VISIBLE);
                holder.mWrapperLinearLayout2.setVisibility(View.INVISIBLE);
            }

            if (confirmModel.payStatus == Macro.PS_PAID) {
                if (buy_type == Macro.GIFT) {
                    holder.orderListButton.setText("查看提货单");
                    holder.continueShoppingButton.setText("继续提货");

                    Utils.setViewTypeForTag(holder.orderListButton, ViewType
                            .VIEW_TYPE_SEE_ORDER);
                    Utils.setPositionForTag(holder.orderListButton, position);
                    Utils.setViewTypeForTag(holder.continueShoppingButton, ViewType
                            .VIEW_TYPE_CONTINUE_PICKUP);

                    holder.descriptionTextView.setText("温馨提示：提货的商品已在处理中，我们将尽快为您发货!");

                } else if (buy_type == Macro.EXCHANGE) {

                    holder.orderListButton.setText("查看兑换单");
                    holder.continueShoppingButton.setText("继续兑换");

                    Utils.setViewTypeForTag(holder.orderListButton, ViewType
                            .VIEW_TYPE_ORDER_LIST);
                    Utils.setPositionForTag(holder.orderListButton, position);
                    Utils.setViewTypeForTag(holder.continueShoppingButton, ViewType
                            .VIEW_TYPE_CONTINUE_EXCHANGE);

                    holder.descriptionTextView.setText("兑换商品已在处理中，我们将尽快为您发货！");

                } else {
                    if (!Utils.isNull(confirmModel.group_sn)) {
                        holder.orderListButton.setText("查看拼团详情");
                        Utils.setViewTypeForTag(holder.orderListButton, ViewType
                                .VIEW_TYPE_GROUPON_DETAIL);
                        Utils.setPositionForTag(holder.orderListButton, position);
                    } else {
                        holder.orderListButton.setText(R.string.buttonGoToOrderList);
                        Utils.setViewTypeForTag(holder.orderListButton, ViewType.VIEW_TYPE_ORDER_LIST);
                    }

                    Utils.setViewTypeForTag(holder.continueShoppingButton, ViewType
                            .VIEW_TYPE_CONTINUE_SHOPPING);
                    holder.descriptionTextView.setText(R.string.theGoodsIsBeingProcessed);

                    //猜你喜欢 recy
                    GuessLikeAdapter mLikeAdapter = new GuessLikeAdapter(mContext);
                    holder.mRecyclerView_Like.setAdapter(mLikeAdapter);

                    //猜你喜欢数据 适配
                    holder.mTextView_LikeTitle.setVisibility(View.VISIBLE);
                    holder.mRecyclerView_Like.setVisibility(View.VISIBLE);

                    if (mLikeDataModels != null) {
                        mLikeAdapter.data.addAll(mLikeDataModels);
                    }
                    mLikeAdapter.notifyDataSetChanged();
                }
            } else {//未成功
                holder.orderListButton.setText(R.string.buttonGoToOrderListAndPay);
                Utils.setViewTypeForTag(holder.orderListButton, ViewType
                        .VIEW_TYPE_UNPAID_ORDER_LIST);
                Utils.setViewTypeForTag(holder.continueShoppingButton, ViewType
                        .VIEW_TYPE_CONTINUE_SHOPPING);
                holder.descriptionTextView.setText(R.string.theFollowingOrderNotPayed);
            }
        } else {
            //货到付款
            Utils.setViewTypeForTag(holder.orderListButton, ViewType.VIEW_TYPE_ORDER_LIST);
            Utils.setViewTypeForTag(holder.continueShoppingButton, ViewType
                    .VIEW_TYPE_CONTINUE_SHOPPING);
            holder.descriptionTextView.setText(R.string.theGoodsIsBeingProcessed);
        }

        holder.orderListButton.setOnClickListener(onClickListener);
        holder.continueShoppingButton.setOnClickListener(onClickListener);
        holder.continueShoppingButton2.setOnClickListener(onClickListener);
    }

    private void bindOrderViewHolder(OrderViewHolder holder, int position) {
        OrderListModel orderModel = (OrderListModel) data.get(position);
        holder.orderTextView.setText(orderModel.order_sn);
        String format = holder.moneyTextView.getContext().getString(R.string.formatOrderMoney);
        holder.moneyTextView.setText(String.format(format, orderModel.order_amount));
    }

    private void bindTopViewHolder(TopViewHolder holder, int position) {
        OrderModel commonModel = (OrderModel) data.get(position);

        if (commonModel.is_cod == 0) {
            //非货到付款
            if (commonModel.is_pay == Macro.PS_PAID) {
                if (buy_type == Macro.EXCHANGE || (buy_type == Macro.GIFT)) {
                    if (buy_type == Macro.GIFT) {
                        holder.imageView.setImageResource(R.mipmap.ic_pay_success);
                        holder.resultTextView.setText("感谢您，提货成功！");
                        holder.moneyLabelTextView.setVisibility(View.GONE);
                        holder.moneyTextView.setVisibility(View.GONE);
                    } else {
                        holder.imageView.setImageResource(R.mipmap.ic_pay_success);
                        holder.resultTextView.setText("感谢您，兑换成功！");
                        holder.moneyLabelTextView.setVisibility(View.GONE);
                        holder.moneyTextView.setVisibility(View.GONE);
                    }

                    try {
                        holder.linearlayout_order_info.setVisibility(View.VISIBLE);
                        holder.imageView_order_sn.setImageBitmap(CodeUtils.createImage(commonModel.order_sn, 400, 400, null));
                        holder.textView_order_id.setText(commonModel.order_sn);
                        holder.textView_time.setText(Utils.times(commonModel.add_time));
                    } catch (Exception e) {
                    }

                } else {
                    /**支付成功**/
                    //holder.imageView.setImageResource(R.mipmap.register_success);
                    GlideApp.with(holder.imageView.getContext())
                            .load(R.mipmap.pay_success_gf)
                            .into(holder.imageView);

                    holder.resultTextView.setText(R.string.payResultSuccess);
                    holder.moneyLabelTextView.setVisibility(View.VISIBLE);
                    holder.moneyTextView.setVisibility(View.VISIBLE);
                    holder.moneyTextView.setText(commonModel.money_paid + "");

                    holder.text_cons_ingot.setVisibility(View.VISIBLE);
                    holder.text_cons_ingot.setText("消费元宝:" + commonModel.integral);

                    holder.mLayout_pay_sucess.setVisibility(View.VISIBLE);

                    Utils.setViewTypeForTag(holder.text_look_ingot, ViewType.VIEW_LOOK_INGOT);
                    holder.text_look_ingot.setOnClickListener(onClickListener);

                    String ingot_text = "恭喜你!获得" + "<font color='#ff0000'>" + commonModel.give_integral + "</font>"
                            + "元宝,当前可用元宝" + "<font color='#ff0000'>" + commonModel.total_integral + "</font>";


                    holder.text_pay_ingot.setText(Html.fromHtml(ingot_text));//共计元宝 可用元宝
                }
            } else {
                if (buy_type == Macro.EXCHANGE || (buy_type == Macro.GIFT)) {
                    if (buy_type == Macro.GIFT) {
                        holder.imageView.setImageResource(R.mipmap.ic_pay_failed);
                        holder.resultTextView.setText("提货失败！");
                        holder.moneyLabelTextView.setVisibility(View.GONE);
                        holder.moneyTextView.setVisibility(View.GONE);
                    } else {
                        holder.imageView.setImageResource(R.mipmap.ic_pay_failed);
                        holder.resultTextView.setText("兑换失败！");
                        holder.moneyLabelTextView.setVisibility(View.GONE);
                        holder.moneyTextView.setVisibility(View.GONE);
                    }
                } else {
                    holder.imageView.setImageResource(R.mipmap.ic_pay_failed);
                    holder.resultTextView.setText(R.string.payResultFail);
                    holder.moneyLabelTextView.setVisibility(View.VISIBLE);
                    holder.moneyLabelTextView.setText(R.string.labelSupposedToPay);
                    holder.moneyTextView.setVisibility(View.VISIBLE);
                    holder.moneyTextView.setText(commonModel.money_paid_format);
                }
            }
        } else {
            /**支付成功**/
            //货到付款
            holder.imageView.setImageResource(R.mipmap.ic_pay_success);
            holder.resultTextView.setText(R.string.submitOrderSucceed);
            holder.moneyLabelTextView.setVisibility(View.VISIBLE);
            holder.moneyLabelTextView.setText(R.string.needToPay);
            holder.moneyTextView.setVisibility(View.VISIBLE);
            holder.moneyTextView.setText(commonModel.money_paid_format);
        }

    }

    private RecyclerView.ViewHolder createConfirmViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_result_confirm, parent, false);
        return new ConfirmViewHolder(view);
    }

    private RecyclerView.ViewHolder createOrderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_result_order, parent, false);
        return new OrderViewHolder(view);
    }

    private RecyclerView.ViewHolder createTopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_result_top, parent, false);
        return new TopViewHolder(view);
    }
}
