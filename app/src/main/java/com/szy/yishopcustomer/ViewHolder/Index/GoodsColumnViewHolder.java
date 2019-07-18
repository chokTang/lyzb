package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;

import com.szy.yishopcustomer.Adapter.IndexGoodsListAdapter;
import com.szy.yishopcustomer.Adapter.IndexGuessLikeAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.Util.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsColumnViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_goods_column_recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.img_no_data)
    public ImageView mImageView_Nodata;

    //初始化adapter，用于保存
    public List<GoodsItemModel> items;
    public IndexGoodsListAdapter adapter;

    public GoodsColumnViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        items = new ArrayList<>();
        adapter = new IndexGoodsListAdapter(items);

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(view.getContext(), R.drawable.recy_grid_line));
        recyclerView.setNestedScrollingEnabled(false);
    }

}
