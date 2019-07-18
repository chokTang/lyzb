package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.BonusModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GoodsBonusViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 16/12/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsBonusAdapter extends RecyclerView.Adapter {
    private final int VIEW_TYPE_BONUS_ITEM = 0;
    public View.OnClickListener onClickListener;
    public List<BonusModel> data;
    public int type = 0;

    public GoodsBonusAdapter() {
        this.data = new ArrayList<>();
    }

    public GoodsBonusAdapter(ArrayList<BonusModel> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createBonusItemViewHolder(ViewGroup parent) {
        int layout = R.layout.item_goods_bonus;
        if (type == 1) {
            layout = R.layout.item_goods_bonus_free;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,
                parent, false);
        return new GoodsBonusViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_BONUS_ITEM:
                return createBonusItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_BONUS_ITEM:
                bindBonusItemViewHolder((GoodsBonusViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof BonusModel) {
            return VIEW_TYPE_BONUS_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindBonusItemViewHolder(GoodsBonusViewHolder holder, int position) {
        BonusModel item = data.get(position);
        Context mContext = holder.itemView.getContext();

        if (position == data.size() - 1) {
            holder.itemView.setPadding(holder.itemView.getPaddingLeft(), holder.itemView.getPaddingTop(), holder.itemView.getPaddingRight(), Utils.dpToPx(mContext, 10));
        }
        holder.mBonusMoney.setText(item.bonus_amount);
        holder.mBonusName.setText(item.bonus_name);
        //viewHolder.mBonusType.setText(item.send_id_name);
        holder.mBonusEndDate.setText("使用期限：" + item.start_time_format + "-" + item.end_time_format);

        holder.mTakeBonusButton.setTextColor(Color.WHITE);

        if (Integer.valueOf(item.send_number) < Integer.valueOf(item.bonus_number)) {
            if (item.is_receive.equals("1")) {
                holder.mTakeBonusButton.setText(R.string.received);
                holder.mTakeBonusButton.setEnabled(false);
            } else {
                holder.mTakeBonusButton.setText("立即领取");
                holder.mTakeBonusButton.setEnabled(true);

                Utils.setViewTypeForTag(holder.mTakeBonusButton, ViewType.VIEW_TYPE_TAKE_BONUS);
                Utils.setExtraInfoForTag(holder.mTakeBonusButton, Integer.valueOf(item.bonus_id));
                holder.mTakeBonusButton.setOnClickListener(onClickListener);
            }
        } else {
            holder.mTakeBonusButton.setText(R.string.snappedUp);
            holder.mTakeBonusButton.setTextColor(holder.mTakeBonusButton.getResources().getColor
                    (R.color.colorThree));
            holder.mTakeBonusButton.setEnabled(false);
        }
    }
}
