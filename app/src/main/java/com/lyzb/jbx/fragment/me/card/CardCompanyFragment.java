package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.app.AppUtil;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.other.LogUtil;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.card.CardTextImgAdapter;
import com.lyzb.jbx.adapter.me.card.CustomModelAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.CreateModelDialog;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.common.HomeHtmlTextFragment;
import com.lyzb.jbx.fragment.me.company.ComdAdsDialog;
import com.lyzb.jbx.fragment.me.company.ComdPhoneDialog;
import com.lyzb.jbx.fragment.me.company.CompanyWebFragment;
import com.lyzb.jbx.fragment.me.company.MyCompanyFragment;
import com.lyzb.jbx.model.LocationAddressInfo;
import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.AddComdImgModel;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.CardModelMsg;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.me.CustomModular;
import com.lyzb.jbx.model.me.IntroductionContent;
import com.lyzb.jbx.model.me.company.CompanyModelModel;
import com.lyzb.jbx.model.msg.EventBusMsg;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.model.params.SaveCardModelBody;
import com.lyzb.jbx.model.params.UpdateModelBody;
import com.lyzb.jbx.mvp.presenter.me.AddComdPresenter;
import com.lyzb.jbx.mvp.view.me.IAddComdView;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.widget.CusPopWindow;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.szy.yishopcustomer.Constant.Key;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lyzb.jbx.fragment.common.HomeHtmlTextFragment.KEY_DATA;
import static com.lyzb.jbx.fragment.me.card.AddressFragment.KEY_ADDRESS;
import static com.lyzb.jbx.fragment.me.card.CardInfoFragment.CUSTOM_CODE;
import static com.lyzb.jbx.fragment.me.card.UserInfoFragment.RESULT_ADDRESS_CODE;
import static com.szy.yishopcustomer.Util.LubanImg.getPath;

/**
 * @author wyx  tyk
 * @role 名片-企业
 * @time 2019 2019/3/4 14:40
 */

public class CardCompanyFragment extends BaseVideoFrgament<AddComdPresenter> implements IAddComdView, View.OnClickListener {

    public static final String NULL_IMG = "default.jpg";
    public static final int INFO_CODE = 1011;//公司简介
    public static final int HONOR_CODE = 1022;//企业/文化/团队
    public static final int BRAND_CODE = 1033;//品牌展示
    public static final int VIDEO_CODE = 1053;//视频
    @BindView(R.id.lin_chanage_comd)
    LinearLayout chanageComdLin;
    @BindView(R.id.ll_info)
    LinearLayout ll_info;
    @BindView(R.id.btn_save)
    TextView btn_save;
    @BindView(R.id.ll_honor)
    LinearLayout ll_honor;
    @BindView(R.id.ll_brand)
    LinearLayout ll_brand;
    @BindView(R.id.ll_comd_phone)
    LinearLayout ll_comd_phone;
    @BindView(R.id.ll_comd_address)
    LinearLayout ll_comd_address;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.fl_video)
    LinearLayout fl_video;


    @BindView(R.id.tv_chanage_comd)
    TextView chanageComd;

    @BindView(R.id.tv_text_company_ppzs)
    TextView tv_text_company_ppzs;
    @BindView(R.id.tv_img_company_ppzs)
    TextView tv_img_company_ppzs;
    @BindView(R.id.tv_img_company_info)
    TextView tv_img_company_info;
    @BindView(R.id.tv_text_company_info)
    TextView tv_text_company_info;
    @BindView(R.id.tv_text_company_honor)
    TextView tv_text_company_honor;
    @BindView(R.id.tv_img_company_honor)
    TextView tv_img_company_honor;

    @BindView(R.id.tv_comd_message)
    TextView tv_comd_message;

    @BindView(R.id.video_un_play)
    NiceVideoPlayer videoPlays;

    @BindView(R.id.img_card_com_video_empty)
    LinearLayout videoEmptyImg;
    @BindView(R.id.img_card_com_video_cancle)
    ImageView videoCancleImg;


    @BindView(R.id.img_card_com_img_empty)
    LinearLayout llinfoEmpty;
    @BindView(R.id.ll_add_info)
    LinearLayout ll_add_info;
    @BindView(R.id.recy_un_me_com_img)
    RecyclerView infoRecy;

    @BindView(R.id.img_card_com_honor_empty)
    LinearLayout llhonorEmpty;

    @BindView(R.id.ll_add_honor)
    LinearLayout ll_add_honor;
    @BindView(R.id.recy_un_com_honor_img)
    RecyclerView honrRecy;
    @BindView(R.id.rv_custom_model)
    RecyclerView rv_custom_model;


    @BindView(R.id.img_un_card_com_product)
    LinearLayout llbrandEmpty;
    @BindView(R.id.ll_add_brand)
    LinearLayout ll_add_brand;
    @BindView(R.id.recy_un_card_com_product)
    RecyclerView brandRecy;

    @BindView(R.id.tv_comd_moble)
    TextView mobleText;
    @BindView(R.id.tv_add_model)
    TextView tv_add_model;
    @BindView(R.id.edt_comd_address)
    TextView addressText;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;

    @BindView(R.id.ll_add_model)
    LinearLayout ll_add_model;

    @BindView(R.id.ll_linear)
    LinearLayout ll_linear;

    @BindView(R.id.ll_address)
    LinearLayout ll_address;

    @BindView(R.id.scorllview)
    NestedScrollView scorllview;

    @BindView(R.id.tv_card_comd_upd_vip)
    TextView openVipText;
    private CardFragment parentFragment = null;
    private CompanyWebFragment webFragment = null;
    private String comdId = null;

    private CardTextImgAdapter infoAdapter = null;
    private CardTextImgAdapter honrAdapter = null;
    private CardTextImgAdapter brandAdapter = null;

    private List<LocalMedia> videoList = new ArrayList<>();

    private ComdPhoneDialog mPheDialog = null;      //输入电话
    private ComdAdsDialog mAdsDialog = null;          //输入地址

    TextView tv_text_pop, tv_img_pop;
    private List<LocalMedia> list = new ArrayList<>();
    private List<FileBody> selectInfoList = new ArrayList<>();
    private List<FileBody> selectHonorList = new ArrayList<>();
    private List<FileBody> selectGoodList = new ArrayList<>();
    private List<FileBody> customList = new ArrayList<>();
    IntroductionContent videoContent = null;
    private boolean isScore = false;
    int popShow = -1;//-1  则是没点pop出来
    int popsort = -1;//-1 则是没点pop的位置
    boolean isUpdate = false;//是否是修改
    CusPopWindow popWindow;
    IntroductionContent clickModel; //当前点击的修改的model
    int customPosition = -1;
    int infoPosition = -1;
    int providePosition = -1;
    int needPosition = -1;
    int customContentPosition = -1;
    private int maxNum = 9; //最大9张图片
    CustomModelAdapter customModelAdapter = null;//自定义添加模块适配器
    private CustomModular customModel = null;
    CompanyModelModel companyModelModel;
    String address;
    boolean isCircleCompany = false;
    private boolean isCanEdit = false;
    private int typeCanEdit = -1;//从企业那边跳转到企业中传的是否可以编辑（非个人跳转到企业）  -1则是没传 0 是不可以编辑 1是可以编辑
    String moderId = "", videoModerId = "";


    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);
        if (getParentFragment() instanceof CardFragment) {
            parentFragment = (CardFragment) getParentFragment();
        } else if (getParentFragment() instanceof CompanyWebFragment) {
            webFragment = (CompanyWebFragment) getParentFragment();
        }
        setListener();
    }


    private void setListener() {
        ll_comd_phone.setOnClickListener(this);
        ll_comd_address.setOnClickListener(this);
        chanageComd.setOnClickListener(this);
        videoEmptyImg.setOnClickListener(this);
        videoCancleImg.setOnClickListener(this);
        openVipText.setOnClickListener(this);
        tv_img_company_honor.setOnClickListener(this);
        tv_text_company_honor.setOnClickListener(this);
        tv_text_company_info.setOnClickListener(this);
        tv_img_company_info.setOnClickListener(this);
        tv_img_company_ppzs.setOnClickListener(this);
        tv_text_company_ppzs.setOnClickListener(this);
        ll_add_brand.setOnClickListener(this);
        ll_add_honor.setOnClickListener(this);
        ll_add_info.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        tv_add_model.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        ll_add_info.setVisibility(View.GONE);
        ll_add_honor.setVisibility(View.GONE);
        ll_add_brand.setVisibility(View.GONE);

        //发布商品气泡提示 popupwindow
        if (popWindow == null) {
            View contenView = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_text_img, null, false);
            tv_text_pop = contenView.findViewById(R.id.tv_text);
            tv_img_pop = contenView.findViewById(R.id.tv_img);
            tv_text_pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popClick(false);
                    popWindow.dissmiss();
                }
            });
            tv_img_pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popClick(true);
                    popWindow.dissmiss();
                }
            });

            popWindow = new CusPopWindow.PopupWindowBuilder(getActivity())
                    .setView(contenView)
                    .setOutsideTouchable(true)
                    .create();
        }
        scorllview();
    }

    //在名片详情中的滑动冲突解决
    private void scorllview() {
        if (parentFragment == null) {
            return;
        }
        parentFragment.getAppBar().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //当顶部view滑到最底部时候 这时候不管下面view  是否为0 都得允许 上面appbar滑动
                if (parentFragment.getLayout_view().getHeight() == -verticalOffset) {
                    isScore = true;
                } else {
                    isScore = false;
                }
                LogUtil.loge("view的高度" + parentFragment.getLayout_view().getHeight() + "偏移高度" + verticalOffset);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scorllview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    LogUtil.loge(oldScrollY + "里面的高度" + scrollY);
                    if (scrollY <= 0) {//当下面的fragment拉到顶部的时候 才允许上层的 拉动 0为顶部
                        parentFragment.banAppBarScroll(true);
                    } else {
                        if (isScore) {
                            parentFragment.banAppBarScroll(true);
                        } else {
                            parentFragment.banAppBarScroll(false);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {


        //处理滑动事件冲突
        infoRecy.setNestedScrollingEnabled(false);
        honrRecy.setNestedScrollingEnabled(false);
        brandRecy.setNestedScrollingEnabled(false);
        rv_custom_model.setNestedScrollingEnabled(false);

        infoRecy.setFocusable(false);
        honrRecy.setFocusable(false);
        brandRecy.setFocusable(false);
        rv_custom_model.setFocusable(false);


        //简介信息
        infoAdapter = new CardTextImgAdapter();
        infoRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        infoRecy.setAdapter(infoAdapter);
//        infoRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        //个人信息图片点击
        infoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                infoPosition = position;
                //复位
                popsort = -1;
                popShow = -1;
                isUpdate = false;
                if (!TextUtils.isEmpty(infoAdapter.getId())) {
                    moderId = infoAdapter.getId();
                } else {
                    moderId = ((IntroductionContent) adapter.getData().get(position)).getModularId();
                }
                switch (view.getId()) {
                    case R.id.img_img:
                        if (isCanEdit) {//自己的 编辑
                            clickModel = (IntroductionContent) adapter.getData().get(position);
                            maxNum = 1;
                            isUpdate = true;
                            choosePicture(INFO_CODE, maxNum);
                        } else {//别人的放大
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < adapter.getData().size(); i++) {
                                IntroductionContent model = (IntroductionContent) adapter.getData().get(i);
                                urllist.add(model.getGraphContent());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getActivity(), position, urllist);
                        }
                        break;
                    case R.id.img_cancel:

                        AlertDialogFragment.newIntance()
                                .setTitle("删除内容")
                                .setContent("是否删除当前选择的内容？")
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModelContent(((IntroductionContent) adapter.getData().get(position)).getId(), INFO_CODE);
                                    }
                                })
                                .setCancleBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show(getFragmentManager(), "delelteModelContent");
                        break;

                    case R.id.rl_content:
                        if (!isCanEdit) {
                            return;
                        }
                        isUpdate = true;
                        clickModel = (IntroductionContent) adapter.getData().get(position);
                        String text = ((IntroductionContent) adapter.getData().get(position)).graphContent;
                        childStartForResult(HomeHtmlTextFragment.newIntance("企业简介", text), INFO_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(4, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(4, true, position);
                        break;
                    case R.id.ll_add:
                        if (!isCanEdit) {
                            return;
                        }
                        popShow = INFO_CODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;
                }
            }
        });

        //荣誉
        honrAdapter = new CardTextImgAdapter();
        honrRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        honrRecy.setAdapter(honrAdapter);
