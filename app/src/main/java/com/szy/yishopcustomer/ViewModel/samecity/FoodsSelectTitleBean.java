package com.szy.yishopcustomer.ViewModel.samecity;

/**
 * Created by :TYK
 * Date: 2019/1/2  15:21
 * Desc:
 */
public class FoodsSelectTitleBean {


    /**
     * personTypeId : 1
     * name : 单人
     * jcPrice : 177
     */

    private int personTypeId;
    private String name;
    private String jcPrice;

    public String getJcPrice() {
        return jcPrice;
    }

    public void setJcPrice(String jcPrice) {
        this.jcPrice = jcPrice;
    }

    public int getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(int personTypeId) {
        this.personTypeId = personTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
