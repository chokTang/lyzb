package com.lyzb.jbx.model.me.company;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lyzb.jbx.adapter.me.company.MyCompanyAdapter;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/6/18 14:01
 */

public class OrganThreeLvModel implements MultiItemEntity {

    /**
     * orgId : ef9e9cb75fa44c98a2437c48b4bacce6
     * orgName : 6月测试新增企业_1
     * orgMemNum : 0
     * parentId : 0
     */

    private String id;
    private String orgId;
    private String companyName;
    private String orgName;
    private int membersNum;
    private int orgMemNum;
    private String parentId;
    private boolean last;
    private boolean last2;
    /**
     * 是否还有下级
     */
    private boolean moreOrgan;

    private List<OrganThreeLvModel> children;


    public String getId() {
        return TextUtils.isEmpty(id) ? orgId : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? orgName : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getMembersNum() {
        return membersNum == 0 ? orgMemNum : membersNum;
    }

    public void setMembersNum(int membersNum) {
        this.membersNum = membersNum;
    }

    public void setOrgMemNum(int orgMemNum) {
        this.orgMemNum = orgMemNum;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isMoreOrgan() {
        return moreOrgan;
    }

    public void setMoreOrgan(boolean moreOrgan) {
        this.moreOrgan = moreOrgan;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isLast2() {
        return last2;
    }

    public void setLast2(boolean last2) {
        this.last2 = last2;
    }

    public List<OrganThreeLvModel> getChildren() {
        return children;
    }

    public void setChildren(List<OrganThreeLvModel> children) {
        this.children = children;
    }

    @Override
    public int getItemType() {
        return MyCompanyAdapter.TYPE_THREELV_ORGAN;
    }
}
