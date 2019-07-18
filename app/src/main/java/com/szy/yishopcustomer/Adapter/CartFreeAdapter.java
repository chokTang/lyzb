package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GiftModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidClearModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ShopListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.StoreTipModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpayedListMoreModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Cart.CartGoodsGiftViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartGoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartInvalidClearViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartShopViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartStoreTipViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartUnpayedMoreViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartUnpayedNumberViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartUnpayedViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.EmptyViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zongren on 16/5/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartFreeAdapter extends RecyclerView.Adapter {
    private final int ITEM_TYPE_SHOP = 0;
    private final int ITEM_TYPE_GOODS = 1;
    private final int ITEM_TYPE_GIFT = 2;
    private final int ITEM_TYPE_BLANK = 3;
    private final int ITEM_TYPE_ORDER_UNPAID_NUM = 4;
    private final int ITEM_TYPE_ORDER_UNPAID = 5;
    private final int ITEM_TYPE_MORE = 6;
    private final int ITEM_TYPE_EMPTY = 7;
    private final int ITEM_TYPE_INVALID_TITLE = 8;
    private final int ITEM_TYPE_INVALID_LIST = 9;
    private final int ITEM_TYPE_INVALID_CLEAR = 10;
    private final int ITEM_TYPE_STORE_TIP = 11;
    private final int ITEM_TYPE_VIEW_ORDER = 12;

    public List<Object> data;
    public TextWatcherAdapter.TextWatcherListener onTextChangeListener;
    public View.OnClickListener onClickListener;

    public CartFreeAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_GOODS:
                return createGoodsViewHolder(inflater, parent);
            case ITEM_TYPE_SHOP:
                return createShopViewHolder(inflater, parent);
            case ITEM_TYPE_GIFT:
                return createGiftViewHolder(inflater, parent);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(inflater, parent);
            case ITEM_TYPE_ORDER_UNPAID_NUM:
                return createUnpaidOrderTitleViewHolder(inflater, parent);
            case ITEM_TYPE_ORDER_UNPAID:
                return createUnpaidOrderViewHolder(inflater, parent);
            case ITEM_TYPE_MORE:
                return createMoreUnpaidOrderViewHolder(inflater, parent);
            case ITEM_TYPE_EMPTY:
                return createEmptyViewHolder(inflater, parent);
            case ITEM_TYPE_INVALID_TITLE:
                return createInvalidTitleViewHolder(inflater, parent);
            case ITEM_TYPE_INVALID_LIST:
                return createInvalidListViewHolder(inflater, parent);
            case ITEM_TYPE_INVALID_CLEAR:
                return createInvalidClearViewHolder(inflater, parent);
            case ITEM_TYPE_STORE_TIP:
                return createStoreTipViewHolder(inflater, parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Object object = data.get(position);
        switch (getItemViewType(position)) {
            case ITEM_TYPE_SHOP:
                bindShopViewHolder((CartShopViewHolder) viewHolder, (ShopListModel) object,
                        position);
                break;
            case ITEM_TYPE_GOODS:
                bindGoodsViewHolder((CartGoodsViewHolder) viewHolder, (GoodsModel) object,
                        position);
                break;
            case ITEM_TYPE_GIFT:
                bindGiftViewHolder((CartGoodsGiftViewHolder) viewHolder, (GiftModel) object,
                        position);
                break;
            case ITEM_TYPE_ORDER_UNPAID_NUM:
                bindUnpaidOrderTitleViewHolder((CartUnpayedNumberViewHolder) viewHolder,
                        (UnpaidOrderTitleModel) object);
                break;
            case ITEM_TYPE_ORDER_UNPAID:
                bindUnpaidOrderViewHolder((CartUnpayedViewHolder) viewHolder, (UnpaidOrderModel)
                        object, position);
                break;
            case ITEM_TYPE_MORE:
                bindMoreUnpaidOrderViewHolder((CartUnpayedMoreViewHolder) viewHolder,
                        (UnpayedListMoreModel) object);
                break;
            case ITEM_TYPE_EMPTY:
                bindEmptyViewHolder((EmptyViewHolder) viewHolder, (EmptyItemModel) object);
                break;
            case ITEM_TYPE_INVALID_TITLE:
                bindInvalidTitleViewHolder((DividerViewHolder) viewHolder, (InvalidTitleModel)
                        object);
                break;
            case ITEM_TYPE_INVALID_LIST:
                bindInvalidListViewHolder((CartGoodsGiftViewHolder) viewHolder,
                        (InvalidListModel) object, position);
                break;
            case ITEM_TYPE_INVALID_CLEAR:
                bindInvalidClearViewHolder((CartInvalidClearViewHolder) viewHolder,
                        (InvalidClearModel) object, position);
                break;
            case ITEM_TYPE_STORE_TIP:
            bindStoreTipViewHolder((CartStoreTipViewHolder) viewHolder,
                    (StoreTipModel) object, position);
            break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ShopListModel) {
            return ITEM_TYPE_SHOP;
        } else if (object instanceof GoodsModel) {
            return ITEM_TYPE_GOODS;
        } else if (object instanceof GiftModel) {
            return ITEM_TYPE_GIFT;
        } else if (object instanceof DividerModel) {
            return ITEM_TYPE_BLANK;
        } else if (object instanceof UnpaidOrderTitleModel) {
            return ITEM_TYPE_ORDER_UNPAID_NUM;
        } else if (object instanceof UnpaidOrderModel) {
            return ITEM_TYPE_ORDER_UNPAID;
        } else if (object instanceof UnpayedListMoreModel) {
            return ITEM_TYPE_MORE;
        } else if (object instanceof EmptyItemModel) {
            return ITEM_TYPE_EMPTY;
        } else if (object instanceof InvalidTitleModel) {
            return ITEM_TYPE_INVALID_TITLE;
        } else if (object instanceof InvalidListModel) {
            return ITEM_TYPE_INVALID_LIST;
        } else if (object instanceof InvalidClearModel) {
            return ITEM_TYPE_INVALID_CLEAR;
        } else if(object instanceof StoreTipModel) {
            return ITEM_TYPE_STORE_TIP;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindEmptyViewHolder(EmptyViewHolder viewHolder, EmptyItemModel object) {
        Utils.setViewTypeForTag(viewHolder.button, ViewType.VIEW_TYPE_EMPTY);
        viewHolder.button.setOnClickListener(onClickListener);
        viewHolder.button.setBackgroundResource(R.color.free_buy_primary);
    }

    private void bindInvalidTitleViewHolder(DividerViewHolder viewHolder, InvalidTitleModel
            object) {

    }

    private void bindGiftViewHolder(CartGoodsGiftViewHolder goodsGiftViewHolder, GiftModel
            giftModel, int position) {
        goodsGiftViewHolder.nameTextView.setText(giftModel.sku_name);
        if (giftModel.spec_names != null) {
            String spec_names = "";
            for (int i = 0; i < giftModel.spec_names.size(); i++) {
                spec_names += giftModel.spec_names.get(i);
            }
            goodsGiftViewHolder.attrTextView.setText(spec_names);
        }
        goodsGiftViewHolder.priceTextView.setText(giftModel.goods_price_format);

        ImageLoader.displayImage(Utils.urlOfImage(giftModel.sku_image_thumb), goodsGiftViewHolder
                .goodsImageView);

        goodsGiftViewHolder.goodsNumberEditText.setText(giftModel.goods_number);

        Utils.setViewTypeForTag(goodsGiftViewHolder.itemView, ViewType.VIEW_TYPE_GIFT);
        Utils.setPositionForTag(goodsGiftViewHolder.itemView, position);
        goodsGiftViewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindInvalidListViewHolder(CartGoodsGiftViewHolder goodsGiftViewHolder,
                                           InvalidListModel invalidListModel, int position) {
        goodsGiftViewHolder.nameTextView.setText(invalidListModel.sku_name);
        if (invalidListModel.spec_names != null) {
            String spec_names = "";
            for (int i = 0; i < invalidListModel.spec_names.size(); i++) {
                spec_names += invalidListModel.spec_names.get(i);
            }
            goodsGiftViewHolder.attrTextView.setText(spec_names);
        }

        goodsGiftViewHolder.priceTextView.setText(invalidListModel.goods_price_format);

        ImageLoader.displayImage(Utils.urlOfImage(invalidListModel.goods_image),
                goodsGiftViewHolder.goodsImageView);

        goodsGiftViewHolder.goodsNumberEditText.setText(invalidListModel.goods_number);

        Utils.setViewTypeForTag(goodsGiftViewHolder.itemView, ViewType.VIEW_TYPE_INVALID);
        Utils.setPositionForTag(goodsGiftViewHolder.itemView, position);
        goodsGiftViewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindGoodsViewHolder(CartGoodsViewHolder goodsViewHolder, GoodsModel
            shopGoodsModel, int position) {
        goodsViewHolder.nameTextView.setText(shopGoodsModel.goods_name);
        if (!shopGoodsModel.goods_moq.equals("1")) {
            goodsViewHolder.goodsMoq.setVisibility(View.VISIBLE);
            goodsViewHolder.goodsMoq.setText(goodsViewHolder.goodsMoq.getResources().getString(R
                    .string.minimumQuantity) + shopGoodsModel.goods_moq);
        } else {
            goodsViewHolder.goodsMoq.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(goodsViewHolder.nameTextView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(goodsViewHolder.nameTextView, position);
        goodsViewHolder.nameTextView.setOnClickListener(onClickListener);

        goodsViewHolder.priceTextView.setText(shopGoodsModel.goods_price_format);

        goodsViewHolder.goodsNumberEditText.setText(shopGoodsModel.goods_number);
//        if (shopGoodsModel.activity != null) {
//            if (shopGoodsModel.activity.act_code.equals("group_buy")) {
//                goodsViewHolder.goodsActivityTip.setVisibility(View.VISIBLE);
//                goodsViewHolder.goodsActivityTip.setText(R.string.groupBuy);
//                goodsViewHolder.goodsActivityTip.setBackgroundColor(Color.parseColor
//                        (shopGoodsModel.activity.act_color));
//            } else {
//                goodsViewHolder.goodsActivityTip.setVisibility(View.GONE);
//            }
//        } else {
//            goodsViewHolder.goodsActivityTip.setVisibility(View.GONE);
//        }
        if (shopGoodsModel.spec_names != null) {
            String spec_names = "";
            for (int i = 0; i < shopGoodsModel.spec_names.size(); i++) {
                spec_names += shopGoodsModel.spec_names.get(i) +" ";
            }
            goodsViewHolder.attrTextView.setText(spec_names);
        }

        Utils.setViewTypeForTag(goodsViewHolder.goodsImageView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(goodsViewHolder.goodsImageView, position);
        goodsViewHolder.goodsImageView.setOnClickListener(onClickListener);

        ImageLoader.displayImage(Utils.urlOfImage(shopGoodsModel.sku_image), goodsViewHolder
                .goodsImageView);

        goodsViewHolder.minusButton.setVisibility(View.VISIBLE);
        goodsViewHolder.addButton.setVisibility(View.VISIBLE);
        goodsViewHolder.goodsNumberEditText.setVisibility(View.VISIBLE);

        Utils.setViewTypeForTag(goodsViewHolder.minusButton, ViewType.VIEW_TYPE_MINUS);
        Utils.setPositionForTag(goodsViewHolder.minusButton, position);
        goodsViewHolder.minusButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.addButton, ViewType.VIEW_TYPE_PLUS);
        Utils.setPositionForTag(goodsViewHolder.addButton, position);
        goodsViewHolder.addButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.goodsDeleteButton, ViewType.VIEW_TYPE_DELETE_GOODS);
        Utils.setPositionForTag(goodsViewHolder.goodsDeleteButton, position);
        goodsViewHolder.goodsDeleteButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.goodsNumberEditText, ViewType.VIEW_TYPE_EDIT);
        Utils.setPositionForTag(goodsViewHolder.goodsNumberEditText, position);
        goodsViewHolder.goodsNumberEditText.setOnClickListener(onClickListener);
    }

    private void bindMoreUnpaidOrderViewHolder(CartUnpayedMoreViewHolder viewHolder,
                                               UnpayedListMoreModel object) {
        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_ORDER_LIST);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindShopViewHolder(CartShopViewHolder viewHolder, ShopListModel object, int
            position) {
        viewHolder.nameTextView.setText(object.shop_info.shop_name);
        if (Utils.isNull(object.bonus_list)) {
            viewHolder.nameCartShopGrabBonus.setVisibility(View.GONE);
        } else {
            viewHolder.nameCartShopGrabBonus.setVisibility(View.VISIBLE);
        }

        viewHolder.treeCheckBox.setSelected(object.select);

        Utils.setViewTypeForTag(viewHolder.nameTextView, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.nameTextView, position);
        viewHolder.nameTextView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.nameCartShopGrabBonus, ViewType.VIEW_TYPE_BONUS);
        Utils.setPositionForTag(viewHolder.nameCartShopGrabBonus, position);
        viewHolder.nameCartShopGrabBonus.setOnClickListener(onClickListener);

    }

    private void bindUnpaidOrderTitleViewHolder(CartUnpayedNumberViewHolder viewHolder,
                                                UnpaidOrderTitleModel object) {
/*        String format = viewHolder.unpayedNumber.getContext().getString(
                R.string.unpayedOrderListNumber);
        viewHolder.unpayedNumber.setText(String.format(format, object.number));*/
        viewHolder.unpayedNumber.setText(object.number);
    }

    private void bindUnpaidOrderViewHolder(CartUnpayedViewHolder viewHolder, UnpaidOrderModel
            object, int position) {
        String addTime = String.valueOf(Integer.valueOf(object.add_time));
        viewHolder.unpayedOrderSn.setText("订单编号：" + object.order_sn);
        viewHolder.unpayedOrderAddTime.setText("下单时间：" + Utils.times(addTime));
        viewHolder.unpayedOrderAmount.setText(Utils.formatMoney(viewHolder.unpayedOrderAmount
                .getContext(), object.order_amount));

        Utils.setViewTypeForTag(viewHolder.unpayedOrderCheckButton, ViewType.VIEW_TYPE_ORDER);
        Utils.setPositionForTag(viewHolder.unpayedOrderCheckButton, position);
        viewHolder.unpayedOrderCheckButton.setOnClickListener(onClickListener);
    }

    private void bindInvalidClearViewHolder(CartInvalidClearViewHolder viewHolder,
                                            InvalidClearModel object, int position) {
        Utils.setViewTypeForTag(viewHolder.clearButton, ViewType.VIEW_TYPE_CLEAR_INVALID);
        Utils.setPositionForTag(viewHolder.clearButton, position);
        viewHolder.clearButton.setOnClickListener(onClickListener);
    }

    private void bindStoreTipViewHolder(CartStoreTipViewHolder viewHolder,
                                        StoreTipModel object, int position) {

        double b1 = Double.valueOf(object.start_price);
        double b2 = Double.valueOf(object.select_shop_amount);

         if(b1 > b2 ) {
            viewHolder.linearlayout_root.setVisibility(View.VISIBLE);
            viewHolder.textView_tips.setText("尚未达到起送价，起送价为：¥ "+object.start_price+",快去选购吧！");
        } else {
            viewHolder.linearlayout_root.setVisibility(View.GONE);
            viewHolder.textView_tips.setText("");
        }

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    private RecyclerView.ViewHolder createBlankViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.item_divider_one, parent, false);

        view.setBackgroundResource(R.color.transparent);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createEmptyViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_empty, parent, false);
        return new EmptyViewHolder(view);
    }

    private RecyclerView.ViewHolder createInvalidTitleViewHolder(LayoutInflater inflater,
                                                                 ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_free_cart_item_invalid_title, parent, false);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createGiftViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_free_item_gift, parent, false);
        return new CartGoodsGiftViewHolder(view);
    }

    private RecyclerView.ViewHolder createInvalidListViewHolder(LayoutInflater inflater,
                                                                ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_free_cart_item_invalid_list, parent, false);
        return new CartGoodsGiftViewHolder(view);
    }

    private RecyclerView.ViewHolder createInvalidClearViewHolder(LayoutInflater inflater,
                                                                 ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_free_cart_item_invalid_clear, parent, false);
        return new CartInvalidClearViewHolder(view);
    }

    private RecyclerView.ViewHolder createStoreTipViewHolder(LayoutInflater inflater,
                                                             ViewGroup parent){
        View view = inflater.inflate(R.layout.fragment_cart_item_store_tip, parent, false);
        return new CartStoreTipViewHolder(view);
    }


    private RecyclerView.ViewHolder createGoodsViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_free_item_goods, parent, false);
        return new CartGoodsViewHolder(view);
    }

    private RecyclerView.ViewHolder createMoreUnpaidOrderViewHolder(LayoutInflater inflater,
                                                                    ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_unpaid_order_more, parent, false);
        return new CartUnpayedMoreViewHolder(view);
    }

    private RecyclerView.ViewHolder createShopViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_free_item_shop, parent, false);
        return new CartShopViewHolder(view);
    }

    private RecyclerView.ViewHolder createUnpaidOrderTitleViewHolder(LayoutInflater inflater,
                                                                     ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_unpaid_order_title, parent, false);
        return new CartUnpayedNumberViewHolder(view);
    }

    private RecyclerView.ViewHolder createUnpaidOrderViewHolder(LayoutInflater inflater,
                                                                ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_unpaid_order, parent, false);
        return new CartUnpayedViewHolder(view);
    }
}
