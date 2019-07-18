package com.lyzb.jbx.fragment.me.hot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.access.HotAccessDetailAdapter;
import com.lyzb.jbx.dialog.InterRemindDialog;
import com.lyzb.jbx.mvp.presenter.me.HotAccessDetailPresenter;
import com.lyzb.jbx.mvp.view.me.IHotAccessDetailView;
import com.lyzb.jbx.util.AppPreference;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 热文分析下的单个明细详情
 */
public class HotAccessDetailFragment extends BaseToolbarFragment<HotAccessDetailPresenter> implements IHotAccessDetailView,
        View.OnClickListener,
        OnRefreshLoadMoreListener {

    private ImageView img_acs_head;
    private ImageView img_acs_vip;
    private TextView tv_acs_name;
    private TextView tv_inter_account;
    private TextView tv_acs_text;
    private ImageView img_acs_wx;
    private ImageView img_acs_phone;
    private SmartRefreshLayout sf_un_me_access;
    private RecyclerView recy_un_me_access;

    private HotAccessDetailAdapter mAdapter;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();

        img_acs_head = findViewById(R.id.img_acs_head);
        img_acs_vip = findViewById(R.id.img_acs_vip);
        tv_acs_name = findViewById(R.id.tv_acs_name);
        tv_inter_account = findViewById(R.id.tv_inter_account);
        tv_acs_text = findViewById(R.id.tv_acs_text);
        img_acs_wx = findViewById(R.id.img_acs_wx);
        img_acs_phone = findViewById(R.id.img_acs_phone);
        sf_un_me_access = findViewById(R.id.sf_un_me_access);
        recy_un_me_access = findViewById(R.id.recy_un_me_access);

        img_acs_head.setOnClickListener(this);
        tv_inter_account.setOnClickListener(this);
        img_acs_wx.setOnClickListener(this);
        img_acs_phone.setOnClickListener(this);
        sf_un_me_access.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new HotAccessDetailAdapter(getContext(), null);
        mAdapter.setLayoutManager(recy_un_me_access);
        recy_un_me_access.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recy_un_me_access.setAdapter(mAdapter);

        mPresenter.getList(true, "");
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_hot_access_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像
            case R.id.img_acs_head:
                break;
            //设置为意向客户
            case R.id.tv_inter_account:
                if (true) {
                    mPresenter.settingInterAccount("");
                }
                break;
            //IM聊天
            case R.id.img_acs_wx:
//                Intent intent = new Intent(getContext(), ImCommonActivity.class);
//                ImHeaderGoodsModel model = new ImHeaderGoodsModel();
//
//                model.setChatType(EaseConstant.CHATTYPE_SINGLE);
//                model.setShopImName("jbx" + mBean.getUserId());
//                model.setShopName(mBean.getGaName());
//                model.setShopHeadimg(mBean.getHeadimg());
//                model.setShopId("");
//
//                Bundle args = new Bundle();
//                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
//                intent.putExtras(args);
//                startActivity(intent);
                break;
            //拨打电话
            case R.id.img_acs_phone:
//                if (!TextUtils.isEmpty(mBean.getMobile())) {
//                    AppUtil.openDial(getContext(), mBean.getMobile());
//                } else {
//                    showToast("未设置电话号码,不可以拨打");
//                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSettingInterAccountResult() {
        if (AppPreference.getIntance().getUserShowInterAccount()) {
            new InterRemindDialog()
                    .show(getFragmentManager(), "showInterHint");
        }
    }

    @Override
    public void finshRefreshOrLoadMore(boolean isRefresh) {
        if (isRefresh) {
            sf_un_me_access.finishRefresh();
        } else {
            sf_un_me_access.finishLoadMore();
        }
    }

    @Override
    public void onListResult(boolean isRefresh, List<String> list) {
        if (isRefresh) {
            if (list.size() < 10) {
                sf_un_me_access.finishLoadMoreWithNoMoreData();
            }
            sf_un_me_access.finishRefresh();
            mAdapter.update(list);
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                sf_un_me_access.finishLoadMoreWithNoMoreData();
            } else {
                sf_un_me_access.finishLoadMore();
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, "");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, "");
    }
}
