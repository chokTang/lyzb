package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Activity.WeiXinShareActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Util.Utils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;

/**
 * @author wyx
 * @role
 * @time 2018 2018/7/13 14:19
 */

public class ShareDialog extends Dialog {

    private Activity mActivity;
    private Context mContext;

    private LinearLayout mLayout_ShareWx;
    private LinearLayout mLayout_ShareWxFr;
    private TextView mTextView_Cancle;

    private ArrayList<String> mShareData = new ArrayList<>();

    public String mShareUrl, mShareUserImg, mGuid, mCode;

    public ShareDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ShareDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected ShareDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_share_dialog, null);
        setContentView(view);

        mActivity = (Activity) mContext;

        //设置 固定在底部
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        mTextView_Cancle = (TextView) view.findViewById(R.id.tv_share_cancle);
        mTextView_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mLayout_ShareWx = (LinearLayout) view.findViewById(R.id.lin_share_wx_dialog);
        mLayout_ShareWxFr = (LinearLayout) view.findViewById(R.id.lin_share_wxf_dialog);

        //设置分享内容
        mShareData.add(mShareUrl + "&invite_code=" + mCode + "&invite_guid=" + mGuid);
        mShareData.add("快来抢元宝！1元宝=1元现金抵扣券哦~");
        mShareData.add("给大家发福利啦！快来抢抢抢吧！");
        mShareData.add(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, mShareUserImg));

        Log.d("wyx", "分享地址-" + mShareUrl);

        mLayout_ShareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWX();
            }
        });

        mLayout_ShareWxFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shartWXCircle();
            }
        });
    }

    private void shareWX() {


        if (Utils.isWeixinAvilible(mActivity)) {
            Intent intentWeixin = new Intent();
            intentWeixin.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), mShareData);
            intentWeixin.setClass(mActivity, WeiXinShareActivity.class);
            mActivity.startActivityForResult(intentWeixin, RequestCode.REQUEST_CODE_WEIXIN_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(mActivity, "请先安装微信客户端");
        }
    }

    private void shartWXCircle() {
        if (Utils.isWeixinAvilible(mActivity)) {
            Intent intentWeixinCircle = new Intent();
            intentWeixinCircle.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), mShareData);
            intentWeixinCircle.putExtra(Key.KEY_SCENE.getValue(), SendMessageToWX.Req.WXSceneTimeline);
            intentWeixinCircle.setClass(mActivity, WeiXinShareActivity.class);
            mActivity.startActivityForResult(intentWeixinCircle, RequestCode.REQUEST_CODE_WEIXIN_CIRCLE_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(mActivity, "请先安装微信客户端");
        }
    }

}
