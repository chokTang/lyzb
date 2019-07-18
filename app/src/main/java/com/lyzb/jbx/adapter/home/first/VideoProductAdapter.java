package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

public class VideoProductAdapter extends BaseRecyleAdapter<HomeVideoModel.ListBean.GoodsListBean> {
    public VideoProductAdapter(Context context, List<HomeVideoModel.ListBean.GoodsListBean> list) {
        super(context, R.layout.item_video_product, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeVideoModel.ListBean.GoodsListBean item) {
        String urlImage =Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, item.getGoods_image());
        holder.setRadiusImageUrl(R.id.img_product, urlImage, 4);
        holder.setText(R.id.tv_name, item.getGoods_name());
        holder.setText(R.id.tv_shop_name, item.getShop_name());
        holder.setText(R.id.tv_price, "￥" + item.getGoods_price());
        holder.setVisible(R.id.tv_acer, TextUtils.isEmpty(item.getMax_integral_num()) || Integer.valueOf(item.getMax_integral_num()) <= 0 ? false : true);
        holder.setText(R.id.tv_acer, "+" + item.getMax_integral_num() + "元宝");
    }

    @Override
    public void onConvert(BaseViewHolder holder, HomeVideoModel.ListBean.GoodsListBean item, int position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (getItemCount() > 1) {
            params.width = ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(80);
        } else {
            params.width = ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(20);
        }
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(80), ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position + 1 < getItemCount()) {
            params.setMargins(DensityUtil.dpTopx(10), 0, 0, 0);
        } else {
            params.setMargins(DensityUtil.dpTopx(10), 0, DensityUtil.dpTopx(10), 0);

        }
        holder.cdFindViewById(R.id.layout).setLayoutParams(params);
        super.onConvert(holder, item, position);
    }
}
