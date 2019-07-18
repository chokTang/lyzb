package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Deposit.WithdrawalList;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.BindAccountViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BindAccountAdapter extends RecyclerView.Adapter<BindAccountViewHolder> {

    public List<WithdrawalList> data;
    public View.OnClickListener onClickListener;

    public BindAccountAdapter() {
        this.data = new ArrayList<>();
    }

    public void setData(List<WithdrawalList> data) {
        this.data = data;
    }

    @Override
    public BindAccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_fragment_bind_account, parent, false);
        BindAccountViewHolder viewHolder = new BindAccountViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BindAccountViewHolder holder, int position) {
        WithdrawalList item = data.get(position);
        holder.name.setText("姓名："+item.account_name);
        holder.account.setText("账号："+item.account);
        if (item.account_type.equals("支付宝")){
            holder.image.setBackgroundResource(R.mipmap.ic_alipay);
            holder.bank.setText("开户行：--");
        }else if (item.account_type.equals("银行转账")){
            holder.image.setBackgroundResource(R.mipmap.ic_bank_card);
            holder.bank.setText("开户行：" + item.bank_account);
        }else {
            holder.image.setBackgroundResource(R.mipmap.btn_sign_weixin);
        }
        Utils.setPositionForTag(holder.delete, position);
        Utils.setViewTypeForTag(holder.delete, ViewType.VIEW_TYPE_ACCOUNT_DELETE);
        holder.delete.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
