package com.lyzb.jbx.model.params;

/**
 * Created by :TYK
 * Date: 2019/6/20  14:50
 * Desc:
 */
public class UpdateModelBody {

    public String id;//主键id：新增不传/修改必传
    public String modularId;//模板id
    public String contentType;//内容类别 1：文字 2：图片 3.视频
    public Integer defualtModular;//默认模块（当前提供的模块 0:自定义 1：个人信息 2 ：我能提供 3：我需要的 4：公司简介 5：企业文化/荣誉/团队 6：品牌展示 7.宣传视频）
    public Integer sort;//排序值
    public Integer belongType;//1:个人 2：企业
    public String graphContent;//段落内容（或图片地址）
    public String objectId;//对象id（user_id或 distributor_id）,记录个人相关模时块存user_id,记录企业相关模块时存distributor_id；belongType=2时必须传

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModularId() {
        return modularId;
    }

    public void setModularId(String modularId) {
        this.modularId = modularId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getDefualtModular() {
        return defualtModular;
    }

    public void setDefualtModular(Integer defualtModular) {
        this.defualtModular = defualtModular;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getBelongType() {
        return belongType;
    }

    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }

    public String getGraphContent() {
        return graphContent;
    }

    public void setGraphContent(String graphContent) {
        this.graphContent = graphContent;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
