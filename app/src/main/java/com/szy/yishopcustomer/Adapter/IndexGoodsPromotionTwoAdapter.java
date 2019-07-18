package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsPromotionItemViewHolder;

import java.util.List;

/**
 * Created by Smart.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexGoodsPromotionTwoAdapter extends RecyclerView.Adapter<GoodsPromotionItemViewHolder> {
    public static int mPosition;
    public List<GoodsItemModel> Data;
    public View.OnClickListener onClickListener;

    public int itemPosition = 0 ;

    public IndexGoodsPromotionTwoAdapter(List<GoodsItemModel> data,int itemPosition) {
        this.Data = data;
        this.itemPosition = itemPosition;
    }

    @Override
    public GoodsPromotionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_promotion, parent, false);
        return new GoodsPromotionItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsPromotionItemViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(onClickListener);
        Utils.setExtraInfoForTag(viewHolder.itemView, position);
        Utils.setPositionForTag(viewHolder.itemView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_GOODS);

        Context mContext = viewHolder.itemView.getContext();
        int width = (int) (Utils.getWindowWidth(mContext)/3.5);
        viewHolder.itemView.getLayoutParams().width = width;
        viewHolder.itemView.setPadding(Utils.dpToPx(mContext,5),Utils.dpToPx(mContext,5),Utils.dpToPx(mContext,5),Utils.dpToPx(mContext,15));

        GoodsItemModel goodsModel = Data.get(position);

//        viewHolder.goodsPriceTextView.setText(goodsModel.goods_price_format);
        viewHolder.goodsPriceTextView.setText(Utils.formatMoney(viewHolder.goodsPriceTextView.getContext(), goodsModel.goods_dk_price));

        viewHolder.goodsNameTextView.setText(goodsModel.goods_name);
        ImageLoader.displayImage(Utils.urlOfImage(goodsModel.goods_image), viewHolder
                .goodsImageView);
        //标签
        viewHolder.goodsImageTag.setVisibility(View.GONE);
//        if (goodsModel.is_best.equals("1")) {
//            viewHolder.goodsImageTag.setVisibility(View.VISIBLE);
//            viewHolder.goodsImageTag.setImageResource(R.mipmap.ic_index_best);
//        } else if (goodsModel.is_hot.equals("1")) {
//            viewHolder.goodsImageTag.setVisibility(View.VISIBLE);
//            viewHolder.goodsImageTag.setImageResource(R.mipmap.ic_index_hot);
//        } else if (goodsModel.is_new.equals("1")) {
//            viewHolder.goodsImageTag.setVisibility(View.VISIBLE);
//            viewHolder.goodsImageTag.setImageResource(R.mipmap.ic_index_new);
//        } else {
//            viewHolder.goodsImageTag.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }
}
