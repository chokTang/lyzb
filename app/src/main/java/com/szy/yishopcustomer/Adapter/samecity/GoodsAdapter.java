package com.szy.yishopcustomer.Adapter.samecity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderGoodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 同城生活-外卖订单详情-商品适配器
 * Created by Administrator on 2018/6/20.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder> {

    public List<OrderGoodModel> mList;
    public LayoutInflater mInflater;
    public Context mContext;

    public GoodsAdapter(Context context, List<OrderGoodModel> list) {
        mList = new ArrayList<>();
        if (list != null || list.size() > 0) {
            mList.addAll(list);
        }
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void udpateList(List<OrderGoodModel> data) {
        if (data.size() > 0) {
            this.mList.clear();
            this.mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_takeout_order_good_layout, parent, false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {
        OrderGoodModel itemModel = mList.get(position);
        //设置订单图片
        String imgUrl = itemModel.getOrderProdLogo();
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("http")) {
            GlideApp.with(mContext).load(imgUrl)
                    .error(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.good_img);
        } else {
            GlideApp.with(mContext).load(Api.API_CITY_HOME_MER_IMG_URL + imgUrl)
                    .error(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.good_img);
        }

        holder.goods_name_tv.setText(itemModel.getProdName());
        holder.goods_number_tv.setText(String.format("X%d", itemModel.getProdNum()));
        holder.goods_price_tv.setText(String.format("￥%.2f", itemModel.getProdPrice()));
        holder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GoodsHolder extends RecyclerView.ViewHolder {

        public ImageView good_img;
        public TextView goods_name_tv;
        public TextView goods_number_tv;
        public TextView goods_price_tv;
        public LinearLayout item_view;

        public GoodsHolder(View itemView) {
            super(itemView);
            good_img = (ImageView) itemView.findViewById(R.id.good_img);
            goods_name_tv = (TextView) itemView.findViewById(R.id.goods_name_tv);
            goods_number_tv = (TextView) itemView.findViewById(R.id.goods_number_tv);
            goods_price_tv = (TextView) itemView.findViewById(R.id.goods_price_tv);
            item_view = (LinearLayout) itemView.findViewById(R.id.item_view);
        }
    }
}
