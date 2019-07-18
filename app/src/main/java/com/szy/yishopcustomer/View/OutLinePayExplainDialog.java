package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.FlowLayoutManager;
import com.szy.yishopcustomer.Adapter.AttributeFreeAdapter;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.MapKeyComparator;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModelTwo;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.AttributeLineModel;
import com.szy.yishopcustomer.ViewModel.FreeSkuListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/19.
 */

public class OutLinePayExplainDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    @BindView(R.id.imageViewClose)
    View imageViewClose;


    public OutLinePayExplainDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_outline_pay_explain);
        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的5/6
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失

        imageViewClose.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewClose:
                this.dismiss();
                break;
        }
    }


}
