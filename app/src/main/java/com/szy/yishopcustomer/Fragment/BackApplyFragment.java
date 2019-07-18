package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.lyzb.jbx.R;
import com.szy.common.Activity.RegionActivity;
import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Fragment.RegionFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.BackApplyAdapter;
import com.szy.yishopcustomer.Adapter.ReviewShareOrderImgAdater;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyContentModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyFreightModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyFreightPriceModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyImageModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyNumberModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyReasonModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyRefundModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyServiceModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyWayModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.Model;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.Region.ResponseRegionModel;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.json.GSONUtil;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * 退款申请
 * Created by liwei on 17/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BackApplyFragment extends YSCBaseFragment implements EditText
        .OnEditorActionListener, View.OnFocusChangeListener, TextWatcherAdapter
        .TextWatcherListener {
    private static final int UPLOAD_IMAGE = 1;
    final ArrayList<String> back_service_array = new ArrayList<String>();
    @BindView(R.id.back_apply_recyclerView)
    CommonRecyclerView mBackApplyRecyclerView;
    @BindView(R.id.bottom_button)
    TextView mSubmitButton;
    String backId;
    String orderId;
    String goodsId;
    String skuId;
    String taketype;
    String takepack;
    String address_detail;
    String address_region;
    String recordId;
    int backType = 2;
    String backNumber;
    String backReason = "";
    String refundMoney;
    String refundAmountLimit;
    int refundType = 0;//默认选中返回原支付方式；
    String backDesc = "";
    private GridLayoutManager mLayoutManager;
    private BackApplyAdapter mBackApplyAdapter;
    private Model mModel;
    private File tempFile;
    private LinkedHashMap<String, String> back_reason_array;
    private LinkedHashMap<String, String> back_type_array_link;
    private List<String> back_type_array = new ArrayList<>();
    private BackApplyServiceModel serviceModel;
    private BackApplyReasonModel reasonModel;
    private BackApplyWayModel wayModel;

    private ArrayList<String> dataValue;
    private ArrayList<String> dataKey;
    private BackApplyNumberModel numberModel;
    private BackApplyRefundModel refundModel;
    private BackApplyContentModel contentModel;
    private DividerModel dividerModel;
    private BackApplyImageModel backApplyImageModel;
    private BackApplyFreightModel freightModel = new BackApplyFreightModel();
    private List<String> freightStr = Arrays.asList("自己承担运费", "商家承担运费");
    private List<String> freightStrJd = Arrays.asList("自己承担运费");
    private BackApplyFreightPriceModel priceModel = new BackApplyFreightPriceModel();

    private ArrayList<String> mImageList = new ArrayList<>();
    private String back_img1 = "";
    private String back_img2 = "";
    private String back_img3 = "";

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

    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1)) return null;
        float hh = 1920f;
        float ww = 1080f;
        int be = 1;
        if (originalWidth > originalHeight && originalWidth > ww) {
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {
            be = (int) (originalHeight / hh);
        }
        if (be <= 0) be = 1;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    @Override
    public void onEmptyViewClicked() {
        getActivity().finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_back_apply;

        Intent intent = getActivity().getIntent();
        backId = intent.getStringExtra(Key.KEY_BACK_ID.getValue());

        orderId = intent.getStringExtra(Key.KEY_ORDER_ID.getValue());
        goodsId = intent.getStringExtra(Key.KEY_GOODS_ID.getValue());
        skuId = intent.getStringExtra(Key.KEY_SKU_ID.getValue());
        recordId = intent.getStringExtra(Key.KEY_RECORD_ID.getValue());

        createCameraTempFile(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mBackApplyAdapter = new BackApplyAdapter();
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mBackApplyRecyclerView.setLayoutManager(mLayoutManager);
        mBackApplyRecyclerView.setAdapter(mBackApplyAdapter);
        mBackApplyAdapter.onClickListener = this;
        ReviewShareOrderImgAdater.onClickListener = this;
        mBackApplyRecyclerView.setEmptyViewClickListener(this);
        mBackApplyAdapter.textWatcherListener = this;

        Utils.setViewTypeForTag(mSubmitButton, ViewType.VIEW_TYPE_SUBMIT);
        mSubmitButton.setOnClickListener(this);
        mSubmitButton.setText(getResources().getString(R.string.submit));
        if (!Utils.isNull(backId)) {
            getActivity().setTitle("修改退款申请");
            editRefresh();
        } else {
            refresh();
        }
        showLocation();

        //点击包装和 退款方式
        mBackApplyAdapter.setOnclickListener(new BackApplyAdapter.OnclickListener() {
            @Override
            public void onlcick(int position, String type) {
                switch (type) {
                    case "type":
                        switch (position) {
                            case 1:
                                taketype = "4";
                                break;
                            case 2:
                                taketype = "40";
                                break;
                            case 3:
                                openRegionActivity(App.getInstance().city_code);
                                break;
                        }
                        break;
                    case "pack":
                        switch (position) {
                            case 1:
                                takepack = "0";
                                break;
                            case 2:
                                takepack = "10";
                                break;
                            case 3:
                                takepack = "20";
                                break;
                        }
                        break;
                }
            }
        });
        return view;
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SUBMIT:
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                break;
            case HTTP_UPLOAD_IMAGE:
                Toast.makeText(getActivity(), "image upload failed", Toast.LENGTH_SHORT).show();
                break;


            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_BACK_APPLY:
                refreshCallback(response);
                break;
            case HTTP_UPLOAD_IMAGE:
                HttpResultManager.resolve(response, UploadModel.class, new HttpResultManager.HttpResultCallBack<UploadModel>() {
                    @Override
                    public void onSuccess(UploadModel modelUpload) {
                        mImageList.add(modelUpload.data.url);
                        backApplyImageModel.imageList = mImageList;
                        mBackApplyAdapter.notifyDataSetChanged();
                        Utils.toastUtil.showToast(getActivity(), modelUpload.message);
                    }
                });
                break;
            case HTTP_BACK_EDIT:
                LogUtils.Companion.e("京东商品退款数据" + response);
                refreshCallback(response);
                break;
            case HTTP_SUBMIT:
                submitCallBack(response);
                break;
            case HTTP_GET_REGION_NAME:
                HttpResultManager.resolve(response, ResponseRegionModel.class, new HttpResultManager
                        .HttpResultCallBack<ResponseRegionModel>() {
                    @Override
                    public void onSuccess(ResponseRegionModel back) {
                        mBackApplyAdapter.refresh(back.data.region_name);
                        mBackApplyAdapter.notifyDataSetChanged();
                    }
                }, true);

                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    /**
     * 定位地址
     */
    private void showLocation() {
        final CommonRequest request = new CommonRequest(Api.API_GET_REGION_NAME, HttpWhat
                .HTTP_GET_REGION_NAME.getValue());
        Location.locationCallback(new Location.OnLocationListener.DefaultLocationListener() {
            @Override
            public void onFinished(AMapLocation amapLocation) {
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
        });
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_BACK_SERVICE:
                openBackServiceDialog(backType);
                break;
            case VIEW_TYPE_BACK_REASON:
                openBackReasonDialog();
                break;
            case VIEW_TYPE_BACK_WAY:
                openBackWayDialog(refundType);
                break;
            case VIEW_TYPE_SUBMIT:
                submit();
                break;
            case VIEW_TYPE_UPLOAD:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder
                        (getActivity());
                final String[] arr = new String[]{"拍照", "本地选择"};
                builder.setItems(arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
//                            PackageManager pm = getActivity().getPackageManager();
//                            boolean flag = (PackageManager.PERMISSION_GRANTED == pm
//                                    .checkPermission("android.permission.CAMERA", App.packageName));
                            if (cameraIsCanUse()) {
                                takePhoto();
                            } else {
                                Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
                            }
//                            if (flag) {
//                                takePhoto();
//                            } else {
//                                Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
//                            }
                        } else {
                            pickImage();
                        }
                    }

                });
                builder.create().show();
                break;
            case VIEW_TYPE_SHARE_ORDER_IMG:
                mImageList.remove(position);
                backApplyImageModel.imageList = mImageList;
                mBackApplyAdapter.notifyDataSetChanged();
                break;
            //运费方式选择
            case VIEW_TYPE_APPLY_FREIGHT:
                openFreightDialog();
                break;
            default:
                super.onClick(view);
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, RequestCode.REQUEST_CAPTURE.getValue());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RequestCode.REQUEST_PICK
                .getValue());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    Uri uri = Uri.fromFile(tempFile);
                    zipImage(uri);
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
                    String regionName = addressDate.getString(RegionFragment.KEY_REGION_LIST);
                    String regionCode = addressDate.getString(RegionFragment.KEY_REGION_CODE);

                    address_region = regionName;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
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

    private void zipImage(Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = getBitmapFormUri(getActivity(), uri);
            OutputStream fOut;
            File file = createOutputFile("compressed");
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
            fOut.flush();
            fOut.close();
            uploadAvatar(file.getPath());

            /*ByteArrayOutputStream out = null;
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();

            CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HttpWhat
                    .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
            request.add("img_base64",Base64.encodeToString(imgBytes, Base64.DEFAULT));
            addRequest(request);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadAvatar(String imagePath) {
        CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HttpWhat
                .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        request.add("load_img", binary);
        addRequest(request);
    }

    private File createOutputFile(String prefix) throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(prefix, ".jpg", storageDir);
    }

    public void refresh() {
        CommonRequest commonRequest = new CommonRequest(Api.API_BACK_APPLY, HttpWhat.HTTP_BACK_APPLY.getValue());
        commonRequest.add("id", orderId);
        commonRequest.add("gid", goodsId);
        commonRequest.add("sid", skuId);
        commonRequest.add("record_id", recordId);

        addRequest(commonRequest);
    }

    private void editRefresh() {
        CommonRequest commonRequest = new CommonRequest(Api.API_BACK_EDIT, HttpWhat.HTTP_BACK_EDIT.getValue());
        commonRequest.add("id", backId);
        addRequest(commonRequest);
    }

    private void refreshCallback(final String response) {
        final String response2 = response;


        try {
            Model model = GSONUtil.getEntity(response, Model.class);

            mModel = model;
            if (null != model && model.code != 0) {//请求失败的时候
                Toast.makeText(getActivity(), model.message, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            back_reason_array = mModel.data.back_reason_array;

            back_type_array_link = mModel.data.back_type_array;
            for (TreeMap.Entry<String, String> entry : back_type_array_link.entrySet()) {
                if (!entry.getKey().equals("")) {
                    back_type_array.add(entry.getValue());
                }
            }

            backNumber = mModel.data.goods_info.goods_number;

            refundMoney = mModel.data.goods_info.all_number_money_new;
            refundAmountLimit = mModel.data.goods_info.all_number_money;

            if (!Utils.isNull(backId)) {

                BackModel backModel = mModel.data.model;
                orderId = backModel.order_id;
                skuId = backModel.sku_id;
                goodsId = backModel.goods_id;

                backType = backModel.back_type;
                backNumber = backModel.back_number;
                backReason = backModel.back_reason;
                refundMoney = backModel.refund_money;
                refundType = backModel.refund_type;
                backDesc = backModel.back_desc;
                back_img1 = backModel.back_img1;
                back_img2 = backModel.back_img2;
                back_img3 = backModel.back_img3;

                if (mModel.data.model == null) {
                    Log.d("wyx", "model为空");
                } else {
                    Log.d("wyx", "model-data");
                }
            }

            dividerModel = new DividerModel();
            mBackApplyAdapter.data.add(dividerModel);


            if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)<=0){//不是京东商品  京东的 没得这模块
                //退款类型 1
                back_service_array.add("退款退货");
                back_service_array.add("仅退款");
                serviceModel = new BackApplyServiceModel();
                if (backType == 2) {
                    serviceModel.content = back_service_array.get(0).toString();
                } else {
                    serviceModel.content = back_service_array.get(1).toString();
                }
                serviceModel.viewType = ViewType.VIEW_TYPE_BACK_SERVICE;
                mBackApplyAdapter.data.add(serviceModel);
            }

            mBackApplyAdapter.data.add(dividerModel);

            //退款数量 2
            numberModel = new BackApplyNumberModel();
            numberModel.content = backNumber;
            numberModel.viewType = ViewType.VIEW_TYPE_BACK_NUMBER;
            mBackApplyAdapter.data.add(numberModel);

            mBackApplyAdapter.data.add(dividerModel);

            //退款原因 3
            dataValue = new ArrayList<String>();
            dataKey = new ArrayList<String>();
            reasonModel = new BackApplyReasonModel();

            for (TreeMap.Entry<String, String> entry : back_reason_array.entrySet()) {
                if (!entry.getKey().equals("")) {
                    dataKey.add(entry.getKey());
                    dataValue.add(entry.getValue());
                }
            }

            if (!Utils.isNull(backReason)) {
                for (int i = 0; i < dataKey.size(); i++) {
                    if (dataKey.get(i).equals(backReason)) {
                        reasonModel.content = dataValue.get(i).toString();
                    }
                }
            } else {
                reasonModel.content = "请选择退款原因";
            }
            mBackApplyAdapter.data.add(reasonModel);

            //运费金额
            priceModel.maxPrice = Float.parseFloat(mModel.data.max_refund_freight);//默认不要添加
            priceModel.content = TextUtils.isEmpty(model.data.model.refund_freight) ? mModel.data.max_refund_freight
                    : model.data.model.refund_freight;

            //运费方式
            if (priceModel.maxPrice > 0) {
                int freightType = 0;
                if (!TextUtils.isEmpty(model.data.model.refund_freight_type)) {
                    freightType = Integer.parseInt(model.data.model.refund_freight_type);
                }

                freightModel.content = freightStr.get(freightType);
                freightModel.contentType = freightType;
                mBackApplyAdapter.data.add(freightModel);
                if (freightType == 1) {
                    if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0) {//京东不添加运费模块
                        mBackApplyAdapter.data.remove(priceModel);
                    } else {
                        mBackApplyAdapter.data.add(priceModel);
                    }
                }
            }


            //退款金额
            refundModel = new BackApplyRefundModel();
            refundModel.content = refundMoney;
            refundModel.order_amount = refundAmountLimit;

            refundModel.viewType = ViewType.VIEW_TYPE_REFUND;
            mBackApplyAdapter.data.add(refundModel);

            mBackApplyAdapter.data.add(dividerModel);
            //退款方式 默认未选择退款方式
            wayModel = new BackApplyWayModel();


            if (mModel.data.model.refund_type < back_type_array.size()) {
                wayModel.content = back_type_array.get(mModel.data.model.refund_type).toString();
            } else {
                wayModel.content = back_type_array.get(0).toString();
            }
            wayModel.content = "请选择退款方式";

            mBackApplyAdapter.data.add(wayModel);

            mBackApplyAdapter.data.add(dividerModel);

            //选择退款方式  (上门取件,客户发货)  包装   京东商品特有特有

            if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0) {//不为空说明是京东商品
                if (null != mModel.data.jd_take_type_array) {
                    mBackApplyAdapter.data.add(mModel.data.jd_take_type_array);
                }
                if (null != mModel.data.jd_take_pack_array) {
                    mBackApplyAdapter.data.add(mModel.data.jd_take_pack_array);
                }

                mBackApplyAdapter.data.add(dividerModel);
            }


            //退款说明
            contentModel = new BackApplyContentModel();
            contentModel.content = backDesc;
            contentModel.viewType = ViewType.VIEW_TYPE_BACK_DESC;
            mBackApplyAdapter.data.add(contentModel);
            mBackApplyAdapter.data.add(dividerModel);
            //退款图片
            backApplyImageModel = new BackApplyImageModel();
            if (!Utils.isNull(back_img1)) {
                mImageList.add(Utils.urlOfImage(back_img1));
            }
            if (!Utils.isNull(back_img2)) {
                mImageList.add(Utils.urlOfImage(back_img2));
            }
            if (!Utils.isNull(back_img3)) {
                mImageList.add(Utils.urlOfImage(back_img3));
            }
            backApplyImageModel.imageList = mImageList;
            mBackApplyAdapter.data.add(backApplyImageModel);
            mBackApplyAdapter.data.add(dividerModel);

            mBackApplyAdapter.notifyDataSetChanged();


        } catch (Exception ex) {
            Log.d("wyx", "json异常-");
            ex.printStackTrace();
        }


//        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
//            @Override
//            public void onSuccess(Model back) {
//                mSubmitButton.setVisibility(View.VISIBLE);
//                mBackApplyRecyclerView.hideEmptyView();
//
//                mModel = back;
//                back_reason_array = mModel.data.back_reason_array;
//
//                back_type_array_link = mModel.data.back_type_array;
//                for (TreeMap.Entry<String, String> entry : back_type_array_link.entrySet()) {
//                    if (!entry.getKey().equals("")) {
//                        back_type_array.add(entry.getValue());
//                    }
//                }
//
//                backNumber = mModel.data.goods_info.goods_number;
//
//                refundMoney = mModel.data.goods_info.all_number_money_new;
//                refundAmountLimit = mModel.data.goods_info.all_number_money;
//
//                if (!Utils.isNull(backId)) {
//                    BackModel backModel = mModel.data.model;
//                    orderId = backModel.order_id;
//                    skuId = backModel.sku_id;
//                    goodsId = backModel.goods_id;
//
//                    backType = backModel.back_type;
//                    backNumber = backModel.back_number;
//                    backReason = backModel.back_reason;
//                    refundMoney = backModel.refund_money;
//                    refundType = backModel.refund_type;
//                    backDesc = backModel.back_desc;
//                    back_img1 = backModel.back_img1;
//                    back_img2 = backModel.back_img2;
//                    back_img3 = backModel.back_img3;
//                }
//
//                dividerModel = new DividerModel();
//                mBackApplyAdapter.data.add(dividerModel);
//
//                //退款类型
//                back_service_array.add("退款退货");
//                back_service_array.add("仅退款");
//                serviceModel = new BackApplyServiceModel();
//                if (backType == 2) {
//                    serviceModel.content = back_service_array.get(0).toString();
//                } else {
//                    serviceModel.content = back_service_array.get(1).toString();
//                }
//                serviceModel.viewType = ViewType.VIEW_TYPE_BACK_SERVICE;
//                mBackApplyAdapter.data.add(serviceModel);
//
//                mBackApplyAdapter.data.add(dividerModel);
//
//                //退款数量
//                numberModel = new BackApplyNumberModel();
//                numberModel.content = backNumber;
//                numberModel.viewType = ViewType.VIEW_TYPE_BACK_NUMBER;
//                mBackApplyAdapter.data.add(numberModel);
//
//                mBackApplyAdapter.data.add(dividerModel);
//
//                //退款原因
//                dataValue = new ArrayList<String>();
//                dataKey = new ArrayList<String>();
//                reasonModel = new BackApplyReasonModel();
//
//                for (TreeMap.Entry<String, String> entry : back_reason_array.entrySet()) {
//                    if (!entry.getKey().equals("")) {
//                        dataKey.add(entry.getKey());
//                        dataValue.add(entry.getValue());
//                    }
//                }
//
//                if (!Utils.isNull(backReason)) {
//                    for (int i = 0; i < dataKey.size(); i++) {
//                        if (dataKey.get(i).equals(backReason)) {
//                            reasonModel.content = dataValue.get(i).toString();
//                        }
//                    }
//                } else {
//                    reasonModel.content = "请选择退款原因";
//                }
//                mBackApplyAdapter.data.add(reasonModel);
//
//                //运费金额
//                priceModel.maxPrice = Float.parseFloat(mModel.data.max_refund_freight);//默认不要添加
//                priceModel.content = TextUtils.isEmpty(back.data.model.refund_freight) ? mModel.data.max_refund_freight
//                        : back.data.model.refund_freight;
//
////                priceModel.maxPrice = Double.parseDouble(mModel.data.order_info.getShipping_fee());//默认不要添加
////                priceModel.content = back.data.model.refund_freight;
//                //运费方式
//                if (priceModel.maxPrice > 0) {
//                    int freightType = 0;
//                    if (!TextUtils.isEmpty(back.data.model.refund_freight_type)) {
//                        freightType = Integer.parseInt(back.data.model.refund_freight_type);
//                    }
//
//                    freightModel.content = freightStr.get(freightType);
//                    freightModel.contentType = freightType;
//                    mBackApplyAdapter.data.add(freightModel);
//
//                    if (freightType == 1) {
//                        mBackApplyAdapter.data.add(priceModel);
//                    }
//                }
//
//                //退款金额
//                refundModel = new BackApplyRefundModel();
//                refundModel.content = refundMoney;
//                refundModel.order_amount = refundAmountLimit;
//
//                refundModel.viewType = ViewType.VIEW_TYPE_REFUND;
//                mBackApplyAdapter.data.add(refundModel);
//
//                mBackApplyAdapter.data.add(dividerModel);
//                //退款方式 默认未选择退款方式
//                wayModel = new BackApplyWayModel();
//
//
//                if (mModel.data.model.refund_type < back_type_array.size()) {
//                    wayModel.content = back_type_array.get(mModel.data.model.refund_type).toString();
//                } else {
//                    wayModel.content = back_type_array.get(0).toString();
//                }
//                mBackApplyAdapter.data.add(wayModel);
//
//                mBackApplyAdapter.data.add(dividerModel);
//                //退款说明
//                contentModel = new BackApplyContentModel();
//                contentModel.content = backDesc;
//                contentModel.viewType = ViewType.VIEW_TYPE_BACK_DESC;
//                mBackApplyAdapter.data.add(contentModel);
//                mBackApplyAdapter.data.add(dividerModel);
//                //退款图片
//                backApplyImageModel = new BackApplyImageModel();
//                if (!Utils.isNull(back_img1)) {
//                    mImageList.add(Utils.urlOfImage(back_img1));
//                }
//                if (!Utils.isNull(back_img2)) {
//                    mImageList.add(Utils.urlOfImage(back_img2));
//                }
//                if (!Utils.isNull(back_img3)) {
//                    mImageList.add(Utils.urlOfImage(back_img3));
//                }
//                backApplyImageModel.imageList = mImageList;
//                mBackApplyAdapter.data.add(backApplyImageModel);
//                mBackApplyAdapter.data.add(dividerModel);
//
//                mBackApplyAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(String message) {
//                ResponseCommonModel model = JSON.parseObject(response2, ResponseCommonModel.class);
//                mSubmitButton.setVisibility(View.GONE);
//                mBackApplyRecyclerView.setEmptyTitle(R.string.alertBackOrder);
//                mBackApplyRecyclerView.showEmptyView();
//                /*Toast.makeText(getActivity(),model.message,Toast.LENGTH_SHORT).show();
//                getActivity().finish();*/
//            }
//        });

    }

    private void openBackServiceDialog(int defaultItem) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id
                .item_cancel_order_textView, back_service_array);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id
                .order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        TextView title = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_cancel_order_textView);
        title.setText("请选择服务类型");

        if (defaultItem == 2) {
            listView.setItemChecked(0, true);
        } else {
            listView.setItemChecked(1, true);
        }

        final int[] tempArray = {1, 2};
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tempArray[0] = position;
            }
        });


        final AlertDialog mDialogDialog = new AlertDialog.Builder(getActivity()).setView
                (cancelOrderDialogView).create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_cancle_textView);
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_confirm_textView);
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempArray[0] == 0) {
                    backType = 2;
                } else {
                    backType = 1;
                }

                if (!serviceModel.content.equals(back_service_array.get(tempArray[0]).toString())) {
                    if (backType == 1) {
                        mBackApplyAdapter.data.remove(numberModel);
                        mBackApplyAdapter.data.remove(3);
                        mBackApplyAdapter.notifyItemRangeRemoved(3, 2);
                    } else {
                        mBackApplyAdapter.data.add(3, numberModel);
                        mBackApplyAdapter.data.add(4, dividerModel);
                        mBackApplyAdapter.notifyItemRangeInserted(3, 2);
                    }
                }
                serviceModel.content = back_service_array.get(tempArray[0]).toString();
                mDialogDialog.dismiss();
                mBackApplyAdapter.notifyDataSetChanged();
            }
        });
        mDialogDialog.show();
    }

    private void openBackReasonDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        int defaultItem = 0;

        if (!Utils.isNull(backReason)) {
            for (int i = 0; i < dataKey.size(); i++) {
                if (backReason == dataKey.get(i)) {
                    defaultItem = i;
                }
            }
        }

        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id.item_cancel_order_textView, dataValue);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id.order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(defaultItem, true);

        TextView title = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_cancel_order_textView);
        title.setText("请选择退款原因");

        final String[] tempArray = {dataKey.get(0)};
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < dataKey.size(); i++) {
                    if (position == i) {
                        tempArray[0] = dataKey.get(i);
                    }
                }
            }
        });

        final AlertDialog mDialogDialog = new AlertDialog.Builder(getActivity()).setView
                (cancelOrderDialogView).create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_cancle_textView);
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_confirm_textView);
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonModel.content = dataValue.get(Integer.valueOf(tempArray[0])).toString();
                backReason = tempArray[0];
                mDialogDialog.dismiss();
                mBackApplyAdapter.notifyDataSetChanged();
            }
        });
        mDialogDialog.show();
    }

    /**
     * 选择运费方式
     */
    private void openFreightDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        int defaultItem = freightStr.get(0).equals(freightModel.content) ? 0 : 1;
        ArrayAdapter adapter = null;
        LogUtils.Companion.e("-------->>>"+mModel.data.goods_info.jd_sku_id);
        if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0) {//京东(只能自己承担运费)
            adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id.item_cancel_order_textView, freightStrJd);

        } else {
            adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id.item_cancel_order_textView, freightStr);

        }
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id.order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(defaultItem, true);

        TextView title = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_cancel_order_textView);
        title.setText("请选择退款运费方式");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //赋值
                freightModel.contentType = position;
            }
        });

        final AlertDialog mDialogDialog = new AlertDialog.Builder(getActivity()).setView
                (cancelOrderDialogView).create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_cancle_textView);
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_confirm_textView);
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freightModel.content = freightStr.get(freightModel.contentType);
                if (freightModel.content.equals(freightStr.get(0))) {
                    mBackApplyAdapter.data.remove(priceModel);
                } else {
                    if (!mBackApplyAdapter.data.contains(priceModel)) {
                        for (int i = 0; i < mBackApplyAdapter.data.size(); i++) {
                            if (mBackApplyAdapter.data.get(i) instanceof BackApplyFreightModel) {
                                mBackApplyAdapter.data.add(i + 1, priceModel);
                            }
                        }
                    }
                }
                mDialogDialog.dismiss();
                mBackApplyAdapter.notifyDataSetChanged();
            }
        });
        mDialogDialog.show();
    }

    private void openBackWayDialog(int defaultItem) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);

        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order,
                R.id.item_cancel_order_textView, back_type_array);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id
                .order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(defaultItem, true);

        TextView title = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_cancel_order_textView);
        title.setText("请选择退款方式");

        final int[] tempArray = {0};
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tempArray[0] = position;
            }
        });

        final AlertDialog mDialogDialog = new AlertDialog.Builder(getActivity()).setView(cancelOrderDialogView).create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id.dialog_call_cancle_textView);
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id.dialog_call_confirm_textView);
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wayModel.content = back_type_array.get(tempArray[0]).toString();
                refundType = tempArray[0];
                mDialogDialog.dismiss();
                mBackApplyAdapter.notifyDataSetChanged();
            }
        });
        mDialogDialog.show();
    }

    /**
     * 提交申请
     */
    private void submit() {
        if (!check()) {
            return;
        }
        if (mImageList.size() == 0) {
            back_img1 = "";
            back_img2 = "";
            back_img3 = "";
        }
        if (mImageList.size() == 1) {
            back_img1 = mImageList.get(0);
            back_img2 = "";
            back_img3 = "";
        }
        if (mImageList.size() == 2) {
            back_img1 = mImageList.get(0);
            back_img2 = mImageList.get(1);
            back_img3 = "";
        }
        if (mImageList.size() == 3) {
            back_img1 = mImageList.get(0);
            back_img2 = mImageList.get(1);
            back_img3 = mImageList.get(2);
        }
        String uri;
        if (!Utils.isNull(backId)) {
            uri = Api.API_BACK_EDIT;
            orderId = backId;
        } else {
            uri = Api.API_BACK_APPLY;
        }
        CommonRequest backApply = new CommonRequest(uri, HttpWhat.HTTP_SUBMIT.getValue(), RequestMethod.POST);
        backApply.add("id", orderId);
        backApply.add("gid", goodsId);
        backApply.add("sid", skuId);
        backApply.add("record_id", recordId);
        backApply.add("BackOrder[refund_freight]", priceModel.content);
        backApply.add("BackOrder[refund_freight_type]", freightModel.contentType);
        backApply.add("BackOrder[back_type]", backType);
        backApply.add("BackOrder[back_number]", backNumber);
        backApply.add("BackOrder[back_reason]", backReason);
        backApply.add("BackOrder[refund_money]", refundMoney);
        backApply.add("BackOrder[refund_type]", refundType);
        backApply.add("BackOrder[back_desc]", backDesc);
        backApply.add("BackOrder[back_img1]", back_img1);
        backApply.add("BackOrder[back_img2]", back_img2);
        backApply.add("BackOrder[back_img3]", back_img3);

        if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0) {//京东商品要多传些参数
            if (TextUtils.isEmpty(address_region)) {//为空说明没定位出来需要选择
                address_region = mBackApplyAdapter.addressRegion;
            }
            backApply.add("jd_take_type", taketype);
            backApply.add("jd_take_pack", takepack);
            backApply.add("jd_take_region_code", App.getInstance().city_code);
            backApply.add("jd_take_address_detail", address_region + mBackApplyAdapter.addressDetail);
        }
        addRequest(backApply);
    }

    private boolean check() {
        String message = "";
        if (Utils.isNull(backNumber)) {
            message = "退款数量不能为空";
        } else if (Integer.valueOf(backNumber) > Integer.valueOf(mModel.data.goods_info
                .goods_number)) {
            message = "退款数量的值必须小于或等于" + mModel.data.goods_info.goods_number;
        } else if (Utils.isNull(backReason)) {
            message = "退款原因不能为空";
        } else if (Utils.isNull(refundMoney)) {
            message = "退款金额不能为空";
        } else if (Double.valueOf(refundMoney) > Double.valueOf(mModel.data.goods_info
                .all_number_money)) {
            message = "本次退款金额最多为" + mModel.data.goods_info.all_number_money;
        } else if (Utils.isNull(wayModel.content) || wayModel.content.equals("请选择退款方式")) {
            message = "请选择退款方式";
        } else if (Utils.isNull(taketype)) {
            if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0) {//京东商品要多传些参数
                message = "请选择取件方式";
            }
        } else if (Utils.isNull(takepack)) {
            if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0) {//京东商品要多传些参数
                message = "请选择包装";
            }
        } else if (TextUtils.isEmpty(mBackApplyAdapter.addressDetail)) {
            if (Integer.valueOf(mModel.data.goods_info.jd_sku_id)>0 && taketype.equals("4")) {//京东商品要多传些参数
                message = "请填写详情地址";
            }
        }

        if (Utils.isNull(message)) {
            return true;
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void submitCallBack(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager.HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                Toast.makeText(getContext(), back.message, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_BACK_DETAIL.getValue()));
                getActivity().finish();
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_BACK_DETAIL.getValue()));
                getActivity().finish();
            }
        }, true);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            ViewType viewType = Utils.getViewTypeOfTag(view);
            switch (viewType) {
                case VIEW_TYPE_BACK_NUMBER:
                    backNumber = ((EditText) view).getText().toString();
                    numberModel.content = backNumber;
                    break;
                case VIEW_TYPE_REFUND:
                    refundMoney = ((EditText) view).getText().toString();
                    refundModel.content = refundMoney;
                    break;
                case VIEW_TYPE_BACK_DESC:
                    backDesc = ((EditText) view).getText().toString();
                    contentModel.content = backDesc;
                    break;
                //运费价格
                default:
                    priceModel.content = ((EditText) view).getText().toString();
                    break;
            }
        }
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_BACK_NUMBER:
                backNumber = view.getText().toString();
                numberModel.content = backNumber;
                break;
            case VIEW_TYPE_REFUND:
                refundMoney = view.getText().toString();
                refundModel.content = refundMoney;
                break;
            case VIEW_TYPE_BACK_DESC:
                backDesc = view.getText().toString();
                contentModel.content = backDesc;
                break;
            //运费价格
            default:
                priceModel.content = ((EditText) view).getText().toString();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Utils.hideKeyboard(view);
            ViewType viewType = Utils.getViewTypeOfTag(view);
            switch (viewType) {
                case VIEW_TYPE_BACK_NUMBER:
                    backNumber = view.getText().toString();
                    numberModel.content = backNumber;
                    break;
                case VIEW_TYPE_REFUND:
                    refundMoney = view.getText().toString();
                    refundModel.content = refundMoney;
                    break;
                case VIEW_TYPE_BACK_DESC:
                    backDesc = view.getText().toString();
                    contentModel.content = backDesc;
                    break;
                //运费价格
                default:
                    priceModel.content = ((EditText) view).getText().toString();
                    break;
            }
            return true;
        }
        return false;
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


}
