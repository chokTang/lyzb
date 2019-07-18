package com.lyzb.jbx.fragment.me.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.image.LoadImageUtil;
import com.like.utilslib.other.RegexUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.CityRengionDilaog;
import com.lyzb.jbx.dialog.ShowExampleApplyDialog;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.inter.ISelectDialogListener;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.common.CityDialogModel;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.params.ApplyOpenBody;
import com.lyzb.jbx.mvp.presenter.me.ApplyOpenPresenter;
import com.lyzb.jbx.mvp.view.me.IApplyOpenView;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 申请入驻经销商or申请入驻运营伙伴
 * @time 2019 2019/3/12 17:24
 */

public class ApplyIdentityFragment extends BasePhotoFragment<ApplyOpenPresenter> implements IApplyOpenView,
        View.OnClickListener {
    private static final String TYPE_KEY = "TYPE_KEY";
    private int mType = 0;
    private static final String PARAMS_ID = "PARAMS_COMPANY_ID";
    private String mCompanyId = "";

    @BindView(R.id.img_union_me_com_apply_back)
    ImageView backImg;
    @BindView(R.id.tv_apply_title)
    TextView titleText;

    @BindView(R.id.lin_apply_com_type)
    LinearLayout comTypeLin;
    @BindView(R.id.tv_apply_com_type)
    TextView comTypeText;

    @BindView(R.id.img_apply_com_img)
    ImageView comImg;

    @BindView(R.id.edt_apply_com_name)
    EditText comNameEdt;

    @BindView(R.id.tv_apply_com_address)
    TextView addressText;

    @BindView(R.id.edt_apply_com_the_name)
    EditText nameEdt;
    @BindView(R.id.edt_apply_com_the_phone)
    EditText phoneEdt;

    @BindView(R.id.lin_apply_com_the_ser_code)
    LinearLayout serCodeLin;
    @BindView(R.id.edt_apply_com_the_ser_code)
    EditText serCodeEdt;

    @BindView(R.id.check_agr_ck)
    CheckBox agrCheck;
    @BindView(R.id.tv_agr_text)
    TextView agrText;

    @BindView(R.id.tv_apply_apply_text)
    TextView applyText;
    @BindView(R.id.tv_apply_hint)
    TextView applyHintText;

    private TextView img_apply_example;
    private TextView edt_apply_com_code;
    private LinearLayout btn_layout;

    //参数采集
    private ApplyOpenBody params = new ApplyOpenBody();

    private boolean isToComd = true;     //是否可以-企业资料-提交申请

    /****
     *
     * @param type 1:申请入驻经销商  2:申请入驻运营伙伴
     * @return
     */
    public static ApplyIdentityFragment newIntance(int type) {
        return newIntance(type, null);
    }

    public static ApplyIdentityFragment newIntance(int type, String companyId) {
        ApplyIdentityFragment applyFragment = new ApplyIdentityFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_KEY, type);
        args.putString(PARAMS_ID, companyId);
        applyFragment.setArguments(args);
        return applyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            mType = mBundle.getInt(TYPE_KEY);
            mCompanyId = mBundle.getString(PARAMS_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        img_apply_example = findViewById(R.id.img_apply_example);
        edt_apply_com_code = findViewById(R.id.edt_apply_com_code);
        btn_layout = findViewById(R.id.btn_layout);

        backImg.setOnClickListener(this);
        img_apply_example.setOnClickListener(this);
        comImg.setOnClickListener(this);
        addressText.setOnClickListener(this);
        comTypeText.setOnClickListener(this);
        applyText.setOnClickListener(this);
        agrText.setOnClickListener(this);

        if (!TextUtils.isEmpty(mCompanyId)) {
            mPresenter.getCompantInfo(mCompanyId);
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        //初始化该显示的页面
        params.setId(mCompanyId);
        if (mType == 1) {
            titleText.setText("申请经销商");
            params.setDistributorType(16);
            comTypeLin.setVisibility(View.GONE);
            agrText.setText("《礼仪之邦经销合作协议证书》");
        } else {
            titleText.setText("申请运营伙伴");
            params.setDistributorType(12);
            comTypeLin.setVisibility(View.VISIBLE);
            agrText.setText("《礼仪之邦平台服务协议》");
        }
        //服务码 CBEXZD
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_apply_ident;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_union_me_com_apply_back:
                pop();
                break;
            //点击图片示例
            case R.id.img_apply_example:
                ShowExampleApplyDialog applyDialog = new ShowExampleApplyDialog();
                applyDialog.show(getFragmentManager(), "APPLY_DIALOG");
                break;
            //点击选择图片
            case R.id.img_apply_com_img:
                new ActionSheetDialog(getContext())
                        .builder()
                        .addSheetItem("相册", new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                onChooseFromPhoto(new ISelectPictureListener() {
                                    @Override
                                    public void onSuccess(String imgUrl) {
                                        upFile(imgUrl);
                                    }

                                    @Override
                                    public void onFail() {
                                        showToast("选取失败");
                                    }
                                });
                            }
                        })
                        .addSheetItem("拍照", new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                onChooseFromCamera(new ISelectPictureListener() {
                                    @Override
                                    public void onSuccess(String imgUrl) {
                                        upFile(imgUrl);
                                    }

                                    @Override
                                    public void onFail() {
                                        showToast("拍照失败");
                                    }
                                });
                            }
                        })
                        .show();
                break;
            //选择地址
            case R.id.tv_apply_com_address:
                CityRengionDilaog dilaog = new CityRengionDilaog().setSelectDialogListener(new ISelectDialogListener() {
                    @Override
                    public void onSelect(CityDialogModel model) {
                        params.setAddress(model.getPathName());
                        params.setRegionId(model.getId());
                        params.setRegionLevel(model.getLevel());
                        params.setRegionName(model.getName());
                        addressText.setText(model.getPathName());
                    }
                });
                dilaog.show(getFragmentManager(), "selectCity");
                break;
            //选择运营类型
            case R.id.tv_apply_com_type:
                new ActionSheetDialog(getContext())
                        .builder()
                        .addSheetItem("省级运营伙伴", new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                comTypeText.setText("省级运营伙伴");
                                params.setDistributorType(12);
                            }
                        })
                        .addSheetItem("市级运营伙伴", new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                comTypeText.setText("市级运营伙伴");
                                params.setDistributorType(13);
                            }
                        })
                        .addSheetItem("区/县级运营伙伴", new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                comTypeText.setText("区/县级运营伙伴");
                                params.setDistributorType(14);
                            }
                        })
                        .show();
                break;
            //礼仪之邦平台协议
            case R.id.tv_agr_text:
                Intent pfIntent = new Intent(getContext(), ProjectH5Activity.class);
                if (mType==1){//经销
                    pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_DEALER_AGR);
                }else {
                    pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_SERVICE_AGR);
                }
                startActivity(pfIntent);
                break;
            //提交申请
            case R.id.tv_apply_apply_text:
                if (isToComd) {
                    onSubmitApply();
                } else {
                    showToast("暂时不可执行申请操作");
                }
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param imgUrl
     */
    private void upFile(String imgUrl) {
        mPresenter.upFileLoad(imgUrl, new ISelectPictureListener() {
            @Override
            public void onSuccess(final String imgUrl) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadImageUtil.loadImage(comImg, imgUrl);
                        params.setbLImgUrl(imgUrl);
                    }
                });
            }

            @Override
            public void onFail() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showToast("上传失败");
                    }
                });
            }
        });
    }

    /**
     * 提交
     */
    private void onSubmitApply() {
        if (TextUtils.isEmpty(params.getbLImgUrl())) {
            showToast("请上传营业执照");
            return;
        }
        String companyValue = comNameEdt.getText().toString().trim();

        if (TextUtils.isEmpty(companyValue)) {
            showToast("请输入企业名称");
            return;
        }
        params.setCompanyName(companyValue);

        String companyCodeValue = edt_apply_com_code.getText().toString().trim();
        if (TextUtils.isEmpty(companyCodeValue)) {
            showToast("请输入企业执照码");
            return;
        }
        params.setsCCode(companyCodeValue);

        if (TextUtils.isEmpty(params.getAddress())) {
            showToast("请选择注册地址");
            return;
        }

        String nameValue = nameEdt.getText().toString().trim();
        if (TextUtils.isEmpty(nameValue)) {
            showToast("请输入联系人姓名");
            return;
        }
        params.setLegalEntityName(nameValue);

        String phoneValue = phoneEdt.getText().toString().trim();
        if (!RegexUtil.checkMobile(phoneValue)) {
            showToast("请输入正确格式的手机号");
            return;
        }
        params.setLegalEntityMobile(phoneValue);

        String serviceCode = serCodeEdt.getText().toString().trim();
        if (TextUtils.isEmpty(serviceCode)) {
            showToast("请输入推荐码");
            return;
        }
        params.setContractNumber(serviceCode);

        if (!agrCheck.isChecked()) {
            showToast("请阅读阅读协议证书");
            return;
        }
        mPresenter.onApplyCompany(params);
    }

    @Override
    public void onApplyResultSuccess() {
        pop();
    }

    @Override
    public void onCompanyInfoResult(ComdDetailModel.GsDistributorVoBean model) {
        if (model == null) {
            return;
        }
        if (model.getAuditState() == 1 || model.getAuditState() == 2) {
            applyHintText.setVisibility(View.GONE);
            applyText.setText("提交入驻申请");
            isToComd = true;
            return;
        } else if (model.getAuditState() == 3) {
            applyHintText.setVisibility(View.GONE);
            applyText.setText("资料审核中");
            isToComd = false;
        } else if (model.getAuditState() == 4) {
            applyHintText.setVisibility(View.GONE);
            applyText.setText("已通过");
            isToComd = false;
        } else if (model.getAuditState() == 5) {
            applyText.setText("提交审核");
            applyHintText.setVisibility(View.VISIBLE);
            applyHintText.setText(String.format("审核未通过：%s", model.getDescription()));
            isToComd = true;
        } else {
            applyHintText.setVisibility(View.GONE);
            applyText.setText("提交入驻申请");
            isToComd = true;
            return;
        }

        //需要做赋值操作
        params.setDistributorType(model.getDistributorType());
        comTypeText.setText(model.getIndustryName());

        params.setbLImgUrl(model.getbLImgUrl());
        LoadImageUtil.loadImage(comImg, model.getbLImgUrl());

        params.setCompanyName(model.getCompanyName());
        comNameEdt.setText(model.getCompanyName());

        params.setsCCode(model.getsCCode());
        edt_apply_com_code.setText(model.getsCCode());

        params.setRegionId(model.getRegionId());
        params.setRegionLevel(model.getRegionLevel() + "");
        params.setRegionName(model.getRegionName());
        params.setAddress(model.getAddress());
        addressText.setText(model.getAddress());

        params.setLegalEntityName(model.getLegalEntityName());
        nameEdt.setText(model.getLegalEntityName());

        params.setLegalEntityMobile(model.getLegalEntityMobile());
        phoneEdt.setText(model.getLegalEntityMobile());

        params.setContractNumber(model.getContractNumber());
        serCodeEdt.setText(model.getContractNumber());
    }
}