//        honrRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));
        //荣誉点击
        honrAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                providePosition = position;
                //复位
                popsort = -1;
                popShow = -1;
                isUpdate = false;
                if (!TextUtils.isEmpty(infoAdapter.getId())) {
                    moderId = infoAdapter.getId();
                } else {
                    moderId = ((IntroductionContent) adapter.getData().get(position)).getModularId();
                }
                switch (view.getId()) {
                    case R.id.img_img:
                        if (isCanEdit) {//自己的 编辑
                            clickModel = (IntroductionContent) adapter.getData().get(position);
                            maxNum = 1;
                            isUpdate = true;
                            choosePicture(HONOR_CODE, maxNum);
                        } else {//别人的放大
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < adapter.getData().size(); i++) {
                                IntroductionContent model = (IntroductionContent) adapter.getData().get(i);
                                urllist.add(model.getGraphContent());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getActivity(), position, urllist);
                        }
                        break;
                    case R.id.img_cancel:
                        AlertDialogFragment.newIntance()
                                .setTitle("删除内容")
                                .setContent("是否删除当前选择的内容？")
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModelContent(((IntroductionContent) adapter.getData().get(position)).getId(), HONOR_CODE);
                                    }
                                })
                                .setCancleBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show(getFragmentManager(), "delelteModelContent");
                        break;
                    case R.id.rl_content:
                        if (!isCanEdit) {
                            return;
                        }
                        isUpdate = true;
                        clickModel = (IntroductionContent) adapter.getData().get(position);
                        String text = ((IntroductionContent) adapter.getData().get(position)).graphContent;
                        childStartForResult(HomeHtmlTextFragment.newIntance("企业文化/荣誉/团队", text), HONOR_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(5, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(5, true, position);
                        break;
                    case R.id.ll_add:
                        if (!isCanEdit) {
                            return;
                        }
                        popShow = HONOR_CODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;
                }
            }
        });

        //品牌展示
        brandAdapter = new CardTextImgAdapter();
        brandRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        brandRecy.setAdapter(brandAdapter);
