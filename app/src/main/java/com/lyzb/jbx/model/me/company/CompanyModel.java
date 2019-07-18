package com.lyzb.jbx.model.me.company;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lyzb.jbx.adapter.me.company.MyCompanyAdapter;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/6/18 14:00
 */

public class CompanyModel extends AbstractExpandableItem<OrganTowLvModel> implements MultiItemEntity {

    /**
     * id : ef9e9cb75fa44c98a2437c48b4bacce6
     * companyName : 6月测试新增企业_1
     * logoFilePath : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/04/01/5B587BC1-2498-4010-AAB2-3DA80FEE5446.jpeg
     * industryName : 快速消费品
     * membersNum : 4
     * role : 1
     * orgList : [{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":0,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":0,"parentId":"0"},{"orgId":"ef9e9cb75fa44c98a2437c48b4bacce6","orgName":"6月测试新增企业_1","orgMemNum":0,"parentId":"0"}]
     */

    private String id;
    private String companyName;
    private String logoFilePath;
    private String industryName;
    private int membersNum;
    private int role;
    private boolean hide;
    private boolean noBranch;
    private List<OrganTowLvModel> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogoFilePath() {
        return logoFilePath;
    }

    public void setLogoFilePath(String logoFilePath) {
        this.logoFilePath = logoFilePath;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getMembersNum() {
        return membersNum;
    }

    public void setMembersNum(int membersNum) {
        this.membersNum = membersNum;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<OrganTowLvModel> getChildren() {
        return children;
    }

    public void setChildren(List<OrganTowLvModel> children) {
        this.children = children;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isNoBranch() {
        return noBranch;
    }

    public void setNoBranch(boolean noBranch) {
        this.noBranch = noBranch;
    }

    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    @Override
    public int getLevel() {
        return MyCompanyAdapter.TYPE_COMPANY;
    }

    @Override
    public int getItemType() {
        return MyCompanyAdapter.TYPE_COMPANY;
    }
}
