package com.lyzb.jbx.fragment.me.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.utilslib.app.AppUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.dialog.EditCompanyAccountsDialog;
import com.lyzb.jbx.model.me.CompanyAccountDetailModel;
import com.lyzb.jbx.model.me.CompanyAccountsModel;
import com.lyzb.jbx.model.params.CompanyAccountBody;
import com.lyzb.jbx.mvp.presenter.me.company.EditCompanyAccountPersenter;
import com.lyzb.jbx.mvp.view.me.IEditCompanyAccountView;
import com.szy.yishopcustomer.Util.Utils;

/**
 * s
 */
public class EditCompanyAccountsFragment extends BaseToolbarFragment<EditCompanyAccountPersenter> implements IEditCompanyAccountView {


    private static final String PARAMS_ID = "mOrganId";
    private static final String USER_ID = "userId";
    private static final String POSITION = "position";
    private String mCompanyId = "";
    private String userId = "";
    private int position = -1;

    private TextView tv_account;
    private TextView tv_name;
    private EditText edit_job;
    private EditText edit_phone;

    private EditText edit_password;
    private EditText edit_content;
    private CheckBox switch_btn;

    private RelativeLayout layout_bottom;

    private TextView btn_save;

    private EditCompanyAccountsDialog editCompanyAccountsDialog;


    public static EditCompanyAccountsFragment newIntance(String companyId, String userId, int position) {
        EditCompanyAccountsFragment fragment = new EditCompanyAccountsFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, companyId);
        args.putString(USER_ID, userId);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCompanyId = args.getString(PARAMS_ID);
            userId = args.getString(USER_ID);
            position = args.getInt(POSITION);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("编辑帐号");


        tv_account = findViewById(R.id.tv_account);
        tv_name = findViewById(R.id.tv_name);
        edit_job = findViewById(R.id.edit_job);
        edit_phone = findViewById(R.id.edit_phone);

        edit_password = findViewById(R.id.edit_password);
        edit_content = findViewById(R.id.edit_content);
        switch_btn = findViewById(R.id.switch_btn);

        layout_bottom = findViewById(R.id.layout_bottom);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData() {
        if (TextUtils.isEmpty(edit_job.getText().toString().trim())) {//使用者职位必填
            showToast(edit_job.getHint().toString().trim());
            return;
        }
        mPresenter.onUpDataAccount(getCompanyAccountBody());
    }

    private CompanyAccountBody getCompanyAccountBody() {
        CompanyAccountBody body = new CompanyAccountBody();
        body.setCompanyId(mCompanyId);
        body.setUserId(userId);
        body.setMobile(edit_phone.getText().toString().trim());
        body.setRemark(edit_content.getText().toString().trim());
        body.setPosition(edit_job.getText().toString().trim());
        body.setStatus(switch_btn.isChecked() ? 1 : 0);
        body.setBindCheck(false);
        body.setUnbind(false);
        body.setVersionCode(AppUtil.getVersionCode() + "");
        return body;
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

        mPresenter.getData(userId, mCompanyId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_edit_company_accounts;
    }

    @Override
    public void onApplyData(CompanyAccountDetailModel.DetailDataBean dataBean) {
        tv_account.setText(dataBean.getAccountName());
        tv_name.setText(dataBean.getGsName());

        edit_job.setText(dataBean.getPosition());
        edit_phone.setText(dataBean.getMobile());
        edit_content.setText(dataBean.getRemark());
        switch_btn.setChecked(dataBean.getAccountSt() == 1 ? true : false);
        layout_bottom.setVisibility("1".equals(dataBean.getRole()) ? View.GONE : View.VISIBLE);

    }

    @Override
    public void onUpDataAccount() {

        if (TextUtils.isEmpty(edit_password.getText().toString().trim())) {//回到上一层
            setFragmentResult(RESULT_OK, getBundle());
            pop();
        } else {
            String mPassword = edit_password.getText().toString();
            if (Utils.isPasswordValid(mPassword) && !Utils.isIncludeSpace(mPassword)) {
                mPresenter.onUpDataPassword(userId, edit_password.getText().toString().trim());
            } else {
                showToast(getResources().getString(R.string
                        .registerSetPasswordTip));
            }
        }
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("CompanyMembersBody", getCompanyAccountBody());
        bundle.putInt(POSITION, position);
        return bundle;
    }

    @Override
    public void onUpDataPassword() {
        setFragmentResult(RESULT_OK, getBundle());
        pop();
    }

    @Override
    public void onSendMSMSuccess() {
        editCompanyAccountsDialog.sendCode();
    }

    @Override
    public void onValidateMobileCodeSuccess(String status) {//验证成功了
        editCompanyAccountsDialog.dismiss();
        CompanyAccountBody companyAccountBody = getCompanyAccountBody();
        if ("205".equals(status)) {
            companyAccountBody.setUnbind(false);
            companyAccountBody.setBindCheck(true);
        } else {
            companyAccountBody.setUnbind(true);
            companyAccountBody.setBindCheck(false);
        }
        mPresenter.onUpDataAccount(companyAccountBody);

    }

    @Override
    public void onPhoneStatus(String status, String msg) {
        editCompanyAccountsDialog = EditCompanyAccountsDialog.newIntance(status, msg);

        editCompanyAccountsDialog.setOnClickListener(new EditCompanyAccountsDialog.OnClickListener() {
            @Override
            public void onclick(View view, String status, String code) {
                switch (view.getId()) {
                    case R.id.tv_confirm:
                        if (TextUtils.isEmpty(code)) {
                            showToast("请填写验证码");
                        } else {
                            mPresenter.validateMobile(edit_phone.getText().toString().trim(), code, status);
                        }
                        break;
                    case R.id.btn_code://发送验证码
                        mPresenter.onSendCode(edit_phone.getText().toString().trim());
                        break;
                }
            }
        });
        editCompanyAccountsDialog.show(getFragmentManager(), "EditCompanyAccountsDialog");
    }
}
