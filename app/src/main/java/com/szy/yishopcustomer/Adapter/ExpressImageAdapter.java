package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Express.GoodsListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ExpressImageViewHolder;

import java.util.ArrayList;

/**
 * Created by liuzhfieng on 2016/5/25 0025.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ExpressImageAdapter extends RecyclerView.Adapter<ExpressImageViewHolder> {

    public ArrayList<GoodsListModel> data;
    public View.OnClickListener onClickListener;

    public ExpressImageAdapter() {
        this.data = new ArrayList<GoodsListModel>();
    }

    @Override
    public ExpressImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .item_express_image, viewGroup, false);
        return new ExpressImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpressImageViewHolder holder, int position) {
        if(!Utils.isNull(data.get(position).sku_image)){
            ImageLoader.displayImage(Utils.urlOfImage(data.get(position).sku_image), holder
                    .imageView);
        }else {
            ImageLoader.displayImage(Utils.urlOfImage(data.get(position).goods_image), holder
                    .imageView);
        }


        Utils.setViewTypeForTag(holder.imageView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(holder.imageView, position);
        holder.imageView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<GoodsListModel> data) {
        this.data = data;
    }
}
