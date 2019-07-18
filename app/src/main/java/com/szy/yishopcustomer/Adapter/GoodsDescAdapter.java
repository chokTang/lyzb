package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.like.utilslib.image.config.GlideApp;
import com.szy.common.Adapter.CommonAdapter;
import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsDesc.MobileDescModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GoodsDesc.MobileDescImageViewHolder;
import com.szy.yishopcustomer.ViewHolder.GoodsDesc.MobileDescTextViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2016/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsDescAdapter extends CommonAdapter<MobileDescModel, CommonViewHolder> {
    public final int ITEM_TYPE_TEXT = 0;
    public final int ITEM_TYPE_IMAGE = 1;

    public Context mContext;
    public List<MobileDescModel> data;

    public GoodsDescAdapter() {
        this.data = new ArrayList<>();
    }

    public GoodsDescAdapter(Context context, List<MobileDescModel> data) {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).type == 0) {
            return ITEM_TYPE_TEXT;
        } else if (data.get(position).type == 1) {
            return ITEM_TYPE_IMAGE;
        } else {
            throw new RuntimeException("Unknown error");
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, int viewType, ViewGroup container) {
        int layoutId = 0;
        switch (viewType) {
            case ITEM_TYPE_TEXT:
                layoutId = R.layout.item_goods_desc_text;
                break;
            case ITEM_TYPE_IMAGE:
                layoutId = R.layout.item_goods_desc_image;
                break;
        }
        return LayoutInflater.from(container.getContext()).inflate(layoutId, container, false);
    }

    @Override
    public void onBindViewHolder(int position, int viewType, CommonViewHolder viewHolder, MobileDescModel item) {
        switch (viewType) {
            case ITEM_TYPE_TEXT:
                MobileDescTextViewHolder textViewHolder = (MobileDescTextViewHolder) viewHolder;
                textViewHolder.mDescText.setText(item.content);
                break;
            case ITEM_TYPE_IMAGE:
                final MobileDescImageViewHolder imageViewHolder = (MobileDescImageViewHolder) viewHolder;

                GlideApp.with(imageViewHolder.mDescImage.getContext())
                        .load(Utils.urlOfImage(item.content))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                if (imageViewHolder.mDescImage == null) {
                                    return false;
                                }

                                if (imageViewHolder.mDescImage.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                    imageViewHolder.mDescImage.setScaleType(ImageView.ScaleType.FIT_XY);
                                }

                                ViewGroup.LayoutParams params = imageViewHolder.mDescImage.getLayoutParams();
                                int vw = imageViewHolder.mDescImage.getWidth() - imageViewHolder.mDescImage.getPaddingLeft() - imageViewHolder.mDescImage.getPaddingRight();
                                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                                params.height = vh + imageViewHolder.mDescImage.getPaddingTop() + imageViewHolder.mDescImage.getPaddingBottom();
                                imageViewHolder.mDescImage.setLayoutParams(params);

                                return false;
                            }
                        })
                        .into(imageViewHolder.mDescImage);
                break;
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(int position, View itemView, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_TEXT:
                return new MobileDescTextViewHolder(itemView);
            case ITEM_TYPE_IMAGE:
                return new MobileDescImageViewHolder(itemView);
            default:
                return null;
        }
    }
}
