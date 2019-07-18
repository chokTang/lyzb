package com.lyzb.jbx.mvp.presenter.me.card;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICdMallView;
import com.szy.yishopcustomer.Util.App;

import java.util.Map;

import io.reactivex.Observable;

/**
 * 智能名片-商城
 */

public class CdMallPresenter extends APPresenter<ICdMallView> {

    private int pageIndex = 1;
    private int pageSize = 10;


    /**
     * 查询企业商城商品
     *
     * @param isRefrsh
     * @param companyId
     */
    public void getList(final boolean isRefrsh, final String companyId) {

        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }

        onRequestDataHaveCommon(false, new IRequestListener<CardMallModel>() {
            @Override
            public Observable onCreateObservable() {
                return phpCommonApi.getCardGoodList(companyId, "java", pageIndex, pageSize);
            }

            @Override
            public void onSuccess(CardMallModel model) {
                getView().onGoodList(isRefrsh, model);
            }

            @Override
            public void onFail(String message) {
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }

    /**
     * 删除商品
     *
     * @param position
     * @param map
     */
    public void deleteGoods(final int position, final Map<String, Object> map) {

        onRequestDataHaveCommon(false, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                return phpCommonApi.deleteGoods(map);
            }

            @Override
            public void onSuccess(Object object) {
                getView().onDelete(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
