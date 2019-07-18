package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.IngotListActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.NearShopMoreActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Activity.samecity.GroupBuyActivity;
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.TemplateModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.appMoudle;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.carouselAdvert;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.middleAdvert;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.GlideUtil;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.json.GsonUtils;
import com.szy.yishopcustomer.View.BannerScroller;
import com.szy.yishopcustomer.View.HeadViewPager;
import com.szy.yishopcustomer.ViewHolder.Index.AdBannerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.IngotsBuyViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.NearShopViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopJoinAdViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopNewJoinViewHolder;
import com.szy.yishopcustomer.ViewHolder.LeftRightTitleViewHolder;
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.szy.yishopcustomer.Activity.NearShopMoreActivity.ADDRESS;
import static com.szy.yishopcustomer.Activity.NearShopMoreActivity.BUNDLE;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_BUNDLE;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_PRODUCT_ID;

/**
 * @author wyx
 * @role 同城生活 首页 adapter
 * @time 2018 10:06
 */

public class CityHomeAdapter extends RecyclerView.Adapter {

    public static final String KEY_SHOP_ID = "shopid";


    /***广告栏**/
    static final int ITEM_BANNER = 1;
    /***我的元宝**/
    static final int ITEM_INGOT = 2;
    /***菜单栏**/
    static final int ITEM_MENU = 3;
    /***推荐广告**/
    static final int ITEM_RECOM_AD = 4;

    /***附近商家模块**/

    public View.OnClickListener mOnClickListener;

    public Context mContext;
    private Intent mIntent = null;

    public String user_ingot;

    public List<carouselAdvert> mCarouselAdvertList = new ArrayList<>();

    public List<appMoudle> mAppMoudleList = new ArrayList<>();

    public List<middleAdvert> mMiddleAdvertList = new ArrayList<>();

    public List<TemplateModel> data = new ArrayList<>();
    public int windowWidth;


    public static final int HEADERCUNMT = 4;//前面有四个固定的model

    HomeIngotsBuyAdapter ingotsBuyAdapter;//元宝购适配器
    HomeShopNewJoinAdapter newJoinAdapter;//最新入驻商家
    HomeShopNetHotAdapter netHotAdapter;//网红商家
    HomeShopLikeAdapter likeAdapter;//最受欢迎商家

    public CityHomeAdapter(Context context) {
        this.mContext = context;
    }

    public ViewType getViewType(int position) {
        TemplateModel item = data.get(position - HEADERCUNMT);
        ViewType viewType = Utils.getTemplateViewType(item.temp_code);
        return viewType;
    }

    /***
     * banner
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder creatBannerViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_banner, parent, false);
        BannerHolder holder = new BannerHolder(view);
        return holder;
    }

    /***
     * 我的元宝
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder createIngotViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_ingot, parent, false);
        IngotHolder holder = new IngotHolder(view);

        return holder;
    }

    /***
     * 菜单栏
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder creatMenuViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_menu, parent, false);
        MenuHolder holder = new MenuHolder(view);
        return holder;
    }

    /***
     * 推荐广告栏
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder creatAdViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_ad, parent, false);
        AdHolder holder = new AdHolder(view);
        return holder;
    }

    /***
     * 推荐商家 title
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder creatMerChantsTitleViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_merchants_title, parent, false);
        MerChantsTitleHolder holder = new MerChantsTitleHolder(view);
        return holder;
    }

    private RecyclerView.ViewHolder createAdBannercenterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_banner_center, parent, false);
        return new AdBannerViewHolder(view);
    }

    /**
     * 元宝换购模块
     *
     * @param parent
     * @return
     */
    private RecyclerView.ViewHolder createIngotsBuyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_home_ingots_buy, parent, false);
        return new IngotsBuyViewHolder(view);
    }

    /**
     * 附近商家模块
     *
     * @param parent
     * @return
     */
    private RecyclerView.ViewHolder createNearShopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_home_near_shop, parent, false);
        return new NearShopViewHolder(view);
    }


    /**
     * 入驻商家广告模块
     *
     * @param parent
     * @return
     */
    private RecyclerView.ViewHolder createShopJoinViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_home_shop_jion_ad, parent, false);
        return new ShopJoinAdViewHolder(view);
    }


    /**
     * 最新入驻商家
     *
     * @param parent
     * @return
     */
    private RecyclerView.ViewHolder createShopNewJoinViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_home_shop_new_join, parent, false);
        return new ShopNewJoinViewHolder(view);
    }

    /***
     * 推荐商家
     * @param parent
     * @return
     */
    public RecyclerView.ViewHolder creatMerChantsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_city_mer_layout, parent, false);
        MerChantsHolder holder = new MerChantsHolder(view);
        return holder;
    }

    public class BannerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_city_banner_viewPager)
        HeadViewPager mHeadViewPager;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class IngotHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_city_ingot)
        TextView mTextView_Ingot;
        @BindView(R.id.tv_city_ingot_number)
        TextView mTextView_IngotNumber;
        @BindView(R.id.tv_city_ingot_look)
        TextView mTextView_IngotLook;


        public IngotHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_city_menu_recyclerView)
        RecyclerView mMenuRecyclerView;

        public MenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class AdHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_city_ad_left)
        ImageView mImageView_Left;
        @BindView(R.id.fragment_city_ad_right)
        ImageView mImageView_Right;
        @BindView(R.id.fragment_city_ad_bottom_left)
        ImageView mImageView_Bottom_Left;
        @BindView(R.id.fragment_city_ad_bottom_right)
        ImageView mImageView_Bottom_Right;

        public AdHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MerChantsTitleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_city_merchants_title)
        TextView mTextView_Title;

        public MerChantsTitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MerChantsHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.fragment_city_mer_recy)
