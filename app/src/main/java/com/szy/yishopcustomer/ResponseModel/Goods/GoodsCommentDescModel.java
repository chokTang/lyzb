package com.szy.yishopcustomer.ResponseModel.Goods;

import java.util.ArrayList;

/**
 * Created by liuzhifeng on 2016/12/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GoodsCommentDescModel {
    public int time;
    public String goodsSpec;
    public String value;
    public ArrayList image;

    //是否是追加评论的最后一个
    public boolean isLastReview;

}
