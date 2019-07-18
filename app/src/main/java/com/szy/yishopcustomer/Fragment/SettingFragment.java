package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;

/**
 * Created by zongren on 16/4/26.
 */
public class SettingFragment extends YSCBaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_setting;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }
}
