package com.szy.yishopcustomer.ResponseModel.Bonus;

import com.szy.yishopcustomer.ResponseModel.*;

import java.util.List;

/**
 * Created by zongren on 16/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public List<BonusItemModel> list;
    public int system_bonus_count;//"0",
    public int shop_bonus_count;//"10",
    public String type;//1,
    public PageModel page;
    public com.szy.yishopcustomer.ResponseModel.ContextModel context;
}
