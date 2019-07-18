package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.ApplyListModel;

import java.util.List;

/****
 * 申请列表 adapter
 *
 */
public class ApplyListAdapter extends BaseRecyleAdapter<ApplyListModel.ListBean> {

    public ApplyListAdapter(Context context, List<ApplyListModel.ListBean> list) {
        super(context, R.layout.item_circle_applylist, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, ApplyListModel.ListBean item) {
        holder.setRadiusImageUrl(R.id.img_item_cir_appl_head, item.getHeadimg(), 4);
        holder.setText(R.id.tv_item_cir_appl_name, item.getUserName());
        holder.setText(R.id.tv_item_cir_appl_com, item.getCompanyInfo());
        holder.setText(R.id.tv_item_cir_appl_time, item.getAddTime());
        TextView tv_item_cir_appl_agreed = holder.cdFindViewById(R.id.tv_item_cir_appl_agreed);
        TextView tv_item_cir_appl_refuse = holder.cdFindViewById(R.id.tv_item_cir_appl_refuse);
        TextView tv_item_cir_appl_resulte = holder.cdFindViewById(R.id.tv_item_cir_appl_resulte);

        switch (item.getPass()) {
            case 0:
                tv_item_cir_appl_resulte.setText("已拒绝");
                tv_item_cir_appl_agreed.setVisibility(View.GONE);
                tv_item_cir_appl_refuse.setVisibility(View.GONE);
                tv_item_cir_appl_resulte.setVisibility(View.VISIBLE);
                break;
            case 1:
                tv_item_cir_appl_resulte.setText("已同意");
                tv_item_cir_appl_agreed.setVisibility(View.GONE);
                tv_item_cir_appl_refuse.setVisibility(View.GONE);
                tv_item_cir_appl_resulte.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_item_cir_appl_agreed.setVisibility(View.VISIBLE);
                tv_item_cir_appl_refuse.setVisibility(View.VISIBLE);
                tv_item_cir_appl_resulte.setVisibility(View.GONE);
                break;
            default:
        }

        holder.addItemClickListener(R.id.img_item_cir_appl_head);
        holder.addItemClickListener(R.id.tv_item_cir_appl_agreed);
        holder.addItemClickListener(R.id.tv_item_cir_appl_refuse);
    }
}
