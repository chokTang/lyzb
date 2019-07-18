package com.szy.yishopcustomer.Adapter;

import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.TopGoodsModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsPromotionWrapperViewHolder;

import java.util.List;

/**
 * Created by 宗仁 on 16/6/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsTopAdapter extends PagerAdapter {
    public View.OnClickListener onClickListener;
    private List<TopGoodsModel> mData;

    public GoodsTopAdapter(List<TopGoodsModel> data) {
        super();
        mData = data;
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((float) mData.size() / 3.0);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View wrapper = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_goods_top_wrapper, container, false);
        GoodsPromotionWrapperViewHolder viewHolder = new GoodsPromotionWrapperViewHolder(wrapper);

        if (mData.size() > position * 3) {

            viewHolder.firstGoodsView.setVisibility(View.VISIBLE);
            viewHolder.firstGoodsView.setOnClickListener(onClickListener);
            Utils.setExtraInfoForTag(viewHolder.firstGoodsView, position * 3);
            Utils.setViewTypeForTag(viewHolder.firstGoodsView, ViewType.VIEW_TYPE_GOODS);

            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(viewHolder.firstGoodsView);
            TopGoodsModel goodsModel = mData.get(position * 3);

            goodsViewHolder.goodsPriceTextView.setText(goodsModel.goods_dk_price_format);

            goodsViewHolder.goodsNameTextView.setText(goodsModel.goods_name);
            ImageLoader.displayImage(Utils.urlOfImage(goodsModel.goods_image), goodsViewHolder.goodsImageView);

            if (!TextUtils.isEmpty(goodsModel.max_integral_num) && Integer.parseInt(goodsModel.max_integral_num) > 0) {
                goodsViewHolder.indexGoodsIngot.setVisibility(View.VISIBLE);
                goodsViewHolder.indexGoodsIngot.setText("+" + Utils.formatMoney(goodsViewHolder.indexGoodsIngot.getContext(), goodsModel.max_integral_num));
            } else {
                goodsViewHolder.indexGoodsIngot.setVisibility(View.GONE);
            }

            goodsViewHolder.indexGoodsPrices.setText(goodsModel.goods_bxprice_format);

        } else {
            viewHolder.firstGoodsView.setVisibility(View.INVISIBLE);
        }

        if (mData.size() > position * 3 + 1) {
            viewHolder.secondGoodsView.setVisibility(View.VISIBLE);
            viewHolder.secondGoodsView.setOnClickListener(onClickListener);
            Utils.setExtraInfoForTag(viewHolder.secondGoodsView, position * 3 + 1);
            Utils.setViewTypeForTag(viewHolder.secondGoodsView, ViewType.VIEW_TYPE_GOODS);
            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(viewHolder.secondGoodsView);
            TopGoodsModel goodsModel = mData.get(position * 3 + 1);

            goodsViewHolder.goodsPriceTextView.setText(goodsModel.goods_dk_price_format);

            goodsViewHolder.goodsNameTextView.setText(goodsModel.goods_name);
            ImageLoader.displayImage(Utils.urlOfImage(goodsModel.goods_image), goodsViewHolder.goodsImageView);


            if (!TextUtils.isEmpty(goodsModel.max_integral_num) && Integer.parseInt(goodsModel.max_integral_num) > 0) {
                goodsViewHolder.indexGoodsIngot.setVisibility(View.VISIBLE);
                goodsViewHolder.indexGoodsIngot.setText("+" + Utils.formatMoney(goodsViewHolder.indexGoodsIngot.getContext(), goodsModel.max_integral_num));
            } else {
                goodsViewHolder.indexGoodsIngot.setVisibility(View.GONE);
            }

            goodsViewHolder.indexGoodsPrices.setText(goodsModel.goods_bxprice_format);

        } else {
            viewHolder.secondGoodsView.setVisibility(View.INVISIBLE);
        }
        if (mData.size() > position * 3 + 2) {
            viewHolder.thirdGoodsView.setVisibility(View.VISIBLE);
            viewHolder.thirdGoodsView.setOnClickListener(onClickListener);
            Utils.setExtraInfoForTag(viewHolder.thirdGoodsView, position * 3 + 2);
            Utils.setViewTypeForTag(viewHolder.thirdGoodsView, ViewType.VIEW_TYPE_GOODS);
            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(viewHolder.thirdGoodsView);
            TopGoodsModel goodsModel = mData.get(position * 3 + 2);

            goodsViewHolder.goodsPriceTextView.setText(goodsModel.goods_dk_price_format);

            goodsViewHolder.goodsNameTextView.setText(goodsModel.goods_name);
            ImageLoader.displayImage(Utils.urlOfImage(goodsModel.goods_image), goodsViewHolder
                    .goodsImageView);
            if (!TextUtils.isEmpty(goodsModel.max_integral_num) && Integer.parseInt(goodsModel.max_integral_num) > 0) {
                goodsViewHolder.indexGoodsIngot.setVisibility(View.VISIBLE);
                goodsViewHolder.indexGoodsIngot.setText("+" + Utils.formatMoney(goodsViewHolder.indexGoodsIngot.getContext(), goodsModel.max_integral_num));
            } else {
                goodsViewHolder.indexGoodsIngot.setVisibility(View.GONE);
            }

            goodsViewHolder.indexGoodsPrices.setText(goodsModel.goods_bxprice_format);

        } else {
            viewHolder.thirdGoodsView.setVisibility(View.INVISIBLE);
        }
        container.addView(wrapper);
        return wrapper;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}