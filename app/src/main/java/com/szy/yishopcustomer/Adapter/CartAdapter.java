package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ActInfo;
import com.szy.yishopcustomer.ResponseModel.CartIndex.FullCutModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GiftModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidClearModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ShopListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.SkuListBean;
import com.szy.yishopcustomer.ResponseModel.CartIndex.StoreTipModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpayedListMoreModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.WholeModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Cart.CartFullcutViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartGoodsGiftViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartGoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartInvalidClearViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartInvalidGoodsTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartPackageViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartShopViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartStoreTipViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartUnpayedMoreViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartUnpayedNumberViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartUnpayedViewHolder;
import com.szy.yishopcustomer.ViewHolder.Cart.CartWholeViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.EmptyViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zongren on 16/5/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartAdapter extends RecyclerView.Adapter {

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
    private final int ITEM_TYPE_WHOLE = 12;

    //促销套餐
    private final int ITEM_TYPE_PACKAGE = 13;
    //满减商品
    private final int ITEM_TYPE_FULLCUT = 14;

    public String rc_code;
    public boolean isEdit = true;

    public List<Object> data;
    public TextWatcherAdapter.TextWatcherListener onTextChangeListener;
    public View.OnClickListener onClickListener;

    public CartAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_GOODS:
                return createGoodsViewHolder(inflater, parent);
            case ITEM_TYPE_PACKAGE:
                return createPackageViewHolder(inflater, parent);
            case ITEM_TYPE_FULLCUT:
                return createFullcutViewHolder(inflater, parent);
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
            case ITEM_TYPE_WHOLE:
                return createWholeViewHolder(inflater, parent);
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
                        position,"");
                break;
            case ITEM_TYPE_PACKAGE:
                bindPackageViewHolder((CartPackageViewHolder) viewHolder, (ActInfo) object,
                        position);
                break;
            case ITEM_TYPE_FULLCUT:
                bindFullcutViewHolder((CartFullcutViewHolder) viewHolder, (FullCutModel) object,
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
                bindInvalidTitleViewHolder((CartInvalidGoodsTitleViewHolder) viewHolder, (InvalidTitleModel)
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
            case ITEM_TYPE_WHOLE:
                bindWholesViewHolder((CartWholeViewHolder) viewHolder, (WholeModel) object,
                        position);
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
        } else if (object instanceof ActInfo) {
            return ITEM_TYPE_PACKAGE;
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
        } else if (object instanceof StoreTipModel) {
            return ITEM_TYPE_STORE_TIP;
        } else if (object instanceof WholeModel) {
            return ITEM_TYPE_WHOLE;
        } else if (object instanceof FullCutModel) {
            return ITEM_TYPE_FULLCUT;
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
    }

    private void bindInvalidTitleViewHolder(CartInvalidGoodsTitleViewHolder viewHolder, InvalidTitleModel
            object) {
        Context context = viewHolder.invalidGoodsNumber.getContext();
        viewHolder.invalidGoodsNumber.setText(String.format(context.getResources().getString(R.string.formatInvalidGoodsNum), object.invalidGoodsNumber));

        Utils.setViewTypeForTag(viewHolder.invalidGoodsCollect, ViewType.VIEW_TYPE_GOODS_COLLECTION);
        viewHolder.invalidGoodsCollect.setOnClickListener(onClickListener);
        Utils.setViewTypeForTag(viewHolder.invalidGoodsClear, ViewType.VIEW_TYPE_CLEAR_INVALID);
        viewHolder.invalidGoodsClear.setOnClickListener(onClickListener);
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


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) goodsGiftViewHolder.goodsImageView.getLayoutParams();
        Context context = goodsGiftViewHolder.goodsImageView.getContext();
        if (!TextUtils.isEmpty(rc_code)) {
            layoutParams.setMargins(0, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
        } else {
            layoutParams.setMargins(Utils.dpToPx(context, 31), layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
        }
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


    private void bindPackageViewHolder(CartPackageViewHolder goodsViewHolder, ActInfo
            actInfo, int position) {
        Context mContext = goodsViewHolder.itemView.getContext();

        goodsViewHolder.treeCheckBox.setSelected(actInfo.select);
        goodsViewHolder.nameTextView.setText(actInfo.act_name);
        goodsViewHolder.priceTextView.setText("¥ " + actInfo.act_goods_price);
        goodsViewHolder.goodsNumberEditText.setText(actInfo.goods_number);

        goodsViewHolder.linearlayout_goods.removeAllViews();
        if (actInfo.goods_list != null) {
            for (Map.Entry<String, GoodsModel> entry : actInfo.goods_list.entrySet()) {
                View goodView = LayoutInflater.from(mContext).inflate(
                        R.layout.fragment_cart_item_package_goods_item, null);

                GoodsModel shopGoodsModel = entry.getValue();

                ImageView item_cart_goods_imageView = (ImageView) goodView.findViewById(R.id.item_cart_goods_imageView);
                TextView nameTextView = (TextView) goodView.findViewById(R.id.item_cart_goods_name_textView);
                TextView priceTextView = (TextView) goodView.findViewById(R.id.item_cart_goods_price_textView);
                TextView fragment_cart_goods_number = (TextView) goodView.findViewById(R.id.fragment_cart_goods_number);
                TextView item_cart_goods_attribute_textView = (TextView) goodView.findViewById(R.id.item_cart_goods_attribute_textView);

                fragment_cart_goods_number.setText("X " + actInfo.goods_number);
                priceTextView.setText(
                        shopGoodsModel.goods_price_format);
                ImageLoader.displayImage(Utils.urlOfImage(shopGoodsModel.sku_image), item_cart_goods_imageView);
                nameTextView.setText(shopGoodsModel.goods_name);
                if (shopGoodsModel.spec_names != null) {
                    String spec_names = "";
                    for (int i = 0; i < shopGoodsModel.spec_names.size(); i++) {
                        spec_names += shopGoodsModel.spec_names.get(i);
                    }
                    item_cart_goods_attribute_textView.setText(spec_names);
                }

                Utils.setViewTypeForTag(item_cart_goods_imageView, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(item_cart_goods_imageView, position);
                Utils.setExtraInfoForTag(item_cart_goods_imageView, Integer.parseInt(shopGoodsModel.cart_id));
                item_cart_goods_imageView.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(nameTextView, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(nameTextView, position);
                Utils.setExtraInfoForTag(nameTextView, Integer.parseInt(shopGoodsModel.cart_id));
                nameTextView.setOnClickListener(onClickListener);

                goodsViewHolder.linearlayout_goods.addView(goodView);

                if (actInfo.isInvalid) {
                    //失效商品
                    int invalidColor = mContext.getResources().getColor(R.color.colorSix);

                    nameTextView.setTextColor(invalidColor);
                    priceTextView.setTextColor(invalidColor);
                    fragment_cart_goods_number.setTextColor(invalidColor);
                    item_cart_goods_attribute_textView.setTextColor(invalidColor);
                }

            }

            LinearLayout lastView = (LinearLayout) goodsViewHolder.linearlayout_goods.getChildAt(goodsViewHolder.linearlayout_goods.getChildCount() - 1);
            lastView.removeViewAt(1);
        }

        goodsViewHolder.treeCheckBox.setVisibility(View.VISIBLE);
        goodsViewHolder.treeCheckBox.setSelected(actInfo.select);

        Utils.setViewTypeForTag(goodsViewHolder.treeCheckBox, ViewType.VIEW_TYPE_SELECT_GOODS);
        Utils.setPositionForTag(goodsViewHolder.treeCheckBox, position);
        goodsViewHolder.treeCheckBox.setOnClickListener(onClickListener);

        goodsViewHolder.minusButton.setVisibility(View.VISIBLE);
        goodsViewHolder.addButton.setVisibility(View.VISIBLE);
        goodsViewHolder.goodsNumberEditText.setVisibility(View.VISIBLE);

        if ("1".equals(actInfo.goods_number)) {
            goodsViewHolder.minusButton.setEnabled(false);
            goodsViewHolder.minusButton.setImageResource(R.mipmap.btn_minus_disabled);
        } else {
            goodsViewHolder.minusButton.setImageResource(R.mipmap.btn_minus_normal);
            goodsViewHolder.minusButton.setEnabled(true);

            Utils.setViewTypeForTag(goodsViewHolder.minusButton, ViewType.VIEW_TYPE_MINUS);
            Utils.setPositionForTag(goodsViewHolder.minusButton, position);
            goodsViewHolder.minusButton.setOnClickListener(onClickListener);

        }

        Utils.setViewTypeForTag(goodsViewHolder.addButton, ViewType.VIEW_TYPE_PLUS);
        Utils.setPositionForTag(goodsViewHolder.addButton, position);
        goodsViewHolder.addButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.goodsDeleteButton, ViewType.VIEW_TYPE_DELETE_GOODS);
        Utils.setPositionForTag(goodsViewHolder.goodsDeleteButton, position);
        goodsViewHolder.goodsDeleteButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.goodsNumberEditText, ViewType.VIEW_TYPE_EDIT);
        Utils.setPositionForTag(goodsViewHolder.goodsNumberEditText, position);
        goodsViewHolder.goodsNumberEditText.setOnClickListener(onClickListener);


        if (actInfo.isInvalid) {
            //失效商品
            int invalidColor = mContext.getResources().getColor(R.color.colorSix);
            goodsViewHolder.treeCheckBox.setVisibility(View.GONE);
            goodsViewHolder.item_cart_goods_invalid_tip.setVisibility(View.VISIBLE);

            goodsViewHolder.nameTextView.setTextColor(invalidColor);
            goodsViewHolder.priceTextView.setTextColor(invalidColor);
            goodsViewHolder.item_order_explain.setBackgroundResource(R.drawable.invalid_goods_tag);
            goodsViewHolder.layout_add_to_cart.setVisibility(View.GONE);
            goodsViewHolder.textViewSetPriceTip.setTextColor(invalidColor);
        } else {
            goodsViewHolder.treeCheckBox.setVisibility(View.VISIBLE);
            goodsViewHolder.item_cart_goods_invalid_tip.setVisibility(View.GONE);

            goodsViewHolder.nameTextView.setTextColor(mContext.getResources().getColor(R.color.colorOne));
            goodsViewHolder.priceTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            goodsViewHolder.item_order_explain.setBackgroundResource(R.drawable.enable_button);
            goodsViewHolder.layout_add_to_cart.setVisibility(View.VISIBLE);
            goodsViewHolder.textViewSetPriceTip.setTextColor(mContext.getResources().getColor(R.color.colorOne));
        }
    }

    private void bindFullcutViewHolder(CartFullcutViewHolder viewHolder, FullCutModel
            fullCutModel, int position){

        Context mContext = viewHolder.itemView.getContext();

        viewHolder.textViewActMessgae.setText(fullCutModel.act_info.act_message + (TextUtils.isEmpty(fullCutModel.act_info.reduce_cash) ? "":("减" + fullCutModel.act_info.reduce_cash)));

        viewHolder.linearlayout_goods.removeAllViews();
        for (TreeMap.Entry<String, GoodsModel> entry :  fullCutModel.goods_list.entrySet()) {
            View goodView = LayoutInflater.from(mContext).inflate(R.layout.fragment_cart_item_goods_fullcut, null);

            CartGoodsViewHolder cartGoodsViewHolder = new CartGoodsViewHolder(goodView);
            bindGoodsViewHolder(cartGoodsViewHolder,entry.getValue(),position,entry.getKey());

            viewHolder.linearlayout_goods.addView(goodView);
        }

        if(Utils.isNull(fullCutModel.act_info.gift_list)) {
            viewHolder.linearlayoutGift.setVisibility(View.GONE);
        } else {
            viewHolder.linearlayoutGift.setVisibility(View.VISIBLE);
            viewHolder.linearlayoutGiftList.removeAllViews();

            //循环
            for(int i = 0 ; i < fullCutModel.act_info.gift_list.size() ; i ++ ){

                GiftModel giftModel = fullCutModel.act_info.gift_list.get(i);
                View giftGoodView = LayoutInflater.from(mContext).inflate(
                        R.layout.fragment_cart_item_goods_gift, null);

                ImageView imageViewGoodImage = (ImageView) giftGoodView.findViewById(R.id.imageViewGoodImage);
                TextView textViewGoodName = (TextView) giftGoodView.findViewById(R.id.textViewGoodName);

                ImageLoader.displayImage(Utils.urlOfImage(giftModel.goods_image),imageViewGoodImage);
                textViewGoodName.setText(giftModel.sku_name);

                viewHolder.linearlayoutGiftList.addView(giftGoodView);
            }
        }

        Utils.setViewTypeForTag(viewHolder.textViewGoList, ViewType.VIEW_FULLCUT_LIST);
        Utils.setExtraInfoForTag(viewHolder.textViewGoList, Integer.parseInt(fullCutModel.act_info.order_act_id));
        viewHolder.textViewGoList.setOnClickListener(onClickListener);

    }

    private void setExtra(View view,String extra){
        if(!TextUtils.isEmpty(extra)) {
            try {
                Utils.setExtraInfoForTag(view, Integer.parseInt(extra));
            }catch (Exception e) {}
        }
    }

    private void bindGoodsViewHolder(CartGoodsViewHolder goodsViewHolder, GoodsModel
            shopGoodsModel, int position,String extra) {
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
        setExtra(goodsViewHolder.nameTextView,extra);
        goodsViewHolder.nameTextView.setOnClickListener(onClickListener);

        goodsViewHolder.priceTextView.setText(Utils.formatMoney(goodsViewHolder.priceTextView.getContext(), shopGoodsModel.goods_price_format));

        goodsViewHolder.goodsNumberEditText.setText(shopGoodsModel.goods_number);

        if (shopGoodsModel.activity != null) {
            if (shopGoodsModel.activity.act_code.equals("group_buy")) {
                goodsViewHolder.goodsActivityTip.setVisibility(View.VISIBLE);
                goodsViewHolder.goodsActivityTip.setText(R.string.groupBuy);
                goodsViewHolder.goodsActivityTip.setBackgroundColor(Color.parseColor
                        (shopGoodsModel.activity.act_color));
            } else {
                goodsViewHolder.goodsActivityTip.setVisibility(View.GONE);
            }
        } else {
            goodsViewHolder.goodsActivityTip.setVisibility(View.GONE);
        }

        if (shopGoodsModel.spec_names != null) {
            String spec_names = "";
            for (int i = 0; i < shopGoodsModel.spec_names.size(); i++) {
                spec_names += shopGoodsModel.spec_names.get(i);
            }
            goodsViewHolder.attrTextView.setText(spec_names);
        }

        Utils.setViewTypeForTag(goodsViewHolder.goodsImageView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(goodsViewHolder.goodsImageView, position);
        setExtra(goodsViewHolder.goodsImageView,extra);
        goodsViewHolder.goodsImageView.setOnClickListener(onClickListener);

        ImageLoader.displayImage(Utils.urlOfImage(shopGoodsModel.sku_image), goodsViewHolder
                .goodsImageView);


        goodsViewHolder.treeCheckBox.setVisibility(View.VISIBLE);
        goodsViewHolder.treeCheckBox.setSelected(shopGoodsModel.select);

        Utils.setViewTypeForTag(goodsViewHolder.treeCheckBox, ViewType.VIEW_TYPE_SELECT_GOODS);
        Utils.setPositionForTag(goodsViewHolder.treeCheckBox, position);
        setExtra(goodsViewHolder.treeCheckBox,extra);
        goodsViewHolder.treeCheckBox.setOnClickListener(onClickListener);

        goodsViewHolder.minusButton.setVisibility(View.VISIBLE);
        goodsViewHolder.addButton.setVisibility(View.VISIBLE);
        goodsViewHolder.goodsNumberEditText.setVisibility(View.VISIBLE);

        if (shopGoodsModel.goods_number.equals("1")) {
            goodsViewHolder.minusButton.setEnabled(false);
            goodsViewHolder.minusButton.setImageResource(R.mipmap.btn_minus_disabled);
        } else {
            goodsViewHolder.minusButton.setImageResource(R.mipmap.btn_minus_normal);
            goodsViewHolder.minusButton.setEnabled(true);

            Utils.setViewTypeForTag(goodsViewHolder.minusButton, ViewType.VIEW_TYPE_MINUS);
            Utils.setPositionForTag(goodsViewHolder.minusButton, position);
            setExtra(goodsViewHolder.minusButton,extra);
            goodsViewHolder.minusButton.setOnClickListener(onClickListener);

        }

        Utils.setViewTypeForTag(goodsViewHolder.addButton, ViewType.VIEW_TYPE_PLUS);
        Utils.setPositionForTag(goodsViewHolder.addButton, position);
        setExtra(goodsViewHolder.addButton,extra);
        goodsViewHolder.addButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.goodsDeleteButton, ViewType.VIEW_TYPE_DELETE_GOODS);
        Utils.setPositionForTag(goodsViewHolder.goodsDeleteButton, position);
        setExtra(goodsViewHolder.goodsDeleteButton,extra);
        goodsViewHolder.goodsDeleteButton.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(goodsViewHolder.goodsNumberEditText, ViewType.VIEW_TYPE_EDIT);
        Utils.setPositionForTag(goodsViewHolder.goodsNumberEditText, position);
        setExtra(goodsViewHolder.goodsNumberEditText,extra);
        goodsViewHolder.goodsNumberEditText.setOnClickListener(onClickListener);

        if (!TextUtils.isEmpty(rc_code)) {
            goodsViewHolder.treeCheckBox.setVisibility(View.INVISIBLE);
            goodsViewHolder.treeCheckBox.getLayoutParams().width = Utils.dpToPx(goodsViewHolder.treeCheckBox.getContext(), 10);
        } else {
            goodsViewHolder.treeCheckBox.getLayoutParams().width = Utils.dpToPx(goodsViewHolder.treeCheckBox.getContext(), 41);
        }

        if (!TextUtils.isEmpty(shopGoodsModel.max_integral_num) && Integer.parseInt(shopGoodsModel.max_integral_num) > 0) {
            goodsViewHolder.deductibleTextView.setVisibility(View.VISIBLE);
            goodsViewHolder.deductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(goodsViewHolder.deductibleTextView.getContext(), shopGoodsModel.max_integral_num));
        } else {
            goodsViewHolder.deductibleTextView.setVisibility(View.INVISIBLE);
        }

    }

    private void bindWholesViewHolder(CartWholeViewHolder wholeViewHolder, WholeModel
            shopGoodsModel, int position) {
        wholeViewHolder.nameTextView.setText(shopGoodsModel.goods_name);

        if (!"1".equals(shopGoodsModel.goods_moq)) {
            wholeViewHolder.goodsMoq.setVisibility(View.VISIBLE);
            wholeViewHolder.goodsMoq.setText(wholeViewHolder.goodsMoq.getResources().getString(R
                    .string.minimumQuantity) + shopGoodsModel.goods_moq);
        } else {
            wholeViewHolder.goodsMoq.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(wholeViewHolder.relativeLayout_goods, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(wholeViewHolder.relativeLayout_goods, position);
        wholeViewHolder.relativeLayout_goods.setOnClickListener(onClickListener);

        ImageLoader.displayImage(Utils.urlOfImage(shopGoodsModel.sku_image), wholeViewHolder
                .goodsImageView);

        wholeViewHolder.treeCheckBox.setVisibility(View.VISIBLE);
        wholeViewHolder.treeCheckBox.setSelected(shopGoodsModel.select);

        Utils.setViewTypeForTag(wholeViewHolder.treeCheckBox, ViewType.VIEW_TYPE_SELECT_WHOLE);
        Utils.setPositionForTag(wholeViewHolder.treeCheckBox, position);
        wholeViewHolder.treeCheckBox.setOnClickListener(onClickListener);

        wholeViewHolder.cartWholeListAdapter.itemPosition = position;
        wholeViewHolder.cartWholeListAdapter.onClickListener = onClickListener;
        wholeViewHolder.data.clear();

        for (SkuListBean skuListBean : shopGoodsModel.sku_list.values()) {
            wholeViewHolder.data.add(skuListBean);
        }

        shopGoodsModel.sortSkuList = wholeViewHolder.data;
        wholeViewHolder.cartWholeListAdapter.notifyDataSetChanged();
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

        Utils.setViewTypeForTag(viewHolder.treeCheckBox, ViewType.VIEW_TYPE_SELECT_SHOP);
        Utils.setPositionForTag(viewHolder.treeCheckBox, position);
        viewHolder.treeCheckBox.setOnClickListener(onClickListener);

        if (TextUtils.isEmpty(rc_code)) {
            viewHolder.textViewTableNum.setVisibility(View.GONE);
        } else {
            viewHolder.textViewTableNum.setVisibility(View.VISIBLE);
            viewHolder.textViewTableNum.setText(rc_code);
            viewHolder.textViewTableNum.setRotation(-10);
            viewHolder.treeCheckBox.setVisibility(View.GONE);
        }

    }

    private void bindUnpaidOrderTitleViewHolder(CartUnpayedNumberViewHolder holder,
                                                UnpaidOrderTitleModel object) {
        SpannableStringBuilder style = null;
        style = Utils.spanStringColor(String.format(holder.itemView.getContext().getString(R.string.formatUnpaidList), object.number),
                ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary),
                2,
                object.number.length());
        holder.unpayedNumber.setText(style);
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

        if (b1 > b2) {
            viewHolder.linearlayout_root.setVisibility(View.VISIBLE);
            viewHolder.textView_tips.setText("尚未达到起送价，起送价为：¥ " + object.start_price + ",快去选购吧！");
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
        View view = inflater.inflate(R.layout.item_divider_two, parent, false);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createEmptyViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_empty, parent, false);
        return new EmptyViewHolder(view);
    }

    private RecyclerView.ViewHolder createInvalidTitleViewHolder(LayoutInflater inflater,
                                                                 ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_invalid_title, parent, false);
        return new CartInvalidGoodsTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createGiftViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_gift, parent, false);
        return new CartGoodsGiftViewHolder(view);
    }

    private RecyclerView.ViewHolder createInvalidListViewHolder(LayoutInflater inflater,
                                                                ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_invalid_list, parent, false);
        return new CartGoodsGiftViewHolder(view);
    }

    private RecyclerView.ViewHolder createInvalidClearViewHolder(LayoutInflater inflater,
                                                                 ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_invalid_clear, parent, false);
        return new CartInvalidClearViewHolder(view);
    }

    private RecyclerView.ViewHolder createStoreTipViewHolder(LayoutInflater inflater,
                                                             ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_store_tip, parent, false);
        return new CartStoreTipViewHolder(view);
    }

    private RecyclerView.ViewHolder createGoodsViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_goods, parent, false);
        return new CartGoodsViewHolder(view);
    }

    private RecyclerView.ViewHolder createPackageViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_package, parent, false);
        return new CartPackageViewHolder(view);
    }

    private RecyclerView.ViewHolder createFullcutViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_fullcut, parent, false);
        return new CartFullcutViewHolder(view);
    }

    private RecyclerView.ViewHolder createWholeViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_whole, parent, false);
        return new CartWholeViewHolder(view);
    }

    private RecyclerView.ViewHolder createMoreUnpaidOrderViewHolder(LayoutInflater inflater,
                                                                    ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_unpaid_order_more, parent, false);
        return new CartUnpayedMoreViewHolder(view);
    }

    private RecyclerView.ViewHolder createShopViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_cart_item_shop, parent, false);
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
