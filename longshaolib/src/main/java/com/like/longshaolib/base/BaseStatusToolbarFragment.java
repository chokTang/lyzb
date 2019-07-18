package com.like.longshaolib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.R;
import com.like.longshaolib.base.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/4/23.
 */

public abstract class BaseStatusToolbarFragment<P extends BasePresenter> extends BaseStatusFragment<P>{

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    protected TextView mToolbarSubTitle;
    protected View line_toolbar;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarSubTitle = findViewById(R.id.toolbar_subtitle);
        line_toolbar = findViewById(R.id.line_toolbar);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    protected void setToolbarTitle(String title) {
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(title);
    }

    protected void setToolbarSubTitle(String subTitle) {
        mToolbarSubTitle.setVisibility(View.VISIBLE);
        mToolbarSubTitle.setText(subTitle);
    }

    protected void onBack() {
        mToolbar.setNavigationIcon(R.drawable.toolbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_base_status_toolbar_layout;
    }
}
