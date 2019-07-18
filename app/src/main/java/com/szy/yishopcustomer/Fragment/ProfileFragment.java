package com.szy.yishopcustomer.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinaums.pppay.unify.UnifyPayListener;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.CertificationActivity;
import com.szy.yishopcustomer.Activity.ChangeNickNameActivity;
import com.szy.yishopcustomer.Activity.ChangeSexActivity;
import com.szy.yishopcustomer.Activity.CropImageDummyActivity;
import com.szy.yishopcustomer.Activity.EditPasswordActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Business;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ProfileModel.InfoModel;
import com.szy.yishopcustomer.ResponseModel.ProfileModel.ResponseProfileModel;
import com.szy.yishopcustomer.ResponseModel.ResponseProfile.ResponseAnotherProfileModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.szy.yishopcustomer.Util.Utils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by liuzhifeng on 2016/3/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ProfileFragment extends YSCBaseFragment implements UnifyPayListener {
    private static final String TAG = "ProfileFragment";
    private static final int UPLOAD_IMAGE = 1;
    //    private static final int REQUEST_CAPTURE = 100;
    //    private static final int REQUEST_PICK = 101;
    //    private static final int REQUEST_CROP_PHOTO = 102;

    @BindView(R.id.item_user_info_title_relativalayout)
    LinearLayout mTitleRelativeLayout;
    @BindView(R.id.item_user_info_username_relativalayout)
    LinearLayout mUserNameRelativeLayout;
    @BindView(R.id.item_user_info_nickname_relativalayout)
    LinearLayout mNicknameRelativeLayout;
    @BindView(R.id.item_user_info_sex_relativalayout)
    LinearLayout mGenderRelativeLayout;
    @BindView(R.id.item_user_info_birthday_relativalayout)
    LinearLayout mBirthdayRelativeLayout;
    @BindView(R.id.item_user_info_edit_password_layout)
    LinearLayout mEditPasswordLayout;
    @BindView(R.id.item_user_info_certification_layout)
    LinearLayout certificationLayout;
    @BindView(R.id.item_user_info_avatar_imageView)
    CircleImageView mAvatarImageView;
    @BindView(R.id.item_user_info_username_textView)
    TextView mUserNameTextView;
    @BindView(R.id.item_user_info_nickname_textView)
    TextView mNicknameTextView;
    @BindView(R.id.item_user_info_sex_textView)
    TextView mGenderTextView;
    @BindView(R.id.item_user_info_birthday_textView)
    TextView mBirthdayTextView;

//    @BindView(R.id.img_bank_wx)
//    ImageView mWxImg;
//    @BindView(R.id.img_bank_zfb)
//    ImageView mZfbImg;

    private String username;
    private String birthday;
    private String nickname;
    private String sex;
    private String heading;
    private String mDetailAddress;
    private String mAddressId;
    private String mCurrentImagePath;
    private String mCroppedImagePath;
    private ProgressDialog mProgressDialog;
    private File tempFile;

    public ArrayList<String> idcard_demo_image;
    public InfoModel.RealInfoBean real_info;

    private JSONObject payJson = new JSONObject();

    private UnifyPayPlugin mPayPlugin;
    private UnifyPayRequest mPayRequest;

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

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore
                    .Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RequestCode.valueOf(requestCode) != null) {
            switch (RequestCode.valueOf(requestCode)) {
                case REQUEST_PICK:  //调用系统相册返回
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        initUCrop(data.getData());
                    }
                    break;
                case REQUEST_CODE_NICK_NAME://名称
                    if (data != null) {
                        Bundle nicknamedata = data.getExtras();
                        String name = nicknamedata.getString("NICKNAME");
                        //                    mNicknameTextView.setText(name);
                        CommonRequest requestnickname = new CommonRequest(Api.API_USER_INFO, HttpWhat
                                .HTTP_UP_DATE_USER_INFO.getValue(), RequestMethod.POST);
                        requestnickname.add("UserModel[birthday]", birthday);
                        requestnickname.add("UserModel[detail_address]", mDetailAddress);
                        requestnickname.add("UserModel[nickname]", name);
                        requestnickname.add("UserModel[sex]", sex);
                        requestnickname.add("UserModel[address_now]", mAddressId);
                        addRequest(requestnickname);
                    } else {
                        return;
                    }
                    break;
                case REQUEST_CODE_GENDER:// 性别
                    if (data != null) {
                        Bundle sexdata = data.getExtras();
                        String sex = sexdata.getString("SEX");
                        //                    mGenderTextView.setText(sex);
                        CommonRequest requestsex = new CommonRequest(Api.API_USER_INFO, HttpWhat
                                .HTTP_UP_DATE_USER_INFO.getValue(), RequestMethod.POST);

                        requestsex.add("UserModel[birthday]", birthday);
                        requestsex.add("UserModel[detail_address]", mDetailAddress);
                        requestsex.add("UserModel[nickname]", nickname);
                        requestsex.add("UserModel[sex]", sex);
                        requestsex.add("UserModel[address_now]", mAddressId);
                        addRequest(requestsex);
                    } else {
                        return;
                    }
                    break;
                case REQUEST_CODE_SERVICE:
                    refresh();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }

        } else {
            switch (requestCode) {
                case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = UCrop.getOutput(data);
                        uploadAvatar(roadImageView(resultUri));
                    } else if (resultCode == UCrop.RESULT_ERROR) {
                        final Throwable cropError = UCrop.getError(data);
                    }
                    break;
                case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                    final Throwable cropError = UCrop.getError(data);
                    break;
                case RxPhotoTool.CROP_IMAGE:
                    Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                    break;
                case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                    if (resultCode == RESULT_OK) {
                   /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                        initUCrop(RxPhotoTool.imageUriFromCamera);
                    }

                    break;
                default:
                    break;
            }
        }

    }


    public String roadImageView(Uri uri) {
        return (RxPhotoTool.getImageAbsolutePath(getActivity(), uri));
    }

    public void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(getActivity(), R.color.aliwx_black));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(getActivity(), R.color.aliwx_black));

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

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(getContext(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_user_info_title_relativalayout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] arr = new String[]{"拍照", "本地选择"};
                builder.setItems(arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
//                            PackageManager pm = getActivity().getPackageManager();
//                            boolean flag = (PackageManager.PERMISSION_GRANTED == pm
//                                    .checkPermission("android.permission.CAMERA", App.packageName));
                            //原来的方式
//                            if (cameraIsCanUse()) {
//                                RxPhotoTool.openCameraImage(ProfileFragment.this);
//                            } else {
//                                Utils.toastUtil.showToast(getActivity(), getResources().getString
//                                        (R.string.noCameraPermission));
//                            }

                            //加上动态权限
                            if (hasPermission(Business.PERMISSIONS)){
                                RxPhotoTool.openCameraImage(ProfileFragment.this);
                            }else {
                                requestPermission(Business.PEIRMISSION_CODE,Business.PERMISSIONS);
                            }


//                            if (flag) {
//                                RxPhotoTool.openCameraImage(ProfileFragment.this);
//                            } else {
//                                Utils.toastUtil.showToast(getActivity(), getResources().getString
//                                        (R.string.noCameraPermission));
//                            }

                        } else {
                            //加上动态权限
                            if (hasPermission(Business.PERMISSIONS)){
                                pickImage();
                            }else {
                                requestPermission(Business.PEIRMISSION_CODE,Business.PERMISSIONS);
                            }
                        }
                    }

                });
                builder.create().show();
                break;
            case R.id.item_user_info_nickname_relativalayout:
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_NIKE_NAME.getValue(), nickname);
                intent.setClass(getContext(), ChangeNickNameActivity.class);
                startActivityForResult(intent, RequestCode.REQUEST_CODE_NICK_NAME.getValue());
                break;
            case R.id.item_user_info_sex_relativalayout:
                startActivityForResult(new Intent().setClass(getContext(), ChangeSexActivity
                                .class).putExtra("sex", mGenderTextView.getText().toString()),
                        RequestCode.REQUEST_CODE_GENDER.getValue());
                break;
            case R.id.item_user_info_birthday_relativalayout:
                pickDate();
                break;
            case R.id.item_user_info_edit_password_layout:
                editPassword();
                break;
            case R.id.item_user_info_certification_layout:
                openCertification();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    /**
     * 获取动态权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Business.PEIRMISSION_CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//允许
                        RxPhotoTool.openCameraImage(ProfileFragment.this);
                    } else {//禁止
//                        XXPermissions.gotoPermissionSettings(getActivity());
                        Utils.toastUtil.showToast(getActivity(), "相关权限被禁止");
                    }
                } else {
                    Utils.toastUtil.showToast(getActivity(),  "相关权限被禁止,请在设置里面去开起相关权限");
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mPayPlugin = UnifyPayPlugin.getInstance(getActivity()).setListener(this);
        mPayRequest = new UnifyPayRequest();

        mTitleRelativeLayout.setOnClickListener(this);
        mNicknameRelativeLayout.setOnClickListener(this);
        mGenderRelativeLayout.setOnClickListener(this);
        mBirthdayRelativeLayout.setOnClickListener(this);
        mEditPasswordLayout.setOnClickListener(this);
        certificationLayout.setOnClickListener(this);

//        mWxImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                try {
//                    payJson.put("orderId", "62770461985712103");
//                    payJson.put("payMent", 1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                getPayData();
//            }
//        });
//
//        mZfbImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    payJson.put("orderId", "62770461985712105");
//                    payJson.put("payMent", 2);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                getPayData();
//            }
//        });

        createCameraTempFile(savedInstanceState);
        return v;
    }

//    /***
//     *
//     *  订单号 62770461985712103  62770461985712105
//     *  支付类型(1微信 2支付宝)
//     */
//    private void getPayData() {
//
//        CommonRequest request = new CommonRequest(Api.API_BANK_PAY_DATA, HttpWhat.HTTP_PAY_BANK_DATA.getValue(), RequestMethod.POST);
//        RequestAddHead.addHead(request, getActivity(), Api.API_BANK_PAY_DATA, "POST");
//        request.setDefineRequestBodyForJson(payJson);
//        addRequest(request);
//    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_UPLOAD_IMAGE:
                Utils.toastUtil.showToast(getActivity(), "fail");
                break;

        }
        super.onRequestFailed(what, response);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_UPLOAD_IMAGE:
                ResponseCommonModel mData = JSON.parseObject(response, ResponseCommonModel.class);
                if (mData.code == 0) {
                    setImage(mAvatarImageView, mCroppedImagePath);
                } else {
                    Utils.makeToast(getActivity(), mData.message);
                }
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue()));
                break;
            case HTTP_GET_USER_INFO:
                ResponseProfileModel mDataTwo = JSON.parseObject(response, ResponseProfileModel
                        .class);
                if (mDataTwo.code == 0) {

                    idcard_demo_image = mDataTwo.data.idcard_demo_image;
                    real_info = mDataTwo.data.info.real_info;

                    username = mDataTwo.data.info.user_info.user_name;
                    birthday = mDataTwo.data.info.user_info.birthday;
                    nickname = mDataTwo.data.info.user_info.nickname;
                    sex = mDataTwo.data.info.user_info.sex;
                    heading = mDataTwo.data.info.user_info.headimg;
                    mDetailAddress = mDataTwo.data.info.user_info.detail_address;
                    mAddressId = mDataTwo.data.info.user_info.address_now;
                    mBirthdayTextView.setText(Utils.toDateString(birthday));
                    if (nickname == null) {
                        nickname = "";
                    }
                    mNicknameTextView.setText(nickname);
                    mUserNameTextView.setText(username);
                    if (sex.equals("1")) {
                        mGenderTextView.setText("男");
                    } else if (sex.equals("2")) {
                        mGenderTextView.setText("女");
                    } else {
                        mGenderTextView.setText("保密");
                    }
                    ImageLoader.displayImage(Utils.urlOfImage(heading), mAvatarImageView);

                } else {
                    Utils.makeToast(getActivity(), mDataTwo.message);
                }

                break;
            case HTTP_UP_DATE_USER_INFO:
                ResponseAnotherProfileModel mDataThree = JSON.parseObject(response,
                        ResponseAnotherProfileModel.class);
                if (mDataThree.code == 0) {

                    birthday = mDataThree.data.model.birthday;
                    nickname = mDataThree.data.model.nickname;
                    sex = mDataThree.data.model.sex;

                    mBirthdayTextView.setText(Utils.toDateString(birthday));
                    if (nickname.equals("null")) {
                        nickname = "";
                    }
                    mNicknameTextView.setText(nickname);
                    if (sex.equals("1")) {
                        mGenderTextView.setText("男");
                    } else if (sex.equals("2")) {
                        mGenderTextView.setText("女");
                    } else {
                        mGenderTextView.setText("保密");
                    }
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue()));
                } else {
                    Utils.makeToast(getActivity(), mDataThree.message);
                }
                break;
