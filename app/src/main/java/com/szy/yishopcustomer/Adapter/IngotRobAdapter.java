package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotRobRecordModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 元宝分享-抢元宝记录 adapter
 * @time 2018 2018/7/12 9:56
 */

public class IngotRobAdapter extends RecyclerView.Adapter {

    public List<Object> mList;
    public Context mContext;

    public IngotRobAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.mContext = context;
    }

    public RecyclerView.ViewHolder createRobHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_rob_record, parent, false);
        RobHolder holder = new RobHolder(view);
        return holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createRobHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        IngotRobRecordModel recordModel = (IngotRobRecordModel) mList.get(position);
        bindItem((RobHolder) holder, recordModel);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class RobHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_rob_record_img)
        ImageView mImageView_Img;
        @BindView(R.id.tv_rob_record_name)
        TextView mTextView_Name;
        @BindView(R.id.tv_rob_record_best)
        TextView mTextView_Best;
        @BindView(R.id.tv_rob_record_time)
        TextView mTextView_Time;
        @BindView(R.id.tv_rob_record_number)
        TextView mTextView_Number;

        public RobHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindItem(RobHolder holder, IngotRobRecordModel model) {

        if(!TextUtils.isEmpty(model.headimg)){
            GlideApp.with(mContext)
                    .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, model.headimg))
                    .error(R.mipmap.img_empty)
                    .into(holder.mImageView_Img);
        }else{
            holder.mImageView_Img.setImageResource(R.mipmap.img_empty);
        }

        holder.mTextView_Name.setText(model.user_name);
        holder.mTextView_Time.setText(Utils.times(model.receive_time));
        holder.mTextView_Number.setText(model.integral + "元宝");

        if (model.is_lucy == 1) {
            holder.mTextView_Best.setVisibility(View.VISIBLE);
            holder.mTextView_Best.setText("手气最佳");
        } else {
            holder.mTextView_Best.setVisibility(View.GONE);
        }
    }
}
