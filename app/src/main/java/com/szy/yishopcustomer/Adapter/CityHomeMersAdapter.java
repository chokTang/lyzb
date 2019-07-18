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
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.list;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author
 * @role 同城生活 首页 推荐商家 adapter
 * @time 2018 14:22
 */

public class CityHomeMersAdapter extends RecyclerView.Adapter {

    public View.OnClickListener onClickListener;

    public Context mContext;
    public List<list> mLists = new ArrayList<>();

    public Intent mIntent = null;

    public CityHomeMersAdapter(Context context, List<list> list) {
        this.mContext = context;
        this.mLists = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatMenuHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItemMenuGroup((ItemHoler) holder, position);
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public RecyclerView.ViewHolder creatMenuHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_mer_layout, parent, false);
        ItemHoler holder = new ItemHoler(view);
        return holder;
    }

    public class ItemHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_city_mer)
        RelativeLayout mLayout_Mer;

        @BindView(R.id.fragment_city_mer_img)
        ImageView mImageView_Img;
        @BindView(R.id.fragment_city_mer_name)
        TextView mTextView_Name;
        @BindView(R.id.fragment_city_mer_content)
        TextView mTextView_Content;
        @BindView(R.id.fragment_city_mer_price)
        TextView mTextView_Price;

        @BindView(R.id.lin_dedu_title_home)
        LinearLayout mLinearLayout_Dedu;
        @BindView(R.id.img_dedu_label_home)
        ImageView mImageView_Dedu;
        @BindView(R.id.tv_ingot_dedu_home)
        TextView mTextView_IngotNumber;

        @BindView(R.id.lin_takeout_title_home)
        LinearLayout mLinearLayout_Takeout;
        @BindView(R.id.img_takeout_label_home)
        ImageView mImageView_Takeout;
        @BindView(R.id.tv_takeout_title_home)
        TextView mTextView_Takeout;


        @BindView(R.id.fragment_city_mer_distance)
        TextView mTextView_Distance;
        @BindView(R.id.fragment_city_mer_sale_number)
        TextView mTextView_SaleNumber;

        public ItemHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindItemMenuGroup(ItemHoler holder, final int position) {


        if (!TextUtils.isEmpty(mLists.get(position).shopLogo)) {
            GlideApp.with(mContext)
                    .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, mLists.get(position).shopLogo))
                    .error(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.mImageView_Img);
        } else {
            GlideApp.with(mContext)
                    .load(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.mImageView_Img);
        }

        holder.mTextView_Name.setText(mLists.get(position).shopName);


        if (mLists.get(position).shopDescription == null) {
            holder.mTextView_Content.setVisibility(View.GONE);
        } else {
            holder.mTextView_Content.setText(mLists.get(position).shopDescription);
        }

        holder.mTextView_Price.setText("¥" + mLists.get(position).minPice);

        //元宝抵扣标签  外卖标签 显示和隐藏
        if (mLists.get(position).acerLabel.equals("1")) {
            holder.mImageView_Dedu.setVisibility(View.VISIBLE);
            holder.mTextView_IngotNumber.setText("元宝最高抵扣:¥" + mLists.get(position).acer);
        } else {
            holder.mLinearLayout_Dedu.setVisibility(View.GONE);
        }

        if (mLists.get(position).takeOutStatus) {
            holder.mImageView_Takeout.setVisibility(View.VISIBLE);
            holder.mTextView_Takeout.setText("商家支持外卖");
        } else {
            holder.mLinearLayout_Takeout.setVisibility(View.GONE);
        }


        double distance = Math.ceil(mLists.get(position).distance);
        if (distance > 1000) {
            //距离 单位换算
            holder.mTextView_Distance.setText(Utils.toDistance(distance) + "km");
        } else if (distance == 0) {
            holder.mTextView_Distance.setVisibility(View.GONE);
        } else {
            holder.mTextView_Distance.setText(Utils.toM(distance) + "m");
        }


        if (mLists.get(position).soldNum == null) {
            holder.mTextView_SaleNumber.setVisibility(View.GONE);
        } else {
            holder.mTextView_SaleNumber.setText(mLists.get(position).soldNum);
        }

        //推荐商家点击
        holder.mLayout_Mer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(mContext, ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/shopIndex?storeId=" + mLists.get(position).shopId);
                mContext.startActivity(mIntent);
            }
        });


    }
}
