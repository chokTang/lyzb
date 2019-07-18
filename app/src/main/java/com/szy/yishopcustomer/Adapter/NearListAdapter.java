package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.ScoreStart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID;

/**
 * @author wyx
 * @role 同城生活-附近 列表 适配器
 * @time
 */

public class NearListAdapter extends RecyclerView.Adapter {

    public Context mContext;
    public List<NearListModel> listModels = new ArrayList<>();

    public NearListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<NearListModel> modelList) {
        this.listModels = modelList;
    }

    public RecyclerView.ViewHolder creatNearListViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_near_list, parent, false);
        NearListHolder holder = new NearListHolder(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatNearListViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNearList((NearListHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }


    public class NearListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rale_near_list)
        RelativeLayout mLayout_List;

        @BindView(R.id.img_near_list_name)
        ImageView mImageView_Img;
        @BindView(R.id.tv_near_list_name)
        TextView mTextView_Name;
        @BindView(R.id.st_naer_list_start)
        ScoreStart mStart;
        @BindView(R.id.tv_near_list_type)
        TextView mTextView_Type;

        @BindView(R.id.tv_near_distance)
        TextView mTextView_Distance;
        @BindView(R.id.tv_near_sale_number)
        TextView mTextView_SaleNumber;


        @BindView(R.id.lin_group_label_near)
        LinearLayout mLinearLayout_Group;
        @BindView(R.id.tv_group_title_near)
        TextView mTextView_Group;

        @BindView(R.id.lin_dedu_label_naer)
        LinearLayout mLinearLayout_Dedu;
        @BindView(R.id.tv_dedu_title_near)
        TextView mTextView_Dedu;

        @BindView(R.id.lin_takeout_label_near)
        LinearLayout mLinearLayout_Takeout;
        @BindView(R.id.tv_takeout_title_near)
        TextView mTextView_Takeout;

        public NearListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindNearList(final NearListHolder holder, final int position) {

        if (!TextUtils.isEmpty(listModels.get(position).shopLogo)) {

            GlideApp.with(mContext)
                    .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, listModels.get(position).shopLogo))
                    .error(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.mImageView_Img);

        } else {

            GlideApp.with(mContext)
                    .load(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.mImageView_Img);
        }

        holder.mTextView_Name.setText(listModels.get(position).shopName);
        holder.mStart.setMark(listModels.get(position).evalScore);

        if (listModels.get(position).goodsCatNameList != null) {
            if (listModels.get(position).goodsCatNameList.size() > 0) {
                holder.mTextView_Type.setText(listModels.get(position).goodsCatNameList.get(0).catName);
            }
        }

        //根据值 显示or隐藏  团购 抵扣 外卖 标签 文本
        if (listModels.get(position).userShopPower.equals("1")) {
            holder.mTextView_Group.setText(listModels.get(position).prodectName);
        } else {
            holder.mLinearLayout_Group.setVisibility(View.GONE);
        }

        if (listModels.get(position).acerLabel.equals("1")) {
            holder.mTextView_Dedu.setText("元宝最高抵扣:¥" + listModels.get(position).acer);
        } else {
            holder.mLinearLayout_Dedu.setVisibility(View.GONE);
        }

        if (listModels.get(position).takeOutStatus) {
            holder.mTextView_Takeout.setText("商家支持外卖");
        } else {
            holder.mLinearLayout_Takeout.setVisibility(View.GONE);
        }

        holder.mTextView_SaleNumber.setVisibility(View.GONE);

        double distance = listModels.get(position).distance;
        if (distance > 1000) {
            holder.mTextView_Distance.setText(Utils.toDistance(distance) + "km");
        } else if (distance > 0) {
            holder.mTextView_Distance.setText(Utils.toM(distance) + "m");
        } else {
            holder.mTextView_Distance.setVisibility(View.GONE);
        }

        holder.mLayout_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                if (listModels.get(position).saleSkip<=0) {//不是外卖
                    intent = new Intent(mContext, ShopDetailActivity.class);
                    intent.putExtra(KEY_SHOP_ID, String.valueOf(listModels.get(position).shopId));
                } else {//外卖
                    intent = new Intent(mContext, ProjectH5Activity.class);
                    intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + listModels.get(position).shopId );
                }
                mContext.startActivity(intent);
            }
        });
    }
}
