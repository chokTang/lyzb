package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.lyzb.jbx.webscoket.BaseClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.common.Activity.RegionActivity;
import com.szy.common.Fragment.RegionFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.AttributeWholesaleActivity;
import com.szy.yishopcustomer.Activity.CheckoutActivity;
import com.szy.yishopcustomer.Activity.CheckoutFreeActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GoodsBonusActivity;
import com.szy.yishopcustomer.Activity.GroupOnRulesActivity;
import com.szy.yishopcustomer.Activity.HelpBargainListActivity;
import com.szy.yishopcustomer.Activity.InvalidActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.PickUpListActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.PromotionPopActivity;
import com.szy.yishopcustomer.Activity.RegisterBonusActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Activity.VideoFullActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Adapter.GoodsBargainAdapter;
import com.szy.yishopcustomer.Adapter.GoodsContractListAdapter;
import com.szy.yishopcustomer.Adapter.GoodsGiftListAdapter;
import com.szy.yishopcustomer.Adapter.GoodsGroupOnAdapter;
import com.szy.yishopcustomer.Adapter.GoodsTopAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Follow.ResponseFollowModel;
import com.szy.yishopcustomer.ResponseModel.Goods.AddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Goods.BargainModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ContractModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GiftModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.Goods.HelpBargainModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ParamsModel;
import com.szy.yishopcustomer.ResponseModel.Goods.RankPriceModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ResponseGoodsModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ShopInfoModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ShopModel;
import com.szy.yishopcustomer.ResponseModel.Goods.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Goods.TopGoodsModel;
import com.szy.yishopcustomer.ResponseModel.GoodsMix;
import com.szy.yishopcustomer.ResponseModel.Region.ResponseRegionModel;
import com.szy.yishopcustomer.ResponseModel.Share.ShareModel;
import com.szy.yishopcustomer.ResponseModel.ShippingFee.ResponseShippingFeeModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.BannerScroller;
import com.szy.yishopcustomer.View.ButtonDialog;
import com.szy.yishopcustomer.View.CircleImageView;
import com.szy.yishopcustomer.View.CirclePageIndicator;
import com.szy.yishopcustomer.View.CustomViewPager;
import com.szy.yishopcustomer.View.HeadWrapContentViewPager;
import com.szy.yishopcustomer.View.RuleHintDialog;
import com.szy.yishopcustomer.View.SlideDetailsLayout;
import com.szy.yishopcustomer.View.YSOStoreRegionListFlowLayoutManager;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by liuzhifeng on 2017/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsIndexFragment extends LazyFragment implements SlideDetailsLayout
        .OnSlideDetailsListener {

    public static List<Map<String, Object>> mAttrData = new ArrayList<Map<String, Object>>();
    public static ArrayList<String> shareData = new ArrayList();

    @BindView(R.id.fragment_goods_groupon_detail)
    LinearLayout mGroupOnLayout;
    @BindView(R.id.fragment_goods_groupon_detail_titleLayout)
    LinearLayout mGroupOnLinearLayout;
    @BindView(R.id.fragment_goods_groupon_recyclerView)
    RecyclerView mGroupOnRecycler;
    //    @BindView(R.id.fragment_goods_index_slide_layout)
//    SlideDetailsLayout mSlideLayout;


    //商品详情页面 活动图标
    @BindView(R.id.img_good_details_activity)
    ImageView mImg_GoodeDetailsAc;

    @BindView(R.id.go_up_button)
    ImageView mGoUp;
    @BindView(R.id.fragment_goods_index_scroll_view)
    ScrollView mScrollView;
    //    @BindView(R.id.v_tab_cursor)
//    View v_tab_cursor;
    @BindView(R.id.tv_goods_detail)
    TextView tv_goods_detail;
    @BindView(R.id.tv_goods_config)
    TextView tv_goods_config;
    @BindView(R.id.fragment_index_banner_pageIndicator)
    CirclePageIndicator pageIndicator;
    @BindView(R.id.fragment_goods_index_video_show)
    ImageView videoShowButton;
    @BindView(R.id.fragment_goods_thumb)
    HeadWrapContentViewPager mGoodsThumbViewPager;
    //砍价轮播图
    @BindView(R.id.bargin_viewPager)
    HeadWrapContentViewPager bargin_viewPager;

    @BindView(R.id.fragment_goods_name)
    TextView mGoodsName;
    @BindView(R.id.fragmeng_goods_description)
    TextView mGoodsDescription;

    @BindView(R.id.fragment_goods_price)
    TextView mGoodsPrice;
    @BindView(R.id.fragment_goods_ingot)
    TextView mGoodsIngot;


    @BindView(R.id.fragment_goods_evaluate_number)
    TextView mGoodEvaluateNumber;
    @BindView(R.id.fragment_goods_market_price)
    TextView mGoodsMarketPrice;
    @BindView(R.id.fragment_goods_deductible)
    TextView mGoodsDeductible;
    @BindView(R.id.fragment_goods_sale_number)
    TextView mGoodsSaleNumber;
    @BindView(R.id.fragment_goods_bonus_layout)
    LinearLayout mGoodsBonusLayout;
    @BindView(R.id.fragment_goods_step_price)
    LinearLayout mGoodsStepPrice;

    @BindView(R.id.fragment_goods_spec_layout)
    LinearLayout mGoodsSpecLayout;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.fragment_goods_spec_title)
    TextView mGoodsSpecTitle;
    @BindView(R.id.fragment_goods_spec_list)
    TextView mGoodsSpecList;
    @BindView(R.id.fragment_goods_region_layout)
    RelativeLayout mGoodsRegionLayout;
    @BindView(R.id.fragment_goods_region)
    TextView mGoodsRegion;
    @BindView(R.id.fragment_goods_freight_title)
    TextView mGoodsFreightTitle;
    @BindView(R.id.fragment_goods_gift_layout)
    LinearLayout mGoodsGiftLayout;
    @BindView(R.id.fragment_goods_rank_price_layout)
    LinearLayout mGoodsRankPriceLayout;
    @BindView(R.id.fragment_goods_pick_uplayout)
    LinearLayout mGoodsPickUpLayout;
    @BindView(R.id.fragment_goods_gift_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_goods_comment_layout)
    LinearLayout mGoodsCommentLayout;
    @BindView(R.id.fragment_comment_count)
    TextView mGoodsCommentCount;
    @BindView(R.id.circleImageView)
    CircleImageView mGoodsCommentUserPhoto;
    @BindView(R.id.fragment_comment_user_name)
    TextView mGoodsCommentUserName;
    @BindView(R.id.fragment_comment_user_rank)
    ImageView mGoodsCommentUserRank;
    @BindView(R.id.comment_content)
    TextView mGoodsCommentContent;
    @BindView(R.id.comment_time)
    TextView mGoodsCommentTime;
    @BindView(R.id.fragment_comment_check_more)
    TextView mGoodsCommentCheckMore;

    @BindView(R.id.fragment_goods_shop_normal_info_layout)
    RelativeLayout mShopLayout;
    @BindView(R.id.fragment_goods_shop_logo)
    ImageView mShopLogo;
    @BindView(R.id.fragment_goods_shop_name)
    TextView mShopName;
    @BindView(R.id.fragment_goods_shop_type)
    TextView mShopType;
    @BindView(R.id.fragment_goods_shop_logistics_score)
    TextView mShopLogisticsScore;
    @BindView(R.id.fragment_goods_shop_desc_score)
    TextView mShopDescScore;
    @BindView(R.id.fragment_goods_all_products)
    TextView mShopGoodsCount;
    @BindView(R.id.fragment_goods_all_products_layout)
    LinearLayout mShopGoodsLayout;
    @BindView(R.id.fragment_goods_collect_number)
    TextView mShopCollectCount;
    @BindView(R.id.fragment_goods_shop_service_score)
    TextView mShopServiceScore;
    @BindView(R.id.fragment_goods_shop_send_score)
    TextView mShopSendScore;
    @BindView(R.id.fragmnet_goods_collect_button)
    LinearLayout mShopCollectButton;
    @BindView(R.id.fragment_goods_enter_shop_button)
    LinearLayout mShopEnterButton;
    @BindView(R.id.ic_shop_collection)
    ImageView mIcShopCollection;
    @BindView(R.id.ic_shop_collection_tip)
    TextView mIcShopCollectionTip;
    @BindView(R.id.btn_share_layout)
    LinearLayout mShareButton;
    @BindView(R.id.fragment_goods_shop_fans_layout)
    LinearLayout mShopFansLayout;
    @BindView(R.id.linearLayout_price_sales)
    View mPriceLayout;


    /**
     * 倒计时 布局
     * 倒计时结束 替换背景  隐藏linearlayout
     */
    @BindView(R.id.fragment_goods_group_buy_countdown)
    RelativeLayout mCountDown;

    /***
     * 倒计时开始 布局
     */
    @BindView(R.id.lin_ac_count_time)
    LinearLayout mLayout_CountTime;
    @BindView(R.id.cv_countdownViewTestHasBg)
    CountdownView mCvCountdownViewTestHasBg;
    @BindView(R.id.tv_count_text)
    TextView mTextView_CountText;

    /***
     * 活动未开始 预告 布局
     */
    @BindView(R.id.lin_ac_hint)
    LinearLayout mLayout_AcHint;
    @BindView(R.id.tv_ac_hint)
    TextView mTextView_AcHint;

    /***
     * 活动已结束 提示文本
     */
    @BindView(R.id.tv_ac_over_text)
    TextView mTextView_OverText;


//    @BindView(R.id.fragment_goods_group_buy_price)
//    TextView mGroupBuyPrice;

//    @BindView(R.id.fragment_group_buy_market_price)
//    TextView mGroupBuyMarketPrice;

//    @BindView(R.id.fragment_group_buy_sale_count)
//    TextView mGroupBuySaleCount;

    @BindView(R.id.fragment_user_groupon_detail_check)
    TextView mViewGrouponRules;
    @BindView(R.id.viewPagerLayout)
    RelativeLayout viewPagerLayout;

    @BindView(R.id.groupon_rules_result)
    TextView grouponResultTip;
    @BindView(R.id.groupon_rules_image)
    ImageView grouponImageView;
    @BindView(R.id.groupon_rules_result1)
    TextView grouponResultTip1;
    @BindView(R.id.groupon_rules_image1)
    ImageView grouponImageView1;

    @BindView(R.id.fragment_goods_shop_info_layout)
    View fragment_goods_shop_info_layout;

    @BindView(R.id.fragment_goods_contract_list)
    RelativeLayout contractListLayout;

    @BindView(R.id.fragment_contract_list_recyclerView)
    CommonRecyclerView mContractListRecyclerView;

    @BindView(R.id.layout_fragment_goods_item)
    LinearLayout layout_fragment_goods_item;

    //砍价商品 倒计时布局内商品信息
    @BindView(R.id.rela_bargain_good)
    RelativeLayout mLayout_BargainGood;
    @BindView(R.id.tv_bargain_floor_price)
    TextView mText_BargainFloorPrice;
    @BindView(R.id.tv_bargain_price_time)
    TextView mText_BargainPrice_Time;
    @BindView(R.id.tv_bargain_in_time)
    TextView mText_BargainIn_Time;

    //砍价商品
    @BindView(R.id.img_bargain_img)
    ImageView mImage_BargainImg;

    @BindView(R.id.tv_bargain_help_name)
    TextView mText_Bargain_HelpName;

    @BindView(R.id.tv_bargain_price)
    TextView mText_BargainPrice;
    @BindView(R.id.tv_bargain_invent)
    TextView mText_BargainInvent;
    @BindView(R.id.tv_bargain_in)
    TextView mText_BargainIn;
    @BindView(R.id.tv_bargain_help)
    TextView mText_BargainHelp;
    @BindView(R.id.tv_bargain_rule)
    TextView mText_BargainRule;//砍价规则
    @BindView(R.id.tv_bargain_list)
    TextView mText_BargainList;//砍价名单

    @BindView(R.id.fragment_goods_bargain_item)
    RelativeLayout layout_bargain_item;


    @BindView(R.id.layout_fragment_goods_disable_layout)
    LinearLayout layout_fragment_goods_disable_layout;

    @BindView(R.id.fragment_goods_mix_layout)
    View fragment_goods_mix_layout;
    @BindView(R.id.fragment_goods_mix_textView)
    TextView fragment_goods_mix_textView;
    //按销量和收藏数展示商品
    @BindView(R.id.layout_fragment_goods_top_list)
    View topView;
    @BindView(R.id.fragment_goods_top_view)
    View topGap;
    /**
     * 销售量
     */
    @BindView(R.id.fragment_goods_top_sale)
    TextView topSaleTextView;
    /**
     * 收藏数
     */
    @BindView(R.id.fragment_goods_top_collect)
    TextView topCollectTextView;
    @BindView(R.id.fragment_goods_top_recyclerView)
    RecyclerView topRecyclerView;
    @BindView(R.id.fragment_goods_top_viewPager)
    CustomViewPager topCustomViewPager;
    @BindView(R.id.fragment_goods_top_iconPageIndicator)
    com.viewpagerindicator.CirclePageIndicator topCirclePageIndicator;


