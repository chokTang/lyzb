package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Adapter.DistributorIndexAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.PageModel;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistributorIndexModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

public class DistributorIndexFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIBUTOR_INDEX = 1;

    @BindView(R.id.fragment_distributor_goodsList)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_distributor_bg)
    ImageView mBg;
    @BindView(R.id.fragment_distributor_shopImage)
    ImageView mShopImage;
    @BindView(R.id.fragment_distributor_shopName)
    TextView mShopName;

    @BindView(R.id.fragment_index_ad_title_textView)
    TextView mShopTitle;

    private DistributorIndexAdapter mIndexAdapter;
    private boolean isLoad = false;
    private PageModel mPageModel = new PageModel();

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 10;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (isLoad) {
                        loadMoreGoods();
                    }
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distributor_index;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mShopTitle.setText("精挑细选");
        mShopTitle.setTextColor(getResources().getColor(R.color.colorOne));

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mIndexAdapter = new DistributorIndexAdapter();
        mIndexAdapter.onClickListener = this;
        int windowWidth = Utils.getWindowWidth(getActivity());
        int itemWidth = (int) Math.round(windowWidth / 2.0);
        int itemHeight = (int) (itemWidth * 1.4);
        mIndexAdapter.itemWidth = itemWidth;
        mIndexAdapter.itemHeight = itemHeight;
        mRecyclerView.setAdapter(mIndexAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object object = mIndexAdapter.data.get(position);

                if(object instanceof GoodsItemModel){
                    return 1;
                }else{
                    return 2;
                }
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);

        refresh();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                GoodsItemModel goodsItemModel = (GoodsItemModel)mIndexAdapter.data.get(position);
                openGoodsActivity(goodsItemModel.goods_id);
                break;
            default:
                super.onClick(v);
        }
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIBUTOR_INDEX,
                HTTP_WHAT_DISTRIBUTOR_INDEX);
        request.add("user_id", App.getInstance().userId);
        request.add("page[cur_page]", cur_page);
        request.add("page[page_size]", page_size);
        addRequest(request);
    }

    private void loadMoreGoods() {
        isLoad = false;
        cur_page++;
        if(cur_page > mPageModel.page_count){
            EmptyItemModel blankModel = new EmptyItemModel();
            mIndexAdapter.data.add(blankModel);
            mIndexAdapter.notifyDataSetChanged();
        }else{
            refresh();
        }
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIBUTOR_INDEX:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(final String response) {
        HttpResultManager.resolve(response, DistributorIndexModel.class, new HttpResultManager
                .HttpResultCallBack<DistributorIndexModel>() {
            @Override
            public void onSuccess(DistributorIndexModel distributorIndexModel) {
                mPageModel = distributorIndexModel.data.page;
                isLoad = true;
                //店铺头部信息赋值
                if(cur_page == 1){
                    if (!Utils.isNull(distributorIndexModel.data.shop_info.shop_background)) {
                        ImageLoader.displayImage(Utils.urlOfImage(distributorIndexModel.data
                                .shop_info.shop_background), mBg);
                    } else {
                        mBg.setImageResource(R.mipmap.bg_distribution_shop_top);
                    }
                    if (!Utils.isNull(distributorIndexModel.data.shop_info.shop_headimg)) {
                        ImageLoader.displayImage(Utils.urlOfImage(distributorIndexModel.data
                                .shop_info.shop_headimg), mShopImage);
                    } else {
                        mBg.setImageResource(R.mipmap.bg_default_micro_shop);
                    }

                    mShopName.setText(distributorIndexModel.data.shop_info.shop_name);
                }

                //精挑细选商品展示
                if (distributorIndexModel.data.list.size() != 0) {
                    mRecyclerView.hideEmptyView();
                    mIndexAdapter.data.addAll(distributorIndexModel.data.list);
                }else {
                    isLoad = false;
                    mRecyclerView.showEmptyView();
                }

                mIndexAdapter.notifyDataSetChanged();
            }
        });
    }

    public void openGoodsActivity(String goodsId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }


}
