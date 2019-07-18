package com.like.longshaolib.net.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.R;
import com.like.utilslib.screen.DensityUtil;

/**
 * 网络请求等待框
 * Created by longshao on 2017/3/20.
 */

public class HttpResultDialog extends Dialog {

    private ImageView dialog_iv;
    private AnimationDrawable animationDrawable;
    private Context context;
    private LinearLayout request_layout;
    private TextView dialog_tv;
    private String[] contentStrs = new String[]{"加载中", "加载中.", "加载中..", "加载中..."};
    private int timeStr = 100;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int index = Math.abs(timeStr % 4);
            dialog_tv.setText(contentStrs[index]);
            timeStr--;
            handler.sendMessageDelayed(handler.obtainMessage(0x123), 1000);
            return true;
        }
    });

    public HttpResultDialog(Context context) {
        super(context, R.style.waitDialog);
        setCanceledOnTouchOutside(false);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);//设置返回键不可用
        initView();
        initData();
    }

    private void initData() {
        dialog_iv.setBackgroundResource(R.drawable.imgloading);
        animationDrawable = (AnimationDrawable) dialog_iv.getBackground();
        animationDrawable.start();
        handler.sendMessage(handler.obtainMessage(0x123));
    }

    private void initView() {
        setContentView(R.layout.dialog_httpresult_layout);
        dialog_iv = (ImageView) findViewById(R.id.dialog_iv);
        dialog_tv = (TextView) findViewById(R.id.dialog_tv);
        dialog_tv = (TextView) findViewById(R.id.dialog_tv);
        request_layout = (LinearLayout) findViewById(R.id.request_layout);

        final int width = DensityUtil.dpTopx(140);
        final int height = DensityUtil.dpTopx(140);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
//        request_layout.setLayoutParams(params);
    }

    @Override
    public void cancel() {
        super.cancel();
        if (handler != null) {
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
