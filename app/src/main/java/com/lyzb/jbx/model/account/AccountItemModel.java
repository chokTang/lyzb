package com.lyzb.jbx.model.account;

import com.lyzb.jbx.model.common.DynamicItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索用户下-单个用户动态是实体
 */
public class AccountItemModel {
    private List<DynamicItemModel> list;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DynamicItemModel> getList() {
        if (list == null) return new ArrayList<>();
        return list;
    }

    public void setList(List<DynamicItemModel> list) {
        this.list = list;
    }
}
