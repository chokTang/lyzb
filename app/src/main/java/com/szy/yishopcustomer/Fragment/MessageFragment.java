package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.GroupBuyListActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MessageDetailActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Adapter.MessageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Message.ListModel;
import com.szy.yishopcustomer.ResponseModel.Message.Model;
import com.szy.yishopcustomer.ResponseModel.UnreadMessage.ResponseUnreadMessageModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by zongren on 16/7/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MessageFragment extends YSCBaseFragment implements OnPullListener {
    private static final String TAG = "MessageFragment";
    @BindView(R.id.fragment_message_pullableRecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_message_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.button)
    Button loginButton;
    private int unreadMessageCount;
    private boolean upDataSuccess = false;
    private MessageAdapter mMessageAdapter;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private int pageCount;
    private int position;
    private UnreadMessageInterface mUnreadMessageCount;
    private ArrayList<Object> list;
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
        Side side = pullableComponent.getSide();
        switch (side) {
            case TOP:
                upDataSuccess = true;
                page = 1;
                mMessageAdapter.data.clear();
                request();
                break;
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        position = Utils.getPositionOfTag(view);
        int msg_id = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_MESSAGE:
                ListModel data = (ListModel) mMessageAdapter.data.get(position);
                String pushType = data.push_type;
                String pushValue = data.push_value;
                if (pushType != null) {
                    if (pushType.equals("0")) {
                        openGoodsActivity(pushValue);
                    } else if (pushType.equals("1")) {
                        openShopActivity(pushValue);
                    } else if (pushType.equals("2")) {
                        openArticle(pushValue);
                    } else if (pushType.equals("3")) {
                        openCategory(pushValue);
                    } else if (pushType.equals("4")) {
                        openGroupBuyList(pushValue);
                    } else if (pushType.equals("5")) {
                        openBrandGoodsList(pushValue);
                    } else if (pushType.equals("6")) {
                        openWeb(pushValue);
                    } else {
                        openMessage(data.title, data.content);
                    }
                } else {
                    openMessage(data.title, data.content);
                }
                readMessageRequest(data.rec_id);
                break;
            case VIEW_TYPE_MESSAGE_LOGIN_BUTTON:
                logIn();
                break;
            case VIEW_TYPE_DELETE:
                ListModel data2 = (ListModel) mMessageAdapter.data.get(position);
                deleteMessage(data2.rec_id);
                break;
            case VIEW_TYPE_MARK:
                ListModel data3 = (ListModel) mMessageAdapter.data.get(position);
                markMessage(data3.rec_id);
                break;
            default:
                super.onClick(view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        Utils.setViewTypeForTag(loginButton, ViewType.VIEW_TYPE_MESSAGE_LOGIN_BUTTON);
        Utils.setPositionForTag(loginButton, -1);
        Utils.setExtraInfoForTag(loginButton, -1);
        loginButton.setOnClickListener(this);
        mPullableLayout.topComponent.setOnPullListener(this);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mMessageAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        request();
        MessageAdapter.onClickListener = this;
        return view;
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                request();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_MESSAGE:
                refreshCallback(response);
                break;
                //loadmore
            case HTTP_GET_MESSAGE_ADD:
                addCallback(response);
                break;
            case HTTP_DETAIL:
                readMessageInfoCallback(response);
                break;
            case HTTP_DELETE:
                deleteMessageCallback(response);
                break;
            case HTTP_MARK:
                markMessageCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.fragment_message;
        super.onCreate(savedInstanceState);
        mMessageAdapter = new MessageAdapter();
        list = new ArrayList<Object>();
    }

    public void openArticle(String articleId) {
        Intent intent = new Intent(getContext(), YSCWebViewActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), Api.API_ARTICLE + articleId);
        startActivity(intent);
    }

    public void openBrandGoodsList(String brandId) {
        Intent intent = new Intent(getContext(), GoodsListActivity.class);
        intent.putExtra(Key.KEY_GOODS_BRAND_ID.getValue(), brandId);
        startActivity(intent);
    }

    public void openCategory(String catId) {
        Intent intent = new Intent(getContext(), GoodsListActivity.class);
        intent.putExtra(Key.KEY_CATEGORY.getValue(), catId);
        startActivity(intent);
    }

    public void openGroupBuyList(String actId) {
        Intent intent = new Intent(getContext(), GroupBuyListActivity.class);
        intent.putExtra(Key.KEY_GROUP_BUY_ACT_ID.getValue(), actId);
        startActivity(intent);
    }

    public void openWeb(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        Utils.openActivity(getContext(), intent);
    }

    private void addCallback(String response) {
        upDataSuccess = true;
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model mModel) {
                if (mModel.data.is_login != 1) {
                    for (int i = 0; i < mModel.data.list.size(); i++) {
                        mModel.data.list.get(i).read_time = "1";
                    }
                }
                pageCount = mModel.data.page.page_count;
                mMessageAdapter.data.addAll(mModel.data.list);
                mMessageAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollBy(0, 100);
                mPullableLayout.topComponent.finish(Result.SUCCEED);
            }

            @Override
            public void onFailure(String message) {
//                super.onFailure(message);
                mPullableLayout.topComponent.finish(Result.SUCCEED);
            }
        });
    }

    private void addRequest() {
        CommonRequest request = new CommonRequest(Api.API_MESSAGE, HttpWhat.HTTP_GET_MESSAGE_ADD
                .getValue());
        request.add("page[cur_page]", page);
        addRequest(request);
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel blankModel = new CheckoutDividerModel();
            list.add(blankModel);
            mMessageAdapter.notifyDataSetChanged();
            return;
        } else {
            addRequest();
        }
    }

    private void logIn() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void openGoodsActivity(String skuId) {
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        startActivity(intent);
    }

    private void openMessage(String title, String content) {
        Intent intent = new Intent(getContext(), MessageDetailActivity.class);
        intent.putExtra(Key.KEY_MESSAGE_TITLE.getValue(), title);
        intent.putExtra(Key.KEY_MESSAGE_CONTENT.getValue(), content);
        startActivity(intent);
    }

    private void openShopActivity(String shopId) {
        Intent intent = new Intent(getContext(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    private void readMessageInfoCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                ListModel data = (ListModel) mMessageAdapter.data.get(position);
                data.read_time = "1";
                mMessageAdapter.notifyDataSetChanged();
                unreadMessageCount--;
                if(unreadMessageCount<=0){
                    unreadMessageCount=0;
                }
                mUnreadMessageCount.setUnreadMessageCount(unreadMessageCount);
            }
        },true);
    }

    private void readMessageRequest(String recId) {
        CommonRequest mReadMessage = new CommonRequest(Api.API_MESSAGE_INFO, HttpWhat
                .HTTP_DETAIL.getValue());
        mReadMessage.add("id", recId);
        mReadMessage.setAjax(true);
        addRequest(mReadMessage);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model mModel) {
                upDataSuccess = true;
                if (mModel.data.is_login == 1) {
                    loginButton.setVisibility(View.GONE);
                } else {
                    loginButton.setVisibility(View.VISIBLE);
                    for (int i = 0; i < mModel.data.list.size(); i++) {
                        mModel.data.list.get(i).read_time = "1";
                    }
                }
                pageCount = mModel.data.page.page_count;

                list.addAll(mModel.data.list);
                mMessageAdapter.setData(list);
                if (pageCount == 0) {
                    mRecyclerView.showEmptyView();
                    upDataSuccess = false;
                }
                mMessageAdapter.notifyDataSetChanged();
                mPullableLayout.topComponent.finish(Result.SUCCEED);
               unreadMessageCount = mModel.data.unread_count;
                mUnreadMessageCount.setUnreadMessageCount(mModel.data.unread_count);
            }

            @Override
            public void onFailure(String message) {
                mPullableLayout.topComponent.finish(Result.SUCCEED);
            }
        });

    }

    private void request() {
        CommonRequest request = new CommonRequest(Api.API_MESSAGE, HttpWhat.HTTP_GET_MESSAGE
                .getValue());
        request.add("page[cur_page]", page);
        addRequest(request);
    }
    //删除消息
    private void deleteMessage(String id) {
        CommonRequest request = new CommonRequest(Api.API_MESSAGE_DELETE, HttpWhat.HTTP_DELETE
                .getValue(), RequestMethod.POST);
        request.add("id", id);
        addRequest(request);
    }
    private void deleteMessageCallback(String response) {
        HttpResultManager.resolve(response, ResponseUnreadMessageModel.class, new HttpResultManager.HttpResultCallBack<ResponseUnreadMessageModel>() {
            @Override
            public void onSuccess(ResponseUnreadMessageModel back) {
                Toast.makeText(getActivity(),back.message,Toast.LENGTH_SHORT).show();
                mMessageAdapter.data.remove(position);
                if (mMessageAdapter.data.size() == 0) {
                    upDataSuccess = false;
                    mRecyclerView.showEmptyView();
                }
                mMessageAdapter.notifyDataSetChanged();
                mUnreadMessageCount.setUnreadMessageCount(back.unread_count);
            }
        },true);
    }
    //标记消息为已读
    private void markMessage(String id) {
        CommonRequest request = new CommonRequest(Api.API_MESSAGE_READ, HttpWhat.HTTP_MARK
                .getValue(), RequestMethod.POST);
        request.add("id", id);
        addRequest(request);
    }

    private void markMessageCallback(String response) {
        HttpResultManager.resolve(response, ResponseUnreadMessageModel.class, new HttpResultManager.HttpResultCallBack<ResponseUnreadMessageModel>() {
            @Override
            public void onSuccess(ResponseUnreadMessageModel back) {
                ListModel data = (ListModel) mMessageAdapter.data.get(position);
                data.read_time = "1";
                mMessageAdapter.notifyDataSetChanged();
                mUnreadMessageCount.setUnreadMessageCount(back.unread_count);
            }
        },true);
    }

    public interface UnreadMessageInterface {
        void setUnreadMessageCount(int unread_message_count);
    }

    public void setFinishUserName(UnreadMessageInterface finishUserName) {
        mUnreadMessageCount = finishUserName;
    }
}
