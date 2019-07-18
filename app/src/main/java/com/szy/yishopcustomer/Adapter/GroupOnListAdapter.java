package com.szy.yishopcustomer.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RelativeLayout;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.GroupOnModel.GrouponListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.GroupOnListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupOnListAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE_SPELL = 0;
    private static final int ITEM_TYPE_BLANK = 1;
    private static final int VIEW_DIVIDER = 2;
    public RelativeLayout.OnClickListener onClickListener;
    public List<Object> data;

    public GroupOnListAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_SPELL:
                return createGroupOnListViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            case VIEW_DIVIDER:
                return createDividerViewHolder(parent);
            default:
                return null;
        }
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_SPELL:
                GroupOnListViewHolder viewHolder = (GroupOnListViewHolder) holder;
                GrouponListModel model = (GrouponListModel) data.get(position);
                if (!Utils.isNull(model.act_img)) {
                    ImageLoader.displayImage(Utils.urlOfImage(model.act_img), viewHolder
                            .mGoodsImage);

                    ViewGroup.LayoutParams lp = viewHolder.mGoodsImage.getLayoutParams();
                    lp.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
                    lp.height = Utils.getWindowWidth(viewHolder.mGoodsImage.getContext()) * 260 /
                            640;
                    viewHolder.mGoodsImage.setLayoutParams(lp);
                }else{
                    ImageLoader.displayImage(Utils.urlOfImage(model.goods_image), viewHolder
                            .mGoodsImage);

                    ViewGroup.LayoutParams lp = viewHolder.mGoodsImage.getLayoutParams();
                    lp.width = Utils.getWindowWidth(viewHolder.mGoodsImage.getContext()) / 2;
                    lp.height = Utils.getWindowWidth(viewHolder.mGoodsImage.getContext()) / 2;
                    viewHolder.mGoodsImage.setLayoutParams(lp);
                }

                if(model.goods_number.equals("0")){
                    viewHolder.mSoldOutImage.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.mSoldOutImage.setVisibility(View.GONE);
                }

                viewHolder.mGoodsName.setText(model.goods_name);
                viewHolder.mShopName.setText(model.shop_name);
                viewHolder.mShopPrice.setText(model.act_price_format);
                viewHolder.mFightNum.setText(String.format(viewHolder.mFightNum.getContext()
                        .getString(R.string.formatFightNum), model.fight_num));
                viewHolder.mShopOlderPrice.setText(Utils.spanStrickhrough(viewHolder
                        .mShopOlderPrice.getContext(), model.goods_price_format));
                if(model.is_finish.equals("1")){
                    viewHolder.mGoGroupOn.setText("去开团 >");
                }else{
                    viewHolder.mGoGroupOn.setText(Utils.times(model.start_time,"MM-dd HH:mm")+"开团");
                }


                Utils.setViewTypeForTag(viewHolder.mGoodsName, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(viewHolder.mGoodsName, position);
                viewHolder.mGoodsName.setOnClickListener(onClickListener);
                Utils.setViewTypeForTag(viewHolder.mGoodsImage, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(viewHolder.mGoodsImage, position);
                viewHolder.mGoodsImage.setOnClickListener(onClickListener);
                Utils.setViewTypeForTag(viewHolder.mGoGroupOn, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(viewHolder.mGoGroupOn, position);
                viewHolder.mGoGroupOn.setOnClickListener(onClickListener);
                Utils.setViewTypeForTag(viewHolder.mShopName, ViewType.VIEW_TYPE_SHOP);
                Utils.setPositionForTag(viewHolder.mShopName, position);
                viewHolder.mShopName.setOnClickListener(onClickListener);
                break;
            case ITEM_TYPE_BLANK:
                DividerViewHolder blankViewHolder = (DividerViewHolder) holder;
                break;
            case VIEW_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
        }

    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof GrouponListModel) {
            return ITEM_TYPE_SPELL;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_BLANK;
        } else if (object instanceof DividerModel) {
            return VIEW_DIVIDER;
        }else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    @NonNull
    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createGroupOnListViewHolder(ViewGroup parent,
                                                                   LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_group_on_list, parent, false);
        return new GroupOnListViewHolder(view);
    }
}