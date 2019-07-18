package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RelativeLayout;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Adapter.CategoryAdapter;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ViewModel.CategoryModel;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class DistribCategoryFragment extends CategoryFragment {

    @BindView(R.id.fragment_category_title)
    RelativeLayout mTitle;
    private String cat_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mTitle.setVisibility(View.GONE);

        mAdapter.setOnItemClickListener(new CategoryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryModel data) {
                cat_id = data.getCatId();
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_GOODS_LIST.getValue()));
            }
        });
        return view;
    }

    public String getCatId(){
        return cat_id;
    }
}
