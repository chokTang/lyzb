package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.HelpBargainModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 帮砍名单 list 适配器
 * @time
 */

public class HelpBargainAdapter extends RecyclerView.Adapter {

    public List<Object> data;
    public Context mContext;

    public HelpBargainAdapter(Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    public RecyclerView.ViewHolder createIncomeViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help_bargain_list, parent, false);
        HelpBargain holder = new HelpBargain(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createIncomeViewHolder(parent);
    }

    /***
     * ITEM 赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        HelpBargainModel list = (HelpBargainModel) data.get(position);
        bindItem((HelpBargain) holder, list);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class HelpBargain extends RecyclerView.ViewHolder {

        @BindView(R.id.img_help_bargain_head)
        public ImageView mImageView_Head;
        @BindView(R.id.tv_help_bargain_name)
        public TextView mTextView_Name;
        @BindView(R.id.tv_help_bargain_time)
        public TextView mTextView_Time;
        @BindView(R.id.tv_help_bargain_amount)
        public TextView mTextView_Amount;

        public HelpBargain(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void bindItem(HelpBargain holder, HelpBargainModel bargainList) {

        GlideApp.with(mContext)
                .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, bargainList.headimg))
                .error(R.mipmap.img_empty)
                .centerCrop()
                .into(holder.mImageView_Head);

        holder.mTextView_Name.setText(bargainList.nickname);
        holder.mTextView_Time.setText(Utils.times(bargainList.add_time));

        holder.mTextView_Amount.setText("¥" + bargainList.amount);
    }
}
