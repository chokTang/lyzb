package com.lyzb.jbx.adapter.common;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.image.LoadImageUtil;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;

import java.util.Collections;
import java.util.List;

/**
 * 动态下-图片适配器
 * 可以动态改变图片的大小的
 */
public class SingleImageAdapter extends BaseRecyleAdapter<DynamicFileModel> {

    private ViewGroup.LayoutParams params;

    public SingleImageAdapter(Context context, List<DynamicFileModel> list) {
        super(context, R.layout.recycle_dynamic_img, list);
        int imgWidth = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(28)) / 3;
        params = new ViewGroup.LayoutParams(imgWidth, imgWidth);
    }

    public SingleImageAdapter(Context context, int width, List<DynamicFileModel> list) {
        super(context, R.layout.recycle_dynamic_img, list);
        params = new ViewGroup.LayoutParams(width, width);
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicFileModel item) {
        ImageView imageView = holder.cdFindViewById(R.id.img_dynamic_grid);
        imageView.setLayoutParams(params);
        LoadImageUtil.loadRoundImage(imageView, item.getFile(), 2);
    }

    @Override
    public void update(List<DynamicFileModel> items) {
        if (items != null && items.size() > 0) {
            Collections.sort(items);
        }
        super.update(items);
    }
}
