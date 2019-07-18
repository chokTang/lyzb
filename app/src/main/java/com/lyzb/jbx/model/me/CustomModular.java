package com.lyzb.jbx.model.me;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/6/20  16:50
 * Desc:
 */
public class CustomModular implements Serializable {

    public String modularName;
    private String id;
    private String objectId;
    private String userExtId;
    private Integer belongType;
    private String createTime;
    private String modifyTime;
    private Integer sort;
    private Integer showState;
    private String operUserId;
    private String defualtModular;
    List<IntroductionContent> paragraphVoList;
    List<IntroductionContent> paragraphDTOList;//用于

    public String getDefualtModular() {
        return defualtModular;
    }

    public void setDefualtModular(String defualtModular) {
        this.defualtModular = defualtModular;
    }

    public List<IntroductionContent> getParagraphDTOList() {
        if (paragraphDTOList == null) return new ArrayList<>();
        return paragraphDTOList;
    }

    public void setParagraphDTOList(List<IntroductionContent> paragraphDTOList) {
        this.paragraphDTOList = paragraphDTOList;

    }

    public String getModularName() {
        return modularName;
    }

    public void setModularName(String modularName) {
        this.modularName = modularName;
    }

    public List<IntroductionContent> getParagraphVoList() {
        if (paragraphVoList == null) return new ArrayList<>();
        return paragraphVoList;
    }

    public void setParagraphVoList(List<IntroductionContent> paragraphVoList) {
        this.paragraphVoList = paragraphVoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserExtId() {
        return userExtId;
    }

    public void setUserExtId(String userExtId) {
        this.userExtId = userExtId;
    }

    public Integer getBelongType() {
        return belongType;
    }

    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getShowState() {
        if (null == showState) return 0;
        return showState;
    }

    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }
}
