package com.szy.yishopcustomer.ViewHolder.GoodsDesc;

import android.view.*;
import android.widget.TextView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by liwei on 2016/9/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MobileDescTextViewHolder extends CommonViewHolder {
    @BindView(R.id.goods_desc_textView)
    public TextView mDescText;

    public MobileDescTextViewHolder(View view) {
        super(view);
    }
}
