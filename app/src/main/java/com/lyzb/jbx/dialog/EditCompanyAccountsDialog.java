package com.lyzb.jbx.dialog;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;

public class EditCompanyAccountsDialog extends BaseDialogFragment {

    private TextView tv_content_value;

    private LinearLayout layout_code;

    private TextView btn_code;
    private EditText ed_code;
    private TimeCount time;
    private OnClickListener onClickListener;

    private String status, value;


    public static EditCompanyAccountsDialog newIntance(String status, String value) {
        EditCompanyAccountsDialog fragment = new EditCompanyAccountsDialog();
        fragment.status = status;
        fragment.value = value;
        return fragment;
    }


    @Override
    public Object getResId() {
        return R.layout.dialog_edit_company_accounts;
    }

    @Override
    public void initView() {
        tv_content_value = findViewById(R.id.tv_content_value);
        layout_code = findViewById(R.id.layout_code);

        btn_code = findViewById(R.id.btn_code);
        ed_code = findViewById(R.id.ed_code);
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDailogDismiss();
            }
        });

        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnClickListener() != null) {
                    if (layout_code.getVisibility() == View.VISIBLE) {//需要填写验证码
                        getOnClickListener().onclick(v, status, ed_code.getText().toString().trim());
                    } else {
                        getOnClickListener().onclick(v, status, "");
                    }
                }

            }
        });
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnClickListener() != null) {
                    getOnClickListener().onclick(v, status, "");
                }
            }
        });
    }

    @Override
    public void initData() {
        tv_content_value.setText(value);
//        if ("203".equals(status)) {
//            layout_code.setVisibility(View.GONE);
//        } else if ("205".equals(status)) {
//            layout_code.setVisibility(View.VISIBLE);
//        }
        time = new TimeCount(60000, 1000);
    }

    public void sendCode() {
        if (time == null) {
            time = new TimeCount(60000, 1000);
        }
        time.start();
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

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public interface OnClickListener {
        void onclick(View view, String status, String code);
    }

    @Override
    public int getAnimationType() {
        return CNTER;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btn_code.setText("获取验证");
            btn_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_code.setClickable(false);
            btn_code.setText(millisUntilFinished / 1000 + "秒");
        }
    }

}
