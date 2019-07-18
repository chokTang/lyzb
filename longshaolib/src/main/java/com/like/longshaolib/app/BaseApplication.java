package com.like.longshaolib.app;

import android.app.Application;
import android.content.Context;

import com.like.longshaolib.widget.AnyRefreshFooder;
import com.like.longshaolib.widget.AnyRefreshHeader;
import com.like.utilslib.UtilApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 对下拉刷新进行了初始化
 * Created by longshao on 2017/8/22.
 */

public class BaseApplication extends Application {

    static {
        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "松开刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
        ClassicsHeader.REFRESH_HEADER_UPDATE = "上次更新 MM-dd HH:mm:ss";

        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "松开加载";
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "全部加载完成";

        /*配置全局的刷新和加载*/
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new AnyRefreshHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new AnyRefreshFooder(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UtilApp.getIntance().init(this);
    }
}
