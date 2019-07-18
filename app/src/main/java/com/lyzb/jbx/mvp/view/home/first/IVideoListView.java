package com.lyzb.jbx.mvp.view.home.first;

import com.lyzb.jbx.model.dynamic.AddCommentOrReplyModel;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowModel;
import com.lyzb.jbx.model.dynamic.DynamicCommentModel;
import com.lyzb.jbx.model.dynamic.DynamicFocusModel;
import com.lyzb.jbx.mvp.view.dynamic.IDynamicDetailView;

public interface IVideoListView {
    void onSuccess(boolean isRefrsh, DynamicCommentModel model);

    void onFinshOrLoadMore(boolean isRefrsh);


    void addLikeOrFollow(int status, AddLikeOrFollowModel model, int position, int type);

    void cancleLikeOrFollow(int status, AddLikeOrFollowModel model, int position,int type);

    void resultFocus(DynamicFocusModel model, int position);

    void addCommentOrReply(int position, AddCommentOrReplyModel model);
}
