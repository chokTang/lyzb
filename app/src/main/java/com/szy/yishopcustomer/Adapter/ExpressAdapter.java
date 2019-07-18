package com.szy.yishopcustomer.Adapter;

import android.view.*;

import com.szy.common.Adapter.CommonAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Express.ListModel;
import com.szy.yishopcustomer.ViewHolder.ExpressViewHolder;

import java.util.List;

/**
 * Created by liwei on 2016/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ExpressAdapter extends CommonAdapter<ListModel, ExpressViewHolder> {

    public ExpressAdapter(List<ListModel> data) {

        super(data, R.layout.item_express);

    }

    @Override
    public void onBindViewHolder(int position, int viewType, ExpressViewHolder viewHolder,
                                 ListModel list) {

        if (mData.size() == 1) {
            viewHolder.mExpressMsg.setTextColor(0xff70B588);
            viewHolder.mExpressTime.setTextColor(0xff70B588);
            viewHolder.mViewOne.setVisibility(View.INVISIBLE);
            viewHolder.mViewTwo.setVisibility(View.INVISIBLE);
            viewHolder.mImageViewOne.setVisibility(View.VISIBLE);
            viewHolder.mImageViewTwo.setVisibility(View.INVISIBLE);
        } else {
            if (position == 0) {
                viewHolder.mExpressMsg.setTextColor(0xff70B588);
                viewHolder.mExpressTime.setTextColor(0xff70B588);
                viewHolder.mViewOne.setVisibility(View.INVISIBLE);
                viewHolder.mViewTwo.setVisibility(View.VISIBLE);
                viewHolder.mImageViewOne.setVisibility(View.VISIBLE);
                viewHolder.mImageViewTwo.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.mExpressMsg.setTextColor(0xff999999);
                viewHolder.mExpressTime.setTextColor(0xff999999);
                viewHolder.mViewOne.setVisibility(View.VISIBLE);
                viewHolder.mViewTwo.setVisibility(View.INVISIBLE);
                viewHolder.mImageViewOne.setVisibility(View.INVISIBLE);
                viewHolder.mImageViewTwo.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.mExpressMsg.setText(list.msg);
        viewHolder.mExpressTime.setText(list.time);
    }

    @Override
    public ExpressViewHolder onCreateViewHolder(int position, View itemView, int viewType) {
        return new ExpressViewHolder(itemView);
    }
}
