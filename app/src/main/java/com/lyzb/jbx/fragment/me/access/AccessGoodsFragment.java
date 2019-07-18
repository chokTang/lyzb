package com.lyzb.jbx.fragment.me.access;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.access.AccessGoodsAdapter;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessGoodsDetailModel;
import com.lyzb.jbx.mvp.presenter.me.AccessGoodsPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessGoodsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Util.App;

/**
 * 名片商城交易记录
 *
 * @author shidengzhong
 */
public class AccessGoodsFragment extends BaseToolbarFragment<AccessGoodsPresenter> implements IAccessGoodsView, OnRefreshLoadMoreListener {

    private String mUserId = "";
    private String mDayType = DayEnum.DAY_THIRTY.getValue();
    private String mUserName = "";
    private static final String PARAMS_ID = "params_id";
    private static final String PARAMS_NAME = "params_name";
    private static final String PARAMS_DAY = "params_day";

    private TextView tv_total_price;
    private SmartRefreshLayout refresh_goods;
    private RecyclerView recycle_goods;
    private View empty_view;

    private AccessGoodsAdapter mAdapter;

    public static AccessGoodsFragment newIntance(String userId, String userName, String dayType) {
        AccessGoodsFragment fragment = new AccessGoodsFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, userId);
        args.putString(PARAMS_DAY, dayType);
        args.putString(PARAMS_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserId = args.getString(PARAMS_ID);
            mDayType = args.getString(PARAMS_DAY);
            mUserName = args.getString(PARAMS_NAME);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        if (TextUtils.isEmpty(mUserId) || mUserId.equals(App.getInstance().userId)) {
            setToolbarTitle("名片商城交易记录");
        } else {
            setToolbarTitle(String.format("%s的名片商城交易记录", mUserName));
        }

        tv_total_price = findViewById(R.id.tv_total_price);
        refresh_goods = findViewById(R.id.refresh_goods);
        recycle_goods = findViewById(R.id.recycle_goods);
        empty_view = findViewById(R.id.empty_view);

        refresh_goods.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new AccessGoodsAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_goods);
        recycle_goods.setAdapter(mAdapter);

        //初始化加载无数据页面
        ClassicsFooter.REFRESH_FOOTER_NOTHING = String.format("以上是%s全部交易记录", mDayType);

        mPresenter.getGoodsList(true, mUserId, mDayType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_access_goods;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getGoodsList(false, mUserId, mDayType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getGoodsList(true, mUserId, mDayType);
    }

    @Override
    public void onGoodsListResult(boolean isRefresh, AccessGoodsDetailModel model) {
        if (model == null)
            return;
        mAdapter.update(model.getOrderByDay());
        tv_total_price.setText(String.format("%s交易%d笔，交易金额%.2f", mDayType, model.getOrderCount(), model.getOrderAmount()));
        refresh_goods.finishLoadMoreWithNoMoreData();
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
//        if (isRefresh) {
//            refresh_goods.finishRefresh();
//            mAdapter.update(list);
//            if (list.size() < 10) {
//                refresh_goods.finishLoadMoreWithNoMoreData();
//            }
//        } else {
//            mAdapter.addAll(list);
//            if (list.size() < 10) {
//                refresh_goods.finishLoadMoreWithNoMoreData();
//            } else {
//                refresh_goods.finishLoadMore();
//            }
//        }
//        if (mAdapter.getItemCount() == 0) {
//            empty_view.setVisibility(View.VISIBLE);
//        } else {
//            empty_view.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "已显示全部";
    }
}
