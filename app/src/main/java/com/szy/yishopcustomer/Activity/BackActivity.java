package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.BackListFragment;
import com.szy.yishopcustomer.Fragment.ComplaintListFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liwei on 2017/11/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackActivity extends YSCBaseActivity {
    @BindView(R.id.textView_my_backlist)
    TextView mTabBackList;
    @BindView(R.id.textView_my_complaint)
    TextView mTabComplaint;

    FragmentManager fragmentManager;
    private List<Fragment> fragmentList = new ArrayList<>();
    BackListFragment backListFragment;
    ComplaintListFragment complaintListFragment;
    private String type;

    @Override
    public CommonFragment createFragment() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_back;
        super.onCreate(savedInstanceState);

        mTabBackList.setOnClickListener(this);
        mTabComplaint.setOnClickListener(this);

        fragmentList.add(backListFragment = new BackListFragment());
        fragmentList.add(complaintListFragment = new ComplaintListFragment());

        Intent intent = getIntent();
        type = intent.getStringExtra(Key.KEY_TYPE.getValue());

        if(!Utils.isNull(type)){
            //打开投诉
            switchButton(mTabComplaint);
            switchFragment(1);
        }else{
            //打开退换货
            switchButton(mTabBackList);
            switchFragment(0);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textView_my_backlist:
                switchButton(view);
                switchFragment(0);
                break;
            case R.id.textView_my_complaint:
                switchButton(view);
                switchFragment(1);
                break;
        }
    }

    void switchButton(View w){

        mTabBackList.setSelected(false);
        mTabComplaint.setSelected(false);

        w.setSelected(true);
    }

    void switchFragment(int position) {
        if(fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            for(int i = 0 ;i < fragmentList.size() ; i ++) {
                transaction.add(R.id.framelayout,fragmentList.get(i));
            }

            transaction.commit();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for(int i = 0 ;i < fragmentList.size() ; i ++) {
            if(i == position) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }
}