package com.like.longshaolib.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.R;
import com.like.longshaolib.base.presenter.BasePresenter;

/**
 * 需要优化的各种状态的fragment
 * 优化的有：空布局
 * 错误布局
 * 无网络布局
 * Created by Administrator on 2018/4/21.
 */

public abstract class BaseStatusFragment<P extends BasePresenter> extends BaseFragment<P> {

    protected ViewStub main_viewstub;
    protected ViewStub empty_viewstub;
    protected ViewStub error_viewstub;
    protected ViewStub noNet_viewstub;
    private TextView error_tv;
    protected ImageView empty_img;
    protected TextView empty_tv;

    protected View main_view;
    protected View empty_view;
    protected View error_view;
    protected View nonet_view;

    public abstract Integer getMainResId();//获取主题的

    public abstract void onLazyRequest();//第一次请求数据，懒加载数据

    public abstract void onAgainRequest();//表示再次加载

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        main_viewstub = findViewById(R.id.main_viewstub);
        empty_viewstub = findViewById(R.id.empty_viewstub);
        error_viewstub = findViewById(R.id.error_viewstub);
        noNet_viewstub = findViewById(R.id.noNet_viewstub);

        main_viewstub.setLayoutResource(getMainResId());
        main_viewstub.inflate();
        main_view = findViewById(R.id.normal_id);
    }

    @Override
    public final void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLazyRequest();
            }
        }, 500);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_base_status_layout;
    }

    /**
     * 显示特定类型页面
     *
     * @param viewType
     */
    public final void showView(@ViewType int viewType, String message) {
        switch (viewType) {
            case EMPTY_VIEW:
                if (main_view != null) main_view.setVisibility(View.GONE);
                if (nonet_view != null) nonet_view.setVisibility(View.GONE);
                if (error_view != null) error_view.setVisibility(View.GONE);
                if (empty_view != null) {
                    if (empty_view.getVisibility() != View.VISIBLE) {
                        empty_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    empty_viewstub.setLayoutResource(getEmptyView());
                    empty_viewstub.inflate();
                    empty_view = findViewById(R.id.empty_id);
                    empty_tv=empty_view.findViewById(R.id.empty_tv);
                    empty_img=empty_view.findViewById(R.id.empty_img);
                }
                break;
            case MAIN_VIEW:
                if (empty_view != null) empty_view.setVisibility(View.GONE);
                if (nonet_view != null) nonet_view.setVisibility(View.GONE);
                if (error_view != null) error_view.setVisibility(View.GONE);
                if (main_view != null) {
                    if (main_view.getVisibility() != View.VISIBLE) {
                        main_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    main_viewstub.setLayoutResource(getMainResId());
                    main_viewstub.inflate();
                    main_view = findViewById(R.id.normal_id);
                }
                break;
            case NONET_VIEW:
                if (empty_view != null) empty_view.setVisibility(View.GONE);
                if (main_view != null) main_view.setVisibility(View.GONE);
                if (error_view != null) error_view.setVisibility(View.GONE);
                if (nonet_view != null) {
                    if (nonet_view.getVisibility() != View.VISIBLE) {
                        nonet_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    noNet_viewstub.setLayoutResource(R.layout.noworknet_layout);
                    noNet_viewstub.inflate();
                    nonet_view = findViewById(R.id.nonet_id);
                    initNoNetView();
                }
                break;
//            case ERROR_VIEW:
//                if (empty_view != null) empty_view.setVisibility(View.GONE);
//                if (nonet_view != null) nonet_view.setVisibility(View.GONE);
//                if (main_view != null) main_view.setVisibility(View.GONE);
//                if (error_view != null) {
//                    if (error_view.getVisibility() != View.VISIBLE) {
//                        error_view.setVisibility(View.VISIBLE);
//                    }
//                    error_tv.setText(String.valueOf(message));
//                } else {
//                    error_viewstub.setLayoutResource(R.layout.error_layout);
//                    error_viewstub.inflate();
//                    error_view = findViewById(R.id.error_id);
//                    error_tv = error_view.findViewById(R.id.error_tv);
//                    initErrorView(message);
//                }
//                break;
            default:
                break;
        }
    }

    //无数据页面
    @LayoutRes
    protected Integer getEmptyView() {
        return R.layout.empty_layout;
    }

    /**
     * 初始化错误信息组件
     */
    private void initErrorView(String message) {
        error_tv.setText(String.valueOf(message));
        error_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAgainRequest();
            }
        });
    }

    /**
     * 初始化无网络组件
     */
    private void initNoNetView() {
        nonet_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAgainRequest();
            }
        });
    }
}
