package com.lyzb.jbx.model.me.company;

import android.text.TextUtils;

import com.lyzb.jbx.model.me.CustomModular;
import com.lyzb.jbx.model.me.IntroductionContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/6/25  15:18
 * Desc:
 */
public class CompanyModelModel {


    /**
     * msg : 查询成功
     * code : 200
     * address : 重庆重庆重庆
     * phone : 15512341234
     * groupId :
     * canEdit : true
     * id : ec813bd3da56444d9c9ebbb8d58734f0
     * infoList : [{"id":359,"contentType":"1","modularId":38,"objectId":"ec813bd3da56444d9c9ebbb8d58734f0","sort":1,"showState":1,"graphContent":"自定义自定义内容内容","defualtModular":4},{"id":359,"contentType":"1","modularId":38,"objectId":"ec813bd3da56444d9c9ebbb8d58734f0","sort":1,"showState":1,"graphContent":"自定义自定义内容内容","defualtModular":4}]
     * mapLat :
     * mapLng :
     */

    private String msg;
    private String code;
    private String address;
    private String phone;
    private String groupId;
    private boolean canEdit;
    private int editSt;
    private String id;
    private String mapLat;
    private String mapLng;
    private List<IntroductionContent> defualtModularList;
    private List<IntroductionContent> modularList;
    private List<CustomModular> customModular;


    public int isEditSt() {
        return editSt;
    }

    public void setEditSt(int editSt) {
        this.editSt = editSt;
    }

    public List<IntroductionContent> getModularList() {
        if (null == modularList) return new ArrayList<>();
        return modularList;
    }

    public void setModularList(List<IntroductionContent> modularList) {
        this.modularList = modularList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        if (TextUtils.isEmpty(code)) return "";
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMapLat() {
        if (null ==mapLat || TextUtils.isEmpty(mapLat)) return "0.0";
        return mapLat;
    }

    public void setMapLat(String mapLat) {
        this.mapLat = mapLat;
    }

    public String getMapLng() {
        if (null ==mapLng || TextUtils.isEmpty(mapLng)) return "0.0";
        return mapLng;
    }

    public void setMapLng(String mapLng) {
        this.mapLng = mapLng;
    }

    public List<IntroductionContent> getInfoList() {
        if (defualtModularList == null) return new ArrayList<>();
        return defualtModularList;
    }

    public void setInfoList(List<IntroductionContent> infoList) {
        this.defualtModularList = infoList;
    }

    public List<CustomModular> getCustomModular() {
        if (customModular == null) return new ArrayList<>();
        return customModular;
    }

    public void setCustomModular(List<CustomModular> customModular) {
        this.customModular = customModular;
    }
}
