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
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.szy.common.View.FlowLayoutManager;
import com.szy.yishopcustomer.Adapter.AttributeAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/8/18.
 */

public class DemoDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private String mImgUrl;

    @BindView(R.id.imageView_close)
    ImageView imageView_close;
    @BindView(R.id.image_demo)
    ImageView image_demo;

    public DemoDialog(@NonNull Context context,String imgUrl) {
        super(context, R.style.dialog);
        mContext = context;
        mImgUrl = imgUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_demo);
        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的5/6
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);// 点击Dialog外部消失

        imageView_close.setOnClickListener(this);

        ImageLoader.displayImage(Utils.urlOfImage(mImgUrl),image_demo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_close:
                this.dismiss();
                break;
        }
    }
}
