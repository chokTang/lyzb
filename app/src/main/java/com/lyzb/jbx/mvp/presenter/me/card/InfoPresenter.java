package com.lyzb.jbx.mvp.presenter.me.card;

import android.os.Handler;

import com.google.gson.JsonObject;
import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.me.CardFileDeModel;
import com.lyzb.jbx.model.me.CardImgTextModel;
import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.model.me.VoiceModel;
import com.lyzb.jbx.model.params.BodyId;
import com.lyzb.jbx.model.params.SaveCardModelBody;
import com.lyzb.jbx.model.params.UpdateModelBody;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICInfoView;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static com.lyzb.jbx.fragment.me.card.CardInfoFragment.CUSTOM_CODE;
import static com.lyzb.jbx.fragment.me.card.CardInfoFragment.INFO_CODE;
import static com.lyzb.jbx.fragment.me.card.CardInfoFragment.NEED_CODE;
import static com.lyzb.jbx.fragment.me.card.CardInfoFragment.PRIVIDE_CODE;

/**
 * @author wyx
 * @role
 * @time 2019 2019/3/24 18:55
 */

public class InfoPresenter extends APPresenter<ICInfoView> {

    /**
     * 上传文件
     *
     * @param file 文件地址
     */
    public void upLoadFile(final File file, final Handler handler) {
        Oss.getInstance().updaLoadImage(context, getToken(), file.getAbsolutePath(), new Oss.OssListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(String url) {
                if (isViewAttached()) {
                    handler.sendMessage(handler.obtainMessage(1, url));
                }
            }

            @Override
            public void onFailure() {
                if (isViewAttached()) {
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }


    /**
     * 上传 图片到云服务器
     *
     * @param list
     * @param status
     */
    public void upLoadFiles(final List<FileBody> list, final int status) {
        final List<FileBody> uplist = new ArrayList<>();
        final HttpResultDialog dialog = new HttpResultDialog(context);
        dialog.show();

        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i).getFile());
            final int finalI = i;
            Oss.getInstance().updaLoadImage(context, App.getInstance().user_token, file.getAbsolutePath(), new Oss.OssListener() {
                @Override
                public void onProgress(int progress) {

                }

                @Override
                public void onSuccess(String url) {
                    FileBody fileBody = new FileBody();
                    fileBody.setFile(url);
                    fileBody.setSort(finalI);
                    uplist.add(fileBody);
                    if (uplist.size() == list.size()) {
                        dialog.dismiss();
                        switch (status) {
                            case INFO_CODE:
                                getView().getInfo(uplist);
                                break;
                            case PRIVIDE_CODE:
                                getView().getProvide(uplist);
                                break;
                            case NEED_CODE:
                                getView().getNeed(uplist);
                                break;
                            case CUSTOM_CODE:
                                getView().getCustom(uplist);
                                break;
                        }
                    }

                }

                @Override
                public void onFailure() {
                    dialog.dismiss();
                }
            });

        }


    }


    /****
     * 个人信息上传语音
     * @param model
     */
    public void uploadVoice(final CardImgTextModel model) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.saveCardInfo(getHeadersMap(POST, "/lbs/gs/user/updateAttribute"), model);
            }

            @Override
            public void onSuccess(String o) {
                VoiceModel voiceModel = GSONUtil.getEntity(o, VoiceModel.class);
                getView().upLoadVoiceSuccess(voiceModel);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }


    /**
     * 删除文件
     *
     * @param model
     */
    public void deleFile(final CardFileDeModel model) {

        onRequestData(false, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.deleteFile(getHeadersMap(POST, "/lbs/gs/user/delGsUserFile"), model);
            }

            @Override
            public void onSuccess(Object o) {
                getView().deleteSuccess();
            }

            @Override
            public void onFail(String message) {

            }
        });
    }


    private int pageIndex = 1;
    private int pageSize = 2;

    /**
     * 查询企业商城商品
     *
     * @param companyId
     */
    public void getList(final String companyId) {

        onRequestDataHaveCommon(false, new IRequestListener<CardMallModel>() {
            @Override
            public Observable onCreateObservable() {
                return phpCommonApi.getCardGoodList(companyId, "java", pageIndex, pageSize);
            }

            @Override
            public void onSuccess(CardMallModel model) {
                getView().onGoodList(model);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }


    /****
     * 修改模板内容
     * @param body
     */
    public void updateModelContent(final UpdateModelBody body, final int type, final String content) {

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.updateCardModel(getHeadersMap(POST, "/lbs/gsParagraph/addOrUpdate"), body);
            }

            @Override
            public void onSuccess(String o) {
                getView().update(o,type,content);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }


    /****
     * 新增模板内容
     * @param body   可以多个
     * @param  typeGuding  固定模板的type  默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示7.宣传视频 ）
     * type  1 创建自定义模板，2 修改自定义模板 3.三个固定模板添加内容 4. 自定义模板添加内容
     *                    当type  124  自定义模板  的时候 typeGuding 没用  type  3的时候才有用
     */
    public void addModelContent(final SaveCardModelBody body, final int type, final boolean isImg, int typeGuding) {
        switch (type) {
            case 1:
            case 2:
            case 4:
                body.setDefualtModular(0);
                break;
            case 3:
                body.setDefualtModular(typeGuding);
                break;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveCardModel(getHeadersMap(POST, "/lbs/gsModular/addOrUpdate"), body);
            }

            @Override
            public void onSuccess(String o) {
                try {
                    JSONObject object = new JSONObject(o);
                    if (object.getInt("status")==200){
                        getView().saveInfo(o, type, isImg);
                    }else {
                        showFragmentToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }


    /****
     * 删除模板内容
     * @param id   只能单个
     */
    public void delereModelContent(final String id, final int type) {

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                BodyId bodyId = new BodyId();
                bodyId.setId(id);
                return meApi.deleteCardModelContent(getHeadersMap(POST, "/lbs/gsParagraph/delete"), bodyId);
            }

            @Override
            public void onSuccess(String o) {
                getView().deleteModelContent(o, type);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

    /****
     * 删除模板
     * @param id   只能单个
     */
    public void delereModel(final String id) {

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                BodyId bodyId = new BodyId();
                bodyId.setId(id);
                return meApi.deleteCardModel(getHeadersMap(POST, "/lbs/gsModular/delete"), bodyId);
            }

            @Override
            public void onSuccess(String o) {
                getView().deleteModel(o);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

}
