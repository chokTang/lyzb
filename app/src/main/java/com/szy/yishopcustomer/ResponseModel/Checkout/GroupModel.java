package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.szy.yishopcustomer.Constant.ViewType;

import java.util.List;

import static com.szy.yishopcustomer.Constant.ViewType.VIEW_TYPE_GROUP;

/**
 * Created by 宗仁 on 16/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupModel {
    public String title;
    public String selectedItem;
    public List items;
    public boolean isExpanded;
    public ViewType viewType;
    public boolean isShopBouns;//店铺红包的线条需要特殊处理

    public GroupModel(String title, String selectedItem, List items) {
        this.title = title;
        this.selectedItem = selectedItem;
        this.items = items;
        this.viewType = VIEW_TYPE_GROUP;
    }

}
