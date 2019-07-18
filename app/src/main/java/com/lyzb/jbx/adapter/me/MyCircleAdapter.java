package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CircleModel;

import java.util.List;

/****
 * 我的圈子 adapter
 *
 */
public class MyCircleAdapter extends BaseRecyleAdapter<CircleModel.ListBean> {

    public MyCircleAdapter(Context context, List<CircleModel.ListBean> list) {
        super(context, R.layout.recycle_search_circle, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleModel.ListBean item) {
        //头像
        holder.setRadiusImageUrl(R.id.img_cricle_header, item.getLogo(), 4);
        //圈子名称
        holder.setText(R.id.tv_cricle_name, item.getGroupname());
        holder.setText(R.id.tv_cricle_introce, String.format("圈主：%s", item.getOwnerVo().getUserName()));
        //成员数量and动态数量
        String cricleCumber = item.getMenberNum() + "成员" + "  丨  " + item.getDynamicNum() + "动态";
        holder.setText(R.id.tv_cricle_number, cricleCumber);
        //能出现在这里的都是加入了的或者创建的，隐藏加入按钮
        holder.setVisible(R.id.circle_more_item_status_tv, false);
        //是否为我创建的
        holder.setVisible(R.id.circle_more_item_isme_tv, item.isIfOwner());
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
