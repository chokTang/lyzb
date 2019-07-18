package com.lyzb.jbx.adapter.me.card;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CardItemInfoModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/3/15 11:48
 */

public class InfoImgAdapter extends BaseRecyleAdapter<CardItemInfoModel> {

    private boolean isMeCard=true;

    public void setMeCard(boolean meCard) {
        isMeCard = meCard;
    }

    public InfoImgAdapter(Context context, List<CardItemInfoModel> list) {
        super(context, R.layout.item_union_card_info_img, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CardItemInfoModel item) {

        if(isMeCard){
            holder.setVisible(R.id.img_un_comd_cancle, false);
        }else{
            holder.setVisible(R.id.img_un_comd_cancle, false);
        }

        holder.setImageUrl(R.id.img_un_me_card_info_img, item.getFilePath());
//        LoadImageUtil.MachWitdhHightWrop((ImageView) holder.cdFindViewById(R.id.img_un_me_card_info_img), item.getFilePath());
        holder.addItemClickListener(R.id.img_un_me_card_info_img);
    }
}
