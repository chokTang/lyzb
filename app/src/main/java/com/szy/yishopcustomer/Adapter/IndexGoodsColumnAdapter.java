package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsColumnItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexGoodsColumnAdapter extends RecyclerView.Adapter<GoodsColumnItemViewHolder> {
    public View.OnClickListener onClickListener;
    public int itemPosition;
    public int itemWidth;
    public int itemHeight;
    public ViewType itemType;
    public List<GoodsItemModel> data;

    public IndexGoodsColumnAdapter(List<GoodsItemModel> data) {
        this.data = data;
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        itemType = ViewType.VIEW_TYPE_GOODS;
    }

    @Override
    public GoodsColumnItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = itemHeight;
        layoutParams.width = itemWidth;
        return new GoodsColumnItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsColumnItemViewHolder viewHolder, int position) {
        GoodsItemModel item = data.get(position);
        if (!Utils.isNull(item.goods_image)) {
            int windowWidth = Utils.getWindowWidth(viewHolder.itemView.getContext());
            int imageWidth = (int) (windowWidth / 2.5);
            if (imageWidth > 350) {
                imageWidth = 350;
            }
            ImageLoader.displayImage(Utils.urlOfImage(item.goods_image), viewHolder
                    .thumbImageView, imageWidth, imageWidth);
        }

        if (item.is_best.equals("1")) {
            viewHolder.goodsTag.setVisibility(View.VISIBLE);
            viewHolder.goodsTag.setImageResource(R.mipmap.ic_index_best);
        } else if (item.is_hot.equals("1")) {
            viewHolder.goodsTag.setVisibility(View.VISIBLE);
            viewHolder.goodsTag.setImageResource(R.mipmap.ic_index_hot);
        } else if (item.is_new.equals("1")) {
            viewHolder.goodsTag.setVisibility(View.VISIBLE);
            viewHolder.goodsTag.setImageResource(R.mipmap.ic_index_new);
        } else {
            viewHolder.goodsTag.setVisibility(View.GONE);
        }
        viewHolder.goodsTag.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(item.max_integral_num)&&null!=viewHolder.ingotText){
            if (Integer.parseInt(item.max_integral_num) > 0) {
                //显示 + 元宝
                viewHolder.ingotText.setText("+" + item.max_integral_num + "元宝");
            }
        }

        viewHolder.priceTextView.setText(Utils.formatMoney(viewHolder.priceTextView.getContext(), item.goods_dk_price));

        viewHolder.nameTextView.setText(item.goods_name);

        //宝箱价
        viewHolder.deductibleTextView.setText(item.goods_bxprice_format);

        Utils.setPositionForTag(viewHolder.itemView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.itemView, itemType);
        Utils.setExtraInfoForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
