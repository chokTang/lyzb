package com.szy.yishopcustomer.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyzb.jbx.R;
import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.VideoActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Activity.samecity.GroupBuyActivity;
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnUserIngotNumberViewListener;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdTitleDataModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdTitleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.BlankDataModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsTitleDataModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsTitleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NearShopItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NearShopModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NoticeModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.TemplateModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.VideoModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.GlideUtil;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.json.GsonUtils;
import com.szy.yishopcustomer.View.BannerScroller;
import com.szy.yishopcustomer.View.IndexAdColumnLayoutManager;
import com.szy.yishopcustomer.View.MarqueeView;
import com.szy.yishopcustomer.View.MyItemDecoration;
import com.szy.yishopcustomer.ViewHolder.Index.AdBannerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.AdColumnViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.AdTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ArticleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.BlankLineViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.DummyViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsColumnViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsListTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsPromotionViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.IndexEmptyViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.IngotsBuyViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.LoadDisabledViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.NavigationViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.NearShopTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.NearShopViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.NoticeViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopJoinAdViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopListTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopListViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopNewJoinViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopStreetViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.VideoShowViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.VideoViewHolder;
import com.szy.yishopcustomer.ViewHolder.LeftRightTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.LoadingItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserGuessTitleViewHolder;
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean;

import java.util.ArrayList;
import java.util.List;

import static com.szy.yishopcustomer.Activity.VideoActivity.KEY_VIDEO;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_BUNDLE;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_PRODUCT_ID;
import static com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID;
import static com.szy.yishopcustomer.Constant.Api.API_HEAD_IMG_URL;

