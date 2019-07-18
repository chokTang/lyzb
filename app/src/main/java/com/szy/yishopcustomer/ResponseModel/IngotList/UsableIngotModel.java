package com.szy.yishopcustomer.ResponseModel.IngotList;

/**
 * @author wyx
 * @role 可用元宝 model
 * @time 2018 10:29
 */

public class UsableIngotModel {

    /**
     * code : 0
     * message :
     * data : {"total_integral":{"integral_num":"0"}}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total_integral : {"integral_num":"0"}
         */

        private TotalIntegralBean total_integral;

        public TotalIntegralBean getTotal_integral() {
            return total_integral;
        }

        public void setTotal_integral(TotalIntegralBean total_integral) {
            this.total_integral = total_integral;
        }

        public static class TotalIntegralBean {
            /**
             * integral_num : 0
             */

            private String integral_num;

            public String getIntegral_num() {
                return integral_num;
            }

            public void setIntegral_num(String integral_num) {
                this.integral_num = integral_num;
            }
        }
    }
}
