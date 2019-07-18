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
import com.lyzb.jbx.model.dynamic.DynamicDetailModel;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;

import java.util.List;

/**
 * 动态下-图片适配器
 * 可以动态改变图片的大小的
 */
public class SingleImageAdapterDynamic extends BaseRecyleAdapter<DynamicDetailModel.TopicFileListBean> {

    private ViewGroup.LayoutParams params;

    public SingleImageAdapterDynamic(Context context, List<DynamicDetailModel.TopicFileListBean> list) {
        super(context, R.layout.recycle_dynamic_img, list);
        int imgWidth = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(28)) / 3;
        params = new ViewGroup.LayoutParams(imgWidth, imgWidth);
    }

    public SingleImageAdapterDynamic(Context context, int width, List<DynamicDetailModel.TopicFileListBean> list) {
        super(context, R.layout.recycle_dynamic_img, list);
        params = new ViewGroup.LayoutParams(width, width);
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicDetailModel.TopicFileListBean item) {
        ImageView imageView = holder.cdFindViewById(R.id.img_dynamic_grid);
        imageView.setLayoutParams(params);
        LoadImageUtil.loadImage(imageView, item.getFile());
    }
}
