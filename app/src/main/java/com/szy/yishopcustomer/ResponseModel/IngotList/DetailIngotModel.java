package com.szy.yishopcustomer.ResponseModel.IngotList;

/**
 * @author wyx
 * @role 元宝明细 model
 * @time 2018 10:30
 */

public class DetailIngotModel {

    /**
     * log_id : 23
     * log_sn : 1120180320110437928214
     * user_id : 1626
     * shop_id : 772
     * admin_id : 0
     * current_points : 200
     * changed_points : 100
     * order_id : null
     * reason : 11
     * add_time : 1521515077
     * is_delete : 0
     * delete_time : 0
     * note : 调节账户积分-增加<br>积分所属店铺：安辉的测试店铺2<br>增加积分：100积分<br>调整日期：2018-03-20 11:04:37<br>操作人员：admin<br>备注：
     * is_add : 0
     * overdue_time : 1529291077
     */

    private String log_id;
    private String log_sn;
    private String user_id;
    private String shop_id;
    private String admin_id;
    private String current_points;
    private String changed_points;
    private Object order_id;
    private String reason;
    private String add_time;
    private String is_delete;
    private String delete_time;
    private String note;
    private String is_add;
    private int overdue_time;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getLog_sn() {
        return log_sn;
    }

    public void setLog_sn(String log_sn) {
        this.log_sn = log_sn;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getCurrent_points() {
        return current_points;
    }

    public void setCurrent_points(String current_points) {
        this.current_points = current_points;
    }

    public String getChanged_points() {
        return changed_points;
    }

    public void setChanged_points(String changed_points) {
        this.changed_points = changed_points;
    }

    public Object getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Object order_id) {
        this.order_id = order_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIs_add() {
        return is_add;
    }

    public void setIs_add(String is_add) {
        this.is_add = is_add;
    }

    public int getOverdue_time() {
        return overdue_time;
    }

    public void setOverdue_time(int overdue_time) {
        this.overdue_time = overdue_time;
    }
}
