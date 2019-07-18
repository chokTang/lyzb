package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by Smart on 2018/1/16.
 */

public class PaymentIdenModel {

    /**
     * code : 0
     * data : {"payment_iden":"70433fc4dc76dff7e13682b260c7cc8e","amount_format":"","code_format":"","clientRuleCache":"cache"}
     * message : 验证成功
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * payment_iden : 70433fc4dc76dff7e13682b260c7cc8e
         * amount_format :
         * code_format :
         * clientRuleCache : cache
         */

        public String payment_iden;
        public String amount_format;
        public String code_format;
        public String clientRuleCache;
    }
}
