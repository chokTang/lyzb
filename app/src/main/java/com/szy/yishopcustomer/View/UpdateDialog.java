package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.View.FlowLayoutManager;
import com.szy.yishopcustomer.Activity.SplashActivity;
import com.szy.yishopcustomer.Adapter.AttributeFreeAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppInfo.ResponseAppInfoModel;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/10/24.
 */

public class UpdateDialog extends Dialog {

    private Context mContext;

    @BindView(R.id.relativeLayout_back)
    View relativeLayout_back;
    @BindView(R.id.relativeLayout_root)
    View relativeLayout_root;
    @BindView(R.id.activity_splash_update_textView)
    TextView mUpdateTextView;
    ResponseAppInfoModel mResponseAppInfoModel;

    @BindView(R.id.activity_splash_update_button)
    Button mUpdateButton;

    @BindView(R.id.activity_splash_use_button)
    Button mUseButton;

    public UpdateDialog(@NonNull Context context, ResponseAppInfoModel responseAppInfoModel) {
        super(context, R.style.dialog);
        mContext = context;
        mResponseAppInfoModel = responseAppInfoModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        setContentView(R.layout.activity_splash_update);
        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth();
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失


        relativeLayout_root.setBackgroundColor(Color.parseColor("#00000000"));
        relativeLayout_back.setBackgroundColor(Color.parseColor("#00000000"));
        relativeLayout_back.setAlpha(1.0f);

        if (mResponseAppInfoModel.data.app_android_is_force == 0) {
            mUseButton.setVisibility(View.GONE);
        } else if (mResponseAppInfoModel.data.app_android_is_force == 1) {
            mUseButton.setVisibility(View.VISIBLE);
        }

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUpdate();
            }
        });

        mUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (!Utils.isNull(mResponseAppInfoModel.data.app_android_update_content)) {
            mUpdateTextView.setText(mResponseAppInfoModel.data.app_android_update_content);
        }

    }

    private void goUpdate() {
        if (!Utils.isNull(mResponseAppInfoModel.data.update_url)) {
            Utils.openBrowser(mContext, mResponseAppInfoModel.data.update_url);
        } else {
            Toast.makeText(mContext, "应用下载链接为空", Toast.LENGTH_SHORT).show();
        }
    }
}
