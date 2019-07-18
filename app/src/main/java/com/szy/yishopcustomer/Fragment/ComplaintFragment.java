package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.BackActivity;
import com.szy.yishopcustomer.Adapter.ReviewShareOrderImgAdater;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.ComplaintModel;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
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
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by liwei on 2017/11/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ComplaintFragment extends YSCBaseFragment implements OnEmptyViewClickListener {

    private static final int HTTP_USER_COMPLAINT = 0;
    private static final int HTTP_USER_COMPLAINT_ADD = 1;

    @BindView(R.id.fragment_complaint_shop_name)
    TextView shopName;
    @BindView(R.id.fragment_complaint_order_sn)
    TextView orderSn;
    @BindView(R.id.fragment_complaint_shipping_fee)
    TextView shippingFee;
    @BindView(R.id.fragment_complaint_total)
    TextView totalFee;
    @BindView(R.id.fragment_complaint_add_time)
    TextView addTime;

    @BindView(R.id.fragment_complaint_reason)
    TextView complaintReason;
    @BindView(R.id.fragment_complaint_desc)
    CommonEditText complaintDesc;
    @BindView(R.id.fragment_complaint_phone)
    CommonEditText complaintPhone;
    @BindView(R.id.fragment_complaint_imageLayout)
    RelativeLayout complaintImage;

    @BindView(R.id.bottom_button)
    TextView confirmButton;

    @BindView(R.id.fragment_complaint_imageRecyclerView)
    CommonRecyclerView imageRecyclerView;

    private String orderId;
    private String skuId;
    private String complaintId;

    private List<String> complaintReasons;
    private int selectedItem;

    private File tempFile;
    private ArrayList<String> mImageList = new ArrayList<>();
    private String imageLink = "";
    private ReviewShareOrderImgAdater reviewShareOrderImgAdater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_complaint;

        Intent intent = getActivity().getIntent();
        orderId = intent.getStringExtra(Key.KEY_ORDER_ID.getValue());
        skuId = intent.getStringExtra(Key.KEY_SKU_ID.getValue());
        complaintId = intent.getStringExtra(Key.KEY_COMPLAINT_ID.getValue());

        createCameraTempFile(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        complaintReason.setOnClickListener(this);
        complaintImage.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        reviewShareOrderImgAdater.onClickListener = this;

        //图片列表
        reviewShareOrderImgAdater = new ReviewShareOrderImgAdater(mImageList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        imageRecyclerView.setLayoutManager(mLayoutManager);
        imageRecyclerView.setAdapter(reviewShareOrderImgAdater);
        confirmButton.setText("提交投诉申请");
        refresh();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_complaint_reason:
                final ArrayList<String> data = new ArrayList<String>();
                for (int i = 0; i < complaintReasons.size(); i++) {
                    data.add(complaintReasons.get(i));
                }

                openComplaintReason("请选择服务类型", data, selectedItem);
                break;
            case R.id.fragment_complaint_imageLayout:
                AlertDialog.Builder builder = new AlertDialog.Builder
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
            case R.id.bottom_button:
                if(Utils.isNull(complaintDesc.getText().toString())){
                    Toast.makeText(getActivity(),"投诉说明不能为空",Toast.LENGTH_SHORT).show();
                }else if(Utils.isNull(imageLink)){
                    Toast.makeText(getActivity(),"上传投诉凭证图片不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    submit();
                }

                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_SHARE_ORDER_IMG:
                        //Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
                        mImageList.remove(position);

                        if(mImageList.size()<6){
                            complaintImage.setVisibility(View.VISIBLE);
                        }else{
                            complaintImage.setVisibility(View.GONE);
                        }

                        String s = mImageList.toString();
                        imageLink = s.substring(1, s.length() - 1).replaceAll(" ","");;
                        reviewShareOrderImgAdater.notifyDataSetChanged();
                        break;
                    default:
                        super.onClick(v);
                }

        }
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (what) {
            case HTTP_USER_COMPLAINT:
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_USER_COMPLAINT:
                refreshCallback(response);
                break;
            case HTTP_USER_COMPLAINT_ADD:
                submitCallback(response);
                break;
            default:
                switch (HttpWhat.valueOf(what)) {
                    case HTTP_UPLOAD_IMAGE:
                        HttpResultManager.resolve(response, UploadModel.class, new
                                HttpResultManager.HttpResultCallBack<UploadModel>() {
                            @Override
                            public void onSuccess(UploadModel modelUpload) {
                                mImageList.add(modelUpload.data.url);
                                reviewShareOrderImgAdater.notifyDataSetChanged();

                                if(mImageList.size()<6){
                                    complaintImage.setVisibility(View.VISIBLE);
                                }else{
                                    complaintImage.setVisibility(View.GONE);
                                }

                                String s = mImageList.toString();
                                imageLink = s.substring(1, s.length() - 1).replaceAll(" ","");
                            }
                        });
                        break;
                }
                super.onRequestSucceed(what, response);
        }
    }

    public void refresh() {
        if (Utils.isNull(complaintId)) {
            CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_ADD,
                    HTTP_USER_COMPLAINT);
            mRequest.add("order_id", orderId);
            mRequest.add("sku_id", skuId);
            addRequest(mRequest);
        } else {
            CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_EDIT,
                    HTTP_USER_COMPLAINT);
            mRequest.add("complaint_id", complaintId);
            addRequest(mRequest);
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, ComplaintModel.class, new HttpResultManager
                .HttpResultCallBack<ComplaintModel>() {
            @Override
            public void onSuccess(ComplaintModel back) {
                complaintReasons = back.data.complaint_item;
                ComplaintModel.DataBean.ModelBean model = back.data.model;

                if (!Utils.isNull(complaintId)) {
                    selectedItem = model.complaint_type;
                    imageLink = model.complaint_images;

                    complaintReason.setText(complaintReasons.get(model.complaint_type));
                    complaintDesc.setText(model.complaint_desc);
                    complaintPhone.setText(model.complaint_mobile);

                    String[] sArray = model.complaint_images.split(",");
                    for (int i = 0; i < sArray.length; i++) {
                        mImageList.add(Utils.urlOfImage(sArray[i]));
                    }

                    if(mImageList.size()<6){
                        complaintImage.setVisibility(View.VISIBLE);
                    }else{
                        complaintImage.setVisibility(View.GONE);
                    }

                    reviewShareOrderImgAdater.notifyDataSetChanged();

                } else {
                    complaintReason.setText(complaintReasons.get(0));
                }

                //订单信息
                ComplaintModel.DataBean.ComplaintOrderBean orderModel = back.data.complaint_order;
                shopName.setText(orderModel.shop_name);
                orderSn.setText(orderModel.order_sn);
                shippingFee.setText(orderModel.shipping_fee+"元");
                totalFee.setText(orderModel.order_amount+"元");
                addTime.setText(Utils.times(orderModel.pay_time));
            }
        });
    }

    //打开投诉原因
    private void openComplaintReason(String title, final ArrayList<String> data, int defaultItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout
                .dialog_list, null);
        builder.setView(layout);

        final AlertDialog alertDialog = builder.create();

        TextView titleTextView = (TextView) layout.findViewById(R.id.dialog_title);
        ListView listView = (ListView) layout.findViewById(R.id.dialog_listView);
        TextView cancelButton = (TextView) layout.findViewById(R.id.dialog_cancel);
        TextView confirmButton = (TextView) layout.findViewById(R.id.dialog_confirm);

        //列表
        final int[] selectedItemTemp = new int[1];
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout
                .dialog_list_item, R.id.dialog_list_item_textView, data);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(defaultItem, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemTemp[0] = position;
            }
        });

        //标题
        titleTextView.setText(title);

        //取消
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        //确定
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = selectedItemTemp[0];
                complaintReason.setText(complaintReasons.get(selectedItem));
                alertDialog.dismiss();
                //Toast.makeText(getActivity(),selectedItem+"",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);

        alertDialog.show();

    }


    private void submit() {
        if(Utils.isNull(complaintId)){
            CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_ADD + "?order_id=" +
                    orderId + "&sku_id=" + skuId, HTTP_USER_COMPLAINT_ADD, RequestMethod.POST);
            mRequest.add("ComplaintModel[complaint_type]", selectedItem);
            mRequest.add("ComplaintModel[complaint_desc]", complaintDesc.getText().toString());
            mRequest.add("ComplaintModel[complaint_mobile]", complaintPhone.getText().toString());
            mRequest.add("ComplaintModel[complaint_images]", imageLink);
            addRequest(mRequest);
        }else{
            CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_EDIT+"?complaint_id="+complaintId , HTTP_USER_COMPLAINT_ADD, RequestMethod.POST);
            mRequest.add("ComplaintModel[complaint_type]", selectedItem);
            mRequest.add("ComplaintModel[complaint_desc]", complaintDesc.getText().toString());
            mRequest.add("ComplaintModel[complaint_mobile]", complaintPhone.getText().toString());
            mRequest.add("ComplaintModel[complaint_images]", imageLink);
            addRequest(mRequest);
        }

    }


    private void submitCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel model) {
                Utils.toastUtil.showToast(getActivity(), model.message);
                //如果complaintId为空说明是从订单详情点进来，添加投诉
                if(Utils.isNull(complaintId)){
                    openBackActivity();
                }else{
                    //否则说明从投诉列表->投诉详情点进来修改投诉
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_COMPLAINT_REFRESH
                            .getValue()));
                }

                getActivity().finish();
            }
        },true);
    }

    private void openBackActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_TYPE.getValue(),"1");
        intent.setClass(getActivity(), BackActivity.class);
        startActivity(intent);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, RequestCode.REQUEST_CAPTURE.getValue());
    }

    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath()
                    + "/image/"), System.currentTimeMillis() + ".jpg");
        }
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
            bitmap = getBitmapFormUri(getActivity(), uri);
            OutputStream fOut;
            File file = createOutputFile("compressed");
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
            fOut.flush();
            fOut.close();
            uploadAvatar(file.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void uploadAvatar(String imagePath) {
        CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HttpWhat
                .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        request.add("load_img", binary);
        addRequest(request);
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RequestCode.REQUEST_PICK
                .getValue());
    }


}
