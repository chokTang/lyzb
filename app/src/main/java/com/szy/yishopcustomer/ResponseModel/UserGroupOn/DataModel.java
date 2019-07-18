package com.szy.yishopcustomer.ResponseModel.UserGroupOn;

import java.util.List;

/**
 * Created by liwei on 2017/3/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public SkuModel sku;
    public GoodsModel goods;
    public GroupOnLogModel groupon_log;
    public GroupOnInfoModel groupon_info;
    public GroupOnShareModel share;
    public List<GroupOnLogItemModel> groupon_log_list;
    public int fight_num;
    public int diff_num;
    public ContextModel context;
    public long end_time;
}
