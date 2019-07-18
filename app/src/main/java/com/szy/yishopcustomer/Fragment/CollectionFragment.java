package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.CartActivity;
import com.szy.yishopcustomer.Activity.CollectionActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.CollectionAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddToCartModel.ResponseAddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Collection.CollectionDeleteModel;
import com.szy.yishopcustomer.ResponseModel.Collection.CollectionGoodsModel;
import com.szy.yishopcustomer.ResponseModel.Collection.CollectionItemModel;
import com.szy.yishopcustomer.ResponseModel.Collection.CollectionShopModel;
import com.szy.yishopcustomer.ResponseModel.Collection.Model;
import com.szy.yishopcustomer.ResponseModel.Goods.AddToCartModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Collection.CollectionGoodsViewHolder;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

import static com.szy.yishopcustomer.Activity.CollectionActivity.TYPE_LIST;

/**
 * Created by liwei on 2017/1/6.
 * 个人中心-我的收藏
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CollectionFragment extends YSCBaseFragment implements OnEmptyViewClickListener,OnPullListener {
    public int type;
    public int page = 1;
    public int pageCount;
    public HashSet<String> selectCollectionNumber = new HashSet<>();
    public boolean selectAll = false;
    @BindView(R.id.fragment_goods_collection_textView)
    TextView mGoodsTextView;
    @BindView(R.id.fragment_shop_collection_textView)
    TextView mShopTextView;

    @BindView(R.id.fragment_collection_pullableRecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_collection_pullableLayout)
    PullableLayout fragment_pullableLayout;

    @BindView(R.id.fragment_collection_cart_relativeLayout)
    RelativeLayout mCartRelativeLayout;
    @BindView(R.id.cart_number_textView)
    TextView mCartMumberTextView;
    @BindView(R.id.fragment_collection_bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.linearlayout)
    LinearLayout mTopLayout;
    @BindView(R.id.image_checkbox)
    ImageView mSelectAllButton;
    @BindView(R.id.fragment_collection_goods_number)
    TextView mSelectNumberTextView;
    @BindView(R.id.collection_delete_button)
    TextView mDeleteButton;

    @BindView(R.id.relativeLayout_content)
    RelativeLayout relativeLayout_content;

    private LinearLayoutManager mLayoutManager;
    private CollectionAdapter mAdapter;
    private Model mModel;
    private int mPosition;
    private String goodsNumber;
    private int mCurrentType = TYPE_LIST;
    private boolean upDataSuccess = true;
    private String collectionIds = "";

    //用于加入购物车动画
    private CollectionGoodsViewHolder animholder;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }
                }
            }
        }
    };
    private String goodsId;
    private String skuId;

    public void addToCart(String skuId, String goodsNumber) {
        CommonRequest mAddToCartRequest = new CommonRequest(Api.API_ADD_TO_CART, HttpWhat
                .HTTP_ADD_CART_ATTRIBUTE.getValue(), RequestMethod.POST);
        mAddToCartRequest.add("sku_id", skuId);
        mAddToCartRequest.add("number", goodsNumber);
        mAddToCartRequest.setAjax(true);
        addRequest(mAddToCartRequest);
    }

    /**
     * 切换编辑模式
     *
     * @param showType
     */
    public void changeType(int showType) {
        mCurrentType = showType;
        //TYPE_LIST列表模式
        if (mCurrentType == TYPE_LIST) {
            mAdapter.editing = false;

            mTopLayout.setVisibility(View.VISIBLE);
            mBottomLayout.setVisibility(View.GONE);
            //type=0商品收藏；type=1;店铺收藏
            if (type == 0) {
                mCartRelativeLayout.setVisibility(View.VISIBLE);
            } else {
                mCartRelativeLayout.setVisibility(View.GONE);
            }
            //编辑模式
        } else {
            mAdapter.editing = true;
            mTopLayout.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.VISIBLE);
            mCartRelativeLayout.setVisibility(View.GONE);
        }

        setUpAdapterData();
    }

    public void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_ADD_CART:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());
                    if (resultModel.resultType.equals(Macro.RESULT_TYPE_ADD_TO_CART)) {
                        goodsNumber = resultModel.goodsNumber;
                        addToCart(resultModel.skuId, resultModel.goodsNumber);
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    public View getCartView() {
        return mCartRelativeLayout;
    }

    public RelativeLayout getContentView() {
        return relativeLayout_content;
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_GOODS_COLLECTION:
                changeGoodsCollectionTab();
                break;
            case VIEW_TYPE_SHOP_COLLECTION:
                changeShopCollectionTab();
                break;
            case VIEW_TYPE_SHOP:
                openShopActivity(extraInfo + "");
                break;
            case VIEW_TYPE_GOODS:
                openGoodsActivity(extraInfo + "");
                break;
            case VIEW_TYPE_ADD:
                animholder = (CollectionGoodsViewHolder) v.getTag(R.layout.item_collection_goods);
                //添加到购物车
                addCart(v, position);
                break;
            case VIEW_TYPE_MINUS:
                removeCart(position);
                break;
            case VIEW_TYPE_CART:
                openCartActivity();
                break;
            case VIEW_TYPE_SELECT_ALL:
                selectAll = selectAll == false;

                selectCollectionNumber.clear();
                for (int i = 0; i < mAdapter.data.size(); i++) {
                    Object object = mAdapter.data.get(i);
                    if (object instanceof CollectionGoodsModel) {
                        ((CollectionGoodsModel) object).selected = selectAll;
                        if(selectAll) {
                            selectCollectionNumber.add(((CollectionGoodsModel) object).collect_id);
                        }
                    } else if (object instanceof CollectionShopModel) {
                        ((CollectionShopModel) object).selected = selectAll;
                        if(selectAll) {
                            selectCollectionNumber.add(((CollectionShopModel) object).collect_id);
                        }
                    }
                }
                refreshAllButtonView();
                break;
            case VIEW_TYPE_SELECT_GOOD:
                CollectionGoodsModel collectionGoodsModel = (CollectionGoodsModel) mAdapter.data
                        .get(position);
                collectionGoodsModel.selected = collectionGoodsModel.selected == false;
                if (collectionGoodsModel.selected) {
                    selectCollectionNumber.add(collectionGoodsModel.collect_id);
                } else {
                    selectCollectionNumber.remove(collectionGoodsModel.collect_id);
                }
                refreshAllButtonView();
                break;
            case VIEW_TYPE_SELECT_SHOP:
                CollectionShopModel collectionShopModel = (CollectionShopModel) mAdapter.data.get
                        (position);
                collectionShopModel.selected = collectionShopModel.selected == false;
                if (collectionShopModel.selected) {
                    selectCollectionNumber.add(collectionShopModel.collect_id);
                } else {
                    selectCollectionNumber.remove(collectionShopModel.collect_id);
                }
                refreshAllButtonView();
                break;
            case VIEW_TYPE_DELETE:
                if (selectCollectionNumber.size() == 0) {
                    Toast.makeText(getActivity(), "亲，请先选择要删除的店铺或宝贝", Toast.LENGTH_SHORT).show();
                } else {
                    showConfirmDialog(R.string.areYouSureToDelete, ViewType.VIEW_TYPE_CONFIRM_DELETE
                            .ordinal());
                }
                break;
        }
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CONFIRM_DELETE:
                deleteCollection(getCollectionIds());
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        fragment_pullableLayout.topComponent.setOnPullListener(this);


        Utils.setViewTypeForTag(mGoodsTextView, ViewType.VIEW_TYPE_GOODS_COLLECTION);
        mGoodsTextView.setOnClickListener(this);
        Utils.setViewTypeForTag(mShopTextView, ViewType.VIEW_TYPE_SHOP_COLLECTION);
        mShopTextView.setOnClickListener(this);


        Utils.setViewTypeForTag(mSelectAllButton, ViewType.VIEW_TYPE_SELECT_ALL);
        mSelectAllButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mDeleteButton, ViewType.VIEW_TYPE_DELETE);
        mDeleteButton.setOnClickListener(this);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setEmptyViewClickListener(this);
        mAdapter.onClickListener = this;

        Utils.setViewTypeForTag(mCartRelativeLayout, ViewType.VIEW_TYPE_CART);
        mCartRelativeLayout.setOnClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onEmptyViewClicked() {
        goIndex();
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                mCartMumberTextView.setText(App.getCartString());
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_GOODS_COLLECTION:
            case HTTP_GET_SHOP_COLLECTION:
                upDataSuccess = false;
                mRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_GOODS_COLLECTION:
            case HTTP_GET_SHOP_COLLECTION:
                refreshCallback(response);
                break;
            case HTTP_ADD_CART:
                refreshCallbackAddCart(response);
                break;
            case HTTP_ADD_CART_ATTRIBUTE:
                refreshCallbackAddCartAttribute(response);
                break;
            case HTTP_REMOVE_CART:
                refreshCallbackRemoveCart(response);
                break;
            case HTTP_DELETE_COLLECTION:
                deleteCollectionCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_collection;

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CollectionAdapter();

        Intent intent = getActivity().getIntent();
        type = intent.getIntExtra(Key.KEY_BONUS_TYPE.getValue(), 0);
    }

    public void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    private void addCart(View view, int position) {
        mPosition = position;
        CollectionGoodsModel item = (CollectionGoodsModel) mAdapter.data.get(position);
        goodsId = item.goods_id;
        skuId = item.sku_id;
        CommonRequest addCartRequest = new CommonRequest(Api.API_ADD_TO_CART, HttpWhat
                .HTTP_ADD_CART.getValue(), RequestMethod.POST);
        addCartRequest.setAjax(true);
        addCartRequest.add("goods_id", goodsId);
        addCartRequest.add("number", 1);
        addRequest(addCartRequest);
    }

    private void changeGoodsCollectionTab() {
        selectCollectionNumber.clear();
        mSelectAllButton.setSelected(true);
        mSelectNumberTextView.setText(String.format(getResources().getString(R.string
                .selectedCollectionGoodsFormat), selectCollectionNumber.size() + ""));
        type = 0;
        page = 1;
        mAdapter.data.clear();
        mAdapter.notifyDataSetChanged();
        colorSelect(mGoodsTextView, mShopTextView);
        mCartRelativeLayout.setVisibility(View.VISIBLE);
        refresh();
    }

    private void changeShopCollectionTab() {
        selectCollectionNumber.clear();
        mSelectAllButton.setSelected(false);
        mSelectNumberTextView.setText(String.format(getResources().getString(R.string
                .selectedCollectionShopFormat), selectCollectionNumber.size() + ""));
        type = 1;
        page = 1;
        mAdapter.data.clear();
        mAdapter.notifyDataSetChanged();
        colorSelect(mShopTextView, mGoodsTextView);
        mCartRelativeLayout.setVisibility(View.GONE);
        refresh();
    }


    private void colorSelect(TextView t1, TextView t2) {
        t1.setSelected(true);
        t2.setSelected(false);
    }

    private void deleteCollection(String collectionIds) {
        CommonRequest request = new CommonRequest(Api.DELETE_GOODS_COLLECTION, HttpWhat
                .HTTP_DELETE_COLLECTION.getValue());
        request.setAjax(true);
        request.add("id", collectionIds);
        addRequest(request);
    }

    private void deleteCollectionCallback(String response) {
        collectionIds = "";
        HttpResultManager.resolve(response, CollectionDeleteModel.class, new HttpResultManager.HttpResultCallBack<CollectionDeleteModel>() {
            @Override
            public void onSuccess(CollectionDeleteModel back) {
                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
                changeType(TYPE_LIST);
                if(getActivity() instanceof CollectionActivity) {
                    ((CollectionActivity)getActivity()).changeCurrentType();
                }
                mAdapter.data.clear();
                refresh();
            }
        }, true);
    }

    private String getCollectionIds() {
        for (int i = 0; i < mAdapter.data.size(); i++) {
            Object object = mAdapter.data.get(i);
            if (object instanceof CollectionGoodsModel) {
                if (((CollectionGoodsModel) object).selected) {
                    collectionIds += ((CollectionGoodsModel) object).collect_id + ",";
                }
            } else if (object instanceof CollectionShopModel) {
                if (((CollectionShopModel) object).selected) {
                    collectionIds += ((CollectionShopModel) object).collect_id + ",";
                }

            }
        }
        return collectionIds;
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel blankModel = new CheckoutDividerModel();
            mAdapter.data.add(blankModel);
            mAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh();
        }
    }

    private void openCartActivity() {
        Intent intent = new Intent(getActivity(), CartActivity.class);
        startActivity(intent);
    }

    private void openGoodsActivity(String skuId) {
        Intent intent = new Intent(getActivity(), GoodsActivity.class);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        startActivity(intent);
    }

    public void refresh() {
        if (type == 0) {
            CommonRequest request = new CommonRequest(Api.API_COLLECTION_GOODS, HttpWhat
                    .HTTP_GET_GOODS_COLLECTION.getValue());
            request.add("page[cur_page]", page);
            request.add("page[page_size]", 10);
            addRequest(request);
        } else if (type == 1) {
            CommonRequest request = new CommonRequest(Api.API_COLLECTION_SHOP, HttpWhat
                    .HTTP_GET_SHOP_COLLECTION.getValue());
            request.add("page[cur_page]", page);
            request.add("page[page_size]", 10);
            addRequest(request);
        }
    }

    private int getAdapterDataSize() {
        int num = 0;
        for(int i = 0 ; i < mAdapter.data.size() ; i ++) {
            if(!(mAdapter.data.get(i) instanceof CheckoutDividerModel)) {
                num++;
            }
        }
        return num;
    }

    private void refreshAllButtonView() {
        if (selectCollectionNumber.size() == getAdapterDataSize()) {
            //全选后更改view
            selectAll = true;
            mSelectAllButton.setSelected(true);
        } else {
            selectAll = false;
            mSelectAllButton.setSelected(false);
        }
        if (type == 0) {
            mSelectNumberTextView.setText(String.format(getResources().getString(R.string
                    .selectedCollectionGoodsFormat), selectCollectionNumber.size() + ""));
        } else {
            mSelectNumberTextView.setText(String.format(getResources().getString(R.string
                    .selectedCollectionShopFormat), selectCollectionNumber.size() + ""));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void refreshCallback(String response) {

        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mModel = back;
                pageCount = mModel.data.page.page_count;

                if(page == 1) {
                    mAdapter.data.clear();
                }

                mShopTextView.setText(String.format(getResources().getString(R.string
                        .shopCollectionFormat), mModel.data.shop_collect_count));
                mGoodsTextView.setText(String.format(getResources().getString(R.string
                        .goodsCollectionFormat), mModel.data.goods_collect_count));

                if (mModel.data.list == null || mModel.data.list.size() == 0) {
                    upDataSuccess = false;

                    if (type == 0) {
                        colorSelect(mGoodsTextView, mShopTextView);
                        mRecyclerView.setEmptySubtitle(R.string.emptyCollectionGoodsTitle);
                    } else if (type == 1) {
                        colorSelect(mShopTextView, mGoodsTextView);
                        mRecyclerView.setEmptySubtitle(R.string.emptyCollectionShopTitle);
                    }
                    mRecyclerView.showEmptyView();
                } else {
                    upDataSuccess = true;

                    if (type == 0) {
                        setUpAdapterGoodsData();
                    } else if (type == 1) {
                        setUpAdapterShopData();
                    }

                }
            }
        });
    }

    private void refreshCallbackAddCart(String response) {

        HttpResultManager.resolve(response, ResponseAddToCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseAddToCartModel>() {
            @Override
            public void onSuccess(ResponseAddToCartModel data) {
                String mSkuOpen;
                mSkuOpen = data.data.sku_open;
                if (mSkuOpen.equals("0")) {
                    makeCartAnimation();
                    App.addCartNumber(1, this);
                    Utils.toastUtil.showToast(getActivity(), data.message);

                    CollectionGoodsModel item = (CollectionGoodsModel) mAdapter.data.get(mPosition);
                    item.cart_num = Integer.parseInt(item.cart_num) + 1 + "";

                    mAdapter.notifyDataSetChanged();
                } else if (mSkuOpen.equals("1")) {
                    Intent intent = new Intent();

                    ArrayList<SkuModel> skuList = new ArrayList<>();
                    if (data.data.sku_list != null) {
                        for (Map.Entry<String, SkuModel> entry : data.data.sku_list.entrySet()) {
                            skuList.add(data.data.sku_list.get(entry.getKey()));
                        }
                    }
                    intent.putParcelableArrayListExtra(Key.KEY_SKU_LIST.getValue(), skuList);
                    ArrayList<SpecificationModel> specificationList = new ArrayList<>();
                    if (data.data.spec_list != null) {
                        specificationList.addAll(data.data.spec_list);
                    }
                    intent.putParcelableArrayListExtra(Key.KEY_SPECIFICATION_LIST.getValue(),
                            specificationList);

                    intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
                    intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
                    intent.putExtra(Key.KEY_TYPE.getValue(), Macro.BUTTON_TYPE_ADD_TO_CART_GOODSLIST);

                    intent.setClass(getActivity(), AttributeActivity.class);
                    startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());
                }
            }
        });
    }

    /**
     * 商品添加到购物车的动画
     */
    private void makeCartAnimation() {
        if (getActivity() instanceof CartAnimationMaker) {
            CartAnimationMaker animationMaker = (CartAnimationMaker) getActivity();
            int[] location = new int[2];
            animholder.goodsNumber.getLocationInWindow(location);
            location[1] -= animholder.goodsNumber.getMeasuredHeight();

            Drawable drawable = animholder.goodsImage.getDrawable();//复制一个新的商品图标
            animationMaker.makeAnimation(drawable, location[0], location[1]);
        }
    }


    /**
     * @param response
     */
    private void refreshCallbackAddCartAttribute(String response) {

        HttpResultManager.resolve(response, AddToCartModel.class, new HttpResultManager.HttpResultCallBack<AddToCartModel>() {
            @Override
            public void onSuccess(AddToCartModel back) {
                makeCartAnimation();
                App.addCartNumber(goodsNumber, this);
                Utils.makeToast(getActivity(), back.message);

                CollectionGoodsModel item = (CollectionGoodsModel)
                        mAdapter.data.get(mPosition);
                item.cart_num = Integer.parseInt(item.cart_num) + 1 + "";

                mAdapter.notifyDataSetChanged();
            }
        }, true);
    }

    private void refreshCallbackRemoveCart(String response) {

        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                App.addCartNumber(-1, this);
                CollectionGoodsModel item = (CollectionGoodsModel) mAdapter.data.get(mPosition);
                item.cart_num = Integer.parseInt(item.cart_num) - 1 + "";
                mAdapter.notifyDataSetChanged();
                Utils.toastUtil.showToast(getActivity(), back.message);
            }
        }, true);
    }

    private void removeCart(int position) {

        mPosition = position;
        CollectionGoodsModel item = (CollectionGoodsModel) mAdapter.data.get(position);
        String goodsId = item.goods_id;
        CommonRequest removeCartRequest = new CommonRequest(Api.API_REMOVE_CART, HttpWhat
                .HTTP_REMOVE_CART.getValue());
        removeCartRequest.setAjax(true);
        removeCartRequest.add("number", "1");
        removeCartRequest.add("goods_id", goodsId);
        addRequest(removeCartRequest);
    }

    private void setUpAdapterData() {
//        for (int i = 0; i < mAdapter.data.size(); i++) {
//            Object object = mAdapter.data.get(i);
//            if (object instanceof CollectionGoodsModel) {
//                ((CollectionGoodsModel) object).editing = mCurrentType == CollectionActivity.TYPE_EDIT;
//            } else if (object instanceof CollectionShopModel) {
//                ((CollectionShopModel) object).editing = mCurrentType == CollectionActivity.TYPE_EDIT;
//            }
//        }
        mAdapter.notifyDataSetChanged();
    }

    private void setUpAdapterGoodsData() {
        mSelectNumberTextView.setText(String.format(getResources().getString(R.string
                .selectedCollectionGoodsFormat), selectCollectionNumber.size() + ""));
        App.setCartNumber(mModel.data.context.cart.goods_count, this);
        colorSelect(mGoodsTextView, mShopTextView);

        if (mModel.data.list != null) {
            for (CollectionItemModel collectionItemModel : mModel.data.list) {

                if (!Utils.isNull(collectionItemModel.sku_id)) {
                    CollectionGoodsModel goodsModel = new CollectionGoodsModel();
                    goodsModel.collect_id = collectionItemModel.collect_id;
                    goodsModel.goods_image = collectionItemModel.goods_image;
                    goodsModel.goods_name = collectionItemModel.goods_name;
                    goodsModel.goods_price = collectionItemModel.goods_price;
                    goodsModel.sku_id = collectionItemModel.sku_id;
                    goodsModel.cart_num = collectionItemModel.cart_num;
                    goodsModel.goods_id = collectionItemModel.goods_id;
                    goodsModel.sales_model = collectionItemModel.sales_model;
                    goodsModel.max_integral_num = collectionItemModel.max_integral_num;
                    goodsModel.goods_dk_price = collectionItemModel.goods_dk_price;

                    goodsModel.selected = isSelectExists(collectionItemModel.collect_id);

                    mAdapter.data.add(goodsModel);
                }
            }
        }

        refreshAllButtonView();
    }

    private void setUpAdapterShopData() {
        mSelectNumberTextView.setText(String.format(getResources().getString(R.string
                .selectedCollectionShopFormat), selectCollectionNumber.size() + ""));
        colorSelect(mShopTextView, mGoodsTextView);

        for (CollectionItemModel collectionItemModel : mModel.data.list) {
            CollectionShopModel shopModel = new CollectionShopModel();
            shopModel.collect_id = collectionItemModel.collect_id;
            shopModel.shop_logo = collectionItemModel.shop_logo;
            shopModel.shop_name = collectionItemModel.shop_name;
            shopModel.shop_id = collectionItemModel.shop_id;

            shopModel.selected = isSelectExists(collectionItemModel.collect_id);

            mAdapter.data.add(shopModel);
        }

        refreshAllButtonView();
    }

    private boolean isSelectExists(String collecid){
        for(String s :selectCollectionNumber) {
            if(s.equals(collecid)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            page = 1;
            refresh();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }
}