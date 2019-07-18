package com.szy.yishopcustomer.Fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonStringModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.FilterActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.ShopGoodsListActivity;
import com.szy.yishopcustomer.Adapter.FullCutGoodsListAdapter;
import com.szy.yishopcustomer.Adapter.GoodsListAdapter;
import com.szy.yishopcustomer.Adapter.ShopCategoryListAdapter;
import com.szy.yishopcustomer.Adapter.ShopGoodsListAdapter;
import com.szy.yishopcustomer.Adapter.SortAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.szy.yishopcustomer.Interface.CoordinatorLayoutObservable;
import com.szy.yishopcustomer.Interface.ListStyleObserver;
import com.szy.yishopcustomer.Interface.ScrollObservable;
import com.szy.yishopcustomer.Interface.ScrollObserver;
import com.szy.yishopcustomer.Interface.SimpleAnimatorListener;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.szy.yishopcustomer.Other.GoodsEventModel;
import com.szy.yishopcustomer.Other.GoodsNumberEvent;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddToCartModel.ResponseAddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.AttributeListModel;
import com.szy.yishopcustomer.ResponseModel.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.BrandModel;
import com.szy.yishopcustomer.ResponseModel.CategoryModel;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.OtherModel;
import com.szy.yishopcustomer.ResponseModel.PageModel;
import com.szy.yishopcustomer.ResponseModel.ShopGoodsList.DataModel;
import com.szy.yishopcustomer.ResponseModel.ShopGoodsList.ResponseShopGoodsListModel;
import com.szy.yishopcustomer.ResponseModel.SortModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.FixBugGridLayoutManager;
import com.szy.yishopcustomer.ViewHolder.GoodsListViewHolder;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.szy.yishopcustomer.ViewModel.Filter.FilterChildModel;
import com.szy.yishopcustomer.ViewModel.Filter.FilterGroupModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2017/1/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopGoodsListFragment extends YSCBaseFragment implements ScrollObservable,
        ListStyleObserver {

    public static final int LEFT_MODEL = 1;
    public static final int RIGHT_MODEL = 2;
    public static final int BOTTOM_MODEL = 4;
    public static final int TOP_MODEL = 8;

    private static final float GO_TO_TOP_BUTTON_MIN_ALPHA = 0;
    private static final float GO_TO_TOP_BUTTON_START_APPEAR_POSITION = 100;
    private static final float GO_TO_TOP_BUTTON_FULL_APPEAR_OFFSET = 50;

    private static final int REQUEST_WHAT_GOODS_LIST = 0;
    private static final int REQUEST_WHAT_ADD_TO_CART = 1;
    private static final int REQUEST_WHAT_REMOVE_CART = 2;
    private static final int REQUEST_WHAT_LOAD_MORE = 3;
    private static final int REQUEST_CODE_CART = 0;
    private static final int REQUEST_CODE_FILTER = 2;

    private static final String SORT_DESC = "DESC";
    private static final String SORT_ASC = "ASC";
    private String defaultApi = Api.API_SHOP_GOODS_LIST;

    @BindView(R.id.fragment_shop_category_categoryRecyclerView)
    public RecyclerView mCategoryListRecyclerView;

    @BindView(R.id.fragment_shop_category_goodsListRecyclerView)
    public CommonRecyclerView mGoodsListRecyclerView;

    public int mListStyle = Macro.STYLE_GRID;
    protected ResponseShopGoodsListModel mModel;

    @BindView(R.id.fragment_goods_list_topWrapperRelativeLayout)
    View mTopWrapper;
    //    @BindView(R.id.fragment_shop_goods_list_pushView)
//    View mPushView;
    @BindView(R.id.fragment_goods_list_shadowView)
    View mShadowView;
    @BindView(R.id.fragment_goods_list_sortRecyclerView)
    RecyclerView mSortRecyclerView;
    @BindView(R.id.fragment_shop_goods_list_offlineView)
    View mOfflineView;
    @BindView(R.id.go_up_button)
    ImageView mGoToTopImageButton;
    @BindView(R.id.fragment_goods_list_compositeTextView)
    TextView mCompositeTextView;
    @BindView(R.id.fragment_goods_list_filterTextView)
    TextView mFilterTextView;
    @BindView(R.id.fragment_goods_list_filterImageView)
    ImageView mFilterImageView;

    @BindView(R.id.fragment_goods_list_sortWrapperRelativeLayout)
    LinearLayout mSortWrapperRelativeLayout;
    @BindView(R.id.fragment_goods_list_salesRelativeLayout)
    LinearLayout mSalesRelativeLayout;
    @BindView(R.id.fragment_goods_list_salesTextView)
    TextView mSalesTextView;
    @BindView(R.id.fragment_goods_list_releaseRelativeLayout)
    View mReleaseRelativeLayout;
    @BindView(R.id.fragment_goods_list_releaseTextView)
    TextView mReleaseTextView;
    @BindView(R.id.fragment_goods_list_sortImageView)
    ImageView mSortImageView;
    @BindView(R.id.fragment_goods_list_composite_sortImageView)
    ImageView mCompositeSortImageView;
    @BindView(R.id.fragment_goods_list_sortTextView)
    TextView mSortTextView;

    @BindView(R.id.fragment_goods_list_compositeWrapperRelativeLayout)
    LinearLayout mCompositeWrapperRelativeLayout;
    @BindView(R.id.fragment_goods_list_filterWrapperRelativeLayout)
    LinearLayout mFilterWrapperRelativeLayout;

    //商品分页
    @BindView(R.id.textView_page_info)
    TextView textView_page_info;

    @BindView(R.id.empty_view_button)
    Button empty_view_button;

    boolean flag = false;//点击综合是否弹出布局
    int mPreviousSelectedPosition = 0;
    int mPreviousSelectedParentPosition = 0;
    String mApi;
    Map<String, String> mParameters;
    int mGridItemOffset;
    List<ScrollObserver> mScrollObservers;
    @Nullable
    ShopCategoryListAdapter mCategoryListAdapter;
    @Nullable
    GoodsListAdapter mGoodsListAdapter;
    RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mListStyle == Macro.STYLE_GRID) {
                outRect.top = mGridItemOffset;
                if (itemPosition % 2 == 0) {
                    outRect.left = mGridItemOffset;
                    outRect.right = mGridItemOffset / 2;
                } else {
                    outRect.left = mGridItemOffset / 2;
                    outRect.right = mGridItemOffset;
                }
            } else {
                outRect.top = 0;
                outRect.left = 0;
                outRect.bottom = 0;
                outRect.right = 0;
            }
        }

    };
    GridLayoutManager.SpanSizeLookup mGoodsListSpanSizeLookup = new GridLayoutManager
            .SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            if (mGoodsListAdapter == null) {
                return 0;
            }
            switch (mGoodsListAdapter.getItemViewType(position)) {
                case GoodsListAdapter.VIEW_TYPE_GOODS_GRID:
                    return 1;
                case GoodsListAdapter.VIEW_TYPE_EMPTY:
                case GoodsListAdapter.VIEW_TYPE_GOODS_LIST:
                case GoodsListAdapter.VIEW_TYPE_LOADING:
                case GoodsListAdapter.VIEW_TYPE_REQUEST_FAILED:
                    return 2;
                default:
                    return 0;
            }
        }
    };
    private String mOrder = SORT_ASC;

    private GoodsListViewHolder mAnimationStartView;

    private int mVisibleModels = LEFT_MODEL | RIGHT_MODEL | BOTTOM_MODEL;
    private GoodsModel mSelectedGoodsModel;
    private int mSelectedGoodsPosition;
    private int mSelectedGoodsNumber;
    private FixBugGridLayoutManager mGoodsRecyclerViewLayoutManager;
    @Nullable
    private SortAdapter mSortAdapter;
    private RecyclerView.OnScrollListener mGoodsRecyclerViewScrollListener = new RecyclerView
            .OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if (mGoodsListAdapter != null && newState == 0) {

                loadMoreIfNeeded(mGoodsListAdapter.bindPosition);

            }

            switch (newState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    textView_page_info.setVisibility(View.VISIBLE);
                    updatePageInfo(mGoodsRecyclerViewLayoutManager.findLastVisibleItemPosition());
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    textView_page_info.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            updateGoToTopButton(recyclerView.computeVerticalScrollOffset());

            updatePageInfo(mGoodsRecyclerViewLayoutManager.findLastVisibleItemPosition());
        }
    };
    private String mKeyword;
    private boolean mDisabledCategoryCartNumber = true;
    private String cateGoryId;
    private String brandId;
    private String shopId;
    //用来区分/full-cut-list-数据界面
    private String act_id;
    //-----
    private boolean firstChangeStyle = true;
    public static ArrayList<FilterGroupModel> list;

    private SortModel mSortModel;

    private ArrayList<SortModel> data;
    private String skuId;
    private String goodsId;

    public boolean isSearchResult = false;

    /**
     * 针对API_SEARCH_RESULT 接口 请求参数:data_page 分页标识 data_sort:筛选标识（默认0）
     **/
    public int data_page = 1;
    public String data_sort = "0";//0默认排序（综合），1销量，2新品，3评论数，4价格，5人气，6折扣率
    private String data_order = SORT_DESC;

    public ShopGoodsListFragment() {
        mScrollObservers = new ArrayList<>();
    }

    @Override
    public void addScrollObserver(ScrollObserver observer) {
        if (mScrollObservers == null) {
            mScrollObservers = new ArrayList<>();
        }
        if (mScrollObservers.contains(observer)) {
            return;
        }
        mScrollObservers.add(observer);
    }

    @Override
    public void hideOfflineView() {
        if (mOfflineView != null) {
            mOfflineView.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mModel == null) {
                refresh();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_ITEM_REQUEST_FAILED:
                loadMoreOnClickFailedItem();
                break;
            case VIEW_TYPE_CATEGORY:
                selectCategory(position);
                break;
            case VIEW_TYPE_PLUS:
            case VIEW_TYPE_ADD:
                mAnimationStartView = (GoodsListViewHolder) view.getTag(R.layout
                        .fragment_goods_list_item_list);
                increaseGoodsNumber(position);
                break;
            case VIEW_TYPE_MINUS:
                decreaseGoodsNumber(position);
                break;
            case VIEW_TYPE_SHOP:
                openShopActivity(position);
                break;
            case VIEW_TYPE_TOP:
                goToTop();
                break;
            case VIEW_TYPE_GOODS:
                openGoodsActivity(position);
                break;
            case VIEW_TYPE_SORT:
                //价格
                data_page = 1;

                if (mOrder.equals(SORT_DESC)) {
                    mOrder = SORT_ASC;
                } else {
                    mOrder = SORT_DESC;
                }
                hideSortRecyclerView();
                changeSort("4", mOrder);
                break;
            case VIEW_TYPE_RELEASE:
                hideSortRecyclerView();
                changeSort("2", SORT_DESC);
                break;
            case VIEW_TYPE_SHADOW:
                hideSortRecyclerView();
                break;
            case VIEW_TYPE_FILTER:
                hideSortRecyclerView();
                openFilterActivity();
                break;
            case VIEW_TYPE_COMPOSITE:
                //综合
                data_page = 1;

                if (flag) {
                    switchSortRecyclerView();
                } else {
                    changeSort("0", SORT_DESC);
                }
                break;
            case VIEW_TYPE_SALES:
                //销量
                data_page = 1;

                hideSortRecyclerView();
                changeSort("1", SORT_DESC);
                break;
            case VIEW_TYPE_SORT_ITEM:
                refreshCompositeSort(position);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    private void refreshCompositeSort(int position) {
        flag = true;
        //综合排序只有降序
        if (data.get(position).value.equals("0")) {
            changeSort("0", SORT_DESC);
        } else {
            if ("1".equals(data.get(position).selected)) {
                if (data.get(position).order.equals(SORT_ASC)) {
                    changeSort(data.get(position).value, SORT_DESC);
                } else if (data.get(position).order.equals(SORT_DESC)) {
                    changeSort(data.get(position).value, SORT_ASC);
                }
            } else {
                changeSort(data.get(position).value, data.get(position).order);
            }

        }
        switchSortRecyclerView();
    }

    private void changeSort(String value, String order) {
        data_sort = value;
        data_order = order;

        changeSortTextViewIsUnSelect();
        //value = changeSortTextViewIsSelect(value, order);
        mParameters.put(Key.KEY_REQUEST_SORT.getValue(), value);
        mParameters.put(Key.KEY_REQUEST_SORT_ORDER.getValue(), order);
        resetPage();
        getGoodsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (!TextUtils.isEmpty(act_id)) {
            mFilterWrapperRelativeLayout.setVisibility(View.GONE);
            if (getActivity() instanceof ShopGoodsListActivity) {
                ((ShopGoodsListActivity) getActivity()).mKeywordEditText.setHint("请输入商品名称");
            }

            mGoodsListAdapter = new FullCutGoodsListAdapter(getContext());
            mGoodsListAdapter.onClickListener = this;
        }


        BottomMenuController.init(getContext(), view);

        Utils.setViewTypeForTag(mGoToTopImageButton, ViewType.VIEW_TYPE_TOP);
        mGoToTopImageButton.setOnClickListener(this);

        initNecessaryModels();
        mParameters = Utils.removeInternalParameter(App.packageName, mParameters);
        init();

        if (!needLogin() || App.getInstance().isLogin()) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            }, 10);
        }

        empty_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        if (mScrollObservers != null) {
            for (int i = mScrollObservers.size() - 1; i >= 0; i--) {
                mScrollObservers.remove(i);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_GOODS_NUMBER:
                updateGoodsNumber((GoodsNumberEvent) event);
                break;
            case EVENT_SHOP_GOODS_LIST_REFRESH:
                resetPage();
                getGoodsList();
                break;
        }
    }

    @Override
    public void showOfflineView() {
        if (mOfflineView != null) {
            mOfflineView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestFailed(int what, String response) {
        switch (what) {
            case REQUEST_WHAT_GOODS_LIST:
                getGoodsListFailed(response);
                break;
            case REQUEST_WHAT_LOAD_MORE:
                loadMoreFailed();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (what) {
            case REQUEST_WHAT_GOODS_LIST:
                getGoodsListSucceed(response);
                break;
            case REQUEST_WHAT_ADD_TO_CART:
                addToCartSucceed(response);
                break;
            case REQUEST_WHAT_REMOVE_CART:
                removeGoodsFromCartSucceed(response);
                break;
            case REQUEST_WHAT_LOAD_MORE:
                loadMoreSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FILTER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    List<FilterGroupModel> groupModelList = data.getParcelableArrayListExtra(Key
                            .KEY_FILTER.getValue());
                    openFilterActivityResult(groupModelList);
                }
                break;
            case REQUEST_CODE_CART:
                if (resultCode == Activity.RESULT_OK) {
                    openAttributeActivitySucceed(data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_goods_list;
        Bundle arguments = getArguments();
        list = new ArrayList<>();
        mGridItemOffset = Utils.dpToPx(getContext(), 0);

        mParameters = Utils.removeInternalParameter(App.packageName, arguments);
        if (arguments != null) {
            mApi = arguments.getString(Key.KEY_API.getValue());
            mVisibleModels = arguments.getInt(Key.KEY_VISIBLE_MODEL.getValue(), mVisibleModels);
            cateGoryId = arguments.getString(Key.KEY_REQUEST_CATEGORY_ID.getValue());
            brandId = arguments.getString(Key.KEY_GOODS_BRAND_ID.getValue());
            shopId = arguments.getString(Key.KEY_REQUEST_SHOP_ID.getValue());
            mKeyword = arguments.getString(Key.KEY_REQUEST_KEYWORD.getValue());
            act_id = arguments.getString(Key.KEY_ACT_ID.getValue());
        }
        if (brandId != null) {
            mParameters.put(Key.KEY_REQUEST_BRAND_ID.getValue(), brandId);
        }

        if (shopId != null) {
            mParameters.put(Key.KEY_REQUEST_SHOP_ID.getValue(), shopId);
        }

        if (Utils.isNull(mApi)) {
            mApi = defaultApi;
        }
        mModel = new ResponseShopGoodsListModel();
        mModel.data = new DataModel();
        mModel.data.page = new PageModel();
        initNecessaryObjects();
    }

    public void search() {

        data_page = 1;

        if (mKeyword != null) {
            mParameters.put(Key.KEY_REQUEST_KEYWORD.getValue(), mKeyword);
        } else {
            mParameters.put(Key.KEY_REQUEST_KEYWORD.getValue(), "");
        }
        resetPage();

        getGoodsList();
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public void setStyle(int style) {
        mListStyle = style;
        if (mGoodsListAdapter != null) {
            mGoodsListAdapter.style = style;
            mGoodsListAdapter.notifyDataSetChanged();
        }
    }

    protected GoodsListAdapter createGoodsListAdapter() {
        return new ShopGoodsListAdapter(getContext());
    }

    protected void getCategoryCallback(List<CategoryModel> category, String type) {

        List<CategoryModel> previousCategoryList = null;
        if (mModel != null && mModel.data != null) {
            previousCategoryList = mModel.data.category;
        }
        if (mCategoryListAdapter != null) {
            if (previousCategoryList != null) {
                mModel.data.category = previousCategoryList;
                setUpCategoryListAdapterData(type);
            } else {
                mModel.data.category = category;
                if (mModel.data.category.size() > 0) {
                    mModel.data.category.get(0).selected = true;
                    setUpCategoryListAdapterData(type);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isShopActivity()) {
//            refresh();
            ((ShopActivity) getActivity()).updateCart();
        }
    }

    protected String getCategoryParameterName() {
        return Key.KEY_REQUEST_CATEGORY_ID.getValue();
    }

    protected void getGoodsList() {
        getGoodsList(mApi, mParameters, REQUEST_WHAT_GOODS_LIST, true);
    }

    protected String getPageParameterName() {
        return Key.KEY_REQUEST_PAGE.getValue();
    }

    protected void init() {
    }

    protected boolean isCategoryRequestedSeparately() {
        return false;
    }

    protected boolean needLogin() {
        return false;
    }

    protected void refresh() {
        getGoodsList();
    }

    private void addToCart(String goodsId, int goodsNumber, String skuId) {
        mSelectedGoodsNumber = goodsNumber;
        CommonRequest request = new CommonRequest(Api.API_ADD_TO_CART, REQUEST_WHAT_ADD_TO_CART,
                RequestMethod.POST);
        request.setAjax(true);
        request.add("goods_id", goodsId);
        request.add("number", goodsNumber);
        request.add("sku_id", skuId);
        if (isShopActivity()) {
            request.add("shop_id", ((ShopActivity) getActivity()).mShopId);
        }

        addRequest(request);
    }

    private boolean isShopActivity() {
        if (getActivity() instanceof ShopActivity) {
            return true;
        }

        return false;
    }

    private void addToCart(String goodsId, int goodsNumber) {
        addToCart(goodsId, goodsNumber, "");
    }

    /**
     * 商品添加到购物车成功
     *
     * @param response
     */
    private void addToCartSucceed(String response) {
        HttpResultManager.resolve(response, ResponseAddToCartModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseAddToCartModel>() {
            @Override
            public void onSuccess(ResponseAddToCartModel back) {
                if (back.data.sku_open.contentEquals("1")) {
                    openAttributeActivity(back);
                } else {
                    if (isShopActivity()) {
                        ((ShopActivity) getActivity()).updateCart();
                    } else {
                        App.addCartNumber(mSelectedGoodsNumber, ShopGoodsListFragment.this);
                    }

                    makeCartAnimation();
                    mSelectedGoodsModel.cart_num += mSelectedGoodsNumber;
                    if (mGoodsListAdapter != null) {
                        mGoodsListAdapter.notifyItemChanged(mSelectedGoodsPosition);
                    }
                    updateCategoryListNumber(mSelectedGoodsNumber);
                }
            }
        }, true);
    }

    private void changeSort(int position) {
        if (mSortAdapter == null) {
            return;
        }
        SortModel mSelectedModel = mModel.data.filter.sorts.get(position);
        if (mSelectedModel.selected.contentEquals("1")) {
            mSelectedModel.order = mSelectedModel.order.contentEquals(Macro.ORDER_BY_ASC) ? Macro
                    .ORDER_BY_DESC : Macro.ORDER_BY_ASC;
        }
        for (SortModel sortModel : mModel.data.filter.sorts) {
            if (sortModel.sort.contentEquals(mSelectedModel.sort)) {
                sortModel.selected = "1";
            } else {
                sortModel.selected = "0";
            }
        }
        setUpSortAdapterData();
        hideSortRecyclerView();
        resetPage();
        mParameters.put(Key.KEY_REQUEST_SORT.getValue(), mSelectedModel.value);
        mParameters.put(Key.KEY_REQUEST_SORT_ORDER.getValue(), mSelectedModel.order);
        getGoodsList();
    }

    private void decreaseGoodsNumber(int position) {
        if (mGoodsListAdapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) mGoodsListAdapter.data.get(position);

        goodsId = goodsModel.goods_id;
        skuId = goodsModel.sku_id;

        mSelectedGoodsModel = goodsModel;
        mSelectedGoodsPosition = position;
        removeGoodsFromCart(goodsModel.goods_id, 1);
    }

    private void enableCoordinatorLayout(boolean enabled) {
        if (getActivity() instanceof CoordinatorLayoutObservable) {
            CoordinatorLayoutObservable observable = (CoordinatorLayoutObservable) getActivity();
            observable.enableCoordinatorLayout(enabled);
        }
    }

    private ArrayList<FilterGroupModel> getFilterData() {
        list = new ArrayList<>();
        FilterGroupModel groupModel;

        FilterChildModel childModel;

        if (mModel != null && mModel.data != null && mModel.data.filter != null && mModel.data.filter.others != null) {
            groupModel = new FilterGroupModel();
            groupModel.title = "通用筛选";
            groupModel.type = FilterGroupModel.FILTER_TYPE_NORMAL;

            if (mModel != null && mModel.data != null && mModel.data.filter.others != null) {
                for (OtherModel otherModel : mModel.data.filter.others) {
                    childModel = new FilterChildModel();
                    childModel.title = otherModel.name;
                    childModel.value = otherModel.param;
                    childModel.selected = otherModel.selected;
                    childModel.type = FilterChildModel.FILTER_TYPE_SELLER;

                    groupModel.children.add(childModel);
                }

            }

            list.add(groupModel);

//            groupModel = new FilterGroupModel();
//            groupModel.type = FilterGroupModel.FILTER_TYPE_LINE;
//            list.add(groupModel);
        }
        if (mModel != null && mModel.data != null && mModel.data.filter != null && !mModel.data.filter.price.name.contentEquals
                ("")) {
            groupModel = new FilterGroupModel();
            groupModel.title = "价格区间";
            /*价格输入形式*/
            groupModel.type = FilterGroupModel.FILTER_TYPE_PRICE;
            /*价格选择形式seekbar*/
            //groupModel.type = FilterGroupModel.FILTER_TYPE_PRICE_SEEKBAR;

            childModel = new FilterChildModel();
            /*价格输入形式*/
            childModel.type = FilterChildModel.FILTER_TYPE_PRICE;
            /*价格选择形式seekbar*/
            //childModel.type = FilterChildModel.FILTER_TYPE_PRICE_SEEKBAR;

            /*价格输入形式*/
            if (mModel != null && mModel.data != null && mModel.data.filter.price.start != null) {
                childModel.minimumValue = mModel.data.filter.price.start;
            }
            if (mModel != null && mModel.data != null && mModel.data.filter.price.end != null) {
                childModel.maximumValue = mModel.data.filter.price.end;
            }

            /*价格选择形式seekbar*/
           /* if (mModel != null && mModel.data != null && mModel.data.filter.price.price_min != null) {
                childModel.minimumValue = mModel.data.filter.price.price_min;
                if(!Utils.isNull(mModel.data.filter.price.price_min)){
                    childModel.rangeStart = mModel.data.filter.price.price_min;
                }else {
                    childModel.rangeStart = "0";
                }

            }
            if (mModel != null && mModel.data != null && mModel.data.filter.price.price_max != null) {
                childModel.maximumValue = mModel.data.filter.price.price_max;
                if(!Utils.isNull(mModel.data.filter.price.price_max)){
                    childModel.rangeEnd = mModel.data.filter.price.price_max;
                }else {
                    childModel.rangeEnd = "0";
                }
            }*/
            groupModel.children.add(childModel);

            list.add(groupModel);
        }
        if (Utils.isNull(cateGoryId)) {
            return list;
        }
        if (mModel != null && mModel.data != null && mModel.data.filter != null && mModel.data.filter.brand.items != null &&
                mModel.data.filter.brand.items.size() > 0) {
            groupModel = new FilterGroupModel();
            groupModel.type = FilterGroupModel.FILTER_TYPE_LINE;
            list.add(groupModel);

            groupModel = new FilterGroupModel();
            groupModel.type = FilterGroupModel.FILTER_TYPE_DIVIDER;
            list.add(groupModel);

            groupModel = new FilterGroupModel();
            groupModel.expandEnabled = true;
            groupModel.title = mModel.data.filter.brand.name;
            groupModel.type = FilterGroupModel.FILTER_TYPE_BRAND;

            for (BrandModel brandModel : mModel.data.filter.brand.items) {
                childModel = new FilterChildModel();
                childModel.title = brandModel.name;
                childModel.type = FilterChildModel.FILTER_TYPE_BRAND;
                childModel.value = brandModel.value;
                if (brandModel.selected.equals("1")) {
                    childModel.selected = true;
                }
                groupModel.children.add(childModel);
            }
            list.add(groupModel);
        }

        if (mModel != null && mModel.data != null && mModel.data.filter != null && mModel.data.filter.filter_attr.items != null
                && mModel.data.filter.filter_attr.items.size() > 0) {
            for (AttributeListModel attributeListModel : mModel.data.filter.filter_attr.items) {
//                groupModel = new FilterGroupModel();
//                groupModel.type = FilterGroupModel.FILTER_TYPE_LINE;
//                list.add(groupModel);

                groupModel = new FilterGroupModel();
                groupModel.expandEnabled = true;
                groupModel.title = attributeListModel.name;
                groupModel.type = FilterGroupModel.FILTER_TYPE_ATTRIBUTE;
                for (AttributeModel attributeModel : attributeListModel.items) {
                    if (!Utils.isNull(attributeModel.value)) {
                        childModel = new FilterChildModel();
                        childModel.type = FilterChildModel.FILTER_TYPE_ATTRIBUTE;
                        childModel.value = attributeModel.value;
                        childModel.title = attributeModel.name;
                        childModel.selected = attributeModel.selected;
                        groupModel.children.add(childModel);
                    }
                }
                list.add(groupModel);
            }
        }

        return list;
    }

    private void getGoodsList(String api, Map<String, String> parameters, int what, boolean alarmed) {


        CommonRequest request = new CommonRequest(api, what);
        if (api.equals(Api.API_SEARCH_RESULT)) {

            isSearchResult = true;

            RequestAddHead.addHead(request, getActivity(), api, "GET");
            request.add("msg", mKeyword);
            request.add("order", data_order);
            request.add("page", data_page);
            request.add("size", 24);
            request.add("sort", data_sort);
            request.add("user_id", App.getInstance().userId);
        } else {
            request.add(parameters);
        }

        request.alarm = alarmed;
        addRequest(request);
    }

    private void getGoodsListFailed(String response) {
        Toast.makeText(getContext(), Utils.isNull(response) ? getString(R.string.requestFailed) : response, Toast.LENGTH_SHORT).show();
    }

    private void getGoodsListSucceed(String response) {
        HttpResultManager.resolve(response, ResponseShopGoodsListModel.class, new
                HttpResultManager.HttpResultCallBack<ResponseShopGoodsListModel>() {
                    @Override
                    public void onSuccess(ResponseShopGoodsListModel model) {
                        if (mModel == null) {
                            mModel = model;
                        }
                        if (!isCategoryRequestedSeparately()) {
                            getCategoryCallback(model.data.category, "1");
                        }
                        mModel.data.list = model.data.list;
                        mModel.data.filter = model.data.filter;
                        mModel.data.page = model.data.page;
                        mModel.data.show_sale_number = model.data.show_sale_number;

                        if ((mVisibleModels & TOP_MODEL) > 0) {
                            //综合下的弹出排序
                            setUpSortAdapterData();
                            //默认排序的选中
                            changeSortTextViewIsSelect(model.data.params.sort, model.data.params.order);

                            //默认选中综合
                            if (model.data.params.sort.equals("0")) {
                                flag = true;
                            }
                        }

                        if (mGoodsListAdapter != null) {

                            if (firstChangeStyle) {
                                firstChangeStyle = !firstChangeStyle;

                                //设置默认风格
                                if (model.data.display.equals("grid")) {
                                    mGoodsListAdapter.style = Macro.STYLE_GRID;
                                    mListStyle = Macro.STYLE_GRID;
                                } else {
                                    mGoodsListAdapter.style = Macro.STYLE_LIST;
                                    mListStyle = Macro.STYLE_LIST;
                                }

                                updateSwitchButtonStatus(mListStyle);

                            }
                            setUpGoodsListAdapterData();
                            updateGoodsRecyclerViewLastItem();
                        }

                        updateGoToTopButton(0);
                        loadMoreIfNeeded(mGoodsListAdapter.bindPosition);
                        init();
                    }
                });
    }

    public void updateSwitchButtonStatus(int style) {

        if (getActivity() instanceof ShopGoodsListActivity) {
            ((ShopGoodsListActivity) getActivity()).updateSwitchButtonStatus(mListStyle);
        }

    }

    private void goToTop() {
        mGoodsListRecyclerView.smoothScrollToPosition(0);
        for (ScrollObserver observer : mScrollObservers) {
            observer.scrollToTop();
        }
    }

    private void hideShadowView() {
        mShadowView.clearAnimation();
        mShadowView.animate().alpha(0).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mShadowView.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    private void hideSortRecyclerView() {
        //updateSortView(false);
        hideShadowView();
        enableCoordinatorLayout(true);
        int height = mSortRecyclerView.getMeasuredHeight();
        mSortRecyclerView.animate().translationYBy(-height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mSortRecyclerView.setVisibility(View.INVISIBLE);
                mSortRecyclerView.setTranslationY(0);
            }
        }).start();
    }

    /**
     * 增加商品的数量
     *
     * @param position
     */
    private void increaseGoodsNumber(int position) {
        if (mGoodsListAdapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) mGoodsListAdapter.data.get(position);

        skuId = goodsModel.sku_id;
        goodsId = goodsModel.goods_id;

        if (goodsModel.buy_enable.code.equals("0")) {
            Toast.makeText(getActivity(), goodsModel.buy_enable.button_content, Toast
                    .LENGTH_SHORT).show();
        } else {

            if (goodsModel.goods_number > 0) {
                mSelectedGoodsModel = goodsModel;
                mSelectedGoodsPosition = position;
                addToCart(goodsModel.goods_id, 1);
            } else {
                Toast.makeText(getActivity(), "商品库存不足", Toast
                        .LENGTH_SHORT).show();
            }


        }
    }

    private void initNecessaryModels() {
        if ((mVisibleModels & LEFT_MODEL) > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mCategoryListRecyclerView.setLayoutManager(linearLayoutManager);
            mCategoryListRecyclerView.setAdapter(mCategoryListAdapter);
            mCategoryListRecyclerView.setVisibility(View.VISIBLE);
            mCategoryListRecyclerView.getItemAnimator().setChangeDuration(0);
            mCategoryListRecyclerView.getItemAnimator().setAddDuration(0);
            mCategoryListRecyclerView.getItemAnimator().setRemoveDuration(0);
        } else {
            mCategoryListRecyclerView.setVisibility(View.GONE);
        }

        if ((mVisibleModels & RIGHT_MODEL) > 0) {
            mGoodsRecyclerViewLayoutManager = new FixBugGridLayoutManager(getContext(), 2);
            mGoodsRecyclerViewLayoutManager.setSpanSizeLookup(mGoodsListSpanSizeLookup);
            mGoodsListRecyclerView.setLayoutManager(mGoodsRecyclerViewLayoutManager);
            mGoodsListRecyclerView.setAdapter(mGoodsListAdapter);
//            mGoodsListRecyclerView.setOnScrollListener(mGoodsRecyclerViewScrollListener);
            mGoodsListRecyclerView.addOnScrollListener(mGoodsRecyclerViewScrollListener);
            mGoodsListRecyclerView.setVisibility(View.VISIBLE);
            mGoodsListRecyclerView.getItemAnimator().setChangeDuration(0);
            mGoodsListRecyclerView.addItemDecoration(mItemDecoration);
            mGoodsListRecyclerView.setEmptyViewClickListener(this);
        } else {
            mGoodsListRecyclerView.setVisibility(View.GONE);
        }

        if ((mVisibleModels & TOP_MODEL) > 0) {
            mTopWrapper.setVisibility(View.VISIBLE);
            //mCompositeTextView.setSelected(true);
            Utils.setViewTypeForTag(mShadowView, ViewType.VIEW_TYPE_SHADOW);
            mShadowView.setOnClickListener(this);

            Utils.setViewTypeForTag(mSortWrapperRelativeLayout, ViewType.VIEW_TYPE_SORT);
            mSortWrapperRelativeLayout.setOnClickListener(this);

            Utils.setViewTypeForTag(mFilterWrapperRelativeLayout, ViewType.VIEW_TYPE_FILTER);
            mFilterWrapperRelativeLayout.setOnClickListener(this);

            Utils.setViewTypeForTag(mSalesRelativeLayout, ViewType.VIEW_TYPE_SALES);
            mSalesRelativeLayout.setOnClickListener(this);

            Utils.setViewTypeForTag(mReleaseRelativeLayout, ViewType.VIEW_TYPE_RELEASE);
            mReleaseRelativeLayout.setOnClickListener(this);

            Utils.setViewTypeForTag(mCompositeWrapperRelativeLayout, ViewType.VIEW_TYPE_COMPOSITE);
            mCompositeWrapperRelativeLayout.setOnClickListener(this);


            mSortRecyclerView.setNestedScrollingEnabled(true);

            mSortRecyclerView.setAdapter(mSortAdapter);
            mSortRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        } else {
            mTopWrapper.setVisibility(View.GONE);
        }

//        if ((mVisibleModels & BOTTOM_MODEL) > 0) {
//            mPushView.setVisibility(View.VISIBLE);
//            mPushView.getLayoutParams().height = Utils.dpToPx(getContext(), 50);
//        } else {
//            mPushView.setVisibility(View.INVISIBLE);
//            mPushView.getLayoutParams().height = Utils.dpToPx(getContext(), 0);
//        }
    }

    private void initNecessaryObjects() {
        if ((mVisibleModels & LEFT_MODEL) > 0) {
            mCategoryListAdapter = new ShopCategoryListAdapter();
            mCategoryListAdapter.onClickListener = this;
        }

        if ((mVisibleModels & RIGHT_MODEL) > 0) {
            mGoodsListAdapter = createGoodsListAdapter();
            mGoodsListAdapter.onClickListener = this;
            mGoodsListAdapter.style = mListStyle;
        }

        if ((mVisibleModels & TOP_MODEL) > 0) {
            mSortAdapter = new SortAdapter();
            mSortAdapter.onClickListener = this;
        }
    }

    private void loadMore() {
        if (mGoodsListAdapter == null) {
            return;
        }
        int pageCount = mModel.data.page.page_count;
        int page = mModel.data.page.cur_page;
        if (page >= pageCount) {
            return;
        }
        if (!mGoodsListAdapter.isLastItemGoodsType()) {
            return;
        }

        mGoodsListAdapter.insertLoadingItemAtTheEnd();
        page++;
        mParameters.put(getPageParameterName(), page + "");

        data_page = page;

        getGoodsList(mApi, mParameters, REQUEST_WHAT_LOAD_MORE, false);
    }

    private void loadMoreFailed() {

        if (mGoodsListAdapter == null) {
            return;
        }
        mGoodsListAdapter.insertRequestFailedItemAtTheEnd();
    }

    private void loadMoreIfNeeded(int currentPosition) {
        if (mModel == null) {
            return;
        }

        if (mModel.data.page.cur_page >= mModel.data.page.page_count) {
            return;
        }

        if (mGoodsListAdapter == null) {
            return;
        }

        if (!mGoodsListAdapter.isLastItemGoodsType()) {
            return;
        }

        if (mModel.data.list.size() - currentPosition <= mModel.data.page.page_size) {
            loadMore();
        }
    }

    private void loadMoreOnClickFailedItem() {
        if (mGoodsListAdapter == null) {
            return;
        }
        mGoodsListAdapter.insertLoadingItemAtTheEnd();
        getGoodsList(mApi, mParameters, REQUEST_WHAT_LOAD_MORE, false);
    }

    private void updatePageInfo(int lastPosition) {
        if (lastPosition > 0) {
            textView_page_info.setVisibility(View.VISIBLE);
            int cur_page = (int) Math.ceil(lastPosition / (mModel.data.page.page_size * 1.0f));
            textView_page_info.setText(cur_page + "/" + mModel.data.page.page_count);
        } else {
            textView_page_info.setText("");
            textView_page_info.setVisibility(View.GONE);
        }
    }

    private void loadMoreSucceed(String response) {
        if (mGoodsListAdapter == null) {
            return;
        }
        HttpResultManager.resolve(response, ResponseShopGoodsListModel.class, new
                HttpResultManager.HttpResultCallBack<ResponseShopGoodsListModel>() {
                    @Override
                    public void onSuccess(ResponseShopGoodsListModel model) {
                        mGoodsListAdapter.removeLastNonGoodsItem();
                        mModel.data.list.addAll(model.data.list);
                        mModel.data.page = model.data.page;
                        int position = mGoodsListAdapter.data.size();
                        mGoodsListAdapter.data.addAll(model.data.list);
                        mGoodsListAdapter.notifyItemRangeInserted(position, model.data.list.size());
                        updateGoodsRecyclerViewLastItem();
                        loadMoreIfNeeded(mGoodsListAdapter.bindPosition);
                    }

                    @Override
                    public void onFailure(String message) {
                        mGoodsListAdapter.insertRequestFailedItemAtTheEnd();
                    }

                }, true);
    }


    /**
     * 商品添加到购物车的动画
     */
    private void makeCartAnimation() {
        if (getActivity() instanceof CartAnimationMaker) {
            CartAnimationMaker animationMaker = (CartAnimationMaker) getActivity();
            int[] location = new int[2];
            mAnimationStartView.plusImageView.getLocationInWindow(location);
            location[1] -= mAnimationStartView.plusImageView.getMeasuredHeight();

            Drawable drawable = mAnimationStartView.goodsImageView.getDrawable();//复制一个新的商品图标
            animationMaker.makeAnimation(drawable, location[0], location[1]);
        }
    }

    private void openAttributeActivity(ResponseAddToCartModel model) {
        Intent intent = new Intent();
        ArrayList<SkuModel> skuList = new ArrayList<>();
        if (model.data.sku_list != null) {
            for (Map.Entry<String, SkuModel> entry : model.data.sku_list.entrySet()) {
                skuList.add(model.data.sku_list.get(entry.getKey()));
            }
        }
        intent.putParcelableArrayListExtra(Key.KEY_SKU_LIST.getValue(), skuList);
        ArrayList<SpecificationModel> specificationList = new ArrayList<>();
        if (model.data.spec_list != null) {
            specificationList.addAll(model.data.spec_list);
        }
        intent.putParcelableArrayListExtra(Key.KEY_SPECIFICATION_LIST.getValue(),
                specificationList);

        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.putExtra(Key.KEY_TYPE.getValue(), Macro.BUTTON_TYPE_ADD_TO_CART_GOODSLIST);
        intent.putExtra(Key.KEY_IS_STOCK.getValue(), model.data.show_goods_stock);

        intent.setClass(getActivity(), AttributeActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CART);
    }

    private void openAttributeActivitySucceed(Intent data) {
        if (data != null) {
            ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());
            if (resultModel.resultType.equals(Macro.RESULT_TYPE_ADD_TO_CART)) {
                addToCart(mSelectedGoodsModel.goods_id, Integer.valueOf(resultModel.goodsNumber),
                        resultModel.skuId);
            }
        }
    }

    private void openFilterActivity() {
        Intent intent = new Intent(getContext(), FilterActivity.class);
        if (Utils.isNull(list)) {
            intent.putParcelableArrayListExtra(Key.KEY_FILTER.getValue(), getFilterData());
        } else {
            intent.putParcelableArrayListExtra(Key.KEY_FILTER.getValue(), list);
        }

        startActivityForResult(intent, REQUEST_CODE_FILTER);
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_SHOW_FILTER_VIEW.getValue()));
    }

    private void openFilterActivityResult(List<FilterGroupModel> groupModelList) {
        List<String> selectedBrands = new ArrayList<>();
        List<String> selectedAttributesList = new ArrayList<>();
        boolean selectedPrice = false;
        boolean selectedNormal = false;
        for (FilterGroupModel groupModel : groupModelList) {
            switch (groupModel.type) {
                case FilterGroupModel.FILTER_TYPE_NORMAL:
                    for (FilterChildModel childModel : groupModel.children) {
                        switch (childModel.type) {
                            case FilterChildModel.FILTER_TYPE_SELLER:
                                mParameters.put(childModel.value, childModel.selected ? "1" : "0");
                                if (childModel.selected) {
                                    selectedNormal = true;
                                }
                                break;
                        }
                    }
                    break;
                case FilterGroupModel.FILTER_TYPE_PRICE:
                    for (FilterChildModel childModel : groupModel.children) {
                        switch (childModel.type) {
                            case FilterChildModel.FILTER_TYPE_PRICE:
                                String priceStart = "0";
                                String priceEnd = "0";
                                if (!Utils.isNull(childModel.minimumValue) && !Utils.isNull
                                        (childModel.maximumValue)) {
                                    double start = Double.parseDouble(childModel.minimumValue);
                                    double end = Double.parseDouble(childModel.maximumValue);

                                    if (start > end) {
                                        priceStart = childModel.maximumValue;
                                        priceEnd = childModel.minimumValue;
                                    } else {
                                        priceStart = childModel.minimumValue;
                                        priceEnd = childModel.maximumValue;
                                    }
                                }
                                mParameters.put("price_min", priceStart);
                                mParameters.put("price_max", priceEnd);
                                if (!priceEnd.equals("0")) {
                                    selectedPrice = true;
                                }
                                break;
                        }
                    }
                    break;
                case FilterGroupModel.FILTER_TYPE_PRICE_SEEKBAR:
                    for (FilterChildModel childModel : groupModel.children) {
                        switch (childModel.type) {
                            case FilterChildModel.FILTER_TYPE_PRICE_SEEKBAR:
                                String priceStart = "0";
                                String priceEnd = "0";
                                if (!Utils.isNull(childModel.minimumValue) && !Utils.isNull
                                        (childModel.maximumValue)) {
                                    double start = Double.parseDouble(childModel.minimumValue);
                                    double end = Double.parseDouble(childModel.maximumValue);

                                    if (start > end) {
                                        priceStart = childModel.maximumValue;
                                        priceEnd = childModel.minimumValue;
                                    } else {
                                        priceStart = childModel.minimumValue;
                                        priceEnd = childModel.maximumValue;
                                    }
                                }
                                mParameters.put("price_min", priceStart);
                                mParameters.put("price_max", priceEnd);
                                if (!priceEnd.equals("0")) {
                                    selectedPrice = true;
                                }
                                break;
                        }
                    }
                    break;
                case FilterGroupModel.FILTER_TYPE_BRAND:
                    for (FilterChildModel childModel : groupModel.children) {
                        switch (childModel.type) {
                            case FilterChildModel.FILTER_TYPE_BRAND:
                                if (childModel.selected) {
                                    selectedBrands.add(childModel.value);
                                }
                                break;
                        }
                    }
                    break;
                case FilterGroupModel.FILTER_TYPE_ATTRIBUTE:
                    List<String> selectedAttributes = new ArrayList<>();
                    for (FilterChildModel childModel : groupModel.children) {
                        switch (childModel.type) {
                            case FilterChildModel.FILTER_TYPE_ATTRIBUTE:
                                if (childModel.selected) {
                                    selectedAttributes.add(childModel.value);
                                }
                                break;
                        }
                    }
                    if (!Utils.isNull(selectedAttributes)) {
                        selectedAttributesList.add(Utils.join(selectedAttributes, "_"));
                    } else {
                        selectedAttributesList.add("0");
                    }
                    break;
            }
        }
        String brandIds;
        boolean selectedBrand = false;
        if (!Utils.isNull(selectedBrands)) {
            brandIds = Utils.join(selectedBrands, "_");
            selectedBrand = true;
        } else {
            brandIds = "0";
        }
        mParameters.put("brand_id", brandIds);
        boolean isSelect = false;
        for (int i = 0; i < selectedAttributesList.size(); i++) {
            if (!selectedAttributesList.get(i).equals("0")) {
                isSelect = true;
            }
        }
        String attributes;
        if (isSelect) {

            if (!Utils.isNull(selectedAttributesList)) {
                attributes = Utils.join(selectedAttributesList, "-");
            } else {
                attributes = "0";
            }
        } else {
            attributes = null;
        }
        if (attributes != null) {
            mParameters.put("filter_attr", attributes);
        } else {
            mParameters.remove("filter_attr");
        }
        if (isSelect || selectedNormal || selectedPrice || selectedBrand) {
            mFilterTextView.setSelected(true);
            mFilterImageView.setImageResource(R.mipmap.ic_triangle_down_selected);
        } else {
            mFilterTextView.setSelected(false);
            mFilterImageView.setImageResource(R.mipmap.ic_triangle_down_normal);
        }
        resetPage();
        getGoodsList();
    }

    private void openGoodsActivity(int position) {
        if (mGoodsListAdapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) mGoodsListAdapter.data.get(position);
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsModel.goods_id);
        startActivity(intent);
    }

    private void openShopActivity(int position) {
        if (mGoodsListAdapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) mGoodsListAdapter.data.get(position);
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), goodsModel.shop_id);
        intent.setClass(getActivity(), ShopActivity.class);
        startActivity(intent);
    }

    private void removeGoodsFromCart(String goodsId, int number) {
        mSelectedGoodsNumber = -number;
        CommonRequest request = new CommonRequest(Api.API_REMOVE_CART, REQUEST_WHAT_REMOVE_CART);
        request.setAjax(true);
        request.add("number", number);
        request.add("goods_id", goodsId);
        if (isShopActivity()) {
            request.add("shop_id", ((ShopActivity) getActivity()).mShopId);
        }

        addRequest(request);
    }

    private void removeGoodsFromCartSucceed(String response) {
        if (mGoodsListAdapter == null) {
            return;
        }

        HttpResultManager.resolve(response, ResponseCommonStringModel.class, new
                HttpResultManager.HttpResultCallBack<ResponseCommonStringModel>() {
                    @Override
                    public void onSuccess(ResponseCommonStringModel back) {
                        if (isShopActivity()) {
                            ((ShopActivity) getActivity()).updateCart();
                        } else {
                            App.addCartNumber(mSelectedGoodsNumber, ShopGoodsListFragment.this);
                        }

                        mSelectedGoodsModel.cart_num += mSelectedGoodsNumber;
                        mGoodsListAdapter.notifyItemChanged(mSelectedGoodsPosition);
                        updateCategoryListNumber(mSelectedGoodsNumber);

                    }
                }, true);
    }

    private void resetPage() {
        mParameters.put(getPageParameterName(), "1");
    }

    private void selectCategory(final int position) {
        if (mCategoryListAdapter == null) {
            return;
        }
        final CategoryModel selectedModel = mCategoryListAdapter.data.get(position);
        if (selectedModel.selected && !selectedModel.isParent) {
            return;
        }
        resetPage();
        mParameters.put(getCategoryParameterName(), selectedModel.cat_id);
        getGoodsList();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //子分类
                if (!selectedModel.isParent) {
                    int beginPosition = 0;
                    for (int i = position; i > 0; i--) {
                        if (mCategoryListAdapter.data.get(i).isParent) {
                            beginPosition = i;
                        }
                    }

                    CategoryModel categoryModel = mCategoryListAdapter.data.get(beginPosition);

                    for (int i = 0; i < categoryModel.chr_list.size(); i++) {
                        categoryModel.chr_list.get(i).selected = false;
                    }
                }

                selectedModel.selected = true;

                int parentPosition = position;
                if ((selectedModel.chr_list == null || selectedModel.chr_list.size() == 0) &&
                        !selectedModel.cat_id.contentEquals("0")) {
                    for (int i = position - 1; i > 0; i--) {
                        CategoryModel previousModel = mCategoryListAdapter.data.get(i);
                        if (previousModel.chr_list != null && previousModel.chr_list.size() > 0) {
                            parentPosition = i;
                            break;
                        }
                    }
                }

                if (parentPosition == mPreviousSelectedParentPosition) {
                    CategoryModel previousSelectedModel = mCategoryListAdapter.data.get
                            (mPreviousSelectedPosition);
                    if (mPreviousSelectedPosition != mPreviousSelectedParentPosition) {
                        previousSelectedModel.selected = false;
                    }
                    mCategoryListAdapter.notifyDataSetChanged();
                    mPreviousSelectedPosition = position;
                } else {
                    CategoryModel previousParentModel = mCategoryListAdapter.data.get
                            (mPreviousSelectedParentPosition);
                    previousParentModel.selected = false;
                    int newParentPosition = parentPosition;

                    if (previousParentModel.chr_list != null && previousParentModel.chr_list.size
                            () > 0) {
                        for (int i = previousParentModel.chr_list.size() - 1; i >= 0; i--) {
                            mCategoryListAdapter.data.remove(i + 1 +
                                    mPreviousSelectedParentPosition);
                        }
                        if (newParentPosition > mPreviousSelectedParentPosition) {
                            newParentPosition -= previousParentModel.chr_list.size();
                        }
                    }

                    CategoryModel parentModel = mCategoryListAdapter.data.get(newParentPosition);

                    if (parentModel.chr_list != null && parentModel.chr_list.size() > 0) {
                        mCategoryListAdapter.data.addAll(newParentPosition + 1, parentModel
                                .chr_list);
                    }
                    mCategoryListAdapter.notifyDataSetChanged();
                    mPreviousSelectedParentPosition = newParentPosition;
                    mPreviousSelectedPosition = mPreviousSelectedParentPosition;
                }
            }
        });
    }

    private void setUpCategoryListAdapterData(String type) {
        if (mCategoryListAdapter == null) {
            return;
        }
        if (mModel == null || mModel.data == null || mModel.data.category == null || mModel.data
                .category.size() == 0) {
            return;
        }

        if (mCategoryListAdapter.data.size() == 0) {
            for (CategoryModel model : mModel.data.category) {
                model.isParent = true;
                if (type.equals("1")) {
                    model.type = 1;
                }
                mCategoryListAdapter.data.add(model);
            }
            mCategoryListAdapter.notifyDataSetChanged();
            return;
        }

        mCategoryListAdapter.data.clear();
        for (CategoryModel model : mModel.data.category) {
            mCategoryListAdapter.data.add(model);
            if (model.selected && model.chr_list != null && model.chr_list.size() > 0) {
                mCategoryListAdapter.data.addAll(model.chr_list);
            }
        }

        mCategoryListAdapter.notifyDataSetChanged();
    }

    private void setUpGoodsListAdapterData() {
        if (mGoodsListAdapter == null) {
            return;
        }
        mGoodsListAdapter.data.clear();
        for (GoodsModel goodsModel : mModel.data.list) {
            goodsModel.show_sale_number = mModel.data.show_sale_number;
            mGoodsListAdapter.data.add(goodsModel);
        }

        //mGoodsListAdapter.data.addAll(mModel.data.list);
        mGoodsListAdapter.notifyDataSetChanged();
        if (mGoodsListAdapter.data.size() == 0) {
            mGoodsListRecyclerView.showEmptyView();
        }
    }

    //综合下的弹出排序
    private void setUpSortAdapterData() {
        if (mSortAdapter == null) {
            return;
        }
        data = new ArrayList<>();
        for (SortModel sortModel : mModel.data.filter.sorts) {
            if (sortModel.sort.contentEquals(Macro.SORT_BY_PRICE) || sortModel.sort.contentEquals
                    (Macro.SORT_BY_SALE)) {
                continue;
            }
            data.add(sortModel);
        }

        if (isSearchResult) {
            mSortModel = new SortModel();
            mSortModel.name = "折扣率";
            mSortModel.param = "sort";
            mSortModel.value = "6";
            mSortModel.order = "DESC";
            mSortModel.selected = "0";

            data.add(mSortModel);
        }

        if (isSearchResult) {
            for (SortModel item : data) {
                if (item.value.equals(data_sort)) {
                    item.order = data_order;
                    item.selected = "1";
                } else {
                    item.selected = "0";
                }
            }
        }

        mSortAdapter.setData(data);
        mSortAdapter.notifyDataSetChanged();
    }

    private void showShadowView() {
        mShadowView.clearAnimation();
        if (mShadowView.getVisibility() != View.VISIBLE) {
            mShadowView.setVisibility(View.VISIBLE);
        }
        mShadowView.animate().alpha(1).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mShadowView.setAlpha(1);
                mShadowView.setVisibility(View.VISIBLE);
            }
        }).start();
    }

    private void showSortRecyclerView() {
        //updateSortView(true);
        showShadowView();
        enableCoordinatorLayout(false);
        int height = mSortRecyclerView.getMeasuredHeight();
        mSortRecyclerView.setTranslationY(-height);
        mSortRecyclerView.setVisibility(View.VISIBLE);
        mSortRecyclerView.animate().translationYBy(height).setListener(new SimpleAnimatorListener
                () {
            @Override
            public void onAnimationEnd(Animator animator) {
                mSortRecyclerView.setVisibility(View.VISIBLE);
                mSortRecyclerView.setTranslationY(0);
            }
        }).start();
    }

    private void switchSortRecyclerView() {
        if (mSortRecyclerView.getAnimation() != null) {
            return;
        }
        if (mSortRecyclerView.getVisibility() == View.VISIBLE) {
            hideSortRecyclerView();
        } else {
            showSortRecyclerView();
        }
    }

    private void updateCategoryListNumber(int number) {
        if (mDisabledCategoryCartNumber) {
            return;
        }
        if (mCategoryListAdapter == null) {
            return;
        }
        if (mCategoryListAdapter.data.size() == 0) {
            return;
        }

        mCategoryListAdapter.data.get(0).cart_num += number;
        mCategoryListAdapter.notifyItemChanged(0);

        if (mCategoryListAdapter.data.size() == 1) {
            return;
        }
        if (mSelectedGoodsModel.shop_cat_ids == null || mSelectedGoodsModel.shop_cat_ids.size()
                == 0) {
            return;
        }
        for (int i = 1; i < mModel.data.category.size(); i++) {
            CategoryModel parentModel = mModel.data.category.get(i);

            if (mSelectedGoodsModel.shop_cat_ids.contains(parentModel.cat_id)) {
                parentModel.cart_num += number;
                mCategoryListAdapter.notifyItemChanged(mCategoryListAdapter.data.indexOf
                        (parentModel));
                for (int j = 0; j < parentModel.chr_list.size(); j++) {
                    CategoryModel childModel = parentModel.chr_list.get(j);
                    if (mSelectedGoodsModel.shop_cat_ids.contains(childModel.cat_id)) {
                        childModel.cart_num += number;
                        if (mCategoryListAdapter.data.contains(childModel)) {
                            mCategoryListAdapter.notifyItemChanged(mCategoryListAdapter.data
                                    .indexOf(childModel));
                        }
                    }
                }
            }
        }
    }

    private void updateGoToTopButton(float offset) {
        float alpha;
        if (offset < GO_TO_TOP_BUTTON_START_APPEAR_POSITION) {
            alpha = GO_TO_TOP_BUTTON_MIN_ALPHA;
        } else {
            alpha = GO_TO_TOP_BUTTON_MIN_ALPHA + (offset -
                    GO_TO_TOP_BUTTON_START_APPEAR_POSITION) / GO_TO_TOP_BUTTON_FULL_APPEAR_OFFSET;
            if (alpha > 1) {
                alpha = 1;
            }
        }
        mGoToTopImageButton.setAlpha(alpha);
        if (alpha <= 0) {
            mGoToTopImageButton.setVisibility(View.INVISIBLE);
        } else {
            mGoToTopImageButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateGoodsNumber(final GoodsNumberEvent event) {
        if (mGoodsListAdapter == null) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for (int i = mGoodsListAdapter.data.size() - 1; i >= 0; i--) {
                    if (mGoodsListAdapter.getItemViewType(i) == GoodsListAdapter
                            .VIEW_TYPE_GOODS_GRID || mGoodsListAdapter.getItemViewType(i) ==
                            GoodsListAdapter.VIEW_TYPE_GOODS_LIST) {
                        GoodsModel goodsModel = (GoodsModel) mGoodsListAdapter.data.get(i);
                        int cartNumber = 0;
                        for (GoodsEventModel cartGoodsModel : event.goodsInCart) {
                            if (cartGoodsModel.goodsId.contentEquals(goodsModel.goods_id)) {
                                cartNumber += cartGoodsModel.cartNumber;
                            }
                        }

                        if (goodsModel.cart_num != cartNumber) {
                            goodsModel.cart_num = cartNumber;
                            mGoodsListAdapter.notifyItemChanged(i);
                        }
                    }
                }
            }
        });
    }

    private void updateGoodsRecyclerViewLastItem() {
        if (mGoodsListAdapter == null) {
            return;
        }

        if (mGoodsListAdapter.data.size() == 0) {
            return;
        }

        if (mModel.data.page.cur_page >= mModel.data.page.page_count) {
            mGoodsListAdapter.insertEmptyItemAtTheEnd();
        }
    }

    private void updateSortView(boolean isSortRecyclerViewVisible) {
        if (mModel.data.filter.sorts.size() != 0) {
            SortModel mSelectedModel = null;

            for (SortModel sortModel : mModel.data.filter.sorts) {
                if (sortModel.selected.contentEquals("1")) {
                    mSelectedModel = sortModel;
                    break;
                }
            }

            if (mSelectedModel != null) {
                if (mSelectedModel.sort.contentEquals(Macro.SORT_BY_COMPOSITE)) {
                    mCompositeTextView.setSelected(true);
                    mSortTextView.setSelected(false);
                    if (isSortRecyclerViewVisible) {
                        mSortImageView.setImageResource(R.mipmap.bg_arrow_up_dark);
                    } else {
                        mSortImageView.setImageResource(R.mipmap.bg_arrow_down_dark);
                    }
                } else {
                    mSortTextView.setText(mSelectedModel.toString());
                    mCompositeTextView.setSelected(false);
                    mSortTextView.setSelected(true);
                    if (isSortRecyclerViewVisible) {
                        mSortImageView.setImageResource(R.mipmap.bg_arrow_up);
                    } else {
                        mSortImageView.setImageResource(R.mipmap.bg_arrow_down);
                    }
                }
            }
        }
    }

    private void changeSortTextViewIsUnSelect() {
        mCompositeTextView.setSelected(false);
        mSortTextView.setSelected(false);
        mReleaseTextView.setSelected(false);
        mSalesTextView.setSelected(false);
        mSortImageView.setImageResource(R.mipmap.bg_arrow_default);
    }

    @NonNull
    private String changeSortTextViewIsSelect(String value, String order) {
        if (value.equals("1")) {
            flag = false;
            mSalesTextView.setSelected(true);

            mCompositeTextView.setText("" +
                    "综合");
            mCompositeSortImageView.setImageResource(R.mipmap.ic_sorting_arrow_black);
        } else if (value.equals("4")) {
            flag = false;
            if (order.equals(SORT_DESC)) {
                mSortImageView.setImageResource(R.mipmap.bg_arrow_down);
            } else {
                mSortImageView.setImageResource(R.mipmap.bg_arrow_up);
            }
            mSortTextView.setSelected(true);

            mCompositeTextView.setText("综合");
            mCompositeSortImageView.setImageResource(R.mipmap.ic_sorting_arrow_black);

            //新品2、评论3、人气5    第一次降序
        } else if (value.equals("0") || value.equals("2") || value.equals("3") || value.equals("5") || value.equals("6")) {
            flag = true;
            changeCompositeTextView(value);
            //updateSortView(false);
        }
        return value;
    }

    private void changeCompositeTextView(String value) {

        String name = "";
        String order = SORT_DESC;

        mCompositeTextView.setSelected(true);

        for (SortModel sortModel : data) {
            if (sortModel.value.equals(value)) {
                name = sortModel.name;
                order = sortModel.order;
            }
        }
        //默认综合只有降序
        if (value.equals("0")) {
            mCompositeTextView.setText(name);
            mCompositeSortImageView.setImageResource(R.mipmap.ic_sorting_arrow_red);
        } else {
            if (order.equals(SORT_DESC)) {
                mCompositeTextView.setText(name);
                mCompositeSortImageView.setImageResource(R.mipmap.bg_arrow_down);
            } else if (order.equals(SORT_ASC)) {
                mCompositeTextView.setText(name);
                mCompositeSortImageView.setImageResource(R.mipmap.bg_arrow_up);
            }
        }
        //switchSortRecyclerView();
    }


}
