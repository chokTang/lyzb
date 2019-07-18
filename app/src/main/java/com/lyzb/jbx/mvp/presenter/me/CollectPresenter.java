package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.me.CollectModel;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.ICollectView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;

/**
 * 我收藏的
 */

public class CollectPresenter extends BaseDynamicPresenter<ICollectView> {

    /**
     * 获取动态列表
     *
     * @param isRefresh
     */
    @Override
    public void getFollowDynamicList(final boolean isRefresh) {
        if (isRefresh) {
            pageIndexDynamic = 1;
        } else {
            pageIndexDynamic++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCollList(getHeadersMap(GET, "/lbs/gs/topic/selectMyTopicFavour"), pageIndexDynamic, pageSize);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CollectModel> list = GSONUtil.getEntityList(listArray.toString(), CollectModel.class);
                getView().onCollectList(isRefresh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshRefresh(isRefresh);
            }
        });
    }
}
