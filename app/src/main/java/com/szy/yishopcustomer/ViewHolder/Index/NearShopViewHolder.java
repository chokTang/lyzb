package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.szy.yishopcustomer.Adapter.HomeNearShopAdapter;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TYK.
 */
public class NearShopViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rv_home_near_shop)
    public RecyclerView recyclerViewNearShop;

    @BindView(R.id.tv_right_title)
    public TextView tv_right_title;


    public HomeNearShopAdapter shopAdapter;

    public NearShopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        shopAdapter = new HomeNearShopAdapter(R.layout.item_item_home_near_shop);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerViewNearShop.setLayoutManager(manager);
        recyclerViewNearShop.setAdapter(shopAdapter);
    }
}
