package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 同城生活 fragment
 * @time 2018 15:24
 */

public class CityLifeFragment extends Fragment {

    View mView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_citylife, container, false);
        }

        return mView;
    }
}
