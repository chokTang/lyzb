package com.lyzb.jbx.model.cenum;

import com.lyzb.jbx.R;

public enum CompanyItemType {
    CompanyAddOrgan("添加机构", R.drawable.union_company_addorgan),
    CompanyAddStaff("添加员工", R.drawable.union_company_addstaff),
    CompanyWeb("企业官网", R.drawable.union_company_web),
    CompanyCircle("企业圈子", R.drawable.union_company_dynamic),
    CompanyApply("申请列表", R.drawable.union_company_list),
    CompanyAdmin("管理员", R.drawable.union_company_member),
    CompanyShop("企业商城", R.drawable.union_company_shop),
    CompanyStatistics("统计数据", R.drawable.union_company_data),
    CustomerManger("客户管理", R.drawable.union_company_customer),
    CompanyExit("退出企业", R.drawable.union_company_info),

    CompanyInfo("基本信息", R.drawable.union_company_info),
    CompanyAccount("企业帐号", R.drawable.union_company_account),
    CompanyMember("企业成员", R.drawable.union_company_member),
    CompanyApplyOperate("申请运营", R.drawable.union_company_apply_yy),
    CompanyApplyDistribution("申请经销", R.drawable.union_company_apply_jx);

    String value;
    Object imgUrl;

    CompanyItemType(String value, Object imgUrl) {
        this.value = value;
        this.imgUrl = imgUrl;
    }

    public String getValue() {
        return value;
    }

    public Object getImgUrl() {
        return imgUrl;
    }
}
