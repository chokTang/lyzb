package com.lyzb.jbx.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.base.BaseStatusFragment;
import com.lyzb.jbx.R;
import com.szy.common.View.CommonEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 修改密码
 * @time 2019 2019/3/5 14:12
 */

public class SetPwdFragment extends BaseStatusFragment {

    @BindView(R.id.img_union_me_set_pwd_back)
    ImageView backImg;

    @BindView(R.id.edt_union_set_pwd)
    CommonEditText setPwdEdt;
    @BindView(R.id.img_union_set_pwd)
    ImageView setPwdImg;

    @BindView(R.id.edt_union_set_again_pwd)
    CommonEditText setAgainEdt;
    @BindView(R.id.img_union_set_again_pwd)
    ImageView setAgainImg;

    private boolean mShowPwd = false;
    private boolean mShowAgainPwd = false;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        ButterKnife.bind(this, mView);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        setPwdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPwd(false);
            }
        });

        setAgainImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPwd(true);
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_union_setpwd;
    }

    @Override
    public void onLazyRequest() {

    }

    @Override
    public void onAgainRequest() {

    }

    private void showPwd(boolean isAgain) {

        if (isAgain) {
            mShowAgainPwd = !mShowAgainPwd;

            if (mShowAgainPwd) {
                setAgainEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                setAgainEdt.setSelection(setPwdEdt.getText().toString().length());

                setAgainImg.setImageResource(R.mipmap.union_set_pwd_open);
            } else {
                setAgainEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                setAgainEdt.setSelection(setPwdEdt.getText().toString().length());

                setAgainImg.setImageResource(R.mipmap.union_set_pwd_open);
            }
        } else {
            mShowPwd = !mShowPwd;

            if (mShowPwd) {
                setPwdEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                setPwdEdt.setSelection(setPwdEdt.getText().toString().length());

                setPwdImg.setImageResource(R.mipmap.union_set_pwd_open);
            } else {
                setPwdEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                setPwdEdt.setSelection(setPwdEdt.getText().toString().length());

                setPwdImg.setImageResource(R.mipmap.union_set_pwd_open);
            }
        }
    }
}
