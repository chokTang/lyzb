package com.szy.yishopcustomer.Fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonStringModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ReachbuyCartActivity;
import com.szy.yishopcustomer.Adapter.GoodsListAdapter;
import com.szy.yishopcustomer.Adapter.ShopCategoryListAdapter;
import com.szy.yishopcustomer.Adapter.ShopGoodsListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddToCartModel.ResponseAddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.ReachbuyGoodsList;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.FixBugGridLayoutManager;
import com.szy.yishopcustomer.ViewHolder.GoodsListViewHolder;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by smart on 2017/10/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReachbuyFragment extends YSCBaseFragment {

    private static final int REQUEST_CODE_CART = 0;

    private Context mContext;
    private String rc_id;

    private String sort = "0";
    private String order = "DESC";
    private String keyword = "";
    private String cat_id = "0";
    private int go = 1;

    int mGridItemOffset;

    public int mListStyle = Macro.STYLE_LIST;

    @BindView(R.id.fragment_shop_category_categoryRecyclerView)
    public RecyclerView mCategoryListRecyclerView;
    @BindView(R.id.fragment_shop_category_goodsListRecyclerView)
    public CommonRecyclerView mGoodsListRecyclerView;

    @BindView(R.id.fragment_goods_list_compositeWrapperRelativeLayout)
    View mCompositeWrapperRelativeLayout;
    @BindView(R.id.fragment_goods_list_compositeTextView)
    TextView mCompositeTextView;

    @BindView(R.id.fragment_goods_list_salesRelativeLayout)
    View mSalesRelativeLayout;
    @BindView(R.id.fragment_goods_list_salesTextView)
    TextView mSalesTextView;

    @BindView(R.id.fragment_goods_list_sortWrapperRelativeLayout)
    View mSortWrapperRelativeLayout;
    @BindView(R.id.fragment_goods_list_sortTextView)
    TextView mSortTextView;
    @BindView(R.id.fragment_goods_list_sortImageView)
    ImageView mSortImageView;

    @BindView(R.id.fragment_goods_list_evaluateWrapperRelativeLayout)
    View mEvaluateFilterWrapperRelativeLayout;
    @BindView(R.id.fragment_goods_list_evaluateTextView)
    TextView mEvaluateTextView;

    @BindView(R.id.fragment_shop_goods_list_checkoutButton)
    Button mCheckoutButton;
    @BindView(R.id.fragment_shop_goods_list_cartNumberTextView)
    TextView mCartNumberTextView;
    @BindView(R.id.activity_shop_cartWrapperTwo)
    View mCartWrapperTwo;
    @BindView(R.id.imageView_empty_cart)
    ImageView imageView_empty_cart;

    FixBugGridLayoutManager mGoodsRecyclerViewLayoutManager;
    ShopCategoryListAdapter mCategoryListAdapter;
    GoodsListAdapter mGoodsListAdapter;

    @BindView(R.id.activity_contentView)
    ViewGroup mContentView;

    protected ReachbuyGoodsList mModel;

    int mPreviousSelectedPosition = 0;
    int mPreviousSelectedParentPosition = 0;

    //购物车数量
    private int cart_count = 0;
    private boolean isLoading = false;

    private GoodsListViewHolder mAnimationStartView;
    private String skuId;
    private String goodsId;
    private GoodsModel mSelectedGoodsModel;
    private int mSelectedGoodsPosition;
    private int mSelectedGoodsNumber;

    private boolean mDisabledCategoryCartNumber = true;

    public ReachbuyFragment() {
    }

    protected GoodsListAdapter createGoodsListAdapter() {
        return new ShopGoodsListAdapter(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_reachbuy;

        mCategoryListAdapter = new ShopCategoryListAdapter();
        mCategoryListAdapter.onClickListener = this;
        mGridItemOffset = Utils.dpToPx(getContext(), 0);

        setTitle("");
    }

    boolean flag = false;
    @Override
    public void onResume() {
        super.onResume();

        if(flag) {
            go = 1;
            refresh();
        }

        flag = true;

    }

    private void selectCategory(final int position) {
        if (mCategoryListAdapter == null) {
            return;
        }
        final com.szy.yishopcustomer.ResponseModel.CategoryModel selectedModel = mCategoryListAdapter.data.get(position);
        if (selectedModel.selected && !"0".equals(selectedModel.parent_id)) {
            return;
        }

        //更改参数
        cat_id = selectedModel.cat_id;
        go = 1;
        refresh();

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                //子分类
                if (!selectedModel.isParent) {
                    int beginPosition = 0;
                    int endPosition = 0;
                    for (int i = position; i > 0; i--) {
                        if (mCategoryListAdapter.data.get(i).isParent) {
                            beginPosition = i + 1;
                        }
                    }

                    for (int i = position; i < mCategoryListAdapter.data.size(); i++) {
                        if (mCategoryListAdapter.data.get(i).isParent) {
                            endPosition = i - 1;
                        }
                    }

                    for (int i = beginPosition; i < endPosition; i++) {
                        mCategoryListAdapter.data.get(i).selected = false;
                    }

                    com.szy.yishopcustomer.ResponseModel.CategoryModel categoryModel = mCategoryListAdapter.data.get(beginPosition - 1);
                    for (int i = 0; i < categoryModel.chr_list.size(); i++) {
                        categoryModel.chr_list.get(i).selected = false;
                    }
                }

                selectedModel.selected = true;

                //记录父分类的下标
                int parentPosition = position;

                if (!selectedModel.isParent) {
                    for (int i = position - 1; i > 0; i--) {
                        com.szy.yishopcustomer.ResponseModel.CategoryModel previousModel = mCategoryListAdapter.data.get(i);
                        if (previousModel.isParent) {
                            parentPosition = i;
                            break;
                        }
                    }
                } else {
                    parentPosition = -1;
                    for (int j = 0; j <= position; j++) {
                        com.szy.yishopcustomer.ResponseModel.CategoryModel previousModel = mCategoryListAdapter.data.get(j);

                        if (previousModel.isParent) {
                            parentPosition += 1;
                        }
                    }
                }

                //将上一次选中的取消选中
                if (parentPosition == mPreviousSelectedParentPosition) {

                    com.szy.yishopcustomer.ResponseModel.CategoryModel previousSelectedModel = mCategoryListAdapter.data.get
                            (mPreviousSelectedPosition);
                    if (mPreviousSelectedPosition != mPreviousSelectedParentPosition) {
                        previousSelectedModel.selected = false;
                    }
                    mCategoryListAdapter.notifyDataSetChanged();
                    mPreviousSelectedPosition = position;
                } else {
                    com.szy.yishopcustomer.ResponseModel.CategoryModel previousParentModel = mCategoryListAdapter.data.get
                            (mPreviousSelectedParentPosition);
                    previousParentModel.selected = false;


                    if (previousParentModel.chr_list != null && previousParentModel.chr_list.size
                            () > 0) {
                        mCategoryListAdapter.data.removeAll(previousParentModel.chr_list);
                    }

                    com.szy.yishopcustomer.ResponseModel.CategoryModel parentModel = mCategoryListAdapter.data.get(parentPosition);

                    if (parentModel.chr_list != null && parentModel.chr_list.size() > 0) {
                        mCategoryListAdapter.data.addAll(parentPosition + 1, parentModel
                                .chr_list);
                    }
                    mCategoryListAdapter.notifyDataSetChanged();
                    mPreviousSelectedParentPosition = parentPosition;
                    mPreviousSelectedPosition = mPreviousSelectedParentPosition;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_CATEGORY:
                selectCategory(position);
                break;
            case VIEW_TYPE_COMPOSITE:
                changeSort("0", "DESC");
                break;
            case VIEW_TYPE_GOODS:
                openGoodsActivity(position);
                break;
            case VIEW_TYPE_SORT:
                changeSort("4", order);
                if ("ASC".equals(order)) {
                    order = "DESC";
                } else {
                    order = "ASC";
                }
                break;
            case VIEW_TYPE_FILTER:
                changeSort("3", "DESC");
                break;
            case VIEW_TYPE_SALES:
                changeSort("1", "DESC");
                break;
            case VIEW_TYPE_PLUS:
            case VIEW_TYPE_ADD:
                mAnimationStartView = (GoodsListViewHolder) view.getTag(R.layout
                        .fragment_goods_list_item_list);
                increaseGoodsNumber(position);
                break;
            case VIEW_TYPE_CART:
                openCartActivity();
                break;
            case VIEW_TYPE_MINUS:
                decreaseGoodsNumber(position);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    private void openGoodsActivity(int position) {
        if (mGoodsListAdapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) mGoodsListAdapter.data.get(position);
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsModel.goods_id);
        intent.putExtra("rc_id",rc_id);
        startActivity(intent);
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

    private void removeGoodsFromCart(String goodsId, int number) {
        mSelectedGoodsNumber = -number;
        CommonRequest request = new CommonRequest(Api.API_REACHBUY_REMOVE_CART, HttpWhat.HTTP_REMOVE_CART.getValue());
        request.setAjax(true);
        request.add("number", number);
        request.add("goods_id", goodsId);
        request.add("shop_id", mModel.data.shop_id);

        addRequest(request);
    }

    private void openCartActivity() {
        Intent intent = new Intent(getActivity(), ReachbuyCartActivity.class);
        intent.putExtra("rc_id", rc_id+"");
        startActivity(intent);
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

    private void addToCart(String goodsId, int goodsNumber) {
        addToCart(goodsId, goodsNumber, "");
    }

    private void addToCart(String goodsId, int goodsNumber, String skuId) {
        mSelectedGoodsNumber = goodsNumber;
        CommonRequest request = new CommonRequest(Api.API_REACHBUY_ADD_TO_CART, HttpWhat.HTTP_ADD_CART.getValue(),
                RequestMethod.POST);
        request.setAjax(true);
        request.add("goods_id", goodsId);
        request.add("number", goodsNumber);
        request.add("sku_id", skuId);
        request.add("shop_id", mModel.data.shop_id);

        addRequest(request);
    }

    private void changeSortTextViewIsUnSelect() {
        mCompositeTextView.setSelected(false);
        mSortTextView.setSelected(false);
        mEvaluateTextView.setSelected(false);
        mSalesTextView.setSelected(false);
        mSortImageView.setImageResource(R.mipmap.bg_arrow_default);
    }

    private void changeSort(String value, String order) {
        changeSortTextViewIsUnSelect();

        go = 1;
        sort = value;
        this.order = order;

        switch (value) {
            case "0":
                //综合
                mCompositeTextView.setSelected(true);
                break;
            case "1":
                //销量
                mSalesTextView.setSelected(true);
                break;
            case "3":
                //评价
                mEvaluateTextView.setSelected(true);
                break;
            case "4":
                mSortTextView.setSelected(true);
                //价格下
                if ("ASC".equals(order)) {
                    mSortImageView.setImageResource(R.mipmap.bg_arrow_up);
                } else {
                    mSortImageView.setImageResource(R.mipmap.bg_arrow_down);
                }
                break;
        }

        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mContext = getActivity();
        rc_id = getActivity().getIntent().getStringExtra(Key.KEY_SHOP_ID.getValue());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mCategoryListRecyclerView.setLayoutManager(linearLayoutManager);
        mCategoryListRecyclerView.setAdapter(mCategoryListAdapter);
        mCategoryListRecyclerView.getItemAnimator().setChangeDuration(0);
        mCategoryListRecyclerView.getItemAnimator().setAddDuration(0);
        mCategoryListRecyclerView.getItemAnimator().setRemoveDuration(0);

        mGoodsListAdapter = createGoodsListAdapter();
        mGoodsListAdapter.onClickListener = this;
        mGoodsListAdapter.style = mListStyle;

        mGoodsRecyclerViewLayoutManager = new FixBugGridLayoutManager(getContext(), 2);
        mGoodsRecyclerViewLayoutManager.setSpanSizeLookup(mGoodsListSpanSizeLookup);
        mGoodsListRecyclerView.setLayoutManager(mGoodsRecyclerViewLayoutManager);
        mGoodsListRecyclerView.setAdapter(mGoodsListAdapter);
        mGoodsListRecyclerView.addOnScrollListener(mGoodsRecyclerViewScrollListener);
        mGoodsListRecyclerView.setVisibility(View.VISIBLE);
        mGoodsListRecyclerView.getItemAnimator().setChangeDuration(0);
        mGoodsListRecyclerView.addItemDecoration(mItemDecoration);
        mGoodsListRecyclerView.setEmptyViewClickListener(this);

        Utils.setViewTypeForTag(mSortWrapperRelativeLayout, ViewType.VIEW_TYPE_SORT);
        mSortWrapperRelativeLayout.setOnClickListener(this);

        Utils.setViewTypeForTag(mEvaluateFilterWrapperRelativeLayout, ViewType.VIEW_TYPE_FILTER);
        mEvaluateFilterWrapperRelativeLayout.setOnClickListener(this);

        Utils.setViewTypeForTag(mSalesRelativeLayout, ViewType.VIEW_TYPE_SALES);
        mSalesRelativeLayout.setOnClickListener(this);

        Utils.setViewTypeForTag(mCompositeWrapperRelativeLayout, ViewType.VIEW_TYPE_COMPOSITE);
        mCompositeWrapperRelativeLayout.setOnClickListener(this);

        Utils.setViewTypeForTag(mCartWrapperTwo, ViewType.VIEW_TYPE_CART);
        mCartWrapperTwo.setOnClickListener(this);
        Utils.setViewTypeForTag(mCheckoutButton, ViewType.VIEW_TYPE_CART);
        mCheckoutButton.setOnClickListener(this);

        changeSort("0", "DESC");
        return view;
    }

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

    private void loadMore() {
        if (mGoodsListAdapter == null) {
            return;
        }
        int pageCount = mModel.data.page.page_count;
        go = mModel.data.page.cur_page;
        if (go >= pageCount) {
            return;
        }
        if (!mGoodsListAdapter.isLastItemGoodsType()) {
            return;
        }

        mGoodsListAdapter.insertLoadingItemAtTheEnd();
        go++;

        refresh();
//        mParameters.put(getPageParameterName(), page + "");
//        getGoodsList(mApi, mParameters, REQUEST_WHAT_LOAD_MORE, false);
    }


    private RecyclerView.OnScrollListener mGoodsRecyclerViewScrollListener = new RecyclerView
            .OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
//            if (mGoodsListAdapter != null && newState == 0) {
//                loadMoreIfNeeded(mGoodsListAdapter.bindPosition);
//            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int lastVisibleItemPosition = mGoodsRecyclerViewLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition + 1 == mGoodsListAdapter.data.size()) {

                if (!isLoading) {
                    isLoading = true;

                    loadMoreIfNeeded(lastVisibleItemPosition + 1);
                }
            }

//            updateGoToTopButton(recyclerView.computeVerticalScrollOffset());
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

    private void setTitle(String shopname) {
        if(TextUtils.isEmpty(shopname)) {
            return;
        }

        if(shopname.length() > 12) {
            shopname = shopname.substring(0,12) + "...";
        }
        (getActivity()).setTitle(shopname);
    }

    //0综合 1销量  3评价 4价格下
    public void refresh() {
        isLoading = true;
        CommonRequest mOrderDetailRequest = new CommonRequest(Api.API_REACHBUY+"/"+rc_id, HttpWhat
                .HTTP_GOODS.getValue());

        mOrderDetailRequest.add("cat_id", cat_id);
        mOrderDetailRequest.add("sort", sort);
        mOrderDetailRequest.add("order", order);
        mOrderDetailRequest.add("keyword", "");
        mOrderDetailRequest.add("go", go);

        if (go > 1) {
            mOrderDetailRequest.alarm = false;
        }
        addRequest(mOrderDetailRequest);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GOODS:
                refreshCallBack(response);
                break;
            case HTTP_ADD_CART:
                addToCartSucceed(response);
                break;
            case HTTP_REMOVE_CART:
                removeGoodsFromCartSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void updateCart() {
        if (cart_count > 0) {
            mCartWrapperTwo.setVisibility(View.VISIBLE);
            imageView_empty_cart.setVisibility(View.GONE);
            mCartNumberTextView.setText(cart_count + "");
            mCheckoutButton.setEnabled(true);
        } else {
            mCartWrapperTwo.setVisibility(View.INVISIBLE);
            imageView_empty_cart.setVisibility(View.VISIBLE);
            mCheckoutButton.setEnabled(false);
        }
    }

    private void refreshCallBack(String response) {
        isLoading = false;
        mCategoryListRecyclerView.setVisibility(View.VISIBLE);
        mGoodsListRecyclerView.setVisibility(View.VISIBLE);

        HttpResultManager.resolve(response, ReachbuyGoodsList.class, new HttpResultManager.HttpResultCallBack<ReachbuyGoodsList>() {
            @Override
            public void onSuccess(ReachbuyGoodsList modell) {

                mModel = modell;
                setTitle(mModel.data.shop_name);
                cart_count = mModel.data.cart_count;
                updateCart();

                if (mCategoryListAdapter.data.size() == 0) {
                    mModel.data.category.get(0).selected = true;
                    for (com.szy.yishopcustomer.ResponseModel.CategoryModel model : mModel.data.category) {
                        model.isParent = true;
                        model.type = 1;
                        mCategoryListAdapter.data.add(model);
                    }
                    mCategoryListAdapter.notifyDataSetChanged();
                }

                if (go == 1) {
                    mGoodsListAdapter.data.clear();
                } else {
                    mGoodsListAdapter.removeLastNonGoodsItem();
                }

                mGoodsListAdapter.data.addAll(mModel.data.list);

                if (mGoodsListAdapter.data.size() == 0) {
                    mGoodsListRecyclerView.showEmptyView();
                }

                mGoodsListAdapter.notifyDataSetChanged();
            }
        });
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

                    cart_count += 1;
                    updateCart();

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
            com.szy.yishopcustomer.ResponseModel.CategoryModel parentModel = mModel.data.category.get(i);

            if (mSelectedGoodsModel.shop_cat_ids.contains(parentModel.cat_id)) {
                parentModel.cart_num += number;
                mCategoryListAdapter.notifyItemChanged(mCategoryListAdapter.data.indexOf
                        (parentModel));
                for (int j = 0; j < parentModel.chr_list.size(); j++) {
                    com.szy.yishopcustomer.ResponseModel.CategoryModel childModel = parentModel.chr_list.get(j);
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

    /**
     * 商品添加到购物车的动画
     */
    private void makeCartAnimation() {
        int[] location = new int[2];
        mAnimationStartView.plusImageView.getLocationInWindow(location);

        location[0] -= mAnimationStartView.plusImageView.getMeasuredWidth();
        location[1] -= 2*mAnimationStartView.plusImageView.getMeasuredHeight();

        Drawable drawable = mAnimationStartView.goodsImageView.getDrawable();//复制一个新的商品图标
        makeAnimation(drawable, location[0], location[1]);
    }

    public void makeAnimation(Drawable makeAnimation, int x, int y) {

        if(x == 0 && y < 0) {
            return;
        }

        int[] endLocation = new int[2];
        mCartWrapperTwo.getLocationInWindow(endLocation);
        endLocation[0] -= mCartWrapperTwo.getWidth() / 2;
        endLocation[1] -= mCartWrapperTwo.getWidth() / 2;
        startAnimation(makeAnimation, x, y, endLocation[0], endLocation[1]);
    }

    private void startAnimation(Drawable animView, final int startX, final int startY, final int
            endX, final int endY) {
        final View animationView = LayoutInflater.from(getActivity()).inflate(R.layout
                .activity_shop_animator1, null);

        ImageView im = (ImageView) animationView.findViewById(R.id.imageView_anim);
        im.setImageDrawable(animView);

        mContentView.addView(animationView);

        int picSize = Utils.dpToPx(im.getContext(), 50) / 2;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "X", startX - picSize,
                endX - picSize);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "Y", startY - picSize,
                endY - picSize);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animationView, "scaleX", 1f, 0.25f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animationView, "scaleY", 1f, 0.25f);

        scaleX.setInterpolator(new AnticipateInterpolator(2.0f));
        scaleY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorX.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(700);
        animatorSet.playTogether(animatorX, animatorY, scaleX, scaleY);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.removeView(animationView);
                startCartViewAnimation();
            }
        });
    }

    private void startCartViewAnimation() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mCartWrapperTwo, "scaleX", 1.0f, 1.4f,
                1.0f, 1.2f, 1.0f);
        animatorX.setDuration(200);
        animatorX.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorX.start();

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mCartWrapperTwo, "scaleY", 1.0f, 1.4f,
                1.0f, 1.2f, 1.0f);
        animatorY.setDuration(200);
        animatorY.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorY.start();
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
        intent.putExtra(Key.KEY_TYPE.getValue(), Macro.BUTTON_TYPE_ADD_TO_CART);

        intent.setClass(getActivity(), AttributeActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CART);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
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

    private void openAttributeActivitySucceed(Intent data) {
        if (data != null) {
            ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());
            if (resultModel.resultType.equals(Macro.RESULT_TYPE_ADD_TO_CART)) {
                addToCart(mSelectedGoodsModel.goods_id, Integer.valueOf(resultModel.goodsNumber),
                        resultModel.skuId);
            }
        }
    }

    private void removeGoodsFromCartSucceed(String response) {
        if (mGoodsListAdapter == null) {
            return;
        }

        HttpResultManager.resolve(response, ResponseCommonStringModel.class, new
                HttpResultManager.HttpResultCallBack<ResponseCommonStringModel>() {
                    @Override
                    public void onSuccess(ResponseCommonStringModel back) {

                        mSelectedGoodsModel.cart_num += mSelectedGoodsNumber;
                        mGoodsListAdapter.notifyItemChanged(mSelectedGoodsPosition);
                        updateCategoryListNumber(mSelectedGoodsNumber);

                        cart_count -= 1;
                        updateCart();
                    }
                }, true);
    }
}
