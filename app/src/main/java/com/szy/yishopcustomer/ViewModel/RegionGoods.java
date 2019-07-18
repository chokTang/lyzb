package com.szy.yishopcustomer.ViewModel;

public class RegionGoods {

    String name;
    String code;
    String select;

    public RegionGoods(String name, String code, String select) {
        super();
        this.name = name;
        this.code = code;
        this.code = select;
    }

    public RegionGoods() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

}
