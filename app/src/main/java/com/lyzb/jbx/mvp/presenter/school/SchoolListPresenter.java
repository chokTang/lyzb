package com.lyzb.jbx.mvp.presenter.school;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.params.ShopZanBody;
import com.lyzb.jbx.model.school.SchoolModel;
import com.lyzb.jbx.model.school.SchoolTypeModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.school.IBusinessSchoolView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class SchoolListPresenter extends APPresenter<IBusinessSchoolView> {

    private int pageSize = 10;
    private int pageIndex = 1;

    public void getArticleTypeList(final int mType) {
        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return schoolApi.getSchoolTypeList(getHeadersMap(GET, "/lbs/article/articleTypes"),mType);
            }

            @Override
            public void onSuccess(String s) {
                getView().onGetArticleTypeList(GSONUtil.getEntityList(s, SchoolTypeModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void getArticleListBySearch(final boolean isfresh, final String value,final int catType) {
        if (isfresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("catId", "");
                params.put("pageNum", pageIndex);
                params.put("pageSize", pageSize);
                params.put("searchKey", value);
                params.put("userId", getUserId());
                params.put("catType", catType);
                return schoolApi.getSchoolListByType(getHeadersMap(GET, "/lbs/article/getArticleListByCat"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<SchoolModel> list = GSONUtil.getEntityList(listArray.toString(), SchoolModel.class);
                getView().onSchoolResult(isfresh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 点赞
     *
     * @param articleId
     * @param optType   =1:点赞; =2:取消点赞
     */
    public void zan(final String articleId, final int optType, final int position) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return schoolApi.zan(getHeadersMap(POST, "/lbs/article/doThumbs"), new ShopZanBody(articleId, optType));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int status = JSONUtil.get(resultObject, "status", 0);
                if (status == 200) {
                    getView().onZanReuslt(position);
                } else {
                    showFragmentToast(JSONUtil.get(resultObject, "msg", ""));
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
