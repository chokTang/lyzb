package com.lyzb.jbx.fragment.me.company;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.account.SearchCompanyResultAdapter;
import com.lyzb.jbx.model.me.CompanyModel;
import com.lyzb.jbx.model.me.SearchResultCompanyModel;
import com.lyzb.jbx.model.me.SetComdModel;
import com.lyzb.jbx.mvp.presenter.me.CompanyPresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我的企业 列表
 * @time 2019 2019/3/8 16:51
 */

public class CompanySearchFragment extends BaseFragment<CompanyPresenter> implements ICompanyView {

    SearchCompanyResultAdapter resultAdapter;

    @BindView(R.id.edt_search_value)
    ClearEditText edt_search_value;
    @BindView(R.id.img_search)
    ImageView img_search;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    private View empty_view;

    @BindView(R.id.rv_search_result)
    RecyclerView rv_search_result;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        empty_view = findViewById(R.id.empty_view);
        TextView title = findViewById(R.id.statistics_title_tv);
        title.setText("更多企业");
        findViewById(R.id.statistics_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        resultAdapter = new SearchCompanyResultAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv_search_result.setLayoutManager(manager);
        rv_search_result.setAdapter(resultAdapter);

        //搜索按钮点击事件
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCompanyList(edt_search_value.getText().toString().trim());
            }
        });

        edt_search_value.setOnTextChangeListener(new ClearEditText.onTextChangeListener() {
            @Override
            public void onTextChange(String content) {
                if (TextUtils.isEmpty(content)) {
                    resultAdapter.setNewData(null);
                } else {
                    mPresenter.getCompanyList(edt_search_value.getText().toString().trim());
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        resultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                hintKeyBoard();
                SearchResultCompanyModel bean = (SearchResultCompanyModel) adapter.getData().get(position);
                switch (bean.getApplyState()) {
                    case 2://已经加入
                        start(OrganDetailFragment.Companion.newIntance(bean.getId()));
                        break;
                    default://未加入
                        start(CompanyWebFragment.Companion.newInstance(bean.getId(), bean.getGroupId(), bean.getCompanyName(),-1));
                        break;
                }
            }
        });

        resultAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                hintKeyBoard();
                switch (view.getId()) {
                    case R.id.tv_jion:
                        AlertDialogFragment.newIntance()
                                .setContent("是否加入该企业?")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.joinConpany(resultAdapter.getItem(position).getId());
                                    }
                                })
                                .show(getFragmentManager(), "addCompanyTag");
                        break;
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_company;
    }

    @Override
    public void onSet(CompanyModel.ListBean listBean) {
        if (listBean.getIsMy() == 1) {//1自己的企业  2不是自己的企业
            App.getInstance().isMeComd = true;
        } else {
            App.getInstance().isMeComd = false;
        }
    }

    @Override
    public void onQueryList(List<SearchResultCompanyModel> resultlist) {
        if (resultlist != null && resultlist.size() > 0) {
            resultAdapter.setNewData(resultlist);
        }
    }

    @Override
    public void joinSuccess() {
        mPresenter.getCompanyList(edt_search_value.getText().toString().trim());
    }

    @Override
    public void joinFail() {
        resultAdapter.setNewData(null);
    }

    @Override
    public void onList(boolean isRefresh, CompanyModel model) {
        ll_search.setVisibility(View.VISIBLE);
        List<CompanyModel.ListBean> list = model.getList();

        if (resultAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
