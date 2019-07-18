package com.szy.yishopcustomer.ViewModel;

/**
 * Created by Smart on 2017/7/27.
 */

public class PickupAddresModel {


    /**
     * code : 0
     * data : {"is_show":1,"pickup_id":3,"pickup_name":"测试自提点-燕大自提点","region_code":"13,03,02","pickup_address":"海港镇东方明珠城(大秦华府南)","address_lng":"119.639559","address_lat":"39.94642","pickup_tel":"13111111111","pickup_desc":"12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890","pickup_images":"/shop/2/images/2017/06/05/14966318402992.png","shop_id":2,"sort":255}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * is_show : 1
         * pickup_id : 3
         * pickup_name : 测试自提点-燕大自提点
         * region_code : 13,03,02
         * pickup_address : 海港镇东方明珠城(大秦华府南)
         * address_lng : 119.639559
         * address_lat : 39.94642
         * pickup_tel : 13111111111
         * pickup_desc : 12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890
         * pickup_images : /shop/2/images/2017/06/05/14966318402992.png
         * shop_id : 2
         * sort : 255
         */

        public int is_show;
        public int pickup_id;
        public String pickup_name;
        public String region_code;
        public String pickup_address;
        public String address_lng;
        public String address_lat;
        public String pickup_tel;
        public String pickup_desc;
        public String pickup_images;
        public int shop_id;
        public int sort;
    }
}
