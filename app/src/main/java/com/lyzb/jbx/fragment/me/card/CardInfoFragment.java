package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.image.LoadImageUtil;
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
import com.lyzb.jbx.adapter.me.card.CdMallAdapter;
import com.lyzb.jbx.adapter.me.card.CustomModelAdapter;
import com.lyzb.jbx.adapter.me.card.HobbyAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.CreateModelDialog;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.fragment.common.HomeHtmlTextFragment;
import com.lyzb.jbx.fragment.home.search.HomeSearchFragment;
import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.CardFileDeModel;
import com.lyzb.jbx.model.me.CardImgTextModel;
import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.CardModelMsg;
import com.lyzb.jbx.model.me.CustomModular;
import com.lyzb.jbx.model.me.IntroductionContent;
import com.lyzb.jbx.model.me.VoiceModel;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.model.params.SaveCardModelBody;
import com.lyzb.jbx.model.params.UpdateModelBody;
import com.lyzb.jbx.mvp.presenter.me.card.InfoPresenter;
import com.lyzb.jbx.mvp.view.me.ICInfoView;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.widget.CusPopWindow;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.PermissionUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.model.AudioFormat;

import static com.lyzb.jbx.fragment.common.HomeHtmlTextFragment.KEY_DATA;
import static com.lyzb.jbx.fragment.me.card.CardFragment.TAB_3;
import static com.szy.yishopcustomer.Activity.samecity.CityListActivity.REQUEST_CODE;
import static com.szy.yishopcustomer.Util.LubanImg.getPath;

/**
 * @author TYK
 * @role 我(TA)的名片-个人
 * @time 2019 2019/3/4 14:40
 */

public class CardInfoFragment extends BasePhotoFragment<InfoPresenter> implements ICInfoView, View.OnClickListener {
    public static final int INFO_CODE = 111;//个人信息
    public static final int PRIVIDE_CODE = 112;//我能提供的
    public static final int NEED_CODE = 113;//我所需要的
    public static final int CUSTOM_CODE = 118;//自定义模板
    public static final int HOBBY_CODE = 114;//兴趣爱好

    private List<LocalMedia> list = new ArrayList<>();
    private List<FileBody> selectInfoList = new ArrayList<>();
    private List<FileBody> selectProvideList = new ArrayList<>();
    private List<FileBody> selectNeedList = new ArrayList<>();
    private List<FileBody> customList = new ArrayList<>();

    View divider_info, divider_provide, divider_need;

    //个人简介-能提供的   没数据的时候显示布局
    LinearLayout llinfoEmpty, ll_shop_empty, ll_company, ll_shop;
    LinearLayout ll_add_info, ll_add_model;
    TextView tv_text_info, tv_img_info, tv_goto_web, tv_all_product, tv_see, tv_add_model, tv_text_pop, tv_img_pop, tv_company_model_name, tv_mall_model_name;
    //声音 音频
    ImageView img_delete_voice;
    ImageView img_animal_play_voice;
    ImageView img_company_logo;
    LinearLayout img_voice_play;

    int customPosition = -1;
    int infoPosition = -1;
    int providePosition = -1;
    int needPosition = -1;
    int customContentPosition = -1;
    ImageView img_avatar;
    LinearLayout ll_voice_msg;
    LinearLayout ll_voice;
    TextView tv_voice_length;
    View view_red_circle;
    RecyclerView imgRecy;
    LinearLayout ll_info;
    EaseVoiceRecorderView voice_recorder;
    LinearLayout ll_provide;
    LinearLayout ll_need;
    LinearLayout ll_field;
    LinearLayout ll_know;
    LinearLayout ll_interest;
    LinearLayout ll_city;
    LinearLayout ll_school;
    LinearLayout ll_address;
    LinearLayout ll_web, ll_company_empty;

    //我能提供的
    TextView tv_text_provide, tv_img_provide;
    RecyclerView supplyRecy;
    LinearLayout llsupplyEmpty, ll_add_provide;

    //需求信息
    TextView tv_text_need, tv_img_need;
    LinearLayout llneedEmpty, ll_add_need;
    RecyclerView needRecy;
    RecyclerView rv_product;
    RecyclerView rv_custom_model;

    private CdMallAdapter mMallAdapter = null;

    //其他信息
    LinearLayout otherAddLin;
    LinearLayout ll_empty;
    NestedScrollView scorllview;

    //熟悉领域  显示
    TagFlowLayout flowlayout_field;
    //期待认识 显示
    TagFlowLayout flowlayout_know;
    RecyclerView likeRecy;
    RecyclerView rvAddress;
    TextView cityText, tv_company_name;
    TextView schoolText;
    TextView stayAddress;
    TextView addPosi;
    TextView addKnow;
    TextView addLike;

    TextView openVip;       //升级智能名片
    TextView tvLuyin;

    ImageView emptyImg;
    TextView emptyTv;


    AnimationDrawable animaition;

    Unbinder unbinder;

    private HobbyAdapter likeAdapter = null, addressAdapter = null;

    private CardTextImgAdapter infoAdapter = null;
    private CardTextImgAdapter provideAdapter = null;
    private CardTextImgAdapter needAdapter = null;

    CustomModelAdapter customModelAdapter = null;//自定义添加模块适配器
    IntroductionContent clickModel; //当前点击的修改的model
    private boolean isScore = false;
    private CardFragment parentFragment = null;

    private CardModel mCardModel = null;
    private CustomModular customModel = null;
    private int maxNum = 9; //最大9张图片
    private String voiceId = "";
    private String voicePath = "";
    int popShow = -1;//-1  则是没点pop出来
    int popsort = -1;//-1 则是没点pop的位置
    boolean isUpdate = false;//是否是修改
    CusPopWindow popWindow;
    MediaPlayer player;
    String moderId = "";
    boolean isShowShop = false;
    private Handler fileHander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showToast("上传失败");
                    break;
                case 1:
                    upLoadOs(msg.obj.toString());
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

        parentFragment = (CardFragment) getParentFragment();
        bindView();
        setListener();

        if (parentFragment.getFromType() == 1) {
            ll_add_model.setVisibility(View.VISIBLE);
        } else {
            ll_add_model.setVisibility(View.GONE);
        }

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


        //处理滑动事件冲突
        supplyRecy.setNestedScrollingEnabled(false);
        needRecy.setNestedScrollingEnabled(false);
        rv_product.setNestedScrollingEnabled(false);
        rv_custom_model.setNestedScrollingEnabled(false);
        likeRecy.setNestedScrollingEnabled(false);
        rvAddress.setNestedScrollingEnabled(false);
        imgRecy.setNestedScrollingEnabled(false);
        supplyRecy.setFocusable(false);
        needRecy.setFocusable(false);
        rv_product.setFocusable(false);
        rv_custom_model.setFocusable(false);
        imgRecy.setFocusable(false);
        rvAddress.setFocusable(false);
        likeRecy.setFocusable(false);

        //个人信息
        infoAdapter = new CardTextImgAdapter();
        infoAdapter.setCanEdit(parentFragment.getFromType() == 1);
        if (parentFragment.getFromType() == 2) {
            addPosi.setText("");
            addLike.setText("");
            stayAddress.setText("");
            addKnow.setText("");

        } else {
            PermissionUtils.requestPermission(getActivity(), REQUEST_CODE, new String[]{"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"});
        }
        imgRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        imgRecy.setAdapter(infoAdapter);

        //我的提供的
        provideAdapter = new CardTextImgAdapter();
        provideAdapter.setCanEdit(parentFragment.getFromType() == 1);
        supplyRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        supplyRecy.setAdapter(provideAdapter);


