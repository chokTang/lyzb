package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 预存货款迁入成功 ac
 * @time 2018 2018/8/13 16:07
 */

public class MoneyIntoHintActivity extends Activity {

    @BindView(R.id.img_money_hint_back)
    ImageView mImageView_Back;
    @BindView(R.id.tv_into_balance_money)
    TextView mTextView_Money;
    @BindView(R.id.btn_into_go_shop)
    Button mButton_Shop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_into_hint);
        ButterKnife.bind(this);

        mImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mButton_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回首页
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
                startActivity(new Intent().setClass(MoneyIntoHintActivity.this, RootActivity.class));
                finish();
            }
        });

        getMoneyData();
    }

    private void getMoneyData() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_MONEY_SUCCESS, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        request.add("user_id", App.getInstance().userId);
        requestQueue.add(HttpWhat.HTTP_MONEY_SUCCESS.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");
                JSONObject object = JSONObject.parseObject(response.get()).getJSONObject("data");

                if (code == 0) {
                    mTextView_Money.setText(object.getString("cur_balance"));
                } else {
                    Toast.makeText(MoneyIntoHintActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.d("WYX","data/"+response.responseCode()+"/"+response.getException());
                Toast.makeText(MoneyIntoHintActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
