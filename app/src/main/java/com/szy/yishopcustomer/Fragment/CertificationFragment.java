package com.szy.yishopcustomer.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.CertificationActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.MyGlideEngine;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.ProfileModel.InfoModel;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.DemoDialog;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


public class CertificationFragment extends YSCBaseFragment {
    private static final int UPLOAD_IMAGE = 1;

    @BindView(R.id.imageView_upload1)
    ImageView imageView_upload1;
    @BindView(R.id.imageView_upload1_close)
    ImageView imageView_upload1_close;

    @BindView(R.id.imageView_upload2)
    ImageView imageView_upload2;
    @BindView(R.id.imageView_upload2_close)
    ImageView imageView_upload2_close;

    @BindView(R.id.imageView_upload3)
    ImageView imageView_upload3;
    @BindView(R.id.imageView_upload3_close)
    ImageView imageView_upload3_close;

    @BindView(R.id.textView_demo1)
    TextView textView_demo1;
    @BindView(R.id.textView_demo2)
    TextView textView_demo2;
    @BindView(R.id.textView_demo3)
    TextView textView_demo3;

    @BindView(R.id.fragment_name)
    EditText fragment_name;
    @BindView(R.id.fragment_id_number)
    EditText fragment_id_number;

    @BindView(R.id.linearlayout_tips)
    LinearLayout linearlayout_tips;
    @BindView(R.id.textView_tips)
    TextView textView_tips;

    private String imageView_upload1_str = "";
    private String imageView_upload2_str = "";
    private String imageView_upload3_str = "";

    private View imageView_upload_click;

    private Context mContext;
    private ProgressDialog mProgressDialog;

    public ArrayList<String> idcard_demo_image;
    public InfoModel.RealInfoBean real_info;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_certification;

        Intent intent = getActivity().getIntent();
        real_info = intent.getParcelableExtra(CertificationActivity.PARAMS_REAL_INFO);
        idcard_demo_image = intent.getStringArrayListExtra(CertificationActivity.PARAMS_IDCARD_DEMO);

