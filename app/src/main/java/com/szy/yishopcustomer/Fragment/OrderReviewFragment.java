package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ReviewActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Adapter.GoodsCommentImageAdapter;
import com.szy.yishopcustomer.Adapter.OrderReviewAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Review.EvaluateItemModel;
import com.szy.yishopcustomer.ResponseModel.Review.GoodsCommentDescModel;
import com.szy.yishopcustomer.ResponseModel.Review.Model;
import com.szy.yishopcustomer.ResponseModel.Review.ShopComment;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2017/2/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderReviewFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_order_review_recyclerView)
    CommonRecyclerView mRecyclerView;

    private static final int REQUESTCODE_EVALUATE = 1;

    private LinearLayoutManager mLayoutManager;
    private OrderReviewAdapter mOrderReviewAdapter;
    private String mOrderId;
    private String mShopId;
    private Model mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_order_review;

        mOrderReviewAdapter = new OrderReviewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mOrderReviewAdapter);
        mOrderReviewAdapter.onClickListener = this;
        GoodsCommentImageAdapter.onClickListener = this;
        Intent intent = getActivity().getIntent();
        mOrderId = intent.getStringExtra("order_id");
        mShopId = intent.getStringExtra("shop_id");

        refresh();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_EVALUATE:
                if (data != null) {
                    mOrderReviewAdapter.data.clear();
                    refresh();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_EVALUATE_INFO:
                refreshCallback(response);
                break;
            case PUBLISH_SHOP:
                CommonModel modelPublicShop = JSON.parseObject(response, CommonModel.class);
                if (modelPublicShop.code == 0) {
                    Utils.toastUtil.showToast(getActivity(), modelPublicShop.message);
                    mOrderReviewAdapter.data.clear();
                    refresh();
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_EVALUATE:
                EvaluateItemModel evaluateShareOrderItemModel = (EvaluateItemModel)
                        mOrderReviewAdapter.data.get(position);
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReviewActivity.class);
                intent.putExtra(Key.KEY_GOODS_ID.getValue(), evaluateShareOrderItemModel.goods_id);
                intent.putExtra(Key.KEY_GOODS_IMAGE.getValue(), evaluateShareOrderItemModel
                        .goods_image);
                intent.putExtra(Key.KEY_RECORD_ID.getValue(), evaluateShareOrderItemModel
                        .record_id);
                intent.putExtra(Key.KEY_SKU_ID.getValue(), evaluateShareOrderItemModel.sku_id);
                intent.putExtra(Key.KEY_REVIEW_TYPE.getValue(), evaluateShareOrderItemModel
                        .evaluate_status);
                startActivityForResult(intent, REQUESTCODE_EVALUATE);
                break;
            case VIEW_TYPE_CHECK_EVALUATE:
                checkEvaluateClicked(position);
                break;
            case VIEW_TYPE_PUBLISH_SHOP:
                CommonRequest mPublishShopRequest;

                mPublishShopRequest = new CommonRequest(Api.API_PUBLISH_SHOP, HttpWhat
                        .PUBLISH_SHOP.getValue(), RequestMethod.POST);

                Float shopService = mOrderReviewAdapter.shopService;
                Float shopSpeed = mOrderReviewAdapter.shopSpeed;
                Float logisticsSpeed = mOrderReviewAdapter.logisticsSpeed;

                if (shopService == null) {
                    Utils.toastUtil.showToast(getActivity(), "请对店铺服务态度进行评价");
                } else if(shopSpeed == null){
                    Utils.toastUtil.showToast(getActivity(), "请对店铺发货速度进行评价");
                } else if(logisticsSpeed == null){
                    Utils.toastUtil.showToast(getActivity(), "请对店铺物流服务进行评价");
                }else{
                    mPublishShopRequest.add("service_desc_score", String.valueOf(shopService)
                            .split("\\.")[0]);
                    mPublishShopRequest.add("send_desc_score", String.valueOf(shopSpeed).split
                            ("\\.")[0]);
                    mPublishShopRequest.add("logistics_speed_score", String.valueOf
                            (logisticsSpeed).split("\\.")[0]);
                    mPublishShopRequest.add("shop_id", mShopId);
                    mPublishShopRequest.add("order_id", mOrderId);

                    addRequest(mPublishShopRequest);
                }
                break;
            case VIEW_TYPE_GOODS_COMMENT_IMAGE:
                    GoodsCommentDescModel goodsCommentDescModel = (GoodsCommentDescModel)
                            mOrderReviewAdapter.data.get(extraInfo);
                    viewOriginalImage(goodsCommentDescModel.image, position);
                break;
            default:
                super.onClick(v);
        }
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_EVALUATE_INFO, HttpWhat
                .HTTP_EVALUATE_INFO.getValue());
        request.add("order_id", mOrderId);
        addRequest(request);
    }

    private void refreshCallback(String response) {
        Model model = JSON.parseObject(response, Model.class);
        mModel = model;
        if (model.code == 0) {
            setUpAdapterData();
        } else {

        }
    }

    private void setUpAdapterData() {
        if (mModel.data.list != null) {
            for (EvaluateItemModel evaluateItemModel : mModel.data.list) {
                evaluateItemModel.isExpanded = false;
                mOrderReviewAdapter.data.add(evaluateItemModel);
            }
        }
        mOrderReviewAdapter.data.add(new CheckoutDividerModel());
        ShopComment shopComment = new ShopComment();
        shopComment.shop_service = mModel.data.shop_comment.shop_service;
        shopComment.shop_speed = mModel.data.shop_comment.shop_speed;
        shopComment.logistics_speed = mModel.data.shop_comment.logistics_speed;
        mOrderReviewAdapter.data.add(shopComment);

        mOrderReviewAdapter.notifyDataSetChanged();
    }

    private void checkEvaluateClicked(int position) {
        EvaluateItemModel evaluateItemModel = (EvaluateItemModel) mOrderReviewAdapter.data.get
                (position);

        if (evaluateItemModel.isExpanded) {
            mOrderReviewAdapter.data.removeAll(evaluateItemModel.items);
        } else {
            ArrayList<GoodsCommentDescModel> items = new ArrayList<>();
            GoodsCommentDescModel goodsCommentDescModel = new GoodsCommentDescModel();
            goodsCommentDescModel.value = "[评价内容]："+evaluateItemModel.comment_desc;
            goodsCommentDescModel.time = evaluateItemModel.comment_time;
            goodsCommentDescModel.goodsSpec = evaluateItemModel.spec_info;
            goodsCommentDescModel.image = evaluateItemModel.comment_images;
            items.add(goodsCommentDescModel);

            ArrayList<Integer> timeList = new ArrayList<>();
            if (!Utils.isNull(evaluateItemModel.seller_reply_time) && evaluateItemModel
                    .seller_reply_time != 0) {
                timeList.add(evaluateItemModel.seller_reply_time);
            }
            if (!Utils.isNull(evaluateItemModel.add_time) && evaluateItemModel.add_time != 0) {
                timeList.add(evaluateItemModel.add_time);
            }
            if (!Utils.isNull(evaluateItemModel.user_reply_time) && evaluateItemModel
                    .user_reply_time != 0) {
                timeList.add(evaluateItemModel.user_reply_time);
            }

            if (!Utils.isNull(timeList)) {
                Integer[] arr = timeList.toArray(new Integer[0]);
                for (int i = arr.length - 1; i > 0; i--) {
                    for (int j = 1; j <= i; j++) {
                        if (arr[j - 1] > arr[j]) {
                            int tmp = arr[j - 1];
                            arr[j - 1] = arr[j];
                            arr[j] = tmp;
                        }
                    }
                }

                List list = new ArrayList();
                list = Arrays.asList(arr);
                for (int i = 0; i < list.size(); i++) {
                    GoodsCommentDescModel goodsCommentDescData = new GoodsCommentDescModel();
                    if (!Utils.isNull(evaluateItemModel.seller_reply_time) && evaluateItemModel
                            .seller_reply_time != 0) {
                        if ((int) list.get(i) == evaluateItemModel.seller_reply_time) {
                            goodsCommentDescData.time = evaluateItemModel.seller_reply_time;
                            goodsCommentDescData.value = "[卖家回复]：" + evaluateItemModel
                                    .seller_reply_desc;
                            items.add(goodsCommentDescData);
                        }
                    }
                    if (!Utils.isNull(evaluateItemModel.add_time) && evaluateItemModel.add_time
                            != 0) {
                        if ((int) list.get(i) == evaluateItemModel.add_time) {
                            goodsCommentDescData.time = evaluateItemModel.add_time;
                            goodsCommentDescData.value = "[追加评价]：" + evaluateItemModel
                                    .add_comment_desc;
                            goodsCommentDescData.image = evaluateItemModel.add_images;
                            items.add(goodsCommentDescData);
                        }
                    }
                    if (!Utils.isNull(evaluateItemModel.user_reply_time) && evaluateItemModel
                            .user_reply_time != 0) {
                        if ((int) list.get(i) == evaluateItemModel.user_reply_time) {
                            goodsCommentDescData.time = evaluateItemModel.user_reply_time;
                            goodsCommentDescData.value = "[买家回复]：" + evaluateItemModel
                                    .user_reply_desc;
                            items.add(goodsCommentDescData);
                        }
                    }
                }
            }
            evaluateItemModel.items = items;
            mOrderReviewAdapter.data.addAll(position + 1, evaluateItemModel.items);
        }
        evaluateItemModel.isExpanded = !evaluateItemModel.isExpanded;
        mOrderReviewAdapter.notifyDataSetChanged();
    }

    public void viewOriginalImage(ArrayList data, int selectedIndex) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), data);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), selectedIndex);
        startActivity(intent);
    }
}
