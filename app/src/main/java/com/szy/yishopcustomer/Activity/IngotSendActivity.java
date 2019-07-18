package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.UsableIngotModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.IngotHintDialog;
import com.szy.yishopcustomer.View.ShareDialog;
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
 * @role 送元宝 赠送元宝/分享元宝
 * @time 2018 2018/7/6 9:22
 */

public class IngotSendActivity extends Activity implements View.OnClickListener {

    public static final String SEND_INGOT_TYOE = "SendIngotType";

    private boolean isSend = false;
    private ShareDialog mShareDialog;

    @BindView(R.id.toolbar_ingot_send)
    Toolbar mToolbar;
    @BindView(R.id.ingot_send_title)
    TextView mTextView_Title;

    @BindView(R.id.tv_send_ingot_title)
    TextView Tv_SendIngot_Title;
    @BindView(R.id.tv_send_ingot_number)
    TextView Tv_SendIngot_Number;
    @BindView(R.id.lin_ingot_expl)
    LinearLayout mLayout_Expl;
    @BindView(R.id.tv_send_ingot_expl)
    TextView Tv_SendIngot_Expl;

    @BindView(R.id.tv_send_ingot_one)
    TextView Tv_SendIngot_One;
    @BindView(R.id.edt_send_ingot_one)
    CommonEditText Edt_SendIngot_One;
    @BindView(R.id.tv_send_ingot_two)
    TextView Tv_SendIngot_Two;
    @BindView(R.id.edt_send_ingot_two)
    CommonEditText Edt_SendIngot_Two;
    @BindView(R.id.btn_send_ingot)
    Button Btn_SendIngot;
    @BindView(R.id.tv_send_ingot_record)
    TextView Tv_SendIngot_Record;

    @BindView(R.id.tv_send_ingot_hint_title)
    TextView Tv_HintTitle;
    @BindView(R.id.tv_send_ingot_hint)
    TextView Tv_HintText;

    private Intent mIntent = null;

    private String sendName;
    private int sendNumber;

    private int shareTotal;
    private int shareNumber;

