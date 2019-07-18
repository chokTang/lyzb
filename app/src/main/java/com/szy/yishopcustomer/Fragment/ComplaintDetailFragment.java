package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.BackActivity;
import com.szy.yishopcustomer.Activity.ComplaintActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Adapter.ComplaintDetailAdapter;
import com.szy.yishopcustomer.Adapter.GoodsCommentImageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.OrderDetailModel.ComplaintDetailModel;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailTitleModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailTitleTwoModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailValueImageModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailValueModel;
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

public class ComplaintDetailFragment extends YSCBaseFragment implements OnEmptyViewClickListener {

    private static final int HTTP_COMPLAINT_DETAIL = 0;
    private static final int HTTP_COMPLAINT_DEL = 1;
    private static final int HTTP_COMPLAINT_INTERVENTION = 2;
    private static final int HTTP_COMPLAINT_EDIT = 3;
    private String complaintId;

    @BindView(R.id.fragment_complaint_detail_RecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.bottom_button)
    TextView mConfirmButton;
    private ComplaintDetailAdapter adapter;

    private ArrayList<Object> list;
    private int type = -1;

    private ComplaintDetailModel model;
    private List<String> complaintItemModel;
    private ComplaintDetailModel.DataBean.ComplaintViewBean complaintViewModel;

    RecyclerView.ItemDecoration mItemDecorationOne;
    RecyclerView.ItemDecoration mItemDecorationTwo;
    private String imageLink;
    private File tempFile;
    private ArrayList<String> mImageList = new ArrayList<>();
    private BackDetailValueImageModel imageValueModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_complaint_detail;
        list = new ArrayList<Object>();
        Intent intent = getActivity().getIntent();
        complaintId = intent.getStringExtra(Key.KEY_COMPLAINT_ID.getValue());

