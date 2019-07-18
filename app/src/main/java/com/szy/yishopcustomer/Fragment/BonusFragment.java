package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Adapter.BonusAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Bonus.BonusItemModel;
import com.szy.yishopcustomer.ResponseModel.Bonus.Model;
import com.szy.yishopcustomer.ResponseModel.Checkout.BlankModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;


/**
 * Created by liwei on 2016/6/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BonusFragment extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener {

    public int cur_page = 1;
    //public int page_size = 5;
    public String sortName = "ub.start_time";
    public String sortOrder = "desc";

    public int pageCount = 1;

    @BindView(R.id.fragment_shop_bonus_textView)
    TextView mShopBonusTextView;//店铺红包text
    @BindView(R.id.fragment_pay_bonus_textView)
    TextView mPayBonusTextView;//平台红包text
    @BindView(R.id.fragment_bonus_pullableRecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_bonus_pullableLayout)
    PullableLayout mPullAbleLayout;

    private LinearLayoutManager mLayoutManager;
    private BonusAdapter mBonusAdapter;
    private Model mModel;
    private int mPosition;
    private boolean upDataSuccess = false;

    //左边菜单 1
    private final static int MENU_LEFT = 1;
    //右边菜单 2
    private final static int MENU_RIGHT = 0;

    public int type = MENU_LEFT;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }

                }
            }
        }
    };

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            mBonusAdapter.data.clear();
            cur_page = 1;
            refresh(type);
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_SHOP_BONUS:
                //店铺红包点击
                if (mBonusAdapter.menuIsOpen()) {
                    mBonusAdapter.closeMenu();
                }
                colorSelect(mShopBonusTextView, mPayBonusTextView);
                mBonusAdapter.data.clear();
                type = MENU_LEFT;
                cur_page = 1;
                pageCount = 1;
                refresh(type);
                break;
            case VIEW_TYPE_PLATFORM_BONUS:

                if (mBonusAdapter.menuIsOpen()) {
                    mBonusAdapter.closeMenu();
                }
                colorSelect(mPayBonusTextView, mShopBonusTextView);
                type = MENU_RIGHT;
                cur_page = 1;
                pageCount = 1;
                mBonusAdapter.data.clear();
                refresh(type);
                break;
            case VIEW_TYPE_BONUS:
                BonusItemModel bonusItemModel = (BonusItemModel) mBonusAdapter.data.get(position);
                if (bonusItemModel.search_url.equals("/index.html")) {
                    goIndex();
                } else {
                    new BrowserUrlManager().parseUrl(getContext(), bonusItemModel.search_url);
                }

                if (mBonusAdapter.menuIsOpen()) {
                    mBonusAdapter.closeMenu();
                }
                break;
            case VIEW_TYPE_DELETE:
                deleteBonusRequest(position);
                break;
            default:

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Utils.setViewTypeForTag(mShopBonusTextView, ViewType.VIEW_TYPE_SHOP_BONUS);
        mShopBonusTextView.setOnClickListener(this);
        Utils.setViewTypeForTag(mPayBonusTextView, ViewType.VIEW_TYPE_PLATFORM_BONUS);
        mPayBonusTextView.setOnClickListener(this);

        mBonusAdapter = new BonusAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mBonusAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPullAbleLayout.topComponent.setOnPullListener(this);
        mBonusAdapter.onClickListener = this;
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setEmptyViewClickListener(this);


        refresh(type);
        if (type == MENU_RIGHT) {
            colorSelect(mPayBonusTextView, mShopBonusTextView);
        } else {
            colorSelect(mShopBonusTextView, mPayBonusTextView);
        }
        return view;
    }

    @Override
    public void onOfflineViewClicked() {
        upDataSuccess = false;
        refresh(type);
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_BONUS:
                mPullAbleLayout.topComponent.finish(Result.FAILED);
                upDataSuccess = false;
                mRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_BONUS:
                refreshCallback(response, MENU_LEFT);
                break;
            case HTTP_GET_BONUS_TWO:
                refreshCallback(response, MENU_RIGHT);
                break;
            case HTTP_GET_BONUS_DELETE:
                refreshDeleteCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_bonus;

        Intent intent = getActivity().getIntent();
        type = intent.getIntExtra(Key.KEY_BONUS_TYPE.getValue(), 1);
    }

    public void refresh(int type) {

        CommonRequest mBonusRequest = null;

        if (type == MENU_LEFT) {
            mBonusRequest = new CommonRequest(Api.API_BONUS, HttpWhat.HTTP_GET_BONUS.getValue());
        } else {
            mBonusRequest = new CommonRequest(Api.API_BONUS, HttpWhat.HTTP_GET_BONUS_TWO.getValue());
        }

        mBonusRequest.add("page[cur_page]", cur_page);
        //mBonusRequest.add("page[page_size]", page_size);
        mBonusRequest.add("type", type);
        mBonusRequest.add("sortname", sortName);
        // mBonusRequest.add("sortorder", sortOrder);
        addRequest(mBonusRequest);
    }

    private void colorSelect(TextView t1, TextView t2) {
        t1.setSelected(true);
        t2.setSelected(false);
    }

    private void deleteBonusRequest(int position) {
        mPosition = position;
        BonusItemModel bonusItemModel = (BonusItemModel) mBonusAdapter.data.get(position);
        if (mBonusAdapter.menuIsOpen()) {
            mBonusAdapter.closeMenu();
        }
        CommonRequest mBonusRequest = new CommonRequest(Api.API_BONUS_DELETE, HttpWhat
                .HTTP_GET_BONUS_DELETE.getValue(), RequestMethod.POST);
        mBonusRequest.add("user_bonus_id", bonusItemModel.user_bonus_id);
        mBonusRequest.setAjax(true);
        addRequest(mBonusRequest);
    }

    private void loadMore() {

        upDataSuccess = false;
        cur_page++;
        if (cur_page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel blankModel = new CheckoutDividerModel();
            mBonusAdapter.data.add(blankModel);
            mBonusAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh(type);
        }
    }

    private void refreshCallback(String response, int position) {

        if (type != position) {
            return;
        }

        upDataSuccess = true;
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {

                mModel = back;
                pageCount = mModel.data.page.page_count;
                mPullAbleLayout.topComponent.finish(Result.SUCCEED);
                setUpAdapterData();

                mBonusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                mPullAbleLayout.topComponent.finish(Result.FAILED);
            }
        });
    }

    private void refreshDeleteCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mBonusAdapter.data.remove(mPosition);
                mBonusAdapter.data.remove(mPosition - 1);
                if (mBonusAdapter.data.size() == 0) {
                    upDataSuccess = false;
                    mRecyclerView.setEmptyImage(R.mipmap.bg_public);
                    mRecyclerView.setEmptyTitle(R.string.emptyBonusListTitle);
                    mRecyclerView.showEmptyView();
                }

                mBonusAdapter.notifyDataSetChanged();
            }
        }, true);
    }

    private void setUpAdapterData() {
        mRecyclerView.hideEmptyView();
        mShopBonusTextView.setText("店铺红包(" + mModel.data.shop_bonus_count + ")");
        mPayBonusTextView.setText("平台红包(" + mModel.data.system_bonus_count + ")");
        if (!Utils.isNull(mModel.data.list) && mModel.data.list.size() != 0) {
            for (BonusItemModel bonusItemModel : mModel.data.list) {
                if ("0".equals(bonusItemModel.shop_id)) {
                    bonusItemModel.shop_name = mModel.data.context.config.site_name;
                }

                BlankModel blankModel = new BlankModel();
                mBonusAdapter.data.add(blankModel);
                mBonusAdapter.data.add(bonusItemModel);
            }

        } else {
            upDataSuccess = false;
            mRecyclerView.setEmptyImage(R.mipmap.bg_public);
            mRecyclerView.setEmptyTitle(R.string.emptyBonusListTitle);
            mRecyclerView.showEmptyView();
        }
    }

    private void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }
}
