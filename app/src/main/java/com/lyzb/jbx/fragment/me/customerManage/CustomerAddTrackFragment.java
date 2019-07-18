package com.lyzb.jbx.fragment.me.customerManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerAddTrackPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerAddTrackView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 添加跟进
 * @time 2019 2019/5/9 15:08
 */

public class CustomerAddTrackFragment extends BaseFragment<CustomerAddTrackPresenter>
        implements ICustomerAddTrackView {
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.customer_manage_head_iv)
    ImageView mCustomerManageHeadIv;
    @BindView(R.id.customer_manage_isvip_iv)
    ImageView mCustomerManageIsvipIv;
    @BindView(R.id.customer_manage_name_tv)
    TextView mCustomerManageNameTv;
    @BindView(R.id.customer_manage_company_tv)
    TextView mCustomerManageCompanyTv;
    @BindView(R.id.customer_manage_content_tv)
    TextView mCustomerManageContentTv;
    @BindView(R.id.customer_manage_addtrack_tv)
    TextView mCustomerManageAddtrackTv;
    @BindView(R.id.customer_addtrack_tracknumber_tv)
    TextView mCustomerAddtrackTracknumberTv;
    @BindView(R.id.customer_addtrack_edit)
    EditText mCustomerAddtrackEdit;
    Unbinder unbinder;

    private CustomerInfoModel mCustomerInfoModel;

    public static CustomerAddTrackFragment newIntance(CustomerInfoModel b) {
        CustomerAddTrackFragment fragment = new CustomerAddTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomerManageFragment.INTENTKEY_CUSTOMERINFO, b);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsTitleTv.setText("添加跟进");
        mCustomerManageAddtrackTv.setVisibility(View.GONE);
        if (mCustomerInfoModel != null) {
            //头像
            LoadImageUtil.loadRoundImage(mCustomerManageHeadIv, mCustomerInfoModel.getHeadimg(), 4);
            //姓名
            mCustomerManageNameTv.setText(mCustomerInfoModel.getRemarkName());
            //公司
            mCustomerManageCompanyTv.setText(mCustomerInfoModel.getCompany());
            //看了多少次-名片、商品、动态
            String content = _mActivity.getString(R.string.customer_content, mCustomerInfoModel.getBrowseNum(), mCustomerInfoModel.getGoodsNum(), mCustomerInfoModel.getTopicNum());
            mCustomerManageContentTv.setText(content);
            //跟进次数
            mCustomerAddtrackTracknumberTv.setText(String.format("跟进%d次", mCustomerInfoModel.getCustomersFollowNum()));
            //是否为vip
            if (mCustomerInfoModel.getUserActionVos() == null || mCustomerInfoModel.getUserActionVos().size() < 1) {
                mCustomerManageIsvipIv.setVisibility(View.INVISIBLE);
            } else {
                mCustomerManageIsvipIv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_customer_addtrack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCustomerInfoModel = (CustomerInfoModel) bundle.getSerializable(CustomerManageFragment.INTENTKEY_CUSTOMERINFO);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.statistics_back_iv, R.id.customer_addtrack_keep_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.statistics_back_iv:
                pop();
                break;
            //保存
            case R.id.customer_addtrack_keep_tv:
                if (mCustomerAddtrackEdit.getText().length() < 1) {
                    showToast("请填写跟进记录");
                    return;
                }
                if (mCustomerInfoModel != null) {
                    mPresenter.addTrack(mCustomerInfoModel.getId(), mCustomerAddtrackEdit.getText().toString());
                }
                break;
            default:
        }
    }

    @Override
    public void onSuccess() {
        pop();
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }
}
