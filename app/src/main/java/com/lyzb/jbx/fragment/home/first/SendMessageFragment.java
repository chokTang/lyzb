package com.lyzb.jbx.fragment.home.first;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.Iconify;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.inter.IPermissonResultListener;
import com.like.utilslib.screen.DensityUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.SendMessageActivity;
import com.lyzb.jbx.adapter.home.first.SendMessagePicAdapter;
import com.lyzb.jbx.model.dynamic.RequestBodyComment;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.mvp.presenter.home.first.SendMessagePresenter;
import com.lyzb.jbx.mvp.view.home.first.ISendMessageView;
import com.szy.yishopcustomer.Util.LubanImg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SendMessageFragment extends BaseFragment<SendMessagePresenter> implements ISendMessageView {
    private View btnBack;
    private RecyclerView rv_pic;
    private EditText edit_send_tw;

    private int maxNum = 9; //最大9张图片
    private List<LocalMedia> list = new ArrayList<>();
    private SendMessagePicAdapter mSendMessagePicAdapter;

//    private ItemTouchHelper itemTouchHelper;


    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击空白区返回上一个界面
                hideSoftInput();
                if (getBaseActivity() instanceof SendMessageActivity) {
                    getBaseActivity().finish();
                } else {
                    pop();
                }
            }
        });
        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_send_tw.getText().toString().trim()) && mSendMessagePicAdapter.getItemCount() == 0) {
                    showToast("请输入评论内容");
                    return;
                }
                hideSoftInput();
                if (mSendMessagePicAdapter.getItemCount() > 0) {
                    mPresenter.upFileLoads(mSendMessagePicAdapter.getList());
                } else {
                    OnUploadResult(null);
                }
            }
        });
        edit_send_tw = findViewById(R.id.edit_send_tw);
        rv_pic = findViewById(R.id.rv_pic);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {//添加图片
            @Override
            public void onClick(View v) {

                onRequestPermisson(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        new IPermissonResultListener() {

                            @Override
                            public void onSuccess() {//成功后
                                hideSoftInput();
                                choosePicture();
                            }

                            @Override
                            public void onFail(List<String> fail) {
                                showToast("授权失败!");
                            }
                        });
            }
        });

        rv_pic.addOnItemTouchListener(new OnRecycleItemClickListener() {
//            @Override
//            public void onItemLongClick(BaseRecyleAdapter adapter, View view, int position) {
//                super.onItemLongClick(adapter, view, position);
//                itemTouchHelper.startDrag(getVidewHolder(rv_pic, position));
//            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.img_delete://删除
                        mSendMessagePicAdapter.remove(position);
                        break;
                }
            }
        });

        //recycle拖拽
        itemTouchHelper.attachToRecyclerView(rv_pic);
        rv_pic.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemLongClick(BaseRecyleAdapter adapter, View view, int position) {
                itemTouchHelper.startDrag(rv_pic.getChildViewHolder(view));
            }
        });
    }


    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return ItemTouchHelper.Callback.makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mSendMessagePicAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            Collections.swap(mSendMessagePicAdapter.getList(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dpTopx(100), DensityUtil.dpTopx(100));
                viewHolder.itemView.findViewById(R.id.pic).setLayoutParams(layoutParams);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }


        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            super.clearView(recyclerView, viewHolder);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dpTopx(85), DensityUtil.dpTopx(85));
            viewHolder.itemView.findViewById(R.id.pic).setLayoutParams(layoutParams);
        }


        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    });

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (edit_send_tw != null) {
            edit_send_tw.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftInput(edit_send_tw);
                }
            }, 50);

        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mSendMessagePicAdapter = new SendMessagePicAdapter(getContext(), null);
        mSendMessagePicAdapter.setLayoutManager(rv_pic, LinearLayoutManager.HORIZONTAL);
        rv_pic.setAdapter(mSendMessagePicAdapter);

        edit_send_tw.setHint(Iconify.compute(getContext(), "{iconpig}写评论......", edit_send_tw));
    }

    @Override
    public void pop() {
        super.pop();
        hideSoftInput();
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_send_message;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                list = PictureSelector.obtainMultipleResult(data);
                List<FileBody> bodies = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    FileBody fileBody = new FileBody();
                    fileBody.setFile(list.get(i).getPath());
                    bodies.add(fileBody);
                }
                mSendMessagePicAdapter.update(bodies);
                break;
        }
    }

    private RequestBodyComment getRequestBodyComment(List<FileBody> bodies) {
        RequestBodyComment bodyComment = new RequestBodyComment();
        RequestBodyComment.GsTopicCommentBean bean = new RequestBodyComment.GsTopicCommentBean();
        bean.setContent(edit_send_tw.getText().toString().trim());

        if (bodies != null && bodies.size() > 0) {
            List<RequestBodyComment.FileList> fileLists = new ArrayList<>();
            for (int i = 0; i < bodies.size(); i++) {
                RequestBodyComment.FileList fileList = new RequestBodyComment.FileList();
                fileList.file = bodies.get(i).getFile();
                fileLists.add(fileList);
            }
            bodyComment.setFileList(fileLists);
        }

        bodyComment.setGsTopicComment(bean);

        return bodyComment;
    }

    /**
     * 查找当前下标显示的的item
     *
     * @param position
     * @return
     */
    private BaseViewHolder getVidewHolder(RecyclerView recyclerView, int position) {
        if (recyclerView.getChildCount() <= 0) {
            return null;
        }
        View v = recyclerView.getLayoutManager().findViewByPosition(position);
        if (v == null) {
            return null;
        }
        return (BaseViewHolder) recyclerView.getChildViewHolder(v);

    }

    private void choosePicture() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxNum)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .compressSavePath(LubanImg.getPath())//压缩图片保存地址
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void OnUploadResult(List<FileBody> fileBodies) {
        hideSoftInput();

        Intent intent = new Intent();
        intent.putExtra("RequestBodyComment", getRequestBodyComment(fileBodies));
        getBaseActivity().setResult(Activity.RESULT_OK, intent);
        if (getBaseActivity() instanceof SendMessageActivity) {
            getBaseActivity().finish();
        } else {
            pop();
        }
    }
}
