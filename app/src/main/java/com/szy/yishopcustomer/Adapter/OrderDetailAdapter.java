package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderAddressModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderCountDownModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.OutDeliveryModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.PaymentModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.QrcodeModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.TitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.TotalModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderAddressViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderCountDownViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderGoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderInfoViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderPaymentViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderQrcodeViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderStatusViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.OrderDetail.OrderTotalViewHolder;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 16/11/12/06.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderDetailAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_ORDER_STATUS = 0;
    public static final int ITEM_TYPE_DIVIDER = 1;
    public static final int ITEM_TYPE_COUNT_DOWN = 2;
    public static final int ITEM_TYPE_ADDRESS = 3;
    public static final int ITEM_TYPE_TITLE = 4;
    public static final int ITEM_TYPE_OUT_DELIVERY_GOODS = 5;
    public static final int ITEM_TYPE_PAYMENT = 6;
    public static final int ITEM_TYPE_TOTAL = 7;
    public static final int ITEM_TYPE_ORDER_INFO = 8;
    public static final int ITEM_TYPE_DELIVERY_GOODS = 9;
    public static final int ITEM_TYPE_ORDER_CODE = 10;

    public View.OnClickListener onClickListener;

    public List<Object> data;

    public OrderDetailAdapter() {
        this.data = new ArrayList<>();
    }

    public OrderDetailAdapter(List<Object> data) {
        this.data = data;
    }

    public static String formatDuring(long mss) {
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / (60);
        long seconds = (mss % (60));
        return days + " 天 " + hours + " 小时 " + minutes + " 分 " + seconds + " 秒 ";
    }

    public RecyclerView.ViewHolder createAddressViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_address, parent, false);
        return new OrderAddressViewHolder(view);
    }

    public RecyclerView.ViewHolder createCountDownViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_count_down, parent, false);
        return new OrderCountDownViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createGoodsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_goods, parent, false);
        return new OrderGoodsViewHolder(view);
    }

    public RecyclerView.ViewHolder createOrderInfoViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_info, parent, false);
        return new OrderInfoViewHolder(view);
    }

    public RecyclerView.ViewHolder createOrderStatusViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_status, parent, false);
        return new OrderStatusViewHolder(view);
    }

    public RecyclerView.ViewHolder createOrderQrCodeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_qrcode, parent, false);
        return new OrderQrcodeViewHolder(view);
    }

    public RecyclerView.ViewHolder createPaymentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_payment, parent, false);
        return new OrderPaymentViewHolder(view);
    }

    public RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_title, parent, false);
        return new OrderTitleViewHolder(view);
    }

    public RecyclerView.ViewHolder createTotalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_order_detail_total, parent, false);
        return new OrderTotalViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_ORDER_STATUS:
                return createOrderStatusViewHolder(parent);
            case ITEM_TYPE_ORDER_CODE:
                return createOrderQrCodeViewHolder(parent);
            case ITEM_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
            case ITEM_TYPE_COUNT_DOWN:
                return createCountDownViewHolder(parent);
            case ITEM_TYPE_ADDRESS:
                return createAddressViewHolder(parent);
            case ITEM_TYPE_TITLE:
                return createTitleViewHolder(parent);
            case ITEM_TYPE_OUT_DELIVERY_GOODS:
                return createGoodsViewHolder(parent);
            case ITEM_TYPE_PAYMENT:
                return createPaymentViewHolder(parent);
            case ITEM_TYPE_TOTAL:
                return createTotalViewHolder(parent);
            case ITEM_TYPE_ORDER_INFO:
                return createOrderInfoViewHolder(parent);
            case ITEM_TYPE_DELIVERY_GOODS:
                return createGoodsViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_TYPE_ORDER_STATUS:
                bindOrderStatusViewHolder((OrderStatusViewHolder) holder, position);
                break;
            case ITEM_TYPE_ORDER_CODE:
                bindOrderQrcodeViewHolder((OrderQrcodeViewHolder) holder, position);
                break;
            case ITEM_TYPE_DIVIDER:
                break;
            case ITEM_TYPE_COUNT_DOWN:
                bindOrderCountDownViewHolder((OrderCountDownViewHolder) holder, position);
                break;
            case ITEM_TYPE_ADDRESS:
                bindAddressViewHolder((OrderAddressViewHolder) holder, position);
                break;
            case ITEM_TYPE_TITLE:
                bindTitleViewHolder((OrderTitleViewHolder) holder, position);
                break;
            case ITEM_TYPE_OUT_DELIVERY_GOODS:
                bindDeliveryGoodsViewHolder((OrderGoodsViewHolder) holder, position);
                break;
            case ITEM_TYPE_PAYMENT:
                bindPaymentViewHolder((OrderPaymentViewHolder) holder, position);
                break;
            case ITEM_TYPE_TOTAL:
                bindTotalViewHolder((OrderTotalViewHolder) holder, position);
                break;
            case ITEM_TYPE_ORDER_INFO:
                bindOrderInfoViewHolder((OrderInfoViewHolder) holder, position);
                break;
            case ITEM_TYPE_DELIVERY_GOODS:
                bindGoodsViewHolder((OrderGoodsViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof OrderStatusModel) {
            return ITEM_TYPE_ORDER_STATUS;
        } else if (object instanceof QrcodeModel) {
            return ITEM_TYPE_ORDER_CODE;
        } else if (object instanceof DividerModel) {
            return ITEM_TYPE_DIVIDER;
        } else if (object instanceof OrderCountDownModel) {
            return ITEM_TYPE_COUNT_DOWN;
        } else if (object instanceof OrderAddressModel) {
            return ITEM_TYPE_ADDRESS;
        } else if (object instanceof TitleModel) {
            return ITEM_TYPE_TITLE;
        } else if (object instanceof OutDeliveryModel) {
            return ITEM_TYPE_OUT_DELIVERY_GOODS;
        } else if (object instanceof PaymentModel) {
            return ITEM_TYPE_PAYMENT;
        } else if (object instanceof TotalModel) {
            return ITEM_TYPE_TOTAL;
        } else if (object instanceof OrderInfoModel) {
            return ITEM_TYPE_ORDER_INFO;
        } else if (object instanceof GoodsModel) {
            return ITEM_TYPE_DELIVERY_GOODS;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindAddressViewHolder(OrderAddressViewHolder holder, int position) {
        OrderAddressModel item = (OrderAddressModel) data.get(position);
        holder.mConsignee.setText(item.consignee);
        holder.mTel.setText(item.tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        holder.mAddress.setText(item.address);
    }

    //设置订单商品详情 内容
    private void bindDeliveryGoodsViewHolder(OrderGoodsViewHolder holder, int position) {
        OutDeliveryModel outDeliveryModel = (OutDeliveryModel) data.get(position);
        ImageLoader.displayImage(Utils.urlOfImage(outDeliveryModel.goods_image), holder
                .mGoodsThumb);

        holder.mGoodsPrice.setText(Utils.formatMoney(holder.mGoodsPrice.getContext(),
                outDeliveryModel.goods_price));
        holder.mGoodsNumber.setText("x" + outDeliveryModel.unsend_number);
        holder.mGoodsSpec.setText(outDeliveryModel.spec_info);
        holder.mGoodsStatus.setText(outDeliveryModel.goods_status_format);

        holder.llActivity.removeAllViews();
        String space = "";
        if (outDeliveryModel.goods_type != 0) {
            TextView textView = getGoodsTypeTextView(holder.itemView.getContext(), outDeliveryModel.goods_type);
            holder.llActivity.addView(textView);
            for (int i = 0; i < textView.getText().toString().length()+2; i++) {
                space += "   ";
            }
        }

        if (outDeliveryModel.act_type == Macro.GT_FULL_CUT_GOODS) {
            TextView textView = getActTypeTextView(holder.itemView.getContext(), outDeliveryModel.act_type);
            holder.llActivity.addView(textView);
            for (int i = 0; i < textView.getText().toString().length()+2; i++) {
                space += "   ";
            }
        }

        holder.mGoodsName.setText(space + outDeliveryModel.goods_name);

        if (outDeliveryModel.backAllow && outDeliveryModel.is_gift == 0) {
            holder.mBackButtonLayout.setVisibility(View.VISIBLE);
            if (!Utils.isNull(outDeliveryModel.goods_back_format)) {
                holder.mBackButton.setText(outDeliveryModel.goods_back_format);

                Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_BACK_DETAIL);
                Utils.setPositionForTag(holder.mBackButtonLayout, position);
                Utils.setExtraInfoForTag(holder.mBackButtonLayout, outDeliveryModel.back_id);
            } else {
                holder.mBackButton.setText("退款/退货");

                Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_REFUND);
                Utils.setPositionForTag(holder.mBackButtonLayout, position);
                Utils.setExtraInfoForTag(holder.mBackButtonLayout, 0);
            }

            holder.mBackButtonLayout.setOnClickListener(onClickListener);
        } else {
            //发货中的商品才显示“查看团详情”
            if (outDeliveryModel.goods_type == 6) {
                holder.mBackButtonLayout.setVisibility(View.VISIBLE);
                holder.mBackButton.setText(R.string.checkGroupOnDetail);

                Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_GROUPON_DETAIL);
                Utils.setPositionForTag(holder.mBackButtonLayout, position);

                holder.mBackButtonLayout.setOnClickListener(onClickListener);
            } else {
                holder.mBackButtonLayout.setVisibility(View.GONE);
            }
        }


        Utils.setViewTypeForTag(holder.mGoodsItem, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(holder.mGoodsItem, position);
        Utils.setExtraInfoForTag(holder.mGoodsItem, Integer.valueOf(outDeliveryModel.goods_id));
        holder.mGoodsItem.setOnClickListener(onClickListener);
    }

    private static TextView getGoodsTypeTextView(Context context, int goods_type) {
        TextView textView = (TextView) LayoutInflater.from(context).inflate(
                R.layout.item_activity, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(context, 17));
        layoutParams.setMargins(0, 0, Utils.dpToPx(context, 5), 0);
        textView.setLayoutParams(layoutParams);

        if (goods_type == Macro.OT_FIGHT_GROUP) {
            textView.setText("拼团");
            textView.setBackgroundColor(Color.parseColor("#48CFAE"));
        } else if (goods_type == Macro.GT_AUCTION_GOODS) {
            textView.setText("拍卖");
            textView.setBackgroundColor(Color.parseColor("#1B9AF7"));
        } else if (goods_type == Macro.GT_EXCHANGE_GOODS) {
            textView.setText("积分兑换");
            textView.setBackgroundColor(Color.parseColor("#F0AA4A"));
        } else if (goods_type == Macro.GT_GROUP_BUY_GOODS) {
            textView.setText("团购");
            textView.setBackgroundColor(Color.parseColor("#5CB85C"));
        } else if (goods_type == Macro.GT_PRE_SALE_GOODS) {
            textView.setText("预售");
            textView.setBackgroundColor(Color.parseColor("#FF6A6A"));
        } else if (goods_type == Macro.GT_BARGAIN_GOODS) {
            textView.setText("砍价");
            textView.setBackgroundColor(Color.parseColor("#5cb85c"));
        } else if (goods_type == Macro.GT_LIMITED_DISCOUNT_GOODS) {
            textView.setText("限时折扣");
            textView.setBackgroundColor(Color.parseColor("#FF7700"));
        } else if (goods_type == Macro.GT_GIFT_GOODS) {
            textView.setText("赠品");
            textView.setBackgroundColor(Color.parseColor("#FF7700"));
        }

        return textView;
    }

    private static TextView getActTypeTextView(Context context, int act_type) {
        TextView textView = (TextView) LayoutInflater.from(context).inflate(
                R.layout.item_activity, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(context, 17));
        layoutParams.setMargins(0, 0, Utils.dpToPx(context, 5), 0);
        textView.setLayoutParams(layoutParams);

        if (act_type == Macro.GT_FULL_CUT_GOODS) {
            textView.setText("满减送");
            textView.setBackgroundColor(Color.parseColor("#ff0000"));
        }

        return textView;
    }

    private void bindGoodsViewHolder(OrderGoodsViewHolder holder, int position) {
        GoodsModel goodsModel = (GoodsModel) data.get(position);
        ImageLoader.displayImage(Utils.urlOfImage(goodsModel.goods_image), holder.mGoodsThumb);

        holder.mGoodsPrice.setText(Utils.formatMoney(holder.mGoodsPrice.getContext(), goodsModel
                .goods_price));
        holder.mGoodsNumber.setText("x" + goodsModel.send_number);
        holder.mGoodsSpec.setText(goodsModel.spec_info);
        holder.mGoodsStatus.setText(goodsModel.goods_status_format);

        holder.llActivity.removeAllViews();
        String space = "";
        if (goodsModel.goods_type != 0) {
            TextView textView = getGoodsTypeTextView(holder.itemView.getContext(), goodsModel.goods_type);
            holder.llActivity.addView(textView);
            for (int i = 0; i < textView.getText().toString().length()+2; i++) {
                space += "   ";
            }
        }

        if (goodsModel.act_type == Macro.GT_FULL_CUT_GOODS) {
            TextView textView = getActTypeTextView(holder.itemView.getContext(), goodsModel.act_type);
            holder.llActivity.addView(textView);
            for (int i = 0; i < textView.getText().toString().length()+2; i++) {
                space += "   ";
            }
        }

        holder.mGoodsName.setText(space + goodsModel.goods_name);

        if (goodsModel.is_gift != 1 && goodsModel.is_delete == 0 && goodsModel.back_id > 0) {
            holder.mBackButtonLayout.setVisibility(View.VISIBLE);
            holder.mBackButton.setText(goodsModel.goods_back_format);

            Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_BACK_DETAIL);
            Utils.setPositionForTag(holder.mBackButtonLayout, position);
            Utils.setExtraInfoForTag(holder.mBackButtonLayout, goodsModel.back_id);
        } else if (goodsModel.is_gift != 1 && goodsModel.backAllow) {
            holder.mBackButtonLayout.setVisibility(View.VISIBLE);
            holder.mBackButton.setText("退款/退货");

            Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_REFUND);
            Utils.setPositionForTag(holder.mBackButtonLayout, position);
            Utils.setExtraInfoForTag(holder.mBackButtonLayout, 1);
        } else {
            /*if(goodsModel.goods_type.equals("6")){
                holder.mBackButtonLayout.setVisibility(View.VISIBLE);
                holder.mBackButton.setText(R.string.checkGroupOnDetail);

                Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_GROUPON_DETAIL);
                Utils.setPositionForTag(holder.mBackButtonLayout, position);
            }else {
                holder.mBackButtonLayout.setVisibility(View.GONE);
            }*/

            //确认收货的商品才会显示“投诉商家”
            //投诉商家 按钮
            if (goodsModel.complaintAllow) {
                holder.mBackButtonLayout.setVisibility(View.VISIBLE);
                if (goodsModel.complainted == 7) {
                    holder.mBackButton.setText("投诉商家");

                    Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_COMPLAINT);
                    Utils.setPositionForTag(holder.mBackButtonLayout, position);
                    Utils.setExtraInfoForTag(holder.mBackButtonLayout, 1);
                } else if (goodsModel.complainted == 2) {
                    holder.mBackButton.setText("投诉撤销");
                } else if (goodsModel.complainted == Macro.CS_ARBITRATION_SUCCESS) {
                    holder.mBackButton.setText("投诉成功");
                } else if (goodsModel.complainted == Macro.CS_ARBITRATION_FAIL) {
                    holder.mBackButton.setText("投诉失败");
                } else if (goodsModel.complainted == 9) {
                    holder.mBackButton.setText("投诉中");

                    Utils.setViewTypeForTag(holder.mBackButtonLayout, ViewType.VIEW_TYPE_DETAIL);
                    Utils.setPositionForTag(holder.mBackButtonLayout, position);
                    Utils.setExtraInfoForTag(holder.mBackButtonLayout, 1);
                }
            } else {
                holder.mBackButtonLayout.setVisibility(View.VISIBLE);
            }
        }


        holder.mBackButtonLayout.setOnClickListener(onClickListener);
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setExtraInfoForTag(holder.itemView, Integer.valueOf(goodsModel.goods_id));
        holder.itemView.setOnClickListener(onClickListener);
    }

    private void bindOrderCountDownViewHolder(OrderCountDownViewHolder holder, int position) {
        OrderCountDownModel item = (OrderCountDownModel) data.get(position);

        String leftTime = "";
        if (item.count_down > 0) {
            leftTime = formatDuring(Math.round(item.count_down));
        } else {
            leftTime = "已超时！";
        }

        if (item.order_status_code.equals("unpayed")) {
            SpannableStringBuilder style = null;
            style = Utils.spanStringColor(String.format(holder.itemView.getContext().getString(R.string.formatPayOrderLeftTime), leftTime),
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary),
                    2,
                    leftTime.length());
            holder.mOrderDetailLeftTime.setText(style);
        } else if (item.order_status_code.equals("shipped")) {
            SpannableStringBuilder style = null;
            style = Utils.spanStringColor(String.format(holder.itemView.getContext().getString(R.string.formatConfirmOrderLeftTime), leftTime),
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary),
                    2,
                    leftTime.length());
            holder.mOrderDetailLeftTime.setText(style);
        } else if (item.order_status_code.equals("groupon_active")) {
            SpannableStringBuilder style = null;
            style = Utils.spanStringColor(String.format(holder.itemView.getContext().getString(R.string.formatGroupOnLeftTime), item.diff_num, leftTime),
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary),
                    10 + item.diff_num.length(),
                    leftTime.length());
            holder.mOrderDetailLeftTime.setText(style);
        }
    }

    private void bindOrderInfoViewHolder(OrderInfoViewHolder holder, int position) {
        OrderInfoModel orderInfoModel = (OrderInfoModel) data.get(position);

        holder.mOrderSnLabel.setText("订单编号：" + orderInfoModel.order_sn);

        if (orderInfoModel.pay_sn.equals("0")) {
            holder.mPaySnLabel.setVisibility(View.GONE);
        } else {
            holder.mPaySnLabel.setVisibility(View.VISIBLE);
            holder.mPaySnLabel.setText("交易号：" + orderInfoModel.pay_sn);
        }

        if (!orderInfoModel.add_time.equals("0")) {
            holder.mAddtimeLabel.setText("成交时间：" + Utils.times(orderInfoModel.add_time));
        }

        if (!orderInfoModel.pay_time.equals("0")) {
            holder.mPaytimeLabel.setVisibility(View.VISIBLE);
            holder.mPaytimeLabel.setText("付款时间：" + Utils.times(orderInfoModel.pay_time));
        } else {
            holder.mPaytimeLabel.setVisibility(View.GONE);
        }

        if (!orderInfoModel.shipping_time.equals("0")) {
            holder.mShippingtimeLabel.setVisibility(View.VISIBLE);
            holder.mShippingtimeLabel.setText("发货时间：" + Utils.times(orderInfoModel.shipping_time));
        } else {
            holder.mShippingtimeLabel.setVisibility(View.GONE);
        }

        if (!orderInfoModel.end_time.equals("0")) {
            holder.mConfirmtimeLabel.setVisibility(View.VISIBLE);
            holder.mConfirmtimeLabel.setText("完结时间：" + Utils.times(orderInfoModel.end_time));
        } else {
            holder.mConfirmtimeLabel.setVisibility(View.GONE);
        }

        holder.mDeliveryTimeTextView.setText("送货时间：" + orderInfoModel.best_time);

        if (!Utils.isNull(orderInfoModel.postscript)) {
            holder.mPostScriptLabel.setText("买家留言：" + orderInfoModel.postscript);
        } else {
            holder.mPostScriptLabel.setText("买家留言：-");
        }
        if (Utils.isNull(orderInfoModel.inv_id)) {
            holder.mInvoiceLabel.setText("发票信息：" + "无需发票");
        } else {
            holder.mInvoiceLabel.setText("发票信息：" + orderInfoModel.inv_type_format);
        }

        String pay_name = "";
        if (Double.parseDouble(orderInfoModel.surplus) > 0 && orderInfoModel.pay_id > 0) {
            pay_name += "余额支付+";
        }
        pay_name += orderInfoModel.pay_name;
        holder.mPayTypeLabel.setText("支付方式：" + pay_name);

        Utils.setViewTypeForTag(holder.mCopy, ViewType.VIEW_TYPE_COPY);
        holder.mCopy.setOnClickListener(onClickListener);
    }

    private void bindOrderStatusViewHolder(OrderStatusViewHolder holder, int position) {
        OrderStatusModel item = (OrderStatusModel) data.get(position);
        holder.mOrderStatus.setText(item.order_status);
    }

    private void bindOrderQrcodeViewHolder(OrderQrcodeViewHolder holder, int position) {
        QrcodeModel item = (QrcodeModel) data.get(position);

        holder.mQrcode.setImageBitmap(CodeUtils.createImage(Utils.getMallMBaseUrl()+"/user/order/info?id="+item.order_id, 400, 400, null));
        holder.mOrderSn.setText("订单编号：" + item.order_sn);
    }

    private void bindPaymentViewHolder(OrderPaymentViewHolder holder, int position) {
        PaymentModel paymentModel = (PaymentModel) data.get(position);
        holder.mPayment.setText(paymentModel.payment);
    }

    private void bindTitleViewHolder(OrderTitleViewHolder holder, int position) {
        TitleModel item = (TitleModel) data.get(position);
        holder.mTitle.setText(item.title);
        holder.mExpressSn.setText(item.subTitle);
    }

    private void bindTotalViewHolder(OrderTotalViewHolder holder, int position) {
        TotalModel totalModel = (TotalModel) data.get(position);

        holder.mProductAmount.setText(totalModel.goods_amount_format);
        holder.mShippingFee.setText("+" + totalModel.shipping_fee_format);

        if (Float.parseFloat(totalModel.bonus) > 0) {
            holder.mBonus.setVisibility(View.VISIBLE);
            holder.mBonusLabel.setVisibility(View.VISIBLE);

            holder.mBonus.setText("-" + totalModel.all_bonus_format);
        } else {
            holder.mBonus.setVisibility(View.GONE);
            holder.mBonusLabel.setVisibility(View.GONE);
        }
        //
        holder.mBenefit.setText("-" + totalModel.benefit);

        holder.mOrderAmount.setText(totalModel.order_amount);

        if (totalModel.pay_code.equals("cod")) {
            holder.mCodCashMoreLabel.setVisibility(View.VISIBLE);
            holder.mCodCashMore.setVisibility(View.VISIBLE);
            holder.mCodCashMore.setText("+" + totalModel.cash_more_format);
            holder.mBalance.setText(totalModel.surplus_format);
/*            if (totalModel.order_status_code.equals(Macro.ORDER_FINISHED)) {
                holder.mBalance.setText("+" + Utils.formatMoney(holder.mBalance.getContext(),
                        totalModel.surplus));
            } else {
                holder.mBalance.setText("-" + Utils.formatMoney(holder.mBalance.getContext(),
                        totalModel.surplus));
            }*/
        } else {
            holder.mCodCashMoreLabel.setVisibility(View.GONE);
            holder.mCodCashMore.setVisibility(View.GONE);
        }

        holder.mOrderMoneyLabel.setText(totalModel.pay_status_format);

        if (totalModel.pay_status == Macro.PS_PAID) {

            holder.mOrderMoney.setText(totalModel.surplus_format);

            holder.mMoneyPaidLabel.setVisibility(View.VISIBLE);
            holder.mMoneyPaid.setVisibility(View.VISIBLE);
            holder.mMoneyPaid.setText(totalModel.money_paid_format);
            holder.mBalance.setText(totalModel.surplus_format);
/*            holder.mBalance.setText("+" + Utils.formatMoney(holder.mBalance.getContext(),
                    totalModel.surplus));*/
        } else {

            holder.mOrderMoney.setText(totalModel.money_paid_format);

            holder.mMoneyPaidLabel.setVisibility(View.GONE);
            holder.mMoneyPaid.setVisibility(View.GONE);
            holder.mBalance.setText(totalModel.surplus_format);/*
            holder.mBalance.setText("-" + Utils.formatMoney(holder.mBalance.getContext(),
                    totalModel.surplus));*/
        }


        if (totalModel.isExchangeClosed()) {
            holder.relativeLayout_order_amount.setVisibility(View.GONE);
        } else {
            holder.relativeLayout_order_amount.setVisibility(View.VISIBLE);
        }

        holder.mDeductible.setText("-¥"+totalModel.integral);
    }

}
