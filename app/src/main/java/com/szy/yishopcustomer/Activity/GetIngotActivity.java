package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 赚元宝 activity
 * @time 2018 14:32
 */

public class GetIngotActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.toolbar_getingot)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_getingot_textview)
    TextView mToolbar_TextView;

    @BindView(R.id.img_getingot_buy)
    ImageView mImageView_Buy;
    @BindView(R.id.img_getingot_share)
    ImageView mImageView_Share;
    @BindView(R.id.img_getingot_reg)
    ImageView mImageView_Reg;

    Intent mIntent = null;
    boolean isInvCode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getingot);
        ButterKnife.bind(this);

        mToolbar_TextView.setText(R.string.get_ingot);
        mImageView_Buy.setOnClickListener(this);
        mImageView_Share.setOnClickListener(this);
        mImageView_Reg.setOnClickListener(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (App.getInstance().isLogin()) {
            getUserCode();
        }

        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_getingot_buy:
                mIntent = new Intent(this, GoodsListActivity.class);
                mIntent.putExtra(Key.KEY_KEYWORD.getValue(), "");
                startActivity(mIntent);
                break;
            case R.id.img_getingot_share:
                if (App.getInstance().isLogin()) {

                    if (isInvCode) {
                        mIntent = new Intent(this, ShareIngotActivity.class);
                        startActivity(mIntent);
                    } else {
                        Toast.makeText(GetIngotActivity.this, "邀请码获取失败,请稍后尝试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mIntent = new Intent(this, LoginActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.img_getingot_reg:
                if (App.getInstance().isLogin()) {
                    mIntent = new Intent(this, RegisterSuccessActivity.class);
                    mIntent.putExtra("isLogin", "1");
                    mIntent.putExtra("ingotNumber", App.getInstance().user_ingot_number);
                    startActivity(mIntent);
                } else {
                    mIntent = new Intent(this, RegisterActivity.class);
                    startActivity(mIntent);
                }
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                getUserCode();
                break;
            default:
                break;
        }
    }

    public void getUserCode() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(Api.API_USER_CODE, RequestMethod.GET);
        request.add("user_id", App.getInstance().userId);
        requestQueue.add(1, request, new SimpleResponseListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);

                JSONObject object = JSONObject.parseObject(response.get());
                if (object.getInteger("code") == 0) {
                    App.getInstance().user_inv_code = JSONObject.parseObject(JSONObject.parseObject(response.get()).getString("data")).getString("invite_code");
                    AppPreference.getIntance().setInviteCode(App.getInstance().user_inv_code);
                    isInvCode = true;
                } else {
                    isInvCode = false;
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                isInvCode = false;
            }
        });
    }
}
