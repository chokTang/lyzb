package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.LikeGood.UserIngotModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 注册成功页面 猜你喜欢 adapter
 * @time
 */

public class LikeDataAdapter extends RecyclerView.Adapter {

    static final int ITEM_HINT = 1;
    static final int ITEM_LIKEDATA = 2;

    public Context mContext;
    public UserIngotModel mIngotModel;
    public List<GuessGoodsModel> mModelList;

    public LikeDataAdapter(Context context) {
        this.mContext = context;
    }

    public void setHintModel(UserIngotModel model) {
        this.mIngotModel = model;
    }

    public void setLikeData(List<GuessGoodsModel> modelList) {
        this.mModelList = modelList;
    }

    public RecyclerView.ViewHolder createHintHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_regs_suces_hint, parent, false);
        HintViewHolder holder = new HintViewHolder(view);
        return holder;
    }

    public RecyclerView.ViewHolder createLikeHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_regs_suces_likedata, parent, false);
        LikeViewHolder holder = new LikeViewHolder(view);
        return holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ITEM_HINT:
                return createHintHolder(parent);
            case ITEM_LIKEDATA:
                return createLikeHolder(parent);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_HINT:
                bindHintHolder((HintViewHolder) holder);
                break;
            case ITEM_LIKEDATA:
                bindLikeHolder((LikeViewHolder) holder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return ITEM_HINT;
            case 1:
                return ITEM_LIKEDATA;
        }

        return position;
    }

    public class HintViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_regs_hint)
        ImageView mImageView_Hint;
        @BindView(R.id.tv_regs_hint)
        TextView mTextView_Hint;
        @BindView(R.id.tv_regs_ingot_number)
        TextView mTextView_IngotNumber;
        @BindView(R.id.tv_regs_ingot_look)
        TextView mTextView_IngotLook;
        @BindView(R.id.btn_go_home)
        Button mButton_GoHome;


        public HintViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LikeViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_likedata_title)
        TextView mTextView_LikeTitle;
        @BindView(R.id.recy_likedata_list)
        CommonRecyclerView mRecyclerView_List;

        public LikeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindHintHolder(HintViewHolder holder) {

        if (mIngotModel != null) {

            if (mIngotModel.isLogin) {

                holder.mTextView_Hint.setText("你已注册!");
                holder.mTextView_Hint.setTextColor(mContext.getResources().getColor(R.color.aliwx_black));
                holder.mImageView_Hint.setBackgroundResource(R.mipmap.login_true);
                holder.mTextView_IngotNumber.setText("当前可用元宝:" + mIngotModel.usableIngot);

                holder.mTextView_IngotLook.setVisibility(View.VISIBLE);

                holder.mTextView_IngotLook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mOnItemClick != null) {
                            mOnItemClick.lookIngotClick();
                        }
                    }
                });

            } else {

                holder.mTextView_Hint.setText("恭喜!注册成功");
                holder.mTextView_Hint.setTextColor(mContext.getResources().getColor(R.color.register_success_green));
                holder.mTextView_IngotNumber.setText("恭喜你,获得" + mIngotModel.giveIngot + "元宝,当前可用元宝" + mIngotModel.usableIngot);
                holder.mImageView_Hint.setBackgroundResource(R.mipmap.register_success);

                holder.mTextView_IngotLook.setVisibility(View.GONE);
            }

            holder.mButton_GoHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnItemClick != null) {
                        mOnItemClick.goHomeClick();
                    }
                }
            });
        }

    }

    public void bindLikeHolder(LikeViewHolder holder) {

        if (mModelList != null) {

            holder.mTextView_LikeTitle.setVisibility(View.VISIBLE);

            holder.mRecyclerView_List.setLayoutManager(new GridLayoutManager(mContext, 2));
            holder.mRecyclerView_List.setFocusable(false);
            holder.mRecyclerView_List.requestFocus();

            GuessLikeAdapter mLikeAdapter = new GuessLikeAdapter(mContext);
            holder.mRecyclerView_List.setAdapter(mLikeAdapter);

            mLikeAdapter.data.addAll(mModelList);
            mLikeAdapter.notifyDataSetChanged();
        }
    }

    private onItemClick mOnItemClick;

    public interface onItemClick {
        void lookIngotClick();

        void goHomeClick();
    }

    public void setOnItemClick(onItemClick click) {
        this.mOnItemClick = click;
    }
}