//        brandRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        //品牌点击
        brandAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                needPosition = position;
                //复位
                popsort = -1;
                popShow = -1;
                isUpdate = false;
                if (!TextUtils.isEmpty(infoAdapter.getId())) {
                    moderId = infoAdapter.getId();
                } else {
                    moderId = ((IntroductionContent) adapter.getData().get(position)).getModularId();
                }
                switch (view.getId()) {
                    case R.id.img_img:
                        if (isCanEdit) {//自己的 编辑
                            clickModel = (IntroductionContent) adapter.getData().get(position);
                            maxNum = 1;
                            isUpdate = true;
                            choosePicture(BRAND_CODE, maxNum);
                        } else {//别人的放大
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < adapter.getData().size(); i++) {
                                IntroductionContent model = (IntroductionContent) adapter.getData().get(i);
                                urllist.add(model.getGraphContent());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getActivity(), position, urllist);
                        }
                        break;
                    case R.id.img_cancel:
                        AlertDialogFragment.newIntance()
                                .setTitle("删除内容")
                                .setContent("是否删除当前选择的内容？")
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModelContent(((IntroductionContent) adapter.getData().get(position)).getId(), BRAND_CODE);
                                    }
                                })
                                .setCancleBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show(getFragmentManager(), "delelteModelContent");
                        break;
                    case R.id.rl_content:
                        if (!isCanEdit) {
                            return;
                        }
                        isUpdate = true;
                        clickModel = (IntroductionContent) adapter.getData().get(position);
                        String text = ((IntroductionContent) adapter.getData().get(position)).graphContent;
                        childStartForResult(HomeHtmlTextFragment.newIntance("品牌展示", text), BRAND_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(6, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(6, true, position);
                        break;
                    case R.id.ll_add:
                        if (!isCanEdit) {
                            return;
                        }
                        popShow = BRAND_CODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;
                }
            }
        });
        //自定义模块
        customModelAdapter = new CustomModelAdapter();

        rv_custom_model.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_custom_model.setAdapter(customModelAdapter);
        rv_custom_model.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        //自定义模板点击事件
        customModelAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final CustomModular modular = (CustomModular) adapter.getData().get(position);
                //复位
                isUpdate = false;
                popsort = -1;
                popShow = -1;
                customModel = modular;
                customPosition = position;
                switch (view.getId()) {
                    case R.id.ll_edit_custom://编辑模板  换名字
                        CreateModelDialog.newIntance()
                                .setTitle("修改模板")
                                .setContent(modular.modularName)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }).setCancleBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setClickListener(new CreateModelDialog.ClickListener() {
                            @Override
                            public void click(View view, String edtString) {
                                SaveCardModelBody body = new SaveCardModelBody();
                                body.setId(modular.getId());
                                body.setModularName(edtString);
                                body.setShowState(modular.getShowState());
                                body.setParagraphDTOList(modular.getParagraphVoList());
                                mPresenter.addModelContent(body, 2, false, -1);
                            }
                        }).show(getFragmentManager(), "updateModel");
                        break;
                    case R.id.ll_can_see://是否显示
                        SaveCardModelBody body = new SaveCardModelBody();
                        body.setId(modular.getId());
                        body.setModularName(modular.getModularName());
                        if (modular.getShowState() == 1) {
                            body.setShowState(0);
                        } else {
                            body.setShowState(1);
                        }
                        body.setParagraphDTOList(modular.getParagraphVoList());
                        mPresenter.addModelContent(body, 2, false, -1);
                        break;
                    case R.id.ll_delete://删除
                        AlertDialogFragment.newIntance()
                                .setContent("是否删除当前模板?")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModel(modular.getId());
                                    }
                                })
                                .show(getFragmentManager(), "deleteCustomModel");
                        break;
                    case R.id.tv_text://添加文字
                        if (!isCanEdit) {
                            return;
                        }
                        childStartForResult(HomeHtmlTextFragment.newIntance(modular.modularName, null), CUSTOM_CODE);
                        break;
                    case R.id.tv_img://添加图片
                        if (!isCanEdit) {
                            return;
                        }
                        maxNum = 9;
                        choosePicture(CUSTOM_CODE, maxNum);
                        break;
                    case R.id.ll_add_custom:
                        if (!isCanEdit) {
                            return;
                        }
                        popShow = CUSTOM_CODE;
                        popsort = customModelAdapter.getData().get(position).getParagraphVoList().size();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;
                }
            }
        });

        customModelAdapter.invoke(new CustomModelAdapter.ClickListener() {
            @Override
            public void click(@org.jetbrains.annotations.Nullable View v, @org.jetbrains.annotations.Nullable Integer modePosition, @org.jetbrains.annotations.Nullable Integer position) {
                //复位
                isUpdate = false;
                popsort = -1;
                popShow = -1;
                customPosition = modePosition;
                customContentPosition = position;
                CustomModular customModular = customModelAdapter.getItem(modePosition);
                customModel = customModular;
                clickModel = customModular.getParagraphVoList().get(position);
                switch (v.getId()) {
                    case R.id.img_img:
                        if (isCanEdit) {//自己的 编辑
                            maxNum = 1;
                            isUpdate = true;
                            choosePicture(CUSTOM_CODE, maxNum);
                        } else {//别人的放大
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < customModular.getParagraphVoList().size(); i++) {
                                IntroductionContent model = customModular.getParagraphVoList().get(i);
                                urllist.add(model.getGraphContent());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getActivity(), position, urllist);
                        }
                        break;
                    case R.id.img_cancel:
                        AlertDialogFragment.newIntance()
                                .setTitle("删除内容")
                                .setContent("是否删除当前选择的内容？")
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModelContent(clickModel.getId(), CUSTOM_CODE);
                                    }
                                })
                                .setCancleBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show(getFragmentManager(), "delelteModelContent");
                        break;

                    case R.id.rl_content:
                        if (!isCanEdit) {
                            return;
                        }
                        isUpdate = true;
                        String text = clickModel.getGraphContent();
                        childStartForResult(HomeHtmlTextFragment.newIntance(customModular.modularName, text), CUSTOM_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(0, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (!isCanEdit) {
                            return;
                        }
                        createSortRequest(0, true, position);
                        break;
                    case R.id.ll_add:
                        if (!isCanEdit) {
                            return;
                        }
                        popShow = CUSTOM_CODE;
                        popsort = customModelAdapter.getData().get(modePosition).getParagraphVoList().size();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;

                }

            }

        });

        if (parentFragment == null || parentFragment.mCardModel == null) {//我的企业面的官网
            if (TextUtils.isEmpty(comdId)) {
                showEmptyView();
            } else {
                mPresenter.getComdModelInfo(comdId);
            }
        } else {//名片里面的官网
            if (!TextUtils.isEmpty(parentFragment.mCardModel.getCurrentDepartmentID())) {
                comdId = parentFragment.mCardModel.getCurrentDepartmentID();
                mPresenter.getComdModelInfo(comdId);
            } else {
                showEmptyView();
            }
        }

    }


    /**
     * 请求网络
     *
     * @param companyId
     */
    public void onQuestCompany(String companyId, Boolean isCircleCompany) {
        this.isCircleCompany = isCircleCompany;
        if (!TextUtils.isEmpty(companyId)) {
            comdId = companyId;
            if (!isCircleCompany){//这里是做个判断  从个人名片中 点击企业名片 直接进入圈子中看官网（判断一下不请求数据，否则请求两次会导致图片显示出问题，具体怎么出现这种状况不清楚）！..！
                mPresenter.getComdModelInfo(companyId);
            }
        } else {
            showEmptyView();
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_card_com;
    }

    @Override
    public void getTypeList(List<BusinessModel> list) {

    }

    public void playVideo(String videoUrl) {
        videoPlays.setVisibility(View.VISIBLE);
        videoPlays.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        videoPlays.setUp(videoUrl, null);
        TxCustomVideoPlayerController controller = new TxCustomVideoPlayerController(getActivity());
        controller.setTitle("");

        Glide.with(getActivity())
                .setDefaultRequestOptions(new RequestOptions()
                        .frame(1)
                        .centerCrop()).load(videoUrl)
                .into(controller.imageView());

        videoPlays.setController(controller);

        controller.setDoVideoPlayer(new TxCustomVideoPlayerController.DoVideoPlayer() {
            @Override
            public void isPlay(boolean isplay, boolean isShow, boolean iscontroller) {
                if (isplay && !isShow) {
                    if (videoPlays.isIdle()) {
                        videoPlays.start();
                    } else {
                        if (videoPlays.isPlaying() || videoPlays.isBufferingPlaying()) {
                            videoPlays.pause();
                        } else if (videoPlays.isPaused() || videoPlays.isBufferingPaused()) {
                            videoPlays.restart();
                        }
                    }
                }
            }
        });

    }

    private void chooseVideo() {
        new ActionSheetDialog(getActivity())
                .builder()
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍摄", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        initCamera();
                    }
                })
                .addSheetItem("视频选择", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        initVideo();
                    }
                })
                .show();
    }

    @Override
    public void getData(ComdDetailModel model) {

    }

    @Override
    public void getModelData(CompanyModelModel model) {

        switch (typeCanEdit) {
            case -1://未传 该值  不是从企业跳转过来   个人跳转过来的
                if (isCircleCompany) {  //圈子挑过来 永远不能编辑
                    isCanEdit = false;
                } else { //不是圈子 也不是个人
                    isCanEdit = model.isCanEdit();
                }
                break;
            case 0://typeCanEdit  从企业跳转过来  不可编辑
                isCircleCompany = false;
                isCanEdit = false;
                break;
            case 1://typeCanEdit  从企业跳转过来  可编辑
                isCircleCompany = false;
                isCanEdit = true;
                break;
        }
        if (!model.getCode().equals("200") && !isCanEdit) {
            showEmptyView();
            return;
        }
        companyModelModel = model;


        infoAdapter.setCanEdit(isCanEdit);
        honrAdapter.setCanEdit(isCanEdit);
        brandAdapter.setCanEdit(isCanEdit);
        customModelAdapter.setCanEdit(isCanEdit);
        if (isCanEdit) {
            chanageComdLin.setVisibility(View.VISIBLE);
            videoCancleImg.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.VISIBLE);
            ll_add_model.setVisibility(View.VISIBLE);
        } else {
            videoCancleImg.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
            ll_add_model.setVisibility(View.GONE);
            chanageComdLin.setVisibility(View.GONE);
        }

        List<IntroductionContent> infoList = new ArrayList<>();
        List<IntroductionContent> honorList = new ArrayList<>();
        List<IntroductionContent> brandList = new ArrayList<>();
        List<IntroductionContent> videoList = new ArrayList<>();
        for (int i = 0; i < model.getInfoList().size(); i++) {
            switch (model.getInfoList().get(i).getDefualtModular()) {
                case "4":
                    infoList.add(model.getInfoList().get(i));
                    break;
                case "5":
                    honorList.add(model.getInfoList().get(i));
                    break;
                case "6":
                    brandList.add(model.getInfoList().get(i));
                    break;
                case "7"://宣传视频
                    videoList.add(model.getInfoList().get(i));
                    break;
            }
        }

        //空数据的时候拿到 固定模板ID
        for (int i = 0; i < model.getModularList().size(); i++) {
            switch (model.getModularList().get(i).getDefualtModular()) {
                case "7"://宣传视频
                    videoModerId = model.getModularList().get(i).getId();
                    break;
            }
        }

        //公司简介模块显示隐藏
        if (infoList.size() > 0) {//公司简介模有内容
            ll_info.setVisibility(View.VISIBLE);
            //公司简介模-已添加数据
            llinfoEmpty.setVisibility(View.GONE);
            if (isCanEdit) {
                if (isCircleCompany) {
                    ll_add_info.setVisibility(View.GONE);
                } else {
                    ll_add_info.setVisibility(View.VISIBLE);
                }
            } else {
                ll_add_info.setVisibility(View.GONE);
            }
            infoRecy.setVisibility(View.VISIBLE);

            infoAdapter.setNewData(infoList);
        } else {//公司简介模没内容
            if (isCanEdit) {//自己的
                ll_info.setVisibility(View.VISIBLE);
                llinfoEmpty.setVisibility(View.VISIBLE);
                ll_add_info.setVisibility(View.GONE);
            } else {
                ll_info.setVisibility(View.GONE);
            }
        }

        //公司荣誉模块显示隐藏
        if (honorList.size() > 0) {//我能提供有内容
            ll_honor.setVisibility(View.VISIBLE);
            //我能提供-已添加数据
            llhonorEmpty.setVisibility(View.GONE);
            if (isCanEdit) {
                if (isCircleCompany) {
                    ll_add_honor.setVisibility(View.GONE);
                } else {
                    ll_add_honor.setVisibility(View.VISIBLE);
                }
            } else {
                ll_add_honor.setVisibility(View.GONE);
            }
            honrRecy.setVisibility(View.VISIBLE);

            honrAdapter.setNewData(honorList);
        } else {//我能提供没内容
            if (isCanEdit) {//自己的
                ll_honor.setVisibility(View.VISIBLE);
                llhonorEmpty.setVisibility(View.VISIBLE);
                ll_add_honor.setVisibility(View.GONE);
            } else {
                ll_honor.setVisibility(View.GONE);
            }
        }

        //品牌展示模块显示隐藏
        if (brandList.size() > 0) {//我需要的有内容
            ll_brand.setVisibility(View.VISIBLE);
            //我需要的-已添加数据
            llbrandEmpty.setVisibility(View.GONE);
            if (isCanEdit) {
                if (isCircleCompany) {
                    ll_add_brand.setVisibility(View.GONE);
                } else {
                    ll_add_brand.setVisibility(View.VISIBLE);
                }
            } else {
                ll_add_brand.setVisibility(View.GONE);
            }
            brandRecy.setVisibility(View.VISIBLE);

            brandAdapter.setNewData(brandList);
        } else {//我需要的没内容
            if (isCanEdit) {//自己的
                ll_brand.setVisibility(View.VISIBLE);
                llbrandEmpty.setVisibility(View.VISIBLE);
                ll_add_brand.setVisibility(View.GONE);
            } else {
                ll_brand.setVisibility(View.GONE);
            }
        }

        //初始化赋值视频
        if (videoList.size() > 0) {
            videoContent = videoList.get(0);
            videoEmptyImg.setVisibility(View.GONE);
            playVideo(videoList.get(0).getGraphContent());
            videoCancleImg.setVisibility(isOwnAddManger() ? View.VISIBLE : View.GONE);
        } else {
            videoCancleImg.setVisibility(View.GONE);
            videoPlays.setVisibility(View.GONE);
            videoEmptyImg.setVisibility(isOwnAddManger() ? View.VISIBLE : View.GONE);
            fl_video.setVisibility(isOwnAddManger() ? View.VISIBLE : View.GONE);
        }


        ll_empty.setVisibility(View.GONE);
        //处理电话文本
        mobleText.setText(model.getPhone());
        if (isOwnAddManger()) {
            ll_comd_phone.setVisibility(View.VISIBLE);
        } else {
            ll_comd_phone.setVisibility(TextUtils.isEmpty(model.getPhone()) ? View.GONE : View.VISIBLE);
        }
        //处理地址文本
        addressText.setText(model.getAddress());
        if (isOwnAddManger()) {
            ll_comd_address.setVisibility(View.VISIBLE);
        } else {
            ll_comd_address.setVisibility(TextUtils.isEmpty(model.getAddress()) ? View.GONE : View.VISIBLE);
        }


        //自定义模块
        customModelAdapter.setNewData(model.getCustomModular());

    }

    @Override
    public void getModelDataFail() {
        showEmptyView();
    }

    @Override
    public void onFinshBack() {

    }


    private List<AddComdImgModel> deleteVideo = new ArrayList<>();


    private void initCamera() {
        PictureSelector.create(CardCompanyFragment.this)
                .openCamera(PictureMimeType.ofVideo())
                .previewImage(false)
                .compress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void initVideo() {
        PictureSelector.create(CardCompanyFragment.this)
                .openGallery(PictureMimeType.ofVideo())
                .imageSpanCount(3)
                .selectionMode(PictureConfig.SINGLE)
                .previewVideo(true)
                .enablePreviewAudio(true)
                .isCamera(false)
                .setOutputCameraPath("/CustomPath")
                .compress(true)// 是否压缩 true or false
                .selectionMedia(videoList)
                .minimumCompressSize(100)
                .synOrAsy(true)
                .videoQuality(0)
                .videoMaxSecond(150)
                .videoMinSecond(2)
                .recordVideoSecond(60)
                .forResult(PictureConfig.TYPE_VIDEO);
    }

    @Override
    public void onCardComd() {
        addressText.setText(address);
        deleteVideo.clear();
        mPresenter.getComdModelInfo(comdId);
    }

    @Override
    public void joinSuccess() {

    }

    @Override
    public void joinFail() {

    }

    @Override
    public void getInfo(List<FileBody> uplist) {
        selectInfoList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), INFO_CODE);
        } else {//增加内容
            createRequest(uplist, 4);
        }
    }

    @Override
    public void getHonor(List<FileBody> uplist) {
        selectHonorList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), HONOR_CODE);
        } else {//增加内容
            createRequest(uplist, 5);
        }
    }

    @Override
    public void getBrand(List<FileBody> uplist) {
        selectGoodList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), BRAND_CODE);
        } else {//增加内容
            createRequest(uplist, 6);
        }
    }

    @Override
    public void getCustom(List<FileBody> uplist) {
        customList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), CUSTOM_CODE);
        } else {//增加自定义模板内容
            createRequest(uplist, 0);
        }
    }


    /**
     * 修改内容请求
     */
    private void updateContent(String content, int type) {
        if (clickModel != null && !TextUtils.isEmpty(clickModel.getId())) {//修改内容
            UpdateModelBody body = new UpdateModelBody();
            body.setId(clickModel.getId());
            body.setBelongType(Integer.valueOf(clickModel.getBelongType()));
            body.setContentType(clickModel.getContentType());
            body.setGraphContent(content);
            body.setDefualtModular(Integer.valueOf(clickModel.getDefualtModular()));
            body.setSort(clickModel.getSort());
            body.setObjectId(clickModel.getObjectId());
            mPresenter.updateModelContent(body, type, content);
        }
    }


    @Override
    public void saveInfo(String string, int type, boolean isImg) {
        moderId = "";
        clickModel = null;
        CardModelMsg modelMsg = GSONUtil.getEntity(string, CardModelMsg.class);
        CustomModular customModular = modelMsg.getData();
        switch (type) {
            case 1://创建模板  刷新数据 临时刷新适配器
                if (modelMsg.getStatus().equals("200")) {
                    showToast("创建模块成功");
                    customModelAdapter.addData(customModular);
                    if (customModelAdapter.getData().size() < 5) {
                        ll_add_model.setVisibility(View.VISIBLE);
                    } else {
                        ll_add_model.setVisibility(View.GONE);
                    }
                } else {
                    showToast(modelMsg.getMsg());
                }
                break;
            case 2://修改自定义模板
                if (customModular.getParagraphVoList().size() == 0) {
                    customModular.setParagraphVoList(customModular.getParagraphDTOList());
                }
                customModelAdapter.getData().set(customPosition, customModular);
                customModelAdapter.replaceData(customModelAdapter.getData());
                break;
            case 3://三个固定模板添加内容成功
                showInfoView(customModular, isImg);
                break;
            case 4://自定义模板添加内容成功
                List<IntroductionContent> list = new ArrayList<>();
                if (customModular.getParagraphVoList().size() == 0) {
                    if (isUpdate || popShow != -1) {
                        customModular.setParagraphVoList(customModular.getParagraphDTOList());
                    } else {
                        list = customModelAdapter.getData().get(customPosition).getParagraphVoList();
                        list.addAll(customModular.getParagraphDTOList());
                        customModular.setParagraphVoList(sortList(list));
                    }
                }
                customModelAdapter.getData().set(customPosition, customModular);
                customModelAdapter.replaceData(customModelAdapter.getData());
                break;

        }
    }

    @Override
    public void deleteModelContent(String string, int type) {
        clickModel = null;
        switch (type) {
            case INFO_CODE:
                infoAdapter.remove(infoPosition);
                infoAdapter.notifyItemChanged(infoAdapter.getData().size() - 1);
                if (infoAdapter.getData().size() == 0) {
                    llinfoEmpty.setVisibility(View.VISIBLE);
                    ll_add_info.setVisibility(View.GONE);
                } else {
                    llinfoEmpty.setVisibility(View.GONE);
                    ll_add_info.setVisibility(View.VISIBLE);
                }
                break;
            case HONOR_CODE:
                honrAdapter.remove(providePosition);
                honrAdapter.notifyItemChanged(honrAdapter.getData().size() - 1);

                if (honrAdapter.getData().size() == 0) {
                    llhonorEmpty.setVisibility(View.VISIBLE);
                    ll_add_honor.setVisibility(View.GONE);
                } else {
                    llhonorEmpty.setVisibility(View.GONE);
                    ll_add_honor.setVisibility(View.VISIBLE);
                }
                break;
            case BRAND_CODE:
                brandAdapter.remove(needPosition);
                brandAdapter.notifyItemChanged(brandAdapter.getData().size() - 1);

                if (brandAdapter.getData().size() == 0) {
                    llbrandEmpty.setVisibility(View.VISIBLE);
                    ll_add_brand.setVisibility(View.GONE);
                } else {
                    llbrandEmpty.setVisibility(View.GONE);
                    ll_add_brand.setVisibility(View.VISIBLE);
                }
                break;
            case CUSTOM_CODE:
                List<IntroductionContent> list = customModelAdapter.getData().get(customPosition).getParagraphVoList();
                list.remove(customContentPosition);
                customModelAdapter.getData().get(customPosition).setParagraphVoList(list);
                customModelAdapter.notifyItemChanged(customPosition);
                break;
            case VIDEO_CODE:
                break;
        }
    }

    @Override
    public void deleteModel(String string) {
        clickModel = null;
        customModelAdapter.remove(customPosition);
        customModelAdapter.notifyDataSetChanged();
        if (customModelAdapter.getData().size() < 5) {
            ll_add_model.setVisibility(View.VISIBLE);
        } else {
            ll_add_model.setVisibility(View.GONE);
        }
    }

    @Override
    public void update(String string, int type, String content) {
        switch (type) {
            case INFO_CODE:
                infoAdapter.getData().get(infoPosition).setGraphContent(content);
                infoAdapter.replaceData(infoAdapter.getData());
                break;
            case HONOR_CODE:
                honrAdapter.getData().get(providePosition).setGraphContent(content);
                honrAdapter.replaceData(honrAdapter.getData());
                break;
            case BRAND_CODE:
                brandAdapter.getData().get(needPosition).setGraphContent(content);
                brandAdapter.replaceData(brandAdapter.getData());
                break;
            case CUSTOM_CODE:
                customModelAdapter.getData().get(customPosition).getParagraphVoList().get(customContentPosition).setGraphContent(content);
                customModelAdapter.replaceData(customModelAdapter.getData());
                break;
        }
        clickModel = null;
    }

    @Override
    public void getVideo(final String videoUrl) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoCancleImg.setVisibility(View.VISIBLE);
                                playVideo(videoUrl);
                                //提交视频信息到接口
                                createTextRequest(videoUrl, 7);

                            }
                        });
                    }
                }

        ).start();
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                //公司简介
                case INFO_CODE:
                    String textInfo = data.getString(KEY_DATA);
                    llinfoEmpty.setVisibility(View.GONE);
                    infoRecy.setVisibility(View.VISIBLE);
                    if (isUpdate) {
                        updateContent(textInfo, INFO_CODE);
                    } else {
                        createTextRequest(textInfo, 4);
                    }
                    break;
                //公司荣誉
                case HONOR_CODE:
                    String textProvide = data.getString(KEY_DATA);
                    llhonorEmpty.setVisibility(View.GONE);
                    honrRecy.setVisibility(View.VISIBLE);
                    if (isUpdate) {
                        updateContent(textProvide, HONOR_CODE);
                    } else {
                        createTextRequest(textProvide, 5);
                    }
                    break;
                //公司品牌
                case BRAND_CODE:
                    String textNeed = data.getString(KEY_DATA);
                    llbrandEmpty.setVisibility(View.GONE);
                    brandRecy.setVisibility(View.VISIBLE);
                    if (isUpdate) {
                        updateContent(textNeed, BRAND_CODE);
                    } else {
                        createTextRequest(textNeed, 6);
                    }
                    break;
                //自定义模板的文字
                case CUSTOM_CODE:
                    String textCustom = data.getString(KEY_DATA);
                    if (isUpdate) {
                        updateContent(textCustom, CUSTOM_CODE);
                    } else {
                        createTextRequest(textCustom, 0);
                    }
                    break;
                case 1066:
