package com.szy.yishopcustomer.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsColumnItemViewHolder;

import java.util.List;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexGoodsListAdapter extends IndexGoodsColumnAdapter {
    public IndexGoodsListAdapter(List<GoodsItemModel> data) {
        super(data);
        itemType = ViewType.VIEW_TYPE_GOODS_LIST_ITEM;
    }

    @Override
    public GoodsColumnItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_guess_like, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = itemHeight;
        layoutParams.width = itemWidth;
        return new GoodsColumnItemViewHolder(view);
    }

}