//        CommonRecyclerView mRecyclerView;

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

        @BindView(R.id.fragment_city_mer_acer)
        TextView fragment_city_mer_acer;


        public MerChantsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /***
     * Banner 数据绑定
     * @param holder
     * @param position
     */
    public void bindItemBanner(BannerHolder holder, int position) {

        ViewGroup.LayoutParams layoutParams = holder.mHeadViewPager.getLayoutParams();
        holder.mHeadViewPager.setLayoutParams(layoutParams);
        CityHomeAdBannerAdapter adapter = new CityHomeAdBannerAdapter(mContext, mCarouselAdvertList);
        adapter.itemPosition = position;
        holder.mHeadViewPager.setAdapter(adapter);
    }


    /***
     * 我的元宝 数据绑定
     * @param holder
     */
    public void bindIngotHolder(IngotHolder holder) {

        holder.mTextView_Ingot.setText("我的元宝:");
        holder.mTextView_IngotLook.setText("查看>");

        if (App.getInstance().isLogin()) {
            holder.mTextView_IngotNumber.setText(user_ingot);
        } else {
            holder.mTextView_IngotNumber.setText("0");
        }

        holder.mTextView_IngotLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (App.getInstance().isLogin()) {
                    mContext.startActivity(new Intent(mContext, IngotListActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });
    }

    /***
     * 推荐菜单 数据绑定
     * @param holder
     * @param position
     */
    public void bindItemMenu(MenuHolder holder, int position) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5, GridLayoutManager.VERTICAL, false);
        holder.mMenuRecyclerView.setLayoutManager(gridLayoutManager);

        CityHomeMenuAdapter menuAdapter = new CityHomeMenuAdapter(mContext, mAppMoudleList);
        menuAdapter.onClickListener = mOnClickListener;
        holder.mMenuRecyclerView.setAdapter(menuAdapter);
    }

    /***
     * 推荐广告 数据绑定
     * @param holder
     * @param position
     */
    public void bindItemAd(AdHolder holder, int position) {

        for (int i = 0; i < mMiddleAdvertList.size(); i++) {
            final String linkUrl = mMiddleAdvertList.get(i).linkUrl;
            if (mMiddleAdvertList.get(i).status == 1) {
                switch (mMiddleAdvertList.get(i).orderNo) {
                    case 1:
                        Glide.with(mContext)
                                .load(mMiddleAdvertList.get(i).imageUrl)
                                .into(holder.mImageView_Left);
                        //低价换购
                        holder.mImageView_Left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mIntent = new Intent(mContext, ProjectH5Activity.class);
                                mIntent.putExtra(Key.KEY_URL.getValue(), linkUrl);
                                mContext.startActivity(mIntent);
                            }
                        });
                        break;
                    case 2:
                        Glide.with(mContext)
                                .load(mMiddleAdvertList.get(i).imageUrl)
                                .into(holder.mImageView_Right);


                        //超值推荐
                        holder.mImageView_Right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mIntent = new Intent(mContext, ProjectH5Activity.class);
                                mIntent.putExtra(Key.KEY_URL.getValue(), linkUrl);
                                mContext.startActivity(mIntent);
                            }
                        });
                        break;
                    case 3:
                        Glide.with(mContext)
                                .load(mMiddleAdvertList.get(i).imageUrl)
                                .into(holder.mImageView_Bottom_Left);
                        //新品首发 or 新品尝鲜
                        holder.mImageView_Bottom_Left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mIntent = new Intent(mContext, ProjectH5Activity.class);
                                mIntent.putExtra(Key.KEY_URL.getValue(), linkUrl);
                                mContext.startActivity(mIntent);
                            }
                        });
                        break;
                    case 4:
                        Glide.with(mContext)
                                .load(mMiddleAdvertList.get(i).imageUrl)
                                .into(holder.mImageView_Bottom_Right);
                        //人气排行
                        holder.mImageView_Bottom_Right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mIntent = new Intent(mContext, ProjectH5Activity.class);
                                mIntent.putExtra(Key.KEY_URL.getValue(), linkUrl);
                                mContext.startActivity(mIntent);
                            }
                        });
                        break;
                }
            }
        }
    }

    /***
     * 推荐商家 title 数据绑定
     * @param holder
     * @param position
     */
    public void bindItemMerTitle(MerChantsTitleHolder holder, int position) {
        holder.mTextView_Title.setText("-猜你喜欢-");
    }

