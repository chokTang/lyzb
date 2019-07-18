package com.lyzb.jbx.model.account;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/3/11  14:41
 * Desc: 行业数据源
 */
public class BusinessModel implements Serializable{

    /**
     * id : 1
     * name : 运营
     */

    private int id;
    private boolean isChecked = false;
    private String name = "";

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
