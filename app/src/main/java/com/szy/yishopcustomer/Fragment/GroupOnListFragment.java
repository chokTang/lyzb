package com.szy.yishopcustomer.Fragment;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GroupOnListActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Adapter.GroupOnListAdapter;
import com.szy.yishopcustomer.Adapter.GroupOnListAttrAdapter;
import com.szy.yishopcustomer.Adapter.GroupOnListTitleAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.SimpleAnimatorListener;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.GroupOnModel.GrouponListModel;
import com.szy.yishopcustomer.ResponseModel.GroupOnModel.Model;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.GroupOnLIstTabModel;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liuzhifeng on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupOnListFragment extends YSCBaseFragment implements OnPullListener{

    @BindView(R.id.fragment_group_on_list_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_group_on_list_attr_recyclerView)
    CommonRecyclerView mAttrRecyclerView;
    @BindView(R.id.fragment_group_on_list_title_recyclerView)
    CommonRecyclerView mTitleRecyclerView;
    @BindView(R.id.fragment_group_on_categray_layout)
    LinearLayout mTitleLayout;
    @BindView(R.id.fragment_group_on_list_all_textView)
    TextView mTabAll;
    @BindView(R.id.item_group_on_list_imageView)
    ImageView mTabMore;
    @BindView(R.id.fragment_group_on_list_attr_relativeLayout)
    RelativeLayout mTabLayout;
    @BindView(R.id.fragment_group_on_list_attr_close_imageView)
    ImageView mClose;
    @BindView(R.id.fragment_grup_on_list_pullAbleLayout)
    PullableLayout mPullableLayout;

    String shopId = "";
    String mPosition = "";
    private ArrayList<Object> mList;
    private GroupOnListAdapter mAdapter;
    private GroupOnListTitleAdapter mTabAdapter;
    private LinearLayoutManager mTabLayoutManager;
    private ArrayList<String> catIdList;
    private GroupOnListAttrAdapter mAttrAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_group_on_list;
        Intent intent = getActivity().getIntent();
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(),v);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new GroupOnListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = Utils.dpToPx(getContext(), 10);
            }
        };
        mRecyclerView.addItemDecoration(mItemDecoration);
        mList = new ArrayList<Object>();
        mAdapter.onClickListener = this;

        mTabLayoutManager = new LinearLayoutManager(getContext());
        mTabLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTabAdapter = new GroupOnListTitleAdapter();
        mTitleRecyclerView.setAdapter(mTabAdapter);
        mTitleRecyclerView.setLayoutManager(mTabLayoutManager);
        mTabAdapter.onClickListener = this;

        mPullableLayout.topComponent.setOnPullListener(this);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mGridLayoutManager.setSpanSizeLookup(mSpanSizeLookup);
        mAttrAdapter = new GroupOnListAttrAdapter();
        mAttrRecyclerView.setAdapter(mAttrAdapter);
        mAttrRecyclerView.setLayoutManager(mGridLayoutManager);
        mAttrAdapter.onClickListener = this;

        mTabAll.setOnClickListener(this);
        mTabMore.setOnClickListener(this);
        mClose.setOnClickListener(this);
        addRequest();
        return v;
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
                GrouponListModel listModel = (GrouponListModel) mAdapter.data.get(position);
                openGoodsActivity(listModel.goods_id);
                break;
            case VIEW_TYPE_SHOP:
                GrouponListModel model = (GrouponListModel) mAdapter.data.get(position);
                openShopActivity(model.shop_id);
                break;
            case VIEW_TYPE_CATEGORY:
                mTabAll.setSelected(false);
                for (int i = 0; i < mTabAdapter.mData.size(); i++) {
                    mTabAdapter.mData.get(i).selected = false;
                    mAttrAdapter.mData.get(i).selected = false;
                }
                mTabAdapter.mData.get(position).selected = true;
                mAttrAdapter.mData.get(position).selected = true;

                if (position != mTabAdapter.mData.size() - 1) {
                    int n = position - mTabLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mTitleRecyclerView.getChildCount()) {
                        int top = mTitleRecyclerView.getChildAt(n).getLeft();
                        if (280 > top) {
                            mTitleRecyclerView.smoothScrollBy(-(280 - top), 0);
                        } else {
                            mTitleRecyclerView.smoothScrollBy(top - 280, 0);
                        }
                    } else {
                        mTitleRecyclerView.smoothScrollToPosition(position);
                    }
                } else {
                    mTitleRecyclerView.smoothScrollToPosition(position);
                }
                mTabAdapter.notifyDataSetChanged();
                mAttrAdapter.notifyDataSetChanged();
                hideSortRecyclerView();
                mPosition = String.valueOf(position);
                refreshRequest(position);
                break;
            default:
                switch (v.getId()) {
                    case R.id.fragment_group_on_list_all_textView:
                        addRequest();
                        break;
                    case R.id.fragment_group_on_list_attr_close_imageView:
                        clickMore();
                        break;
                    case R.id.item_group_on_list_imageView:
                        clickMore();
                        break;
                    default:
                        super.onClick(v);
                }

        }
    }

    private void clickMore() {
        if (mTabLayout.getVisibility() == View.GONE) {
            showSortRecyclerView();
        } else {
            hideSortRecyclerView();
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GROUP_0N_LSIT:
                requestCallback(response);
                break;
            case HTTP_GROUP_0N_LSIT_REFRESH:
                Model model = JSON.parseObject(response, Model.class);
                if (model.code == 0) {
                    if (model.data.groupon_list.size() != 0) {
                        mList.clear();
                        mList.addAll(model.data.groupon_list);
                        mList.add(new DividerModel());
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.data.clear();
                        mRecyclerView.setEmptyImage(R.mipmap.bg_public);
                        mRecyclerView.setEmptyTitle(R.string.emptyGroupOn);
                        mRecyclerView.showEmptyView();
                    }

                    mPullableLayout.topComponent.finish(Result.SUCCEED);
                }
                break;

            default:
                super.onRequestSucceed(what, response);
        }
    }

    private void requestCallback(String response) {
        Model model = JSON.parseObject(response, Model.class);

        if (model.code == 0) {
            mPosition = "";
            if (model.data.groupon_list.size() != 0) {
                mList.clear();
                mList.addAll(model.data.groupon_list);
                mList.add(new DividerModel());
                mAdapter.setData(mList);
                mAdapter.notifyDataSetChanged();
            } else {
                mRecyclerView.setEmptyImage(R.mipmap.bg_public);
                mRecyclerView.setEmptyTitle(R.string.emptyGroupOn);
                mRecyclerView.showEmptyView();
            }
            catIdList = new ArrayList<String>();
            ArrayList<GroupOnLIstTabModel> data = new ArrayList<GroupOnLIstTabModel>();
            if (!Utils.isNull(model.data.cat_list)) {
                mTitleLayout.setVisibility(View.VISIBLE);

                for(int i=0;i<model.data.cat_list.size();i++){
                    catIdList.add(model.data.cat_list.get(i).cat_id);

                    GroupOnLIstTabModel mTabModel = new GroupOnLIstTabModel();
                    mTabModel.name = model.data.cat_list.get(i).cat_name;
                    mTabModel.selected = false;
                    data.add(mTabModel);
                }

                //增加下期预告
                if (model.data.underline) {
                    catIdList.add(Macro.GROUPON_UNDERLINE);
                    GroupOnLIstTabModel mTabModel = new GroupOnLIstTabModel();
                    mTabModel.name = "下期预告";
                    mTabModel.selected = false;
                    data.add(mTabModel);
                }

            }else {
                mTitleLayout.setVisibility(View.GONE);
            }

            //分享数据
            GroupOnListActivity.shareData.add(Utils.getMallMBaseUrl() + "/groupon.html");
            GroupOnListActivity.shareData.add(model.data.share.seo_groupon_title);
            GroupOnListActivity.shareData.add(model.data.share.seo_groupon_discription);
            GroupOnListActivity.shareData.add(Utils.urlOfImage(model.data.share.seo_groupon_image));
            GroupOnListActivity.shareData.add(Config.BASE_URL + model.data.share
                    .seo_groupon_qrcode);

            mTabAll.setSelected(true);
            mTabAdapter.setData(data);
            mTabAdapter.notifyDataSetChanged();
            mAttrAdapter.setData(data);
            mAttrAdapter.mData.add(new GroupOnLIstTabModel().isSingleLine(true));
            mAttrAdapter.mData.add(new GroupOnLIstTabModel().isSingleLine(true));
            mAttrAdapter.notifyDataSetChanged();

            mPullableLayout.topComponent.finish(Result.SUCCEED);
        }
    }

    private void addRequest() {
        CommonRequest request = new CommonRequest(Api.API_GROUP_ON_LIST, HttpWhat
                .HTTP_GROUP_0N_LSIT.getValue());
        if (!Utils.isNull(shopId)) {
            request.add("shop_id", shopId);
        }
        addRequest(request);
    }

    private void refreshRequest(int mPosition) {
        CommonRequest request = new CommonRequest(Api.API_GROUP_ON_LIST, HttpWhat
                .HTTP_GROUP_0N_LSIT_REFRESH.getValue());
        if (!Utils.isNull(mPosition)) {
            if (catIdList.get(mPosition).equals("-1")) {
                request.add("underline", "1");
            } else {
                request.add("cat_id", catIdList.get(mPosition));
            }
        }
        addRequest(request);
    }

    private void openUserGroupOnDetail(String groupSn) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.setClass(getActivity(), UserGroupOnDetailActivity.class);
        startActivity(intent);
    }

    private void hideSortRecyclerView() {
        mTabMore.setImageResource(R.mipmap.btn_down_arrow_red);
        int height = mTabLayout.getMeasuredHeight();
        mTabLayout.animate().translationYBy(-height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mTabLayout.setVisibility(View.GONE);
                mPullableLayout.setVisibility(View.VISIBLE);
                mTabLayout.setTranslationY(0);
            }
        }).start();
    }

    private void showSortRecyclerView() {
        mTabMore.setImageResource(R.mipmap.btn_up_arrow_red);
        int height = mTabLayout.getMeasuredHeight();
        mTabLayout.setTranslationY(-height);
        mTabLayout.setVisibility(View.VISIBLE);
        mPullableLayout.setVisibility(View.GONE);
        mTabLayout.animate().translationYBy(height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mTabLayout.setVisibility(View.VISIBLE);
                mTabLayout.setTranslationY(0);
            }
        }).start();
    }

    private void openGoodsActivity(String goodId) {
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodId);
        startActivity(intent);
    }

    private void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        intent.setClass(getActivity(), ShopActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if(mPosition.equals("")){
            addRequest();
        }else{
            refreshRequest(Integer.valueOf(mPosition));
        }

    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }


    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager
            .SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            if(mAttrAdapter.mData.get(position).singleLine) {
                return 3;
            }

            return 1;
        }
    };
}
