package com.szy.yishopcustomer.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.SpellGroupListModel.ListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.GroupOn.UserGroupOnListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGroupOnListAdapter extends RecyclerView.Adapter {
    private final int ITEM_TYPE_GROUP_ON = 0;
    private final int ITEM_TYPE_BLANK = 1;
    public RelativeLayout.OnClickListener onClickListener;
    public List<Object> data;

    public UserGroupOnListAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_GROUP_ON:
                return createUserGroupOnListViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_GROUP_ON:
                UserGroupOnListViewHolder viewHolder = (UserGroupOnListViewHolder) holder;
                ListModel model = (ListModel) data.get(position);
                viewHolder.mGoodsAttr.setText(model.spec_info);
                viewHolder.mGoodsName.setText(model.goods_name);
                viewHolder.mMoney.setText(model.goods_price_format);

                viewHolder.mSpellNo.setText(String.format(viewHolder.mSpellNo.getContext()
                        .getString(R.string.formatGrouponSn), model.group_sn));

                if (model.status == 0) {
                    viewHolder.mStatus.setText("组团中");
                    viewHolder.mGrouponShare.setVisibility(View.VISIBLE);

                    Utils.setViewTypeForTag(viewHolder.mGrouponShare, ViewType
                            .VIEW_TYPE_USER_GROUPON_DETAIL_SHARE);
                    Utils.setPositionForTag(viewHolder.mGrouponShare, position);
                    viewHolder.mGrouponShare.setOnClickListener(onClickListener);
                } else if (model.status == 1) {
                    viewHolder.mStatus.setText("组团成功");
                    viewHolder.mGrouponShare.setVisibility(View.GONE);

                    Utils.setViewTypeForTag(viewHolder.mGrouponShare, ViewType
                            .VIEW_TYPE_USER_GROUPON_DETAIL);
                    Utils.setPositionForTag(viewHolder.mGrouponShare, position);
                    viewHolder.mGrouponShare.setOnClickListener(onClickListener);
                } else {
                    viewHolder.mStatus.setText("组团失败");
                    viewHolder.mGrouponShare.setVisibility(View.GONE);

                    Utils.setViewTypeForTag(viewHolder.mGrouponShare, ViewType
                            .VIEW_TYPE_USER_GROUPON_DETAIL);
                    Utils.setPositionForTag(viewHolder.mGrouponShare, position);
                    viewHolder.mGrouponShare.setOnClickListener(onClickListener);
                }
                if (!Utils.isNull(model.goods_image)) {
                    ImageLoader.getInstance().displayImage(Utils.urlOfImage(model.goods_image),
                            viewHolder.mGoodsImage);
                } else {
                    ImageLoader.getInstance().displayImage("drawable://" + R.drawable.pl_image,
                            viewHolder.mGoodsImage);

                }
                viewHolder.mPeopleNumber.setText("/" + model.fight_num + "人团");
                viewHolder.mHasPeopleNumber.setText(Utils.spanStringColor("已参团" + model.join_num + "人", ContextCompat.getColor(viewHolder.mHasPeopleNumber
                        .getContext(), R.color.colorPrimary),3,model.join_num.length()));

                Utils.setViewTypeForTag(viewHolder.mGrouponDetail, ViewType
                        .VIEW_TYPE_USER_GROUPON_DETAIL);
                Utils.setPositionForTag(viewHolder.mGrouponDetail, position);
                viewHolder.mGrouponDetail.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(viewHolder.mOrderDetail, ViewType.VIEW_TYPE_ORDER);
                Utils.setPositionForTag(viewHolder.mOrderDetail, position);
                viewHolder.mOrderDetail.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(viewHolder.mGoodsItem, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(viewHolder.mGoodsItem, position);
                Utils.setExtraInfoForTag(viewHolder.mGoodsItem, Integer.valueOf(model.sku_id));
                viewHolder.mGoodsItem.setOnClickListener(onClickListener);
                break;
            case ITEM_TYPE_BLANK:
                DividerViewHolder blankViewHolder = (DividerViewHolder) holder;
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ListModel) {
            return ITEM_TYPE_GROUP_ON;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_BLANK;
        } else {
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
    private RecyclerView.ViewHolder createUserGroupOnListViewHolder(ViewGroup parent,
                                                                    LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_fragment_user_groupon_list, parent, false);
        return new UserGroupOnListViewHolder(view);
    }
}
