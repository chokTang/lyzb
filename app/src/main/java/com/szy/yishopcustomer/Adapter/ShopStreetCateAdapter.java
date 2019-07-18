package com.szy.yishopcustomer.Adapter;

import android.view.*;

import com.szy.common.Adapter.CommonAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.ShopStreetCateOneItemModel;
import com.szy.yishopcustomer.ViewHolder.ShopStreetCateViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buqingqiang on 2016/6/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopStreetCateAdapter extends CommonAdapter<ShopStreetCateOneItemModel,
        ShopStreetCateViewHolder> {
    public ShopStreetCateAdapter() {
        this(new ArrayList<ShopStreetCateOneItemModel>());
    }

    public ShopStreetCateAdapter(List<ShopStreetCateOneItemModel> data) {
        super(R.layout.item_shop_street_cate, data);
    }

    @Override
    public void onBindViewHolder(int position, int viewType, ShopStreetCateViewHolder viewHolder,
                                 ShopStreetCateOneItemModel item) {
        viewHolder.shopStreetCateTextView.setText(item.cls_name);
    }

    @Override
    public ShopStreetCateViewHolder onCreateViewHolder(int position, View itemView, int viewType) {
        return new ShopStreetCateViewHolder(itemView);

    }

}
