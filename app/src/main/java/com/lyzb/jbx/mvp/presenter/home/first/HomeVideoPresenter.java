package com.lyzb.jbx.mvp.presenter.home.first;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody;
import com.lyzb.jbx.model.me.CompanyApplyListModel;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeVideoView;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class HomeVideoPresenter extends APPresenter<IHomeVideoView> {

    private int pageIndex = 1;
    private int pageSize = 15;

    public void getList(final boolean isRefrsh) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageNum", String.valueOf(pageIndex));
                map.put("pageSize", String.valueOf(pageSize));
                if (App.getInstance().isLogin()) {
                    map.put("userId", App.getInstance().userId);
                }
                return commonApi.getVideoTopic(getHeadersMap(GET, "/esapi/lbs/user/getVideoTopic"), map);
            }

            @Override
            public void onSuccess(String o) {

                if (!TextUtils.isEmpty(o)) {

                    HomeVideoModel listModel = GSONUtil.getEntity(o, HomeVideoModel.class);
                    if (listModel!=null&&listModel.getList() != null) {
                        getView().onApplyList(isRefrsh, listModel.getList());
                    } else {
                    }
                } else {
                    getView().onFinshOrLoadMore(isRefrsh);
//                    showFragmentToast(listModel.getMsg());
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }


    /**
     * 点赞
     *
     * @param dynamicId
     */
    public void onZan(final String dynamicId, final int position) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.onAddLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicFavour"),
                        new AddLikeOrFollowBody(dynamicId, "2"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onZanResult(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 取消点赞
     *
     * @param dynamicId
     */
    public void onCancleZan(final String dynamicId, final int position) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.onCancelLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicFavour"),
                        new AddLikeOrFollowBody(dynamicId, "2"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onZanResult(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

}
