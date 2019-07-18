package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.szy.yishopcustomer.Fragment.GoodsDetailFragment;
import com.szy.yishopcustomer.Fragment.GoodsIndexFragment;
import com.szy.yishopcustomer.Fragment.GoodsReviewFragment;
import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宗仁 on 2017/1/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GoodsPagerAdapter extends FragmentPagerAdapter {

    public List<Class<? extends Fragment>> data;
    public List<String> mTitleList;
    private Map<Integer, Fragment> mFragments;
    private Bundle mArguments;

    public GoodsPagerAdapter(Context context, FragmentManager fragmentManager, Bundle arguments) {
        super(fragmentManager);
        data = new ArrayList<>();
        data.add(GoodsIndexFragment.class);
        data.add(GoodsDetailFragment.class);
        data.add(GoodsReviewFragment.class);
        mFragments = new HashMap<>();
        mTitleList = new ArrayList<>();
        mTitleList.add(context.getString(R.string.tabGoods));
        mTitleList.add(context.getString(R.string.tabDetail));
        mTitleList.add(context.getString(R.string.tabReview));
        this.mArguments = arguments;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Class<? extends Fragment> clazz = data.get(position);
        Fragment fragment = null;
        if (mFragments.containsKey(position)) {
            fragment = mFragments.get(position);
        }
        if (fragment == null) {
            try {
                fragment = clazz.newInstance();
                if (mArguments != null && fragment != null) {
                    if (fragment.getArguments() == null) {
                        fragment.setArguments(mArguments);
                    } else {
                        fragment.getArguments().putAll(mArguments);
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);
            }
        }

        return fragment;
    }
}
