package com.lyzb.jbx.mvp.presenter.home.search;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.search.ISearchGoodsView;
import com.szy.yishopcustomer.ResponseModel.ShopGoodsList.ResponseShopGoodsListModel;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class GoodSearchPresenter extends APPresenter<ISearchGoodsView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    public void getList(final boolean isRefrsh, final String sreachValue) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("msg", sreachValue);
                params.put("order", "DESC");
                params.put("page", pageIndex);
                params.put("size", pageSize);
                params.put("sort", 0);
                params.put("user_id", App.getInstance().userId == null ? "" : App.getInstance().userId);
                return commonApi.getSearchGoodsList(getHeadersMap(GET, "/esapi/lbs/search"), params);
            }

            @Override
            public void onSuccess(String s) {
                ResponseShopGoodsListModel model = GSONUtil.getEntity(s, ResponseShopGoodsListModel.class);
                getView().onGoodsListResult(isRefrsh, model.data.list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().finshLoadData(isRefrsh);
            }
        });
    }
}
