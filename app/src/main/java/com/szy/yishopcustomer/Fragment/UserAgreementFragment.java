package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.webkit.WebView;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by liwei on 2016/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserAgreementFragment extends YSCBaseFragment {
    @BindView(R.id.webView)
    public WebView mWebView;
    private String user_agreement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.activity_user_agreement;

        Intent intent = getActivity().getIntent();
        user_agreement = intent.getStringExtra(Key.KEY_AGREEMENT.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = super.onCreateView(inflater, container, savedInstance);
        mWebView.loadData(user_agreement, "text/html; charset=UTF-8", null);
        return v;
    }
}
