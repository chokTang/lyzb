package com.lyzb.jbx.dialog;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CompanyCircleTabModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/15 11:53
 */

public class CircleSetTabDialog extends BaseDialogFragment implements CompoundButton.OnCheckedChangeListener {
    public static final String INTENTKEY_CIRCLETAB = "intentkey_circletab";

    private final String member_dynamic = "member_topic";
    private final String company_dynamic = "com_topic";
    private final String company_website = "com_website";
    private final String company_mall = "com_shop";


    @BindView(R.id.circle_settab_member_dynamic_edit)
    EditText mCircleSettabMemberDynamicEdit;
    @BindView(R.id.circle_settab_member_dynamic_cbx)
    CheckBox mCircleSettabMemberDynamicCbx;
    @BindView(R.id.circle_settab_company_dynamic_edit)
    EditText mCircleSettabCompanyDynamicEdit;
    @BindView(R.id.circle_settab_company_dynamic_cbx)
    CheckBox mCircleSettabCompanyDynamicCbx;
    @BindView(R.id.circle_settab_website_edit)
    EditText mCircleSettabWebsiteEdit;
    @BindView(R.id.circle_settab_website_cbx)
    CheckBox mCircleSettabWebsiteCbx;
    @BindView(R.id.circle_settab_mall_edit)
    EditText mCircleSettabMallEdit;
    @BindView(R.id.circle_settab_mall_cbx)
    CheckBox mCircleSettabMallCbx;
    Unbinder unbinder;

    private List<CompanyCircleTabModel> mTabModels;
    private OnSureClickListener mOnSureClickListener;
    private List<Integer> cbxSelectionList = new ArrayList<>();

    public static CircleSetTabDialog newIntance(List<CompanyCircleTabModel> models) {
        CircleSetTabDialog fragment = new CircleSetTabDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENTKEY_CIRCLETAB, (Serializable) models);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getResId() {
        return R.layout.dialog_circle_settab;
    }

