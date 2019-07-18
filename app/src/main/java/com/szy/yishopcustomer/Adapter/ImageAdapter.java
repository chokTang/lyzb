package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.*;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/12.
 */

//轮播图
public class ImageAdapter extends PagerAdapter {
    private DisplayImageOptions options;
    private ArrayList<String> imgs;
    private Context mContext;

    public View.OnClickListener listener;

    public ImageAdapter(Context context, ArrayList<String> img) {
        mContext = context;
        imgs = img;
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(ImageLoader.ic_error).cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    //图片轮播
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageview = new ImageView(mContext);
        imageview.setScaleType(ImageView.ScaleType.FIT_START);
        String img = imgs.get(position);

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(img,
                imageview,options);

        Glide.with(mContext).load(img).into(imageview);

        container.addView(imageview);

        //imageview.setTag(position);
        imageview.setTag(R.id.imagePosition,position);
        View.OnClickListener myListener;
        myListener = listener == null ? new ImageViewClick() : listener;

        imageview.setOnClickListener(myListener);
        return imageview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    class ImageViewClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Object obj = view.getTag(R.id.imagePosition);
            int position = obj == null ? 0 : (int) obj;
            openImageGallery(position, imgs);
        }

        public void openImageGallery(int postion, ArrayList imgList) {
            Intent intent = new Intent();
            intent.setClass(mContext, ViewOriginalImageActivity.class);
            intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), imgList);
            intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), postion);
            mContext.startActivity(intent);
        }
    }
}