package com.szy.yishopcustomer.Adapter;

import android.view.*;

import com.szy.common.Adapter.CommonAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.ShopStreetCateTwoItemModel;
import com.szy.yishopcustomer.ViewHolder.ShopStreetCateViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buqingqiang on 2016/6/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopStreetCateTwoAdapter extends CommonAdapter<ShopStreetCateTwoItemModel,
        ShopStreetCateViewHolder> {
    public ShopStreetCateTwoAdapter() {
        this(new ArrayList<ShopStreetCateTwoItemModel>());
    }

    public ShopStreetCateTwoAdapter(List<ShopStreetCateTwoItemModel> data) {
        super(R.layout.item_shop_street_cate, data);
    }

    @Override
    public void onBindViewHolder(int position, int viewType, ShopStreetCateViewHolder viewHolder,
                                 ShopStreetCateTwoItemModel item) {
        viewHolder.shopStreetCateTextView.setText(item.cat_name);
    }

    @Override
    public ShopStreetCateViewHolder onCreateViewHolder(int position, View itemView, int viewType) {
        return new ShopStreetCateViewHolder(itemView);
    }

}
