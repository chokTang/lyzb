package com.szy.yishopcustomer.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * 确认、取消按钮弹框
 */

public class CommonDialog extends Dialog {

    private Context mContext;
    private String title;
    private String positiveButtonText;
    private String negativeButtonText;
    private boolean outSideCancel = true;
    private OnClickListener positiveButtonClickListener;
    private OnClickListener negativeButtonClickListener;

    private String mContent;
    private int height;

    public CommonDialog(@NonNull Context context, Builder builder) {
        super(context);
        mContext = builder.context;
        this.title = builder.title;
        this.positiveButtonText = builder.positiveButtonText;
        this.negativeButtonText = builder.negativeButtonText;
        this.positiveButtonClickListener = builder.positiveButtonClickListener;
        this.negativeButtonClickListener = builder.negativeButtonClickListener;
        this.outSideCancel = builder.outSideCancel;
        this.height = builder.height;
        mContent = builder.content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_refund_check);
        setCancelable(true);
        setCanceledOnTouchOutside(outSideCancel);
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = display.getWidth() - 40;
        window.setAttributes(wl);

        TextView titleTextView = (TextView) findViewById(R.id.dialog_style_one_dialogStyleOneTitleTextView);
        if (TextUtils.isEmpty(title)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }

        Button positiveButton = (Button) findViewById(R.id.dialog_style_one_dialogStyleOneConfirmButton);
        if (positiveButtonClickListener != null) {

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveButtonClickListener.onClick(mContent);
                }
            });
            if (TextUtils.isEmpty(positiveButtonText)) {
                positiveButton.setText("确定");
            } else {
                positiveButton.setText(positiveButtonText);
            }
        } else {
            positiveButton.setVisibility(View.GONE);
        }

        Button negativeButton = (Button) findViewById(R.id.dialog_style_one_dialogStyleOneCancelButton);
        if (negativeButtonClickListener != null) {
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeButtonClickListener.onClick(mContent);
                }
            });
            if (TextUtils.isEmpty(negativeButtonText)) {
                negativeButton.setText("取消");
            } else {
                negativeButton.setText(negativeButtonText);
            }
        } else {
            negativeButton.setVisibility(View.GONE);
        }

        TextView contentTextView = (TextView) findViewById(R.id.content);
        contentTextView.setText(mContent);

        if (height > 0){
            contentTextView.setHeight(Utils.dpToPx(getContext(), height));
        }

    }

    public static class Builder {

        private Context context;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private String content;
        private boolean outSideCancel;
        private int height;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setOutSideCancel(boolean outSideCancel) {
            this.outSideCancel = outSideCancel;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public CommonDialog create() {
            return new CommonDialog(context, this);
        }
    }

    public interface OnClickListener {
        void onClick(String content);
    }
}
