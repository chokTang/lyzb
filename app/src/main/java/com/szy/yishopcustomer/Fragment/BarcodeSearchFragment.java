package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.ScanShopAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AddToCartModel.ResponseAddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.Goods.AddToCartModel;
import com.szy.yishopcustomer.ResponseModel.ScanShop.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.ScanShop.ListModel;
import com.szy.yishopcustomer.ResponseModel.ScanShop.Model;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liuzhifeng on 2016/9/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BarcodeSearchFragment extends YSCBaseFragment implements OnPullListener {
    @BindView(R.id.fragment_scan_shop_list_view)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_index_refresh_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.cart_number_textView)
    TextView mCartNumber;
    @BindView(R.id.layout_float_cart)
    View mCartImageView;
    private ScanShopAdapter mAdapter;
    private List<Object> data;
    private int mPosition;
    private String barcode;
    private int mCurPage = 0;
    private int mPageCount;
    private boolean isLoadMore = true;
    private RecyclerView.OnScrollListener mGoodsRecyclerViewScrollListener = new RecyclerView
            .OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (mAdapter != null && newState == 0) {
                if (isLoadMore) {
                    loadMore();
                }

            }
        }
    };
    private int goodsId;
    private int skuId;
    private String shopid;

    private void loadMore() {
        isLoadMore = false;
        if (mCurPage >= mPageCount) {
            isLoadMore = false;
            //Toast.makeText(getActivity(),"已没有更多",Toast.LENGTH_SHORT).show();
            DividerModel blankModel = new DividerModel();
            mAdapter.data.add(blankModel);
            mAdapter.notifyDataSetChanged();
            return;
        }
        refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_ADD_CART:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());
                    if (resultModel.resultType.equals(Macro.RESULT_TYPE_ADD_TO_CART)) {
                        addToCartAttribute(resultModel.skuId, resultModel.goodsNumber);
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {


    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int id = Utils.getExtraInfoOfTag(v);
        int position = Utils.getPositionOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_SCAN_SHOP_GOODS:
                openGoodsActivity(id);
                break;
            case VIEW_TYPE_SCAN_SHOP_TITLE:
                openShopStreetActivity(id);
                break;
            case VIEW_TYPE_SCAN_SHOP_PLUS:
                //              增加购物车
                mPosition = position;

                GoodsListModel goodsListModel = (GoodsListModel) mAdapter.data.get(position);
                skuId = goodsListModel.sku_id;
                goodsId = goodsListModel.goods_id;

                addToCart(id + "");
                break;
            case VIEW_TYPE_SCAN_SHOP_MINUS:
                //              减少购物车
                mPosition = position;
                removeCart(id + "");
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        data = new ArrayList<Object>();
        mAdapter = new ScanShopAdapter(data);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        Intent intent = getActivity().getIntent();
        barcode = intent.getStringExtra(Key.KEY_BARCODE.getValue());
        shopid = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
        refresh();
        mAdapter.onClickListener = this;
//        mPullableLayout.bottomComponent.setOnPullListener(this);
        mRecyclerView.setOnScrollListener(mGoodsRecyclerViewScrollListener);
        mCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCartActivity();
            }
        });
        mRecyclerView.setEmptyViewClickListener(this);
        return v;
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                mCartNumber.setText(App.getCartString());
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SCAN_SHOP:
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
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_scan_shop;

    }

    private void addToCart(String goodId) {
        CommonRequest addCartRequest = new CommonRequest(Api.API_ADD_TO_CART, HttpWhat
                .HTTP_ADD_CART.getValue(), RequestMethod.POST);
        addCartRequest.setAjax(true);
        addCartRequest.add("goods_id", goodId);
        addCartRequest.add("number", 1);
        addRequest(addCartRequest);
    }

    private void addToCartAttribute(String skuId, String goodsNumber) {
        CommonRequest mAddToCartRequest = new CommonRequest(Api.API_ADD_TO_CART, HttpWhat
                .HTTP_ADD_CART_ATTRIBUTE.getValue(), RequestMethod.POST);
        mAddToCartRequest.add("sku_id", skuId);
        mAddToCartRequest.add("number", goodsNumber);
        mAddToCartRequest.setAjax(true);
        addRequest(mAddToCartRequest);
    }

    private void openCartActivity() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CART_TAB.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        finish();
    }

    private void openGoodsActivity(int position) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), position + "");
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    private void openShopStreetActivity(int shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId + "");
        startActivity(intent);
    }

    public void refresh() {
        mCurPage++;
        CommonRequest request = new CommonRequest(Api.API_SCAN_SHOP, HttpWhat.HTTP_GET_SCAN_SHOP
                .getValue());
        request.add("barcode", barcode);
        if (!TextUtils.isEmpty(shopid)) {
            request.add("shop_id", shopid);
        }
        request.add("page[cur_page]", mCurPage);
        addRequest(request);
    }

    private void refreshCallback(String response) {
        isLoadMore = true;
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model Data) {
                App.setCartNumber(Data.data.all_cart_num, this);
                mCurPage = Data.data.page.cur_page;
                mPageCount = Data.data.page.page_count;
                if (Data.data.list != null && Data.data.list.size() > 0) {
                    if (mCurPage == 1) {
                        data.add(Data.data.page);
                    }
                    for (ListModel listModel : Data.data.list) {
                        data.add(listModel);
                        for (GoodsListModel goodsListModel : listModel.goods_list) {
                            data.add(goodsListModel);
                        }
                    }
                    if (data.size() == 0) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.setData(data);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    /*mAdapter.setData(data);
                    mAdapter.notifyDataSetChanged();*/
                    mRecyclerView.showEmptyView();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                mAdapter.setData(data);
                mAdapter.notifyDataSetChanged();
            }
        }, true);

