package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.CheckoutIntegralActivity;
import com.szy.yishopcustomer.Activity.ConvertibleActivity;
import com.szy.yishopcustomer.Activity.GoodsIntegralActivity;
import com.szy.yishopcustomer.Activity.RedExchangeActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.UserIntegralActivity;
import com.szy.yishopcustomer.Adapter.ImageAdapter;
import com.szy.yishopcustomer.Adapter.IntegralMallListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.CirclePageIndicator;
import com.szy.yishopcustomer.View.HeadWrapContentViewPager;
import com.szy.yishopcustomer.ViewModel.IntegralMallListModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

/**
 * 积分商城
 */
public class IntegralMallFragment extends YSCBaseFragment {

    @BindView(R.id.linearlayout_user_score)
    View linearlayout_user_score;
    @BindView(R.id.linearlayout_red_exchange)
    View linearlayout_red_exchange;
    @BindView(R.id.linearlayout_convertible)
    View linearlayout_convertible;
    @BindView(R.id.linearlayout_root)
    View linearlayout_root;

    @BindView(R.id.fragment_integral_sort_default)
    View fragment_integral_sort_default;
    @BindView(R.id.fragment_integral_sort_exchange_amount)
    View fragment_integral_sort_exchange_amount;
    @BindView(R.id.fragment_integral_sort_integral_value)
    View fragment_integral_sort_integral_value;
    @BindView(R.id.fragment_integral_sort_shelf_time)
    View fragment_integral_sort_shelf_time;

    @BindView(R.id.fragment_integral_sort_default_textview)
    TextView fragment_integral_sort_default_textview;
    @BindView(R.id.fragment_integral_sort_exchange_amount_textView)
    TextView fragment_integral_sort_exchange_amount_textView;
    @BindView(R.id.fragment_integral_sort_integral_value_textview)
    TextView fragment_integral_sort_integral_value_textview;
    @BindView(R.id.fragment_integral_sort_shelf_time_textview)
    TextView fragment_integral_sort_shelf_time_textview;

    @BindView(R.id.fragment_integral_sort_exchange_amount_imageview)
    ImageView fragment_integral_sort_exchange_amount_imageview;
    @BindView(R.id.fragment_integral_sort_integral_value_imageview)
    ImageView fragment_integral_sort_integral_value_imageview;
    @BindView(R.id.fragment_integral_sort_shelf_time_imageview)
    ImageView fragment_integral_sort_shelf_time_imageview;

    @BindView(R.id.fragment_integral_listView)
    CommonRecyclerView fragment_integral_listView;

    @BindView(R.id.viewPager)
    HeadWrapContentViewPager viewPager;
    @BindView(R.id.relativeLayout_viewPager)
    View relativeLayout_viewPager;
    @BindView(R.id.fragment_index_banner_pageIndicator)
    CirclePageIndicator pageIndicator;

    ArrayList<String> imgs;

    Boolean[] sort_order = null;
    private int sort = 0;
    private String is_self = "";
    private String can_exchange = "";

    private IntegralMallListAdapter mAdapter;
    private IntegralMallListModel data;
    private ImageAdapter imageAdapter;

    private String shop_id;

    BrowserUrlManager mBrowserUrlManager = new BrowserUrlManager();

    @BindView(R.id.relativeLayout_empty)
    LinearLayout relativeLayout_empty;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_integral_mall;

        sort_order = new Boolean[4];
        for (int i = 0; i < sort_order.length; i++) {
            sort_order[i] = false;
        }

        mAdapter = new IntegralMallListAdapter();
        mAdapter.onClickListener = this;

        shop_id = getActivity().getIntent().getStringExtra(Key.KEY_SHOP_ID.getValue());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_integral_sort_default.setOnClickListener(sortClick);
        fragment_integral_sort_exchange_amount.setOnClickListener(sortClick);
        fragment_integral_sort_integral_value.setOnClickListener(sortClick);
        fragment_integral_sort_shelf_time.setOnClickListener(sortClick);

