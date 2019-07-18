package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.FansModel;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.IFansView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;

/**
 * 我关注的
 */

public class FansPresenter extends BaseDynamicPresenter<IFansView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * @param isRefrsh
     */
    public void getList(final boolean isRefrsh, final int type, final String userId,final String mSearchKey) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getFansList(getHeadersMap(GET, "/lbs/gs/topic/selectMyRelatioList"), pageIndex, pageSize, type,userId,mSearchKey);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject result = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(result, "list");
                List<FansModel> list = GSONUtil.getEntityList(listArray.toString(), FansModel.class);
                int total = JSONUtil.get(result, "total", 0);
                getView().onFansList(isRefrsh, total, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshRefresh(isRefrsh);
            }
        });
    }


}
