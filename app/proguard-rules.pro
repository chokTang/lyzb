# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\androidstudio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-------greendao的混淆编译开始----------
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
#-------greendao的混淆编译结束----------

# 混淆
-keepclassmembers class com.szy.yishopcustomer.Activity.IBaseWebview$NativeInterface {
   public *;
}

-ignorewarnings

-dontwarn HttpUtils.HttpFetcher
-dontwarn android.support.v7.**
-dontwarn butterknife.internal.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn com.amap.**
-dontwarn com.ta.utdid2.**
-dontwarn com.taobao.**
-dontwarn com.tencent.**
-dontwarn com.unionpay.**
-dontwarn com.ut.device.**
-dontwarn okio.**
-keep class **$$ViewBinder { *; }
-keep class android.support.v7.** { *; }
-keep class butterknife.** { *; }
-keep class com.alibaba.** { *; }
-keep class com.alipay.** {*;}
-keep class com.amap.** { *; }
-keep class com.autonavi.** { *; }
-keep class com.iflytek.** { *; }
-keep class com.netease.nis.bugrpt.** {*;}
-keep class com.sina.** { *; }
-keep class com.szy.common.ResponseModel.** { *; }
-keep class com.szy.common.ViewModel.** { *; }
-keep class com.szy.yishopcustomer.ResponseModel.** { *; }
-keep class com.szy.yishopcustomer.ViewModel.** { *; }
-keep class com.tencent.** { *; }
-keep class com.taobao.** { *; }
-keep class com.unionpay.** { *; }
-keep class com.ut.*

-keep class com.weibo.** { *; }
-keep class com.yolanda.nohttp.** { *; }
-keep enum org.greenrobot.eventbus.**{ *; }
-keep interface android.support.v7.** { *; }
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.AppCompatActivity

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-renamesourcefileattribute Proguard

-keepattributes SourceFile,LineNumberTable
#防止inline
-dontoptimize

-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
}

-dontwarn com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**
-keep class com.shuyu.gsyvideoplayer.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.**

-keep class com.baidu.speech.**{*;}

-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#huawei Push
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-keep class com.huawei.android.hms.agent.**{*;}