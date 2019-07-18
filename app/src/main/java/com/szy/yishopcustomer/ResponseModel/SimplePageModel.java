package com.szy.yishopcustomer.ResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 2017/5/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SimplePageModel<T> {
    //当前页
    private int currentPage;
    //总页数
    private int pageCount;
    //数据
    private List<T> list;

    private List comment_counts;

    public SimplePageModel() {
        this.currentPage = 0;
        this.list = new ArrayList<>();
    }


    public List getComment_counts() {
        return comment_counts;
    }

    public void setComment_counts(List comment_counts) {
        this.comment_counts = comment_counts;
    }

    public void listClean(){
        list.clear();
    }



    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void listAddAll(List<T> list) {
        this.list.addAll(list);
    }
}
