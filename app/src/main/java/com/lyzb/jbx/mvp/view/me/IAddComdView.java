package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.me.company.CompanyModelModel;
import com.lyzb.jbx.model.me.company.UpdateCompanyMsg;
import com.lyzb.jbx.model.params.FileBody;

import java.util.List;

/**
 * 创建企业
 */

public interface IAddComdView {

    void getVideo(String videoUrl);

    void getTypeList(List<BusinessModel> list);

    void getData(ComdDetailModel model);

    void getModelData(CompanyModelModel model);

    void getModelDataFail();

    void onFinshBack();

    void onCardComd();

    void joinSuccess();

    void joinFail();

    void getInfo(List<FileBody> uplist);

    void getHonor(List<FileBody> uplist);

    void getBrand(List<FileBody> uplist);

    void getCustom(List<FileBody> uplist);

    void saveInfo(String string, int type, boolean isImg);

    void deleteModelContent(String string, int type);

    void deleteModel(String string);

    void update(String string,int type,String content);


}