//        mPullableLayout.bottomComponent.finish(Result.SUCCEED);
    }

    private void refreshCallbackAddCart(String response) {
        HttpResultManager.resolve(response, ResponseAddToCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseAddToCartModel>() {
            @Override
            public void onSuccess(ResponseAddToCartModel data) {
                String mSkuOpen;
                mSkuOpen = data.data.sku_open;
                if (mSkuOpen.equals("0")) {
                    Utils.toastUtil.showToast(getActivity(), "加入购物车成功");
                    App.addCartNumber(1, BarcodeSearchFragment.this);
                    GoodsListModel goodsListModel = (GoodsListModel) mAdapter.data.get(mPosition);
                    goodsListModel.cart_num = Integer.parseInt(goodsListModel.cart_num) + 1 + "";
                    mAdapter.notifyDataSetChanged();
                } else if (mSkuOpen.equals("1")) {
                    Intent intent = new Intent();

                    ArrayList<com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> skuList = new
                            ArrayList<>();
                    if (data.data.sku_list != null) {
                        for (Map.Entry<String, com.szy.yishopcustomer.ResponseModel.Attribute
                                .SkuModel> entry : data.data.sku_list.entrySet()) {
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
                    intent.putExtra(Key.KEY_TYPE.getValue(), RequestCode.REQUEST_CODE_ADD_CART.getValue());

                    intent.setClass(getActivity(), AttributeActivity.class);
                    startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());
                }
            }
        });
    }

    private void refreshCallbackAddCartAttribute(String response) {
        HttpResultManager.resolve(response, AddToCartModel.class, new HttpResultManager.HttpResultCallBack<AddToCartModel>() {
            @Override
            public void onSuccess(AddToCartModel addToCartModel) {
                App.addCartNumber(1, BarcodeSearchFragment.this);
                Utils.makeToast(getActivity(), addToCartModel.message);
                GoodsListModel goodsListModel = (GoodsListModel) mAdapter.data.get(mPosition);
                goodsListModel.cart_num = Integer.parseInt(goodsListModel.cart_num) + 1 + "";
                mAdapter.notifyDataSetChanged();
            }
        }, true);
    }

    private void refreshCallbackRemoveCart(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel mData) {
                App.addCartNumber(-1, this);
                GoodsListModel goodsListModel = (GoodsListModel) mAdapter.data.get(mPosition);
                goodsListModel.cart_num = Integer.parseInt(goodsListModel.cart_num) - 1 + "";
                mAdapter.notifyDataSetChanged();
                Utils.toastUtil.showToast(getActivity(), mData.message);
            }
        }, true);
    }

    private void removeCart(String goodsId) {
        CommonRequest request = new CommonRequest(Api.API_REMOVE_CART, HttpWhat.HTTP_REMOVE_CART
                .getValue());
        request.setAjax(true);
        request.add("number", 1);
        request.add("goods_id", goodsId);
        addRequest(request);
    }

    @Override
    public void onEmptyViewClicked() {
        goIndex();
    }

    private void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }
}
