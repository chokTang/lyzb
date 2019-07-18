package com.lyzb.jbx.fragment.circle;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.inter.IPermissonResultListener;
import com.like.utilslib.image.BitmapUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.DynamicCircleItemListAdapter;
import com.lyzb.jbx.dialog.SendDialog;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.fragment.send.UnionSendTWFragment;
import com.lyzb.jbx.fragment.send.UnionSendVideoFragment;
import com.lyzb.jbx.model.circle.CircleDetailItemModel;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.mvp.presenter.me.CircleDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.ICircleDynamictView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.ImageUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 圈子-动态
 * @time 2019 2019/4/15 17:27
 */

public class CircleDynamicFragment extends BaseVideoFrgament<CircleDynamicPresenter>
        implements ICircleDynamictView, OnLoadMoreListener, IPermissonResultListener {
    /**
     * 1普通圈子动态，2企业动态,3成员动态,4新闻
     */
    private final static String INTENTKEY_TYPE = "intentkey_type";

    /**
     * id,圈子的时候为圈子id，新闻的时候为企业id
     */
    private final static String INTENTKEY_ID = "intentkey_id";
    /**
     * 要查询的用户的id
     */
    private final static String INTENTKEY_LOGINUSERID = "intentkey_loginuserid";

    @BindView(R.id.recy_circle_dynamic)
    RecyclerView mRecyCircleDynamic;
    @BindView(R.id.lin_circle_dynamic)
    View linCircleDynamic;
    Unbinder unbinder;

    private DynamicCircleItemListAdapter mDynamicAdapter = null;
    /**
     * id,圈子的时候为圈子id，新闻的时候为企业id
     */
    private String id;
    /**
     * 1普通圈子动态，2企业动态,3成员动态,4新闻
     */
    private int type;
    /**
     * 要查询的用户的id
     */
    private String loginUserId;
    private boolean isLoadMoreData = false;

    /**
     * @param id          圈子的时候为圈子id，新闻的时候为企业id
     * @param type        1普通圈子动态，2企业动态,3成员动态,4新闻
     * @param loginUserId 要查询的用户的id
     * @return
     */
    public static CircleDynamicFragment newIntance(String id, int type, String loginUserId) {
        CircleDynamicFragment fragment = new CircleDynamicFragment();
        Bundle args = new Bundle();
        args.putInt(INTENTKEY_TYPE, type);
        args.putString(INTENTKEY_ID, id);
        args.putString(INTENTKEY_LOGINUSERID, loginUserId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle args = getArguments();
        if (args != null) {
            type = args.getInt(INTENTKEY_TYPE);
            id = args.getString(INTENTKEY_ID);
            loginUserId = args.getString(INTENTKEY_LOGINUSERID);
        }
        return rootView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getCircleDynamicList(true, id, type, loginUserId);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mDynamicAdapter = new DynamicCircleItemListAdapter(getContext(), null);
        mDynamicAdapter.setLayoutManager(mRecyCircleDynamic);
        mRecyCircleDynamic.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));

        mRecyCircleDynamic.setAdapter(mDynamicAdapter);

        mRecyCircleDynamic.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof DynamicCircleItemListAdapter.VideoHolder) {
                    NiceVideoPlayer niceVideoPlayer = ((DynamicCircleItemListAdapter.VideoHolder) holder).videoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

        mPresenter.getCircleDynamicList(true, id, type, loginUserId);

        mRecyCircleDynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition == layoutManager.getItemCount() - 1) {
                    if (isLoadMoreData) {
                        // showToast("暂无更多数据了~~~");
                    } else {
                        mPresenter.getCircleDynamicList(false, id, type, loginUserId);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mRecyCircleDynamic.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, final View view, final int position) {
                view.setTag("");
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Object tag = view.getTag();
                        if (tag == null || TextUtils.isEmpty(tag.toString())) {
                            CircleDetailItemModel model = mDynamicAdapter.getPositionModel(position);
                            childStart(DynamicDetailFragment.Companion.newIntance(model.getId()));
                        }
                    }
                }, 50);
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                CircleDetailItemModel model = mDynamicAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //点赞
                    case R.id.layout_zan_number:
                        if (App.getInstance().isLogin()) {
                            if (model.getGiveLike() > 0) {
                                mPresenter.onCancleZan(model.getId(), position);
                            } else {
                                mPresenter.onZan(model.getId(), position);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;

                    //一张图的时候 加载大图
                    case R.id.layout_first:
                        List<String> urllist = new ArrayList<>();
                        for (int i = 0; i < mDynamicAdapter.getPositionModel(position).getFileList().size(); i++) {
                            DynamicFileModel model1 = (DynamicFileModel) mDynamicAdapter.getPositionModel(position).getFileList().get(i);
                            urllist.add(model1.getFile());
                        }
                        ImageUtil.Companion.statPhotoViewActivity(getContext(), 0, urllist);
                        break;

                    //已关注
                    case R.id.tv_follow:
                        if (App.getInstance().isLogin()) {
                            mPresenter.onDynamciFollowUser(model.getCreateMan(), 0, position);
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //未关注
                    case R.id.tv_no_follow:

                        if (App.getInstance().isLogin()) {
                            if (mDynamicAdapter.getPositionModel(position).getConcern() > 0) {//在线沟通
                                Intent intent = new Intent(getContext(), ImCommonActivity.class);
                                ImHeaderGoodsModel model1 = new ImHeaderGoodsModel();

                                model1.setChatType(EaseConstant.CHATTYPE_SINGLE);
                                model1.setShopImName("jbx" + mDynamicAdapter.getPositionModel(position).getCreateMan());
                                model1.setShopName(mDynamicAdapter.getPositionModel(position).getUserName());
                                model1.setShopHeadimg(mDynamicAdapter.getPositionModel(position).getHeadimg());
                                model1.setShopId("");

                                Bundle args = new Bundle();
                                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model1);
                                intent.putExtras(args);
                                startActivity(intent);
                            } else {//关注某人
                                mPresenter.onDynamciFollowUser(model.getCreateMan(), 1, position);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }

                        break;
                    //点击头像
                    case R.id.img_header:
                        childStart(CardFragment.newIntance(2, model.getCreateMan()));
                        break;
                    //分享
                    case R.id.layout_share_number:
                        LinearLayout parentView = (LinearLayout) view.getParent().getParent();
                        Bitmap bitmap = BitmapUtil.createViewBitmap(parentView);
                        bitmap = BitmapUtil.zoomMaxImage(bitmap, 500, 400);//分享的图片配置
                        AppCommonUtil.startAdapterShare(getContext(), model.getId(), model.getCreateMan(), model.getUserName(), BitmapUtil.bitmap2Bytes(bitmap));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_circledynamic;
    }

    @Override
    public void onDataList(boolean isRefresh, List<CircleDetailItemModel> dynamicModelList) {
        if (isRefresh) {
            mDynamicAdapter.update(dynamicModelList);
        } else {
            mDynamicAdapter.addAll(dynamicModelList);
        }

        if (mDynamicAdapter.getItemCount() > 0 && dynamicModelList.size() < 10) {
            isLoadMoreData = true;
            mDynamicAdapter.add(new CircleDetailItemModel(true));
        }
        if (mDynamicAdapter.getItemCount() == 0) {
            linCircleDynamic.setVisibility(View.VISIBLE);
        } else {
            linCircleDynamic.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFishOrLoadMore(boolean isfresh) {
        if (isfresh) {
//            mSrCircleDynamic.finishRefresh();
        } else {
//            mSrCircleDynamic.finishLoadMore();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        //不用管
    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {
        //不用管
    }

    @Override
    public void onZanResult(int position) {
        CircleDetailItemModel model = mDynamicAdapter.getPositionModel(position);
        model.setGiveLike(model.getGiveLike() == 0 ? 1 : 0);
        model.setUpCount(model.getGiveLike() > 0 ? model.getUpCount() + 1 : model.getUpCount() - 1);
        mDynamicAdapter.change(position, model);
    }

    @Override
    public void onFollowItemResult(int position) {
        CircleDetailItemModel model = mDynamicAdapter.getPositionModel(position);
        model.setRelationNum(model.getConcern() == 0 ? 1 : 0);
        model.setConcern(model.getConcern() == 0 ? 1 : 0);
        mDynamicAdapter.change(position, model);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getCircleDynamicList(false, id, type, loginUserId);
    }

    public void onAddDynamic() {
        //点击发布按钮
        onRequestPermisson(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                this);

    }

    private SendDialog dialog;
    private List<LocalMedia> videoList = new ArrayList<>();

    @Override
    public void onSuccess() {
        if (App.getInstance().isLogin()) {
            dialog = new SendDialog();
            dialog.invoke(new SendDialog.ClickListener() {
                @Override
                public void click(@org.jetbrains.annotations.Nullable View v) {
                    switch (v.getId()) {
                        case R.id.tv_send_tw:
                            //图文
                            childStart(UnionSendTWFragment.Companion.newIntance("sendCircle", id));
                            break;
                        case R.id.tv_send_ps:
                            //拍摄
                            initCamera();
                            break;
                        case R.id.tv_send_video:
                            //视频
                            initVideo();
                            break;
                        case R.id.tv_send_active:
                            //活动建设中
                            //start(new PerfectOneFragment());
                            break;
                        default:
                            break;
                    }
                }
            });
            dialog.show(getFragmentManager(), "ABC");
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onFail(List<String> fail) {
        showToast("授权失败!");
    }

    /**
     * 初始化视频选择  录制视频
     */
    private void initVideo() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .selectionMedia(videoList)// 是否传入已选图片 List<LocalMedia> list
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(60)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(2)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .forResult(PictureConfig.TYPE_VIDEO);//结果回调onActivityResult code
    }

    private void initCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofVideo())
                .previewImage(false)
                .compress(true)// 是否压缩 true or false
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(60)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(2)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            // 图片、视频、音频选择结果回调
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            // adapter.setList(selectList);
            // adapter.notifyDataSetChanged();
            if (selectList.get(0).getDuration() == 0) {//照片
                childStart(UnionSendTWFragment.Companion.newIntance(id, selectList.get(0)));
            } else {//视频
                childStart(UnionSendVideoFragment.Companion.newIntanceByCircle(id, selectList.get(0)));
            }
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