        linearlayout_user_score.setOnClickListener(sortClick);
        linearlayout_red_exchange.setOnClickListener(sortClick);
        linearlayout_convertible.setOnClickListener(sortClick);
        linearlayout_root.setOnClickListener(sortClick);

        fragment_integral_listView.addOnScrollListener(mOnScrollListener);
        fragment_integral_listView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_integral_listView.setLayoutManager(layoutManager);

        imgs = new ArrayList<>();
        viewPager.setAdapter(imageAdapter = new ImageAdapter(getActivity(), imgs));
        imageAdapter.listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object obj = view.getTag(R.id.imagePosition);
                int position = obj == null ? 0 : (int) obj;
                try {
                    mBrowserUrlManager.parseUrl(getContext(), data.data.banner.get(position).link);
                } catch (Exception e) {
                }
            }
        };

        refresh();
        return view;
    }


    //默认加载第一页
    private int cur_page = 1;

    public void loadMore() {
        if (data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest request = new CommonRequest(Api.API_INTEGRALMALL, HttpWhat.HTTP_INDEX.getValue());
            request.add("sort", sort + "");
            if (sort == 0) {
                request.add("order", "");
            } else {
                if (sort_order[sort]) {
                    request.add("order", "asc");
                } else {
                    request.add("order", "desc");
                }
                sort_order[sort] = !sort_order[sort];
            }

            if(!TextUtils.isEmpty(shop_id)) {
                request.add("shop_id", shop_id);
            }

            request.add("is_self", is_self);
            request.add("can_exchange", can_exchange);
            request.add("page[cur_page]", cur_page);
            addRequest(request);
        }
    }

    public void refresh() {
        cur_page = 1;
        changeButtonStyles();
        CommonRequest request = new CommonRequest(Api.API_INTEGRALMALL,  HttpWhat.HTTP_INDEX.getValue());
        request.add("sort", sort + "");
        if (sort == 0) {
            request.add("order", "");
        } else {
            if (sort_order[sort]) {
                request.add("order", "asc");
            } else {
                request.add("order", "desc");
            }
            sort_order[sort] = !sort_order[sort];
        }

        if(!TextUtils.isEmpty(shop_id)) {
            request.add("shop_id", shop_id);
        }
        request.add("is_self", is_self);
        request.add("can_exchange", can_exchange);
        request.add("page[cur_page]", cur_page);
        addRequest(request);
    }
    
    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INDEX:
                refreshSucceed(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, IntegralMallListModel.class, new HttpResultManager.HttpResultCallBack<IntegralMallListModel>() {
            @Override
            public void onSuccess(IntegralMallListModel back) {
                data = back;

                if (data.data.banner != null && data.data.banner.size() > 0) {
                    relativeLayout_viewPager.setVisibility(View.VISIBLE);
                    pageIndicator.setViewPager(viewPager);
                    pageIndicator.setSnap(true);

                    imgs.clear();
                    for (int i = 0; i < data.data.banner.size(); i++) {
                        imgs.add(Utils.urlOfImage(data.data.banner.get(i).img,false) + "?" + System.currentTimeMillis());
                    }

                    viewPager.getMyPagerAdapter().notifyDataSetChanged();

                } else {
                    relativeLayout_viewPager.setVisibility(View.GONE);
                }

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (back.data.list.size() > 0) {
                    fragment_integral_listView.setVisibility(View.VISIBLE);
                    relativeLayout_empty.setVisibility(View.GONE);
                    mAdapter.data.addAll(back.data.list);
                } else {
                    fragment_integral_listView.setVisibility(View.GONE);
                    relativeLayout_empty.setVisibility(View.VISIBLE);
                }

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    View.OnClickListener sortClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Utils.isDoubleClick()) {
                return;
            }
            switch (view.getId()) {
                //默认排序
                case R.id.fragment_integral_sort_default:
                    sort = 0;
                    refresh();
                    changeButtonStyles();
                    break;
                case R.id.fragment_integral_sort_exchange_amount:
                    sort = 1;
                    refresh();
                    break;
                case R.id.fragment_integral_sort_integral_value:
                    sort = 2;
                    refresh();
                    break;
                case R.id.fragment_integral_sort_shelf_time:
                    sort = 3;
                    refresh();
                    break;
                case R.id.linearlayout_user_score:
                    //我的积分
                    startActivity(new Intent(getActivity(), UserIntegralActivity.class));
                    break;
                case R.id.linearlayout_red_exchange:
                    //红包兑换
                    startActivity(new Intent(getActivity(), RedExchangeActivity.class));
                    break;
                case R.id.linearlayout_convertible:
                    //我可兑换
                    startActivity(new Intent(getActivity(), ConvertibleActivity.class));
                    break;
                case R.id.linearlayout_root:
                    //首页
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
                    startActivity(new Intent(getActivity(), RootActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                IntegralMallListModel.DataBean.ListBean temp = mAdapter.data.get(position);
                //进入商品详情
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_GOODS_ID.getValue(), temp.goods_id + "");
                intent.setClass(getActivity(), GoodsIntegralActivity.class);
                startActivity(intent);
                break;
            case VIEW_TYPE_RED_EXCHANGE:
                quickBuy(mAdapter.data.get(position).goods_id,"1");
                break;
        }
    }

    public void quickBuy(String goods_id, String goodsNumber) {
        String api = Api.API_INTEGRAL_QUICK_BUY;

        CommonRequest mQuickBuyRequest = new CommonRequest(api, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("goods_id", goods_id);
        mQuickBuyRequest.add("number", goodsNumber);
        addRequest(mQuickBuyRequest);
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
            }

        }, true);
    }

    public void goCheckout() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CheckoutIntegralActivity.class);
        startActivity(intent);
    }



    private void changeButtonStyles() {
        fragment_integral_sort_default_textview.setSelected(false);
        fragment_integral_sort_exchange_amount_textView.setSelected(false);
        fragment_integral_sort_integral_value_textview.setSelected(false);
        fragment_integral_sort_shelf_time_textview.setSelected(false);
        fragment_integral_sort_exchange_amount_imageview.setImageResource(R.mipmap.bg_arrow_default);
        fragment_integral_sort_integral_value_imageview.setImageResource(R.mipmap.bg_arrow_default);
        fragment_integral_sort_shelf_time_imageview.setImageResource(R.mipmap.bg_arrow_default);

        switch (sort) {
            case 0:
                fragment_integral_sort_default_textview.setSelected(true);
                break;
            case 1:
                fragment_integral_sort_exchange_amount_textView.setSelected(true);
                if (sort_order[1]) {
                    fragment_integral_sort_exchange_amount_imageview.setImageResource(R.mipmap.bg_arrow_up);
                } else {
                    fragment_integral_sort_exchange_amount_imageview.setImageResource(R.mipmap.bg_arrow_down);
                }
                break;
            case 2:
                fragment_integral_sort_integral_value_textview.setSelected(true);
                if (sort_order[2]) {
                    fragment_integral_sort_integral_value_imageview.setImageResource(R.mipmap.bg_arrow_up);
                } else {
                    fragment_integral_sort_integral_value_imageview.setImageResource(R.mipmap.bg_arrow_down);
                }
                break;
            case 3:
                fragment_integral_sort_shelf_time_textview.setSelected(true);
                if (sort_order[3]) {
                    fragment_integral_sort_shelf_time_imageview.setImageResource(R.mipmap.bg_arrow_up);
                } else {
                    fragment_integral_sort_shelf_time_imageview.setImageResource(R.mipmap.bg_arrow_down);
                }
                break;
        }
    }

    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_integral_listView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        if (data != null) {
                            cur_page = data.data.page.cur_page + 1;
                            loadMore();
                        }
                    }
                }
            }
        }
    };
}
