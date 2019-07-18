package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.AcsRecomdModel;

import java.util.List;

/**
 *
 *
 */

public interface IAcsDetView {

    void onAcsList(boolean isRefresh, List<AcsRecomdModel> list);

    void onSettingAccountResult();
}
