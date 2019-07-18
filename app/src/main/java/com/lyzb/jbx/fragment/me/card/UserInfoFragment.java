package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.utilslib.other.LogUtil;
import com.like.utilslib.other.RegexUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.model.LocationAddressInfo;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.CardUserInfoModel;
import com.lyzb.jbx.mvp.presenter.me.card.CardInfoPresenter;
import com.lyzb.jbx.mvp.view.me.ICardInfoView;
import com.szy.yishopcustomer.Util.image.LoadImageUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.lyzb.jbx.fragment.me.card.AddressFragment.KEY_ADDRESS;
import static com.szy.yishopcustomer.Util.LubanImg.getPath;

/**
 * @author wyx
 * @role 个人信息编辑
 * @time 2019 2019/3/4 14:40
 */

public class UserInfoFragment extends BasePhotoFragment<CardInfoPresenter> implements ICardInfoView, View.OnClickListener {


    public static final int LOGO = 3031;//logo
    public static final int BG_CARD = 3032; // 名片模板背景
    public static final int AVATAR = 3033; // 头像
    public static final int WX = 3034; //微信二维码
    public static final int RESULT_ADDRESS_CODE = 0x963; //地址返回码
    private static final String TYPE_CARD = "TYPE_CARD";

    private ImageView backImg;
    private ImageView headCrl;
    private EditText nameEdt;
    private EditText phoneEdt;
    Double lat = 0.0,lng = 0.0;
    private List<LocalMedia> list = new ArrayList<>();

    private TextView wxText;
    private TextView logoText;
    private TextView tvHint,edt_address;
    private ImageView qrcodeImg, img_wx_cancel;
    private ImageView img_bg_card;
    private ImageView img_company_logo, img_logo_cancel;
    private RelativeLayout rl_logo, rl_wx_img;
    private EditText positionEdt;
    private EditText  edt_u_info_zuoji, edt_u_info_wx, edt_u_info_mail;
    private EditText edt_name;
    private RadioGroup radioGroup;
    private RadioButton rb_man, rb_woman;
    private Button saveBtn;
    private int sex;//性别(1.先生 2.女士)
    private CardUserInfoModel mInfoModel = null;
    private String headUrl, qrcodeUrl = null, logo = "", cardbg = "";

    private CardModel cardModel = null;

    public static UserInfoFragment newIntance(CardModel cardModel) {

        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_CARD, (Serializable) cardModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            cardModel = (CardModel) bundle.getSerializable(TYPE_CARD);
        }

    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);

        backImg = findViewById(R.id.img_union_u_info_back);
        headCrl = findViewById(R.id.cim_union_info_head);
        nameEdt = findViewById(R.id.edt_u_info_name);
        phoneEdt = findViewById(R.id.edt_u_info_phone);

        wxText = findViewById(R.id.tv_u_info_wx);
        logoText = findViewById(R.id.tv_u_info_logo);
        tvHint = findViewById(R.id.tv_hint);
        qrcodeImg = findViewById(R.id.img_u_info_qrcode);
        img_wx_cancel = findViewById(R.id.img_wx_cancel);
        img_company_logo = findViewById(R.id.img_company_logo);
        rl_logo = findViewById(R.id.rl_logo);
        rl_wx_img = findViewById(R.id.rl_wx_img);
        img_logo_cancel = findViewById(R.id.img_logo_cancel);
        img_bg_card = findViewById(R.id.img_bg_card);
        positionEdt = findViewById(R.id.edt_u_info_position);
        edt_address = findViewById(R.id.edt_address);
        edt_name = findViewById(R.id.edt_name);
        saveBtn = findViewById(R.id.btn_union_u_info_save);
        radioGroup = findViewById(R.id.radioGroup);
        rb_man = findViewById(R.id.rb_man);
        rb_woman = findViewById(R.id.rb_woman);
        edt_u_info_zuoji = findViewById(R.id.edt_u_info_zuoji);
        edt_u_info_wx = findViewById(R.id.edt_u_info_wx);
        edt_u_info_mail = findViewById(R.id.edt_u_info_mail);

        mInfoModel = new CardUserInfoModel();

