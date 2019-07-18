package com.szy.yishopcustomer.Fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.CustomProgressDialog;
import com.szy.yishopcustomer.Activity.AccountBalanceActivity;
import com.szy.yishopcustomer.Activity.ArticleListActivity;
import com.szy.yishopcustomer.Activity.CollectionActivity;
import com.szy.yishopcustomer.Activity.FloatADActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ImgSearchActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.NearShopMoreActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Activity.SearchActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.ShopStreetActivity;
import com.szy.yishopcustomer.Activity.SiteActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Activity.im.LyMessageActivity;
import com.szy.yishopcustomer.Activity.samecity.CityLifeActivity;
import com.szy.yishopcustomer.Activity.samecity.CityListActivity;
import com.szy.yishopcustomer.Activity.samecity.SortStoreActivity;
import com.szy.yishopcustomer.Adapter.IndexAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.DataModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsTitleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.Model;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NearShopItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NearShopModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NearShopPageModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NoticeModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.PageModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.TemplateModel;
import com.szy.yishopcustomer.ResponseModel.AppInfo.ResponseAppInfoModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.IndexGoodListModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.UsableIngotModel;
import com.szy.yishopcustomer.ResponseModel.SiteSubsiteModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.NoDoubleClickListener;
import com.szy.yishopcustomer.Util.PermissionUtils;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.szy.yishopcustomer.Util.SharedPreferencesHelper;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.UrlUtil;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.szy.yishopcustomer.Util.json.GSONUtil;
import com.szy.yishopcustomer.Util.json.GsonUtils;
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

import static com.szy.yishopcustomer.Activity.NearShopMoreActivity.ADDRESS;
import static com.szy.yishopcustomer.Activity.NearShopMoreActivity.BUNDLE;
import static com.szy.yishopcustomer.Activity.samecity.CityListActivity.KEY_FINISH;
import static com.szy.yishopcustomer.Activity.samecity.CityListActivity.REQUEST_CODE;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.PERMISSION_CODE;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_GUESS_LIKE_TITLE;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_HOME_VIDEO_SHOW;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_INGOTS_BUY;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_LIKE_SHOP;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_NEAR_SHOP;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_NET_HOT_SHOP;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_SHOP_JION;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_SHOP_NEW_JOIN;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_VIEW_TYPE_AD_ADVERT;

/**
 * Created by liuzhifeng on 2017/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexFragment extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener, EMMessageListener {
    private static final String TAG = "IndexFragment";


    RootActivity rootActivity;
    private float alphaP = 0;
    private String weather = "多云";
    private static final String ARTICLE_LIST = "文章列表";
    private static final float GO_TO_TOP_BUTTON_MIN_ALPHA = 0;
    private static final float GO_TO_TOP_BUTTON_START_APPEAR_POSITION = 100;
    private static final float GO_TO_TOP_BUTTON_FULL_APPEAR_OFFSET = 50;
    //动画的执行时间
    private static final int ANIM_RUN_TIME = 500;
    //动画的延迟时间
    private static final int ANIM_DELAY_TIME = 0;
    //判断滚动后多长时间才隐藏按钮
    private static final int SLIDE_BEGIN_TIME_INTERVAL = 600;
    //判断滚动停止后多长时间才显示按钮
    private static final int SLIDE_TIME_INTERVAL = 1100;
    //滑动停顿时间，超过这个时间就不算连续滑动
    private static final int SLIDE_TIME_PAUSE = 1000;
    //    public static CustomProgressDialog mProgress;
    @BindView(R.id.fragment_index_content_wrapper_commonRecyclerView)
    CommonRecyclerView mContentWrapperRecyclerView;
    @BindView(R.id.go_up_button)
    ImageView mGoToTopImageButton;


    @BindView(R.id.fragment_index_refresh_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.fragment_index_title_scan_topImageButton)
    RelativeLayout mScanButton;
    @BindView(R.id.fragment_index_title_message_topImageButton)
    RelativeLayout mMessageButton;

    @BindView(R.id.fragment_index_title_input_relative_layout)
    RelativeLayout searchRelativeLayout;
    @BindView(R.id.img_index_search_photo)
    ImageView mImageView_SearchPhoto;


    @BindView(R.id.fragment_index_title)
    LinearLayout mTitle;

    @Nullable
    @BindView(R.id.fragment_index_title_site)
    RelativeLayout mSiteLayout;
    @BindView(R.id.fragment_index_site_text)
    TextView mSiteTextView;
    @BindView(R.id.fragment_index_serviceImageButton)
    ImageView mServiceImageButton;
    ResponseAppInfoModel mResponseAppInfoModel;
    Timer timer = new Timer();
    @BindView(R.id.weather_image_view)
    ImageView weatherImageView;
    @BindView(R.id.tv_weather_temperature)
    TextView tvWeatherTemperature;
    @BindView(R.id.img_home_city_choose)
    ImageView img_home_city_choose;
    @BindView(R.id.tv_home_city)
    TextView tvHomeCity;
    @BindView(R.id.img_back)
    ImageView img_back;

    public final String[] peimission = {"android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_WIFI_STATE"};
    private RefreshListener mRefreshListener;
    private boolean mDidAddGoodsListTitle = false;
    private boolean mDidDummyTitle = false;
    private boolean addNearShop = false;
    private boolean addListGoods = false;
    private boolean isFirstLoad = true;
    private IndexAdapter mIndexAdapter;
    private PageModel mPageModel = new PageModel();
    private NearShopPageModel mNearShopPageModel = new NearShopPageModel();
    private boolean goodsListIsMiddle;
    private boolean openFloatAdActivityIsOnce = true;
    private Set popupsList = new HashSet();
    //记录当前滚动是否停止
    private boolean isSlideStop = false;
    //记录滚动停止时间
    private long slideStopTime = System.currentTimeMillis();

    //猜你喜欢数据 分页int
    private int guess_page = 1;
    private boolean guess_more = false;

    private com.szy.yishopcustomer.ResponseModel.User.Model mModel;

    private List<GuessGoodsModel> mGoodsModelList = new ArrayList<>();
    private List<GuessGoodsModel> mGoodsModelList_More = new ArrayList<>();

    private UsableIngotModel mUsableIngotModel;
    List<TemplateModel> list = new ArrayList<>();

    private TemplateModel mTemplateModelGuessLike = new TemplateModel();

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    hideControlAnim();
                    break;
                case 1:
                    displayControlAnim();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //recycler的管理器
    LinearLayoutManager linearLayoutManager;
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };
    //记录滚动开始事件
    private long slideBeginTime = System.currentTimeMillis();
    private int moveCount = 0;

    private String goods_ids = "";
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        public void onScrolledUp() {
            moveCount = 0;
            isSlideStop = true;
            slideStopTime = System.currentTimeMillis();
        }

        public void onScrolledDown() {
            moveCount = 0;
        }

        public void onScrolledMove() {
            moveCount += 1;

            long nowTime = System.currentTimeMillis();
            //记录滑动的开始时间
            if (moveCount == 1 && (nowTime - slideStopTime) > SLIDE_TIME_PAUSE) {
                slideBeginTime = nowTime;
            }

            if (moveCount > 1) {
                isSlideStop = false;
                if ((nowTime - slideBeginTime) > SLIDE_BEGIN_TIME_INTERVAL) {
                    handler.sendEmptyMessage(0);
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            switch (newState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    onScrolledDown();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                    if (mContentWrapperRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {

                        if (guess_more) {
                            mGoodsModelList_More.clear();

                            guess_page++;
                            getLikeData();
                        }
                    }
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    //onScrolledUp();
                    break;
                default:
                    break;
            }

            //获取第一个可见view的位置
            int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();


            if (getActivity() instanceof RootActivity) {
                RootActivity rootActivity = (RootActivity) getActivity();
                if (firstItemPosition <= 0) {
                    rootActivity.changeHomeTop(true);
                } else {
                    if (firstItemPosition < lastTemplatePosition) {
                        rootActivity.changeHomeTop(true);
                    } else {
                        rootActivity.changeHomeTop(false);
                    }

                    if (firstItemPosition <= lastTemplatePosition && lastItemPosition >= lastTemplatePosition - 1) {
                        rootActivity.changeHomeTop(false);
                    }
                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            onScrolledMove();

            float offset = recyclerView.computeVerticalScrollOffset();

            float alpha = 0;
            //处理返回顶部按钮
            if (offset < GO_TO_TOP_BUTTON_START_APPEAR_POSITION) {
                alpha = GO_TO_TOP_BUTTON_MIN_ALPHA;
            } else {
                alpha = GO_TO_TOP_BUTTON_MIN_ALPHA + (offset -
                        GO_TO_TOP_BUTTON_START_APPEAR_POSITION) /
                        GO_TO_TOP_BUTTON_FULL_APPEAR_OFFSET;
                if (alpha > 1) {
                    alpha = 1;
                }
            }
            mGoToTopImageButton.setAlpha(alpha);
            alphaP = alpha;
            showTitleTranslation(alphaP);
            if (cur_style == TOOLBAR_STYLE_TRANSLUCENT) {
                if (alpha == 1) {
                    toolbarTop();
                } else {
                    toolbarDown();
                }
            }
        }
    };

    /**
     * 显示主页title是否透明
     */
    private void showTitleTranslation(float alpha) {
        if (alpha <= 0) {
            mGoToTopImageButton.setVisibility(View.INVISIBLE);
            if (cur_style == TOOLBAR_STYLE_TRANSLUCENT) {
                mTitle.setBackgroundResource(android.R.color.transparent);
                setWeatherForPic(weather);
            }
        } else {
            mGoToTopImageButton.setVisibility(View.VISIBLE);
            if (cur_style == TOOLBAR_STYLE_TRANSLUCENT) {
                mTitle.setBackgroundResource(R.color.white);
                setWeatherForPic(weather);
            }
        }
    }

    private String img_path;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (RequestCode.valueOf(requestCode)) {
                case REQUEST_CODE_LOGIN_FOR_COLLECTION:
                    openCollectionActivity();
                    break;
                case REQUEST_CODE_SERVICE: {
                    getAppInfo();
                    break;
                }
                case REQUEST_CODE_LOGIN_FOR_BALANCE:
                    openAccountBalanceActivity();
                    break;
/*            case REQUEST_CODE_MESSAGE:

                if(resultCode == getActivity().RESULT_OK) {
                    openMessageActivity();
                }

                break;*/


                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } catch (Exception e) {

        }

        //选择地址后回调
        if (resultCode == KEY_FINISH) {
            if (requestCode == REQUEST_CODE) {
                refreshAddress();
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {
                img_path = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);
                File picPath = new File(img_path);
                Uri uri = null;
                if (picPath.exists()) {
                    uri = Uri.fromFile(picPath);
                    initUCrop(uri);
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//UCrop裁剪之后的处理
                Uri resultUri = UCrop.getOutput(data);
                Intent intent = new Intent(getActivity(), ImgSearchActivity.class);
                intent.putExtra(Key.KEY_URL.getValue(), img_path);
                intent.putExtra(Key.KEY_TEMP_URL.getValue(), RxPhotoTool.getRealFilePath(getActivity(), resultUri));
                startActivity(intent);
            }
        }
    }

    public void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(getActivity(), R.color.aliwx_black));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(getActivity(), R.color.aliwx_black));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);
        options.setFreeStyleCropEnabled(true);
        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(getActivity());
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
        mRequestQueue.cancelAll();
    }

    private boolean isLike = true;

    @Override
    public void onLoading(PullableComponent pullableComponent) {

        refresh();
        getShopAndProduct();//获取商品和附近商家数据
        isLike = true;

        mIndexAdapter.noMoreViewIsVise(false);
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int size) {
    }

    public void jumpViewTop() {
        if (mContentWrapperRecyclerView != null) {
            mContentWrapperRecyclerView.getLayoutManager().smoothScrollToPosition
                    (mContentWrapperRecyclerView, null, 0);
        }
    }

    boolean visiable;

    @Override
    public void onStart() {
        visiable = false;
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick() || !visiable) {
            return;
        }


        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_TOP:
                jumpViewTop();
                break;
            case VIEW_TYPE_SHOP_LIST_DUMMY:
                openShopStreetOtherActivity(extraInfo + "");
                break;
            case VIEW_TYPE_GOODS_DUMMY_TITLE:
                openShopStreetActivity();
                break;
            case VIEW_TYPE_MESSAGE:
