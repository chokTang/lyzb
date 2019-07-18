package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by Smart on 2018/1/16.
 */

public class GetCodeModel {


    /**
     * code : 0
     * data : {"id":899,"code":"301516073718000137","type":0,"amount":0,"valid_time":1516073838,"user_id":137,"status":0,"pay_user_id":0}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * id : 899
         * code : 301516073718000137
         * type : 0
         * amount : 0
         * valid_time : 1516073838
         * user_id : 137
         * status : 0
         * pay_user_id : 0
         */

        public int id;
        public String code;
        public int type;
        public int amount;
        public int valid_time;
        public int user_id;
        public int status;
        public int pay_user_id;
    }
}
