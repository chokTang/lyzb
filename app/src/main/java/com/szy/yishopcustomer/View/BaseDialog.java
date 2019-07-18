package com.szy.yishopcustomer.View;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;


public class BaseDialog extends Dialog {

    public Window dialogWindow;
    public Context context;

    public BaseDialog(Context context, int layoutId) {
        super(context, R.style.MyDialog);
        this.context = context;
        setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失
        setCancelable(true);//手机返回键，Dialog小时
        setContentView(layoutId);
        dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getScreenWidth(context) - 80;
        dialogWindow.setAttributes(lp);
        setWindowAnimCenter();

    }

    public void setWindowMatch() {
        dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getScreenWidth(context);
        dialogWindow.setAttributes(lp);
    }

    public void setWindowMatchPadding(int width) {
        dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getScreenWidth(context) - width;
        dialogWindow.setAttributes(lp);
    }

    public void setWindowMatchAll() {
        dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getScreenWidth(context);
        lp.height = Utils.getScreenHeight(context) - Utils.getStatusBarHeight(context);
        dialogWindow.setAttributes(lp);
    }

    public void setWindowMatchAllPadding(int width, int height) {
        dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getScreenWidth(context) - width;
        lp.height = Utils.getScreenHeight(context) - Utils.getStatusBarHeight(context) - height;
        dialogWindow.setAttributes(lp);
    }

    public void setWindowAnimBottom() {
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
    }

    public void setWindowAnimTop() {
        dialogWindow.setGravity(Gravity.TOP);
        dialogWindow.setWindowAnimations(R.style.AnimTop);
    }

    public void setWindowAnimCenter() {
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.AnimCenter);
    }

    //设置窗口显示
    public void windowDeploy(int x, int y) {
        dialogWindow = getWindow(); //得到对话框
        dialogWindow.setWindowAnimations(R.style.AnimTop); //设置窗口弹出动画
        WindowManager.LayoutParams wl = dialogWindow.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
        wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.BOTTOM; //设置重力
        dialogWindow.setAttributes(wl);
    }
}
