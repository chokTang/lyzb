package com.lyzb.jbx.inter;

/**
 * 选择图片回调
 */
public interface ISelectPictureListener {
    void onSuccess(String imgUrl);
    void onFail();
}