//                    //切换企业后的刷新
//                    mPresenter.getComdModelInfo(data.getString("ComdId"));
                    break;
                case RESULT_ADDRESS_CODE:

                    LocationAddressInfo addressInfo = (LocationAddressInfo) data.getSerializable(KEY_ADDRESS);
                    CardComdModel inModel = new CardComdModel();
                    CardComdModel.GsDistributorVoBean bean = new CardComdModel.GsDistributorVoBean();
                    address = addressInfo.getText();
                    bean.setDisAddress(addressInfo.getText());
                    bean.setId(comdId);
                    //经纬度
                    bean.setMapLat(addressInfo.lat.toString());
                    bean.setMapLng(addressInfo.lon.toString());

                    inModel.setDiff("card");
                    inModel.setOptType("upt");
                    inModel.setGsDistributorVo(bean);

                    mPresenter.addComdInfo(true, inModel);
                    break;
                default:

                    break;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMsg messageEvent) {
        mPresenter.getComdModelInfo(messageEvent.getCompanyId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //编辑电话
            case R.id.ll_comd_phone:
                if (!isCanEdit) {
                    return;
                }
                if (mPheDialog == null) {
                    mPheDialog = new ComdPhoneDialog();
                }
                mPheDialog.setText(mobleText.getText().toString());
                mPheDialog.show(getFragmentManager(), "EditPhone");
                mPheDialog.setListener(new ComdPhoneDialog.ClickListener() {
                    @Override
                    public void onYes(String text) {
                        mobleText.setText(text);
                        mPheDialog.dismiss();

                        CardComdModel inModel = new CardComdModel();
                        CardComdModel.GsDistributorVoBean bean = new CardComdModel.GsDistributorVoBean();

                        bean.setDisTel(text);
                        bean.setId(comdId);

                        inModel.setDiff("card");
                        inModel.setOptType("upt");
                        inModel.setGsDistributorVo(bean);
                        mPresenter.addComdInfo(true, inModel);
                    }
                });
                break;
            //编辑地址
            case R.id.ll_comd_address:
//
//                if (mAdsDialog == null) {
//                    mAdsDialog = new ComdAdsDialog();
//                }
//                mAdsDialog.setText(addressText.getText().toString());
//                mAdsDialog.show(getFragmentManager(), "EditAddress");
//                mAdsDialog.setListener(new ComdAdsDialog.ClickListener() {
//                    @Override
//                    public void onYes(String text) {
//                        addressText.setText(text);
//                        mAdsDialog.dismiss();
//
//                        CardComdModel inModel = new CardComdModel();
//                        CardComdModel.GsDistributorVoBean bean = new CardComdModel.GsDistributorVoBean();
//
//                        bean.setDisAddress(text);
//                        bean.setId(comdId);
//                        //经纬度
//                        bean.setMapLat("");
//                        bean.setMapLng("");
//
//                        inModel.setDiff("card");
//                        inModel.setOptType("upt");
//                        inModel.setGsDistributorVo(bean);
//
//                        mPresenter.addComdInfo(true, inModel);
//                    }
//                });

                break;
            //切换企业
            case R.id.tv_chanage_comd:
                if (!isCanEdit) {
                    return;
                }
//                childStartForResult(new MyCompanyFragment(), 1066);
                childStart(new MyCompanyFragment());
                break;
            //编辑视频
            case R.id.img_card_com_video_empty:
                if (!isCanEdit) {
                    return;
                }
                chooseVideo();
                break;
            //播放
            case R.id.video_un_play:
                if (videoPlays != null && !videoPlays.isPlaying()) {
                    videoPlays.start();
                }
                break;
            //删除视频
            case R.id.img_card_com_video_cancle:
                if (!isCanEdit) {
                    return;
                }
                AlertDialogFragment.newIntance()
                        .setKeyBackable(false)
                        .setCancleable(false)
                        .setContent("是否删除该视频?")
                        .setCancleBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                videoCancleImg.setVisibility(View.GONE);
                                videoPlays.setVisibility(View.GONE);

//                                videoEmptyImg.setScaleType(ImageView.ScaleType.CENTER);
//                                videoEmptyImg.setImageResource(R.mipmap.card_com_video);
                                videoEmptyImg.setVisibility(View.VISIBLE);

                                if (!TextUtils.isEmpty(videoContent.getId())) {
                                    mPresenter.delereModelContent(videoContent.getId(), VIDEO_CODE);
                                } else {
                                    showToast("视频为空");
                                }
                            }
                        })
                        .show(getFragmentManager(), "DeleteVideo");
                break;
            //升级智能名片
            case R.id.tv_card_comd_upd_vip:
                Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_OPEN_VIP);
                startActivity(tgIntent);
                break;
            //编辑公司简介
            case R.id.tv_text_company_info:
                if (!isCanEdit) {
                    return;
                }
                for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                    if (companyModelModel.getModularList().get(i).getDefualtModular().equals("4")) {
                        moderId = companyModelModel.getModularList().get(i).getId();
                    }
                }
                childStartForResult(HomeHtmlTextFragment.newIntance("企业简介", null), INFO_CODE);
                break;
            case R.id.tv_img_company_info:
                if (!isCanEdit) {
                    return;
                }
                for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                    if (companyModelModel.getModularList().get(i).getDefualtModular().equals("4")) {
                        moderId = companyModelModel.getModularList().get(i).getId();
                    }
                }
                maxNum = 9;
                choosePicture(INFO_CODE, maxNum);
                break;
            //编辑企业/文化/团队
            case R.id.tv_text_company_honor:
                if (!isCanEdit) {
                    return;
                }
                for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                    if (companyModelModel.getModularList().get(i).getDefualtModular().equals("5")) {
                        moderId = companyModelModel.getModularList().get(i).getId();
                    }
                }

                childStartForResult(HomeHtmlTextFragment.newIntance("企业文化/荣誉/团队", null), HONOR_CODE);
                break;
            case R.id.tv_img_company_honor:
                if (!isCanEdit) {
                    return;
                }
                for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                    if (companyModelModel.getModularList().get(i).getDefualtModular().equals("5")) {
                        moderId = companyModelModel.getModularList().get(i).getId();
                    }
                }
                maxNum = 9;
                choosePicture(HONOR_CODE, maxNum);
                break;
            //品牌展示
            case R.id.tv_text_company_ppzs:
                if (!isCanEdit) {
                    return;
                }
                for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                    if (companyModelModel.getModularList().get(i).getDefualtModular().equals("6")) {
                        moderId = companyModelModel.getModularList().get(i).getId();
                    }
                }

                childStartForResult(HomeHtmlTextFragment.newIntance("品牌展示", null), BRAND_CODE);
                break;
            case R.id.tv_img_company_ppzs:
                if (!isCanEdit) {
                    return;
                }
                for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                    if (companyModelModel.getModularList().get(i).getDefualtModular().equals("6")) {
                        moderId = companyModelModel.getModularList().get(i).getId();
                    }
                }
                maxNum = 9;
                choosePicture(BRAND_CODE, maxNum);
                break;

            case R.id.ll_add_info:
                if (!isCanEdit) {
                    return;
                }
                if (!TextUtils.isEmpty(infoAdapter.getId())) {
                    moderId = infoAdapter.getId();
                } else {
                    if (!TextUtils.isEmpty(infoAdapter.getData().get(0).getModularId())) {
                        moderId = infoAdapter.getData().get(0).getModularId();
                    } else {
                        for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                            if (companyModelModel.getModularList().get(i).getDefualtModular().equals("4")) {
                                moderId = companyModelModel.getModularList().get(i).getId();
                            }
                        }
                    }
                }
                popShow = INFO_CODE;
                infoPosition = -1;
                popsort = infoAdapter.getData().size();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                }
                break;
            case R.id.ll_add_honor:
                if (!isCanEdit) {
                    return;
                }
                if (!TextUtils.isEmpty(honrAdapter.getId())) {
                    moderId = honrAdapter.getId();
                } else {
                    if (!TextUtils.isEmpty(honrAdapter.getData().get(0).getModularId())) {
                        moderId = honrAdapter.getData().get(0).getModularId();
                    } else {
                        for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                            if (companyModelModel.getModularList().get(i).getDefualtModular().equals("5")) {
                                moderId = companyModelModel.getModularList().get(i).getId();
                            }
                        }
                    }
                }
                popShow = HONOR_CODE;
                providePosition = -1;
                popsort = honrAdapter.getData().size();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                }
                break;
            case R.id.ll_add_brand:
                if (!isCanEdit) {
                    return;
                }
                if (!TextUtils.isEmpty(brandAdapter.getId())) {
                    moderId = brandAdapter.getId();
                } else {
                    if (!TextUtils.isEmpty(brandAdapter.getData().get(0).getModularId())) {
                        moderId = brandAdapter.getData().get(0).getModularId();
                    } else {
                        for (int i = 0; i < companyModelModel.getModularList().size(); i++) {
                            if (companyModelModel.getModularList().get(i).getDefualtModular().equals("6")) {
                                moderId = companyModelModel.getModularList().get(i).getId();
                            }
                        }
                    }
                }
                popShow = BRAND_CODE;
                needPosition = -1;
                popsort = brandAdapter.getData().size();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                }
                break;
            case R.id.tv_add_model:
                if (!isCanEdit) {
                    return;
                }
                CreateModelDialog.newIntance()
                        .setTitle("创建模板")
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).setCancleBtn(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setClickListener(new CreateModelDialog.ClickListener() {
                    @Override
                    public void click(View view, String edtString) {
                        SaveCardModelBody model = new SaveCardModelBody();
                        model.setModularName(edtString);
                        model.setObjectId(comdId);
                        model.setBelongType(2);
                        mPresenter.addModelContent(model, 1, false, -1);
                    }
                }).show(getFragmentManager(), "createModel");
                break;

            case R.id.ll_phone://打电话
                //拨打电话
                if (!TextUtils.isEmpty(mobleText.getText().toString().trim())) {
                    AppUtil.openDial(getActivity(), mobleText.getText().toString().trim());
                } else {
                    showToast("未设置电话号码,不可以拨打");
                }
                break;
            case R.id.ll_address:  //编辑地址
                if (isOwnAddManger()) {//是自己的企业 并且自己是管理员才能点击定位重新设置位置
                    if (companyModelModel.isEditSt() == 1) {//普通企业才能编辑地址 团购企业不允许
                        childStartForResult(new AddressFragment(), RESULT_ADDRESS_CODE);
                    }
                } else {
                    if (getParentFragment() instanceof CircleDetailFragment || getParentFragment() instanceof CardFragment || getParentFragment() instanceof CompanyWebFragment) {
                        childStart(AmapFragment.Companion.newIncetance(addressText.getText().toString(), Double.valueOf(companyModelModel.getMapLat()), Double.valueOf(companyModelModel.getMapLng())));
                    } else {
                        start(AmapFragment.Companion.newIncetance(addressText.getText().toString(), Double.valueOf(companyModelModel.getMapLat()), Double.valueOf(companyModelModel.getMapLng())));
                    }
                }
                break;
            case R.id.btn_save:
                LogUtil.loge("你当前点击了pop按钮");
                if (parentFragment != null) {
                    parentFragment.pop();
                } else {
                    webFragment.pop();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }


    /**
     * 只要吊用了该方法
     *
     * @param isEdit 从企业那边跳转到企业中传的是否可以编辑（非个人跳转到企业）  -1则是没传 0 是不可以编辑 1是可以编辑
     */
    public void setIsEdit(String companyId, int isEdit) {
        typeCanEdit = isEdit;
        if (!TextUtils.isEmpty(companyId)) {
            comdId = companyId;
            mPresenter.getComdModelInfo(companyId);
        } else {
            showEmptyView();
        }
    }

    /**
     * 是否是自己并且是管理员
     *
     * @return
     */
    private boolean isOwnAddManger() {

        return isCanEdit;
    }

    /**
     * 显示空布局
     */
    private void showEmptyView() {
        videoCancleImg.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        ll_add_model.setVisibility(View.GONE);
        ll_add_info.setVisibility(View.GONE);
        ll_add_honor.setVisibility(View.GONE);
        ll_add_brand.setVisibility(View.GONE);
//        if (!isCanEdit) {//别人的才显示空布局
        chanageComdLin.setVisibility(View.GONE);
        fl_video.setVisibility(View.GONE);
        ll_info.setVisibility(View.GONE);
        ll_honor.setVisibility(View.GONE);
        ll_brand.setVisibility(View.GONE);
        ll_comd_phone.setVisibility(View.GONE);
        ll_comd_address.setVisibility(View.GONE);
        ll_empty.setVisibility(View.VISIBLE);
//        }
    }


    /**
     * 图片选择器
     *
     * @param code
     */
    private void choosePicture(int code, int max) {
        int Num = 9;
        if (max == 9) {
            switch (code) {
                case INFO_CODE:
                    if (infoAdapter.getData().size() > 0) {
                        int picNum = 0;
                        for (int i = 0; i < infoAdapter.getData().size(); i++) {
                            if (infoAdapter.getData().get(i).contentType.equals("2")) {
                                picNum++;
                            }
                        }
                        Num = max - picNum;
                    } else {
                        Num = max;
                    }
                    break;
                case HONOR_CODE:
                    if (honrAdapter.getData().size() > 0) {
                        int picNum = 0;
                        for (int i = 0; i < honrAdapter.getData().size(); i++) {
                            if (honrAdapter.getData().get(i).contentType.equals("2")) {
                                picNum++;
                            }
                        }
                        Num = max - picNum;
                    } else {
                        Num = max;
                    }
                    break;
                case BRAND_CODE:
                    if (brandAdapter.getData().size() > 0) {
                        int picNum = 0;
                        for (int i = 0; i < brandAdapter.getData().size(); i++) {
                            if (brandAdapter.getData().get(i).contentType.equals("2")) {
                                picNum++;
                            }
                        }
                        Num = max - picNum;
                    } else {
                        Num = max;
                    }
                    break;
                case CUSTOM_CODE:
                    if (customModelAdapter.getData().get(customPosition).getParagraphVoList().size() > 0) {
                        int picNum = 0;
                        for (int i = 0; i < customModelAdapter.getData().get(customPosition).getParagraphVoList().size(); i++) {
                            if (customModelAdapter.getData().get(customPosition).getParagraphVoList().get(i).contentType.equals("2")) {
                                picNum++;
                            }
                        }
                        Num = max - picNum;
                    } else {
                        Num = max;
                    }
                    break;

            }
        } else {
            Num = max;
        }

        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(Num)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
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


    /**
     * 针对保存个人信息接口
     * 创建请求数据  图片
     *
     * @param type 默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示 ）
     */
    private void createRequest(final List<FileBody> uplist, final int type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SaveCardModelBody model = new SaveCardModelBody();
                model.setBelongType(2);
                model.setObjectId(comdId);
                List<IntroductionContent> lists = new ArrayList<>();
                for (int i = 0; i < uplist.size(); i++) {
                    IntroductionContent content = new IntroductionContent();
                    content.setContentType("2");
                    content.setGraphContent(uplist.get(i).getFile());
                    content.setObjectId(comdId);
                    content.setSort(i);
                    content.setBelongType("2");
                    content.setDefualtModular(type + "");
                    lists.add(content);
                }
                List<IntroductionContent> endlist = new ArrayList<>();
                switch (type) {
                    case 4:
                        model.setModularName("公司简介");
                        model.setSort(1);
                        model.setId(moderId);
                        endlist = infoAdapter.getData();
                        if (endlist.size() == 0) {
                            endlist = lists;
                        } else {
                            if (popShow != -1 || popsort != -1) {//插入东西
                                endlist.addAll(popsort, lists);
                            } else {
                                endlist.clear();
                                endlist = lists;
                            }
                        }
                        break;
                    case 5:
                        model.setModularName("企业文化/荣誉/团队");
                        model.setSort(2);
                        model.setId(moderId);
                        endlist = honrAdapter.getData();
                        if (endlist.size() == 0) {
                            endlist = lists;
                        } else {
                            if (popShow != -1 || popsort != -1) {//插入东西
                                endlist.addAll(popsort, lists);
                            } else {
                                endlist.clear();
                                endlist = lists;
                            }
                        }
                        break;
                    case 6:
                        model.setModularName("品牌展示");
                        model.setSort(3);
                        model.setId(moderId);
                        endlist = brandAdapter.getData();
                        if (endlist.size() == 0) {
                            endlist = lists;
                        } else {
                            if (popShow != -1 || popsort != -1) {//插入东西
                                endlist.addAll(popsort, lists);
                            } else {
                                endlist.clear();
                                endlist = lists;
                            }
                        }
                        break;

                    case 0:
                        model.setModularName((customModelAdapter.getData().get(customPosition)).modularName);
                        model.setSort((customModelAdapter.getData().get(customPosition)).getSort());
                        model.setId((customModelAdapter.getData().get(customPosition)).getId());
                        endlist = (customModelAdapter.getData().get(customPosition)).getParagraphVoList();
                        if (endlist.size() == 0) {
                            endlist = lists;
                        } else {
                            if (popShow != -1 || popsort != -1) {//插入东西
                                endlist.addAll(popsort, lists);
                            } else {
                                endlist.clear();
                                endlist = lists;
                            }
                        }
                        break;
                }
                for (int i = 0; i < endlist.size(); i++) {
                    endlist.get(i).setSort(i);
                }
                model.setParagraphDTOList(endlist);
                if (type == 0) {
                    mPresenter.addModelContent(model, 4, true, -1);
                } else {
                    mPresenter.addModelContent(model, 3, true, type);
                }
            }
        });

    }


    /**
     * 调换位置
     * 创建请求数据  换位置
     *
     * @param type 默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示 ）
     */
    private void createSortRequest(int type, boolean isTop, int position) {
        SaveCardModelBody model = new SaveCardModelBody();
        model.setBelongType(2);
        model.setObjectId(comdId);
        List<IntroductionContent> endlist = new ArrayList<>();
        switch (type) {
            case 4:
                model.setModularName("公司简介");
                model.setSort(1);
                model.setId(moderId);
                endlist = infoAdapter.getData();
                if (isTop) {
                    if (position > 0) {
                        Collections.swap(endlist, position, position - 1);
                    } else {
                        showToast("当前已经是最顶部了");
                        return;
                    }
                } else {
                    if (position < endlist.size() - 1) {
                        Collections.swap(endlist, position, position + 1);
                    } else {
                        showToast("当前已经是最底部了");
                        return;
                    }
                }
                break;
            case 5:
                model.setModularName("企业文化/荣誉/团队");
                model.setSort(2);
                model.setId(moderId);
                endlist = honrAdapter.getData();
                if (isTop) {
                    if (position > 0) {
                        Collections.swap(endlist, position, position - 1);
                    } else {
                        showToast("当前已经是最顶部了");
                        return;
                    }
                } else {
                    if (position < endlist.size() - 1) {
                        Collections.swap(endlist, position, position + 1);
                    } else {
                        showToast("当前已经是最底部了");
                        return;
                    }
                }
                break;
            case 6:
                model.setModularName("品牌展示");
                model.setSort(3);
                model.setId(moderId);
                endlist = brandAdapter.getData();
                if (isTop) {
                    if (position > 0) {
                        Collections.swap(endlist, position, position - 1);
                    } else {
                        showToast("当前已经是最顶部了");
                        return;
                    }
                } else {
                    if (position < endlist.size() - 1) {
                        Collections.swap(endlist, position, position + 1);
                    } else {
                        showToast("当前已经是最底部了");
                        return;
                    }
                }
                break;
            case 0:
                model.setModularName((customModelAdapter.getData().get(customPosition)).modularName);
                model.setSort((customModelAdapter.getData().get(customPosition)).getSort());
                model.setId((customModelAdapter.getData().get(customPosition)).getId());
                endlist = (customModelAdapter.getData().get(customPosition)).getParagraphVoList();
                if (isTop) {
                    if (position > 0) {
                        Collections.swap(endlist, position, position - 1);
                    } else {
                        showToast("当前已经是最顶部了");
                        return;
                    }
                } else {
                    if (position < endlist.size() - 1) {
                        Collections.swap(endlist, position, position + 1);
                    } else {
                        showToast("当前已经是最底部了");
                        return;
                    }
                }
                break;
        }
        for (int i = 0; i < endlist.size(); i++) {
            endlist.get(i).setSort(i);
        }
        model.setParagraphDTOList(endlist);
        isUpdate = true;
        popShow = -1;
        if (type == 0) {
            mPresenter.addModelContent(model, 4, true, -1);
        } else {
            mPresenter.addModelContent(model, 3, true, type);
        }
    }

    /**
     * 针对保存个人信息接口
     * 创建请求数据 文字
     *
     * @param type 默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示 7视频 ）
     */
    private void createTextRequest(String text, int type) {
        SaveCardModelBody model = new SaveCardModelBody();
        model.setBelongType(2);
        model.setObjectId(comdId);
        List<IntroductionContent> lists = new ArrayList<>();
        IntroductionContent content = new IntroductionContent();
        if (type==7){
            content.setContentType("3");
        }else {
            content.setContentType("1");
        }
        content.setGraphContent(text);
        if (popsort != -1) {
            content.setSort(popsort);
        }
        content.setObjectId(comdId);
        content.setBelongType("2");
        content.setDefualtModular(type + "");
        lists.add(content);
        List<IntroductionContent> endlist = new ArrayList<>();
        switch (type) {
            case 4:
                model.setModularName("公司简介");
                model.setSort(1);
                model.setId(moderId);
                endlist = infoAdapter.getData();
                if (endlist.size() == 0) {
                    endlist = lists;
                } else {
                    if (popShow != -1 || popsort != -1) {//插入东西
                        endlist.addAll(popsort, lists);
                    } else {
                        endlist.clear();
                        endlist = lists;
                    }
                }
                break;
            case 5:
                model.setModularName("企业文化/荣誉/团队");
                model.setSort(2);
                model.setId(moderId);
                endlist = honrAdapter.getData();
                if (endlist.size() == 0) {
                    endlist = lists;
                } else {
                    if (popShow != -1 || popsort != -1) {//插入东西
                        endlist.addAll(popsort, lists);
                    } else {
                        endlist.clear();
                        endlist = lists;
                    }
                }
                break;
            case 6:
                model.setModularName("品牌展示");
                model.setSort(3);
                model.setId(moderId);
                endlist = brandAdapter.getData();
                if (endlist.size() == 0) {
                    endlist = lists;
                } else {
                    if (popShow != -1 || popsort != -1) {//插入东西
                        endlist.addAll(popsort, lists);
                    } else {
                        endlist.clear();
                        endlist = lists;
                    }
                }
                break;
            case 7://视频
                model.setModularName("宣传视频");
                model.setSort(0);
                model.setId(videoModerId);
                endlist.clear();
                endlist = lists;
                break;
            case 0:
                model.setModularName((customModelAdapter.getData().get(customPosition)).modularName);
                model.setSort((customModelAdapter.getData().get(customPosition)).getSort());
                model.setId((customModelAdapter.getData().get(customPosition)).getId());
                endlist = (customModelAdapter.getData().get(customPosition)).getParagraphVoList();
                if (endlist.size() == 0) {
                    endlist = lists;
                } else {
                    if (popShow != -1 || popsort != -1) {//插入东西
                        endlist.addAll(popsort, lists);
                    } else {
                        endlist.clear();
                        endlist = lists;
                    }
                }
                break;
        }
        for (int i = 0; i < endlist.size(); i++) {
            endlist.get(i).setSort(i);
        }
        model.setParagraphDTOList(endlist);
        if (type == 0) {
            mPresenter.addModelContent(model, 4, false, -1);
        } else {
            mPresenter.addModelContent(model, 3, false, type);
        }
    }


    /**
     * 显示view
     *
     * @param data  数据源
     * @param isImg true  图片  false  文字
     */
    private void showInfoView(CustomModular data, boolean isImg) {
        if (data == null || data.getParagraphDTOList().size() == 0) {
            return;
        }
        switch (data.getDefualtModular()) {
            case "4"://公司简介
                if (data.getParagraphDTOList().size() > 0) {
                    llinfoEmpty.setVisibility(View.GONE);
                    ll_add_info.setVisibility(View.VISIBLE);
                    infoRecy.setVisibility(View.VISIBLE);
                } else {
                    llinfoEmpty.setVisibility(View.VISIBLE);
                    ll_add_info.setVisibility(View.GONE);
                    infoRecy.setVisibility(View.VISIBLE);
                }
                infoAdapter.setId(data.getId());
                if (isUpdate || popShow != -1) {
                    infoAdapter.setNewData(sortList(data.getParagraphDTOList()));
                } else {
                    List<IntroductionContent> list = infoAdapter.getData();
                    list.addAll(data.getParagraphDTOList());
                    infoAdapter.setNewData(sortList(list));
                }
                break;
            case "5"://企业文化/荣誉/团队
                if (data.getParagraphDTOList().size() > 0) {
                    llhonorEmpty.setVisibility(View.GONE);
                    ll_add_honor.setVisibility(View.VISIBLE);
                    honrRecy.setVisibility(View.VISIBLE);
                } else {
                    llhonorEmpty.setVisibility(View.VISIBLE);
                    ll_add_honor.setVisibility(View.GONE);
                    honrRecy.setVisibility(View.VISIBLE);
                }
                honrAdapter.setId(data.getId());
                if (isUpdate || popShow != -1) {
                    honrAdapter.setNewData(data.getParagraphDTOList());
                } else {
                    List<IntroductionContent> list = honrAdapter.getData();
                    list.addAll(data.getParagraphDTOList());
                    honrAdapter.setNewData(sortList(list));
                }
                break;

            case "6"://品牌展示
                if (data.getParagraphDTOList().size() > 0) {
                    llbrandEmpty.setVisibility(View.GONE);
                    ll_add_brand.setVisibility(View.VISIBLE);
                    brandRecy.setVisibility(View.VISIBLE);
                } else {
                    llbrandEmpty.setVisibility(View.VISIBLE);
                    ll_add_brand.setVisibility(View.GONE);
                    brandRecy.setVisibility(View.VISIBLE);
                }
                brandAdapter.setId(data.getId());
                if (isUpdate || popShow != -1) {
                    brandAdapter.setNewData(data.getParagraphDTOList());
                } else {
                    List<IntroductionContent> list = brandAdapter.getData();
                    list.addAll(data.getParagraphDTOList());
                    brandAdapter.setNewData(sortList(list));
                }
                break;
            case "7"://宣传视频
                List<IntroductionContent> videoList = new ArrayList<>();
                for (int i = 0; i < data.getParagraphDTOList().size(); i++) {
                    switch (data.getParagraphDTOList().get(i).getDefualtModular()) {
                        case "7"://宣传视频
                            videoList.add(data.getParagraphDTOList().get(i));
                            break;
                    }
                }
                //初始化赋值视频
                if (videoList.size() > 0) {
                    videoContent = videoList.get(0);
                    videoEmptyImg.setVisibility(View.GONE);
                    playVideo(videoList.get(0).getGraphContent());
                    videoCancleImg.setVisibility(isOwnAddManger() ? View.VISIBLE : View.GONE);
                } else {
                    videoCancleImg.setVisibility(View.GONE);
                    videoPlays.setVisibility(View.GONE);
                    videoEmptyImg.setVisibility(isOwnAddManger() ? View.VISIBLE : View.GONE);
                    fl_video.setVisibility(isOwnAddManger() ? View.VISIBLE : View.GONE);
                }
                break;
        }
    }

    /**
     * 将list 根据sort字段来排序
     *
     * @param list
     */
    private List<IntroductionContent> sortList(List<IntroductionContent> list) {
        List<IntroductionContent> mlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == list.get(j).getSort()) {
                    mlist.add(list.get(j));
                }
            }
        }
        return mlist;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            switch (requestCode) {
                case PictureConfig.TYPE_VIDEO:
                    //视频选择回调
                    mPresenter.upLoadFiles(selectList.get(0).getPath());
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    //视频拍摄
                    mPresenter.upLoadFiles(selectList.get(0).getPath());
                    break;
                case INFO_CODE:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    for (int i = 0; i < selectList.size(); i++) {
                        FileBody body = new FileBody();
                        if (selectList.get(i).isCompressed()) {
                            body.setFile(selectList.get(i).getCompressPath());
                        } else {
                            body.setFile(selectList.get(i).getPath());
                        }
                        body.setSort(i);
                        selectInfoList.add(body);
                    }
                    mPresenter.upLoadFiles(selectInfoList, INFO_CODE);
                    break;
                case HONOR_CODE:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    for (int i = 0; i < selectList.size(); i++) {
                        FileBody body = new FileBody();
                        if (selectList.get(i).isCompressed()) {
                            body.setFile(selectList.get(i).getCompressPath());
                        } else {
                            body.setFile(selectList.get(i).getPath());
                        }
                        body.setSort(i);
                        selectHonorList.add(body);
                    }
                    mPresenter.upLoadFiles(selectHonorList, HONOR_CODE);
                    break;
                case BRAND_CODE:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (int i = 0; i < selectList.size(); i++) {
                        FileBody body = new FileBody();
                        if (selectList.get(i).isCompressed()) {
                            body.setFile(selectList.get(i).getCompressPath());
                        } else {
                            body.setFile(selectList.get(i).getPath());
                        }
                        body.setSort(i);
                        selectGoodList.add(body);
                    }
                    mPresenter.upLoadFiles(selectGoodList, BRAND_CODE);
                    break;
                case CUSTOM_CODE:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (int i = 0; i < selectList.size(); i++) {
                        FileBody body = new FileBody();
                        if (selectList.get(i).isCompressed()) {
                            body.setFile(selectList.get(i).getCompressPath());
                        } else {
                            body.setFile(selectList.get(i).getPath());
                        }
                        body.setSort(i);
                        customList.add(body);
                    }
                    mPresenter.upLoadFiles(customList, CUSTOM_CODE);
                    break;
            }

        }
    }


    /**
     * popupwindow点击事件方法
     *
     * @param isImg
     */
    private void popClick(boolean isImg) {
        switch (popShow) {
            case INFO_CODE:
                if (infoPosition != -1) {
                    popsort = infoPosition;
                }
                if (isImg) {
                    maxNum = 9;
                    choosePicture(INFO_CODE, maxNum);
                } else {
                    childStartForResult(HomeHtmlTextFragment.newIntance("企业简介", null), INFO_CODE);
                }
                break;
            case HONOR_CODE:
                if (providePosition != -1) {
                    popsort = providePosition;
                }
                if (isImg) {
                    maxNum = 9;
                    choosePicture(HONOR_CODE, maxNum);
                } else {
                    childStartForResult(HomeHtmlTextFragment.newIntance("品牌展示", null), HONOR_CODE);
                }
                break;

            case BRAND_CODE:
                if (needPosition != -1) {
                    popsort = needPosition;
                }
                if (isImg) {
                    maxNum = 9;
                    choosePicture(BRAND_CODE, maxNum);
                } else {
                    childStartForResult(HomeHtmlTextFragment.newIntance("品牌展示", null), BRAND_CODE);
                }
                break;
            case CUSTOM_CODE:
                if (customContentPosition != -1) {
                    popsort = customContentPosition;
                }
                if (isImg) {
                    maxNum = 9;
                    choosePicture(CUSTOM_CODE, maxNum);
                } else {
                    childStartForResult(HomeHtmlTextFragment.newIntance(customModel.modularName, null), CUSTOM_CODE);
                }
                break;

        }
    }

    public String getGroupId() {
        if (companyModelModel == null || TextUtils.isEmpty(companyModelModel.getGroupId())) {
            return "";
        }
        return companyModelModel.getGroupId();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
