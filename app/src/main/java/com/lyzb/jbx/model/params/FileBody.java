package com.lyzb.jbx.model.params;

import android.text.TextUtils;

/**
 * Created by :TYK
 * Date: 2019/3/12  17:42
 * Desc:
 */
public class FileBody {
    private String file;
    private int sort;

    public FileBody() {
    }

    public FileBody(String file, int sort) {
        this.file = file;
        this.sort = sort;
    }

    public String getFile() {
        if (TextUtils.isEmpty(file)) return "";
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "FileBody{" +
                "file='" + file + '\'' +
                ", sort=" + sort +
                '}';
    }
}
