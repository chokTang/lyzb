package com.lyzb.jbx.model.me.company;

public class StaffModel {

    /**
     * gsName : xxxx
     * extId : 7643
     * position : boss
     * headimg : https://wx.qlogo.cn/mmopen/vi_32/DEqsKbjAcR20FG5tHhSga8RgHFPeTbib810QSNJP0Tw35kc2fWGgric2w0VjuicjPuTHEoGUMrghV9NZL3EcLHw0g/132
     * role : 1
     * companyId : ef9e9cb75fa44c98a2437c48b4bacce6
     * userId : 110689
     */

    /**
     * 名称
     */
    private String gsName;
    /**
     * 名片id
     */
    private int extId;
    /**
     * 岗位
     */
    private String position;
    /**
     * 头像
     */
    private String headimg;
    /**
     * 角色（1.创建者 2.普通成员）
     */
    private int role;
    /**
     * 角色
     */
    private String  roleName;
    /**
     * 企业id
     */
    private String companyId;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 可否编辑
     */
    private String isEdit;

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public int getExtId() {
        return extId;
    }

    public void setExtId(int extId) {
        this.extId = extId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }
}
