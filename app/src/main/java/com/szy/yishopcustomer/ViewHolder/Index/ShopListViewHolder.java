package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_shop_list_recyclerView)
    public RecyclerView recyclerView;

    public ShopListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