//                openMessageActivity();
                //跳转到分类
                rootActivity.openSortFragment();

                break;
            case VIEW_TYPE_SHOP_LIST_DUMMY_LOCATION:
                openMapActivity(position, extraInfo);
                break;
            case VIEW_TYPE_SITE:
                openSiteActivity(true);
                break;
            case VIEW_TYPE_SCAN://扫描
                openScanActivity();
                break;
            case VIEW_TYPE_AD:
                openAd(position, extraInfo);
                break;
            case VIEW_TYPE_HOME_CENTER_AD:
                openHomeCenterAd(position, extraInfo);
                break;
            case VIEW_TYPE_AD_COLUMN_THREE:
                openAdThree(position, extraInfo);
                break;
            case VIEW_TYPE_ARTICLE:
                openArticle(position);
                break;
            case VIEW_TYPE_ARTICLE_TITLE:
                openArticleTitle(position);
                break;
            case VIEW_TYPE_SHOP:
                clickShopAd(position, extraInfo);
                break;
            case VIEW_TYPE_GOODS:
                openGoods(position, extraInfo);
                break;
            case VIEW_TYPE_GOODS_LIST_ITEM:
                openGoodsListItem(position, extraInfo);
                break;
            case VIEW_TYPE_NAVIGATION_ITEM://点击10个按钮
                openMenu(position, extraInfo);
                break;
            case VIEW_TYPE_SEARCH:
                openSearchActivity();
                break;
            case VIEW_TYPE_GOODS_TITLE:
                openGoodsTitle(position);
                break;
            case VIEW_TYPE_SHOP_LIST_TITLE:
                openShopStreetActivity();
                break;
            case VIEW_TYPE_SERVICE:
                mProgress.show();
                openServiceActivity();
                break;
            case VIEW_TYPE_NOTICE:
                openNotice(position);
                break;
            case VIEW_INDEX_GUESS_LIKE:
                openGuessGoodItem(position, extraInfo);
                break;

            case VIEW_TYPE_NEAR_SHOP_MORE://附近商家更多
                openNearMoreAc();
                break;
            case VIEW_TYPE_INGOTS_BUY_MORE://元宝换购更多
                Intent mIntent = new Intent(getActivity(), YSCWebViewActivity.class);
                if (App.getInstance().city != null) {//定位成功
                    mIntent.putExtra(Key.KEY_URL.getValue(), Config.BASE_URL + "/guess/repurchase?latitude=" + App.getInstance().lat
                            + "&longitude=" + App.getInstance().lng + "&regionCode=" + App.getInstance().city_code);
                    LogUtils.Companion.e("当前的链接为" + Config.BASE_URL + "/guess/repurchase?latitude=" + App.getInstance().lat
                            + "&longitude=" + App.getInstance().lng + "&regionCode=" + App.getInstance().city_code);
                } else {//定位失败选择的城市
                    mIntent.putExtra(Key.KEY_URL.getValue(), Config.BASE_URL + "/guess/repurchase?regionCode=" + App.getInstance().city_code);
                }
                startActivity(mIntent);
                break;
            default:
                super.onClick(v);
        }
    }

    private void clickShopAd(int position, int extraInfo) {
        if (position == -1) {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .leaveASeatVacant));
        } else {
            openShop(position, extraInfo);
        }
    }

    private String cacheStr;

    private void init() {
        mContentWrapperRecyclerView.setEmptyViewClickListener(this);
        mContentWrapperRecyclerView.addOnScrollListener(mOnScrollListener);
        mContentWrapperRecyclerView.setAdapter(mIndexAdapter);
        mPullableLayout.topComponent.setOnPullListener(this);

//        mPullableLayout.topComponent.autoLoad();

        //从share中读取数据
        SharedPreferencesHelper.initialize(getActivity());
        cacheStr = (String) SharedPreferencesHelper.get(Api.API_APP_INDEX, "");

        if (!TextUtils.isEmpty(cacheStr)) {//读取缓存数据
            refreshCallback(cacheStr);
        }
        getShopAndProduct();//获取商品和附近商家数据
        refresh();

        linearLayoutManager = new LinearLayoutManager(getContext());
        mContentWrapperRecyclerView.setLayoutManager(linearLayoutManager);
        Utils.setViewTypeForTag(mGoToTopImageButton, ViewType.VIEW_TYPE_TOP);
        mGoToTopImageButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mMessageButton, ViewType.VIEW_TYPE_MESSAGE);
        mMessageButton.setOnClickListener(this);

        Utils.setViewTypeForTag(mSiteLayout, ViewType.VIEW_TYPE_SITE);
        mSiteLayout.setOnClickListener(this);

        searchRelativeLayout.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                openSearchActivity();
            }
        });
        mScanButton.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
