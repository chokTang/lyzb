package com.szy.yishopcustomer.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnticipateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Fragment.ShopGoodsListFragment;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.szy.yishopcustomer.Interface.CoordinatorLayoutObservable;
import com.szy.yishopcustomer.Interface.ScrollObserver;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2016/7/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopGoodsListActivity extends YSCBaseActivity implements TextWatcherAdapter.TextWatcherListener, TextView.OnEditorActionListener, CoordinatorLayoutObservable, CartAnimationMaker, ScrollObserver {

    @BindView(R.id.activity_goods_list_commonEditText)
    public CommonEditText mKeywordEditText;
    protected ShopGoodsListFragment mFragment;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mContentView;
    @BindView(R.id.activity_goods_list_cartWrapper)
    View mCartWrapper;
    @BindView(R.id.activity_goods_list_searchImageView)
    ImageView mSearchImageView;
    @BindView(R.id.cart_number_textView)
    TextView mCartNumberTextView;
    @BindView(R.id.activity_common_fragment_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.activity_goods_list_filterShadowView)
    View mShadowView;
    @BindView(R.id.activity_goods_list_backImage)
    ImageView mBackButton;
    static ImageView mShowTypeButton;
    private int mCurrentStyle = Macro.STYLE_GRID;
    private MenuItem mStyleMenuItem;

    @Override
    public void enableCoordinatorLayout(boolean enabled) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_goods_list_searchImageView:
                mFragment.search();
                break;
            case R.id.activity_goods_list_cartWrapper:
                openCartActivity();
                break;
            case R.id.activity_goods_list_backImage:
                finish();
                break;
            case R.id.activity_goods_list_showType:
                switchStyle();
                break;
        }
    }

    private void openCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_SHOW_FILTER_VIEW:
                showShadowView();
                break;
            case EVENT_HIDE_FILTER_VIEW:
                hideShadowView();
                break;
            case EVENT_UPDATE_CART_NUMBER:
                mCartNumberTextView.setText(App.getCartString());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_goods_list, menu);
        mStyleMenuItem = menu.findItem(R.id.activity_goods_list_style);*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_goods_list_style:
                switchStyle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected CommonFragment createFragment() {
        mFragment = new ShopGoodsListFragment();
//        mFragment.defaultApi = Api.API_SHOP_GOODS_LIST;
        Bundle arguments = new Bundle();
        arguments.putString(Key.KEY_API.getValue(),Api.API_SHOP_GOODS_LIST);
        arguments.putInt(Key.KEY_VISIBLE_MODEL.getValue(), ShopGoodsListFragment.RIGHT_MODEL |
                ShopGoodsListFragment.TOP_MODEL);
        arguments.putInt(Key.KEY_STYLE.getValue(), mCurrentStyle);
        mFragment.setArguments(arguments);
        mFragment.addScrollObserver(this);
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_goods_list;
        super.onCreate(savedInstanceState);

        mShowTypeButton = (ImageView) findViewById(R.id.activity_goods_list_showType);

        Intent intent = getIntent();
        if (intent != null) {
            setTitle(intent.getStringExtra(Key.KEY_SHOP_NAME.getValue()));
            mKeywordEditText.setText(intent.getStringExtra(Key.KEY_REQUEST_KEYWORD.getValue()));
        }
        mKeywordEditText.setTextWatcherListener(this);
        mKeywordEditText.setOnEditorActionListener(this);
        mKeywordEditText.setHint(R.string.hintSearchGoodsInThisShop);
        mCartNumberTextView.setText(App.getCartString());
        mSearchImageView.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mShowTypeButton.setOnClickListener(this);
        mCartWrapper.setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        switch (view.getId()) {
            case R.id.activity_goods_list_commonEditText:
                if (EditorInfo.IME_ACTION_SEARCH == actionId || EditorInfo.IME_ACTION_DONE == actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId) {
                    mFragment.search();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        switch (view.getId()) {
            case R.id.activity_goods_list_commonEditText:
                if(mFragment != null && mKeywordEditText != null) {
                    mFragment.setKeyword(mKeywordEditText.getText().toString());
                }
                break;
        }
    }

    @Override
    public void scrollToTop() {
        mAppBarLayout.setExpanded(true);
    }

    private void hideShadowView() {
        mShadowView.clearAnimation();
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setDuration(300);
        mShadowView.startAnimation(animation);
    }

    private void showShadowView() {
        mShadowView.clearAnimation();
        if (mShadowView.getVisibility() != View.VISIBLE) {
            mShadowView.setVisibility(View.VISIBLE);
        }
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        mShadowView.startAnimation(animation);
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

    private void startAnimation(final int startX, final int startY, final int endX, final int
            endY) {
        final View animationView = LayoutInflater.from(this).inflate(R.layout
                .activity_shop_animator, mContentView, false);
        mContentView.addView(animationView);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "x", startX, endX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "y", startY, endY);
        animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorX.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(700);
        animatorSet.playTogether(animatorX, animatorY);
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

    //切换风格
    private void switchStyle() {
        mCurrentStyle = mFragment.mListStyle;
        if (mCurrentStyle == Macro.STYLE_GRID) {
            mCurrentStyle = Macro.STYLE_LIST;
        } else {
            mCurrentStyle = Macro.STYLE_GRID;
        }
        mFragment.setStyle(mCurrentStyle);
        updateSwitchButtonStatus(mCurrentStyle);
    }

    public void updateSwitchButtonStatus(int style) {
        if (style == Macro.STYLE_LIST) {
            //mStyleMenuItem.setIcon(R.mipmap.btn_list_style_list);
            mShowTypeButton.setImageResource(R.mipmap.btn_list_style_list);
        } else if (style == Macro.STYLE_GRID) {
            //mStyleMenuItem.setIcon(R.mipmap.btn_list_style_grid);
            mShowTypeButton.setImageResource(R.mipmap.btn_list_style_grid);
        }
    }
}