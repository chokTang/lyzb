package com.lyzb.jbx.model.me.company;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/6/19 13:49
 */

public class ChoiceOrganModel {

    /**
     * msg : 查询成功
     * code : 200
     * data : [{"id":"ec813bd3da56444d9c9ebbb8d58734f0","companyName":"6月测试新增企业_1_机构1(部门)","mParentOrgId":"ef9e9cb75fa44c98a2437c48b4bacce6","children":[{"id":"588ca1daa6144afe96c4a92971049679","companyName":"6月测试新增企业_1_机构1-子(部门)","mParentOrgId":"ec813bd3da56444d9c9ebbb8d58734f0"}]}]
     */

    private String msg;
    private String code;
    private List<ChildrenBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ChildrenBean> getData() {
        return data;
    }

    public void setData(List<ChildrenBean> data) {
        this.data = data;
    }

}
