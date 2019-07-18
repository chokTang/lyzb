package com.lyzb.jbx.adapter.statistics;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.statistics.AnalysisTransactionContentModel;
import com.lyzb.jbx.model.statistics.AnalysisTransactionDateModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/18 15:24
 */

public class AnalysisTransactionAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AnalysisTransactionAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_transaction_record_date);
        addItemType(1, R.layout.item_transaction_record_content);
    }


    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                AnalysisTransactionDateModel b = (AnalysisTransactionDateModel) item;
                //时间
                helper.setText(R.id.transaction_record_date_time_tv, b.getAddTime());
                //交易笔数
                helper.setText(R.id.transaction_record_date_number_tv, String.valueOf(b.getOrderCount()));
                //合计金额
                helper.setText(R.id.transaction_record_date_money_tv, "¥" + b.getOrderAmount());
                break;
            case 1:
                AnalysisTransactionContentModel m = (AnalysisTransactionContentModel) item;
                //商品标题
                helper.setText(R.id.transaction_record_content_title_tv, m.getGoodsName());
                //数量
                helper.setText(R.id.transaction_record_content_number_tv, "X" + m.getGoodsNumber());
                //金额
                helper.setText(R.id.transaction_record_content_money_tv, "¥" + m.getGoodsPrice());
                //团购企业账号名为空时认为是具体用户的交易，隐藏名称，反之为企业，展示企业、企业使用者名称
                if (TextUtils.isEmpty(m.getAccountName())) {
                    helper.setVisible(R.id.transaction_record_content_name_tv, false);
                } else {
                    helper.setVisible(R.id.transaction_record_content_name_tv, true);
                    //企业使用者名称为空是只展示企业名称，反之都展示
                    if (TextUtils.isEmpty(m.getUserName())) {
                        helper.setText(R.id.transaction_record_content_name_tv, m.getAccountName());
                    } else {
                        helper.setText(R.id.transaction_record_content_name_tv, m.getAccountName() + "(" + m.getUserName() + ")");
                    }
                }
                break;
            default:
        }
    }
}