        mContext = getActivity();
        mProgressDialog = new ProgressDialog(mContext);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (real_info != null) {
            linearlayout_tips.setVisibility(View.VISIBLE);

            fragment_name.setText(real_info.real_name);
            fragment_id_number.setText(real_info.id_code);

            if (!TextUtils.isEmpty(real_info.card_pic1)) {
                ImageLoader.displayImage(Utils.urlOfImage(real_info.card_pic1), imageView_upload1);
                imageView_upload1_str = real_info.card_pic1;
                imageView_upload1_close.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(real_info.card_pic2)) {
                ImageLoader.displayImage(Utils.urlOfImage(real_info.card_pic2), imageView_upload2);
                imageView_upload2_str = real_info.card_pic2;
                imageView_upload2_close.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(real_info.card_pic3)) {
                ImageLoader.displayImage(Utils.urlOfImage(real_info.card_pic3), imageView_upload3);
                imageView_upload3_str = real_info.card_pic3;
                imageView_upload3_close.setVisibility(View.VISIBLE);
            }

            switch (real_info.status) {
                case 0:
                    textView_tips.setText("您已提交实名认证申请，等待平台方进行审核确认。");
                    break;
                case 1:
                    textView_tips.setText("恭喜您，您提交的实名认证申请已审核通过啦!");
                    //这个时候隐藏右上角的确认
                    break;
                case 2:
                    String reason = "信息不符";
                    if (!TextUtils.isEmpty(real_info.reason)) {
                        reason = real_info.reason;
                    }

                    textView_tips.setText("很抱歉，您提交的实名认证申请，经审查，【" + reason + "】，实名认证被驳回！");
                    break;
                default:
                    textView_tips.setText("");
                    linearlayout_tips.setVisibility(View.GONE);
                    break;
            }

            if (real_info != null && real_info.status != 1) {
                imageView_upload1.setOnClickListener(this);
                imageView_upload2.setOnClickListener(this);
                imageView_upload3.setOnClickListener(this);

                imageView_upload1_close.setOnClickListener(this);
                imageView_upload2_close.setOnClickListener(this);
                imageView_upload3_close.setOnClickListener(this);

                fragment_name.setEnabled(true);
                fragment_id_number.setEnabled(true);
            } else {
                fragment_name.setEnabled(false);
                fragment_name.setFocusable(false);
                fragment_id_number.setEnabled(false);
                fragment_id_number.setFocusable(false);

                imageView_upload1_close.setVisibility(View.GONE);
                imageView_upload2_close.setVisibility(View.GONE);
                imageView_upload3_close.setVisibility(View.GONE);
            }

            textView_demo1.setOnClickListener(this);
            textView_demo2.setOnClickListener(this);
            textView_demo3.setOnClickListener(this);

        } else {
            linearlayout_tips.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_upload1:
            case R.id.imageView_upload2:
            case R.id.imageView_upload3:
                imageView_upload_click = v;
                uploadPictures();
                break;
//            case R.id.fragment_apply_shop_confirmButton:
//                submit();
//                break;
            case R.id.imageView_upload1_close:
                v.setVisibility(View.GONE);
                imageView_upload1.setImageDrawable(null);
                imageView_upload1_str = "";
                break;
            case R.id.imageView_upload2_close:
                v.setVisibility(View.GONE);
                imageView_upload2.setImageDrawable(null);
                imageView_upload2_str = "";
                break;
            case R.id.imageView_upload3_close:
                v.setVisibility(View.GONE);
                imageView_upload3.setImageDrawable(null);
                imageView_upload3_str = "";
                break;
            case R.id.textView_demo1:
                if (idcard_demo_image != null && idcard_demo_image.size() > 0 && idcard_demo_image.get(0) != null) {
                    DemoDialog demoDialog1 = new DemoDialog(getContext(), idcard_demo_image.get(0));
                    demoDialog1.show();
                } else {
                    Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                            .noImage));
                }
                break;
            case R.id.textView_demo2:
                if (idcard_demo_image != null && idcard_demo_image.size() > 1 && idcard_demo_image.get(1) != null) {
                    DemoDialog demoDialog2 = new DemoDialog(getContext(), idcard_demo_image.get(1));
                    demoDialog2.show();
                } else {
                    Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                            .noImage));
                }
                break;
            case R.id.textView_demo3:
                if (idcard_demo_image != null && idcard_demo_image.size() > 2 && idcard_demo_image.get(2) != null) {
                    DemoDialog demoDialog3 = new DemoDialog(getContext(), idcard_demo_image.get(2));
                    demoDialog3.show();
                } else {
                    Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                            .noImage));
                }
                break;
            default:
                super.onClick(v);
        }
    }

    public void submit() {
        String name = fragment_name.getText().toString().trim();
        String id_number = fragment_id_number.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            toast("请输入真实姓名");
            return;
        }

        if (TextUtils.isEmpty(id_number)) {
            toast("请输入身份证号");
            return;
        }

        if (!Utils.IDNumber(id_number)) {
            toast("请输入正确的身份证号");
            return;
        }

        if (TextUtils.isEmpty(imageView_upload1_str)) {
            toast("请上传证件正面照片");
            return;
        }

        if (TextUtils.isEmpty(imageView_upload2_str)) {
            toast("请上传证件背面照片");
            return;
        }

        if (TextUtils.isEmpty(imageView_upload3_str)) {
            toast("请上传手持证件照片");
            return;
        }

        CommonRequest request = new CommonRequest(Api.API_USRE_PROFILE_EDIT_REAL, HttpWhat.HTTP_SUBMIT.getValue(),
                RequestMethod.POST);
        request.add("UserRealModel[real_name]", name);
        request.add("UserRealModel[id_code]", id_number);
        request.add("UserRealModel[card_pic1]", imageView_upload1_str);
        request.add("UserRealModel[card_pic2]", imageView_upload2_str);
        request.add("UserRealModel[card_pic3]", imageView_upload3_str);
        addRequest(request);
    }


    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_UPLOAD_IMAGE:
                HttpResultManager.resolve(response, UploadModel.class, new HttpResultManager.HttpResultCallBack<UploadModel>() {
                    @Override
                    public void onSuccess(UploadModel modelUpload) {
                        switch (imageView_upload_click.getId()) {
                            case R.id.imageView_upload1:
                                imageView_upload1_str = modelUpload.data.url;
                                ImageLoader.displayImage(Utils.urlOfImage(imageView_upload1_str),
                                        imageView_upload1);
                                imageView_upload1_close.setVisibility(View.VISIBLE);
                                break;
                            case R.id.imageView_upload2:
                                imageView_upload2_str = modelUpload.data.url;
                                ImageLoader.displayImage(Utils.urlOfImage(imageView_upload2_str),
                                        imageView_upload2);
                                imageView_upload2_close.setVisibility(View.VISIBLE);
                                break;
                            case R.id.imageView_upload3:
                                imageView_upload3_str = modelUpload.data.url;
                                ImageLoader.displayImage(Utils.urlOfImage(imageView_upload3_str),
                                        imageView_upload3);
                                imageView_upload3_close.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });

                break;
            case HTTP_SUBMIT:
                submitCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }


    private void submitCallback(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                toast("更新成功");

                setResult(getActivity().RESULT_OK);
                finish();
            }
        }, true);
    }

    int poi = 0;
    List<Uri> result = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RequestCode.valueOf(requestCode) != null) {
            switch (RequestCode.valueOf(requestCode)) {
                case REQUEST_PICK:  //调用系统相册返回
                    if (resultCode == RESULT_OK) {
                        result = Matisse.obtainResult(data);

                        if (!Utils.isNull(result)) {
                            zipImage(result.get(poi));
                        }
                    }
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } else {
            switch (requestCode) {
                case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                    if (resultCode == RESULT_OK) {
                        result.clear();
                        result.add(RxPhotoTool.imageUriFromCamera);
                        zipImage(RxPhotoTool.imageUriFromCamera);
                    }
                    break;
                default:
                    break;
            }
        }
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
                        RxPhotoTool.openCameraImage(CertificationFragment.this);
                    } else {
                        Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
                    }
//                    if (flag) {
//                        RxPhotoTool.openCameraImage(CertificationFragment.this);
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

    private static final int IMAGE_MAX_COUNT = 1;

    private void pickImage() {
        Matisse.from(this)
                .choose(MimeType.allOf())//图片类型
                .countable(false)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(IMAGE_MAX_COUNT)//可选的最大数
                .imageEngine(new MyGlideEngine())//图片加载引擎
                .forResult(RequestCode.REQUEST_PICK.getValue());
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
        CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HttpWhat
                .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        binary.setUploadListener(UPLOAD_IMAGE, mUploadListener);
        request.add("load_img", binary);
        addRequest(request);
    }
}
