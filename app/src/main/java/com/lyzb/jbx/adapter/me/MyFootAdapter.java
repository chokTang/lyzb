package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.MeFootModel;
import com.szy.yishopcustomer.Util.DateUtil;

import java.util.List;

public class MyFootAdapter extends BaseRecyleAdapter<MeFootModel> {

    public MyFootAdapter(Context context, List<MeFootModel> list) {
        super(context, R.layout.recycle_my_foot, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, MeFootModel item) {
        holder.setText(R.id.item_acs_time, item.getCreateTime());
        if (item.getType() == 1) {
            holder.setText(R.id.item_acs_text, String.format("查看了%s的名片", item.getTitle()));
        } else if (item.getType() == 2) {
            String dynamicValue = "";
            if (!TextUtils.isEmpty(item.getContent())) {
                dynamicValue = item.getContent().length() > 10 ? item.getContent().substring(0, 10) + "..." : item.getContent();
                holder.setText(R.id.item_acs_text, String.format("查看了《%s》的动态", dynamicValue));
            } else {
                holder.setText(R.id.item_acs_text, "查看了动态");
            }
        } else if (item.getType() == 3) {
            holder.setText(R.id.item_acs_text, String.format("查看了《%s》商品", item.getTitle()));
        }

        //处理月份是否显示
        int position = holder.getAdapterPosition();
        final TextView tv_mouth = holder.cdFindViewById(R.id.tv_mouth);
        final TextView tv_header = holder.cdFindViewById(R.id.tv_header);

        if (position <= 0) {
            tv_mouth.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_mouth, String.format("%d月", DateUtil.getMonth(item.getCreateTime()) + 1));

            tv_header.setVisibility(View.VISIBLE);
            tv_header.setText(String.format("%d年", DateUtil.getYear(item.getCreateTime())));
        } else {
            int preMonth = DateUtil.getMonth(getPositionModel(position - 1).getCreateTime()) + 1;
            int mouth = DateUtil.getMonth(getPositionModel(position).getCreateTime()) + 1;
            if (preMonth == mouth) {
                tv_mouth.setVisibility(View.INVISIBLE);
            } else {
                holder.setText(R.id.tv_mouth, String.format("%d月", mouth));
                tv_mouth.setVisibility(View.VISIBLE);
            }

            int preYear = DateUtil.getYear(getPositionModel(position - 1).getCreateTime());
            int year = DateUtil.getYear(getPositionModel(position).getCreateTime());
            if (preYear == year) {
                tv_header.setVisibility(View.GONE);
            } else {
                tv_header.setVisibility(View.VISIBLE);
                tv_header.setText(String.format("%d年", year));
            }
        }
    }
}
