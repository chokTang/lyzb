package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShopBonusModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ShopRedpacketViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/10/16.
 */

public class ShopRedPacketAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_REDPACKET = 0;
    public static final int VIEW_TYPE_REDPACKETTWO = 1;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public ShopRedPacketAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_REDPACKET:
                return createShopPacketViewHolder(parent);
            case VIEW_TYPE_REDPACKETTWO:
                return createShopPacketTwoViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_REDPACKET:
                bindShopPacketViewHolder((ShopRedpacketViewHolder) holder, position);
                break;
            case VIEW_TYPE_REDPACKETTWO:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);

        if (object instanceof ShopBonusModel) {
            return VIEW_TYPE_REDPACKET;
        } else if (object instanceof EmptyItemModel) {
            return VIEW_TYPE_REDPACKETTWO;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindShopPacketViewHolder(ShopRedpacketViewHolder holder, int position) {
//        holder.confirmButton.setOnClickListener(onCLickListener);
        ShopBonusModel shopBonusModel = (ShopBonusModel) data.get(position);
        holder.textViewBonusPrice.setText(shopBonusModel.bonus_amount);
        holder.textViewBonusTip.setText("满"+shopBonusModel.min_goods_amount+"元使用");
        holder.textViewBonusTime.setText("有效期" + Utils.times(shopBonusModel.start_time,"yyyy-MM-dd") + "-" + Utils.times(shopBonusModel.end_time,"yyyy-MM-dd"));

        holder.imageViewReceive.setVisibility(View.GONE);
        if(1 == shopBonusModel.user_bonus_status) {
            holder.imageViewReceive.setVisibility(View.VISIBLE);
            holder.textViewUsed.setText("使用");

            Utils.setViewTypeForTag(holder.textViewUsed, ViewType.VIEW_TYPE_SHOP_BONUS_USED);
            Utils.setPositionForTag(holder.textViewUsed, position);
            Utils.setExtraInfoForTag(holder.textViewUsed,Integer.valueOf(shopBonusModel.bonus_id));
            holder.textViewUsed.setOnClickListener(onClickListener);
        } else if(2 == shopBonusModel.user_bonus_status) {
            holder.textViewUsed.setText("已领完");
        } else {
            holder.textViewUsed.setText("立即领取");

            Utils.setViewTypeForTag(holder.textViewUsed, ViewType.VIEW_TYPE_SHOP_BONUS_RECIVE);
            Utils.setPositionForTag(holder.textViewUsed, position);
            Utils.setExtraInfoForTag(holder.textViewUsed,Integer.valueOf(shopBonusModel.bonus_id));
            holder.textViewUsed.setOnClickListener(onClickListener);
        }
    }


    private RecyclerView.ViewHolder createShopPacketViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_redpacket_item, parent, false);
        return new ShopRedpacketViewHolder(view);
    }

    private RecyclerView.ViewHolder createShopPacketTwoViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_redpacket_nomore_item, parent, false);
        return new NomMoreViewHolder(view);
    }

    public class NomMoreViewHolder extends RecyclerView.ViewHolder {

        public NomMoreViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
