package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Adapter.GoodsCommentImageAdapter;
import com.szy.yishopcustomer.Adapter.ReviewListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.BlankModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsCommentDescModel;
import com.szy.yishopcustomer.ResponseModel.ReviewList.Model;
import com.szy.yishopcustomer.ResponseModel.ReviewList.ReviewGoodsInfoModel;
import com.szy.yishopcustomer.ResponseModel.ReviewList.ReviewItemModel;
import com.szy.yishopcustomer.ResponseModel.ReviewList.ReviewTitleModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 2016/12/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewListFragment extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener {
    @BindView(R.id.fragment_review_list_all_comment_textView)
    TextView mAllCommentTextView;
    @BindView(R.id.fragment_review_list_favourable_comment_textView)
    TextView mFavourableCommentTextView;
    @BindView(R.id.fragment_review_list_moderate_comment_textView)
    TextView mModerateCommentTextView;
    @BindView(R.id.fragment_review_list_negative_comment_textView)
    TextView mNegativeCommentTextView;
    @BindView(R.id.fragment_review_list_pullableLayout)
    PullableLayout mPullableLayout;

    @BindView(R.id.fragment_review_list_recyclerView)
    CommonRecyclerView mCommentRecyclerView;
    private ReviewListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private int page = 1;
    private String mReviewStatus = "0";
    private int pageCount;

    private boolean upDataSuccess = true;

    private final int ALL = 0;   //0 : 全部
    private final int GOOD = 1;  //1 : 好评
    private final int MIDDLE = 2;//2 : 中评
    private final int BAD = 3;   //3 : 差评
    private int httpCode = ALL;   //请求标识,识别不同状态
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mCommentRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }

                }
            }
        }
    };

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            mAdapter.data.clear();
            page = 1;
            refresh(mReviewStatus, String.valueOf(page));
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_review_list_all_comment_textView:
                if (httpCode != ALL) {
                    AllEvaluation();
                }
                break;
            case R.id.fragment_review_list_favourable_comment_textView:
                if (httpCode != GOOD) {
                    goodEvaluation();
                }
                break;
            case R.id.fragment_review_list_moderate_comment_textView:
                if (httpCode != MIDDLE) {
                    middleEvaluation();
                }
                break;
            case R.id.fragment_review_list_negative_comment_textView:
                if (httpCode != BAD) {
                    badEvaluation();
                }
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_GOODS_COMMENT_IMAGE:
                        if (mAdapter.data.get(extraInfo) instanceof GoodsCommentDescModel) {
                            GoodsCommentDescModel goodsCommentDescModel = (GoodsCommentDescModel)
                                    mAdapter.data.get(extraInfo);
                            viewOriginalImage(goodsCommentDescModel.image, position);
                        } else {
                            ReviewGoodsInfoModel goodsCommentDescModel = (ReviewGoodsInfoModel)
                                    mAdapter.data.get(extraInfo);
                            viewOriginalImage(goodsCommentDescModel.images, position);
                        }
                        break;
                    case VIEW_TYPE_GIFT:
                        ReviewGoodsInfoModel goodsInfoModel = (ReviewGoodsInfoModel)
                                mAdapter.data.get(position);
                        Intent intent = new Intent(getContext(), GoodsActivity.class);
                        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsInfoModel.goods_id);
                        startActivity(intent);
                        break;
                    default:
                        super.onClick(v);
                }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mAllCommentTextView.setOnClickListener(this);
        mFavourableCommentTextView.setOnClickListener(this);
        mModerateCommentTextView.setOnClickListener(this);
        mNegativeCommentTextView.setOnClickListener(this);
        mPullableLayout.topComponent.setOnPullListener(this);

        mAdapter = new ReviewListAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mCommentRecyclerView.setLayoutManager(mLayoutManager);
        mCommentRecyclerView.setAdapter(mAdapter);
        mCommentRecyclerView.addOnScrollListener(mOnScrollListener);
        mCommentRecyclerView.setEmptyViewClickListener(this);
        //晒单
        GoodsCommentImageAdapter.onClickListener = this;
        mAdapter.onClickListener = this;

        AllEvaluation();
        return v;
    }

    @Override
    public void onOfflineViewClicked() {
        mAdapter.data.clear();
        page = 1;
        refresh(mReviewStatus, String.valueOf(page));
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_EVALUATE:
                mPullableLayout.topComponent.finish(Result.FAILED);
                upDataSuccess = false;
                mCommentRecyclerView.showOfflineView();
                break;
            case HTTP_GET_EVALUATE_GOOD:
                mPullableLayout.topComponent.finish(Result.FAILED);
                upDataSuccess = false;
                mCommentRecyclerView.showOfflineView();
                break;
            case HTTP_GET_EVALUATE_MIDDLE:
                mPullableLayout.topComponent.finish(Result.FAILED);
                upDataSuccess = false;
                mCommentRecyclerView.showOfflineView();
                break;
            case HTTP_GET_EVALUATE_BAD:
                mPullableLayout.topComponent.finish(Result.FAILED);
                upDataSuccess = false;
                mCommentRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_EVALUATE:
                refreshCallback(response, ALL);
                break;
            case HTTP_GET_EVALUATE_GOOD:
                refreshCallback(response, GOOD);
                break;
            case HTTP_GET_EVALUATE_MIDDLE:
                refreshCallback(response, MIDDLE);
                break;
            case HTTP_GET_EVALUATE_BAD:
                refreshCallback(response, BAD);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_review_list;
    }

    public void refresh(String status, String page) {
        CommonRequest mEvaluateRequest ;
        switch (httpCode) {
            case ALL:
                mEvaluateRequest = new CommonRequest(Api.API_EVALUATE, HttpWhat
                        .HTTP_GET_EVALUATE.getValue());
                break;
            case GOOD:
                mEvaluateRequest = new CommonRequest(Api.API_EVALUATE, HttpWhat
                        .HTTP_GET_EVALUATE_GOOD.getValue());
                break;
            case MIDDLE:
                mEvaluateRequest = new CommonRequest(Api.API_EVALUATE, HttpWhat
                        .HTTP_GET_EVALUATE_MIDDLE.getValue());
                break;
            case BAD:
                mEvaluateRequest = new CommonRequest(Api.API_EVALUATE, HttpWhat
                        .HTTP_GET_EVALUATE_BAD.getValue());
                break;
                default:
                    mEvaluateRequest = new CommonRequest(Api.API_EVALUATE, HttpWhat
                            .HTTP_GET_EVALUATE.getValue());
                    break;
        }
        mEvaluateRequest.add("page[cur_page]", page);
        mEvaluateRequest.add("page[page_size]", "5");
        mEvaluateRequest.add("comment_level", status);
        addRequest(mEvaluateRequest);
    }

    public void viewOriginalImage(ArrayList data, int selectedIndex) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), data);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), selectedIndex);
        startActivity(intent);
    }

    private void AllEvaluation() {
        mAdapter.notifyItemRangeRemoved(0,mAdapter.getItemCount());
        mAdapter.data.clear();
        upDataSuccess = true;
        mReviewStatus = "0";
        page = 1;
        colorSelect(mAllCommentTextView);
        httpCode = ALL;
        refresh(mReviewStatus, page + "");
    }

    private void badEvaluation() {
        mAdapter.notifyItemRangeRemoved(0,mAdapter.getItemCount());
        mAdapter.data.clear();
        upDataSuccess = true;
        mReviewStatus = "3";
        page = 1;
        colorSelect(mNegativeCommentTextView);
        httpCode = BAD;
        refresh(mReviewStatus, page + "");
    }

    private void colorSelect(TextView t) {
        mAllCommentTextView.setSelected(false);
        mFavourableCommentTextView.setSelected(false);
        mModerateCommentTextView.setSelected(false);
        mNegativeCommentTextView.setSelected(false);
        t.setSelected(true);
    }

    private void goodEvaluation() {
        mAdapter.notifyItemRangeRemoved(0,mAdapter.getItemCount());
        mAdapter.data.clear();
        upDataSuccess = true;
        mReviewStatus = "1";
        page = 1;
        colorSelect(mFavourableCommentTextView);
        httpCode = GOOD;
        refresh(mReviewStatus, page + "");
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel dividerModel = new CheckoutDividerModel();
            mAdapter.data.add(dividerModel);
            mAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh(mReviewStatus, page + "");
        }
    }

    private void middleEvaluation() {
        mAdapter.notifyItemRangeRemoved(0,mAdapter.getItemCount());
        mAdapter.data.clear();
        upDataSuccess = true;
        mReviewStatus = "2";
        page = 1;
        colorSelect(mModerateCommentTextView);
        httpCode = MIDDLE;
        refresh(mReviewStatus, page + "");
    }

    private void refreshCallback(String response, int position) {
        if (position != httpCode) {
            return;
        }
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                pageCount = model.data.page.page_count;
                if (!Utils.isNull(model.data.list)) {
                    if (page == 1) {
                        mAdapter.data.add(new BlankModel());
                    }
                    upDataSuccess = true;
                    for (ReviewItemModel reviewItemModel : model.data.list) {
                        ReviewTitleModel titleModel = new ReviewTitleModel();
                        titleModel.score_desc = reviewItemModel.score_desc;
                        titleModel.desc_mark = reviewItemModel.desc_mark;
                        titleModel.order_add_time = Utils.times(reviewItemModel.order_add_time, "yyyy-MM-dd HH:mm:ss");
                        mAdapter.data.add(titleModel);

                        ReviewGoodsInfoModel goodsInfoModel = new ReviewGoodsInfoModel();
                        goodsInfoModel.goods_image = reviewItemModel.goods_image;
                        goodsInfoModel.goods_name = reviewItemModel.goods_name;
                        goodsInfoModel.spec_info = reviewItemModel.spec_info;
                        goodsInfoModel.goods_id = reviewItemModel.goods_id;
                        goodsInfoModel.message = reviewItemModel.comment_desc;
                        goodsInfoModel.time = reviewItemModel.comment_time;

//                        ArrayList<String> images = new ArrayList<String>();
//                        images.add(reviewItemModel.comment_img1);
//                        images.add(reviewItemModel.comment_img2);
//                        images.add(reviewItemModel.comment_img3);
//                        images.add(reviewItemModel.comment_img4);
//                        images.add(reviewItemModel.comment_img5);

                        goodsInfoModel.images = reviewItemModel.comment_images;
                        mAdapter.data.add(goodsInfoModel);

                        ArrayList<Integer> timeList = new ArrayList<>();
                        if (reviewItemModel.seller_reply_time != 0) {
                            timeList.add(reviewItemModel.seller_reply_time);
                        }
                        if (reviewItemModel.add_time != 0) {
                            timeList.add(reviewItemModel.add_time);
                        }
                        if (reviewItemModel.user_reply_time != 0) {
                            timeList.add(reviewItemModel.user_reply_time);
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

                                if (i == list.size() - 1) {
                                    goodsCommentDescData.isLastReview = true;
                                } else {
                                    goodsCommentDescData.isLastReview = false;
                                }

                                if (reviewItemModel.seller_reply_time != 0) {
                                    if ((int) list.get(i) == reviewItemModel.seller_reply_time) {
                                        goodsCommentDescData.time = reviewItemModel.seller_reply_time;
                                        goodsCommentDescData.value = "[卖家回复]：" + reviewItemModel
                                                .seller_reply_desc;
                                        mAdapter.data.add(goodsCommentDescData);
                                    }
                                }
                                if (reviewItemModel.add_time != 0) {
                                    if ((int) list.get(i) == reviewItemModel.add_time) {
                                        goodsCommentDescData.time = reviewItemModel.add_time;
                                        goodsCommentDescData.value = "[追加评论]：" + reviewItemModel
                                                .add_comment_desc;
                                        goodsCommentDescData.image = reviewItemModel.add_images;
                                        mAdapter.data.add(goodsCommentDescData);
                                    }
                                }
                                if (reviewItemModel.user_reply_time != 0) {
                                    if ((int) list.get(i) == reviewItemModel.user_reply_time) {
                                        goodsCommentDescData.time = reviewItemModel.user_reply_time;
                                        goodsCommentDescData.value = "[买家回复]：" + reviewItemModel
                                                .user_reply_desc;
                                        mAdapter.data.add(goodsCommentDescData);
                                    }
                                }
                            }
                        }
                        mAdapter.data.add(new BlankModel());
                    }

                } else {
                    upDataSuccess = false;
                    mCommentRecyclerView.showEmptyView();
                }
            }
        });

        mAdapter.notifyDataSetChanged();
        mPullableLayout.topComponent.finish(Result.SUCCEED);
    }

}
