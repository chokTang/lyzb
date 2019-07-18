package com.lyzb.jbx.fragment.me.customerManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.DialogTimeFragment;
import com.like.longshaolib.dialog.inter.IDialogTimeListener;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerInfoModifyPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerInfoModifyView;
import com.szy.common.Activity.RegionActivity;
import com.szy.common.Fragment.RegionFragment;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.RequestCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/9 15:23
 */

public class CustomerInfoFramgner extends BaseFragment<CustomerInfoModifyPresenter>
        implements ICustomerInfoModifyView {
    /**
     * title
     */
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    /**
     * 头像
     */
    @BindView(R.id.customer_manage_head_iv)
    ImageView mCustomerManageHeadIv;
    @BindView(R.id.customer_manage_isvip_iv)
    ImageView mCustomerManageIsvipIv;
    /**
     * 姓名
     */
    @BindView(R.id.customer_manage_name_tv)
    TextView mCustomerManageNameTv;
    /**
     * 公司
     */
    @BindView(R.id.customer_manage_company_tv)
    TextView mCustomerManageCompanyTv;
    /**
     * 查看次数
     */
    @BindView(R.id.customer_manage_content_tv)
    TextView mCustomerManageContentTv;
    /**
     * 添加跟进，没用，隐藏掉
     */
    @BindView(R.id.customer_manage_addtrack_tv)
    TextView mCustomerManageAddtrackLl;
    /**
     * 跟进次数
     */
    @BindView(R.id.customerinfo_track_number_tv)
    TextView mCustomerInfoTrackNumberTv;
    /**
     * 姓名
     */
    @BindView(R.id.customerinfo_name_edit)
    EditText mCustomerinfoNameEdit;
    /**
     * 性别-男
     */
    @BindView(R.id.customerinfo_man_rbtn)
    RadioButton mCustomerinfoManRbtn;
    /**
     * 性别-女
     */
    @BindView(R.id.customerinfo_female_rbtn)
    RadioButton mCustomerinfoFemaleRbtn;
    /**
     * 性别
     */
    @BindView(R.id.customerinfo_sex_tv)
    TextView mCustomerinfoSexTv;
    /**
     * 电话
     */
    @BindView(R.id.customerinfo_phone_edit)
    EditText mCustomerinfoPhoneEdit;
    /**
     * 微信
     */
    @BindView(R.id.customerinfo_wxnumber_edit)
    EditText mCustomerinfoWxnumberEdit;
    /**
     * 公司
     */
    @BindView(R.id.customerinfo_company_edit)
    EditText mCustomerinfoCompanyEdit;
    /**
     * 职位
     */
    @BindView(R.id.customerinfo_position_edit)
    EditText mCustomerinfoPositionEdit;
    /**
     * 地区
     */
    @BindView(R.id.customerinfo_region_tv)
    TextView mCustomerinfoRegionTv;
    /**
     * 地区-箭头
     */
    @BindView(R.id.customerinfo_region_iv)
    ImageView mCustomerinfoRegionIv;
    /**
     * 地址
     */
    @BindView(R.id.customerinfo_address_edit)
    EditText mCustomerinfoAddressEdit;
    /**
     * 毕业院校
     */
    @BindView(R.id.customerinfo_school_edit)
    EditText mCustomerinfoSchoolEdit;
    /**
     * 生日
     */
    @BindView(R.id.customerinfo_birthday_tv)
    TextView mCustomerinfoBirthdayTv;
    /**
     * 生日-箭头
     */
    @BindView(R.id.customerinfo_birthday_iv)
    ImageView mCustomerinfoBirthdayIv;
    /**
     * 备注
     */
    @BindView(R.id.customerinfo_remark_edit)
    EditText mCustomerinfoRemarkEdit;
    /**
     * 保存
     */
    @BindView(R.id.customerinfo_keep_tv)
    TextView mCustomerinfoKeepTv;
    Unbinder unbinder;
    private CustomerInfoModel mUserInfo;
    private CustomerInfoModel mBody;

    public static CustomerInfoFramgner newIntance(CustomerInfoModel b) {
        CustomerInfoFramgner fragment = new CustomerInfoFramgner();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomerManageFragment.INTENTKEY_CUSTOMERINFO, b);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsTitleTv.setText("详细资料");
        mCustomerManageAddtrackLl.setVisibility(View.GONE);
        if (mUserInfo != null) {
            LoadImageUtil.loadRoundImage(mCustomerManageHeadIv, mUserInfo.getHeadimg(), 4);
            mCustomerManageNameTv.setText(mUserInfo.getRemarkName());
            mCustomerManageCompanyTv.setText(mUserInfo.getCompany());
            //看了多少次-名片、商品、动态
            String content = _mActivity.getString(R.string.customer_content, mUserInfo.getBrowseNum(), mUserInfo.getGoodsNum(), mUserInfo.getTopicNum());
            mCustomerManageContentTv.setText(content);
            //跟进次数
            mCustomerInfoTrackNumberTv.setText(String.format("跟进%d次", mUserInfo.getCustomersFollowNum()));
            mCustomerinfoNameEdit.setText(mUserInfo.getRemarkName());
            //是否为vip
            if (mUserInfo.getUserActionVos() == null || mUserInfo.getUserActionVos().size() < 1) {
                mCustomerManageIsvipIv.setVisibility(View.INVISIBLE);
            } else {
                mCustomerManageIsvipIv.setVisibility(View.VISIBLE);
            }
            //性别
            if (mUserInfo.getSex() == 1) {
                mCustomerinfoManRbtn.setChecked(true);
                mCustomerinfoSexTv.setText("先生");
            } else if (mUserInfo.getSex() == 2) {
                mCustomerinfoFemaleRbtn.setChecked(true);
                mCustomerinfoSexTv.setText("女士");
            }
            //电话
            mCustomerinfoPhoneEdit.setText(mUserInfo.getMobile());
            //微信
            mCustomerinfoWxnumberEdit.setText(mUserInfo.getWxAccount());
            //公司
            mCustomerinfoCompanyEdit.setText(mUserInfo.getCompany());
            //职位
            mCustomerinfoPositionEdit.setText(mUserInfo.getPosition());
            //地区
            mCustomerinfoRegionTv.setText(mUserInfo.getResidenceName());
            //地址
            mCustomerinfoAddressEdit.setText(mUserInfo.getAddress());
            //学校
            mCustomerinfoSchoolEdit.setText(mUserInfo.getSchool());
            //生日
            if (!TextUtils.isEmpty(mUserInfo.getBirthday())) {
                mCustomerinfoBirthdayTv.setText(DateUtil.StringToString(mUserInfo.getBirthday(), DateStyle.YYYY_MM_DD));
            }
            //备注
            mCustomerinfoRemarkEdit.setText(mUserInfo.getRemark());
            //管理员查看时不允许编辑！！！
            if (mUserInfo.isAdmin()) {
                //隐藏性别的选择、地区选择的箭头、生日选择箭头、保存按钮
                mCustomerinfoManRbtn.setVisibility(View.GONE);
                mCustomerinfoFemaleRbtn.setVisibility(View.GONE);
                mCustomerinfoSexTv.setVisibility(View.VISIBLE);
                mCustomerinfoRegionIv.setVisibility(View.GONE);
                mCustomerinfoBirthdayIv.setVisibility(View.GONE);
                mCustomerinfoKeepTv.setVisibility(View.GONE);
                //所有客户信息的提示，全部换为未填写(自己看的时候为请填写)
                List<TextView> tvs = new ArrayList<>();
                tvs.add(mCustomerinfoNameEdit);
                tvs.add(mCustomerinfoSexTv);
                //电话
                tvs.add(mCustomerinfoPhoneEdit);
                //微信
                tvs.add(mCustomerinfoWxnumberEdit);
                //公司
                tvs.add(mCustomerinfoCompanyEdit);
                //职位
                tvs.add(mCustomerinfoPositionEdit);
                //地区
                tvs.add(mCustomerinfoRegionTv);
                //地址
                tvs.add(mCustomerinfoAddressEdit);
                //学校
                tvs.add(mCustomerinfoSchoolEdit);
                //生日
                tvs.add(mCustomerinfoBirthdayTv);
                //备注
                tvs.add(mCustomerinfoRemarkEdit);
                for (TextView tv : tvs) {
                    tv.setHint("未填写");
                    tv.setFocusable(false);
                    tv.setFocusableInTouchMode(false);
                }
            }
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_customerinfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserInfo = (CustomerInfoModel) bundle.getSerializable(CustomerManageFragment.INTENTKEY_CUSTOMERINFO);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.statistics_back_iv, R.id.customerinfo_man_rbtn, R.id.customerinfo_female_rbtn,
            R.id.customerinfo_region_tv, R.id.customerinfo_birthday_tv, R.id.customerinfo_keep_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.statistics_back_iv:
                pop();
                break;
            //地区
            case R.id.customerinfo_region_tv:
                if (mUserInfo.isAdmin()) {
                    return;
                }
                chooseCity("500112");
                break;
            //生日
            case R.id.customerinfo_birthday_tv:
                if (mUserInfo.isAdmin()) {
                    return;
                }
                pickRegion();
                break;
            //保存
            case R.id.customerinfo_keep_tv:
                if (mBody == null) {
                    mBody = new CustomerInfoModel(mUserInfo.getUserExtId());
                    mBody.setId(mUserInfo.getId());
                }
                if (mCustomerinfoManRbtn.isChecked()) {
                    mBody.setSex(1);
                } else if (mCustomerinfoFemaleRbtn.isChecked()) {
                    mBody.setSex(2);
                }
                //备注姓名
                mBody.setRemarkName(mCustomerinfoNameEdit.getText().toString());
                //电话
                mBody.setMobile(mCustomerinfoPhoneEdit.getText().toString());
                //微信
                mBody.setWxAccount(mCustomerinfoWxnumberEdit.getText().toString());
                //公司
                mBody.setCompany(mCustomerinfoCompanyEdit.getText().toString());
                //职位
                mBody.setPosition(mCustomerinfoPositionEdit.getText().toString());
                //地区
                mBody.setResidenceName(mCustomerinfoRegionTv.getText().toString());
                //地区id
                mBody.setResidence(regionCode);
                //地址
                mBody.setAddress(mCustomerinfoAddressEdit.getText().toString());
                //学校
                mBody.setSchool(mCustomerinfoSchoolEdit.getText().toString());
                //生日
                Date date = DateUtil.StringToDate(mCustomerinfoBirthdayTv.getText().toString());
                mBody.setBirthday(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
                //备注
                mBody.setRemark(mCustomerinfoRemarkEdit.getText().toString());

                mPresenter.modifyCustomerInfo(mBody);
                break;
            default:
        }
    }

    /**
     * 选择地区
     */
    private void chooseCity(String regionCode) {
        Intent intent = new Intent(getContext(), RegionActivity.class);
        intent.putExtra(RegionFragment.KEY_REGION_CODE, regionCode);
        intent.putExtra(RegionFragment.KEY_API, Api.API_REGION_LIST);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_REGION_CODE.getValue());
    }

    /**
     * 选择生日
     */
    private void pickRegion() {
        DialogTimeFragment.newIntance()
                .setDateStyle(DialogTimeFragment.YYYY_MM_DD)
                .setTitle("选择开始时间")
                .setCurrentDate(new Date())
                .setDialogListener(new IDialogTimeListener() {
                    @Override
                    public void onSure(Date date) {
                        mCustomerinfoBirthdayTv.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD));
                    }

                    @Override
                    public void onCancle() {

                    }
                })
                .show(getFragmentManager(), "customerInfoTime");
    }


    @Override
    public void onSuccess() {
        pop();
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    private String regionCode;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RequestCode.valueOf(requestCode) == RequestCode.REQUEST_CODE_REGION_CODE) {
            if (data != null) {
                Bundle addressDate = data.getExtras();
                String regionName = addressDate.getString(RegionFragment.KEY_REGION_LIST);
                regionCode = addressDate.getString(RegionFragment.KEY_REGION_CODE);
                mCustomerinfoRegionTv.setText(regionName);
            }
        }
    }
}
