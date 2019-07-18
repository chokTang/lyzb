package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.like.utilslib.image.config.GlideApp;
import com.szy.common.View.SquareImageView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 猜你喜欢 adapter
 * @time 2018 14:12
 */

public class GuessLikeAdapter extends RecyclerView.Adapter {

    public List<Object> data;
    public Context mContext;

    public GuessLikeAdapter(Context context) {
        data = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_index_guss_goods, parent, false);
        return new GuessLikeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItemData((GuessLikeHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class GuessLikeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_guess_like_img)
        SquareImageView thumbImageView;
        @BindView(R.id.tv_guess_like_name)
        TextView nameTextView;
        @BindView(R.id.tv_guess_like_price)
        TextView priceTextView;

        @BindView(R.id.tv_guess_like_tag_one)
        TextView mTextView_TagOne;
        @BindView(R.id.tv_guess_like_tag_two)
        TextView mTextView_TagTwo;
        @BindView(R.id.tv_guess_like_tag_thr)
        TextView mTextView_TagThr;

        @BindView(R.id.tv_guess_like_deductible)
        TextView deductibleTextView;

        @BindView(R.id.tv_index_guss_shop_name)
        TextView tv_index_guss_shop_name;

        @BindView(R.id.tv_guss_go_shop)
        TextView tv_guss_go_shop;

        @BindView(R.id.tv_gl_ingot_price)
        TextView ingotPrice;     //元宝数目
        @BindView(R.id.tv_guess_like_goods_price)
        TextView goodsPrice;    //宝箱价

        @BindView(R.id.tv_guess_like_similar)
        TextView mText_Similar; //相似

        public GuessLikeHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void bindItemData(final GuessLikeHolder holder, int position) {

        final GuessGoodsModel item = (GuessGoodsModel) data.get(position);

        if (!Utils.isNull(item.goods_image)) {
            GlideApp.with(mContext)
                    .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, item.goods_image))
                    .error(R.mipmap.pl_image)
                    .thumbnail(0.2f)
                    .centerCrop()
                    .into(holder.thumbImageView);
        }

        holder.mTextView_TagOne.setVisibility(View.VISIBLE);
        holder.mTextView_TagTwo.setVisibility(View.VISIBLE);
        holder.mTextView_TagThr.setVisibility(View.VISIBLE);

        //标签判断
        if (!Utils.isNull(item.tags)) {

            JSONArray jsonArray = JSONObject.parseArray(item.tags);

            if (jsonArray.size() > 0) {
                switch (jsonArray.size()) {
                    case 1:
                        holder.mTextView_TagTwo.setVisibility(View.GONE);
                        holder.mTextView_TagThr.setVisibility(View.GONE);
                        holder.mTextView_TagOne.setText(jsonArray.get(0).toString());
                        break;
                    case 2:
                        holder.mTextView_TagThr.setVisibility(View.GONE);
                        holder.mTextView_TagOne.setText(jsonArray.get(0).toString());

                        if (!TextUtils.isEmpty(jsonArray.get(1).toString())) {
                            holder.mTextView_TagTwo.setText(jsonArray.get(1).toString());
                        } else {
                            holder.mTextView_TagTwo.setVisibility(View.GONE);
                        }
                        break;
                    case 3:
                        holder.mTextView_TagOne.setText(jsonArray.get(0).toString());

                        if (!TextUtils.isEmpty(jsonArray.get(1).toString()) && !TextUtils.isEmpty(jsonArray.get(2).toString())) {
                            holder.mTextView_TagTwo.setText(jsonArray.get(1).toString());
                            holder.mTextView_TagThr.setText(jsonArray.get(2).toString());
                        } else {
                            holder.mTextView_TagTwo.setVisibility(View.GONE);
                            holder.mTextView_TagThr.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        holder.mTextView_TagOne.setVisibility(View.GONE);
                        holder.mTextView_TagTwo.setVisibility(View.GONE);
                        holder.mTextView_TagThr.setVisibility(View.GONE);
                        break;
                }
            } else {
                holder.mTextView_TagOne.setVisibility(View.GONE);
                holder.mTextView_TagTwo.setVisibility(View.GONE);
                holder.mTextView_TagThr.setVisibility(View.GONE);
            }
        }


        if (TextUtils.isEmpty(item.goods_price_format)) {
            holder.priceTextView.setText(Utils.formatMoney(holder.priceTextView.getContext(), item.goods_price));
        } else {
            holder.priceTextView.setText(Utils.formatMoney(holder.priceTextView.getContext(), item.goods_price_format));
        }


        holder.nameTextView.setText(item.goods_name);

        if (item.max_integral_num > 0) {
            holder.ingotPrice.setText("+" + item.max_integral_num + "元宝");
        } else {
            holder.ingotPrice.setVisibility(View.GONE);
        }

        holder.goodsPrice.setText(item.goods_bxprice_format);

        if (!TextUtils.isEmpty(item.max_integral_num_format)) {
            holder.deductibleTextView.setVisibility(View.VISIBLE);
            holder.deductibleTextView.setText(item.max_integral_num_format);
        } else {
            holder.deductibleTextView.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(item.shop_name)){
            holder.tv_index_guss_shop_name.setText(item.shop_name);
        }
        holder.tv_guss_go_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_SHOP_ID.getValue(), item.getShop_id());
                intent.setClass(holder.itemView.getContext(), ShopActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(mContext, GoodsActivity.class);
                intent.putExtra(Key.KEY_SKU_ID.getValue(), item.sku_id);
                mContext.startActivity(intent);
            }
        });

        holder.mText_Similar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra(Key.KEY_KEYWORD_ACTION.getValue(), 1);
                intent.putExtra(Key.KEY_KEYWORD.getValue(), item.goods_name);
                intent.setClass(mContext, GoodsListActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
