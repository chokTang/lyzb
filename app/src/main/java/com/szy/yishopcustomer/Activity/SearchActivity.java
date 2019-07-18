package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.SearchFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by liuzhifeng on 2017/2/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SearchActivity extends YSCBaseActivity implements TextWatcherAdapter.TextWatcherListener, TextView.OnEditorActionListener {


    @BindView(R.id.activity_search_srearch_button)
    public TextView mSearchTextView;
    @BindView(R.id.img_seach_photo)
    public ImageView mImageView_Photo;

    @BindView(R.id.activity_search_search_eidttext)
    public CommonEditText mKeywordEditText;

    private SearchFragment mFragment;
    private boolean isSelectPhote;

    private boolean isSearchAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mLayoutId = R.layout.activity_search;
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            if (getIntent().getIntExtra(Key.KEY_KEYWORD_ACTION.getValue(), 0) == 1) {
                mFragment.isSearchAction = true;

                isSearchAction = true;
                mImageView_Photo.setVisibility(View.VISIBLE);
            }
        }

        mSearchTextView.setOnClickListener(this);

        mImageView_Photo.setOnClickListener(this);

        mKeywordEditText.setTextWatcherListener(this);
        mKeywordEditText.setOnEditorActionListener(this);

        mKeywordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isSearchAction) {
                    /*** 监听输入完成内容 动态提示搜索结果list */
                    mFragment.searchHint(s.toString());
                    if (s.length() > 0) {
                        mImageView_Photo.setVisibility(View.GONE);
                    } else {
                        mImageView_Photo.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected CommonFragment createFragment() {
        mFragment = new SearchFragment();
        return mFragment;
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {

            case EVENT_SHOW_KEYWORDS:
                mKeywordEditText.setHint(mFragment.showKeyword());
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_srearch_button:
                //保存搜索内容
                mFragment.search();
                break;
            case R.id.img_seach_photo:
                Intent intent = new Intent(SearchActivity.this, ImgSearchActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    public void setKeyword(String str) {
        mKeywordEditText.setText(str);
    }

    @Override
    public void finish() {

        if (isSelectPhote) {
            return;
        }

        super.finish();
    }

    private String img_path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {
                img_path = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);
                File picPath = new File(img_path);
                Uri uri = null;
                if (picPath.exists()) {
                    uri = Uri.fromFile(picPath);
                    initUCrop(uri);
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    Intent intent = new Intent(SearchActivity.this, ImgSearchActivity.class);
                    intent.putExtra(Key.KEY_URL.getValue(), img_path);
                    intent.putExtra(Key.KEY_TEMP_URL.getValue(), RxPhotoTool.getRealFilePath(this, resultUri));
                    startActivity(intent);
                }
                isSelectPhote = false;
            } else {
                isSelectPhote = false;
            }
        } else {
            isSelectPhote = false;
        }
    }

    public void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.aliwx_black));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.aliwx_black));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);
        options.setFreeStyleCropEnabled(true);
        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        switch (view.getId()) {
            case R.id.activity_search_search_eidttext:
                mFragment.setKeyword(mKeywordEditText.getText().toString());
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        switch (view.getId()) {
            case R.id.activity_search_search_eidttext:
                if (EditorInfo.IME_ACTION_SEARCH == actionId || EditorInfo.IME_ACTION_DONE == actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId) {
                    mFragment.search();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
