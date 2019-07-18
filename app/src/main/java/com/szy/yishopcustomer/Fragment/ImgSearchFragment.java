package com.szy.yishopcustomer.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edmodo.cropper.CropImageView;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ImgSearchActivity;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Adapter.HeaderFooterAdapter;
import com.szy.yishopcustomer.Adapter.ImgShoppingGoodsAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.LubanImg;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.ResizeAbleSurfaceView;
import com.szy.yishopcustomer.View.SlidingLayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zongren.pullablelayout.Constant.Side;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * 拍照购拍摄
 */
public class ImgSearchFragment extends YSCBaseFragment {
    @BindView(R.id.cameraView)
    ResizeAbleSurfaceView mSurfaceView;
    Unbinder unbinder;
    @BindView(R.id.bg)
    RelativeLayout bg;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.flash)
    ImageView flash;
    @BindView(R.id.chosePic)
    ImageView chosePic;
    @BindView(R.id.takePic)
    ImageView takePic;
    @BindView(R.id.flip)
    ImageView flip;
    @BindView(R.id.takeLayout)
    RelativeLayout takeLayout;
    @BindView(R.id.searchLayout)
    RelativeLayout searchLayout;
    @BindView(R.id.goodsList)
    CommonRecyclerView goodsList;
    @BindView(R.id.mSlidingLayer)
    SlidingLayer mSlidingLayer;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.cropImageView)
    CropImageView cropImageView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ll_take_pic_buy)
    LinearLayout llTakePicBuy;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_bottom_selector)
    LinearLayout llBottomSelector;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.closeGoodsList)
    ImageView closeGoodsList;
    @BindView(R.id.topPanel)
    RelativeLayout topPanel;
    @BindView(R.id.view_divider_take_pic_buy)
    View viewDividerTakePicBuy;
    @BindView(R.id.view_divider_scan)
    View viewDividerScan;
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private static final int REQUEST_CAMERA_CODE = 100;
    private static final int REQUEST_READ_CODE = 200;
    private static int FRONT = 1;
    private static int BACK = 0;
    private int mCamerId = 0;
    private int page = 1;
    private boolean isRequesting;
    private boolean hasMore;
    private String tempUrl;
    private ImgShoppingGoodsAdapter adapter;
    private int mListStyle = Macro.STYLE_GRID;
    private boolean isOpened;
    private Bitmap bm;
    private String imagePath;
    private int screenHeight, screenWidth;

    private static final int FILE_PERMISSION = 1122;

    private RecyclerView.OnScrollListener mGoodsRecyclerViewScrollListener = new RecyclerView
            .OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (!isRequesting && goodsList.reachEdgeOfSide(Side.BOTTOM) && hasMore) {
                searchGoods(tempUrl);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_imgsearch_take;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ImgSearchActivity) getActivity()).hideTopBar();
        initView();
        llScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDividerScan.setVisibility(View.VISIBLE);
                viewDividerTakePicBuy.setVisibility(View.GONE);
                openScanActivity();//扫一扫
            }
        });
        llTakePicBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDividerScan.setVisibility(View.GONE);
                viewDividerTakePicBuy.setVisibility(View.VISIBLE);
                initCamera();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //默认显示拍照购
        viewDividerScan.setVisibility(View.GONE);
        viewDividerTakePicBuy.setVisibility(View.VISIBLE);
        initCamera();
    }

    private void initView() {

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                if (mCamera != null) {
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {

                        @Override

                        public void onAutoFocus(boolean success, Camera camera) {
                            if (success) {
                                camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦
                                doAutoFocus();
                            }
                        }
                    });
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        mSlidingLayer.setOffsetDistance(Utils.dpToPx(getContext(), 200));
        mSlidingLayer.setOnScrollListener(new SlidingLayer.OnScrollListener() {
            @Override
            public void onScroll(int absoluteScroll) {
                int imgScroll = (absoluteScroll - mSlidingLayer.getOffsetDistance()) / 2;
                if (imgScroll < 0)
                    imgScroll = 0;
                cropImageView.scrollTo(0, imgScroll);
                if (scrollView.getLayoutParams().height != screenHeight) {
                    scrollView.getLayoutParams().height = screenHeight;
                    scrollView.setLayoutParams(scrollView.getLayoutParams());
                }
            }
        });
        mSlidingLayer.setOnInteractListener(new SlidingLayer.OnInteractListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onShowPreview() {

            }

            @Override
            public void onClose() {
            }

            @Override
            public void onOpened() {
                isOpened = true;
            }

            @Override
            public void onPreviewShowed() {
            }

            @Override
            public void onClosed() {
                isOpened = false;
                scrollView.getLayoutParams().height = screenHeight - mSlidingLayer.getOffsetDistance() + Utils.dpToPx(getContext(), 40);
                scrollView.requestLayout();
            }
        });
        adapter = new ImgShoppingGoodsAdapter();
        adapter.onCLickListener = this;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return isOpened && super.canScrollVertically();
            }
        };
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case HeaderFooterAdapter.TYPE_NORMAL:
                        return 1;
                    default:
                        return 2;
                }
            }
        });
        goodsList.setLayoutManager(layoutManager);
        goodsList.addOnScrollListener(mGoodsRecyclerViewScrollListener);
        final int mGridItemOffset = Utils.dpToPx(getContext(), 5);
        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                if (mListStyle == Macro.STYLE_GRID) {
                    outRect.top = mGridItemOffset;
                    if (itemPosition % 2 == 0) {
                        outRect.left = mGridItemOffset;
                        outRect.right = mGridItemOffset / 2;
                    } else {
                        outRect.left = mGridItemOffset / 2;
                        outRect.right = mGridItemOffset;
                    }
                } else {
                    outRect.top = 0;
                    outRect.left = 0;
                    outRect.bottom = 0;
                    outRect.right = 0;
                }
            }

        };
        goodsList.addItemDecoration(mItemDecoration);
        adapter.onCLickListener = this;
        goodsList.setAdapter(adapter);
        cropImageView.setOnCropWindowChangedListener(new CropImageView.OnCropWindowChangedListener() {
            @Override
            public void onCropWindowChanged() {
                cropImage();
            }
        });



    }

    private long beginTime;

    private void cropImage() {
        if (takeLayout.getVisibility() == View.GONE) {
            beginTime = System.currentTimeMillis();
            final Bitmap bitmap = cropImageView.getCroppedImage();
            mProgress.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String fileName = getContext().getExternalCacheDir().getPath() + "/" + System.currentTimeMillis() + ".jpg";
                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, fileOutputStream);
                        bitmap.recycle();
                        Log.d("lyzb", "============img crop cost " + (System.currentTimeMillis() - beginTime));
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    upLoadImg(fileName);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                openGoodsActivity(position);
                break;
        }
    }

    private void openGoodsActivity(int position) {
        if (adapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) adapter.data.get(position);
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsModel.goods_id);
        startActivity(intent);
    }


    private void doAutoFocus() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    try {
                        camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                        if (!Build.MODEL.equals("KORIDY H30")) {
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
                            camera.setParameters(parameters);
                        } else {
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                            camera.setParameters(parameters);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    /**
     * @return 摄像头是否存在
     */
    private boolean checkCamera() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void initCamera() {
        if (!checkCamera())
            return;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_CODE);
        } else {
            openCamera(mCamerId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(mCamerId);
            } else {

            }
        } else if (requestCode == REQUEST_READ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSearchLayout(imagePath);
            } else {

            }
        } else if (requestCode == FILE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!TextUtils.isEmpty(imagePath)) {
                    showSearchLayout(imagePath);
                } else {
                    cropImage();
                }
            } else {

            }
        }
    }

    private void openCamera(int mCamerId) {
        this.mCamerId = mCamerId;
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
        mCamera = Camera.open(mCamerId);
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(90);
        } else {//如果是横屏
            mCamera.setDisplayOrientation(0);
        }
        //给照相机设置参数
        Camera.Parameters parameters = mCamera.getParameters();
        //设置保存格式
        parameters.setPictureFormat(PixelFormat.JPEG);
        //设置质量
        parameters.set("jpeg-quality", 85);
        //给照相机设置参数
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (screenHeight == 0) {
            screenHeight = mSurfaceView.getHeight();
            screenWidth = mSurfaceView.getWidth();
        }
