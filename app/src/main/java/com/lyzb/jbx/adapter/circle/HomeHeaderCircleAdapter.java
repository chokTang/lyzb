package com.lyzb.jbx.adapter.circle;

import android.content.Context;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.circle.CircleModel;

import java.util.List;

/**
 * 主页-圈子-头部——item
 */
public class HomeHeaderCircleAdapter extends BaseRecyleAdapter<CircleModel> {

    public HomeHeaderCircleAdapter(Context context, List<CircleModel> list) {
        super(context, R.layout.recycle_home_circle_item, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleModel item) {
        if (holder.getLayoutPosition() == 0) {
            holder.setTextColor(R.id.tv_circle_name, R.color.fontcColor1);
            holder.setText(R.id.tv_circle_name, "更多圈子");
            holder.setImageUrl(R.id.img_circle, R.mipmap.icon_pic_add);
            holder.setVisible(R.id.img_recmmond_circle, false);
        } else {
            holder.setTextColor(R.id.tv_circle_name, R.color.fontcColor2);
            holder.setText(R.id.tv_circle_name, item.getGroupname());
            holder.setVisible(R.id.img_recmmond_circle, "3".equals(item.getIsJoin()) || "0".equals(item.getIsJoin()));
            ImageView imageView = holder.cdFindViewById(R.id.img_circle);
            LoadImageUtil.loadRoundImage(imageView, item.getLogo(), 4);
        }
    }
}
