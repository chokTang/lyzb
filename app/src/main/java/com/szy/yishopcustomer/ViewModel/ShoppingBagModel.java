package com.szy.yishopcustomer.ViewModel;

import java.util.List;

/**
 * Created by Smart on 2017/8/8.
 */

public class ShoppingBagModel {

    /**
     * code : 0
     * data : [{"sku_id":"1276","sku_name":"购物袋小号","sku_image":"/shop/2/images/2017/08/07/15020860211554.png","sku_images":null,"goods_id":"508","spec_ids":null,"spec_vids":null,"spec_names":null,"goods_price":"0.10","mobile_price":"0.00","market_price":"0.00","goods_number":"99996","goods_sn":null,"goods_barcode":null,"warn_number":"0","pc_desc":null,"mobile_desc":null,"is_spu":"1","is_enable":"1"},{"sku_id":"1277","sku_name":"购物袋中号","sku_image":"/shop/2/images/2017/08/07/15020860211554.png","sku_images":null,"goods_id":"509","spec_ids":null,"spec_vids":null,"spec_names":null,"goods_price":"0.20","mobile_price":"0.00","market_price":"0.00","goods_number":"99996","goods_sn":null,"goods_barcode":null,"warn_number":"0","pc_desc":null,"mobile_desc":null,"is_spu":"1","is_enable":"1"},{"sku_id":"1278","sku_name":"购物袋大号","sku_image":"/shop/2/images/2017/08/07/15020860211554.png","sku_images":null,"goods_id":"510","spec_ids":null,"spec_vids":null,"spec_names":null,"goods_price":"0.30","mobile_price":"0.00","market_price":"0.00","goods_number":"99996","goods_sn":null,"goods_barcode":"6903244675147","warn_number":"0","pc_desc":null,"mobile_desc":null,"is_spu":"1","is_enable":"1"}]
     * message :
     */
    public int code;
    public String message;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * sku_id : 1276
         * sku_name : 购物袋小号
         * sku_image : /shop/2/images/2017/08/07/15020860211554.png
         * sku_images : null
         * goods_id : 508
         * spec_ids : null
         * spec_vids : null
         * spec_names : null
         * goods_price : 0.10
         * mobile_price : 0.00
         * market_price : 0.00
         * goods_number : 99996
         * goods_sn : null
         * goods_barcode : null
         * warn_number : 0
         * pc_desc : null
         * mobile_desc : null
         * is_spu : 1
         * is_enable : 1
         */

        public String sku_id;
        public String sku_name;
        public String sku_image;
        public Object sku_images;
        public String goods_id;
        public Object spec_ids;
        public Object spec_vids;
        public Object spec_names;
        public String goods_price;
        public String mobile_price;
        public String market_price;
        public String goods_number;
        public Object goods_sn;
        public Object goods_barcode;
        public String warn_number;
        public Object pc_desc;
        public Object mobile_desc;
        public String is_spu;
        public String is_enable;
    }
}
