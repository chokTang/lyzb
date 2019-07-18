package com.lyzb.jbx.fragment.me.company;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.company.CompanyMembersAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.CompanyMembersModel;
import com.lyzb.jbx.model.params.CompanyAccountBody;
import com.lyzb.jbx.model.params.RemoveMembersBody;
import com.lyzb.jbx.mvp.presenter.me.company.CompanyMembersPersenter;
import com.lyzb.jbx.mvp.view.me.ICompanyMembersView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Util.App;

public class CompanyMembersFragment extends BaseToolbarFragment<CompanyMembersPersenter> implements ICompanyMembersView, OnRefreshLoadMoreListener {
    private static final String PARAMS_ID = "mOrganId";
    private static final String ACCOUNT_TYPE = "accountType";
    public static final int EDIT_CODE = 1011;//
    private String mCompanyId = "";
    private int accountType = 0;

    private String userId;


    SmartRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    View empty_view;
    TextView btnDelete;


    private CompanyMembersAdapter mCompanyMembersAdapter = null;

    public static CompanyMembersFragment newIntance(String companyId, int accountType) {
        CompanyMembersFragment fragment = new CompanyMembersFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, companyId);
        args.putInt(ACCOUNT_TYPE, accountType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            accountType = args.getInt(ACCOUNT_TYPE, 0);
            mCompanyId = args.getString(PARAMS_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("企业成员");
        mRefreshLayout = findViewById(R.id.sf_company_memlist);
        mRecyclerView = findViewById(R.id.recy_company_memlist);
        empty_view = findViewById(R.id.empty_view);

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogFragment.newIntance()
                        .setCancleable(false)
                        .setContent(accountType == 1 ? "确认要退出该企业吗?" : "确认要退出企业并解散企业吗?")
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RemoveMembersBody removeMembersBody = new RemoveMembersBody();
                                removeMembersBody.setUserId(userId);
                                removeMembersBody.setRemoveType(2);//1.移除成员 2.退出企业
                                removeMembersBody.setCompanyId(mCompanyId);
                                removeMembersBody(removeMembersBody, -1);
                            }
                        })
                        .show(getFragmentManager(), "ExitTag");


            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, final int position) {
                final CompanyMembersModel.DataBean.ListBean bean = (CompanyMembersModel.DataBean.ListBean) adapter.getPositionModel(position);
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.tv_item_com_merber_remove:

                        AlertDialogFragment.newIntance()
                                .setCancleable(false)
                                .setContent("确定要移除该成员吗?")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RemoveMembersBody removeMembersBody = new RemoveMembersBody();
                                        removeMembersBody.setUserId(bean.getUserId());
                                        removeMembersBody.setCompanyId(bean.getCompanyId());
                                        removeMembersBody.setRemoveType(1);
                                        removeMembersBody(removeMembersBody, position);
                                    }
                                })
                                .show(getFragmentManager(), "RemoveTag");

                        break;
                    case R.id.img_item_com_merber_head:
                        if (App.getInstance().userId.equals(bean.getUserId())) {
                            start(CardFragment.newIntance(1, bean.getUserId()));
                        } else {
                            start(CardFragment.newIntance(2, bean.getUserId()));
                        }

                        break;
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                CompanyMembersModel.DataBean.ListBean bean = (CompanyMembersModel.DataBean.ListBean) adapter.getPositionModel(position);

                if (mCompanyMembersAdapter.getAccountType() == 2 && bean.getDataType() != 0) {//团购企业帐号
                    startForResult(EditCompanyAccountsFragment.newIntance(bean.getCompanyId(), bean.getUserId(), position), EDIT_CODE);
                } else {
                    if (App.getInstance().userId.equals(bean.getUserId())) {
                        start(CardFragment.newIntance(1, bean.getUserId()));
                    } else {
                        start(CardFragment.newIntance(2, bean.getUserId()));
                    }
                }
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        if (accountType == 1) {//1显示“退出企业”按钮条件 (1.是 其它.否)
            btnDelete.setText("退出企业");
        } else {
            btnDelete.setText("退出企业并解散企业");
        }
        btnDelete.setVisibility(accountType == 1 ? View.VISIBLE : View.GONE);//1显示“退出企业”按钮条件 (1.是 其它.否)
//        btnDelete.setVisibility(accountType < 2 ? View.VISIBLE : View.GONE);//小于2显示按钮条件 (1.是 其它.否)
    }

    private void removeMembersBody(RemoveMembersBody removeMembersBody, int position) {
        mPresenter.onRemove(removeMembersBody, position);//移除人员
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mCompanyMembersAdapter = new CompanyMembersAdapter(getContext(), null);
        mCompanyMembersAdapter.setLayoutManager(mRecyclerView);
        //     mRecyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mCompanyMembersAdapter);
        mPresenter.getList(true, mCompanyId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_company_members;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case EDIT_CODE:
                CompanyAccountBody body = (CompanyAccountBody) data.getSerializable("CompanyMembersBody");
                CompanyMembersModel.DataBean.ListBean bean = mCompanyMembersAdapter.getPositionModel(data.getInt("position"));
                bean.setAccountSt(body.getStatus());
                bean.setPosition(body.getPosition());
                mCompanyMembersAdapter.change(data.getInt("position"), bean);
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mCompanyId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mCompanyId);
    }


    @Override
    public void onList(boolean isRefresh, CompanyMembersModel model) {
        userId = model.getUserId();
        mCompanyMembersAdapter.setAccountType(model.getShowBtnCon());// 能否移除成员权限(1.否 2.是)
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
            if (model.getData().getList().size() < 15) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            mCompanyMembersAdapter.update(model.getData().getList());
        } else {
            if (model.getData().getList().size() < 15) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
            mCompanyMembersAdapter.addAll(model.getData().getList());
        }
        if (mCompanyMembersAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefrsh) {
        if (isRefrsh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onRemove(int position) {
        if (position == -1) {//退出企业
            popTo(CompanySearchFragment.class, false);
        } else {//删除成员

            mCompanyMembersAdapter.remove(position);
            if (mCompanyMembersAdapter.getItemCount() == 0) {
                empty_view.setVisibility(View.VISIBLE);
            } else {
                empty_view.setVisibility(View.GONE);
            }

        }
    }
}
