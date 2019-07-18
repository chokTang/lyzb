package com.lyzb.jbx.fragment.me.card;

import android.content.DialogInterface;
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
 * @role 兴趣爱好添加 dialog
 * @time 2019 2019/3/15 11:02
 */

public class EdtHobbyDialog extends BaseDialogFragment {

    private WindowManager.LayoutParams mParams = null;

    private int type = 0;//1  来往地
    private EditText hobbyEdt;
    private TextView cancle, yes;

    @Override
    public Object getResId() {
        return R.layout.dialog_edt_hobby;
    }

    @Override
    public void initView() {
        mParams = getActivity().getWindow().getAttributes();
        mParams.alpha = 0.5f;

        hobbyEdt = findViewById(R.id.edt_hobby_text);
        cancle = findViewById(R.id.tv_hobby_cancle);
        yes = findViewById(R.id.tv_hobby_yes);

        if (type ==1){
            hobbyEdt.setHint("请输入常来往地");
        }else {
            hobbyEdt.setHint("请输入兴趣爱好");
        }
        hobbyEdt.setText("");

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
                    Toast.makeText(getActivity(), "请输入兴趣爱好", Toast.LENGTH_SHORT).show();
                    return;
                }

                mListener.onYes(hobbyEdt.getText().toString().trim());
                hobbyEdt.setText("");
                dismiss();
            }
        });
    }

    public void setType(int type){
        this.type= type;
    }

    @Override
    public void initData() {

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
        return FORM_BOTTOM_TO_TOP;
    }

    public interface ClickListener {
        void onYes(String text);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mParams = getActivity().getWindow().getAttributes();
        mParams.alpha = 1f;
    }

    private ClickListener mListener = null;

    public void setListener(ClickListener clickListener) {
        this.mListener = clickListener;
    }
}
