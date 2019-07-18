package com.lyzb.jbx.model.me;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/7/3  17:25
 * Desc:
 */
public class Modular implements Serializable {
    private String id;
    private String modularName;

    public String getId() {
        if (id == null) return "";
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModularName() {
        return modularName;
    }

    public void setModularName(String modularName) {
        this.modularName = modularName;
    }
}
