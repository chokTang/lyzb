package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GetCodeModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.QRCodeUtil;
import com.szy.yishopcustomer.Util.Utils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Smart on 2018/1/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserCardFragment extends YSCBaseFragment {

    @BindView(R.id.imageViewShopLogo)
    ImageView imageViewShopLogo;
    @BindView(R.id.textViewCardNumber)
    TextView textViewCardNumber;

    @BindView(R.id.imageViewPayBarcode)
    ImageView imageViewPayBarcode;
    @BindView(R.id.imageViewPayQrcode)
    ImageView imageViewPayQrcode;

    @BindView(R.id.textViewPayBarcode)
    TextView textViewPayBarcode;

    @BindView(R.id.textViewQuery)
    TextView textViewQuery;

    private String code = "";

    Timer timer;
    TimerTask timerTask;

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
        mLayoutId = R.layout.fragment_user_card;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        textViewQuery.setOnClickListener(this);
        refresh();

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

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewQuery:
                getActivity().finish();
                break;
            default:
                super.onClick(view);
                break;
        }

    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_CARD, HttpWhat.HTTP_USER_CARD
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
            case HTTP_USER_CARD:
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
//        HttpResultManager.resolve(response, ScanCodeIndexModel.class, new HttpResultManager.HttpResultCallBack<ScanCodeIndexModel>() {
//            @Override
//            public void onSuccess(ScanCodeIndexModel back) {
//                textViewCardNumber.setText("No." + back.data.card_sn_format);
//                setUpData(back.data.model.code);
//            }
//        });
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

        int barcodeWidth = Utils.dpToPx(getContext(), 340);
        imageViewPayBarcode.setImageBitmap(QRCodeUtil.creatBarcode(getContext(), code, barcodeWidth, barcodeWidth / 5, false));
        textViewPayBarcode.setText(code.replaceAll("(\\d{4})\\d+(\\d{4})", "$1**********$2"));

        imageViewPayQrcode.setImageBitmap(CodeUtils.createImage(code, 400, 400, null));

    }

}
