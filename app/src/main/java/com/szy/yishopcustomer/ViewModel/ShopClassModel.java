package com.szy.yishopcustomer.ViewModel;

import com.szy.yishopcustomer.ResponseModel.ShopClass.ClsList2Model;

import java.util.List;

/**
 * Created by liwei on 17/8/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopClassModel {
    public String cls_id;// "3",
    public String cls_name;// "教育培训",
    public String parent_id;// "0",
    public String cls_level;// 1,
    public String keywords;// null,
    public String cls_desc;// null,
    public String is_show;// "1",
    public String is_parent;// "1",
    public String cls_sort;// "1",
    public String cls_image;// null,
    public String is_hot;// "1",
    public String shop_count;// "14",
    public List<ClsList2Model> cls_list_2;
    public String level = "1";
    public boolean Click;

    public boolean isClick() {
        return Click;
    }

    public void setClick(boolean click) {
        Click = click;
    }

    public ShopClassModel() {

    }

    public String getCls_id() {
        return cls_id;
    }

    public void setCls_id(String cls_id) {
        this.cls_id = cls_id;
    }

    public String getCls_name() {
        return cls_name;
    }

    public void setCls_name(String cls_name) {
        this.cls_name = cls_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCls_level() {
        return cls_level;
    }

    public void setCls_level(String cls_level) {
        this.cls_level = cls_level;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCls_desc() {
        return cls_desc;
    }

    public void setCls_desc(String cls_desc) {
        this.cls_desc = cls_desc;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(String is_parent) {
        this.is_parent = is_parent;
    }

    public String getCls_sort() {
        return cls_sort;
    }

    public void setCls_sort(String cls_sort) {
        this.cls_sort = cls_sort;
    }

    public String getCls_image() {
        return cls_image;
    }

    public void setCls_image(String cls_image) {
        this.cls_image = cls_image;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getShop_count() {
        return shop_count;
    }

    public void setShop_count(String shop_count) {
        this.shop_count = shop_count;
    }

    public List<ClsList2Model> getCls_list_2() {
        return cls_list_2;
    }

    public void setCls_list_2(List<ClsList2Model> cls_list_2) {
        this.cls_list_2 = cls_list_2;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
