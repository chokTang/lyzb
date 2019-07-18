package com.szy.yishopcustomer.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.NavigationItemViewHolder;

import java.util.List;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexNavigationAdapter extends RecyclerView.Adapter<NavigationItemViewHolder> {
    public View.OnClickListener onClickListener;
    public int itemPosition;

    //多个页面的时候，用来算出正确的position，默认是0,当是第二页的时候，就是（1*每页显示的个数），需要使用方算出
    public int baseNum = 0;

    public List<NavigationItemModel> data;

    public IndexNavigationAdapter(List<NavigationItemModel> data) {
        this.data = data;
    }

    @Override
    public NavigationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_navigation_item, parent, false);
        return new NavigationItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NavigationItemViewHolder viewHolder, int position) {
        NavigationItemModel item = data.get(position);
        viewHolder.textView.setText(item.name);
        try {
            int textColor = Color.parseColor(item.color);
            viewHolder.textView.setTextColor(textColor);
        } catch (Exception e) {

        }

        if (!Utils.isNull(item.path)) {

            String imgUrl = Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, item.path);

            GlideApp.with(viewHolder.imageView.getContext())
                    .load(imgUrl)
                    .error(R.mipmap.img_empty)
                    .into(viewHolder.imageView);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.img_empty);
        }
        Utils.setPositionForTag(viewHolder.itemView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_NAVIGATION_ITEM);
        Utils.setExtraInfoForTag(viewHolder.itemView, position + baseNum);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
