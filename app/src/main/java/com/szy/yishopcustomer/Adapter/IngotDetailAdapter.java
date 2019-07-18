package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.IngotSendActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.DetailModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.OverdueIngotModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 元宝明细 适配器
 * @time
 */

public class IngotDetailAdapter extends RecyclerView.Adapter {

    /**
     * 元宝收入 元宝支出
     **/
    public static final int TYPE_INCOME = 1;

    /**
     * 即将过期
     **/
    public static final int TYPE_OVERDUE = 2;


    public List<Object> data;
    public Context mContext;

    public IngotDetailAdapter(Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    //元宝收入 元宝支出
    public RecyclerView.ViewHolder createIncomeViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_income, parent, false);
        IngotIncomeViewHolder holder = new IngotIncomeViewHolder(view);
        return holder;
    }

    //即将过期
    public RecyclerView.ViewHolder createOverduelViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_overdue, parent, false);
        IngotOverdueViewHolder holder = new IngotOverdueViewHolder(view);
        return holder;
    }


    /***
     * ITEM布局切换
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_INCOME:
                return createIncomeViewHolder(parent);
            case TYPE_OVERDUE:
                return createOverduelViewHolder(parent);
            default:
                break;
        }

        return null;
    }

    /***
     * ITEM 赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_INCOME:
                bindItemIngotIncome((IngotIncomeViewHolder) holder, position);
                break;
            case TYPE_OVERDUE:
                bindItemOverdue((IngotOverdueViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object object = data.get(position);
        if (object instanceof DetailModel) {
            return TYPE_INCOME;
        } else if (object instanceof OverdueIngotModel) {
            return TYPE_OVERDUE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class IngotIncomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_ingot_income_type)
        public TextView tv_ingot_type;
        @BindView(R.id.item_ingot_income_time)
        public TextView tv_ingot_time;
        @BindView(R.id.item_ingot_income_source)
        public TextView tv_ingot_source;
        @BindView(R.id.item_ingot_income_number)
        public TextView tv_ingot_number;

        public IngotIncomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class IngotOverdueViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_ingot_overdue_number)
        public TextView tv_overdue_number;
        @BindView(R.id.item_ingot_overdue_share)
        public Button btn_ingot_share;
        @BindView(R.id.item_ingot_overdue_shop)
        public Button btn_ingot_shop;


        public IngotOverdueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /***
     * 元宝收入
     * @param holder
     * @param position
     */
    public void bindItemIngotIncome(IngotIncomeViewHolder holder, int position) {

        DetailModel model = (DetailModel) data.get(position);

        holder.tv_ingot_type.setText(model.type_name);
        holder.tv_ingot_time.setText(Utils.times(model.add_time));
        holder.tv_ingot_source.setText(model.note);
        holder.tv_ingot_number.setText(model.changed_points + "元宝");
    }

    /***
     * 即将过期
     * @param holder
     * @param position
     */
    public void bindItemOverdue(IngotOverdueViewHolder holder, int position) {

        OverdueIngotModel model = (OverdueIngotModel) data.get(position);
        holder.tv_overdue_number.setText(model.integral_num);

        /**元宝 赠送好友***/
        holder.btn_ingot_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, IngotSendActivity.class);
                intent.putExtra(IngotSendActivity.SEND_INGOT_TYOE, 1);
                mContext.startActivity(intent);
            }
        });

        /**元宝 立即使用***/
        holder.btn_ingot_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra(Key.KEY_KEYWORD.getValue(), "");
                mContext.startActivity(intent);
            }
        });
    }
}
