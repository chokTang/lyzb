package com.szy.yishopcustomer.ResponseModel.AddressList;

import java.util.List;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public List<AddressItemModel> list;

    public int cur_page;
    public Page page;

    public class Page {

        public int record_count;//数据源的总数
        public int page_count;
        public int cur_page;
    }
}