//        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
//        Camera.Size optionSize = getOptimalPreviewSize(sizeList, screenWidth, screenHeight);//获取一个最为适配的camera.size
//        parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
//
//        try {
//            mSurfaceView.resize(optionSize.height, optionSize.width);
//            mCamera.setParameters(parameters);
//        } catch (Exception e) {
//            e.printStackTrace();
////            try {
////                mSurfaceView.resize(720, 1080);
////                mCamera.setParameters(parameters);
////            } catch (Exception e1) {
////                e1.printStackTrace();
////            }
//        }
//
//        try {
//            parameters.setPictureSize(optionSize.width, optionSize.height);
//            mCamera.setParameters(parameters);
//        } catch (Exception e) {
//            e.printStackTrace();
////            try {
////                parameters.setPictureSize(720, 1080);
////                mCamera.setParameters(parameters);
////            } catch (Exception e1) {
////                e1.printStackTrace();
////            }
//        }

        setCameraParameters();
        //将照相机捕捉的画面展示到SurfaceView
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //开启预览
            mCamera.startPreview();
            bg.setVisibility(View.GONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCameraParameters() {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        for (int i = 0; i < sizeList.size(); i++) {
            Camera.Size optionSize = sizeList.get(i);
            float optionScal = (float) optionSize.height / (float) optionSize.width;
            float screenScal = (float) screenWidth / (float) screenHeight;
            if (optionScal < screenScal) {
                try {
                    mSurfaceView.resize(screenWidth, (int) (screenWidth / optionScal));
                    parameters.setPreviewSize(optionSize.width, optionSize.height);
                    parameters.setPictureSize(optionSize.width, optionSize.height);
                    mCamera.setParameters(parameters);
                    Log.d("lyzb", "============img camera params " + optionSize.height + " " + optionSize.width + " success");
                    break;
                } catch (Exception e) {
                    Log.d("lyzb", "============img camera params " + optionSize.height + " " + optionSize.width + " error");
                }
            }

        }

    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        unbinder.unbind();
    }

    private void chosePic() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RequestCode.REQUEST_PICK
                .getValue());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RequestCode.valueOf(requestCode) != null) {
            switch (RequestCode.valueOf(requestCode)) {
                case REQUEST_PICK:  //调用系统相册返回
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Cursor cursor = getContext().getContentResolver().query(uri, new String[]{MediaStore
                                .Images.ImageColumns.DATA}, null, null, null);
                        if (null != cursor) {
                            if (cursor.moveToFirst()) {
                                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                                if (index > -1) {
                                    String imagePath = cursor.getString(index);
                                    checkPermission(imagePath);
                                }
                            }
                            cursor.close();
                        }
                    }
                    break;
            }
        }
    }

    private void takePic() {
        if (mCamera == null)
            return;
        mProgress.show();
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, final Camera camera) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            Matrix m = new Matrix();
                            if (mCamerId == FRONT) {
                                m.setRotate(270, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                                m.postScale(-1, 1);   //镜像水平翻转
                            } else {
                                m.setRotate(90, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                            }
                            bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                            bitmap.recycle();
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showSearchLayout(bm);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                camera.stopPreview();
                camera.startPreview();
            }
        });
    }

    private void changeCamera() {
        if (mCamera == null)
            return;
        if (mCamerId == FRONT) {
            openCamera(BACK);
        } else if (mCamerId == BACK) {
            mCamerId = FRONT;
            closeFlash();
        }
    }

    @OnClick({R.id.back, R.id.flash, R.id.chosePic, R.id.takePic, R.id.flip, R.id.closeGoodsList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.flash:
                changeFlash();
                break;
            case R.id.chosePic:
                chosePic();
                break;
            case R.id.takePic:
                takePic();
                break;
            case R.id.flip:
                changeCamera();
                break;
            case R.id.closeGoodsList:
                showTakeLayout();
                break;
        }
    }

    public void onBackPressed() {
        if (takeLayout.getVisibility() == View.VISIBLE) {
            getActivity().finish();
        } else {
            showTakeLayout();
        }
    }

    public void changeFlash() {
        if (mCamerId == FRONT)
            return;
        if (isFlashlightOn()) {
            closeFlash();
        } else {
            openFlash();
        }
    }

    public boolean isFlashlightOn() {
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            String flashMode = parameters.getFlashMode();
            if (flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 打开闪光灯
     */
    public void openFlash() {
        if (mCamera != null) {
            flash.setImageResource(R.mipmap.ic_sgd_dk);
            mCamera.startPreview();
            Camera.Parameters parameter = mCamera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameter);
        }
    }

    /**
     * 关闭闪光灯
     */
    public void closeFlash() {
        if (mCamera != null) {
            flash.setImageResource(R.mipmap.ic_sgd_gb);
            mCamera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mCamera.getParameters());
            openCamera(mCamerId);
        }
    }

    public void showSearchLayout(Bitmap bitmap) {
        if (searchLayout.getVisibility() == View.GONE) {
            imagePath = null;
            takeLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            cropImageView.setImageBitmap(bitmap);
            cropImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPermission(null);
                }
            }, 100);
        }
    }

    public void showSearchLayout(final String url) {
        if (searchLayout.getVisibility() == View.GONE) {
            takeLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            cropImageView.setImageBitmap(BitmapFactory.decodeFile(url));
        }
        if (searchLayout.getVisibility() == View.VISIBLE) {
            cropImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPermission(url);
                }
            }, 100);
        }
    }

    public void showTakeLayout() {
        if (mProgress != null)
            mProgress.dismiss();
        if (takeLayout.getVisibility() == View.GONE) {
            isFirstOpen = true;
            openCamera(mCamerId);
            takeLayout.setVisibility(View.VISIBLE);
            searchLayout.setVisibility(View.GONE);
            mSlidingLayer.closeLayer(true);
            mSlidingLayer.setVisibility(View.GONE);
            adapter.data.clear();
            adapter.notifyDataSetChanged();
            scrollView.getLayoutParams().height = screenHeight;
            cropImageView.setImageBitmap(null);
            cropImageView.scrollTo(0, 0);
            if (bm != null) {
                bm.recycle();
                bm = null;
            }
        }
    }

    private void checkPermission(String imagePath) {
        this.imagePath = imagePath;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_CODE);
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_PERMISSION);
            } else {
                if (searchLayout.getVisibility() == View.VISIBLE) {
                    cropImage();
                } else {
                    showSearchLayout(imagePath);
                }
            }
        }
    }

    private void upLoadImg(String imgPath) {
        if (takeLayout.getVisibility() == View.GONE) {
            mProgress.show();
            beginTime = System.currentTimeMillis();

            Luban.with(getContext())
                    .load(imgPath)
                    .ignoreBy(100)
                    .setTargetDir(LubanImg.getPath())
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            Oss.getInstance().upLoadSearImg(getActivity(), file.getAbsolutePath(), new Oss.OssListener() {
                                @Override
                                public void onProgress(int progress) {

                                }

                                @Override
                                public void onSuccess(String url) {
                                    page = 1;
                                    Log.d("lyzb", "============img upload cost " + (System.currentTimeMillis() - beginTime));
                                    searchGoods(url);
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(getContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                                    mProgress.dismiss();
                                }
                            });

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }

                    }).launch();
        }
    }

    private void searchGoods(String imgUrl) {
        beginTime = System.currentTimeMillis();
        this.tempUrl = imgUrl;
        CommonRequest searchRequest = new CommonRequest(Api.API_IMG_SHOPPING, HttpWhat.HTTP_IMG_SHOPPING.getValue());
        RequestAddHead.addHead(searchRequest, getActivity(), Api.API_IMG_SHOPPING, "GET");
        searchRequest.add("msg", imgUrl);
        searchRequest.add("page", page);
        searchRequest.add("size", 24);
        searchRequest.alarm = true;
        addRequest(searchRequest);
        isRequesting = true;
    }

    @Override
    protected void onRequestFinish(int what) {
        super.onRequestFinish(what);
        isRequesting = false;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_IMG_SHOPPING:
                Log.d("lyzb", "============img search cost " + (System.currentTimeMillis() - beginTime));
                if (page == 1) {
                    adapter.data.clear();
                    adapter.setFooterView(null);
                }
                try {
                    JSONObject object = JSONObject.parseObject(response).getJSONObject("data");
                    List<GoodsModel> mGoodsModelList = JSON.parseArray(object.getString("list"), GoodsModel.class);
                    if (mGoodsModelList != null && mGoodsModelList.size() > 0) {
                        adapter.data.addAll(mGoodsModelList);
                        page++;
                        hasMore = true;
                    } else {
                        hasMore = false;
                        //设置footer
                        View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
                        adapter.setFooterView(footer);
                    }
                } catch (Exception e) {

                }
                if (adapter.data.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                showGoodsList();
                break;
        }
    }

    boolean isFirstOpen = true;

    private void showGoodsList() {
        if (isFirstOpen) {
            mSlidingLayer.setVisibility(View.VISIBLE);
            mSlidingLayer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSlidingLayer.openLayer(true);
                }
            }, 300);
            isFirstOpen = false;
            scrollView.getLayoutParams().height = screenHeight - mSlidingLayer.getOffsetDistance();
        }
    }
    public void openScanActivity() {
        if (cameraIsCanUse()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), ScanActivity.class);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_SCAN.getValue());
        } else {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .noCameraPermission));
        }
    }

}