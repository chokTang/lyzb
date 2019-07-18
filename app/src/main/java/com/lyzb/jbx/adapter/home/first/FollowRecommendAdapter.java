package com.lyzb.jbx.adapter.home.first;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.follow.FollowAdoutUserModel;

import java.util.List;

/**
 * 首页-关注-推荐的人适配器
 */
public class FollowRecommendAdapter extends BaseRecyleAdapter<FollowAdoutUserModel> {

    public FollowRecommendAdapter(Context context, List<FollowAdoutUserModel> list) {
        super(context, R.layout.recycle_home_follow_recmmoend, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, FollowAdoutUserModel item) {
        holder.setVisible(R.id.img_recommond_vip,false);
        holder.setRoundImageUrl(R.id.img_recmmond_header,item.getHeadimg(),50);
        holder.setText(R.id.tv_recommend_name,item.getGsName());
        holder.setText(R.id.tv_recommend_company,item.getShopName());
        holder.setText(R.id.tv_relation_number,String.format("%d个共同好友",item.getPublicCount()));

        holder.addItemClickListener(R.id.tv_follow);
    }
}
