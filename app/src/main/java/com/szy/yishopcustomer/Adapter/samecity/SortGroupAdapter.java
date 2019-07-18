package com.szy.yishopcustomer.Adapter.samecity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.samecity.GroupBuyActivity;
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Sort.SortGroupModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_BUNDLE;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_PRODUCT_ID;
import static com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID;


/**
 * @author wyx
 * @role 分类 团购列表 adapter
 * @time
 */

public class SortGroupAdapter extends RecyclerView.Adapter {

    public Context mContext;
    public List<SortGroupModel> listModels;

    public SortGroupAdapter(Context context) {
        this.mContext = context;
        listModels = new ArrayList<>();
    }

    public RecyclerView.ViewHolder creatGroupViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_group, parent, false);
        GroupHolder holder = new GroupHolder(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatGroupViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SortGroupModel model = listModels.get(position);
        bindGroupList((GroupHolder) holder, model);
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }


    public class GroupHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rale_sort_group_list)
        RelativeLayout mLayout_Item;

        @BindView(R.id.lin_sort_group_shop_info)
        LinearLayout mLayout_ShopInfo;
        @BindView(R.id.tv_sort_group_shop_name)
        TextView mTextView_ShopName;
        @BindView(R.id.img_sort_group_good_img)
        ImageView mImageView_GoodImg;

        @BindView(R.id.tv_sort_group_good_title)
        TextView mTextView_Title;
        @BindView(R.id.tv_sort_group_good_type)
        TextView mTextView_ShopType;
        @BindView(R.id.tv_sort_group_price)
        TextView mTextView_Price;
        @BindView(R.id.tv_sort_group_marke_price)
        TextView mTextView_MarkePrice;


        @BindView(R.id.img_sort_group_dedu_img)
        ImageView mImageView_DeduImg;
        @BindView(R.id.tv_sort_group_dedu_text)
        TextView mTextView_DeduText;

        @BindView(R.id.tv_sort_group_distance)
        TextView mTextView_Distance;

        public GroupHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindGroupList(final GroupHolder holder, final SortGroupModel model) {

        if (TextUtils.isEmpty(model.prodLogo)) {
            GlideApp.with(mContext)
                    .load(R.mipmap.img_empty)
                    .centerCrop()
                    .into(holder.mImageView_GoodImg);
        } else {
            GlideApp.with(mContext)
                    .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, model.prodLogo))
                    .centerCrop()
                    .into(holder.mImageView_GoodImg);
        }

        holder.mTextView_ShopName.setText(model.shopName);

        holder.mTextView_Title.setText(model.prodName);

        if (!TextUtils.isEmpty(model.prodDesc)) {

            try {
                JSONObject object = JSONObject.parseObject(model.prodDesc);
                String desc = object.getString("desc");
                holder.mTextView_ShopType.setText(desc);
            } catch (Exception ex) {
                holder.mTextView_ShopType.setText(model.prodDesc);
                ex.printStackTrace();
            }
        }

        holder.mTextView_Price.setText("¥" + model.jcPrice);
        holder.mTextView_MarkePrice.setText(Utils.spanStrickhrough(mContext, "¥" + model.marketPrice));

        if (model.acer == 0) {
            holder.mImageView_DeduImg.setVisibility(View.GONE);
            holder.mTextView_DeduText.setVisibility(View.GONE);
        } else {
            holder.mTextView_DeduText.setText("元宝最高抵扣:¥" + model.acer);
        }

        if (model.distance > 1000) {
            holder.mTextView_Distance.setText(Utils.toDistance(model.distance) + "km");
        } else {
            holder.mTextView_Distance.setText(Utils.toM(model.distance) + "m");
        }

        holder.mLayout_ShopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.isShowH5){//显示H5
                    Intent intent = new Intent(mContext, ProjectH5Activity.class);
                    intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/shopIndex?storeId=" + model.storeId);
                    mContext.startActivity(intent);
                }else {//显示原生
                    Intent mIntent = new Intent(mContext, ShopDetailActivity.class);
                    mIntent.putExtra(KEY_SHOP_ID, model.storeId);
                    mContext.startActivity(mIntent);
                }

            }
        });

        holder.mLayout_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.isShowH5){//显示H5
                    Intent intent = new Intent(mContext, ProjectH5Activity.class);
                    intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/groupDetailIndex?goodsId=" + model.prodId
                            + "&storeName=" + model.shopName + "&storeId=" + model.storeId + "&goodsName=" + model.prodName);
                    mContext.startActivity(intent);
                }else {//显示原生
                    Intent intent = new Intent(mContext, GroupBuyActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putString(KEY_SHOP_ID, model.storeId);
                    intent.putExtra(KEY_PRODUCT_ID,model.prodId).putExtra(KEY_BUNDLE, bundle);
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
