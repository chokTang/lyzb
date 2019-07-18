package com.lyzb.jbx.adapter.me.card;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CardInfoModel;

import java.io.File;
import java.util.List;

/**
 * @author wyx
 * @role 个人简介or个人荣誉 图片适配 adapter
 * @time 2019 2019/3/11 11:09
 */

public class CdInfoImgAdapter extends BaseRecyleAdapter<CardInfoModel> {

    public CdInfoImgAdapter(Context context, List<CardInfoModel> list) {
        super(context, R.layout.item_un_me_cd_info_img, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CardInfoModel item) {

        ImageView imageView = holder.cdFindViewById(R.id.img_un_me_card_info_img);
        imageView.setImageURI(Uri.fromFile(new File(item.getImgUrl())));

        //设置 maxWidth maxHeight
        imageView.setMaxWidth(ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(24));
        imageView.setMaxWidth(DensityUtil.dpTopx(180));
    }
}
