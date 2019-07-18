package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.me.AgreedCircleModel;
import com.lyzb.jbx.model.me.ApplyListModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IApplyListView;

import io.reactivex.Observable;

/**
 * 我的圈子-申请成员列表
 */

public class ApplyListPresenter extends APPresenter<IApplyListView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    public void getList(final boolean isRefrsh, final String groupId) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                //pass这个参数是3.9.1新加的   目前的需求是直接写死
                return meApi.getApplyList(getHeadersMap(GET, "/lbs/gsGroup/applyGroupList"), groupId, pageIndex, pageSize,3);
            }

            @Override
            public void onSuccess(String o) {
                ApplyListModel listModel = GSONUtil.getEntity(o, ApplyListModel.class);
                getView().onApplyList(isRefrsh, listModel.getList());
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }

    /**
     * 同意or拒绝加入圈子（之前没有拒绝功能，3.9.1cx新增的）
     *
     * @param type=1同意=0拒绝
     * @param model
     * @param position
     */
    public void onAgreed(final int type, final AgreedCircleModel model, final int position) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                if (type == 1) {
                    return meApi.onAgreedCir(getHeadersMap(POST, "/lbs/gsGroup/applyGroup"), model);
                } else {
                    return meApi.onRefuseCir(getHeadersMap(POST, "/lbs/gsGroup/refuseGroup"), model);
                }
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);
                if (Integer.parseInt(resultModel.getStatus()) == 200) {
                    //同意
                    if (type == 1) {
                        getView().onAgreed(position);
                    } else {
                        //拒绝
                        getView().onRefuse(position);
                    }

                } else {
                    showFragmentToast(resultModel.getMsg());
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
