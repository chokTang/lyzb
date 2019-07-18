package com.lyzb.jbx.util;

import com.like.utilslib.preference.PreferenceUtil;
import com.lyzb.jbx.model.account.UserModel;
import com.lyzb.jbx.model.cenum.APPTag;
import com.szy.yishopcustomer.Util.App;

/**
 * Created by longshao on 2017/8/1.
 */

public class AppPreference {
    private static AppPreference preference;

    public final static AppPreference getIntance() {
        synchronized (AppPreference.class) {
            if (preference == null) {
                preference = new AppPreference();
            }
        }
        return preference;
    }

    /**
     * 设置是否第一次登陆
     *
     * @param islogin
     */
    public final void setFirstLogin(boolean islogin) {
        PreferenceUtil.getIntance().setBoolean(APPTag.IS_FRIST_ENTER.name(), islogin);
    }

    /**
     * 获取是否第一次登陆
     *
     * @return
     */
    public final boolean getIsFirstLogin() {
        return PreferenceUtil.getIntance().getBoolean(APPTag.IS_FRIST_ENTER.name(), true);
    }

    /**
     * 设置用户登录状态
     *
     * @param state
     */
    public final void setAccountLonginState(boolean state) {
        PreferenceUtil.getIntance().setBoolean(APPTag.LOGIN_STATE.name(), state);
    }

    /**
     * 获取用户登录状态
     *
     * @return
     */
    public final boolean getAccountLonginState() {
        return PreferenceUtil.getIntance().getBoolean(APPTag.LOGIN_STATE.name(), false);
    }


    /**
     * 保存登录用户数据
     *
     * @param model
     */
    public final void setAccountData(UserModel model) {
        PreferenceUtil.getIntance().saveObject(APPTag.ACCOUNT_DATA.name(), model);
    }

    /**
     * 获取登录用户数据
     *
     * @return
     */
    public final UserModel getAccountData() {
        UserModel model = (UserModel) PreferenceUtil.getIntance().readObject(APPTag.ACCOUNT_DATA.name());
        return model == null ? new UserModel() : model;
    }

    /**
     * 保存登录用户密码
     *
     * @param password
     */
    public final void setAccountPassword(String password) {
        PreferenceUtil.getIntance().setString(APPTag.ACCOUNT_PASSWORD.name(), password);
    }

    /**
     * 获取登录用户密码
     *
     * @return
     */
    public final String getAccountPassword() {
        return PreferenceUtil.getIntance().getString(APPTag.ACCOUNT_PASSWORD.name());
    }

    /**
     * 保存登录用户账号
     *
     * @param name
     */
    public final void setAccountName(String name) {
        PreferenceUtil.getIntance().setString(APPTag.ACCOUNT_NAME.name(), name);
    }

    /**
     * 获取登录用户账号
     *
     * @return
     */
    public final String getAccountName() {
        return PreferenceUtil.getIntance().getString(APPTag.ACCOUNT_NAME.name());
    }

    /*--用户当前定位信息开始--*/
    public final void setMeMapLatitude(float latitude) {
        PreferenceUtil.getIntance().setFloat("latitude", latitude);
    }

    public final float getMeMapLatitude() {
        return PreferenceUtil.getIntance().getFloat("latitude");
    }

    public final void setMeMapLongitude(float longitude) {
        PreferenceUtil.getIntance().setFloat("longitude", longitude);
    }

    public final float getMeMapLongitude() {
        return PreferenceUtil.getIntance().getFloat("longitude");
    }

    public final void setMeAddress(String address) {
        PreferenceUtil.getIntance().setString("address_me", address);
    }

    public final String getMeAddress() {
        return PreferenceUtil.getIntance().getString("address_me", "");
    }
    /*--用户当前定位信息结束--*/

    ///首页发布提示3次控制
    public final int getKeyHintThree() {
        return PreferenceUtil.getIntance().getInt("key_hint", 0);
    }


    //设置次数
    public final void setKeyHintThree(int num) {
        PreferenceUtil.getIntance().setInt("key_hint", num);
    }

    ///发布动态提示第一次控制
    public final int getKeyHintOne() {
        return PreferenceUtil.getIntance().getInt("key_hint_one", 0);
    }


    //发布动态提示第一次控制
    public final void setKeyHintOne(int num) {
        PreferenceUtil.getIntance().setInt("key_hint_one", num);
    }


    ///名片个人动态提示第一次控制
    public final int getKeyCardHintOne() {
        return PreferenceUtil.getIntance().getInt("key_card_hint_one", 0);
    }


    //发布个人提示第一次控制
    public final void setKeyCardHintOne(int num) {
        PreferenceUtil.getIntance().setInt("key_card_hint_one", num);
    }


    ///名片官网动态提示第一次控制
    public final int getKeyWebHintOne() {
        return PreferenceUtil.getIntance().getInt("key_web_hint_one", 0);
    }


