package com.szy.yishopcustomer.Fragment;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.BarCodeActivity;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GetCodeModel;
import com.szy.yishopcustomer.ResponseModel.ScanCodeIndexModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.QRCodeUtil;
import com.szy.yishopcustomer.Util.Utils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Smart on 2018/1/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserPayFragment extends YSCBaseFragment {

    @BindView(R.id.imageViewBackground)
    ImageView imageViewBackground;

    @BindView(R.id.imageViewPayBarcode)
    ImageView imageViewPayBarcode;
    @BindView(R.id.imageViewPayQrcode)
    ImageView imageViewPayQrcode;

    @BindView(R.id.textViewUserBalance)
    TextView textViewUserBalance;

    @BindView(R.id.textViewPayBarcode)
    TextView textViewPayBarcode;
    @BindView(R.id.textViewPayBarcodeSee)
    TextView textViewPayBarcodeSee;

    @BindView(R.id.textViewMemshipLevel)
    TextView textViewMemshipLevel;

    @BindView(R.id.fragment_user_top_avatarCircleImageView)
    public CircleImageView avatarImageView;

    @BindView(R.id.textViewAllMoneyFormat)
    TextView textViewAllMoneyFormat;
    @BindView(R.id.textViewUserMoneyLimit)
    TextView textViewUserMoneyLimit;

    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;

    private String code = "";

    Timer timer;
    TimerTask timerTask;

    GradientDrawable gd;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                getCode();
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_user_pay;

        gd = new GradientDrawable();

        int strokeWidth = 0; //
        int roundRadius = Utils.dpToPx(getContext(), 20); //
        int strokeColor = Color.parseColor("#f23030");
        int fillColor = Color.parseColor("#f23030");


        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        textViewPayBarcodeSee.setOnClickListener(this);

        imageViewBack.setOnClickListener(this);

        refresh();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(code)) {
            getCode();
        }

        startTimer();
    }

    private void startTimer() {

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        timer.schedule(timerTask, 1000 * 60 * 2, 1000 * 60 * 2);

    }


    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewPayBarcodeSee:
                openBarCodeActivity();
                break;
            case R.id.imageViewBack:
                getActivity().finish();
            default:
                super.onClick(view);
                break;
        }
    }

    public void openScanActivity() {
        if (cameraIsCanUse()){
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            intent.putExtra(Key.KEY_TYPE.getValue(),ScanActivity.TYPE_PAYMENT);
            startActivity(intent);
        }else {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .noCameraPermission));
        }

    }

    public void openBarCodeActivity() {
        if (!TextUtils.isEmpty(code)) {
            Intent intent = new Intent(getActivity(), BarCodeActivity.class);
            intent.putExtra(Key.KEY_BARCODE.getValue(), code);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        timer.cancel();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_SCAN_CODE_INDEX, HttpWhat.HTTP_SCAN_CODE_INDEX
                .getValue());
        addRequest(request);
    }

    private void getCode() {
        CommonRequest request = new CommonRequest(Api.API_USER_CARD_GET_CODE, HttpWhat.HTTP_CARD_GET_CODE
                .getValue());
        addRequest(request);
    }


    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SCAN_CODE_INDEX:
                refreshCallback(response);
                break;
            case HTTP_CARD_GET_CODE:
                getCodeCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, ScanCodeIndexModel.class, new HttpResultManager.HttpResultCallBack<ScanCodeIndexModel>() {
            @Override
            public void onSuccess(ScanCodeIndexModel back) {
                textViewUserBalance.setText(back.data.user_info.all_money_format);
                textViewAllMoneyFormat.setText(back.data.user_info.user_money_format);
                textViewUserMoneyLimit.setText(back.data.user_info.user_money_limit_format);

                textViewMemshipLevel.setText(back.data.user_info.user_rank.rank_name + "：" + back.data.user_info.user_name);
                if (!TextUtils.isEmpty(back.data.scan_code_bgcolor)) {
                    try {
                        int color = Color.parseColor(back.data.scan_code_bgcolor);
                        gd.setColor(color);
                    } catch (Exception e) {
                    }
                }

                if (!TextUtils.isEmpty(back.data.scan_code_bgimage)) {
                    ImageLoader.loadImage(Utils.urlOfImage(back.data.scan_code_bgimage), new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);

//                            imageViewBackground.setBackground(null);
                            imageViewBackground.setImageBitmap(loadedImage);
                        }
                    });
                } else if (!TextUtils.isEmpty(back.data.scan_code_bgcolor)) {
                    try {
                        int color = Color.parseColor(back.data.scan_code_bgcolor);

//                        imageViewBackground.setImageDrawable(null);
                        imageViewBackground.setBackgroundColor(color);
                    } catch (Exception e) {
                    }
                } else {
                    imageViewBackground.setBackgroundColor(Color.parseColor("#f23030"));
                }

                textViewMemshipLevel.setBackground(gd);
                ImageLoader.displayImage(Utils.urlOfImage(back.data.user_info.headimg, false), avatarImageView);


                setUpData(back.data.model.code);
            }
        });
    }

    private void getCodeCallback(String response) {
        HttpResultManager.resolve(response, GetCodeModel.class, new HttpResultManager.HttpResultCallBack<GetCodeModel>() {
            @Override
            public void onSuccess(GetCodeModel back) {
                setUpData(back.data.code);
            }
        });
    }

    private void setUpData(String code) {
        this.code = code;
        int barcodeWidth = Utils.dpToPx(getContext(), 290);
        int barcodeHeight = Utils.dpToPx(getContext(), 70);
        imageViewPayBarcode.setImageBitmap(QRCodeUtil.creatBarcode(getContext(), code, barcodeWidth, barcodeHeight, false));
        textViewPayBarcode.setText(code.replaceAll("(\\d{4})\\d+(\\d{4})", "$1**********$2"));

        imageViewPayQrcode.setImageBitmap(CodeUtils.createImage(code, 400, 400, null));

    }

}
