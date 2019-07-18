package com.szy.yishopcustomer.Application;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.EasyUtils;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.eventbus.AccountPerfectEventBus;
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus;
import com.lyzb.jbx.model.eventbus.MessageEventBus;
import com.lyzb.jbx.model.eventbus.MessageLoginEventBus;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.im.CallReceiver;
import com.szy.yishopcustomer.Activity.im.LyMessageActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Iterator;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

//import im.fir.sdk.FIR;

/**
 * Created by 宗仁 on 16/8/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CommonApplication extends MultiDexApplication {

    private static final String TAG = "CommonApplication";

    @Override
    public void onCreate() {
        super.onCreate();


        App.getInstance().mContext = this;

        EventBus.getDefault().register(this);
        NoHttp.initialize(this);

        Logger.setDebug(Config.DEBUG); // 开启NoHttp调试模式。

        App.packageName = getPackageName();
        if (!Utils.isNull(Config.CUSTOM_FONT_NAME)) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath
                    (Config.CUSTOM_FONT_NAME).setFontAttrId(R.attr.fontPath).build());
        }

        //配置图片缓存
        ImageLoader mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            DisplayImageOptions.Builder imageOptionsBuilder = new DisplayImageOptions.Builder();
            imageOptionsBuilder.cacheInMemory(true);
            imageOptionsBuilder.cacheOnDisk(true);
            imageOptionsBuilder.considerExifParams(true);
            imageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);

            DisplayImageOptions imageOptions = imageOptionsBuilder.build();

            ImageLoaderConfiguration.Builder imageConfigurationBuilder = new ImageLoaderConfiguration.Builder(
                    getApplicationContext());
            imageConfigurationBuilder.threadPriority(Thread.NORM_PRIORITY - 2);
            imageConfigurationBuilder.memoryCacheSize(50 * 1024 * 1024);
            imageConfigurationBuilder.denyCacheImageMultipleSizesInMemory();
            imageConfigurationBuilder.diskCacheFileNameGenerator(new Md5FileNameGenerator());
            imageConfigurationBuilder.diskCacheSize(100 * 1024 * 1024);
            imageConfigurationBuilder.tasksProcessingOrder(QueueProcessingType.LIFO);
            imageConfigurationBuilder.defaultDisplayImageOptions(imageOptions);
            imageConfigurationBuilder.threadPoolSize(5);
            imageConfigurationBuilder.diskCacheExtraOptions(1024, 1024, null);

            ImageLoaderConfiguration configuration = imageConfigurationBuilder.build();
            ImageLoader.getInstance().init(configuration);
        }

        try {
            AuthInfo mAuthInfo = new AuthInfo(this, Config.WEIBO_KEY, "http://www.sina.com", "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write");
            WbSdk.install(this, mAuthInfo);
        } catch (Exception e) {

        }

        //腾讯bug日志
        CrashReport.initCrashReport(getApplicationContext(), "48144bcab5", false);

        /*-----------------------------------IM环信配置开始-------------------------------------------*/

        EMOptions options = new EMOptions();

        options.setAutoLogin(true); //IM自动登录
        options.setAcceptInvitationAlways(false);//添加好友是否需要验证
        options.setAutoTransferMessageAttachments(true);//附件是否上传环信服务器
        options.setAutoDownloadThumbnail(true);// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        //小米推送
        options.setMipushConfig("2882303761517728172", "5271772881172");

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
//        EMClient.getInstance().init(this, options);
        EaseUI.getInstance().init(this, options);

        if (shouldInit()){
            MiPushClient.registerPush(this, "2882303761517728172", "5271772881172");
        }

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        initEaseUINotice();

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(new CallReceiver(), callFilter);


        /*-----------------------------------IM环信配置结束-------------------------------------------*/
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //会导致出错，iamgeLoader配置信息不见了，会抛出一个错误
        try {
            ImageLoader.getInstance().clearMemoryCache();
        } catch (Exception e) {

        }

    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        EventWhat eventWhat = EventWhat.valueOf(event.getWhat());
        switch (eventWhat) {
            case EVENT_LOGIN:
                EventBus.getDefault().post(new AccountPerfectEventBus());
                EventBus.getDefault().post(new DynamicItemStatusEventBus(true));
                App.getInstance().setLogin(true);
                if (App.getInstance().onLoginListener != null) {
                    App.getInstance().onLoginListener.onLogin();
                    App.getInstance().onLoginListener = null;
                }
                break;
            case EVENT_LOGIN_CANCEL:
                App.getInstance().onLoginListener = null;
                break;
            case EVENT_LOGOUT:
                App.getInstance().setLogin(false);
                break;
            //环信注册监听
            case HX_LISTINENER:
                EMClient.getInstance().chatManager().addMessageListener(listener);
                //环信登录成功后，表示要刷新消息列表及未读消息数量
                EventBus.getDefault().post(new MessageLoginEventBus());
                int number = EMClient.getInstance().chatManager().getUnreadMessageCount();
                EventBus.getDefault().post(new MessageEventBus(number));
                break;
            //取消环信监听
            case HX_UNLISTINENER:
                EMClient.getInstance().chatManager().removeMessageListener(listener);
                break;
            default:
                Log.i(TAG, "Event is " + eventWhat.toString() + " ,message is " + event
                        .getMessage());
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * IM相关方法
     *
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 初始化环信通知栏
     */
    private void initEaseUINotice() {
        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //修改标题,这里使用默认
                return "集宝箱";
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {

                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, getApplicationContext());
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = EaseUserUtils.getUserInfo(message.getFrom());
                if (user != null) {
                    return EaseUserUtils.getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //设置点击通知栏跳转事件
                Intent intent = new Intent(getApplicationContext(), LyMessageActivity.class);
                App.getInstance().msg_info_type = true;
//                    //有电话时优先跳转到通话页面
//                    if(isVideoCalling){
//                        intent = new Intent(appContext, VideoCallActivity.class);
//                    }else if(isVoiceCalling){
//                        intent = new Intent(appContext, VoiceCallActivity.class);
//                    }else{
//                        ChatType chatType = message.getChatType();
//                        if (chatType == ChatType.Chat) { // 单聊信息
//                            intent.putExtra("userId", message.getFrom());
//                            intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
//                        } else { // 群聊信息
//                            // message.getTo()为群聊id
//                            intent.putExtra("userId", message.getTo());
//                            if(chatType == ChatType.GroupChat){
//                                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
//                            }else{
//                                intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
//                            }
//
//                        }
//                    }
                return intent;
            }
        });
    }

    private EMMessageListener listener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            /**发送 通知消息*/
            if (EasyUtils.isAppRunningForeground(getApplicationContext())) {
                EaseUI.getInstance().getNotifier().vibrateAndPlayTone(list.get(0));
            } else {
                EaseUI.getInstance().getNotifier().notify(list);
            }
            int number = EMClient.getInstance().chatManager().getUnreadMessageCount();
            EventBus.getDefault().post(new MessageEventBus(number, list));
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {
        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
        }
    };

    // 程序在内存清理的时候执行
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
