package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role
 * @time 2018 2018/7/11 10:52
 */

public class IngotHintDialog extends Dialog {

    private Context mContext;
    private String mTitle;
    //true 为赠送元宝说明
    //false 为分享元宝说明
    private boolean isContentType = true;

    private TextView tv_title;
    private TextView tv_content_one;
    private TextView tv_content_two;
    private TextView tv_content_thr;
    private TextView tv_content_four;
    private TextView tv_content_five;

    private TextView tv_ensure;

    public IngotHintDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public IngotHintDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected IngotHintDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isContentType() {
        return isContentType;
    }

    public void setContentType(boolean contentType) {
        isContentType = contentType;
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingot_expl_dialog, null);
        setContentView(view);

        tv_title = (TextView) view.findViewById(R.id.tv_ingot_expl_title);
        tv_content_one = (TextView) view.findViewById(R.id.tv_ingot_expl_content_one);
        tv_content_two = (TextView) view.findViewById(R.id.tv_ingot_expl_content_two);
        tv_content_thr = (TextView) view.findViewById(R.id.tv_ingot_expl_content_thr);
        tv_content_four = (TextView) view.findViewById(R.id.tv_ingot_expl_content_four);
        tv_content_five = (TextView) view.findViewById(R.id.tv_ingot_expl_content_five);

        tv_ensure = (TextView) view.findViewById(R.id.tv_ingot_expl_ensure);

        //设置dialog大小
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) mContext).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8，根据实际情况调整
        dialogWindow.setAttributes(params);

        tv_title.setText(mTitle);

        if (isContentType) {
            tv_content_one.setText("1.赠送账号必须为平台已注册帐号;");
            tv_content_two.setText("2.赠送元宝数量不能大于赠送元宝数;");
            tv_content_thr.setText("3.赠送元宝使用规则:");
            tv_content_four.setText("3.1:元宝赠送后,获赠元宝有效期从获赠时起90天(含)内有效;");
            tv_content_five.setText("3.2:获赠元宝可再次赠送给他人。");
        } else {
            tv_content_one.setText("1.赠送元宝总数不能大于可赠送元宝数;");
            tv_content_two.setText("2.赠送元宝使用规则:");
            tv_content_thr.setText("2.1:元宝赠送后，领取到的元宝有效期从领取时起90天（含）内有效;");
            tv_content_four.setText("2.2:领取到元宝可再次赠送给他人;");
            tv_content_five.setText("3:赠送元宝可领取时效为24小时，若未被抢完则会退回我的元宝中。");
        }

        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
