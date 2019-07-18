package com.szy.yishopcustomer.Fragment;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lyzb.jbx.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.AttributeIntegralActivity;
import com.szy.yishopcustomer.Activity.CheckoutIntegralActivity;
import com.szy.yishopcustomer.Activity.GoodsIntegralActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.RegisterBonusActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AttributeIntegralModel;
import com.szy.yishopcustomer.ResponseModel.Follow.ResponseFollowModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ResponseIntegralGoodsModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.CirclePageIndicator;
import com.szy.yishopcustomer.View.HeadWrapContentViewPager;
import com.szy.yishopcustomer.View.MyScrollView;
import com.szy.yishopcustomer.View.MyWebViewClient;
import com.szy.yishopcustomer.View.ScrollWebView;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by smart on 2017/12/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsIndexIntegralFragment extends YSCBaseFragment {

    private GoodsIndexFragment.OnClickListener onClickListener;

    @BindView(R.id.fragment_goods_index_scroll_view)
    MyScrollView mScrollView;

    @BindView(R.id.fragment_goods_thumb)
    HeadWrapContentViewPager mGoodsThumbViewPager;

    @BindView(R.id.fragment_goods_shop_info_layout)
    View fragment_goods_shop_info_layout;
    @BindView(R.id.fragment_goods_name)
    TextView mGoodsName;
    @BindView(R.id.fragment_goods_price)
    TextView mGoodsPrice;
    @BindView(R.id.fragment_goods_sale_number)
    TextView mGoodsSaleNumber;

    @BindView(R.id.linearlayout_goods_desc_line)
    View linearlayout_goods_desc_line;

    @BindView(R.id.fragment_goods_shop_logo)
    ImageView mShopLogo;
    @BindView(R.id.fragment_goods_shop_name)
    TextView mShopName;
    @BindView(R.id.fragment_goods_shop_type)
    TextView mShopType;

    @BindView(R.id.fragment_index_banner_pageIndicator)
    CirclePageIndicator pageIndicator;

    @BindView(R.id.fragmnet_goods_collect_button)
    LinearLayout mShopCollectButton;

    @BindView(R.id.fragment_goods_enter_shop_button)
    LinearLayout mShopEnterButton;


    private int goodsDescTop = 0;

    @BindView(R.id.webViewDesc)
    ScrollWebView mWebView;
    @BindView(R.id.go_up_button)
    ImageView mGoUp;

    private ArrayList<String> goods_banner;
    public String goods_id;

    private String quickBuyGoodsNumber;
    private String quickBuyGoodsId;
    public String shopId;
    private RefreshListener mRefreshListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_index_integral;

        goods_id = getActivity().getIntent().getStringExtra(Key.KEY_GOODS_ID.getValue());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        mGoUp.setVisibility(View.INVISIBLE);
        mGoUp.setOnClickListener(this);

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

        mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY >= goodsDescTop) {
                    onClickListener.onClickListener("1");
                    mGoUp.setVisibility(View.VISIBLE);
                } else {
                    onClickListener.onClickListener("0");
                    mGoUp.setVisibility(View.INVISIBLE);
                }
            }
        });


        linearlayout_goods_desc_line.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                linearlayout_goods_desc_line.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        goodsDescTop = linearlayout_goods_desc_line.getTop();
                    }
                }, 300);
            }
        });

        mShopCollectButton.setOnClickListener(this);
        mShopEnterButton.setOnClickListener(this);


        refresh();
        return view;
    }

    public void setOnClickListener(GoodsIndexFragment.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void quickBuyCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                goCheckout();
            }

            @Override
            public void onLogin() {
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_QUICK_BUY);

            }

        }, true);
    }

    public void goCheckout() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CheckoutIntegralActivity.class);
        startActivity(intent);
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, requestCode.getValue());
    }

    public void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    public void quickBuy(String goods_id, String goodsNumber) {
        String api = Api.API_INTEGRAL_QUICK_BUY;

        CommonRequest mQuickBuyRequest = new CommonRequest(api, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("goods_id", goods_id);
        mQuickBuyRequest.add("number", goodsNumber);
        addRequest(mQuickBuyRequest);
    }

    //地址选择
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_LOGIN_FOR_QUICK_BUY:
                if (resultCode == Activity.RESULT_OK) {
                    quickBuy(quickBuyGoodsId, quickBuyGoodsNumber);
                }
                break;
            case REQUEST_CODE_ADD_CART:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    quickBuyGoodsNumber = data.getStringExtra(Key.KEY_RESULT.getValue());
                    quickBuy(quickBuyGoodsId, quickBuyGoodsNumber);
                }
                break;
            case REQUEST_CODE_LOGIN_FOR_REFRESH:
                if (resultCode == Activity.RESULT_OK) {
                    refresh();
                }
                break;
        }
    }

    //0是滚动到顶部，1是滚动到商品详情的
    public void scrollBy(int index) {
        if (index == 1) {
            mScrollView.smoothScrollTo(0, goodsDescTop);
        } else {
            mScrollView.smoothScrollTo(0, 0);
        }

    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Config.BASE_URL + "/integralmall/goods-" +
                goods_id, HttpWhat.HTTP_GOODS.getValue());
        addRequest(request);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GOODS:
                refreshCallBack(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            case HTTP_COLLECT_SHOP:
                collectShopCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void collectShopCallback(String response) {
        HttpResultManager.resolve(response, ResponseFollowModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseFollowModel>() {
            @Override
            public void onSuccess(ResponseFollowModel model) {
                if (model.data == 1) {
                    if (!Utils.isNull(model.bonus_info) && !Utils.isNull(model.bonus_info
                            .bonus_amount)) {
                        openCollectBonus(model.bonus_info.bonus_number, model.bonus_info
                                .bonus_amount_format, model.bonus_info.bonus_name);
                    }
                    mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_selected);
                    mIcShopCollectionTip.setText(R.string.unfollow);
                    Toast.makeText(getActivity(), model.message, Toast.LENGTH_SHORT).show();

                    mShopCollectCount.setText((Integer.parseInt(mShopCollectCount.getText().toString()) + 1) + "");
                } else {
                    mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_normal);
                    mIcShopCollectionTip.setText(R.string.saveThisSeller);
                    Toast.makeText(getActivity(), model.message, Toast.LENGTH_SHORT).show();

                    mShopCollectCount.setText((Integer.parseInt(mShopCollectCount.getText().toString()) - 1) + "");
                }
            }

            @Override
            public void onLogin() {
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP);
            }
        }, true);
    }

    private void openCollectBonus(String number, String value, String name) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), RegisterBonusActivity.class);
        intent.putExtra(Key.KEY_BONUS_NUMBER.getValue(), number);
        intent.putExtra(Key.KEY_BONUS_VALUE.getValue(), value);
        intent.putExtra(Key.KEY_BONUS_NAME.getValue(), name);
        intent.putExtra(Key.KEY_BONUS_TYPE.getValue(), 1);
        startActivity(intent);
    }

    @BindView(R.id.fragment_goods_shop_desc_score)
    TextView mShopDescScore;
    @BindView(R.id.fragment_goods_shop_service_score)
    TextView mShopServiceScore;
    @BindView(R.id.fragment_goods_shop_send_score)
    TextView mShopSendScore;

    @BindView(R.id.fragment_goods_shop_logistics_score)
    TextView mShopLogisticsScore;

    @BindView(R.id.ic_shop_collection)
    ImageView mIcShopCollection;
    @BindView(R.id.ic_shop_collection_tip)
    TextView mIcShopCollectionTip;
    @BindView(R.id.fragment_goods_all_products)
    TextView mShopGoodsCount;
    @BindView(R.id.fragment_goods_collect_number)
    TextView mShopCollectCount;

    public ResponseIntegralGoodsModel mResponseGoodsModel;

    private void refreshCallBack(String response) {
        HttpResultManager.resolve(response, ResponseIntegralGoodsModel.class, new HttpResultManager.HttpResultCallBack<ResponseIntegralGoodsModel>() {
            @Override
            public void onSuccess(final ResponseIntegralGoodsModel back) {

                mResponseGoodsModel = back;
                ResponseIntegralGoodsModel.DataBean.GoodsBean goodsBean = back.data.goods;

                quickBuyGoodsId = goodsBean.goods_id;

                attributeIntegralModel = new AttributeIntegralModel();
                attributeIntegralModel.can_exchange = back.data.can_exchange;
                attributeIntegralModel.can_exchange_msg = back.data.can_exchange_msg;
                attributeIntegralModel.goods_image = goodsBean.goods_image;
                attributeIntegralModel.goods_integral = goodsBean.goods_integral;
                attributeIntegralModel.goods_number = goodsBean.goods_number;


                mGoodsSaleNumber.setText("兑换销量：" + goodsBean.exchange_number + "件");
                mGoodsPrice.setText(goodsBean.goods_integral + "积分");

                //轮播图
                goods_banner = new ArrayList<>();
                int goods_thumb_number = goodsBean.goods_images.size();
                for (int i = 0; i < goods_thumb_number; i++) {
                    goods_banner.add(goodsBean.goods_images.get(i).get(1));
                }

                mGoodsThumbViewPager.setAdapter(new ImageAdapter());
                pageIndicator.setRadius(10);
                pageIndicator.setViewPager(mGoodsThumbViewPager);

                mWebView.loadData(Utils.imgContentSetMaxHeight(goodsBean.pc_desc),
                        "text/html; charset=UTF-8", null);

                //店铺
                shopId = goodsBean.shop_id;
                if ("0".equals(goodsBean.shop_id) || back.data.shop_info == null) {

                    fragment_goods_shop_info_layout.setVisibility(View.GONE);

                } else {
                    fragment_goods_shop_info_layout.setVisibility(View.VISIBLE);

                    if (!Utils.isNull(back.data.shop_info.shop.shop_logo)) {
                        ImageLoader.displayImage(Utils.urlOfImage(back.data.shop_info.shop.shop_logo), mShopLogo);
                    } else {
                        ImageLoader.displayImage(Utils.urlOfImage(back.data.shop_info.shop.shop_image), mShopLogo);
                    }
                    mShopName.setText(back.data.shop_info.shop.shop_name);

                    if ("0".equals(back.data.shop_info.shop.shop_type)) {
                        mShopType.setText("自营");
                    } else {
                        mShopType.setVisibility(View.GONE);
                    }
                    mShopGoodsCount.setText(back.data.shop_goods_count);
                    mShopCollectCount.setText(back.data.shop_collect_count);

                    DecimalFormat df = new DecimalFormat("#.00");
                    String average = df.format((Double.parseDouble(back.data.shop_info.shop.desc_score) + Double.parseDouble(back.data.shop_info.shop.service_score) + Double.parseDouble(back.data.shop_info.shop.send_score)) / 3);
                    mShopLogisticsScore.setText("综合评分 " + average);

                    mShopDescScore.setText(back.data.shop_info.shop.desc_score);
                    mShopServiceScore.setText(back.data.shop_info.shop.service_score);
                    mShopSendScore.setText(back.data.shop_info.shop.send_score);

                    if (back.data.goods.shop_collect) {
                        mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_selected);
                        mIcShopCollectionTip.setText(R.string.unfollow);
                    } else {
                        mIcShopCollection.setImageResource(R.mipmap.ic_shop_collection_normal);
                        mIcShopCollectionTip.setText(R.string.saveThisSeller);
                    }
                }


                mGoodsName.setText(goodsBean.goods_name);
                mGoodsName.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showCopyWindows(v);
                        return false;
                    }
                });


                if (App.getInstance().isLogin()) {
                    GoodsIntegralActivity.mButtonTwo.setText("立即兑换");

                    GoodsIntegralActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
                            (getActivity(), R.color.colorPrimary));
                    GoodsIntegralActivity.mButtonTwo.setTextColor(0xffffffff);

                    Utils.setViewTypeForTag(GoodsIntegralActivity.mBuyNowButton, ViewType
                            .VIEW_TYPE_BUY_NOW);
                    GoodsIntegralActivity.mBuyNowButton.setOnClickListener(GoodsIndexIntegralFragment.this);

