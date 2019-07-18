package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.ContractModel;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsContractListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 17/07/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsContractListAdapter extends RecyclerView.Adapter {
    public final int VIEW_TYPE_ITEM = 0;

    public View.OnClickListener onCLickListener;
    public List<Object> data;

    public GoodsContractListAdapter(List<Object> data) {
        this.data = data;
        setHasStableIds(true);
    }

    public GoodsContractListAdapter() {
        this.data = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new GoodsContractListViewHolder(inflater.inflate(R.layout
                        .item_goods_contract_list, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                GoodsContractListViewHolder itemViewHolder = (GoodsContractListViewHolder) viewHolder;
                ContractModel model = (ContractModel) data.get
                        (position);
                //样式修改，不调用返回图片，写死
                //ImageLoader.displayImage(Utils.urlOfImage(model.contract_image),itemViewHolder.mContractImage);
                itemViewHolder.mContractName.setText(model.contract_name);

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ContractModel) {
            return VIEW_TYPE_ITEM;
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
