package com.szy.yishopcustomer.ViewModel;

import com.szy.yishopcustomer.ResponseModel.CateforyModel.SubcategoryModel;

import java.util.List;

/**
 * Created by zongren on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CategoryModel {
    private String mCatName;
    private String mCatId;
    private String mCatLink;//增加了商品分类打开链接功能
    private boolean Click;
    private String mCatImage;
    private String mParentId = "0";
    private String mCatLevel = "1";
    private boolean haveChild;
    private List<SubcategoryModel> mItems;

    public boolean isHaveChild() {
        return haveChild;
    }

    public void setHaveChild(boolean haveChild) {
        this.haveChild = haveChild;
    }

    public CategoryModel() {
    }

    public String getCatId() {
        return mCatId;
    }

    public void setCatId(String catId) {
        mCatId = catId;
    }

    public String getCatImage() {
        return mCatImage;
    }

    public void setCatImage(String catImage) {
        mCatImage = catImage;
    }

    public String getCatLevel() {
        return mCatLevel;
    }

    public void setCatLevel(String catLevel) {
        mCatLevel = catLevel;
    }

    public String getCatName() {
        return mCatName;
    }

    public void setCatName(String catName) {
        mCatName = catName;
    }

    public String getParentId() {
        return mParentId;
    }

    public void setParentId(String parentId) {
        mParentId = parentId;
    }

    public boolean isClick() {
        return Click;
    }

    public void setClick(boolean click) {
        Click = click;
    }

    public List<SubcategoryModel> getCatItems() {
        return mItems;
    }

    public void setCatItems(List<SubcategoryModel> items) {
        mItems = items;
    }

    public String getCatLink() {
        return mCatLink;
    }

    public void setCatLink(String mCatLink) {
        this.mCatLink = mCatLink;
    }
}
