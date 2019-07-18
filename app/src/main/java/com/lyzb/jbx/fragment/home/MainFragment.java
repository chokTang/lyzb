package com.lyzb.jbx.fragment.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.inter.IPermissonResultListener;
import com.like.utilslib.screen.DensityUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lyzb.jbx.R;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.SendDialog;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.fragment.common.WebViewFragment;
import com.lyzb.jbx.fragment.message.MessageFragment;
import com.lyzb.jbx.fragment.send.UnionSendTWFragment;
import com.lyzb.jbx.fragment.send.UnionSendVideoFragment;
import com.lyzb.jbx.model.eventbus.AccountPerfectEventBus;
import com.lyzb.jbx.model.eventbus.LonginInEventBus;
import com.lyzb.jbx.model.eventbus.MainIndexEventBus;
import com.lyzb.jbx.model.eventbus.MessageEventBus;
import com.lyzb.jbx.mvp.presenter.home.MainPersenter;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.widget.CusPopWindow;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Util.App;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 首页fragment
 * Created by shidengzhong on 2019/3/4.
 */

public class MainFragment extends BasePhotoFragment<MainPersenter> implements View.OnClickListener {

    private static final int RESULT_LOGIN = 0x652;//点击我的时候
    private static final int RESULT_LOGIN_MESSAGE = 0x656;//点击消息时候
    private static final int PERMISSION_TW = 0x963;//图文权限
    private static final int PERMISSION_CAMERA = 0x962;//拍照
    private static final int PERMISSION_VIDEO = 0x961;//视频

    private SendDialog dialog;
    private TextView tv_home_first;
    private TextView tv_home_second;
    private TextView tv_home_five;
    private TextView tv_home_four;
    private TextView tv_home_three;
    private TextView tv_message_unread;
    private List<TextView> mTextList = new ArrayList<>();
    private List<BaseFragment> fragmentList = new ArrayList<>();

    private List<LocalMedia> videoList = new ArrayList<>();

    CusPopWindow popWindow;
    Timer timer;
    private int index = 0;//显示哪个fragment
    private int mCurrentIndex = index;//显示了哪个
    private boolean isShowRefresh = false;//是否处于刷新状态
    public boolean isInviseble = false;//页面是否失去焦点

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        tv_home_first = (TextView) findViewById(R.id.tv_home_first);
        tv_home_second = (TextView) findViewById(R.id.tv_home_second);
        tv_home_five = (TextView) findViewById(R.id.tv_home_five);
        tv_home_four = (TextView) findViewById(R.id.tv_home_four);
        tv_home_three = (TextView) findViewById(R.id.tv_home_three);
        tv_message_unread = (TextView) findViewById(R.id.tv_message_unread);


        if (popWindow == null) {
            View contenView = LayoutInflater.from(getContext()).inflate(R.layout.layout_send_hint, null, false);

            popWindow = new CusPopWindow.PopupWindowBuilder(getActivity())
                    .setView(contenView)
                    .setOutsideTouchable(true)
                    .create();
        }

        tv_home_first.setOnClickListener(this);
        tv_home_second.setOnClickListener(this);
        tv_home_three.setOnClickListener(this);
        tv_home_four.setOnClickListener(this);
        tv_home_five.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mTextList.add(tv_home_first);
        mTextList.add(tv_home_second);
        mTextList.add(tv_home_four);
        mTextList.add(tv_home_five);

