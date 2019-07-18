package com.szy.yishopcustomer.ViewHolder.GroupOn;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2017/3/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class UserGroupOnListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_groupon_list_sn)
    public TextView mSpellNo;
    @BindView(R.id.item_groupon_status)
    public TextView mStatus;
    @BindView(R.id.item_groupon_goodsName)
    public TextView mGoodsName;
    @BindView(R.id.item_groupon_attr_textView)
    public TextView mGoodsAttr;
    @BindView(R.id.item_groupon_money_textView)
    public TextView mMoney;
    @BindView(R.id.item_groupon_number_all)
    public TextView mPeopleNumber;
    @BindView(R.id.item_groupon_number)
    public TextView mHasPeopleNumber;
    @BindView(R.id.item_groupon_goods_imageView)
    public ImageView mGoodsImage;
    @BindView(R.id.item_groupon_goodsLayout)
    public RelativeLayout mGoodsItem;
    @BindView(R.id.item_groupon_order_detail_button)
    public TextView mOrderDetail;
    @BindView(R.id.item_groupon_detail_button)
    public TextView mGrouponDetail;
    @BindView(R.id.item_groupon_detail_share)
    public TextView mGrouponShare;

    public UserGroupOnListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
