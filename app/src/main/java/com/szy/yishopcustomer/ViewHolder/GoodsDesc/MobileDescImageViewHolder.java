package com.szy.yishopcustomer.ViewHolder.GoodsDesc;

import android.view.*;
import android.widget.ImageView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2016/9/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MobileDescImageViewHolder extends CommonViewHolder {
    @BindView(R.id.goods_desc_imageView)
    public ImageView mDescImage;

    public MobileDescImageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
