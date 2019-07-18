package com.lyzb.jbx.model.search;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shidengzhong on 2019/3/4.
 */
@Entity
public class HistroyModel {
    @Id(autoincrement = true)
    private Long id;
    private String value;
    private long time;

    public HistroyModel(String value) {
        this.value = value;
        time = System.currentTimeMillis();
    }

    @Generated(hash = 2024472560)
    public HistroyModel(Long id, String value, long time) {
        this.id = id;
        this.value = value;
        this.time = time;
    }

    @Generated(hash = 299346271)
    public HistroyModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
