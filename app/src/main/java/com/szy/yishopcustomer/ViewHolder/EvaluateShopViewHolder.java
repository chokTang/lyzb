package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.RatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/2/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EvaluateShopViewHolder extends RecyclerView.ViewHolder {
    //服务态度
    @BindView(R.id.item_evaluate_shop_service_ratingBar)
    public RatingBar shopServiceRatingBar;
    //发货速度
    @BindView(R.id.item_evaluate_shop_speed_ratingBar)
    public RatingBar shopSpeedRatingBar;
    //物流服务
    @BindView(R.id.item_evaluate_logistics_speed_ratingBar)
    public RatingBar logisticsSpeedRatingBar;

    @BindView(R.id.item_evaluate_shop_publish_textView)
    public TextView publishShopNumTextView;
    @BindView(R.id.item_evaluate_button_relativeLayout)
    public LinearLayout publishShopRelativeLayout;
    @BindView(R.id.tv_five_star_tip)
    public TextView tv_five_star_tip;
    @BindView(R.id.item_evaluate_line_two_View)
    public View lineTwoView;

    public EvaluateShopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
