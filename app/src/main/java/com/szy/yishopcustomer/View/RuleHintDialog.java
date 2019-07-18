package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 规则文本提示dialog
 * @time
 */

public class RuleHintDialog extends Dialog {

    private Context mContext;

    private LinearLayout mLayout_Bg;
    private TextView mTextView_Text;
    private ImageView mImageView_Cancle;

    public RuleHintDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public RuleHintDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected RuleHintDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rule_hint_dialog, null);
        setContentView(view);

        mLayout_Bg = (LinearLayout) view.findViewById(R.id.lin_bargain_bg);
        mTextView_Text = (TextView) view.findViewById(R.id.tv_rule_hint_text);
        mImageView_Cancle = (ImageView) view.findViewById(R.id.img_bargain_cancle);

        //设置dialog大小
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) mContext).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9，根据实际情况调整
        dialogWindow.setAttributes(params);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));

        mTextView_Text.setText("1.每次砍价金额随机，参与好友越多越容易成功;" + "\n" + "2.如果您的好友首次进入集宝箱平台," +
                "砍价同时您还可获得元宝哦~" + "\n" + "3.砍价成功后，需在活动期间内付款，购买后可子在我的订单中查看;" + "\n" + "4.若被判定为异常用户操作，平台" +
                "有权取消其砍价资格;" + "\n" + "5.本公司可在法律允许范围内对活动规则解释;" + "\n" + "6.如有疑问请联系客服:400-026-6573");

        mImageView_Cancle.getBackground().setAlpha(90);
        mImageView_Cancle.invalidate();

        mImageView_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
