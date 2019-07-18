package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.View.HintDialog;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 集食惠资金迁入 ac
 * @time 2018 2018/8/13 15:08
 */

public class MoneyIntoActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.img_jsh_money_back)
    ImageView mImageView_Back;
    @BindView(R.id.tv_jsh_balance_money)
    TextView mTextView_Money;
    @BindView(R.id.btn_jsh_money_into)
    Button mButton_Into;

    private String mMoney;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_into);
        ButterKnife.bind(this);

        mImageView_Back.setOnClickListener(this);
        mButton_Into.setOnClickListener(this);

        getMoneyData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_jsh_money_back:
                finish();
                break;
            case R.id.btn_jsh_money_into:
                //弹出框提示
                final HintDialog dialog = new HintDialog(this);
                dialog.mStringTitle = "是否确认集食惠预存货款迁入集宝箱的余额中";
                dialog.show();

                dialog.onViewClick(new HintDialog.mViewOnClick() {
                    @Override
                    public void onCancle() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onEnsure() {
                        intoMoney();
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    private void getMoneyData() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_MONEY_DATA, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        request.add("user_id", App.getInstance().userId);

        requestQueue.add(HttpWhat.HTTP_MONEY_DATA.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");
                JSONObject object = JSONObject.parseObject(response.get()).getJSONObject("data");

                if (code == 0) {
                    mMoney = object.getString("balance");
                    mTextView_Money.setText(mMoney);

                    if (Double.parseDouble(mMoney) > 0) {
                        mButton_Into.setEnabled(true);
                    } else {
                        mButton_Into.setEnabled(false);
                    }

                } else {
                    Toast.makeText(MoneyIntoActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(MoneyIntoActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void intoMoney() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_MONEY_INTO, RequestMethod.POST);
        request.setUserAgent("szyapp/android");
        request.add("user_id", App.getInstance().userId);
        requestQueue.add(HttpWhat.HTTP_MONEY_INTO.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");
                if (code == 0) {
                    Intent intent = new Intent(MoneyIntoActivity.this, MoneyIntoHintActivity.class);
                    startActivity(intent);
                    Toast.makeText(MoneyIntoActivity.this, "迁入成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MoneyIntoActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(MoneyIntoActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
