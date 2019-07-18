package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.carouselAdvert;

import java.util.List;

/**
 * @author wyx
 * @role banner viewpager
 * @time 2018 16:26
 */

public class CityHomeAdBannerAdapter extends PagerAdapter {

    public int itemPosition;
    public List<carouselAdvert> mList;
    public DisplayImageOptions options;

    public Context mContext;

    public CityHomeAdBannerAdapter(Context context, List<carouselAdvert> mCarouselAdvertList) {
        this.mContext = context;
        this.mList = mCarouselAdvertList;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(ImageLoader.ic_error)
                .showImageOnFail(ImageLoader.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_city_home_banner_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_home_ad_item_imageView);

        final carouselAdvert model = mList.get(position);

        Glide.with(mContext).load(model.imageUrl).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.linkUrl.length()>0){
                    Intent intent = new Intent(mContext, ProjectH5Activity.class);
                    intent.putExtra(Key.KEY_URL.getValue(), model.linkUrl);
                    mContext.startActivity(intent);
                }
            }
        });
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
}
