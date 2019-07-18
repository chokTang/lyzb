package com.lyzb.jbx.adapter.me;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CircleMerberModel;

import java.util.List;

/****
 * 我的圈子-成员列表 adapter
 *
 */
public class CircleMerberAdapter extends BaseRecyleAdapter<CircleMerberModel.DataBean.ListBean> {


    private boolean mOwner = false;

    public CircleMerberAdapter(Context context, List<CircleMerberModel.DataBean.ListBean> list) {
        super(context, R.layout.item_circle_merber, list);
    }

    public boolean isOwner(boolean owner) {
        mOwner = owner;
        return mOwner;
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleMerberModel.DataBean.ListBean item) {
        holder.setRadiusImageUrl(R.id.img_item_cir_merber_head, item.getHeadimg(), 4);
        holder.setText(R.id.tv_item_cir_merber_name, item.getUserName());
        holder.setText(R.id.tv_item_cir_merber_com, item.getCompanyInfo());

        holder.addItemClickListener(R.id.img_item_cir_merber_head);

        if (mOwner) {
            holder.setVisible(R.id.tv_item_cir_merber_remove, true);
        } else {
            holder.setVisible(R.id.tv_item_cir_merber_remove, false);
        }
        holder.addItemClickListener(R.id.tv_item_cir_merber_remove);
    }
}
