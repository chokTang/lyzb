package com.szy.yishopcustomer.Adapter.samecity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.SeachHisModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 同城生活-分类商品 搜索历史 adapter
 * Created by Administrator on 2018/6/20.
 */

public class SeachHisAdapter extends RecyclerView.Adapter<SeachHisAdapter.HisHolder> {

    public List<Object> data;
    private Context mContext;

    public SeachHisAdapter(Context context) {

        data = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public HisHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_seach_his, parent, false);
        return new HisHolder(view);
    }

    @Override
    public void onBindViewHolder(HisHolder holder, int position) {

        SeachHisModel model = (SeachHisModel) data.get(position);
        bindHisItem(holder, model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HisHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_seach_his_content)
        TextView mTextView;

        public HisHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void bindHisItem(HisHolder holder, final SeachHisModel model) {
        holder.mTextView.setText(model.his_name);

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onClick(model.his_name);
                }
            }
        });
    }

    public interface onItemClick {
        void onClick(String name);
    }

    private onItemClick mOnItemClick;

    public void setOnItemClick(onItemClick itemClick) {
        this.mOnItemClick = itemClick;
    }
}
