package com.szy.yishopcustomer.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

/**
 * Created by 宗仁 on 16/7/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GuideAdapter extends PagerAdapter {

    public List<String> data;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) LayoutInflater.from(container.getContext()).inflate(R
                .layout.fragment_guide_item, null);
        ImageLoader.getInstance().displayImage(Utils.urlOfImage(data.get(position)), imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
