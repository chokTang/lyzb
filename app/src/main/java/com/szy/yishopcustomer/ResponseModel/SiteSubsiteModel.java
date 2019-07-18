package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by Smart on 2017/12/18.
 */

public class SiteSubsiteModel {

    /**
     * code : 0
     * data : {"city":"秦皇岛市","site_id":"12"}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * city : 秦皇岛市
         * site_id : 12
         */

        public String city;
        public String site_id;
    }
}
