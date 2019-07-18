package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.FrameLayout;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DepositCardFragment extends YSCBaseFragment {

    @BindView(R.id.framelayout)
    FrameLayout framelayout;

    private List<Fragment> fragmentList = new ArrayList<>();

    DepositCardPlatformFragment depositCardPlatformFragment;

    FragmentManager fragmentManager;
    int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_deposit_card;

        fragmentList.add(depositCardPlatformFragment = new DepositCardPlatformFragment());

        page = getActivity().getIntent().getIntExtra(Key.KEY_PAGE.getValue(), 0);

        if (fragmentManager == null) {
            fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            for (int i = 0; i < fragmentList.size(); i++) {
                transaction.add(R.id.framelayout, fragmentList.get(i));
            }

            transaction.commit();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //设置默认
        switchFragment(0);
        return view;
    }

    void switchFragment(int position) {
        if (getActivity() instanceof OnChangeMenuItem) {
            ((OnChangeMenuItem) getActivity()).changeMenuItem(position);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == position) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }

    public interface OnChangeMenuItem {
        void changeMenuItem(int type);
    }

}
