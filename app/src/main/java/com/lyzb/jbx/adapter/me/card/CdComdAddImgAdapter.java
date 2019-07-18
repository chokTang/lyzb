package com.lyzb.jbx.adapter.me.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CardComdModel;

import java.util.List;

/**
 * @author wyx
 * @role 企业信息 添加图片 adapter
 * @time 2019 2019/3/9 14:42
 */

public class CdComdAddImgAdapter extends BaseRecyleAdapter<CardComdModel.ComdFile> {

    public CdComdAddImgAdapter(Context context, List<CardComdModel.ComdFile> list) {
        super(context, R.layout.item_union_me_card_img, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CardComdModel.ComdFile item) {

        ImageView imageView = holder.cdFindViewById(R.id.img_un_me_info_add);
        ImageView cancle = holder.cdFindViewById(R.id.img_un_me_img_cancle);

        if (item.getImgType() == 1) {
            imageView.setImageResource(R.mipmap.union_card_info_add_img);
            holder.addItemClickListener(R.id.img_un_me_info_add);
            cancle.setVisibility(View.GONE);
        } else {
            holder.setImageUrl(R.id.img_un_me_info_add, item.getFilePath());
            cancle.setVisibility(View.VISIBLE);

            holder.addItemClickListener(R.id.img_un_me_img_cancle);
        }
    }
}