//                openScanActivity();
                startActivityForResult(new Intent(getActivity(), CityListActivity.class), REQUEST_CODE);
            }
        });

        mImageView_SearchPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImgSearchActivity.class);
                startActivity(intent);
            }
        });

        Utils.setViewTypeForTag(mServiceImageButton, ViewType.VIEW_TYPE_SERVICE);
        mServiceImageButton.setOnClickListener(this);
        if ("1".equals(App.getInstance().aliim_icon_show)) {
            mServiceImageButton.setVisibility(View.VISIBLE);
        } else {
            mServiceImageButton.setVisibility(View.INVISIBLE);
        }


        mContentWrapperRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        moveCount = 0;
                        isSlideStop = true;
                        slideStopTime = System.currentTimeMillis();
                        break;


                }
                return false;
            }
        });

        mProgress = new CustomProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(true);
        mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                mRequestQueue.cancelAll();
            }
        });

        if (task != null) {
            task.cancel();  //将原任务从队列中移除
        }
        task = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(task, 1000, 500); // 1s后执行task,经过1s再次执行

        //设置返回键
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_RECEIVER_MESSAGE:
                break;
            case EVENT_REFRESH_INDEX:
                openFloatAdActivityIsOnce = true;
                refresh();
                break;
            /**登录成功后 刷新*/
            case EVENT_LOGIN:
                refresh();
                isLike = true;
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        showTitleTranslation(alphaP);//这里是防止请求时候title透明度错乱
        LogUtils.Companion.e("当前的错误参数" + response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INDEX:
                mIndexAdapter.data.clear();
                mContentWrapperRecyclerView.showOfflineView();
                mPullableLayout.topComponent.finish(Result.FAILED);
                break;
            case HTTP_INDEX_GOODS_LIST:
                mIndexAdapter.removeLastItem();
                mIndexAdapter.data.clear();
                mContentWrapperRecyclerView.showOfflineView();
                break;
            case HTTP_INDEX_DUMMY:
                mIndexAdapter.removeLastItem();
                mIndexAdapter.data.clear();
                mContentWrapperRecyclerView.showOfflineView();
                break;
            case HTTP_MALL_USER:
                App.getInstance().user_mall_permi = "-1";
                break;
            case HTTP_INDEX_GUESS_LIKE:
                mIndexAdapter.removeLastItem();
                mIndexAdapter.data.clear();
                mContentWrapperRecyclerView.showOfflineView();
                break;
            case HTTP_HOME_NEAR_SHOP://首页店铺商品参数
                LogUtils.Companion.e("附近商家错误数据" + response);
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        showTitleTranslation(alphaP);//这里是防止请求时候title透明度错乱
        switch (HttpWhat.valueOf(what)) {
            case HTTP_NO_READ_MESSAGE:
                break;
            case HTTP_INDEX:
//                getShopAndProduct();//获取商品和附近商家数据
                cacheStr = response;
                mPullableLayout.topComponent.finish(Result.SUCCEED);
                break;
            case HTTP_INDEX_GOODS_LIST:
                loadMoreGoodsCallback(response);
                break;
            case HTTP_INDEX_DUMMY:
                loadMoreGoodsNearShopCallback(response);
                break;
            case HTTP_APP_INFO:
                getAppInfoCallback(response);
                //请求成功后清除一下缓存
//                NoHttp.getCacheStore();
                break;
            case HTTP_GET_SITE:
                siteSubsiteRefreshCallback(response);
                break;
            case HTTP_GET_SITE_SELECT:
                HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager.HttpResultCallBack<CommonModel>() {
                    @Override
                    public void onSuccess(CommonModel back) {
                        App.getInstance().site_region_code = getLocalAdcode();

                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_INDEX
                                .getValue()));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_NEARBY
                                .getValue()));
                    }
                }, true);
                break;
            case HTTP_INGOT_USABLE:
                //设置元宝数量
                setIngotData(response);
                break;
            case HTTP_USER://初始化数据
                initUserCallback(response);
                break;
            case HTTP_USER_INVCODE:
                JSONObject object1 = JSONObject.parseObject(response);
                if (object1.getInteger("code") == 0) {
                    App.getInstance().user_inv_code = JSONObject.parseObject(JSONObject.parseObject(response).getString("data")).getString("invite_code");
                    AppPreference.getIntance().setInviteCode(App.getInstance().user_inv_code);
                }
                break;
            case HTTP_MALL_USER:
                JSONObject object = JSONObject.parseObject(response);
                App.getInstance().user_mall_permi = object.getString("code");
                break;
            case HTTP_INDEX_GUESS_LIKE:
                addLikeData(response);
                refreshCallback(cacheStr);
                isLike = false;
                break;
            case HTTP_HOME_NEAR_SHOP://首页店铺商品参数
                callBackData(what, response);
                break;
            default:
                //super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutId = R.layout.fragment_index;
        rootActivity = (RootActivity) getActivity();
        mIndexAdapter = new IndexAdapter(getActivity());
        mIndexAdapter.onClickListener = this;

        mIndexAdapter.setUserIngotNumber((RootActivity) getActivity());

        mIndexAdapter.windowWidth = Utils.getWindowWidth(getActivity());
        CookieManager cookieManager = NoHttp.getCookieManager();
        List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
        if (App.getInstance().isLogin()) {
            initUserMsg();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);


        init();
        if (PermissionUtils.hasPermission(getActivity(), peimission)) {
            setLocationWeather("");
        } else {
            PermissionUtils.requestPermission(getActivity(), PERMISSION_CODE, peimission);
        }
        return v;
    }

    public void openAd(int position, int extraInfo) {
        TemplateModel templateModel = mIndexAdapter.data.get(position);
        AdColumnModel adModel = JSON.parseObject(templateModel.data, AdColumnModel.class);
        AdItemModel adItemModel = adModel.pic_1.get(extraInfo);
        if (!TextUtils.isEmpty(adItemModel.link)) {
            new BrowserUrlManager(adItemModel.link).parseUrl(getContext(), adItemModel.link);
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openHomeCenterAd(int position, int extraInfo) {
        TemplateModel templateModel = mIndexAdapter.data.get(position);

        List<HomeShopAndProductBean.AdvertBean> list = GsonUtils.Companion.toList(templateModel.data, HomeShopAndProductBean.AdvertBean.class);

        if (!TextUtils.isEmpty(list.get(extraInfo).getLinkUrl())) {
            new BrowserUrlManager(list.get(extraInfo).getLinkUrl()).parseUrl(getContext(), list.get(extraInfo).getLinkUrl());
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void openAdThree(int position, int extraInfo) {
        TemplateModel templateModel = mIndexAdapter.data.get(position);
        AdColumnModel adColumnModel = JSON.parseObject(templateModel.data, AdColumnModel.class);

        if (extraInfo == 0) {
            if (!TextUtils.isEmpty(adColumnModel.pic_1.get(0).link)) {
                new BrowserUrlManager().parseUrl(getContext(), adColumnModel.pic_1.get(0).link);
            } else {
                if (Config.DEBUG) {
                    Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (extraInfo == 1) {
            if (!TextUtils.isEmpty(adColumnModel.pic_2.get(0).link)) {
                new BrowserUrlManager().parseUrl(getContext(), adColumnModel.pic_2.get(0).link);
            } else {
                if (Config.DEBUG) {
                    Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (extraInfo == 2) {
            if (!TextUtils.isEmpty(adColumnModel.pic_2.get(1).link)) {
                new BrowserUrlManager().parseUrl(getContext(), adColumnModel.pic_2.get(1).link);
            } else {
                if (Config.DEBUG) {
                    Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void openGoodsActivity(String skuId) {
        if (isFastClick()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), GoodsActivity.class);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
            startActivity(intent);
        }
    }

    public void openMapActivity(int position, int mPosition) {

        TemplateModel dataModel = mIndexAdapter.data.get(position);
        NearShopModel nearShopModel = JSON.parseObject(dataModel.data, NearShopModel.class);
        List<NearShopItemModel> items = nearShopModel.data.list;
        String shopName = items.get(mPosition).shop_name;
        String shopLat = items.get(mPosition).shop_lat;
        String shopLng = items.get(mPosition).shop_lng;
        String shopSimply = items.get(mPosition).simply_introduce;

        Intent intent = new Intent();
        intent.setClass(getActivity(), MapActivity.class);
        intent.putExtra(Key.KEY_MARKER_NAME.getValue(), shopName);
        intent.putExtra(Key.KEY_MARKER_SNIPPET.getValue(), shopSimply);
        intent.putExtra(Key.KEY_LATITUDE.getValue(), shopLat);
        intent.putExtra(Key.KEY_LONGITUDE.getValue(), shopLng);
        if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng)) {
            intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
            intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
        }
        startActivity(intent);
    }

    public void openMessageActivity() {
        startActivity(new Intent(getContext(), LyMessageActivity.class));
    }


    public void openScanActivity() {
        if (cameraIsCanUse()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), ScanActivity.class);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_SCAN.getValue());
        } else {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .noCameraPermission));
        }
    }

    public void openSearchActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_KEYWORD_ACTION.getValue(), 1);
        intent.setClass(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    private void openQq(String qq) {
        if (Utils.isQQClientAvailable(getActivity())) {
            final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
        } else {
            Utils.toastUtil.showToast(getActivity(), "请先安装QQ客户端");
        }
    }

    private void getAppInfo() {
        CommonRequest request = new CommonRequest(Api.API_APP_INFO, HttpWhat.HTTP_APP_INFO.getValue());
        request.setUserAgent("szyapp/android");
        request.alarm = false;
        addRequest(request);
    }

    private void getAppInfoCallback(String response) {
        HttpResultManager.resolve(response, ResponseAppInfoModel.class, new HttpResultManager.HttpResultCallBack<ResponseAppInfoModel>() {
            @Override
            public void onSuccess(ResponseAppInfoModel back) {
                mResponseAppInfoModel = back;
                App.getInstance().wangXinAppKey = mResponseAppInfoModel.data.aliim_appkey;
                App.getInstance().wangXinUserId = mResponseAppInfoModel.data.aliim_uid;
                App.getInstance().wangXinPassword = mResponseAppInfoModel.data.aliim_pwd;
                App.getInstance().wangXinServiceId = mResponseAppInfoModel.data.aliim_main_customer;
                if (mRefreshListener != null) {
                    mRefreshListener.onRefreshed();
                    mRefreshListener = null;
                }
            }
        }, true);
    }

    private void loadMoreGoods() {

        if (mIndexAdapter.getItemCount() == 0) {
            return;
        }
        if (goodsListIsMiddle && !isFirstLoad) {
            return;
        }
        ViewType lastItemViewType = mIndexAdapter.getLastItemViewType();

        if (lastItemViewType == ViewType.VIEW_TYPE_LOADING || lastItemViewType == ViewType.VIEW_TYPE_LOAD_DISABLED) {
            return;
        }

        if (addNearShop) {
            loadMoreSuccess = false;
            TemplateModel templateModel = new TemplateModel();
            templateModel.temp_code = Macro.TEMPLATE_CODE_LOADING;
            mIndexAdapter.insertAtLastPosition(templateModel);
            CommonRequest request = new CommonRequest(Api.API_INDEX_SITE, HttpWhat
                    .HTTP_INDEX_DUMMY.getValue(), RequestMethod.GET);
            int page = mNearShopPageModel.cur_page + 1;
            request.add("page[cur_page]", page);
            request.add("page[page_size]", 150);
            request.alarm = false;
            request.add("tpl_code", Macro.TEMPLATE_CODE_SHOP_LIST_DUMMY);
            String lat = App.getInstance().lat;
            String lng = App.getInstance().lng;
            if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng)) {
                request.add("lng", App.getInstance().lng);
                request.add("lat", App.getInstance().lat);
            }
            addRequest(request);
            return;
        }
        if (addListGoods) {

            TemplateModel templateModel = new TemplateModel();
            templateModel.temp_code = Macro.TEMPLATE_CODE_LOADING;
            mIndexAdapter.insertAtLastPosition(templateModel);
            CommonRequest request = new CommonRequest(Api.API_INDEX_SITE, HttpWhat
                    .HTTP_INDEX_GOODS_LIST.getValue(), RequestMethod.GET);
            request.alarm = false;
            request.add("page[cur_page]", mPageModel.cur_page + 1);
            request.add("page[page_size]", 20);
            request.add("goods_ids", goods_ids);
            request.add("tpl_code", Macro.TEMPLATE_CODE_GOODS_LIST_DUMMY);
            addRequest(request);
            return;
        }
    }

    /***
     * 猜你喜欢 data获取
     */
    private void getLikeData() {

        CommonRequest request = new CommonRequest(Api.API_GUESS_LIKE_URL, HttpWhat.HTTP_INDEX_GUESS_LIKE.getValue());
        request.setUserAgent("szyapp/android");
        if (App.getInstance().isLogin()) {
            request.add("user_id", App.getInstance().userId);
        } else {
            request.add("user_id", 0);
        }
        request.add("cur_page", guess_page);
        request.add("page_size", 24);
        request.alarm = false;

        addRequest(request);
    }


    //用于禁止底部连续滑动加载
    private boolean loadMoreSuccess = true;

    private void loadMoreGoodsCallback(final String response) {
        HttpResultManager.resolve(response, GoodsListModel.class, new HttpResultManager.HttpResultCallBack<GoodsListModel>() {
            @Override
            public void onSuccess(GoodsListModel back) {
                mPageModel = back.data.page;
                mIndexAdapter.removeLastItem();

                if (goodsListIsMiddle && isFirstLoad) {
                    for (int i = 0; i < mIndexAdapter.data.size(); i++) {
                        if (mIndexAdapter.data.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST)) {
                            mIndexAdapter.data.get(i).data = response;
                        }
                    }
                    mIndexAdapter.notifyDataSetChanged();
                    isFirstLoad = false;
                    return;
                }

                TemplateModel templateModel = new TemplateModel();
                templateModel.temp_code = Macro.TEMPLATE_CODE_GOODS_LIST;
                templateModel.data = JSON.toJSONString(back);
                mIndexAdapter.insertAtLastPosition(templateModel);

                if (mPageModel.page_count >= 0 && mPageModel.cur_page >= mPageModel.page_count) {
                    templateModel = new TemplateModel();
                    templateModel.temp_code = Macro.TEMPLATE_CODE_LOAD_DISABLED;
                    templateModel.data = getString(R.string.no_more_goods);
                    mIndexAdapter.insertAtLastPosition(templateModel);
                }
            }
        }, true);

        loadMoreSuccess = true;
    }


    private void loadMoreGoodsNearShopCallback(final String response) {
        HttpResultManager.resolve(response, NearShopModel.class, new HttpResultManager.HttpResultCallBack<NearShopModel>() {
            @Override
            public void onSuccess(NearShopModel nearShopModel) {
                mNearShopPageModel = nearShopModel.data.page;

                mIndexAdapter.removeLastItem();
                if (goodsListIsMiddle && isFirstLoad) {
                    for (int i = 0; i < mIndexAdapter.data.size(); i++) {
                        if (mIndexAdapter.data.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_SHOP_LIST_DUMMY)) {

                            if (!Utils.isNull(nearShopModel.data.list)) {
                                mIndexAdapter.data.get(i).data = response;
                            } else {
                                mIndexAdapter.data.get(i).temp_code = Macro.TEMPLATE_CODE_LOAD_DISABLED;
                                mIndexAdapter.data.get(i).data = nearShopModel.message;
                            }
                        }
                    }
                    mIndexAdapter.notifyDataSetChanged();
                    isFirstLoad = false;
                    return;
                }

                TemplateModel templateModel = new TemplateModel();

                if (nearShopModel.data.list != null && nearShopModel.data.list.size() > 0) {

                    for (int i = 0; i < mIndexAdapter.data.size(); i++) {
                        if (mIndexAdapter.data.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_SHOP_LIST_DUMMY)) {

                            NearShopModel aaaaa = JSON.parseObject(mIndexAdapter.data.get(i).data, NearShopModel.class);

                            if (aaaaa == null || aaaaa.data.list.size() <= 0) {
                                mIndexAdapter.data.remove(i);
                                i--;
                            }
                            mIndexAdapter.notifyDataSetChanged();

                            break;
                        }
                    }

                    templateModel.temp_code = Macro.TEMPLATE_CODE_GOODS_DUMMY;
                    templateModel.message = nearShopModel.message;
                    templateModel.data = JSON.toJSONString(nearShopModel);

                    mIndexAdapter.insertAtLastPosition(templateModel);


                } else {
                    //更新
                    for (int i = 0; i < mIndexAdapter.data.size(); i++) {
                        if (mIndexAdapter.data.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_SHOP_LIST_DUMMY)) {
                            mIndexAdapter.data.get(i).message = nearShopModel.message;
                            break;
                        }
                    }
                    mIndexAdapter.notifyDataSetChanged();
                }

                if (mNearShopPageModel.cur_page >= 0 && mNearShopPageModel.cur_page >= mNearShopPageModel
                        .page_count) {
                    templateModel = new TemplateModel();
                    templateModel.temp_code = Macro.TEMPLATE_CODE_LOAD_DISABLED;
                    templateModel.data = getString(R.string.noMoreShop);
                    mIndexAdapter.insertAtLastPosition(templateModel);
                }
            }
        }, true);

        loadMoreSuccess = true;

    }

    private void openAccountBalanceActivity() {
        if (App.getInstance().isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), AccountBalanceActivity.class);
            startActivity(intent);
        } else {
            openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_BALANCE);
        }
    }

    private void openArticle(int position) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        ArticleModel articleModel = JSON.parseObject(dataModel.data, ArticleModel.class);
        ArrayList<ArticleItemModel> articleItems = new ArrayList<>();
        articleItems.addAll(articleModel.article_1);
        openArticleListActivity(ARTICLE_LIST, articleItems);
    }

    private void openArticleListActivity(String title, ArrayList<ArticleItemModel>
            articleItemModels) {
        Intent intent = new Intent(getContext(), ArticleListActivity.class);
        intent.putParcelableArrayListExtra(Key.KEY_ARTICLE_LIST.getValue(), articleItemModels);
        intent.putExtra(Key.KEY_TITLE.getValue(), title);
        startActivity(intent);
    }

    private void openArticleTitle(int position) {
        TemplateModel templateModel = mIndexAdapter.data.get(position);
        ArticleModel articleModel = JSON.parseObject(templateModel.data, ArticleModel.class);
        if (!Utils.isNull(articleModel.pic_1)) {
            if (!TextUtils.isEmpty(articleModel.pic_1.get(0).link)) {
                new BrowserUrlManager().parseUrl(getContext(), articleModel.pic_1.get(0)
                        .link);
            } else {
                if (Config.DEBUG) {
                    Toast.makeText(getContext(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getContext(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCollectionActivity() {
        if (App.getInstance().isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), CollectionActivity.class);
            startActivity(intent);
        } else {
            openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_COLLECTION);
        }
    }

    private void openGoods(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GoodsColumnModel goodsModel = JSON.parseObject(dataModel.data, GoodsColumnModel.class);
        GoodsItemModel goodsItemModel = goodsModel.goods_1.get(extraInfo);
        openGoodsActivity(goodsItemModel.sku_id);
    }

    private void openGoodsListItem(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GoodsListModel goodsListModel = JSON.parseObject(dataModel.data, GoodsListModel.class);
        GoodsItemModel goodsItemModel = goodsListModel.data.list.get(extraInfo);
        openGoodsActivity(goodsItemModel.sku_id);
    }

    private void openGoodsTitle(int position) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GoodsTitleModel titleModel = JSON.parseObject(dataModel.data, GoodsTitleModel.class);
        if (!TextUtils.isEmpty(titleModel.title_1.link)) {
            new BrowserUrlManager().parseUrl(getContext(), titleModel.title_1.link);
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGuessGoodItem(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GuessGoodsModel goodsListModel = dataModel.mGoodsList.get(extraInfo);
        openGoodsActivity(goodsListModel.sku_id);
    }

    /**
     * 跳转到附近商店更多
     */
    private void openNearMoreAc() {
        if (isFastClick()) {
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
            intent.setClass(getActivity(), NearShopMoreActivity.class);
            startActivity(intent);
        }
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, requestCode.ordinal());
    }

    private void openMenu(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        NavigationModel navigatorModel = JSON.parseObject(dataModel.data, NavigationModel.class);
        NavigationItemModel navigatorItemModel = navigatorModel.mnav_1.get(extraInfo);


        if (!TextUtils.isEmpty(navigatorItemModel.link)) {
            if (navigatorItemModel.link.contains(Api.H5_MALL_URL)) {
                if (App.getInstance().isLogin()) {

                    /** 批发商城 url 匹配**/
                    if (App.getInstance().user_mall_permi.equals("0")) {
                        Intent intent = new Intent(getActivity(), YSCWebViewActivity.class);
                        intent.putExtra(Key.KEY_URL.getValue(), navigatorItemModel.link);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "暂无权限进入批发商城", Toast.LENGTH_LONG).show();
                    }
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            } else if (navigatorItemModel.link.equals(Api.CITYLIFE_HOME) || navigatorItemModel.link.equals(Api.CITYLIFE_HOME_NEW)) {
                App.getInstance().isCityChanage = true;
                startActivity(new Intent(getActivity(), CityLifeActivity.class));
            } else if (navigatorItemModel.link.contains("page/category")) {//跳转分类
                rootActivity.openSortFragment();
            } else if (navigatorItemModel.link.contains("/index.html#/categorys")) {//跳转原生的便利超市,家具嫁妆
                if (App.isShowH5) {//  显示H5
                    new BrowserUrlManager(navigatorItemModel.link).parseUrl(getContext(), navigatorItemModel.link);
                } else {//原生
                    String cateid = "";
                    Intent mIntent = new Intent(getActivity(), SortStoreActivity.class);
                    mIntent.putExtra(SortStoreActivity.SORT_NAME, navigatorItemModel.name);
                    if (!TextUtils.isEmpty(navigatorItemModel.link) && navigatorItemModel.link.contains("cateid")) {
                        cateid = UrlUtil.parse(navigatorItemModel.link).params.get("cateid");
                        mIntent.putExtra(SortStoreActivity.SORT_TYPE, cateid);
                    } else {
                        mIntent.putExtra(SortStoreActivity.SORT_TYPE, navigatorItemModel.link_type);
                    }
                    startActivity(mIntent);
                }

            } else {
//                new BrowserUrlManager("https://m.jbxgo.com/lbs/index.html#/directPay?storeId=3509&storeName=%E8%BE%BE%E5%B7%9E%E5%B8%82%E8%BE%BE%E5%B7%9D%E5%8C%BA%E6%99%AF%E7%A8%8B%E5%9F%B9%E8%AE%AD%E5%AD%A6%E6%A0%A1&page=shopIndex").parseUrl(getContext(), navigatorItemModel.link);
                new BrowserUrlManager(navigatorItemModel.link).parseUrl(getContext(), navigatorItemModel.link);
            }
        }

    }

    private void openServiceActivity() {

        try {
            if (!App.getInstance().isLogin()) {
                mProgress.hide();
                mRefreshListener = new RefreshListener() {
                    @Override
                    public void onRefreshed() {
                        if (App.getInstance().YWEnable) {
                            openServiceActivity();
                        } else {
                            Utils.openPhoneDialog(getActivity(), App.getInstance().phoneNumber);
                        }
                    }
                };
                openLoginActivityForResult(RequestCode.REQUEST_CODE_SERVICE);
                return;
            } else {
                if (App.getInstance().YWEnable) {

                } else if (Utils.isNull(App.getInstance().qq)) {
                    openQq(App.getInstance().qq);
                } else {
                    Utils.openPhoneDialog(getActivity(), App.getInstance().phoneNumber);
                }
            }

        } catch (Exception e) {

        }
    }

    private void openShop(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        ShopStreetModel shopStreetModel = JSON.parseObject(dataModel.data, ShopStreetModel.class);
        ShopStreetItemModel shopStreetItemModel = shopStreetModel.shop_1.get(extraInfo);
        openShopActivity(shopStreetItemModel.shop_id);
    }

    private void openShopStreetActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopStreetActivity.class);
        startActivity(intent);
    }

    private void openNotice(int position) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        NoticeModel noticeModel = JSON.parseObject(dataModel.data, NoticeModel.class);
        if (!TextUtils.isEmpty(noticeModel.pic_1.get(0).link)) {
            new BrowserUrlManager().parseUrl(getContext(), noticeModel.pic_1.get(0).link);
        }

    }

    private void openShopStreetOtherActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    private void openSiteActivity(boolean enableCloseButton) {
        startActivity(new Intent().putExtra(Key.KEY_ENABLE_CLOSE_BUTTON.getValue(),
                enableCloseButton).setClass(getActivity(), SiteActivity.class));
    }


    /**
     * 请求整个首页数据
     */
    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_APP_INDEX, HttpWhat.HTTP_INDEX.getValue());
        request.alarm = false;

        addRequest(request);
    }


    /**
     * 获取首页商品和店铺的相关信息
     */
    private void getShopAndProduct() {
        CommonRequest request = new CommonRequest(Api.API_SHOP_AND_PRODUCT, HttpWhat.HTTP_HOME_NEAR_SHOP.getValue());
        String type = "";
        if (App.getInstance().isLocation) {//定位成功
            if (!TextUtils.isEmpty(App.getInstance().lat)) {//定位成功
                if (App.getInstance().clickChangeCity) {//选择城市(点击变换的)
                    request.add("areaId", App.getInstance().home_area_code);
                    request.add("regionCode", App.getInstance().city_code);
                    type = "定位成功,选择城市1";
                } else {
                    request.add("areaId", App.getInstance().adcode);
                    request.add("latitude", App.getInstance().lat);
                    request.add("longitude", App.getInstance().lng);
                    type = "定位成功,用经纬度请求数据2";
                }
            } else {
                request.add("areaId", App.getInstance().home_area_code);
                request.add("regionCode", App.getInstance().city_code);
                type = "定位失败,选择城市3";
            }
        } else {//定位失败(选择了城市)
            request.add("areaId", App.getInstance().home_area_code);
            request.add("regionCode", App.getInstance().city_code);
            type = "定位失败,选择城市4";
        }

        LogUtils.Companion.e("请求方式" + type);
        LogUtils.Companion.e("areaId选择" + App.getInstance().home_area_code);
        LogUtils.Companion.e("regionCode" + App.getInstance().city_code);
        LogUtils.Companion.e("areaId定位" + App.getInstance().adcode);
        LogUtils.Companion.e("latitude" + App.getInstance().lat);
        LogUtils.Companion.e("longitude" + App.getInstance().lng);
        request.alarm = false;

        addRequest(request);
    }

    /**
     * 商品和店铺数据请求回调
     *
     * @param what
     * @param response
     */
    private void callBackData(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_HOME_NEAR_SHOP:
                LogUtils.Companion.e("主页附近商家数据-->" + response);
                list.clear();
                if (!GSONUtil.isGoodGson(response, HomeShopAndProductBean.class)) {
                    LogUtils.Companion.e("数据解析错误" + response);
                    return;
                }
                HomeShopAndProductBean bean = GSONUtil.getEntity(response, HomeShopAndProductBean.class);
                assert bean != null;
                if (null != bean.getSort()) {
                    for (int i = 0; i < bean.getSort().size(); i++) {
                        TemplateModel templateModel = new TemplateModel();
                        if (TEMPLATE_VIEW_TYPE_AD_ADVERT.equals(bean.getSort().get(i).getType())) {
                            templateModel.temp_code = bean.getSort().get(i).getKey();
                        } else {
                            templateModel.temp_code = bean.getSort().get(i).getType();
                        }
                        switch (bean.getSort().get(i).getType()) {
                            case TEMPLATE_VIEW_TYPE_AD_ADVERT:
                                switch (bean.getSort().get(i).getKey()) {
                                    case TEMPLATE_VIEW_TYPE_AD_ADVERT:
                                        templateModel.data = GsonUtils.Companion.toJson(bean.getAdvert());
                                        break;
                                    case TEMPLATE_CODE_SHOP_JION:
                                        if (null != bean.getShop_num()) {
                                            templateModel.data = GsonUtils.Companion.toJson(bean.getShop_num());
                                        }
                                        break;
                                }
                                break;
                            case TEMPLATE_CODE_INGOTS_BUY:
                                templateModel.data = GsonUtils.Companion.toJson(bean.getRepurchase());
                                break;
                            case TEMPLATE_CODE_NEAR_SHOP:
                                templateModel.data = GsonUtils.Companion.toJson(bean.getNearby());
                                break;
                            case TEMPLATE_CODE_SHOP_NEW_JOIN:
                                templateModel.data = GsonUtils.Companion.toJson(bean.getShop_new());
                                break;
                            case TEMPLATE_CODE_NET_HOT_SHOP:
                                templateModel.data = GsonUtils.Companion.toJson(bean.getShop_hot());
                                break;
                            case TEMPLATE_CODE_LIKE_SHOP:
                                templateModel.data = GsonUtils.Companion.toJson(bean.getShop_like());
                                break;
                            case TEMPLATE_CODE_HOME_VIDEO_SHOW:
                                templateModel.data = GsonUtils.Companion.toJson(bean.getLive());
                                break;
                        }
                        list.add(templateModel);
                    }
                }

                //添加加数据模板
                if (isLike) {
                    getLikeData();
                } else {
                    addLikeData(App.getInstance().index_like_data);
                    refreshCallback(cacheStr);
                }
                break;
        }
    }

    public void getMallUerInfo() {

        if (App.getInstance().isLogin()) {
            CommonRequest request = new CommonRequest(Api.H5_MALL_USER, HttpWhat.HTTP_MALL_USER.getValue());
            request.add("user_id", App.getInstance().userId);
            request.alarm = false;

            addRequest(request);
        } else {
            App.getInstance().user_mall_permi = "-1";
        }
    }

    public void getUserCode() {
        CommonRequest request = new CommonRequest(Api.API_USER_CODE, HttpWhat.HTTP_USER_INVCODE.getValue());
        request.add("user_id", App.getInstance().userId);
        request.alarm = false;

        addRequest(request);
    }

    /**
     * 添加猜你喜欢数据
     *
     * @param response
     */
    private void addLikeData(String response) {

        mPullableLayout.topComponent.finish(Result.SUCCEED);

        try {

            int code = JSONObject.parseObject(response).getInteger("code");
            String message = JSONObject.parseObject(response).getString("message");
            JSONObject object_data = JSONObject.parseObject(response).getJSONObject("data");

            if (object_data != null) {
                if (code == 0) {
                    mTemplateModelGuessLike.temp_code = "INDEX_GUESS_LIKE";
                    if (guess_page == 1) {
                        mGoodsModelList = com.alibaba.fastjson.JSON.parseArray(object_data.getString("list"), GuessGoodsModel.class);
                        if (mGoodsModelList.size() > 0) {
                            mTemplateModelGuessLike.mGoodsList = mGoodsModelList;
                            guess_more = true;
                        }
                    } else {
                        mGoodsModelList_More = com.alibaba.fastjson.JSON.parseArray(object_data.getString("list"), GuessGoodsModel.class);

                        if (mGoodsModelList_More.size() > 0) {
                            mGoodsModelList.addAll(mGoodsModelList_More);
                            mTemplateModelGuessLike.mGoodsList = mGoodsModelList;
                            guess_more = true;
                        } else {
                            guess_more = false;
                            mIndexAdapter.noMoreViewIsVise(true);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 模版数据回调成功
     *
     * @param response
     */
    private void refreshCallback(final String response) {

        mPullableLayout.topComponent.finish(Result.SUCCEED);
//        loadMoreGoods();

        final Model ttt = JSON.parseObject(response, Model.class);
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {

            @Override
            public void onSuccess(Model back1) {
                mPageModel.cur_page = 0;
                mNearShopPageModel.cur_page = 0;
                addNearShop = false;
                goodsListIsMiddle = false;
                addListGoods = false;
                mIndexAdapter.data.clear();


                try {
                    List<TemplateModel> templateModels = ttt.data.template;
                    for (int i = 0; i < templateModels.size(); i++) {
                        if (templateModels.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_GOODS_LIST_DUMMY)) {
                            templateModels.remove(i);
                            i--;
                        } else if (templateModels.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_SHOP_LIST_DUMMY)) {
                            templateModels.remove(i);
                            i--;
                        }
                    }
                } catch (Exception e) {
                }

                /**添加模版数据到sp 缓存**/
                SharedPreferencesHelper.put(Api.API_APP_INDEX, JSON.toJSONString(ttt));

                DataModel back = back1.data;

                switch (back.app_header_style) {
                    case "0":
                        toolbarStyleSwitch(TOOLBAR_STYLE_HIDE);
                        break;
                    case "1":
                        toolbarStyleSwitch(TOOLBAR_STYLE_SHOW);
                        break;
                    case "2":
                        toolbarStyleSwitch(TOOLBAR_STYLE_TRANSLUCENT);
                        break;
                    default:
                        toolbarStyleSwitch(TOOLBAR_STYLE_SHOW);
                        break;
                }

                goods_ids = "";
                if (isPop(back.site_id)) {
                    openFloatAdActivityIsOnce = false;
                }

                if (!TextUtils.isEmpty(App.getInstance().aliim_icon)) {
                    ImageLoader.displayImage(Utils.urlOfImage(App.getInstance().aliim_icon, false), mServiceImageButton);
                } else {
                    mServiceImageButton.setImageResource(R.mipmap.btn_customer_service);
                }

                App.setCartNumber(back.goods_counts, this);
                if (back.template != null) {
                    for (int i = 0; i < back.template.size(); i++) {
                        if (back.template.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_SHOP_LIST_DUMMY)) {
                            addNearShop = true;
                        } else if (back.template.get(i).temp_code.equals(Macro
                                .TEMPLATE_CODE_GOODS_LIST_DUMMY)) {
                            addListGoods = true;

                            //如果有滚动商品，就获取里面的商品id
                            if (!TextUtils.isEmpty(back.template.get(i).data)) {
                                IndexGoodListModel indexGoodListModel = JSON.parseObject(back.template.get(i).data, IndexGoodListModel.class);

                                if (indexGoodListModel != null && indexGoodListModel.getGoods_1() != null) {
                                    for (int gi = 0, glen = indexGoodListModel.getGoods_1().size(); gi < glen; gi++) {
                                        goods_ids += indexGoodListModel.getGoods_1().get(gi).getGoods_id();

                                        if (gi < glen - 1) {
                                            goods_ids += ",";
                                        }
                                    }
                                }
                            }
                        } else if (back.template.get(i).temp_code.equals(Macro.TEMPLATE_CODE_FLOAT_AD)) {

                            if (openFloatAdActivityIsOnce) {

                                popupsList.add(back.site_id);
                                openFloatADActivity(back.template.get(i).data);
                            }
                        }
                    }
                }
                initSit(back);
                DataModel dataModel = back;
                if (dataModel.template == null) {
                    dataModel.template = new ArrayList<>();
                }
                List<TemplateModel> data = dataModel.template;


                /*** 添加商品和商店 模板在适配器中**/
                data.addAll(list);

                if (!TextUtils.isEmpty(mTemplateModelGuessLike.temp_code)) {
                    /***
                     * 添加 猜你喜欢model 到adapter
                     */
                    TemplateModel liketitle = new TemplateModel();
                    liketitle.temp_code = TEMPLATE_CODE_GUESS_LIKE_TITLE;
                    data.add(liketitle);
                    data.add(mTemplateModelGuessLike);
                }

                //只返回支持的模块的数量,并删除不支持的模块
                for (int i = data.size() - 1; i >= 0; i--) {
                    if (data.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST) && i < data.size() - 1) {
                        goodsListIsMiddle = true;
                    }
                    if (data.get(i).temp_code.equals(Macro.TEMPLATE_CODE_SHOP_LIST_DUMMY) && i < data.size() - 1) {
                        goodsListIsMiddle = true;
                    }

                    if (Utils.getTemplateViewType(data.get(i).temp_code) == ViewType.VIEW_TYPE_NOT_SUPPORT) {
                        data.remove(i);
                    }
                }

                if (data.size() == 0) {
                    mContentWrapperRecyclerView.setEmptyImage(R.mipmap.bg_public);
                    mContentWrapperRecyclerView.setEmptyTitle(R.string.emptyIndexDecoration);
                    mContentWrapperRecyclerView.showEmptyView();
                }
                //如果第一个没有banner的话
                if (data.size() > 0) {
                    if (!data.get(0).temp_code.equals(Macro.TEMPLATE_CODE_AD_BANNER)) {
                        TemplateModel model = new TemplateModel();
                        model.temp_code = Macro.TEMPLATE_CODE_AD_BANNER;
                        data.add(0, model);
                    }
                }


                mIndexAdapter.data = data;
                mDidAddGoodsListTitle = false;
                mDidDummyTitle = false;
                isFirstLoad = true;
                mIndexAdapter.notifyDataSetChanged();

                //记录最后一个模板位置
                lastTemplatePosition = getIndexAdapterCount() - 1;
                if (addListGoods || addNearShop) {
                    //记录最后一个模板位置
                    lastTemplatePosition = getIndexAdapterCount();
                    //loadMoreGoods();
                }

            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        visiable = true;
        if (App.getInstance().isLogin()) {

            getUserIngot();

            getUserCode();

            getMallUerInfo();
        }
    }

    /**
     * 刷新地址
     * 选择地址后刷新
     */
    private void refreshAddress() {
        if (App.getInstance().isCityChanage) {//从新选择了地址
            String address = null;
            //这里去除市城区是因为API 查询有市城区就查询不了
            if (!TextUtils.isEmpty(App.getInstance().city_name)) {
                if (App.getInstance().city_name.contains("市城区")) {
                    address = App.getInstance().city_name.replace("市城区", "");
                } else if (App.getInstance().city_name.contains("主城")) {
                    address = App.getInstance().city_name.replace("主城", "");
                } else {
                    address = App.getInstance().city_name;
                }
            }

            getShopAndProduct();//获取商品和附近商家数据
            refresh();

            //只要切换了地址就查询一次天气
            setLocationWeather(address);
            if (App.getInstance().city != null) {
                /** 定位成功 */
                tvHomeCity.setText(App.getInstance().city);
                /** 定位成功后选择城市 */
                if (App.getInstance().city_name != null) {
                    tvHomeCity.setText(App.getInstance().city_name);
                }
            } else {
                /** 定位失败 选择城市 */
                if (App.getInstance().city_name != null) {
                    tvHomeCity.setText(App.getInstance().city_name);
                }
            }
        }
    }


    /**
     * 获取 我的元宝 数量
     ***/
    public void getUserIngot() {
        CommonRequest request = new CommonRequest(Api.API_INGOT_USABLE, HttpWhat.HTTP_INGOT_USABLE.getValue());
        request.alarm = false;

        addRequest(request);
    }

    public void setIngotData(String ingotData) {
        mUsableIngotModel = com.alibaba.fastjson.JSON.parseObject(ingotData, UsableIngotModel.class);
        if (mUsableIngotModel.getCode() == 0) {
            App.getInstance().user_ingot_number = mUsableIngotModel.getData().getTotal_integral().getIntegral_num();
            mIndexAdapter.refreshInGots(mUsableIngotModel.getData().getTotal_integral().getIntegral_num());
            mIndexAdapter.notifyItemChanged(0);
            if (((RootActivity) getActivity()).userIngotNumberView != null) {
                ((RootActivity) getActivity()).userIngotNumberView.setText(App.getInstance().user_ingot_number);
            }
        } else {
            if (!TextUtils.isEmpty(App.getInstance().user_ingot_number) && null != ((RootActivity) getActivity()).userIngotNumberView) {
                //未登录
                App.getInstance().user_ingot_number = "0";
                ((RootActivity) getActivity()).userIngotNumberView.setText(App.getInstance().user_ingot_number);
            }

        }
    }

    public int getIndexAdapterCount() {
        return mIndexAdapter.data.size();
    }

    private void openFloatADActivity(String data) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_FLOAT_DATA.getValue(), data);
        intent.setClass(getActivity(), FloatADActivity.class);
        startActivity(intent);
        openFloatAdActivityIsOnce = false;
    }

    private void initSit(DataModel back) {
        if (back.SYS_SITE_MODE.equals("1")) {
            mTitle.removeAllViews();
            if (Utils.isNull(back.site_name)) {
                mSiteTextView.setVisibility(View.GONE);
                //mSiteTextView.setText(R.string.unknownSite);
            } else {
                mSiteTextView.setVisibility(View.VISIBLE);
                mSiteTextView.setText(back.site_name);
            }
            mSiteLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParam.setMargins((int) (40 / 1.5 + 0.5), 0, (int) (20 / 1.5 + 0.5), 0);
            mScanButton.setLayoutParams(layoutParam);
            LinearLayout.LayoutParams layoutParamTwo = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamTwo.setMargins((int) (20 / 1.5 + 0.5), 0, (int) (40 / 1.5 + 0.5), 0);
            mMessageButton.setLayoutParams(layoutParamTwo);
            searchRelativeLayout.setBackgroundResource(R.drawable
                    .fragment_index_title_search_background);
            mTitle.addView(mSiteLayout);
            mTitle.addView(searchRelativeLayout);
            mTitle.addView(mScanButton);
            mTitle.addView(mMessageButton);
            if (back.site_id == null) {
                openSiteActivity(false);
            }

            site_subsite();
        } else {
            mTitle.removeAllViews();
            mSiteLayout.setVisibility(View.GONE);
            searchRelativeLayout.setBackgroundResource(R.drawable
                    .fragment_index_title_search_background);
            mTitle.addView(mScanButton);
            mTitle.addView(searchRelativeLayout);
            mTitle.addView(mMessageButton);
        }
    }

    private void selectSite(String siteId) {
        CommonRequest mRequest = new CommonRequest(Api.API_SITE_SELECT, HttpWhat
                .HTTP_GET_SITE_SELECT.getValue(), RequestMethod.POST);
        mRequest.add("site_id", siteId);
        mRequest.alarm = false;

        addRequest(mRequest);
    }

    SiteSubsiteModel siteSubsiteModel;

    private void siteSubsiteRefreshCallback(String response) {
        HttpResultManager.resolve(response, SiteSubsiteModel.class, new HttpResultManager.HttpResultCallBack<SiteSubsiteModel>() {
            @Override
            public void onSuccess(SiteSubsiteModel back) {
                siteSubsiteModel = back;
                showConfirmDialog("您当前所在的城市：" + back.data.city + ",是否切换到此城市下的站点", ViewType.VIEW_TYPE_SITE.ordinal(), 0, 0);
            }

            @Override
            public void onFailure(String message) {
//                super.onFailure(message);
            }
        }, true);
    }

    AlertDialog mConfirmDialog = null;

    public void showConfirmDialog(String message, final int viewType, final int position, final int extraInfo) {
        View view = LayoutInflater.from(this.getContext()).inflate(com.szy.common.R.layout.dialog_common_confirm, (ViewGroup) null);

        if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
            mConfirmDialog.dismiss();
        }

        mConfirmDialog = (new AlertDialog.Builder(this.getContext())).create();
        mConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mConfirmDialog.setView(view);
        Button confirmButton = (Button) view.findViewById(com.szy.common.R.id.dialog_common_confirm_confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                mConfirmDialog.hide();
                onConfirmDialogConfirmed(viewType, position, extraInfo);
            }
        });
        Button cancelButton = (Button) view.findViewById(com.szy.common.R.id.dialog_common_confirm_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                mConfirmDialog.hide();
                onConfirmDialogCanceled(viewType, position, extraInfo);
            }
        });
        TextView textView = (TextView) view.findViewById(com.szy.common.R.id.dialog_common_confirm_textView);
        textView.setText(message);
        mConfirmDialog.show();
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_SITE:
                if (siteSubsiteModel != null) {
                    selectSite(siteSubsiteModel.data.site_id);
                }
                break;
        }
    }

    @Override
    public void onConfirmDialogCanceled(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_SITE:
                if (siteSubsiteModel != null && !TextUtils.isEmpty(App.getInstance().adcode)) {
                    //记录一下子当前的site_id,等
                    SharedPreferencesUtils.setParam(getContext(), "no_alert_adcode", App.getInstance().adcode);
                }
                break;
        }
    }

    private void site_subsite() {
        String share_adcode = (String) SharedPreferencesUtils.getParam(getContext(), "no_alert_adcode", "");
        String site_region_code = App.getInstance().site_region_code;
        String adcode = App.getInstance().adcode;

        if (!share_adcode.equals(adcode) && !TextUtils.isEmpty(site_region_code) && !TextUtils.isEmpty(adcode)) {


            CommonRequest request = new CommonRequest(Api.API_SITE_SUBSITE, HttpWhat
                    .HTTP_GET_SITE.getValue());
            request.add("site_region_code", site_region_code);
            request.add("location_region_code", getLocalAdcode());
            request.alarm = false;

            addRequest(request);
        }
    }

    public String getLocalAdcode() {
        String adcode = App.getInstance().adcode;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 2, len = adcode.length(); i < len; i += 2) {
            stringBuffer.append(adcode.substring(i - 2, i));
            stringBuffer.append(",");
        }
        stringBuffer.append(adcode.substring(adcode.length() - 2 + adcode.length() % 2, adcode.length()));

        return stringBuffer.toString();
    }

    private void updateMessageVisibile(int VISIBLE) {
        App.getInstance().isUnreadMessage = VISIBLE;
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_UPDATE_MESSAGE_VISIBILE.getValue(), VISIBLE + ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        //EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    private void hideControlAnim() {
        if (mServiceImageButton.getTag() == null || mServiceImageButton.getTag().equals("1")) {
            mServiceImageButton.setTag("0");
            ObjectAnimator animator = ObjectAnimator.ofFloat(mServiceImageButton, "translationX", 0.0f, mServiceImageButton.getWidth() + Utils.dpToPx(getActivity(), 15));
            animator.setDuration(ANIM_RUN_TIME);//动画时间
            animator.setStartDelay(ANIM_DELAY_TIME);//设置动画延时执行
            animator.start();//启动动画
        }
    }

    private void displayControlAnim() {
        if (mServiceImageButton.getTag() == null || mServiceImageButton.getTag().equals("0")) {

            //判断滑动是否停止，还有停止事件超没超过2秒
            if (isSlideStop && (System.currentTimeMillis() - slideStopTime) > SLIDE_TIME_INTERVAL) {
                isSlideStop = false;
                mServiceImageButton.setTag("1");
                ObjectAnimator animator = ObjectAnimator.ofFloat(mServiceImageButton, "translationX", mServiceImageButton.getWidth() + Utils.dpToPx(getActivity(), 15), 0.0f);
                animator.setDuration(ANIM_RUN_TIME);//动画时间
                animator.setStartDelay(ANIM_DELAY_TIME);//设置动画延时执行
                animator.start();//启动动画
            }
        }
    }

    private boolean isPop(String siteid) {

        try {

            Iterator<String> iterator = popupsList.iterator();
            while (iterator.hasNext()) {

                if (iterator.next().equals(siteid)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private interface RefreshListener {
        void onRefreshed();
    }

    private int cur_style = TOOLBAR_STYLE_SHOW;
    private static final int TOOLBAR_STYLE_HIDE = 0;
    private static final int TOOLBAR_STYLE_SHOW = 1;
    private static final int TOOLBAR_STYLE_TRANSLUCENT = 2;

    private static final int TOOLBAR_STATE_TAG = R.id.fragment_index_title;

    private void toolbarStyleSwitch(int style) {
        cur_style = style;
        //初始化原本的样子

        toolbarInit();
        switch (style) {
            case TOOLBAR_STYLE_HIDE:
                mTitle.setVisibility(View.GONE);
                break;
            case TOOLBAR_STYLE_SHOW:
                mTitle.setVisibility(View.VISIBLE);
                break;
            case TOOLBAR_STYLE_TRANSLUCENT:
                toolbarTranslucent();
                break;
            default:
                break;
        }
    }

    public void toolbarInit() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPullableLayout.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
        }
        layoutParams.addRule(RelativeLayout.BELOW, R.id.fragment_index_title);
        searchRelativeLayout.setAlpha(1);

        mTitle.setBackgroundResource(R.color.white);
        mTitle.setTag(TOOLBAR_STATE_TAG, "show");
    }

    public void toolbarTop() {
        if (!"show".equals(mTitle.getTag(TOOLBAR_STATE_TAG))) {
            searchRelativeLayout.setAlpha(1);

            mTitle.setTag(TOOLBAR_STATE_TAG, "show");
        }
    }

    public void toolbarDown() {
        if (!"translucent".equals(mTitle.getTag(TOOLBAR_STATE_TAG))) {
            mTitle.setVisibility(View.VISIBLE);

            mTitle.setBackgroundResource(android.R.color.transparent);
            searchRelativeLayout.setAlpha(0.8f);
            mTitle.setTag(TOOLBAR_STATE_TAG, "translucent");
        }
    }

    public void toolbarTranslucent() {
        if (!"translucent".equals(mTitle.getTag(TOOLBAR_STATE_TAG))) {
            mTitle.setVisibility(View.VISIBLE);

            mTitle.setBackgroundResource(android.R.color.transparent);
            searchRelativeLayout.setAlpha(0.8f);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPullableLayout.getLayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.removeRule(RelativeLayout.BELOW);
            }
            ((RelativeLayout.LayoutParams) mPullableLayout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_TOP);

            mTitle.setTag(TOOLBAR_STATE_TAG, "translucent");
        }
    }

    private int lastTemplatePosition = 0;

    public void jumpLastTemplate() {
//        MoveToPosition(linearLayoutManager,mContentWrapperRecyclerView,lastTemplatePosition);

        smoothMoveToPosition(mContentWrapperRecyclerView, lastTemplatePosition);
    }


    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {

        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);

            int top = mRecyclerView.getChildAt(n).getTop();
            mRecyclerView.scrollBy(0, top);
        }
    }


    /**
     * 初始化用户信息
     */
    private void initUserMsg() {
        CommonRequest request = new CommonRequest(Api.API_USER, HttpWhat.HTTP_USER.getValue());
        request.setAjax(true);
        request.alarm = false;

        addRequest(request);
    }

    public void initUserCallback(String response) {
        HttpResultManager.resolve(response, com.szy.yishopcustomer.ResponseModel.User.Model.class, new HttpResultManager.HttpResultCallBack<com.szy.yishopcustomer.ResponseModel.User.Model>() {
            @Override
            public void onSuccess(com.szy.yishopcustomer.ResponseModel.User.Model back) {
                mModel = back;
//                Log.e("当前初始化数据成功",mModel.data.context.user_info.mobile);
                App.getInstance().userEmail = mModel.data.context.user_info.email;
                App.getInstance().userName = mModel.data.context.user_info.user_name;
                App.getInstance().userPhone = mModel.data.context.user_info.mobile;
                App.getInstance().headimg = mModel.data.context.user_info.headimg;
                App.getInstance().user_money_format = mModel.data.info.user_money_format;
//                App.getInstance().nickName = mModel.data.context.user_info.nickname;

                if (!"0".equals(mModel.data.reachbuy_order_counts)) {
                    App.getInstance().is_reachbuy_enable = 1;
                } else {
                    App.getInstance().is_reachbuy_enable = 0;
                }

                if (!"0".equals(mModel.data.is_scancode_enable)) {
                    App.getInstance().is_scancode_enable = 1;
                } else {
                    App.getInstance().is_scancode_enable = 0;
                }
                EventBus.getDefault().post(new CommonEvent.Builder(EventWhat.EVENT_LOGIN.getValue()).setMessageSource(this));
                App.setCartNumber(mModel.data.context.cart.goods_count, this);

            }

            @Override
            public void onLogin() {
            }
        });
    }

    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.scrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.scrollToPosition(position);
            mToPosition = position;

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    smoothMoveToPosition(mContentWrapperRecyclerView, mToPosition);
                }
            }, 100);

        }
    }


    /**
     * 定位天气
     */
    private void setLocationWeather(String location) {
        weatherImageView.setBackgroundResource(R.mipmap.icon_dy_white);

        //location 为空的话就是定位 需要半天才去一次,否则就是改变了位置每次都取
        if (TextUtils.isEmpty(location)) {//定位中需要半天才去一次数据
            /***开启定位 获取位置信息**/
            Location.locationCallback(new Location.OnLocationListener() {
                @Override
                public void onSuccess(AMapLocation amapLocation) {
//                    amapLocation.getStreet()+amapLocation.getStreetNum()+
                    App.getInstance().addressDetail = amapLocation.getAoiName();
                    App.getInstance().isLocation = true;
                    getShopAndProduct();
                    tvHomeCity.setText(App.getInstance().city);

                    if (System.currentTimeMillis() - SharedPreferencesUtils.getLongValue(getActivity(), Key.KEY_LAST_TIME.getValue()) < 12 * 3600 * 1000) {
                        return;
                    }
                    //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
                    WeatherSearchQuery mquery = new WeatherSearchQuery(amapLocation.getCity(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
                    WeatherSearch mweathersearch = new WeatherSearch(getActivity());
                    mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                        @Override
                        public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                            if (rCode == 1000) {
                                if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                                    SharedPreferencesUtils.setLongValue(getActivity(), Key.KEY_LAST_TIME.getValue(), System.currentTimeMillis());
                                    weather = weatherLiveResult.getLiveResult().getWeather();
                                    setWeatherForPic(weatherLiveResult.getLiveResult().getWeather());
                                    tvWeatherTemperature.setText(weatherLiveResult.getLiveResult().getTemperature() + "°C");
                                } else {
                                    Utils.makeToast(getActivity(), "天气不见了哟!");
                                }
                            } else {
                                LogUtils.Companion.e("天气错误码" + rCode);
                            }
                        }

                        @Override
                        public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

                        }
                    });
                    mweathersearch.setQuery(mquery);
                    mweathersearch.searchWeatherAsyn(); //异步搜索
                }

                @Override
                public void onError(AMapLocation amapLocation) {

                    App.getInstance().isLocation = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("定位失败请选择城市.");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(getActivity(), CityListActivity.class));
                        }
                    });
                    builder.create().show();
                }

                @Override
                public void onFinished(AMapLocation amapLocation) {
                    Location.stopLocation();
                }
            });
        } else {
            //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
            WeatherSearchQuery mquery = new WeatherSearchQuery(location, WeatherSearchQuery.WEATHER_TYPE_LIVE);
            WeatherSearch mweathersearch = new WeatherSearch(getActivity());
            mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                    if (rCode == 1000) {
                        if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                            SharedPreferencesUtils.setLongValue(getActivity(), Key.KEY_LAST_TIME.getValue(), System.currentTimeMillis());
                            setWeatherForPic(weatherLiveResult.getLiveResult().getWeather());
                            weather = weatherLiveResult.getLiveResult().getWeather();
                            tvWeatherTemperature.setText(weatherLiveResult.getLiveResult().getTemperature() + "°C");
                        } else {
                            Utils.makeToast(getActivity(), "天气不见了哟!");
                        }
                    } else {
                        LogUtils.Companion.e("天气错误码" + rCode);
                    }
                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

                }
            });
            mweathersearch.setQuery(mquery);
            mweathersearch.searchWeatherAsyn(); //异步搜索
        }

    }

    /**
     * 根据天气配图片
     */
    private void setWeatherForPic(String weather) {
//        LogUtils.Companion.e("weather" + weather);
        if (alphaP <= 0) {//背景透明图片白色
            if (null != tvHomeCity) {
                tvHomeCity.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
            tvWeatherTemperature.setTextColor(getActivity().getResources().getColor(R.color.white));
            img_home_city_choose.setBackgroundResource(R.mipmap.btn_arrow_down_white);
            searchRelativeLayout.setBackgroundResource(R.drawable.fragment_index_title_search_background);
            weatherImageView.setBackgroundResource(R.mipmap.icon_dy_white);
            if (weather.contains("多云")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_dy_white);
            } else if (weather.contains("雨")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_ly_white);
            } else if (weather.contains("雪")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_x_white);
            } else if (weather.contains("晴")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_q_white);
            }
        } else {//背景白色 图片黑色
            tvHomeCity.setTextColor(getActivity().getResources().getColor(R.color.ingot_detail_text));
            tvWeatherTemperature.setTextColor(getActivity().getResources().getColor(R.color.ingot_detail_text));
            img_home_city_choose.setBackgroundResource(R.mipmap.bg_arrow_down_dark);
            searchRelativeLayout.setBackgroundResource(R.drawable.fragment_index_title_search_background);
            weatherImageView.setBackgroundResource(R.mipmap.icon_dy_black);
            if (weather.contains("多云")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_dy_black);
            } else if (weather.contains("雨")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_ly_black);
            } else if (weather.contains("雪")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_w_black);
            } else if (weather.contains("晴")) {
                weatherImageView.setBackgroundResource(R.mipmap.icon_qing_blac);
            }
        }

    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

//        EMMessage message = list.get(list.size() - 1);
//        if (EasyUtils.isAppRunningForeground(getContext())) {
//            EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
//        } else {
//            EaseUI.getInstance().getNotifier().notify(message);
//        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                setLocationWeather("");
                break;
        }
    }
}
