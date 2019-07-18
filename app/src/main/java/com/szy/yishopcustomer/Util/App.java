package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huawei.android.hms.agent.HMSAgent;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.like.utilslib.preference.PreferenceUtil;
import com.lyzb.jbx.model.dynamic.DynamicCommentModel;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Interface.OnLoginListener;
import com.szy.yishopcustomer.ResponseModel.AppInfo.DataModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.SeachHisModel;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class App {

    //是否显示原生页面,true显示H5,false显示原生页面
    public static final boolean isShowH5 = false;
    //分页 一页条数
    public static final int PageSize = 10;

    public Context mContext;

    public String weixin_get_access_token_code;

    private static final int MAXIMUM_CART_NUMBER = 99;
    public static int activityCount = 0;
    public static String packageName;
    private static App mApp;
    public String loginBackground;
    public String userCenterBgimage;
    public String loginLogo;
    public boolean weixinLogin;
    public String weixinLoginLogo;
    public String wangXinAppKey;
    public String wangXinUserId;
    public String wangXinPassword;
    public String wangXinServiceId;
    public String xingeUserId;
    public String userId;
    public String phoneNumber;
    public String qq;
    public boolean YWEnable;

    public String aliim_icon_show;
    public String aliim_icon;

    public OnLoginListener onLoginListener;
    public boolean mLogin;
    public String mImageBaseUrl;
    public boolean YWApiIsInit;
    public String wangXinAvatar;

    public String lat;
    public String lng;
    public String province;//省市 名称
    public String district;//城区名称
    public String city;//城市名称
    public String location;//具体地址
    public String addressDetail;//用来在附近商店更多左上角显示地址
    public boolean isLocation = false;
    public boolean isCounty = false;

    /*** 针对 同城生活首页 定位成功or选择城市后的数据刷新标志  true 刷新 **/
    public boolean isCityChanage = false;
    public boolean clickChangeCity = false;

    public String site_region_code;
    public String adcode;
    public String user_token;

    public String city_code;
    public String home_area_code;//仅限于首页/lbs/home/goodsAndShops 这个借口
    public String city_name;
    public String city_province;//该城市的省级名称

    public boolean disableWeixinLoginTemporary;
    private int mCartNumber;
    public boolean isOpenMessageActivity = false;

    public String userName;
    public String userPhone;
    public String userEmail;
    public String headimg;
    public String nickName;//用户的昵称

    public boolean isGoBuyGood = false;   //是否在 名片内上传的商品 有购买权限
    public boolean isMeComd = true;     //切换到-我的 模块下时 获取 当前用户的企业是否是自己创建的企业

    public String cardFansNumber;       //粉丝数目
    public String cardFocusNumber;      //关注数目
    public String cardDycNumber;        //动态数目

    public boolean isUserInfoPer = false;      //标识 是否已完善信息(默认已完善)

    public boolean isUserVip = false;         //标识 当前用户是否为会员用户  默认不是

    public String default_lazyload;

    public String default_shop_image;
    public String default_user_portrait;
    public String default_micro_shop_image;

    public int is_freebuy_enable;
    public int is_reachbuy_enable;

    public int is_scancode_enable;

    public String user_ingot_number;
    /**
     * 用户进入批发商城 权限值
     ***/
    public String user_mall_permi;
    /**
     * 用户 invcode
     */
    public String user_inv_code;
    /**
     * 用户 金额
     **/
    public String user_money_format;

    /***
     * 收货地址信息 数据存储
     *
     */
    public String ads_info;
    public String ads_name;
    public String ads_phone;
    public int ads_id;

    /**
     * TOOD 同城生活 分类  搜索历史 数据model
     **/
    public List<SeachHisModel> mHisModels = new ArrayList<>();

    /**
     * TOOD 广告数据
     * isGuide 是否需要进入 引导页面
     * ads_url 图片地址
     * ads_link 广告链接
     * ads_skip_time 倒计时
     * ads_h5 表示广告跳转到H5 动作 tag
     */
    public boolean isGuide = false;
    public String ads_url;
    public String ads_link;
    public String ads_skip_time;
    public boolean ads_h5 = false;

    /**
     * 环信 app后台运行 通知栏消息点击提示 tag
     */
    public boolean msg_info_type = false;

    public String index_like_data;

    //主题颜色
    public int colorPrimary = Color.parseColor("#ff00ff");
    public int colorPrimaryDark = Color.parseColor("#f23030");

    public List<DataModel.SiteNavListBean> site_nav_list;

    //是否有未读消息
    public int isUnreadMessage = View.GONE;

    private App() {
        mLogin = false;
    }

    /**
     * 更新购物车数量
     *
     * @param delta         购物车数量变量
     * @param messageSource 消息来源，一般为Activity或者Fragment对象，
     *                      用来判断消息来源是否是当前Activity或Fragment，
     *                      避免造成死循环。
     */
    public static void addCartNumber(int delta, Object messageSource) {
        setCartNumber(getCartNumber() + delta, messageSource);
    }

    public static void addCartNumber(String delta, Object messageSource) {
        addCartNumber(Integer.valueOf(delta), messageSource);
    }

    public static int getCartNumber() {
        return App.getInstance().mCartNumber;
    }

    public static String getCartString() {
        if (App.getInstance().mCartNumber > MAXIMUM_CART_NUMBER) {
            return MAXIMUM_CART_NUMBER + "+";
        }
        return App.getInstance().mCartNumber + "";
    }

    public String getImageBaseUrl() {
        return mImageBaseUrl;
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        mImageBaseUrl = imageBaseUrl;
    }

    public static App getInstance() {
        if (mApp == null) {
            mApp = new App();
        }
        return mApp;
    }

    public boolean isLogin() {
        return mLogin && !TextUtils.isEmpty(user_token);
    }

    public void setLogin(boolean login) {
        mLogin = login;
    }

    /**
     * 更新购物车数量
     *
     * @param cartNumber    新的购物车数量
     * @param messageSource 消息来源，一般为Activity或者Fragment对象，
     *                      用来判断消息来源是否是当前Activity或Fragment，
     *                      避免造成死循环。
     */
    public static void setCartNumber(int cartNumber, Object messageSource) {
        App.getInstance().mCartNumber = cartNumber;

        EventBus.getDefault().post(new CommonEvent.Builder(EventWhat.EVENT_UPDATE_CART_NUMBER
                .getValue()).setMessage(getCartString()).setMessageSource(messageSource).build());
    }

    public static void setCartNumber(String cartNumber, Object messageSource) {
        App.setCartNumber(Integer.valueOf(cartNumber), messageSource);
    }

    public static void loginOut(String response, final Context context) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager.HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {


                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGOUT.getValue(), back.message));
                Toast.makeText(context, back.message, Toast.LENGTH_SHORT).show();
                App.getInstance().wangXinServiceId = "";
                App.getInstance().wangXinUserId = "";
                App.getInstance().wangXinPassword = "";
                App.getInstance().wangXinAppKey = "";
                App.getInstance().userId = "";
                App.getInstance().user_token = "";
                App.getInstance().user_inv_code = null;
                App.getInstance().weixin_get_access_token_code = null;

                SharedPreferencesUtils.removeValue(context, Key.USER_INFO_TOKEN.getValue());
                SharedPreferencesUtils.removeValue(context, Key.USER_INFO_ID.getValue());
                SharedPreferencesUtils.removeValue(context, Key.USER_INFO_IDENTITY.getValue());
                SharedPreferencesUtils.removeValue(context, Key.IM_USERNAME.name());
                SharedPreferencesUtils.removeValue(context, Key.IM_USERPASSWORD.name());
                SharedPreferencesUtils.removeValue(context, Key.IM_USERUUID.name());
                SharedPreferencesUtils.removeValue(context, Key.IM_USERHEADING.name());
                SharedPreferencesUtils.removeValue(context, Key.IM_USERNICK.name());
                PreferenceUtil.getIntance().removePreference();

                /**IM 退出登录*/
                ImHelper.getIntance().onLoginOut(null);

            }
        }, true);
    }

    /****
     * 本地化 退出登录
     * @param context
     */
    public static void localLogOut(final Context context) {

        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGOUT.getValue(), ""));

        App.getInstance().wangXinServiceId = null;
        App.getInstance().wangXinUserId = null;
        App.getInstance().wangXinPassword = null;
        App.getInstance().wangXinAppKey = null;
        App.getInstance().userId = null;
        App.getInstance().user_token = "";
        App.getInstance().user_inv_code = null;
        App.getInstance().weixin_get_access_token_code = null;

        SharedPreferencesUtils.removeValue(context, Key.USER_INFO_TOKEN.getValue());
        SharedPreferencesUtils.removeValue(context, Key.USER_INFO_ID.getValue());
        SharedPreferencesUtils.removeValue(context, Key.USER_INFO_IDENTITY.getValue());
        SharedPreferencesUtils.removeValue(context, Key.IM_USERNAME.name());
        SharedPreferencesUtils.removeValue(context, Key.IM_USERPASSWORD.name());
        SharedPreferencesUtils.removeValue(context, Key.IM_USERUUID.name());
        SharedPreferencesUtils.removeValue(context, Key.IM_USERHEADING.name());
        SharedPreferencesUtils.removeValue(context, Key.IM_USERNICK.name());

        /**IM 退出登录*/
        ImHelper.getIntance().onLoginOut(new IMDoneListener() {
            @Override
            public void onSuccess() {
                String HuaWeiToken = AppPreference.getIntance().getHXHuaWeiToken();
                if (!TextUtils.isEmpty(HuaWeiToken)) {
                    HMSAgent.Push.deleteToken(HuaWeiToken, null);
                }
                PreferenceUtil.getIntance().removePreference();
            }

            @Override
            public void onFailer(String message) {

            }
        });
    }
}
