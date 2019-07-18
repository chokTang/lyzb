package com.lyzb.jbx.mvp.presenter.me.card;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.CardFileDeModel;
import com.lyzb.jbx.model.me.CardImgTextModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICardComdImgView;
import com.lyzb.jbx.mvp.view.me.ICardImgTextView;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/***
 * 智能名片-企业-图文信息
 */

public class CardComdPresenter extends APPresenter<ICardComdImgView> {


    public void upLoadFiles(final List<String> list) {
        final HttpResultDialog dialog = new HttpResultDialog(context);
        dialog.show();
        final List<String> imgList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            Oss.getInstance().updaLoadImage(context, App.getInstance().user_token, file.getAbsolutePath(), new Oss.OssListener() {
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

    /****
     * 企业信息编辑
     * @param model
     */
    public void postImgText(final CardComdModel model) {


        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.updateComd(getHeadersMap("/lbs/gs/distributor/addOrUptCompany", POST), model);
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
