package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Fragment.RegisterStepOneFragment;
import com.szy.yishopcustomer.Fragment.RegisterStepThreeFragment;
import com.szy.yishopcustomer.Fragment.RegisterStepTwoFragment;
import com.lyzb.jbx.R;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RegisterActivity extends YSCBaseActivity implements RegisterStepOneFragment.FinishInputNumber, RegisterStepTwoFragment.FinishInputVerifyCode {
    private RegisterStepOneFragment mFragmentOne;
    private RegisterStepTwoFragment mFragmentTwo;
    private RegisterStepThreeFragment mFragmentThree;
/*    private RegisterStepFourFragment mFragmentFour;*/

    @Override
    public CommonFragment createFragment() {
        mFragmentOne = new RegisterStepOneFragment();
        mFragmentOne.setFinishInputNumber(this);
        return mFragmentOne;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

/*    @Override
    public void onFinishThreeStep(String code) {
        mFragmentFour = RegisterStepFourFragment.newInstance(code);
        mFragmentManager.beginTransaction()
                .replace(R.id.activity_common_fragment_container, mFragmentFour)
                .addToBackStack("four")
                .commitAllowingStateLoss();
    }*/

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_CLOSE:
                finish();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishInputNumber(String number) {
        mFragmentTwo = RegisterStepTwoFragment.newInstance(number);
        mFragmentTwo.setFinishInputVerifyCode(this);
        mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                mFragmentTwo).addToBackStack("two").commitAllowingStateLoss();
    }

    @Override
    public void onFinishVerifyCode(String phoneNumber, String verifyCode) {
        mFragmentThree = RegisterStepThreeFragment.newInstance(phoneNumber, verifyCode);
        /*mFragmentThree.setFinishThreeStep(this);*/
        mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
    }

}
