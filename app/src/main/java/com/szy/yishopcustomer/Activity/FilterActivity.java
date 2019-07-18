package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.*;
import android.view.WindowManager;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.FilterFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class FilterActivity extends YSCBaseActivity {

    @BindView(R.id.activity_filter_closeView)
    View mCloseView;

    @Override
    public void finish() {
        super.finish();
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_HIDE_FILTER_VIEW.getValue()));
        overridePendingTransition(R.anim.slide_from_right_to_left, R.anim.slide_from_left_to_right);
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                finish();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    protected CommonFragment createFragment() {
        return new FilterFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_filter;
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_from_right_to_left, R.anim.slide_from_left_to_right);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        Utils.setViewTypeForTag(mCloseView, ViewType.VIEW_TYPE_CLOSE);
        mCloseView.setOnClickListener(this);
    }
}
