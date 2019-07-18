package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ShareViewHolder;
import com.szy.yishopcustomer.ViewModel.ShareItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShareAdapter extends RecyclerView.Adapter<ShareViewHolder> {
    public List<ShareItemModel> data;
    public View.OnClickListener onLickListener;

    public ShareAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_share_item, parent, false);
        return new ShareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShareViewHolder viewHolder, int position) {
        ShareItemModel item = data.get(position);
        viewHolder.imageView.setImageResource(item.image);
        viewHolder.titleTextView.setText(item.title);
        Utils.setPositionForTag(viewHolder.itemView, position);
        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHARE);
        viewHolder.itemView.setOnClickListener(onLickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
