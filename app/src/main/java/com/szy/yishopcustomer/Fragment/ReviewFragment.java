package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Adapter.ReviewShareOrderImgAdater;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.MyGlideEngine;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Review.UploadModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.RatingBar;
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

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by buqingqiang on 2016/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewFragment extends YSCBaseFragment {
    private static final String TAG = "ReviewFragment";
    private static final int UPLOAD_IMAGE = 1;
    public static File tempFile;
    public String commentText = "";//评价内容
    public String ogGoodsId;//商品ID
    public String ogOrderId;//订单ID
    public String ogRecordId;
    public String mSkuId;
    public String mShopId;
    public String mRecordId;
    public String imgPath = "";//图片路径
    public String score = "0";//评分
    public String isChecked = "1";//是否匿名0/1
    public String mReviewType = "0";
    @BindView(R.id.fragment_evaluate_share_order_edit_editText)
    EditText mEvaluateShareOrderEditText;
    @BindView(R.id.fragement_edit_count_textView)
    TextView mEditCountTextView;
    @BindView(R.id.fragment_review_pic_relativeLayout)
    RelativeLayout mAddShareOrderPicRelativeLayout;
    @BindView(R.id.evaluate_share_order_ratingBar)
    RatingBar mEvaluateShareOrderRatingBar;
    @BindView(R.id.image_checkbox)
    ImageView mAnonymousEvaluateImageView;
    @BindView(R.id.fragment_evaluate_share_order_goods_img_imageView)
    ImageView mGoodsImgImageView;
    @BindView(R.id.fragment_score_textView)
    TextView mScoreTextView;
    @BindView(R.id.fragment_review_anonymous_relativeLayout)
    LinearLayout mAnonymousRelativeLayout;
    @BindView(R.id.fragment_review_share_order_img_pullableRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_review_publish_review_textView)
    TextView mPublishReviewTextView;
    @BindView(R.id.frgment_review_anonymous_evaluate_textView)
    TextView mAnonymousTextView;
    @BindView(R.id.fragment_review_line_View)
    View mLineView;
    private ProgressDialog mProgressDialog;
    private int mCurrentImage = 0;
    private int editCount;
    private Uri imageUri;

    private List<String> mShareOrderImgList = new ArrayList<>();
    private ReviewShareOrderImgAdater reviewShareOrderImgAdater;
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

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    int poi = 0;
    List<Uri> result = new ArrayList<>();
    private static final int IMAGE_MAX_COUNT = 5;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_review_pic_relativeLayout:
                if (mCurrentImage >= IMAGE_MAX_COUNT) {
                    Utils.toastUtil.showToast(getActivity(), "最多选择五张图片");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final String[] arr = new String[]{"拍照", "本地选择"};
                    builder.setItems(arr, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
//                                PackageManager pm = getActivity().getPackageManager();
//                                boolean flag = (PackageManager.PERMISSION_GRANTED == pm
//                                        .checkPermission("android.permission.CAMERA", App.packageName));
                                if (cameraIsCanUse()) {
                                    RxPhotoTool.openCameraImage(ReviewFragment.this);
                                } else {
                                    Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
                                }
//                                if (flag) {
//                                    RxPhotoTool.openCameraImage(ReviewFragment.this);
//                                } else {
//                                    Utils.toastUtil.showToast(getActivity(), "没有拍照权限，请到设置里开启权限");
//                                }
                            } else {
                                pickImage();
                            }
                        }

                    });
                    builder.create().show();
                }

                break;
            case R.id.fragment_review_publish_review_textView:
                if (editCount < 0) {
                    Utils.toastUtil.showToast(getActivity(), "请将字数限制在400字以内");
                } else {
                    publish();
                }
                break;
            case R.id.image_checkbox:
                Object type = mAnonymousEvaluateImageView.getTag();
                if ((int) type == R.mipmap.bg_check_normal) {
                    mAnonymousEvaluateImageView.setSelected(true);
                    mAnonymousEvaluateImageView.setTag(R.mipmap.bg_check_selected);
                    isChecked = "1";

                } else {
                    mAnonymousEvaluateImageView.setSelected(false);
                    mAnonymousEvaluateImageView.setTag(R.mipmap.bg_check_normal);
                    isChecked = "0";
                }
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_SHARE_ORDER_IMG:
                        mShareOrderImgList.remove(position);
                        changeAdapter();
                        break;
                    default:
                        super.onClick(v);
                }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        reviewShareOrderImgAdater = new ReviewShareOrderImgAdater();
        mRecyclerView.setAdapter(reviewShareOrderImgAdater);
        ReviewShareOrderImgAdater.onClickListener = this;

        Intent intent = getActivity().getIntent();
        mReviewType = intent.getStringExtra(Key.KEY_REVIEW_TYPE.getValue());
        if (mReviewType.equals("1")) {
            mGoodsImgImageView.setVisibility(View.GONE);
            mScoreTextView.setVisibility(View.GONE);
            mEvaluateShareOrderRatingBar.setVisibility(View.GONE);
            mLineView.setVisibility(View.GONE);
            mAnonymousEvaluateImageView.setVisibility(View.GONE);
            mAnonymousTextView.setVisibility(View.GONE);

            mEvaluateShareOrderEditText.setHint(R.string.hintEnterEvaluated);
        } else if (mReviewType.equals("0")) {
            mGoodsImgImageView.setVisibility(View.VISIBLE);
            mScoreTextView.setVisibility(View.VISIBLE);
            mEvaluateShareOrderRatingBar.setVisibility(View.VISIBLE);
            mLineView.setVisibility(View.VISIBLE);
            mAnonymousEvaluateImageView.setVisibility(View.VISIBLE);
            mAnonymousTextView.setVisibility(View.VISIBLE);

            mEvaluateShareOrderEditText.setHint(R.string.hintEnterEvaluate);
        }
        ImageLoader.displayImage(Utils.urlOfImage(intent.getStringExtra(Key.KEY_GOODS_IMAGE
                .getValue())), mGoodsImgImageView);
        mAddShareOrderPicRelativeLayout.setOnClickListener(this);
        mAnonymousEvaluateImageView.setOnClickListener(this);
        mAnonymousEvaluateImageView.setSelected(true);
        mAnonymousEvaluateImageView.setTag(R.mipmap.bg_check_selected);

        //编辑器点击事件
        mEvaluateShareOrderEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                commentText = String.valueOf(mEvaluateShareOrderEditText.getText());
                editCount = 400 - (commentText.length());
                mEditCountTextView.setText(String.valueOf(editCount));
                if (editCount < 0) {
                    Utils.toastUtil.showToast(getActivity(), "请将字数限制在400字以内");
                }
                changeAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPublishReviewTextView.setOnClickListener(this);
        mEvaluateShareOrderRatingBar.setOnStarChangeListener(new RatingBar.OnChangeListener() {
            @Override
            public void onChange(int star) {
                score = star + "";
            }
        });
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_UPLOAD_IMAGE:
                HttpResultManager.resolve(response, UploadModel.class, new HttpResultManager.HttpResultCallBack<UploadModel>() {
                    @Override
                    public void onSuccess(UploadModel back) {
                        mShareOrderImgList.add(back.data.url);
                        changeAdapter();

                        poi++;
                        if (poi < result.size()) {
                            zipImage(result.get(poi));
                        }
                    }
                });
                break;
            case HTTP_PUBLISH_SHARE_REPLY_ORDER:
                // mProgress.dismiss();
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        mShareOrderImgList.clear();
                        Utils.toastUtil.showToast(getActivity(), back.message);
                    }
                }, true);

                Intent intent = new Intent();
                intent.putExtra(Key.KEY_REVIEW_STATUS.getValue(), "1");
                setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                break;

            case HTTP_PUBLISH_SHARE_ORDER:
                Log.i(TAG, "HTTP_PUBLISH_SHARE_ORDER");
                HttpResultManager.resolve(response,
                        ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel modelPublishShareOrder) {
                                mShareOrderImgList.clear();
                                Utils.toastUtil.showToast(getActivity(), modelPublishShareOrder.message);
                            }
                        }, true);
                Intent intentPublishShareOrder = new Intent();
                intentPublishShareOrder.putExtra(Key.KEY_REVIEW_STATUS.getValue(), "1");
                setResult(Activity.RESULT_OK, intentPublishShareOrder);
                getActivity().finish();
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_review;
        mProgressDialog = new ProgressDialog(getContext());

        Intent intent = getActivity().getIntent();
        ogOrderId = intent.getStringExtra(Key.KEY_ORDER_ID.getValue());
        ogRecordId = intent.getStringExtra(Key.KEY_RECORD_ID.getValue());
        mSkuId = intent.getStringExtra(Key.KEY_SKU_ID.getValue());
        mShopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
        mRecordId = intent.getStringExtra(Key.KEY_RECORD_ID.getValue());
        mReviewType = intent.getStringExtra(Key.KEY_REVIEW_TYPE.getValue());

    }

    public void publish() {
        mCurrentImage = 0;

        CommonRequest mPublishShareOrderRequest;
        if (mReviewType.equals("0")) {
            if (score.equals("0")) {
                Utils.toastUtil.showToast(getActivity(), "必须输入分数");
            } else {
                changeImg();
                if (commentText.equals("") && imgPath.equals("")) {
                    Utils.toastUtil.showToast(getActivity(), "请先添加评论或上传图片再提交");
                } else {
                    mPublishShareOrderRequest = new CommonRequest(Api.API_PUBLISH_SHARE_ORDER,
                            HttpWhat.HTTP_PUBLISH_SHARE_ORDER.getValue(), RequestMethod.POST);
                    mPublishShareOrderRequest.add("content", commentText);
                    mPublishShareOrderRequest.add("record_id", ogRecordId);
                    mPublishShareOrderRequest.add("score", score);
                    mPublishShareOrderRequest.add("images", imgPath);
                    mPublishShareOrderRequest.add("is_anonymous", isChecked);
                    addRequest(mPublishShareOrderRequest);
                    // mProgress.show();
                }
            }
        } else {
            changeImg();
            if (commentText.equals("") && imgPath.equals("")) {
                Utils.toastUtil.showToast(getActivity(), "请先添加评论或上传图片再提交");
            } else {
                mPublishShareOrderRequest = new CommonRequest(Api.API_PUBLISH_SHARE_REPLY_ORDER,
                        HttpWhat.HTTP_PUBLISH_SHARE_REPLY_ORDER.getValue(), RequestMethod.POST);
                mPublishShareOrderRequest.add("content", commentText);
                mPublishShareOrderRequest.add("record_id", mRecordId);
                mPublishShareOrderRequest.add("images", imgPath);
                mPublishShareOrderRequest.setAjax(true);
                addRequest(mPublishShareOrderRequest);
                // mProgress.show();
            }

        }
    }

    private void changeAdapter() {
        if (mShareOrderImgList != null) {
            reviewShareOrderImgAdater.data.clear();
            reviewShareOrderImgAdater.data.addAll(mShareOrderImgList);
        }
        if (mShareOrderImgList.size() >= IMAGE_MAX_COUNT) {
            mAddShareOrderPicRelativeLayout.setVisibility(View.GONE);
        } else {
            mAddShareOrderPicRelativeLayout.setVisibility(View.VISIBLE);
        }

        reviewShareOrderImgAdater.notifyDataSetChanged();
    }

    private void changeImg() {
        imgPath = "";
        for (int i = 0; i < mShareOrderImgList.size(); i++) {
            if (imgPath.equals("")) {
                imgPath = mShareOrderImgList.get(i);
            } else {
                imgPath += "," + mShareOrderImgList.get(i);

            }
        }
    }

    private File createOutputFile(String prefix) throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(prefix, ".jpg", storageDir);
    }

    private void pickImage() {

        poi = 0;
        Matisse.from(this)
                .choose(MimeType.allOf())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(IMAGE_MAX_COUNT - mShareOrderImgList.size())//可选的最大数
                .imageEngine(new MyGlideEngine())//图片加载引擎
                .forResult(RequestCode.REQUEST_PICK.getValue());
    }

    private void uploadAvatar(String imagePath) {
        CommonRequest request = new CommonRequest(Api.API_REVIEW_UPLOAD, HttpWhat
                .HTTP_UPLOAD_IMAGE.getValue(), RequestMethod.POST);
        BasicBinary binary = new FileBinary(new File(imagePath));
        binary.setUploadListener(UPLOAD_IMAGE, mUploadListener);
        request.add("load_img", binary);
        addRequest(request);
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
}