    //发布官网提示第一次控制
    public final void setKeyWebHintOne(int num) {
        PreferenceUtil.getIntance().setInt("key_web_hint_one", num);
    }


    ///名片发布商品提示气泡
    public final boolean getKeyHintSendProduct() {
        return PreferenceUtil.getIntance().getBoolean("key_is_send_product", true);
    }


    //设置次数
    public final void setKeySendProduct(boolean num) {
        PreferenceUtil.getIntance().setBoolean("key_is_send_product", num);
    }

    //设置环信的头像
    public final void setHxHeaderImg(String header) {
        App.getInstance().headimg = header;
        PreferenceUtil.getIntance().setString("user_hx_header", header);
    }

    public final String getHxHeaderImg() {
        return PreferenceUtil.getIntance().getString("user_hx_header", "");
    }

    //设置环信的昵称
    public final void setHxNickName(String nickName) {
        App.getInstance().nickName = nickName;
        PreferenceUtil.getIntance().setString("user_hx_nickName", nickName);
    }

    public final String getHxNickName() {
        return PreferenceUtil.getIntance().getString("user_hx_nickName", "");
    }

    //设置双首页功能--如果为true表示是工商联盟的首页，如果为false表示商城首页
    public final void setDoubleHome(boolean isGongshang) {
        PreferenceUtil.getIntance().setBoolean("user_choose_gongshang", isGongshang);
    }

    public final boolean getDoubleHome() {
        return PreferenceUtil.getIntance().getBoolean("user_choose_gongshang", true);
    }

    //设置用户是否是会员
    public final void setUserIsVip(boolean isVip) {
        PreferenceUtil.getIntance().setBoolean("user_is_vip", isVip);
    }

    public final boolean getUserIsVip() {
        return PreferenceUtil.getIntance().getBoolean("user_is_vip", false);
    }

    //设置用户是否公开了自己的资料
    public final void setUserShowInfo(boolean isShowInfo) {
        PreferenceUtil.getIntance().setBoolean("user_is_show_info", isShowInfo);
    }

    public final boolean getUserShowInfo() {
        return PreferenceUtil.getIntance().getBoolean("user_is_show_info", false);
    }

    //访客记录中，记录用户设置了意向客户是否需要提示 默认需要
    public final void setUserShowInterAccount(boolean isShow) {
        PreferenceUtil.getIntance().setBoolean("setting_show_inter_account", isShow);
    }

    public final boolean getUserShowInterAccount() {
        return PreferenceUtil.getIntance().getBoolean("setting_show_inter_account", true);
    }

    //访客记录中，是否需要蒙层
    public final void setUserFristInterAccess(boolean isFrist) {
        PreferenceUtil.getIntance().setBoolean("user_first_inter_access", isFrist);
    }

    public final boolean getUserFristInterAccess() {
        return PreferenceUtil.getIntance().getBoolean("user_first_inter_access", true);
    }

    //环信华为消息token
    public final void setHXHuaWeiToken(String token) {
        PreferenceUtil.getIntance().setString("hx_huawei_token", token);
    }

    public final String getHXHuaWeiToken() {
        return PreferenceUtil.getIntance().getString("hx_huawei_token");
    }

    //获取【徐志-java那边的userGuid】
    public final void setUserGuid(String userGuid) {
        PreferenceUtil.getIntance().setString("user_guid", userGuid);
    }

    public final String getUserGuid() {
        return PreferenceUtil.getIntance().getString("user_guid");
    }

    //获取【徐志-java那边的邀请码】
    public final void setInviteCode(String inviteCode) {
        PreferenceUtil.getIntance().setString("user_inviteCode", inviteCode);
        App.getInstance().user_inv_code = inviteCode;
    }

    public final String getInviteCode() {
        return PreferenceUtil.getIntance().getString("user_inviteCode");
    }

    //保存分享语--名片
    public final void setShareCardValue(String cardValue) {
        PreferenceUtil.getIntance().setString("share_card_describe", cardValue);
    }

    public final String getShareCardValue() {
        return PreferenceUtil.getIntance().getString("share_card_describe");
    }

    //保存分享语--动态
    public final void setShareDynamicValue(String dynamicValue) {
        PreferenceUtil.getIntance().setString("share_dynamic_describe", dynamicValue);
    }

    public final String getShareDynamicValue() {
        return PreferenceUtil.getIntance().getString("share_dynamic_describe");
    }

    //是否第一次关注提示
    public final void setFirstFollow(boolean isFirst) {
        PreferenceUtil.getIntance().setBoolean("operation_dynamic_follow", isFirst);
    }

    public final boolean getFirstFollow() {
        return PreferenceUtil.getIntance().getBoolean("operation_dynamic_follow", true);
    }

    //是否第一次收藏提示
    public final void setFirstCollection(boolean isFirst) {
        PreferenceUtil.getIntance().setBoolean("operation_dynamic_collection", isFirst);
    }

    public final boolean getFirstCollection() {
        return PreferenceUtil.getIntance().getBoolean("operation_dynamic_collection", true);
    }
}
