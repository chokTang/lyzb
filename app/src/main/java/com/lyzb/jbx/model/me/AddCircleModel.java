package com.lyzb.jbx.model.me;

/**
 * @author wyx
 * @role 创建圈子 model
 * @time 2019 2019/3/18 15:31
 */

public class AddCircleModel {

    private String background;              //圈子背景图片
    private String desc;                    //圈子描述
    private String groupname;               //圈子名称
    private String logo;                    //圈子logo
    private boolean publicStr;           //是否需要审核
    private String orgId;                  //企业id


    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isPublicStr() {
        return publicStr;
    }

    public void setPublicStr(boolean publicStr) {
        this.publicStr = publicStr;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
