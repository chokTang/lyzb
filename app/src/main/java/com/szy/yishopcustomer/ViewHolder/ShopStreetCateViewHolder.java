package com.szy.yishopcustomer.ViewHolder;

import android.view.*;
import android.widget.TextView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by buqingqiang on 2016/6/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopStreetCateViewHolder extends CommonViewHolder {

    @BindView(R.id.item_shop_street_cate_textView)
    public TextView shopStreetCateTextView;

    public ShopStreetCateViewHolder(View view) {
        super(view);
    }
}
