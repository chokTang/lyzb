package com.lyzb.jbx.fragment.me.customerManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.like.utilslib.app.AppUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.eventbus.CustomerDeleteEventBus;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerDetailsPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerDetailsView;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author wyx
 * @role 客户详情
 * @time 2019 2019/5/7 10:18
 */

public class CustomerDetailsFragment extends BaseFragment<CustomerDetailsPresenter>
        implements ICustomerDetailsView, TabLayout.OnTabSelectedListener {
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
    @BindView(R.id.customer_manage_delete_tv)
    TextView mCustomerManageDeleteTv;
    @BindView(R.id.customer_details_tab)
    AutoWidthTabLayout mCustomerManageTab;
    @BindView(R.id.customer_details_track_number_tv)
    TextView mCustomerDetailsTrackNumberTv;

    Unbinder unbinder;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private int mCurrentIndex = 0;
    private boolean isFirst = true;
    /**
     * 这是那啥主键id，不是用户id也不是名片id，不要弄错了
     */
    private String mId;
    private String mUserName;
    private CustomerInfoModel mCustomerInfoModel;

    public static CustomerDetailsFragment newIntance(String id, String userName) {
        CustomerDetailsFragment fragment = new CustomerDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CustomerManageFragment.INTENTKEY_ID, id);
        bundle.putString(CustomerManageFragment.INTENTKEY_NAME, userName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        //隐藏添加客户
        mCustomerManageAddtrackTv.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mUserName)) {
            mStatisticsTitleTv.setText("客户详情");
            //显示删除客户
            mCustomerManageDeleteTv.setVisibility(View.VISIBLE);
        } else {
            mStatisticsTitleTv.setText(mUserName + "的客户详情");
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getCustomerInfo(mId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_customer_details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getString(CustomerManageFragment.INTENTKEY_ID);
            mUserName = bundle.getString(CustomerManageFragment.INTENTKEY_NAME);
        }
        return rootView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isFirst) {
            mPresenter.getCustomerInfo(mId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.statistics_back_iv, R.id.customer_manage_head_iv,
            R.id.customer_details_sendmessage_ll, R.id.customer_details_callphone_ll,
            R.id.customer_details_details_ll, R.id.customer_manage_delete_tv})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.statistics_back_iv) {
            pop();
            return;
        }
        if (mCustomerInfoModel == null) {
            return;
        }
        switch (view.getId()) {
            //头像、TA的名片跳转名片页
            case R.id.customer_manage_head_iv:
                start(CardFragment.newIntance(2, mCustomerInfoModel.getCustomerUserId()));
                break;
            //发消息
            case R.id.customer_details_sendmessage_ll:
                if (mCustomerInfoModel == null) {
                    return;
                }

                Intent intent = new Intent(getContext(), ImCommonActivity.class);
                ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                model.setShopImName("jbx" + mCustomerInfoModel.getCustomerUserId());
                model.setShopName(mCustomerInfoModel.getRemarkName());
                model.setShopHeadimg(mCustomerInfoModel.getHeadimg());
                model.setShopId("");

                Bundle args = new Bundle();
                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                intent.putExtras(args);
                startActivity(intent);
                break;
            //打电话
            case R.id.customer_details_callphone_ll:
                if (!TextUtils.isEmpty(mCustomerInfoModel.getMobile())) {
                    AppUtil.openDial(getContext(), mCustomerInfoModel.getMobile());
                }
                break;
            //详细资料
            case R.id.customer_details_details_ll:
                start(CustomerInfoFramgner.newIntance(mCustomerInfoModel));
                break;
            //删除客户
            case R.id.customer_manage_delete_tv:
                AlertDialogFragment.newIntance()
                        .setKeyBackable(false)
                        .setCancleable(false)
                        .setContent("删除客户后所有跟进记录会被删除，确认要删除该客户吗？")
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除
                                mPresenter.deleteCustomer(mId);
                            }
                        })
                        .show(getFragmentManager(), "LogOutTag");
                break;
            default:
        }
    }

    @Override
    public void onData(CustomerInfoModel b) {
        mCustomerInfoModel = b;
        //设置一下是否为管理员，传来传去麻烦
        mCustomerInfoModel.setAdmin(!TextUtils.isEmpty(mUserName));
        //头像
        LoadImageUtil.loadRoundImage(mCustomerManageHeadIv, b.getHeadimg(), 4);
        //姓名
        mCustomerManageNameTv.setText(b.getRemarkName());
        //公司
        mCustomerManageCompanyTv.setText(b.getCompany());
        //看了多少次-名片、商品、动态
        String content = _mActivity.getString(R.string.customer_content, b.getBrowseNum(), b.getGoodsNum(), b.getTopicNum());
        mCustomerManageContentTv.setText(content);
        //跟进次数
        mCustomerDetailsTrackNumberTv.setText(String.format("跟进%d次", b.getCustomersFollowNum()));
        //是否为vip
        if (mCustomerInfoModel.getUserActionVos() == null || mCustomerInfoModel.getUserActionVos().size() < 1) {
            mCustomerManageIsvipIv.setVisibility(View.INVISIBLE);
        } else {
            mCustomerManageIsvipIv.setVisibility(View.VISIBLE);
        }
        if (isFirst) {
            isFirst = false;
            mCustomerManageTab.addTab("跟进记录");
            mCustomerManageTab.addTab("访问记录");
            mCustomerManageTab.addOnTabSelectedListener(this);
            mCustomerManageTab.getTabLayout().getTabAt(mCurrentIndex).select();
            mFragments.add(CustomerTrackRecordFragment.newIntance(mCustomerInfoModel));
            mFragments.add(CustomerVisitRecordFragment.newIntance(mCustomerInfoModel.getCustomerUserId()));
            loadMultipleRootFragment(R.id.customer_details_fl, mCurrentIndex, mFragments.toArray(new ISupportFragment[2]));
        }
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onDeteleSuccess() {
        EventBus.getDefault().post(new CustomerDeleteEventBus());
        pop();
    }

    @Override
    public void onDeteleFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        chanageFragment(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void chanageFragment(int postion) {
        if (postion >= mFragments.size()) {
            return;
        }
        showHideFragment(mFragments.get(postion), mFragments.get(mCurrentIndex));
        mCurrentIndex = postion;
    }
}
