package com.lyzb.jbx.mvp.presenter.school;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.params.ShopZanBody;
import com.lyzb.jbx.model.school.ArticleDetailModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.school.IArticleDeatilView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class ArticleDetailPresenter extends APPresenter<IArticleDeatilView> {

    /**
     * 获取文章详情
     *
     * @param articleId
     */
    public void getArticleDetail(final String articleId) {

        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return schoolApi.getArticleDetail(getHeadersMap(GET, "/lbs/article/getArticleDetail"), articleId);
            }

            @Override
            public void onSuccess(String s) {
                getView().onArticleResult(GSONUtil.getEntity(s, ArticleDetailModel.class));
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
    public void zan(final String articleId, final int optType) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                return schoolApi.zan(getHeadersMap(POST, "/lbs/article/doThumbs"), new ShopZanBody(articleId, optType));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int status = JSONUtil.get(resultObject, "status", 0);
                if (status == 200) {
                    getView().onZanReuslt();
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
