package com.like.longshaolib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.R;
import com.like.longshaolib.base.presenter.BasePresenter;

/**
 * 基础有toolbar页面
 * Created by longshao on 2017/10/13.
 */

public abstract class BaseToolbarFragment<P extends BasePresenter> extends BaseFragment<P> {

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    protected TextView mToolbarSubTitle;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarSubTitle = findViewById(R.id.toolbar_subtitle);
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
}
