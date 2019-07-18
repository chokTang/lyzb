package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.FoodsAdBannerBean;

import java.util.List;

/**
 * Created by 宗仁 on 16/6/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FoodsBannerAdapter extends PagerAdapter {
    public List<FoodsAdBannerBean> data;
    public DisplayImageOptions options;

    public FoodsBannerAdapter(List<FoodsAdBannerBean> data) {
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
        final ImageView imageView = (ImageView) view.findViewById(R.id.fragment_index_ad_item_imageView);
        final FoodsAdBannerBean adItemModel = data.get(position);

        GlideApp.with(imageView.getContext())
                .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, adItemModel.getImageUrl()))
                .error(R.mipmap.pl_image)
                .into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAd(adItemModel.getImageUrl(), imageView.getContext());
            }
        });
        container.addView(view);
        return view;
    }


    public void openAd(String url, Context context) {
        if (!TextUtils.isEmpty(url)) {
            new BrowserUrlManager(url).parseUrl(context, url);
        } else {
            if (Config.DEBUG) {
                Toast.makeText(context, R.string.emptyLink, Toast.LENGTH_SHORT).show();
            }
        }
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