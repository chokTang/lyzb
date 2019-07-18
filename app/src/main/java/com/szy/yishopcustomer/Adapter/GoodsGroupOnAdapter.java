package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.GoodsIndexFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.GrouponLogListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GoodsGroupOnViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by buqingqiang on 2016/8/29.
 */
public class GoodsGroupOnAdapter extends RecyclerView.Adapter<GoodsGroupOnViewHolder> {
    public List<GrouponLogListModel> data;
    public ImageView.OnClickListener onClickListener;
    public GoodsIndexFragment goodsIndexFragment;

    public GoodsGroupOnAdapter(List<GrouponLogListModel> list,GoodsIndexFragment goodsIndexFragment) {
        this.data = list;
        this.goodsIndexFragment = goodsIndexFragment;
    }

    public void setData() {
        data = new ArrayList<>();
    }

    @Override
    public GoodsGroupOnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_goods_group_on, parent, false);
        return new GoodsGroupOnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsGroupOnViewHolder holder, int position) {
        if (!Utils.isNull(data.get(position))) {
            final GrouponLogListModel item = data.get(position);
            ImageLoader.displayImage(Utils.urlOfImage(item.headimg), holder.mUserImageView);
            holder.mShopName.setText(item.user_name);
            holder.mGroupOnNumber.setText("还差" + item.diff_num + "人,");


            if((item.end_time - item.start_time)>0){
                holder.mTime.start((item.end_time - item.start_time) * 1000);
                holder.mTime.setVisibility(View.VISIBLE);
                holder.textView12.setText("剩余");
            }else{
                holder.mTime.setVisibility(View.GONE);
                holder.textView12.setText("已超时!");
                goodsIndexFragment.cancelGroupon(item.group_sn);
            }

            Utils.setViewTypeForTag(holder.mGoFightGroup, ViewType.VIEW_TYPE_GO_FIGHT_GROUP);
            Utils.setPositionForTag(holder.mGoFightGroup, position);
            holder.mGoFightGroup.setOnClickListener(onClickListener);

            holder.mTime.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    holder.mTime.setVisibility(View.GONE);
                    holder.textView12.setText("已超时!");

                    goodsIndexFragment.cancelGroupon(item.group_sn);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