//    @BindView(R.id.linearlayout_countdown)
//    View linearlayout_countdown;

//    @BindView(R.id.fragment_group_buy_tip)
//    TextView fragment_group_buy_tip;

//    @BindView(R.id.fragment_group_buy_market_price_prefix)
//    TextView fragment_group_buy_market_price_prefix;

//    @BindView(R.id.relativeLayoutLeftInfo)
//    View relativeLayoutLeftInfo;

    @BindView(R.id.linearlayout_discount)
    View linearlayout_discount;

    @BindView(R.id.fragment_promotion_info)
    View fragment_promotion_info;

    @BindView(R.id.textView_activity_name)
    TextView textView_activity_name;
    @BindView(R.id.textView_activity_discount)
    TextView textView_activity_discount;
    @BindView(R.id.textView_activity_starttime)
    TextView textView_activity_starttime;
    @BindView(R.id.linearlayout_activity_countdown)
    View linearlayout_activity_countdown;
    @BindView(R.id.view_activity_countdown)
    CountdownView view_activity_countdown;

    @BindView(R.id.ll_member_price)
    View ll_member_price;
    @BindView(R.id.tv_member_price_message)
    TextView tv_member_price_message;
    @BindView(R.id.ll_full_cut)
    View ll_full_cut;
    @BindView(R.id.tv_reduce_cash)
    View tv_reduce_cash;
    @BindView(R.id.tv_freight_free)
    View tv_freight_free;
    @BindView(R.id.tv_gift)
    View tv_gift;

    //堂内点餐
    private String rc_id;
    //统计商品访问时长数据
    private String card_user_id;//名片的用户ID

    public String goods_id;
    public String sku_id;


    public String bargainId;//砍价商品id
    public String merchants_code;//商家code

    private ArrayList<String> goods_banner;
    private String shopId;
    private String addressName;
    private String addressCode;
    private String tel;
    private OnClickListener onClickListener;
    public ResponseGoodsModel mResponseGoodsModel;
    private String addCartNumber;
    private String quickBuyGoodsNumber;
    private String quickBuySkuId;
    private RefreshListener mRefreshListener;
    private boolean booleanTag;

    private List<TextView> tabTextList;
    private float fromX;
    private Fragment nowFragment;
    private int nowIndex;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsIndexAttrDetailFragment goodsConfigFragment;
    private GoodsIndexImageDetailFragment goodsInfoWebFragment;
    private GoodsActivity activity;
    private SkuModel sku;
    private boolean typeGroupOn;
    private boolean buyEnable = true;
    private String enableButtonContent;
    private LinearLayoutManager mLinearLayoutManager;
    private GoodsGiftListAdapter mGoodsGiftAdapter;
    private boolean hasRankPrice;

    private GoodsContractListAdapter mContractListAdapter;
    private String mRegionName;
    private String mRegionCode;
    private List<TopGoodsModel> saleTopModel;
    private List<TopGoodsModel> collectTopModel;
    public static ArrayList<RankPriceModel> rankPrices;
    private int topType;

    /***砍价商品 分享信息**/
    private String bargain_name;

    /***砍价商品 分享data**/
    private ArrayList<String> mShareData_Bargain = new ArrayList<>();

    /***帮砍lsit*/
    private List<HelpBargainModel> mHelpBargainLists = new ArrayList<>();

    /***帮砍list第一条data**/
    private HelpBargainModel help_bargain_model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods;
        mAttrData = new ArrayList<>();
        shareData = new ArrayList<>();
        initId();
    }

    private void initId() {
        Bundle arguments = getArguments();
        sku_id = arguments.getString(Key.KEY_SKU_ID.getValue());
        goods_id = arguments.getString(Key.KEY_GOODS_ID.getValue());

        rc_id = arguments.getString("rc_id");
        card_user_id = arguments.getString("card_user_id");

        if (!Utils.isNull(sku_id)) {
            refresh();
            getGoodsDesc(sku_id);
        } else if (!Utils.isNull(goods_id)) {
            getSkuId(goods_id);
        } else {
            Toast.makeText(getContext(), "该商品已下架", Toast.LENGTH_SHORT).show();
            finish();
        }

//        //隐藏购买按钮
//        if (!App.getInstance().isUserBuy) {
//            GoodsActivity.doBuyView.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setDetailData();
        initData();
        if (goodsInfoWebFragment.getArguments() == null) {
            goodsInfoWebFragment.setArguments(getActivity().getIntent().getExtras());
        } else {
            goodsInfoWebFragment.getArguments().putAll(getActivity().getIntent().getExtras());
        }
        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment)
                .commitAllowingStateLoss();
        mGoUp.setVisibility(View.INVISIBLE);
