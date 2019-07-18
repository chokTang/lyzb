package com.lyzb.jbx.fragment.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.presenter.BasePresenter;
import com.like.utilslib.app.AppUtil;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.file.FileUtil;
import com.like.utilslib.image.LuBanUtil;
import com.like.utilslib.image.inter.ICompressListener;
import com.lyzb.jbx.inter.ISelectPictureListener;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础的图片选取封装类
 * Created by Administrator on 2018/4/13.
 */

public abstract class BasePhotoFragment<P extends BasePresenter> extends BaseFragment<P>
        implements TakePhoto.TakeResultListener, InvokeListener {

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;

    private ISelectPictureListener mListener;

    private int type_select = 0;
    private final int SELECT_PHOTO = 0x352;//从相册选择一张图片
    private final int SELECT_PHOTO_ROUND = 0x353;//从相册选择一张图片 圆形/方形裁剪
    private final int SELECT_CAMERA = 0x354;//从相机选择一张图片
    private final int SELECT_CAMERA_ROUND = 0x355;//从相机选择一张图片 圆形/方形裁剪
    private final int SELECT_MULTIPLE = 0x356;//从相机选择多张图片

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getBaseActivity(), type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(final TResult result) {
        if (mListener != null) {
            switch (type_select) {
                case SELECT_PHOTO_ROUND:
                case SELECT_PHOTO:
                case SELECT_CAMERA:
                case SELECT_CAMERA_ROUND:
                    LuBanUtil.compress(result.getImage().getOriginalPath(), new ICompressListener() {
                        @Override
                        public void onSuccess(File file) {
                            mListener.onSuccess(file.getAbsolutePath());
                        }

                        @Override
                        public void onFail(String msg) {
                            showToast(msg);
                            mListener.onFail();
                        }
                    });
                    break;
                //多张图片的时候
                case SELECT_MULTIPLE:
                    List<TImage> images = result.getImages();
                    if (images != null && images.size() > 0) {
                        List<String> mlist = new ArrayList<>();
                        for (TImage image : images) {
                            mlist.add(image.getOriginalPath());
                        }
                        if (mlist.size() > 0) {
                            mListener.onSuccess(CommonUtil.ListToString(mlist));
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showToast(msg);
        if (mListener != null) {
            mListener.onFail();
        }
    }

    @Override
    public void takeCancel() {
    }

    /**
     * 从相册选择一张图片
     */
    protected void onChooseFromPhoto(ISelectPictureListener listener) {
        type_select = SELECT_PHOTO;
        mListener = listener;
        getTakePhoto().onPickFromGallery();
    }

    /**
     * 从相机中拍照一张图片
     */
    protected void onChooseFromCamera(ISelectPictureListener listener) {
        Uri uri = AppUtil.getUriForFile(getContext(), FileUtil.createFile(getContext().getPackageName(), System.currentTimeMillis() + ".jpg"));
        type_select = SELECT_CAMERA;
        mListener = listener;
        getTakePhoto().onPickFromCapture(uri);
    }


    /**
     * 选择多张图片
     *
     * @param number   最多多少张图片
     * @param listener
     */
    protected void onChooseMultiple(int number, ISelectPictureListener listener) {
        type_select = SELECT_MULTIPLE;
        mListener = listener;
        getTakePhoto().onPickMultiple(number);
    }

    /**
     * 从相册选择一张图片并裁剪成圆形/方形
     *
     * @param isRound 是否是圆形
     */
    protected void onChooseFromPhotoRound(boolean isRound, ISelectPictureListener listener) {
        type_select = SELECT_PHOTO_ROUND;
        mListener = listener;
        Uri uri = AppUtil.getUriForFile(getContext(), FileUtil.createFile(getContext().getPackageName(), System.currentTimeMillis() + ".jpg"));
        getTakePhoto().onPickFromGalleryWithCrop(uri, getRoundCropOptions(isRound));
    }

    /**
     * 拍照 并裁剪
     *
     * @param isRound
     * @param listener
     */
    protected void onChooseFromCameraRound(boolean isRound, ISelectPictureListener listener) {
        type_select = SELECT_CAMERA_ROUND;
        mListener = listener;
        Uri uri = AppUtil.getUriForFile(getContext(), FileUtil.createFile(getContext().getPackageName(), System.currentTimeMillis() + ".jpg"));
        getTakePhoto().onPickFromCaptureWithCrop(uri, getRoundCropOptions(isRound));
    }

    /**
     * 获取圆形裁剪配置
     *
     * @param isRound 是否是圆形
     * @return
     */
    private CropOptions getRoundCropOptions(boolean isRound) {
        CropOptions options = new CropOptions.Builder()
                .setAspectX(1)
                .setAspectY(1)
                .setOutputX(600)
                .setOutputY(600)
                .setWithOwnCrop(!isRound)//设置圆角 false是圆角，true是正方形
                .create();
        return options;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
