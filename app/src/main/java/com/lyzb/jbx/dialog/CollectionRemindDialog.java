package com.lyzb.jbx.dialog;

import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;

/**
 * 首次收藏提示的对话框
 */
public class CollectionRemindDialog extends BaseDialogFragment {

    private ImageView img_close;
    private TextView tv_sure;

    @Override
    public Object getResId() {
        return R.layout.dialog_collection_remind;
    }

    @Override
    public void initView() {
        img_close = findViewById(R.id.img_close);
        tv_sure = findViewById(R.id.tv_sure);
    }

    @Override
    public void initData() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference.getIntance().setFirstCollection(false);
                dismiss();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference.getIntance().setFirstCollection(false);
                dismiss();
            }
        });

        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                AppPreference.getIntance().setFirstCollection(false);
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
