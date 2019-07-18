package com.lyzb.jbx;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.stetho.Stetho;
import com.huawei.android.hms.agent.HMSAgent;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.widget.AnyRefreshFooder;
import com.like.longshaolib.widget.AnyRefreshHeader;
import com.like.utilslib.UtilApp;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.activity.HomeActivity;
import com.lyzb.jbx.dbdata.manger.DataBaseManager;
import com.lyzb.jbx.util.icon.FontLongModule;
import com.lyzb.jbx.webscoket.BaseClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.szy.yishopcustomer.Application.CommonApplication;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;

/**
 * Created by longshao on 2017/3/12.
 */

public class CdLongShaoAppaction extends CommonApplication {

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
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "已显示全部";

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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /*解决方法总数超过3665的问题*/
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //bug1024的分支
        UtilApp.getIntance().init(this);

        LongshaoAPP.init(this)
//                .withApiHost("http://m.jibaoh.com/")//测式java端地址
                .withApiHost("http://m.jbxgo.com/")//正式java端地址
//                .withInterceptor(new DebugInterceptor("/lbs/gs/user/doPerfectInfo", R.raw.tangyikang1))
//                .withInterceptor(new DebugInterceptor("/lbs/gs/topic/doPublish", R.raw.tangyikang2))
//                .withInterceptor(new DebugInterceptor("AUserAccount/Register", R.raw.home))
//                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withWeChatAPPID("wxd929c3b322e3123a")
                .withWeChatSecret("1294e48d952af63b1ae23dbe7c5773c8")
                .withIcon(new FontAwesomeModule())//设置默认的字体图标
                .withIcon(new FontLongModule())//设置自定义的字体图库
                .configure();

        /*初始化greendao数据库*/
        DataBaseManager.getIntance().init(this);

        //处理android 7.0以上FileProvider问题 直接把安全模式检测给取消掉
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());

        /*初始化数据图图形化工具,上线之后不需要*/
        if (BuildConfig.DEBUG) {
            initStetho();
            //crash日志
            Recovery.getInstance()
                    .debug(true)
                    .recoverInBackground(false)
                    .recoverStack(true)
                    .mainPage(HomeActivity.class)
                    .callback(new MyCrashCallback())
                    .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                    .init(this);
        }


        //华为推送服务
        HMSAgent.init(this);

        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                // Great!
                LogUtil.loge("当前初始化音频格式转换成功");
            }

            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
                LogUtil.loge("音频格式转换器不支持该设备");
            }
        });
    }

    /**
     * 初始化数据库图形化
     * 地址：chrome://inspect/#devices
     */
    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
        }

        @Override
        public void cause(String cause) {
            LogUtil.loge("cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            LogUtil.loge("exceptionClassName:" + exceptionType + "\n"
                    + "throwClassName:" + throwClassName + "\n"
                    + "throwMethodName:" + throwMethodName + "\n"
                    + "throwLineNumber:" + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }

    @Override
    public void onTrimMemory(int level) {
        if (BaseClient.getInstance() != null && !BaseClient.getInstance().isClosed()) {
            BaseClient.getInstance().close();
        }
        super.onTrimMemory(level);
    }

    //初始化缓存数据
    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        CdLongShaoAppaction app = (CdLongShaoAppaction) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);//默认缓存视频为512M
    }
}
