package com.lyzb.jbx.adapter.me.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.inter.IRecycleHolderAnyClilck;
import com.lyzb.jbx.model.me.CardItemInfoModel;

import java.util.List;

/**
 * @author wyx
 * @role 个人简介, 我能提供, 我的需求 图片显示 adapter
 * @time 2019 2019/3/9 14:42
 */

public class CdInfoAddImgAdapter extends BaseRecyleAdapter<CardItemInfoModel> {

    public CdInfoAddImgAdapter(Context context, List<CardItemInfoModel> list) {
        super(context, R.layout.item_union_me_card_img, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CardItemInfoModel item) {
        if (item.getType() == 1) {
            holder.setImageUrl(R.id.img_un_me_info_add, R.mipmap.union_card_info_add_img);
            holder.setVisible(R.id.img_un_me_img_cancle,false);
        } else {
            holder.setImageUrl(R.id.img_un_me_info_add, item.getFilePath());
            holder.setVisible(R.id.img_un_me_img_cancle,true);
        }
        holder.addItemClickListener(R.id.img_un_me_img_cancle);
    }
}