    private String shareId, shareImg, guid, invite_code;
    private int user_ingot_number;
    private String mShareUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingot_send);
        ButterKnife.bind(this);

        mIntent = getIntent();
        if (mIntent.getIntExtra(SEND_INGOT_TYOE, 0) == 1) {

            isSend = true;

            mTextView_Title.setText(R.string.ingot_send_donate);
            Tv_SendIngot_Title.setText(R.string.send_number);
            Tv_SendIngot_Expl.setText(R.string.send_expl);

            Tv_SendIngot_One.setText("赠送账号");
            Tv_SendIngot_Two.setText("赠送元宝数");

            Edt_SendIngot_One.setHint("请输入赠送帐号");
            Edt_SendIngot_Two.setHint("请输入赠送元宝数量");

            Btn_SendIngot.setText("确定赠送");
            Tv_SendIngot_Record.setText("账号赠送元宝记录>");

            Tv_HintTitle.setVisibility(View.VISIBLE);
            Tv_HintText.setVisibility(View.VISIBLE);
        } else if (mIntent.getIntExtra(SEND_INGOT_TYOE, 0) == 2) {

            isSend = false;

            mTextView_Title.setText(R.string.ingot_send_share);
            Tv_SendIngot_Title.setText(R.string.share_number);
            Tv_SendIngot_Expl.setText(R.string.share_expl);

            Tv_SendIngot_One.setText("赠送元宝总数");
            Tv_SendIngot_Two.setText("赠送人数");

            Edt_SendIngot_One.setHint("请输入赠送元宝总数");
            Edt_SendIngot_Two.setHint("请输入赠送人数");

            Btn_SendIngot.setText("立即赠送");
            Tv_SendIngot_Record.setText("微信赠送元宝记录>");

            Edt_SendIngot_One.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Btn_SendIngot.setOnClickListener(this);
        mLayout_Expl.setOnClickListener(this);
        Tv_SendIngot_Record.setOnClickListener(this);

        Edt_SendIngot_One.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Edt_SendIngot_One.getText().toString().trim().length() == 0) {
                    Btn_SendIngot.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (Edt_SendIngot_Two.getText().toString().trim().length() > 0 && s.toString().trim().length() > 0) {
                    Btn_SendIngot.setEnabled(true);
                } else {
                    Btn_SendIngot.setEnabled(false);
                }
            }
        });

        Edt_SendIngot_Two.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Edt_SendIngot_One.getText().toString().trim().length() == 0) {
                    Btn_SendIngot.setEnabled(false);
                } else if (Edt_SendIngot_Two.getText().toString().trim().length() == 0) {
                    Btn_SendIngot.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (Edt_SendIngot_One.getText().toString().trim().length() > 0 && s.toString().trim().length() > 0) {
                    Btn_SendIngot.setEnabled(true);
                } else {
                    Btn_SendIngot.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_ingot:

                if (isSend) {
                    //赠送元宝      赠送数量不能大于可用元宝数量
                    sendName = Edt_SendIngot_One.getText().toString().trim();
                    sendNumber = Integer.valueOf(Edt_SendIngot_Two.getText().toString().trim());
                    int usableNumber = Integer.valueOf(App.getInstance().user_ingot_number);

                    if (sendNumber > usableNumber) {
                        Toast.makeText(this, "赠送数量不可大于可赠元宝", Toast.LENGTH_SHORT).show();
                    } else {
                        //赠送元宝
                        sendIngot();
                    }
                } else {
                    //分享元宝      分享元宝数量不能大于可分享元宝数量  分享次数不能大于可分享元宝数量
                    shareTotal = Integer.valueOf(Edt_SendIngot_One.getText().toString().trim());
                    shareNumber = Integer.valueOf(Edt_SendIngot_Two.getText().toString().trim());//分享次数

                    if (shareTotal > user_ingot_number) {
                        Toast.makeText(this, "分享元宝数量不可大于可分享元宝", Toast.LENGTH_SHORT).show();
                    } else if (shareNumber > user_ingot_number) {
                        Toast.makeText(this, "分享次数不可大于可分享元宝", Toast.LENGTH_SHORT).show();
                    } else {
                        //分享元宝
                        shareIngot();
                    }
                }

                break;
            case R.id.lin_ingot_expl:
                IngotHintDialog ingotHintDialog = new IngotHintDialog(this, R.style.HintDialog);
                if (isSend) {
                    ingotHintDialog.setTitle("元宝赠送说明");
                    ingotHintDialog.setContentType(true);
                } else {
                    ingotHintDialog.setTitle("元宝赠送说明");
                    ingotHintDialog.setContentType(false);
                }
                ingotHintDialog.show();
                break;
            case R.id.tv_send_ingot_record:
                mIntent = new Intent(this, SendRecordActivity.class);
                mIntent.putExtra(SEND_INGOT_TYOE, getIntent().getIntExtra(SEND_INGOT_TYOE, 0));
                startActivity(mIntent);
                break;
        }
    }

    private void sendIngot() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_SEND, RequestMethod.POST);
        request.add("giving_user_name", sendName);
        request.add("giving_amount", sendNumber);

        requestQueue.add(HttpWhat.HTPP_INGOT_SEND.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");

                if (code == 0) {
                    Toast.makeText(IngotSendActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(IngotSendActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(IngotSendActivity.this, "网络繁忙,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //分享元宝 分享 微信  微信朋友圈
    private void shareIngot() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_SHARE, RequestMethod.POST);
        request.add("integral_total", shareTotal);
        request.add("num_total", shareNumber);

        requestQueue.add(HttpWhat.HTPP_INGOT_SHARE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");

                if (code == 0) {

                    /****
                     * 分享成功后 需要对分享地址进行加密处理
                     * 然后拼接分享地址 进行分享
                     */
                    JSONObject object = JSONObject.parseObject(response.get()).getJSONObject("data");
                    shareId = object.getString("share_id");
                    guid = object.getString("guid");
                    invite_code = object.getString("invite_code");
                    shareImg = object.getString("headimg");

                    //判断分享内容是否健全
                    if (!TextUtils.isEmpty(shareId) && !TextUtils.isEmpty(guid) && !TextUtils.isEmpty(invite_code) && !TextUtils.isEmpty(shareImg)) {

                        mShareUrl = Config.BASE_URL + Api.API_INGOT_PAGEURL + "shareid=" + shareId + "&invite_guid=" + guid + "&invite_code=" + invite_code;
                        //分享地址加密
                        setShareUrl();
                    } else {

                        if(TextUtils.isEmpty(shareImg)){
                            Toast.makeText(IngotSendActivity.this, "请先设置头像后再分享", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(IngotSendActivity.this, "网络繁忙,请稍候尝试", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(IngotSendActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(IngotSendActivity.this, "网络繁忙,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void setShareUrl() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_ENC_SHAREURL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_ENC_SHAREURL, "GET");
        request.add("U", mShareUrl);

        requestQueue.add(HttpWhat.HTTP_ENCY_SHARE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                
                if(response.responseCode()== Utils.CITY_NET_SUCES){
                    String ency_url = JSONObject.parseObject(response.get()).getString("u");

                    //弹出分享弹框
                    mShareDialog = new ShareDialog(IngotSendActivity.this, R.style.ShareDialog);
                    mShareDialog.mShareUrl = Config.BASE_URL + "/lbsapi/rrp?U=" + ency_url;
                    mShareDialog.mGuid = guid;
                    mShareDialog.mCode = invite_code;
                    mShareDialog.mShareUserImg = shareImg;

                    mShareDialog.show();
                }else{
                    Toast.makeText(IngotSendActivity.this, "分享服务异常,请稍候尝试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(IngotSendActivity.this, "分享异常,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_WEIXIN_SHARE:
            case REQUEST_CODE_WEIXIN_CIRCLE_SHARE:
                shareCallback(resultCode, data);
                break;
        }
    }

    private void shareCallback(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (data.getIntExtra(Key.KEY_SHARE.getValue(), Macro.SHARE_FAILED)) {
                case Macro.SHARE_SUCCEED:
                    Toast.makeText(this, R.string.shareSucceed, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case Macro.SHARE_FAILED:
                    Toast.makeText(this, R.string.shareFailed, Toast.LENGTH_SHORT).show();

                    mShareDialog.dismiss();
                    break;
                case Macro.SHARE_CANCELED:
                    Toast.makeText(this, R.string.shareCanceled, Toast.LENGTH_SHORT)
                            .show();

                    mShareDialog.dismiss();
                    break;
            }
        } else {
            Toast.makeText(this, R.string.shareCanceled, Toast.LENGTH_SHORT).show();
        }
    }

    private UsableIngotModel mIngotModel;

    private void getIngot() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_USABLE, RequestMethod.GET);

        requestQueue.add(HttpWhat.HTTP_INGOT_USABLE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                mIngotModel = JSON.parseObject(response.get(), UsableIngotModel.class);

                if (mIngotModel.getCode() == 0) {
                    user_ingot_number = Integer.valueOf(mIngotModel.getData().getTotal_integral().getIntegral_num());

                    Tv_SendIngot_Number.setText(mIngotModel.getData().getTotal_integral().getIntegral_num());
                    App.getInstance().user_ingot_number = mIngotModel.getData().getTotal_integral().getIntegral_num();

                } else {
                    user_ingot_number = Integer.valueOf(App.getInstance().user_ingot_number);
                    Tv_SendIngot_Number.setText(App.getInstance().user_ingot_number);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Tv_SendIngot_Number.setText(App.getInstance().user_ingot_number);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /***
     * 获取最新元宝数量
     */
    @Override
    protected void onResume() {
        super.onResume();
        getIngot();
    }
}
