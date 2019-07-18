package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.OrderListFreeFragment;
import com.szy.yishopcustomer.Interface.OnOrderButtonListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GiftsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.PickupModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.TotalModel;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Order.OrderListPickupViewHolder;
import com.szy.yishopcustomer.ViewHolder.Order.OrderListStatusViewHolder;
import com.szy.yishopcustomer.ViewHolder.Order.OrderListTotalViewHolder;
import com.szy.yishopcustomer.ViewHolder.Order.OrderListViewHolder;
import com.szy.yishopcustomer.ViewHolder.ShopTitleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderListFreeAdapter extends RecyclerView.Adapter {

    //    0是自由购，1是到店付款,2是提货券兑换
    public int buy_type = 0;

    public static final int ITEM_TYPE_GOODS = 0;
    public static final int ITEM_TYPE_SHOP = 1;
    public static final int ITEM_TYPE_TOTAL = 2;
    public static final int ITEM_TYPE_STATUS = 3;
    public static final int ITEM_BLANK = 4;
    public static final int ITEM_TYPE_GIFT = 5;
    private static final int VIEW_DIVIDER = 6;
    public static final int ITEM_TYPE_PICKUP = 7;

    public List<Object> data;
    public View.OnClickListener onClickListener;
    private boolean is = true;

    public OrderListFreeAdapter() {
        this.data = new ArrayList<>();
    }

    public OrderListFreeAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder creatStatusViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_order_list_status, parent, false);
        return new OrderListStatusViewHolder(view);
    }

    public RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }


    public RecyclerView.ViewHolder createGoodsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list,
                parent, false);
        return new OrderListViewHolder(view);
    }

    public RecyclerView.ViewHolder createShopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .layout_shop_title, parent, false);
        return new ShopTitleViewHolder(view);
    }

    public RecyclerView.ViewHolder createPickupViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_order_list_pickup, parent, false);
        return new OrderListPickupViewHolder(view);
    }

    public RecyclerView.ViewHolder createTotalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_order_list_total, parent, false);
        return new OrderListTotalViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_GOODS:
                return createGoodsViewHolder(parent);
            case ITEM_TYPE_SHOP:
                return createShopViewHolder(parent);
            case ITEM_TYPE_TOTAL:
                return createTotalViewHolder(parent);
            case ITEM_TYPE_STATUS:
                return creatStatusViewHolder(parent);
            case ITEM_BLANK:
                return createBlankViewHolder(parent);
            case ITEM_TYPE_GIFT:
                return createGoodsViewHolder(parent);
            case VIEW_DIVIDER:
                return createDividerViewHolder(parent);
            case ITEM_TYPE_PICKUP:
                return createPickupViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_TYPE_GOODS:
                bindGoodsViewHolder((OrderListViewHolder) holder, position);
                break;
            case ITEM_TYPE_SHOP:
                bindShopViewHolder((ShopTitleViewHolder) holder, position);
                break;
            case ITEM_TYPE_TOTAL:
                bindTotalViewHolder((OrderListTotalViewHolder) holder, position);
                break;
            case ITEM_TYPE_STATUS:
                bindStatusViewHolder((OrderListStatusViewHolder) holder, position);
                break;
            case ITEM_BLANK:
                break;
            case ITEM_TYPE_GIFT:
                bindGiftViewHolder((OrderListViewHolder) holder, position);
                break;
            case VIEW_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
            case ITEM_TYPE_PICKUP:
                bindPickupViewHolder((OrderListPickupViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof GoodsListModel) {
            return ITEM_TYPE_GOODS;
        } else if (object instanceof OrderTitleModel) {
            return ITEM_TYPE_SHOP;
        } else if (object instanceof TotalModel) {
            return ITEM_TYPE_TOTAL;
        } else if (object instanceof PickupModel) {
            return ITEM_TYPE_PICKUP;
        } else if (object instanceof OrderStatusModel) {
            return ITEM_TYPE_STATUS;
        } else if (object instanceof DividerModel) {
            return ITEM_BLANK;
        } else if (object instanceof GiftsListModel) {
            return ITEM_TYPE_GIFT;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_DIVIDER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindGiftViewHolder(OrderListViewHolder viewHolder, int position) {
        GiftsListModel giftsListModel = (GiftsListModel) data.get(position);
        if (!Utils.isNull(giftsListModel.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(giftsListModel.goods_image), viewHolder
                    .mGoodsImageView);
        }

        viewHolder.mGoodsName.setText(giftsListModel.goods_name);
        viewHolder.mGoodsPrice.setText(Utils.formatMoney(viewHolder.mGoodsPrice.getContext(),
                giftsListModel.goods_price));
        viewHolder.mGoodsNumber.setText("x" + giftsListModel.goods_number);
        viewHolder.mGoodsAttribute.setText(giftsListModel.spec_info);

        viewHolder.mGoodsActivity.setVisibility(View.VISIBLE);
        viewHolder.mGoodsActivity.setBackgroundColor(ContextCompat.getColor(viewHolder.mGoodsActivity.getContext(), R.color.colorPrimary));

        viewHolder.mGoodsActivity.setText(R.string.gift);

        Utils.setViewTypeForTag(viewHolder.mItemGoods, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(viewHolder.mItemGoods, position);
        Utils.setExtraInfoForTag(viewHolder.mItemGoods, Integer.valueOf(giftsListModel
                .order_id));
        viewHolder.mItemGoods.setOnClickListener(onClickListener);
    }

    private void bindGoodsViewHolder(OrderListViewHolder orderListViewHolder, int position) {

        final GoodsListModel goodsListModel = (GoodsListModel) data.get(position);
        if (!Utils.isNull(goodsListModel.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(goodsListModel.goods_image),
                    orderListViewHolder.mGoodsImageView);
        }

//        orderListViewHolder.item_order_explain.setVisibility(View.VISIBLE);
//        if(buy_type == OrderListFreeFragment.TYPE_FREEBUY) {
//            orderListViewHolder.item_order_explain.setText("自由购");
//        } else {
//            orderListViewHolder.item_order_explain.setText("堂内点餐");
//        }
//        orderListViewHolder.mGoodsName.setText("               " + goodsListModel.goods_name);

        orderListViewHolder.mGoodsName.setText(goodsListModel.goods_name);
        orderListViewHolder.mGoodsPrice.setText(Utils.formatMoney(orderListViewHolder.mGoodsPrice
                .getContext(), goodsListModel.goods_price));
        orderListViewHolder.mGoodsNumber.setText("x" + goodsListModel.goods_number);
        orderListViewHolder.mGoodsAttribute.setText(goodsListModel.spec_info);

        if (goodsListModel.goods_type == 0) {
            orderListViewHolder.mGoodsActivity.setVisibility(View.GONE);
        } else if (goodsListModel.goods_type == 3) {
            orderListViewHolder.mGoodsActivity.setVisibility(View.VISIBLE);
            orderListViewHolder.mGoodsActivity.setBackgroundColor(ContextCompat.getColor(orderListViewHolder.mGoodsActivity.getContext(), R.color.colorGreen));

            orderListViewHolder.mGoodsActivity.setText(R.string.groupBuy);

        } else if (goodsListModel.goods_type == Macro.OT_FIGHT_GROUP) {
            orderListViewHolder.mGoodsActivity.setVisibility(View.VISIBLE);
            orderListViewHolder.mGoodsActivity.setBackgroundColor(Color.parseColor("#48cfae"));

            orderListViewHolder.mGoodsActivity.setText(R.string.groupOn);
        }

        if (!Utils.isNull(goodsListModel.back_id)) {
            orderListViewHolder.mGoodsBackStatus.setVisibility(View.VISIBLE);
            orderListViewHolder.mGoodsBackStatus.setText(goodsListModel.goods_back_format);
        } else {
            orderListViewHolder.mGoodsBackStatus.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(orderListViewHolder.mItemGoods, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(orderListViewHolder.mItemGoods, position);
        Utils.setExtraInfoForTag(orderListViewHolder.mItemGoods, Integer.valueOf(goodsListModel
                .order_id));
        orderListViewHolder.mItemGoods.setOnClickListener(onClickListener);

        if (buy_type == 2) {
//            orderListViewHolder.mGoodsPrice.setVisibility(View.INVISIBLE);
            orderListViewHolder.mGoodsPrice.setVisibility(View.VISIBLE);
            orderListViewHolder.mGoodsNumber.setVisibility(View.INVISIBLE);
        } else {
            orderListViewHolder.mGoodsPrice.setVisibility(View.VISIBLE);
            orderListViewHolder.mGoodsNumber.setVisibility(View.VISIBLE);
        }
    }

    private void bindShopViewHolder(ShopTitleViewHolder viewHolder, int position) {
        OrderTitleModel orderTitleModel = (OrderTitleModel) data.get(position);

        viewHolder.mShopNameTextView.setText(orderTitleModel.shop_name);
        viewHolder.mStatus.setText(orderTitleModel.order_status);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.itemView, position);
        Utils.setExtraInfoForTag(viewHolder.itemView, Integer.valueOf
                (orderTitleModel.shop_id));
        viewHolder.itemView.setOnClickListener(onClickListener);

        switch (buy_type) {
            case OrderListFreeFragment.TYPE_FREEBUY:
                viewHolder.textViewTableNum.setVisibility(View.GONE);
                break;
            case OrderListFreeFragment.TYPE_REACHBUY:
                if (!TextUtils.isEmpty(orderTitleModel.table_number)) {
                    viewHolder.textViewTableNum.setVisibility(View.VISIBLE);
                    viewHolder.textViewTableNum.setText(orderTitleModel.table_number + "");
                    viewHolder.textViewTableNum.setRotation(-10);
                }

                break;
            default:
                viewHolder.textViewTableNum.setVisibility(View.GONE);
                break;
        }

    }

    private Button getButton(Context context, int i) {
        Button button = (Button) LayoutInflater.from(context).inflate(
                R.layout.item_order_list_button, null);

        button.setMinWidth(Utils.dpToPx(context, 90));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(context, 35));

        if (i > 0) {
            layoutParams.setMargins(Utils.dpToPx(context, 5), 0, 0, 0);
        }
        button.setLayoutParams(layoutParams);

        return button;
    }

    private void bindStatusViewHolder(OrderListStatusViewHolder orderListStatusViewHolder, final int
            position) {
        final OrderStatusModel orderStatusModel = (OrderStatusModel) data.get(position);
        orderListStatusViewHolder.linearlayout_buttons.setVisibility(View.VISIBLE);
        orderListStatusViewHolder.linearlayout_buttons.removeAllViews();

        Context context = orderListStatusViewHolder.itemView.getContext();

        TextView tv = (TextView) LayoutInflater.from(context).inflate(
                R.layout.item_order_list_textview, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(Color.parseColor("#999999"));
        tv.setText(orderStatusModel.cancelTip);
        tv.setGravity(Gravity.RIGHT);
        tv.setPadding(0, 0, Utils.dpToPx(context, 5), 0);
        orderListStatusViewHolder.linearlayout_buttons.addView(tv);

        if (orderStatusModel != null && orderStatusModel.buttons != null && orderStatusModel.buttons.size() > 0) {

            OrderButtonHelper.initButtons(context, orderStatusModel.buttons, new OnOrderButtonListener(orderListStatusViewHolder.linearlayout_buttons) {
                @Override
                public void setButtons(Button button) {
                    super.setButtons(button);
                    Utils.setPositionForTag(button, position);
                    Utils.setExtraInfoForTag(button, Integer.valueOf
                            (orderStatusModel.order_id));
                    button.setOnClickListener(onClickListener);
                }

                @Override
                public void commented(Button button) {
                    linearlayout_buttons.addView(button);
                    button.setEnabled(false);
                }

            });
        } else if (TextUtils.isEmpty(orderStatusModel.cancelTip)) {
            orderListStatusViewHolder.linearlayout_buttons.setVisibility(View.GONE);
        }
    }

    private void bindPickupViewHolder(OrderListPickupViewHolder orderListPickupViewHolder, int
            position) {
        Utils.setViewTypeForTag(orderListPickupViewHolder.itemView, ViewType
                .VIEW_TYPE_PICK_UP);
        Utils.setPositionForTag(orderListPickupViewHolder.itemView, position);
        orderListPickupViewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindTotalViewHolder(OrderListTotalViewHolder orderListTotalViewHolder, int
            position) {
        TotalModel totalModel = (TotalModel) data.get(position);
        orderListTotalViewHolder.orderListTotal.setText(String.format("共%1$s件商品", totalModel
                .mGoodsNumber));
        orderListTotalViewHolder.orderListAmount.setText(Utils.formatMoney(orderListTotalViewHolder.orderListAmount.getContext(), totalModel.mGoodsAmount));

        double shopfee = 1;
        try {
            shopfee = Double.parseDouble(totalModel.mGoodsShippingFee);
        } catch (Exception e) {

        }

        if (shopfee == 0) {
            orderListTotalViewHolder.orderListShippingFee.setText("(免邮)");
        } else {
            orderListTotalViewHolder.orderListShippingFee.setText(String.format("(含运费%1$s)",
                    Utils.formatMoney(orderListTotalViewHolder.orderListShippingFee.getContext(), totalModel.mGoodsShippingFee)));
        }

        if (totalModel.mGoodsPriceEdit != 0.00) {
            orderListTotalViewHolder.orderListPriceEdit.setVisibility(View.VISIBLE);
        } else {
            orderListTotalViewHolder.orderListPriceEdit.setVisibility(View.GONE);
        }


    }
}
