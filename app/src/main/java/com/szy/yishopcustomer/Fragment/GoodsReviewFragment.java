package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Adapter.CommentTagAdapter;
import com.szy.yishopcustomer.Adapter.GoodsCommentAdapter;
import com.szy.yishopcustomer.Adapter.GoodsCommentImageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.Goods.CommentItemModel;
import com.szy.yishopcustomer.ResponseModel.Goods.CommentModel;
import com.szy.yishopcustomer.ResponseModel.Goods.CommentUserInfoModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsCommentDescModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsCommentTagModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ResponseGoodsModel;
import com.szy.yishopcustomer.ResponseModel.SimplePageModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.YSOStoreRegionListFlowLayoutManager;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

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
 * Created by liuzhifeng on 2016/8/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsReviewFragment extends LazyFragment implements OnPullListener,
        OnEmptyViewClickListener {
    @BindView(R.id.fragment_comment_recyclerView)
    CommonRecyclerView mCommentTagRecyclerView;

    @BindView(R.id.fragment_goods_review_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_comment_list_recyclerView_layout)
    PullableLayout mPullableLayout;
    String sku_id;
    private GoodsCommentAdapter mGoodsCommentAdapter;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private int page_size = 5;
    //每一种评论的最多页数
    private int pageCount;
    private int output = 1;
    private String mCommentTagNameList[] = {"全部", "晒图", "好评", "中评", "差评"};
    private CommentTagAdapter mCommentTagAdapter;
    private int selectedPosition = 0;

    private boolean upDataSuccess = true;
    //评论tag是否加载过，只加载1次
    private boolean isLoaded = false;

    //管理每一页的数据和页数信息
    private List<SimplePageModel<CommentItemModel>> simplePageModelList;

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
    private String goods_id;

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            mGoodsCommentAdapter.data.clear();
            page = 1;
            refresh(selectedPosition);
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_GOODS_COMMENT_IMAGE:
                GoodsCommentDescModel goodsCommentDescModel = (GoodsCommentDescModel)
                        mGoodsCommentAdapter.data.get(extraInfo);
                viewOriginalImage(goodsCommentDescModel.image, position);
                break;
            case VIEW_TYPE_ITEM:
                selectedPosition = position;
                commentTagClicked();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_review;
        Bundle arguments = getArguments();
        sku_id = arguments.getString(Key.KEY_SKU_ID.getValue());
        goods_id = arguments.getString(Key.KEY_GOODS_ID.getValue());
        if (!Utils.isNull(sku_id)) {
            refresh(selectedPosition);
        } else if (!Utils.isNull(goods_id)) {
            getSkuId(goods_id);
        } else {
            Toast.makeText(getContext(), "该商品已下架", Toast.LENGTH_SHORT).show();
        }

        initPageMode();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mPullableLayout.topComponent.setOnPullListener(this);
        //评论标签
        mCommentTagAdapter = new CommentTagAdapter();
        mCommentTagAdapter.onCLickListener = this;
        YSOStoreRegionListFlowLayoutManager ysoStoreRegionListFlowLayoutManager = new
                YSOStoreRegionListFlowLayoutManager(true);
        mCommentTagRecyclerView.setLayoutManager(ysoStoreRegionListFlowLayoutManager);
        mCommentTagRecyclerView.setAdapter(mCommentTagAdapter);

        //评论
        mGoodsCommentAdapter = new GoodsCommentAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGoodsCommentAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setEmptyViewClickListener(this);

        //晒单
        GoodsCommentImageAdapter.onClickListener = this;
        return v;
    }

    @Override
    public void onOfflineViewClicked() {
        upDataSuccess = false;
        refresh(selectedPosition);
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_COMMENT:
                upDataSuccess = false;
                mRecyclerView.showOfflineView();
                mPullableLayout.topComponent.finish(Result.FAILED);
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_COMMENT:
                refreshCallBack(response);
                break;
            case HTTP_GET_SKU_ID:
                ResponseGoodsModel responseGoodsModel = JSON.parseObject(response,
                        ResponseGoodsModel.class);
                if (responseGoodsModel != null) {
                    if (responseGoodsModel.code == 0) {
                        sku_id = responseGoodsModel.data.goods.sku_id;
                        refresh(selectedPosition);
                    }
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void refresh(int status) {
        CommonRequest mCommentRequest = new CommonRequest(Api.API_GOODS_COMMENT, HttpWhat
                .HTTP_COMMENT.getValue());
        mCommentRequest.add("sku_id", sku_id);
        mCommentRequest.add("type", status);
        mCommentRequest.add("page[cur_page]", page);
        mCommentRequest.add("page[page_size]", page_size);
        mCommentRequest.add("output", output);
        mCommentRequest.alarm = false;
        addRequest(mCommentRequest);
    }

    public void viewOriginalImage(ArrayList data, int selectedIndex) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), data);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), selectedIndex);
        startActivity(intent);
    }

    private void commentTagClicked() {
        mGoodsCommentAdapter.data.clear();
        commentTagSelectChange();
        //每次切换判断是否已经加载过数据，没有再请求网络
        if (isCurrentPageEmpty(selectedPosition)) {
            page = 1;
            refresh(selectedPosition);
        } else {
            SimplePageModel<CommentItemModel> temp = simplePageModelList.get(selectedPosition);
            page = temp.getCurrentPage();
            pageCount = temp.getPageCount();
            //updateCommentTagAdapter(temp.getComment_counts());
            updateGoodsCommentAdapter(temp.getList());
        }
    }

    private void getSkuId(String goodsId) {
        CommonRequest mGoodsDetailRequest = new CommonRequest(Config.BASE_URL + "/goods/" +
                goodsId, HttpWhat.HTTP_GET_SKU_ID.getValue());
        mGoodsDetailRequest.alarm = false;
        addRequest(mGoodsDetailRequest);
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;

            DividerModel blankModel = new DividerModel();
            mGoodsCommentAdapter.data.add(blankModel);
            mGoodsCommentAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh(selectedPosition);
        }
    }

    private void refreshCallBack(String response) {
        CommentModel commentModel = JSON.parseObject(response, CommentModel.class);
        pageCount = commentModel.data.page.page_count;

        setCurrentPage(selectedPosition, page, commentModel.data.page.page_count, commentModel
                .data.list, commentModel.data.comment_counts);

        //commentTag只加载1次
        if(!isLoaded){
            updateCommentTagAdapter(commentModel.data.comment_counts);
            isLoaded = true;
        }

        updateGoodsCommentAdapter(commentModel.data.list);

        mPullableLayout.topComponent.finish(Result.SUCCEED);
    }

