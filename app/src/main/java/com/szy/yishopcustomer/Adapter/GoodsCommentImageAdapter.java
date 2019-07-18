package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GoodsCommentImageViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2016/8/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsCommentImageAdapter extends RecyclerView.Adapter<GoodsCommentImageViewHolder> {
    public static ImageView.OnClickListener onClickListener;
    public List<String> data;
    public int itemPosition;

    public GoodsCommentImageAdapter(ArrayList<String> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

    @Override
    public GoodsCommentImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_goods_comment_image, parent, false);
        return new GoodsCommentImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsCommentImageViewHolder holder, int position) {
        if (!Utils.isNull(data.get(position))) {
            if (!Utils.isNull(data.get(position))) {
                ImageLoader.displayImage(Utils.urlOfImage(data.get(position)), holder
                        .mGoodsCommentImage);
            }
            Utils.setViewTypeForTag(holder.mGoodsCommentImage, ViewType
                    .VIEW_TYPE_GOODS_COMMENT_IMAGE);
            Utils.setPositionForTag(holder.mGoodsCommentImage, position);
            Utils.setExtraInfoForTag(holder.mGoodsCommentImage, itemPosition);
            holder.mGoodsCommentImage.setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
