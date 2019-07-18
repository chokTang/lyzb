package com.lyzb.jbx.mvp.presenter.me.card;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.ICdDynamicView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;

/**
 * 智能名片-动态
 */

public class CdDynamicPresenter extends BaseDynamicPresenter<ICdDynamicView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * 获取动态列表
     *
     * @param isRefrsh
     */
    public void getList(final boolean isRefrsh, final boolean isMe, final String userId) {

        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {

                if (isMe) {
                    return meApi.getMeDycList(getHeadersMap(GET, "/lbs/gs/topic/selectTopicInfoList"), pageIndex, pageSize);
                } else {
                    return meApi.getOthDycList(getHeadersMap(GET, "/lbs/gs/topic/selectTopicInfoListByUserId"), pageIndex, pageSize, userId);
                }
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<DynamicModel> list = GSONUtil.getEntityList(listArray.toString(), DynamicModel.class);
                getView().onDataList(isRefrsh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });

    }
}