        //我所需要的
        needAdapter = new CardTextImgAdapter();
        needAdapter.setCanEdit(parentFragment.getFromType() == 1);

        needRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        needRecy.setAdapter(needAdapter);


        //商城
        mMallAdapter = new CdMallAdapter(getContext(), null);
        mMallAdapter.setShowBottom(false);
        mMallAdapter.setGridLayoutManager(rv_product, 2);
        rv_product.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_white_10));
        rv_product.setAdapter(mMallAdapter);


        //自定义模块
        customModelAdapter = new CustomModelAdapter();
        customModelAdapter.setCanEdit(parentFragment.getFromType() == 1);

        rv_custom_model.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_custom_model.setAdapter(customModelAdapter);
        rv_custom_model.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));


        //兴趣爱好
        likeAdapter = new HobbyAdapter(getContext(), null);
        likeRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        likeRecy.setAdapter(likeAdapter);
        likeRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_white_10));

        likeRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                if (parentFragment.getFromType() == 2) {
                    search(adapter.getPositionModel(position).toString());
                } else {
                    clickOther();
                }
            }
        });

        //常来往地
        addressAdapter = new HobbyAdapter(getContext(), null);
        rvAddress.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvAddress.setAdapter(addressAdapter);
        rvAddress.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_white_10));
        rvAddress.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                if (parentFragment.getFromType() == 2) {
                    search(adapter.getPositionModel(position).toString());
                } else {
                    clickOther();
                }
            }
        });


        animaition = (AnimationDrawable) img_animal_play_voice.getBackground();
        animaition.setOneShot(false);

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
                    if (!TextUtils.isEmpty(((IntroductionContent) adapter.getData().get(position)).getModularId())) {
                        moderId = ((IntroductionContent) adapter.getData().get(position)).getModularId();
                    } else {
                        moderId = mCardModel.getIntroductionModular().getId();
                    }
                }
                switch (view.getId()) {
                    case R.id.img_img:
                        if (parentFragment.getFromType() == 1) {//自己的 编辑
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
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

                    case R.id.rl_content://修改文本内容
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        isUpdate = true;
                        clickModel = (IntroductionContent) adapter.getData().get(position);
                        String text = ((IntroductionContent) adapter.getData().get(position)).graphContent;
                        childStartForResult(HomeHtmlTextFragment.newIntance("个人信息", text), INFO_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(1, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(1, true, position);
                        break;
                    case R.id.ll_add:
                        if (parentFragment.getFromType() != 1) {
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

        //我能提供的图片点击
        provideAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
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
                    if (!TextUtils.isEmpty(((IntroductionContent) adapter.getData().get(position)).getModularId())) {
                        moderId = ((IntroductionContent) adapter.getData().get(position)).getModularId();
                    } else {
                        moderId = mCardModel.getMyResourcesModular().getId();
                    }
                }
                switch (view.getId()) {
                    case R.id.img_img:
                        if (parentFragment.getFromType() == 1) {//自己的 编辑
                            clickModel = (IntroductionContent) adapter.getData().get(position);
                            maxNum = 1;
                            isUpdate = true;
                            choosePicture(PRIVIDE_CODE, maxNum);
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        AlertDialogFragment.newIntance()
                                .setTitle("删除内容")
                                .setContent("是否删除当前选择的内容？")
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModelContent(((IntroductionContent) adapter.getData().get(position)).getId(), PRIVIDE_CODE);
                                    }
                                })
                                .setCancleBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show(getFragmentManager(), "delelteModelContent");
                        break;
                    case R.id.rl_content:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        isUpdate = true;
                        clickModel = (IntroductionContent) adapter.getData().get(position);
                        String text = ((IntroductionContent) adapter.getData().get(position)).graphContent;
                        childStartForResult(HomeHtmlTextFragment.newIntance("我能提供", text), PRIVIDE_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(2, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(2, true, position);
                        break;
                    case R.id.ll_add:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        popShow = PRIVIDE_CODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;
                }
            }
        });


        //我需要的图片点击
        needAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
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
                    if (!TextUtils.isEmpty(((IntroductionContent) adapter.getData().get(position)).getModularId())) {
                        moderId = ((IntroductionContent) adapter.getData().get(position)).getModularId();
                    } else {
                        moderId = mCardModel.getMyDemandModular().getId();
                    }
                }
                switch (view.getId()) {
                    case R.id.img_img:
                        if (parentFragment.getFromType() == 1) {//自己的 编辑
                            clickModel = (IntroductionContent) adapter.getData().get(position);
                            maxNum = 1;
                            isUpdate = true;
                            choosePicture(NEED_CODE, maxNum);
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        AlertDialogFragment.newIntance()
                                .setTitle("删除内容")
                                .setContent("是否删除当前选择的内容？")
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.delereModelContent(((IntroductionContent) adapter.getData().get(position)).getId(), NEED_CODE);
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        isUpdate = true;
                        clickModel = (IntroductionContent) adapter.getData().get(position);
                        String text = ((IntroductionContent) adapter.getData().get(position)).graphContent;
                        childStartForResult(HomeHtmlTextFragment.newIntance("我所需要的", text), NEED_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(3, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(3, true, position);
                        break;
                    case R.id.ll_add:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        popShow = NEED_CODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                        }
                        break;
                }
            }
        });


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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        childStartForResult(HomeHtmlTextFragment.newIntance(modular.modularName, null), CUSTOM_CODE);
                        break;
                    case R.id.tv_img://添加图片
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        maxNum = 9;
                        choosePicture(CUSTOM_CODE, maxNum);
                        break;
                    case R.id.ll_add_custom:
                        if (parentFragment.getFromType() != 1) {
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
                        if (parentFragment.getFromType() == 1) {//自己的 编辑
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
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
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        isUpdate = true;
                        String text = clickModel.getGraphContent();
                        childStartForResult(HomeHtmlTextFragment.newIntance(customModular.modularName, text), CUSTOM_CODE);
                        break;
                    case R.id.img_turn_down:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(0, false, position);
                        break;
                    case R.id.img_turn_top:
                        if (parentFragment.getFromType() != 1) {
                            return;
                        }
                        createSortRequest(0, true, position);
                        break;
                    case R.id.ll_add:
                        if (parentFragment.getFromType() != 1) {
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

        //长按发布语音 声音 录音 音频
        if (parentFragment.getFromType() == 1) {
            tvLuyin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (PermissionUtils.hasPermission(getContext(), new String[]{"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"})) {
                        return voice_recorder.onPressToSpeakBtnTouchTime(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
                            @Override
                            public void onVoiceRecordComplete(final String voiceFilePath, int voiceTimeLength) {
                                LogUtil.loge("当前文件路径为" + voiceFilePath);
                                voice_recorder.setVisibility(View.GONE);
                                if (voiceTimeLength != 0) {
                                    convertAudio(voiceFilePath);
                                }
                            }
                        }, 61);

                    } else {
                        showToast("未给麦克风权限和读写权限");
                        return false;
                    }
                }
            });
        }
    }


    /**
     * 音频格式转换
     *
     * @param uri 文件地址
     */
    public void convertAudio(String uri) {
        /**
         *  Update with a valid audio file!
         *  Supported formats: {@link AndroidAudioConverter.AudioFormat}
         */
        File wavFile = new File(uri);
        IConvertCallback callback = new IConvertCallback() {
            @Override
            public void onSuccess(File convertedFile) {
                mPresenter.upLoadFile(convertedFile, fileHander);
            }

            @Override
            public void onFailure(Exception error) {
                Toast.makeText(getActivity(), "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        AndroidAudioConverter.with(getActivity())
                .setFile(wavFile)
                .setFormat(AudioFormat.MP3)
                .setCallback(callback)
                .convert();
    }


    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        onCardData(parentFragment.mCardModel);
        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(App.getInstance().userId);
        } else {
            mPresenter.getList(parentFragment.getCardUserId());
        }
    }


    @Override
    public Object getResId() {
        return R.layout.fragment_union_card_info;
    }

    /**
     * 上传语音文件到OS成功
     *
     * @param url
     */
    @Override
    public void upLoadOs(String url) {
        CardImgTextModel model = new CardImgTextModel();
        List<CardImgTextModel.Imgs> list = new ArrayList<>();
        CardImgTextModel.Imgs file = new CardImgTextModel.Imgs();
        file.setFilePath(url);
        file.setFileLength(getUrlDurationS(url));
        list.add(file);
        model.setFilePath(list);
        model.setTagType(6);
        model.setFileType(3);
        mPresenter.uploadVoice(model);
    }

    /**
     * 语音文件上传成功
     */
    @Override
    public void upLoadVoiceSuccess(VoiceModel voiceModel) {
        for (int i = 0; i < voiceModel.getFileList().size(); i++) {
            if (voiceModel.getFileList().get(i).getFileType() == 3) {
                voiceId = voiceModel.getFileList().get(i).getId();
                voicePath = voiceModel.getFileList().get(i).getFilePath();
                tvLuyin.setVisibility(View.GONE);
                img_voice_play.setVisibility(View.VISIBLE);
                ll_voice_msg.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(voiceModel.getFileList().get(i).getFileLength())) {
                    tv_voice_length.setText(getUrlDuration(voiceModel.getFileList().get(i).getFilePath()) + "″");
                } else {
                    tv_voice_length.setText(voiceModel.getFileList().get(i).getFileLength() + "″");
                }
                img_delete_voice.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 删除音频成功
     */
    @Override
    public void deleteSuccess() {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
        }
        tvLuyin.setVisibility(View.VISIBLE);
        img_voice_play.setVisibility(View.GONE);
        ll_voice_msg.setVisibility(View.GONE);
        img_delete_voice.setVisibility(View.GONE);
    }

    /**
     * 上传个人信息图片
     *
     * @param uplist
     */
    @Override
    public void getInfo(List<FileBody> uplist) {
        selectInfoList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), INFO_CODE);
        } else {//增加内容
            createRequest(uplist, 1);
        }
    }


    /**
     * 上传我能提供图片
     *
     * @param uplist
     */
    @Override
    public void getProvide(List<FileBody> uplist) {
        selectProvideList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), PRIVIDE_CODE);
        } else {//增加内容
            createRequest(uplist, 2);
        }
    }

    /**
     * 上传我需要的图片
     *
     * @param uplist
     */
    @Override
    public void getNeed(List<FileBody> uplist) {
        selectNeedList.clear();
        if (isUpdate) {
            updateContent(uplist.get(0).getFile(), NEED_CODE);
        } else {//增加内容
            createRequest(uplist, 3);
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

    /**
     * 保存个人信息资料成功
     * 模板信息  自定义模板增删盖茶
     *
     * @param string
     */
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

    /**
     * 删除成功回调
     *
     * @param string
     */
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
                    divider_info.setVisibility(View.VISIBLE);
                } else {
                    llinfoEmpty.setVisibility(View.GONE);
                    ll_add_info.setVisibility(View.VISIBLE);
                    divider_info.setVisibility(View.GONE);
                }
                break;
            case PRIVIDE_CODE:
                provideAdapter.remove(providePosition);
                provideAdapter.notifyItemChanged(provideAdapter.getData().size() - 1);
                if (provideAdapter.getData().size() == 0) {
                    llsupplyEmpty.setVisibility(View.VISIBLE);
                    ll_add_provide.setVisibility(View.GONE);
                    divider_provide.setVisibility(View.VISIBLE);
                } else {
                    llsupplyEmpty.setVisibility(View.GONE);
                    ll_add_provide.setVisibility(View.VISIBLE);
                    divider_provide.setVisibility(View.GONE);
                }
                break;
            case NEED_CODE:
                needAdapter.remove(needPosition);
                needAdapter.notifyItemChanged(needAdapter.getData().size() - 1);

                if (needAdapter.getData().size() == 0) {
                    llneedEmpty.setVisibility(View.VISIBLE);
                    ll_add_need.setVisibility(View.GONE);
                    divider_need.setVisibility(View.VISIBLE);
                } else {
                    llneedEmpty.setVisibility(View.GONE);
                    ll_add_need.setVisibility(View.VISIBLE);
                    divider_need.setVisibility(View.GONE);
                }
                break;
            case CUSTOM_CODE:
                List<IntroductionContent> list = customModelAdapter.getData().get(customPosition).getParagraphVoList();
                list.remove(customContentPosition);
                customModelAdapter.getData().get(customPosition).setParagraphVoList(list);
                customModelAdapter.notifyItemChanged(customPosition);

                break;
        }
    }

    /**
     * 删除模板成功
     *
     * @param string
     */
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
            case PRIVIDE_CODE:
                provideAdapter.getData().get(providePosition).setGraphContent(content);
                provideAdapter.replaceData(provideAdapter.getData());
                break;
            case NEED_CODE:
                needAdapter.getData().get(needPosition).setGraphContent(content);
                needAdapter.replaceData(needAdapter.getData());
                break;
            case CUSTOM_CODE:
                customModelAdapter.getData().get(customPosition).getParagraphVoList().get(customContentPosition).setGraphContent(content);
                customModelAdapter.replaceData(customModelAdapter.getData());
                break;
        }
        clickModel = null;
    }

    @Override
    public void onGoodList(CardMallModel model) {

        if (model != null && model.getList() != null && model.getList().size() != 0) {
            mMallAdapter.update(model.getList());
            ll_shop_empty.setVisibility(View.GONE);
        } else {
            if (parentFragment.getFromType() != 1) {//别人的没商品就不显示模块
                ll_shop.setVisibility(View.GONE);
            } else {//自己的没商品 如果有商城模块就显示 空模块  没商城模块才不显示模块
                ll_shop.setVisibility(isShowShop ? View.VISIBLE : View.GONE);
            }
            ll_shop_empty.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 获取时长 文字
     *
     * @param audioUrl
     * @return
     */
    private String getUrlDuration(String audioUrl) {
        String total = "";
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            int duration = mediaPlayer.getDuration();
            if (0 != duration) {
                //更新 seekbar 长度
                int s = duration / 1000;
                //设置文件时长，单位 "分:秒" 格式
                if (s / 60 > 0) {
                    total = s / 60 + ":" + s % 60;
                } else {
                    total = s % 60 + "";
                }
                //记得释放资源
                mediaPlayer.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 获取时长
     *
     * @param audioUrl
     * @return
     */
    private String getUrlDurationS(String audioUrl) {
        String total = "";
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            int duration = mediaPlayer.getDuration();
            if (0 != duration) {
                //更新 seekbar 长度
                total = (int) Math.ceil(duration / 1000) + "";
                //记得释放资源
                mediaPlayer.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 获取详情信息
     *
     * @param model
     */
    public void onCardData(CardModel model) {
        if (model == null) {
            return;
        }

        if (model.getId() == null) {
            showToast("当前用户名片信息未完善!");
            pop();
            return;
        }
        mCardModel = model;
        isShowShop = false;
        if (model.getGsCardFunctionSetList().size() > 0) {
            for (int i = 0; i < model.getGsCardFunctionSetList().size(); i++) {
                switch (model.getGsCardFunctionSetList().get(i).getFunCode()) {
                    case TAB_3:
                        isShowShop = true;
                        tv_mall_model_name.setText(model.getGsCardFunctionSetList().get(i).getFunName());
                        break;
                }
            }
        }
        ll_shop.setVisibility(isShowShop ? View.VISIBLE : View.GONE);
        ll_company.setVisibility(View.GONE);

//        音频模块
        if (model.getIntroductionAudioFile().size() > 0) {//有音频
            voiceId = model.getIntroductionAudioFile().get(0).getId();
            voicePath = model.getIntroductionAudioFile().get(0).getFilePath();
            if (parentFragment.getFromType() == 1) {//自己的才显示删除按钮
                img_delete_voice.setVisibility(View.VISIBLE);
            } else {//别人的不显示删除按钮
                img_delete_voice.setVisibility(View.GONE);
            }
            ll_voice.setVisibility(View.VISIBLE);
            tvLuyin.setVisibility(View.GONE);
            img_voice_play.setVisibility(View.VISIBLE);
            ll_voice_msg.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(model.getIntroductionAudioFile().get(0).getFileLength())) {
                tv_voice_length.setText(getUrlDuration(model.getIntroductionAudioFile().get(0).getFilePath()) + "″");
            } else {
                tv_voice_length.setText(model.getIntroductionAudioFile().get(0).getFileLength() + "″");
            }
            LoadImageUtil.loadRoundImage(img_avatar, model.getHeadimg(), 4);
        } else {//没有音频
            img_voice_play.setVisibility(View.GONE);
            ll_voice_msg.setVisibility(View.GONE);
            img_delete_voice.setVisibility(View.GONE);
            if (parentFragment.getFromType() == 1) {//自己的 显示头像
                ll_voice.setVisibility(View.VISIBLE);
                tvLuyin.setVisibility(View.VISIBLE);
                LoadImageUtil.loadRoundImage(img_avatar, model.getHeadimg(), 4);
            } else {
                ll_voice.setVisibility(View.GONE);
                tvLuyin.setVisibility(View.GONE);
            }
        }

        //个人信息模块
        if (model.getIntroductionContent().size() > 0) {//个人信息有内容
            ll_info.setVisibility(View.VISIBLE);
            //个人信息-已添加数据
            llinfoEmpty.setVisibility(View.GONE);
            if (parentFragment.getFromType() == 1) {//自己的
                ll_add_info.setVisibility(View.VISIBLE);
                divider_info.setVisibility(View.GONE);
            } else {
                ll_add_info.setVisibility(View.GONE);
                divider_info.setVisibility(View.VISIBLE);
            }
            imgRecy.setVisibility(View.VISIBLE);

            infoAdapter.setNewData(model.getIntroductionContent());
        } else {//个人信息没内容
            if (parentFragment.getFromType() == 1) {//自己的
                ll_info.setVisibility(View.VISIBLE);
                llinfoEmpty.setVisibility(View.VISIBLE);
                ll_add_info.setVisibility(View.GONE);
                divider_info.setVisibility(View.VISIBLE);
            } else {
                ll_info.setVisibility(View.GONE);

            }
        }

        //我能提供模块
        if (model.getMyResourcesContent().size() > 0) {//我能提供有内容
            ll_provide.setVisibility(View.VISIBLE);
            //我能提供-已添加数据
            llsupplyEmpty.setVisibility(View.GONE);
            if (parentFragment.getFromType() == 1) {//自己的
                ll_add_provide.setVisibility(View.VISIBLE);
                divider_provide.setVisibility(View.GONE);
            } else {
                ll_add_provide.setVisibility(View.GONE);
                divider_provide.setVisibility(View.VISIBLE);
            }
            supplyRecy.setVisibility(View.VISIBLE);

            provideAdapter.setNewData(model.getMyResourcesContent());
        } else {//我能提供没内容
            if (parentFragment.getFromType() == 1) {//自己的
                ll_provide.setVisibility(View.VISIBLE);
                llsupplyEmpty.setVisibility(View.VISIBLE);
                ll_add_provide.setVisibility(View.GONE);
                divider_provide.setVisibility(View.VISIBLE);
            } else {
                ll_provide.setVisibility(View.GONE);
            }
        }

        //我需要的模块
        if (model.getMyDemandContent().size() > 0) {//我需要的有内容
            ll_need.setVisibility(View.VISIBLE);
            //我需要的-已添加数据
            llneedEmpty.setVisibility(View.GONE);
            if (parentFragment.getFromType() == 1) {//自己的
                ll_add_need.setVisibility(View.VISIBLE);
                divider_need.setVisibility(View.GONE);
            } else {
                ll_add_need.setVisibility(View.GONE);
                divider_need.setVisibility(View.VISIBLE);
            }
            needRecy.setVisibility(View.VISIBLE);

            needAdapter.setNewData(model.getMyDemandContent());
        } else {//我需要的没内容
            if (parentFragment.getFromType() == 1) {//自己的
                ll_need.setVisibility(View.VISIBLE);
                llneedEmpty.setVisibility(View.VISIBLE);
                ll_add_need.setVisibility(View.GONE);
                divider_need.setVisibility(View.VISIBLE);
            } else {
                ll_need.setVisibility(View.GONE);
            }
        }

        //地址模块
        if (!TextUtils.isEmpty(model.getResidenceCN())) {
            cityText.setTextColor(getResources().getColor(R.color.fontcColor1));
            cityText.setText(model.getResidenceCN());
            cityText.setBackground(getResources().getDrawable(R.drawable.stock_bottom_blue));
        } else {
            cityText.setBackground(null);
            if (parentFragment.getFromType() == 1) {//自己的
                ll_city.setVisibility(View.VISIBLE);
            } else {//别人的
                ll_city.setVisibility(View.GONE);
            }
        }

//        学校模块
        if (!TextUtils.isEmpty(model.getEducation())) {
            schoolText.setText(model.getEducation());
            schoolText.setBackground(getResources().getDrawable(R.drawable.stock_bottom_blue));
        } else {
            schoolText.setBackground(null);
            if (parentFragment.getFromType() == 1) {//自己的
                ll_school.setVisibility(View.VISIBLE);
            } else {//别人的
                ll_school.setVisibility(View.GONE);
            }
        }
        //常来往地
        if (!TextUtils.isEmpty(model.getOftenToPace())) {
            ll_address.setVisibility(View.VISIBLE);
            stayAddress.setVisibility(View.GONE);
            if (CommonUtil.StringToList(model.getOftenToPace()).size() > 0) {
                rvAddress.setVisibility(View.VISIBLE);
            } else {
                rvAddress.setVisibility(View.GONE);
            }
            addressAdapter.update(CommonUtil.StringToList(model.getOftenToPace()));
        } else {
            if (parentFragment.getFromType() == 2) {//别人的
                stayAddress.setVisibility(View.GONE);
                ll_address.setVisibility(View.GONE);
            } else {
                stayAddress.setVisibility(View.VISIBLE);
                ll_address.setVisibility(View.VISIBLE);
            }
        }

        //熟悉领域
        if (!TextUtils.isEmpty(model.getProfessionName())) {
            ll_field.setVisibility(View.VISIBLE);
            addPosi.setVisibility(View.GONE);
            setFlowLayout(CommonUtil.StringToList(model.getProfessionName()));
        } else {
            if (parentFragment.getFromType() == 2) {//别人的
                ll_field.setVisibility(View.GONE);
                addPosi.setVisibility(View.GONE);
            } else {
                ll_field.setVisibility(View.VISIBLE);
                addPosi.setVisibility(View.VISIBLE);
            }
        }

        //期待认识
        if (!TextUtils.isEmpty(model.getConcernProfessionName())) {
            ll_know.setVisibility(View.VISIBLE);
            addKnow.setVisibility(View.GONE);
            setFlowLayoutKnow(CommonUtil.StringToList(model.getConcernProfessionName()));
        } else {
            if (parentFragment.getFromType() == 2) {//别人的
                addKnow.setVisibility(View.GONE);
                ll_know.setVisibility(View.GONE);
            } else {
                addKnow.setVisibility(View.VISIBLE);
                ll_know.setVisibility(View.VISIBLE);
            }
        }

        //兴趣爱好
        if (!TextUtils.isEmpty(model.getInterest())) {
            ll_interest.setVisibility(View.VISIBLE);
            addLike.setVisibility(View.GONE);
            if (CommonUtil.StringToList(model.getInterest()).size() > 0) {
                likeRecy.setVisibility(View.VISIBLE);
            } else {
                likeRecy.setVisibility(View.GONE);
            }
            likeAdapter.update(CommonUtil.StringToList(model.getInterest()));
        } else {
            if (parentFragment.getFromType() == 2) {//别人的
                addLike.setVisibility(View.GONE);
                ll_interest.setVisibility(View.GONE);
            } else {
                addLike.setVisibility(View.VISIBLE);
                ll_interest.setVisibility(View.VISIBLE);
            }
        }

        //我的企业模块
        if (TextUtils.isEmpty(model.getShopName()) && TextUtils.isEmpty(model.getShopLogo()) && TextUtils.isEmpty(model.getCurrentDepartmentID())) {
            ll_web.setVisibility(View.GONE);
            ll_company_empty.setVisibility(View.VISIBLE);
        } else {
            ll_web.setVisibility(View.VISIBLE);
            ll_company_empty.setVisibility(View.GONE);
            LoadImageUtil.loadImage(img_company_logo, model.getDistributorLogo());
            tv_company_name.setText(model.getCompanyName());
        }

        //自定义模块
        if (parentFragment.getFromType() != 1) {//别人的时候显示 要除去没有内容的自定义model
            for (int i = model.getCustomModular().size() - 1; i >= 0; i--) {
                if (model.getCustomModular().get(i).getParagraphVoList().size() == 0) {
                    model.getCustomModular().remove(i);
                }
            }
        }

        customModelAdapter.setNewData(model.getCustomModular());

        if (parentFragment.getFromType() != 1) {//别人的名片才显示
            //其他模块的显示隐藏
            if (TextUtils.isEmpty(model.getProfessionName())
                    && TextUtils.isEmpty(model.getConcernProfessionName())
                    && TextUtils.isEmpty(model.getInterest())
                    && TextUtils.isEmpty(model.getResidenceCN())
                    && TextUtils.isEmpty(model.getEducation())) {//其他模块为空的时候不显示其他模块
                otherAddLin.setVisibility(View.GONE);
                if (model.getIntroductionImgFile().size() == 0
                        && model.getMyResourcesImgFile().size() == 0
                        && model.getMyDemandImgFile().size() == 0
                        && TextUtils.isEmpty(model.getMyResources())
                        && TextUtils.isEmpty(model.getMyDemand())
                        && TextUtils.isEmpty(model.getIntroduction())) {//全都为空的时候显示 恐怖剧
                    ll_empty.setVisibility(View.VISIBLE);
                } else {
                    ll_empty.setVisibility(View.GONE);
                }
            } else {
                ll_empty.setVisibility(View.GONE);
                otherAddLin.setVisibility(View.VISIBLE);
            }
        } else {
            otherAddLin.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }

        //升级智能名片
        if (parentFragment.getFromType() == 1) {
            if (App.getInstance().isUserVip) {
                openVip.setVisibility(View.GONE);
            } else {
                openVip.setVisibility(View.VISIBLE);
            }
        } else {
            openVip.setVisibility(View.GONE);
        }


    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {
                //个人信息
                case INFO_CODE:
                    String textInfo = data.getString(KEY_DATA);
                    llinfoEmpty.setVisibility(View.GONE);
                    imgRecy.setVisibility(View.VISIBLE);
                    if (isUpdate) {
                        updateContent(textInfo, INFO_CODE);
                    } else {//增加内容
                        createTextRequest(textInfo, 1);
                    }
                    break;
                //我能提供
                case PRIVIDE_CODE:
                    String textProvide = data.getString(KEY_DATA);
                    llsupplyEmpty.setVisibility(View.GONE);
                    supplyRecy.setVisibility(View.VISIBLE);
                    if (isUpdate) {
                        updateContent(textProvide, PRIVIDE_CODE);
                    } else {//增加内容
                        createTextRequest(textProvide, 2);
                    }
                    break;
                //我需要的
                case NEED_CODE:
                    String textNeed = data.getString(KEY_DATA);
                    llneedEmpty.setVisibility(View.GONE);
                    needRecy.setVisibility(View.VISIBLE);
                    if (isUpdate) {
                        updateContent(textNeed, NEED_CODE);
                    } else {//增加内容
                        createTextRequest(textNeed, 3);
                    }
                    break;
                //自定义模板的文字
                case CUSTOM_CODE:
                    String textCustom = data.getString(KEY_DATA);
                    if (isUpdate) {
                        updateContent(textCustom, CUSTOM_CODE);
                    } else {//增加自定义模板内容
                        createTextRequest(textCustom, 0);
                    }
                    break;
                //兴趣爱好
                case HOBBY_CODE:
                    mCardModel = null;
                    mCardModel = GSONUtil.getEntity(data.getString("CardInfoOther"), CardModel.class);
                    if (!TextUtils.isEmpty(mCardModel.getProfessionName())) {
                        addPosi.setVisibility(View.GONE);
                    } else {
                        addPosi.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(mCardModel.getConcernProfessionName())) {
                        addKnow.setVisibility(View.GONE);
                    } else {
                        addKnow.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(mCardModel.getInterest())) {//兴趣爱好
                        addLike.setVisibility(View.GONE);
                        ll_interest.setVisibility(View.VISIBLE);
                        if (CommonUtil.StringToList(mCardModel.getInterest()).size() > 0) {
                            likeRecy.setVisibility(View.VISIBLE);
                        } else {
                            likeRecy.setVisibility(View.GONE);
                        }
                        likeAdapter.update(CommonUtil.StringToList(mCardModel.getInterest()));                   //兴趣爱好
                    } else {
                        likeRecy.setVisibility(View.GONE);
                        addLike.setVisibility(View.VISIBLE);
                        ll_interest.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(mCardModel.getOftenToPace())) {//常来往地
                        stayAddress.setVisibility(View.GONE);
                        ll_address.setVisibility(View.VISIBLE);
                        if (CommonUtil.StringToList(mCardModel.getOftenToPace()).size() > 0) {
                            rvAddress.setVisibility(View.VISIBLE);
                        } else {
                            rvAddress.setVisibility(View.GONE);
                        }
                        addressAdapter.update(CommonUtil.StringToList(mCardModel.getOftenToPace()));                   //兴趣爱好
                    } else {
                        rvAddress.setVisibility(View.GONE);
                        stayAddress.setVisibility(View.VISIBLE);
                        ll_address.setVisibility(View.VISIBLE);
                    }

                    //熟悉领域
                    setFlowLayout(CommonUtil.StringToList(mCardModel.getProfessionName()));
                    //期待认识
                    setFlowLayoutKnow(CommonUtil.StringToList(mCardModel.getConcernProfessionName()));


                    if (!TextUtils.isEmpty(mCardModel.getResidenceCN())) {
                        cityText.setText(mCardModel.getResidenceCN());
                    } else {
                        cityText.setBackground(getResources().getDrawable(R.drawable.stock_bottom_blue));
                    }
                    if (!TextUtils.isEmpty(mCardModel.getEducation())) {
                        schoolText.setText(mCardModel.getEducation());
                        schoolText.setBackground(getResources().getDrawable(R.drawable.stock_bottom_blue));
                    } else {
                        schoolText.setBackground(null);
                    }
                    break;
            }

        }
    }


    /**
     * 去重字符串中用逗号隔开的字符串  如   "1,1,2"->"1,2"
     *
     * @param str
     * @return
     */
    private String removeDoublue(String str) {
        List<String> noDouble = new ArrayList<>();
        if (str == null) {
            return "";
        } else {
            List<String> list = CommonUtil.StringToList(str);
            for (int i = 0; i < list.size(); i++) {
                if (!noDouble.contains(list.get(i))) {
                    noDouble.add(list.get(i));
                }
            }
            return CommonUtil.ListToString(noDouble);
        }

    }

    /**
     * 设置熟悉领域
     *
     * @param list
     */
    private void setFlowLayout(final List<String> list) {
        flowlayout_field.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String str) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_union_info_hobby, flowlayout_field, false);
                textView.setText(str);
                return textView;
            }
        });

        flowlayout_field.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (parentFragment.getFromType() == 2) {
                    search(list.get(position));
                } else {
                    clickOther();
                }
                return false;
            }
        });
    }

    /**
     * 设置期待认识
     *
     * @param list
     */
    private void setFlowLayoutKnow(final List<String> list) {
        flowlayout_know.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String str) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_union_info_hobby, flowlayout_know, false);
                textView.setText(str);
                return textView;
            }
        });

        flowlayout_know.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (parentFragment.getFromType() == 2) {
                    search(list.get(position));
                } else {
                    clickOther();
                }
                return false;
            }
        });
    }

    private void setListener() {
        tv_text_info.setOnClickListener(this);
        tv_img_info.setOnClickListener(this);
        tv_goto_web.setOnClickListener(this);
        tv_all_product.setOnClickListener(this);
        tv_see.setOnClickListener(this);
        tv_add_model.setOnClickListener(this);

        tv_text_provide.setOnClickListener(this);
        tv_text_need.setOnClickListener(this);
        tv_img_need.setOnClickListener(this);
        tv_img_provide.setOnClickListener(this);
        openVip.setOnClickListener(this);
        img_delete_voice.setOnClickListener(this);
        img_voice_play.setOnClickListener(this);
        cityText.setOnClickListener(this);
        schoolText.setOnClickListener(this);
        otherAddLin.setOnClickListener(this);
        ll_add_info.setOnClickListener(this);
        ll_add_provide.setOnClickListener(this);
        ll_add_need.setOnClickListener(this);


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
    public void onClick(View v) {
        switch (v.getId()) {

            //简介 图文信息编辑
            case R.id.tv_text_info:
                moderId = mCardModel.getIntroductionModular().getId();
                childStartForResult(HomeHtmlTextFragment.newIntance("个人信息", null), INFO_CODE);
                break;
            case R.id.tv_img_info:
                moderId = mCardModel.getIntroductionModular().getId();
                maxNum = 9;
                choosePicture(INFO_CODE, maxNum);
                break;
            //我能提供的 文本
            case R.id.tv_text_provide:
                moderId = mCardModel.getMyResourcesModular().getId();
                childStartForResult(HomeHtmlTextFragment.newIntance("我能提供", null), PRIVIDE_CODE);
                break;
            case R.id.tv_img_provide:
                moderId = mCardModel.getMyResourcesModular().getId();
                maxNum = 9;
                choosePicture(PRIVIDE_CODE, maxNum);
                break;
            //我需要的 文本
            case R.id.tv_text_need:
                moderId = mCardModel.getMyDemandModular().getId();
                childStartForResult(HomeHtmlTextFragment.newIntance("我所需要的", null), NEED_CODE);
                break;
            case R.id.tv_img_need:
                moderId = mCardModel.getMyDemandModular().getId();
                maxNum = 9;
                choosePicture(NEED_CODE, maxNum);
                break;
            //升级只能名片
            case R.id.tv_card_upgrade_vip:
                Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_OPEN_VIP);
                startActivity(tgIntent);
                break;
            //删除录音
            case R.id.img_delete_voice:
                final CardFileDeModel file = new CardFileDeModel();
                file.setId(voiceId);
                AlertDialogFragment.newIntance()
                        .setContent("是否删除录音")
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.deleFile(file);
                            }
                        })
                        .show(getFragmentManager(), "EixttTag");

                break;
            //播放录音
            case R.id.img_voice_play:
                playVoice(voicePath);
                break;
            case R.id.lin_un_cd_other_add:
                clickOther();
                break;
            case R.id.tv_card_city://点击我的家乡
                if (parentFragment.getFromType() == 2) {
                    if (!TextUtils.isEmpty(mCardModel.getResidenceCN())) {
                        search(mCardModel.getResidenceCN());
                    }
                } else {
                    clickOther();
                }
                break;

            case R.id.tv_card_school://点击我的学校
                if (parentFragment.getFromType() == 2) {
                    if (!TextUtils.isEmpty(mCardModel.getEducation())) {
                        search(mCardModel.getEducation());
                    }
                } else {
                    clickOther();
                }
                break;

            case R.id.tv_goto_web://进入官网
                parentFragment.gotoWeb();
                break;

            case R.id.tv_all_product://进入商城 （查看全部商品）
            case R.id.tv_see:
                parentFragment.gotoShop();
                break;
            case R.id.tv_add_model://添加模块
                if (parentFragment.getFromType() != 1) {
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
                        model.setBelongType(1);
                        mPresenter.addModelContent(model, 1, false, -1);
                    }
                }).show(getFragmentManager(), "createModel");
                break;
            case R.id.ll_add_info:
                if (parentFragment.getFromType() != 1) {
                    return;
                }
                if (!TextUtils.isEmpty(infoAdapter.getId())) {
                    moderId = infoAdapter.getId();
                } else {
                    if (!TextUtils.isEmpty(infoAdapter.getData().get(0).getModularId())) {
                        moderId = infoAdapter.getData().get(0).getModularId();
                    } else {
                        moderId = mCardModel.getIntroductionModular().getId();
                    }
                }
                popShow = INFO_CODE;
                infoPosition = -1;
                popsort = infoAdapter.getData().size();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                }
                break;
            case R.id.ll_add_provide:
                if (parentFragment.getFromType() != 1) {
                    return;
                }
                if (!TextUtils.isEmpty(provideAdapter.getId())) {
                    moderId = provideAdapter.getId();
                } else {
                    if (!TextUtils.isEmpty(provideAdapter.getData().get(0).getModularId())) {
                        moderId = provideAdapter.getData().get(0).getModularId();
                    } else {
                        moderId = mCardModel.getMyResourcesModular().getId();
                    }
                }
                popShow = PRIVIDE_CODE;
                providePosition = -1;
                popsort = provideAdapter.getData().size();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                }
                break;
            case R.id.ll_add_need:
                if (parentFragment.getFromType() != 1) {
                    return;
                }
                if (!TextUtils.isEmpty(needAdapter.getId())) {
                    moderId = needAdapter.getId();
                } else {
                    if (!TextUtils.isEmpty(needAdapter.getData().get(0).getModularId())) {
                        moderId = needAdapter.getData().get(0).getModularId();
                    } else {
                        moderId = mCardModel.getMyDemandModular().getId();
                    }
                }
                popShow = NEED_CODE;
                needPosition = -1;
                popsort = needAdapter.getData().size();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popWindow.showAsDropDown(v, ScreenUtil.getScreenWidth() / 2 - DensityUtil.dpTopx(60), 0, Gravity.BOTTOM);
                }
                break;

        }
    }


    /**
     * 播放音频
     *
     * @param url
     */
    private void playVoice(String url) {

        if (player == null) {
            //如果为空就new我一个
            player = new MediaPlayer();
            try {
                player.setDataSource(url);
                //异步准备
                player.prepareAsync();
                //添加准备好的监听
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //如果准备好了，就会进行这个方法
                        mp.start();
                        animaition.start();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //判断是否处于播放状态
            if (player.isPlaying()) {
                player.pause();
                animaition.stop();
            } else {
                player.start();
                animaition.start();
            }
        }

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtil.loge("当前已经完成播放");
                animaition.stop();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
        }
    }

    /**
     * 点击其他模块
     */
    private void clickOther() {
        if (parentFragment.getFromType() == 1) {
            List<BusinessModel> knows = new ArrayList<>();
            List<BusinessModel> doms = new ArrayList<>();
            List<String> likes = new ArrayList<>();
            List<String> address = new ArrayList<>();
            String schText = "";

            if (!TextUtils.isEmpty(mCardModel.getProfessionName())) {

                List<String> name = CommonUtil.StringToList(removeDoublue(mCardModel.getProfessionName()));
                List<String> name_id = CommonUtil.StringToList(removeDoublue(mCardModel.getProfessionId()));

                //熟悉领域
                for (int i = 0; i < name.size(); i++) {
                    BusinessModel model = new BusinessModel();
                    model.setName(name.get(i));
                    model.setId(Integer.valueOf(name_id.get(i)));
                    knows.add(model);
                }
            }

            if (!TextUtils.isEmpty(mCardModel.getConcernProfessionName())) {

                List<String> name = CommonUtil.StringToList(removeDoublue(mCardModel.getConcernProfessionName()));
                List<String> name_id = CommonUtil.StringToList(removeDoublue(mCardModel.getConcernProfession()));

                //期待认识
                for (int i = 0; i < name.size(); i++) {
                    BusinessModel model = new BusinessModel();
                    model.setName(name.get(i));
                    model.setId(Integer.valueOf(name_id.get(i)));
                    doms.add(model);
                }
            }

            if (!TextUtils.isEmpty(mCardModel.getInterest())) {
                //兴趣爱好
                likes = CommonUtil.StringToList(removeDoublue(mCardModel.getInterest()));
            }
            if (!TextUtils.isEmpty(mCardModel.getOftenToPace())) {
                //常来往地
                address = CommonUtil.StringToList(removeDoublue(mCardModel.getOftenToPace()));
            }

            if (!TextUtils.isEmpty(mCardModel.getEducation())) {
                schText = mCardModel.getEducation();
            }

            ((BaseFragment) getParentFragment()).startForResult(
                    CardInfoOtherFragment.Companion.newItance(knows, doms, likes,
                            address, mCardModel.getResidenceCN(), mCardModel.getResidence(), schText), HOBBY_CODE);
        }
    }

    /**
     * 跳转搜索页面
     *
     * @param searchValue
     */
    private void search(String searchValue) {
        childStart(HomeSearchFragment.newIntance(searchValue, HomeSearchFragment.ACCOUNT_VIEW));
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        parentFragment.banAppBarScroll(true);

    }

    public void setPlayerRelease() {
        LogUtil.loge("你释放了播放器");
        if (player != null && player.isPlaying()) {
            player.stop();
            animaition.stop();
            player.release();
            player = null;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.loge("当前显示了" + isVisibleToUser);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void bindView() {
        llinfoEmpty = findViewById(R.id.img_card_info_img_empty);
        ll_shop_empty = findViewById(R.id.ll_shop_empty);
        ll_company = findViewById(R.id.ll_company);
        divider_info = findViewById(R.id.divider_info);
        divider_provide = findViewById(R.id.divider_provide);
        divider_need = findViewById(R.id.divider_need);
        ll_add_info = findViewById(R.id.ll_add_info);
        llsupplyEmpty = findViewById(R.id.img_un_card_supply_edt);
        ll_add_provide = findViewById(R.id.ll_add_provide);
        img_delete_voice = findViewById(R.id.img_delete_voice);
        img_animal_play_voice = findViewById(R.id.img_animal_play_voice);
        img_voice_play = findViewById(R.id.img_voice_play);
        img_avatar = findViewById(R.id.img_avatar);
        ll_voice_msg = findViewById(R.id.ll_voice_msg);
        ll_voice = findViewById(R.id.ll_voice);
        tv_voice_length = findViewById(R.id.tv_voice_length);
        view_red_circle = findViewById(R.id.view_red_circle);
        imgRecy = findViewById(R.id.recy_un_me_info_img);
        ll_info = findViewById(R.id.ll_info);
        voice_recorder = findViewById(R.id.voice_recorder);
        ll_provide = findViewById(R.id.ll_provide);
        img_company_logo = findViewById(R.id.img_company_logo);
        ll_need = findViewById(R.id.ll_need);
        ll_field = findViewById(R.id.ll_field);
        ll_know = findViewById(R.id.ll_know);
        ll_interest = findViewById(R.id.ll_interest);
        ll_city = findViewById(R.id.ll_city);
        ll_school = findViewById(R.id.ll_school);
        ll_address = findViewById(R.id.ll_stay_address);
        ll_company_empty = findViewById(R.id.ll_company_empty);
        ll_web = findViewById(R.id.ll_web);
        tv_text_provide = findViewById(R.id.tv_text_provide);
        tv_text_need = findViewById(R.id.tv_text_need);
        tv_img_need = findViewById(R.id.tv_img_need);
        tv_text_info = findViewById(R.id.tv_text_info);
        tv_img_info = findViewById(R.id.tv_img_info);
        tv_goto_web = findViewById(R.id.tv_goto_web);
        tv_mall_model_name = findViewById(R.id.tv_mall_model_name);
        tv_company_model_name = findViewById(R.id.tv_company_model_name);
        tv_all_product = findViewById(R.id.tv_all_product);
        tv_see = findViewById(R.id.tv_see);
        tv_add_model = findViewById(R.id.tv_add_model);
        ll_add_model = findViewById(R.id.ll_add_model);
        tv_img_provide = findViewById(R.id.tv_img_provide);
        supplyRecy = findViewById(R.id.recy_un_card_supply);
        llneedEmpty = findViewById(R.id.img_un_card_demand_edt);
        ll_shop = findViewById(R.id.ll_shop);
        ll_add_need = findViewById(R.id.ll_add_need);
        needRecy = findViewById(R.id.recy_un_card_demand);
        rv_product = findViewById(R.id.rv_product);
        rv_custom_model = findViewById(R.id.rv_custom_model);
        otherAddLin = findViewById(R.id.lin_un_cd_other_add);
        ll_empty = findViewById(R.id.ll_empty);
        flowlayout_field = findViewById(R.id.flowlayout_field);
        flowlayout_know = findViewById(R.id.flowlayout_know);
        likeRecy = findViewById(R.id.recy_card_like);
        rvAddress = findViewById(R.id.rv_address);
        cityText = findViewById(R.id.tv_card_city);
        tv_company_name = findViewById(R.id.tv_company_name);
        schoolText = findViewById(R.id.tv_card_school);
        stayAddress = findViewById(R.id.tv_stay_address);
        addPosi = findViewById(R.id.add_cd_pos_title);
        addKnow = findViewById(R.id.add_cd_know_title);
        addLike = findViewById(R.id.add_cd_like_title);
        openVip = findViewById(R.id.tv_card_upgrade_vip);
        tvLuyin = findViewById(R.id.tv_luyin);
        emptyImg = findViewById(R.id.empty_img);
        emptyTv = findViewById(R.id.empty_tv);
        scorllview = findViewById(R.id.scorllview);
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
                    childStartForResult(HomeHtmlTextFragment.newIntance("个人信息", null), INFO_CODE);
                }
                break;

            case PRIVIDE_CODE:
                if (providePosition != -1) {
                    popsort = providePosition;
                }
                if (isImg) {
                    maxNum = 9;
                    choosePicture(PRIVIDE_CODE, maxNum);
                } else {
                    childStartForResult(HomeHtmlTextFragment.newIntance("我能提供", null), PRIVIDE_CODE);
                }
                break;

            case NEED_CODE:
                if (needPosition != -1) {
                    popsort = needPosition;
                }
                if (isImg) {
                    maxNum = 9;
                    choosePicture(NEED_CODE, maxNum);
                } else {
                    childStartForResult(HomeHtmlTextFragment.newIntance("我所需要的", null), NEED_CODE);
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
                case PRIVIDE_CODE:
                    if (provideAdapter.getData().size() > 0) {
                        int picNum = 0;
                        for (int i = 0; i < provideAdapter.getData().size(); i++) {
                            if (provideAdapter.getData().get(i).contentType.equals("2")) {
                                picNum++;
                            }
                        }
                        Num = max - picNum;
                    } else {
                        Num = max;
                    }
                    break;
                case NEED_CODE:
                    if (needAdapter.getData().size() > 0) {
                        int picNum = 0;
                        for (int i = 0; i < needAdapter.getData().size(); i++) {
                            if (needAdapter.getData().get(i).contentType.equals("2")) {
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

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(Num)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INFO_CODE:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
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
                }
                break;
            case PRIVIDE_CODE:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
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
                        selectProvideList.add(body);
                    }
                    mPresenter.upLoadFiles(selectProvideList, PRIVIDE_CODE);
                }
                break;
            case NEED_CODE:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
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
                        selectNeedList.add(body);
                    }
                    mPresenter.upLoadFiles(selectNeedList, NEED_CODE);
                }
                break;

            case CUSTOM_CODE:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
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
                }
                break;

        }
    }


    /**
     * 针对保存个人信息接口
     * 创建请求数据  图片
     *
     * @param type 默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示7.宣传视频 ）
     */
    private void createRequest(final List<FileBody> uplist, final int type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SaveCardModelBody model = new SaveCardModelBody();
                model.setBelongType(1);
                List<IntroductionContent> lists = new ArrayList<>();
                for (int i = 0; i < uplist.size(); i++) {
                    IntroductionContent content = new IntroductionContent();
                    content.setContentType("2");
                    content.setGraphContent(uplist.get(i).getFile());
                    content.setSort(i);
                    content.setBelongType("1");
                    content.setDefualtModular(type + "");
                    lists.add(content);
                }
                List<IntroductionContent> endlist = new ArrayList<>();
                switch (type) {
                    case 1:
                        model.setModularName("个人信息");
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
                    case 2:
                        model.setModularName("我能提供");
                        model.setSort(2);
                        model.setId(moderId);
                        endlist = provideAdapter.getData();
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
                    case 3:
                        model.setModularName("我所需要的");
                        model.setSort(3);
                        model.setId(moderId);
                        endlist = needAdapter.getData();
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
     * @param type 默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示7.宣传视频 ）
     */
    private void createSortRequest(int type, boolean isTop, int position) {
        SaveCardModelBody model = new SaveCardModelBody();
        model.setBelongType(1);
        List<IntroductionContent> endlist = new ArrayList<>();
        switch (type) {
            case 1:
                model.setModularName("个人信息");
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
            case 2:
                model.setModularName("我能提供");
                model.setSort(2);
                model.setId(moderId);
                endlist = provideAdapter.getData();
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
            case 3:
                model.setModularName("我所需要的");
                model.setSort(3);
                model.setId(moderId);
                endlist = needAdapter.getData();
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
     * @param type 默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示 7.宣传视频）
     */
    private void createTextRequest(String text, int type) {
        SaveCardModelBody model = new SaveCardModelBody();
        model.setBelongType(1);
        List<IntroductionContent> lists = new ArrayList<>();
        IntroductionContent content = new IntroductionContent();
        content.setContentType("1");
        content.setGraphContent(text);
        if (popsort != -1) {
            content.setSort(popsort);
        }
        content.setBelongType("1");
        content.setDefualtModular(type + "");
        lists.add(content);
        List<IntroductionContent> endlist = new ArrayList<>();
        switch (type) {
            case 1:
                model.setModularName("个人信息");
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
            case 2:
                model.setModularName("我能提供");
                model.setSort(2);
                model.setId(moderId);
                endlist = provideAdapter.getData();
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
            case 3:
                model.setModularName("我所需要的");
                model.setSort(3);
                model.setId(moderId);
                endlist = needAdapter.getData();
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
            case "1"://个人信息
                if (data.getParagraphDTOList().size() > 0) {
                    llinfoEmpty.setVisibility(View.GONE);
                    ll_add_info.setVisibility(View.VISIBLE);
                    divider_info.setVisibility(View.GONE);
                    imgRecy.setVisibility(View.VISIBLE);
                } else {
                    llinfoEmpty.setVisibility(View.VISIBLE);
                    ll_add_info.setVisibility(View.GONE);
                    divider_info.setVisibility(View.VISIBLE);
                    imgRecy.setVisibility(View.VISIBLE);
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
            case "2"://提供
                if (data.getParagraphDTOList().size() > 0) {
                    llsupplyEmpty.setVisibility(View.GONE);
                    ll_add_provide.setVisibility(View.VISIBLE);
                    divider_provide.setVisibility(View.GONE);
                    supplyRecy.setVisibility(View.VISIBLE);
                } else {
                    llsupplyEmpty.setVisibility(View.VISIBLE);
                    ll_add_provide.setVisibility(View.GONE);
                    divider_provide.setVisibility(View.VISIBLE);
                    supplyRecy.setVisibility(View.VISIBLE);
                }
                provideAdapter.setId(data.getId());
                if (isUpdate || popShow != -1) {
                    provideAdapter.setNewData(data.getParagraphDTOList());
                } else {
                    List<IntroductionContent> list = provideAdapter.getData();
                    list.addAll(data.getParagraphDTOList());
                    provideAdapter.setNewData(sortList(list));
                }
                break;

            case "3"://需要的
                if (data.getParagraphDTOList().size() > 0) {
                    llneedEmpty.setVisibility(View.GONE);
                    ll_add_need.setVisibility(View.VISIBLE);
                    divider_need.setVisibility(View.GONE);
                    needRecy.setVisibility(View.VISIBLE);
                } else {
                    llneedEmpty.setVisibility(View.VISIBLE);
                    ll_add_need.setVisibility(View.GONE);
                    divider_need.setVisibility(View.VISIBLE);
                    needRecy.setVisibility(View.VISIBLE);
                }
                needAdapter.setId(data.getId());
                if (isUpdate || popShow != -1) {
                    needAdapter.setNewData(data.getParagraphDTOList());
                } else {
                    List<IntroductionContent> list = needAdapter.getData();
                    list.addAll(data.getParagraphDTOList());
                    needAdapter.setNewData(sortList(list));
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
