package com.lyzb.jbx.fragment.me;

import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 查看大图 dialog
 * @time
 */

public class ImgDialog extends BaseDialogFragment {

    private WindowManager.LayoutParams mParams = null;
    ImageView mImageView;

    private String mImgUrl;

    @Override
    public Object getResId() {
        return R.layout.dialog_big_img;
    }

    @Override
    public void initView() {
        mParams = getActivity().getWindow().getAttributes();
        mParams.alpha = 0.5f;

        mImageView = findViewById(R.id.img_big_wx_img);
    }

    public void setImgUrl(String imgUrl) {
        this.mImgUrl = imgUrl;
    }

    @Override
    public void initData() {

        LoadImageUtil.loadImage(mImageView, mImgUrl);
    }

    @Override
    public int getViewWidth() {
        return -2;
    }

    @Override
    public int getViewHeight() {
        return -2;
    }

    @Override
    public int getViewGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getAnimationType() {
        return CENTER_DEFAULT;
    }
}
