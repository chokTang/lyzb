package com.szy.yishopcustomer.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribShopSetModel;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribShopSetValueModel;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by liwei on 2017/9/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribShopSetFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_SHOP_INFO = 1;
    private static final int HTTP_WHAT_SHOP_SET = 2;

    private static final int UPLOAD_IMAGE = 3;
    private static final int HTTP_UPLOAD_IMAGE = 4;

    @BindView(R.id.fragment_distrib_shop_set_layout)
    LinearLayout mShopSetLayout;

    @BindView(R.id.small_shop_logo_layout)
    LinearLayout mShopLogoLayout;
    @BindView(R.id.small_shop_name_layout)
    LinearLayout mShopNameLayout;
    @BindView(R.id.small_shop_QQ_layout)
    LinearLayout mShopQQLayout;
    @BindView(R.id.small_shop_weixin_layout)
    LinearLayout mShopWeixinLayout;
    @BindView(R.id.small_shop_background_layout)
    LinearLayout mShopBackgroundLayout;

    @BindView(R.id.fragment_distrib_shop_set_value_layout)
    LinearLayout mShopSetValueLayout;
    @BindView(R.id.fragment_distrib_shop_set_tip)
    TextView mShopSetTip;
    @BindView(R.id.fragment_distrib_shop_set_value)
    CommonEditText mShopSetValue;

    @BindView(R.id.item_small_shop_logo)
    ImageView mShopLogo;
    @BindView(R.id.item_small_shop_name)
    TextView mShopName;
    @BindView(R.id.item_small_shop_qq)
    TextView mShopQQ;
    @BindView(R.id.item_small_shop_weixin)
    TextView mShopWeixin;
    @BindView(R.id.item_small_shop_background)
    ImageView mShopBackground;
    private DistribShopSetModel.DataBean.ShopInfoModel model;

    private String shop_headimg;
    private String shop_name;
    private String qq;
    private String wechat;
    private String shop_background;
    private String type;
    public String layouttype = "normal";

    public static File tempFile;
    private Uri imageUri;
    private ProgressDialog mProgressDialog;

    private OnUploadListener mUploadListener = new OnUploadListener() {
        @Override
        public void onStart(int what) {
            mProgressDialog.setMessage(getString(R.string.uploadStart));
            mProgressDialog.show();
        }

        @Override
        public void onCancel(int what) {
            mProgressDialog.setMessage(getString(R.string.uploadCanceled));
            mProgressDialog.hide();
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            mProgressDialog.setProgress(progress);
        }

        @Override
        public void onFinish(int what) {
            mProgressDialog.setMessage(getString(R.string.uploadFinish));
            mProgressDialog.hide();
        }

        @Override
        public void onError(int what, Exception exception) {
            mProgressDialog.setMessage(String.format(getString(R.string.uploadError), exception
                    .getMessage()));
            mProgressDialog.hide();
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    zipImage(imageUri);
                }

                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    zipImage(uri);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib_shop_set;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mShopLogoLayout.setOnClickListener(this);
        mShopNameLayout.setOnClickListener(this);
        mShopQQLayout.setOnClickListener(this);
        mShopWeixinLayout.setOnClickListener(this);
        mShopBackgroundLayout.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(getActivity());

        refresh();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.small_shop_logo_layout:
                type = "logo";
                uploadPictures();
                break;
            case R.id.small_shop_name_layout:
                showEditLayout("店铺名称：",model.shop_name,"name");
                break;
            case R.id.small_shop_QQ_layout:
                showEditLayout("店主Q  Q：",model.qq,"qq");
                break;
            case R.id.small_shop_weixin_layout:
                showEditLayout("店主微信：",model.wechat,"wechat");
                break;
            case R.id.small_shop_background_layout:
                type = "background";
                uploadPictures();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                switch (viewType) {
                    default:
                        super.onClick(v);
                }
        }
    }

    public void showEditLayout(String tip,String value,String editType) {
        mShopSetLayout.setVisibility(View.GONE);
        mShopSetValueLayout.setVisibility(View.VISIBLE);
        mShopSetTip.setText(tip);
        mShopSetValue.setText(value);

        layouttype = "edit";
        type = editType;

        switch (type){
            case "name":
                getActivity().setTitle("微店名称");
                break;
            case "qq":
                getActivity().setTitle("QQ");
                break;
            case "wechat":
                getActivity().setTitle("微信号");
                break;
        }

        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_DISTRIB_MENU_CHANGE.getValue(),
                "edit"));

    }

    public void showNormalLayout() {
        layouttype = "normal";
        mShopSetLayout.setVisibility(View.VISIBLE);
        mShopSetValueLayout.setVisibility(View.GONE);
        mShopSetTip.setText("");
        mShopSetValue.setText("");
        getActivity().setTitle("微店信息");

        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_DISTRIB_MENU_CHANGE.getValue(),
                "normal"));
    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_SHOP_SET, HTTP_WHAT_SHOP_INFO);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_SHOP_INFO:
                refreshCallback(response);
                break;
            case HTTP_WHAT_SHOP_SET:
                updateDataCallBack(response);
                break;
            case HTTP_UPLOAD_IMAGE:
                HttpResultManager.resolve(response, UploadModel.class, new HttpResultManager.HttpResultCallBack<UploadModel>() {
                    @Override
                    public void onSuccess(UploadModel modelUpload) {
                        if (type.equals("logo")) {
                            shop_headimg = modelUpload.data.url;
                            ImageLoader.displayImage(Utils.urlOfImage(shop_headimg),
                                    mShopLogo);
                            submit();
                        } else {
                            shop_background = modelUpload.data.url;
                            ImageLoader.displayImage(Utils.urlOfImage(shop_background),
                                    mShopBackground);
                            submit();
                        }
                    }
                });

                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, DistribShopSetModel.class, new HttpResultManager
                .HttpResultCallBack<DistribShopSetModel>() {
            @Override
            public void onSuccess(DistribShopSetModel back) {
                model = back.data.model;

                //初始值
                //shop_headimg = model.shop_headimg;
                shop_name = model.shop_name;
                qq = model.qq;
                wechat = model.wechat;
                //shop_background = model.shop_background;

                //页面赋值
                if(!Utils.isNull(model.shop_headimg)){
                    ImageLoader.displayImage(Utils.urlOfImage(back.data.model.shop_headimg), mShopLogo);
                }
                mShopName.setText(model.shop_name);
                mShopQQ.setText(model.qq);
                mShopWeixin.setText(model.wechat);
                if(!Utils.isNull(model.shop_background)){
                    ImageLoader.displayImage(Utils.urlOfImage(back.data.model.shop_background), mShopBackground);
                }
            }
        });
    }

    public void submit(){

        switch (type){
            case "name":
                shop_name = mShopSetValue.getText().toString();
                break;
            case "qq":
                qq = shop_name = mShopSetValue.getText().toString();
                break;
            case "wechat":
                wechat = mShopSetValue.getText().toString();
                break;
        }

        CommonRequest requestsex = new CommonRequest(Api.API_DISTRIB_SHOP_SET_EDIT_SHOP, HTTP_WHAT_SHOP_SET, RequestMethod.POST);
        requestsex.add("Distributor[shop_name]", shop_name);
        requestsex.add("Distributor[qq]", qq);
        requestsex.add("Distributor[wechat]", wechat);

        if(!TextUtils.isEmpty(shop_headimg)){
            requestsex.add("Distributor[shop_headimg]", shop_headimg);
        }

        if(!TextUtils.isEmpty(shop_background)){
            requestsex.add("Distributor[shop_background]", shop_background);
        }

        addRequest(requestsex);
    }

    private void updateDataCallBack(String response) {
        HttpResultManager.resolve(response, DistribShopSetValueModel.class, new HttpResultManager
                .HttpResultCallBack<DistribShopSetValueModel>() {
            @Override
            public void onSuccess(DistribShopSetValueModel back) {
                Toast.makeText(getActivity(),"更新成功",Toast.LENGTH_SHORT).show();
                if(!Utils.isNull(back.data.model.shop_headimg)){
                    ImageLoader.displayImage(Utils.urlOfImage(back.data.model.shop_headimg), mShopLogo);
                }
                mShopName.setText(back.data.model.shop_name);
                mShopQQ.setText(back.data.model.qq);
                mShopWeixin.setText(back.data.model.wechat);
                if(!Utils.isNull(back.data.model.shop_background)){
                    ImageLoader.displayImage(Utils.urlOfImage(back.data.model.shop_background), mShopBackground);
                }
            }
        });

        showNormalLayout();
    }

    public void uploadPictures() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] arr = new String[]{"拍照", "本地选择"};
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
//                    PackageManager pm = getActivity().getPackageManager();
//                    boolean flag = (PackageManager.PERMISSION_GRANTED == pm
//                            .checkPermission("android.permission.CAMERA", App.packageName));
                    if (cameraIsCanUse()) {
                        openCamera(getActivity());
                    } else {
                        Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
                    }
//                    if (flag) {
//                        openCamera(getActivity());
//                    } else {
//                        Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
//                    }
                } else {
                    pickImage();
                }
            }

        });
        builder.create().show();
    }

    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (Utils.hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());

                if (ContextCompat.checkSelfPermission(activity, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(activity, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media
                        .EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, RequestCode.REQUEST_CAPTURE.getValue());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RequestCode.REQUEST_PICK
                .getValue());
    }

    private void zipImage(Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = Utils.getBitmapFormUri(getActivity(), uri);
            OutputStream fOut;
            File file = createOutputFile("compressed");

            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

            uploadAvatar(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createOutputFile(String prefix) throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(prefix, ".jpg", storageDir);
    }


    private void uploadAvatar(String imagePath) {
        CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HTTP_UPLOAD_IMAGE, RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        binary.setUploadListener(UPLOAD_IMAGE, mUploadListener);
        request.add("load_img", binary);
        addRequest(request);
    }


}
