package com.szy.yishopcustomer.Other;

import android.content.Context;
import android.content.Intent;
import android.view.*;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Smart on 2017/11/2.
 */

public class BottomMenuController implements View.OnClickListener{
    private View view;
    private Context mContext;

    private BottomMenuController(){}

    public static void init(Context mContext,View view){
        BottomMenuController bottomMenuController = new BottomMenuController();
        bottomMenuController.action(mContext,view);
    }

    public void action(Context mContext,View view){
        this.mContext = mContext;
        this.view = view;

        View menu1 = view.findViewById(R.id.activity_root_tabHome);
        View menu2 = view.findViewById(R.id.activity_root_tabCategory);
        View menu3 = view.findViewById(R.id.activity_root_tabShop);
        View menu4 = view.findViewById(R.id.activity_root_tabCart);
        View menu5 = view.findViewById(R.id.activity_root_tabUser);

        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menu4.setOnClickListener(this);
        menu5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_root_tabHome:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
                break;
            case R.id.activity_root_tabCategory:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CATEGORY_TAB.getValue()));
                break;
            case R.id.activity_root_tabShop:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_SHOP_STREET_TAB.getValue()));
                break;
            case R.id.activity_root_tabCart:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CART_TAB.getValue()));
                break;
            case R.id.activity_root_tabUser:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue()));
//                if (App.getInstance().isLogin()) {
//                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue()));
//                } else {
//                    openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
//                }
                break;
        }
        mContext.startActivity(new Intent().setClass(mContext, RootActivity.class));
    }


    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }
}