        fragmentList.add(new HomeFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(WebViewFragment.newIntance(UrlConfig.H5_DISCOUNT));
        fragmentList.add(new MeFragment());

        loadMultipleRootFragment(R.id.home_bottom_container, index, fragmentList.toArray(new ISupportFragment[4]));
        clickItem(index);

        //小于三次用户提示 弹出对话框
        if (AppPreference.getIntance().getKeyHintThree() < 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                popWindow.showAsDropDown(tv_home_three, 0, -DensityUtil.dpTopx(100), Gravity.TOP);
            }
            //弹出对话框
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }, 5000);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    popWindow.dissmiss();
                    break;
            }
        }
    };


    @Override
    public Object getResId() {
        return R.layout.fragment_home_main;
    }

    /**
     * 点击了哪个
     *
     * @param postion
     */
    private void clickItem(int postion) {
        showHideFragment(fragmentList.get(postion), fragmentList.get(mCurrentIndex));
        mCurrentIndex = postion;
        for (int i = 0; i < 4; i++) {
            if (i == postion) {
                mTextList.get(i).setSelected(true);
            } else {
                mTextList.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //主页
            case R.id.tv_home_first:
                if (isShowRefresh) {
                    HomeFragment homeFragment = findChildFragment(HomeFragment.class);
                    if (homeFragment != null) {
                        homeFragment.sroclloToTop();
                    }
                    isHomeRefresh(false);
                }
                clickItem(0);
                break;
            //消息页面
            case R.id.tv_home_second:
                if (App.getInstance().isLogin()) {
                    fristHomeNomal();
                    clickItem(1);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), RESULT_LOGIN_MESSAGE);
                }
                break;
            //发布
            case R.id.tv_home_three:
                onRequestPermisson(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        new IPermissonResultListener() {
                            @Override
                            public void onSuccess() {
                                if (App.getInstance().isLogin()) {
                                    dialog = new SendDialog();
                                    dialog.invoke(new SendDialog.ClickListener() {
                                        @Override
                                        public void click(@org.jetbrains.annotations.Nullable View v) {
                                            switch (v.getId()) {
                                                case R.id.tv_send_tw://图文
                                                    start(new UnionSendTWFragment());
                                                    break;
                                                case R.id.tv_send_ps://拍摄
                                                    initCamera();
                                                    break;
                                                case R.id.tv_send_video://视频
                                                    initVideo();
                                                    break;
                                                case R.id.tv_send_active://活动建设中
//                                                    start(new PerfectOneFragment());
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
                        });


                break;
            //商城
            case R.id.tv_home_four:
                //clickItem(2);
                Intent tgIntent = new Intent(getContext(), RootActivity.class);
                startActivity(tgIntent);
                break;
            //我的
            case R.id.tv_home_five:
                if (App.getInstance().isLogin()) {
                    fristHomeNomal();
                    clickItem(3);
//                    if (App.getInstance().isUserInfoPer) {
//                        clickItem(3);
//                    } else {
//                        startActivity(new Intent(getContext(), PerfectActivity.class));
//                    }
                } else {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), RESULT_LOGIN);
                }
                break;
            default:
                break;
        }
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
                .videoMaxSecond(120)// 显示多少秒以内的视频or音频也可适用 int
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
        switch (requestCode) {
            case RESULT_LOGIN:
                if (resultCode == RESULT_OK) {
                    clickItem(3);
//                    if (App.getInstance().isUserInfoPer) {
//                        clickItem(3);
//                    } else {
//                        startActivity(new Intent(getContext(), PerfectActivity.class));
//                    }
                }
                break;
            case RESULT_LOGIN_MESSAGE:
                if (resultCode == RESULT_OK) {
                    clickItem(1);
                }
                break;
            case PictureConfig.TYPE_VIDEO:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    start(UnionSendVideoFragment.Companion.newIntance(selectList.get(0)));
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    if (selectList.get(0).getDuration() == 0) {//照片
                        start(UnionSendTWFragment.Companion.newIntance(selectList.get(0)));
                    } else {//视频
                        start(UnionSendVideoFragment.Companion.newIntance(selectList.get(0)));
                    }
                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showIndexFragment(MainIndexEventBus eventBus) {
        clickItem(eventBus.getIndex());
        HomeFragment fragment = findChildFragment(HomeFragment.class);
        if (fragment != null) {
            fragment.showRecommendFragment();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogonIn(LonginInEventBus eventBus) {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoReadMessage(MessageEventBus eventBus) {
        if (eventBus.getNumber() > 0) {
            tv_message_unread.setText(String.valueOf(eventBus.getNumber()));
            tv_message_unread.setVisibility(View.VISIBLE);
        } else {
            tv_message_unread.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetAccountIsPrefresh(AccountPerfectEventBus eventBus) {
        mPresenter.getAccuontIsPerfect();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (App.getInstance().isLogin()) {
            int number = EMClient.getInstance().chatManager().getUnreadMessageCount();
            if (number > 0) {
                tv_message_unread.setText(String.valueOf(number));
                tv_message_unread.setVisibility(View.VISIBLE);
            } else {
                tv_message_unread.setVisibility(View.GONE);
            }
        } else {
            tv_message_unread.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 是否显示刷新的按钮
     *
     * @param misShowRefresh
     */
    public synchronized void isHomeRefresh(final boolean misShowRefresh) {
        if (isInviseble) return;
        if (misShowRefresh) {
            if (!isShowRefresh) {
                tv_home_first.setText("刷新");
                tv_home_first.setCompoundDrawablesWithIntrinsicBounds(null,
                        ContextCompat.getDrawable(getContext(), R.drawable.union_home_refresh), null, null);
                isShowRefresh = true;
            }
        } else {
            if (isShowRefresh) {
                tv_home_first.setText("首页");
                tv_home_first.setCompoundDrawablesWithIntrinsicBounds(null,
                        ContextCompat.getDrawable(getContext(), R.drawable.selector_home_first), null, null);
                isShowRefresh = false;
            }
        }
    }

    /**
     * 还原首页
     */
    private void fristHomeNomal() {
        isInviseble = true;
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_home_first.setText("首页");
                tv_home_first.setCompoundDrawablesWithIntrinsicBounds(null,
                        ContextCompat.getDrawable(getContext(), R.drawable.selector_home_first), null, null);
                isShowRefresh = false;
            }
        }, 500);
    }
}
