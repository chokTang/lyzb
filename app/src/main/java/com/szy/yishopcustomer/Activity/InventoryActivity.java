package com.szy.yishopcustomer.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.InventoryFragment;
import com.szy.yishopcustomer.Fragment.ShopGoodsListFragment;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2016/6/20.
 */
public class InventoryActivity extends YSCBaseActivity implements CartAnimationMaker {

    private InventoryFragment mFragment;

    @BindView(R.id.activity_goods_list_cartWrapper)
    View mCartWrapper;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mContentView;
    @BindView(R.id.cart_number_textView)
    TextView mCartNumberTextView;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_inventory ;
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
        mCartNumberTextView.setText(App.getCartString());


        mCartWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCartActivity();
            }
        });
    }

    private void openCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    public InventoryFragment createFragment() {
        mFragment = new InventoryFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Key.KEY_VISIBLE_MODEL.getValue(), ShopGoodsListFragment.LEFT_MODEL |
                ShopGoodsListFragment.RIGHT_MODEL);
        arguments.putString(Key.KEY_API.getValue(), Api.API_INVENTORY_GOODS);
        mFragment.setArguments(arguments);
        return mFragment;
    }


    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                mCartNumberTextView.setText(App.getCartString());
                break;
        }
    }

    @Override
    public void makeAnimation(Drawable animView, int x, int y) {
        if(x == 0 && y < 0) {
            return;
        }

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

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "X", startX-picSize, endX-picSize);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "Y", startY-picSize, endY-picSize);

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

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
