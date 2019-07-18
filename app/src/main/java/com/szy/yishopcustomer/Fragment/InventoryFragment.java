package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Adapter.GoodsListAdapter;
import com.szy.yishopcustomer.Adapter.InventoryGoodsListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Macro;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.InventoryCategory.ResponseInventoryCategoryModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 宗仁 on 2016/6/20 0020.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InventoryFragment extends ShopGoodsListFragment {
    private static final int HTTP_WHAT_CATEGORY = 4;

    @Override
    public void onEmptyViewClicked() {
        if (!App.getInstance().isLogin()) {
            openLoginActivity();
        } else {
            goToIndexTab();
        }
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                init();
                refresh();
                break;
            case EVENT_LOGOUT:
                init();
                break;
            case EVENT_REFRESH_INVENTORY:
                refresh();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_CATEGORY:
                getCategorySucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mGoodsListAdapter != null) {
            mListStyle = Macro.STYLE_LIST;
            mGoodsListAdapter.style = mListStyle;
        }
    }

    @Override
    protected String getCategoryParameterName() {
        return "cid";
    }

    @Override
    protected String getPageParameterName() {
        return "page[cur_page]";
    }

    @Override
    protected void init() {
        if (App.getInstance().isLogin()) {
            if (mModel == null || mModel.data == null || mModel.data.category == null || mModel
                    .data.category.size() == 0 || mModel.data.list == null || mModel.data.list
                    .size() == 0) {
                mCategoryListRecyclerView.setVisibility(View.GONE);
                mGoodsListRecyclerView.setEmptyImage(R.mipmap.bg_public);
                mGoodsListRecyclerView.setEmptySubtitle(R.string.checkGoodsYouMightLike);
                mGoodsListRecyclerView.setEmptyTitle(R.string.emptyInventory);
                mGoodsListRecyclerView.setEmptyButton(R.string.goShopping);
//                mGoodsListRecyclerView.showEmptyView();
            } else {
                mCategoryListRecyclerView.setVisibility(View.VISIBLE);
                mGoodsListRecyclerView.hideEmptyView();
            }
        } else {
            mCategoryListRecyclerView.setVisibility(View.GONE);
            if (mCategoryListAdapter != null) {
                mCategoryListAdapter.data.clear();
                mCategoryListAdapter.notifyDataSetChanged();
            }
            if (mGoodsListAdapter != null) {
                mGoodsListAdapter.data.clear();
                mGoodsListAdapter.notifyDataSetChanged();
            }
            mGoodsListRecyclerView.setEmptyImage(R.mipmap.bg_public);
            mGoodsListRecyclerView.setEmptySubtitle(0);
            mGoodsListRecyclerView.setEmptyTitle(R.string.loginBeforeCheckInventory);
            mGoodsListRecyclerView.setEmptyButton(R.string.loginNow);
            mGoodsListRecyclerView.showEmptyView();
        }
    }

    @Override
    protected boolean isCategoryRequestedSeparately() {
        return true;
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected void refresh() {
        getCategory();
        getGoodsList();

    }

    private void getCategory() {
        CommonRequest request = new CommonRequest(Api.API_INVENTORY_CATEGORY, HTTP_WHAT_CATEGORY);
        addRequest(request);
    }

    private void getCategorySucceed(String response) {
        HttpResultManager.resolve(response,
                ResponseInventoryCategoryModel.class, new HttpResultManager.HttpResultCallBack<ResponseInventoryCategoryModel>() {
                    @Override
                    public void onSuccess(ResponseInventoryCategoryModel model) {
                        getCategoryCallback(model.data.cat_list,"2");
                    }
                },true);
        init();
    }

    private void goToIndexTab() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    private void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected GoodsListAdapter createGoodsListAdapter() {
        return new InventoryGoodsListAdapter(getContext());
    }
}
