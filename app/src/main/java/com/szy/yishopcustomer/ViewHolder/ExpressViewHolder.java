package com.szy.yishopcustomer.ViewHolder;

import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by liwei on 2016/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ExpressViewHolder extends CommonViewHolder {
    @BindView(R.id.item_express_msg_text_view)
    public TextView mExpressMsg;
    @BindView(R.id.item_express_time_text_view)
    public TextView mExpressTime;
    @BindView(R.id.imageViewOne)
    public ImageView mImageViewOne;
    @BindView(R.id.imageViewTwo)
    public ImageView mImageViewTwo;
    @BindView(R.id.viewOne)
    public View mViewOne;
    @BindView(R.id.viewTwo)
    public View mViewTwo;

    public ExpressViewHolder(View view) {
        super(view);
    }
}
