package com.szy.yishopcustomer.Fragment;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import me.zongren.pullablelayout.Constant.Status;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by 宗仁 on 2017/1/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public abstract class YSCBaseFragment extends CommonFragment {

    View activity_common_offline_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(!Utils.isNetAvailable(getActivity())) {
//            showOfflineView();
//        } else {
//            hideOfflineView();
//        }

        mRequestListener = new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                updateDialog();
                onRequestStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                hideOfflineView();
                try {
                    String responseString = response.get();
                    //com.alibaba.fastjson.JSON.parseObject(responseString);
                    Log.d("whatValue", what + "");
                    Log.d("succeedValue", responseString);

                    /*** 登录API 存入identity **/
                    if (what == HttpWhat.HTTP_LOGIN.getValue() || what == HttpWhat.HTTP_LOGIN_OTHER.getValue()) {
                        /****
                         * 拿到identity
                         *
                         */
                        List<HttpCookie> cookies = response.getHeaders().getCookies();
                        for (HttpCookie httpCookie : cookies) {
                            if (httpCookie.getName().equals("_identity")) {

                                try {
                                    String identity = URLEncoder.encode(httpCookie.getValue(), "UTF-8");
                                    SharedPreferencesUtils.setParam(getActivity(), Key.USER_INFO_IDENTITY.getValue(), identity);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                    onRequestSucceed(what, responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showOfflineView();
                onRequestFailed(what, response.get());
            }

            @Override
            public void onFinish(int what) {
                mRequests.remove(what);
                updateDialog();
                onRequestFinish(what);

//                updateColorPrimary(root_view);
            }
        };
    }

    private PullableLayout getPullableLayout(View v) {
        if (v == null) {
            return null;
        }

        if (v instanceof PullableLayout) {
            return (PullableLayout) v;
        }

        ViewGroup viewGroup = (ViewGroup) v;
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof PullableLayout) { // 若是Button记录下
                return (PullableLayout) view;
            } else if (view instanceof ViewGroup) {
                // 若是布局控件（LinearLayout或RelativeLayout）,继续查询子View
                PullableLayout temp = this.getPullableLayout(view);
                if (temp != null) {
                    return temp;
                }
            }
        }

        return null;
    }


    //用来判断 当下拉加载时隐藏页面中间的加载样式
    PullableLayout mTopPullableLayout;

    private View root_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        root_view = view;

        //循环获取是否有mPullableLayout控件
        mTopPullableLayout = getPullableLayout(view);

        RelativeLayout rootView = new RelativeLayout(container.getContext());
        rootView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        rootView.addView(view);

        activity_common_offline_view = view.findViewById(R.id.activity_common_offline_view);

        if (activity_common_offline_view == null) {
            activity_common_offline_view = inflater.inflate(R.layout.offline_view, container, false);

//            ViewGroup parent = (ViewGroup) activity_common_offline_view.getParent();
//            if (parent != null) {
//                container.removeAllViews();
//            }

            rootView.addView(activity_common_offline_view);
        }

        activity_common_offline_view.setVisibility(View.GONE);
        View empty_view_button = activity_common_offline_view.findViewById(R.id.empty_view_button);
        empty_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOfflineViewClicked();
            }
        });


        return rootView;
    }

    @Override
    public void showOfflineView() {
        if (activity_common_offline_view != null) {
            activity_common_offline_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideOfflineView() {
        if (activity_common_offline_view != null) {
            activity_common_offline_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }


    void refresh() {
    }


    public void updateDialog() {
        boolean hasAlarmRequests = false;
        for (CommonRequest request : mRequests.values()) {
            if (request.alarm) {
                hasAlarmRequests = true;
                break;
            }
        }

        if (hasAlarmRequests) {
            if (!mProgress.isShowing() && isVisible()) {
                if (mTopPullableLayout != null) {
                    Status mStatus = (Status) Utils.getFieldValueByType(Status.class, mTopPullableLayout.topComponent);
                    if (mStatus != Status.LOAD) {
                        mProgress.show();
                    }
                } else {
                    mProgress.show();
                }
            }
        } else {
            if (mProgress.isShowing()) {
                mProgress.dismiss();
            }
        }
    }

    void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void updateColorPrimary(View view) {
        for (View v : getAllChildViews(view)) {

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
//                ColorStateList colorStateList = tv.getTextColors();
//                colorStateList.getColors();

                if (tv.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                    tv.setTextColor(App.getInstance().colorPrimary);
                }
                continue;
            }

//            if(v instanceof RecyclerView) {
//                RecyclerView recyclerView = ((RecyclerView)v);
//                for(int i = 0 ; i < recyclerView.getChildCount() ; i ++) {
//                    updateColorPrimary(recyclerView.getChildAt(i));
//                }
//                continue;
//            }

//            if(v instanceof ViewGroup) {
//                Log.e("TTT","__" + v.getClass().getSimpleName() + "__" + (v.getBackground() == null));
//                if(v.getBackground() != null && getResources().getDrawable(R.color.colorPrimary).equals(v.getBackground().getCurrent())) {
//                    Log.e("TTT","1执行了" + v.getClass().getSimpleName());
//                    v.setBackgroundColor(App.getInstance().colorPrimary);
//                }}
//            continue;
        }
    }

    /**
     * 获取全部的子View
     *
     * @param view
     * @return
     */
    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    /**
     * 解决SDK 23以下 判断是否拥有相机权限
     * 抛异常方式
     *
     * @return
     */
    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private final int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime;

    /**
     * 判断是否是多次点击
     *
     * @return
     */
    public boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        Log.d("isFastClick", flag + "");
        return flag;
    }




    /**
     * 判断是否拥有权限
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * 请求权限
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(getActivity(), permissions, code);
        Utils.toastUtil.showToast(getActivity(), "如果拒绝授权,会导致应用无法正常使用");
    }

    /**
     * 请求权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

//说明：
//Constants.CODE_CAMERA
//这是在外部封装的一个常量类，里面有许多静态的URL以及权限的CODE，可以自定义
//但是在调用的时候，记得这个CODE要和你自己定义的CODE一一对应
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
