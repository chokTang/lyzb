package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.AddCircleModel;
import com.lyzb.jbx.model.me.UpdCircleModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAddCircleView;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;

import java.io.File;

import io.reactivex.Observable;

/***
 * 添加圈子
 *
 */

public class AddCirclePresenter extends APPresenter<IAddCircleView> {


    public void upLoadFiles(String imgUrl) {
        final HttpResultDialog dialog = new HttpResultDialog(context);
        dialog.show();

        File file = new File(imgUrl);
        Oss.getInstance().updaLoadImage(context, App.getInstance().user_token, file.getAbsolutePath(), new Oss.OssListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(String url) {

                dialog.dismiss();
                getView().getImgList(url);
            }

            @Override
            public void onFailure() {
                dialog.dismiss();
                getView().onError("网络连接失败");
            }
        });

    }

    public void createCircle(final AddCircleModel model) {

        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.createCircle(getHeadersMap(POST, "/lbs/gsGroup/addOrUpdateGroup"), model);
            }

            @Override
            public void onSuccess(String o) {

                String status = JSONUtil.get(JSONUtil.toJsonObject(o), "status", "0");

                if ("200".equals(status)) {
                    getView().onCreateSucces();
                } else {
                    String msg = JSONUtil.get(JSONUtil.toJsonObject(o), "msg", "0");
                    getView().onError(msg);
                }

            }

            @Override
            public void onFail(String message) {
                getView().onError(message);
            }
        });
    }

    public void updateCircle(final UpdCircleModel model) {

        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.uptCircle(getHeadersMap(POST, "/lbs/gsGroup/addOrUpdateGroup"), model);
            }

            @Override
            public void onSuccess(String o) {
                String status = JSONUtil.get(JSONUtil.toJsonObject(o), "status", "0");
                if ("200".equals(status)) {
                    getView().onUpdate();
                } else {
                    String msg = JSONUtil.get(JSONUtil.toJsonObject(o), "msg", "0");
                    getView().onError(msg);
                }
            }

            @Override
            public void onFail(String message) {
                getView().onError(message);
            }
        });
    }
}
