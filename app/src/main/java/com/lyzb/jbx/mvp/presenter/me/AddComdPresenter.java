package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.dialog.original.HttpProessDialog;
import com.like.longshaolib.net.widget.HttpResultDialog;
import com.like.utilslib.file.FileUtil;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.CardUserInfoModel;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.me.JoinCompanyBody;
import com.lyzb.jbx.model.me.JoinCompanyResponseModel;
import com.lyzb.jbx.model.me.company.CompanyModelModel;
import com.lyzb.jbx.model.me.company.UpdateCompanyMsg;
import com.lyzb.jbx.model.params.AddOrganBody;
import com.lyzb.jbx.model.params.BodyId;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.model.params.SaveCardModelBody;
import com.lyzb.jbx.model.params.UpdateModelBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAddComdView;
import com.lyzb.jbx.mvp.view.me.ICdOtherInfoView;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.list;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.vincent.videocompressor.VideoCompress;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.lyzb.jbx.fragment.me.card.CardCompanyFragment.BRAND_CODE;
import static com.lyzb.jbx.fragment.me.card.CardCompanyFragment.HONOR_CODE;
import static com.lyzb.jbx.fragment.me.card.CardCompanyFragment.INFO_CODE;
import static com.lyzb.jbx.fragment.me.card.CardInfoFragment.CUSTOM_CODE;
import static org.greenrobot.greendao.generator.PropertyType.Int;

/***
 * 创建企业
 *
 */

public class AddComdPresenter extends APPresenter<IAddComdView> {


    /**
     * 上传文件
     */
    public void upLoadFiles(final String fileUrl) {
        if (fileUrl == null) {
            showFragmentToast("无效路径视频");
            return;
        }
        final HttpProessDialog dialog = new HttpProessDialog(context, "压缩中...");
        File file = new File(fileUrl);
        if (file.length() > 100 * 1024 * 1024) {//如果文件大于100M，才启用压缩
            final File[] outFile = {FileUtil.createFile(FileUtil.getVideoFileUrl(), System.currentTimeMillis() + "." + FileUtil.getExtension(file.getAbsolutePath()))};
            VideoCompress.compressVideoLow(file.getAbsolutePath(), outFile[0].getAbsolutePath(), new VideoCompress.CompressListener() {
                @Override
                public void onStart() {
                    dialog.show();
                }

                @Override
                public void onSuccess() {
                    dialog.setText("0");
                    dialog.setText("上传中...");
                    Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.getValue(), "").toString(),
                            outFile[0].getAbsolutePath(), new Oss.OssListener() {
                                @Override
                                public void onProgress(int progress) {
                                    dialog.setProress(progress+"");

                                }

                                @Override
                                public void onSuccess(String url) {
                                    dialog.dismiss();
                                    getView().getVideo(url);
                                    if (outFile[0].exists()) {
                                        outFile[0].delete();
                                        outFile[0] = null;
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    showFragmentToast("上传失败");
                                    dialog.dismiss();
                                }

                            });
                }

                @Override
                public void onFail() {
                    showFragmentToast("压缩失败");

                }

                @Override
                public void onProgress(float percent) {
                    dialog.setProress(String.format("%.2f", percent));

                }

            });
        } else {
            dialog.show();
            dialog.setText("0");
            dialog.setText("上传中...");
            Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.getValue(), "").toString(),
                    file.getAbsolutePath(), new Oss.OssListener() {
                        @Override
                        public void onProgress(int progress) {
                            dialog.setProress(progress+"");
                        }

                        @Override
                        public void onSuccess(String url) {
                            dialog.dismiss();
                            getView().getVideo(url);
                        }

                        @Override
                        public void onFailure() {
                            showFragmentToast("上传失败");
                            dialog.dismiss();
                        }

                    });
        }

    }
