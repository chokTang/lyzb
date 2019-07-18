package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.szy.common.View.SquareImageView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotGoodModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我的元宝-低价换购+超值推荐 adapter
 * @time 2018 2018/7/6 13:59
 */

public class IngotGoodDataAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<IngotGoodModel> mGoodModels;

    public IngotGoodDataAdapter(Context context) {
        this.mContext = context;
        mGoodModels = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_good, parent, false);
        DataHolder holder = new DataHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int windowWidth = Utils.getWindowWidth(holder.itemView.getContext());
        int imageWidth = (int) (windowWidth / 2.5);
        if (imageWidth > 350) {
            imageWidth = 350;
        }

        IngotGoodModel model = mGoodModels.get(position);
        bindData((DataHolder) holder, imageWidth, model);
    }

    @Override
    public int getItemCount() {
        return mGoodModels == null ? 0 : mGoodModels.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rela_ingot_good)
        RelativeLayout mLayout;
        @BindView(R.id.img_ingot_good_img)
        SquareImageView mImageView_Img;
        @BindView(R.id.tv_ingot_good_name)
        TextView mTextView_Name;
        @BindView(R.id.tv_ingot_good_price)
        TextView mTextView_Price;
        @BindView(R.id.tv_ingot_good_ing)
        TextView mTextView_Ing;


        public DataHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindData(DataHolder holder, int width, final IngotGoodModel model) {

        GlideApp.with(mContext)
                .load(Api.API_HEAD_IMG_URL + model.goods_image)
                .override(width, width)
                .error(R.mipmap.bg_public)
                .into(holder.mImageView_Img);

        holder.mTextView_Name.setText(model.goods_name);
        holder.mTextView_Price.setText(Utils.formatMoney(mContext, model.goods_price));
        holder.mTextView_Ing.setText("元宝最高抵扣:" + Utils.formatMoney(holder.mTextView_Ing.getContext(), model.max_integral_num));

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, GoodsActivity.class);
                intent.putExtra(Key.KEY_GOODS_ID.getValue(), model.goods_id);
                mContext.startActivity(intent);
            }
        });
    }
}