//        选择性别
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_man://男士
                        sex = 1;
                        break;
                    case R.id.rb_woman://女士
                        sex = 2;
                        break;
                }
            }
        });

        backImg.setOnClickListener(this);
        headCrl.setOnClickListener(this);
        qrcodeImg.setOnClickListener(this);
        img_wx_cancel.setOnClickListener(this);
        img_company_logo.setOnClickListener(this);
        img_logo_cancel.setOnClickListener(this);
        wxText.setOnClickListener(this);
        logoText.setOnClickListener(this);
        img_bg_card.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        edt_address.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        if (cardModel != null) {
//            头像
            if (!TextUtils.isEmpty(cardModel.getHeadimg())) {
                LoadImageUtil.loadImage(headCrl, cardModel.getHeadimg());
                headUrl = cardModel.getHeadimg();
            }
            //名片模板背景
            if (!TextUtils.isEmpty(cardModel.getPosterImg())) {
                LoadImageUtil.loadImage(img_bg_card, cardModel.getPosterImg());
                cardbg = cardModel.getPosterImg();
            }

            if (cardModel.getSex() == 1) {//男士
                rb_man.setChecked(true);
                rb_woman.setChecked(false);
            } else {//女士
                rb_man.setChecked(false);
                rb_woman.setChecked(true);
            }
            nameEdt.setText(cardModel.getGsName());
            phoneEdt.setText(cardModel.getMobile());

//            微信二维码处理
            if (!TextUtils.isEmpty(cardModel.getWxImg())) {
                LoadImageUtil.loadImage(qrcodeImg, cardModel.getWxImg());
                qrcodeUrl = cardModel.getWxImg();

                wxText.setVisibility(View.GONE);
                rl_wx_img.setVisibility(View.VISIBLE);
            } else {
                rl_wx_img.setVisibility(View.GONE);
                wxText.setVisibility(View.VISIBLE);
            }

//            logo处理
            if (!TextUtils.isEmpty(cardModel.getShopLogo())) {
                LoadImageUtil.loadImage(img_company_logo, cardModel.getShopLogo());
                logo = cardModel.getShopLogo();

                logoText.setVisibility(View.GONE);
                rl_logo.setVisibility(View.VISIBLE);
            } else {
                rl_logo.setVisibility(View.GONE);
                logoText.setVisibility(View.VISIBLE);
            }
            lat = Double.valueOf(cardModel.getMapLat());
            lng = Double.valueOf(cardModel.getMapLng());
            positionEdt.setText(cardModel.getPosition());
            edt_address.setText(cardModel.getShopAddress());
            edt_name.setText(cardModel.getShopName());
            edt_u_info_zuoji.setText(cardModel.getTel());
            edt_u_info_wx.setText(cardModel.getWxNum());
            edt_u_info_mail.setText(cardModel.getEmail());
        }
    }

    @Override
    public void toCard(String data) {

        Bundle bundle = new Bundle();
        bundle.putString("UserInfo", data);
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }

    @Override
    public void getHeadImg(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(url)) {
                            tvHint.setVisibility(View.VISIBLE);
                        } else {
                            tvHint.setVisibility(View.GONE);
                        }
                        LoadImageUtil.loadImage(headCrl, url);
                        headUrl = url;
                    }
                });
            }
        }).start();

    }

    @Override
    public void getWxImg(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadImageUtil.loadImage(qrcodeImg, url);
                        qrcodeUrl = url;
                        rl_wx_img.setVisibility(View.VISIBLE);
                        wxText.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void getLogoImg(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadImageUtil.loadImage(img_company_logo, url);
                        logo = url;
                        rl_logo.setVisibility(View.VISIBLE);
                        logoText.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void getCardBgImg(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadImageUtil.loadImage(img_bg_card, url);
                        cardbg = url;
                    }
                });
            }
        }).start();
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_user_info;
    }


    /**
     * 保存信息
     */
    private void saveInfo() {

        if (TextUtils.isEmpty(headUrl)) {
            showToast("请选择头像");
            return;
        }

        if (TextUtils.isEmpty(nameEdt.getText().toString().trim())) {
            showToast("请输入名称");
            return;
        }


        if (!TextUtils.isEmpty(edt_u_info_mail.getText().toString().trim())) {
            if (!RegexUtil.checkEmail(edt_u_info_mail.getText().toString().trim())) {
                showToast("请输入正确的邮箱地址");
                return;
            }
        }

        mInfoModel.setGsName(nameEdt.getText().toString().trim());
        mInfoModel.setHeadimg(headUrl);
        mInfoModel.setMobile(phoneEdt.getText().toString().trim());
        mInfoModel.setWxImg(qrcodeUrl);
        mInfoModel.setShopLogo(logo);
        mInfoModel.setPosterImg(cardbg);
        sex = radioGroup.getCheckedRadioButtonId() == R.id.rb_man ? 1 : 2;
        mInfoModel.setSex(sex);
        mInfoModel.setPosition(positionEdt.getText().toString().trim());
        mInfoModel.setShopAddress(edt_address.getText().toString().trim());
        mInfoModel.setShopName(edt_name.getText().toString().trim());
        mInfoModel.setTel(edt_u_info_zuoji.getText().toString().trim());
        mInfoModel.setWxNum(edt_u_info_wx.getText().toString().trim());
        mInfoModel.setEmail(edt_u_info_mail.getText().toString().trim());

        mPresenter.saveInfo(mInfoModel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_union_u_info_back://返回
                pop();
                break;
            case R.id.cim_union_info_head://点击头像
                choosePicture(AVATAR);
                break;
            case R.id.img_u_info_qrcode://点击二维码
            case R.id.tv_u_info_wx:
                choosePicture(WX);
                break;
            case R.id.img_wx_cancel://删除wx图
                qrcodeUrl ="";
                rl_wx_img.setVisibility(View.GONE);
                wxText.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_union_u_info_save://点击保存
                saveInfo();
                break;
            case R.id.tv_u_info_logo://公司logo
            case R.id.img_company_logo://公司logo
                choosePicture(LOGO);
                break;
            case R.id.img_logo_cancel://删除logo
                logo = "";
                rl_logo.setVisibility(View.GONE);
                logoText.setVisibility(View.VISIBLE);
                break;
            case R.id.img_bg_card://点击背景
                choosePicture(BG_CARD);
                break;
            case R.id.edt_address://点击地址
                startForResult(AddressFragment.Companion.newIncetance(lat,lng,edt_address.getText().toString().trim()),RESULT_ADDRESS_CODE);
                break;
        }
    }


    private void choosePicture(int code) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(code);//结果回调onActivityResult code
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_ADDRESS_CODE:
                if (data!=null){
                    LocationAddressInfo addressInfo = (LocationAddressInfo) data.getSerializable(KEY_ADDRESS);
                    lat = addressInfo.lat;
                    lng = addressInfo.lon;
                    mInfoModel.setMapLat(addressInfo.lat.toString());
                    mInfoModel.setMapLng(addressInfo.lon.toString());
                    edt_address.setText(addressInfo.getText());
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGO:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCut()) {
                        mPresenter.upLoadFiles(selectList.get(0).getCutPath(), LOGO);
                    } else {
                        mPresenter.upLoadFiles(selectList.get(0).getPath(), LOGO);

                    }
                }
                break;
            case BG_CARD:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCut()) {
                        mPresenter.upLoadFiles(selectList.get(0).getCutPath(), BG_CARD);
                    } else {
                        mPresenter.upLoadFiles(selectList.get(0).getPath(), BG_CARD);
                    }
                }
                break;
            case WX:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCut()) {
                        mPresenter.upLoadFiles(selectList.get(0).getCutPath(), WX);
                    } else {
                        mPresenter.upLoadFiles(selectList.get(0).getPath(), WX);
                    }
                }
                break;
            case AVATAR:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCut()) {
                        mPresenter.upLoadFiles(selectList.get(0).getCutPath(), AVATAR);
                    } else {
                        mPresenter.upLoadFiles(selectList.get(0).getPath(), AVATAR);
                    }
                }
                break;
        }
    }
}
