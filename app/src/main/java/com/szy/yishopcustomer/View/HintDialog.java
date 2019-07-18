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

import com.google.android.exoplayer.C;
import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role
 * @time 2018 2018/8/13 17:02
 */

public class HintDialog extends Dialog {
    public String mStringTitle;

    private Context mContext;
    private TextView mView_Title;
    private TextView mView_Cancle;
    private TextView mView_Ensure;

    public HintDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public HintDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected HintDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(mContext).inflate(R.layout.hint_dialog, null);
        setContentView(view);

        mView_Title = (TextView) view.findViewById(R.id.tv_dialog_title);
        mView_Cancle = (TextView) view.findViewById(R.id.tv_dialog_cancle);
        mView_Ensure = (TextView) view.findViewById(R.id.tv_dialog_ensure);

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) mContext).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        dialogWindow.setAttributes(params);

        mView_Title.setText(mStringTitle);

        mView_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick != null) {
                    mOnClick.onCancle();
                }
            }
        });

        mView_Ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick != null) {
                    mOnClick.onEnsure();
                }
            }
        });
    }

    public interface mViewOnClick {
        void onCancle();

        void onEnsure();
    }

    private mViewOnClick mOnClick;

    public void onViewClick(mViewOnClick click) {
        this.mOnClick = click;
    }
}
