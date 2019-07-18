package com.lyzb.jbx.adapter.school;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.like.longshaolib.base.BaseFragment;

import java.util.List;

public class ArticlePagerApdater extends FragmentStatePagerAdapter {

    private List<BaseFragment> list;

    public ArticlePagerApdater(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
