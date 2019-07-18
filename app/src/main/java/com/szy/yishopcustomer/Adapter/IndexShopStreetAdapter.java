package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetItemModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页店铺版式一Adapter
 */
public class IndexShopStreetAdapter extends RecyclerView.Adapter<IndexShopStreetAdapter
        .ImageViewHolder> {
    public List<ShopStreetItemModel> data;
    public View.OnClickListener onClickListener;
    public int itemPosition;

    public IndexShopStreetAdapter(List<ShopStreetItemModel> data) {
        this.data = data;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_shop_street_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position) {
        ShopStreetItemModel shopStreetItemModel = data.get(position);
        if (!Utils.isNull(shopStreetItemModel.shop_logo)) {
            ImageLoader.displayImage(Utils.urlOfImage(shopStreetItemModel.shop_logo), viewHolder
                    .imageView);
            Utils.setViewTypeForTag(viewHolder.imageView, ViewType.VIEW_TYPE_SHOP);
            Utils.setPositionForTag(viewHolder.imageView, itemPosition);
            Utils.setExtraInfoForTag(viewHolder.imageView, position);
            viewHolder.imageView.setOnClickListener(onClickListener);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.ic_shop_empty);
            Utils.setViewTypeForTag(viewHolder.imageView, ViewType.VIEW_TYPE_SHOP);
            Utils.setPositionForTag(viewHolder.imageView, -1);
            Utils.setExtraInfoForTag(viewHolder.imageView, -1);
            viewHolder.imageView.setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_shop_street_item_imageView)
        public ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
