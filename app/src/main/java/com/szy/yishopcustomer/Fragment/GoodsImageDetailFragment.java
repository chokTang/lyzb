package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Adapter.GoodsDescAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsDesc.MobileDescModel;
import com.szy.yishopcustomer.ResponseModel.GoodsDesc.Model;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.MyWebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhfieng on 2017/2/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsImageDetailFragment extends LazyFragment {

    @BindView(R.id.fragment_goods_desc_webView)
    WebView mWebView;
    @BindView(R.id.fragment_goods_desc_listView)
    ListView mListView;
    @BindView(R.id.relativeLayout_empty)
    LinearLayout mEmptyView;
    @BindView(R.id.empty_view_titleTextView)
    TextView mEmptyTitle;


    private List<MobileDescModel> mAdapterData;
    private GoodsDescAdapter mGoodsDescAdapter;

//    private String sku_id;
//    private String goods_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_image_detail;
        initId();
    }

    private void initId() {
        Bundle arguments = getArguments();
//        sku_id = arguments.getString(Key.KEY_SKU_ID.getValue());
//        goods_id = arguments.getString(Key.KEY_GOODS_ID.getValue());
//        if (!Utils.isNull(sku_id)) {
//            getGoodsDesc(sku_id);
//        } else if (!Utils.isNull(goods_id)) {
//            getSkuId(goods_id);
//        } else {
//            Toast.makeText(getContext(), "该商品已下架", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mAdapterData = new ArrayList<>();
        mGoodsDescAdapter = new GoodsDescAdapter(getActivity(),mAdapterData);

        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm
                .SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        // 添加js交互接口类，并起别名 imagelistner

        mWebView.addJavascriptInterface(new com.szy.yishopcustomer.View
                .JavascriptInterface(getActivity()), MyWebViewClient
                .FLAG_IMAGE);
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.setWebChromeClient(new WebChromeClient());

        return view;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GOODS_DESC:
                updateGoodsDesc(response);
                break;
            case HTTP_GET_SKU_ID:
//                HttpResultManager.resolve(response, ResponseGoodsModel.class, new
//                        HttpResultManager.HttpResultCallBack<ResponseGoodsModel>() {
//                    @Override
//                    public void onSuccess(ResponseGoodsModel mData) {
//                        sku_id = mData.data.goods.sku_id;
//                        getGoodsDesc(sku_id);
//                    }
//                }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void getGoodsDesc(String skuId) {
        CommonRequest mGoodsDescRequest = new CommonRequest(Api.API_GOODS_DESC, HttpWhat
                .HTTP_GOODS_DESC.getValue());
        mGoodsDescRequest.add("sku_id", skuId);
        mGoodsDescRequest.alarm = false;
        addRequest(mGoodsDescRequest);
    }

    private void getSkuId(String goodsId) {
        CommonRequest mGoodsDetailRequest = new CommonRequest(Config.BASE_URL + "/goods/" +
                goodsId, HttpWhat.HTTP_GET_SKU_ID.getValue());
        mGoodsDetailRequest.alarm = false;
        addRequest(mGoodsDetailRequest);
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_GOODS_DESC:
                String uuid = event.getMessage();

                String thisUUID = null;
                if(getActivity() instanceof GoodsActivity) {
                    thisUUID = ((GoodsActivity)getActivity()).getUUID();
                }

                if(uuid.equals(thisUUID)) {
                    updateGoodsDesc(event.getMessageSource());
                }

                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    void updateGoodsDesc(String source){

        Model model = JSON.parseObject(source, Model.class);
        if (model != null) {
            if (model.need_load) {
                mEmptyView.setVisibility(View.VISIBLE);
                mEmptyTitle.setText(R.string.emptyGodosDesc);
            } else {
                mEmptyView.setVisibility(View.GONE);
                if (model.desc_type != 1) {
                    mWebView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);

                    mWebView.loadData(Utils.imgContentSetMaxHeight(model.pc_desc),
                            "text/html; charset=UTF-8", null);
                } else {
                    mWebView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    for (MobileDescModel mobileDescModel : model.mobile_desc) {
                        mAdapterData.add(mobileDescModel);
                    }
                    mGoodsDescAdapter.setData(mAdapterData);
                    mListView.setAdapter(mGoodsDescAdapter);
                }
            }

        }
    }
}
