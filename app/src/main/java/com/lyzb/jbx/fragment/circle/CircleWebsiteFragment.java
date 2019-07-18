package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.AddComdAdapter;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.me.card.CardCompanyEditFragment;
import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.AddComdImgModel;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.me.company.CompanyModelModel;
import com.lyzb.jbx.model.me.company.UpdateCompanyMsg;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.mvp.presenter.me.AddComdPresenter;
import com.lyzb.jbx.mvp.view.me.IAddComdView;
import com.lyzb.jbx.util.ImageUtil;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 圈子详情-官网
 * @time 2019 2019/4/16 9:16
 */

public class CircleWebsiteFragment extends BaseVideoFrgament<AddComdPresenter>
        implements IAddComdView {

    private final static String INTENTKEY_COMPANYID_ID = "intentkey_companyid_id";

    @BindView(R.id.circle_website_video_paly)
    NiceVideoPlayer mCircleWebsiteVideoPaly;
    @BindView(R.id.circle_website_video_ll)
    LinearLayout mCircleWebsiteVideoLl;
    @BindView(R.id.circle_website_comd_info_tv)
    TextView mCircleWebsiteComdInfoTv;
    @BindView(R.id.circle_website_com_img_rec)
    RecyclerView mCircleWebsiteComImgRec;
    @BindView(R.id.circle_website_info_ll)
    LinearLayout mCircleWebsiteInfoLl;
    @BindView(R.id.circle_website_comd_honr_tv)
    TextView mCircleWebsiteComdHonrTv;
    @BindView(R.id.circle_website_com_honor_img_rec)
    RecyclerView mCircleWebsiteComHonorImgRec;
    @BindView(R.id.circle_website_honor_ll)
    LinearLayout mCircleWebsiteHonorLl;
    @BindView(R.id.circle_website_comd_good_tv)
    TextView mCircleWebsiteComdGoodTv;
    @BindView(R.id.circle_website_com_product_rec)
    RecyclerView mCircleWebsiteComProductRec;
    @BindView(R.id.circle_website_brand_ll)
    LinearLayout mCircleWebsiteBrandLl;
    @BindView(R.id.circle_website_comd_moble_tv)
    TextView mCircleWebsiteComdMobleTv;
    @BindView(R.id.circle_website_comd_phone_ll)
    LinearLayout mCircleWebsiteComdPhoneLl;
    @BindView(R.id.circle_website_comd_address_tv)
    TextView mCircleWebsiteComdAddressTv;
    @BindView(R.id.circle_website_comd_address_ll)
    LinearLayout mCircleWebsiteComdAddressLl;
    @BindView(R.id.circle_website_empty_ll)
    LinearLayout mCircleWebsiteEmptyLl;
    Unbinder unbinder;

    private String comdId = null;

    private int isMeComd = 99;

    private AddComdAdapter infoAdapter = null;
    private AddComdAdapter honrAdapter = null;
    private AddComdAdapter goodAdapter = null;

    public static CircleWebsiteFragment newIntance(String companyId) {
        CircleWebsiteFragment fragment = new CircleWebsiteFragment();
        Bundle args = new Bundle();
        args.putString(INTENTKEY_COMPANYID_ID, companyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            comdId = args.getString(INTENTKEY_COMPANYID_ID);
            onQuestCompant(comdId);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

        mCircleWebsiteInfoLl.setVisibility(View.GONE);
        mCircleWebsiteHonorLl.setVisibility(View.GONE);
        mCircleWebsiteBrandLl.setVisibility(View.GONE);
        mCircleWebsiteComdPhoneLl.setVisibility(View.GONE);
        mCircleWebsiteComdAddressLl.setVisibility(View.GONE);
        mCircleWebsiteVideoLl.setVisibility(View.GONE);
        mCircleWebsiteEmptyLl.setVisibility(View.VISIBLE);

        //处理滑动事件冲突
        mCircleWebsiteComImgRec.setNestedScrollingEnabled(false);
        mCircleWebsiteComHonorImgRec.setNestedScrollingEnabled(false);
        mCircleWebsiteComProductRec.setNestedScrollingEnabled(false);

        mCircleWebsiteVideoPaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleWebsiteVideoPaly.start();
            }
        });

        infoAdapter = new AddComdAdapter(getContext(), null);
        mCircleWebsiteComImgRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCircleWebsiteComImgRec.setAdapter(infoAdapter);
        mCircleWebsiteComImgRec.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        //简介信息
        mCircleWebsiteComImgRec.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_info_img:
                        if (isMeComd == 1) {
                            childStartForResult(CardCompanyEditFragment.newIntance(1, mCircleWebsiteComdInfoTv.getText().toString(), infoModel), 1011);
                        } else {
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < adapter.getList().size(); i++) {
                                AddComdImgModel model = (AddComdImgModel) adapter.getList().get(i);
                                urllist.add(model.getImgUrl());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getContext(), position, urllist);
                        }
                        break;
                    default:
                }
            }
        });

        //荣誉
        honrAdapter = new AddComdAdapter(getContext(), null);
        mCircleWebsiteComHonorImgRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCircleWebsiteComHonorImgRec.setAdapter(honrAdapter);
        mCircleWebsiteComHonorImgRec.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        mCircleWebsiteComHonorImgRec.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_info_img:
                        if (isMeComd == 1) {
                            childStartForResult(CardCompanyEditFragment.newIntance(2, mCircleWebsiteComdHonrTv.getText().toString(), honrModel), 1022);
                        } else {
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < adapter.getList().size(); i++) {
                                AddComdImgModel model = (AddComdImgModel) adapter.getList().get(i);
                                urllist.add(model.getImgUrl());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getContext(), position, urllist);
                        }

                        break;
                    default:
                }
            }
        });

        //产品展示
        goodAdapter = new AddComdAdapter(getContext(), null);
        mCircleWebsiteComProductRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCircleWebsiteComProductRec.setAdapter(goodAdapter);
        mCircleWebsiteComProductRec.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));

        mCircleWebsiteComProductRec.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_card_info_img:
                        if (isMeComd == 1) {
                            childStartForResult(CardCompanyEditFragment.newIntance(3, mCircleWebsiteComdGoodTv.getText().toString(), goodModel), 1033);
                        } else {
                            List<String> urllist = new ArrayList<>();
                            for (int i = 0; i < adapter.getList().size(); i++) {
                                AddComdImgModel model = (AddComdImgModel) adapter.getList().get(i);
                                urllist.add(model.getImgUrl());
                            }
                            ImageUtil.Companion.statPhotoViewActivity(getContext(), position, urllist);
                        }
                        break;
                    default:
                }
            }
        });

    }

    /**
     * 请求网络
     *
     * @param companyId
     */
    public void onQuestCompant(String companyId) {
        if (!TextUtils.isEmpty(companyId)) {
            comdId = companyId;
        }
        mPresenter.getComdInfo(companyId);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_circle_website;
    }

    @Override
    public void getVideo(String videoUrl) {

    }

    @Override
    public void getTypeList(List<BusinessModel> list) {

    }

    public void playVideo(String videoUrl) {


        mCircleWebsiteVideoPaly.setVisibility(View.VISIBLE);

        mCircleWebsiteVideoPaly.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        mCircleWebsiteVideoPaly.setUp(videoUrl, null);
        TxCustomVideoPlayerController controller = new TxCustomVideoPlayerController(getActivity());
        controller.setTitle("");

        Glide.with(getActivity())
                .setDefaultRequestOptions(new RequestOptions()
                        .frame(1)
                        .centerCrop()).load(videoUrl)
                .into(controller.imageView());

        mCircleWebsiteVideoPaly.setController(controller);

        controller.setDoVideoPlayer(new TxCustomVideoPlayerController.DoVideoPlayer() {
            @Override
            public void isPlay(boolean isplay, boolean isShow, boolean iscontroller) {
                if (isplay && !isShow) {
                    if (mCircleWebsiteVideoPaly.isIdle()) {
                        mCircleWebsiteVideoPaly.start();
                    } else {
                        if (mCircleWebsiteVideoPaly.isPlaying() || mCircleWebsiteVideoPaly.isBufferingPlaying()) {
                            mCircleWebsiteVideoPaly.pause();
                        } else if (mCircleWebsiteVideoPaly.isPaused() || mCircleWebsiteVideoPaly.isBufferingPaused()) {
                            mCircleWebsiteVideoPaly.restart();
                        }
                    }
                }
            }
        });

    }

    /**
     * 新加个判断数据是否有效
     */
    private boolean isNotData = true;

    @Override
    public void getData(ComdDetailModel model) {

        if (model.getGsDistributorVo() == null) {
            return;
        }

        isMeComd = 2;

        if (model.getGsDistributorVo() != null) {
            mCircleWebsiteEmptyLl.setVisibility(View.GONE);
            //企业电话
            if (!TextUtils.isEmpty(model.getGsDistributorVo().getDisTel())) {
                mCircleWebsiteComdPhoneLl.setVisibility(View.VISIBLE);
                mCircleWebsiteComdMobleTv.setText(model.getGsDistributorVo().getDisTel());
                isNotData = false;
            } else {
                mCircleWebsiteComdPhoneLl.setVisibility(View.GONE);
            }
            //企业地址
            if (!TextUtils.isEmpty(model.getGsDistributorVo().getDisAddress())) {
                mCircleWebsiteComdAddressLl.setVisibility(View.VISIBLE);
                mCircleWebsiteComdAddressTv.setText(model.getGsDistributorVo().getDisAddress());
                isNotData = false;
            } else {
                mCircleWebsiteComdAddressLl.setVisibility(View.GONE);
            }
        } else {
            //都没有
            mCircleWebsiteComdPhoneLl.setVisibility(View.GONE);
            mCircleWebsiteComdAddressLl.setVisibility(View.GONE);
        }


        infoModel.clear();
        honrModel.clear();
        goodModel.clear();

        videoModel.clear();

        if (model.getDistributorFiles().size() > 0) {
            //根据之前的逻辑：把企业的视频、简介、荣誉、品牌展示，展示出来，后面再做隐藏
            mCircleWebsiteEmptyLl.setVisibility(View.GONE);
            mCircleWebsiteInfoLl.setVisibility(View.VISIBLE);
            mCircleWebsiteHonorLl.setVisibility(View.VISIBLE);
            mCircleWebsiteBrandLl.setVisibility(View.VISIBLE);
            mCircleWebsiteVideoLl.setVisibility(View.VISIBLE);


            for (int i = 0; i < model.getDistributorFiles().size(); i++) {
                //简介
                if (model.getDistributorFiles().get(i).getTagType() == 1) {

                    AddComdImgModel info = new AddComdImgModel();
                    info.setImgUrl(model.getDistributorFiles().get(i).getFilePath());
                    info.setImgId(model.getDistributorFiles().get(i).getId());
                    info.setIsMeComd(isMeComd);

                    infoModel.add(info);
                }
                //荣誉
                if (model.getDistributorFiles().get(i).getTagType() == 2) {

                    AddComdImgModel honr = new AddComdImgModel();
                    honr.setImgUrl(model.getDistributorFiles().get(i).getFilePath());
                    honr.setImgId(model.getDistributorFiles().get(i).getId());
                    honr.setIsMeComd(isMeComd);

                    honrModel.add(honr);
                }
                //品牌
                if (model.getDistributorFiles().get(i).getTagType() == 3) {

                    AddComdImgModel good = new AddComdImgModel();
                    good.setImgUrl(model.getDistributorFiles().get(i).getFilePath());
                    good.setImgId(model.getDistributorFiles().get(i).getId());
                    good.setIsMeComd(isMeComd);

                    goodModel.add(good);
                }
                //视频
                if (model.getDistributorFiles().get(i).getTagType() == 4) {

                    AddComdImgModel video = new AddComdImgModel();
                    video.setImgType(6);
                    video.setImgId(model.getDistributorFiles().get(i).getId());
                    video.setImgUrl(model.getDistributorFiles().get(i).getFilePath());

                    videoModel.add(video);
                }
            }

            if (videoModel.size() > 0) {
                //加载网络地址视频 - 播放
                playVideo(videoModel.get(0).getImgUrl());
                isNotData = false;
            } else {
                mCircleWebsiteVideoLl.setVisibility(View.GONE);
            }

            //公司简介模块显示隐藏
            if (infoModel.size() > 0) {//有企业简介图片
                mCircleWebsiteComImgRec.setVisibility(View.VISIBLE);
                isNotData = false;
            } else {//没有图片
                if (!TextUtils.isEmpty(model.getGsDistributorVo().getIntroduction())) {//有文字
                    mCircleWebsiteComdInfoTv.setText(model.getGsDistributorVo().getIntroduction());
                    isNotData = false;
                } else {//既没图片也没文字
                    mCircleWebsiteInfoLl.setVisibility(View.GONE);
                }

            }

            //公司荣誉模块显示隐藏
            if (honrModel.size() > 0) {//有企业荣誉图片
                mCircleWebsiteComHonorImgRec.setVisibility(View.VISIBLE);
                isNotData = false;
            } else {
                if (!TextUtils.isEmpty(model.getGsDistributorVo().getCulture())) {//有文字
                    mCircleWebsiteComdHonrTv.setText(model.getGsDistributorVo().getCulture());
                    isNotData = false;
                } else {//既没图片也没文字
                    mCircleWebsiteHonorLl.setVisibility(View.GONE);
                }

            }
            //品牌展示模块显示隐藏
            if (goodModel.size() > 0) {//有企业品牌图片
                mCircleWebsiteComProductRec.setVisibility(View.VISIBLE);
                isNotData = false;
            } else {//图片
                if (!TextUtils.isEmpty(model.getGsDistributorVo().getBrand())) {//有文字
                    mCircleWebsiteComdGoodTv.setText(model.getGsDistributorVo().getBrand());
                    isNotData = false;
                } else {//既没有文字 也没有图片
                    mCircleWebsiteBrandLl.setVisibility(View.GONE);
                }

            }

        } else {//没有图片文字相关数据的话
            if (model.getGsDistributorVo() == null) {
                mCircleWebsiteInfoLl.setVisibility(View.GONE);
                mCircleWebsiteHonorLl.setVisibility(View.GONE);
                mCircleWebsiteBrandLl.setVisibility(View.GONE);

                mCircleWebsiteVideoLl.setVisibility(View.GONE);
                mCircleWebsiteEmptyLl.setVisibility(View.VISIBLE);
            }

        }

        if (videoModel.size() == 0) {
            mCircleWebsiteVideoPaly.setVisibility(View.GONE);
        }

        infoAdapter.update(infoModel);
        honrAdapter.update(honrModel);
        goodAdapter.update(goodModel);
        /**
         * 打个补丁
         */
        if (isNotData) {
            mCircleWebsiteInfoLl.setVisibility(View.GONE);
            mCircleWebsiteHonorLl.setVisibility(View.GONE);
            mCircleWebsiteBrandLl.setVisibility(View.GONE);

            mCircleWebsiteVideoLl.setVisibility(View.GONE);
            mCircleWebsiteEmptyLl.setVisibility(View.VISIBLE);
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

    }

    private List<AddComdImgModel> videoModel = new ArrayList<>();
    private List<AddComdImgModel> infoModel = new ArrayList<>();
    private List<AddComdImgModel> honrModel = new ArrayList<>();
    private List<AddComdImgModel> goodModel = new ArrayList<>();

    private List<AddComdImgModel> deleteInfo = new ArrayList<>();
    private List<AddComdImgModel> deleteHonr = new ArrayList<>();
    private List<AddComdImgModel> deleteGood = new ArrayList<>();

    @Override
    public void onCardComd() {
        mPresenter.getComdInfo(comdId);
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




    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if (data != null) {

            switch (requestCode) {
                case 1011:

                    infoModel.clear();
                    deleteInfo.clear();
                    mCircleWebsiteComImgRec.setVisibility(View.VISIBLE);

                    mCircleWebsiteComdInfoTv.setText(data.getString("AddComdText"));
                    infoModel = (List<AddComdImgModel>) data.getSerializable("AddComdImg");
                    deleteInfo = (List<AddComdImgModel>) data.getSerializable("DeleImg");

                    //简介信息 修改
                    CardComdModel inModel = new CardComdModel();
                    CardComdModel.GsDistributorVoBean bean = new CardComdModel.GsDistributorVoBean();
                    bean.setIntroduction(mCircleWebsiteComdInfoTv.getText().toString().trim());
                    bean.setId(comdId);
                    inModel.setGsDistributorVo(bean);

                    List<CardComdModel.ComdFile> comdList = new ArrayList<>();
                    if (infoModel.size() > 0) {
                        List<CardComdModel.ComdFile> infoList = new ArrayList<>();
                        for (int i = 0; i < infoModel.size(); i++) {
                            CardComdModel.ComdFile info = new CardComdModel.ComdFile();
                            info.setDelStatus(0);
                            info.setDistributorId(comdId);
                            info.setFilePath(infoModel.get(i).getImgUrl());
                            info.setFileType(1);

                            if (!TextUtils.isEmpty(infoModel.get(i).getImgId())) {
                                info.setId(infoModel.get(i).getImgId());
                            } else {
                                info.setId(null);
                            }

                            info.setTagType(1);

                            infoList.add(info);
                        }
                        comdList.addAll(infoList);
                    }

                    if (deleteInfo.size() > 0) {
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
                    inModel.setDistributorFiles(comdList);
                    inModel.setOptType("upt");
                    inModel.setDiff("card");

                    mPresenter.addComdInfo(true, inModel);
                    break;
                case 1022:

                    honrModel.clear();
                    deleteHonr.clear();

                    mCircleWebsiteComHonorImgRec.setVisibility(View.VISIBLE);

                    mCircleWebsiteComdHonrTv.setText(data.getString("AddComdText"));
                    honrModel = (List<AddComdImgModel>) data.getSerializable("AddComdImg");
                    deleteHonr = (List<AddComdImgModel>) data.getSerializable("DeleImg");

                    //荣誉信息
                    CardComdModel hnModel = new CardComdModel();
                    CardComdModel.GsDistributorVoBean hnBean = new CardComdModel.GsDistributorVoBean();
                    hnBean.setCulture(mCircleWebsiteComdHonrTv.getText().toString().trim());
                    hnBean.setId(comdId);

                    List<CardComdModel.ComdFile> hnList = new ArrayList<>();
                    if (honrModel.size() > 0) {

                        List<CardComdModel.ComdFile> infoList = new ArrayList<>();
                        for (int i = 0; i < honrModel.size(); i++) {
                            CardComdModel.ComdFile info = new CardComdModel.ComdFile();
                            info.setDelStatus(0);
                            info.setDistributorId(comdId);
                            info.setFilePath(honrModel.get(i).getImgUrl());
                            info.setFileType(1);

                            if (!TextUtils.isEmpty(honrModel.get(i).getImgId())) {
                                info.setId(honrModel.get(i).getImgId());
                            } else {
                                info.setId(null);
                            }

                            info.setTagType(2);

                            infoList.add(info);
                        }
                        hnList.addAll(infoList);
                    }

                    if (deleteHonr.size() > 0) {

                        List<CardComdModel.ComdFile> deleteInfs = new ArrayList<>();
                        for (int i = 0; i < deleteHonr.size(); i++) {
                            CardComdModel.ComdFile delete = new CardComdModel.ComdFile();
                            delete.setDelStatus(1);
                            delete.setDistributorId(comdId);
                            delete.setFilePath(deleteHonr.get(i).getImgUrl());
                            delete.setFileType(1);
                            delete.setId(deleteHonr.get(i).getImgId());
                            delete.setTagType(2);

                            deleteInfs.add(delete);
                        }

                        hnList.addAll(deleteInfs);
                    }
                    hnModel.setDistributorFiles(hnList);
                    hnModel.setOptType("upt");
                    hnModel.setDiff("card");

                    mPresenter.addComdInfo(true, hnModel);
                    break;
                case 1033:

                    goodModel.clear();
                    deleteGood.clear();

                    mCircleWebsiteComProductRec.setVisibility(View.VISIBLE);

                    mCircleWebsiteComdGoodTv.setText(data.getString("AddComdText"));
                    goodModel = (List<AddComdImgModel>) data.getSerializable("AddComdImg");
                    deleteGood = (List<AddComdImgModel>) data.getSerializable("DeleImg");

                    //产品信息
                    CardComdModel gdModel = new CardComdModel();
                    CardComdModel.GsDistributorVoBean gdBean = new CardComdModel.GsDistributorVoBean();
                    gdBean.setBrand(mCircleWebsiteComdGoodTv.getText().toString().trim());
                    gdBean.setId(comdId);


                    List<CardComdModel.ComdFile> gdList = new ArrayList<>();
                    if (goodModel.size() > 0) {

                        //企业的简介信息 图片 集合
                        List<CardComdModel.ComdFile> infoList = new ArrayList<>();
                        for (int i = 0; i < goodModel.size(); i++) {
                            CardComdModel.ComdFile info = new CardComdModel.ComdFile();
                            info.setDelStatus(0);
                            info.setDistributorId(comdId);
                            info.setFilePath(goodModel.get(i).getImgUrl());
                            info.setFileType(1);

                            if (!TextUtils.isEmpty(goodModel.get(i).getImgId())) {
                                info.setId(goodModel.get(i).getImgId());
                            } else {
                                info.setId(null);
                            }

                            info.setTagType(3);

                            infoList.add(info);
                        }
                        gdList.addAll(infoList);
                    }

                    if (deleteGood.size() > 0) {
                        // 简介 删除图片记录-集合
                        List<CardComdModel.ComdFile> deleteInfs = new ArrayList<>();
                        for (int i = 0; i < deleteGood.size(); i++) {
                            CardComdModel.ComdFile delete = new CardComdModel.ComdFile();
                            delete.setDelStatus(1);
                            delete.setDistributorId(comdId);
                            delete.setFilePath(deleteGood.get(i).getImgUrl());
                            delete.setFileType(1);
                            delete.setId(deleteGood.get(i).getImgId());
                            delete.setTagType(3);

                            deleteInfs.add(delete);
                        }

                        gdList.addAll(deleteInfs);
                    }

                    gdModel.setDistributorFiles(gdList);
                    gdModel.setOptType("upt");
                    gdModel.setDiff("card");
                    mPresenter.addComdInfo(true, gdModel);
                    break;

                case 1066:
                    //切换企业后的刷新
                    mPresenter.getComdInfo(data.getString("ComdId"));
                    break;
                default:

                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}