package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.IngotDetailActivity;
import com.szy.yishopcustomer.Activity.IngotSendActivity;
import com.szy.yishopcustomer.Activity.ShareIngotActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotGoodModel;
import com.szy.yishopcustomer.Util.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我的元宝 适配器
 * @time 2018 10:18
 */

public class IngotListAdapter extends RecyclerView.Adapter {

    //元宝数目
    private final int TYPE_INGOT_NUMBER = 1;

    //元宝赠送
    private final int TYPE_INGOT_DONATE = 2;

    //元宝分享
    private final int TYPE_INGOT_SHARE = 3;

    //低价换购+超值推荐
    private final int TYPE_GOOD_DATA = 4;

    public Context mContext;
    public List<IngotGoodModel> mGoodModels;
    public String mUserIngot;

    public IngotListAdapter(Context context) {
        this.mContext = context;
        mGoodModels = new ArrayList<>();
    }

    public RecyclerView.ViewHolder createNumberView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_number, parent, false);
        NumberViewHolder holder = new NumberViewHolder(view);

        return holder;
    }


    public RecyclerView.ViewHolder createDonateView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_donate, parent, false);
        DonateViewHolder holder = new DonateViewHolder(view);

        return holder;
    }

    public RecyclerView.ViewHolder createShareView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_share, parent, false);
        ShareViewHolder holder = new ShareViewHolder(view);

        return holder;
    }


    public RecyclerView.ViewHolder createGoodDataView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_good_data, parent, false);
        GoodDataViewHolder holder = new GoodDataViewHolder(view);

        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_INGOT_NUMBER:
                return createNumberView(parent);

            case TYPE_INGOT_DONATE:
                return createDonateView(parent);

            case TYPE_INGOT_SHARE:
                return createShareView(parent);

            case TYPE_GOOD_DATA:
                return createGoodDataView(parent);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_INGOT_NUMBER:
                bindItemNumber((NumberViewHolder) holder);
                break;

            case TYPE_INGOT_DONATE:
                bindItemDonate((DonateViewHolder) holder);
                break;

            case TYPE_INGOT_SHARE:
                bindItemShare((ShareViewHolder) holder);
                break;

            case TYPE_GOOD_DATA:
                bindItemGood((GoodDataViewHolder) holder);
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return TYPE_INGOT_NUMBER;

            case 1:
                return TYPE_INGOT_DONATE;

            case 2:
                return TYPE_INGOT_SHARE;

            case 3:
                return TYPE_GOOD_DATA;

        }


        return position;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingot_number)
        TextView mTextView_Number;

        @BindView(R.id.fme_ingot_detail)
        FrameLayout mLayout_Detail;
        @BindView(R.id.fme_ingot_explain)
        FrameLayout mLayout_Explain;
        @BindView(R.id.fme_ingot_shop)
        FrameLayout mLayout_Shop;


        public NumberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DonateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rela_ingot_reg)
        RelativeLayout mLayout_Reg;
        @BindView(R.id.rela_ingot_shop)
        RelativeLayout mLayout_Shop;

        @BindView(R.id.tv_ingot_reg_title)
        TextView mTextView_Title;


        public DonateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ShareViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_ingot_donate)
        ImageView mImageView_Donate;
        @BindView(R.id.img_ingot_share)
        ImageView mImageView_Share;
        @BindView(R.id.img_ingot_ac)
        ImageView mImageView_Ac;

        public ShareViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class GoodDataViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rela_low_price)
        RelativeLayout mLayout_LowPrice;
        @BindView(R.id.tv_low_price)
        TextView mTextView_LowPrice;
        @BindView(R.id.view_low_price)
        View mView_LowPrice;

        @BindView(R.id.rela_recommended)
        RelativeLayout mLayout_Recom;
        @BindView(R.id.tv_recommended)
        TextView mTextView_Recom;
        @BindView(R.id.view_recommended)
        View mView_Recom;

        @BindView(R.id.recy_ingot_good_data)
        CommonRecyclerView mRecyclerView;

        public GoodDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindItemNumber(NumberViewHolder holder) {

        holder.mTextView_Number.setText(mUserIngot);

        /***元宝明细**/
        holder.mLayout_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, IngotDetailActivity.class));
            }
        });

        /***规则说明**/
        holder.mLayout_Explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/acerrule");
                mContext.startActivity(intent);
            }
        });

        /***去换购**/
        holder.mLayout_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra(Key.KEY_KEYWORD.getValue(), "");
                mContext.startActivity(intent);
            }
        });
    }

    public void bindItemDonate(DonateViewHolder holder) {

        String text = "即可获得" + "<font color='#ff0000'>" + 18 + "</font>" + "元宝";
        holder.mTextView_Title.setText(Html.fromHtml(text));

        /**邀请好友注册***/
        holder.mLayout_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShareIngotActivity.class));
            }
        });

        /**消费得元宝***/
        holder.mLayout_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra(Key.KEY_KEYWORD.getValue(), "");
                mContext.startActivity(intent);
            }
        });
    }

    public void bindItemShare(ShareViewHolder holder) {

        /**赠送好友元宝***/
        holder.mImageView_Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, IngotSendActivity.class);
                intent.putExtra(IngotSendActivity.SEND_INGOT_TYOE, 1);
                mContext.startActivity(intent);
            }
        });

        /**分享元宝***/
        holder.mImageView_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, IngotSendActivity.class);
                intent.putExtra(IngotSendActivity.SEND_INGOT_TYOE, 2);
                mContext.startActivity(intent);
            }
        });

        /*** 元宝活动 **/
        holder.mImageView_Ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/acerTurntable");
                mContext.startActivity(intent);
            }
        });
    }

    public void bindItemGood(final GoodDataViewHolder holder) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        holder.mRecyclerView.setLayoutManager(gridLayoutManager);
        holder.mRecyclerView.addItemDecoration(ListItemDecoration.createVertical(mContext, Color.GRAY, 1));
        holder.mRecyclerView.setFocusable(false);
        holder.mRecyclerView.requestFocus();

        final IngotGoodDataAdapter dataAdapter = new IngotGoodDataAdapter(mContext);
        holder.mRecyclerView.setAdapter(dataAdapter);

        if (mGoodModels.size() > 0) {
            dataAdapter.mGoodModels.addAll(mGoodModels);
        } else {
            holder.mRecyclerView.showEmptyView();
            holder.mRecyclerView.setEmptyImage(R.mipmap.bg_public);
            holder.mRecyclerView.setEmptyTitle(R.string.near_data_empty);
        }
        dataAdapter.notifyDataSetChanged();

        holder.mLayout_LowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.mTextView_LowPrice.setTextColor(mContext.getResources().getColor(R.color.ing_number));
                holder.mView_LowPrice.setVisibility(View.VISIBLE);

                holder.mTextView_Recom.setTextColor(mContext.getResources().getColor(R.color.colorOne));
                holder.mView_Recom.setVisibility(View.GONE);

                if (mItemGoodClick != null) {
                    mItemGoodClick.onLowPriceClick();
                }
            }
        });

        holder.mLayout_Recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.mTextView_Recom.setTextColor(mContext.getResources().getColor(R.color.ing_number));
                holder.mView_Recom.setVisibility(View.VISIBLE);

                holder.mTextView_LowPrice.setTextColor(mContext.getResources().getColor(R.color.colorOne));
                holder.mView_LowPrice.setVisibility(View.GONE);

                if (mItemGoodClick != null) {
                    mItemGoodClick.onRecommClick();
                }
            }
        });
    }

    public interface ItemGoodClick {

        void onLowPriceClick();

        void onRecommClick();
    }

    private ItemGoodClick mItemGoodClick;

    public void setItemGoodClick(ItemGoodClick goodClick) {
        this.mItemGoodClick = goodClick;
    }
}
