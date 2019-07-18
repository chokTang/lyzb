package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.samecity.SortFoodActivity;
import com.szy.yishopcustomer.Activity.samecity.SortGroupActivity;
import com.szy.yishopcustomer.Activity.samecity.SortMoreActivity;
import com.szy.yishopcustomer.Activity.samecity.SortStoreActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.appMoudle;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author
 * @role
 * @time 2018 14:22
 */

public class CityHomeMenuAdapter extends RecyclerView.Adapter {

    public View.OnClickListener onClickListener;

    public Context mContext;
    public List<appMoudle> mMoudleList = new ArrayList<>();

    public Intent mIntent = null;

    public CityHomeMenuAdapter(Context context, List<appMoudle> moudleList) {
        this.mContext = context;
        this.mMoudleList = moudleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatMenuHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        appMoudle moudle = mMoudleList.get(position);
        bindItemMenuGroup((ItemHoler) holder, moudle);
    }

    @Override
    public int getItemCount() {
        return mMoudleList.size();
    }

    public RecyclerView.ViewHolder creatMenuHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recy_menu, parent, false);
        ItemHoler holder = new ItemHoler(view);
        return holder;
    }

    public class ItemHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.fg_home_menu_img)
        ImageView mImageView_Img;
        @BindView(R.id.fg_home_menu_name)
        TextView mTextView_Name;

        public ItemHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindItemMenuGroup(ItemHoler holder, final appMoudle moudle) {

        GlideApp.with(mContext)
                .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, moudle.imgUrl))
                .error(R.mipmap.img_empty)
                .centerCrop()
                .into(holder.mImageView_Img);

        holder.mTextView_Name.setText(moudle.name);

        holder.mImageView_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int linkType = Integer.parseInt(moudle.linkType);
                if (linkType == 2) {/**分类*/

                    if (moudle.name.equals("商超")) {
                        mIntent = new Intent(mContext, ProjectH5Activity.class);
                        mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/classificationindex?parentId=" + moudle.linkPath);
                        mContext.startActivity(mIntent);
                    } else if (moudle.name.equals("美食")) {//美食

                        if (App.isShowH5){//显示点击同城美食按钮后的h5
                            mIntent = new Intent(mContext, ProjectH5Activity.class);
                            mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/deliciousFoodIndex?cateid=" + moudle.linkPath);
                            mContext.startActivity(mIntent);
                        }else {//进入原生
                            mIntent = new Intent(mContext, SortFoodActivity.class);
                            mIntent.putExtra(SortGroupActivity.GROUP_ID, moudle.linkPath);
                            mContext.startActivity(mIntent);
                        }
                    } else {//既不是美食,也不是商超
                        if (App.isShowH5){//显示H5
                            mIntent = new Intent(mContext, ProjectH5Activity.class);
                            mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL +"/categorys?cateid=" + moudle.linkPath+"&catename="+moudle.name);
                            mContext.startActivity(mIntent);
                        }else {
                            mIntent = new Intent(mContext, SortStoreActivity.class);
                            mIntent.putExtra(SortStoreActivity.SORT_NAME, moudle.name);
                            mIntent.putExtra(SortStoreActivity.SORT_TYPE, moudle.linkPath);
                            mContext.startActivity(mIntent);
                        }

                    }

                } else if (linkType == 4) {/**团购*/
                    mIntent = new Intent(mContext, SortGroupActivity.class);
                    mIntent.putExtra(SortGroupActivity.GROUP_ID, moudle.catgId);
                    mContext.startActivity(mIntent);
                } else if (linkType == 5) {/**外卖*/
                    mIntent = new Intent(mContext, ProjectH5Activity.class);
                    mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/classificationindex");
                    mContext.startActivity(mIntent);
                } else if (linkType == 6) {/**更多*/

//                    mIntent = new Intent(mContext, ProjectH5Activity.class);
//                    mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/more");
//                    mContext.startActivity(mIntent);

                    mIntent = new Intent(mContext, SortMoreActivity.class);
                    mContext.startActivity(mIntent);
                }
            }
        });
    }
}
