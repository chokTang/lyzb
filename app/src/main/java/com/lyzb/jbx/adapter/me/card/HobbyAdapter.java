package com.lyzb.jbx.adapter.me.card;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;

import java.util.List;

/**
 * @author wyx
 * @role 熟悉行业/期待认识/兴趣爱好 adapter
 * @time 2019 2019/3/15 11:48
 */

public class HobbyAdapter extends BaseRecyleAdapter<String> {

    public HobbyAdapter(Context context, List<String> list) {
        super(context, R.layout.item_union_info_hobby, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {

        holder.setText(R.id.tv_info_hobby_text, item);
    }
}
