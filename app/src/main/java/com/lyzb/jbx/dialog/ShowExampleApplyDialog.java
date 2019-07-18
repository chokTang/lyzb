package com.lyzb.jbx.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.lyzb.jbx.R;

public class ShowExampleApplyDialog extends BaseDialogFragment {

    private ImageView img_close;

    @Override
    public Object getResId() {
        return R.layout.dialog_exmaple_apply;
    }

    @Override
    public void initView() {
        img_close = findViewById(R.id.img_close);
    }

    @Override
    public void initData() {
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public int getViewWidth() {
        return -1;
    }

    @Override
    public int getViewHeight() {
        return -1;
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