/*    private void updateCommentTagAdapter(List comment_counts){
        //评论标签
        boolean is = false;
        if (!comment_counts.get(0).equals("0")) {
            is = true;
        }
        if (is) {
            mCommentTagRecyclerView.setVisibility(View.VISIBLE);
            mCommentTagAdapter.data.clear();
            ArrayList<GoodsCommentTagModel> commentTagArrayList = new ArrayList<>();
            for (int i = 0; i < comment_counts.size(); i++) {
                GoodsCommentTagModel goodsCommentTagModel = new GoodsCommentTagModel();
                goodsCommentTagModel.comment_rank = mCommentTagNameList[i] + "(" + comment_counts
                .get(i) + ")";
                goodsCommentTagModel.selected = selectedPosition == i;
                commentTagArrayList.add(goodsCommentTagModel);
            }
            for (GoodsCommentTagModel goodsCommentTagModel : commentTagArrayList) {
                mCommentTagAdapter.data.add(goodsCommentTagModel);
            }

            mCommentTagAdapter.notifyDataSetChanged();
        } else {
            if (mCommentTagAdapter.data.size() == 0) {
                mCommentTagRecyclerView.setVisibility(View.GONE);
            } else {
                commentTagSelectChange();
            }
        }
    }*/

    private void updateCommentTagAdapter(List comment_counts) {
        if (!comment_counts.get(0).equals("0")) {
            mCommentTagRecyclerView.setVisibility(View.VISIBLE);
            mCommentTagAdapter.data.clear();
            ArrayList<GoodsCommentTagModel> commentTagArrayList = new ArrayList<>();
            for (int i = 0; i < comment_counts.size(); i++) {
                GoodsCommentTagModel goodsCommentTagModel = new GoodsCommentTagModel();
                goodsCommentTagModel.comment_rank = mCommentTagNameList[i] + " " + comment_counts
                        .get(i);
                goodsCommentTagModel.selected = selectedPosition == i;
                commentTagArrayList.add(goodsCommentTagModel);
            }
            for (GoodsCommentTagModel goodsCommentTagModel : commentTagArrayList) {
                mCommentTagAdapter.data.add(goodsCommentTagModel);
            }

            mCommentTagAdapter.notifyDataSetChanged();
        } else {
            mCommentTagRecyclerView.setVisibility(View.GONE);
        }
    }

    private void commentTagSelectChange() {
        for (int i = 0; i < mCommentTagAdapter.data.size(); i++) {
            GoodsCommentTagModel item = (GoodsCommentTagModel) mCommentTagAdapter.data.get(i);
            item.selected = false;
        }
        GoodsCommentTagModel item = (GoodsCommentTagModel) mCommentTagAdapter.data.get
                (selectedPosition);
        item.selected = true;
        mCommentTagAdapter.notifyDataSetChanged();
    }

    private void updateGoodsCommentAdapter(List<CommentItemModel> data) {
        //评论
        if (!Utils.isNull(data)) {

            upDataSuccess = true;
            for (CommentItemModel commentItemModel : data) {
                CommentUserInfoModel userInfoModel = new CommentUserInfoModel();
                userInfoModel.headimg = commentItemModel.headimg;

                if(!Utils.isNull(commentItemModel.nickname_encrypt)){
                    userInfoModel.userName = commentItemModel.nickname_encrypt;
                }else {
                    userInfoModel.userName = commentItemModel.user_name_encrypt;
                }

                userInfoModel.rank_img = commentItemModel.rank_img;
                userInfoModel.is_anonymous = commentItemModel.is_anonymous;
                mGoodsCommentAdapter.data.add(userInfoModel);

                if (!Utils.isNull(commentItemModel.comment_desc) || !Utils.isNull
                        (commentItemModel.comment_images)) {
                    GoodsCommentDescModel goodsCommentDescData = new GoodsCommentDescModel();
                    goodsCommentDescData.value = commentItemModel.comment_desc;
                    goodsCommentDescData.time = commentItemModel.comment_time;
                    goodsCommentDescData.goodsSpec = commentItemModel.spec_info;
                    goodsCommentDescData.image = commentItemModel.comment_images;
                    mGoodsCommentAdapter.data.add(goodsCommentDescData);
                }

                ArrayList<Integer> timeList = new ArrayList<>();
                if (!Utils.isNull(commentItemModel.seller_reply_time) && commentItemModel
                        .seller_reply_time != 0) {
                    timeList.add(commentItemModel.seller_reply_time);
                }
                if (!Utils.isNull(commentItemModel.add_time) && commentItemModel.add_time != 0) {
                    timeList.add(commentItemModel.add_time);
                }
                if (!Utils.isNull(commentItemModel.user_reply_time) && commentItemModel
                        .user_reply_time != 0) {
                    timeList.add(commentItemModel.user_reply_time);
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
                        if (!Utils.isNull(commentItemModel.seller_reply_time) && commentItemModel
                                .seller_reply_time != 0) {
                            if ((int) list.get(i) == commentItemModel.seller_reply_time) {
                                goodsCommentDescData.time = commentItemModel.seller_reply_time;
                                goodsCommentDescData.value = "[卖家回复]：" + commentItemModel
                                        .seller_reply_desc;
                                mGoodsCommentAdapter.data.add(goodsCommentDescData);
                            }
                        }
                        if (!Utils.isNull(commentItemModel.add_time) && commentItemModel.add_time
                                != 0) {
                            if ((int) list.get(i) == commentItemModel.add_time) {
                                goodsCommentDescData.time = commentItemModel.add_time;
                                goodsCommentDescData.value = "[追加评论]：" + commentItemModel
                                        .add_comment_desc;
                                goodsCommentDescData.image = commentItemModel.add_images;
                                mGoodsCommentAdapter.data.add(goodsCommentDescData);
                            }
                        }
                        if (!Utils.isNull(commentItemModel.user_reply_time) && commentItemModel
                                .user_reply_time != 0) {
                            if ((int) list.get(i) == commentItemModel.user_reply_time) {
                                goodsCommentDescData.time = commentItemModel.user_reply_time;
                                goodsCommentDescData.value = "[买家回复]：" + commentItemModel
                                        .user_reply_desc;
                                mGoodsCommentAdapter.data.add(goodsCommentDescData);
                            }
                        }
                    }
                }
                if (data.size() != 1) {
                    mGoodsCommentAdapter.data.add(new EmptyItemModel());
                }
            }
        } else {
            upDataSuccess = false;
            mRecyclerView.showEmptyView();
        }
        mGoodsCommentAdapter.notifyDataSetChanged();
    }


    private void initPageMode() {
        if (simplePageModelList == null) {
            simplePageModelList = new ArrayList<>();
        }

        simplePageModelList.clear();
        for (int i = 0, len = mCommentTagNameList.length; i < len; i++) {
            SimplePageModel<CommentItemModel> temp = new SimplePageModel<>();
            simplePageModelList.add(temp);
        }
    }

    private void initPage(int position) {
        SimplePageModel<CommentItemModel> temp = simplePageModelList.get(position);
        temp.setCurrentPage(1);
        temp.listClean();
    }

    private void setCurrentPage(int position, int page, int pageCount, List<CommentItemModel>
            list, List comment_counts) {
        SimplePageModel<CommentItemModel> temp = simplePageModelList.get(position);
        if (page < temp.getCurrentPage() || page == 1) {
            initPage(position);
        }

        temp.listAddAll(list);

        temp.setPageCount(pageCount);
        temp.setCurrentPage(page);
        temp.setComment_counts(comment_counts);
    }

    /**
     * 当前页面是否有数据
     *
     * @param position
     * @return
     */
    private boolean isCurrentPageEmpty(int position) {
        SimplePageModel<CommentItemModel> temp = simplePageModelList.get(position);
        if (temp.getList() == null || temp.getList().size() <= 0) {
            return true;
        }

        return false;
    }

}