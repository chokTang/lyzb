package com.szy.yishopcustomer.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.szy.common.Activity.RegionActivity;
import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Fragment.RegionFragment;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.AddressReceiveDetailPoiAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Region.ResponseRegionModel;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.ResponseModel.SelectAddress.PoiListModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ApplyRecommShopFragment extends YSCBaseFragment implements View.OnFocusChangeListener, View.OnTouchListener, PoiSearch.OnPoiSearchListener, TextWatcherAdapter.TextWatcherListener {

    private static final int UPLOAD_IMAGE = 1;
    public static File tempFile;
    public AddressReceiveDetailPoiAdapter mSelectAddressAdapter;
    @BindView(R.id.scrollView_parent)
    ScrollView scrollView_parent;
    @BindView(R.id.linearlayout_parent)
    LinearLayout linearlayout_parent;
    @BindView(R.id.linearlayout_detailed_address)
    LinearLayout linearlayout_detailed_address;
    @BindView(R.id.commonEditText_shop_name)
    CommonEditText commonEditText_shop_name;
    @BindView(R.id.textView_delivery_address)
    TextView textView_delivery_address;
    @BindView(R.id.commonEditText_contact_number)
    CommonEditText commonEditText_contact_number;
    @BindView(R.id.commonEditText_detailed_address)
    CommonEditText commonEditText_detailed_address;
    @BindView(R.id.commonEditText_recommended_reasons)
    CommonEditText commonEditText_recommended_reasons;
    @BindView(R.id.linearlayout_delivery_address)
    LinearLayout linearlayout_delivery_address;
    @BindView(R.id.imageView_upload1)
    ImageView imageView_upload1;
    @BindView(R.id.imageView_upload2)
    ImageView imageView_upload2;
    @BindView(R.id.imageView_upload1_close)
    ImageView imageView_upload1_close;
    @BindView(R.id.imageView_upload2_close)
    ImageView imageView_upload2_close;
    @BindView(R.id.submit_button)
    Button fragment_apply_shop_confirmButton;
    @BindView(R.id.fragment_address_detail_labelTextView_complete)
    TextView fragment_address_detail_labelTextView_complete;
    @BindView(R.id.fragment_address_poi_list_parent)
    LinearLayout fragment_address_poi_list_parent;
    @BindView(R.id.fragment_address_poi_list)
    CommonRecyclerView fragment_address_poi_list;
    private ProgressDialog mProgressDialog;
    private String region_name;

    //    private List<String> mShareOrderImgList = new ArrayList<>();
    private String region_code;
    private String imageView_upload1_str = "";
    private String imageView_upload2_str = "";
    //除去标题栏的view高度
    private int viewHeight;
    //poi搜索
    private String city = App.getInstance().city;
    private Context mContext;
    private boolean canScroll = true;
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
    private boolean imageView_upload_click = false;
    private String shop_name = "";
    private String mobile = "";
    private String address = "";
    private String res_reason = "";
    private Uri imageUri;

    //是否是手动输入的
    private boolean isInput = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_apply_recomm_shop;

        mContext = getActivity();
        mProgressDialog = new ProgressDialog(mContext);

        mSelectAddressAdapter = new AddressReceiveDetailPoiAdapter();
        mSelectAddressAdapter.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewType viewType = Utils.getViewTypeOfTag(view);
                switch (viewType) {
                    case VIEW_TYPE_NEAR_POI_ITEM:
                        int position = Utils.getPositionOfTag(view);
                        PoiListModel subPoiListModel = (PoiListModel) mSelectAddressAdapter.data
                                .get(position);

                        isInput = false;
                        commonEditText_detailed_address.setText(subPoiListModel.content + " " + subPoiListModel.title);
                        region_code = subPoiListModel.regionCode;

//                        mModel.data.model.address_lat = subPoiListModel.latLonPoint.getLatitude() +"";
//                        mModel.data.model.address_lng = subPoiListModel.latLonPoint.getLongitude() + "";
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        imageView_upload1.setOnClickListener(this);
        imageView_upload2.setOnClickListener(this);
        fragment_apply_shop_confirmButton.setOnClickListener(this);
        linearlayout_delivery_address.setOnClickListener(this);

        imageView_upload1_close.setOnClickListener(this);
        imageView_upload2_close.setOnClickListener(this);

        fragment_address_detail_labelTextView_complete.setOnClickListener(this);


        commonEditText_recommended_reasons.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        commonEditText_recommended_reasons.setGravity(Gravity.TOP);
        commonEditText_recommended_reasons.setSingleLine(false);
        commonEditText_recommended_reasons.setHorizontallyScrolling(false);
        fragment_apply_shop_confirmButton.setText(getResources().getString(R.string.submit));
        fragment_apply_shop_confirmButton.setEnabled(true);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        viewHeight = view.getMeasuredHeight();
        viewHeight = viewHeight - Utils.dpToPx(getActivity(), 50);

        commonEditText_detailed_address.setOnClickListener(this);
        commonEditText_detailed_address.setOnFocusChangeListener(this);
        commonEditText_detailed_address.setTextWatcherListener(this);
        scrollView_parent.setOnTouchListener(this);


        fragment_address_poi_list.setLayoutManager(new LinearLayoutManager(mContext));
        fragment_address_poi_list.setAdapter(mSelectAddressAdapter);

//        fragment_address_poi_list_parent.getLayoutParams().height = Utils.getAppAreaHeight(mContext) - Utils.dpToPx(mContext, 100);

        showLocation();


        return view;
    }


    @Override
    public void onTextChanged(EditText view, String text) {
        switch (view.getId()) {
            case R.id.commonEditText_detailed_address:
                String queryWord = view.getText().toString();

                if (view.isFocused()) {
                    searchPoiInfo(queryWord);
                }

                isInput = true;
                break;
        }
    }

    private void searchPoiInfo(final String queryWord) {

        if (isInput) {
            int currentPage = 0;
            PoiSearch.Query query = new PoiSearch.Query(queryWord, "", city);
            query.setPageSize(1);// 设置每页最多返回多少条poiitem
            query.setPageNum(currentPage);// 设置查第一页
            PoiSearch poiSearch = new PoiSearch(getContext(), query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    if (!Utils.isNull(poiResult)) {
                        ArrayList<PoiItem> pois = poiResult.getPois();
                        for (int j = 0; j < 1; j++) {
                            PoiItem item = pois.get(j);
                            searchPoiInfo(queryWord, item.getLatLonPoint());
                        }
                    } else {
                        if (!TextUtils.isEmpty(queryWord)) {
                            Toast.makeText(getActivity(), "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    private void searchPoiInfo(String queryWord, LatLonPoint latlon) {
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(queryWord, "", city);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setBound(new PoiSearch.SearchBound(latlon, 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_UPLOAD_IMAGE:
                HttpResultManager.resolve(response, UploadModel.class, new HttpResultManager.HttpResultCallBack<UploadModel>() {
                    @Override
                    public void onSuccess(UploadModel modelUpload) {
                        if (imageView_upload_click) {
                            imageView_upload_click = false;
                            imageView_upload1_str = modelUpload.data.url;
                            ImageLoader.displayImage(Utils.urlOfImage(imageView_upload1_str),
                                    imageView_upload1);
                            imageView_upload1_close.setVisibility(View.VISIBLE);
                        } else {
                            imageView_upload2_str = modelUpload.data.url;
                            ImageLoader.displayImage(Utils.urlOfImage(imageView_upload2_str),
                                    imageView_upload2);
                            imageView_upload2_close.setVisibility(View.VISIBLE);
                        }
                    }
                });

                break;
            case HTTP_GET_REGION_NAME:
                refreshRegionNameCallback(response);
                break;
            case HTTP_SUBMIT:
                submitCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    private void submitCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();

                getActivity().setResult(getActivity().RESULT_OK, new Intent());
                getActivity().finish();
            }
        }, true);
    }

    private void refreshRegionNameCallback(String response) {

        Utils.showSoftInputFromWindowTwo(getActivity(), commonEditText_shop_name);

        HttpResultManager.resolve(response, ResponseRegionModel.class, new HttpResultManager.HttpResultCallBack<ResponseRegionModel>() {
            @Override
            public void onSuccess(ResponseRegionModel mData) {
                region_name = mData.data.region_name;
                region_code = mData.data.region_code;

                city = getTwoForStringSplit(region_name);
                textView_delivery_address.setText(region_name);
            }
        });
    }

    /**
     * 选择地区
     *
     * @param regionCode
     */
    private void openRegionActivity(String regionCode) {
        Intent intent = new Intent(getContext(), RegionActivity.class);
        intent.putExtra(RegionFragment.KEY_REGION_CODE, regionCode);
        intent.putExtra(RegionFragment.KEY_API, Api.API_REGION_LIST);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_REGION_CODE.getValue());
    }

    private void showLocation() {
        CommonRequest request = new CommonRequest(Api.API_GET_REGION_NAME, HttpWhat
                .HTTP_GET_REGION_NAME.getValue());
        if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng) && !App
                .getInstance().lat.equals("0") && !App.getInstance().lat.equals("0")) {
            request.add("lat", App.getInstance().lat);
            request.add("lng", App.getInstance().lng);
            addRequest(request);
        } else {
            Utils.toastUtil.showToast(getActivity(), "定位失败，请手动选择！");
            request.add("lat", "");
            request.add("lng", "");
            addRequest(request);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_upload1:
                imageView_upload_click = true;
                uploadPictures();
                break;
            case R.id.imageView_upload2:
                uploadPictures();
                break;
            case R.id.submit_button:
                submit();
                break;
            case R.id.linearlayout_delivery_address:
                openRegionActivity(region_code);
                break;
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
            case R.id.commonEditText_detailed_address:
                if (v.isFocused()) {
                    openPoiList();
                }
                break;
            case R.id.fragment_address_detail_labelTextView_complete:
                closePoiList();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.commonEditText_detailed_address:
                if (hasFocus) {
                    openPoiList();
                }
                break;

        }
    }

    private void openPoiList() {
        fragment_address_detail_labelTextView_complete.setVisibility(View.VISIBLE);
        canScroll = false;
        float y = linearlayout_detailed_address.getY();
        linearlayout_parent.scrollTo(0, (int) y);
        fragment_address_poi_list_parent.setVisibility(View.VISIBLE);

        searchPoiInfo("1");
    }

    private void closePoiList() {
        fragment_address_detail_labelTextView_complete.setVisibility(View.GONE);
        canScroll = true;
        linearlayout_parent.scrollTo(0, 0);
        fragment_address_poi_list_parent.setVisibility(View.GONE);
    }

    private boolean check() {
        String message = "";

        shop_name = commonEditText_shop_name.getText().toString();
        mobile = commonEditText_contact_number.getText().toString();
        res_reason = commonEditText_recommended_reasons.getText().toString();
        address = commonEditText_detailed_address.getText().toString();

        if (Utils.isNull(shop_name)) {
            message = "请输入店铺名称";
        } else if (Utils.isNull(mobile)) {
            message = "请输入联系电话";
        } else if (!Utils.isPhone(mobile)) {
            message = "手机号格式不正确";
        } else if (Utils.isNull(region_code)) {
            message = "请选择收货地址";
        } else if (Utils.isNull(address)) {
            message = "请输入详细地址";
        }
        if (Utils.isNull(res_reason)) {
            message = "请输入推荐理由";
        }

        if (Utils.isNull(message)) {
            return true;
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 推荐这个店铺
     */
    private void submit() {
        if (!check()) {
            return;
        }
        CommonRequest request = new CommonRequest(Api.API_USER_RECOMMEND_SHOP_ADD, HttpWhat.HTTP_SUBMIT.getValue(),
                RequestMethod.POST);
        request.setAjax(true);
        request.add("RecommendShopModel[id]", "");
        request.add("RecommendShopModel[shop_name]", shop_name);
        request.add("RecommendShopModel[mobile]", mobile);
        request.add("RecommendShopModel[region_code]", region_code);
        request.add("RecommendShopModel[address]", address);
        request.add("RecommendShopModel[res_reason]", res_reason);
        request.add("RecommendShopModel[facade_img]", imageView_upload1_str);
        request.add("RecommendShopModel[card_img]", imageView_upload2_str);
        addRequest(request);
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
//                        //takePhoto();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                   /* Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image*//*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, RequestCode.REQUEST_CROP_PHOTO.getValue());*/
                    zipImage(imageUri);
                }

                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    zipImage(uri);
                }
                break;
            case REQUEST_CODE_REGION_CODE:
                if (data != null) {
                    Bundle addressDate = data.getExtras();
                    region_name = addressDate.getString(RegionFragment.KEY_REGION_LIST);
                    region_code = addressDate.getString(RegionFragment.KEY_REGION_CODE);

                    city = getTwoForStringSplit(region_name);
                    textView_delivery_address.setText(region_name);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private File createOutputFile(String prefix) throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(prefix, ".jpg", storageDir);
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

    private void uploadAvatar(String imagePath) {
        CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HttpWhat
                .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        binary.setUploadListener(UPLOAD_IMAGE, mUploadListener);
        request.add("load_img", binary);
        addRequest(request);
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.linearlayout_detailed_address:
                return !canScroll;
        }
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (!Utils.isNull(poiResult)) {
            ArrayList<PoiItem> pois = poiResult.getPois();
            mSelectAddressAdapter.data.clear();
            for (int j = 0; j < pois.size(); j++) {
                PoiItem item = pois.get(j);
                PoiListModel poiListModel = new PoiListModel();
                poiListModel.title = item.getTitle();
                poiListModel.content = item.getSnippet();
                poiListModel.latLonPoint = item.getLatLonPoint();
                poiListModel.regionCode = formatCodeString(item.getAdCode());
                mSelectAddressAdapter.data.add(poiListModel);
            }

            mSelectAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    private String formatCodeString(String input) {
        String regex = "(.{2})";
        input = input.replaceAll(regex, "$1,");
        input.substring(0, input.length() - 1);
        return input.substring(0, input.length() - 1);
    }

    private String getTwoForStringSplit(String t_regionName) {
        String[] sss = t_regionName.split(" ");
        if (sss != null && sss.length >= 2) {
            return sss[1];
        }

        return t_regionName;
    }
}
