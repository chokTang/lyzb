package com.lyzb.jbx.fragment.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.CircleMallAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.mvp.presenter.me.company.CompanyMallPresenter;
import com.lyzb.jbx.mvp.view.me.ICdMallView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Constant.Key;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/16 9:16
 */

public class CircleMallFragment extends BaseFragment<CompanyMallPresenter>
        implements BaseQuickAdapter.RequestLoadMoreListener, ICdMallView {

    private final static String INTENTKEY_COMPANY = "intentkey_company";

    @BindView(R.id.circle_mall_recyclerview)
    RecyclerView mCircleMallRecyclerview;
    @BindView(R.id.empty_tv)
    TextView mEmptyTv;
    @BindView(R.id.circle_mall_notdata)
    View mCircleMallNotdata;
    Unbinder unbinder;

    private CircleMallAdapter mMallAdapter = null;
    private String mCompanyId;

    public static CircleMallFragment newIntance(String mCompanyId) {
        CircleMallFragment fragment = new CircleMallFragment();
        Bundle args = new Bundle();
        args.putString(INTENTKEY_COMPANY, mCompanyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCompanyId = args.getString(INTENTKEY_COMPANY);
            mPresenter.getList(true, mCompanyId);
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_circle_mall;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

        mMallAdapter = new CircleMallAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mCircleMallRecyclerview.setLayoutManager(layoutManager);

        mCircleMallRecyclerview.setNestedScrollingEnabled(false);
        mCircleMallRecyclerview.setFocusable(false);
        mCircleMallRecyclerview.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_winbg_10));
        mCircleMallRecyclerview.setAdapter(mMallAdapter);
        mMallAdapter.setOnLoadMoreListener(this, mCircleMallRecyclerview);
        mMallAdapter.setPreLoadNumber(mMallAdapter.getData().size() - 2);
        mMallAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_good_img:
                    case R.id.tv_un_me_card_good_name:
                        CardMallModel.ListBean gd = (CardMallModel.ListBean) adapter.getItem(position);

                        if (gd.getCan_buy() == 1) {//能买
                            //跳转商品详情页面
                            Intent mIntent = new Intent();
                            mIntent.setClass(getActivity(), GoodsActivity.class);
                            mIntent.putExtra(Key.KEY_GOODS_ID.getValue(), gd.getGoods_id());
                            startActivity(mIntent);
                        } else {
                            Intent pfIntent = new Intent(getContext(), YSCWebViewActivity.class);
                            pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.GOODS_URL + gd.getGoods_id());
                            startActivity(pfIntent);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDelete(int position) {

    }

    @Override
    public void onGoodList(boolean isRefresh, CardMallModel model) {
        if (isRefresh) {
            mMallAdapter.setNewData(model.getList());
        } else {
            mMallAdapter.addData(model.getList());
        }
        if (model.getList().size() < 10) {
            mMallAdapter.loadMoreEnd();
        } else {
            mMallAdapter.loadMoreComplete();
        }
        if (mMallAdapter.getItemCount() == 0) {//没有商品
            mEmptyTv.setText("暂无数据");
            mCircleMallNotdata.setVisibility(View.VISIBLE);
        } else {//有商品
            mCircleMallNotdata.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefresh) {
        mMallAdapter.loadMoreEnd();
        if (mMallAdapter.getItemCount() == 0) {
            mEmptyTv.setText("暂无数据");
            mCircleMallNotdata.setVisibility(View.VISIBLE);
        } else {
            mCircleMallNotdata.setVisibility(View.GONE);
            mCircleMallRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getList(false, mCompanyId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}