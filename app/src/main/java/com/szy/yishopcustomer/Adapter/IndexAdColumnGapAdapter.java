package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.AdItemViewHolder;

import java.util.List;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexAdColumnGapAdapter extends RecyclerView.Adapter<AdItemViewHolder> {
    public int itemPosition;
    public List<AdItemModel> data;
    public View.OnClickListener onClickListener;
    public int itemWidth;
    public int dividerWidth;

    public IndexAdColumnGapAdapter(List<AdItemModel> data) {
        this.data = data;
    }

    @Override
    public AdItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_index_ad_item, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = itemWidth;

        return new AdItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdItemViewHolder viewHolder, int position) {

        AdItemModel itemModel = data.get(position);

        GlideApp.with(viewHolder.imageView.getContext())
                .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, itemModel.path))
                .error(R.mipmap.img_empty)
                .into(viewHolder.imageView);

        Utils.setPositionForTag(viewHolder.imageView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.imageView, ViewType.VIEW_TYPE_AD);
        Utils.setExtraInfoForTag(viewHolder.imageView, position);
        viewHolder.imageView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
