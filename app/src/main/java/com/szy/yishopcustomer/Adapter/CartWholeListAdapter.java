package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.SkuListBean;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Cart.CartWholeListViewHolder;

import java.util.List;

/**
 * Created by Smart on 2017/9/7.
 */
public class CartWholeListAdapter extends RecyclerView.Adapter<CartWholeListViewHolder> {
    public int itemPosition;
    public List<SkuListBean> data;
    public View.OnClickListener onClickListener;

    public CartWholeListAdapter(List<SkuListBean> data) {
        this.data = data;
    }

    @Override
    public CartWholeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_cart_item_whole_list, parent, false);
        return new CartWholeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartWholeListViewHolder holder, int position) {
        SkuListBean object = data.get(position);
        CartWholeListViewHolder cartWholeListViewHolder = holder;

        Context mContext = cartWholeListViewHolder.itemView.getContext();
        cartWholeListViewHolder.priceTextView.setText(
                object.goods_price_format);

        cartWholeListViewHolder.treeCheckBox.setVisibility(View.VISIBLE);
        cartWholeListViewHolder.treeCheckBox.setSelected(object.select);

        cartWholeListViewHolder.goodsNumberEditText.setText(object.goods_number);

        int cart_id = -1;
        try {
            cart_id = Integer.valueOf(object.cart_id);
        } catch (Exception e) {
        }

        Utils.setViewTypeForTag(cartWholeListViewHolder.treeCheckBox, ViewType.VIEW_TYPE_SELECT_WHOLE_LIST);
        Utils.setPositionForTag(cartWholeListViewHolder.treeCheckBox, itemPosition);
        Utils.setExtraInfoForTag(cartWholeListViewHolder.treeCheckBox, cart_id);
        cartWholeListViewHolder.treeCheckBox.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(cartWholeListViewHolder.goodsNumberEditText, ViewType.VIEW_TYPE_EDIT_WHOLE_LIST);
        Utils.setPositionForTag(cartWholeListViewHolder.goodsNumberEditText, itemPosition);
        Utils.setExtraInfoForTag(cartWholeListViewHolder.goodsNumberEditText, position);
        cartWholeListViewHolder.goodsNumberEditText.setOnClickListener(onClickListener);

        StringBuilder stringBuffer = new StringBuilder();

        if (object.spec_names != null) {
            for (int i = 0; i < object.spec_names.size(); i++) {
                stringBuffer.append(object.spec_names.get(i));
                stringBuffer.append(" ");
            }
        }

        String attr = stringBuffer.toString();
        if(TextUtils.isEmpty(attr)) {
            cartWholeListViewHolder.attrTextView.setVisibility(View.GONE);
        } else {
            cartWholeListViewHolder.attrTextView.setVisibility(View.VISIBLE);
            cartWholeListViewHolder.attrTextView.setText(stringBuffer.toString());
        }



        cartWholeListViewHolder.minusButton.setVisibility(View.VISIBLE);
        cartWholeListViewHolder.addButton.setVisibility(View.VISIBLE);
        cartWholeListViewHolder.goodsNumberEditText.setVisibility(View.VISIBLE);

        if (object.goods_number.equals("1")) {
            cartWholeListViewHolder.minusButton.setEnabled(false);
            cartWholeListViewHolder.minusButton.setImageResource(R.mipmap.btn_minus_disabled);
        } else {
            cartWholeListViewHolder.minusButton.setImageResource(R.mipmap.btn_minus_normal);
            cartWholeListViewHolder.minusButton.setEnabled(true);

            Utils.setViewTypeForTag(cartWholeListViewHolder.minusButton, ViewType.VIEW_TYPE_MINUS);
            Utils.setPositionForTag(cartWholeListViewHolder.minusButton, itemPosition);
            Utils.setExtraInfoForTag(cartWholeListViewHolder.minusButton, cart_id);
            cartWholeListViewHolder.minusButton.setOnClickListener(onClickListener);

        }

        Utils.setViewTypeForTag(cartWholeListViewHolder.addButton, ViewType.VIEW_TYPE_PLUS);
        Utils.setPositionForTag(cartWholeListViewHolder.addButton, itemPosition);
        Utils.setExtraInfoForTag(cartWholeListViewHolder.addButton, cart_id);
        cartWholeListViewHolder.addButton.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
