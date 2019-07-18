package com.szy.yishopcustomer.ResponseModel.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 搜索内容 输入完成 动态提示 数据 model
 * @time 2018 2018/9/21 15:26
 */

public class SearchHintModel {
    public String row;
    public List<String> tags;

    public List<String> getTags() {
        if (tags == null)
            return new ArrayList<>();
        return tags;
    }

}