    @Override
    public void initView() {

        mCircleSettabMemberDynamicCbx.setOnCheckedChangeListener(this);
        mCircleSettabCompanyDynamicCbx.setOnCheckedChangeListener(this);
        mCircleSettabWebsiteCbx.setOnCheckedChangeListener(this);
        mCircleSettabMallCbx.setOnCheckedChangeListener(this);
        //第一次id是空的,且是否隐藏的状态是没有值的，所以要判断状态还要判断id
        for (CompanyCircleTabModel b : mTabModels) {
            switch (b.getFunCode()) {
                case member_dynamic:
                    mCircleSettabMemberDynamicCbx.setChecked(b.getStatus() == 0 && !TextUtils.isEmpty(b.getId()));
                    mCircleSettabMemberDynamicEdit.setText(TextUtils.isEmpty(b.getFunName()) ? "成员动态" : b.getFunName());
                    break;
                case company_dynamic:
                    mCircleSettabCompanyDynamicCbx.setChecked(b.getStatus() == 0 && !TextUtils.isEmpty(b.getId()));
                    mCircleSettabCompanyDynamicEdit.setText(TextUtils.isEmpty(b.getFunName()) ? "企业动态" : b.getFunName());
                    break;
                case company_website:
                    mCircleSettabWebsiteCbx.setChecked(b.getStatus() == 0 && !TextUtils.isEmpty(b.getId()));
                    mCircleSettabWebsiteEdit.setText(TextUtils.isEmpty(b.getFunName()) ? "企业官网" : b.getFunName());
                    break;
                case company_mall:
                    mCircleSettabMallCbx.setChecked(b.getStatus() == 0 && !TextUtils.isEmpty(b.getId()));
                    mCircleSettabMallEdit.setText(TextUtils.isEmpty(b.getFunName()) ? "企业商城" : b.getFunName());
                    break;
                default:
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public int getViewWidth() {
        return ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(16) * 2;
    }

    @Override
    public int getViewHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getViewGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getAnimationType() {
        return CNTER;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabModels = (List<CompanyCircleTabModel>) bundle.getSerializable(INTENTKEY_CIRCLETAB);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public CircleSetTabDialog setOnSureClickListener(OnSureClickListener listener) {
        mOnSureClickListener = listener;
        return this;
    }

    @OnClick({R.id.circle_settab_cancel_tv, R.id.circle_settab_sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_settab_cancel_tv:
                dismiss();
                break;
            case R.id.circle_settab_sure_tv:
                //显示状态且没输入栏目名称不让提交
                if (!mCircleSettabMemberDynamicCbx.isChecked() && mCircleSettabMemberDynamicEdit.getText().length() < 1) {
                    showToast("栏目名称限1-4个个字");
                    return;
                }
                if (!mCircleSettabCompanyDynamicCbx.isChecked() && mCircleSettabCompanyDynamicEdit.getText().length() < 1) {
                    showToast("栏目名称限1-4个个字");
                    return;
                }
                if (!mCircleSettabWebsiteCbx.isChecked() && mCircleSettabWebsiteEdit.getText().length() < 1) {
                    showToast("栏目名称限1-4个个字");
                    return;
                }
                if (!mCircleSettabMallCbx.isChecked() && mCircleSettabMallEdit.getText().length() < 1) {
                    showToast("栏目名称限1-4个个字");
                    return;
                }
                //就改个显示状态和名称
                for (CompanyCircleTabModel b : mTabModels) {
                    switch (b.getFunCode()) {
                        case member_dynamic:
                            b.setFunName(mCircleSettabMemberDynamicEdit.getText().toString());
                            b.setStatus(mCircleSettabMemberDynamicCbx.isChecked() ? 0 : 1);
                            break;
                        case company_dynamic:
                            b.setFunName(mCircleSettabCompanyDynamicEdit.getText().toString());
                            b.setStatus(mCircleSettabCompanyDynamicCbx.isChecked() ? 0 : 1);
                            break;
                        case company_website:
                            b.setFunName(mCircleSettabWebsiteEdit.getText().toString());
                            b.setStatus(mCircleSettabWebsiteCbx.isChecked() ? 0 : 1);
                            break;
                        case company_mall:
                            b.setFunName(mCircleSettabMallEdit.getText().toString());
                            b.setStatus(mCircleSettabMallCbx.isChecked() ? 0 : 1);
                            break;
                        default:
                    }
                }
                mOnSureClickListener.onSureClick(mTabModels);
                dismiss();
                break;
            default:
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cbxSelectionList.size() >= 3 && isChecked) {
            showToast("至少需保留一个栏目");
            buttonView.setChecked(false);
            return;
        }
        EditText editText = null;
        switch (buttonView.getId()) {
            case R.id.circle_settab_member_dynamic_cbx:
                editText = mCircleSettabMemberDynamicEdit;
                break;
            case R.id.circle_settab_company_dynamic_cbx:
                editText = mCircleSettabCompanyDynamicEdit;
                break;
            case R.id.circle_settab_website_cbx:
                editText = mCircleSettabWebsiteEdit;
                break;
            case R.id.circle_settab_mall_cbx:
                editText = mCircleSettabMallEdit;
                break;
            default:
        }
        if (editText == null) {
            return;
        }
        if (isChecked) {
            cbxSelectionList.add(buttonView.getId());
            editText.setTextColor(ContextCompat.getColor(getActivity(), R.color.fontcColor3));
        } else {
            if (cbxSelectionList.contains(buttonView.getId())) {
                cbxSelectionList.remove((Object) buttonView.getId());
            }
            editText.setTextColor(ContextCompat.getColor(getActivity(), R.color.fontcColor1));
        }

        editText.setFocusable(!isChecked);
        editText.setFocusableInTouchMode(!isChecked);
    }

    public interface OnSureClickListener {
        void onSureClick(List<CompanyCircleTabModel> list);
    }
}
