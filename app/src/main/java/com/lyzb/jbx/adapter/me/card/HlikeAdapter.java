package com.lyzb.jbx.adapter.me.card;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;

import java.util.List;

/**
 * @author wyx
 * @role 兴趣爱好 adapter
 * @time 2019 2019/3/15 11:48
 */

public class HlikeAdapter extends BaseRecyleAdapter<String> {

    public HlikeAdapter(Context context, List<String> list) {
        super(context, R.layout.item_union_info_hlike, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {

        holder.setText(R.id.tv_info_hobby_text, item);
        holder.addItemClickListener(R.id.img_info_hobby_re);
    }
}
