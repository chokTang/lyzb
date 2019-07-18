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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnOrderButtonListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GiftsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsRecyclerModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.GoodsSingleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderStatusModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.OrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.PickupModel;
import com.szy.yishopcustomer.ResponseModel.OrderList.TotalModel;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Order.OrderListGoodsListViewHolder;
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
public class OrderListAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_GOODS_ONE = 0;
    public static final int ITEM_TYPE_SHOP = 1;
    public static final int ITEM_TYPE_TOTAL = 2;
    public static final int ITEM_TYPE_STATUS = 3;
    public static final int ITEM_BLANK = 4;
    public static final int ITEM_TYPE_GIFT = 5;
    private static final int VIEW_DIVIDER = 6;
    public static final int ITEM_TYPE_PICKUP = 7;
    public static final int ITEM_TYPE_GOODS_TWO = 8;

    public List<Object> data;
    public View.OnClickListener onClickListener;
    private boolean is = true;

    public OrderListAdapter() {
        this.data = new ArrayList<>();
    }

    public OrderListAdapter(List<Object> data) {
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
        //样式修改，商品展示形式修改为recyclerview
/*        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list,
                parent, false);*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list_goods_list,
                parent, false);
        return new OrderListGoodsListViewHolder(view);
    }

    public RecyclerView.ViewHolder createGoodsSingleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list_goods_single,
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
            case ITEM_TYPE_GOODS_ONE:
                return createGoodsViewHolder(parent);
            case ITEM_TYPE_GOODS_TWO:
                return createGoodsSingleViewHolder(parent);
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
            case ITEM_TYPE_GOODS_ONE:
                //bindGoodsViewHolder((OrderListGoodsListViewHolder) holder, position);
                bindGoodsListViewHolder((OrderListGoodsListViewHolder) holder, position);
                break;
            case ITEM_TYPE_GOODS_TWO:
                bindGoodsSingleViewHolder((OrderListViewHolder) holder, position);
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
       /* if (object instanceof GoodsListModel) {
            return ITEM_TYPE_GOODS_ONE;
        }*/
        if (object instanceof GoodsRecyclerModel) {
            return ITEM_TYPE_GOODS_ONE;
        } else if (object instanceof GoodsSingleModel) {
            return ITEM_TYPE_GOODS_TWO;
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
        Utils.setExtraInfoForTag(viewHolder.mItemGoods, Integer.valueOf(giftsListModel.order_id));
        viewHolder.mItemGoods.setOnClickListener(onClickListener);
    }

    //订单列表-商品多个展示形式
    private void bindGoodsListViewHolder(OrderListGoodsListViewHolder holder, int position) {
        GoodsRecyclerModel goodsRecyclerModel = (GoodsRecyclerModel) data.get(position);

        List<GoodsListModel> newList = new ArrayList<>();
        for (GoodsListModel goodsListModel : goodsRecyclerModel.goods_list) {
            newList.add(goodsListModel);
            if (goodsListModel.gifts_list.size() > 0) {
                for (GiftsListModel giftsListModel : goodsListModel.gifts_list.values()) {
                    GoodsListModel goodsListModel2 = new GoodsListModel();
                    goodsListModel2.order_id = giftsListModel.order_id;
                    goodsListModel2.goods_image = giftsListModel.goods_image;
                    goodsListModel2.goods_type = giftsListModel.goods_type;
                    goodsListModel2.goods_name = giftsListModel.goods_name;
                    newList.add(goodsListModel2);
                }
            }
        }


        holder.mLayout.removeAllViews();

        Context context = holder.mLayout.getContext();
        for (int i = 0; i < newList.size(); i++) {
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.item_order_list_goods_list_item, null);
            TextView tv = (TextView) relativeLayout.findViewById(R.id.fragment_order_goods_activity);
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.item_order_list_goods_imageView);


            if (!Utils.isNull(newList.get(i).goods_image)) {
                ImageLoader.displayImage(Utils.urlOfImage(newList.get(i).goods_image), imageView);
            }

            //由于样式修改，订单列表不显示活动标签
            tv.setVisibility(View.GONE);
            /*if (newList.get(i).goods_type == 3) {
                tv.setVisibility(View.VISIBLE);
                tv.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));

                tv.setText(R.string.groupBuy);

            } else if ((newList.get(i).goods_type == Macro.OT_FIGHT_GROUP)) {
                tv.setVisibility(View.VISIBLE);
                tv.setBackgroundColor(Color.parseColor("#48cfae"));

                tv.setText(R.string.groupOn);
            }else{
                tv.setVisibility(View.GONE);
            }*/


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, Utils.dpToPx(context, 10), 0);
            relativeLayout.setLayoutParams(layoutParams);

            holder.mLayout.addView(relativeLayout);
        }

        Utils.setViewTypeForTag(holder.mLayout, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(holder.mLayout, position);
        Utils.setExtraInfoForTag(holder.mLayout, Integer.valueOf(goodsRecyclerModel.goods_list.get(0).order_id));
        holder.mLayout.setOnClickListener(onClickListener);
    }

    //订单列表-商品单个展示形式
    private void bindGoodsSingleViewHolder(OrderListViewHolder viewHolder, int position) {

        final GoodsSingleModel goodsSingleModel = (GoodsSingleModel) data.get(position);
        if (!Utils.isNull(goodsSingleModel.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(goodsSingleModel.goods_image),
                    viewHolder.mGoodsImageView);
        }

        viewHolder.mGoodsName.setText(goodsSingleModel.goods_name);
        if (!TextUtils.isEmpty(goodsSingleModel.max_integral_num) && Integer.parseInt(goodsSingleModel.max_integral_num) > 0) {
            viewHolder.deductibleTextView.setVisibility(View.VISIBLE);
            viewHolder.deductibleTextView.setText("元宝最高抵扣:"+Utils.formatMoney(viewHolder.deductibleTextView.getContext(), goodsSingleModel.max_integral_num));
        } else {
            viewHolder.deductibleTextView.setVisibility(View.INVISIBLE);
        }

        viewHolder.mGoodsActivity.setVisibility(View.GONE);
        /*if (goodsSingleModel.goods_type == 3) {
            viewHolder.mGoodsActivity.setVisibility(View.VISIBLE);
            viewHolder.mGoodsActivity.setBackgroundColor(ContextCompat.getColor(viewHolder.mGoodsActivity.getContext(), R.color.colorGreen));

            viewHolder.mGoodsActivity.setText(R.string.groupBuy);

        } else if (goodsSingleModel.goods_type == Macro.OT_FIGHT_GROUP) {
            viewHolder.mGoodsActivity.setVisibility(View.VISIBLE);
            viewHolder.mGoodsActivity.setBackgroundColor(Color.parseColor("#48cfae"));

            viewHolder.mGoodsActivity.setText(R.string.groupOn);
        }else{
            viewHolder.mGoodsActivity.setVisibility(View.GONE);
        }*/

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(viewHolder.itemView, position);
        Utils.setExtraInfoForTag(viewHolder.itemView, Integer.valueOf(goodsSingleModel.order_id));
        viewHolder.itemView.setOnClickListener(onClickListener);
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
    }

    private void bindStatusViewHolder(OrderListStatusViewHolder orderListStatusViewHolder, final int position) {
        final OrderStatusModel orderStatusModel = (OrderStatusModel) data.get(position);
        orderListStatusViewHolder.linearlayout_buttons.setVisibility(View.VISIBLE);
        orderListStatusViewHolder.linearlayout_buttons.removeAllViews();

        Context context = orderListStatusViewHolder.itemView.getContext();

        //按钮前的取消订单/延长收货提示
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_order_list_textview, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(Color.parseColor("#999999"));
        tv.setPadding(0, 0, Utils.dpToPx(context, 5), 0);
        tv.setGravity(Gravity.RIGHT);

        if (!TextUtils.isEmpty(orderStatusModel.cancelTip)) {
            tv.setText(orderStatusModel.cancelTip);
            orderListStatusViewHolder.linearlayout_buttons.addView(tv);
        } else if (orderStatusModel.delay_days != 0 && orderStatusModel.order_status == Macro.ORDER_CONFIRM) {
            tv.setText("已延长" + orderStatusModel.delay_days + "天收货");
            orderListStatusViewHolder.linearlayout_buttons.addView(tv);
        } else if (orderStatusModel.buttons.size() == 0) {
            orderListStatusViewHolder.linearlayout_buttons.setVisibility(View.GONE);
        }

        TextView deductibleTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_order_list_textview, null);
        LinearLayout.LayoutParams deductibleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        deductibleLayoutParams.weight = 1;
        deductibleTextView.setLayoutParams(deductibleLayoutParams);
        deductibleTextView.setTextColor(Color.parseColor("#f23030"));
        deductibleTextView.setTextSize(12);
        deductibleTextView.setPadding(Utils.dpToPx(context, 5), 0, Utils.dpToPx(context, 5), 0);
        deductibleTextView.setGravity(Gravity.LEFT);
        deductibleTextView.setText("元宝实际抵扣:-"+Utils.formatMoney(deductibleTextView.getContext(), orderStatusModel.integral));
        orderListStatusViewHolder.linearlayout_buttons.addView(deductibleTextView);

        if (orderStatusModel != null && orderStatusModel.buttons != null && orderStatusModel.buttons.size() > 0) {

            OrderButtonHelper.initButtons(context, orderStatusModel.buttons, new OnOrderButtonListener(orderListStatusViewHolder.linearlayout_buttons) {
                @Override
                public void setButtons(Button button) {
//                    String button_type = (String) button.getTag(OrderButtonHelper.TAG_BUTTON_TYPE);
                    super.setButtons(button);
                    Utils.setPositionForTag(button, position);
                    Utils.setExtraInfoForTag(button, Integer.valueOf(orderStatusModel.order_id));
                    button.setOnClickListener(onClickListener);
                }

                @Override
                public void commented(Button button) {
                    button.setEnabled(false);
                    linearlayout_buttons.addView(button);
                }
            });
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
