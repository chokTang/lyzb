package com.lyzb.jbx.adapter.circle;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.circle.CircleModel;

import java.util.List;

public class SearchCircleAdapter extends BaseRecyleAdapter<CircleModel> {

    public SearchCircleAdapter(Context context, List<CircleModel> list) {
        super(context, R.layout.recycle_search_circle, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleModel item) {
        holder.setRadiusImageUrl(R.id.img_cricle_header, item.getLogo(), 4);
        holder.setText(R.id.tv_cricle_name, item.getGroupname());
        holder.setText(R.id.tv_cricle_introce, String.format("圈主：%s", item.getOwnerVo().getUserName()));
        //圈子简介
        holder.setText(R.id.tv_cricle_describe, item.getDescription());
        //成员、动态数量
        String cricleCumber = item.getMenberNum() + "成员" + "  丨  " + item.getDynamicNum() + "动态";
        holder.setText(R.id.tv_cricle_number, cricleCumber);
        //加入圈子的状态1、管理员 2：加入 ，3未加入4 申请中
        TextView statusTv = holder.cdFindViewById(R.id.circle_more_item_status_tv);
        switch (CommonUtil.converToT(item.getIsJoin(), 0)) {
            case 1:
                statusTv.setText("我的");
                statusTv.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor3));
                statusTv.setBackground(null);
                break;
            case 2:
                statusTv.setText("已加入");
                statusTv.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor3));
                statusTv.setBackground(null);
                break;
            case 3:
                statusTv.setText("加入");
                statusTv.setTextColor(ContextCompat.getColor(_context, R.color.app_blue));
                statusTv.setBackground(ContextCompat.getDrawable(_context, R.drawable.st_app_blue_r4));
                //加入点击
                holder.addItemClickListener(R.id.circle_more_item_status_tv);
                break;
            case 4:
                statusTv.setText("申请中");
                statusTv.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor3));
                statusTv.setBackground(null);
                break;
            default:
                statusTv.setVisibility(View.GONE);
        }
        //是否为企业圈子
        holder.setVisible(R.id.circle_more_item_iscompany_iv, !TextUtils.isEmpty(item.getOrgId()));

        //三个成员头像
        holder.setVisible(R.id.img_cir_search_one, false);
        holder.setVisible(R.id.img_cir_search_two, false);
        holder.setVisible(R.id.img_cir_search_three, false);
        if (item.getMenberList().size() > 0) {
            holder.setVisible(R.id.img_cir_search_one, true);
            holder.setRadiusImageUrl(R.id.img_cir_search_one, item.getMenberList().get(0).getHeadimg(), 30);
        }
        if (item.getMenberList().size() > 1) {
            holder.setVisible(R.id.img_cir_search_two, true);
            holder.setRadiusImageUrl(R.id.img_cir_search_two, item.getMenberList().get(1).getHeadimg(), 30);
        }
        if (item.getMenberList().size() > 2) {
            holder.setVisible(R.id.img_cir_search_three, true);
            holder.setRadiusImageUrl(R.id.img_cir_search_three, item.getMenberList().get(2).getHeadimg(), 30);
        }
    }
}
