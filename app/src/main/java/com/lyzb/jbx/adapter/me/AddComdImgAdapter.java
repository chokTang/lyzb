package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.AddComdImgModel;

import java.util.List;

/**
 * @author wyx
 * @role 创建企业 添加图片 adapter
 * @time 2019 2019/3/9 14:42
 */

public class AddComdImgAdapter extends BaseRecyleAdapter<AddComdImgModel> {

    public AddComdImgAdapter(Context context, List<AddComdImgModel> list) {
        super(context, R.layout.item_union_me_card_img, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AddComdImgModel item) {

        ImageView imageView = holder.cdFindViewById(R.id.img_un_me_info_add);
        ImageView cancle = holder.cdFindViewById(R.id.img_un_me_img_cancle);

        if (item.getImgType() == 1) {
            imageView.setImageResource(R.mipmap.union_card_info_add_img);
            holder.addItemClickListener(R.id.img_un_me_info_add);
            cancle.setVisibility(View.GONE);
        } else {
            holder.setImageUrl(R.id.img_un_me_info_add, item.getImgUrl());
            cancle.setVisibility(View.VISIBLE);
            holder.addItemClickListener(R.id.img_un_me_img_cancle);
        }
    }
}
