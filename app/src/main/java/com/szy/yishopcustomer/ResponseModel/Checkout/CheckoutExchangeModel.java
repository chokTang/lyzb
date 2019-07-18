package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.alibaba.fastjson.annotation.JSONField;
import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 2017/12/22.
 */

public class CheckoutExchangeModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public String send_time_desc;
        public String best_time;
        public CartInfoBean cart_info;
        public String pickup_name;
        public String select_pickup_id;
        public String context;
        public List<AddressItemModel> address_list;
        public List<SendTimeItemModel> send_time_list;

        public static class CartInfoBean {
            /**
             * goods_list : [{"goods_id":"13","goods_name":"懒人园艺（LAZYGARDEN） 绿萝植物网纹草小芦荟栀子花文竹发财树办公室内盆栽小盆景 栀子花-小号透明方盆 ","goods_price":"13.80","market_price":"0.00","goods_number":"1","goods_integral":"100","exchange_number":"30","start_time":"1513180800","end_time":"1514044799","add_time":"1513233927","goods_image":"/backend/1/images/2017/12/14/15132338663522.jpg","goods_images":"/backend/1/images/2017/12/14/15132338663522.jpg|/backend/1/images/2017/12/14/15132338710009.jpg|/backend/1/images/2017/12/14/15132338743613.jpg|/backend/1/images/2017/12/14/15132338783104.jpg","goods_video":null,"pc_desc":"<p style=\"text-align:center;\">\r\n\t购物须知：\r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t基地是非常注意质量和售后的\r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t有任何问题请与我们联系，我们都会认真对待，也会非常重视\r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t我们家绿植<span style=\"color:#CC0000;\"><strong>路损包赔并支持7天无理由退换<\/strong>！<\/span> \r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t有任何不合适请在签收后3日内与我们售后拍照联系\r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t目前保温技术只能保证-5℃以上的植物不被冻伤\r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t<span style=\"color:#CC0000;\"><strong>所以<\/strong><\/span><span style=\"color:#CC0000;\"><strong>低温地区请慎拍！<\/strong><\/span> \r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t开春后，针对北方有大型优惠活动，<strong>详情关注收藏店铺哦~<\/strong> \r\n<\/p>\r\n<div style=\"text-align:center;\">\r\n\t<span style=\"color:#CC0000;font-weight:bold;\">栀子花因季节问题，不带花苞发出，但冬季很易养活哦<\/span> \r\n<\/div>\r\n<div style=\"text-align:center;\">\r\n\t<span style=\"color:#CC0000;font-weight:bold;\">栀子花！<\/span> \r\n<\/div>\r\n<p style=\"text-align:center;\">\r\n\t<span style=\"color:#990000;\"><strong><br />\r\n<\/strong><\/span> \r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t<span style=\"font-weight:bold;\">本链接<\/span><span style=\"font-size:large;color:#660000;\"><strong>2盆-5元<\/strong><\/span><span style=\"color:#cc0000;font-weight:bold;\">赠送草头娃娃一只<\/span> \r\n<\/p>\r\n<p style=\"text-align:center;\">\r\n\t<strong><span style=\"font-size:large;color:#660000;\">&nbsp; 4盆-10<\/span><span style=\"color:#cc0000;\">再加赠糖果喷壶一只<\/span><\/strong> \r\n<\/p>\r\n<br />\r\n<img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWaterMark/jfs/t14929/307/592215966/46675/6d45c64a/5a3200beN82887f86.jpg\" /><br />\r\n<a href=\"https://item.jd.com/13652890842.html\" target=\"_blank\"><br />\r\n<\/a><br />\r\n<a href=\"https://item.jd.com/13652890842.html\"><img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t14851/176/581991754/23023/cd566191/5a3200d1Ne81fd611.jpg\" /><\/a><a href=\"https://item.jd.com/14141319454.html#crumb-wrap\"><img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t12955/104/2099498932/27965/4698986c/5a3200d1Nb4c38020.jpg\" /><\/a><a href=\"https://item.jd.com/19409257612.html#crumb-wrap\" target=\"_blank\"><img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t13504/318/2017304016/23383/305b7bff/5a3200d1Nf5d0dac1.jpg\" /><\/a><br />\r\n<a href=\"https://item.jd.com/17853332964.html#crumb-wrap\" target=\"_blank\"><img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t14032/309/2106050331/18878/13022140/5a3200b2N35494d96.jpg\" /><\/a><a href=\"https://item.jd.com/15348834454.html\" target=\"_blank\"><img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t14560/292/581543519/25101/9a49e90b/5a3200d2N6dd9e48b.jpg\" /><\/a><a href=\"https://item.jd.com/15347128155.html\" target=\"_blank\"><img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t15229/293/595970050/27800/f16c48ad/5a3200d1Nd0ad0c4c.jpg\" /><\/a><br />\r\n<br />\r\n<img alt=\"\" id=\"79be25f71eb245bf9ab2a1a489f51081\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t6028/108/5144040866/533608/a3876383/5969d0a3N0e230af8.jpg\" /><br />\r\n<img alt=\"\" class=\"\" src=\"https://img10.360buyimg.com/imgzone/jfs/t11698/321/2643519925/192709/5847fd19/5a1bb9efN761c9e34.jpg\" /><br />\r\n<img alt=\"\" class=\"\" src=\"https://img30.360buyimg.com/popWaterMark/jfs/t14098/204/264258504/196996/f8dd1d9a/5a06ee2fN94a4d89d.jpg\" /><br />\r\n<img alt=\"\" id=\"398ea43a40df4bc5aff03eecbe00eac4\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5767/59/6442565628/181945/ec88abcd/5969b113N5bed720d.jpg\" /><br />\r\n<p>\r\n\t<img alt=\"\" id=\"3a819710fccf4a54bf654a6e85b6534e\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5914/317/5312309711/271491/869d820c/5969b112N623a26a5.jpg\" /><img alt=\"\" id=\"61b7ce82bb2f43e0b0cfa26f900ec944\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5851/134/6378465228/266899/a1fc0ff8/5969b11eNa14f2faa.jpg\" /> \r\n<\/p>\r\n<img alt=\"\" id=\"7a2c01fc63a0481fb2c7145decc312e6\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5797/20/4341845739/261248/15d39c63/5969b115Nff53522e.jpg\" /><br />\r\n<img alt=\"\" id=\"aad8bb13bad74bfa87c934d1b7e7ac88\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5977/317/5192167301/270676/7e401110/5969b116N211cf25f.jpg\" /><br />\r\n<img alt=\"\" id=\"61b7ce82bb2f43e0b0cfa26f900ec944\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5851/134/6378465228/266899/a1fc0ff8/5969b11eNa14f2faa.jpg\" /><br />\r\n<img alt=\"\" id=\"bf29a7901a8c42558bc02f3f5a1aac95\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5731/198/6411536016/193859/2d687f5f/5969b11dNbf0f70a3.jpg\" /><br />\r\n<img alt=\"\" id=\"b71cc81e9c2d4fd1949ee850dbdf43ff\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5638/12/6366206382/206088/7137730b/5969b11bNddbeda0e.jpg\" /><br />\r\n<img alt=\"\" id=\"a8b2aac825cc44e594c8a3cae6e0fdac\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t6121/135/5115183001/278380/99b0018e/5969b11cNeaf00f80.jpg\" /><br />\r\n<img alt=\"\" id=\"c31445591ebb4d5783f08d3e97381f9e\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t6064/61/5104918219/440643/27d655c8/5969b111N7e83069f.jpg\" /><br />\r\n<img alt=\"\" id=\"b8184898249e499f8bc880128d4319b5\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5785/262/6317871885/239996/10eee952/5969b119N2d21a448.jpg\" /><br />\r\n<img alt=\"\" id=\"b0327e62ba984983984a59de59942b23\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5644/352/6349107491/301645/1b0381f4/5969b10eN13669ebb.jpg\" /><br />\r\n<img alt=\"\" id=\"4f2e5914aaa94657a548cad5c4d1f988\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5587/120/6426479107/269378/9abc05b5/5969b118Nf7cf2946.jpg\" /><br />\r\n<img alt=\"\" id=\"43c56330d7574ff6a25bff90a177b47f\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t6022/70/5116894237/238075/4a41cc2b/5969b117N8957ec5d.jpg\" /><br />\r\n<img alt=\"\" id=\"a6f18ead9692429190878800fd1af3a5\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5707/138/6466762243/238825/7a0495c0/5969b116Nc05009eb.jpg\" /><br />\r\n<img alt=\"\" id=\"2d1eca488db7494598bd526561ff9991\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5791/190/6372096874/190055/b52284cc/5969b11eNe3c4540b.jpg\" /><br />\r\n<img alt=\"\" id=\"59d283c450774c4f8eb39c570ba0a7af\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t5725/50/6342238743/74537/b433b988/5969b11aN7929af06.jpg\" /><br />\r\n<p>\r\n\t<img alt=\"\" id=\"a0e76e194661402eb89b7d02262d9cac\" class=\"\" src=\"https://img30.360buyimg.com/popWareDetail/jfs/t6127/90/5139873494/268362/a4b254a4/5969b120N66edd4d7.jpg\" /> \r\n<\/p>\r\n<p>\r\n\t<img alt=\"\" class=\"\" src=\"https://img10.360buyimg.com/imgzone/jfs/t11071/193/2598780333/171230/296e6001/5a1bf63cN540cc188.jpg\" /> \r\n<\/p>","mobile_desc":"","click_count":"0","keywords":null,"sale_num":"0","collect_num":"0","goods_sort":"0","is_delete":"0","goods_status":"1","shop_id":"0","cart_id":null}]
             * order : {"shipping_fee":0,"order_points":100,"buyer_type":0,"shop_id":"0"}
             * shop_id : 0
             * shipping_list : [{"id":0,"price":0,"name":"普通快递 ","selected":true},{"id":1,"name":"上门自提 ","selected":false,"pickup_id":0,"pickup_list":[{"pickup_id":"43","pickup_name":"自提点111","region_code":"13,03,02","pickup_address":"港城大街街道迎宾路君御大酒店1","address_lng":"119.593686","address_lat":"39.953033","pickup_tel":"15833344433","pickup_desc":"","pickup_images":"/backend/1/images/2017/12/11/15129807304338.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"},{"pickup_id":"44","pickup_name":"123","region_code":"13,03,02","pickup_address":"西港镇金屋·秦皇半岛2区","address_lng":"119.519802","address_lat":"39.901149","pickup_tel":"13111111111","pickup_desc":"商家推荐","pickup_images":"/backend/1/images/2017/12/12/15130401540636.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"},{"pickup_id":"45","pickup_name":"12345678901234567890","region_code":"13,03,02","pickup_address":"北戴河区东山街道金湾环路","address_lng":"119.524176","address_lat":"39.883558","pickup_tel":"13111111111","pickup_desc":"12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890","pickup_images":"/backend/1/images/2017/12/12/15130402349752.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"},{"pickup_id":"47","pickup_name":"自提点名称自提点名称自提点名称自提点名称","region_code":"13,03,02","pickup_address":"西港镇蒲公英艺术学校金屋·秦皇半岛2区","address_lng":"119.522506","address_lat":"39.900868","pickup_tel":"15133519802","pickup_desc":"自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称","pickup_images":"/backend/1/images/2017/12/21/15138457349471.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"}]}]
             * user_info : {"pay_point":"100703","pay_point_amount":1007.03,"pay_point_amount_format":"￥1007.03"}
             * key : 57eb6a0b148d0c2612c92773249e2e56
             */

            public OrderBean order;
            public String shop_id;
            public String user_info;
            public String key;
            public List<GoodsListBean> goods_list;
            public List<ShippingListBean> shipping_list;

            public static class OrderBean {
                /**
                 * shipping_fee : 0
                 * order_points : 100
                 * buyer_type : 0
                 * shop_id : 0
                 */

                public int shipping_fee;
                public int order_points;
                public int buyer_type;
                public String shop_id;
            }


            public static class GoodsListBean {

                public String goods_id;
                public String goods_name;
                public String goods_price;
                public String market_price;
                public String goods_number;
                public String goods_integral;
                public String exchange_number;
                public String start_time;
                public String end_time;
                public String add_time;
                public String goods_image;
                public String goods_images;
                public String goods_video;
                public String pc_desc;
                public String mobile_desc;
                public String click_count;
                public String keywords;
                public String sale_num;
                public String collect_num;
                public String goods_sort;
                public String is_delete;
                public String goods_status;
                public String shop_id;
                public String cart_id;
            }

            public static class ShippingListBean {
                /**
                 * id : 0
                 * price : 0
                 * name : 普通快递
                 * selected : true
                 * pickup_id : 0
                 * pickup_list : [{"pickup_id":"43","pickup_name":"自提点111","region_code":"13,03,02","pickup_address":"港城大街街道迎宾路君御大酒店1","address_lng":"119.593686","address_lat":"39.953033","pickup_tel":"15833344433","pickup_desc":"","pickup_images":"/backend/1/images/2017/12/11/15129807304338.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"},{"pickup_id":"44","pickup_name":"123","region_code":"13,03,02","pickup_address":"西港镇金屋·秦皇半岛2区","address_lng":"119.519802","address_lat":"39.901149","pickup_tel":"13111111111","pickup_desc":"商家推荐","pickup_images":"/backend/1/images/2017/12/12/15130401540636.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"},{"pickup_id":"45","pickup_name":"12345678901234567890","region_code":"13,03,02","pickup_address":"北戴河区东山街道金湾环路","address_lng":"119.524176","address_lat":"39.883558","pickup_tel":"13111111111","pickup_desc":"12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890","pickup_images":"/backend/1/images/2017/12/12/15130402349752.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"},{"pickup_id":"47","pickup_name":"自提点名称自提点名称自提点名称自提点名称","region_code":"13,03,02","pickup_address":"西港镇蒲公英艺术学校金屋·秦皇半岛2区","address_lng":"119.522506","address_lat":"39.900868","pickup_tel":"15133519802","pickup_desc":"自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称自提点名称","pickup_images":"/backend/1/images/2017/12/21/15138457349471.png","shop_id":"0","is_show":"1","sort":"255","is_delete":"0"}]
                 */

                public String id;
                public String price;
                public String name;
                public boolean selected;
                public String pickup_id;
                public String pickup_name;
                public ArrayList<PickUpModel> pickup_list;

            }
        }


    }
}
