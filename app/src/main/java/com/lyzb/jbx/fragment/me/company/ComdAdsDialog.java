package com.lyzb.jbx.fragment.me.company;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 创建企业or修改企业信息 修改地址信息
 * @time 2019 2019/3/15 11:02
 */

public class ComdAdsDialog extends BaseDialogFragment {

    private WindowManager.LayoutParams mParams = null;

    private String mText = null;
    private EditText hobbyEdt;
    private TextView cancle, yes;

    @Override
    public Object getResId() {
        return R.layout.dialog_add_comd_ads;
    }

    @Override
    public void initView() {
        mParams = getActivity().getWindow().getAttributes();
        mParams.alpha = 0.5f;

        hobbyEdt = findViewById(R.id.edt_comd_text);
        cancle = findViewById(R.id.tv_cancle);
        yes = findViewById(R.id.tv_yes);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(hobbyEdt.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请输入地址信息!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mListener.onYes(hobbyEdt.getText().toString().trim());
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mParams = getActivity().getWindow().getAttributes();
        mParams.alpha = 1f;
    }

    public void setText(String text) {
        mText = text;
    }

    @Override
    public void initData() {
        if (mText == null) {
            hobbyEdt.setText("");
        } else {
            hobbyEdt.setText(mText);
            Selection.setSelection(hobbyEdt.getText(),mText.length());
        }
    }

    @Override
    public int getViewWidth() {
        return ScreenUtil.getScreenWidth();
    }

    @Override
    public int getViewHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getViewGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int getAnimationType() {
        return FORM_BOTTOM_TO_BOTTOM;
    }

    public interface ClickListener {
        void onYes(String text);
    }

    private ClickListener mListener = null;

    public void setListener(ClickListener clickListener) {
        this.mListener = clickListener;
    }
}
