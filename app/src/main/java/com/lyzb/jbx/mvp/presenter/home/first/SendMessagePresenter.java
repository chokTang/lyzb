package com.lyzb.jbx.mvp.presenter.home.first;

import com.like.longshaolib.net.widget.HttpResultDialog;
import com.like.utilslib.image.LuBanUtil;
import com.like.utilslib.image.inter.ICompressListener;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.first.ISendMessageView;
import com.szy.yishopcustomer.Util.LubanImg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendMessagePresenter extends APPresenter<ISendMessageView> {

    private int index = 0;
    private List<FileBody> upPic;
    private HttpResultDialog dialog;

    public void upFileLoads(List<FileBody> fileBodies) {
        index = 0;
        dialog = new HttpResultDialog(context);
        dialog.show();
        upPic = new ArrayList<>();

        upFileLuba(fileBodies);
    }

    private void upFileLuba(final List<FileBody> fileBodies) {
        LuBanUtil.compress(new File(fileBodies.get(index).getFile()), new ICompressListener() {
            @Override
            public void onSuccess(final File file) {
                upFileLoad(file.getPath(), new ISelectPictureListener() {
                    @Override
                    public void onSuccess(String imgUrl) {
                        index++;
                        FileBody fileBody = new FileBody();
                        fileBody.setFile(imgUrl);
                        upPic.add(fileBody);
                        if (index == fileBodies.size()) {//说明处理完了
                            getView().OnUploadResult(upPic);
                            dialog.dismiss();
                        } else {
                            upFileLuba(fileBodies);
                        }
                    }

                    @Override
                    public void onFail() {
                        if (isViewAttached()) {
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

}
