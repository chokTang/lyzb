package com.szy.yishopcustomer.ResponseModel.GoodsDesc;

import com.szy.yishopcustomer.ResponseModel.Goods.GoodsModel;

import java.util.List;

/**
 * Created by liuzhifeng on 2016/9/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class Model extends GoodsModel {
    public int desc_type;
    public int code;
    public String message;

    public List<MobileDescModel> mobile_desc;

    public String pc_desc;


    public Boolean need_load;
    public boolean is_jd;

}