//
//    /****
//     * 上传视频
//     * @param fileUrl
//     */
//    public void upLoadFiles(final String fileUrl) {
//        if (fileUrl == null) {
//            showFragmentToast("无效路径视频");
//            return;
//        }
//
//        final HttpResultDialog dialog = new HttpResultDialog(context);
//        dialog.show();
//
//        File file = new File(fileUrl);
//
//
//        Oss.getInstance().updaLoadImage(context, App.getInstance().user_token, file.getAbsolutePath(), new Oss.OssListener() {
//            @Override
//            public void onProgress(int progress) {
//
//            }
//
//            @Override
//            public void onSuccess(String url) {
//                getView().getVideo(url);
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
//
//    }


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
                            case HONOR_CODE:
                                getView().getHonor(uplist);
                                break;
                            case BRAND_CODE:
                                getView().getBrand(uplist);
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

    /***
     * 获取 行业信息 data
     *
     */
    public void getInducList() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.getIndustryList(getHeadersMap(GET, "/lbs/gs/user/selectGsProfessionList"));
            }

            @Override
            public void onSuccess(String s) {
                List<BusinessModel> modelList = GSONUtil.getEntityList(s, BusinessModel.class);  //行业信息list
                getView().getTypeList(modelList);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /*****
     * 创建or修改企业
     * @param comdModel
     */
    public void addComdInfo(final boolean isCard, final CardComdModel comdModel) {

        onRequestData(false, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.updateComd(getHeadersMap(POST, "/lbs/gs/distributor/addOrUptCompany"), comdModel);
            }

            @Override
            public void onSuccess(Object o) {

                if (isCard) {
                    getView().onCardComd();
                } else {
                    getView().onFinshBack();
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

    /****
     * 获取 企业详情
     * @param comdId
     */
    public void getComdInfo(final String comdId) {
        if (TextUtils.isEmpty(comdId)) {
            return;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getComdInfo(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyDetail"), comdId);
            }

            @Override
            public void onSuccess(String o) {
                ComdDetailModel model = GSONUtil.getEntity(o, ComdDetailModel.class);
                getView().getData(model);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

//    /****
//     *添加、修改、删除机构
//     * @param body
//     */
//    public void addOrgan(final AddOrganBody body) {
//
//        onRequestData(false, new IRequestListener<String>() {
//
//            @Override
//            public Observable onCreateObservable() {
//                return meApi.addOrgan(getHeadersMap(POST, "/lbs/gs/org/addOrUptOrg"), body);
//            }
//
//            @Override
//            public void onSuccess(String o) {
//                UpdateCompanyMsg model = GSONUtil.getEntity(o, UpdateCompanyMsg.class);
//                getView().saveCompanyMsg(model);
//            }
//
//            @Override
//            public void onFail(String message) {
//                showFragmentToast(message);
//            }
//        });
//    }


    /****
     * 获取 企业模板详情
     * @param comdId
     */
    public void getComdModelInfo(final String comdId) {
        if (TextUtils.isEmpty(comdId)) {
            return;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getComdModelInfo(getHeadersMap(GET, "/lbs/gs/org/getOrgWebsite"), comdId);
            }

            @Override
            public void onSuccess(String o) {
                CompanyModelModel model = GSONUtil.getEntity(o, CompanyModelModel.class);
                getView().getModelData(model);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().getModelDataFail();
            }
        });
    }


    /**
     * 加入企业
     */
    public void joinConpany(final String companyId) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                JoinCompanyBody body = new JoinCompanyBody();
                body.setCompanyId(companyId);
                return meApi.joinCompany(getHeadersMap(POST, "/lbs/gs/distributor/doJoinCompany"), body);
            }

            @Override
            public void onSuccess(String o) {

                JoinCompanyResponseModel model = GSONUtil.getEntity(o, JoinCompanyResponseModel.class);
                if (model.getStatus().equals("200")) {
                    getView().joinSuccess();
                } else {
                    getView().joinFail();
                    showFragmentToast(model.getMsg());
                }
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
                getView().update(o, type, content);
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
     * type  1 创建自定义模板，2 修改自定义模板 3.三个固定模板添加内容 4. 自定义模板添加内容 7 视频
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
                    if (object.getInt("status") == 200) {
                        getView().saveInfo(o, type, isImg);
                    } else {
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
