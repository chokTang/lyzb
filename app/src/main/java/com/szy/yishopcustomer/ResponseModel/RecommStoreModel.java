package com.szy.yishopcustomer.ResponseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommStoreModel {


    /**
     * code : 0
     * data : {"page":{"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":1,"page_count":1,"offset":0,"url":null,"sql":null},"list":[{"id":"11","shop_id":"0","shop_name":"lalala","cat_id":"0","mobile":"13001163148","region_code":"13,03,02","address":"河北省 秦皇岛市 海港区 漓江塔","shop_lng":"","shop_lat":"","shop_type":"1","res_reason":"守望先锋","examine_reason":"","facade_img":"/user/64/images/2017/05/16/14948973205837.jpeg","card_img":"/user/64/images/2017/05/16/14948973247009.jpeg","user_id":"64","add_time":"1494897328","status":"0","remark":""}],"searchModel":null,"nav_default":"recommend-shop","status":{"-1":"全部","0":"待审核","1":"审核通过","2":"审核未通过","3":"已取消"},"context":{"current_time":1494919809,"user_info":{"user_id":64,"user_name":"SZY130OOVM3148","nickname":"SZY130OOVM3148","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1494917601,"last_ip":"100.97.126.29","last_region_code":null,"user_rank":{"rank_id":"1","rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":"0","max_points":"1","type":"0","is_special":"0"},"unread_msg_cnt":"0"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"800007396","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.png","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png"},"cart":{"goods_count":0}}}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : {"page_key":"page","page_id":"pagination","default_page_size":15,"cur_page":1,"page_size":10,"page_size_list":[10,50,500,1000],"record_count":1,"page_count":1,"offset":0,"url":null,"sql":null}
         * list : [{"id":"11","shop_id":"0","shop_name":"lalala","cat_id":"0","mobile":"13001163148","region_code":"13,03,02","address":"河北省 秦皇岛市 海港区 漓江塔","shop_lng":"","shop_lat":"","shop_type":"1","res_reason":"守望先锋","examine_reason":"","facade_img":"/user/64/images/2017/05/16/14948973205837.jpeg","card_img":"/user/64/images/2017/05/16/14948973247009.jpeg","user_id":"64","add_time":"1494897328","status":"0","remark":""}]
         * searchModel : null
         * nav_default : recommend-shop
         * status : {"-1":"全部","0":"待审核","1":"审核通过","2":"审核未通过","3":"已取消"}
         * context : {"current_time":1494919809,"user_info":{"user_id":64,"user_name":"SZY130OOVM3148","nickname":"SZY130OOVM3148","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1494917601,"last_ip":"100.97.126.29","last_region_code":null,"user_rank":{"rank_id":"1","rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":"0","max_points":"1","type":"0","is_special":"0"},"unread_msg_cnt":"0"},"config":{"mall_logo":"/system/config/mall/mall_logo_0.png","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"800007396","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.png","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png"},"cart":{"goods_count":0}}
         */

        private PageBean page;
        private Object searchModel;
        private String nav_default;
        private Map<String,String> status;
        private ContextBean context;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public Object getSearchModel() {
            return searchModel;
        }

        public void setSearchModel(Object searchModel) {
            this.searchModel = searchModel;
        }

        public String getNav_default() {
            return nav_default;
        }

        public void setNav_default(String nav_default) {
            this.nav_default = nav_default;
        }

        public Map<String, String> getStatus() {
            return status;
        }

        public void setStatus(Map<String, String> status) {
            this.status = status;
        }

        public ContextBean getContext() {
            return context;
        }

        public void setContext(ContextBean context) {
            this.context = context;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * page_key : page
             * page_id : pagination
             * default_page_size : 15
             * cur_page : 1
             * page_size : 10
             * page_size_list : [10,50,500,1000]
             * record_count : 1
             * page_count : 1
             * offset : 0
             * url : null
             * sql : null
             */

            private String page_key;
            private String page_id;
            private int default_page_size;
            private int cur_page;
            private int page_size;
            private int record_count;
            private int page_count;
            private int offset;
            private Object url;
            private Object sql;
            private List<Integer> page_size_list;

            public String getPage_key() {
                return page_key;
            }

            public void setPage_key(String page_key) {
                this.page_key = page_key;
            }

            public String getPage_id() {
                return page_id;
            }

            public void setPage_id(String page_id) {
                this.page_id = page_id;
            }

            public int getDefault_page_size() {
                return default_page_size;
            }

            public void setDefault_page_size(int default_page_size) {
                this.default_page_size = default_page_size;
            }

            public int getCur_page() {
                return cur_page;
            }

            public void setCur_page(int cur_page) {
                this.cur_page = cur_page;
            }

            public int getPage_size() {
                return page_size;
            }

            public void setPage_size(int page_size) {
                this.page_size = page_size;
            }

            public int getRecord_count() {
                return record_count;
            }

            public void setRecord_count(int record_count) {
                this.record_count = record_count;
            }

            public int getPage_count() {
                return page_count;
            }

            public void setPage_count(int page_count) {
                this.page_count = page_count;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public Object getSql() {
                return sql;
            }

            public void setSql(Object sql) {
                this.sql = sql;
            }

            public List<Integer> getPage_size_list() {
                return page_size_list;
            }

            public void setPage_size_list(List<Integer> page_size_list) {
                this.page_size_list = page_size_list;
            }
        }

        public static class ContextBean {
            /**
             * current_time : 1494919809
             * user_info : {"user_id":64,"user_name":"SZY130OOVM3148","nickname":"SZY130OOVM3148","headimg":"/system/config/default_image/default_user_portrait_0.png","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1494917601,"last_ip":"100.97.126.29","last_region_code":null,"user_rank":{"rank_id":"1","rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":"0","max_points":"1","type":"0","is_special":"0"},"unread_msg_cnt":"0"}
             * config : {"mall_logo":"/system/config/mall/mall_logo_0.png","backend_logo":"/system/config/website/backend_logo_0.png","site_name":"小京东+测试站","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"13,03,02","mall_region_name":{"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"12222222222222222","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。","site_powered_by":"","stats_code":"<script>\r\nvar _hmt = _hmt || [];\r\n(function() {\r\n  var hm = document.createElement(\"script\");\r\n  hm.src = \"https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc\";\r\n  var s = document.getElementsByTagName(\"script\")[0]; \r\n  s.parentNode.insertBefore(hm, s);\r\n})();\r\n<\/script>","mall_service_right":"","open_download_qrcode":"1","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_qq":"800007396","mall_wangwang":"","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_appkey":"23488235","aliim_secret_key":"b88d4dd831e463d7ec451d7c171a323e","aliim_main_customer":"xn8801160116","aliim_customer_logo":"/system/config/aliim/aliim_customer_logo_0.gif","aliim_welcome_words":"","aliim_uid":"d41d8cd98f00b204e9800998ecf8427e","aliim_pwd":"d41d8cd98f00b204e9800998ecf8427e","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.png","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png"}
             * cart : {"goods_count":0}
             */

            private int current_time;
            private UserInfoBean user_info;
            private ConfigBean config;
            private CartBean cart;

            public int getCurrent_time() {
                return current_time;
            }

            public void setCurrent_time(int current_time) {
                this.current_time = current_time;
            }

            public UserInfoBean getUser_info() {
                return user_info;
            }

            public void setUser_info(UserInfoBean user_info) {
                this.user_info = user_info;
            }

            public ConfigBean getConfig() {
                return config;
            }

            public void setConfig(ConfigBean config) {
                this.config = config;
            }

            public CartBean getCart() {
                return cart;
            }

            public void setCart(CartBean cart) {
                this.cart = cart;
            }

            public static class UserInfoBean {
                /**
                 * user_id : 64
                 * user_name : SZY130OOVM3148
                 * nickname : SZY130OOVM3148
                 * headimg : /system/config/default_image/default_user_portrait_0.png
                 * email : null
                 * email_validated : 0
                 * mobile : 13001163148
                 * mobile_validated : 1
                 * is_seller : 0
                 * shop_id : 0
                 * last_time : 1494917601
                 * last_ip : 100.97.126.29
                 * last_region_code : null
                 * user_rank : {"rank_id":"1","rank_name":"注册会员","rank_img":"/user/rank/2017/04/12/14919737728332.jpg","min_points":"0","max_points":"1","type":"0","is_special":"0"}
                 * unread_msg_cnt : 0
                 */

                private int user_id;
                private String user_name;
                private String nickname;
                private String headimg;
                private Object email;
                private int email_validated;
                private String mobile;
                private int mobile_validated;
                private int is_seller;
                private int shop_id;
                private int last_time;
                private String last_ip;
                private Object last_region_code;
                private UserRankBean user_rank;
                private String unread_msg_cnt;

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getHeadimg() {
                    return headimg;
                }

                public void setHeadimg(String headimg) {
                    this.headimg = headimg;
                }

                public Object getEmail() {
                    return email;
                }

                public void setEmail(Object email) {
                    this.email = email;
                }

                public int getEmail_validated() {
                    return email_validated;
                }

                public void setEmail_validated(int email_validated) {
                    this.email_validated = email_validated;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getMobile_validated() {
                    return mobile_validated;
                }

                public void setMobile_validated(int mobile_validated) {
                    this.mobile_validated = mobile_validated;
                }

                public int getIs_seller() {
                    return is_seller;
                }

                public void setIs_seller(int is_seller) {
                    this.is_seller = is_seller;
                }

                public int getShop_id() {
                    return shop_id;
                }

                public void setShop_id(int shop_id) {
                    this.shop_id = shop_id;
                }

                public int getLast_time() {
                    return last_time;
                }

                public void setLast_time(int last_time) {
                    this.last_time = last_time;
                }

                public String getLast_ip() {
                    return last_ip;
                }

                public void setLast_ip(String last_ip) {
                    this.last_ip = last_ip;
                }

                public Object getLast_region_code() {
                    return last_region_code;
                }

                public void setLast_region_code(Object last_region_code) {
                    this.last_region_code = last_region_code;
                }

                public UserRankBean getUser_rank() {
                    return user_rank;
                }

                public void setUser_rank(UserRankBean user_rank) {
                    this.user_rank = user_rank;
                }

                public String getUnread_msg_cnt() {
                    return unread_msg_cnt;
                }

                public void setUnread_msg_cnt(String unread_msg_cnt) {
                    this.unread_msg_cnt = unread_msg_cnt;
                }

                public static class UserRankBean {
                    /**
                     * rank_id : 1
                     * rank_name : 注册会员
                     * rank_img : /user/rank/2017/04/12/14919737728332.jpg
                     * min_points : 0
                     * max_points : 1
                     * type : 0
                     * is_special : 0
                     */

                    private String rank_id;
                    private String rank_name;
                    private String rank_img;
                    private String min_points;
                    private String max_points;
                    private String type;
                    private String is_special;

                    public String getRank_id() {
                        return rank_id;
                    }

                    public void setRank_id(String rank_id) {
                        this.rank_id = rank_id;
                    }

                    public String getRank_name() {
                        return rank_name;
                    }

                    public void setRank_name(String rank_name) {
                        this.rank_name = rank_name;
                    }

                    public String getRank_img() {
                        return rank_img;
                    }

                    public void setRank_img(String rank_img) {
                        this.rank_img = rank_img;
                    }

                    public String getMin_points() {
                        return min_points;
                    }

                    public void setMin_points(String min_points) {
                        this.min_points = min_points;
                    }

                    public String getMax_points() {
                        return max_points;
                    }

                    public void setMax_points(String max_points) {
                        this.max_points = max_points;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getIs_special() {
                        return is_special;
                    }

                    public void setIs_special(String is_special) {
                        this.is_special = is_special;
                    }
                }
            }

            public static class ConfigBean {
                /**
                 * mall_logo : /system/config/mall/mall_logo_0.png
                 * backend_logo : /system/config/website/backend_logo_0.png
                 * site_name : 小京东+测试站
                 * user_center_logo : /system/config/mall/user_center_logo_0.png
                 * mall_region_code : 13,03,02
                 * mall_region_name : {"13":"河北省","13,03":"秦皇岛市","13,03,02":"海港区"}
                 * mall_address : 秦皇半岛51号楼3层　
                 * site_icp : 12222222222222222
                 * site_copyright : <script type="text/javascript"></script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。
                 * site_powered_by :
                 * stats_code : <script>
                 var _hmt = _hmt || [];
                 (function() {
                 var hm = document.createElement("script");
                 hm.src = "https://hm.baidu.com/hm.js?4731b2b80b7e2092ce62623c553a2afc";
                 var s = document.getElementsByTagName("script")[0];
                 s.parentNode.insertBefore(hm, s);
                 })();
                 </script>
                 * mall_service_right :
                 * open_download_qrcode : 1
                 * mall_phone : 13333344125
                 * mall_email : zhaoyunlong@68ecshop.com
                 * mall_qq : 800007396
                 * mall_wangwang :
                 * favicon : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg
                 * aliim_appkey : 23488235
                 * aliim_secret_key : b88d4dd831e463d7ec451d7c171a323e
                 * aliim_main_customer : xn8801160116
                 * aliim_customer_logo : /system/config/aliim/aliim_customer_logo_0.gif
                 * aliim_welcome_words :
                 * aliim_uid : d41d8cd98f00b204e9800998ecf8427e
                 * aliim_pwd : d41d8cd98f00b204e9800998ecf8427e
                 * mall_wx_qrcode : /system/config/mall/mall_wx_qrcode_0.png
                 * default_user_portrait : /system/config/default_image/default_user_portrait_0.png
                 */

                private String mall_logo;
                private String backend_logo;
                private String site_name;
                private String user_center_logo;
                private String mall_region_code;
                private Map<String,String> mall_region_name;
                private String mall_address;
                private String site_icp;
                private String site_copyright;
                private String site_powered_by;
                private String stats_code;
                private String mall_service_right;
                private String open_download_qrcode;
                private String mall_phone;
                private String mall_email;
                private String mall_qq;
                private String mall_wangwang;
                private String favicon;
                private String aliim_appkey;
                private String aliim_secret_key;
                private String aliim_main_customer;
                private String aliim_customer_logo;
                private String aliim_welcome_words;
                private String aliim_uid;
                private String aliim_pwd;
                private String mall_wx_qrcode;
                private String default_user_portrait;

                public String getMall_logo() {
                    return mall_logo;
                }

                public void setMall_logo(String mall_logo) {
                    this.mall_logo = mall_logo;
                }

                public String getBackend_logo() {
                    return backend_logo;
                }

                public void setBackend_logo(String backend_logo) {
                    this.backend_logo = backend_logo;
                }

                public String getSite_name() {
                    return site_name;
                }

                public void setSite_name(String site_name) {
                    this.site_name = site_name;
                }

                public String getUser_center_logo() {
                    return user_center_logo;
                }

                public void setUser_center_logo(String user_center_logo) {
                    this.user_center_logo = user_center_logo;
                }

                public String getMall_region_code() {
                    return mall_region_code;
                }

                public void setMall_region_code(String mall_region_code) {
                    this.mall_region_code = mall_region_code;
                }

                public Map<String,String> getMall_region_name() {
                    return mall_region_name;
                }

                public void setMall_region_name(Map<String,String> mall_region_name) {
                    this.mall_region_name = mall_region_name;
                }

                public String getMall_address() {
                    return mall_address;
                }

                public void setMall_address(String mall_address) {
                    this.mall_address = mall_address;
                }

                public String getSite_icp() {
                    return site_icp;
                }

                public void setSite_icp(String site_icp) {
                    this.site_icp = site_icp;
                }

                public String getSite_copyright() {
                    return site_copyright;
                }

                public void setSite_copyright(String site_copyright) {
                    this.site_copyright = site_copyright;
                }

                public String getSite_powered_by() {
                    return site_powered_by;
                }

                public void setSite_powered_by(String site_powered_by) {
                    this.site_powered_by = site_powered_by;
                }

                public String getStats_code() {
                    return stats_code;
                }

                public void setStats_code(String stats_code) {
                    this.stats_code = stats_code;
                }

                public String getMall_service_right() {
                    return mall_service_right;
                }

                public void setMall_service_right(String mall_service_right) {
                    this.mall_service_right = mall_service_right;
                }

                public String getOpen_download_qrcode() {
                    return open_download_qrcode;
                }

                public void setOpen_download_qrcode(String open_download_qrcode) {
                    this.open_download_qrcode = open_download_qrcode;
                }

                public String getMall_phone() {
                    return mall_phone;
                }

                public void setMall_phone(String mall_phone) {
                    this.mall_phone = mall_phone;
                }

                public String getMall_email() {
                    return mall_email;
                }

                public void setMall_email(String mall_email) {
                    this.mall_email = mall_email;
                }

                public String getMall_qq() {
                    return mall_qq;
                }

                public void setMall_qq(String mall_qq) {
                    this.mall_qq = mall_qq;
                }

                public String getMall_wangwang() {
                    return mall_wangwang;
                }

                public void setMall_wangwang(String mall_wangwang) {
                    this.mall_wangwang = mall_wangwang;
                }

                public String getFavicon() {
                    return favicon;
                }

                public void setFavicon(String favicon) {
                    this.favicon = favicon;
                }

                public String getAliim_appkey() {
                    return aliim_appkey;
                }

                public void setAliim_appkey(String aliim_appkey) {
                    this.aliim_appkey = aliim_appkey;
                }

                public String getAliim_secret_key() {
                    return aliim_secret_key;
                }

                public void setAliim_secret_key(String aliim_secret_key) {
                    this.aliim_secret_key = aliim_secret_key;
                }

                public String getAliim_main_customer() {
                    return aliim_main_customer;
                }

                public void setAliim_main_customer(String aliim_main_customer) {
                    this.aliim_main_customer = aliim_main_customer;
                }

                public String getAliim_customer_logo() {
                    return aliim_customer_logo;
                }

                public void setAliim_customer_logo(String aliim_customer_logo) {
                    this.aliim_customer_logo = aliim_customer_logo;
                }

                public String getAliim_welcome_words() {
                    return aliim_welcome_words;
                }

                public void setAliim_welcome_words(String aliim_welcome_words) {
                    this.aliim_welcome_words = aliim_welcome_words;
                }

                public String getAliim_uid() {
                    return aliim_uid;
                }

                public void setAliim_uid(String aliim_uid) {
                    this.aliim_uid = aliim_uid;
                }

                public String getAliim_pwd() {
                    return aliim_pwd;
                }

                public void setAliim_pwd(String aliim_pwd) {
                    this.aliim_pwd = aliim_pwd;
                }

                public String getMall_wx_qrcode() {
                    return mall_wx_qrcode;
                }

                public void setMall_wx_qrcode(String mall_wx_qrcode) {
                    this.mall_wx_qrcode = mall_wx_qrcode;
                }

                public String getDefault_user_portrait() {
                    return default_user_portrait;
                }

                public void setDefault_user_portrait(String default_user_portrait) {
                    this.default_user_portrait = default_user_portrait;
                }

            }

            public static class CartBean {
                /**
                 * goods_count : 0
                 */

                private int goods_count;

                public int getGoods_count() {
                    return goods_count;
                }

                public void setGoods_count(int goods_count) {
                    this.goods_count = goods_count;
                }
            }
        }

        public static class ListBean {
            /**
             * id : 11
             * shop_id : 0
             * shop_name : lalala
             * cat_id : 0
             * mobile : 13001163148
             * region_code : 13,03,02
             * address : 河北省 秦皇岛市 海港区 漓江塔
             * shop_lng :
             * shop_lat :
             * shop_type : 1
             * res_reason : 守望先锋
             * examine_reason :
             * facade_img : /user/64/images/2017/05/16/14948973205837.jpeg
             * card_img : /user/64/images/2017/05/16/14948973247009.jpeg
             * user_id : 64
             * add_time : 1494897328
             * status : 0
             * remark :
             */

            private String id;
            private String shop_id;
            private String shop_name;
            private String cat_id;
            private String mobile;
            private String region_code;
            private String address;
            private String shop_lng;
            private String shop_lat;
            private String shop_type;
            private String res_reason;
            private String examine_reason;
            private String facade_img;
            private String card_img;
            private String user_id;
            private String add_time;
            private String status;
            private String remark;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getRegion_code() {
                return region_code;
            }

            public void setRegion_code(String region_code) {
                this.region_code = region_code;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getShop_lng() {
                return shop_lng;
            }

            public void setShop_lng(String shop_lng) {
                this.shop_lng = shop_lng;
            }

            public String getShop_lat() {
                return shop_lat;
            }

            public void setShop_lat(String shop_lat) {
                this.shop_lat = shop_lat;
            }

            public String getShop_type() {
                return shop_type;
            }

            public void setShop_type(String shop_type) {
                this.shop_type = shop_type;
            }

            public String getRes_reason() {
                return res_reason;
            }

            public void setRes_reason(String res_reason) {
                this.res_reason = res_reason;
            }

            public String getExamine_reason() {
                return examine_reason;
            }

            public void setExamine_reason(String examine_reason) {
                this.examine_reason = examine_reason;
            }

            public String getFacade_img() {
                return facade_img;
            }

            public void setFacade_img(String facade_img) {
                this.facade_img = facade_img;
            }

            public String getCard_img() {
                return card_img;
            }

            public void setCard_img(String card_img) {
                this.card_img = card_img;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
