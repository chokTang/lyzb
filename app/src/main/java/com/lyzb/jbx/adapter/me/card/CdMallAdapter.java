package com.lyzb.jbx.adapter.me.card;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CardMallModel;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

/****
 * 我(TA)的名片-商城 adapter
 *
 */
public class CdMallAdapter extends BaseRecyleAdapter<CardMallModel.ListBean> {

    public boolean isMeGood = true;
    public boolean isShowBottom = true;

    public void setMeGood(boolean meGood) {
        isMeGood = meGood;
    }

    public void setShowBottom(boolean isShowBottom) {
        this.isShowBottom = isShowBottom;
    }

    public CdMallAdapter(Context context, List<CardMallModel.ListBean> list) {
        super(context, R.layout.item_un_me_card_mall, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CardMallModel.ListBean item) {
        TextView textView = holder.cdFindViewById(R.id.tv_un_me_card_good_status);
        holder.setVisible(R.id.tv_un_me_card_good_status, false);

        holder.setImageUrl(R.id.img_un_me_card_good_img, Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, item.getGoods_image()));
        holder.setText(R.id.tv_un_me_card_good_name, item.getGoods_name());

        holder.setText(R.id.tv_un_me_card_good_tag_price, "￥" + item.getGoods_price());
        holder.setText(R.id.tv_un_me_card_good_tag_ingot, "+" + item.getMax_integral_num() + "元宝");
        holder.setText(R.id.tv_un_me_card_good_shop_name, item.getShop_name());

        //审核状态 0 待审核 1 审核通过 2 未通过
        switch (item.getGoods_audit()) {
            case 0://待审核
                textView.setVisibility(View.VISIBLE);
                textView.setText("待审核");
                textView.setSelected(true);
                break;
            case 1://审核通过
                textView.setVisibility(View.GONE);
                break;
            case 2://未通过
                textView.setVisibility(View.VISIBLE);
                textView.setText("未通过");
                textView.setSelected(false);
                break;
        }

        if (isMeGood) {
            if (item.getCan_del()) {
                holder.setVisible(R.id.ll_pop, true);
            } else {
                holder.setVisible(R.id.ll_pop, false);
            }
        } else {
            holder.setVisible(R.id.ll_pop, false);
        }

        holder.setVisible(R.id.ll_bottom, isShowBottom);

        holder.addItemClickListener(R.id.img_un_me_card_good_img);
        holder.addItemClickListener(R.id.tv_un_me_card_good_name);
        holder.addItemClickListener(R.id.ll_pop);
    }
}
