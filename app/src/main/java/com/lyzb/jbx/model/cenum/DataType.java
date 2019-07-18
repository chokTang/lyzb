package com.lyzb.jbx.model.cenum;

/**
 * 数据类型
 */
public enum DataType {
    CARD(1),//名片
    ALL(0),//全部
    DYNAMIC(2),//动态
    GOODS(3),//商品
    ACRTICE(4);//热文

    int value;

    DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
