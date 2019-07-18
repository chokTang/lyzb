package com.lyzb.jbx.adapter.me.basic;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.basic.CardCilpModel;

import java.util.List;

public class CardCilpAdapter extends BaseRecyleAdapter<CardCilpModel> {

    public CardCilpAdapter(Context context, List<CardCilpModel> list) {
        super(context, R.layout.recycle_card_clip, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CardCilpModel item) {
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
        holder.setVisible(R.id.tv_position_name, !TextUtils.isEmpty(item.getPosition()));
        holder.setText(R.id.tv_position_name, String.format("丨  %s", item.getPosition()));
        holder.setText(R.id.tv_user_phone, TextUtils.isEmpty(item.getMobile())?"暂无电话":item.getMobile());
        holder.setText(R.id.tv_user_company_address, TextUtils.isEmpty(item.getShopAddress())?"暂无地址":item.getShopAddress());
        holder.setText(R.id.tv_user_company, TextUtils.isEmpty(item.getShopName())?"暂无公司":item.getShopName());
        holder.setImageUrl(R.id.img_company_logo, item.getShopLogo());
        holder.setVisible(R.id.img_company_logo, !TextUtils.isEmpty(item.getShopLogo()));
    }
}
