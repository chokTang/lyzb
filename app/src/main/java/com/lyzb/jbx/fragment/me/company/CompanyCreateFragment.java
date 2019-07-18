package com.lyzb.jbx.fragment.me.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.other.LogUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.AddComdAdapter;
import com.lyzb.jbx.dialog.BusinessDialog;
import com.lyzb.jbx.fragment.base.BaseVideoToolbarFrgament;
import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.AddComdImgModel;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.me.company.CompanyModelModel;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.mvp.presenter.me.AddComdPresenter;
import com.lyzb.jbx.mvp.view.me.IAddComdView;
import com.lyzb.jbx.util.AppPreference;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 创建企业(编辑企业)  15012702634
 * @time 2019 2019/3/8 16:51
 */

public class CompanyCreateFragment extends BaseVideoToolbarFrgament<AddComdPresenter> implements IAddComdView {

    private final static String TYPE_KEY = "TYPE_KEY";
    private final static String COMD_ID = "COMD_ID";

    /***来源类型 2:创建  3:详情**/
    private int fromType = 0;

    private String comdId = null;

    EditText nameEdt;
    TextView typeText;

    TextView infoText;
    ImageView videoImg;
    ImageView videoCancle;

    NiceVideoPlayer videoPlays;

    ImageView infoImg;
    RecyclerView infoRecy;

    TextView honrText;
    ImageView honrImg;
    RecyclerView honrRecy;

    TextView goodText;
    ImageView goodImg;
    RecyclerView goodRecy;

    TextView mobleText;
    EditText addressEdt;
    TextView addComdImg;

    private String typeId = "";

    private AddComdAdapter infoAdapter = null;
    private AddComdAdapter honrAdapter = null;
    private AddComdAdapter goodAdapter = null;
    private ComdPhoneDialog mPheDialog = null;      //输入电话

    private int isMeComd = 99;

    private List<LocalMedia> videoList = new ArrayList<>();

    private ComdDetailModel mDetModel = null;

