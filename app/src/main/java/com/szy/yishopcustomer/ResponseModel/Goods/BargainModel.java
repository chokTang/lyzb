package com.szy.yishopcustomer.ResponseModel.Goods;

import java.util.List;

/**
 * @author wyx
 * @role 砍价成功 model
 * @time 2018 2018/9/19 15:08
 */

public class BargainModel {

    public int code;
    public BargainData data;
    public String message;

    public class BargainData {

        public BargainInfo bar_info;

        public class BargainInfo {
            public String bar_id;
            public String bar_amount;
        }
    }

}
