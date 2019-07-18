package com.lyzb.jbx.mvp.presenter.me;

import com.google.gson.JsonObject;
import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.model.me.PhpRestModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAddGoodView;
import com.szy.yishopcustomer.Util.Oss;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 添加商品
 * @time 2019 2019/3/24 14:00
 */

public class AddGoodPresenter extends APPresenter<IAddGoodView> {


    public void addGood(final Map<String, Object> map) {


        onRequestDataHaveCommon(true, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                return phpCommonApi.addGoods(map);
            }

            @Override
            public void onSuccess(Object object) {
                getView().onSuceess();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

    public void upLoadFiles(final List<String> list) {
        final HttpResultDialog dialog = new HttpResultDialog(context);
        dialog.show();
        final List<String> imgList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            Oss.getInstance().updaLoadImage(context, getToken(), file.getAbsolutePath(), new Oss.OssListener() {
                @Override
                public void onProgress(int progress) {

                }

                @Override
                public void onSuccess(String url) {
                    imgList.add(url);
                    if (imgList.size() == list.size()) {
                        dialog.dismiss();
                        getView().setImgList(imgList);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

}
