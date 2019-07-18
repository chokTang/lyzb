package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.AddComdImgModel;

import java.util.List;

/**
 * @author wyx
 * @role 创建企业 列表 adapter
 * @time 2019 2019/3/15 11:48
 */

public class AddComdAdapter extends BaseRecyleAdapter<AddComdImgModel> {


    public AddComdAdapter(Context context, List<AddComdImgModel> list) {
        super(context, R.layout.item_union_card_info_img, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AddComdImgModel item) {

        LoadImageUtil.MachWitdhHightWrop((ImageView) holder.cdFindViewById(R.id.img_un_me_card_info_img),item.getImgUrl(),20);
        holder.addItemClickListener(R.id.img_un_me_card_info_img);
        if (item.getIsMeComd() == 1) {
            holder.setVisible(R.id.img_un_comd_cancle, false);
        } else {
            holder.setVisible(R.id.img_un_comd_cancle, false);
        }
    }
}
