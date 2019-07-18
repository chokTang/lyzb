package com.lyzb.jbx.model.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/12  9:01
 * Desc: 完善信息 请求数据 源
 */
public class RequestPerfectBean implements Serializable {

    public String concernProfession;//感兴趣的行业(行业id,","隔开)
    public String gsName;//姓名
    public String headimg;//头像地址
    public String position;//岗位
    public String professionId;//熟悉行业(行业id,","隔开)
    public String residence;//所在城市(城市region_code,以";"隔开。省 ;市;区)
    public String sex;//称呼(1.先生 2.女士)
    public String shopName;//shopName
    public String regionName;//地址中文名
    public String currentDepartmentID;//企业ID
    public List<BusinessModel> professionName;//行业名字

    public List<BusinessModel> getProfessionName() {
        return professionName;
    }

    public void setProfessionName(List<BusinessModel> professionName) {
        this.professionName = professionName;
    }

    public String getCurrentDepartmentID() {
        return currentDepartmentID;
    }

    public void setCurrentDepartmentID(String currentDepartmentID) {
        this.currentDepartmentID = currentDepartmentID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getConcernProfession() {
        return concernProfession;
    }

    public void setConcernProfession(String concernProfession) {
        this.concernProfession = concernProfession;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