//    private CityHomeMersAdapter mersAdapter;

//    /***
//     * 推荐商家 数据绑定
//     * @param holder
//     * @param position
//     */
//    public void bindItemMerChants(final MerChantsHolder holder, final int position) {
//
//        final list item =mLists.get(position);
//
//        if (!TextUtils.isEmpty(item.shopLogo)) {
//            GlideApp.with(mContext)
//                    .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, item.shopLogo))
//                    .error(R.mipmap.image_load_error)
//                    .centerCrop()
//                    .into(holder.mImageView_Img);
//        } else {
//            GlideApp.with(mContext)
//                    .load(R.mipmap.image_load_error)
//                    .centerCrop()
//                    .into(holder.mImageView_Img);
//        }
//
//        holder.mTextView_Name.setText(item.shopName);
//
//
//        if (item.shopDescription == null) {
//            holder.mTextView_Content.setVisibility(View.GONE);
//        } else {
//            holder.mTextView_Content.setVisibility(View.VISIBLE);
//            holder.mTextView_Content.setText(item.shopDescription);
//        }
//
//        holder.mTextView_Price.setText("¥" +item.jibPice);
//        if (item.acer==0){
//            holder.fragment_city_mer_acer.setVisibility(View.GONE);
//        }else {
//            holder.fragment_city_mer_acer.setVisibility(View.VISIBLE);
//            holder.fragment_city_mer_acer.setText("+" + item.acer+"元宝");
//        }
//
//        //元宝抵扣标签  外卖标签 显示和隐藏
//        if ("1".equals(item.acerLabel)) {
//            holder.mLinearLayout_Dedu.setVisibility(View.VISIBLE);
//            holder.mTextView_IngotNumber.setText("元宝最高抵扣:¥" + item.acer);
//        } else {
//            holder.mLinearLayout_Dedu.setVisibility(View.GONE);
//        }
//
//        if (item.takeOutStatus) {
//            holder.mLinearLayout_Takeout.setVisibility(View.VISIBLE);
//            holder.mTextView_Takeout.setText("商家支持外卖");
//        } else {
//            holder.mLinearLayout_Takeout.setVisibility(View.GONE);
//        }
//
//
//        double distance = Math.ceil(item.distance);
//        if (distance > 1000) {
//            //距离 单位换算
//            holder.mTextView_Distance.setText(Utils.toDistance(distance) + "km");
//            holder.mTextView_Distance.setVisibility(View.VISIBLE);
//        } else if (distance == 0) {
//            holder.mTextView_Distance.setVisibility(View.GONE);
//        } else {
//            holder.mTextView_Distance.setText(Utils.toM(distance) + "m");
//            holder.mTextView_Distance.setVisibility(View.VISIBLE);
//        }
//
//
//        if (item.soldNum == null) {
//            holder.mTextView_SaleNumber.setVisibility(View.GONE);
//        } else {
//            holder.mTextView_SaleNumber.setText(item.soldNum);
//            holder.mTextView_SaleNumber.setVisibility(View.VISIBLE);
//        }
//
//        //推荐商家点击
//        holder.mLayout_Mer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (App.isShowH5) {//显示H5
//                    mIntent = new Intent(mContext, ProjectH5Activity.class);
//                    mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/shopIndex?storeId=" + item.shopId);
//                    mContext.startActivity(mIntent);
//                } else {//显示原生
//                    Intent intent = null;
//                    LogUtils.Companion.e("itemshopId"+item.shopId);
//                            if (item.saleSkip<=0) {//不是外卖
//                                intent = new Intent(mContext, ShopDetailActivity.class);
//                                intent.putExtra(KEY_SHOP_ID, String.valueOf(item.shopId));
//                            } else {//外卖
//                                intent = new Intent(mContext, ProjectH5Activity.class);
//                                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + item.shopId );
//                            }
//                            mContext.startActivity(intent);
//
////                    mIntent = new Intent(mContext, ShopDetailActivity.class);
////                    mIntent.putExtra(KEY_SHOP_ID, String.valueOf(item.shopId));
////                    mContext.startActivity(mIntent);
//                }
//
//            }
//        });
//
//    }

    /***
     * ITEM 布局切换
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != ITEM_BANNER && viewType != ITEM_INGOT && viewType != ITEM_MENU && viewType != ITEM_RECOM_AD
                ) {//除开前面四个固定m
            switch (ViewType.valueOf(viewType)) {
//                case VIEW_LEFT_RIGHT_TITLE://左右都有字的title
//                    return createLeftRightTitleViewHolder(parent);
                /**元宝换购上面的广告哦**/
                case VIEW_TYPE_AD_ADVERT:
                    return createAdBannercenterViewHolder(parent);
                case VIEW_TYPE_INGOTS_BUY:
                    /** 元宝换购 */
                    return createIngotsBuyViewHolder(parent);
                case VIEW_TYPE_NEAR_SHOP:
                    /** 附近商家 */
                    return createNearShopViewHolder(parent);
                case VIEW_TYPE_SHOP_JION:
                    /** 入驻商家广告模块 */
                    return createShopJoinViewHolder(parent);
                /** 最新入驻商家 */
                /** 网红商家   最受欢迎 和最新入驻模板一样都是上面一个title 下面一个recycle*/
                case VIEW_TYPE_SHOP_NEW_JION://最新入驻
                case VIEW_TYPE_NET_HOT_SHOP://网红
                case VIEW_TYPE_LIKE_SHOP://最受欢迎
                    return createShopNewJoinViewHolder(parent);

            }
        } else {
            switch (viewType) {
                case ITEM_BANNER:
                    return creatBannerViewHolder(parent);

                case ITEM_INGOT:
                    return createIngotViewHolder(parent);

                case ITEM_MENU:
                    return creatMenuViewHolder(parent);

                case ITEM_RECOM_AD:
                    return creatAdViewHolder(parent);


            }

        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position < HEADERCUNMT) {
            switch (getItemViewType(position)) {
                case ITEM_BANNER:
                    bindItemBanner((BannerHolder) holder, position);
                    break;
                case ITEM_INGOT:
                    bindIngotHolder((IngotHolder) holder);
                    break;
                case ITEM_MENU:
                    bindItemMenu((MenuHolder) holder, position);
                    break;
                case ITEM_RECOM_AD:
                    bindItemAd((AdHolder) holder, position);
                    break;
            }
        } else {
            ViewType viewType = getViewType(position);
            int mposition = position - HEADERCUNMT;
            switch (viewType) {
                //左边右边都有字的title
//                case VIEW_LEFT_RIGHT_TITLE:
//                    setLeftRightTitleViewHolder((LeftRightTitleViewHolder) holder, "元宝换购", "更多");
//                    break;
                //元宝换购上面的广告位
                case VIEW_TYPE_AD_ADVERT:
                    setUpAdBannerADViewHolder((AdBannerViewHolder) holder, mposition);
                    break;
                //元宝换购
                case VIEW_TYPE_INGOTS_BUY:
                    setIngotsBuyViewHolder((IngotsBuyViewHolder) holder, mposition);
                    break;
                //附近商家
                case VIEW_TYPE_NEAR_SHOP:
                    setNearShopViewHolder((NearShopViewHolder) holder, mposition);
                    break;
                //商家入驻广告
                case VIEW_TYPE_SHOP_JION:
                    setShopJoinViewHolder((ShopJoinAdViewHolder) holder, mposition);
                    break;
                //最新入驻 商家
                case VIEW_TYPE_SHOP_NEW_JION:
                    setShopNewJoinViewHolder((ShopNewJoinViewHolder) holder, mposition);
                    break;
                //本周网红商家1 ,本周最受欢迎商家2,
                case VIEW_TYPE_NET_HOT_SHOP:
                    setNetHotShopViewHolder((ShopNewJoinViewHolder) holder, mposition, 1);
                    break;
                case VIEW_TYPE_LIKE_SHOP:
                    setNetHotShopViewHolder((ShopNewJoinViewHolder) holder, mposition, 2);
                    break;

            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + HEADERCUNMT;
    }

    @Override
    public int getItemViewType(int position) {
        //0-4对应五个模块  默认为第五个模块也就是推荐商家
        if (position < HEADERCUNMT) {
            switch (position) {
                case 0:
                    return ITEM_BANNER;
                case 1:
                    return ITEM_INGOT;
                case 2:
                    return ITEM_MENU;
                case 3:
                    return ITEM_RECOM_AD;
            }
        } else {
            int viewType = getViewType(position).ordinal();
            return viewType;
        }

        return 0;
    }


    private void setLeftRightTitleViewHolder(LeftRightTitleViewHolder viewHolder, String left, String right) {
        viewHolder.tv_left_title.setText(left);
        viewHolder.tv_right_title.setText(right);
    }


    /**
     * 配置banner广告数据
     *
     * @param viewHolder
     * @param position
     */
    private void setUpAdBannerADViewHolder(final AdBannerViewHolder viewHolder, final int position) {

        viewHolder.tvMyIngots.setVisibility(View.GONE);


        //设置viewpager 滑动时间
        BannerScroller scroller = new BannerScroller(mContext);
        scroller.setTime(1500);
        scroller.initViewPagerScroll(viewHolder.viewPager);


        final TemplateModel dataModel = data.get(position);


        if (!Utils.isNull(dataModel.data)) {
            final List<HomeShopAndProductBean.AdvertBean> list = GsonUtils.Companion.toList(dataModel.data, HomeShopAndProductBean.AdvertBean.class);
            if (list == null || list.size() == 0) {
                return;
            }
            if (list.size() == 1) {
                viewHolder.pageIndicator.setVisibility(View.GONE);
            } else {
                viewHolder.pageIndicator.setVisibility(View.VISIBLE);
            }

            //获取第一张的宽高
            int height = 0;
            float imageWidth = Float.valueOf(list.get(0).getWidth());
            float imageHeight = Float.valueOf(list.get(0).getHeight());
            float realHeight = windowWidth * imageHeight / imageWidth;
            height = (int) realHeight;
            if (height <= 0) {
                //如果没有宽高，默认使用第一张图片的宽高比
                height = (int) (windowWidth * 0.31f);
            }

            ViewGroup.LayoutParams layoutParams = viewHolder.viewPager.getLayoutParams();
            layoutParams.height = height;
            viewHolder.viewPager.setLayoutParams(layoutParams);
            //设置首页banner 数据
            IndexSameHomeAdverBannerAdapter adapter = new IndexSameHomeAdverBannerAdapter(list);
            viewHolder.viewPager.setAdapter(adapter);

            adapter.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int extraInfo = Utils.getExtraInfoOfTag(v);
                    if (!TextUtils.isEmpty(list.get(extraInfo).getLinkUrl())) {
                        new BrowserUrlManager(list.get(extraInfo).getLinkUrl()).parseUrl(viewHolder.itemView.getContext(), list.get(extraInfo).getLinkUrl());
                    } else {
                        if (Config.DEBUG) {
                            Toast.makeText(viewHolder.itemView.getContext(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };


            viewHolder.pageIndicator.setViewPager(viewHolder.viewPager);
            viewHolder.pageIndicator.notifyDataSetChanged();
            viewHolder.pageIndicator.setSnap(true);
        }
    }


    /**
     * 元宝换购模块数据绑定
     *
     * @param viewHolder
     * @param position
     */
    private void setIngotsBuyViewHolder(final IngotsBuyViewHolder viewHolder, final int position) {

        List<HomeShopAndProductBean.RepurchaseBean> list = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.RepurchaseBean.class);
        GridLayoutManager manager = new GridLayoutManager(viewHolder.itemView.getContext(), 3);
        viewHolder.recyclerViewIngotsBuy.setLayoutManager(manager);

        if (ingotsBuyAdapter == null) {
            ingotsBuyAdapter = new HomeIngotsBuyAdapter(R.layout.item_item_home_ingots_buy);
            viewHolder.recyclerViewIngotsBuy.setAdapter(ingotsBuyAdapter);
        }
        viewHolder.recyclerViewIngotsBuy.setHasFixedSize(true);
        viewHolder.recyclerViewIngotsBuy.setNestedScrollingEnabled(false);
        ingotsBuyAdapter.setNewData(list);

        ingotsBuyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HomeShopAndProductBean.RepurchaseBean bean = (HomeShopAndProductBean.RepurchaseBean) adapter.getItem(position);
                if (null == bean) {
                    return;
                }

                Intent intent = null;
                switch (bean.getLocal_service()) {//跳转店铺详情(点击的产品的话 就会传catid)
                    case "0"://非同城商家 店铺
                        intent = new Intent();
                        intent.putExtra(Key.KEY_SHOP_ID.getValue(), bean.getShop_id());
                        intent.setClass(viewHolder.itemView.getContext(), ShopActivity.class);
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                    case "1"://同城商家  店铺
                        if (bean.getIs_sale().equals("10001")) {//团购10001
                            intent = new Intent(viewHolder.itemView.getContext(), ShopDetailActivity.class);
                            intent.putExtra(KEY_SHOP_ID, bean.getShop_id());
                        } else {//外卖10002
                            intent = new Intent(viewHolder.itemView.getContext(), ProjectH5Activity.class);
                            intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.getShop_id() + "&catId=" + bean.getCat_id());
                        }
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                }
            }
        });

        ingotsBuyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeShopAndProductBean.RepurchaseBean bean = (HomeShopAndProductBean.RepurchaseBean) adapter.getItem(position);
                if (null == bean) {
                    return;
                }
                Intent intent = null;
                switch (bean.getLocal_service()) {//跳转商品页面
                    case "0"://非同城商家
                        intent = new Intent();
                        intent.putExtra(Key.KEY_GOODS_ID.getValue(), bean.getGoods_id());
                        intent.setClass(viewHolder.itemView.getContext(), GoodsActivity.class);
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                    case "1"://同城商家
                        if (bean.getIs_sale().equals("10001")) {//团购10001
                            intent = new Intent(viewHolder.itemView.getContext(), GroupBuyActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(KEY_SHOP_ID, bean.getShop_id());
                            intent.putExtra(KEY_BUNDLE, bundle);
                            intent.putExtra(KEY_PRODUCT_ID, bean.getGoods_id());
                        } else {//外卖10002
                            intent = new Intent(viewHolder.itemView.getContext(), ProjectH5Activity.class);
                            intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.getShop_id() + "&catId=" + bean.getCat_id());
                        }
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                }
            }
        });


        Utils.setViewTypeForTag(viewHolder.tv_right_title, ViewType.VIEW_TYPE_INGOTS_BUY_MORE);
        Utils.setPositionForTag(viewHolder.tv_right_title, position);
        viewHolder.tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(viewHolder.itemView.getContext(), YSCWebViewActivity.class);
                if (App.getInstance().city != null) {//定位成功
                    mIntent.putExtra(Key.KEY_URL.getValue(), Config.BASE_URL + "/guess/repurchase?latitude=" + App.getInstance().lat
                            + "&longitude=" + App.getInstance().lng + "&regionCode=" + App.getInstance().city_code);
                } else {//定位失败选择的城市
                    mIntent.putExtra(Key.KEY_URL.getValue(), Config.BASE_URL + "/guess/repurchase?regionCode=" + App.getInstance().city_code);
                }
                viewHolder.itemView.getContext().startActivity(mIntent);
            }
        });


    }

    /**
     * 附近商家模块数据绑定
     *
     * @param viewHolder
     * @param position
     */
    private void setNearShopViewHolder(final NearShopViewHolder viewHolder, final int position) {

        List<HomeShopAndProductBean.NearbyBean> list = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.NearbyBean.class);

        viewHolder.shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeShopAndProductBean.NearbyBean bean = (HomeShopAndProductBean.NearbyBean) adapter.getItem(position);
                if (null == bean) {
                    return;
                }
                Intent intent = null;
                switch (bean.getLocal_service()) {//跳转店铺详情 (点击的产品的话 就会传catid)
                    case "0"://非同城商家 店铺
                        intent = new Intent();
                        intent.putExtra(Key.KEY_SHOP_ID.getValue(), bean.getShop_id());
                        intent.setClass(viewHolder.itemView.getContext(), ShopActivity.class);
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                    case "1"://同城商家  店铺
                        if (bean.getTake_out_status().equals("0")) {//非外卖
                            intent = new Intent(viewHolder.itemView.getContext(), ShopDetailActivity.class);
                            intent.putExtra(KEY_SHOP_ID, bean.getShop_id());
                        } else {//外卖1
                            intent = new Intent(viewHolder.itemView.getContext(), ProjectH5Activity.class);
                            intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.getShop_id());
                        }
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                }
            }
        });

        viewHolder.recyclerViewNearShop.setHasFixedSize(true);
        viewHolder.recyclerViewNearShop.setNestedScrollingEnabled(false);

        Utils.setViewTypeForTag(viewHolder.tv_right_title, ViewType.VIEW_TYPE_NEAR_SHOP_MORE);
        Utils.setPositionForTag(viewHolder.tv_right_title, position);
        viewHolder.tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                if (App.getInstance().clickChangeCity) {//选择城市
                    bundle.putString(ADDRESS, App.getInstance().city_name);
                } else {//未选择城市(定位)
                    if (App.getInstance().city != null) {
                        bundle.putString(ADDRESS, App.getInstance().addressDetail);
                    }
                }
                intent.putExtra(BUNDLE, bundle);
                intent.setClass(viewHolder.itemView.getContext(), NearShopMoreActivity.class);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
        viewHolder.shopAdapter.setNewData(list);
    }


    /**
     * 商家入驻广告模块数据绑定
     *
     * @param viewHolder
     * @param position
     */
    private void setShopJoinViewHolder(final ShopJoinAdViewHolder viewHolder, final int position) {
        List<HomeShopAndProductBean.ShopNumBean> list = null;
        if (TextUtils.isEmpty(data.get(position).data)) {
            return;
        }
        list = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.ShopNumBean.class);
        if (list.get(0).getMerchantNumStatus().equals("1")) {//1显示
            viewHolder.tvShopJoinCount.setVisibility(View.VISIBLE);
            viewHolder.tvShopJoinCount.setText(list.get(0).getShopNum());
        } else {//0不显示
            viewHolder.tvShopJoinCount.setVisibility(View.GONE);
        }
        viewHolder.tv_text_shop_num.setText(list.get(0).getShopTitle());
        Glide.with(viewHolder.itemView.getContext())
                .load(Utils.setImgNetUrl("", list.get(0).getImageUrl()))
                .thumbnail(0.2f)
                .apply(GlideUtil.RadioOptions(5))
                .into(new ViewTarget<View, Drawable>(viewHolder.rl_bg_shop_join_ad) {
                          @Override
                          public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                              this.view.setBackground(resource.getCurrent());
                          }
                      }
                );

        final List<HomeShopAndProductBean.ShopNumBean> finalList = list;
        viewHolder.rl_bg_shop_join_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(finalList.get(0).getLinkUrl())) {
                    return;
                }
                Intent intent = new Intent(viewHolder.itemView.getContext(), YSCWebViewActivity.class);
                intent.putExtra(Key.KEY_URL.getValue(), finalList.get(0).getLinkUrl());
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

    }

    /**
     * 最新入驻模块数据绑定
     *
     * @param viewHolder
     * @param position
     */
    private void setShopNewJoinViewHolder(final ShopNewJoinViewHolder viewHolder, final int position) {
        viewHolder.tvTitle.setBackgroundResource(R.mipmap.bg_new_join_title);
        List<HomeShopAndProductBean.ShopNewBean> list = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.ShopNewBean.class);

        LinearLayoutManager manager = new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        viewHolder.recyclerView.setLayoutManager(manager);

        if (newJoinAdapter == null) {
            newJoinAdapter = new HomeShopNewJoinAdapter(R.layout.item_home_product_pic_name);
            viewHolder.recyclerView.setAdapter(newJoinAdapter);
        }

        newJoinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeShopAndProductBean.ShopNewBean bean = (HomeShopAndProductBean.ShopNewBean) adapter.getItem(position);
                if (null == bean) {
                    return;
                }
                Intent intent = null;
                switch (bean.getLocal_service()) {//跳转店铺详情
                    case "0"://非同城商家 店铺
                        intent = new Intent();
                        intent.putExtra(Key.KEY_SHOP_ID.getValue(), bean.getShop_id());
                        intent.setClass(viewHolder.itemView.getContext(), ShopActivity.class);
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                    case "1"://同城商家  店铺
                        if (bean.getTake_out_status().equals("0")) {//非外卖
                            intent = new Intent(viewHolder.itemView.getContext(), ShopDetailActivity.class);
                            intent.putExtra(KEY_SHOP_ID, bean.getShop_id());
                        } else {//外卖1
                            intent = new Intent(viewHolder.itemView.getContext(), ProjectH5Activity.class);
                            intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.getShop_id());
                        }
                        viewHolder.itemView.getContext().startActivity(intent);
                        break;
                }
            }
        });

        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setNestedScrollingEnabled(false);
        newJoinAdapter.setNewData(list);
    }

    /**
     * 本周网红模块数据绑定
     * 本周最瘦欢迎商家
     *
     * @param type       1 网红 2 最瘦欢迎
     * @param viewHolder
     * @param position
     */
    private void setNetHotShopViewHolder(final ShopNewJoinViewHolder viewHolder, final int position, int type) {
        //区分 数据源type区分适配数据
        if (type == 1) {
            viewHolder.tvTitle.setBackgroundResource(R.mipmap.bg_nethot_title);
        } else {
            viewHolder.tvTitle.setBackgroundResource(R.mipmap.bg_like_title);
        }
        List<HomeShopAndProductBean.ShopHotBean> list1 = null;
        List<HomeShopAndProductBean.ShopLikeBean> list2 = null;
        if (type == 1) {
            list1 = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.ShopHotBean.class);
        } else {
            list2 = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.ShopLikeBean.class);
        }

        LinearLayoutManager manager = new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        viewHolder.recyclerView.setLayoutManager(manager);

        if (type == 1) {
            if (netHotAdapter == null) {
                netHotAdapter = new HomeShopNetHotAdapter(R.layout.item_home_product_pic_name_see);
                viewHolder.recyclerView.setAdapter(netHotAdapter);
            }

            netHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    HomeShopAndProductBean.ShopHotBean bean = (HomeShopAndProductBean.ShopHotBean) adapter.getItem(position);
                    if (null == bean) {
                        return;
                    }
                    Intent intent = null;
                    switch (bean.getLocal_service()) {//跳转店铺详情
                        case "0"://非同城商家 店铺
                            intent = new Intent();
                            intent.putExtra(Key.KEY_SHOP_ID.getValue(), bean.getShop_id());
                            intent.setClass(viewHolder.itemView.getContext(), ShopActivity.class);
                            viewHolder.itemView.getContext().startActivity(intent);
                            break;
                        case "1"://同城商家  店铺
                            if (bean.getTake_out_status().equals("0")) {//非外卖
                                intent = new Intent(viewHolder.itemView.getContext(), ShopDetailActivity.class);
                                intent.putExtra(KEY_SHOP_ID, bean.getShop_id());
                            } else {//外卖1
                                intent = new Intent(viewHolder.itemView.getContext(), ProjectH5Activity.class);
                                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.getShop_id());
                            }
                            viewHolder.itemView.getContext().startActivity(intent);
                            break;
                    }
                }
            });
        } else {
            if (likeAdapter == null) {
                likeAdapter = new HomeShopLikeAdapter(R.layout.item_home_product_pic_name_see);
                viewHolder.recyclerView.setAdapter(likeAdapter);
            }

            likeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    HomeShopAndProductBean.ShopLikeBean bean = (HomeShopAndProductBean.ShopLikeBean) adapter.getItem(position);
                    if (null == bean) {
                        return;
                    }
                    Intent intent = null;
                    switch (bean.getLocal_service()) {//跳转店铺详情
                        case "0"://非同城商家 店铺
                            intent = new Intent();
                            intent.putExtra(Key.KEY_SHOP_ID.getValue(), bean.getShop_id());
                            intent.setClass(viewHolder.itemView.getContext(), ShopActivity.class);
                            viewHolder.itemView.getContext().startActivity(intent);
                            break;
                        case "1"://同城商家  店铺
                            if (bean.getTake_out_status().equals("0")) {//非外卖
                                intent = new Intent(viewHolder.itemView.getContext(), ShopDetailActivity.class);
                                intent.putExtra(KEY_SHOP_ID, bean.getShop_id());
                            } else {//外卖1
                                intent = new Intent(viewHolder.itemView.getContext(), ProjectH5Activity.class);
                                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.getShop_id());
                            }
                            viewHolder.itemView.getContext().startActivity(intent);
                            break;
                    }
                }
            });

        }
        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setNestedScrollingEnabled(false);
        if (type == 1) {
            netHotAdapter.setNewData(list1);
        } else {
            likeAdapter.setNewData(list2);

        }
    }


}
