package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.HackyViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liwei on 2016/8/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ViewOriginalImageFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_goods_original_thumb)
    HackyViewPager mGoodsThumbViewPager;
//    @BindView(R.id.fragment_view_original_image_pageIndicator)
//    CirclePageIndicator pageIndicator;
    @BindView(R.id.textView_position_tag)
    TextView textView_position_tag;

    private ArrayList<String> imageList;
    private int selectedIndex;
    private ImageAdapter imageAdapter;

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_VIEW_PAGER:
                getActivity().finish();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Intent intent = getActivity().getIntent();
        imageList = intent.getStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue());
        selectedIndex = intent.getIntExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), 1);

        setPostion(selectedIndex);
        imageAdapter = new ImageAdapter();
        imageAdapter.onClickListener = this;
        mGoodsThumbViewPager.setAdapter(imageAdapter);
        mGoodsThumbViewPager.setCurrentItem(selectedIndex);

        mGoodsThumbViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setPostion(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        pageIndicator.setViewPager(mGoodsThumbViewPager);

        return view;
    }

    private void setPostion(int position){
        textView_position_tag.setText(position+1 + "/" + imageList.size());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_view_original_image;
    }

    //轮播图
    class ImageAdapter extends PagerAdapter {
        public View.OnClickListener onClickListener;

        @Override
        public int getCount() {
            return imageList.size();
        }

        //图片轮播
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView imageview = new PhotoView(getActivity());
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            String img = imageList.get(position);

            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Utils.urlOfImage(img),imageview,ImageLoader.options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Drawable drawable =new BitmapDrawable(loadedImage);
                    ((PhotoView)view).setImageDrawable(drawable);
                }
            });

//            ImageLoader.displayImage(Utils.urlOfImage(img), imageview);

            Utils.setViewTypeForTag(imageview, ViewType.VIEW_TYPE_VIEW_PAGER);
            imageview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    getActivity().finish();
                }
            });
            imageview.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    getActivity().finish();
                }
            });
            imageview.setOnClickListener(onClickListener);

            container.addView(imageview);
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
    }
}
