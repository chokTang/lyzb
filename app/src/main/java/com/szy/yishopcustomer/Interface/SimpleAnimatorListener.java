package com.szy.yishopcustomer.Interface;

import android.animation.Animator;
import android.util.Log;

/**
 * Created by 宗仁 on 2017/1/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SimpleAnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animation) {
        Log.i(getClass().getSimpleName(), "Stub method called");
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Log.i(getClass().getSimpleName(), "Stub method called");
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        Log.i(getClass().getSimpleName(), "Stub method called");
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        Log.i(getClass().getSimpleName(), "Stub method called");
    }
}
