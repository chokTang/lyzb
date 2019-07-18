package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.CustomProgressDialog;
import com.szy.yishopcustomer.Activity.ArticleListActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.MessageActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.VideoFullActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Adapter.IndexAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.ScrollObservable;
import com.szy.yishopcustomer.Interface.ScrollObserver;
import com.szy.yishopcustomer.Interface.ServiceProvider;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsTitleModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.PageModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopStreetModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.TemplateModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.VideoModel;
import com.szy.yishopcustomer.ResponseModel.IndexGoodListModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

/**
 * Created by 宗仁 on 2017/1/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopIndexFragment extends YSCBaseFragment implements ScrollObservable {
    private static final float GO_TO_TOP_BUTTON_MIN_ALPHA = 0;
    private static final float GO_TO_TOP_BUTTON_START_APPEAR_POSITION = 100;
    private static final float GO_TO_TOP_BUTTON_FULL_APPEAR_OFFSET = 50;
    public static CustomProgressDialog mProgress;
    @BindView(R.id.fragment_shop_index_commonRecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.go_up_button)
    ImageView mGoToTopImageButton;
    @BindView(R.id.fragment_shop_index_serviceImageButton)
    ImageButton mServiceImageButton;
    List<ScrollObserver> mScrollObservers;
    private boolean isFirstLoad = true;
    private boolean goodsListIsMiddle;
    private PageModel mPageModel = new PageModel();
    private IndexAdapter mIndexAdapter;
    private String shopId;
    private boolean isLoad = false;

    private String goods_ids = "";

    //VIEW_TYPE_TWO_COLUMN_GOODS  IndexAdapter 适配器内

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (isLoad) {
                        loadMoreGoods();
                    }

                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            updateGoToTopButton(recyclerView.computeVerticalScrollOffset());
        }
    };
    public String videoDataString;

    private void loadMoreGoods() {

        if (mIndexAdapter.getItemCount() == 0) {
            return;
        }
        if (goodsListIsMiddle && !isFirstLoad) {
            return;
        }
        ViewType lastItemViewType = mIndexAdapter.getLastItemViewType();
        if (lastItemViewType == ViewType.VIEW_TYPE_LOADING || lastItemViewType == ViewType
                .VIEW_TYPE_LOAD_DISABLED) {
            return;
        }

        TemplateModel templateModel = new TemplateModel();
        templateModel.temp_code = Macro.TEMPLATE_CODE_LOADING;
        mIndexAdapter.insertAtLastPosition(templateModel);

        CommonRequest request = new CommonRequest(Api.API_INDEX_SITE, HttpWhat
                .HTTP_INDEX_GOODS_LIST.getValue(), RequestMethod.GET);
        request.add("page[cur_page]", mPageModel.cur_page + 1);
        request.add("page[page_size]", 20);
        request.add("shop_id", shopId);
        request.add("goods_ids", goods_ids);
        request.add("tpl_code", Macro.TEMPLATE_CODE_GOODS_LIST_DUMMY);
        request.alarm = false;
        addRequest(request);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (shopId == null) {
//                ((ShopActivity) getActivity()).refresh();
                ((ShopActivity)getActivity()).refrshShopIndexFragment();
            }
        }
    }


    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INDEX_GOODS_LIST:
                loadMoreGoodsCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void loadMoreGoodsCallback(final String response) {
        HttpResultManager.resolve(response, GoodsListModel.class, new HttpResultManager.HttpResultCallBack<GoodsListModel>() {
            @Override
            public void onSuccess(GoodsListModel goodsListModel) {

                mPageModel = goodsListModel.data.page;

                GoodsListModel tempGoodsListModel = null;
                for (int i = 0; i < mIndexAdapter.data.size(); i++) {
                    if (mIndexAdapter.data.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST)) {
                        tempGoodsListModel = JSON.parseObject(mIndexAdapter.data.get(i).data, GoodsListModel.class);
                    }
                }

                mIndexAdapter.removeLastItem();

                if (goodsListIsMiddle && isFirstLoad) {
                    for (int i = 0; i < mIndexAdapter.data.size(); i++) {
                        if (mIndexAdapter.data.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST)) {
                            mIndexAdapter.data.get(i).data = response;
                        }
                    }
                    mIndexAdapter.notifyDataSetChanged();
                    isFirstLoad = false;
                    return;
                }
                TemplateModel templateModel = new TemplateModel();
                templateModel.temp_code = Macro.TEMPLATE_CODE_GOODS_LIST;

                List<GoodsItemModel> tempList = new ArrayList<>();
                if (tempGoodsListModel != null) {
                    tempList.addAll(tempGoodsListModel.data.list);
                }

                tempList.addAll(goodsListModel.data.list);
                goodsListModel.data.list.clear();
                goodsListModel.data.list.addAll(tempList);

                templateModel.data = JSON.toJSONString(goodsListModel);

                mIndexAdapter.insertAtLastPosition(templateModel);

                if (mPageModel.page_count >= 0 && mPageModel.cur_page >= mPageModel.page_count) {
                    templateModel = new TemplateModel();
                    templateModel.temp_code = Macro.TEMPLATE_CODE_LOAD_DISABLED;
                    templateModel.data = getString(R.string.no_more_goods);
                    mIndexAdapter.insertAtLastPosition(templateModel);
                }
            }
        });
    }

    @Override
    public void addScrollObserver(ScrollObserver observer) {
        if (mScrollObservers == null) {
            mScrollObservers = new ArrayList<>();
        }
        if (mScrollObservers.contains(observer)) {
            return;
        }
        mScrollObservers.add(observer);
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_ARTICLE:
                openArticle(position);
                break;
            case VIEW_TYPE_TOP:
                goToTop();
                break;
            case VIEW_TYPE_MESSAGE:
                openMessageActivity();
                break;
            case VIEW_TYPE_SCAN:
                openScanActivity();
                break;
            case VIEW_TYPE_AD:
                openAd(position, extraInfo);
                break;
            case VIEW_TYPE_VIDEO:
                initVideo(position, extraInfo);
                break;
            case VIEW_TYPE_SHOP:
                openShop(position, extraInfo);
                break;
            case VIEW_TYPE_GOODS:
                openGoods(position, extraInfo);
                break;
            case VIEW_TYPE_GOODS_LIST_ITEM:
                openGuessLikeGoods(position, extraInfo);
                break;
            case VIEW_TYPE_NAVIGATION_ITEM:
                openMenu(position, extraInfo);
                break;
            case VIEW_TYPE_GOODS_TITLE:
                openGoodsTitle(position);
                break;
            case VIEW_TYPE_SERVICE:
                mProgress.show();
                openServiceActivity();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        mRecyclerView.setAdapter(mIndexAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setOnScrollListener(mScrollListener);

        Utils.setViewTypeForTag(mGoToTopImageButton, ViewType.VIEW_TYPE_TOP);
        mGoToTopImageButton.setOnClickListener(this);

        Utils.setViewTypeForTag(mServiceImageButton, ViewType.VIEW_TYPE_SERVICE);
        mServiceImageButton.setOnClickListener(this);

        if (!TextUtils.isEmpty(App.getInstance().aliim_icon)) {
            ImageLoader.displayImage(Utils.urlOfImage(App.getInstance().aliim_icon), mServiceImageButton);
        } else {
            mServiceImageButton.setImageResource(R.mipmap.btn_customer_service);
        }

        mProgress = new CustomProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((ShopActivity) getActivity()).refresh();
        ((ShopActivity)getActivity()).refrshShopIndexFragment();
    }

    @Override
    public void onDestroy() {
        if (mScrollObservers != null) {
            for (int i = mScrollObservers.size() - 1; i >= 0; i--) {
                mScrollObservers.remove(i);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_index;
        mIndexAdapter = new IndexAdapter(getActivity());
        mIndexAdapter.onClickListener = this;
        mIndexAdapter.windowWidth = Utils.getWindowWidth(getContext());
    }

    public void setUpAdapterData(List<TemplateModel> templateData, String id) {
        shopId = id;
        if (templateData == null || templateData.size() == 0) {
            templateData = new ArrayList<>();
            mRecyclerView.setEmptyImage(R.mipmap.bg_public);
            mRecyclerView.setEmptyTitle(R.string.emptyDecoration);
            mRecyclerView.showEmptyView();
            mIndexAdapter.data.clear();
            mIndexAdapter.data.addAll(templateData);
            mIndexAdapter.notifyDataSetChanged();
            return;
        }
        //两个循环写到一起
        /*for (int i = templateData.size() - 1; i >= 0; i--) {
            if (templateData.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST) && i <
                    templateData.size() - 1) {
                goodsListIsMiddle = true;
            }
        }*/
        for (int i = 0; i < templateData.size(); i++) {
            //商品模板是不是在中间
            if (templateData.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST) && i <
                    templateData.size() - 1) {
                goodsListIsMiddle = true;
            }
            //获取商品ids
            if (templateData.get(i).temp_code.equals(Macro.TEMPLATE_CODE_GOODS_LIST)) {
                isLoad = true;
                goods_ids = "";
                IndexGoodListModel indexGoodListModel = JSON.parseObject(templateData.get(i).data, IndexGoodListModel.class);

                if (indexGoodListModel != null && indexGoodListModel.getGoods_1() != null) {
                    for (int gi = 0, glen = indexGoodListModel.getGoods_1().size(); gi < glen; gi++) {
                        goods_ids += indexGoodListModel.getGoods_1().get(gi).getGoods_id();

                        if (gi < glen - 1) {
                            goods_ids += ",";
                        }
                    }
                }
            }
            //是否有视频模板，有的话需要再单独请求数据
            if (templateData.get(i).temp_code.equals(Macro.TEMPLATE_CODE_VIDEO)) {
                templateData.get(i).data = videoDataString;
            }
        }
        mIndexAdapter.data.clear();
        mIndexAdapter.data.addAll(templateData);
        mIndexAdapter.notifyDataSetChanged();
        if (isLoad) {
            loadMoreGoods();
        }
    }

    private void goToTop() {
        mRecyclerView.smoothScrollToPosition(1);
        for (ScrollObserver observer : mScrollObservers) {
            observer.scrollToTop();
        }
    }

    private void openAd(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        AdColumnModel adModel = JSON.parseObject(dataModel.data, AdColumnModel.class);
        AdItemModel adItemModel = adModel.pic_1.get(extraInfo);
        if (!Utils.isNull(adItemModel.link)) {
            if (adItemModel.link.equals("page/shop_street") || adItemModel.link.equals("page/cart") || adItemModel.link.equals
                    ("page/category") || adItemModel.link.equals("page/user") || adItemModel.link.equals("page/index")) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RootActivity.class);
                startActivity(intent);
            }

            new BrowserUrlManager().parseUrl(getContext(), adItemModel.link);
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getActivity(), "链接为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initVideo(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        VideoModel videoModel = JSON.parseObject(dataModel.data, VideoModel.class);
        String url = videoModel.data.video_list.get(extraInfo);
        String video_introduction = videoModel.data.video_introduction;

        Intent intent = new Intent(getContext(), VideoFullActivity.class);
        intent.putExtra(VideoFullActivity.VIDEO_TITLE, video_introduction);
        intent.putExtra(VideoFullActivity.VIDEO_URL, url);
        startActivity(intent);
    }

    private void openArticle(int position) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        ArticleModel articleModel = JSON.parseObject(dataModel.data, ArticleModel.class);
        ArrayList<ArticleItemModel> articleItems = new ArrayList<>();
        articleItems.addAll(articleModel.article_1);
        openArticleListActivity("文章列表", articleItems);
    }

    private void openArticleListActivity(String title, ArrayList<ArticleItemModel>
            articleItemModels) {
        Intent intent = new Intent(getContext(), ArticleListActivity.class);
        intent.putParcelableArrayListExtra(Key.KEY_ARTICLE_LIST.getValue(), articleItemModels);
        intent.putExtra(Key.KEY_TITLE.getValue(), title);
        startActivity(intent);
    }

    private void openGoods(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GoodsColumnModel goodsModel = JSON.parseObject(dataModel.data, GoodsColumnModel.class);
        GoodsItemModel goodsItemModel = goodsModel.goods_1.get(extraInfo);
        openGoodsActivity(goodsItemModel.sku_id);
    }

    private void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GoodsActivity.class);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        startActivity(intent);
    }

    private void openGoodsTitle(int position) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GoodsTitleModel titleModel = JSON.parseObject(dataModel.data, GoodsTitleModel.class);
        //http://m.jbxgo.com/shop-list-4274-6699.html
        if (!Utils.isNull(titleModel.title_1.link)) {
            if (titleModel.title_1.link.contains("shop-list")){
                Intent mIntent = new Intent(getActivity(), YSCWebViewActivity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(), titleModel.title_1.link);
            }else {
                new BrowserUrlManager().parseUrl(getContext(), titleModel.title_1.link);
            }
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getActivity(), "链接为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGuessLikeGoods(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        GoodsListModel goodsListModel = JSON.parseObject(dataModel.data, GoodsListModel.class);
        GoodsItemModel goodsItemModel = goodsListModel.data.list.get(extraInfo);
        openGoodsActivity(goodsItemModel.sku_id);
    }

    private void openMenu(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        NavigationModel navigatorModel = JSON.parseObject(dataModel.data, NavigationModel.class);
        NavigationItemModel navigatorItemModel = navigatorModel.mnav_1.get(extraInfo);

        if (navigatorItemModel.link.equals("page/shop_street") || navigatorItemModel.link.equals("page/cart") || navigatorItemModel.link.equals
                ("page/category") || navigatorItemModel.link.equals("page/user") || navigatorItemModel.link.equals("page/index")) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), RootActivity.class);
            startActivity(intent);
        }
        new BrowserUrlManager().parseUrl(getContext(), navigatorItemModel.link);
    }

    private void openMessageActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), MessageActivity.class);
        startActivity(intent);
    }

    private void openScanActivity() {
        if (cameraIsCanUse()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), ScanActivity.class);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_SCAN.getValue());
        } else {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .noCameraPermission));
        }
    }

    private void openServiceActivity() {
        if (getActivity() instanceof ServiceProvider) {
            ServiceProvider provider = (ServiceProvider) getActivity();
            provider.openServiceActivity();
        }
    }

    private void openShop(int position, int extraInfo) {
        TemplateModel dataModel = mIndexAdapter.data.get(position);
        ShopStreetModel shopStreetModel = JSON.parseObject(dataModel.data, ShopStreetModel.class);
        ShopStreetItemModel shopStreetItemModel = shopStreetModel.shop_1.get(extraInfo);
        openShopActivity(shopStreetItemModel.shop_id);
    }

    private void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    private void updateGoToTopButton(float offset) {
        float alpha;
        if (offset < GO_TO_TOP_BUTTON_START_APPEAR_POSITION) {
            alpha = GO_TO_TOP_BUTTON_MIN_ALPHA;
        } else {
            alpha = GO_TO_TOP_BUTTON_MIN_ALPHA + (offset -
                    GO_TO_TOP_BUTTON_START_APPEAR_POSITION) / GO_TO_TOP_BUTTON_FULL_APPEAR_OFFSET;
            if (alpha > 1) {
                alpha = 1;
            }
        }
        mGoToTopImageButton.setAlpha(alpha);
        if (alpha <= 0) {
            mGoToTopImageButton.setVisibility(View.INVISIBLE);
        } else {
            mGoToTopImageButton.setVisibility(View.VISIBLE);
        }
    }

}
