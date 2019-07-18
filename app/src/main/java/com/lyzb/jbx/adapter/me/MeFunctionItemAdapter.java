package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.widget.LinearLayout;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.cenum.FunctionItemEnum;

import java.util.List;

/**
 * 我的- 单个item下的网格适配器
 */
public class MeFunctionItemAdapter extends BaseRecyleAdapter<FunctionItemEnum> {

    private LinearLayout.LayoutParams params;

    public MeFunctionItemAdapter(Context context, List<FunctionItemEnum> list) {
        super(context, R.layout.recycle_me_item_item, list);
        int width = ScreenUtil.getScreenWidth() / 4;
        params = new LinearLayout.LayoutParams(width, width);
    }

    @Override
    protected void convert(BaseViewHolder holder, FunctionItemEnum item) {
        holder.get_view().setLayoutParams(params);
        holder.setImageUrl(R.id.img_header, item.getClass1());
        holder.setText(R.id.tv_name, item.getDisplay());
    }
}
