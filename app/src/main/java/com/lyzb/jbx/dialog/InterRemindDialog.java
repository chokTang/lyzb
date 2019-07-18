package com.lyzb.jbx.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;

public class InterRemindDialog extends BaseDialogFragment {

    private CheckBox check_inter;
    private TextView tv_sure;

    @Override
    public Object getResId() {
        return R.layout.dialog_inter_remind;
    }

    @Override
    public void initView() {
        check_inter = findViewById(R.id.check_inter);
        tv_sure = findViewById(R.id.tv_sure);
    }

    @Override
    public void initData() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_inter.isChecked()) {
                    AppPreference.getIntance().setUserShowInterAccount(false);
                }
                dismiss();
            }
        });
    }

    @Override
    public int getViewWidth() {
        return ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(60);
    }

    @Override
    public int getViewHeight() {
        return -2;
    }

    @Override
    public int getViewGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getAnimationType() {
        return CNTER;
    }
}