    /****
     * 区分 创建  详情
     * @param comdType 2:创建 3:详情
     * @param comdId  企业id
     * @return
     */
    public static CompanyCreateFragment newItance(int comdType, String comdId) {
        CompanyCreateFragment fragment = new CompanyCreateFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_KEY, comdType);
        args.putString(COMD_ID, comdId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            fromType = bundle.getInt(TYPE_KEY);
            comdId = bundle.getString(COMD_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();

        nameEdt = findViewById(R.id.edt_comd_name);
        typeText = findViewById(R.id.tv_comd_type);

        infoText = findViewById(R.id.tv_comd_info_text);
        videoImg = findViewById(R.id.img_comd_video);
        videoCancle = findViewById(R.id.img_comd_video_cancle);
        videoPlays = findViewById(R.id.video_un_play);

        infoImg = findViewById(R.id.img_comd_info);
        infoRecy = findViewById(R.id.recy_comd_info);

        honrText = findViewById(R.id.tv_comd_honr_text);
        honrImg = findViewById(R.id.img_comd_honr);
        honrRecy = findViewById(R.id.recy_comd_honr);

        goodText = findViewById(R.id.tv_comd_good_text);
        goodImg = findViewById(R.id.img_comd_good);
        goodRecy = findViewById(R.id.recy_comd_good);

        mobleText = findViewById(R.id.tv_comd_moble);
        addressEdt = findViewById(R.id.edt_comd_address);
        addComdImg = findViewById(R.id.img_add_comd);

        mPheDialog = new ComdPhoneDialog();

        infoAdapter = new AddComdAdapter(getContext(), null);
        infoRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        infoRecy.setAdapter(infoAdapter);
        infoRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        //行业类型选择
        typeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getInducList();
            }
        });

        videoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加视频
                chooseVideo();
            }
        });

        videoCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除视频提示
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
                                if (videoModel.size() > 0) {
                                    deleteVideo.clear();

                                    AddComdImgModel model = new AddComdImgModel();
                                    model.setImgId(videoModel.get(0).getImgId());
                                    model.setImgUrl(videoModel.get(0).getImgUrl());
                                    model.setImgType(6);

                                    deleteVideo.add(model);
                                }


                                videoCancle.setVisibility(View.GONE);
                                videoPlays.setVisibility(View.GONE);

                                videoImg.setScaleType(ImageView.ScaleType.CENTER);
                                videoImg.setImageResource(R.mipmap.card_com_video);
                                videoImg.setVisibility(View.VISIBLE);
                            }
                        })
                        .show(getFragmentManager(), "");
            }
        });


        //简介信息
        infoRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_info_img:

                        if (isMeComd == 1) {
                            startForResult(AddComdImgEdtFragment.newIntance(1, infoText.getText().toString(), infoModel), 1011);
                        }

                        break;
                }
            }
        });

        //荣誉
        honrAdapter = new AddComdAdapter(getContext(), null);
        honrRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        honrRecy.setAdapter(honrAdapter);
        honrRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        honrRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_info_img:
                        if (isMeComd == 1) {
                            startForResult(AddComdImgEdtFragment.newIntance(2, honrText.getText().toString(), honrModel), 1022);
                        }

                        break;
                }
            }
        });

        //产品展示
        goodAdapter = new AddComdAdapter(getContext(), null);
        goodRecy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        goodRecy.setAdapter(goodAdapter);
        goodRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        goodRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_info_img:
                        if (isMeComd == 1) {
                            startForResult(AddComdImgEdtFragment.newIntance(3, goodText.getText().toString(), goodModel), 1033);
                        }
                        break;
                }
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromType == 2) {
                    AlertDialogFragment.newIntance()
                            .setKeyBackable(false)
                            .setCancleable(false)
                            .setContent("编辑信息未完成,确定要返回?")
                            .setCancleBtn(null)
                            .setSureBtn(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pop();
                                }
                            })
                            .show(getFragmentManager(), "AddFragment");
                } else {
                    pop();
                }
            }
        });

        infoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(AddComdImgEdtFragment.newIntance(1, null, null), 1011);
            }
        });

        honrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(AddComdImgEdtFragment.newIntance(2, null, null), 1022);
            }
        });

        goodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(AddComdImgEdtFragment.newIntance(3, null, null), 1033);
            }
        });

        //电话添加
        mobleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPheDialog.show(getFragmentManager(), "");
            }
        });

        mPheDialog.setListener(new ComdPhoneDialog.ClickListener() {
            @Override
            public void onYes(String text) {
                mobleText.setText(text);
                mPheDialog.dismiss();
            }
        });

        //位置信息
        if (fromType == 2) {
            addressEdt.setText(AppPreference.getIntance().getMeAddress());
        }

        addComdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComd();
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        if (fromType == 3) {
            mPresenter.getComdInfo(comdId);
            setToolbarTitle("企业详情");
        } else {
            setToolbarTitle("创建企业");
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_company_create;
    }

    @Override
    public void getTypeList(List<BusinessModel> list) {
        BusinessDialog.Companion
                .newIntance()
                .setMaxSelectd(1)
                .setList(list)
                .invoke(new BusinessDialog.ClickListener() {
                    @Override
                    public void click(@org.jetbrains.annotations.Nullable View v, @org.jetbrains.annotations.Nullable List<BusinessModel> list) {
                        typeId = list.get(0).getId() + "";
                        typeText.setText(list.get(0).getName());
                    }
                }).show(getFragmentManager(), "SelectList");
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

    private void initCamera() {
        PictureSelector.create(CompanyCreateFragment.this)
                .openCamera(PictureMimeType.ofVideo())
                .previewImage(false)
                .compress(true)// 是否压缩 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void initVideo() {
        PictureSelector.create(CompanyCreateFragment.this)
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


    private List<CardComdModel.ComdFile> videozz = new ArrayList<>();
    private List<AddComdImgModel> deleteVideo = new ArrayList<>();

    @Override
    public void getVideo(final String videoUrl) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                videoCancle.setVisibility(View.VISIBLE);
                                playVideo(videoUrl);

                                //提交视频信息到接口
                                CardComdModel.GsDistributorVoBean bean = new CardComdModel.GsDistributorVoBean();
                                bean.setId(comdId);

                                List<CardComdModel.ComdFile> videos = new ArrayList<>();        //新增的视频model

                                CardComdModel.ComdFile mvideo = new CardComdModel.ComdFile();
                                mvideo.setDelStatus(0);
                                mvideo.setDistributorId(comdId);
                                mvideo.setFilePath(videoUrl);
                                mvideo.setFileType(2);
                                mvideo.setId(null);
                                mvideo.setTagType(4);
                                videos.add(mvideo);

                                videozz.addAll(videos);

                                List<CardComdModel.ComdFile> videoList = new ArrayList<>();     //记录删除视频的model
                                //删除视频的记录
                                if (deleteVideo.size() > 0) {

                                    List<CardComdModel.ComdFile> videoFile = new ArrayList<>();
                                    for (int i = 0; i < deleteVideo.size(); i++) {
                                        CardComdModel.ComdFile video = new CardComdModel.ComdFile();
                                        video.setDelStatus(1);
                                        video.setDistributorId(comdId);
                                        video.setFilePath(deleteVideo.get(i).getImgUrl());
                                        video.setFileType(2);
                                        video.setId(deleteVideo.get(i).getImgId());
                                        video.setTagType(4);

                                        videoFile.add(video);
                                    }
                                    videoList.addAll(videoFile);
                                }

                                videozz.addAll(videoList);
                            }
                        });
                    }
                }

        ).start();
    }

    @Override
    public void getData(final ComdDetailModel model) {
        if (model.getGsDistributorVo() == null) {
            return;
        }

        mDetModel = model;

        addComdImg.setText("保存编辑");
        isMeComd = model.getGsDistributorVo().getIsDefault();


        nameEdt.setText(model.getGsDistributorVo().getCompanyName());
        typeText.setText(model.getGsDistributorVo().getIndustryName());
        //赋值企业类型
        typeId = model.getGsDistributorVo().getIndustryId();

        if (!TextUtils.isEmpty(model.getGsDistributorVo().getIntroduction())) {
            infoText.setText(model.getGsDistributorVo().getIntroduction());
        }

        if (!TextUtils.isEmpty(model.getGsDistributorVo().getCulture())) {
            honrText.setText(model.getGsDistributorVo().getCulture());
        }

        if (!TextUtils.isEmpty(model.getGsDistributorVo().getBrand())) {
            goodText.setText(model.getGsDistributorVo().getBrand());
        }

        if (!TextUtils.isEmpty(model.getGsDistributorVo().getDisTel())) {
            mobleText.setText(model.getGsDistributorVo().getDisTel());
        }

        //地址
        addressEdt.setText(model.getGsDistributorVo().getDisAddress());

        if (model.getDistributorFiles().size() > 0) {

            for (int i = 0; i < model.getDistributorFiles().size(); i++) {

                if (model.getDistributorFiles().get(i).getTagType() == 1) {

                    //简介
                    AddComdImgModel info = new AddComdImgModel();
                    info.setImgUrl(model.getDistributorFiles().get(i).getFilePath());
                    info.setImgId(model.getDistributorFiles().get(i).getId());
                    info.setIsMeComd(isMeComd);

                    infoModel.add(info);

                } else if (model.getDistributorFiles().get(i).getTagType() == 2) {

                    //荣誉
                    AddComdImgModel honr = new AddComdImgModel();
                    honr.setImgUrl(model.getDistributorFiles().get(i).getFilePath());
                    honr.setImgId(model.getDistributorFiles().get(i).getId());
                    honr.setIsMeComd(isMeComd);

                    honrModel.add(honr);

                } else if (model.getDistributorFiles().get(i).getTagType() == 3) {

                    //品牌
                    AddComdImgModel good = new AddComdImgModel();
                    good.setImgUrl(model.getDistributorFiles().get(i).getFilePath());
                    good.setImgId(model.getDistributorFiles().get(i).getId());
                    good.setIsMeComd(isMeComd);

                    goodModel.add(good);

                } else if (model.getDistributorFiles().get(i).getTagType() == 4) {
                    //视频
                    videoModel.clear();

                    //视频
                    AddComdImgModel video = new AddComdImgModel();
                    video.setImgType(6);
                    video.setImgId(model.getDistributorFiles().get(i).getId());
                    video.setImgUrl(model.getDistributorFiles().get(i).getFilePath());

                    videoModel.add(video);
                }
            }

            if (videoModel.size() > 0) {

                videoImg.setVisibility(View.GONE);
                videoCancle.setVisibility(View.VISIBLE);
                //加载网络地址视频 - 播放
                playVideo(videoModel.get(0).getImgUrl());
            }

            if (infoModel.size() > 0) {
                infoImg.setVisibility(View.GONE);
                infoRecy.setVisibility(View.VISIBLE);

                infoAdapter.update(infoModel);
            }

            if (honrModel.size() > 0) {
                honrImg.setVisibility(View.GONE);
                honrRecy.setVisibility(View.VISIBLE);

                honrAdapter.update(honrModel);
            }

            if (goodModel.size() > 0) {
                goodImg.setVisibility(View.GONE);
                goodRecy.setVisibility(View.VISIBLE);

                goodAdapter.update(goodModel);
            }
        }

    }

    @Override
    public void getModelData(CompanyModelModel model) {

    }

    @Override
    public void getModelDataFail() {

    }

    @Override
    public void onFinshBack() {
        pop();
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

    private void addComd() {
        if (TextUtils.isEmpty(nameEdt.getText().toString().trim())) {
            showToast("请输入企业名称");
            return;
        }

        if (TextUtils.isEmpty(typeId)) {
            showToast("请选择企业类型");
            return;
        }

        CardComdModel comdModel = new CardComdModel();

        CardComdModel.GsDistributorVoBean bean = new CardComdModel.GsDistributorVoBean();
        bean.setIntroduction(infoText.getText().toString().trim());
        bean.setCompanyName(nameEdt.getText().toString().trim());
        bean.setBrand(goodText.getText().toString().trim());
        bean.setCulture(honrText.getText().toString().trim());

        if (!TextUtils.isEmpty(mobleText.getText().toString().trim())) {
            bean.setDisTel(mobleText.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(addressEdt.getText().toString().trim())) {
            bean.setDisAddress(addressEdt.getText().toString().trim());
        }

        if (fromType == 3) {
            bean.setId(comdId);
        } else {
            bean.setId(null);
        }

        bean.setIndustryId(typeId + "");
        bean.setIndustryName(typeText.getText().toString());
        comdModel.setGsDistributorVo(bean);

        //企业的其他信息(视频 简介 荣誉 品牌)集合
        List<CardComdModel.ComdFile> comdList = new ArrayList<>();


        comdList.addAll(videozz);

        if (infoModel.size() > 0) {

            //企业的简介信息 图片 集合
            List<CardComdModel.ComdFile> infoList = new ArrayList<>();
            for (int i = 0; i < infoModel.size(); i++) {
                CardComdModel.ComdFile info = new CardComdModel.ComdFile();
                info.setDelStatus(0);
                info.setDistributorId(null);
                info.setFilePath(infoModel.get(i).getImgUrl());
                info.setFileType(1);
                info.setId(null);
                info.setTagType(1);

                infoList.add(info);
            }
            comdList.addAll(infoList);
        }

        if (deleteInfo.size() > 0) {
            // 简介 删除图片记录-集合
            List<CardComdModel.ComdFile> deleteInfs = new ArrayList<>();
            for (int i = 0; i < deleteInfo.size(); i++) {
                CardComdModel.ComdFile delete = new CardComdModel.ComdFile();
                delete.setDelStatus(1);
                delete.setDistributorId(comdId);
                delete.setFilePath(deleteInfo.get(i).getImgUrl());
                delete.setFileType(1);
                delete.setId(deleteInfo.get(i).getImgId());
                delete.setTagType(1);

                deleteInfs.add(delete);
            }

            comdList.addAll(deleteInfs);
        }


        if (honrModel.size() > 0) {
            //企业的荣誉信息集合
            List<CardComdModel.ComdFile> honrList = new ArrayList<>();
            for (int i = 0; i < honrModel.size(); i++) {
                CardComdModel.ComdFile honr = new CardComdModel.ComdFile();

                honr.setDelStatus(0);
                honr.setDistributorId(null);
                honr.setFilePath(honrModel.get(i).getImgUrl());
                honr.setFileType(1);
                honr.setId(null);
                honr.setTagType(2);

                honrList.add(honr);
            }
            comdList.addAll(honrList);
        }

        if (deleteHonr.size() > 0) {
            //企业荣誉 删除的图片记录
            List<CardComdModel.ComdFile> deleteHs = new ArrayList<>();
            for (int i = 0; i < deleteHonr.size(); i++) {
                CardComdModel.ComdFile honr = new CardComdModel.ComdFile();

                honr.setDelStatus(1);
                honr.setDistributorId(comdId);
                honr.setFilePath(deleteHonr.get(i).getImgUrl());
                honr.setFileType(1);
                honr.setId(deleteHonr.get(i).getImgId());
                honr.setTagType(2);

                deleteHs.add(honr);
            }
            comdList.addAll(deleteHs);
        }

        if (goodModel.size() > 0) {
            //企业的产品展示信息集合
            List<CardComdModel.ComdFile> goodList = new ArrayList<>();
            for (int i = 0; i < goodModel.size(); i++) {
                CardComdModel.ComdFile good = new CardComdModel.ComdFile();

                good.setDelStatus(0);
                good.setDistributorId(null);
                good.setFilePath(goodModel.get(i).getImgUrl());
                good.setFileType(1);
                good.setId(null);
                good.setTagType(3);

                goodList.add(good);
            }
            comdList.addAll(goodList);
        }

        if (deleteGood.size() > 0) {
            //企业 产品信息 图片删除记录
            List<CardComdModel.ComdFile> deleteGds = new ArrayList<>();
            for (int i = 0; i < deleteGood.size(); i++) {
                CardComdModel.ComdFile good = new CardComdModel.ComdFile();

                good.setDelStatus(1);
                good.setDistributorId(comdId);
                good.setFilePath(deleteGood.get(i).getImgUrl());
                good.setFileType(1);
                good.setId(deleteGood.get(i).getImgId());
                good.setTagType(3);

                deleteGds.add(good);
            }

            comdList.addAll(deleteGds);
        }

        comdModel.setDistributorFiles(comdList);

        if (fromType == 3) {
            comdModel.setOptType("upt");
        } else {
            comdModel.setOptType("add");
        }
        comdModel.setDiff("dis");

        mPresenter.addComdInfo(false, comdModel);
    }

    @Override
    public void onCardComd() {

    }

    @Override
    public void joinSuccess() {

    }

    @Override
    public void joinFail() {

    }

    @Override
    public void getInfo(List<FileBody> uplist) {

    }

    @Override
    public void getHonor(List<FileBody> uplist) {

    }

    @Override
    public void getBrand(List<FileBody> uplist) {

    }

    @Override
    public void getCustom(List<FileBody> uplist) {

    }

    @Override
    public void saveInfo(String string, int type, boolean isImg) {

    }

    @Override
    public void deleteModelContent(String string, int type) {

    }

    @Override
    public void deleteModel(String string) {

    }

    @Override
    public void update(String string, int type, String content) {

    }



    private List<AddComdImgModel> videoModel = new ArrayList<>();
    private List<AddComdImgModel> infoModel = new ArrayList<>();
    private List<AddComdImgModel> honrModel = new ArrayList<>();
    private List<AddComdImgModel> goodModel = new ArrayList<>();

    private List<AddComdImgModel> deleVideo = new ArrayList<>();
    private List<AddComdImgModel> deleteInfo = new ArrayList<>();
    private List<AddComdImgModel> deleteHonr = new ArrayList<>();
    private List<AddComdImgModel> deleteGood = new ArrayList<>();


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
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
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {
                case 1011:

                    infoImg.setVisibility(View.GONE);

                    infoRecy.setVisibility(View.VISIBLE);
                    infoText.setVisibility(View.VISIBLE);

                    infoText.setText(data.getString("AddComdText"));
                    infoModel = (List<AddComdImgModel>) data.getSerializable("AddComdImg");
                    deleteInfo = (List<AddComdImgModel>) data.getSerializable("DeleImg");

                    LogUtil.logd("新增简介图片:" + infoModel.size());
                    LogUtil.logd("删除简介图片:" + deleteInfo.size());

                    infoAdapter.update(infoModel);
                    break;
                case 1022:

                    honrImg.setVisibility(View.GONE);
                    honrRecy.setVisibility(View.VISIBLE);
                    honrText.setVisibility(View.VISIBLE);

                    honrText.setText(data.getString("AddComdText"));
                    honrModel = (List<AddComdImgModel>) data.getSerializable("AddComdImg");
                    deleteHonr = (List<AddComdImgModel>) data.getSerializable("DeleImg");

                    LogUtil.logd("新增荣誉图片:" + honrModel.size());
                    LogUtil.logd("删除荣誉图片:" + deleteHonr.size());

                    honrAdapter.update(honrModel);
                    break;
                case 1033:

                    goodImg.setVisibility(View.GONE);
                    goodRecy.setVisibility(View.VISIBLE);
                    goodText.setVisibility(View.VISIBLE);

                    goodText.setText(data.getString("AddComdText"));
                    goodModel = (List<AddComdImgModel>) data.getSerializable("AddComdImg");
                    deleteGood = (List<AddComdImgModel>) data.getSerializable("DeleImg");

                    LogUtil.logd("新增产品图片:" + goodModel.size());
                    LogUtil.logd("删除产品图片:" + deleteGood.size());

                    goodAdapter.update(goodModel);
                    break;
            }
        }
    }
}
