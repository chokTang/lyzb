package com.lyzb.jbx.mvp.presenter.me.card;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.lyzb.jbx.fragment.me.card.UserInfoFragment;
import com.lyzb.jbx.model.me.CardUserInfoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICardInfoView;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.list;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.lyzb.jbx.fragment.me.card.UserInfoFragment.AVATAR;
import static com.lyzb.jbx.fragment.me.card.UserInfoFragment.BG_CARD;
import static com.lyzb.jbx.fragment.me.card.UserInfoFragment.LOGO;
import static com.lyzb.jbx.fragment.me.card.UserInfoFragment.WX;

/***
 * 智能名片-基本信息 添加
 */

public class CardInfoPresenter extends APPresenter<ICardInfoView> {


    public void upLoadFiles(final String imgUrl, final int status) {

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
                        switch (status){
                            case AVATAR:
                                getView().getHeadImg(url);
                                break;
                            case WX:
                                getView().getWxImg(url);
                                break;
                            case LOGO:
                                getView().getLogoImg(url);
                                break;
                            case BG_CARD:
                                getView().getCardBgImg(url);
                                break;
                        }
                    }

                    @Override
                    public void onFailure() {
                        dialog.dismiss();
                    }
                });

    }

    public void saveInfo(final CardUserInfoModel model) {

        onRequestData(false, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveOtherInfo(getHeadersMap(POST, "/lbs/gs/user/saveGaUserExt"), model);
            }

            @Override
            public void onSuccess(Object o) {
                showFragmentToast("保存成功");
                getView().toCard(o.toString());
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void deleteLogo(){

    }
    public void deleteWxImg(){

    }
}
