package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/8/4.
 */

public class SelectShopDialog extends Dialog {

    private Context mContext;

    @BindView(R.id.fragment_cancel)
    View fragment_cancel;
    @BindView(R.id.fragment_switch)
    View fragment_switch;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.textView_shop_address)
    TextView textView_shop_address;

    public SelectShopDialog(@NonNull Context context) {
        super(context, R.style.dialog);

        mContext = context;
    }


    public void setListener(final View.OnClickListener mListener){
        fragment_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mListener.onClick(view);
            }
        });
    }

    public void setShopName(String shopname){
        shopName.setText(shopname);
    }

    public void setAddress(String address) {
        textView_shop_address.setText(address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_select_shop);
        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的5/6
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);// 点击Dialog外部消失

        fragment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectShopDialog.this.dismiss();
            }
        });


    }
}
