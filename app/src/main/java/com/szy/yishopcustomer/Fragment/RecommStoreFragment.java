package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ApplyRecommShopActivity;
import com.szy.yishopcustomer.Adapter.RecommStoreAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.RecommStoreModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommStoreFragment extends YSCBaseFragment implements OnPullListener{

    private static final int HTTP_WHAT_RECOMM_STORE_LIST = 1;
    public static final int REQUEST_APPLY_RECOMM_STORE = 10001;


    @BindView(R.id.fragment_recomm_stroe_listView_layout)
    PullableLayout fragment_recomm_stroe_listView_layout;
    @BindView(R.id.fragment_recomm_stroe_listView)
    CommonRecyclerView fragment_recomm_stroe_listView;

    private RecommStoreAdapter mAdapter;
    private RecommStoreModel mStoreModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_recomm_store;

        mAdapter = new RecommStoreAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_recomm_stroe_listView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recomm_stroe_listView.setLayoutManager(layoutManager);

        fragment_recomm_stroe_listView_layout.topComponent.setOnPullListener(this);
        fragment_recomm_stroe_listView.setEmptyViewClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onEmptyViewClicked() {
        recommStore();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_RECOMMEND_SHOP, HTTP_WHAT_RECOMM_STORE_LIST);
//        request.add("shop_id", mShopId);
        addRequest(request);
    }


    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            refresh();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_RECOMM_STORE_LIST:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    @Override
    protected void onRequestFailed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_RECOMM_STORE_LIST:
                refreshFailed();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }


    /**
     * 推荐店铺
     */
    public void recommStore(){
        Intent intent = new Intent(getActivity(), ApplyRecommShopActivity.class);
        startActivityForResult(intent,REQUEST_APPLY_RECOMM_STORE);
    }


    //设置数据
    private void refreshSucceed(String response){
        fragment_recomm_stroe_listView_layout.topComponent.finish(Result.SUCCEED);
        mStoreModel = JSON.parseObject(response, RecommStoreModel.class);

        HttpResultManager.resolve(response, RecommStoreModel.class, new HttpResultManager.HttpResultCallBack<RecommStoreModel>() {
            @Override
            public void onSuccess(RecommStoreModel back) {
                mAdapter.status = mStoreModel.getData().getStatus();
                mAdapter.data.clear();

                if(mStoreModel.getData().getList().size() > 0) {
                    fragment_recomm_stroe_listView.hideEmptyView();
                    mAdapter.data.addAll(mStoreModel.getData().getList());
                } else {
                    fragment_recomm_stroe_listView.showEmptyView();
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEmptyData(int state) {
                fragment_recomm_stroe_listView.showEmptyView();
            }

            @Override
            public void onFailure(String message) {
                fragment_recomm_stroe_listView.showEmptyView();
            }
        });
    }


    private void refreshFailed() {
        fragment_recomm_stroe_listView_layout.topComponent.finish(Result.FAILED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case RecommStoreFragment.REQUEST_APPLY_RECOMM_STORE:
                if(resultCode == getActivity().RESULT_OK) {
                    refresh();
                }
                break;
        }
    }

}
