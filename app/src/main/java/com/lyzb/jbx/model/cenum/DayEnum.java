package com.lyzb.jbx.model.cenum;

public enum DayEnum {
    DAY_ALL("全部"),
    DAY_THIRTY("近30天"),
    DAY_SEVEN("近7天"),
    DAY_ZERO("今日");

    String value;

    DayEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
