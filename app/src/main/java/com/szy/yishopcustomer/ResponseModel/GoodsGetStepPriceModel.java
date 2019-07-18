package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by Smart on 2017/9/11.
 */

public class GoodsGetStepPriceModel {

    /**
     * code : 0
     * data : {"goods_price":"6.00","goods_price_format":"￥6.00","total":"18.00","total_format":"￥18.00"}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * goods_price : 6.00
         * goods_price_format : ￥6.00
         * total : 18.00
         * total_format : ￥18.00
         */

        public String goods_price;
        public String goods_price_format;
        public String total;
        public String total_format;
    }
}
