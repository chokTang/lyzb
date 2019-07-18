package com.szy.yishopcustomer.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.like.utilslib.image.config.GlideApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

/**
 * Created by 宗仁 on 16/6/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexAdBannerAdapter extends PagerAdapter {
    public View.OnClickListener onClickListener;
    public int itemPosition;
    public List<AdItemModel> data;
    public DisplayImageOptions options;

    public IndexAdBannerAdapter(List<AdItemModel> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout
                .fragment_index_banner_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_index_ad_item_imageView);
        AdItemModel adItemModel = data.get(position);

        GlideApp.with(imageView.getContext())
                .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL,adItemModel.path))
                .error(R.mipmap.pl_image)
                .into(imageView);

        Utils.setViewTypeForTag(imageView, ViewType.VIEW_TYPE_AD);
        Utils.setPositionForTag(imageView, itemPosition);
        Utils.setExtraInfoForTag(imageView, position);
        imageView.setOnClickListener(onClickListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

}