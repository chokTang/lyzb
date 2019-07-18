package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceGoodsModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Checkout.InvoiceGoodsViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2016/11/07.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

/**
 * Created by liwei on 16/11/07.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceGoodsAdapter extends RecyclerView.Adapter<InvoiceGoodsViewHolder> {
    public ArrayList<InvoiceGoodsModel> mdata;

    public InvoiceGoodsAdapter(ArrayList<InvoiceGoodsModel> data) {
        if (Utils.isNull(data)) {
            mdata = new ArrayList<InvoiceGoodsModel>();
        } else {
            mdata = data;
        }

    }

    @Override
    public InvoiceGoodsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_goods,
                parent, false);
        return new InvoiceGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvoiceGoodsViewHolder viewHolder, int position) {
        InvoiceGoodsModel item = mdata.get(position);
        if (!Utils.isNull(item.sku_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(item.sku_image), viewHolder.imageView);
        }
        viewHolder.nameTextView.setText(item.sku_name);
        viewHolder.shopNameTextView.setText(item.shop_name);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
}