/**
 * Created by liuzhifeng on 2017/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexAdapter extends RecyclerView.Adapter {
    private static final String TAG = "IndexAdapter";
    public List<TemplateModel> data;
    public View.OnClickListener onClickListener;
    public Context context;
    public int windowWidth;
    private ImageView mImageView;
    IndexGuessLikeAdapter adapter;//猜你喜欢适配器
    HomeIngotsBuyAdapter ingotsBuyAdapter;//元宝购适配器
    HomeShopNewJoinAdapter newJoinAdapter;//最新入驻商家
    HomeShopNetHotAdapter netHotAdapter;//网红商家
    HomeShopLikeAdapter likeAdapter;//最受欢迎商家
    HomeVideoShowAdapter videoShowAdapter;


    public String ingots;//元宝数量

    public void noMoreViewIsVise(boolean isShow) {
        if (mImageView != null) {
            mImageView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 刷新元宝数量
     *
     * @param ingots
     */
    public void refreshInGots(String ingots) {
        this.ingots = ingots;
    }

    public IndexAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        this.notifyDataSetChanged();
    }

    public ViewType getLastItemViewType() {
        return getViewType(data.size() - 1);
    }

    public ViewType getViewType(int position) {
        TemplateModel item = data.get(position);
        ViewType viewType = Utils.getTemplateViewType(item.temp_code);
        return viewType;
    }

    public void insertAtLastPosition(TemplateModel templateModel) {
        data.add(templateModel);
        notifyItemRangeChanged(data.size() - 1, 1);
    }

    public void insertAtLastPosition(TemplateModel templateModel, int postion) {
        data.add(postion, templateModel);
        notifyItemChanged(postion);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_SHOP_LIST_DUMMY:
                return createshopListDummyViewHolder(parent);
            case VIEW_TYPE_LOADING:
                return createLoadingViewHolder(parent);

//            /** 我是有底线的 item **/
//            case VIEW_TYPE_LOAD_DISABLED:
//                return createLoadingDisabledViewHolder(parent);

            case VIEW_TYPE_GOODS_LIST_TITLE:
                return createGoodsListTitleViewHolder(parent);
            case VIEW_TYPE_GOODS_DUMMY_TITLE:
                return createGoodsDummyTitleViewHolder(parent);
            case VIEW_TYPE_GOODS_LIST:
                return createGoodsListViewHolder(parent);
            case VIEW_TYPE_SHOP_LIST:
                return createShopListViewHolder(parent);
            case VIEW_TYPE_SHOP_LIST_TITLE:
                return createShopListTitleViewHolder(parent);
            case VIEW_TYPE_AD_COLUMN://
                return createAdColumnViewHolder(parent);
            case VIEW_TYPE_AD_COLUMN_THREE://03(04)  3个选择项的产品推荐
                return createAdColumnViewHolder(parent);
            case VIEW_TYPE_AD_COLUMN_FOUR:
                return createAdColumnFourViewHolder(parent);
            case VIEW_TYPE_AD_BANNER:
                return createAdBannerViewHolder(parent);
            case VIEW_TYPE_NAVIGATION:
                return createNavigationViewHolder(parent);
            case VIEW_TYPE_NAVIGATION_FIVE://
                return createNavigationViewHolder(parent);
            case VIEW_TYPE_ARTICLE:
                return createArticleViewHolder(parent);
            case VIEW_TYPE_NOTICE:
                return createNoticeViewHolder(parent);
            case VIEW_TYPE_GOODS_PROMOTION:
                return createGoodsPromotionViewHolder(parent);
            case VIEW_TYPE_TWO_COLUMN_GOODS:
                return createTwoColumnGoodsViewHolder(parent);
            case VIEW_TYPE_THREE_COLUMN_GOODS:
                return createThreeColumnGoodsViewHolder(parent);
            case VIEW_TYPE_SHOP_STREET:
                return createShopStreetViewHolder(parent);
            case VIEW_TYPE_BLANK://"新人推荐"字上面那一行分割线
                return createBlankLineViewHolder(parent);
            case VIEW_TYPE_AD_TITLE:
                return createAdTitleViewHolder(parent);
            case VIEW_TYPE_AD_TITLE_TWO:// 猜你喜欢 标题样式
                return createAdTitleTwoViewHolder(parent);
            case VIEW_TYPE_GOODS_TITLE://05  更多那一行
                return createGoodsTitleViewHolder(parent);
            case VIEW_TYPE_VIDEO:
                return createVideoViewHolder(parent);
            case GUESS_LIKE_TITLE:
                return createGuessLikeTitleViewHolder(parent);
            case VIEW_INDEX_GUESS_LIKE:
                /** 猜你喜欢 */
                return createGuessLikdeViewHolder(parent);
            case VIEW_LEFT_RIGHT_TITLE://左右都有字的title
                return createLeftRightTitleViewHolder(parent);
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
            case VIEW_HOME_VIDEO_SHOW: /**视频模块*/
                return createVideoShowViewHolder(parent);

        }
        return createEmptyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewType viewType = getViewType(position);
        switch (viewType) {
            case VIEW_TYPE_GOODS_DUMMY_TITLE:
                setUpDummyTitleViewHolder((NearShopTitleViewHolder) holder, position);
                break;
            case VIEW_TYPE_SHOP_LIST_DUMMY:
                setUpDummyViewHolder((DummyViewHolder) holder, position);
                break;

//            /** 我是有底线的 item **/
//            case VIEW_TYPE_LOAD_DISABLED:
//                setUpLoadDisabledViewHolder((LoadDisabledViewHolder) holder, position);
//                break;

            case VIEW_TYPE_AD_COLUMN:// 02(06) 广告位加载
                setUpAdColumnViewHolder((AdColumnViewHolder) holder, position);
                break;
            case VIEW_TYPE_BLANK://"新人推荐"字上面那一行分割线
                setUpBlankViewHolder((BlankLineViewHolder) holder, position);
                break;
            case VIEW_TYPE_AD_COLUMN_THREE://03(04)  3个选择项的产品推荐
                setUpAdColumnThreeViewHolder((AdColumnViewHolder) holder, position);
                break;
            case VIEW_TYPE_AD_COLUMN_FOUR:
                setUpAdColumnFourViewHolder((AdColumnViewHolder) holder, position);
                break;
            case VIEW_TYPE_NAVIGATION:
                setUpNavigationViewHolder((NavigationViewHolder) holder, position);
                break;
            case VIEW_TYPE_NAVIGATION_FIVE:
                setUpNavigationFiveViewHolder((NavigationViewHolder) holder, position);
                break;
            case VIEW_TYPE_ARTICLE:
                setUpArticleViewHolder((ArticleViewHolder) holder, position);
                break;
            case VIEW_TYPE_NOTICE:
                setUpNoticeViewHolder((NoticeViewHolder) holder, position);
                break;
            case VIEW_TYPE_GOODS_PROMOTION:
                setUpGoodsPromotionViewHolder((GoodsPromotionViewHolder) holder, position);
                break;
            case VIEW_TYPE_TWO_COLUMN_GOODS:
                setUpTwoColumnGoodsViewHolder((GoodsColumnViewHolder) holder, position);
                break;
            case VIEW_TYPE_THREE_COLUMN_GOODS:
                setUpThreeColumnGoodsViewHolder((GoodsColumnViewHolder) holder, position);
                break;
            case VIEW_TYPE_AD_BANNER:
                setUpAdBannerViewHolder((AdBannerViewHolder) holder, position);
                break;
            case VIEW_TYPE_SHOP_STREET:
                setUpShopStreetViewHolder((ShopStreetViewHolder) holder, position);
                break;
            case VIEW_TYPE_GOODS_LIST:
                setUpGoodsListViewHolder((GoodsColumnViewHolder) holder, position);
                break;
            case VIEW_TYPE_GOODS_TITLE://05  更多那一行
                setUpGoodsTitleViewHolder((GoodsTitleViewHolder) holder, position);
                break;
            case VIEW_TYPE_AD_TITLE:
                setUpAdTitleViewHolder((AdTitleViewHolder) holder, position);
                break;
            case VIEW_TYPE_AD_TITLE_TWO:
                setUpAdTitleViewHolder((AdTitleViewHolder) holder, position);
                break;

            case VIEW_TYPE_LOADING:
                setLoadingViewHolder((LoadingItemViewHolder) holder, position);
                break;
            case VIEW_TYPE_VIDEO:
                setVideoViewHolder((VideoViewHolder) holder, position);
                break;
            case GUESS_LIKE_TITLE:
                setGuessLikeTitleViewHolder((UserGuessTitleViewHolder) holder, position);
                break;
            //猜你喜欢
            case VIEW_INDEX_GUESS_LIKE:
                setGuessLikeViewHolder((GoodsColumnViewHolder) holder, position);
                break;
            //左边右边都有字的title
            case VIEW_LEFT_RIGHT_TITLE:
                setLeftRightTitleViewHolder((LeftRightTitleViewHolder) holder, "元宝换购", "更多");
                break;
            //元宝换购上面的广告位
            case VIEW_TYPE_AD_ADVERT:
                setUpAdBannerADViewHolder((AdBannerViewHolder) holder, position);
                break;
            //元宝换购
            case VIEW_TYPE_INGOTS_BUY:
                setIngotsBuyViewHolder((IngotsBuyViewHolder) holder, position);
                break;
            //附近商家
            case VIEW_TYPE_NEAR_SHOP:
                setNearShopViewHolder((NearShopViewHolder) holder, position);
                break;
            //商家入驻广告
            case VIEW_TYPE_SHOP_JION:
                setShopJoinViewHolder((ShopJoinAdViewHolder) holder, position);
                break;
            //最新入驻 商家
            case VIEW_TYPE_SHOP_NEW_JION:
                setShopNewJoinViewHolder((ShopNewJoinViewHolder) holder, position);
                break;
            //本周网红商家1 ,本周最受欢迎商家2,
            case VIEW_TYPE_NET_HOT_SHOP:
                setNetHotShopViewHolder((ShopNewJoinViewHolder) holder, position, 1);
                break;
            case VIEW_TYPE_LIKE_SHOP:
                setNetHotShopViewHolder((ShopNewJoinViewHolder) holder, position, 2);
                break;

            //视频直播
            case VIEW_HOME_VIDEO_SHOW:
                setVideoShowViewHolder((VideoShowViewHolder) holder, position);
                break;
        }
    }

    private void setLoadingViewHolder(LoadingItemViewHolder viewHolder, int position) {
        RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation
                (viewHolder.imageView.getContext(), R.anim.rotate_animation);
        viewHolder.imageView.setAnimation(rotateAnimation);
    }

    @Override
    public int getItemViewType(int position) {

        int viewType = getViewType(position).ordinal();
        return viewType;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeLastItem() {
        notifyItemRemoved(data.size() - 1);
        data.remove(data.size() - 1);
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
     * 视频直播模块
     *
     * @param parent
     * @return
     */
    private RecyclerView.ViewHolder createVideoShowViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_home_video_show, parent, false);
        return new VideoShowViewHolder(view);
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


    private RecyclerView.ViewHolder createAdBannerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_banner, parent, false);

        return new AdBannerViewHolder(view);
    }

    private RecyclerView.ViewHolder createAdBannercenterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_banner_center, parent, false);
        return new AdBannerViewHolder(view);
    }


    private RecyclerView.ViewHolder createAdColumnFourViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_four_column, parent, false);
        return new AdColumnViewHolder(view);
    }

    private RecyclerView.ViewHolder createAdColumnViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_column, parent, false);
        return new AdColumnViewHolder(view);
    }

    private VideoViewHolder createVideoViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_video, parent, false);
        return new VideoViewHolder(view);
    }

    private AdTitleViewHolder createAdTitleTwoViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_title_two, parent, false);
        return new AdTitleViewHolder(view);
    }

    private AdTitleViewHolder createAdTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_ad_title, parent, false);
        return new AdTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createArticleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_article, parent, false);
        return new ArticleViewHolder(view);
    }

    private RecyclerView.ViewHolder createNoticeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_notice, parent, false);
        return new NoticeViewHolder(view);
    }

    private BlankLineViewHolder createBlankLineViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_blank_line, parent, false);
        return new BlankLineViewHolder(view);
    }

    private RecyclerView.ViewHolder createEmptyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_empty_view, parent, false);
        return new IndexEmptyViewHolder(view);
    }

    private RecyclerView.ViewHolder createGoodsDummyTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_dummy_title, parent, false);
        return new NearShopTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createGoodsListTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_list_title, parent, false);
        return new GoodsListTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createGoodsListViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_column, parent, false);
        return new GoodsColumnViewHolder(view);
    }

    private RecyclerView.ViewHolder createGoodsPromotionViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_promotion, parent, false);
        return new GoodsPromotionViewHolder(view);
    }

    private GoodsTitleViewHolder createGoodsTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_title, parent, false);
        return new GoodsTitleViewHolder(view);
    }

    private LoadDisabledViewHolder createLoadingDisabledViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_load_disabled, parent, false);
        return new LoadDisabledViewHolder(view);
    }

    private LoadingItemViewHolder createLoadingViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_loading, parent, false);
        return new LoadingItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createNavigationViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_navigation, parent, false);
        return new NavigationViewHolder(view);
    }

    private ShopListTitleViewHolder createShopListTitleViewHolder(ViewGroup parent) {
        return null;
    }

    private ShopListViewHolder createShopListViewHolder(ViewGroup parent) {
        return null;
    }

    private RecyclerView.ViewHolder createShopStreetViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_shop_street, parent, false);
        return new ShopStreetViewHolder(view);
    }

    private RecyclerView.ViewHolder createThreeColumnGoodsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_column, parent, false);
        return new GoodsColumnViewHolder(view);
    }

    private RecyclerView.ViewHolder createTwoColumnGoodsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods_column, parent, false);
        return new GoodsColumnViewHolder(view);
    }


    private RecyclerView.ViewHolder createGuessLikeTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guess_title, parent, false);
        return new UserGuessTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createGuessLikdeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_index_goods_column, parent, false);
        return new GoodsColumnViewHolder(view);
    }

    private RecyclerView.ViewHolder createLeftRightTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_right_title, parent, false);
        return new LeftRightTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createshopListDummyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_shop_list, parent, false);

        return new DummyViewHolder(view);
    }

    private void setUpAdBannerViewHolder(AdBannerViewHolder viewHolder, int position) {

        if (position == 0) {//banner位置在第一个的时候显示我的元宝
            viewHolder.tvMyIngots.setVisibility(View.VISIBLE);
            if (App.getInstance().user_ingot_number == null) {
                if (TextUtils.isEmpty(ingots)) {//看首页是否刷新ingots
                    viewHolder.tvMyIngots.setText("我的元宝:0");
                } else {
                    viewHolder.tvMyIngots.setText("我的元宝:" + ingots);
                    notifyDataSetChanged();
                }
            } else {
                viewHolder.tvMyIngots.setText("我的元宝:" + App.getInstance().user_ingot_number);
            }
        } else {//否则隐藏
            viewHolder.tvMyIngots.setVisibility(View.GONE);
        }


        //设置viewpager 滑动时间
        BannerScroller scroller = new BannerScroller(context);
        scroller.setTime(1500);
        scroller.initViewPagerScroll(viewHolder.viewPager);


        TemplateModel dataModel = data.get(position);

        if (!Utils.isNull(dataModel.data)) {
            AdColumnModel adModel = JSON.parseObject(dataModel.data, AdColumnModel.class);
            if (Utils.isNull(adModel.pic_1)) {
                return;
            }
            List<AdItemModel> items = adModel.pic_1;
            if (items == null || items.size() == 0) {
                return;
            }
            AdItemModel firstItem = items.get(0);
            int height = 0;
            float imageWidth = firstItem.image_width;
            float imageHeight = firstItem.image_height;
            float realHeight = windowWidth * imageHeight / imageWidth;
            height = (int) realHeight;
            if (height <= 0) {
//                viewHolder.viewPager.setAdapter(null);
//                return;
                //如果没有宽高，默认使用第一张图片的宽高比
                height = (int) (windowWidth * 0.31f);
            }

            ViewGroup.LayoutParams layoutParams = viewHolder.viewPager.getLayoutParams();
            layoutParams.height = height;
            viewHolder.viewPager.setLayoutParams(layoutParams);
            //设置首页banner 数据
            IndexAdBannerAdapter adapter = new IndexAdBannerAdapter(items);
            adapter.itemPosition = position;

            adapter.onClickListener = onClickListener;
            viewHolder.viewPager.setAdapter(adapter);
//            viewHolder.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });

            viewHolder.pageIndicator.setViewPager(viewHolder.viewPager);
            viewHolder.pageIndicator.notifyDataSetChanged();
            viewHolder.pageIndicator.setSnap(true);
        }
    }

    /**
     * 配置banner广告数据
     *
     * @param viewHolder
     * @param position
     */
    private void setUpAdBannerADViewHolder(AdBannerViewHolder viewHolder, int position) {
        if (position == 0) {//banner位置在第一个的时候显示我的元宝
            viewHolder.tvMyIngots.setVisibility(View.VISIBLE);
            if (App.getInstance().user_ingot_number == null) {
                if (TextUtils.isEmpty(ingots)) {//看首页是否刷新ingots
                    viewHolder.tvMyIngots.setText("我的元宝:0");
                } else {
                    viewHolder.tvMyIngots.setText("我的元宝:" + ingots);
                    notifyDataSetChanged();
                }
            } else {
                viewHolder.tvMyIngots.setText("我的元宝:" + App.getInstance().user_ingot_number);
            }
        } else {//否则隐藏
            viewHolder.tvMyIngots.setVisibility(View.GONE);
        }


        //设置viewpager 滑动时间
        BannerScroller scroller = new BannerScroller(context);
        scroller.setTime(1500);
        scroller.initViewPagerScroll(viewHolder.viewPager);


        TemplateModel dataModel = data.get(position);


        if (!Utils.isNull(dataModel.data)) {
            List<HomeShopAndProductBean.AdvertBean> list = GsonUtils.Companion.toList(dataModel.data, HomeShopAndProductBean.AdvertBean.class);

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
            IndexHomeAdverBannerAdapter adapter = new IndexHomeAdverBannerAdapter(list);
            adapter.itemPosition = position;

            adapter.onClickListener = onClickListener;
            viewHolder.viewPager.setAdapter(adapter);


            viewHolder.pageIndicator.setViewPager(viewHolder.viewPager);
            viewHolder.pageIndicator.notifyDataSetChanged();
            viewHolder.pageIndicator.setSnap(true);
        }
    }

    private void setUpAdColumnThreeViewHolder(AdColumnViewHolder viewHolder, int position) {

        TemplateModel dataModel = data.get(position);

        if (!Utils.isNull(dataModel.data)) {
            AdColumnModel adModel = JSON.parseObject(dataModel.data, AdColumnModel.class);
            if (Utils.isNull(adModel.pic_1)) {
                return;
            }

            if (Utils.isColor(adModel.style_bgcolor)) {
                viewHolder.relativeLayout.setBackgroundColor(Color.parseColor(adModel.style_bgcolor));

            } else {
                viewHolder.relativeLayout.setBackgroundColor(viewHolder.recyclerView.getResources().getColor(R.color.colorTen));
            }

            List<AdItemModel> items = adModel.pic_1;
            items.addAll(adModel.pic_2);

            AdItemModel firstItem = items.get(0);
            int height;
            float imageWidth = firstItem.image_width;
            float imageHeight = firstItem.image_height;

            float realHeight = windowWidth * imageHeight / (imageWidth * 2);
            height = (int) (realHeight);
            if (height <= 0) {
//                viewHolder.recyclerView.setAdapter(null);
//                return;
                height = (int) (windowWidth * 0.876f);
            }

            ViewGroup.LayoutParams layoutParams = viewHolder.recyclerView.getLayoutParams();

            layoutParams.height = height;

            viewHolder.recyclerView.setLayoutParams(layoutParams);

            IndexAdColumnThreeAdapter adapter = new IndexAdColumnThreeAdapter(items);
            IndexAdColumnThreeAdapter.height = height;
            IndexAdColumnThreeAdapter.weight = Utils.getWindowWidth(context);

            if ("-1".equals(adModel.style_border)) {
                IndexAdColumnThreeAdapter.isShowLine = false;
            } else {
                IndexAdColumnThreeAdapter.isShowLine = true;
            }

            adapter.itemPosition = position;
            adapter.onClickListener = onClickListener;
            viewHolder.recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
            viewHolder.recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void setUpAdColumnFourViewHolder(final AdColumnViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            AdColumnModel adModel = JSON.parseObject(dataModel.data, AdColumnModel.class);
            if (Utils.isNull(adModel.pic_1)) {
                viewHolder.recyclerView.setAdapter(null);
                return;
            }
            final List<AdItemModel> items = adModel.pic_1;
            if (Utils.isNull(items)) {
                viewHolder.recyclerView.setAdapter(null);
                return;
            }
            final int imageCount = items.size();

            AdItemModel firstItem = items.get(0);
            float imageWidth = firstItem.image_width;
            float imageHeight = firstItem.image_height;

            Context context = viewHolder.recyclerView.getContext();
            final int leftMargin = Utils.dpToPx(context, 10);
            final int rightMargin = Utils.dpToPx(context, 10);
            final int gap = Utils.dpToPx(context, 5);
            final int totalGap = gap * (imageCount - 1);
            final int bottomMargin = Utils.dpToPx(context, 5);

            float realWidth = (windowWidth - totalGap - leftMargin - rightMargin) / (float) imageCount;

            float realHeight = realWidth * imageHeight / imageWidth;
            int realHeightInteger = (int) realHeight;
            if (realHeightInteger <= 0) {
//                viewHolder.recyclerView.setAdapter(null);
//                return;
                realHeightInteger = (int) (windowWidth * 0.3f);
            }

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.recyclerView.getLayoutParams();
            layoutParams.height = realHeightInteger + bottomMargin;
            viewHolder.recyclerView.setLayoutParams(layoutParams);

//            if (Utils.isColor(adModel.style_bgcolor)) {
//                viewHolder.recyclerView.setBackgroundColor(Color.parseColor(adModel.style_bgcolor));
//            }
//            else {
//                viewHolder.relativeLayout.setBackgroundColor(viewHolder.relativeLayout.getResources().getColor(R.color.translucentSevenWhite));
//            }

            viewHolder.recyclerView.setNestedScrollingEnabled(false);


            viewHolder.recyclerView.setPadding(leftMargin, 0, rightMargin, bottomMargin);


            IndexAdColumnGapAdapter adapter = new IndexAdColumnGapAdapter(items);
            adapter.itemPosition = position;
            adapter.onClickListener = onClickListener;

            adapter.itemWidth = (int) realWidth + 2;

            viewHolder.recyclerView.setAdapter(adapter);
            IndexAdColumnLayoutManager layoutManager = new IndexAdColumnLayoutManager(viewHolder.recyclerView.getContext());
            viewHolder.recyclerView.setLayoutManager(layoutManager);

            if (!viewHolder.decorated) {
                MyItemDecoration myItemDecoration = new MyItemDecoration(context, MyItemDecoration.HORIZONTAL);
                myItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_bg_4));
                viewHolder.recyclerView.addItemDecoration(myItemDecoration);
                viewHolder.decorated = true;
            }
        }
    }

    private void setUpAdColumnViewHolder(AdColumnViewHolder viewHolder, int position) {


        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            AdColumnModel adModel = JSON.parseObject(dataModel.data, AdColumnModel.class);
            if (Utils.isNull(adModel.pic_1)) {
                viewHolder.recyclerView.setAdapter(null);
                return;
            }
            final List<AdItemModel> items = adModel.pic_1;
            if (Utils.isNull(items)) {
                viewHolder.recyclerView.setAdapter(null);
                return;
            }
            final int imageCount = items.size();

            AdItemModel firstItem = items.get(0);
            float imageWidth = firstItem.image_width;
            float imageHeight = firstItem.image_height;

            Context context = viewHolder.recyclerView.getContext();
//            final int leftMargin = Utils.dpToPx(context, 0);
//            final int rightMargin = Utils.dpToPx(context, 0);


            final int leftMargin = 0;
            final int rightMargin = 0;


            int gap = Utils.dpToPx(context, 1);
            int bottomMargin = Utils.dpToPx(context, 0.5f);

//            //如果style_border等于-1就不显示分割线
//            if ("-1".equals(adModel.style_border)) {
//                gap = 0;
//                bottomMargin = 0;
//            }
            gap = 0;
            bottomMargin = 0;


            final int totalGap = gap * (imageCount - 1);


            float realWidth = (windowWidth - totalGap - leftMargin - rightMargin) / (float)
                    imageCount;

            float realHeight = realWidth * imageHeight / imageWidth;
            int realHeightInteger = (int) realHeight;
            if (realHeightInteger <= 0) {
//                viewHolder.recyclerView.setAdapter(null);
//                return;
                realHeightInteger = (int) (windowWidth * 0.25f);
            }

            ViewGroup.LayoutParams layoutParams = viewHolder.recyclerView.getLayoutParams();
            layoutParams.height = realHeightInteger + bottomMargin;
            viewHolder.recyclerView.setPadding(0, 0, 0, bottomMargin);
            viewHolder.recyclerView.setLayoutParams(layoutParams);
            viewHolder.recyclerView.setNestedScrollingEnabled(false);

//            if (Utils.isColor(adModel.style_bgcolor)) {
//                viewHolder.recyclerView.setBackgroundColor(Color.parseColor(adModel.style_bgcolor));
//            }
//            else {
//                viewHolder.relativeLayout.setBackgroundColor(viewHolder.relativeLayout.getResources().getColor(R.color.translucentSevenWhite));
//            }


            IndexAdColumnGapAdapter adapter = new IndexAdColumnGapAdapter(items);
            adapter.itemPosition = position;
            adapter.onClickListener = onClickListener;

//            adapter.itemWidth = (int) realWidth + 2;
            adapter.itemWidth = (int) realWidth;

            adapter.dividerWidth = gap;
            viewHolder.recyclerView.setAdapter(adapter);

            IndexAdColumnLayoutManager layoutManager = new IndexAdColumnLayoutManager(viewHolder
                    .recyclerView.getContext());
            viewHolder.recyclerView.setLayoutManager(layoutManager);

//            if (!"-1".equals(adModel.style_border)) {
//                if (!viewHolder.decorated) {
//                    viewHolder.recyclerView.addItemDecoration(viewHolder.itemDecoration);
//                    viewHolder.decorated = true;
//                }
//            } else {
            viewHolder.recyclerView.removeItemDecoration(viewHolder.itemDecoration);
            viewHolder.decorated = false;
//            }
        }
    }

    private void setVideoViewHolder(VideoViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            VideoModel videoModel = JSON.parseObject(dataModel.data, VideoModel.class);
            if (Utils.isNull(videoModel.data.video_list)) {
                viewHolder.recyclerView.setAdapter(null);
                return;
            }

            final List<String> videoList = videoModel.data.video_list;
            if (Utils.isNull(videoList)) {
                viewHolder.recyclerView.setAdapter(null);
                return;
            }


            if (TextUtils.isEmpty(videoModel.data.video_introduction)) {
                viewHolder.textViewTitle.setVisibility(View.GONE);
            } else {
                viewHolder.textViewTitle.setVisibility(View.VISIBLE);
                viewHolder.textViewTitle.setText(videoModel.data.video_introduction);
            }

            final int videoCount = videoList.size();

            Context context = viewHolder.recyclerView.getContext();
            final int gap = Utils.dpToPx(context, 1);
            final int totalGap = gap * (videoCount - 1);
            final int bottomMargin = Utils.dpToPx(context, 5);

            float realWidth = (windowWidth - totalGap) / videoCount;
            int realHeightInteger = (int) (windowWidth * 0.3f);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.recyclerView.getLayoutParams();
            layoutParams.height = realHeightInteger + bottomMargin;
            viewHolder.recyclerView.setLayoutParams(layoutParams);

            viewHolder.recyclerView.setNestedScrollingEnabled(false);
            viewHolder.recyclerView.setPadding(0, 0, 0, bottomMargin);
            viewHolder.recyclerView.addItemDecoration(viewHolder.myItemDecoration);

            IndexVideoAdapter adapter = new IndexVideoAdapter(videoList, context);
            adapter.itemPosition = position;
            adapter.onClickListener = onClickListener;
            adapter.itemWidth = (int) realWidth;
            viewHolder.recyclerView.setAdapter(adapter);
            IndexAdColumnLayoutManager layoutManager = new IndexAdColumnLayoutManager(viewHolder
                    .recyclerView.getContext());
            viewHolder.recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void setUpAdTitleViewHolder(AdTitleViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            AdTitleModel item = JSON.parseObject(dataModel.data, AdTitleModel.class);
            if (Utils.isNull(item.title_1)) {
                return;
            }
            AdTitleDataModel itemModel = item.title_1;
            if (!Utils.isColor(itemModel.color)) {
                itemModel.color = "#000000";
            }
            viewHolder.leftLineView.setBackgroundColor(Color.parseColor(itemModel.color));
            viewHolder.rightLineView.setBackgroundColor(Color.parseColor(itemModel.color));
            viewHolder.textView.setTextColor(Color.parseColor(itemModel.color));
            viewHolder.textView.setText(itemModel.name);

            if (!Utils.isNull(item.style_bgcolor) && Utils.isColor(item.style_bgcolor)) {
                viewHolder.itemView.setBackgroundColor(Color.parseColor(item.style_bgcolor));
            } else {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }

    private void setLeftRightTitleViewHolder(LeftRightTitleViewHolder viewHolder, String left, String right) {
        viewHolder.tv_left_title.setText(left);
        viewHolder.tv_right_title.setText(right);
    }

    private void setUpArticleViewHolder(ArticleViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            ArticleModel articleModel = JSON.parseObject(dataModel.data, ArticleModel.class);
            if (Utils.isNull(articleModel.article_1)) {
                return;
            }
            List<ArticleItemModel> items = articleModel.article_1;
            IndexArticleAdapter indexArticleAdapter = new IndexArticleAdapter(items);
            viewHolder.verticalBannerView.setAdapter(indexArticleAdapter);
            if (articleModel.pic_1 != null && articleModel.pic_1.size() > 0) {
                AdItemModel adItemModel = articleModel.pic_1.get(0);
                if (adItemModel != null && !Utils.isNull(adItemModel.path)) {
                    String path = Utils.urlOfImage(adItemModel.path);
                    ImageLoader.displayImage(Utils.urlOfImage(adItemModel.path), viewHolder
                            .imageview);
                }
            } else {
                viewHolder.imageview.setImageResource(R.mipmap.ic_index_article);
            }

            Utils.setViewTypeForTag(viewHolder.imageview, ViewType.VIEW_TYPE_ARTICLE_TITLE);
            Utils.setPositionForTag(viewHolder.imageview, position);
            viewHolder.imageview.setOnClickListener(onClickListener);

            Utils.setViewTypeForTag(viewHolder.coverView, ViewType.VIEW_TYPE_ARTICLE);
            Utils.setPositionForTag(viewHolder.coverView, position);
            viewHolder.coverView.setOnClickListener(onClickListener);
        }
    }

    private void setUpNoticeViewHolder(final NoticeViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            viewHolder.marqueeView.removeAllViews();
            NoticeModel noticeModel = JSON.parseObject(dataModel.data, NoticeModel.class);

            viewHolder.readView.setTextSize(MarqueeView.textSize);
            viewHolder.readView.setTypeface(null);


            if (!viewHolder.flag) {
                viewHolder.readView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        viewHolder.flag = true;
                        viewHolder.marqueeView.startWithList(viewHolder.readView.getCharArray());
//                    //viewHolder.marqueeView.startWithText(noticeModel.text_1.text);
                        viewHolder.readView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
            viewHolder.readView.setText(noticeModel.text_1.text);
            if (viewHolder.flag) {
                viewHolder.marqueeView.startWithList(viewHolder.readView.getCharArray());
            }
//                    //viewHolder.marqueeView.startWithText(noticeModel.text_1.text);

            if (!Utils.isNull(noticeModel.pic_1)) {
                ImageLoader.displayImage(Utils.urlOfImage(noticeModel.pic_1.get(0).path),
                        viewHolder.imageView);

                if (!Utils.isNull(noticeModel.pic_1.get(0).link)) {
                    Utils.setViewTypeForTag(viewHolder.imageView, ViewType.VIEW_TYPE_NOTICE);
                    Utils.setPositionForTag(viewHolder.imageView, position);
                    viewHolder.imageView.setOnClickListener(onClickListener);
                }
            } else {
                viewHolder.imageView.setImageResource(R.mipmap.ic_news_tit);
            }

            viewHolder.marqueeView.start();
        }

    }

    private void setUpBlankViewHolder(BlankLineViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);

        if (!Utils.isNull(dataModel.data)) {

            BlankDataModel blankModel = JSON.parseObject(dataModel.data, BlankDataModel.class);
            if (blankModel.style_height != 0) {
                ViewGroup.LayoutParams layoutParams = viewHolder.blank.getLayoutParams();
                layoutParams.height = Utils.dpToPx(viewHolder.blank.getContext(), (float) blankModel.style_height);

                viewHolder.blank.setLayoutParams(layoutParams);
            }
            if (Utils.isColor(blankModel.style_bgcolor)) {
                viewHolder.blank.setBackgroundColor(Color.parseColor(blankModel.style_bgcolor));
            } else {
                viewHolder.blank.setBackgroundColor(viewHolder.blank.getResources().getColor(R
                        .color.colorTen));
            }

        } else {
            ViewGroup.LayoutParams layoutParams = viewHolder.blank.getLayoutParams();
            layoutParams.height = Utils.dpToPx(viewHolder.blank.getContext(), (float) 10);

            viewHolder.blank.setLayoutParams(layoutParams);

            viewHolder.blank.setBackgroundColor(viewHolder.blank.getResources().getColor(R.color.colorTen));
        }
    }

    private void setUpDummyTitleViewHolder(NearShopTitleViewHolder holder, int position) {
        Utils.setViewTypeForTag(holder.mLayout, ViewType.VIEW_TYPE_GOODS_DUMMY_TITLE);
        holder.mLayout.setOnClickListener(onClickListener);
        Context context = holder.mImageView.getContext();
        Drawable sourceDrawable = ContextCompat.getDrawable(context, R.mipmap
                .btn_right_arrow_circled);
        Bitmap sourceBitmap = Utils.toBitmap(sourceDrawable);
        Bitmap finalBitmap = Utils.changeColor(sourceBitmap, Color.parseColor("#ffc000"));
        holder.mImageView.setImageBitmap(finalBitmap);
    }

    private void setUpDummyViewHolder(DummyViewHolder holder, int position) {
//        holder.linearlayout_root.setVisibility(View.VISIBLE);
        IndexNearShopAdapter.mPosition = position;
        TemplateModel dataModel = data.get(position);

        NearShopModel nearShopModel = JSON.parseObject(dataModel.data, NearShopModel.class);

        holder.linearlayout_empty.setVisibility(View.GONE);
        if (nearShopModel != null && nearShopModel.data.list.size() > 0) {
            holder.linearlayout_empty.setVisibility(View.GONE);
            holder.recyclerView.setVisibility(View.VISIBLE);

            List<NearShopItemModel> items = nearShopModel.data.list;
            IndexNearShopAdapter adapter = new IndexNearShopAdapter(items);

            adapter.onClickListener = onClickListener;
            GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setNestedScrollingEnabled(false);
        } else {
            holder.recyclerView.setVisibility(View.GONE);
            holder.linearlayout_empty.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(dataModel.message)) {
                holder.textView_empty_tip.setText(dataModel.message);
            } else {
//                holder.linearlayout_root.setVisibility(View.GONE);
            }

            return;
        }
    }

    private void setUpGoodsListViewHolder(GoodsColumnViewHolder holder, int position) {
        TemplateModel dataModel = data.get(position);

        if (!Utils.isNull(dataModel.data)) {
            GoodsListModel goodsListModel = JSON.parseObject(dataModel.data, GoodsListModel.class);
            holder.items.clear();
            holder.items.addAll(goodsListModel.data.list);
            holder.adapter.itemPosition = position;
            holder.adapter.onClickListener = onClickListener;
            int windowWidth = Utils.getWindowWidth(context);
            int itemWidth = (int) Math.round(windowWidth / 2.0);
            int itemHeight = (int) (itemWidth * 1.4);
            holder.adapter.itemWidth = itemWidth;
            holder.adapter.itemHeight = itemHeight;
            holder.recyclerView.setAdapter(holder.adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setNestedScrollingEnabled(false);
        }

    }

    private void setUpGoodsPromotionViewHolder(GoodsPromotionViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            GoodsColumnModel goodsColumnModel = JSON.parseObject(dataModel.data, GoodsColumnModel
                    .class);

            if (Utils.isNull(goodsColumnModel.goods_1)) {
                return;
            }

            List<GoodsItemModel> goodsList = goodsColumnModel.goods_1;
            Context mContext = viewHolder.itemView.getContext();
            if ("1".equals(goodsColumnModel.style_roll)) {
                viewHolder.viewPager.setVisibility(View.GONE);
                viewHolder.pageIndicator.setVisibility(View.GONE);
                viewHolder.recyclerView.setVisibility(View.VISIBLE);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                viewHolder.recyclerView.setLayoutManager(linearLayoutManager);

                IndexGoodsPromotionTwoAdapter adapter = new IndexGoodsPromotionTwoAdapter(goodsList, position);
                adapter.onClickListener = onClickListener;
                viewHolder.recyclerView.setAdapter(adapter);
            } else {
                viewHolder.viewPager.setVisibility(View.VISIBLE);
                viewHolder.pageIndicator.setVisibility(View.VISIBLE);
                viewHolder.recyclerView.setVisibility(View.GONE);

                IndexGoodsPromotionAdapter adapter = new IndexGoodsPromotionAdapter(goodsList);
                adapter.itemPosition = position;
                adapter.onClickListener = onClickListener;
                viewHolder.viewPager.setAdapter(adapter);
                viewHolder.pageIndicator.setViewPager(viewHolder.viewPager);
            }
        }
    }

    private void setUpGoodsTitleViewHolder(GoodsTitleViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            GoodsTitleModel item = JSON.parseObject(dataModel.data, GoodsTitleModel.class);
            if (Utils.isNull(item.title_1)) {
                return;
            }
            GoodsTitleDataModel itemModel = item.title_1;
            viewHolder.textView.setText(itemModel.name);
            if (!Utils.isColor(itemModel.color)) {
                itemModel.color = "#000000";
            }
            viewHolder.textView.setTextColor(Color.parseColor(itemModel.color));
            Context context = viewHolder.moreImageView.getContext();
            Drawable sourceDrawable = ContextCompat.getDrawable(context, R.mipmap
                    .btn_right_arrow_circled);

            Bitmap sourceBitmap = Utils.toBitmap(sourceDrawable);
            Bitmap finalBitmap = Utils.changeColor(sourceBitmap, Color.parseColor(itemModel.color));
            viewHolder.moreImageView.setImageBitmap(finalBitmap);

            Utils.setViewTypeForTag(viewHolder.moreTextView, ViewType.VIEW_TYPE_GOODS_TITLE);
            Utils.setPositionForTag(viewHolder.moreTextView, position);
            viewHolder.moreTextView.setOnClickListener(onClickListener);

            if (itemModel.link.equals(Macro.TEMPLATE_CODE_INGOT)) {

                viewHolder.fragment_index_goods_ingot_textView.setVisibility(View.VISIBLE);
                //获取我的元宝数量
                if (App.getInstance().user_ingot_number == null) {
                    viewHolder.fragment_index_goods_ingot_textView.setText("0");
                } else {
                    viewHolder.fragment_index_goods_ingot_textView.setText(App.getInstance().user_ingot_number);
                }


                if (onUserIngotNumberViewListener != null) {
                    onUserIngotNumberViewListener.getIngotView(viewHolder.fragment_index_goods_ingot_textView);
                }
                viewHolder.moreTextView.setText("查看");
            }
        }
    }

    private void setUpLoadDisabledViewHolder(LoadDisabledViewHolder holder, int position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            holder.loadDisabledText.setText(dataModel.data);
        }
    }

    private void setUpNavigationFiveViewHolder(final NavigationViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);

