package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Fragment.EditPasswordOneFragment;
import com.szy.yishopcustomer.Fragment.EditPasswordThreeFragment;
import com.szy.yishopcustomer.Fragment.EditPasswordTwoFragment;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;
import com.lyzb.jbx.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liwei on 2017/7/11.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditPasswordActivity extends YSCBaseActivity implements OnFragmentFinish {
    private EditPasswordOneFragment mFragmentOne;
    private EditPasswordTwoFragment mFragmentTwo;
    private EditPasswordThreeFragment mFragmentThree;

    @Override
    public CommonFragment createFragment() {
        mFragmentOne = new EditPasswordOneFragment();
        return mFragmentOne;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFinish(Fragment fragment) {
        String fragmentName = fragment.getClass().getName();
        fragmentName = fragmentName.substring(fragmentName.lastIndexOf(".") + 1);

        switch (fragmentName) {
            case "EditPasswordOneFragment":
                mFragmentTwo = EditPasswordTwoFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.activity_common_fragment_container, mFragmentTwo).addToBackStack(
                        "two").commitAllowingStateLoss();
                break;
            case "EditPasswordTwoFragment":
                mFragmentThree = EditPasswordThreeFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.activity_common_fragment_container,
                        mFragmentThree).addToBackStack("three").commitAllowingStateLoss();
                break;
            case "EditPasswordThreeFragment":
                startActivity(new Intent().setClass(this, RootActivity.class));
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue()));
                finish();
                break;
        }
    }
}