//            case HTTP_PAY_BANK_DATA:
//
//                String pay_data = com.alibaba.fastjson.JSON.parseObject(response).getString("data");
//                try {
//                    if (payJson.getInt("payMent") == 1) {
//                        payWx(pay_data);
//                    } else if (payJson.getInt("payMent") == 2) {
//                        payAli(pay_data);
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void payWx(String data) {
        mPayRequest.payChannel = UnifyPayRequest.CHANNEL_WEIXIN;
        mPayRequest.payData = data;

        mPayPlugin.sendPayRequest(mPayRequest);
    }

    private void payAli(String data) {
        mPayRequest.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        mPayRequest.payData = data;

        mPayPlugin.sendPayRequest(mPayRequest);
    }

    @Override
    public void onResult(String resultCode, String resultInfo) {
        Log.d("WYX", "支付结果回调/" + resultCode + "///" + resultInfo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_user_info;
        mProgressDialog = new ProgressDialog(getContext());
    }

    void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_PROFILE_INDEX, HttpWhat
                .HTTP_GET_USER_INFO.getValue());
        addRequest(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    /**
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath()
                    + "/image/"), System.currentTimeMillis() + ".jpg");
        }
    }

    private File createOutputFile(String prefix) throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(prefix, ".jpg", storageDir);
    }

    private void cropImage(Uri imageUri, Uri outputUri) {
        Intent intent = new Intent(getContext(), CropImageDummyActivity.class);
        intent.putExtra(Key.KEY_IMAGE_URI.getValue(), imageUri);
        intent.putExtra(Key.KEY_IMAGE_OUTPUT_URI.getValue(), outputUri);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_CROP_IMAGE.getValue());
    }

    private void galleryAddImage(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File imageFile = new File(imagePath);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private String getCurrentData() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    private void pickDate() {
        Calendar cal = Calendar.getInstance();
        String[] date = Utils.toDateString(birthday).split("-");
/*        final DatePickerDialog mDialog = new DatePickerDialog(getActivity(), null, cal.get
                (Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));*/
        final DatePickerDialog mDialog = new DatePickerDialog(getActivity(), null, Integer
                .parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));

        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                String month = String.valueOf(datePicker.getMonth() + 1);

                if (Integer.valueOf(month) < 10) {
                    month = "0" + String.valueOf(datePicker.getMonth() + 1);
                }
                String day = String.valueOf(datePicker.getDayOfMonth());
                if (Integer.valueOf(day) < 10) {
                    day = "0" + String.valueOf(datePicker.getDayOfMonth());
                }
                long currentData = Utils.toTimestamp(getCurrentData());
                long data = Utils.toTimestamp(year + "-" + month + "-" + day);
                if (data >= currentData) {
                    Utils.toastUtil.showToast(getActivity(), "您选择的日期不合法");
                    return;
                }
                CommonRequest request = new CommonRequest(Api.API_USER_INFO, HttpWhat
                        .HTTP_UP_DATE_USER_INFO.getValue(), RequestMethod.POST);
                request.add("UserModel[birthday]", data);
                request.add("UserModel[detail_address]", mDetailAddress);
                request.add("UserModel[nickname]", nickname);
                request.add("UserModel[sex]", sex);
                request.add("UserModel[address_now]", mAddressId);
                addRequest(request);
            }
        });

        mDialog.show();
    }

    private void openCertification() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CertificationActivity.class);
        intent.putStringArrayListExtra(CertificationActivity.PARAMS_IDCARD_DEMO, idcard_demo_image);
        intent.putExtra(CertificationActivity.PARAMS_REAL_INFO, real_info);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_SERVICE.getValue());
    }

    private void editPassword() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), EditPasswordActivity.class);
        startActivity(intent);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RequestCode.REQUEST_PICK
                .getValue());
    }

    private void setImage(ImageView imageView, String imagePath) {
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void uploadAvatar(String imagePath) {
        CommonRequest request = new CommonRequest(Api.API_USER_PROFILE_UPLOAD, HttpWhat
                .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        binary.setUploadListener(UPLOAD_IMAGE, mUploadListener);
        request.add("load_img", binary);
        addRequest(request);
    }
}