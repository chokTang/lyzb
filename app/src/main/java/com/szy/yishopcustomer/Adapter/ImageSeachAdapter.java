package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2018 2018/9/6 15:18
 */

public class ImageSeachAdapter extends RecyclerView.Adapter {

    public List<Object> data;

    private Context mContext;

    public ImageSeachAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_seach_res, parent, false);
        SeachHolder holder = new SeachHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItem();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SeachHolder extends RecyclerView.ViewHolder {

        public SeachHolder(View itemView) {
            super(itemView);
        }
    }

    public void bindItem() {

    }
}
