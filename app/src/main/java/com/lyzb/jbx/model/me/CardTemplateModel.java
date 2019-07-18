package com.lyzb.jbx.model.me;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/5/6  10:21
 * Desc:
 */
public class CardTemplateModel implements Serializable {



    /**
     * id : 1
     * templateName : 通用模板
     * templateImg : 通用模板
     * type : 1
     * remark : 通用模板
     * selectState : false
     */

    private String id;
    private String templateName;
    private String templateImg;
    private int type;
    private String remark;
    private boolean selectState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateImg() {
        return templateImg;
    }

    public void setTemplateImg(String templateImg) {
        this.templateImg = templateImg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isSelectState() {
        return selectState;
    }

    public void setSelectState(boolean selectState) {
        this.selectState = selectState;
    }
}
