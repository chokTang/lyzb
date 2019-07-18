package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.AdItemViewHolder;

import java.util.List;

/**
 * Created by liuzhifeng on 2016/9/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class IndexAdColumnThreeAdapter extends IndexAdColumnAdapter {

    public static int height;
    public static int weight;
    public static List<AdItemModel> data;

    public static boolean isShowLine = true;

    public IndexAdColumnThreeAdapter(List<AdItemModel> data) {
        super(data);
        IndexAdColumnThreeAdapter.data = data;
    }

    @Override
    public AdItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_three_item, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (Utils.getWindowWidth(parent.getContext()) / (float) data.size());
        return new AdItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdItemViewHolder viewHolder, int position) {

        ViewGroup.LayoutParams lp = viewHolder.layout.getLayoutParams();
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Context context = viewHolder.imageView.getContext();
        int leftMargin = Utils.dpToPx(context, 0.3f);

        //为了隐藏分割线
        if (!isShowLine) {
            leftMargin = 0;
        }

        if (position == 1) {
            layoutParam.setMargins(leftMargin, 0, 0, leftMargin);
            viewHolder.imageView.setLayoutParams(layoutParam);
        }
        if (position == 2) {
            layoutParam.setMargins(leftMargin, 0, 0, leftMargin);
            viewHolder.imageView.setLayoutParams(layoutParam);
        }
        if (position == 0) {
            lp.height = height;
            lp.width = weight / 2;
        } else {
            lp.height = (height / 2 + leftMargin + 1);
            lp.width = weight / 2 ;
        }
        viewHolder.layout.setLayoutParams(lp);
        AdItemModel itemModel = data.get(position);
        ImageLoader.displayImage(Utils.urlOfImage(itemModel.path), viewHolder.imageView);

        Utils.setPositionForTag(viewHolder.imageView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.imageView, ViewType.VIEW_TYPE_AD_COLUMN_THREE);
        Utils.setExtraInfoForTag(viewHolder.imageView, position);
        viewHolder.imageView.setOnClickListener(onClickListener);
    }
}
