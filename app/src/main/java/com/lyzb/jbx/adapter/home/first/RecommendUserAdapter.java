package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.follow.InterestMemberModel;

import java.util.List;

public class RecommendUserAdapter extends BaseRecyleAdapter<InterestMemberModel> {

    public RecommendUserAdapter(Context context, List<InterestMemberModel> list) {
        super(context, R.layout.recycle_recommon_user, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, InterestMemberModel item) {
        int i = (int) (1 + Math.random() * (4 - 1 + 1));
        ImageView ivBg = holder.cdFindViewById(R.id.iv_recommon_bg);
        switch (i) {
            case 1:
                ivBg.setImageResource(R.drawable.union_recommend_bg);
                break;
            case 2:
                ivBg.setImageResource(R.drawable.union_recommend_bg2);
                break;
            case 3:
                ivBg.setImageResource(R.drawable.union_recommend_bg);
                break;
            case 4:
                ivBg.setImageResource(R.drawable.union_recommend_bg2);
                break;
            default:
                ivBg.setImageResource(R.drawable.union_recommend_bg);
        }
        holder.setRadiusImageUrl(R.id.img_recommon_header, item.getHeadimg(), 4);
        holder.setText(R.id.tv_recommon_name, item.getGsName());
        holder.setText(R.id.tv_user_phone, item.getMobile());
        holder.setText(R.id.tv_user_company, item.getShopName());
        holder.setText(R.id.tv_user_konw, String.format("我需要的:%s", item.getMyDemand()));
        holder.setText(R.id.tv_user_wait, String.format("我提供的:%s", item.getMyResources()));
        holder.setVisible(R.id.img_voice_play, item.getIntroductionAudioFile().size() > 0);
        if (TextUtils.isEmpty(item.getPosition())) {
            holder.setVisible(R.id.tv_position_name, false);
        } else {
            holder.setVisible(R.id.tv_position_name, true);
            holder.setText(R.id.tv_position_name, String.format("丨  %s", item.getPosition()));
        }

        holder.addItemClickListener(R.id.img_voice_play);
    }
}
