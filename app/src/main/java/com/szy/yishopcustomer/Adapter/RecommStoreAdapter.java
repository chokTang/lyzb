package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.RecommStoreModel;
import com.szy.yishopcustomer.ViewHolder.RecommStoreViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommStoreAdapter extends RecyclerView.Adapter {

    public View.OnClickListener onClickListener;

    public List<Object> data;
    public Map<String,String> status;

    public RecommStoreAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return createShopViewHolder(inflater, parent);
    }

    private RecyclerView.ViewHolder createShopViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_recoom_stroe_item, parent, false);
        return new RecommStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Object object = data.get(position);
        bindShopViewHolder((RecommStoreViewHolder) viewHolder, (RecommStoreModel.DataBean.ListBean) object,
                        position);
    }

    private void bindShopViewHolder(RecommStoreViewHolder viewHolder, RecommStoreModel.DataBean.ListBean object, int
            position) {

        viewHolder.textView_shop_name.setText(object.getShop_name());
        viewHolder.textView_address_info.setText(object.getAddress());
        viewHolder.textView_server_tel.setText(object.getMobile());

        if(status == null) {
            viewHolder.imageView_status.setVisibility(View.GONE);
        } else {
            viewHolder.imageView_status.setVisibility(View.VISIBLE);

            viewHolder.imageView_status.setImageResource(getStatusImage(object.getStatus()));
        }

    }

    private int getStatusImage(String state){
        int res = R.mipmap.bg_examine_ing;
        for (String key : status.keySet()) {
            if(key.equals(state)) {
                switch (status.get(key)) {
                    case "待审核":
                        res = R.mipmap.bg_examine_ing;
                        break;
                    case "审核通过":
                        res = R.mipmap.bg_examine_success;
                        break;
                    case "审核未通过":
                        res = R.mipmap.bg_examine_fail;
                        break;
                    case "已取消":
                        res = R.mipmap.bg_examine_cancel;
                        break;
                    default:
                        res = R.mipmap.bg_examine_ing;
                        break;
                }

                return res;
            }
        }

        return res;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
