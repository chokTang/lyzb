package com.like.longshaolib.dialog.original;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.like.longshaolib.R;

/**
 * 网络请求等待框
 * Created by longshao on 2017/3/20.
 */

public class HttpProessDialog extends Dialog {

    private TextView dialog_tv;
    private TextView dialog_progress;
    private String mValue;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            dialog_progress.setText(msg.obj.toString() + "%");
            return false;
        }
    });

    public HttpProessDialog(Context context, String value) {
        super(context, R.style.waitDialog);
        setCanceledOnTouchOutside(false);
        this.mValue = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);//设置返回键不可用
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_httpprogess_layout);
        dialog_tv = (TextView) findViewById(R.id.dialog_tv);
        dialog_progress = (TextView) findViewById(R.id.dialog_progress);

        dialog_tv.setText(mValue);
    }

    public void setProress(String value) {
        handler.sendMessage(handler.obtainMessage(0, value));
    }

    public void setText(String value) {
        dialog_tv.setText(value);
    }

    @Override
    public void dismiss() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.dismiss();
    }
}
