package com.szy.yishopcustomer.ResponseModel.SameCity.MoreSort;

import java.util.List;

/**
 * @author wyx
 * @role 分类更多 entity
 * @time 2018 2018/8/6 15:42
 */

public class MoreModel {

    /**
     * catgId : 11111112
     * moudleId : 0
     * categoryId : 11111112
     * catgName : 美食
     * catgIcon : https://jmall.oss-cn-hangzhou.aliyuncs.com/logo/0502/meishi.png
     * catgLevel : 1
     * parentId : 0
     * productNum : 0
     * children : [{"catgId":11111144,"moudleId":0,"categoryId":11111144,"catgName":"生日蛋糕","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111145,"moudleId":0,"categoryId":11111145,"catgName":"甜点饮品","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111146,"moudleId":0,"categoryId":11111146,"catgName":"京菜鲁菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111147,"moudleId":0,"categoryId":11111147,"catgName":"日韩料理","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111148,"moudleId":0,"categoryId":11111148,"catgName":"自助餐","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111149,"moudleId":0,"categoryId":11111149,"catgName":"火锅","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111151,"moudleId":0,"categoryId":11111151,"catgName":"西餐","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111153,"moudleId":0,"categoryId":11111153,"catgName":"小吃快餐","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111156,"moudleId":0,"categoryId":11111156,"catgName":"聚餐宴请","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111158,"moudleId":0,"categoryId":11111158,"catgName":"烧烤烤肉","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111160,"moudleId":0,"categoryId":11111160,"catgName":"川湘菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111161,"moudleId":0,"categoryId":11111161,"catgName":"江浙菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111162,"moudleId":0,"categoryId":11111162,"catgName":"香锅烤鱼","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111163,"moudleId":0,"categoryId":11111163,"catgName":"粤菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111164,"moudleId":0,"categoryId":11111164,"catgName":"中式烧烤/烤串","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111165,"moudleId":0,"categoryId":11111165,"catgName":"西北菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111166,"moudleId":0,"categoryId":11111166,"catgName":"咖啡酒吧","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111167,"moudleId":0,"categoryId":11111167,"catgName":"云贵菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111168,"moudleId":0,"categoryId":11111168,"catgName":"生鲜蔬果","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111169,"moudleId":0,"categoryId":11111169,"catgName":"东北菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111170,"moudleId":0,"categoryId":11111170,"catgName":"东南亚菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111171,"moudleId":0,"categoryId":11111171,"catgName":"素食","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111172,"moudleId":0,"categoryId":11111172,"catgName":"台湾/客家菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111174,"moudleId":0,"categoryId":11111174,"catgName":"创意菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111176,"moudleId":0,"categoryId":11111176,"catgName":"汤/粥/炖菜","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0},{"catgId":11111177,"moudleId":0,"categoryId":11111177,"catgName":"霸王餐","catgIcon":"","catgLevel":2,"parentId":11111112,"productNum":0}]
     */

    public int catgId;
    public int moudleId;
    public int categoryId;
    public String catgName;
    public String catgIcon;
    public int catgLevel;
    public int parentId;
    public int productNum;
    public List<ChildrenBean> children;

    public static class ChildrenBean {
        /**
         * catgId : 11111144
         * moudleId : 0
         * categoryId : 11111144
         * catgName : 生日蛋糕
         * catgIcon :
         * catgLevel : 2
         * parentId : 11111112
         * productNum : 0
         */

        public int catgId;
        public int moudleId;
        public int categoryId;
        public String catgName;
        public String catgIcon;
        public int catgLevel;
        public int parentId;
        public int productNum;
    }
}
