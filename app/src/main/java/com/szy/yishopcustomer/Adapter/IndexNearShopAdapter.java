package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.*;

import com.bumptech.glide.Glide;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NearShopItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.ShopListItemViewHolder;

import java.util.List;

/**
 * 首页附近店铺模块的Adapter
 */
public class IndexNearShopAdapter extends RecyclerView.Adapter<ShopListItemViewHolder> {
    public static int mPosition;
    public List<NearShopItemModel> Data;
    public View.OnClickListener onClickListener;

    public IndexNearShopAdapter(List<NearShopItemModel> data) {
        this.Data = data;
    }



    @Override
    public ShopListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_street_item, parent, false);
        return new ShopListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopListItemViewHolder viewHolder, int position) {
        NearShopItemModel item = Data.get(position);

        viewHolder.nameTextView.setText(item.shop_name);
        TextPaint tp = viewHolder.nameTextView.getPaint();
        tp.setFakeBoldText(true);

        if (!Utils.isNull(item.shop_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(item.shop_image), viewHolder.imageView);
        }

        if (!Utils.isNull(item.shop_description)) {
            viewHolder.synopsisTextView.setText(Utils.removeHTMLLabel
                    (item.shop_description));
        } else {
            viewHolder.synopsisTextView.setText(Utils.removeHTMLLabel
                    (item.simply_introduce));
        }

        viewHolder.saleTextView.setText(String.format(viewHolder.saleTextView.getContext()
                .getString(R.string.saleFormat), item.sale_num));

        if (item.distance > 0) {
            viewHolder.distanceTextView.setVisibility(View.VISIBLE);
            viewHolder.location.setVisibility(View.VISIBLE);
            String address = "";
            if(item.address.length()>6){
                address = item.address.substring(0,6)+"...";
            }else {
                address = item.address;
            }
            if (item.distance < 1) {
                viewHolder.distanceTextView.setText(address+" "+item.distance * 1000 + "m");
            } else {
                viewHolder.distanceTextView.setText(address+" "+item.distance + "km");
            }
        } else {
            viewHolder.distanceTextView.setVisibility(View.INVISIBLE);
            viewHolder.location.setVisibility(View.INVISIBLE);
        }

        Glide.with(viewHolder.imageView.getContext()).load(Utils.urlOfImage(item.credit_img)).into(viewHolder.rankImageView);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHOP_LIST_DUMMY);
        Utils.setExtraInfoForTag(viewHolder.itemView, item.shop_id);
        viewHolder.itemView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.location, ViewType.VIEW_TYPE_SHOP_LIST_DUMMY_LOCATION);
        Utils.setExtraInfoForTag(viewHolder.location, position);
        Utils.setPositionForTag(viewHolder.location, mPosition);
        viewHolder.location.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.distanceTextView, ViewType.VIEW_TYPE_SHOP_LIST_DUMMY_LOCATION);
        Utils.setExtraInfoForTag(viewHolder.distanceTextView, position);
        Utils.setPositionForTag(viewHolder.distanceTextView, mPosition);
        viewHolder.distanceTextView.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }
}
