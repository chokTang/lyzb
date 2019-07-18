package com.lyzb.jbx.fragment.me.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author wyx
 * @role 我的发表
 * @time 2019 2019/3/5 14:45
 */

public class PublishFragment extends BaseToolbarFragment {

    private AutoWidthTabLayout mTabLayout;

    private List<BaseFragment> fragments;
    private int mCurrentIndex = 0;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("我发表的");

        mTabLayout = (AutoWidthTabLayout) findViewById(R.id.tab_un_me_publish);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(new PubDynamicFragment());
        fragments.add(new PubReplyFragment());

        loadMultipleRootFragment(R.id.lin_un_me_publish, 0, fragments.toArray(new ISupportFragment[2]));

        mTabLayout.addTab("动态");
        mTabLayout.addTab("回复");

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });

        chanageFragment(0);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_me_publish;
    }

    private void chanageFragment(int postion) {
        showHideFragment(fragments.get(postion), fragments.get(mCurrentIndex));
        mCurrentIndex = postion;
    }
}
