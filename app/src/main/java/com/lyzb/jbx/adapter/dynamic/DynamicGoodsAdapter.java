package com.lyzb.jbx.adapter.dynamic;

import android.content.Context;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;

import java.util.List;

/**
 * 动态下的商品列表
 */
public class DynamicGoodsAdapter extends BaseRecyleAdapter<GoodsModel> {

    private ViewGroup.LayoutParams params;

    public DynamicGoodsAdapter(Context context, List<GoodsModel> list) {
        super(context, R.layout.recycle_dynamic_goods_item, list);
        params = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(80), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsModel item) {
        holder.itemView.setLayoutParams(params);
    }
}
