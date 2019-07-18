package com.lyzb.jbx.adapter.home.search;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

public class SearchGoodsAdapter extends BaseRecyleAdapter<GoodsModel> {

    private LinearLayout.LayoutParams params;

    public SearchGoodsAdapter(Context context, List<GoodsModel> list) {
        super(context, R.layout.recycle_search_goods, list);
        int width = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(30)) / 2;
        params = new LinearLayout.LayoutParams(width, width);
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsModel item) {
        ImageView img_goods_img = holder.cdFindViewById(R.id.img_goods_img);
        img_goods_img.setLayoutParams(params);

        holder.setRadiusImageUrl(R.id.img_goods_img, Utils.urlOfImage(item.goods_image), 4);
        holder.setText(R.id.tv_goods_name, item.goods_name);
        holder.setText(R.id.tv_goods_price, String.format("￥%s", item.goods_price));
        holder.setText(R.id.tv_goods_yuanbao, String.format("+%s元宝", item.max_integral_num));
        holder.setText(R.id.tv_goods_sales_price, item.goods_bxprice_format);
    }
}
