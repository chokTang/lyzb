package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.HomeActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.Guide.Model;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 广告页面 ac (在引导页之后)
 * @time 2018 2018/8/17 13:43
 */

public class AdsActivity extends Activity {

    private static final int REQUEST_GUIDE = 10;
    private boolean isGuide_Page = false;
    private Model mModel;

    private CountDownTimer mDownTimer;
    private Intent mIntent;

    @BindView(R.id.img_ads_img)
    ImageView mImageView_Img;

    @BindView(R.id.frame_skip)
    FrameLayout mLayout_Skip;
    @BindView(R.id.tv_ads_skip)
    TextView mTextView_Skip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**隐藏状态栏 5.0以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_ads);
        ButterKnife.bind(this);

        //设置为半透明
        mTextView_Skip.getBackground().setAlpha(120);

        GlideApp.with(this)
                .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, App.getInstance().ads_url))
                .centerCrop()
                .error(R.mipmap.img_empty)
                .into(mImageView_Img);

        if (!TextUtils.isEmpty(App.getInstance().ads_skip_time)) {
            mDownTimer = new CountDownTimer(Integer.parseInt(App.getInstance().ads_skip_time) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextView_Skip.setText(millisUntilFinished / 1000 + "s 跳过");
                }

                @Override
                public void onFinish() {
                    openActivity();
                }
            };
            mDownTimer.start();
        }


        if (App.getInstance().isGuide) {
            getGuideData();
        }

        //跳过
        mLayout_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDownTimer != null) {
                    mDownTimer.cancel();
                    mDownTimer = null;
                }
                openActivity();
            }
        });

        mImageView_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到H5页面 判断url地址来源 同城or零售
                if (!TextUtils.isEmpty(App.getInstance().ads_link)) {
                    //倒计时停止
                    mDownTimer.cancel();
                    //处理php那边的链接
                    String goodId = Utils.getGoodId(App.getInstance().ads_link);
                    if (!TextUtils.isEmpty(goodId)) {
                        mIntent = new Intent(AdsActivity.this, GoodsActivity.class);
                        mIntent.putExtra(Key.KEY_GOODS_ID.getValue(), goodId);
                    } else {
                        if (Utils.verCityLifeUrl(App.getInstance().ads_link)) {
                            mIntent = new Intent(AdsActivity.this, ProjectH5Activity.class);
                        } else {
                            mIntent = new Intent(AdsActivity.this, YSCWebViewActivity.class);
                        }
                        mIntent.putExtra(Key.KEY_URL.getValue(), App.getInstance().ads_link);
                        App.getInstance().ads_h5 = true;
                    }
                    Intent mainIntent = new Intent(AdsActivity.this, HomeActivity.class);
                    startActivities(new Intent[]{mainIntent, mIntent});
                    finish();
                }
            }
        });
    }

    private void getGuideData() {

        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_APP_GUIDE, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        requestQueue.add(HttpWhat.HTTP_GUIDE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                mModel = JSON.parseObject(response.get(), Model.class);
                if (mModel.data.is_guide_open.equals("1") && !Utils.isNull(mModel.data.img_list)) {

                    boolean guide_tag = Utils.getBoolFromSharedPreferences(getApplicationContext(), Key.KEY_IS_USED.toString());
                    if (guide_tag) {
                        isGuide_Page = true;
                    } else {
                        isGuide_Page = false;
                    }

                } else {
                    isGuide_Page = false;
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                isGuide_Page = false;
            }

            @Override
            public void onFinish(int what) {
                requestQueue.stop();
            }
        });
    }


    private void openGuideActivity(Model model) {
        Intent intent = new Intent(this, GuideActivity.class);
        ArrayList<String> imageList = new ArrayList<>();
        imageList.addAll(model.data.img_list);
        intent.putStringArrayListExtra(Key.KEY_GUIDE_IMAGES.getValue(), imageList);
        intent.putExtra(Key.KEY_GUIDE_BUTTON.getValue(), model.data.app_enter_button);
        startActivity(intent);
        //startActivityForResult(intent, REQUEST_GUIDE);
    }

    private void openActivity() {
        if (isGuide_Page) {
            openGuideActivity(mModel);
        } else {
            Intent intent = new Intent(AdsActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownTimer != null) {
            mDownTimer.cancel();
            mDownTimer = null;
        }
    }
}
