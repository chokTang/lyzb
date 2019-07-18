package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ReviewShareOrderImgViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buqingqiang on 2016/8/29.
 */
public class ReviewShareOrderImgAdater extends RecyclerView.Adapter<ReviewShareOrderImgViewHolder> {
    public List<String> data;
    public static ImageView.OnClickListener onClickListener;

    public ReviewShareOrderImgAdater() {
        data = new ArrayList<>();
    }

    public ReviewShareOrderImgAdater(ArrayList<String> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

    @Override
    public ReviewShareOrderImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_review_share_order_img_item, parent, false);
        return new ReviewShareOrderImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewShareOrderImgViewHolder holder, int position) {
        if (!Utils.isNull(data.get(position))) {
            ImageLoader.displayImage(data.get(position), holder.mShareOrderImg);
            Utils.setViewTypeForTag(holder.mShareOrderImgDelete, ViewType
                    .VIEW_TYPE_SHARE_ORDER_IMG);
            Utils.setPositionForTag(holder.mShareOrderImgDelete, position);
            holder.mShareOrderImgDelete.setOnClickListener(onClickListener);
            Utils.setViewTypeForTag(holder.mShareOrderImg, ViewType.VIEW_TYPE_SHARE_ORDER_IMG);
            Utils.setPositionForTag(holder.mShareOrderImg, position);
            holder.mShareOrderImg.setOnClickListener(onClickListener);

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
