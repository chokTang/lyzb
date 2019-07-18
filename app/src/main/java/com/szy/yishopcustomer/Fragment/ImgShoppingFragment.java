package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Adapter.HeaderFooterAdapter;
import com.szy.yishopcustomer.Adapter.ImgShoppingGoodsAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.LubanImg;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.szy.yishopcustomer.Util.Utils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.iwf.photopicker.PhotoPicker;
import me.zongren.pullablelayout.Constant.Side;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 拍照搜索
 */

public class ImgShoppingFragment extends YSCBaseFragment {


    @BindView(R.id.goodsList)
    CommonRecyclerView goodsList;
    @BindView(R.id.takePic)
    RelativeLayout takePic;
    Unbinder unbinder;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.emptyView)
    TextView emptyView;
    private ImgShoppingGoodsAdapter adapter;
    protected static final int REQUEST_CODE_CAMERA = 2;
    public int mListStyle = Macro.STYLE_GRID;
    private int page = 1;
    private boolean hasMore;
    private String imgUrl, tempUrl;
    private boolean isRequesting;

    private RecyclerView.OnScrollListener mGoodsRecyclerViewScrollListener = new RecyclerView
            .OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (!isRequesting && goodsList.reachEdgeOfSide(Side.BOTTOM) && hasMore) {
                searchGoods(tempUrl);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_imgshopping;
        Bundle bundle = getArguments();
        if (bundle != null) {
            imgUrl = bundle.getString(Key.KEY_URL.getValue(), "");
            tempUrl = bundle.getString(Key.KEY_TEMP_URL.getValue(), "");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ImgShoppingGoodsAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case HeaderFooterAdapter.TYPE_NORMAL:
                        return 1;
                    default:
                        return 2;
                }
            }
        });
        goodsList.setLayoutManager(layoutManager);
        goodsList.addOnScrollListener(mGoodsRecyclerViewScrollListener);
        final int mGridItemOffset = Utils.dpToPx(getContext(), 5);
        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                if (mListStyle == Macro.STYLE_GRID) {
                    outRect.top = mGridItemOffset;
                    if (itemPosition % 2 == 0) {
                        outRect.left = mGridItemOffset;
                        outRect.right = mGridItemOffset / 2;
                    } else {
                        outRect.left = mGridItemOffset / 2;
                        outRect.right = mGridItemOffset;
                    }
                } else {
                    outRect.top = 0;
                    outRect.left = 0;
                    outRect.bottom = 0;
                    outRect.right = 0;
                }
            }

        };
        goodsList.addItemDecoration(mItemDecoration);
        adapter.onCLickListener = this;
        goodsList.setAdapter(adapter);
        if (!TextUtils.isEmpty(tempUrl)) {
            upLoadImg(tempUrl);
        }
    }

    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                openGoodsActivity(position);
                break;
        }
    }

    private void openGoodsActivity(int position) {
        if (adapter == null) {
            return;
        }
        GoodsModel goodsModel = (GoodsModel) adapter.data.get(position);
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsModel.goods_id);
        startActivity(intent);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_IMG_SHOPPING:
                if (page == 1) {
                    adapter.data.clear();
                    adapter.setFooterView(null);
                }
                try {
                    JSONObject object = JSONObject.parseObject(response).getJSONObject("data");
                    List<GoodsModel> mGoodsModelList = JSON.parseArray(object.getString("list"), GoodsModel.class);
                    if (mGoodsModelList != null && mGoodsModelList.size() > 0) {
                        adapter.data.addAll(mGoodsModelList);
                        page++;
                        hasMore = true;
                    } else {
                        hasMore = false;
                        //设置footer
                        View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
                        adapter.setFooterView(footer);
                    }
                } catch (Exception e) {

                }
                if (adapter.data.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onRequestFinish(int what) {
        super.onRequestFinish(what);
        isRequesting = false;
    }

    private void searchGoods(String imgUrl) {
        this.tempUrl = imgUrl;
        CommonRequest searchRequest = new CommonRequest(Api.API_IMG_SHOPPING, HttpWhat.HTTP_IMG_SHOPPING.getValue());
        RequestAddHead.addHead(searchRequest, getActivity(), Api.API_IMG_SHOPPING, "GET");
        searchRequest.add("msg", imgUrl);
        searchRequest.add("page", page);
        searchRequest.add("size", 24);
        searchRequest.alarm = true;
        addRequest(searchRequest);
        isRequesting = true;
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

    @OnClick(R.id.takePic)
    public void onViewClicked() {
        File picPath = new File(imgUrl);
        Uri uri = null;
        if (picPath.exists()) {
            uri = Uri.fromFile(picPath);
            initUCrop(uri);
        }
//        selectPicFromCamera();
    }

    public void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(getActivity(), R.color.aliwx_black));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(getActivity(), R.color.aliwx_black));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);
        options.setFreeStyleCropEnabled(true);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(getActivity(), this);
    }

    File cameraFile;

    protected void selectPicFromCamera() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(true)
                .start(getActivity(), PhotoPicker.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {
            upLoadImg(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0));
        } else if (requestCode == UCrop.REQUEST_CROP) {//UCrop裁剪之后的处理
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = UCrop.getOutput(data);
                tempUrl = RxPhotoTool.getRealFilePath(getContext(), resultUri);
                upLoadImg(tempUrl);
            }
        }
    }

    private void upLoadImg(String imgPath) {
        ImageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(imgPath), imageView);
        mProgress.show();
        Luban.with(getActivity())
                .load(imgPath)
                .ignoreBy(100)
                .setTargetDir(LubanImg.getPath())
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        Oss.getInstance().upLoadSearImg(getActivity(), file.getAbsolutePath(), new Oss.OssListener() {
                            @Override
                            public void onProgress(int progress) {

                            }

                            @Override
                            public void onSuccess(String url) {
                                searchGoods(url);
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(getContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                }).launch();

    }
}
