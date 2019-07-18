package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lyzb.jbx.R;
import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Fragment.EditDefaultThreeFragment;
import com.szy.yishopcustomer.Fragment.EditEmailTwoFragment;
import com.szy.yishopcustomer.Fragment.EditMobileTwoFragment;
import com.szy.yishopcustomer.Fragment.EditPasswordOneFragment;
import com.szy.yishopcustomer.Fragment.EditPasswordThreeFragment;
import com.szy.yishopcustomer.Fragment.EditPasswordTwoFragment;
import com.szy.yishopcustomer.Fragment.EditPayPwdTwoFragment;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;

public class AccountSecurityStepActivity extends YSCBaseActivity implements OnFragmentFinish {

    public static final String TYPE_STEP = "type_step";

    public static final int TYPE_STEP_PWD = 0;
    public static final int TYPE_STEP_EMAIL = 1;
    public static final int TYPE_STEP_MOBILE = 2;
    public static final int TYPE_STEP_PAYPWD = 3;
    public static final int TYPE_STEP_PAYPWD_CLOSE = 4;

    //修改密码
    private EditPasswordOneFragment mFragmentOne;
    private Fragment mFragmentTwo;
    private Fragment mFragmentThree;

    private int type = TYPE_STEP_PWD;

    @Override
    public CommonFragment createFragment() {
        mFragmentOne = new EditPasswordOneFragment();

        return mFragmentOne;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra(TYPE_STEP, TYPE_STEP_PWD);

        switch (type) {
            case TYPE_STEP_PWD:
                setTitle("设置登录密码");
                break;
            case TYPE_STEP_EMAIL:
                setTitle("设置邮箱验证");
                break;
            case TYPE_STEP_MOBILE:
                setTitle("设置手机验证");
                break;
            case TYPE_STEP_PAYPWD:
                setTitle("设置余额支付密码");
                break;
            case TYPE_STEP_PAYPWD_CLOSE:
                setTitle("关闭余额支付密码");
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onFinish(Fragment fragment) {
        String fragmentName = fragment.getClass().getName();
        fragmentName = fragmentName.substring(fragmentName.lastIndexOf(".") + 1);

        switch (fragmentName) {
            case "EditPasswordOneFragment":
                switch (type) {
                    case TYPE_STEP_PWD:
                        mFragmentTwo = EditPasswordTwoFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.activity_common_fragment_container, mFragmentTwo).addToBackStack(
                                "two").commitAllowingStateLoss();
                        break;
                    case TYPE_STEP_EMAIL:
                        mFragmentTwo = EditEmailTwoFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.activity_common_fragment_container, mFragmentTwo).addToBackStack(
                                "two").commitAllowingStateLoss();
                        break;
                    case TYPE_STEP_MOBILE:
                        mFragmentTwo = EditMobileTwoFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.activity_common_fragment_container, mFragmentTwo).addToBackStack(
                                "two").commitAllowingStateLoss();
                        break;
                    case TYPE_STEP_PAYPWD:
                        mFragmentTwo = EditPayPwdTwoFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.activity_common_fragment_container, mFragmentTwo).addToBackStack(
                                "two").commitAllowingStateLoss();
                        break;
                    case TYPE_STEP_PAYPWD_CLOSE:
                        mFragmentThree = EditDefaultThreeFragment.newInstance(type);
                        mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                                mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
                        break;
                }
                break;
            case "EditPasswordTwoFragment":
                mFragmentThree = EditPasswordThreeFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                        mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
                break;
            case "EditPasswordThreeFragment":
                backAccountSecurityCenter();
                break;
            case "EditEmailTwoFragment":
                backAccountSecurityCenter();
                break;
            case "EditDefaultThreeFragment":
                backAccountSecurityCenter();
                break;
            case "EditMobileTwoFragment":
                mFragmentThree = EditDefaultThreeFragment.newInstance(type);
                mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                        mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
                break;
            case "EditPayPwdTwoFragment":
                mFragmentThree = EditDefaultThreeFragment.newInstance(type);
                mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                        mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
                break;
        }
    }

    void backAccountSecurityCenter(){
        startActivity(new Intent().setClass(this, AccountSecurityActivity.class));
        finish();
    }
}
