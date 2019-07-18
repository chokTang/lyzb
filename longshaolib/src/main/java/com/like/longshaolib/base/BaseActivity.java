package com.like.longshaolib.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.like.longshaolib.R;
import com.like.longshaolib.app.LongshaoAPP;
import com.like.utilslib.app.ActivityUtil;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by longshao on 2017/7/27.
 */

public abstract class BaseActivity extends SupportActivity {

    private Toast mToast;

    public abstract BaseFragment setRootFragment();//设置根Fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置输入框模式
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        ActivityUtil.getAppManager().addActivity(this);
        LongshaoAPP.getConfigurator().withActivity(this);

        setContentView(R.layout.activity_long_layout);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.long_container, setRootFragment());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancleToast();
        ActivityUtil.getAppManager().removeActivity(this);
        System.gc();
        System.runFinalization();
    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        onStateNotSaved();
        hideSoftInput();
        super.onBackPressedSupport();
    }

    //2.0以前使用 2.0以后被onBackPressed()替换。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Activity被系统杀死时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态.
     * 在onPause之前被调用.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Activity被系统杀死后再重建时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 发送提示消息
     *
     * @param message
     */
    protected void showToast(String message) {
        if (message.contains("No message available")){
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 取消Toast提示
     */
    protected void cancleToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


    /**
     * 显示输入法
     *
     * @param editText
     */
    protected void showSoftInput(EditText editText) {
        editText.requestFocus();
        editText.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法
     */
    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getWindow().peekDecorView();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(R.anim.popenter_anim, R.anim.popexit_anim, R.anim.enter_anim, R.anim.exit_anim);
    }
}
