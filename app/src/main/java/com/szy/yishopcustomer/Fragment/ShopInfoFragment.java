package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;

import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.ResponseModel.Common.ResponseCommonStringModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.HybridWebViewActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.ShopInfoAdapter;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Shop.ShopModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ShopInfo.ShopInfoImageModel;
import com.szy.yishopcustomer.ViewModel.ShopInfo.ShopInfoRateModel;
import com.szy.yishopcustomer.ViewModel.ShopInfo.ShopInfoTextModel;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2017/1/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoFragment extends YSCBaseFragment {

    private static final int REQUEST_WHAT_QR_CODE = 0;
    private static final int REQUEST_CODE_LOGIN = 1;

    @BindView(R.id.fragment_shop_info_recyclerView)
    RecyclerView mRecyclerView;

    ShopInfoAdapter mAdapter;
    ShopModel mShopModel;

    private String username;
    private String real;

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_PHONE:
                Utils.openPhoneDialog(getContext(), mShopModel.service_tel);
                break;
            case VIEW_TYPE_QR_CODE:
                getQrCode();
                break;
            case VIEW_TYPE_DETAIL:
                openLicense();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        final int topMargin = Utils.dpToPx(getContext(), 10);
        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                final int itemPosition = parent.getChildAdapterPosition(view);

                if (itemPosition == 0) {
                    outRect.top = topMargin;
                }
            }
        };
        mRecyclerView.addItemDecoration(mItemDecoration);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    openLicense();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mShopModel == null) {
            ((ShopActivity) getActivity()).refrshShopInfoFragment();
        }
    }


    public void refrsh(ShopModel shopModel, String uname, String special_aptitude) {
        mShopModel = shopModel;
        username = uname;
        real = special_aptitude;
        setUpAdapterData();
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (what) {
            case REQUEST_WHAT_QR_CODE:
                getQrCodeSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_info;
        mAdapter = new ShopInfoAdapter();
        mAdapter.onClickListener = this;
        Bundle arguments = getArguments();
        if (arguments != null) {
            mShopModel = arguments.getParcelable(Key.KEY_SHOP_INFO.getValue());
            username = arguments.getString("username");
            real = arguments.getString("real");
            setUpAdapterData();
        }

        if (mShopModel == null) {
            Toast.makeText(getContext(), R.string.pleaseEnterShopId, Toast.LENGTH_SHORT).show();
//            finish();
            return;
        }
    }

    Bitmap[] mBitmap = new Bitmap[1];

    private void getQrCode() {
//        CommonRequest request = new CommonRequest(Api.API_SHOP_QR_CODE, REQUEST_WHAT_QR_CODE);
//        request.add("id", mShopModel.shop_id);
//        addRequest(request);

        try {
            final String qrcodeStr = Config.BASE_URL.replaceAll("//[a-zA-Z]+.", "//m.") + "/shop/" + mShopModel.shop_id + ".html";


            if (mBitmap[0] == null) {
                ImageLoader.loadImage(Utils.urlOfImage(mShopModel.shop_image), new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);

                        View centerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_qrcode_center, null);
                        ImageView im = (ImageView) centerView.findViewById(R.id.imageView);
                        im.setImageBitmap(Utils.bimapRound(loadedImage, 10));
                        Bitmap bitmap = Bitmap.createBitmap(convertViewToBitmap(centerView));
                        mBitmap[0] = CodeUtils.createImage(qrcodeStr, 400, 400, Utils.bimapRound(bitmap, 10));
                        centerView.setDrawingCacheEnabled(false);     //禁用DrawingCahce否则会影响性能

                        Utils.qRCodeDialog(getActivity(), mBitmap[0], mShopModel.shop_name, getResources()
                                .getString(R.string.shareShopTip));
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, String failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                        mBitmap[0] = CodeUtils.createImage(qrcodeStr, 400, 400, null);
                        Utils.qRCodeDialog(getActivity(), mBitmap[0], mShopModel.shop_name, getResources()
                                .getString(R.string.shareShopTip));
                    }
                });
            } else {
                Utils.qRCodeDialog(getActivity(), mBitmap[0], mShopModel.shop_name, getResources()
                        .getString(R.string.shareShopTip));
            }
        } catch (Exception e) {

        }
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    private void openLicense() {
        if (App.getInstance().isLogin()) {
/*            Intent intent = new Intent();
            intent.setClass(getActivity(), ShopIndexLicenseActivity.class);
            startActivity(intent);*/
            openHybridWebView(Utils.getMallMBaseUrl() + "/shop/index/license.html?id=" + mShopModel.shop_id);
        } else {
            openLoginActivityForResult();
        }
    }

    private void openHybridWebView(String url) {
        Intent intent = new Intent(getActivity(), HybridWebViewActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), url);
        startActivity(intent);
    }

    private void openLoginActivityForResult() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    private void getQrCodeSucceed(String response) {
        HttpResultManager.resolve(response, ResponseCommonStringModel
                .class, new HttpResultManager.HttpResultCallBack<ResponseCommonStringModel>() {
            @Override
            public void onSuccess(ResponseCommonStringModel back) {
                Utils.qRCodeDialog(getActivity(), Utils.urlOfImage(back.data), "店铺二维码", getResources()
                        .getString(R.string.shareShopTip));
            }
        });
    }

    private void setUpAdapterData() {
        List<Object> list = new ArrayList<>();

        ShopInfoTextModel shopInfoTextModel = new ShopInfoTextModel();
        shopInfoTextModel.name = getString(R.string.shopManager);
        shopInfoTextModel.value = username;
        shopInfoTextModel.icon = R.mipmap.ic_dispensers;
        list.add(shopInfoTextModel);

        shopInfoTextModel = new ShopInfoTextModel();
        shopInfoTextModel.name = getString(R.string.shopPhone);
        shopInfoTextModel.value = TextUtils.isEmpty(mShopModel.service_tel) ? "暂无" : mShopModel.service_tel;
        shopInfoTextModel.icon = R.mipmap.tab_shop_contact_selected;
        shopInfoTextModel.type = ViewType.VIEW_TYPE_PHONE.ordinal();
        list.add(shopInfoTextModel);

        shopInfoTextModel = new ShopInfoTextModel();
        shopInfoTextModel.name = getString(R.string.shopQr);
        //shopInfoTextModel.value = mShopModel.simply_introduce;
        shopInfoTextModel.value = "";
        shopInfoTextModel.icon = R.mipmap.tab_shop_code;
        shopInfoTextModel.type = ViewType.VIEW_TYPE_QR_CODE.ordinal();
        list.add(shopInfoTextModel);

        shopInfoTextModel = new ShopInfoTextModel();
        shopInfoTextModel.name = getString(R.string.shopTime);
        shopInfoTextModel.value = mShopModel.duration_time;
        list.add(shopInfoTextModel);

        shopInfoTextModel = new ShopInfoTextModel();
        shopInfoTextModel.name = getString(R.string.address);
        shopInfoTextModel.value = mShopModel.region_name + " " + mShopModel.address;
        list.add(shopInfoTextModel);

        //工商执照
        if (mShopModel.shop_type.equals("2") || !Utils.isNull(real)) {
            int i = 0;
            if (mShopModel.shop_type.equals("2")) {
                i++;
            }
            if (!Utils.isNull(real)) {
                i++;
            }
            ShopInfoImageModel shopInfoImageModel = new ShopInfoImageModel();
            shopInfoImageModel.type = i;
            list.add(shopInfoImageModel);
        }

        ShopInfoRateModel shopInfoRateModel = new ShopInfoRateModel();
        shopInfoRateModel.descriptionRate = mShopModel.desc_score;
        shopInfoRateModel.expressRate = mShopModel.send_score;
        shopInfoRateModel.serviceRate = mShopModel.service_score;
        list.add(shopInfoRateModel);

        mAdapter.data.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
