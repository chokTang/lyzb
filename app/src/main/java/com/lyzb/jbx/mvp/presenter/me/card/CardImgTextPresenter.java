package com.lyzb.jbx.mvp.presenter.me.card;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.lyzb.jbx.model.me.CardFileDeModel;
import com.lyzb.jbx.model.me.CardImgTextModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICardImgTextView;
import com.szy.yishopcustomer.Util.Oss;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/***
 * 智能名片-个人-图文信息上传
 */

public class CardImgTextPresenter extends APPresenter<ICardImgTextView> {


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

    public void deleImg(final CardFileDeModel model) {

        onRequestData(false, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.deleteFile(getHeadersMap(POST, "/lbs/gs/user/delGsUserFile"), model);
            }

            @Override
            public void onSuccess(Object o) {
                getView().deleImg();
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    /****
     * 个人信息编辑
     * @param model
     */
    public void postImgText(final CardImgTextModel model) {


        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveCardInfo(getHeadersMap(POST, "/lbs/gs/user/updateAttribute"), model);
            }

            @Override
            public void onSuccess(String o) {
                getView().toCardInfo(o);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

}
