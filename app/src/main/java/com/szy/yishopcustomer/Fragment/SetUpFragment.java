package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.android.hms.agent.HMSAgent;
import com.like.utilslib.preference.PreferenceUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AppInfo.ResponseAppInfoModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.DataCleanManager;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.szy.yishopcustomer.View.UpdateDialog;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.cookie.DBCookieStore;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 16/5/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SetUpFragment extends YSCBaseFragment {
    private static final String TAG = "SetUpFragment";
    @BindView(R.id.relativeLayout3)
    RelativeLayout mCblogbutton;
    @BindView(R.id.fragment_setup_clear_number)
    TextView mSettingCache;
    @BindView(R.id.fragment_setup_clear)
    RelativeLayout mClear;
    @BindView(R.id.fragment_setup_version)
    TextView mVersion;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    DataCleanManager dataCleanManager = new DataCleanManager();
    private String cache = null;

    //获取缓存值并赋给页面
    public String getCache() {
        try {
            cache = DataCleanManager.getTotalCacheSize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSettingCache.setText(cache);
        return cache;
    }

    private void singOff() {
        CommonRequest request = new CommonRequest(Api.API_SIGN_OFF, HttpWhat.HTTP_SIGN_OFF.getValue(), RequestMethod.POST);
        addRequest(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_setup_clear:
                DataCleanManager.clearAllCache(getActivity());
                getCache();
                Utils.toastUtil.showToast(getActivity(), "清理完成");
                break;
            case R.id.relativeLayout3:
                refresh();
                break;
            case R.id.tv_logout://退出登录
                showConfirmDialog(R.string.confirmSignOff, ViewType.VIEW_TYPE_LOGOUT.ordinal());
                break;
            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_LOGOUT:
                dismissConfirmDialog();
                singOff();
                break;
        }
    }

    void refresh() {
        CommonRequest request = new CommonRequest(Api.API_APP_INFO, HttpWhat.HTTP_APP_INFO.getValue(), RequestMethod.GET);
        addRequest(request);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_APP_INFO:
                refreshCallback(response);
                break;
            case HTTP_SIGN_OFF:
                signOffCallback(response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, ResponseAppInfoModel.class, new HttpResultManager.HttpResultCallBack<ResponseAppInfoModel>() {
            @Override
            public void onSuccess(ResponseAppInfoModel back) {
                ResponseAppInfoModel mResponseAppInfoModel = back;
                if (Utils.compareTo(mResponseAppInfoModel.data.latest_version, Utils.getVersionName(getContext())) > 0) {
                    UpdateDialog updateDialog = new UpdateDialog(getContext(), back);
                    updateDialog.show();
                } else {
                    toast("您所使用的已是最新版本。");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mCblogbutton.setOnClickListener(this);
        mClear.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        getCache();
        mVersion.setText(Utils.getVersionName(getContext()));
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_setup;
    }


    /***退出登录 **/
    private void signOffCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager.HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGOUT.getValue(), back.message));
                Toast.makeText(getContext(), back.message, Toast.LENGTH_SHORT).show();
                App.getInstance().wangXinServiceId = "";
                App.getInstance().wangXinUserId = "";
                App.getInstance().wangXinPassword = "";
                App.getInstance().wangXinAppKey = "";
                App.getInstance().userId = "";
                App.getInstance().user_inv_code = null;
                App.getInstance().user_ingot_number = "0";
                App.getInstance().user_token = "";

                SharedPreferencesUtils.removeValue(getActivity(), Key.USER_INFO_TOKEN.getValue());
                SharedPreferencesUtils.removeValue(getActivity(), Key.USER_INFO_ID.getValue());
                SharedPreferencesUtils.removeValue(getActivity(), Key.USER_INFO_IDENTITY.getValue());

                SharedPreferencesUtils.removeValue(getActivity(), Key.IM_USERNAME.name());
                SharedPreferencesUtils.removeValue(getActivity(), Key.IM_USERPASSWORD.name());
                SharedPreferencesUtils.removeValue(getActivity(), Key.IM_USERUUID.name());
                SharedPreferencesUtils.removeValue(getActivity(), Key.IM_USERNICK.name());
                SharedPreferencesUtils.removeValue(getActivity(), Key.IM_USERHEADING.name());

                /**IM 退出登录*/
                ImHelper.getIntance().onLoginOut(new IMDoneListener() {
                    @Override
                    public void onSuccess() {
                        EventBus.getDefault().post(new CommonEvent(EventWhat.HX_UNLISTINENER
                                .getValue()));

                        DataCleanManager.clearAllCache(getActivity());
                        DBCookieStore store = new DBCookieStore(getContext());
                        store.removeAll();//删除所有的cookie
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
        }, true);
    }


}
