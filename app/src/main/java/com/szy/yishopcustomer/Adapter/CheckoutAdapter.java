package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BalanceInputModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BonusItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.EmptyAddressModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.EmptyShipModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.GroupModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.PayItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.PlatformBonusItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.PostscriptModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SendTimeItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShipItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShopInfoModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShopOrderModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.StoreCardModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.UserInfoModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ActivityModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Checkout.AddressViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.BalanceInputViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.BalanceViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.CheckoutDividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.ChildViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.ChildViewHolderTwo;
import com.szy.yishopcustomer.ViewHolder.Checkout.EmptyAddressViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.EmptyShipViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.GoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.GroupViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.OrderInfoViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.PaymentViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.PostscriptViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.ShopOrderViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.ShopViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2016/11/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class CheckoutAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_DIVIDER = 0;
    private final int VIEW_TYPE_GROUP = 1;
    private final int VIEW_TYPE_ADDRESS_ITEM = 2;
    private final int VIEW_TYPE_EMPTY_ADDRESS = 3;
    private final int VIEW_TYPE_SHOP_INFO = 4;
    private final int VIEW_TYPE_GOODS_ITEM = 5;
    private final int VIEW_TYPE_SHOP_ORDER = 6;
    private final int VIEW_TYPE_EMPTY_SHIP = 7;
    private final int VIEW_TYPE_USER_INFO = 8;
    private final int VIEW_TYPE_POSTSCRIPT = 9;
    private final int VIEW_TYPE_PAY_ITEM = 10;
    private final int VIEW_TYPE_BALANCE_INPUT = 11;
    private final int VIEW_TYPE_SEND_TIME = 12;
    private final int VIEW_TYPE_SHIP_ITEM = 13;
    private final int VIEW_TYPE_BONUS_ITEM = 14;
    private final int VIEW_TYPE_PLATFORM_BONUS_ITEM = 15;
    private final int VIEW_TYPE_ORDER_INFO = 16;
    private final int VIEW_TYPE_SHIP_ITEM_TWO = 17;

    private OrderInfoModel mOrderInfoModel;

    public String rc_code;

    public List<Object> data;
    public TextWatcherAdapter.TextWatcherListener onTextChangeListener;
    public TextView.OnEditorActionListener onEditorActionListener;
    public View.OnClickListener onClickListener;
    public RadioGroup.OnCheckedChangeListener onCheckedChangeListener;

    private Context mContext;

    public CheckoutAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();
    }

    public RecyclerView.ViewHolder createAddressItemViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_address, parent, false);
        return new AddressViewHolder(view);
    }

    public RecyclerView.ViewHolder createBalanceInputViewHolder(LayoutInflater inflater,
                                                                ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_balance_input, parent, false);
        return new BalanceInputViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_divider, parent, false);
        return new CheckoutDividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createEmptyAddressViewHolder(LayoutInflater inflater,
                                                                ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_empty_address, parent, false);
        return new EmptyAddressViewHolder(view);
    }

    public RecyclerView.ViewHolder createEmptyShipViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_empty_ship, parent, false);
        return new EmptyShipViewHolder(view);
    }

    public RecyclerView.ViewHolder createGoodsItemViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_goods, parent, false);
        return new GoodsViewHolder(view);
    }

    public RecyclerView.ViewHolder createGroupViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_group, parent, false);
        return new GroupViewHolder(view);
    }

    public RecyclerView.ViewHolder createItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_child, parent, false);
        return new ChildViewHolder(view);
    }

    public RecyclerView.ViewHolder createItemViewHolderTwo(LayoutInflater inflater, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_child_two, parent, false);
        return new ChildViewHolderTwo(view);
    }

    public RecyclerView.ViewHolder createPayItemViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_payment2, parent, false);
        return new PaymentViewHolder(view);
    }

    public RecyclerView.ViewHolder createPlatformBonusItemViewHolder(LayoutInflater inflater,
                                                                     ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_child, parent, false);
        return new ChildViewHolder(view);
    }

    public RecyclerView.ViewHolder createPostscriptViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_postscript, parent, false);
        return new PostscriptViewHolder(view);
    }

    public RecyclerView.ViewHolder createShopInfoViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_shop, parent, false);
        return new ShopViewHolder(view);
    }

    public RecyclerView.ViewHolder createShopOrderViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_shop_order, parent, false);
        return new ShopOrderViewHolder(view);
    }

    /***
     * 用户余额支付 item
     * @param inflater
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder createUserInfoViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_checkout_balance, parent, false);
        return new BalanceViewHolder(view);
    }

    public RecyclerView.ViewHolder createOrderInfoViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_checkout_order_info, parent, false);
        return new OrderInfoViewHolder(view);
    }

    public int findGroupModelBefore(int position) {
        if (position < 0) {
            return 0;
        }

        for (int i = position; i >= 0; i--) {
            Object object = data.get(i);
            if (object instanceof GroupModel) {
                return i;
            }
        }
        return 0;
    }

    public void setOrderInfo(OrderInfoModel orderInfo) {
        this.mOrderInfoModel = orderInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(inflater, parent);
            case VIEW_TYPE_GROUP:
                return createGroupViewHolder(inflater, parent);
            case VIEW_TYPE_ADDRESS_ITEM:
                return createAddressItemViewHolder(inflater, parent);
            case VIEW_TYPE_EMPTY_ADDRESS:
                return createEmptyAddressViewHolder(inflater, parent);
            case VIEW_TYPE_SHOP_INFO:
                return createShopInfoViewHolder(inflater, parent);
            case VIEW_TYPE_GOODS_ITEM:
                return createGoodsItemViewHolder(inflater, parent);
            case VIEW_TYPE_SHOP_ORDER:
                return createShopOrderViewHolder(inflater, parent);
            case VIEW_TYPE_EMPTY_SHIP:
                return createEmptyShipViewHolder(inflater, parent);
            case VIEW_TYPE_USER_INFO:
                return createUserInfoViewHolder(inflater, parent);
            case VIEW_TYPE_POSTSCRIPT:
                return createPostscriptViewHolder(inflater, parent);
            case VIEW_TYPE_BONUS_ITEM:
                return createItemViewHolder(inflater, parent);
            case VIEW_TYPE_PLATFORM_BONUS_ITEM:
                return createPlatformBonusItemViewHolder(inflater, parent);
            case VIEW_TYPE_SHIP_ITEM:
                return createItemViewHolder(inflater, parent);
            case VIEW_TYPE_SHIP_ITEM_TWO:
                return createItemViewHolderTwo(inflater, parent);
            case VIEW_TYPE_SEND_TIME:
                return createItemViewHolder(inflater, parent);
            case VIEW_TYPE_BALANCE_INPUT:
                return createBalanceInputViewHolder(inflater, parent);
            case VIEW_TYPE_PAY_ITEM:
                return createPayItemViewHolder(inflater, parent);
            case VIEW_TYPE_ORDER_INFO:
                return createOrderInfoViewHolder(inflater, parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Object object = data.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_DIVIDER:
                break;
            case VIEW_TYPE_GROUP:
                bindGroupViewHolder((GroupViewHolder) viewHolder, (GroupModel) object, position);
                break;
            case VIEW_TYPE_ADDRESS_ITEM:
                bindAddressItemViewHolder((AddressViewHolder) viewHolder, (AddressItemModel)
                        object);
                break;
            case VIEW_TYPE_EMPTY_ADDRESS:
                bindEmptyAddressViewHolder((EmptyAddressViewHolder) viewHolder,
                        (EmptyAddressModel) object);
                break;
            case VIEW_TYPE_SHOP_INFO:
                bindShopInfoViewHolder((ShopViewHolder) viewHolder, (ShopInfoModel) object);
                break;
            case VIEW_TYPE_GOODS_ITEM:
                bindGoodsItemViewHolder((GoodsViewHolder) viewHolder, (GoodsItemModel) object);
                break;
            case VIEW_TYPE_SHOP_ORDER:
                bindShopOrderViewHolder((ShopOrderViewHolder) viewHolder, (ShopOrderModel) object,position);
                break;
            case VIEW_TYPE_EMPTY_SHIP:
                break;
            case VIEW_TYPE_USER_INFO:
                bindUserInfoViewHolder((BalanceViewHolder) viewHolder, (UserInfoModel) object, position);
                break;
            case VIEW_TYPE_POSTSCRIPT:
                bindPostScriptViewHolder((PostscriptViewHolder) viewHolder, (PostscriptModel)
                        object, position);
                break;
            case VIEW_TYPE_BONUS_ITEM:
                bindBonusItemViewHolder((ChildViewHolder) viewHolder, (BonusItemModel) object,
                        position);
                break;
            case VIEW_TYPE_PLATFORM_BONUS_ITEM:
                bindPlatformBonusItemViewHolder((ChildViewHolder) viewHolder,
                        (PlatformBonusItemModel) object, position);
                break;
            case VIEW_TYPE_SHIP_ITEM_TWO:
                bindShipItemViewHolderTwo((ChildViewHolderTwo) viewHolder, (List<ShipItemModel>) object,
                        position);
                break;
            case VIEW_TYPE_SHIP_ITEM:
                bindShipItemViewHolder((ChildViewHolder) viewHolder, object,
                        position);
                break;
            case VIEW_TYPE_SEND_TIME:
                bindSendTimeViewHolder((ChildViewHolder) viewHolder, (SendTimeItemModel) object,
                        position);
                break;
            case VIEW_TYPE_BALANCE_INPUT:
                bindBalanceInputViewHolder((BalanceInputViewHolder) viewHolder,
                        (BalanceInputModel) object);
                break;
            case VIEW_TYPE_PAY_ITEM:
                bindPayItemViewHolder((PaymentViewHolder) viewHolder, (PayItemModel) object,
                        position);
                break;
            case VIEW_TYPE_ORDER_INFO:
                bindOrderInfoViewHolder((OrderInfoViewHolder) viewHolder, (OrderInfoModel)
                        object, position);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof CheckoutDividerModel) {
            return VIEW_TYPE_DIVIDER;
        } else if (object instanceof GroupModel) {
            return VIEW_TYPE_GROUP;
        } else if (object instanceof AddressItemModel) {
            return VIEW_TYPE_ADDRESS_ITEM;
        } else if (object instanceof EmptyAddressModel) {
            return VIEW_TYPE_EMPTY_ADDRESS;
        } else if (object instanceof ShopInfoModel) {
            return VIEW_TYPE_SHOP_INFO;
        } else if (object instanceof GoodsItemModel) {
            return VIEW_TYPE_GOODS_ITEM;
        } else if (object instanceof ShopOrderModel) {
            return VIEW_TYPE_SHOP_ORDER;
        } else if (object instanceof EmptyShipModel) {
            return VIEW_TYPE_EMPTY_SHIP;
        } else if (object instanceof UserInfoModel) {
            return VIEW_TYPE_USER_INFO;
        } else if (object instanceof PostscriptModel) {
            return VIEW_TYPE_POSTSCRIPT;
        } else if (object instanceof PayItemModel) {
            return VIEW_TYPE_PAY_ITEM;
        } else if (object instanceof BalanceInputModel) {
            return VIEW_TYPE_BALANCE_INPUT;
        } else if (object instanceof SendTimeItemModel) {
            return VIEW_TYPE_SEND_TIME;
        } else if (object instanceof List) {
            return VIEW_TYPE_SHIP_ITEM_TWO;
        } else if (object instanceof BonusItemModel) {
            return VIEW_TYPE_BONUS_ITEM;
        } else if (object instanceof PlatformBonusItemModel) {
            return VIEW_TYPE_PLATFORM_BONUS_ITEM;
        } else if (object instanceof OrderInfoModel) {
            return VIEW_TYPE_ORDER_INFO;
        } else if (object instanceof ShipItemModel) {
            return VIEW_TYPE_SHIP_ITEM;
        } else if (object instanceof StoreCardModel) {
            return VIEW_TYPE_SHIP_ITEM;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindAddressItemViewHolder(AddressViewHolder viewHolder, AddressItemModel object) {
        viewHolder.consigneeTextView.setText(object.consignee);
        viewHolder.phoneTextView.setText(object.mobile_format);
        viewHolder.consigneeAddressTextView.setText(object.region_name + "" + object.address_detail);

        if (!object.is_real.equals("0")) {
            viewHolder.realNameLayout.setVisibility(View.VISIBLE);
            viewHolder.idTextView.setText(object.id_code_format);
            viewHolder.nameTextView.setText(object.real_name);
        } else {
            viewHolder.realNameLayout.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(viewHolder.addressLayout, ViewType.VIEW_TYPE_ADDRESS_CHOICE);
        viewHolder.addressLayout.setOnClickListener(onClickListener);

    }

    private void bindBalanceInputViewHolder(BalanceInputViewHolder viewHolder, BalanceInputModel
            object) {
        viewHolder.balanceEditText.setText(object.balance);
        viewHolder.balanceTextView.setText(object.balance_format);
        if (Double.valueOf(object.money_pay) <= 0) {
            viewHolder.paymentLaybelLayout.setVisibility(View.GONE);
        } else {
            viewHolder.moneyTextView.setText(object.money_pay_format);
            viewHolder.paymentLaybelLayout.setVisibility(View.VISIBLE);
        }
        viewHolder.balanceEditText.setOnEditorActionListener(onEditorActionListener);
    }

    private void bindBonusItemViewHolder(ChildViewHolder viewHolder, BonusItemModel object, int
            position) {
        viewHolder.titleTextView.setText(object.bonus_name);

        if (!Utils.isNull(object.selected)) {
            if (object.selected.equals("selected")) {
                viewHolder.indicatorImageView.setSelected(true);
            } else {
                viewHolder.indicatorImageView.setSelected(false);
            }
        } else {
            viewHolder.indicatorImageView.setSelected(false);
        }

        viewHolder.childImage.setVisibility(View.GONE);
        viewHolder.subTitleTextView.setVisibility(View.GONE);
        viewHolder.titleTwoTextView.setVisibility(View.GONE);
        Utils.setViewTypeForTag(viewHolder.childLayout, ViewType.VIEW_TYPE_BONUS);
        Utils.setPositionForTag(viewHolder.childLayout, position);
        viewHolder.childLayout.setOnClickListener(onClickListener);
    }

    private void bindEmptyAddressViewHolder(EmptyAddressViewHolder viewHolder, EmptyAddressModel
            object) {
        Utils.setViewTypeForTag(viewHolder.emptyAddressLayout, ViewType.VIEW_TYPE_ADDRESS);
        viewHolder.emptyAddressLayout.setOnClickListener(onClickListener);
    }

    private void bindGoodsItemViewHolder(GoodsViewHolder viewHolder, GoodsItemModel object) {
        if (object.activity == null) {
            object.activity = new ActivityModel();
        }

        if (Macro.ACTIVITY_TYPE_GOODS_MIX == object.activity.act_type) {
            viewHolder.nameTextView.setText("               " + object.goods_name);
            viewHolder.item_order_explain.setVisibility(View.VISIBLE);
            viewHolder.item_order_explain.setText(R.string.PromotionActivity);
        } else if (Macro.GT_GROUP_BUY_GOODS == object.activity.act_type) {
            viewHolder.nameTextView.setText("        " + object.goods_name);
            viewHolder.item_order_explain.setVisibility(View.VISIBLE);
            viewHolder.item_order_explain.setText(R.string.groupBuy);
        } else if (Macro.GT_LIMITED_DISCOUNT_GOODS == object.activity.act_type) {
            viewHolder.nameTextView.setText("        " + object.goods_name);
            viewHolder.item_order_explain.setVisibility(View.VISIBLE);
            viewHolder.item_order_explain.setText("限时折扣");
        } else {
            viewHolder.nameTextView.setText(object.goods_name);
            viewHolder.item_order_explain.setVisibility(View.GONE);
        }

        viewHolder.priceTextView.setText(Utils.formatMoney(viewHolder.priceTextView.getContext(), object.goods_price_format));

        viewHolder.numberTextView.setText(String.format(viewHolder.numberTextView.getContext()
                .getString(R.string.formatGoodsNumber), object.goods_number));
        if (object.limit) {
            viewHolder.mNoStockImage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mNoStockImage.setVisibility(View.GONE);
        }
        if (object.spec_names != null) {
            viewHolder.attributeTextView.setVisibility(View.VISIBLE);
            viewHolder.attributeTextView.setText(Utils.join(object.spec_names, " "));
        } else {
            viewHolder.attributeTextView.setVisibility(View.INVISIBLE);
            viewHolder.attributeTextView.setText("");
        }
        if (!Utils.isNull(object.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(object.goods_image), viewHolder.imageView);
        }
        if (!Utils.isNull(object.gift_id)) {
            viewHolder.giftTextView.setVisibility(View.VISIBLE);
            viewHolder.giftTextView.setBackgroundColor(ContextCompat.getColor(viewHolder
                    .giftTextView.getContext(), R.color.colorPrimary));
            viewHolder.giftTextView.setText("赠品");
        } else {
            viewHolder.giftTextView.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(object.max_integral_num) && Integer.parseInt(object.max_integral_num) > 0) {
            viewHolder.deductibleTextView.setVisibility(View.VISIBLE);
            viewHolder.deductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(viewHolder.deductibleTextView.getContext(), object.max_integral_num));
        } else {
            viewHolder.deductibleTextView.setVisibility(View.INVISIBLE);
        }

    }

    private void bindGroupViewHolder(GroupViewHolder viewHolder, GroupModel object, int position) {
        viewHolder.titleTextView.setText(object.title);
        viewHolder.selectedItemTextView.setText(object.selectedItem);
        if (object.isExpanded) {
            viewHolder.indicatorImageView.setImageResource(R.mipmap.btn_down_arrow);

            if (object.isShopBouns) {
                viewHolder.itemView.setBackgroundResource(R.drawable.top_border_one);
            }
        } else {
            viewHolder.indicatorImageView.setImageResource(R.mipmap.btn_right_arrow);
            if (object.isShopBouns) {
                viewHolder.itemView.setBackgroundResource(R.drawable.top_bottom_border_one);
            }
        }

        viewHolder.indicatorImageView.setVisibility(View.VISIBLE);
        Utils.setViewTypeForTag(viewHolder.itemView, object.viewType);
        Utils.setPositionForTag(viewHolder.groupLayout, position);
        viewHolder.groupLayout.setOnClickListener(onClickListener);
    }

    private void bindPayItemViewHolder(PaymentViewHolder viewHolder, PayItemModel object, int position) {

        //支付方式 文本
        viewHolder.nameTextView.setText(object.name);

        //微信支付 推荐图标
        Drawable drawableRight = mContext.getResources().getDrawable(R.mipmap.wx_pay_reom);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());

        switch (object.code) {
            case Macro.COD_CODE:
                //获取adapter是否有选择自提点的
                boolean isSelectSinceSome = false;

                for (int i = 0, len = data.size(); i < len; i++) {
                    if (data.get(i) instanceof List) {
                        List<ShipItemModel> shipItemModels = (List<ShipItemModel>) data.get(i);
                        for (int j = 0, jlen = shipItemModels.size(); j < jlen; j++) {
                            if (shipItemModels.get(j).isCheck && "1".equals(shipItemModels.get(j).id)) {
                                isSelectSinceSome = true;
                                break;
                            }
                        }
                        if (isSelectSinceSome) {
                            break;
                        }
                    }
                }
                viewHolder.paymentImageView.setImageResource(R.mipmap.ic_unpaid);
                /*viewHolder.paymentLayout.setBackgroundResource(R.drawable
                        .pay_cod_button_selector);*/
                if (isSelectSinceSome) {
                    viewHolder.itemView.getLayoutParams().height = 0;
                } else {
                    viewHolder.itemView.getLayoutParams().height = Utils.dpToPx(viewHolder.itemView.getContext(), 45);
                }

                break;
            case Macro.ALIPAY_CODE:
                viewHolder.paymentImageView.setImageResource(R.mipmap.ic_alipay);
                /*viewHolder.paymentLayout.setBackgroundResource(R.drawable
                        .pay_alipay_button_selector);*/
                break;
            case Macro.WEIXIN_CODE:
                viewHolder.paymentImageView.setImageResource(R.mipmap.ic_wechat);

                viewHolder.nameTextView.setCompoundDrawables(null, null, drawableRight, null);
                viewHolder.wxTitleText.setVisibility(View.VISIBLE);
                /*viewHolder.paymentLayout.setBackgroundResource(R.drawable
                        .pay_weixin_button_selector);*/
                break;
            case Macro.UNIONPAY_CODE:
                viewHolder.paymentImageView.setImageResource(R.mipmap.ic_bank_card);
                /*viewHolder.paymentLayout.setBackgroundResource(R.drawable
                        .pay_union_button_selector);*/
                break;
        }

        if (object.checked.equals("checked")) {
            //viewHolder.paymentLayout.setEnabled(true);
            viewHolder.paymentCheckbox.setSelected(true);
        } else {
            //viewHolder.paymentLayout.setEnabled(false);
            viewHolder.paymentCheckbox.setSelected(false);
        }

        if (object.label != null) {
            viewHolder.labelTextView.setVisibility(View.VISIBLE);
            viewHolder.labelTextView.setText(object.label);
        } else {
            viewHolder.labelTextView.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_PAYMENT_ITEM);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindPlatformBonusItemViewHolder(ChildViewHolder viewHolder,
                                                 PlatformBonusItemModel object, int position) {
        if (object.bonus_price.equals("0")) {
            viewHolder.titleTextView.setText(object.bonus_name);
            viewHolder.titleTextView.setTextColor(viewHolder.titleTextView.getResources()
                    .getColor(R.color.colorTwo));
            viewHolder.childImage.setBackgroundResource(R.mipmap.ic_bonus_dark);
            viewHolder.titleTwoTextView.setText("");
        } else {
            viewHolder.titleTextView.setText(object.bonus_price_format);
            viewHolder.titleTextView.setTextColor(viewHolder.titleTextView.getResources()
                    .getColor(R.color.colorPrimary));
            viewHolder.childImage.setBackgroundResource(R.mipmap.ic_bonus);
            viewHolder.subTitleTextView.setVisibility(View.VISIBLE);
            if (object.use_range.equals("0")) {
                viewHolder.subTitleTextView.setText("全场通用，有效期至" + object.end_time_format);
            } else {
                viewHolder.subTitleTextView.setText("限品类，有效期至" + object.end_time_format);
            }
            viewHolder.titleTwoTextView.setText("(满" + object.min_goods_amount + ")");
        }

        if (object.selected.equals("selected")) {
            viewHolder.indicatorImageView.setSelected(true);
        } else {
            viewHolder.indicatorImageView.setSelected(false);
        }

        viewHolder.childImage.setVisibility(View.GONE);
        viewHolder.titleTwoTextView.setVisibility(View.VISIBLE);
        viewHolder.titleTwoTextView.setVisibility(View.VISIBLE);
        Utils.setViewTypeForTag(viewHolder.childLayout, ViewType.VIEW_TYPE_PLATFORM_BONUS);
        Utils.setPositionForTag(viewHolder.childLayout, position);
        viewHolder.childLayout.setOnClickListener(onClickListener);
    }

    private void bindPostScriptViewHolder(PostscriptViewHolder viewHolder, PostscriptModel
            object, int position) {
        viewHolder.contentEditText.setText(object.content);
        Utils.setViewTypeForTag(viewHolder.contentEditText, ViewType.VIEW_TYPE_POSTSCRIPT);
        Utils.setPositionForTag(viewHolder.contentEditText, position);
        viewHolder.contentEditText.setTextWatcherListener(onTextChangeListener);
    }

    private void bindSendTimeViewHolder(ChildViewHolder viewHolder, SendTimeItemModel object, int
            position) {
        viewHolder.titleTextView.setText(object.value);
        if (object.checked.equals("checked")) {
            viewHolder.indicatorImageView.setSelected(true);
        } else {
            viewHolder.indicatorImageView.setSelected(false);
        }

        Utils.setViewTypeForTag(viewHolder.childLayout, ViewType.VIEW_TYPE_SEND_TIME_ITEM);
        Utils.setPositionForTag(viewHolder.childLayout, position);
        viewHolder.childLayout.setOnClickListener(onClickListener);

        viewHolder.childImage.setVisibility(View.GONE);
        viewHolder.subTitleTextView.setVisibility(View.GONE);
        viewHolder.titleTwoTextView.setVisibility(View.GONE);
    }

    private void bindShipItemViewHolderTwo(final ChildViewHolderTwo viewHolder, List<ShipItemModel> object, int
            position) {

        Context context = viewHolder.radioGroup.getContext();
        viewHolder.radioGroup.setOnCheckedChangeListener(null);
        viewHolder.radioGroup.removeAllViews();

        int checkCount = 0;
        for (int i = 0, len = object.size(); i < len; i++) {
            RadioButton rb = (RadioButton) LayoutInflater.from(context).inflate(R.layout.fragment_checkout_child_two_item, null);
            rb.setText(object.get(i).name);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            rb.setId(i);

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            if (i == 0) {
                layoutParams.setMargins(0, Utils.dpToPx(context, 5), Utils.dpToPx(context, 10), Utils.dpToPx(context, 5));
            } else {
                layoutParams.setMargins(Utils.dpToPx(context, 10), Utils.dpToPx(context, 5), 0, Utils.dpToPx(context, 5));
            }
            if (object.get(i).isCheck) {
                swtichCheck(object.get(i), viewHolder, position);
            }
            rb.setChecked(object.get(i).isCheck);
            rb.setLayoutParams(layoutParams);
            viewHolder.radioGroup.addView(rb);
        }

//        if(viewHolder.radioGroup.getCheckedRadioButtonId())
//        swtichCheck(object.get(0),viewHolder,position);

        viewHolder.radioGroup.setTag(viewHolder);
        Utils.setPositionForTag(viewHolder.radioGroup, position);

        viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int position = Utils.getPositionOfTag(radioGroup);
                List<ShipItemModel> tempList = (List<ShipItemModel>) data.get(position);

                for (int j = 0; j < tempList.size(); j++) {
                    if (i == j) {
                        tempList.get(j).isCheck = true;
                    } else {
                        tempList.get(j).isCheck = false;
                    }
                }

                if (tempList != null) {
                    swtichCheck(tempList.get(i), viewHolder, position);
                }

                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(radioGroup, i);
                }
            }
        });
    }


    private void bindShipItemViewHolder(ChildViewHolder viewHolder, Object object, int
            position) {
        if (object instanceof ShipItemModel) {
            ShipItemModel shipItemModel = (ShipItemModel) object;
            viewHolder.titleTextView.setText(shipItemModel.name);
            if (shipItemModel.selected.equals("selected")) {
                viewHolder.indicatorImageView.setSelected(true);
            } else {
                viewHolder.indicatorImageView.setSelected(false);
            }

            Utils.setViewTypeForTag(viewHolder.childLayout, ViewType.VIEW_TYPE_SHIP_ITEM);
            Utils.setPositionForTag(viewHolder.childLayout, position);
            viewHolder.childLayout.setOnClickListener(onClickListener);

            viewHolder.childImage.setVisibility(View.GONE);
            viewHolder.subTitleTextView.setVisibility(View.GONE);
            viewHolder.titleTwoTextView.setVisibility(View.GONE);
        } else if (object instanceof StoreCardModel) {
            StoreCardModel storeCardModel = (StoreCardModel) object;
            viewHolder.titleTextView.setText(storeCardModel.type_name);
            if (storeCardModel.selected.equals("selected")) {
                viewHolder.indicatorImageView.setSelected(true);
            } else {
                viewHolder.indicatorImageView.setSelected(false);
            }

            Utils.setViewTypeForTag(viewHolder.childLayout, ViewType.VIEW_TYPE_STORE_CARD_ITEM);
            Utils.setPositionForTag(viewHolder.childLayout, position);
            viewHolder.childLayout.setOnClickListener(onClickListener);

            viewHolder.childImage.setVisibility(View.GONE);
            viewHolder.subTitleTextView.setVisibility(View.GONE);
            viewHolder.titleTwoTextView.setVisibility(View.GONE);
        }

    }

    public void swtichCheck(ShipItemModel shipItemModel, ChildViewHolderTwo viewHolder, int position) {
        if ("1".equals(shipItemModel.id)) {
            if (!TextUtils.isEmpty(shipItemModel.pickup_name)) {
                viewHolder.textView_since_some_tip.setText("自提地点：" + shipItemModel.pickup_name);
                viewHolder.textView_since_some_modify.setVisibility(View.VISIBLE);
                viewHolder.textView_since_some_modify.setTag(R.id.textView_since_some_modify, shipItemModel);
                viewHolder.textView_since_some_modify.setTag(viewHolder.radioGroup.getCheckedRadioButtonId());
                Utils.setViewTypeForTag(viewHolder.textView_since_some_modify, ViewType.VIEW_TYPE_PICK_UP);
                Utils.setPositionForTag(viewHolder.textView_since_some_modify, position);
                viewHolder.textView_since_some_modify.setOnClickListener(onClickListener);
            } else {
                viewHolder.textView_since_some_tip.setText("");
                viewHolder.textView_since_some_modify.setVisibility(View.GONE);
            }
        } else {
            viewHolder.textView_since_some_tip.setText(shipItemModel.name + shipItemModel.price);
            viewHolder.textView_since_some_modify.setVisibility(View.GONE);
        }
    }

    private void bindShopInfoViewHolder(ShopViewHolder viewHolder, ShopInfoModel object) {
        viewHolder.nameTextView.setText(object.shop_name);

        if (TextUtils.isEmpty(rc_code)) {
            viewHolder.textViewTableNum.setVisibility(View.GONE);
        } else {
            viewHolder.textViewTableNum.setVisibility(View.VISIBLE);
            viewHolder.textViewTableNum.setText(rc_code);
            viewHolder.textViewTableNum.setRotation(-10);
        }
    }

    private void bindShopOrderViewHolder(ShopOrderViewHolder viewHolder, ShopOrderModel object,int position) {
        object = (ShopOrderModel) data.get(viewHolder.getAdapterPosition());
        viewHolder.totalPriceTextView.setText(String.format("¥ %s",object.order_amount));
        viewHolder.totalPriceTextViewTip.setText(Utils.spanSizeString(viewHolder.totalPriceTextViewTip.getContext(),
                "店铺合计(含运费): ", 4, 10, 11));

        String str = "";
        boolean b = false;
        if (object.full_cut_amount > 0) {
            str = "立减<font color='#f23030'>" + object.full_cut_amount_format + "</font>元 ";
            b = true;
        }

        if (!Utils.isNull(object.full_cut_bonus) && object.full_cut_bonus.size() > 0) {
            str += "送<font color='#f23030'>" + object.full_cut_bonus_format + "</font>元店铺红包";
            b = true;
        }

        if (b) {
            viewHolder.textViewFullCutAmount.setVisibility(View.VISIBLE);
            viewHolder.textViewFullCutAmount.setText(Html.fromHtml(str));
        } else {
            viewHolder.textViewFullCutAmount.setVisibility(View.GONE);
        }

//        if (mOrderInfoModel.integral_amount_format != null) {
//            viewHolder.totalDeductibleTextView.setText("元宝实际抵扣:-" + Utils.formatMoney(viewHolder.totalDeductibleTextView.getContext(), mOrderInfoModel.integral_amount_format));
//        }
        //viewHolder.totalDeductibleTextView.setText("元宝实际抵扣:-"+Utils.formatMoney(viewHolder.totalDeductibleTextView.getContext(), "12"));
    }

    private void bindUserInfoViewHolder(BalanceViewHolder viewHolder, UserInfoModel object, int position) {

        /***
         * 余额-动态显示
         * 根据用户余额金额 动态 显示or隐藏  余额支付
         */
        viewHolder.linearlayout_root.setVisibility(View.GONE);

        try {
            if (Double.parseDouble(object.balance) > 0) {
                viewHolder.linearlayout_root.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.balanceTextView.setText(object.balance_format);
        if (object.balancedEnabled) {
            viewHolder.switchImageView.setImageResource(R.mipmap.bg_switch_on);
            viewHolder.balanceUsed.setVisibility(View.VISIBLE);
            viewHolder.balanceUsed.setText(",使用" + object.balanceUsed);
        } else {
            viewHolder.switchImageView.setImageResource(R.mipmap.bg_switch_off);
            viewHolder.balanceUsed.setVisibility(View.INVISIBLE);
        }

        Utils.setViewTypeForTag(viewHolder.switchImageView, ViewType.VIEW_TYPE_SWITCH);
        Utils.setPositionForTag(viewHolder.switchImageView, position);
        viewHolder.switchImageView.setOnClickListener(onClickListener);
    }

    private void bindOrderInfoViewHolder(OrderInfoViewHolder viewHolder, OrderInfoModel object,
                                         int position) {
        viewHolder.productAmount.setText(object.goods_amount_format);
        viewHolder.shippingFee.setText("+" + object.shipping_fee_format);
        viewHolder.bonus.setText("-" + object.total_bonus_amount_format);
        viewHolder.benefit.setText("-" + object.full_cut_amount_format);
        viewHolder.balance.setText("-" + object.balance_format);


        //如果是货到付款，订单信息显示货到付款的加价
        //由于样式修改，暂时隐藏，勿删
        /*if (object.is_cod) {
            viewHolder.cashMoreTextView.setVisibility(View.VISIBLE);
            viewHolder.cashMore.setVisibility(View.VISIBLE);
            viewHolder.cashMore.setText("+" + object.cash_more);
        } else {
            viewHolder.cashMoreTextView.setVisibility(View.GONE);
            viewHolder.cashMore.setVisibility(View.GONE);
        }*/

        if (object.is_syunfei) {
            viewHolder.shippingFee.setVisibility(View.VISIBLE);
            viewHolder.shippingFee_text.setVisibility(View.VISIBLE);
        } else {
            viewHolder.shippingFee.setVisibility(View.GONE);
            viewHolder.shippingFee_text.setVisibility(View.GONE);
        }

        viewHolder.shippingDeductible.setText("-" + Utils.formatMoney(viewHolder.shippingDeductible.getContext(), object.integral_amount_format));
    }

}