//        mSlideLayout.setOnSlideDetailsListener(this);
        mGoUp.setOnClickListener(this);
        tv_goods_config.setOnClickListener(this);
        tv_goods_detail.setOnClickListener(this);
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);
        mGoodsBonusLayout.setOnClickListener(this);
        mGoodsSpecLayout.setOnClickListener(this);

        mGoodsRegionLayout.setOnClickListener(this);
        mGoodsCommentCheckMore.setOnClickListener(this);

        mShopLayout.setOnClickListener(this);
        mShopCollectButton.setOnClickListener(this);
        mShopEnterButton.setOnClickListener(this);
        mShopGoodsLayout.setOnClickListener(this);

        mShareButton.setOnClickListener(this);

        videoShowButton.setOnClickListener(this);

        mShopFansLayout.setOnClickListener(this);
        mViewGrouponRules.setOnClickListener(this);

        topSaleTextView.setOnClickListener(this);
        topCollectTextView.setOnClickListener(this);

        viewPagerLayout.getLayoutParams().height = Utils.getWindowWidth(getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {

        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_goods_detail:
                //商品详情tab
                nowIndex = 0;
                switchFragment(nowFragment, goodsInfoWebFragment);
                nowFragment = goodsInfoWebFragment;
                scrollCursor();
                break;
            case R.id.tv_goods_config:
                //规格参数tab
                nowIndex = 1;
                switchFragment(nowFragment, goodsConfigFragment);
                nowFragment = goodsConfigFragment;
                scrollCursor();
                break;
            case R.id.go_up_button:
                //点击滑动到顶部
                mScrollView.smoothScrollTo(0, 0);
//                mSlideLayout.smoothClose(true);
                scrollCursor();
                break;
            case R.id.fragment_goods_index_video_show:
                openVideoFullActivity();
                break;
            case R.id.fragment_goods_bonus_layout:
                mGoodsBonusLayout.setEnabled(false);
                openBonusActivity();
                mGoodsBonusLayout.setEnabled(true);
                break;
            case R.id.fragment_goods_spec_layout:
                //单击属性弹出窗口处理：拼团活动弹出按钮需要特殊处理，其他活动照常
                if ("1".equals(sku.sales_model)) {
                    openAttributeWholesaleActivity(Macro.BUTTON_TYPE_ATTRIBUTE);
                } else {
                    if (!Utils.isNull(sku.activity) && !sku.activity.act_code.equals("")) {
                        if (sku.activity.act_type == Macro.OT_FIGHT_GROUP) {
                            openAttributeActivity(Macro.BUTTON_TYPE_GROUP_ON_ATTRIBUTE);
                        } else {
                            openAttributeActivity(Macro.BUTTON_TYPE_ATTRIBUTE);
                        }
                    } else {
                        openAttributeActivity(Macro.BUTTON_TYPE_ATTRIBUTE);
                    }
                }
                break;
            case R.id.fragment_goods_region_layout:
                openRegionActivity();
                break;
            case R.id.fragment_comment_check_more:
                if (onClickListener != null) {
                    onClickListener.onClickListener(sku_id);
                }
                break;
            case R.id.fragment_goods_shop_normal_info_layout:
                openShopActivity(shopId);
                break;
            case R.id.fragmnet_goods_collect_button:
                collectShop(shopId);
                break;
            case R.id.fragment_goods_enter_shop_button:
                openShopActivity(shopId);
                break;
            case R.id.fragment_goods_all_products_layout:
                openShopGoodsActivity(shopId);
                break;
            /**分享商品**/
            case R.id.btn_share_layout:
                ShareFragment.lastActivity = this;
                startActivity(new Intent(getActivity(), ShareActivity.class)
                        .putStringArrayListExtra(ShareActivity.SHARE_DATA, shareData).putExtra
                                (ShareActivity.SHARE_TYPE, ShareActivity.TYPE_GOODS));
                break;

            case R.id.fragment_goods_shop_fans_layout:
                openShopActivity(shopId);
                break;
            case R.id.fragment_user_groupon_detail_check:
                openGrouponRulesActivity();
                break;
            case R.id.fragment_goods_top_sale:
                topGoodsShow(0);
                break;
            case R.id.fragment_goods_top_collect:
                topGoodsShow(1);
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extra = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_INDEX:
                        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX
                                .getValue()));
                        this.finish();
                        break;
                    case VIEW_TYPE_SHOP:
                        openShopActivity(shopId);
                        break;
                    case VIEW_TYPE_GO_FIGHT_GROUP:
                        openUserGroupOnDetailFragment(position);
                        break;
                    case VIEW_TYPE_LOGIN:
                        openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
                        break;
                    case VIEW_TYPE_MESSAGE:
                        Toast.makeText(getActivity(), enableButtonContent, Toast.LENGTH_SHORT).show();
                        break;
                    case VIEW_TYPE_GIFT:
                        Intent intent = new Intent();
                        intent.putExtra(Key.KEY_SKU_ID.getValue(), extra + "");
                        intent.setClass(getActivity(), GoodsActivity.class);
                        startActivity(intent);
                        break;
                    case VIEW_TYPE_ADD_TO_CART:
                        //商品下架时，sku.is_enable 值为0；点击加入购物车按钮需要提示
                        if (sku.is_enable.equals("0")) {
                            Toast.makeText(getActivity(), getActivity().getResources().getString
                                    (R.string.goodsOffStore), Toast.LENGTH_SHORT).show();
                        } else {
                            if ("1".equals(sku.sales_model)) {
                                openAttributeWholesaleActivity(Macro.BUTTON_TYPE_ADD_TO_CART);
                            } else {
                                GoodsActivity.mAddToCartButton.setEnabled(false);
                                if (!Utils.isNull(sku.activity) && !sku.activity.act_code.equals("")) {
                                    if (sku.activity.act_code.equals("fight_group")) {
                                        openAttributeActivity(Macro.BUTTON_TYPE_GROUP_ON_SINGLE);
                                    } else {
                                        openAttributeActivity(Macro.BUTTON_TYPE_ADD_TO_CART);
                                    }
                                } else {
                                    openAttributeActivity(Macro.BUTTON_TYPE_ADD_TO_CART);
                                }
                            }
                        }
                        break;
                    case VIEW_TYPE_BUY_NOW:
                        //商品下架时，sku.is_enable 值为0；点击加入购物车按钮需要提示
                        if (!App.getInstance().isLogin()) {
                            GoodsActivity.mBuyNowButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                }
                            });


                        }

                        if (sku.is_enable.equals("0")) {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.goodsOffStore), Toast.LENGTH_SHORT).show();
                        } else {
                            if ("1".equals(sku.sales_model)) {
                                openAttributeWholesaleActivity(Macro.BUTTON_TYPE_BUY_NOW);
                            } else {
                                GoodsActivity.mBuyNowButton.setEnabled(false);
                                if (!Utils.isNull(sku.activity) && !sku.activity.act_code.equals("")) {

                                    if (sku.activity.act_code.equals("fight_group")) {
                                        openAttributeActivity(Macro.BUTTON_TYPE_GROUP_ON_NOW);
                                    } else {
                                        openAttributeActivity(Macro.BUTTON_TYPE_BUY_NOW);
                                    }
                                } else {
                                    openAttributeActivity(Macro.BUTTON_TYPE_BUY_NOW);
                                }
                            }
                        }
                        break;

                    case VIEW_TYPE_BARGAIN:
                        /***立即砍价*/
                        bargain_now();
                        break;
                    case VIEW_TYPE_HELP_BARGAIN:
                        /***找人帮砍 分享商品链接+bar_id(微信好友+微信朋友圈)*/
                        mShareData_Bargain.add(Utils.getMallMBaseUrl() + "/goods-" + goods_id + ".html?bar_id=" + bargainId + "&invite_code=" + merchants_code);
                        mShareData_Bargain.add(bargain_name);
                        mShareData_Bargain.add(mResponseGoodsModel.data.share.seo_goods_discription);
                        mShareData_Bargain.add(Config.BASE_URL + "/images/bargain/bargain_share_img.jpg");

                        Intent mIntent = new Intent(getActivity(), ShareActivity.class);
                        mIntent.putStringArrayListExtra(ShareActivity.SHARE_DATA, mShareData_Bargain);
                        mIntent.putExtra(ShareActivity.SHARE_SCOPE, 1);
                        mIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_SOURCE);
                        startActivity(mIntent);
                        break;
                    case VIEW_TYPE_PICK_UP:
                        openPickUp();
                        break;
                    case VIEW_TYPE_GOODS:
                        openGoods(extra);
                        break;
                }
                break;
        }
    }

    private void openUserGroupOnDetailFragment(int position) {

        if (!TextUtils.isEmpty(sku.activity.params)) {
            try {
                ParamsModel paramsModel = JSON.parseObject(sku.activity.params, ParamsModel.class);

                Intent intent = new Intent();
                intent.putExtra(Key.KEY_GROUP_SN.getValue(), paramsModel.groupon_log_list.get
                        (position).group_sn);
                intent.setClass(getActivity(), UserGroupOnDetailActivity.class);
                startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    private void openGoods(int position) {
        TopGoodsModel dataModel;
        if (topType == 0) {
            dataModel = saleTopModel.get(position);
        } else {
            dataModel = collectTopModel.get(position);
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), GoodsActivity.class);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), dataModel.sku_id);
        startActivity(intent);
    }

    private void topGoodsShow(int type) {
        topType = type;
        //按销量显示
        if (type == 0) {
            topSaleTextView.setSelected(true);
            topCollectTextView.setSelected(false);

            if (saleTopModel.size() > 3) {
                topCustomViewPager.setVisibility(View.VISIBLE);
                topCirclePageIndicator.setVisibility(View.VISIBLE);
                topRecyclerView.setVisibility(View.GONE);

                GoodsTopAdapter adapter = new GoodsTopAdapter(saleTopModel);
                adapter.onClickListener = this;
                topCustomViewPager.setAdapter(adapter);
                topCirclePageIndicator.setViewPager(topCustomViewPager);
            } else {

                topCustomViewPager.setVisibility(View.VISIBLE);
                topCirclePageIndicator.setVisibility(View.GONE);
                topRecyclerView.setVisibility(View.GONE);

                GoodsTopAdapter adapter = new GoodsTopAdapter(saleTopModel);
                adapter.onClickListener = this;
                topCustomViewPager.setAdapter(adapter);
                topCirclePageIndicator.setViewPager(topCustomViewPager);
            }
        } else {
            //按收藏显示
            topSaleTextView.setSelected(false);
            topCollectTextView.setSelected(true);

            if (collectTopModel.size() > 3) {
                topCustomViewPager.setVisibility(View.VISIBLE);
                topCirclePageIndicator.setVisibility(View.VISIBLE);
                topRecyclerView.setVisibility(View.GONE);

                GoodsTopAdapter adapter = new GoodsTopAdapter(collectTopModel);
                adapter.onClickListener = this;
                topCustomViewPager.setAdapter(adapter);
                topCirclePageIndicator.setViewPager(topCustomViewPager);
            } else {
                topCustomViewPager.setVisibility(View.VISIBLE);
                topCirclePageIndicator.setVisibility(View.GONE);
                topRecyclerView.setVisibility(View.GONE);

                GoodsTopAdapter adapter = new GoodsTopAdapter(collectTopModel);
                adapter.onClickListener = this;
                topCustomViewPager.setAdapter(adapter);
                topCirclePageIndicator.setViewPager(topCustomViewPager);
            }
        }
    }

    private void openGrouponRulesActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ACT_ID.getValue(), sku.activity.act_id);
        intent.setClass(getActivity(), GroupOnRulesActivity.class);
        startActivity(intent);
    }

    /**
     * 切换Fragment
     * <p>(hide、show、add)
     *
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fl_content, toFragment)
                        .commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss()
                ; // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private void initData() {
        fragmentList = new ArrayList<>();
    }

    public void setDetailData() {
        goodsConfigFragment = new GoodsIndexAttrDetailFragment();
        goodsInfoWebFragment = new GoodsIndexImageDetailFragment();
        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);
        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
    }


    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            mGoUp.setVisibility(View.VISIBLE);
            activity.mScrollView.setNoScroll(true);
            activity.mTitleTextView.setVisibility(View.VISIBLE);
            activity.mTabView.setVisibility(View.GONE);
        } else {
            //当前为商品详情页
            mGoUp.setVisibility(View.INVISIBLE);
            activity.mScrollView.setNoScroll(false);
            activity.mTitleTextView.setVisibility(View.GONE);
            activity.mTabView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 滑动游标
     */
    private void scrollCursor() {
//        TranslateAnimation anim = new TranslateAnimation(fromX, nowIndex * v_tab_cursor
// .getWidth(), 0, 0);
//        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
//        anim.setDuration(50);
//        //保存动画结束时游标的位置,作为下次滑动的起点
//        fromX = nowIndex * v_tab_cursor.getWidth();
//        v_tab_cursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color
                    .colorPrimary) : getResources().getColor(R.color.colorOne));
        }
    }

    /****
     * 加入购物车
     * @param skuId
     * @param goodsNumber
     */
    public void addToCart(String skuId, String goodsNumber) {
        String api = Api.API_ADD_TO_CART;
        if (!TextUtils.isEmpty(rc_id)) {
            api = Api.API_REACHBUY_ADD_TO_CART;
        }
        CommonRequest mAddToCartRequest = new CommonRequest(api, HttpWhat
                .HTTP_ADD_TO_CART.getValue(), RequestMethod.POST);
        mAddToCartRequest.add("sku_id", skuId);
        mAddToCartRequest.add("number", goodsNumber);
        mAddToCartRequest.setAjax(true);
        addRequest(mAddToCartRequest);
    }

    public void collectGoods(String goodsId, String skuId) {
        CommonRequest mGoodsCollectRequest = new CommonRequest(Api.API_COLLECT_TOGGLE,
                HttpWhat.HTTP_COLLECT.getValue(), RequestMethod.POST);
        mGoodsCollectRequest.add("goods_id", goodsId);
        mGoodsCollectRequest.add("sku_id", skuId);
        mGoodsCollectRequest.add("show_count", 1);
        mGoodsCollectRequest.setAjax(true);
        addRequest(mGoodsCollectRequest);
    }

    public void collectShop(String shopId) {
        CommonRequest request = new CommonRequest(Api.API_COLLECT_TOGGLE, HttpWhat
                .HTTP_COLLECT_SHOP.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("shop_id", shopId);
        addRequest(request);
    }

    public void collectShopCallback(String response) {
        HttpResultManager.resolve(response, ResponseFollowModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseFollowModel>() {
            @Override
            public void onSuccess(ResponseFollowModel model) {
                if (model.data == 1) {
                    if (!Utils.isNull(model.bonus_info) && !Utils.isNull(model.bonus_info
                            .bonus_amount)) {
                        openCollectBonus(model.bonus_info.bonus_number, model.bonus_info
                                .bonus_amount_format, model.bonus_info.bonus_name);
                    }
                    mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_selected);
                    mIcShopCollectionTip.setText(R.string.unfollow);
                    Toast.makeText(getActivity(), model.message, Toast.LENGTH_SHORT).show();

                    mShopCollectCount.setText((Integer.parseInt(mShopCollectCount.getText().toString()) + 1) + "");
                } else {
                    mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_normal);
                    mIcShopCollectionTip.setText(R.string.saveThisSeller);
                    Toast.makeText(getActivity(), model.message, Toast.LENGTH_SHORT).show();
                    int number = (Integer.parseInt(mShopCollectCount.getText().toString()) - 1);
                    if (number <= 0) {
                        mShopCollectCount.setText("0");
                    } else {
                        mShopCollectCount.setText(number + "");
                    }
                }
            }

            @Override
            public void onLogin() {
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP);
            }
        }, true);
    }

    public void copyUri(Uri copyUri) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newUri(getActivity().getContentResolver(), "URI", copyUri);
        clipboard.setPrimaryClip(clip);
        Utils.makeToast(getActivity(), R.string.copySuccess);
    }

    public void freightFee(String skuId, String regionCode) {
        CommonRequest request = new CommonRequest(Api.API_GOODS_CHANGE_LOCATION, HttpWhat
                .HTTP_SHIPPING_FEE.getValue());
        request.add("sku_id", skuId);
        request.add("region_code", regionCode);
        request.alarm = false;
        addRequest(request);
    }

    public void goCheckout() {
        Intent intent = new Intent();

        if (!TextUtils.isEmpty(rc_id)) {
            intent.putExtra("rc_id", rc_id);
            intent.setClass(getActivity(), CheckoutFreeActivity.class);
//                intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
            startActivity(intent);
        } else {
            intent.setClass(getActivity(), CheckoutActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
            case EVENT_REFRESH_GOODS:
                refresh();

                break;
        }
    }

    //地址选择
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_REGION_CODE:
                mGoodsRegionLayout.setEnabled(true);
                if (data != null) {
                    Bundle addressData = data.getExtras();
                    addressName = addressData.getString(new RegionFragment().KEY_REGION_LIST);
                    addressCode = addressData.getString(new RegionFragment().KEY_REGION_CODE);

                    mResponseGoodsModel.data.goods.region_name = addressName;
                    mResponseGoodsModel.data.goods.region_code = addressCode;

                    mGoodsRegion.setText(addressName);
                    freightFee(sku_id, addressCode);
                }
                break;
            case REQUEST_CODE_ADD_CART:
                mGoodsSpecLayout.setEnabled(true);
                GoodsActivity.mAddToCartButton.setEnabled(true);
                GoodsActivity.mBuyNowButton.setEnabled(true);
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());
                    if (resultModel.resultType.equals(Macro.RESULT_TYPE_ADD_TO_CART)) {
                        mGoodsSpecList.setText(resultModel.specValue);
                        addCartNumber = resultModel.goodsNumber;
                        addToCart(resultModel.skuId, resultModel.goodsNumber);
                        sku_id = resultModel.skuId;
                        refresh();
                    } else if (resultModel.resultType.equals(Macro.RESULT_TYPE_BUY_NOW)) {
                        quickBuySkuId = resultModel.skuId;
                        quickBuyGoodsNumber = resultModel.goodsNumber;
                        if (!Utils.isNull(sku.activity)) {
                            if (sku.activity.act_code.equals("fight_group")) {
                                typeGroupOn = false;
                            }
                        }
                        quickBuy(resultModel.skuId, resultModel.goodsNumber);

                    } else if (resultModel.resultType.equals(Macro.RESULT_TYPE_BUY_NOW_GROUP)) {
                        quickBuySkuId = resultModel.skuId;
                        quickBuyGoodsNumber = resultModel.goodsNumber;
                        if (!Utils.isNull(sku.activity)) {
                            if (sku.activity.act_code.equals("fight_group")) {
                                typeGroupOn = true;
                            }
                        }
                        quickBuy(resultModel.skuId, resultModel.goodsNumber);
                    } else if (resultModel.resultType.equals(Macro
                            .RESULT_TYPE_ATTRIBUTE_SELECTED)) {
                        sku_id = resultModel.skuId;
                        refresh();
                    }
                }
                break;
            case REQUEST_CODE_TENCENT_SHARE:
            case REQUEST_CODE_TENCENT_QZONE_SHARE:
            case REQUEST_CODE_WEIXIN_SHARE:
            case REQUEST_CODE_WEIXIN_CIRCLE_SHARE:
            case REQUEST_CODE_WEIBO_SHARE:
                shareCallback(resultCode, data);
                break;
            case REQUEST_CODE_LOGIN_FOR_QUICK_BUY:
                if (resultCode == Activity.RESULT_OK) {
                    if (!Utils.isNull(sku.activity)) {
                        if (sku.activity.act_code.equals("fight_group")) {
                            typeGroupOn = true;
                        }
                    }
                    quickBuy(quickBuySkuId, quickBuyGoodsNumber);
                }
                break;
            case REQUEST_CODE_TAKE_BONUS:
                if (resultCode == Activity.RESULT_OK) {
                    refresh();
                }
                break;
            case REQUEST_CODE_LOGIN_FOR_COLLECTION:
                if (resultCode == Activity.RESULT_OK) {
                    collectGoods(goods_id, sku_id);
                }
                break;
            case REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP:
                if (resultCode == Activity.RESULT_OK) {
                    collectShop(shopId);
                }
                break;
            case REQUEST_CODE_LOGIN_FOR_REFRESH:
                if (resultCode == Activity.RESULT_OK) {
                    refresh();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SKU_ID:
                getSkuIdCallback(response);
                break;
            case HTTP_GOODS_DESC:
                updateGoodsDesc(response);
                break;
            case HTTP_GOODS:
                refreshCallBack(response);
                break;
            case HTTP_SHIPPING_FEE:
                feeCallback(response);
                break;
            case HTTP_COLLECT_SHOP:
                collectShopCallback(response);
                break;
            case HTTP_COLLECT:
                collectCallback(response);
                break;
            case HTTP_ADD_TO_CART:
                addToCartCallback(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            case HTTP_ORDER_CANCEL:
                cancelGrouponCallback(response);
                break;
            case HTTP_GET_REGION_NAME:
                refreshRegionNameCallback(response);
                break;
            case HTTP_BARGAIN_NOW:
                bargainNowCallBack(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        super.onRequestFailed(what, response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SKU_ID:

                break;
            case HTTP_GOODS:

                break;
        }
    }

    private void quickBuyCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                goCheckout();
            }

            @Override
            public void onLogin() {
//                super.onLogin();
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_QUICK_BUY);

            }

        }, true);
    }

    private void addToCartCallback(String response) {
        HttpResultManager.resolve(response, AddToCartModel.class, new HttpResultManager
                .HttpResultCallBack<AddToCartModel>() {
            @Override
            public void onSuccess(AddToCartModel back) {
                Utils.makeToast(getActivity(), back.message);
                App.addCartNumber(addCartNumber, this);
            }
        }, true);
    }

    private void collectCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                if (back.data.equals("1")) {
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                    GoodsActivity.mCollectImage.setImageResource(R.mipmap.ic_star_selected);
                    GoodsActivity.mCollectTip.setText("已收藏");
                } else {
                    Toast.makeText(getActivity(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                    GoodsActivity.mCollectImage.setImageResource(R.mipmap.ic_star_normal);
                    GoodsActivity.mCollectTip.setText("收藏");
                }
            }

            @Override
            public void onLogin() {
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_COLLECTION);
            }
        }, true);
    }

    private void feeCallback(String response) {
        HttpResultManager.resolve(response, ResponseShippingFeeModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseShippingFeeModel>() {
            @Override
            public void onSuccess(ResponseShippingFeeModel responseShippingFeeModel) {
                booleanTag = (responseShippingFeeModel.data.limit_sale == 1 &&
                        responseShippingFeeModel.data.is_last == 1);
                mGoodsFreightTitle.setText(responseShippingFeeModel.data.freight_info.replace("￥", "¥"));
            }
        });
    }

    private void getGoodsDesc(String skuId) {
        CommonRequest mGoodsDescRequest = new CommonRequest(Api.API_GOODS_DESC, HttpWhat
                .HTTP_GOODS_DESC.getValue());
        mGoodsDescRequest.add("sku_id", skuId);
        mGoodsDescRequest.alarm = false;
        addRequest(mGoodsDescRequest);
    }

    private void getSkuIdCallback(String response) {
        ResponseGoodsModel responseGoodsModel = JSON.parseObject(response, ResponseGoodsModel
                .class);

        if (responseGoodsModel.code == 0) {
            sku_id = responseGoodsModel.data.goods.sku_id;
            refresh();
            getGoodsDesc(sku_id);
        } else {
            getActivity().finish();
            Intent intent = new Intent();
            intent.setClass(getActivity(), InvalidActivity.class);
            intent.putExtra("error_info", responseGoodsModel.message);
            startActivity(intent);
        }
    }

    public void openBonusActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SHOP_NAME.getValue(), mResponseGoodsModel.data.shop_info.shop
                .shop_name);
        intent.putParcelableArrayListExtra(Key.KEY_BONUS_LIST.getValue(), mResponseGoodsModel
                .data.bonus_list);
        intent.setClass(getActivity(), GoodsBonusActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_BONUS.getValue());
    }

    public void openVideoFullActivity() {
        Intent intent = new Intent(getContext(), VideoFullActivity.class);
        intent.putExtra(VideoFullActivity.VIDEO_TITLE, "商品详情视频");
        intent.putExtra(VideoFullActivity.VIDEO_URL, mResponseGoodsModel.data.goods.goods_video);
        startActivity(intent);
    }

    public void openRegionActivity() {
        mGoodsRegionLayout.setEnabled(false);
        Intent intent = new Intent(getContext(), RegionActivity.class);

        String region_code = "";
        try {
            region_code = mResponseGoodsModel.data.goods.region_code;
        } catch (Exception e) {

        }
        intent.putExtra(RegionFragment.KEY_REGION_CODE, region_code);
        intent.putExtra(RegionFragment.KEY_API, Api.API_REGION_LIST);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_REGION_CODE.getValue());
    }

    public void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    public void openShopGoodsActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        intent.putExtra(Key.KEY_SHOP_ALL_GOODS.getValue(), "1");
        startActivity(intent);
    }

    /****
     * 加入订单
     * @param skuId
     * @param goodsNumber
     */
    public void quickBuy(String skuId, String goodsNumber) {
        String api = Api.API_QUICK_BUY;
        if (!TextUtils.isEmpty(rc_id)) {
            api = Api.API_REACHBUY_QUICK_BUY;
        }

        CommonRequest mQuickBuyRequest = new CommonRequest(api, HttpWhat.HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        if (!TextUtils.isEmpty(rc_id)) {
            mQuickBuyRequest.add("rc_id", rc_id);
        }
        mQuickBuyRequest.add("sku_id", skuId);
        mQuickBuyRequest.add("number", goodsNumber);
        if (typeGroupOn) {
            mQuickBuyRequest.add("group_sn", 0);
        }
        if (!TextUtils.isEmpty(bargainId)) {
            mQuickBuyRequest.add("bar_id", bargainId);
        }

        addRequest(mQuickBuyRequest);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void getSkuId(String goodsId) {
        CommonRequest mGoodsDetailRequest = new CommonRequest(Config.BASE_URL + "/goods/" +
                goodsId, HttpWhat.HTTP_GET_SKU_ID.getValue());
        mGoodsDetailRequest.alarm = false;
        addRequest(mGoodsDetailRequest);
    }

    private void openAttributeActivity(int buttonType) {
        Intent intent = new Intent();

        //属性传值
        ArrayList<com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> skuList = new ArrayList<>();
        if (mResponseGoodsModel.data.goods.sku_list != null) {
            for (Map.Entry<String, com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> entry
                    : mResponseGoodsModel.data.goods.sku_list.entrySet()) {
                skuList.add(mResponseGoodsModel.data.goods.sku_list.get(entry.getKey()));
            }
        }
        intent.putParcelableArrayListExtra(Key.KEY_SKU_LIST.getValue(), skuList);
        ArrayList<SpecificationModel> specificationList = new ArrayList<>();
        if (mResponseGoodsModel.data.goods.spec_list != null) {
            specificationList.addAll(mResponseGoodsModel.data.goods.spec_list);
        }
        intent.putParcelableArrayListExtra(Key.KEY_SPECIFICATION_LIST.getValue(), specificationList);

        intent.putExtra(Key.KEY_SKU_ID.getValue(), sku_id);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goods_id);
        intent.putExtra(Key.KEY_TYPE.getValue(), buttonType);
        intent.putExtra(Key.KEY_IS_STOCK.getValue(), mResponseGoodsModel.data.show_stock);

        intent.setClass(getActivity(), AttributeActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());

    }

    private void openAttributeWholesaleActivity(int buttonType) {
        Intent intent = new Intent();

        //属性传值
        ArrayList<com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> skuList = new
                ArrayList<>();
        if (mResponseGoodsModel.data.goods.sku_list != null) {
            for (Map.Entry<String, com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> entry
                    : mResponseGoodsModel.data.goods.sku_list.entrySet()) {
                skuList.add(mResponseGoodsModel.data.goods.sku_list.get(entry.getKey()));
            }
        }
        intent.putParcelableArrayListExtra(Key.KEY_SKU_LIST.getValue(), skuList);
        ArrayList<SpecificationModel> specificationList = new ArrayList<>();
        if (mResponseGoodsModel.data.goods.spec_list != null) {
            specificationList.addAll(mResponseGoodsModel.data.goods.spec_list);
        }
        intent.putParcelableArrayListExtra(Key.KEY_SPECIFICATION_LIST.getValue(),
                specificationList);

        intent.putExtra("data", JSON.toJSONString(mResponseGoodsModel.data));
        intent.putExtra(Key.KEY_SKU_ID.getValue(), sku_id);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goods_id);
        intent.putExtra(Key.KEY_TYPE.getValue(), buttonType);

        intent.setClass(getActivity(), AttributeWholesaleActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());

    }

    private void openCollectBonus(String number, String value, String name) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), RegisterBonusActivity.class);
        intent.putExtra(Key.KEY_BONUS_NUMBER.getValue(), number);
        intent.putExtra(Key.KEY_BONUS_VALUE.getValue(), value);
        intent.putExtra(Key.KEY_BONUS_NAME.getValue(), name);
        intent.putExtra(Key.KEY_BONUS_TYPE.getValue(), 1);
        startActivity(intent);
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, requestCode.getValue());
    }

    boolean isOenServiceLoading = false;

    public void openServiceActivity() {

        if (!App.getInstance().isLogin()) {
            mRefreshListener = new RefreshListener() {
                @Override
                public void onRefreshed() {
                    openServiceActivity();
                }
            };
            openLoginActivityForResult(RequestCode.REQUEST_CODE_SERVICE);
            return;
        }

        if (mResponseGoodsModel.data.shop_info.aliim == null || Utils.isNull(mResponseGoodsModel
                .data.shop_info.aliim.aliim_uid) || Utils.isNull(mResponseGoodsModel.data
                .shop_info.aliim.aliim_appkey)) {
            Toast.makeText(getContext(), "该店铺未配置云旺客服", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isOenServiceLoading) {
            toast("正在开启客服，请稍后...");
            return;
        }

        isOenServiceLoading = true;
        final String userId = mResponseGoodsModel.data.shop_info.aliim.aliim_uid;
        final String userPassword = mResponseGoodsModel.data.shop_info.aliim.aliim_pwd;
        final String appKey = mResponseGoodsModel.data.shop_info.aliim.aliim_appkey;
        final String serviceId = mResponseGoodsModel.data.shop_info.aliim.aliim_main_customer;

        View top_view = LayoutInflater.from(getContext()).inflate(R.layout
                .custom_chat_window_top, null);
        TextView goodName = (TextView) top_view.findViewById(R.id.textView_goods_name);
        TextView goodPrice = (TextView) top_view.findViewById(R.id.textView_price);
        TextView sendURL = (TextView) top_view.findViewById(R.id.textView_send_url);
        ImageView imageView_good = (ImageView) top_view.findViewById(R.id.imageView_good);

        goodName.setText(mResponseGoodsModel.data.sku.sku_name);
        goodPrice.setText(mResponseGoodsModel.data.sku.goods_price);
        ImageLoader.displayImage(Utils.urlOfImage(mResponseGoodsModel.data.sku.sku_image),
                imageView_good);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Config.BASE_URL + "/" + sku_id, HttpWhat.HTTP_GOODS.getValue());
        if (!TextUtils.isEmpty(card_user_id)) {
            request.add("card_user_id", card_user_id);
        }
        addRequest(request);
    }

    //立即砍价
    private void bargain_now() {
        CommonRequest request = new CommonRequest(Api.API_BARGAIN_NOW, HttpWhat.HTTP_BARGAIN_NOW.getValue());
        request.add("goods_id", goods_id);
        addRequest(request);
    }

    private void bargainNowCallBack(String response) {

        BargainModel bargainInfo = JSONObject.parseObject(response, BargainModel.class);

        if (bargainInfo.code == 0) {

            //弹出框
            bargainId = bargainInfo.data.bar_info.bar_id;

            ButtonDialog buttonDialog = new ButtonDialog(getActivity());
            buttonDialog.setDialog_img(R.mipmap.bargain_now);

            buttonDialog.setDialog_text("您砍掉了   " + bargainInfo.data.bar_info.bar_amount);
            buttonDialog.setDialog_btn_text("我知道了");
            buttonDialog.show();

            //刷新商品数据
            refresh();
        } else {
            Toast.makeText(activity, bargainInfo.message, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCallBack(String response) {
        linearlayout_discount.setVisibility(View.GONE);


        final ResponseGoodsModel responseGoodsModel = JSON.parseObject(response, ResponseGoodsModel.class);

        mResponseGoodsModel = responseGoodsModel;

        if (responseGoodsModel.code == 0) {
            if (Utils.isNull(responseGoodsModel.data) || Utils.isNull(responseGoodsModel.data
                    .goods) || Utils.isNull(responseGoodsModel.data.sku)) {
                Toast.makeText(getActivity(), "该商品已下架", Toast.LENGTH_SHORT).show();
                finish();
            } else {

                if (mResponseGoodsModel != null && mResponseGoodsModel.data != null) {
                    ShopInfoModel shopInfo = mResponseGoodsModel.data.shop_info;
                    BaseClient.getInstance().setMessage(3, TextUtils.isEmpty(goods_id) ? sku_id : goods_id, "",
                            mResponseGoodsModel.data.his_id);

                    if (mResponseGoodsModel != null && "1".equals(shopInfo.aliim_enable)) {
                    } else {
//                        if (!Utils.isNull(shopInfo.shop.service_tel)) {
//                            GoodsActivity.mTelText.setText("电话");
//                            GoodsActivity.mTelImage.setImageResource(R.mipmap.ic_details_phone);
//                        } else {
//                            GoodsActivity.mTelText.setText("暂无");
//                            GoodsActivity.mTelImage.setImageResource(R.mipmap.tab_shop_contact);
//                        }
                    }
                }

                sku = responseGoodsModel.data.sku;
                final GoodsModel goods = responseGoodsModel.data.goods;
                ShopModel shop_info = responseGoodsModel.data.shop_info.shop;

                goods_id = goods.goods_id;
                //轮播图
                goods_banner = new ArrayList<>();
                int goods_thumb_number = sku.sku_images.size();
                for (int i = 0; i < goods_thumb_number; i++) {
                    goods_banner.add(sku.sku_images.get(i).get(1));
                }
                mGoodsThumbViewPager.setAdapter(new ImageAdapter());

                //设置viewpager 滑动时间
                BannerScroller scroller = new BannerScroller(getActivity());
                scroller.setTime(1500);
                scroller.initViewPagerScroll(bargin_viewPager);
                bargin_viewPager.setAdapter(new GoodsBargainAdapter(sku.getBargain_users()));

                if (!Utils.isNull(goods.goods_video)) {
                    videoShowButton.setVisibility(View.VISIBLE);
                } else {
                    videoShowButton.setVisibility(View.GONE);
                }

                mGoodsName.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showCopyWindows(v);
                        return false;
                    }
                });

                if (Utils.isNull(goods.goods_subname)) {
                    mGoodsDescription.setVisibility(View.GONE);
                } else {
                    mGoodsDescription.setVisibility(View.VISIBLE);
                    mGoodsDescription.setText(goods.goods_subname);
                }

                pageIndicator.setRadius(10);
                pageIndicator.setViewPager(mGoodsThumbViewPager);
                pageIndicator.setViewPager(bargin_viewPager);


                if (sku.is_enable.equals("1")) {
                    layout_fragment_goods_item.setVisibility(View.VISIBLE);
                    layout_fragment_goods_disable_layout.setVisibility(View.GONE);

                    //会员价格
                    /*if (!Utils.isNull(responseGoodsModel.data.rank_prices) && responseGoodsModel
                            .data.rank_prices.size() != 0) {
                        hasRankPrice = true;
                        mGoodsRankPriceLayout.setVisibility(View.VISIBLE);
                        Utils.setViewTypeForTag(mGoodsRankPriceLayout, ViewType
                                .VIEW_TYPE_RANK_PRICE);
                        mGoodsRankPriceLayout.setOnClickListener(this);
                    } else {
                        mGoodsRankPriceLayout.setVisibility(View.GONE);
                    }*/
                    //赠品
                    if (!Utils.isNull(sku.gift_list)) {
                        mGoodsGiftLayout.setVisibility(View.VISIBLE);

                        mLinearLayoutManager = new LinearLayoutManager(getActivity());
                        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        mGoodsGiftAdapter = new GoodsGiftListAdapter();
                        mGoodsGiftAdapter.onClickListener = this;
                        mRecyclerView.setLayoutManager(mLinearLayoutManager);
                        mRecyclerView.setAdapter(mGoodsGiftAdapter);

                        for (GiftModel giftModel : sku.gift_list) {
                            mGoodsGiftAdapter.data.add(giftModel);
                        }


                        mGoodsGiftAdapter.notifyDataSetChanged();

                    } else {
                        mGoodsGiftLayout.setVisibility(View.GONE);
                    }

                    //自提点
                    if (!Utils.isNull(responseGoodsModel.data.pickup) && (responseGoodsModel.data
                            .pickup.size() != 0)) {
                        mGoodsPickUpLayout.setVisibility(View.VISIBLE);
                        Utils.setViewTypeForTag(mGoodsPickUpLayout, ViewType.VIEW_TYPE_PICK_UP);
                        mGoodsPickUpLayout.setOnClickListener(this);
                    } else {
                        mGoodsPickUpLayout.setVisibility(View.GONE);
                    }

                    //红包
                    if (Utils.isNull(responseGoodsModel.data.bonus_list)) {
                        mGoodsBonusLayout.setVisibility(View.GONE);
                    } else {
                        mGoodsBonusLayout.setVisibility(View.VISIBLE);
                    }

                    final List<GoodsMix> mixes = responseGoodsModel.data.sku.goods_mix;
                    String temp = "";//满减活动字符串
                    //促销
                    if ((mixes != null && mixes.size() > 0) || !Utils.isNull(sku.order_activity) || !Utils.isNull(mResponseGoodsModel.data.rank_message)) {

                        //会员等级价格
                        if (!Utils.isNull(mResponseGoodsModel.data.rank_message)) {
                            ll_member_price.setVisibility(View.VISIBLE);
                            tv_member_price_message.setText(mResponseGoodsModel.data.rank_message);
                        } else {
                            ll_member_price.setVisibility(View.GONE);
                        }

                        //满减活动
                        if (!Utils.isNull(sku.order_activity) && sku.order_activity.discount_message.size() > 0) {
                            ll_full_cut.setVisibility(View.VISIBLE);

                            //满减标签
                            if (sku.order_activity.discount_label.contains("reduce_cash")) {
                                tv_reduce_cash.setVisibility(View.VISIBLE);
                            } else {
                                tv_reduce_cash.setVisibility(View.GONE);
                            }
                            //包邮标签
                            if (sku.order_activity.discount_label.contains("freight_free")) {
                                tv_freight_free.setVisibility(View.VISIBLE);
                            } else {
                                tv_freight_free.setVisibility(View.GONE);
                            }
                            //赠品标签
                            if (sku.order_activity.discount_label.contains("gift")) {
                                tv_gift.setVisibility(View.VISIBLE);
                            } else {
                                tv_gift.setVisibility(View.GONE);
                            }

                            for (String str : sku.order_activity.discount_message) {
                                temp += str + "\n";
                            }

                        } else {
                            ll_full_cut.setVisibility(View.GONE);
                        }


                        //搭配套餐
                        if (mixes != null && mixes.size() > 0) {
                            double max_goods_diff = 0;

                            for (GoodsMix goodsMix : mixes) {
                                max_goods_diff = Math.max(goodsMix.max_goods_diff, max_goods_diff);
                            }

                            fragment_goods_mix_layout.setVisibility(View.VISIBLE);

                            fragment_goods_mix_textView.setText("最高省" + max_goods_diff + "元，共" + mixes.size() + "款");
                        } else {
                            fragment_goods_mix_layout.setVisibility(View.GONE);
                        }

                        final String goodsFullCut = temp;
                        final String member_price_message = mResponseGoodsModel.data.rank_message;

                        rankPrices = mResponseGoodsModel.data.rank_prices;
                        //促销活动界面显示
                        fragment_promotion_info.setVisibility(View.VISIBLE);
                        fragment_promotion_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), PromotionPopActivity.class);
                                intent.putExtra("goodMix", JSON.toJSONString(mixes));
                                intent.putExtra("goodsFullCutStr", goodsFullCut);
                                intent.putExtra("memberPriceMessage", member_price_message);
                                intent.putExtra(Key.KEY_SKU_ID.getValue(), responseGoodsModel.data.sku.sku_id);
                                startActivity(intent);
                            }
                        });

                    } else {
                        fragment_promotion_info.setVisibility(View.GONE);
                        fragment_promotion_info.setOnClickListener(null);
                    }


                    //属性
                    if (!Utils.isNull(goods.spec_list)) {
                        mGoodsSpecLayout.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        mGoodsSpecList.setText(sku.spec_attr_value);
                    } else {
                        mGoodsSpecLayout.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                    }
                    //地区
                    if (responseGoodsModel.data.show_freight_region) {
                        mGoodsRegionLayout.setVisibility(View.VISIBLE);
                        mRegionName = goods.region_name;
                        //定位
                        Location.locationCallback(new Location.OnLocationListener.DefaultLocationListener() {
                            @Override
                            public void onFinished(AMapLocation amapLocation) {
                                if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng) && !App
                                        .getInstance().lat.equals("0") && !App.getInstance().lat.equals("0")) {
                                    final CommonRequest request = new CommonRequest(Api.API_GET_REGION_NAME, HttpWhat
                                            .HTTP_GET_REGION_NAME.getValue());
                                    request.add("lat", App.getInstance().lat);
                                    request.add("lng", App.getInstance().lng);
                                    addRequest(request);
                                } else {
                                    if (!Utils.isNull(mRegionName)) {
                                        mGoodsRegion.setText(mRegionName);
                                    } else {
                                        mGoodsRegion.setText("定位失败，请手动选择地址");
                                    }
                                }
                            }
                        });

                    } else {
                        mGoodsRegionLayout.setVisibility(View.GONE);
                    }

                    //服务保障
                    if (!Utils.isNull(goods.contract_list) && goods.contract_list.size() != 0) {
                        contractListLayout.setVisibility(View.VISIBLE);

                        /*contractName.setText(Utils.spanStringColor("该商品由" + shop_info.shop_name +
                                "发货,并提供售后服务", ContextCompat.getColor(getActivity(), R.color.colorPrimary), 4, shop_info.shop_name.length()));*/

                        mContractListAdapter = new GoodsContractListAdapter();
                        YSOStoreRegionListFlowLayoutManager ysoStoreRegionListFlowLayoutManager =
                                new YSOStoreRegionListFlowLayoutManager(true);
                        mContractListRecyclerView.setLayoutManager
                                (ysoStoreRegionListFlowLayoutManager);
                        mContractListRecyclerView.setAdapter(mContractListAdapter);

                        for (ContractModel contractModel : goods.contract_list) {
                            mContractListAdapter.data.add(contractModel);
                        }
                        mContractListAdapter.notifyDataSetChanged();

                    } else {
                        contractListLayout.setVisibility(View.GONE);
                    }

                } else {
                    layout_fragment_goods_item.setVisibility(View.GONE);
                    layout_fragment_goods_disable_layout.setVisibility(View.VISIBLE);
                }

                //评论
                if (!goods.comment_count.equals("0")) {
                    mGoodsCommentLayout.setVisibility(View.VISIBLE);
                    mGoodsCommentCount.setText(String.format(getResources().getString(R.string
                            .commentCount), goods.comment_count));
                    ImageLoader.displayImage(Utils.urlOfImage(goods.comment.headimg),
                            mGoodsCommentUserPhoto);
                    ImageLoader.displayImage(Utils.urlOfImage(goods.comment.rank_img),
                            mGoodsCommentUserRank);
                    mGoodsCommentUserName.setText(goods.comment.nickname_encrypt);
                    if (Utils.isNull(goods.comment.comment_desc)) {
                        mGoodsCommentContent.setVisibility(View.GONE);
                    } else {
                        mGoodsCommentContent.setVisibility(View.VISIBLE);
                        mGoodsCommentContent.setText(goods.comment.comment_desc);
                    }
                    mGoodsCommentTime.setText(Utils.times(goods.comment.comment_time + ""));
                } else {
                    mGoodsCommentLayout.setVisibility(View.GONE);
                }

                //店铺
                if (!Utils.isNull(shop_info.shop_image)) {
                    ImageLoader.displayImage(Utils.urlOfImage(shop_info.shop_logo), mShopLogo);
                } else {
                    ImageLoader.displayImage(Utils.urlOfImage(shop_info.shop_image), mShopLogo);
                }
                mShopName.setText(shop_info.shop_name);
                if (shop_info.shop_type.equals("0")) {
                    mShopType.setText("自营");
                } else {
                    mShopType.setVisibility(View.GONE);
                }
                mShopGoodsCount.setText(responseGoodsModel.data.shop_goods_count);
                mShopCollectCount.setText(responseGoodsModel.data.shop_collect_count);


                DecimalFormat df = new DecimalFormat("#.00");
                String average = df.format((Double.parseDouble(shop_info.desc_score) + Double.parseDouble(shop_info.service_score) + Double.parseDouble(shop_info.send_score)) / 3);
                mShopLogisticsScore.setText("综合评分 " + average);
                mShopDescScore.setText(shop_info.desc_score);
                mShopServiceScore.setText(shop_info.service_score);
                mShopSendScore.setText(shop_info.send_score);
                shopId = shop_info.shop_id;
                tel = shop_info.service_tel;
                if (goods.shop_collect) {
                    mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_selected);
                    mIcShopCollectionTip.setText(R.string.unfollow);
                } else {
                    mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_normal);
                    mIcShopCollectionTip.setText(R.string.saveThisSeller);
                }

                //规格参数
                if (goods.attr_list != null) {
                    for (int i = 0; i < goods.attr_list.size(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("goods_parameter_name", goods.attr_list.get(i).attr_name);
                        item.put("goods_parameter_value", goods.attr_list.get(i).attr_values);
                        mAttrData.add(item);
                    }
                }

                //底部按钮
                if (goods.is_collect) {
                    GoodsActivity.mCollectImage.setImageResource(R.mipmap.ic_star_selected);
                    GoodsActivity.mCollectTip.setText("已收藏");
                }

                //购买按钮按照pc的逻辑走，参照retail_goods.tpl
                if ("0".equals(sku.buy_enable.code) && "0".equals(sku.show_price)) {
                    GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                    GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                    GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                    GoodsActivity.mUnableToBuyLayout.setText(sku.buy_enable.button_content);

                    GoodsActivity.mUnableToBuyLayout.setOnClickListener(null);

                    if (Utils.isNull(App.getInstance().userId)) {
                        Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_LOGIN);
                        GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);

                    }
                } else if ("0".equals(sku.buy_enable.code) && "".equals(sku.show_price)) {
                    if (Utils.isNull(App.getInstance().userId)) {
                        GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                        GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                        GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                        GoodsActivity.mUnableToBuyLayout.setText("立即登录");

                        Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_LOGIN);
                        GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);
                    } else {

                        enableButtonContent = sku.buy_enable.button_content;

                        GoodsActivity.mUnableToBuyLayout.setVisibility(View.GONE);
                        GoodsActivity.mAddToCartButton.setVisibility(View.VISIBLE);
                        GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);

                        GoodsActivity.mAddToCartButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorSix));
                        GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorSix));

                        Utils.setViewTypeForTag(GoodsActivity.mAddToCartButton, ViewType.VIEW_TYPE_MESSAGE);
                        GoodsActivity.mAddToCartButton.setOnClickListener(this);
                        Utils.setViewTypeForTag(GoodsActivity.mBuyNowButton, ViewType.VIEW_TYPE_MESSAGE);
                        GoodsActivity.mBuyNowButton.setOnClickListener(this);
                    }

                } else if (!App.getInstance().isLogin()) {
                    /**未 登录**/
                    GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                    GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                    GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                    GoodsActivity.mUnableToBuyLayout.setText("立即登录");

                    Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_LOGIN);
                    GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);
                } else {
                    GoodsActivity.mUnableToBuyLayout.setVisibility(View.GONE);
                    GoodsActivity.mAddToCartButton.setVisibility(View.VISIBLE);
                    GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);

                    GoodsActivity.mAddToCartButton.setBackgroundColor(ContextCompat.getColor
                            (getActivity(), R.color.colorPrimary));
                    GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
                            (getActivity(), R.color.colorCyan));

                    Utils.setViewTypeForTag(GoodsActivity.mAddToCartButton, ViewType
                            .VIEW_TYPE_ADD_TO_CART);
                    GoodsActivity.mAddToCartButton.setOnClickListener(this);
                    Utils.setViewTypeForTag(GoodsActivity.mBuyNowButton, ViewType
                            .VIEW_TYPE_BUY_NOW);
                    GoodsActivity.mBuyNowButton.setOnClickListener(this);
                }


                /*if (sku.buy_enable.code.equals("0")) {

                    buyEnable = false;
                    enableButtonContent = sku.buy_enable.button_content;

                    if (Utils.isNull(responseGoodsModel.data.context.user_info)) {
                        GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                        GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                        GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                        Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType
                                .VIEW_TYPE_LOGIN);
                        GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);

                    } else {
                        GoodsActivity.mUnableToBuyLayout.setVisibility(View.GONE);
                        GoodsActivity.mAddToCartButton.setVisibility(View.VISIBLE);
                        GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);

                        GoodsActivity.mAddToCartButton.setBackgroundColor(ContextCompat.getColor
                                (getActivity(), R.color.colorSix));
                        GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
                                (getActivity(), R.color.colorSix));

                        Utils.setViewTypeForTag(GoodsActivity.mAddToCartButton, ViewType
                                .VIEW_TYPE_MESSAGE);
                        GoodsActivity.mAddToCartButton.setOnClickListener(this);
                        Utils.setViewTypeForTag(GoodsActivity.mBuyNowButton, ViewType
                                .VIEW_TYPE_MESSAGE);
                        GoodsActivity.mBuyNowButton.setOnClickListener(this);

                    }

                } else {
                    GoodsActivity.mUnableToBuyLayout.setVisibility(View.GONE);
                    GoodsActivity.mAddToCartButton.setVisibility(View.VISIBLE);
                    GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);

                    GoodsActivity.mAddToCartButton.setBackgroundColor(ContextCompat.getColor
                            (getActivity(), R.color.colorPrimary));
                    GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
                            (getActivity(), R.color.colorCyan));

                    Utils.setViewTypeForTag(GoodsActivity.mAddToCartButton, ViewType
                            .VIEW_TYPE_ADD_TO_CART);
                    GoodsActivity.mAddToCartButton.setOnClickListener(this);
                    Utils.setViewTypeForTag(GoodsActivity.mBuyNowButton, ViewType
                            .VIEW_TYPE_BUY_NOW);
                    GoodsActivity.mBuyNowButton.setOnClickListener(this);

                }*/


                /*** 判断该商品是否为配置活动商品 **/
                if (sku.activity_navi.is_show == 1) {

                    mImg_GoodeDetailsAc.setVisibility(View.VISIBLE);

                    GlideApp.with(this)
                            .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, sku.activity_navi.icon))
                            .into(mImg_GoodeDetailsAc);

                    mImg_GoodeDetailsAc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (sku.activity_navi.link.contains("webview_type=1")) {
                                Intent intent = new Intent(getActivity(), YSCWebViewActivity.class);
                                intent.putExtra(Key.KEY_URL.getValue(), sku.activity_navi.link);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), ProjectH5Activity.class);
                                intent.putExtra(Key.KEY_URL.getValue(), sku.activity_navi.link);
                                startActivity(intent);
                            }
                        }
                    });
                }

                //活动属性的商品
                if (!Utils.isNull(sku.activity) && !Utils.isNull(sku.activity.act_id)) {


                    /****
                     * 显示商品价格
                     * (元宝>0)￥XXX价格+XX元宝    sku.goods_dk_price(价格)    goods.max_integral_num(元宝)
                     * (元宝<=0)￥XXX价格         sku.goods_price_format(价格)
                     * 宝箱价 ￥XXXX              sku.goods_bxprice_format
                     *
                     */

                    if (Integer.parseInt(goods.max_integral_num) > 0) {
                        mGoodsPrice.setText(Utils.formatMoney(mGoodsPrice.getContext(), sku.goods_dk_price));
                        mGoodsIngot.setText("+" + goods.max_integral_num + "元宝");
                    } else {
                        mGoodsPrice.setText(Utils.formatMoney(mGoodsPrice.getContext(), sku.goods_price_format));
                    }

                    if (!TextUtils.isEmpty(sku.goods_bxprice_format)) {
                        mGoodsDeductible.setText(sku.goods_bxprice_format);
                    }

                    /****
                     * 商品的市场价价
                     *
                     */
                    if (!"0.00".equals(sku.market_price) && !Utils.isNull(sku.market_price) && (Double.parseDouble(sku.market_price) > Double.parseDouble(sku.goods_price))) {
                        //显示商品原价格 横线
                        mGoodsMarketPrice.setText(Utils.spanStrickhrough(getActivity(), sku.market_price_format));
                    }


                    //团购
                    if (sku.activity.act_type == Macro.ACTIVITY_TYPE_NO_START_GROUP_BUY || sku.activity.act_type == Macro.GT_GROUP_BUY_GOODS) {
                        mCountDown.setVisibility(View.VISIBLE);
                        mPriceLayout.setVisibility(View.GONE);

                        String saleCount = String.format(getResources().getString(R.string.sold), goods.sale_num);
//                        mGroupBuySaleCount.setText(Utils.spanSizeString(getActivity(), saleCount, 0, goods.sale_num.length(), 15));

                        long current_time = responseGoodsModel.data.context.current_time;
                        long leftTime = Long.valueOf(sku.activity.end_time) - current_time;

                        mCvCountdownViewTestHasBg.start(leftTime * 1000);

//                        if ("1".equals(sku.activity.is_finish)) {
//                            mGroupBuyPrice.setText(sku.goods_price_format);
//                            mGroupBuyMarketPrice.setText(Utils.spanStrickhrough(getActivity(), sku
//                                    .original_price_format));
//
//                            linearlayout_countdown.setBackgroundResource(R.drawable.shape_group_buy_goods_triangle);
//                            fragment_group_buy_tip.setText("距结束仅剩");
//                            fragment_group_buy_market_price_prefix.setVisibility(View.GONE);
//                            relativeLayoutLeftInfo.setBackgroundColor(Color.parseColor("#f51e4a"));
//                        } else {
//                            mGroupBuyPrice.setText(sku.activity.act_price_format);
//                            mGroupBuyMarketPrice.setText(Utils.spanStrickhrough(getActivity(), sku.activity.show_act_price));
//
//                            linearlayout_countdown.setBackgroundResource(R.drawable.shape_group_buy_goods_triangle_two);
//                            fragment_group_buy_tip.setText("距开团还剩");
//                            fragment_group_buy_market_price_prefix.setVisibility(View.VISIBLE);
//                            relativeLayoutLeftInfo.setBackgroundColor(Color.parseColor("#5fb760"));
//                        }
                    }
                    //拼团
                    if (sku.activity.act_type == Macro.OT_FIGHT_GROUP) {
                        mGoodsPrice.setVisibility(View.GONE);
                        mGoodsMarketPrice.setVisibility(View.GONE);
                        mGoodsSaleNumber.setVisibility(View.GONE);
                        mGroupOnLayout.setVisibility(View.VISIBLE);

                        grouponResultTip.setText("达到人数\n团购成功");
                        grouponResultTip.setTextColor(ContextCompat.getColor(grouponResultTip
                                .getContext(), R.color.colorThree));
                        grouponResultTip1.setTextColor(ContextCompat.getColor(grouponResultTip
                                .getContext(), R.color.colorThree));

                        grouponImageView.setImageResource(R.mipmap.ic_next_dark);
                        grouponImageView1.setImageResource(R.mipmap.ic_next_dark);

                        GoodsActivity.mButtonOne.setText(sku
                                .original_price_format + "\n单独购买");
                        GoodsActivity.mButtonTwo.setText(sku
                                .activity.act_price_format + "\n" + sku.activity.fight_num + "人团");

                        GoodsActivity.mAddToCartButton.setBackgroundColor(Color.parseColor("#fd948e"));
                        GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
                                (getActivity(), R.color.colorPrimary));

                        if (!TextUtils.isEmpty(sku.activity.params)) {
                            ParamsModel mParamsModel = null;
                            try {
                                mParamsModel = JSON.parseObject(sku.activity.params, ParamsModel
                                        .class);
                                if (mParamsModel.groupon_log_list.size() == 0) {
                                    mGroupOnLinearLayout.setVisibility(View.GONE);
                                    mGroupOnRecycler.setVisibility(View.GONE);
                                } else {
                                    long current_time = responseGoodsModel.data.context
                                            .current_time;
                                    for (int i = 0; i < mParamsModel.groupon_log_list.size(); i++) {
                                        mParamsModel.groupon_log_list.get(i).start_time = (int)
                                                current_time;
                                    }
                                    GoodsGroupOnAdapter mAdapter = new GoodsGroupOnAdapter
                                            (mParamsModel.groupon_log_list, this);
                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager
                                            (getContext());
                                    mGroupOnRecycler.setAdapter(mAdapter);
                                    mGroupOnRecycler.setLayoutManager(mLayoutManager);
                                    mAdapter.onClickListener = this;
                                }
                            } catch (Exception e) {
                            }

                        } else {
                            mGroupOnLinearLayout.setVisibility(View.GONE);
                            mGroupOnRecycler.setVisibility(View.GONE);
                        }
                    }

                    if (sku.activity.act_type == Macro.GT_LIMITED_DISCOUNT_GOODS) {
                        mGoodsMarketPrice.setVisibility(View.VISIBLE);

                        linearlayout_discount.setVisibility(View.VISIBLE);
                        mGoodsPrice.setVisibility(View.VISIBLE);

                        mGoodsSaleNumber.setVisibility(View.VISIBLE);
                        mGoodsSaleNumber.setText("销量：" + goods.sale_num + "件");


                        textView_activity_name.setText(sku.activity.act_label);

                        if ("0".equals(sku.activity.discount_mode)) {
                            textView_activity_discount.setText(sku.activity.discount_num + "折");
                        } else {
                            textView_activity_discount.setText("减" + sku.activity.discount_num + "元");
                        }

                        textView_activity_starttime.setText("预计 " + Utils.times(sku.activity.cutdown_time) + " 开始");

                        long current_time = responseGoodsModel.data.context.current_time;

                        if (sku.activity.act_status == 1) {
                            linearlayout_activity_countdown.setVisibility(View.VISIBLE);
                            textView_activity_starttime.setVisibility(View.GONE);

                            long leftTime = Long.valueOf(sku.activity.cutdown_time) - current_time;
                            view_activity_countdown.start(leftTime * 1000);
                        } else {
                            //未开始
                            linearlayout_activity_countdown.setVisibility(View.GONE);
                            textView_activity_starttime.setVisibility(View.VISIBLE);
                        }
                    }

                    /****
                     * (首页-助力砍价) 砍价商品
                     * 我的商品1
                     * sku.activity.act_type==8
                     *
                     *
                     */
                    if (sku.activity.act_type == Macro.GT_BARGAIN_GOODS) {


                        /*** 获取商家店铺的code **/
                        merchants_code = responseGoodsModel.data.invite_code;

                        /*** 隐藏商品价格 **/
                        mPriceLayout.setVisibility(View.GONE);
                        /*** 隐藏 宝箱价 **/
                        mGoodsDeductible.setVisibility(View.GONE);

                        /**砍价 内容显示*/
                        mLayout_BargainGood.setVisibility(View.VISIBLE);
                        layout_bargain_item.setVisibility(View.VISIBLE);

                        final ParamsModel paramsModel = JSON.parseObject(sku.activity.params, ParamsModel.class);


                        /*** 砍价商品价格显示(底价 原价) **/
                        mText_BargainFloorPrice.setText("¥" + paramsModel.bargain_info.base_price);
                        mText_BargainPrice_Time.setText(Utils.spanStrickhrough(getActivity(), "¥" + paramsModel.bargain_info.original_price));
                        mText_BargainIn_Time.setText(paramsModel.bargain_info.part_num + "人已参与");

                        bargain_name = paramsModel.bargain_info.goods_name;

                        mText_BargainPrice.setText("已砍价至:" + Utils.formatMoney(getContext(), sku.activity.act_price_format));
                        mText_BargainInvent.setText("库存:" + sku.activity.goods_number);

                        mText_BargainHelp.setText(paramsModel.help_bargain_num + "人帮砍");

                        //砍价规则 dialog弹出
                        mText_BargainRule.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RuleHintDialog hintDialog = new RuleHintDialog(getActivity());
                                hintDialog.show();
                            }
                        });

                        int bargain_state = Integer.valueOf(paramsModel.bargain_info.is_finish);

                        if (bargain_state == 1) {
                            //砍价商品 活动进行中
                            /**倒计时 开启*/
                            mCountDown.setVisibility(View.VISIBLE);
                            mLayout_CountTime.setVisibility(View.VISIBLE);

                            mCountDown.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bargain_good_bg));

                            /****
                             * 砍价商品 倒计时 结束时间 计算
                             * 针对商品第一次砍价（立即砍价）sku.activity.user_type==0  活动结束时间:sku.activity.end_time
                             * 第二次砍价(立即购买,找人帮砍)  活动结束时间:sku.activity.params.bargain_info.end_time
                             *
                             */

                            long curr_time = Long.valueOf(responseGoodsModel.data.context.current_time);
                            long end_time = 0;

                            if (sku.activity.user_type == 0) {

                                //该商品为第一次砍价
                                if (App.getInstance().isLogin()) {
                                    GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                                    GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                                    GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                                    GoodsActivity.mUnableToBuyLayout.setText("立即砍价");

                                    Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_BARGAIN);
                                    GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);
                                }
                                end_time = Long.valueOf(responseGoodsModel.data.sku.activity.end_time);

                                mText_BargainIn.setText("未参与");

                                //砍价名单 页面跳转
                                mText_BargainList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(activity, "暂时还没有帮砍名单", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //未开始砍价 使用默认图 并且不显示 用户名称
                                GlideApp.with(mImage_BargainImg.getContext())
                                        .load(R.mipmap.bargain_help_img)
                                        .into(mImage_BargainImg);

                                mText_Bargain_HelpName.setVisibility(View.GONE);

                            } else if (sku.activity.user_type == 1) {

                                /*** 解析帮砍名单list **/
                                if (!TextUtils.isEmpty(paramsModel.help_bargain_list)) {
                                    mHelpBargainLists = JSONObject.parseArray(paramsModel.help_bargain_list, HelpBargainModel.class);
                                }

                                if (App.getInstance().isLogin()) {
                                    GoodsActivity.mUnableToBuyLayout.setVisibility(View.GONE);

                                    GoodsActivity.mAddToCartButton.setVisibility(View.VISIBLE);//立即购买
                                    GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);//找人帮砍

                                    GoodsActivity.mButtonOne.setText("立即购买");
                                    GoodsActivity.mButtonTwo.setText("找人帮砍");

                                    Utils.setViewTypeForTag(GoodsActivity.mAddToCartButton, ViewType.VIEW_TYPE_BUY_NOW);
                                    GoodsActivity.mAddToCartButton.setOnClickListener(this);

                                    Utils.setViewTypeForTag(GoodsActivity.mBuyNowButton, ViewType.VIEW_TYPE_HELP_BARGAIN);
                                    GoodsActivity.mBuyNowButton.setOnClickListener(this);
                                }

                                bargainId = sku.activity.bar_id;
                                end_time = Long.valueOf(paramsModel.bargain_info.end_time);

                                mText_BargainIn.setText("已参与");

                                mText_BargainList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!TextUtils.isEmpty(paramsModel.help_bargain_list)) {

                                            Intent intent = new Intent(getActivity(), HelpBargainListActivity.class);
                                            intent.putExtra(HelpBargainListActivity.HELP_LIST, (Serializable) mHelpBargainLists);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(activity, "暂时还没有帮砍名单", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                if (!TextUtils.isEmpty(paramsModel.help_bargain_list)) {
                                    help_bargain_model = mHelpBargainLists.get(0);

                                    mText_Bargain_HelpName.setVisibility(View.VISIBLE);

                                    GlideApp.with(mImage_BargainImg.getContext())
                                            .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, help_bargain_model.headimg))
                                            .centerCrop()
                                            .into(mImage_BargainImg);

                                    mText_Bargain_HelpName.setText(help_bargain_model.nickname);

                                } else {

                                    GlideApp.with(mImage_BargainImg.getContext())
                                            .load(R.mipmap.bargain_help_img)
                                            .into(mImage_BargainImg);

                                    mText_Bargain_HelpName.setVisibility(View.GONE);
                                }

                            }

                            long ac_time = end_time - curr_time;

                            if (ac_time > 0) {
                                mTextView_CountText.setText("距结束仅剩");
                                //倒计时有效
                                mCvCountdownViewTestHasBg.start(ac_time * 1000);
                                mCvCountdownViewTestHasBg.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                                    @Override
                                    public void onEnd(CountdownView cv) {
                                        //倒计时已结束
                                        mCvCountdownViewTestHasBg.stop();
                                        //刷新商品数据
                                        refresh();
                                    }
                                });
                            } else {
                                //倒计时完毕
                                mCvCountdownViewTestHasBg.stop();
                                //刷新商品数据
                                refresh();
                            }

                        }
                    }/***砍价商品结束*/


                } else if (sku.limitActivity == 1) {

                    /***
                     * 秒杀商品
                     * sku.limitActivity=1  该商品为秒杀商品
                     * sku.buy_start_time   开始时间 时间戳(时分秒)
                     * sku.buy_end_time     结束时间
                     * sku.limitStatus      0:活动结束 1:抢购中 2:预告中
                     * sku.limitString      预计开始文本
                     * sku.goods_number     0:已抢光
                     *
                     *
                     * 业务逻辑  1.该活动是否开始or结束 2.活动开始-是否是在抢购中or已结束  3.抢购中-该商品是否已抢光 4.活动结束
                     *
                     */

                    /**商品价格显示**/
                    if (!"0.00".equals(sku.market_price) && !Utils.isNull(sku.market_price) && (Double.parseDouble(sku.market_price) > Double.parseDouble(sku.goods_price))) {
                        //显示商品原价格 横线
                        mGoodsMarketPrice.setText(Utils.spanStrickhrough(getActivity(), sku.market_price_format));
                    }

                    linearlayout_discount.setVisibility(View.VISIBLE);
                    mGoodsPrice.setVisibility(View.VISIBLE);

                    if (Integer.parseInt(goods.max_integral_num) > 0) {
                        mGoodsPrice.setText(Utils.formatMoney(mGoodsPrice.getContext(), sku.goods_dk_price));
                        mGoodsIngot.setText("+" + goods.max_integral_num + "元宝");
                    } else {
                        mGoodsPrice.setText(Utils.formatMoney(mGoodsPrice.getContext(), sku.goods_price_format));
                    }

                    if (!TextUtils.isEmpty(sku.goods_bxprice_format)) {
                        mGoodsDeductible.setText(sku.goods_bxprice_format);
                    }

                    mGoodsMarketPrice.setVisibility(View.VISIBLE);

                    mCountDown.setVisibility(View.VISIBLE);
                    if (sku.limitStatus == 0) {

                        //活动已结束
                        mCountDown.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.seckill_over_bg));
                        mLayout_CountTime.setVisibility(View.GONE);
                        mLayout_AcHint.setVisibility(View.GONE);
                        mTextView_OverText.setText("活动已结束");

                        if (App.getInstance().isLogin()) {
                            GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                            GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);
                            GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.ing_number));

                            //查看商家其他商品 进入该店铺
                            GoodsActivity.mButtonTwo.setText("查看商家其它商品");
                            GoodsActivity.mBuyNowButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openShopActivity(shopId);
                                }
                            });
                        } else {
                            //立即登录
                            GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                            GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                            GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                            GoodsActivity.mUnableToBuyLayout.setText("立即登录");

                            Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_LOGIN);
                            GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);
                        }

                    } else if (sku.limitStatus == 1) {
                        //抢购中
                        mCountDown.setBackgroundResource(R.mipmap.seckill_bg);
                        mLayout_CountTime.setVisibility(View.VISIBLE);
                        mLayout_AcHint.setVisibility(View.GONE);

                        //倒计时文本开启
                        mTextView_CountText.setText("距结束还有");

                        //拿到当前时间和活动结束时间
                        long curr_time = Long.valueOf(responseGoodsModel.data.context.current_time);
                        long end_time = Long.valueOf(sku.buy_end_time);
                        long ac_time = end_time - curr_time;

                        if (ac_time > 0) {
                            //倒计时有效
                            mCvCountdownViewTestHasBg.start(ac_time * 1000);
                            mCvCountdownViewTestHasBg.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                                @Override
                                public void onEnd(CountdownView cv) {
                                    //倒计时已结束
                                    mCvCountdownViewTestHasBg.stop();
                                }
                            });
                        } else {
                            //倒计时完毕
                            mCvCountdownViewTestHasBg.stop();
                        }

                        //只是立即购买or已抢光  不显示加入购物车
                        GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                        GoodsActivity.mBuyNowButton.setVisibility(View.VISIBLE);

                        /** 先判断是否已登录 */
                        if (App.getInstance().isLogin()) {
                            //判断该商品已抢光
                            if (Integer.valueOf(sku.goods_number) > 0) {

                                //立即购买
                                GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.ing_number));
                                GoodsActivity.mUnableToBuyLayout.setText("立即购买");

                                Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_BUY_NOW);
                                GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);
                            } else {
                                //已抢光
                                GoodsActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.seckill_over_bg));

                                GoodsActivity.mUnableToBuyLayout.setText("已抢完");
                                GoodsActivity.mUnableToBuyLayout.setClickable(false);
                            }
                        } else {

                            GoodsActivity.mUnableToBuyLayout.setVisibility(View.VISIBLE);
                            GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
                            GoodsActivity.mBuyNowButton.setVisibility(View.GONE);

                            GoodsActivity.mUnableToBuyLayout.setText("立即登录");

                            Utils.setViewTypeForTag(GoodsActivity.mUnableToBuyLayout, ViewType.VIEW_TYPE_LOGIN);
                            GoodsActivity.mUnableToBuyLayout.setOnClickListener(this);
                        }
                    } else if (sku.limitStatus == 2) {
                        //活动未开始 预告显示
                        mCountDown.setBackgroundResource(R.mipmap.seckill_hint);
                        mLayout_CountTime.setVisibility(View.GONE);
                        mLayout_AcHint.setVisibility(View.VISIBLE);

                        //活动预告文本 商品名称 xxxxxxx
                        mTextView_AcHint.setText(sku.limitString);
                    }

                } else {

                    /*** 普通商品详情 **/

                    mCountDown.setVisibility(View.GONE);
                    mPriceLayout.setVisibility(View.VISIBLE);

                    if (Integer.parseInt(goods.max_integral_num) > 0) {
                        mGoodsPrice.setText(Utils.formatMoney(mGoodsPrice.getContext(), sku.goods_dk_price));
                        mGoodsIngot.setText("+" + goods.max_integral_num + "元宝");
                    } else {
                        mGoodsPrice.setText(Utils.formatMoney(mGoodsPrice.getContext(), sku.goods_price_format));
                    }

                    if (!"0.00".equals(sku.market_price) && !Utils.isNull(sku.market_price) && (Double.parseDouble(sku.market_price) > Double.parseDouble(sku.goods_price))) {
                        mGoodsMarketPrice.setText(Utils.spanStrickhrough(getActivity(), sku.market_price_format));
                    }
                    //隐藏 销售
                    mGoodsSaleNumber.setVisibility(View.GONE);
                    //mGoodsSaleNumber.setText("销量：" + goods.sale_num + "件");

                    if (!TextUtils.isEmpty(sku.goods_bxprice_format)) {
                        mGoodsDeductible.setText(sku.goods_bxprice_format);
                    }

                    mGroupOnLayout.setVisibility(View.GONE);
                }

                /**分享数据*/
                String title = sku.sku_name;
                String des = sku.goods_price_format;
                String share_img = Utils.urlOfImage(sku.sku_image);

                if (!Utils.isNull(responseGoodsModel.data.share)) {
                    if (!TextUtils.isEmpty(responseGoodsModel.data.share.seo_goods_title)) {
                        title = responseGoodsModel.data.share.seo_goods_title;
                    }

                    if (!TextUtils.isEmpty(responseGoodsModel.data.share.seo_goods_discription)) {
                        des = responseGoodsModel.data.share.seo_goods_discription;
                    }

                    if (!TextUtils.isEmpty(responseGoodsModel.data.share.seo_goods_image)) {
                        share_img = Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, responseGoodsModel.data.share.seo_goods_image);
                    }
                }
                shareData.add(Utils.getMallMBaseUrl() + "/" + sku_id + ".html");
                shareData.add(title);
                shareData.add(des);
                shareData.add(share_img);
                shareData.add(sku.goods_id);
                shareData.add(responseGoodsModel.data.context.config.mall_logo);
                shareData.add(responseGoodsModel.data.sku.sku_name);
                shareData.add(responseGoodsModel.data.sku.goods_price);

                if (mRefreshListener != null) {
                    mRefreshListener.onRefreshed();
                    mRefreshListener = null;
                }


                //批发商品判断
                if ("0".equals(sku.sales_model) || sku.sales_model == null) {
                    mGoodsName.setText(sku.sku_name);

                } else {
                    mGoodsName.setText(goods.goods_name);
//                     mShareButton.setVisibility(View.INVISIBLE);
                    mGoodsPrice.setVisibility(View.GONE);
                    mGoodsMarketPrice.setVisibility(View.GONE);

                    mGoodEvaluateNumber.setVisibility(View.VISIBLE);
                    mGoodsSaleNumber.setVisibility(View.VISIBLE);
                    mGoodsSaleNumber.setText("销量：" + goods.sale_num + "件");

                    mGoodEvaluateNumber.setText("用户评价：" + goods.comment_num);

                    //mGoodsRankPriceLayout.setVisibility(View.GONE);
                    mGoodsGiftLayout.setVisibility(View.GONE);

                    mGoodsSpecLayout.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    mGoodsSpecTitle.setText("选项");

                    String specnames = "";
                    if (goods.spec_list != null) {
                        for (SpecificationModel specificationModel : goods.spec_list) {
                            specnames += specificationModel.attr_name + " ";
                        }
                    }

                    if (TextUtils.isEmpty(specnames)) {
                        mGoodsSpecLayout.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                    } else {
                        mGoodsSpecList.setText(specnames);
                        mGoodsSpecLayout.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                    }


                    //设置显示批发价格
                    mGoodsStepPrice.setVisibility(View.VISIBLE);
                    mGoodsStepPrice.removeAllViews();
                    for (String key : sku.step_price.keySet()) {
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout
                                .fragment_goods_whole_price_child, null);
                        TextView textView_whole_price_key = (TextView) view.findViewById(R.id
                                .textView_whole_price_key);
                        TextView textView_whole_price_value = (TextView) view.findViewById(R.id
                                .textView_whole_price_value);
                        textView_whole_price_key.setText(key + goods.unit_name + " 起批");
                        try {
                            double price = Double.valueOf(sku.step_price.get(key));
                            textView_whole_price_value.setText("¥" + sku.step_price.get(key));
                        } catch (Exception e) {
                            textView_whole_price_value.setText(sku.step_price.get(key));
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        params.weight = 1;
                        view.setLayoutParams(params);
                        mGoodsStepPrice.addView(view);
                    }
                }

                //堂内点餐的
                if (!TextUtils.isEmpty(rc_id)) {

                    topView.setVisibility(View.GONE);

                    fragment_goods_shop_info_layout.setVisibility(View.GONE);
                    mGoodsGiftLayout.setVisibility(View.GONE);

                    mCountDown.setVisibility(View.GONE);
                    mPriceLayout.setVisibility(View.VISIBLE);

                    //分享按钮 隐藏
                    mShareButton.setVisibility(View.GONE);

                    mGroupOnLayout.setVisibility(View.GONE);

                    mGoodsRegionLayout.setVisibility(View.GONE);
                    mGoodsPickUpLayout.setVisibility(View.GONE);


                    GoodsActivity.mTelButton.setVisibility(View.GONE);
                    GoodsActivity.mIndexButton.setVisibility(View.GONE);
                    GoodsActivity.mCollectButton.setVisibility(View.GONE);

//                    GoodsActivity.mAddToCartButton.setVisibility(View.GONE);
//                    GoodsActivity.mBuyNowButton.setVisibility(View.GONE);


//                    if (App.getInstance().isLogin()) {
//                        GoodsActivity.mUnableToBuyButton.setText("立即兑换");
//
//                        //未下架
//                        if (sku.is_enable.equals("1")){
//                            //积分不足，不能兑换
//                            if(responseGoodsModel.data.can_not_buy){
//                                GoodsActivity.mUnableToBuyLayout.setBackgroundColor(ContextCompat.getColor
//                                        (getActivity(), R.color.colorNine));
//                                GoodsActivity.mUnableToBuyButton.setTextColor(0xffbdbdbd);
//                                GoodsActivity.mUnableToBuyButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Toast.makeText(getActivity(),"积分不足",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }else{
//                                GoodsActivity.mUnableToBuyLayout.setBackgroundColor(ContextCompat.getColor
//                                        (getActivity(), R.color.colorPrimary));
//                                GoodsActivity.mUnableToBuyButton.setTextColor(0xffffffff);
//
//                                GoodsActivity.mUnableToBuyButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //兑换商品
//                                        openAttributeActivity(Macro.BUTTON_TYPE_EXCHANGE);
//                                    }
//                                });
//                            }
//                        }else{
//                            GoodsActivity.mUnableToBuyLayout.setBackgroundColor(ContextCompat.getColor
//                                    (getActivity(), R.color.colorNine));
//                            GoodsActivity.mUnableToBuyButton.setTextColor(0xffbdbdbd);
//                        }
//
//                    } else {
//                        GoodsActivity.mUnableToBuyButton.setText("立即登录");
//                        GoodsActivity.mUnableToBuyButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //兑换商品
//                                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
//                            }
//                        });
//                    }
                }

                //按销量和收藏数展示商品
                try {
                    saleTopModel = responseGoodsModel.data.sale_top_list;
                    collectTopModel = responseGoodsModel.data.collect_top_list;
                    if (saleTopModel.size() > 0 && collectTopModel.size() > 0) {
                        topGoodsShow(0);
                        topView.setVisibility(View.VISIBLE);
                    } else if (saleTopModel.size() == 0 && collectTopModel.size() == 0) {
                        topView.setVisibility(View.GONE);
                    } else if (saleTopModel.size() > 0) {
                        topGoodsShow(0);
                        topCollectTextView.setVisibility(View.GONE);
                        topGap.setVisibility(View.GONE);
                    } else if (collectTopModel.size() > 0) {
                        topGoodsShow(1);
                        topSaleTextView.setVisibility(View.GONE);
                        topGap.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }

                //销量受后台控制
                if (responseGoodsModel.data.goods.show_sale_number) {
                    mGoodsSaleNumber.setVisibility(View.VISIBLE);
                } else {
                    mGoodsSaleNumber.setVisibility(View.GONE);
                }
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), InvalidActivity.class);
            intent.putExtra("error_info", responseGoodsModel.message);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void shareCallback(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (data.getIntExtra(Key.KEY_SHARE.getValue(), Macro.SHARE_FAILED)) {
                case Macro.SHARE_SUCCEED:
                    Toast.makeText(getActivity(), R.string.shareSucceed, Toast.LENGTH_SHORT).show();
                    break;
                case Macro.SHARE_FAILED:
                    Toast.makeText(getActivity(), R.string.shareFailed, Toast.LENGTH_SHORT).show();
                    break;
                case Macro.SHARE_CANCELED:
                    Toast.makeText(getActivity(), R.string.shareCanceled, Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        } else {
            Toast.makeText(getActivity(), R.string.shareCanceled, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查看大图()浏览大图
     *
     * @param postion
     * @param imgList
     */
    public void openImageGallery(int postion, ArrayList imgList) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), imgList);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), postion);
        startActivity(intent);
    }

    private void openPickUp() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Key.KEY_PICKUP_LIST.getValue(), mResponseGoodsModel
                .data.pickup);
        intent.putExtra("shop_id", mResponseGoodsModel.data.shop_info.shop.shop_id);
        intent.setClass(getActivity(), PickUpListActivity.class);
        startActivity(intent);
    }

    private interface RefreshListener {
        void onRefreshed();
    }

    public interface OnClickListener {
        void onClickListener(String id);
    }

    //轮播图
    class ImageAdapter extends PagerAdapter {
        private DisplayImageOptions options;

        ImageAdapter() {
            options = new DisplayImageOptions.Builder().showImageOnLoading(ImageLoader.ic_error)
                    .showImageOnFail(ImageLoader.ic_error).cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return goods_banner.size();
        }

        //图片轮播
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageview = new ImageView(getActivity());
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String img = goods_banner.get(position);
//            ImageLoader.displayImage(img, imageview);
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(img,
                    imageview, options);
            container.addView(imageview);

            imageview.setTag(position);
            imageview.setOnClickListener(new ImageViewClick());
            return imageview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    class ImageViewClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Object obj = view.getTag();
            int position = obj == null ? 0 : (int) obj;
            openImageGallery(position, goods_banner);
        }
    }

    private void showCopyWindows(View v) {

        /** pop view */
        View mPopView = LayoutInflater.from(getActivity()).inflate(R.layout
                .menu_copy_popup_window, null);
        final PopupWindow mPopWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        /** set */
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /** 这个很重要 ,获取弹窗的长宽度 */
        mPopView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mPopView.getMeasuredWidth();
        int popupHeight = mPopView.getMeasuredHeight();
        /** 获取父控件的位置 */
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        /** 显示位置 */
        mPopWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) -
                popupWidth / 2, location[1] - popupHeight + 40);
        mPopWindow.update();

        final String copyTxt = (String) v.getTag();
        mPopView.findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context
                        .CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("goods_name", sku.sku_name);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);

                Toast.makeText(getActivity(), "商品名称已复制到剪切板", Toast.LENGTH_SHORT).show();
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
            }
        });
    }

    public void cancelGroupon(String groupSn) {
        CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_CANCEL_GROUPON, HttpWhat
                .HTTP_ORDER_CANCEL.getValue());
        mQuickBuyRequest.add("group_sn", groupSn);
        addRequest(mQuickBuyRequest);
    }

    public void cancelGrouponCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                //Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
            }
        }, true);
        refresh();
    }

    private void refreshRegionNameCallback(String response) {
        HttpResultManager.resolve(response, ResponseRegionModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseRegionModel>() {
            @Override
            public void onSuccess(ResponseRegionModel back) {
                mRegionName = back.data.region_name;
                mRegionCode = back.data.region_code;

                if (!Utils.isNull(mRegionName)) {
                    mGoodsRegion.setText(mRegionName);
                } else {
                    mGoodsRegion.setText("定位失败，请手动选择地址");
                }

                //运费
                freightFee(sku_id, mRegionCode);
            }
        }, true);
    }

    void updateGoodsDesc(String response) {
        String uuid = activity.getUUID();
        EventBus.getDefault().post(new CommonEvent.Builder(EventWhat.EVENT_UPDATE_GOODS_DESC
                .getValue()).setMessage(uuid).setMessageSource(response).build());
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseClient.getInstance().close();
    }
}