package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.FindPasswordFourFragment;
import com.szy.yishopcustomer.Fragment.FindPasswordOneFragment;
import com.szy.yishopcustomer.Fragment.FindPasswordThreeFragment;
import com.szy.yishopcustomer.Fragment.FindPasswordTwoFragment;
import com.lyzb.jbx.R;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FindPasswordActivity extends YSCBaseActivity implements FindPasswordOneFragment.FinishUserName, FindPasswordTwoFragment.FinishInputVerifyCode, FindPasswordThreeFragment.FinishNewPassword {
    private FindPasswordOneFragment mFragmentOne;
    private FindPasswordTwoFragment mFragmentTwo;
    private FindPasswordThreeFragment mFragmentThree;
    private FindPasswordFourFragment mFragmentFour;

    @Override
    public CommonFragment createFragment() {
        mFragmentOne = new FindPasswordOneFragment();
        mFragmentOne.setFinishUserName(this);
        return mFragmentOne;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFinishNewPassword(String password) {
        mFragmentFour = FindPasswordFourFragment.newInstance(password);
        mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                mFragmentFour).addToBackStack("four").commitAllowingStateLoss();
    }

    @Override
    public void onFinishUserName(String nickname, String mobile, String email, String type) {
        mFragmentTwo = FindPasswordTwoFragment.newInstance(nickname, mobile, email, type);
        mFragmentTwo.setFinishInputVerifyCode(this);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.activity_common_fragment_container, mFragmentTwo).addToBackStack(
                "two").commitAllowingStateLoss();
    }

    @Override
    public void onFinishVerifyCode(String verifyCode) {
        mFragmentThree = FindPasswordThreeFragment.newInstance(verifyCode);
        mFragmentThree.setFinishNewPassword(this);
        mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
    }

}
