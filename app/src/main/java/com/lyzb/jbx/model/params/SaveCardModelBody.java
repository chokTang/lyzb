package com.lyzb.jbx.model.params;

import com.lyzb.jbx.model.me.IntroductionContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/6/20  15:26
 * Desc:
 */
public class SaveCardModelBody {
    public String id;//自定义模块ID
    public String modularName;//自定义模块名称
    public Integer belongType;//模块所属类别 1：个人 2：企业
    public String objectId;//对象Id，(个人模块对应user_id 企业模块对应distributor_id);belongType=2是才必须传
    public Integer sort;//sort
    public Integer showState;//示标志（0：不显示 1：显示）【修改状态用】
    public Integer defualtModular;//默认版本标志（0:自定义模板 1：默认模板）
    List<IntroductionContent> paragraphDTOList;


    public Integer getDefualtModular() {
        return defualtModular;
    }

    public void setDefualtModular(Integer defualtModular) {
        this.defualtModular = defualtModular;
    }

    public Integer getShowState() {
        return showState;
    }

    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getModularName() {
        return modularName;
    }

    public void setModularName(String modularName) {
        this.modularName = modularName;
    }

    public Integer getBelongType() {
        return belongType;
    }

    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<IntroductionContent> getParagraphDTOList() {
        if (null == paragraphDTOList) return new ArrayList<>();
        return paragraphDTOList;
    }

    public void setParagraphDTOList(List<IntroductionContent> paragraphDTOList) {
        this.paragraphDTOList = paragraphDTOList;
    }


}
