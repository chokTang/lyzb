package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.D;
import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 按钮 提示 dialog
 * @time
 */

public class ButtonDialog extends Dialog {

    private Context mContext;

    private String dialog_title;
    private int dialog_img;
    private String dialog_text;
    private String dialog_btn_text;

    private TextView tv_btn_dialog_title;
    private ImageView img_btn_dialog;
    private TextView tv_btn_dialog_text;
    private TextView tv_btn_dialog_ansure;

    public ButtonDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ButtonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected ButtonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public String getDialog_title() {
        return dialog_title;
    }

    public void setDialog_title(String dialog_title) {
        this.dialog_title = dialog_title;
    }

    public int getDialog_img() {
        return dialog_img;
    }

    public void setDialog_img(int dialog_img) {
        this.dialog_img = dialog_img;
    }

    public String getDialog_text() {
        return dialog_text;
    }

    public void setDialog_text(String dialog_text) {
        this.dialog_text = dialog_text;
    }

    public String getDialog_btn_text() {
        return dialog_btn_text;
    }

    public void setDialog_btn_text(String dialog_btn_text) {
        this.dialog_btn_text = dialog_btn_text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.button_dialog, null);
        setContentView(view);

        tv_btn_dialog_title = (TextView) findViewById(R.id.tv_btn_dialog_title);
        img_btn_dialog = (ImageView) findViewById(R.id.img_btn_dialog);
        tv_btn_dialog_text = (TextView) findViewById(R.id.tv_btn_dialog_text);
        tv_btn_dialog_ansure = (TextView) findViewById(R.id.tv_btn_dialog_ansure);


        //设置dialog大小
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) mContext).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8，根据实际情况调整
        dialogWindow.setAttributes(params);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));

        tv_btn_dialog_title.setText(dialog_title);
        img_btn_dialog.setImageResource(dialog_img);

        tv_btn_dialog_text.setText(dialog_text);
        tv_btn_dialog_ansure.setText(dialog_btn_text);

        tv_btn_dialog_ansure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
