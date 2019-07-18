package com.lyzb.jbx.adapter.me.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CardTemplateModel;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context mContext;
    List<CardTemplateModel> list = new ArrayList<>();
    public MyPagerAdapter(Context context, List<CardTemplateModel>  list) {
        this.list = list;
        this.mContext = context;

        //以下两个变量是做缓存处理
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory * 3 / 8;  //缓存区的大小

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_3d, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_template);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        LoadImageUtil.loadImage(imageView,list.get(position).getTemplateImg());
        tv_title.setText(list.get(position).getTemplateName());
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }






}