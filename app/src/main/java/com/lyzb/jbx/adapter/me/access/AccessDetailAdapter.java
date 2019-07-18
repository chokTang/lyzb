package com.lyzb.jbx.adapter.me.access;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.me.access.AccessMemberModel;
import com.szy.yishopcustomer.ViewModel.samecity.Data;

import java.util.List;

/**
 * 访客明细列表
 */
public class AccessDetailAdapter extends BaseRecyleAdapter<AccessMemberModel> {

    private boolean isShowInterAccount = false;//是否显示设置意向客户按钮
    private int dataType = DataType.ALL.getValue();

    public AccessDetailAdapter(Context context, List<AccessMemberModel> list) {
        super(context, R.layout.recycle_access_detail_item, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AccessMemberModel item) {
        if (dataType==DataType.ACRTICE.getValue()){
            holder.setText(R.id.tv_member_name, item.getGsName());
        }else {
            holder.setText(R.id.tv_member_name, item.getGaName());
        }
        String contentValue = getContentValue(item);
        if (!TextUtils.isEmpty(contentValue)) {
            contentValue = "看了" + contentValue;
        }
        holder.setText(R.id.tv_member_value, contentValue);

        holder.setVisible(R.id.img_vip, item.getUserVipAction().size() > 0);
        holder.setRadiusImageUrl(R.id.img_member_header, item.getHeadimg(), 4);

        if (isShowInterAccount) {
            holder.setVisible(R.id.tv_inter_account, true);
            TextView tv_inter_account = holder.cdFindViewById(R.id.tv_inter_account);
            if (item.isCustomersStatus()) {
                tv_inter_account.setText("已为意向");
                tv_inter_account.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor3));
                tv_inter_account.setBackgroundResource(R.drawable.shape_gray_round);
            } else {
                tv_inter_account.setText("设为意向");
                tv_inter_account.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor1));
                tv_inter_account.setBackgroundResource(R.drawable.shape_gray_round_f3);
            }
        } else {
            holder.setVisible(R.id.tv_inter_account, false);
        }

        holder.addItemClickListener(R.id.img_member_header);
        holder.addItemClickListener(R.id.tv_inter_account);
    }

    public void setShowInterAccount(boolean showInterAccount) {
        isShowInterAccount = showInterAccount;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    private String getContentValue(AccessMemberModel item){
        StringBuilder builder = new StringBuilder();
       if (dataType ==DataType.ALL.getValue()){
           if (item.getUserExtNum() > 0) {
               builder.append(String.format("名片%d次", item.getUserExtNum()));
           }
           if (item.getTopicNum() > 0) {
               if (!TextUtils.isEmpty(builder.toString())) {
                   builder.append("，");
               }
               builder.append(String.format("动态%d次", item.getTopicNum()));
           }
           if (item.getGoodsNum() > 0) {
               if (!TextUtils.isEmpty(builder.toString())) {
                   builder.append("，");
               }
               builder.append(String.format("商品%d次", item.getGoodsNum()));
           }
           if (item.getHotNum() > 0) {
               if (!TextUtils.isEmpty(builder.toString())) {
                   builder.append("，");
               }
               builder.append(String.format("热文%d次", item.getHotNum()));
           }
       }else if (dataType==DataType.CARD.getValue()){
           if (item.getUserExtNum() > 0) {
               builder.append(String.format("名片%d次", item.getUserExtNum()));
           }
       }else if (dataType== DataType.DYNAMIC.getValue()){
           if (item.getTopicNum() > 0) {
               builder.append(String.format("动态%d次", item.getTopicNum()));
           }
       }else if (dataType==DataType.GOODS.getValue()){
           if (item.getGoodsNum() > 0) {
               builder.append(String.format("商品%d次", item.getGoodsNum()));
           }
       }else if (dataType==DataType.ACRTICE.getValue()){
           if (item.getVisitcount() > 0) {
               builder.append(String.format("热文%d次", item.getVisitcount()));
           }
       }
        return builder.toString();
    }
}
