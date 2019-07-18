package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.net.widget.HttpResultDialog;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAddComdImgView;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/***
 * 创建企业 上传图片
 */

public class AddComdImgPresenter extends APPresenter<IAddComdImgView> {


    public void upLoadFiles(final List<String> list) {
        final HttpResultDialog dialog = new HttpResultDialog(context);
        dialog.show();

        final List<String> imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            Oss.getInstance().updaLoadImage(context, App.getInstance().user_token,
                    file.getAbsolutePath(), new Oss.OssListener() {
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
