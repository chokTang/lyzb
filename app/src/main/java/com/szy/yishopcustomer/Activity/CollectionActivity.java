package com.szy.yishopcustomer.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.szy.yishopcustomer.Fragment.CollectionFragment;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

public class CollectionActivity extends YSCBaseActivity implements CartAnimationMaker {
    public static final int TYPE_LIST = 1;
    public static final int TYPE_EDIT = 1 << 1;

    public static int mCurrentType;

    public MenuItem mMenuItem;
    private CollectionFragment mFragment;

    View mCartWrapper;
    RelativeLayout mContentView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_address_list, menu);
        mMenuItem = menu.getItem(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_address_list_edit:
                changeCurrentType();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected CollectionFragment createFragment() {
        mFragment = new CollectionFragment();

        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentType = TYPE_LIST;
    }

    public void changeCurrentType() {
        mCurrentType = mCurrentType == TYPE_LIST ? TYPE_EDIT : TYPE_LIST;
        if (mCurrentType == TYPE_EDIT) {
            mMenuItem.setTitle(R.string.finish);
        } else {
            mMenuItem.setTitle(R.string.menuEdit);
        }
        mFragment.changeType(mCurrentType);
    }



    @Override
    public void makeAnimation(Drawable animView, int x, int y) {
        if(x == 0 && y < 0) {
            return;
        }

        mCartWrapper = mFragment.getCartView();
        mContentView = mFragment.getContentView();
        int[] endLocation = new int[2];
        mCartWrapper.getLocationInWindow(endLocation);
        endLocation[0] += mCartWrapper.getWidth() / 2;
        startAnimation(animView,x, y, endLocation[0], endLocation[1]);
    }


    private void startAnimation(final Drawable anim, final int startX, final int startY, final int endX, final int endY) {
        final View animationView = LayoutInflater.from(this).inflate(
                R.layout.activity_shop_animator1, null);
        ImageView im = (ImageView) animationView.findViewById(R.id.imageView_anim);
        im.setImageDrawable(anim);
        mContentView.addView(animationView);

        int picSize = Utils.dpToPx(im.getContext(),50)/2;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "X", startX, endX-picSize);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "Y", startY-picSize*2, endY-picSize);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animationView, "scaleX", 1f, 0.25f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animationView, "scaleY", 1f, 0.25f);


        animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorX.setInterpolator(new AccelerateInterpolator());

        scaleX.setInterpolator(new AnticipateInterpolator(2.0f));
        scaleY.setInterpolator(new AnticipateInterpolator(2.0f));


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(700);
        animatorSet.playTogether(animatorX,animatorY,scaleX, scaleY);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.removeView(animationView);
                startCartViewAnimation();
            }
        });
    }

    private void startCartViewAnimation() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mCartWrapper, "scaleX", 1.0f, 1.4f,
                1.0f, 1.2f, 1.0f);
        animatorX.setDuration(200);
        animatorX.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorX.start();

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mCartWrapper, "scaleY", 1.0f, 1.2f,
                1.0f, 1.2f, 1.0f);
        animatorY.setDuration(200);
        animatorY.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorY.start();
    }
}