//                    //积分不足，不能兑换
//                    if (!back.data.can_exchange) {
//
//                        GoodsIntegralActivity.mBuyNowButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(getActivity(), back.data.can_exchange_msg, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        GoodsIntegralActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
//                                (getActivity(), R.color.colorNine));
//                        GoodsIntegralActivity.mButtonTwo.setTextColor(0xffbdbdbd);
//                    } else {
//                        GoodsIntegralActivity.mBuyNowButton.setBackgroundColor(ContextCompat.getColor
//                                (getActivity(), R.color.colorPrimary));
//                        GoodsIntegralActivity.mButtonTwo.setTextColor(0xffffffff);
//
//                        Utils.setViewTypeForTag(GoodsIntegralActivity.mBuyNowButton, ViewType
//                                .VIEW_TYPE_BUY_NOW);
//                        GoodsIntegralActivity.mBuyNowButton.setOnClickListener(GoodsIndexIntegralFragment.this);
//                    }
                } else {
                    GoodsIntegralActivity.mButtonTwo.setText("立即登录");
                    GoodsIntegralActivity.mBuyNowButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //兑换商品
                            openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.go_up_button:
                //点击滑动到顶部
                mScrollView.smoothScrollTo(0, 0);
                break;
            case R.id.fragmnet_goods_collect_button:
                collectShop(shopId);
                break;
            case R.id.fragment_goods_enter_shop_button:
                openShopActivity(shopId);
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(view);
                int position = Utils.getPositionOfTag(view);
                int extra = Utils.getExtraInfoOfTag(view);
                switch (viewType) {
                    case VIEW_TYPE_INDEX:
                        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX
                                .getValue()));
                        this.finish();
                        break;
                    case VIEW_TYPE_LOGIN:
                        openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
                        break;
                    case VIEW_TYPE_BUY_NOW:
                        openAttributeIntegralActivity();
                        break;
                }
                break;
        }
    }

    public void collectShop(String shopId) {
        CommonRequest request = new CommonRequest(Api.API_COLLECT_TOGGLE, HttpWhat
                .HTTP_COLLECT_SHOP.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("shop_id", shopId);
        addRequest(request);
    }

    AttributeIntegralModel attributeIntegralModel;

    private void openAttributeIntegralActivity() {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.KEY_SKU_MODEL.getValue(), attributeIntegralModel);
        intent.putExtras(bundle);
        intent.setClass(getActivity(), AttributeIntegralActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());

    }


    private void showCopyWindows(View v) {

        /** pop view */
        View mPopView = LayoutInflater.from(getActivity()).inflate(R.layout
                .menu_copy_popup_window, null);
        final PopupWindow mPopWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        /** set */
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /** 这个很重要 ,获取弹窗的长宽度 */
        mPopView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mPopView.getMeasuredWidth();
        int popupHeight = mPopView.getMeasuredHeight();
        /** 获取父控件的位置 */
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        /** 显示位置 */
        mPopWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) -
                popupWidth / 2, location[1] - popupHeight + 40);
        mPopWindow.update();

        final String copyTxt = (String) v.getTag();
        mPopView.findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context
                        .CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("goods_name", mGoodsName.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);

                Toast.makeText(getActivity(), "商品名称已复制到剪切板", Toast.LENGTH_SHORT).show();
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
            }
        });
    }

    //轮播图
    class ImageAdapter extends PagerAdapter {
        private DisplayImageOptions options;

        ImageAdapter() {
            options = new DisplayImageOptions.Builder().showImageOnLoading(ImageLoader.ic_error)
                    .showImageOnFail(ImageLoader.ic_error).cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return goods_banner.size();
        }

        //图片轮播
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageview = new ImageView(getActivity());
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String img = goods_banner.get(position);
//            ImageLoader.displayImage(img, imageview);
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(img,
                    imageview, options);
            container.addView(imageview);

            imageview.setTag(position);
            imageview.setOnClickListener(new ImageViewClick());
            return imageview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    class ImageViewClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Object obj = view.getTag();
            int position = obj == null ? 0 : (int) obj;
            openImageGallery(position, goods_banner);
        }
    }

    public void openImageGallery(int postion, ArrayList imgList) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ViewOriginalImageActivity.class);
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), imgList);
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), postion);
        startActivity(intent);
    }

    boolean isOenServiceLoading = false;

    public void openServiceActivity() {

        if (!App.getInstance().isLogin()) {
            mRefreshListener = new RefreshListener() {
                @Override
                public void onRefreshed() {
                    openServiceActivity();
                }
            };
            openLoginActivityForResult(RequestCode.REQUEST_CODE_SERVICE);
            return;
        }

        if (mResponseGoodsModel.data.shop_info.aliim == null || Utils.isNull(mResponseGoodsModel
                .data.shop_info.aliim.aliim_uid) || Utils.isNull(mResponseGoodsModel.data
                .shop_info.aliim.aliim_appkey)) {
            Toast.makeText(getContext(), "该店铺未配置云旺客服", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isOenServiceLoading) {
            toast("正在开启客服，请稍后...");
            return;
        }

        isOenServiceLoading = true;
        final String userId = mResponseGoodsModel.data.shop_info.aliim.aliim_uid;
        final String userPassword = mResponseGoodsModel.data.shop_info.aliim.aliim_pwd;
        final String appKey = mResponseGoodsModel.data.shop_info.aliim.aliim_appkey;
        final String serviceId = mResponseGoodsModel.data.shop_info.aliim.aliim_main_customer;

        View top_view = LayoutInflater.from(getContext()).inflate(R.layout
                .custom_chat_window_top, null);
        TextView goodName = (TextView) top_view.findViewById(R.id.textView_goods_name);
        TextView goodPrice = (TextView) top_view.findViewById(R.id.textView_price);
        TextView sendURL = (TextView) top_view.findViewById(R.id.textView_send_url);
        ImageView imageView_good = (ImageView) top_view.findViewById(R.id.imageView_good);

        goodName.setText(mResponseGoodsModel.data.goods.goods_name);
        goodPrice.setText(mResponseGoodsModel.data.goods.goods_price);
        ImageLoader.displayImage(Utils.urlOfImage(mResponseGoodsModel.data.goods.goods_image),
                imageView_good);
    }

    private interface RefreshListener {
        void onRefreshed();
    }
}
