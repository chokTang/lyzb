package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.VoiceModel;
import com.lyzb.jbx.model.params.FileBody;

import java.util.List;

/**
 * 我的-个人信息
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICInfoView {

    void onCardData(CardModel model);

    void upLoadOs(String url);

    void upLoadVoiceSuccess(VoiceModel voiceModel);

    void deleteSuccess();

    void getInfo(List<FileBody> uplist);

    void getProvide(List<FileBody> uplist);

    void getNeed(List<FileBody> uplist);

    void getCustom(List<FileBody> uplist);

    void saveInfo(String string,int type,boolean isImg);

    void deleteModelContent(String string,int type);

    void deleteModel(String string);

    void update(String string,int type,String content);

    void onGoodList(CardMallModel model);
}
