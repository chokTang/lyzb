package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Shop.ShopClassViewHolder;
import com.szy.yishopcustomer.ViewModel.ShopClassModel;

import java.util.ArrayList;

/**
 * Created by liwei on 17/8/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopClassAdapter extends RecyclerView.Adapter<ShopClassViewHolder> {

    private final int TYPE_A = 0;
    private final int TYPE_B = 1;
    private final int TYPE_C = 2;
    public int itemWidth;
    private ArrayList<ShopClassModel> mData;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ShopClassAdapter(ArrayList<ShopClassModel> data) {
        mData = data;
    }

    public ArrayList<ShopClassModel> getData() {
        return mData;
    }

    public void setData(ArrayList<ShopClassModel> mData) {
        this.mData = mData;
    }

    @Override
    public ShopClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_A) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_shop_class_category_type_a, parent, false);
        } else if (viewType == TYPE_B) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_shop_class_category_type_b, parent, false);
        } else if (viewType == TYPE_C) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_shop_class_category_type_c, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_shop_class_category_type_a, parent, false);
        }
        return new ShopClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopClassViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position).getCls_name());
        if (getItemViewType(position) == TYPE_A) {
            holder.textView.getPaint().setFakeBoldText(true);
        } else {
            holder.textView.getPaint().setFakeBoldText(false);
        }

        if (holder.imageButton != null) {
            String catImage = mData.get(position).getCls_image();
            if (!TextUtils.isEmpty(catImage)) {
                ImageLoader.displayImage(Utils.urlOfImage(catImage), holder.imageButton);
            } else {
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
        ShopClassModel shopClassModel = mData.get(position);
        switch (shopClassModel.level) {
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
        void onItemClick(View view, ShopClassModel data);
    }

}
