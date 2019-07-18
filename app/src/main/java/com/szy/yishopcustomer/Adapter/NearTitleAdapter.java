package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleDistance;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleAll;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 同城生活-附近 头部 适配器
 * @time
 */

public class NearTitleAdapter extends RecyclerView.Adapter {

    public Context mContext;
    public List<NearTitleAll> mNearTitleAlls = new ArrayList<>();
    public List<NearTitleDistance> mNearTitleDistances = new ArrayList<>();

    public boolean isAll = false;

    public NearTitleAdapter(Context context) {
        this.mContext = context;
    }

    public void setNearTitle(List<NearTitleAll> titleModels) {
        this.mNearTitleAlls = titleModels;
    }

    public void setNearList(List<NearTitleDistance> listModels) {
        this.mNearTitleDistances = listModels;
    }

    public void setIsAll(boolean all) {
        this.isAll = all;
    }

    public RecyclerView.ViewHolder creatNearTitleViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_near_title, parent, false);
        NearTitleHolder holder = new NearTitleHolder(view);
        return holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatNearTitleViewHolder(parent);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        bindNearTitle((NearTitleHolder) holder, position);
    }

    @Override
    public int getItemCount() {

        if (isAll) {
            return mNearTitleAlls.size();
        } else {
            return mNearTitleDistances.size();
        }
    }

    public class NearTitleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_near_title_name)
        public TextView mTextView_Name;

        public NearTitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindNearTitle(final NearTitleHolder holder, final int position) {

        if (isAll) {
            holder.mTextView_Name.setText(mNearTitleAlls.get(position).catgName);
        } else {
            holder.mTextView_Name.setText(mNearTitleDistances.get(position).disContent);
        }

        holder.mTextView_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTitleItemClick(holder, position);
                }
            }
        });
    }

    public interface NearItemClick {
        void onTitleItemClick(NearTitleHolder holder, int position);
    }

    private NearItemClick listener;

    public void setListener(NearItemClick listener) {
        this.listener = listener;
    }

}
