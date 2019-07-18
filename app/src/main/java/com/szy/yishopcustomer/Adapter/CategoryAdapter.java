package com.szy.yishopcustomer.Adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.CategoryViewHolder;
import com.szy.yishopcustomer.ViewModel.CategoryModel;

import java.util.ArrayList;

/**
 * Created by liuzhfieng on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final int TYPE_A = 0;
    private final int TYPE_B = 1;
    private final int TYPE_C = 2;
    public int itemWidth;
    private ArrayList<CategoryModel> mData;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CategoryAdapter(ArrayList<CategoryModel> data) {
        mData = data;
    }

    public ArrayList<CategoryModel> getData() {
        return mData;
    }

    public void setData(ArrayList<CategoryModel> mData) {
        this.mData = mData;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_A) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_category_type_a, parent, false);
        } else if (viewType == TYPE_B) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_category_type_b, parent, false);
        } else if (viewType == TYPE_C) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_category_type_c, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_category_type_b, parent, false);
        }
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {
        //修改样式，去掉加粗
        /*if (getItemViewType(position) == TYPE_B) {
            holder.textView.getPaint().setFakeBoldText(true);
        } else {
            holder.textView.getPaint().setFakeBoldText(false);
        }*/

        if(getItemViewType(position) == TYPE_A){
            holder.textView.setText("进入"+mData.get(position).getCatName()+"频道>>");
        }else{
            holder.textView.setText(mData.get(position).getCatName());
        }

        if (getItemViewType(position) == TYPE_A) {
            if (holder.imageButton != null) {
                ViewGroup.LayoutParams layoutParams = holder.imageButton.getLayoutParams();
                layoutParams.height = itemWidth;

            }
        }

        if (holder.imageButton != null) {
            String catImage = mData.get(position).getCatImage();
            if (!TextUtils.isEmpty(catImage)) {
                if (getItemViewType(position) == TYPE_A) {

                    holder.imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Utils.urlOfImage(mData.get(position).getCatImage()), holder
                            .imageButton, ImageLoader.options, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            ImageView im = (ImageView) view;
                            if (im.getScaleType().equals(ImageView.ScaleType.FIT_CENTER)) {
                                im.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            ImageView im = (ImageView) view;
                            if (im.getScaleType().equals(ImageView.ScaleType.FIT_CENTER)) {
                                im.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                        }
                    });
                } else {
                    ImageLoader.displayImage(Utils.urlOfImage(catImage), holder.imageButton);
                }
            } else {
                if (getItemViewType(position) == TYPE_A) {
                    holder.imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }

                if (ImageLoader.ic_stub == null) {
                    holder.imageButton.setImageResource(R.mipmap.pl_image);
                } else {
                    holder.imageButton.setImageDrawable(ImageLoader.ic_stub);
                }
            }

        }
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, mData.get(position));
                }
            }
        });
        holder.itemView.setTag(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        CategoryModel category = mData.get(position);
        switch (category.getCatLevel()) {
            case "1":
                return TYPE_A;
            case "2":
                return TYPE_B;
            case "3":
                return TYPE_C;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, CategoryModel data);
    }

}
