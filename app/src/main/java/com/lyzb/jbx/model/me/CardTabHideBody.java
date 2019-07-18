package com.lyzb.jbx.model.me;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/5/17  9:16
 * Desc:
 */
public class CardTabHideBody {

    public int type;
    public String groupId;
    public List<TabShowHideBean> gsCardFunctionSetList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<TabShowHideBean> getGsCardFunctionSetList() {
        return gsCardFunctionSetList;
    }

    public void setGsCardFunctionSetList(List<TabShowHideBean> gsCardFunctionSetList) {
        this.gsCardFunctionSetList = gsCardFunctionSetList;
    }
}
