package com.lyzb.jbx.mvp.presenter.home.first;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.model.dynamic.AddCommentOrReplyModel;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowModel;
import com.lyzb.jbx.model.dynamic.DynamicCommentModel;
import com.lyzb.jbx.model.dynamic.DynamicFocusModel;
import com.lyzb.jbx.model.dynamic.RequestBodyComment;
import com.lyzb.jbx.model.dynamic.RequstBodyFocus;
import com.lyzb.jbx.model.params.TopicIdBody;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.first.IVideoListView;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


public class VideoListPresenter extends APPresenter<IVideoListView> {
    private int pageIndex = 1;
    private int pageSize = 10;

    public void getCommentList(final boolean isRefrsh, final String topicId) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageNum", String.valueOf(pageIndex));
                map.put("pageSize", String.valueOf(pageSize));
                map.put("topicId", topicId);
                return dynamicApi.getDynamicDetailCommentList(getHeadersMap(GET, "/lbs/comment/pageInfo"), map);
            }

            @Override
            public void onSuccess(String o) {

                if (!TextUtils.isEmpty(o)) {
                    DynamicCommentModel dynamicCommentModel = GSONUtil.getEntity(o, DynamicCommentModel.class);
                    if (dynamicCommentModel != null) {
                        getView().onSuccess(isRefrsh, dynamicCommentModel);
                    }

                } else {
                    getView().onFinshOrLoadMore(isRefrsh);
//                    showFragmentToast(listModel.getMsg());
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }


    /**
     * 动态(取消 点赞 收藏)
     * status    1：浏览，2：点赞 ，3：收藏 4评论点赞
     * topicId   主键ID
     */

    public void addLikeOrFollow(final int status, final String topicId, final int position, final int type) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                AddLikeOrFollowBody body = new AddLikeOrFollowBody();
                body.setTopicId(topicId);
                body.setType(String.valueOf(status));
                return dynamicApi.onAddLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicFavour"), body);
            }

            @Override
            public void onSuccess(String s) {
                LogUtil.loge("动态点赞和收藏回调信息$t");
                AddLikeOrFollowModel bean = GSONUtil.getEntity(s, AddLikeOrFollowModel.class);
                if (null != bean) {

                    getView().addLikeOrFollow(status, bean, position, type);

                } else {
                    showFragmentToast("数据解析错误");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 动态(取消 点赞 收藏)
     * status   1：浏览，2：点赞 ，3：收藏 4评论点赞
     * topicId   主键ID
     */
    public void cancelLikeOrFollow(final int status, final String topicId, final int position, final int type) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                AddLikeOrFollowBody body = new AddLikeOrFollowBody();
                body.setTopicId(topicId);
                body.setType(String.valueOf(status));
                return dynamicApi.onCancelLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicFavour"), body);
            }

            @Override
            public void onSuccess(String s) {
                LogUtil.loge("动态点赞和收藏回调信息"+s);
                AddLikeOrFollowModel bean = GSONUtil.getEntity(s, AddLikeOrFollowModel.class);
                if (null != bean) {
                    getView().cancleLikeOrFollow(status, bean, position, type);
                } else {
                    showFragmentToast("数据解析错误");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 关注用户
     */
    public void onFocusUser(final int enabled, final String toUserId, final int position) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                RequstBodyFocus body = new RequstBodyFocus();
                body.setEnabled(enabled);
                body.setToUserId(toUserId);
                return dynamicApi.onFocusUser(getHeadersMap(POST, "/lbs/gs/user/saveUsersRelation"), body);
            }

            @Override
            public void onSuccess(String s) {
                DynamicFocusModel bean = GSONUtil.getEntity(s, DynamicFocusModel.class);
                if (null != bean) {
                    getView().resultFocus(bean, position);
                } else {
                    showFragmentToast("数据解析错误");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 添加评论  添加回复
     */

    public void addCommentOrReply(final int position, final RequestBodyComment body) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.addCommentOrReply(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicComment"), body);
            }

            @Override
            public void onSuccess(String s) {
                AddCommentOrReplyModel bean = GSONUtil.getEntity(s, AddCommentOrReplyModel.class);
                if (null != bean) {
                    getView().addCommentOrReply(position, bean);
                } else {
                    showFragmentToast("数据解析错误");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 分享动态
     */
    public void shareDynamic(final String dynamicId) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.shareDynamic(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicShare"), new TopicIdBody(dynamicId));
            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

}
