package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.like.utilslib.image.config.GlideApp;
import com.szy.common.View.SquareImageView;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 猜你喜欢 adapter
 */
public class IndexGuessLikeAdapter extends RecyclerView.Adapter<IndexGuessLikeAdapter.GuessItemViewHolder> {

    public View.OnClickListener onClickListener;
    public int itemPosition;
    public ViewType itemType;

    private Context mContext;
    public List<GuessGoodsModel> data;

    public IndexGuessLikeAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();
        itemType = ViewType.VIEW_INDEX_GUESS_LIKE;
    }

    @Override
    public GuessItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_index_guss_goods, parent, false);

        return new GuessItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GuessItemViewHolder viewHolder, int position) {

        final GuessGoodsModel item = data.get(position);

        final String goodPrice;

        if (!Utils.isNull(item.goods_image)) {
            GlideApp.with(mContext)
                    .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, item.goods_image))
                    .error(R.mipmap.pl_image)
                    .thumbnail(0.2f)
                    .centerCrop()
                    .into(viewHolder.thumbImageView);
        }

        viewHolder.mTextView_TagOne.setVisibility(View.VISIBLE);
        viewHolder.mTextView_TagTwo.setVisibility(View.VISIBLE);
        viewHolder.mTextView_TagThr.setVisibility(View.VISIBLE);
        //标签判断
        if (!Utils.isNull(item.tags)) {

            JSONArray jsonArray = JSONObject.parseArray(item.tags);

            if (jsonArray.size() > 0) {
                switch (jsonArray.size()) {
                    case 1:
                        viewHolder.mTextView_TagTwo.setVisibility(View.GONE);
                        viewHolder.mTextView_TagThr.setVisibility(View.GONE);
                        viewHolder.mTextView_TagOne.setText(jsonArray.get(0).toString());
                        break;
                    case 2:
                        viewHolder.mTextView_TagThr.setVisibility(View.GONE);
                        viewHolder.mTextView_TagOne.setText(jsonArray.get(0).toString());

                        if (!TextUtils.isEmpty(jsonArray.get(1).toString())) {
                            viewHolder.mTextView_TagTwo.setText(jsonArray.get(1).toString());
                        } else {
                            viewHolder.mTextView_TagTwo.setVisibility(View.GONE);
                        }
                        break;
                    case 3:
                        viewHolder.mTextView_TagOne.setText(jsonArray.get(0).toString());

                        if (!TextUtils.isEmpty(jsonArray.get(1).toString()) && !TextUtils.isEmpty(jsonArray.get(2).toString())) {
                            viewHolder.mTextView_TagTwo.setText(jsonArray.get(1).toString());
                            viewHolder.mTextView_TagThr.setText(jsonArray.get(2).toString());
                        } else {
                            viewHolder.mTextView_TagTwo.setVisibility(View.GONE);
                            viewHolder.mTextView_TagThr.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        viewHolder.mTextView_TagOne.setVisibility(View.GONE);
                        viewHolder.mTextView_TagTwo.setVisibility(View.GONE);
                        viewHolder.mTextView_TagThr.setVisibility(View.GONE);
                        break;
                }
            } else {
                viewHolder.mTextView_TagOne.setVisibility(View.GONE);
                viewHolder.mTextView_TagTwo.setVisibility(View.GONE);
                viewHolder.mTextView_TagThr.setVisibility(View.GONE);
            }
        }


        if (TextUtils.isEmpty(item.goods_price_format)) {
            goodPrice = item.goods_price;
            viewHolder.priceTextView.setText(Utils.formatMoney(viewHolder.priceTextView.getContext(), item.goods_price));
        } else {
            goodPrice = item.goods_price_format;
            viewHolder.priceTextView.setText(Utils.formatMoney(viewHolder.priceTextView.getContext(), item.goods_price_format));
        }

        if (!TextUtils.isEmpty(item.shop_name)) {
            viewHolder.tv_index_guss_shop_name.setText(item.shop_name);
        }

        viewHolder.tv_guss_go_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_SHOP_ID.getValue(), item.getShop_id());
                intent.setClass(viewHolder.itemView.getContext(), ShopActivity.class);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

        viewHolder.nameTextView.setText(item.goods_name);

        if (item.max_integral_num > 0) {
            viewHolder.ingotPrice.setText("+" + item.max_integral_num + "元宝");
        } else {
            viewHolder.ingotPrice.setVisibility(View.GONE);
        }

        viewHolder.goodsPrice.setText(item.goods_bxprice_format);

        if (!TextUtils.isEmpty(item.max_integral_num_format)) {
            viewHolder.deductibleTextView.setVisibility(View.VISIBLE);
            viewHolder.deductibleTextView.setText(item.max_integral_num_format);
        } else {
            viewHolder.deductibleTextView.setVisibility(View.GONE);
        }


        Utils.setPositionForTag(viewHolder.itemView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.itemView, itemType);
        Utils.setExtraInfoForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);

        viewHolder.mText_Similar.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {

        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public class GuessItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_guess_like_img)
        SquareImageView thumbImageView;
        @BindView(R.id.tv_guess_like_name)
        TextView nameTextView;
        @BindView(R.id.tv_guess_like_price)
        TextView priceTextView;

        @BindView(R.id.tv_index_guss_shop_name)
        TextView tv_index_guss_shop_name;

        @BindView(R.id.tv_guss_go_shop)
        TextView tv_guss_go_shop;

        @BindView(R.id.tv_guess_like_tag_one)
        TextView mTextView_TagOne;
        @BindView(R.id.tv_guess_like_tag_two)
        TextView mTextView_TagTwo;
        @BindView(R.id.tv_guess_like_tag_thr)
        TextView mTextView_TagThr;

        @BindView(R.id.tv_guess_like_deductible)
        TextView deductibleTextView;

        @BindView(R.id.tv_gl_ingot_price)
        TextView ingotPrice;     //元宝数目
        @BindView(R.id.tv_guess_like_goods_price)
        TextView goodsPrice;    //宝箱价

        @BindView(R.id.tv_guess_like_similar)
        TextView mText_Similar; //相似

        public GuessItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onViewRecycled(GuessItemViewHolder holder) {
        super.onViewRecycled(holder);
        ImageView imageView = holder.thumbImageView;
        if (imageView != null) {
            Glide.with(mContext).clear(imageView);
        }
    }
}