//        viewHolder.viewPager.setAdapter(null);
        viewHolder.relativeLayout_root.setBackgroundColor(Color.parseColor("#ffffff"));

        if (!TextUtils.isEmpty(dataModel.data)) {
            NavigationModel navigatorModel = JSON.parseObject(dataModel.data, NavigationModel
                    .class);


            if (!TextUtils.isEmpty(navigatorModel.style_bgimage)) {

                ImageLoader.loadImage(Utils.urlOfImage(navigatorModel.style_bgimage)
                        , new SimpleImageLoadingListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                viewHolder.relativeLayout_root.setBackground(new BitmapDrawable(loadedImage));

                            }
                        });

            } else if (!TextUtils.isEmpty(navigatorModel.style_bgcolor)) {
                viewHolder.relativeLayout_root.setBackgroundColor(Color.parseColor(navigatorModel
                        .style_bgcolor));
            } else {
                viewHolder.relativeLayout_root.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            if (Utils.isNull(navigatorModel.mnav_1)) {
                return;
            }

            List<NavigationItemModel> list = navigatorModel.mnav_1;

            IndexGoodsNavigationAdapter indexGoodsNavigationAdapter;
            viewHolder.viewPager.setAdapter(indexGoodsNavigationAdapter = new IndexGoodsNavigationAdapter(viewHolder.itemView.getContext(), list, 5, position));
            viewHolder.viewPager.resetHeight();
            indexGoodsNavigationAdapter.onClickListener = onClickListener;

            viewHolder.pageIndicator.setViewPager(viewHolder.viewPager);

            if (list.size() / 10.0 > 1) {
                viewHolder.pageIndicator.setVisibility(View.VISIBLE);
            } else {
                viewHolder.pageIndicator.setVisibility(View.GONE);
            }


        }
    }

    private void setUpNavigationViewHolder(final NavigationViewHolder viewHolder, int position) {
        TemplateModel dataModel = data.get(position);
//        viewHolder.viewPager.setAdapter(null);
        viewHolder.relativeLayout_root.setBackgroundColor(Color.parseColor("#ffffff"));


        if (!TextUtils.isEmpty(dataModel.data)) {
            NavigationModel navigatorModel = JSON.parseObject(dataModel.data, NavigationModel
                    .class);
            if (!TextUtils.isEmpty(navigatorModel.style_bgimage)) {
                ImageLoader.loadImage(Utils.urlOfImage(navigatorModel.style_bgimage)
                        , new SimpleImageLoadingListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                viewHolder.relativeLayout_root.setBackground(new BitmapDrawable(loadedImage));

                            }
                        });
            } else if (!TextUtils.isEmpty(navigatorModel.style_bgcolor)) {
                viewHolder.relativeLayout_root.setBackgroundColor(Color.parseColor(navigatorModel
                        .style_bgcolor));
            } else {
                viewHolder.relativeLayout_root.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            if (Utils.isNull(navigatorModel.mnav_1)) {
                return;
            }

            List<NavigationItemModel> list = navigatorModel.mnav_1;

//            IndexNavigationAdapter adapter = new IndexNavigationAdapter(list);
//            adapter.onClickListener = onClickListener;
//            adapter.itemPosition = position;
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(viewHolder.recyclerView
//                    .getContext(), 4);
//            viewHolder.recyclerView.setNestedScrollingEnabled(false);
//            viewHolder.recyclerView.setAdapter(adapter);
//            viewHolder.recyclerView.setLayoutManager(gridLayoutManager);

            IndexGoodsNavigationAdapter indexGoodsNavigationAdapter;
            viewHolder.viewPager.setAdapter(indexGoodsNavigationAdapter = new IndexGoodsNavigationAdapter(viewHolder.itemView.getContext(), list, 4, position));
            indexGoodsNavigationAdapter.onClickListener = onClickListener;

            viewHolder.pageIndicator.setViewPager(viewHolder.viewPager);

            if (list.size() / 8.0 > 1) {
                viewHolder.pageIndicator.setVisibility(View.VISIBLE);
            } else {
                viewHolder.pageIndicator.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 猜你喜欢标题设置
     *
     * @param viewHolder
     * @param position
     */
    private void setGuessLikeTitleViewHolder(UserGuessTitleViewHolder viewHolder, final int position) {
        viewHolder.img_title.setBackgroundResource(R.mipmap.bg_guess_like);
    }

    private void setUpShopStreetViewHolder(ShopStreetViewHolder viewHolder, int position) {
        if (!Utils.isNull(data.get(position).data)) {
            ShopStreetModel shopStreetModel = JSON.parseObject(data.get(position).data,
                    ShopStreetModel.class);
            if (shopStreetModel == null || shopStreetModel.shop_1 == null || shopStreetModel
                    .shop_1.size() == 0) {
                return;
            }
            List<ShopStreetItemModel> items = shopStreetModel.shop_1;
            int fill = 8 - items.size();
            if (items.size() < 8) {
                for (int i = 0; i < fill; i++) {
                    ShopStreetItemModel temp = new ShopStreetItemModel();
                    try {
                        temp.shop_logo = data.get(position).ext_info.cat.get("1");
                    } catch (Exception e) {
                    }
                    items.add(temp);
                }
            }
            if (Utils.isNull(items)) {
                return;
            }
            IndexShopStreetAdapter shopStreetAdapter = new IndexShopStreetAdapter(items);
            shopStreetAdapter.itemPosition = position;
            shopStreetAdapter.onClickListener = onClickListener;
            viewHolder.recyclerView.setAdapter(shopStreetAdapter);
            viewHolder.recyclerView.setNestedScrollingEnabled(false);
            GridLayoutManager layoutManager = new GridLayoutManager(viewHolder.recyclerView
                    .getContext(), 2, GridLayoutManager.HORIZONTAL, false);
            viewHolder.recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void setUpThreeColumnGoodsViewHolder(GoodsColumnViewHolder viewHolder, final int
            position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            GoodsColumnModel goodsPromotionModel = JSON.parseObject(dataModel.data,
                    GoodsColumnModel.class);
            if (Utils.isNull(goodsPromotionModel.goods_1)) {
                return;
            }
            List<GoodsItemModel> items = goodsPromotionModel.goods_1;
            IndexGoodsColumnAdapter adapter = new IndexGoodsColumnAdapter(items);
            adapter.itemPosition = position;
            adapter.onClickListener = onClickListener;
            int windowWidth = Utils.getWindowWidth(context);
            int itemWidth = (int) Math.round(windowWidth / 3.0);
            int itemHeight = (int) (itemWidth * 1.6);
            adapter.itemWidth = itemWidth;
            adapter.itemHeight = itemHeight;
            viewHolder.recyclerView.setAdapter(adapter);
            viewHolder.recyclerView.setNestedScrollingEnabled(false);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
            viewHolder.recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void setUpTwoColumnGoodsViewHolder(GoodsColumnViewHolder viewHolder, final int
            position) {
        TemplateModel dataModel = data.get(position);
        if (!Utils.isNull(dataModel.data)) {
            GoodsColumnModel goodsPromotionModel = JSON.parseObject(dataModel.data,
                    GoodsColumnModel.class);

            if (goodsPromotionModel.goods_1 != null) {
                List<GoodsItemModel> items = goodsPromotionModel.goods_1;
                IndexGoodsColumnAdapter adapter = new IndexGoodsColumnAdapter(items);
                adapter.itemPosition = position;
                adapter.onClickListener = onClickListener;
                int windowWidth = Utils.getWindowWidth(context);
                int itemWidth = (int) Math.round(windowWidth / 2.0);
                int itemHeight = (int) (itemWidth * 1.4);
                adapter.itemWidth = itemWidth;
                adapter.itemHeight = itemHeight;
                viewHolder.recyclerView.setAdapter(adapter);
                GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
                viewHolder.recyclerView.setLayoutManager(layoutManager);
                viewHolder.recyclerView.setNestedScrollingEnabled(false);
            }
        }
    }

    private void setGuessLikeViewHolder(GoodsColumnViewHolder viewHolder, final int position) {

        mImageView = viewHolder.mImageView_Nodata;
//        IndexGuessLikeAdapter adapter = new IndexGuessLikeAdapter(context);
        if (adapter == null) {
            adapter = new IndexGuessLikeAdapter(context);
            viewHolder.recyclerView.setAdapter(adapter);
        }
        adapter.itemPosition = position;
        adapter.onClickListener = onClickListener;

        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setNestedScrollingEnabled(false);
        adapter.data = data.get(position).mGoodsList;
        adapter.notifyDataSetChanged();

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
        ingotsBuyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HomeShopAndProductBean.RepurchaseBean bean = (HomeShopAndProductBean.RepurchaseBean) adapter.getItem(position);
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
        viewHolder.tv_right_title.setOnClickListener(onClickListener);

        viewHolder.recyclerViewIngotsBuy.setHasFixedSize(true);
        viewHolder.recyclerViewIngotsBuy.setNestedScrollingEnabled(false);
        ingotsBuyAdapter.setNewData(list);
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

        viewHolder.recyclerViewNearShop.setHasFixedSize(true);
        viewHolder.recyclerViewNearShop.setNestedScrollingEnabled(false);

        Utils.setViewTypeForTag(viewHolder.tv_right_title, ViewType.VIEW_TYPE_NEAR_SHOP_MORE);
        Utils.setPositionForTag(viewHolder.tv_right_title, position);
        viewHolder.tv_right_title.setOnClickListener(onClickListener);
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


    /**
     * 主页视频直播模块数据绑定
     *
     * @param viewHolder
     * @param position
     */
    private void setVideoShowViewHolder(final VideoShowViewHolder viewHolder, final int position) {
        List<HomeShopAndProductBean.LiveBean> list = GsonUtils.Companion.toList(data.get(position).data, HomeShopAndProductBean.LiveBean.class);

        LinearLayoutManager manager = new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        viewHolder.recyclerViewVideoShow.setLayoutManager(manager);

        if (videoShowAdapter == null) {
            videoShowAdapter = new HomeVideoShowAdapter(R.layout.item_home_video_show_video);
            viewHolder.recyclerViewVideoShow.setAdapter(videoShowAdapter);
        }

        videoShowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeShopAndProductBean.LiveBean bean = (HomeShopAndProductBean.LiveBean) adapter.getData().get(position);
                switch (bean.getStatus()) {
                    case 1://直播中
                        bean.setUrl_push(bean.getUrl_play_hls());
                        break;
                    case 2://已结束
                    case 3://被强制结束
                        if (TextUtils.isEmpty(bean.getUrl_review())) {//urlreview为空的话就用urlpush
                            if (bean.getLive_type() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                                bean.setUrl_push(API_HEAD_IMG_URL + bean.getUrl_push());
                            } else {
                                bean.setUrl_push(bean.getUrl_push());
                            }
                        } else {
                            if (bean.getLive_type() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                                bean.setUrl_push(API_HEAD_IMG_URL + bean.getUrl_review().replace(";", ""));
                            } else {
                                bean.setUrl_push(bean.getUrl_review().replace(";", ""));
                            }
                        }
                        break;
                    default:
                        if (TextUtils.isEmpty(bean.getUrl_review())) {//urlreview为空的话就用urlpush
                            if (bean.getLive_type() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                                bean.setUrl_push(API_HEAD_IMG_URL + bean.getUrl_push());
                            } else {
                                bean.setUrl_push(bean.getUrl_push());
                            }
                        } else {
                            if (bean.getLive_type() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                                bean.setUrl_push(API_HEAD_IMG_URL + bean.getUrl_review().replace(";", ""));
                            } else {
                                bean.setUrl_push(bean.getUrl_review().replace(";", ""));
                            }
                        }
                        break;
                }
                Intent intent = new Intent(viewHolder.itemView.getContext(), VideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_VIDEO, bean);
                intent.putExtra(KEY_BUNDLE, bundle);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

        viewHolder.recyclerViewVideoShow.setHasFixedSize(true);
        viewHolder.recyclerViewVideoShow.setNestedScrollingEnabled(false);
        videoShowAdapter.setNewData(list);
    }


    OnUserIngotNumberViewListener onUserIngotNumberViewListener;

    public void setUserIngotNumber(OnUserIngotNumberViewListener onUserIngotNumberViewListener) {
        this.onUserIngotNumberViewListener = onUserIngotNumberViewListener;
    }
}
