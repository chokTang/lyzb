package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by Smart on 2017/6/5.
 */
public class MenuPopwindow extends PopupWindow {

    public static final int MENU_STYLE_BASE = 1;
    public static final int MENU_STYLE_SHOP = 2;
    public static final int MENU_STYLE_MESSAGE = 3;

    private View conentView;
    private int mCustemMenuStyle;
    private Context mContext;
    private View.OnClickListener mListener;

    private LinearLayout activity_base_homeMenu;
    private LinearLayout activity_base_categoryMenu;
    private LinearLayout activity_base_messageMenu;
    private LinearLayout activity_base_searchMenu;
    private LinearLayout activity_base_cartMenu;
    private LinearLayout activity_base_userMenu;
    private LinearLayout action_my_following;
    private LinearLayout activity_base_share;
    private RelativeLayout activity_base_blank;

    private int h;
    private int w;
    private int popWidth;

    public MenuPopwindow(Activity context,int custemMenuStyle,View.OnClickListener listener) {
        mContext = context;
        mCustemMenuStyle = custemMenuStyle;
        mListener = listener;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.menu_popup_window, null);

        activity_base_homeMenu = (LinearLayout) conentView.findViewById(R.id.activity_base_homeMenu);
        activity_base_categoryMenu = (LinearLayout) conentView.findViewById(R.id.activity_base_categoryMenu);
        activity_base_messageMenu = (LinearLayout) conentView.findViewById(R.id.activity_base_messageMenu);
        activity_base_searchMenu = (LinearLayout) conentView.findViewById(R.id.activity_base_searchMenu);
        activity_base_cartMenu = (LinearLayout) conentView.findViewById(R.id.activity_base_cartMenu);
        activity_base_userMenu = (LinearLayout) conentView.findViewById(R.id.activity_base_userMenu);
        action_my_following = (LinearLayout) conentView.findViewById(R.id.action_my_following);
        activity_base_share = (LinearLayout) conentView.findViewById(R.id.activity_base_share);
        activity_base_blank = (RelativeLayout) conentView.findViewById(R.id.activity_base_blank);

        activity_base_homeMenu.setOnClickListener(mListener);
        activity_base_categoryMenu.setOnClickListener(mListener);
        activity_base_messageMenu.setOnClickListener(mListener);
        activity_base_searchMenu.setOnClickListener(mListener);
        activity_base_cartMenu.setOnClickListener(mListener);
        activity_base_userMenu.setOnClickListener(mListener);
        action_my_following.setOnClickListener(mListener);
        activity_base_share.setOnClickListener(mListener);
        activity_base_blank.setOnClickListener(mListener);

        styleController();

        h = context.getWindowManager().getDefaultDisplay().getHeight();
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置MenuPopwindow的View
        this.setContentView(conentView);
        // 设置MenuPopwindow弹出窗体的宽
        this.setWidth(popWidth = w/3*1);
        // 设置MenuPopwindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置MenuPopwindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // this.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if(parent != null) {
            if (!this.isShowing()) {
                // 以下拉方式显示popupwindow
                this.showAtLocation(parent,Gravity.TOP,w/2 - popWidth/2 - Utils.dpToPx(mContext,5),Utils.dpToPx(mContext,50));
            } else {
                this.dismiss();
            }
        }
    }

    public void showPopupWindowAsDropDown(View parent) {
        if(parent != null) {
            if (!this.isShowing()) {
                // 以下拉方式显示popupwindow
                this.showAsDropDown(parent,-popWidth +Utils.dpToPx(mContext,35),-Utils.dpToPx(mContext,15));
            } else {
                this.dismiss();
            }
        }
    }


    private void styleController(){
        switch(mCustemMenuStyle){
            case MENU_STYLE_BASE:
                viewSwitch(activity_base_homeMenu,true);
                viewSwitch(activity_base_categoryMenu,true);
                viewSwitch(activity_base_messageMenu,true);
                viewSwitch(activity_base_searchMenu,true);
                viewSwitch(activity_base_cartMenu,true);
                viewSwitch(activity_base_userMenu,true);
                viewSwitch(action_my_following,false);
                viewSwitch(activity_base_share,false);
                break;
            case MENU_STYLE_SHOP:
                viewSwitch(activity_base_homeMenu,true);
                viewSwitch(activity_base_categoryMenu,false);
                viewSwitch(activity_base_messageMenu,true);
                viewSwitch(activity_base_searchMenu,false);
                viewSwitch(activity_base_cartMenu,true);
                viewSwitch(activity_base_userMenu,true);
                viewSwitch(action_my_following,false);
                viewSwitch(activity_base_share,true);
                break;
            case MENU_STYLE_MESSAGE:
                viewSwitch(activity_base_homeMenu,true);
                viewSwitch(activity_base_categoryMenu,true);
                viewSwitch(activity_base_messageMenu,false);
                viewSwitch(activity_base_searchMenu,true);
                viewSwitch(activity_base_cartMenu,true);
                viewSwitch(activity_base_userMenu,true);
                viewSwitch(action_my_following,false);
                viewSwitch(activity_base_share,false);
                break;
        }
    }

    private void viewSwitch(View view,boolean bool){
        if(bool) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}