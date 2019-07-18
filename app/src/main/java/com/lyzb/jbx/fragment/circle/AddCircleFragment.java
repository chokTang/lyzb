package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.base.BasePhotoToolbarFragment;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.me.AddCircleModel;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.UpdCircleModel;
import com.lyzb.jbx.mvp.presenter.me.AddCirclePresenter;
import com.lyzb.jbx.mvp.view.me.IAddCircleView;
import com.szy.yishopcustomer.Util.image.LoadImageUtil;

import org.devio.takephoto.model.CropOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 创建圈子
 * @time 2019 2019/3/13 13:38
 */

public class AddCircleFragment extends BasePhotoToolbarFragment<AddCirclePresenter> implements IAddCircleView {

    private final static String TYPE = "TYPE";
    private final static String CR_MODEL = "CR_MODEL";
    private final static String ORGID = "orgid";

    private int mType = 0;
    private CircleDetModel mModel = null;

    @BindView(R.id.edt_add_circle_name)
    EditText nameEdt;
    @BindView(R.id.edt_add_circle_info)
    EditText infoEdt;

    @BindView(R.id.img_add_circle_head)
    ImageView headImg;
    @BindView(R.id.img_add_circle_bg)
    ImageView bgImg;

    @BindView(R.id.tv_add_cricle_btn)
    TextView addText;
    @BindView(R.id.cbx_add_examine)
    CheckBox isExamineCbx;

    private boolean isAddLogo = false;
    private String logoImg, backbgImg = null;
    private String mOrgId;

    /****
     *
     * @param fromType 来源类型  2-创建  3-编辑
     * @param model
     * @return
     */
    public static AddCircleFragment newItance(int fromType, String orgId, CircleDetModel model) {
        AddCircleFragment fragment = new AddCircleFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, fromType);
        args.putSerializable(CR_MODEL, model);
        args.putString(ORGID, orgId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE);
            mModel = (CircleDetModel) bundle.getSerializable(CR_MODEL);
            mOrgId = bundle.getString(ORGID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        ButterKnife.bind(this, mView);
        onBack();

        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddLogo = true;
                addImg();
            }
        });

        bgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddLogo = false;
                addImg();
            }
        });

        if (mType == 2) {
            addText.setText("确定创建");
            setToolbarTitle("创建圈子");
        } else {
            addText.setText("编辑保存");
            setToolbarTitle("编辑圈子");
        }

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCircle();
            }
        });
        if (mModel != null) {
            isExamineCbx.setChecked(!mModel.isPublicStr());
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        if (mType == 3) {
            logoImg = mModel.getLogo();
            backbgImg = mModel.getBackground();

            nameEdt.setText(mModel.getGroupname());
            infoEdt.setText(mModel.getDescription());

            LoadImageUtil.loadImage(headImg, logoImg);
            LoadImageUtil.loadImage(bgImg, backbgImg);
            //企业圈子不允许编辑圈子名称
            nameEdt.setFocusable(TextUtils.isEmpty(mModel.getOrgId()));
            nameEdt.setFocusableInTouchMode(TextUtils.isEmpty(mModel.getOrgId()));
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_add_circle;
    }

    private void addImg() {

        new ActionSheetDialog(getActivity())
                .builder()
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                        onChooseFromCameraRound(getCropOptions(), new ISelectPictureListener() {
                            @Override
                            public void onSuccess(String imgUrl) {

                                if (isAddLogo) {
                                    mPresenter.upLoadFiles(imgUrl);
                                } else {
                                    mPresenter.upLoadFiles(imgUrl);
                                }
                            }

                            @Override
                            public void onFail() {

                            }
                        });

                    }
                })
                .addSheetItem("相册选择", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        onChooseFromPhotoRound(getCropOptions(), new ISelectPictureListener() {
                            @Override
                            public void onSuccess(String imgUrl) {

                                if (isAddLogo) {
                                    mPresenter.upLoadFiles(imgUrl);
                                } else {
                                    mPresenter.upLoadFiles(imgUrl);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });

                    }
                })
                .show();
    }

    private void addCircle() {

        if (TextUtils.isEmpty(nameEdt.getText().toString().trim())) {
            showToast("请输入圈子名称");
            return;
        }

        if (TextUtils.isEmpty(infoEdt.getText().toString().trim())) {
            showToast("请输入圈子简介");
            return;
        }


        if (mType == 2) {
            AddCircleModel model = new AddCircleModel();
            model.setGroupname(nameEdt.getText().toString().trim());
            model.setDesc(infoEdt.getText().toString().trim());
            model.setLogo(logoImg);
            model.setBackground(backbgImg);
            model.setPublicStr(!isExamineCbx.isChecked());
            model.setOrgId(mOrgId);

            mPresenter.createCircle(model);
        } else {
            UpdCircleModel updCircleModel = new UpdCircleModel();
            updCircleModel.setGroupname(nameEdt.getText().toString().trim());
            updCircleModel.setDesc(infoEdt.getText().toString().trim());
            updCircleModel.setLogo(logoImg);
            updCircleModel.setBackground(backbgImg);
            updCircleModel.setId(mModel.getId());
            updCircleModel.setPublicStr(!isExamineCbx.isChecked());
            updCircleModel.setOrgId(mOrgId);

            mPresenter.updateCircle(updCircleModel);
        }
    }

    @Override
    public void getImgList(final String imgUrl) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isAddLogo) {
                                    logoImg = imgUrl;
                                    LoadImageUtil.loadImage(headImg, imgUrl);
                                } else {
                                    backbgImg = imgUrl;
                                    LoadImageUtil.loadImage(bgImg, imgUrl);
                                }
                            }
                        });
                    }
                }

        ).start();
    }

    @Override
    public void onCreateSucces() {
        pop();
    }

    @Override
    public void onUpdate() {
        pop();
    }

    @Override
    public void onError(String msg) {
        showToast(msg);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder options = new CropOptions.Builder();
        if (isAddLogo) {
            options.setAspectX(1)
                    .setAspectY(1)
                    .setOutputX(200)
                    .setOutputY(200)
                    .setWithOwnCrop(true);//设置圆角 false是圆角，true是正方形
        } else {
            options.setAspectX(16)
                    .setAspectY(9)
                    .setOutputX(768)
                    .setOutputY(732)
                    .setWithOwnCrop(true);//设置圆角 false是圆角，true是正方形
        }

        return options.create();
    }
}