        createCameraTempFile(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        adapter = new ComplaintDetailAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        final int topMargin = Utils.dpToPx(getContext(), 10);
        final int bottomMargin = Utils.dpToPx(getContext(), 10);

        mItemDecorationOne = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                final int itemPosition = parent.getChildAdapterPosition(view);
                if (itemPosition == 1) {
                    outRect.bottom = bottomMargin;
                    outRect.top = -topMargin;
                }

                if (itemPosition == 2 || itemPosition == 11) {
                    outRect.bottom = bottomMargin;
                }

            }
        };


        mItemDecorationTwo = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                final int itemPosition = parent.getChildAdapterPosition(view);
                if (itemPosition == 1 || itemPosition == 2) {
                    outRect.top = topMargin;
                } else if (itemPosition == 10) {
                    outRect.bottom = bottomMargin;
                }

            }
        };

        ComplaintDetailAdapter.onClickListener = this;
        GoodsCommentImageAdapter.onClickListener = this;
        mConfirmButton.setOnClickListener(this);
        mConfirmButton.setText(getResources().getString(R.string.submit));
        refresh();

        return v;
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CONFIRM:
                //撤销投诉申请
                delComplaint(complaintViewModel.complaint_id);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int info = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_BACK_DETAIL_BUTTON_ONE:
                //修改投诉申请
                editComplaint(complaintViewModel.complaint_id);
                getActivity().finish();
                break;
            case VIEW_TYPE_BACK_DETAIL_BUTTON_TWO:
                //撤销投诉申请
                showConfirmDialog(R.string.confirmDelComplaint, ViewType.VIEW_TYPE_CONFIRM
                        .ordinal());
                break;
            case VIEW_TYPE_BACK_DETAIL_BUTTON_THREE:
                //要求平台方接入处理
                intervention(complaintViewModel.complaint_id);
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
                //Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
                mImageList.remove(position);
                imageValueModel.url = mImageList;
                String s = mImageList.toString();
                imageLink = s.substring(1, s.length() - 1);
                adapter.notifyDataSetChanged();
                break;
            case VIEW_TYPE_GOODS_COMMENT_IMAGE:
                ComplaintDetailModel.DataBean.ComplaintReplyBean model = (ComplaintDetailModel.DataBean
                        .ComplaintReplyBean) adapter.data.get(info);
                viewOriginalImage(model.images,position);
                break;
            default:
                switch (view.getId()) {
                    case R.id.bottom_button:
                        if (!Utils.isNull(imageLink)) {
                            submit(complaintViewModel.complaint_id);
                        } else {
                            Toast.makeText(getActivity(), "上传投诉凭证图片不能为空", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        break;
                    default:
                        super.onClick(view);
                }

                break;
        }
    }


    @Override
    public void onOfflineViewClicked() {
        adapter.data.clear();
        refresh();
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (what) {
            case HTTP_COMPLAINT_DETAIL:
                break;
            case HTTP_COMPLAINT_DEL:
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_COMPLAINT_DETAIL:
                refreshCallback(response);
                break;
            case HTTP_COMPLAINT_DEL:
                delComplaintCallback(response);
                break;
            case HTTP_COMPLAINT_INTERVENTION:
                interventionCallback(response);
                break;
            case HTTP_COMPLAINT_EDIT:
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
                                BackDetailValueImageModel model = (BackDetailValueImageModel)
                                        adapter.data.get(1);
                                model.url = mImageList;
                                adapter.notifyDataSetChanged();

                                String s = mImageList.toString();
                                imageLink = s.substring(1, s.length() - 1);

                                //Utils.toastUtil.showToast(getActivity(), modelUpload.message);
                            }
                        });
                        break;
                }
                super.onRequestSucceed(what, response);
        }
    }

    public void refresh() {
        CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_VIEW,
                HTTP_COMPLAINT_DETAIL);
        mRequest.add("complaint_id", complaintId);
        addRequest(mRequest);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, ComplaintDetailModel.class, new HttpResultManager
                .HttpResultCallBack<ComplaintDetailModel>() {
            @Override
            public void onSuccess(ComplaintDetailModel back) {
                model = back;
                complaintItemModel = back.data.complaint_item;
                complaintViewModel = back.data.complaint_view;
                list.add(getTitleModel(model));

                if (complaintViewModel.complaint_status == Macro.CS_INVOLVE) {
                    mRecyclerView.removeItemDecoration(mItemDecorationOne);
                    mRecyclerView.removeItemDecoration(mItemDecorationTwo);
                    mRecyclerView.addItemDecoration(mItemDecorationOne);

                    mConfirmButton.setVisibility(View.VISIBLE);
                    list.add(getUploadImageModel());
                } else {
                    mRecyclerView.removeItemDecoration(mItemDecorationOne);
                    mRecyclerView.removeItemDecoration(mItemDecorationTwo);
                    mRecyclerView.addItemDecoration(mItemDecorationTwo);
                    mConfirmButton.setVisibility(View.GONE);
                }

                list.add(getTitleTwoModel());

                list.add(getShopNameModel());//店铺名称
                list.add(getReasonModel());//投诉原因
                list.add(getBackSnModel());//投诉编号
                list.add(getComplaintTimeModel());//投诉时间
                list.add(getBackDescModel());//投诉说明
                list.add(getOrderSnModel());//订单编号
                list.add(getShippingFeeModel());//运费
                list.add(getTotalModel());//总计
                list.add(getAddTimeModel());//成交时间

                list.add(getReplyTitleModel());
                list.addAll(model.data.complaint_reply);

                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void editComplaint(String complaintId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_COMPLAINT_ID.getValue(), complaintId);
        intent.setClass(getActivity(), ComplaintActivity.class);
        startActivity(intent);
    }

    private void intervention(String complaintId) {
        CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_INTERVENTION,
                HTTP_COMPLAINT_INTERVENTION);
        mRequest.add("complaint_id", complaintId);
        addRequest(mRequest);
    }

    private void submit(String complaintId) {
        CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_EDIT + "?complaint_id="
                + complaintId, HTTP_COMPLAINT_EDIT, RequestMethod.POST);
        mRequest.add("ComplaintModel[complaint_images]", imageLink);
        addRequest(mRequest);
    }

    private void delComplaint(String complaintId) {
        CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT_DEL, HTTP_COMPLAINT_DEL);
        mRequest.add("complaint_id", complaintId);
        mRequest.setAjax(true);
        addRequest(mRequest);
    }

    private void delComplaintCallback(String response) {
        Toast.makeText(getActivity(), "撤销投诉成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_COMPLAINT_REFRESH.getValue()));
        getActivity().finish();
    }

    private void interventionCallback(String response) {
        Toast.makeText(getActivity(), "申请成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_COMPLAINT_REFRESH.getValue()));
        getActivity().finish();

    }

    private void submitCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }, true);
    }

    private BackDetailValueModel getAddTimeModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "成交时间";
        valueModel.value = Utils.times(complaintViewModel.order_add_time);
        return valueModel;
    }


    private BackDetailValueModel getTotalModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "总计";
        valueModel.value = complaintViewModel.order_amount + "元";
        return valueModel;
    }


    private BackDetailValueModel getBackDescModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "投诉说明";
        valueModel.value = complaintViewModel.complaint_desc;
        return valueModel;
    }

    private BackDetailValueModel getBackSnModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "投诉编号";
        valueModel.value = complaintViewModel.complaint_sn;
        return valueModel;
    }

    private BackDetailValueModel getShippingFeeModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "运费";
        valueModel.value = complaintViewModel.shipping_fee + "元";
        return valueModel;
    }


    private BackDetailValueModel getComplaintTimeModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "投诉时间";
        valueModel.value = Utils.times(complaintViewModel.add_time);
        return valueModel;
    }

    private BackDetailValueModel getOrderSnModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "订单编号";
        valueModel.value = complaintViewModel.order_sn;
        return valueModel;
    }

    private BackDetailValueModel getReasonModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "投诉原因";
        valueModel.value = complaintItemModel.get(complaintViewModel.complaint_type);
        return valueModel;
    }


    private BackDetailTitleModel getTitleModel(ComplaintDetailModel model) {
        BackDetailTitleModel titleModel = new BackDetailTitleModel();
        int status = complaintViewModel.complaint_status;
        //提交投诉、修改投诉申请后的页面
        if (status == Macro.CS_WAIT) {
            type = 0;
            titleModel.title = "您已提交了投诉申请，请等待卖家处理";
            if ("0".equals(model.data.involve_time)) {
                titleModel.nameOne = "如果您对卖家处理投诉的结果不满意，您可以申请平台方介入处理 ";
            } else {
                titleModel.nameOne = "卖家在" + model.data.involve_time + "日之内未处理，您可以申请平台方介入处理 ";
            }

            titleModel.nameTwo = "投诉原因:" + complaintItemModel.get(complaintViewModel
                    .complaint_type);
            titleModel.nameThree = "投诉说明:" + complaintViewModel.complaint_desc;
            titleModel.textOne = "修改投诉申请";
            titleModel.textTwo = "撤销投诉申请";
            if ("1".equals(model.data.involve_status.show)) {
                titleModel.textThree = "要求平台方介入处理";
            } else {
                titleModel.textThree = "";
            }

            titleModel.buttonType = type;
            //卖家回复投诉后的页面
        } else if (status == Macro.CS_REPLIED) {
            type = 1;
            titleModel.title = "卖家已提交协商处理，请及时处理协商结果";
            if ("0".equals(model.data.involve_time)) {
                titleModel.nameOne = "如果您对卖家的协商处理结果不满意，您可以申请平台方介入处理";
            } else {
                titleModel.nameOne = "如果您对卖家的协商处理结果不满意，您可在" + model.data.involve_time +
                        "日之后，申请平台方介入处理";
            }
            titleModel.nameTwo = "";
            titleModel.nameThree = "";
            titleModel.textOne = "修改投诉申请";
            titleModel.textTwo = "撤销投诉申请";
            if ("1".equals(model.data.involve_status.show)) {
                titleModel.textThree = "要求平台方介入处理";
            } else {
                titleModel.textThree = "";
            }

            titleModel.buttonType = type;

            //买家撤销投诉后的页面
        } else if (status == Macro.CS_CANCELLED) {
            type = 2;
            titleModel.title = "投诉关闭";
            titleModel.nameOne = "投诉关闭时间：" + Utils.times(complaintViewModel.close_time);
            titleModel.nameTwo = "投诉结果:投诉关闭";
            titleModel.nameThree = "";
            titleModel.textOne = "";
            titleModel.textTwo = "";
            titleModel.textThree = "";
            titleModel.buttonType = type;

            //买家申请平台仲裁后的页面
        } else if (status == Macro.CS_INVOLVE) {
            titleModel.title = "您已经申请平台方介入，请等待平台方仲裁";
            type = 3;
            titleModel.nameOne = "";
            titleModel.nameTwo = "";
            titleModel.nameThree = "";
            titleModel.textOne = "";
            titleModel.textTwo = "";
            titleModel.textThree = "";
            titleModel.buttonType = type;

            //仲裁成功后的页面
        } else if (status == Macro.CS_ARBITRATION_SUCCESS) {
            type = 5;
            titleModel.title = "仲裁成功";
            titleModel.nameOne = "仲裁成功,店铺已被处罚";
            titleModel.nameTwo = "投诉原因:" + complaintItemModel.get(complaintViewModel
                    .complaint_type);
            titleModel.nameThree = "投诉说明:" + complaintViewModel.complaint_desc;
            titleModel.textOne = "";
            titleModel.textTwo = "";
            titleModel.textThree = "";
            titleModel.buttonType = type;

            //仲裁失败后的页面
        } else if (status == Macro.CS_ARBITRATION_FAIL) {
            titleModel.title = "仲裁失败";
            type = 6;
            titleModel.nameOne = "投诉原因:" + complaintItemModel.get(complaintViewModel
                    .complaint_type);
            titleModel.nameTwo = "投诉说明:" + complaintViewModel.complaint_desc;
            titleModel.nameThree = "";
            ;
            titleModel.textOne = "";
            titleModel.textTwo = "";
            titleModel.textThree = "";
            titleModel.buttonType = type;
        }

        return titleModel;
    }

    private BackDetailValueImageModel getUploadImageModel() {
        imageValueModel = new BackDetailValueImageModel();
        return imageValueModel;
    }

    private BackDetailTitleTwoModel getTitleTwoModel() {
        BackDetailTitleTwoModel valueModel = new BackDetailTitleTwoModel();
        valueModel.title = "投诉信息";
        return valueModel;
    }

    private BackDetailTitleTwoModel getReplyTitleModel() {
        BackDetailTitleTwoModel valueModel = new BackDetailTitleTwoModel();
        valueModel.title = "协商记录";
        return valueModel;
    }

    private BackDetailValueModel getShopNameModel() {
        BackDetailValueModel valueModel = new BackDetailValueModel();
        valueModel.name = "店铺名称";
        valueModel.value = complaintViewModel.shop_name;
        return valueModel;
    }

    private void openBackActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_TYPE.getValue(), "1");
        intent.setClass(getActivity(), BackActivity.class);
        startActivity(intent);
    }

    //上传图片相关
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

    public void viewOriginalImage(ArrayList data, int selectedIndex) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), data);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), selectedIndex);
        startActivity(intent);
    }
}
