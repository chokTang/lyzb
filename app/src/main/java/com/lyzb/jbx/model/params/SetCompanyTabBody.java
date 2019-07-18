package com.lyzb.jbx.model.params;

import com.lyzb.jbx.model.me.CompanyCircleTabModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/15 14:32
 */

public class SetCompanyTabBody {
    private int type;
    private String groupId;
    private List<CompanyCircleTabModel> gsCardFunctionSetList;

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

    public List<CompanyCircleTabModel> getGsCardFunctionSetList() {
        return gsCardFunctionSetList;
    }

    public void setGsCardFunctionSetList(List<CompanyCircleTabModel> gsCardFunctionSetList) {
        this.gsCardFunctionSetList = gsCardFunctionSetList;
    }
}
