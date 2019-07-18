package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/11/14.
 */

public class PayPasswordView extends Dialog {

    @BindView(R.id.textView_forget_paypwd)
    View textView_forget_paypwd;
    @BindView(R.id.button_close_paypwd)
    View button_close_paypwd;
    @BindView(R.id.button_modify_paypwd)
    View button_modify_paypwd;

    private Context mContext;
    private View.OnClickListener mListener;

    public PayPasswordView(Context context,View.OnClickListener listener) {
        super(context, R.style.dialog);
        mListener = listener;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_pay_password);
        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的5/6
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失


        button_modify_paypwd.setOnClickListener(mListener);
        button_close_paypwd.setOnClickListener(mListener);
        textView_forget_paypwd.setOnClickListener(mListener);
    }
}
