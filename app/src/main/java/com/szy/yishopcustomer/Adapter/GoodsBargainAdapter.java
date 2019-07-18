package com.szy.yishopcustomer.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.ResponseModel.Goods.BargainUsers;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

/**
 * 商品砍价轮播适配器
 */
public class GoodsBargainAdapter extends PagerAdapter {
    public View.OnClickListener onClickListener;
    public int itemPosition;
    public List<BargainUsers> data;
    public DisplayImageOptions options;

    public GoodsBargainAdapter(List<BargainUsers> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout
                .item_goods_bargain, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_bargain_avatar);
        TextView name = (TextView) view.findViewById(R.id.tv_bargain_name);
        BargainUsers bargainUsers = data.get(position);

        name.setText(bargainUsers.user_name);
        GlideApp.with(imageView.getContext())
                .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL,bargainUsers.headimg))
                .error(R.mipmap.pl_image)
                .into(imageView);

//        Utils.setViewTypeForTag(imageView, ViewType.VIEW_TYPE_AD);
//        Utils.setPositionForTag(imageView, itemPosition);
//        Utils.setExtraInfoForTag(imageView, position);
//        imageView.setOnClickListener(onClickListener);
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